package compilador;

import java.io.BufferedReader;
import java.lang.Character;

import accion_semantica.AccionSemantica;

import java.io.IOException;

public class AnalizadorLexico {
	
	private MatrixEstados matrixEstados = new MatrixEstados();
	private MatrixAccionesSemanticas matrixAS = null;
	private TablaPalabrasReservadas tpr = new TablaPalabrasReservadas(); 
	
	/** Se asume que se inicia en el estado 0 */
	private int estado_actual = 0;
	
	/** Acumula el lexema leido hasta el momento */
	private StringBuilder lexema = new StringBuilder("");
	
	/** Mantiene la linea del programa leia hasta el momento */
	private int linea_actual = 0;
	
	/** Referencia al Reader de archivo de codigo */
	private BufferedReader lector_archivo = null;
	
	private int tokenLexema = -1;
	private int inputCaracter = 0;
	private int linea = 0;
	
	/** Determinar que columna de la matriz corresponde al char leido */
	private int obtenerColumnaCaracter(char input) {
		
		/* mayusculas L = columna 0 */
		if (Character.isUpperCase(input)) {
			return 0;
		}
		
		/* minusculas l = columna 1 */
		if (Character.isLowerCase(input)) {
			return 1;
		}
		
		/* digito d = columna 2 */
		if (Character.isDigit(input)) {
			return 2;
		}
		
		/* caracter D = columna 3 */
		if (input == 'D') {
			return 3;
		}
		
		/* caracter _ = columna 4 */
		if (input == '_') {
			return 4;
		}
		
		/* caracter + = columna 5 */
		if (input == '+') {
			return 5;
		}
		
		/* caracter - = columna 6 */
		if (input == '-') {
			return 6;
		}
		
		/* caracter { = columna 7 */
		if (input == '{') {
			return 7;
		}
		
		/* caracter } = columna 8 */
		if (input == '}') {
			return 8;
		}
		
		/* caracter ( = columna 9 */
		if (input == '(') {
			return 9;
		}
		
		/* caracter ) = columna 10 */
		if (input == ')') {
			return 10;
		}
		
		/* caracter ; = columna 11 */
		if (input == ';') {
			return 11;
		}
		
		/* caracter / = columna 12 */
		if (input == '/') {
			return 12;
		}
		
		/* caracter * = columna 13 */
		if (input == '*') {
			return 13;
		}
		
		/* caracter , = columna 14 */
		if (input == ',') {
			return 14;
		}
		
		/* caracter = = columna 15 */
		if (input == '=') {
			return 15;
		}
		
		/* caracter > = columna 16 */
		if (input == '>') {
			return 16;
		}
		
		/* caracter < = columna 17 */
		if (input == '<') {
			return 17;
		}
		
		/* caracter : = columna 18 */
		if (input == ':') {
			return 18;
		}
		
		/* caracter ! = columna 19 */
		if (input == '!') {
			return 19;
		}
		
		/* caracter . = columna 20 */
		if (input == '.') {
			return 20;
		}
		
		/* caracter ' = columna 21, hay que escapar */
		if (input == '\'') {
			return 21;
		}
	
		/* caracter tab = columna 22 */
		if (input == '\t') {
			return 22;
		}
		
		/* blank space = columna 23 */
		if (input == ' ') {
			return 23;
		}
		
		/* blank space = columna 24 */
		if (input == '\n') {
			return 24;
		}
		
		/* Char desconocido -> deberia ir a error? */
		return -1;
	}
	
	public AnalizadorLexico(BufferedReader lector, TablaDeSimbolos ts) {
		lector_archivo = lector;
		matrixAS = new MatrixAccionesSemanticas(ts, tpr);
	};
	
	public boolean hasNext() {
		return inputCaracter != -1;
	}
	
	public int getToken() {
		
		/* -1 indica que se llego a un token valido */
		/**
		 * Revisar esta sarasa pero ANDA
		 */
		while (estado_actual != -1) {	
			if (tokenLexema != TablaDeSimbolos.IDENTIFICADOR &&
					tokenLexema != TablaDeSimbolos.CADENA &&
					tokenLexema != TablaDeSimbolos.CONSTANTE &&
					!tpr.isPalabraReservada(tokenLexema) && tokenLexema != 61) {
				
				try {
					inputCaracter = lector_archivo.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ((char)inputCaracter == '\n') {
				linea++;
			}
			
			if (inputCaracter != -1) {
				/* Obtengo la columna del char leido */
				int columnaCaracter = obtenerColumnaCaracter((char)inputCaracter);
				/* Determino el proximo estado */
				int proximoEstado = matrixEstados.getEstadoSiguiente(estado_actual, columnaCaracter);
				
				System.out.println("Estado: " + estado_actual + " Input: " + (char)inputCaracter + " proximo estado: " + proximoEstado);
				
				AccionSemantica as = matrixAS.getAccionSemantica(estado_actual, columnaCaracter);
				
				tokenLexema = as.ejecutar((char)inputCaracter, lexema);
				
				/* Guardo el proximo estado */
				estado_actual = proximoEstado;
			}
		}
		
		/* -1 es un estado final en la matrix */
		if (estado_actual == -1) {
			System.out.println("Se reconoce un token para " + lexema.toString() + " con el token " + tokenLexema);
			estado_actual = 0;
		}
		
		return tokenLexema;
//		
//		System.out.println("Se alcanzo el end of file");
//		
//		//@TODO return next token
//		return 0;
	}
}
