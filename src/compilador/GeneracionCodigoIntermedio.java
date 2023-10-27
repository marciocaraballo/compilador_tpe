package compilador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class GeneracionCodigoIntermedio {
    private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();
    private static Stack<String> ambitos = new Stack<String>();
    private static Stack<String> ambitosClase = new Stack<String>();
    private String ambitoClaseInterfaz = "";

    private static GeneracionCodigoIntermedio instance = null;

    public static GeneracionCodigoIntermedio getInstance() {
        if (instance == null) {
            instance = new GeneracionCodigoIntermedio();
            ambitos.push("main");
        }

        return instance;
    }

    public void setAmbitoClaseInterfaz(String identificador) {
        ambitoClaseInterfaz = identificador;
    }

    public void clearAmbitoClaseInterfaz() {
        ambitoClaseInterfaz = "";
    }

    public String getAmbitoClaseInterfaz() {
        return ambitoClaseInterfaz;
    }

    public void agregarAmbitoAListaDeAtributos() {
        Iterator<String> it = lista_variables_a_declarar.iterator();
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

        while (it.hasNext()) {
            String variableActual = it.next();
            TS.swapLexemas(variableActual, variableActual + ":" + ambitoClaseInterfaz);
        }
    }

    public void apilarAmbito(String nombre_ambito) {
        if (esDefinicionDeClase()) {
            ambitosClase.push(nombre_ambito);
        } else {
            ambitos.push(nombre_ambito);
        }
    }

    public void desapilarAmbito() {
        if (esDefinicionDeClase()) {
            ambitosClase.pop();
        } else {
            ambitos.pop();
        }
    }

    public String generarAmbito() {
        Iterator<String> it = null;
        String ambitoCompleto = "";

        if (esDefinicionDeClase()) {
            it = ambitosClase.iterator();
        } else {
            it = ambitos.iterator();
        }

        while (it.hasNext()) {
            ambitoCompleto += ":" + it.next();
        }

        return ambitoCompleto;
    }

    public String generarAmbitoClase() {
        Iterator<String> it = ambitosClase.iterator();
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

    public void agregarUsoAListaDeVariables(String use) {

        Iterator<String> it = lista_variables_a_declarar.iterator();
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

        while (it.hasNext()) {
            String variableActual = it.next();
            TS.putIdentificadorUso(variableActual, use);
        }
    }

    public void agregarTipoAParametroDeFuncion(String parametro, String type) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        TS.putTipo(parametro, type);
    }

    public void agregarAmbitoAListaDeVariables() {
        Iterator<String> it = lista_variables_a_declarar.iterator();

        while (it.hasNext()) {
            String variableActual = it.next();
            this.agregarAmbitoAIdentificador(variableActual);
        }
    }

    public Boolean esDefinicionDeClase() {
        return !ambitoClaseInterfaz.equals("");
    }

    public void agregarAmbitoAIdentificadorMetodo(String identificadorMetodo) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        TS.swapLexemas(identificadorMetodo, identificadorMetodo + ":" + ambitoClaseInterfaz);
    }

    public void agregarAmbitoAIdentificador(String identificador) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        String ambitoCompleto = generarAmbito();

        TS.swapLexemas(identificador, identificador + ambitoCompleto);
    }

    public boolean existeIdentificadorEnAmbito(String identificador) {
        TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
        Iterator<String> itAmbitos = ambitos.iterator();
        String ambitoParcial = "";

        while (itAmbitos.hasNext()) {
            ambitoParcial += itAmbitos.next();
            Boolean identificadorExisteEnAmbito = TS.has(identificador + ":" + ambitoParcial);

            if (identificadorExisteEnAmbito) {
                return true;
            }

            ambitoParcial += ":";
        }

        return false;
    }

    public void removerListaVariablesADeclarar() {
        lista_variables_a_declarar.clear();
    }
}
