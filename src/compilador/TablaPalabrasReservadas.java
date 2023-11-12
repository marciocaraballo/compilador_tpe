package compilador;

import java.util.HashMap;

public class TablaPalabrasReservadas {

	private HashMap<String, Short> palabras_reservadas = new HashMap<String, Short>();

	private static TablaPalabrasReservadas instance = null;

	public static final int NO_ENCONTRADO = -1;

	private TablaPalabrasReservadas() {
		palabras_reservadas.put("IF", Parser.IF);
		palabras_reservadas.put("ELSE", Parser.ELSE);
		palabras_reservadas.put("END_IF", Parser.ENDIF);
		palabras_reservadas.put("PRINT", Parser.PRINT);
		palabras_reservadas.put("CLASS", Parser.CLASS);
		palabras_reservadas.put("VOID", Parser.VOID);
		palabras_reservadas.put("WHILE", Parser.WHILE);
		palabras_reservadas.put("DO", Parser.DO);
		palabras_reservadas.put("INTERFACE", Parser.INTERFACE);
		palabras_reservadas.put("IMPLEMENT", Parser.IMPLEMENT);
		palabras_reservadas.put(Constantes.TYPE_INT, Parser.INT);
		palabras_reservadas.put(Constantes.TYPE_ULONG, Parser.ULONG);
		palabras_reservadas.put(">=", Parser.COMP_MAYOR_IGUAL);
		palabras_reservadas.put("<=", Parser.COMP_MENOR_IGUAL);
		palabras_reservadas.put("==", Parser.COMP_IGUAL);
		palabras_reservadas.put("!!", Parser.COMP_DISTINTO);
		palabras_reservadas.put("--", Parser.OPERADOR_MENOS);
		palabras_reservadas.put(Constantes.TYPE_FLOAT, Parser.FLOAT);
		palabras_reservadas.put("RETURN", Parser.RETURN);
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
