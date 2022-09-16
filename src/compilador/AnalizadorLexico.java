package compilador;

import java.io.BufferedReader;

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
	
	public AnalizadorLexico(BufferedReader lector) {
		lector_archivo = lector;
	};
	
	public int getToken() {
		
		System.out.println(matrixEstados.getEstadoSiguiente(1, 1));
		
		//@TODO return next token
		return 0;
	}
}
