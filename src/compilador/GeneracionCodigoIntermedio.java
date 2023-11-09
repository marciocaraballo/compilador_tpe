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

    public String getTopePila() {
        return ambitos.peek();
    }

    public void setAmbitoClaseInterfaz(String identificador) {
        ambitoClaseInterfaz = identificador;
    }

    public void clearAmbitoClaseInterfaz() {
        ambitoClaseInterfaz = "";
        ambitosClase = new Stack<String>();
    }

    public String getAmbitoClaseInterfaz() {
        return ambitoClaseInterfaz;
    }

    public void agregarAmbitoAListaDeAtributos() {

        String claseActual = ambitoClaseInterfaz;
        String ambitoClaseActual = existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
        String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;

        for (String variableActual : lista_variables_a_declarar) {
            TS.swapLexemas(variableActual, variableActual + ambitoClaseDefinidaActual);
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

        if (esDefinicionDeClase())
            it = ambitosClase.iterator();
        else
            it = ambitos.iterator();

        while (it.hasNext()) {
            ambitoCompleto.append(":").append(it.next());
        }

        return ambitoCompleto;
    }

    /**
     * Es un caso mas que habria que ver si podemos hacerlo mejor, actualmente
     * tenemos
     * 
     * estamos por fuera de definicion de clase
     * estamos definiendo una clase, entonces el de todo lo que se define es la
     * clase
     * estamos definiendo una clase pero encontramos otro identificador de clase,
     * luego
     * debe existir fuera de la clase para ser valido
     */
    public StringBuilder generarAmbitoIdentificadorDeClase() {
        Iterator<String> it = null;
        StringBuilder ambitoCompleto = new StringBuilder();

        it = ambitos.iterator();

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
            /** Se pidio para variables, no para atributos de clas */
            if (use == Constantes.USO_VARIABLE) {
                TS.agregarAtributo(variableActual, Constantes.COMPROBACION_USO, false);
            }
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

    public String existeIdentificadorDeClaseEnAlgunAmbitoContenedor(String identificador) {
        String ambitoParcial = String.valueOf(generarAmbitoIdentificadorDeClase());

        while (!ambitoParcial.isEmpty()) {
            if (TS.has(identificador + ambitoParcial))
                return ambitoParcial;
            ambitoParcial = ambitoParcial.substring(0, ambitoParcial.lastIndexOf(":"));
        }
        return "";
    }

    public String existeIdentificadorEnAlgunAmbitoContenedor(String identificador) {

        String ambitoParcial = String.valueOf(generarAmbito());

        while (!ambitoParcial.isEmpty()) {
            if (TS.has(identificador + ambitoParcial))
                return ambitoParcial;
            ambitoParcial = ambitoParcial.substring(0, ambitoParcial.lastIndexOf(":"));
        }
        return "";
    }

    public Boolean esMayorAMaximoNivelAnidamientoFuncionEnMetodo() {
        /**
         * Como venimos apilando ambientes, en el caso de una clase, 3 es el maximo
         * permitido
         * 
         * Por ejemplo, en ca:m:funcion1:funcion2
         * 
         * ca: estamos en un ambito de una definicion de clase (nivel 1)
         * m: metodo definido dentro de ca (nivel 2)
         * funcion1: funcion definida dentro de m() de ca (nivel 3)
         * funcion2: una funcion dentro de funcion1() dentro de m() de ca(nivel 4)
         * 
         * Como solo se permite 1 nivel de anidamiento dentro de una fn en un metodo
         * de clase, la pila de ambitos solo deberia llegar hasta 3 elementos apilados.
         * A partir de 4 ya deberia informar error el compilador y no generar codigo.
         */
        return (ambitosClase.size() >= 4);
    }

    public void agregarAtributoMetodos(String interfaz) {
        String aux = ambitoClaseInterfaz;
        ambitoClaseInterfaz = "";
        TS.agregarAtributo(aux + generarAmbito(), Constantes.METODOS, interfaz);
        ambitoClaseInterfaz = aux;
    }

    public boolean implementaAlgunaInterfaz(String clase) {
        String ambito = existeIdentificadorDeClaseEnAlgunAmbitoContenedor(clase);
        return TS.getAtributo(clase + ambito, Constantes.IMPLEMENTA) != null;
    }

    /* Chequeo que los metodos de la interfaz fueron implementados en la clase */
    public boolean verificarImplementacionCompletaDeInterfaz(String clase) {

        String aux = ambitoClaseInterfaz;
        ambitoClaseInterfaz = "";
        boolean retorno = true;
        String interfaz = (String) TS.getAtributo(clase + generarAmbito(), Constantes.IMPLEMENTA);

        if (interfaz != null) {
            HashSet<String> metodos_implementados = (HashSet<String>) TS.getAtributo(clase + generarAmbito(),
                    Constantes.METODOS);
            HashSet<String> metodos_a_implementar = (HashSet<String>) TS.getAtributo(interfaz + generarAmbito(),
                    Constantes.METODOS);
            retorno = metodos_implementados.containsAll(metodos_a_implementar);

            ambitoClaseInterfaz = aux;
        }

        return retorno;
    }

    /**
     * Casos validos (aplica tambien para funciones)
     *
     * b1.a
     * b1.cb.a
     * b1.cb.cc.a
     */
    public boolean esCadenaDeLlamadasValida(String cadenaLlamados) {
        String[] partes = cadenaLlamados.split("\\.");
        /** Caso invalido b1.cb.cc.cd.a, hay 4 niveles de anidamiento */
        if (partes.length >= 5) {
            Logger.getInstance().logError(
                    "[Generacion codigo] Se supera el maximo permitido de niveles de anidamiento de herencia en "
                            + cadenaLlamados);
            return false;
        } else {
            String ambito = existeIdentificadorEnAlgunAmbitoContenedor(partes[0]);
            /** Instancia clase esta definida */
            if (!ambito.isEmpty()) {
                String tipo = (String) TS.getAtributo(partes[0] + ambito, Constantes.TYPE);
                boolean isValid = true;

                for (int i = 1; i < partes.length && isValid; i++) {
                    /** El primer nivel debe validar contra el tipo de la variable */
                    if (i == 1) {
                        String ambitoClaseTipo = existeIdentificadorEnAlgunAmbitoContenedor(tipo);
                        if (!TS.has(partes[i] + ambitoClaseTipo + ":" + tipo)) {
                            isValid = false;
                            Logger.getInstance().logError("[Codigo intermedio] El identificador " + partes[i]
                                    + " no esta declarado como miembro de la clase " + tipo);
                        }
                    } else {
                        String ambitoClaseTipo = existeIdentificadorEnAlgunAmbitoContenedor(partes[i - 1]);
                        if (!TS.has(partes[i] + ambitoClaseTipo + ":" + partes[i - 1])) {
                            isValid = false;
                            Logger.getInstance().logError("[Codigo intermedio] El identificador " + partes[i]
                                    + " no esta declarado como miembro de la clase " + partes[i - 1]);
                        } else {
                            /** Encuentra el */
                        }
                    }
                }

                return isValid;
            } else {
                Logger.getInstance()
                        .logError("[Codigo intermedio] El identificador " + partes[0] + " no esta declarado");
                return false;
            }
        }
    }

    public boolean tieneParametroElMetodoLlamado(String cadenaLlamados) {
        String[] partes = cadenaLlamados.split("\\.");

        /**
         * Caso b1.p(a), donde b1 es un nombre de variable y NO un type heredado, y se
         * llama con un param
         */
        if (partes.length == 2) {
            String ambito = existeIdentificadorEnAlgunAmbitoContenedor(partes[0]);
            String tipo = (String) TS.getAtributo(partes[0] + ambito, Constantes.TYPE);
            String ambitoClaseTipo = existeIdentificadorEnAlgunAmbitoContenedor(tipo);
            boolean tieneParametro = (boolean) TS.getAtributo(partes[1] + ambitoClaseTipo + ":" + tipo,
                    Constantes.TIENE_PARAMETRO);

            return tieneParametro;
        } else {
            /**
             * Caso b1.ca.d(), donde se chequea la ultima parte (metodo) con la anterior
             * (type)
             */
            String ambito = existeIdentificadorEnAlgunAmbitoContenedor(partes[partes.length - 2]);
            String tipo = partes[partes.length - 2];
            boolean tieneParametro = (boolean) TS.getAtributo(partes[partes.length - 1] + ambito + ":" + tipo,
                    Constantes.TIENE_PARAMETRO);

            return tieneParametro;
        }
    }
}
