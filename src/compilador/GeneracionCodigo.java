package compilador;

import java.util.Stack;

public class GeneracionCodigo {

    private Stack<String> tokens = new Stack<>();
    private TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
    private StringBuilder codigo_assembler = new StringBuilder();
    private int numero_var_auxiliar = 0;
    private String tipo_salto;

    private final String ERROR_OVERFLOW_PRODUCTO_ENTEROS = "overflow_enteros";
    private final String ERROR_OVERFLOW_SUMA_FLOTANTES = "overflow_flotantes";
    private final String ERROR_RECURSIVIDAD = "error_recursividad";
    private int flag_recursion = 1;

    public GeneracionCodigo() {
        generarCabecera();
        for (String nombre_polaca : Polaca.getInstance().getNombresPolaca()) { // Recorro las diferentes polacas
                                                                               // generadas
            codigo_assembler
                    .append(";-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- \n");
            codigo_assembler.append(nombre_polaca.substring(1)).append(":").append('\n');
            if (!nombre_polaca.equals(":main")) {
                generarInstruccionesChequeoRecursividad();
            }
            int i = 0;
            for (String token : Polaca.getInstance().getPolaca(nombre_polaca)) {
                if (Polaca.getInstance().esLabel(i, nombre_polaca)) { // Verifico si es una posicion a la cual debo
                                                                      // agregar etiqueta
                    codigo_assembler.append("L").append(i).append(": "); // Agrego la etiqueta
                }
                generarInstrucciones(token);
                i++;
            }

            if (nombre_polaca.equals(":main")) {
                codigo_assembler.append("invoke ExitProcess, 0").append('\n');
                codigo_assembler.append("end ").append("main").append('\n');
            } else {
                codigo_assembler.append("MOV recursion_flag, 0").append('\n'); // Indico que la funcion ya termino su
                                                                               // ejecucion
                codigo_assembler.append("end");
            }
        }
        generarData();
        showAssembler();
    }

    private void generarInstruccionesChequeoRecursividad() {
        codigo_assembler.append("CMP recursion_flag, ").append(flag_recursion).append('\n');
        codigo_assembler.append("JNE CONTINUAR_EJECUCION \n"); // Si el flag de recursion es distinto de
                                                               // 'flag_recursion' continuo la ejecucion
        codigo_assembler.append("invoke MessageBox, NULL, addr ").append(ERROR_RECURSIVIDAD)
                .append(", addr ").append(ERROR_RECURSIVIDAD)
                .append(", MB_OK").append('\n');
        codigo_assembler.append("invoke ExitProcess, 0").append('\n');
        codigo_assembler.append("CONTINUAR_EJECUCION: ").append('\n');
        codigo_assembler.append("MOV recusion_flag, ").append(flag_recursion).append('\n'); // Indico que la funcion
                                                                                            // esta siendo ejecutada
        flag_recursion += 1;
    }

    private void generarData() {
        int posicion_data = codigo_assembler.indexOf(".code");
        codigo_assembler.insert(posicion_data, ERROR_OVERFLOW_PRODUCTO_ENTEROS
                + " db \" El producto de los valores ha sobrepasado el rango \" , 0" + '\n');
        codigo_assembler.insert(posicion_data,
                ERROR_OVERFLOW_SUMA_FLOTANTES + " db \" La suma de los valores ha sobrepasado el rango \" , 0" + '\n');
        codigo_assembler.insert(posicion_data,
                ERROR_RECURSIVIDAD + " db \" No se admite recursividad de invocación a funciones \" , 0" + '\n');
        codigo_assembler.insert(posicion_data, "recursion_flag DW 0 \n");
        for (String lexema : TablaDeSimbolos.getInstance().getLexemas()) {
            String dato = getAtributos(lexema);
            if (dato != null)
                codigo_assembler.insert(posicion_data, dato + '\n');
        }
    }

    private boolean cumple_condicion(String lexema) {
        String uso = (String) TS.getAtributo(lexema, Constantes.USE);
        if (TS.getAtributo(lexema, Constantes.TOKEN).equals(Constantes.CADENA)) {
            return false;
        }
        if (uso != null) {
            return uso.equals("nombre_funcion") ||
                    uso.equals("nombre_clase") ||
                    uso.equals("nombre_metodo");
        }
        return true;

    }

    private String getAtributos(String lexema) {
        if (cumple_condicion(lexema)) {
            return null;
        }
        StringBuilder dato = new StringBuilder();
        if (TS.getAtributo(lexema, Constantes.TOKEN).equals(Constantes.CADENA)) {
            dato.append(lexema.replace(" ", "").replace("%", ""));
            dato.append(" DB ");
            dato.append(lexema.replace("%", "\"")).append(", 0");
        } else {
            dato.append(lexema.replaceAll("\\:", "_"));
            String tipo = (String) TS.getAtributo(lexema, Constantes.TYPE);
            if (tipo != null) {
                if (tipo.equals("INT"))
                    dato.append(" DW ?");
                else if (tipo.equals("ULONG"))
                    dato.append(" DD ?");
                else
                    dato.append(" DT ?");
            }
        }

        return String.valueOf(dato);

    }

    private void generarInstrucciones(String token) {
        switch (token) {
            case "+", "-", "*", "/", "<", ">", ">=", "<=", "=", "!!", "==" -> generarOperador(token);
            case "BI" -> generarSalto("BI");
            case "BF" -> generarSalto("BF");
            case "CALL" -> generarLlamadaAFuncion();
            case "RETURN" -> codigo_assembler.append("RET").append('\n');
            case "PRINT" -> generarImpresionPantalla();
            default -> tokens.push(token);
        }
    }

    private void generarImpresionPantalla() {
        String mje = tokens.pop();
        mje = mje.replace("%", "").replace(" ", "");
        codigo_assembler.append("invoke MessageBox, NULL, addr ").append(mje).append(", addr ").append(mje)
                .append(", MB_OK").append('\n');
    }

    private void generarCabecera() {
        codigo_assembler.append(".386").append('\n');
        codigo_assembler.append(".model flat, stdcall").append('\n');
        codigo_assembler.append("option casemap :none").append('\n');
        codigo_assembler.append("include \\masm32\\include\\windows.inc").append('\n');
        codigo_assembler.append("include \\masm32\\include\\kernel32.inc").append('\n');
        codigo_assembler.append("include \\masm32\\include\\user32.inc").append('\n');
        codigo_assembler.append("includelib \\masm32\\lib\\kernel32.lib").append('\n');
        codigo_assembler.append("includelib \\masm32\\lib\\user32.lib").append('\n');
        codigo_assembler.append(".data").append('\n');
        codigo_assembler.append(".code").append('\n');
    }

    private void generarOperador(String token) {
        String op2 = tokens.pop();
        String op1 = tokens.pop();
        if (!TS.getAtributo(op1, Constantes.TYPE).equals(TS.getAtributo(op2, Constantes.TYPE))) {
            tokens.push(op2);
            tokens.push(op1);
            Logger.getInstance().logError("[Generacion codigo] Tipos incompatibles "
                    + op1 + ": " + TS.getAtributo(op1, Constantes.TYPE) + "/" + op2 + ": "
                    + TS.getAtributo(op2, Constantes.TYPE));
        } else {
            String tipo = (String) TS.getAtributo(op1, Constantes.TYPE);

            Integer tipoTokenOp1 = (Integer) TS.getAtributo(op1, Constantes.TOKEN);

            if (tipoTokenOp1 == Constantes.IDENTIFICADOR) {
                op1 = op1.replaceAll("\\:", "_");
            } else {
                op1 = op1.replace("_i", "");
            }

            Integer tipoTokenOp2 = (Integer) TS.getAtributo(op2, Constantes.TOKEN);

            if (tipoTokenOp2 == Constantes.IDENTIFICADOR) {
                op2 = op2.replaceAll("\\:", "_");
            } else {
                op2 = op2.replace("_i", "");
            }

            switch (tipo) {
                case Constantes.TYPE_INT -> {
                    generarInstruccionesEnteros(op1, op2, token);
                }
                case Constantes.TYPE_FLOAT -> {
                    generarInstruccionesFlotantes(op1, op2, token);
                }
                case Constantes.TYPE_ULONG -> {
                    op1 = op1.replace("_ul", "");
                    op2 = op2.replace("_ul", "");
                    generarInstruccionesULong(op1, op2, token);
                }
            }
        }
    }

    private void generarInstruccionesEnteros(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("ADD AX, ").append(op2).append("\n");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("SUB AX, ").append(op2).append("\n");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MOV AX, ").append(op1).append("\n"); // EN MULTIPLICACION SOLO PUEDO UTILIZAR
                                                                              // REG AX
                codigo_assembler.append("MULI AX, ").append(op2).append("\n");
                /*
                 * Si la multiplicacion se excede de rango, se setea el flag OF en 1, luego la
                 * instruccion 'JNO' salta o no dependiendo
                 * del valor del flag
                 */
                codigo_assembler.append("JNO CONTINUAR_EJECUCION ").append('\n');

                // Si hay overflow, muestro el error por pantalla y finalizo ejecucion
                codigo_assembler.append("invoke MessageBox, NULL, addr ").append(ERROR_OVERFLOW_PRODUCTO_ENTEROS)
                        .append(", addr ").append(ERROR_OVERFLOW_PRODUCTO_ENTEROS)
                        .append(", MB_OK").append('\n');
                codigo_assembler.append("invoke ExitProcess, 0").append('\n');

                // Si no hay overflow, continuo normalmente la ejecucion
                codigo_assembler.append("CONTINUAR_EJECUCION:");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("XOR DX, DX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MOV AX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR DX:AX
                codigo_assembler.append("MOV BX, ").append(op2).append('\n');
                codigo_assembler.append("IDIV BX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX
                                                                 // = resultado
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", AX").append('\n'); // MUEVO LO QUE
                                                                                                       // QUEDO EN AX A
                                                                                                       // VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JGE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case ">" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JLE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case ">=" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JL"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "<=" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JG"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "!!" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "==" -> {
                codigo_assembler.append("MOV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JNE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "=" -> {
                codigo_assembler.append("MOV " + "AX, ").append(op1).append("\n"); // Guardo valor de la expresion de
                                                                                   // lado derecho
                codigo_assembler.append("MOV ").append(op2).append(", AX").append("\n"); // Almacento guardado en AX en
                                                                                         // la var del lado izquierdo
            }
        }
    }

    private void generarInstruccionesULong(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("ADD EAX, ").append(op2).append("\n");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("SUB EAX, ").append(op2).append("\n");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n"); // EN MULTIPLICACION SOLO PUEDO UTILIZAR
                                                                               // REG EAX
                codigo_assembler.append("MUL EAX, ").append(op2).append("\n");
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("XOR EDX, EDX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MOV EAX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR
                                                                               // DX:AX
                codigo_assembler.append("MOV EBX, ").append(op2).append('\n');
                codigo_assembler.append("DIV EBX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX
                                                                  // = resultado
                codigo_assembler.append("MOV ").append(variable_auxiliar).append(", EAX").append('\n'); // MUEVO LO QUE
                                                                                                        // QUEDO EN AX A
                                                                                                        // VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JB";
            }
            case ">" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JA";
            }
            case ">=" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JAE";
            }
            case "<=" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JBE";
            }
            case "!!" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JNE";
            }
            case "==" -> {
                codigo_assembler.append("MOV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JE";
            }
            case "=" -> {
                codigo_assembler.append("MOV " + "EAX, ").append(op1).append("\n"); // Guardo valor de la expresion de
                                                                                    // lado derecho
                codigo_assembler.append("MOV ").append(op2).append(", EAX").append("\n"); // Almacento guardado en AX en
                                                                                          // la var del lado izquierdo
            }
        }
    }

    public void comparacionesParaFlotantes(String op1, String op2) {
        codigo_assembler.append("FLD ").append(op2).append("\n");
        codigo_assembler.append("FCOM ").append(op1).append("\n");
        codigo_assembler.append("FSTSW aux_mem").append(op1).append("\n");
        codigo_assembler.append("MOV EAX, aux_mem").append(op1).append("\n");
        codigo_assembler.append("SAHF").append(op1).append("\n");
    }

    private void generarInstruccionesFlotantes(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FADD ").append('\n');

                codigo_assembler.append("FCOM 15h").append('\n');
                codigo_assembler.append("FSTSW aux_mem").append('\n');
                codigo_assembler.append("MOV EAX, aux_mem").append('\n');
                codigo_assembler.append("SAHF").append('\n');
                codigo_assembler.append("JA CONTINUAR_EJECUCION").append('\n');

                codigo_assembler.append("invoke MessageBox, NULL, addr ").append(ERROR_OVERFLOW_SUMA_FLOTANTES)
                        .append(", addr ").append(ERROR_OVERFLOW_SUMA_FLOTANTES)
                        .append(", MB_OK").append('\n');
                codigo_assembler.append("invoke ExitProcess, 0").append('\n');

                codigo_assembler.append("CONTINUAR_EJECUCION: ");
                codigo_assembler.append("FSTP ").append(variable_auxiliar).append('\n');
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FSUB ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar).append('\n');
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FMUL ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar).append('\n');
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FDIV ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar).append('\n');
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JB";
            }
            case ">" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JA";
            }
            case ">=" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JAE";
            }
            case "<=" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JBE";
            }
            case "!!" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JNE";
            }
            case "==" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JE";
            }
            case "=" -> {
                codigo_assembler.append("FLD ").append(op1).append("\n");
                codigo_assembler.append("FSTP ").append(op2).append("\n");
            }
        }
    }

    private void generarLlamadaAFuncion() {
        codigo_assembler.append("CALL ").append(tokens.pop().substring(1)).append('\n');
    }

    private void generarSalto(String tipo) {
        String direccion_salto = tokens.pop();
        if (tipo.equals("BI"))
            codigo_assembler.append("JMP ").append("L").append(direccion_salto).append('\n');
        else
            codigo_assembler.append(tipo_salto).append(" ").append("L").append(direccion_salto).append('\n');
    }

    private String nuevaVariableAuxiliar(String tipo) {
        String retorno = "@aux" + numero_var_auxiliar;
        TS.putLexema(retorno, Constantes.IDENTIFICADOR);
        TS.agregarAtributo(retorno, Constantes.TYPE, tipo);
        numero_var_auxiliar += 1;
        return retorno;
    }

    public void showAssembler() {
        System.out.println(codigo_assembler);
    }
}
