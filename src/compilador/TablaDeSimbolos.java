package compilador;

import java.util.HashMap;

public class TablaDeSimbolos {
	
	private HashMap<String, Integer> tabla_simbolos = new HashMap<String, Integer>(); 
	
	private int baseToken = 100;
	
	public TablaDeSimbolos() {};

	public void put(String lexema) {
		tabla_simbolos.put(lexema, baseToken);
		baseToken++;
	}
	
	public boolean has(String lexema) {
		return tabla_simbolos.containsKey(lexema);
	}
	
	public int getToken(String lexema) {
		return tabla_simbolos.get(lexema);
	}
}
