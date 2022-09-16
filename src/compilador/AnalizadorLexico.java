package compilador;

import java.io.BufferedReader;
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
		return 0;
	}
	
	public AnalizadorLexico(BufferedReader lector) {
		lector_archivo = lector;
	};
	
	public int getToken() {
		
		char input = 0;
		
		try {
			input = (char) lector_archivo.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(matrixEstados.getEstadoSiguiente(1, 1));
		
		//@TODO return next token
		return 0;
	}
}
