package compilador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * La tabla de simbolos se indexara por el lexema
 * Cada uno podra tener asociada otro hashmap para
 * atributos, indexados por alguna key identificadora
 */
public class TablaDeSimbolos {

	private static TablaDeSimbolos instance = null;

	private HashMap<String, HashMap<String, Object>> tabla_simbolos = new HashMap<String, HashMap<String, Object>>();

	public static final int IDENTIFICADOR = 257;
	public static final int CONSTANTE = 258;
	public static final int CADENA = 259;

	/* Keys de atributos */
	public static final String TOKEN = "token";
	public static final String TYPE = "tipo";
	public static final String USE = "uso";
	public static final String COMPROBACION_USO = "comp_uso";

	TablaDeSimbolos() {
	};

	public static TablaDeSimbolos getInstance() {
		if (instance == null) {
			instance = new TablaDeSimbolos();
		}

		return instance;
	}

	/* Agrega un lexema que se reconoce como identificador */
	public void putIdentificador(String lexema) {
		HashMap<String, Object> atributos = new HashMap<String, Object>();
		atributos.put(TOKEN, IDENTIFICADOR);
		atributos.put(COMPROBACION_USO, false);
		tabla_simbolos.put(lexema, atributos);

	}

	/* Agrega un lexema que se reconoce como constante */
	public void putConstante(String lexema) {

		HashMap<String, Object> atributos = new HashMap<String, Object>();
		atributos.put(TOKEN, CONSTANTE);

		tabla_simbolos.put(lexema, atributos);
	}

	/* Agrega un lexema que se reconoce como cadena de caracteres */
	public void putCadena(String lexema) {

		HashMap<String, Object> atributos = new HashMap<String, Object>();
		atributos.put(TOKEN, CADENA);

		tabla_simbolos.put(lexema, atributos);
	}

	public void putTipo(String lexema, String tipo) {
		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(TYPE, tipo);
	}

	public void putIdentificadorUso(String lexema, String uso) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(USE, true);
	}

	public void putComprobacionUso(String lexema) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(COMPROBACION_USO, true);
	}

	public boolean has(String lexema) {
		return tabla_simbolos.containsKey(lexema);
	}

	public int getToken(String lexema) {

		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);

		return (int) atributos.get(TOKEN);
	}

	public String getUso(String lexema) {
		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);
		return (String) atributos.get(USE);
	}

	public String getTipo(String lexema) {
		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);
		return (String) atributos.get(TYPE);
	}

	public void swapLexemas(String lexemaOriginal, String lexemaModificado) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexemaOriginal);

		tabla_simbolos.remove(lexemaOriginal);

		tabla_simbolos.put(lexemaModificado, attributes);
	}

	public String print() {
		System.out.println("Tabla de Simbolos");
		Set<String> keys = tabla_simbolos.keySet();
		Iterator<String> keysIterator = keys.iterator();

		StringBuilder tsPrint = new StringBuilder();

		if (!keysIterator.hasNext()) {
			tsPrint.append("No hay simbolos detectados");
		}

		while (keysIterator.hasNext()) {
			String lexema = keysIterator.next();
			tsPrint.append("Lexema: " + lexema + "\n");
			HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

			Set<String> attributesKeys = attributes.keySet();
			Iterator<String> attributesIterator = attributesKeys.iterator();

			while (attributesIterator.hasNext()) {

				String attributeKey = attributesIterator.next();
				Object attributeValue = attributes.get(attributeKey);
				if (attributeKey.equals(COMPROBACION_USO) && (Boolean) attributeValue == false)
					Logger.getInstance().logWarning("La variable " +  lexema + " no se uso del lado izquierdo en asignacion");
				tsPrint.append("Atributo: " + attributeKey + " Valor: " + attributeValue.toString() + "\n");
			}
		}

		System.out.println(tsPrint.toString());

		return tsPrint.toString();
	}
}
