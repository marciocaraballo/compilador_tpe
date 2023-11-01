package compilador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

public class GeneracionCodigoIntermedio {

    TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
    private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();
    private static Stack<String> ambitos = new Stack<String>();
    private static Stack<String> ambitosClase = new Stack<String>();
    private String ambitoClaseInterfaz = "";

    private ArrayList<String> polaca = new ArrayList<>();
    private Stack<Integer> pila = new Stack<>();
    private boolean puedoDesapilar = true;
    int contador = 0;
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

        for (String variableActual : lista_variables_a_declarar) {
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

    public boolean isPuedoDesapilar() {
        return puedoDesapilar;
    }

    public void setPuedoDesapilar() {
        this.puedoDesapilar = !puedoDesapilar;
    }

    public Boolean esDefinicionDeClase() {
        return !ambitoClaseInterfaz.isEmpty();
    }

    public StringBuilder generarAmbito() {
        Iterator<String> it = null;
        StringBuilder ambitoCompleto = new StringBuilder();

        if (esDefinicionDeClase()) it = ambitosClase.iterator(); else it = ambitos.iterator();

        while (it.hasNext()) {
            ambitoCompleto.append(":").append(it.next());
        }

        return ambitoCompleto;
    }

    public void agregarTipoAListaDeVariables(String type) {

        for (String variableActual : lista_variables_a_declarar) {
            TS.agregarAtributo(variableActual, Constantes.TYPE, type);
        }
    }

    public Boolean variableRedeclarada(String variable) {
        return lista_variables_a_declarar.contains(variable) || TS.has(variable + generarAmbito());
    }

    public void agregarVariableADeclarar(String variable) {
        lista_variables_a_declarar.add(variable);
    }

    public void agregarUsoAListaDeVariables(String use) {
        for (String variableActual : lista_variables_a_declarar) {
            TS.agregarAtributo(variableActual, Constantes.USE, use);
            TS.agregarAtributo(variableActual, Constantes.COMPROBACION_USO, false);
        }
    }

    public void agregarAmbitoAListaDeVariables() {

        for (String variableActual : lista_variables_a_declarar) {
            TS.swapLexemas(variableActual, variableActual + generarAmbito());
        }
    }

    public void removerListaVariablesADeclarar() {
        lista_variables_a_declarar.clear();
    }

    public String existeIdentificadorEnAlgunAmbitoContenedor(String identificador) {
        Iterator<String> it = null;
        String ambitoParcial = "";

        if (esDefinicionDeClase()) ambitoParcial = ":" + getAmbitoClaseInterfaz(); else
            ambitoParcial = String.valueOf(generarAmbito());

        while (!ambitoParcial.isEmpty()) {
            if (TS.has(identificador + ambitoParcial))
                return ambitoParcial;
            ambitoParcial = ambitoParcial.substring(0, ambitoParcial.lastIndexOf(":"));
        }
        return "";
    }

    public void agregarAtributoMetodos(String interfaz) {
        String aux = ambitoClaseInterfaz;
        ambitoClaseInterfaz = "";
        TS.agregarAtributo(aux + generarAmbito(), Constantes.METODOS, interfaz);
        ambitoClaseInterfaz = aux;
    }

    /* Chequeo que los metodos de la interfaz fueron implementados en la clase */
    public boolean verificarImplementacion(String clase) {

        String aux = ambitoClaseInterfaz;
        ambitoClaseInterfaz = "";
        boolean retorno = true;
        String interfaz = (String) TS.getAtributo(clase + generarAmbito(), Constantes.IMPLEMENTA);
        if (interfaz != null) {
            HashSet<String> metodos_implementados = (HashSet<String>) TS.getAtributo(clase + generarAmbito(), Constantes.METODOS);
            HashSet<String> metodos_a_implementar = (HashSet<String>) TS.getAtributo(interfaz + generarAmbito(), Constantes.METODOS);
            retorno = metodos_implementados.containsAll(metodos_a_implementar);
        }
        ambitoClaseInterfaz = aux;
        return retorno;
    }
}
