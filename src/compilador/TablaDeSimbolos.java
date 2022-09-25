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
	
	private HashMap<String, HashMap<String, Object>> tabla_simbolos = new HashMap<String, HashMap<String, Object>>();
	
	public static final int IDENTIFICADOR = 257;
    public static final int CONSTANTE = 258;
    public static final int CADENA = 259;
    
    /* Keys de atributos */
	public static final String TOKEN = "token";
    
	public TablaDeSimbolos() {};

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
	
	public boolean has(String lexema) {
		return tabla_simbolos.containsKey(lexema);
	}
	
	public int getToken(String lexema) {
		
		HashMap<String, Object> atributos = tabla_simbolos.get(lexema);
		
		return (int)atributos.get(TOKEN);
	}

	public void print() {
		System.out.println("Tabla de Simbolos");
		Set<String> keys = tabla_simbolos.keySet();
		Iterator<String> keysIterator = keys.iterator();
		
		if (!keysIterator.hasNext()) {
			System.out.println("No hay simbolos detectados");
		}
		
		while(keysIterator.hasNext()) {
			String lexema = keysIterator.next();
			System.out.println("Lexema: " + lexema);
			HashMap<String, Object> attributes = tabla_simbolos.get(lexema);
			
			Set<String> attributesKeys = attributes.keySet();
			Iterator<String> attributesIterator = attributesKeys.iterator();
			
			while(attributesIterator.hasNext()) {
				String attributeKey = attributesIterator.next();
				Object attributeValue = attributes.get(attributeKey);
				System.out.println("Atributo: " + attributeKey + " Valor: " + attributeValue.toString());
			}
		} 
	}
}
