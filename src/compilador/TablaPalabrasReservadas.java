package compilador;

import java.util.HashMap;

public class TablaPalabrasReservadas {

	private HashMap<String, Integer> palabras_reservadas = new HashMap<String, Integer>(); 
	
	public static final int NO_ENCONTRADO = -1;
	
	public TablaPalabrasReservadas() {
		palabras_reservadas.put("if", 0);
		palabras_reservadas.put("then", 1);
		palabras_reservadas.put("else", 2);
		palabras_reservadas.put("end_if", 3);
		palabras_reservadas.put("out", 4);
		palabras_reservadas.put("fun", 5);
		palabras_reservadas.put("return", 6);
		palabras_reservadas.put("break", 7);
		palabras_reservadas.put("=:", 8);
		palabras_reservadas.put(">=", 9);
		palabras_reservadas.put("<=", 10);
		palabras_reservadas.put("=!", 11);
		palabras_reservadas.put("do", 12);
		palabras_reservadas.put("until", 13);
		palabras_reservadas.put("continue", 14);
		palabras_reservadas.put("f64", 15);
		palabras_reservadas.put("ui16", 16);
	}
	
	public int getToken(String palabra) {
		if (palabras_reservadas.containsKey(palabra)) {
			return palabras_reservadas.get(palabra);
		}
		return NO_ENCONTRADO;
	}

}
