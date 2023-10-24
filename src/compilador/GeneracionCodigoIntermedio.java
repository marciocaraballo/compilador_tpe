package compilador;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneracionCodigoIntermedio {
    private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();

    private static GeneracionCodigoIntermedio instance = null;

    public static GeneracionCodigoIntermedio getInstance() {
        if (instance == null) {
            instance = new GeneracionCodigoIntermedio();
        }

        return instance;
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

        lista_variables_a_declarar.clear();
    }
}
