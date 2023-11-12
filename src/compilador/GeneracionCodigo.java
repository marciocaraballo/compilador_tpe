package compilador;

import java.util.Stack;

public class GeneracionCodigo {

    private Stack<String> tokens = new Stack<>();
    private TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
    private StringBuilder codigo_assembler = new StringBuilder();
    private int numero_var_auxiliar = 0;
    private String tipo_salto;

    public GeneracionCodigo() {
        generarCabecera();
        for (String nombre_polaca : Polaca.getInstance().getNombresPolaca()) { // Recorro las diferentes polacas generadas
            codigo_assembler.append("-------------------------- ESTO ES PARA MEJORAR VISUALIZACION -----------------");
            codigo_assembler.append(nombre_polaca.substring(1)).append(":").append('\n');
            int i = 0;
            for (String token : Polaca.getInstance().getPolaca(nombre_polaca)){
                if (Polaca.getInstance().esLabel(i, nombre_polaca)) { // Verifico si es una posicion a la cual debo agregar etiqueta
                    codigo_assembler.append("L").append(i).append(": "); // Agrego la etiqueta
                }
                generarInstrucciones(token);
                i++;
            }

            if (nombre_polaca.equals(":main")){
                codigo_assembler.append("invoke ExitProcess, 0").append('\n');
                codigo_assembler.append("end ").append("main").append('\n');
            }
            else
                codigo_assembler.append("end");
        }
        generarData();
        showAssembler();
    }

    private void generarData() {
        int posicion_data = codigo_assembler.indexOf(".code");
        codigo_assembler.insert(posicion_data, '\n');
        for (String lexema: TablaDeSimbolos.getInstance().getLexemas()){
            String dato = getAtributos(lexema);
            if (dato != null)
                codigo_assembler.insert(posicion_data, dato + '\n');
        }
    }

    private String getAtributos(String lexema) {
        StringBuilder dato = new StringBuilder(lexema);
        if (TS.getAtributo(lexema, Constantes.TOKEN).equals(Constantes.CADENA))
            dato.append(" DB");
        else{
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
            default -> tokens.push(token);
        }
    }

    private void generarCabecera() {
        codigo_assembler.append(".386").append('\n');
        codigo_assembler.append(".model flat, stdcall");
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
            Logger.getInstance().logError("[Generacion codigo] Error, tipos incompatibles");
        } else {
            String tipo = (String) TS.getAtributo(op1, Constantes.TYPE);
            switch (tipo) {
                case Constantes.TYPE_INT -> generarInstruccionesEnteros(op1, op2, token);
                case Constantes.TYPE_FLOAT -> generarInstruccionesFlotantes(op1, op2, token);
                case Constantes.TYPE_ULONG -> generarInstruccionesULong(op1, op2, token);
            }
        }
    }

    private void generarInstruccionesEnteros(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("ADD AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("SUB AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("MV AX, ").append(op1).append("\n"); // EN MULTIPLICACION SOLO PUEDO UTILIZAR
                                                                             // REG AX
                codigo_assembler.append("MULI AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_INT);
                codigo_assembler.append("XOR DX, DX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MV AX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR DX:AX
                codigo_assembler.append("MV BX, ").append(op2).append('\n');
                codigo_assembler.append("IDIV BX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX
                                                                 // = resultado
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append('\n'); // MUEVO LO QUE
                                                                                                      // QUEDO EN AX A
                                                                                                      // VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JGE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case ">" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JLE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case ">=" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JL"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "<=" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JG"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "!!" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "==" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                tipo_salto = "JNE"; // El tipo de salto debe ser la negacion, ya que siempre bifurco por falso
            }
            case "=" -> {
                codigo_assembler.append("MV " + "AX, ").append(op1).append("\n"); // Guardo valor de la expresion de
                                                                                  // lado derecho
                codigo_assembler.append("MV ").append(op2).append(", AX").append("\n"); // Almacento guardado en AX en
                                                                                        // la var del lado izquierdo
            }
        }
    }

    private void generarInstruccionesULong(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("ADD EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("SUB EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("MV EAX, ").append(op1).append("\n"); // EN MULTIPLICACION SOLO PUEDO UTILIZAR
                                                                              // REG EAX
                codigo_assembler.append("MUL EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_ULONG);
                codigo_assembler.append("XOR EDX, EDX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MV EAX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR DX:AX
                codigo_assembler.append("MV EBX, ").append(op2).append('\n');
                codigo_assembler.append("IDIV EBX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX
                                                                  // = resultado
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append('\n'); // MUEVO LO QUE
                                                                                                       // QUEDO EN AX A
                                                                                                       // VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JB";
            }
            case ">" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JA";
            }
            case ">=" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JAE";
            }
            case "<=" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JBE";
            }
            case "!!" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JNE";
            }
            case "==" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                tipo_salto = "JE";
            }
            case "=" -> {
                codigo_assembler.append("MV " + "EAX, ").append(op1).append("\n"); // Guardo valor de la expresion de
                                                                                   // lado derecho
                codigo_assembler.append("MV ").append(op2).append(", EAX").append("\n"); // Almacento guardado en AX en
                                                                                         // la var del lado izquierdo
            }
        }
    }

    public void comparacionesParaFlotantes(String op1, String op2) {
        codigo_assembler.append("FLD ").append(op2).append("\n");
        codigo_assembler.append("FCOM ").append(op1).append("\n");
        codigo_assembler.append("FSTSW aux_mem").append(op1).append("\n");
        codigo_assembler.append("MOV AX, aux_mem").append(op1).append("\n");
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
                codigo_assembler.append("FSTP ").append(variable_auxiliar);
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FSUB ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar);
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FMUL ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar);
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar(Constantes.TYPE_FLOAT);
                codigo_assembler.append("FLD ").append(op2).append('\n');
                codigo_assembler.append("FLD ").append(op1).append('\n');
                codigo_assembler.append("FDIV ").append('\n');
                codigo_assembler.append("FSTP ").append(variable_auxiliar);
                tokens.push(variable_auxiliar);
            }
            case "<" -> {
                comparacionesParaFlotantes(op1, op2);
                tipo_salto = "JB";
            }
            case ">" -> {
                comparacionesParaFlotantes(op1, op2);
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
                codigo_assembler.append("MV " + "EAX, ").append(op1).append("\n"); // Guardo valor de la expresion de
                                                                                   // lado derecho
                codigo_assembler.append("MV ").append(op2).append(", EAX").append("\n"); // Almacento guardado en AX en
                                                                                         // la var del lado izquierdo
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
