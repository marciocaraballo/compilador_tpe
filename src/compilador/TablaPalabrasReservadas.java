package compilador;

import java.util.HashMap;

public class TablaPalabrasReservadas {

	private HashMap<String, Integer> palabras_reservadas = new HashMap<String, Integer>(); 
	
	private static TablaPalabrasReservadas instance = null;
	
	public static final int NO_ENCONTRADO = -1;
	
	private TablaPalabrasReservadas() {
//		palabras_reservadas.put("if", 260);
//		palabras_reservadas.put("then", 261);
//		palabras_reservadas.put("else", 262);
//		palabras_reservadas.put("end_if", 263);
//		palabras_reservadas.put("out", 264);
//		palabras_reservadas.put("fun", 265);
//		palabras_reservadas.put("return", 266);
//		palabras_reservadas.put("break", 267);
//		palabras_reservadas.put("=:", 268);
//		palabras_reservadas.put(">=", 269);
//		palabras_reservadas.put("<=", 270);
//		palabras_reservadas.put("=!", 271);
//		palabras_reservadas.put("when", 272);
//		palabras_reservadas.put("do", 273);
//		palabras_reservadas.put("until", 274);
//		palabras_reservadas.put("continue", 275);
//		palabras_reservadas.put("f64", 276);
//		palabras_reservadas.put("ui16", 277);
//		palabras_reservadas.put("defer", 278);
//		palabras_reservadas.put("const", 279);
		
		palabras_reservadas.put("IF", 260);
		palabras_reservadas.put("ELSE", 261);
		palabras_reservadas.put("END_IF", 262);
		palabras_reservadas.put("PRINT", 263);
		palabras_reservadas.put("CLASS", 264);
		palabras_reservadas.put("VOID", 265);
		palabras_reservadas.put("WHILE", 266);
		palabras_reservadas.put("DO", 267);
		palabras_reservadas.put("INTERFACE", 268);
		palabras_reservadas.put("IMPLEMENT", 269);
		palabras_reservadas.put("INT", 270);
		palabras_reservadas.put("ULONG", 271);
		palabras_reservadas.put(">=", 272);
		palabras_reservadas.put("<=", 273);
		palabras_reservadas.put("==", 274);
		palabras_reservadas.put("!!", 275);
		palabras_reservadas.put("--", 276);
	}
	
	public static TablaPalabrasReservadas getInstance() {
		if (instance == null) {
			instance = new TablaPalabrasReservadas();
		}
		
		return instance;
	}
	
	public int getToken(String palabra) {
		if (palabras_reservadas.containsKey(palabra)) {
			return palabras_reservadas.get(palabra);
		}
		return NO_ENCONTRADO;
	}

}
