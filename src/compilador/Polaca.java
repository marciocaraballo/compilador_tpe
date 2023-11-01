package compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
            ArrayList<String> aux_pila = new ArrayList<>();
            polaca.put("main", aux_pila);
            pila_llamados_polacas.push("main");
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        incrementarContador();
        ArrayList<String> polaca_auxiliar = polaca.get(pila_llamados_polacas.firstElement());

        polaca_auxiliar.add(elemento);
        polaca.put(pila_llamados_polacas.firstElement(), polaca_auxiliar);
    }

    public void apilar(int posicion) {
        pila.get(pila_llamados_polacas.firstElement()).push(posicion - 1);
    }

    public Integer desapilar() {

        return pila.get(pila_llamados_polacas.firstElement()).pop();
    }

    public void crearPolacaAmbitoNuevo(String identificador){
        ArrayList<String> polaca_auxiliar = new ArrayList<>();
        polaca.put(identificador, polaca_auxiliar);
    }

    public void completarPasoIncompleto() {
        int posicion = pila.get(pila_llamados_polacas.firstElement()).pop();
        polaca.remove(posicion);
        polaca.get(pila_llamados_polacas.firstElement()).add(posicion, String.valueOf(polaca.size() + 1));
    }

    public void completarPasoIncompletoIteracion() {
        int posicion = desapilar();
        polaca.remove(polaca.get(pila_llamados_polacas.firstElement()).size() - 2);
        polaca.get(pila_llamados_polacas.firstElement()).add(polaca.get(pila_llamados_polacas.firstElement()).size() - 1, String.valueOf(posicion));
    }

    public int polacaSize() {
        return polaca.get(pila_llamados_polacas.firstElement()).size();
    }

    public void generarPasoIncompleto(String aux) {
        polaca.get(pila_llamados_polacas.firstElement()).add("VACIO");
        polaca.get(pila_llamados_polacas.firstElement()).add(aux);
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
        for (int i = 0; i < polaca.get("main").size(); i++) {
            System.out.println("[" + i + "] " + polaca.get("main").get(i));
        }
    }


}
