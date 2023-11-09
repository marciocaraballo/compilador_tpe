package compilador;

import java.util.*;

public class Polaca {

    static private HashMap<String, ArrayList<String>> polaca = new HashMap<>();
    static private HashMap<String, Stack<Integer>> pila = new HashMap<>();
    private int contador = 0;
    private static Polaca instance = null;
    GeneracionCodigoIntermedio genCodigoIntermedio = GeneracionCodigoIntermedio.getInstance();
    int ultimo_desapilado;

    public static Polaca getInstance() {
        if (instance == null) {
            instance = new Polaca();
            ArrayList<String> aux = new ArrayList<>();
            polaca.put(":main", aux);
            Stack<Integer> aux_pila = new Stack<>();
            pila.put(":main", aux_pila);
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        incrementarContador();
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        Logger.getInstance().logWarning(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.add(elemento);
    }

    public void apilar(int posicion) {
        pila.get(genCodigoIntermedio.generarAmbito().toString()).push(posicion - 1);
    }

    public Integer getTope() {
        return pila.get(genCodigoIntermedio.generarAmbito().toString()).peek();
    }

    public Integer desapilar() {
        ultimo_desapilado = pila.get(genCodigoIntermedio.generarAmbito().toString()).pop();
        return ultimo_desapilado;
    }

    //public void guardarPosicion(int i){
    //    posicion_aux.push(i);
    //}

    public int getPosicion(){
        return ultimo_desapilado;
    }

    public void crearPolacaAmbitoNuevo(String identificador){
        ArrayList<String> polaca_auxiliar = new ArrayList<>();
        Stack<Integer> pila_auxiliar = new Stack<>();
        Logger.getInstance().logWarning(identificador);
        polaca.put(identificador, polaca_auxiliar);
        pila.put(identificador, pila_auxiliar);
    }

    public void completarPasoIncompleto() {
        int posicion = desapilar();
        ArrayList<String>  polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(posicion);
        polaca_auxiliar.add(posicion, String.valueOf(polaca_auxiliar.size() + 1));
    }

    public void completarPasoIncompletoIteracion() {
        int posicion = desapilar();
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, String.valueOf(posicion));
    }

    public int polacaSize() {
        return polaca.get(genCodigoIntermedio.generarAmbito().toString()).size();
    }

    public void generarPasoIncompleto(String aux) {
        agregarElemento("VACIO");
        agregarElemento(aux);
    }

    public void completarPasoIncompletoInvocacion(String etiqueta, boolean borrar_parametro){
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        if (borrar_parametro)
            polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, etiqueta);
    }

    public void removeElementos(){
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        int cantidad_elementos = polacaSize() - getContador();
        if (polacaSize() + 1> cantidad_elementos) {
            polaca_auxiliar.subList(cantidad_elementos, polacaSize()).clear();
        }
    }

    public void incrementarContador() {
        contador++;
    }

    public void resetContador() {
        contador = 0;
    }

    public int getContador() {
        return contador;
    }


    public void showPolaca() {
        for (String nombre_polaca : polaca.keySet()) {
            Logger.getInstance().logSuccess("NOMBRE POLACA: " + nombre_polaca);
            for (int i = 0; i < polaca.get(nombre_polaca).size(); i++) {
                System.out.println("[" + i + "] " + polaca.get(nombre_polaca).get(i));
            }
            System.out.println("----------------------------------------------------------");
        }
    }


}
