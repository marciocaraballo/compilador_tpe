package compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Polaca {

    private ArrayList<String> polaca = new ArrayList<>();
    private Stack<Integer> pila = new Stack<>();
    private int contador = 0;
    private static Polaca instance = null;

    public static Polaca getInstance() {
        if (instance == null) {
            instance = new Polaca();
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        incrementarContador();
        polaca.add(elemento);
    }

    public void apilar(int posicion) {
        pila.push(posicion - 1);
    }

    public Integer desapilar() {
        return pila.pop();
    }

    public void completarPasoIncompleto() {
        int posicion = desapilar();
        polaca.remove(posicion);
        polaca.add(posicion, String.valueOf(polaca.size() + 1));
    }

    public void completarPasoIncompletoIteracion() {
        int posicion = desapilar();
        polaca.remove(polaca.size() - 2);
        polaca.add(polaca.size() - 1, String.valueOf(posicion));
    }

    public int polacaSize() {
        return polaca.size();
    }

    public void generarPasoIncompleto(String aux) {
        polaca.add("VACIO");
        polaca.add(aux);
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
        for (int i = 0; i < polaca.size(); i++) {
            System.out.println("[" + i + "] " + polaca.get(i));
        }
    }


}
