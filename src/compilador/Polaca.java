package compilador;

import java.util.*;

public class Polaca {

    static private HashMap<String, ArrayList<String>> polaca = new HashMap<>();
    static private HashMap<String, ArrayList<Boolean>> marcas_label = new HashMap<>();
    boolean es_label = false;
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
            ArrayList<Boolean> direcciones = new ArrayList<>();
            marcas_label.put(":main", direcciones);
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        incrementarContador();
        ArrayList<Boolean> posicion_aux = marcas_label.get(genCodigoIntermedio.generarAmbito().toString());
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.add(elemento);
        posicion_aux.add(es_label);
        if (es_label)
            es_label = false;
    }

    public void apilar(int posicion) {
        Logger.getInstance().logWarning("QUe esta apilando esto? " + posicion);
        pila.get(genCodigoIntermedio.generarAmbito().toString()).push(posicion - 1);
    }

    public Integer desapilar() {
        ultimo_desapilado = pila.get(genCodigoIntermedio.generarAmbito().toString()).pop();
        return ultimo_desapilado;
    }

    public int getPosicion() {
        return ultimo_desapilado;
    }

    public void crearPolacaAmbitoNuevo(String identificador) {
        ArrayList<String> polaca_auxiliar = new ArrayList<>();
        Stack<Integer> pila_auxiliar = new Stack<>();
        ArrayList<Boolean> marcas_aux = new ArrayList<>();
        polaca.put(identificador, polaca_auxiliar);
        pila.put(identificador, pila_auxiliar);
        marcas_label.put(identificador, marcas_aux);
    }

    public void completarPasoIncompleto() {
        int posicion = desapilar();

        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(posicion);
        polaca_auxiliar.add(posicion, String.valueOf(polaca_auxiliar.size() + 1));

        es_label = true;
    }

    public void completarPasoIncompletoIteracion() {
        int posicion = desapilar();
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, String.valueOf(posicion));
        ArrayList<Boolean> posicion_aux = marcas_label.get(genCodigoIntermedio.generarAmbito().toString());

        posicion_aux.remove(posicion);
        posicion_aux.add(posicion, true);
    }

    public void completarPasoIncompletoInvocacion(String etiqueta) {
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        polaca_auxiliar.remove(polaca_auxiliar.size() - 2);
        polaca_auxiliar.add(polaca_auxiliar.size() - 1, etiqueta);
    }

    public int polacaSize() {
        return polaca.get(genCodigoIntermedio.generarAmbito().toString()).size();
    }

    public void generarPasoIncompleto(String aux) {
        agregarElemento("VACIO");
        agregarElemento(aux);
    }

    public void removeElementos() {
        ArrayList<String> polaca_auxiliar = polaca.get(genCodigoIntermedio.generarAmbito().toString());
        int cantidad_elementos = polacaSize() - getContador();
        if (polacaSize() + 1 > cantidad_elementos) {
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

    public String showPolaca() {
        String polacaCompleta = "";
        for (String nombre_polaca : polaca.keySet()) {
            polacaCompleta += "NOMBRE POLACA: " + nombre_polaca + "\n";
            Logger.getInstance().logSuccess("NOMBRE POLACA: " + nombre_polaca);
            for (int i = 0; i < polaca.get(nombre_polaca).size(); i++) {
                String polacaElement = "[" + i + "] " + polaca.get(nombre_polaca).get(i) + " "
                        + marcas_label.get(nombre_polaca).get(i);
                polacaCompleta += polacaElement + "\n";
                System.out.println(polacaElement);
            }
            polacaCompleta += "----------------------------------------------------------" + "\n";
            System.out.println("----------------------------------------------------------");
        }
        return polacaCompleta;
    }

    public Set<String> getNombresPolaca() {
        return polaca.keySet();
    }

    public ArrayList<String> getPolaca(String nombre) {
        return polaca.get(nombre);
    }

    public boolean esLabel(int i, String nombre_polaca) {
        return marcas_label.get(nombre_polaca).get(i);
    }
}
