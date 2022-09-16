package compilador;

import java.io.BufferedReader;
import java.lang.Character;
import java.io.IOException;

public class AnalizadorLexico {
	
	private MatrixEstados matrixEstados = new MatrixEstados();
	
	/** Se asume que se inicia en el estado 0 */
	private int estado_actual = 0;
	/** Acumula el token leido hasta el momento */
	private String token_actual = new String(" ");
	/** Mantiene la linea del programa leia hasta el momento */
	private int linea_actual = 0;
	/** Referencia al Reader de archivo de codigo */
	private BufferedReader lector_archivo = null;
	
	/** Determinar que columna de la matriz corresponde al char leido */
	private int obtenerColumnaCaracter(char input) {
		
		/* minusculas l = columna 1 */
		if (Character.isLowerCase(input)) {
			return 1;
		}
		
		/* mayusculas L = columna 0 */
		if (Character.isUpperCase(input)) {
			return 0;
		}
		
		/* digito d = columna 2 */
		if (Character.isDigit(input)) {
			return 2;
		}
		
		/* @TODO faltan todas las posibles columnas por char */
		return 0;
	}
	
	public AnalizadorLexico(BufferedReader lector) {
		lector_archivo = lector;
	};
	
	public int getToken() {
		
		char inputCaracter = 0;
		
		try {
			inputCaracter = (char) lector_archivo.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int columna = obtenerColumnaCaracter(inputCaracter);
		
		System.out.println("Estado: " + estado_actual + " Input: " + inputCaracter + " proximo estado: " + columna);
		
		//@TODO return next token
		return 0;
	}
}
