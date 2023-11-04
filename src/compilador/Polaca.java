package compilador;

import java.util.*;

public class Polaca {

    static private HashMap<String, ArrayList<String>> polaca = new HashMap<>();
    static private HashMap<String, Stack<Integer>> pila = new HashMap<>();
    private int contador = 0;
    private static Polaca instance = null;
    private static Stack<String> pila_llamados_polacas = new Stack<>();

    public static Polaca getInstance() {
        if (instance == null) {
            instance = new Polaca();
            ArrayList<String> aux = new ArrayList<>();
            polaca.put("main", aux);
            Stack<Integer> aux_pila = new Stack<>();
            pila.put("main", aux_pila);
            pila_llamados_polacas.push("main");
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        incrementarContador();
        ArrayList<String> polaca_auxiliar = polaca.get(pila_llamados_polacas.peek());

        polaca_auxiliar.add(elemento);
        polaca.put(pila_llamados_polacas.peek(), polaca_auxiliar);
    }

    public void apilarAmbito(String identificador){
        pila_llamados_polacas.push(identificador);
    }
    public void desapilarAmbito(){
        pila_llamados_polacas.pop();
    }
    public void apilar(int posicion) {
        pila.get(pila_llamados_polacas.peek()).push(posicion - 1);
    }

    public Integer desapilar() {

        return pila.get(pila_llamados_polacas.peek()).pop();
    }

    public void crearPolacaAmbitoNuevo(String identificador){
        ArrayList<String> polaca_auxiliar = new ArrayList<>();
        this.apilarAmbito(identificador);
        polaca.put(identificador, polaca_auxiliar);
    }

    public void completarPasoIncompleto() {
        int posicion = desapilar();
        ArrayList<String>  polaca_auxiliar = polaca.get(pila_llamados_polacas.peek());
        polaca_auxiliar.remove(posicion);
        polaca_auxiliar.add(posicion, String.valueOf(polaca_auxiliar.size() + 1));
    }

    public void completarPasoIncompletoIteracion() {
        int posicion = desapilar();
        ArrayList<String> polaca_auxiliar = polaca.get(pila_llamados_polacas.peek());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, String.valueOf(posicion));
        Logger.getInstance().logWarning("asdasd " + pila.get(pila_llamados_polacas.peek()).size());
    }

    public int polacaSize() {
        return polaca.get(pila_llamados_polacas.peek()).size();
    }

    public void generarPasoIncompleto(String aux) {
        polaca.get(pila_llamados_polacas.peek()).add("VACIO");
        polaca.get(pila_llamados_polacas.peek()).add(aux);
    }

    public void completarPasoIncompletoInvocacion(String etiqueta){
        ArrayList<String> polaca_auxiliar = polaca.get(pila_llamados_polacas.peek());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, etiqueta);
        polaca.put(pila_llamados_polacas.peek(), polaca_auxiliar);
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
