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
	/** Keys de atributos */
	public static final String USE = "uso";
	public static final String COMPROBACION_USO = "comp_uso";
	public static final String TIENE_PARAMETRO = "tiene_param";
	public static final String METODOS_A_IMPLEMENTAR = "metodos_iter";
	public static final String CLASES_A_EXTENDER = "clases_extend";

	TablaDeSimbolos() {
	};

	public static TablaDeSimbolos getInstance() {
		if (instance == null) {
			instance = new TablaDeSimbolos();

			instance.putLexema("1_i", Constantes.CONSTANTE);
			instance.agregarAtributo("1_i", Constantes.TYPE, Constantes.TYPE_INT);

			instance.putLexema("1_ul", Constantes.CONSTANTE);
			instance.agregarAtributo("1_ul", Constantes.TYPE, Constantes.TYPE_ULONG);

			instance.putLexema("1.0", Constantes.CONSTANTE);
			instance.agregarAtributo("1.0", Constantes.TYPE, Constantes.TYPE_FLOAT);
		}

		return instance;
	}

	/* Agrega un lexema que se reconoce como identificador, constante o cadena */
	public void putLexema(String lexema, int tipo) {
		HashMap<String, Object> atributos = new HashMap<String, Object>();
		atributos.put(Constantes.TOKEN, tipo);
		tabla_simbolos.put(lexema, atributos);
	}

	public void iniciarAtributoLista(String lexema, String nombre_atributo) {
		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);
		atributos.put(nombre_atributo, new HashSet<String>());
	}

	public void agregarAtributo(String lexema, String nombre_atributo, Object valor) {
		/**
		 * En algunos casos puede que se llegue a este punto y el lexema no exista
		 * Por ejemplo, si intentamos definir una clase, pero hay algun error en su
		 * definicion,
		 * puede que luego se intente agregar algun atributo pero no existe en la TS
		 */
		if (has(lexema)) {
			HashMap<String, Object> atributos = tabla_simbolos.get(lexema);
			if (nombre_atributo.equals(Constantes.METODOS) || nombre_atributo.equals(Constantes.ATRIBUTOS)) {

				HashSet<String> aux;

				if (nombre_atributo.equals(Constantes.METODOS)) {
					aux = (HashSet<String>) atributos.get(Constantes.METODOS);
				} else {
					aux = (HashSet<String>) atributos.get(Constantes.ATRIBUTOS);
				}

				aux.add((String) valor);

				if (nombre_atributo.equals(Constantes.METODOS)) {
					atributos.put(Constantes.METODOS, aux);
				} else {
					atributos.put(Constantes.ATRIBUTOS, aux);
				}
			} else {
				atributos.put(nombre_atributo, valor);
			}
		}
	}

	public Object getAtributo(String lexema, String atributo) {
		/**
		 * En algunos casos puede que se llegue a este punto y el lexema no exista
		 * Por ejemplo, si intentamos definir una clase, pero hay algun error en su
		 * definicion,
		 * puede que luego se intente agregar algun atributo pero no existe en la TS
		 */
		if (has(lexema)) {
			return tabla_simbolos.get(lexema).get(atributo);
		}

		return null;
	}

	public boolean has(String lexema) {
		return tabla_simbolos.containsKey(lexema);
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
				if (attributeValue != null) {
					if (attributeKey.equals(COMPROBACION_USO) && (Boolean) attributeValue == false)
						Logger.getInstance()
								.logWarning("La variable " + lexema + " no se uso del lado izquierdo en asignacion");

					tsPrint.append("Atributo: " + attributeKey + " Valor: " + attributeValue.toString() + "\n");
				}
			}
		}

		System.out.println(tsPrint.toString());

		return tsPrint.toString();
	}
}
