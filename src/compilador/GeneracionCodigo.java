package compilador;

import java.util.Stack;

public class GeneracionCodigo {

    private Stack<String> tokens = new Stack<>();
    private TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
    private StringBuilder codigoAssembler = new StringBuilder();
    private int numero_var_auxiliar = 0;
    private String ultimo_comparador;

    public GeneracionCodigo() {
        for (String token: Polaca.getInstance().getPolaca())
            switch (token) {
                case "+", "-", "*", "/", "<", ">", ">=", "<=", "=", "!!" -> generarOperador(token);
                case "BI", "BF" -> generarSalto();
                case "CALL" -> generarLlamadaAFuncion();
                default -> tokens.push(token);
            }
        showAssembler();
    }

    private void generarOperador(String token) {
        String op2 = tokens.pop();
        String op1 = tokens.pop();
        Logger.getInstance().logWarning(op1);
        Logger.getInstance().logWarning(op2);
        if (!TS.getAtributo(op1, Constantes.TYPE).equals(TS.getAtributo(op2, Constantes.TYPE))){
            Logger.getInstance().logError("[Generacion codigo] Error, tipos incompatibles");
        }
        else {
            String tipo = (String) TS.getAtributo(op1, Constantes.TYPE);
            switch (tipo){
                case "INT":
                    generarInstruccionesEnteros(op1, op2, token);
                case "FLOAT":
                    generarInstruccionesFlotantes(op1, op2, token);
                case "ULONG":
                    generarInstruccionesULong(op1, op2, token);
            }
        }
    }

    private void generarInstruccionesEnteros(String op1, String op2, String operador) {
        String variable_auxiliar;
        switch (operador){
            case "+":
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigoAssembler.append("MV AX, ").append(op1).append("\n");
                codigoAssembler.append("ADD AX, ").append(op2).append("\n");
                codigoAssembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
                break;
            case "-":
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigoAssembler.append("MV AX, ").append(op1).append("\n");
                codigoAssembler.append("SUB AX, ").append(op2).append("\n");
                codigoAssembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
                break;
            case "*":
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                codigoAssembler.append("MV AX, ").append(op1).append("\n"); //EN MULTIPLICACION SOLO PUEDO UTILIZAR REG EAX
                codigoAssembler.append("MULI AX, ").append(op2).append("\n");
                codigoAssembler.append("MV ").append(variable_auxiliar).append(", AX").append("\n");
                tokens.push(variable_auxiliar);
                break;
            case "/":
                variable_auxiliar = nuevaVariableAuxiliar("INT");
                tokens.push(variable_auxiliar);
            case "<":
            case ">":
            case ">=":
            case "<=":
            case "!!":
                codigoAssembler.append("MV AX, ").append(op1).append("\n");
                codigoAssembler.append("CMP AX, ").append(op2).append("\n");
                ultimo_comparador = operador;
                break;
            case "=":
                codigoAssembler.append("MV " + "AX, ").append(op1).append("\n");
                codigoAssembler.append("MV ").append(op2).append(", AX").append("\n");
        }
    }

    private void generarInstruccionesFlotantes(String op1, String op2, String operador) {
    }

    private void generarInstruccionesULong(String op1, String op2, String operador) {
    }


    private void generarLlamadaAFuncion() {
    }

    private void generarSalto() {
    }

    public void generarCabecera(){

    }

    private String nuevaVariableAuxiliar(String tipo) {
        String retorno = "@aux" + numero_var_auxiliar;
        TS.putLexema(retorno, Constantes.IDENTIFICADOR);
        TS.agregarAtributo(retorno, Constantes.TYPE, tipo);
        numero_var_auxiliar += 1;
        return retorno;
    }

    public void showAssembler(){
        System.out.println(codigoAssembler);
    }
}
