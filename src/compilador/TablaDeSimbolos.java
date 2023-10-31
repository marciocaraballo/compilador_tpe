package compilador;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
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
	public static final String TIENE_PARAMETRO = "tiene_param";
	public static final String IMPLEMENTA = "implementa";
	public static final String METODOS_A_IMPLEMENTAR = "metodos_iter";
	public static final String METODOS_IMPLEMENTADOS = "metodos_imp";

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

		attributes.put(USE, uso);
		if (uso.equals("nombre_funcion") || uso.equals("nombre_metodo")) {
			attributes.put(TIENE_PARAMETRO, false);
		} else {
			if (uso.equals("variable") || uso.equals("nombre_parametro") || uso.equals("atributo")) {
				attributes.put(COMPROBACION_USO, false);
			}
		}
		if (uso.equals("nombre_interfaz")){
			attributes.put(METODOS_A_IMPLEMENTAR, new HashSet<String>());
		}
		if (uso.equals("nombre_clase")){
			attributes.put(METODOS_IMPLEMENTADOS, new HashSet<String>());
		}
	}

	public void putComprobacionUso(String lexema) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(COMPROBACION_USO, true);
	}

	public void putMetodosAImplementar(String lexema, String metodo) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		HashSet<String> aux = (HashSet<String>) attributes.get(METODOS_A_IMPLEMENTAR);
		aux.add(metodo);
		attributes.put(METODOS_A_IMPLEMENTAR, aux);
	}

	public void putMetodosImplementados(String lexema, String metodo) {

		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		HashSet<String> aux = (HashSet<String>) attributes.get(METODOS_IMPLEMENTADOS);
		aux.add(metodo);
		attributes.put(METODOS_IMPLEMENTADOS, aux);
	}

	public void putImplementa(String lexema, String interfaz) {
		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(IMPLEMENTA, interfaz);
	}

	public String getImplementa(String lexema) {
		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);

		return (String) atributos.get(IMPLEMENTA);
	}

	public boolean has(String lexema) {
		return tabla_simbolos.containsKey(lexema);
	}

	public int getToken(String lexema) {

		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);

		return (int) atributos.get(TOKEN);
	}

	public boolean getTieneParametro(String lexema) {

		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);

		return (boolean) atributos.get(TIENE_PARAMETRO);
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

	public void removeLexema(String lexema) {
		tabla_simbolos.remove(lexema);
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
					Logger.getInstance()
							.logWarning("La variable " + lexema + " no se uso del lado izquierdo en asignacion");
				tsPrint.append("Atributo: " + attributeKey + " Valor: " + attributeValue.toString() + "\n");
			}
		}

		System.out.println(tsPrint.toString());

		return tsPrint.toString();
	}

	public void tieneParametros(String lexema) {
		HashMap<String, Object> attributes = tabla_simbolos.get(lexema);

		attributes.put(TIENE_PARAMETRO, true);
	}

	public String getInterfaz(String lexema) {

		return (String) tabla_simbolos.get(lexema).get(IMPLEMENTA);

	}

	public HashSet<String> getMetodosImplementados(String lexema) {
		return (HashSet<String>) tabla_simbolos.get(lexema).get(METODOS_IMPLEMENTADOS);
	}

	public HashSet<String> getMetodosAImplementar(String lexema) {
		return (HashSet<String>) tabla_simbolos.get(lexema).get(METODOS_A_IMPLEMENTAR);
	}
}
