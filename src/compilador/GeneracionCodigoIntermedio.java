package compilador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class GeneracionCodigoIntermedio {
    private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();
    private static Stack<String> ambitos = new Stack<String>();

    private static GeneracionCodigoIntermedio instance = null;

    public static GeneracionCodigoIntermedio getInstance() {
        if (instance == null) {
            instance = new GeneracionCodigoIntermedio();
            ambitos.push("main");
        }

        return instance;
    }

    public void apilarAmbito(String nombre_ambito) {
        ambitos.push(nombre_ambito);
    }

    public void desapilarAmbito() {
        ambitos.pop();
    }

    public String generarAmbito() {
        Iterator<String> it = ambitos.iterator();
        String ambitoCompleto = "";

        while (it.hasNext()) {
            ambitoCompleto += ":" + it.next();
        }

        return ambitoCompleto;
    }

    public void agregarUsoAIdentificador(String identificador, String uso) {

        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

        TS.putIdentificadorUso(identificador, uso);
    }

    public void agregarVariableADeclarar(String variable) {
        lista_variables_a_declarar.add(variable);
    }

    public void agregarTipoAListaDeVariables(String type) {

        Iterator<String> it = lista_variables_a_declarar.iterator();
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

        while (it.hasNext()) {
            String variableActual = it.next();
            TS.putTipo(variableActual, type);
        }
    }

    public void agregarTipoAParametroDeFuncion(String parametro, String type) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        TS.putTipo(parametro, type);
    }

    public void agregarAmbitoAListaDeVariables() {

        Iterator<String> it = lista_variables_a_declarar.iterator();
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        String ambitoCompleto = generarAmbito();

        while (it.hasNext()) {
            String variableActual = it.next();
            TS.swapLexemas(variableActual, variableActual + ambitoCompleto);
        }
    }

    public void agregarAmbitoAIdentificador(String identificador) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        String ambitoCompleto = generarAmbito();

        TS.swapLexemas(identificador, identificador + ambitoCompleto);
    }

    public void removerListaVariablesADeclarar() {
        lista_variables_a_declarar.clear();
    }
}
