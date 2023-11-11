package compilador;

import java.util.Stack;

public class GeneracionCodigo {

    private Stack<String> tokens = new Stack<>();
    private TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
    private StringBuilder codigo_assembler = new StringBuilder();
    private int numero_var_auxiliar = 0;
    private String ultimo_comparador;

    public GeneracionCodigo() {
        generarCabecera();
        for (String token: Polaca.getInstance().getPolaca())
            switch (token) {
                case "+", "-", "*", "/", "<", ">", ">=", "<=", "=", "!!", "==" -> generarOperador(token);
                case "BI" -> generarSalto("BI");
                case "BF" -> generarSalto("BF");
                case "CALL" -> generarLlamadaAFuncion();
                default -> tokens.push(token);
            }
        codigo_assembler.append("invoke ExitProcess, 0").append('\n');
        codigo_assembler.append("end start");
        showAssembler();
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
        codigo_assembler.append("start:").append('\n');
    }

    private void generarOperador(String token) {
        String op2 = tokens.pop();
        String op1 = tokens.pop();
        if (!TS.getAtributo(op1, Constantes.TYPE).equals(TS.getAtributo(op2, Constantes.TYPE))){
            Logger.getInstance().logError("[Generacion codigo] Error, tipos incompatibles");
        }
        else {
            String tipo = (String) TS.getAtributo(op1, Constantes.TYPE);
            switch (tipo) {
                case "INT" -> generarInstruccionesEnteros(op1, op2, token);
                case "FLOAT" -> generarInstruccionesFlotantes(op1, op2, token);
                case "ULONG" -> generarInstruccionesULong(op1, op2, token);
            }
        }
    }

    private void generarInstruccionesEnteros(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("ADD AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("SUB AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigo_assembler.append("MV AX, ").append(op1).append("\n"); //EN MULTIPLICACION SOLO PUEDO UTILIZAR REG EAX
                codigo_assembler.append("MULI AX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigo_assembler.append("XOR DX, DX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MV AX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR DX:AX
                codigo_assembler.append("MV BX, ").append(op2).append('\n');
                codigo_assembler.append("IDIV BX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX = resultado
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append('\n'); // MUEVO LO QUE QUEDO EN AX A VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<", ">", ">=", "<=", "!!", "==" -> {
                codigo_assembler.append("MV AX, ").append(op1).append("\n");
                codigo_assembler.append("CMP AX, ").append(op2).append("\n");
                ultimo_comparador = operador;
            }
            case "=" -> {
                codigo_assembler.append("MV " + "AX, ").append(op1).append("\n"); // Guardo valor de la expresion de lado derecho
                codigo_assembler.append("MV ").append(op2).append(", AX").append("\n"); // Almacento guardado en AX en la var del lado izquierdo
            }
        }
    }

    private void generarInstruccionesULong(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador) {
            case "+" -> {
                variable_auxiliar = nuevaVariableAuxiliar("ULONG");
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("ADD EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "-" -> {
                variable_auxiliar = nuevaVariableAuxiliar("ULONG");
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("SUB EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "*" -> {
                variable_auxiliar = nuevaVariableAuxiliar("ULONG");
                codigo_assembler.append("MV EAX, ").append(op1).append("\n"); //EN MULTIPLICACION SOLO PUEDO UTILIZAR REG EAX
                codigo_assembler.append("MUL EAX, ").append(op2).append("\n");
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
            }
            case "/" -> {
                variable_auxiliar = nuevaVariableAuxiliar("ULONG");
                codigo_assembler.append("XOR EDX, EDX").append('\n'); // INICIALIZO DX EN 0,
                codigo_assembler.append("MV EAX, ").append(op1).append('\n'); // EL DIVIDENDO DEBE ESTAR EN EL PAR DX:AX
                codigo_assembler.append("MV EBX, ").append(op2).append('\n');
                codigo_assembler.append("IDIV EBX").append('\n'); // DIVIDO LO QUE HAY DE DX:AX POR BX -> DX = resto, AX = resultado
                codigo_assembler.append("MV ").append(variable_auxiliar).append(", EAX").append('\n'); // MUEVO LO QUE QUEDO EN AX A VAR AUX
                tokens.push(variable_auxiliar);
            }
            case "<", ">", ">=", "<=", "!!", "==" -> {
                codigo_assembler.append("MV EAX, ").append(op1).append("\n");
                codigo_assembler.append("CMP EAX, ").append(op2).append("\n");
                ultimo_comparador = operador;
            }
            case "=" -> {
                codigo_assembler.append("MV " + "EAX, ").append(op1).append("\n"); // Guardo valor de la expresion de lado derecho
                codigo_assembler.append("MV ").append(op2).append(", EAX").append("\n"); // Almacento guardado en AX en la var del lado izquierdo
            }
        }
    }

    private void generarInstruccionesFlotantes(String op1, String op2, String operador) {
    }


    private void generarLlamadaAFuncion() {
    }

    private void generarSalto(String tipo) {
        String direccion_salto = tokens.pop();
        if (tipo.equals("BI")){
            codigo_assembler.append("JMP ").append("L").append(direccion_salto).append('\n');
        }
        else {
            switch (ultimo_comparador) {
                case "<" -> codigo_assembler.append("JB ").append("L").append(direccion_salto).append('\n');
                case "<=" -> codigo_assembler.append("JBE ").append("L").append(direccion_salto).append('\n');
                case ">=" -> codigo_assembler.append("JAE ").append("L").append(direccion_salto).append('\n');
                case ">" -> codigo_assembler.append("JA ").append("L").append(direccion_salto).append('\n');
                case "!!" -> codigo_assembler.append("JNE ").append("L").append(direccion_salto).append('\n');
                case "==" -> codigo_assembler.append("JE ").append("L").append(direccion_salto).append('\n');
            }
        }
    }

    private String nuevaVariableAuxiliar(String tipo) {
        String retorno = "@aux" + numero_var_auxiliar;
        TS.putLexema(retorno, Constantes.IDENTIFICADOR);
        TS.agregarAtributo(retorno, Constantes.TYPE, tipo);
        numero_var_auxiliar += 1;
        return retorno;
    }

    public void showAssembler(){
        System.out.println(codigo_assembler);
    }
}
