package compilador;

import java.lang.Character;

import accion_semantica.AccionSemantica;

public class AnalizadorLexico {

	private FileReaderHelper fileHelper = null;
	private MatrixEstados matrixEstados = new MatrixEstados();
	private MatrixAccionesSemanticas matrixAS = null;
	private TablaPalabrasReservadas tpr = new TablaPalabrasReservadas(); 
	private Logger logger = null;
	
	private int estado_actual = 0;
	
	private StringBuilder lexema = new StringBuilder("");
	private int tokenLexema = -1;
	private int inputCaracter = 0;
	
	private int obtenerColumnaCaracter(int inputCaracter) {
		
		/* EOF -> $ columna 25 */
		if (inputCaracter == -1) {
			return 25;
		}
		
		char input = (char)inputCaracter;
		
		/* mayusculas L = columna 0 */
		if (Character.isUpperCase(input) && input != 'D') {
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
		
		/* carriage return */
		if (input == '\r') {
			return 26;
		}
		
		/* Char desconocido -> deberia ir a error? */
		return -1;
	}
	
	public AnalizadorLexico(FileReaderHelper fileHelper, TablaDeSimbolos ts, Logger lgr) {
		this.fileHelper = fileHelper;
		logger = lgr;
		matrixAS = new MatrixAccionesSemanticas(ts, tpr, logger);
	};
	
	public boolean hasNext() {
		return inputCaracter != -1;
	}
	
	public int getToken() {

		while (estado_actual != MatrixEstados.F) {
			
			inputCaracter = fileHelper.nextChar();
			
			char inputAsChar = (char)inputCaracter;
			
			int columnaCaracter = obtenerColumnaCaracter(inputCaracter);
			
			if (columnaCaracter != -1) {
				
				if (inputAsChar == '\n') {
					logger.incrementarLinea();
				}
				
				int proximoEstado = matrixEstados.getEstadoSiguiente(estado_actual, columnaCaracter);
				
				System.out.println("Estado: " + estado_actual + " Input: " + inputAsChar + " proximo estado: " + proximoEstado);
				
				AccionSemantica as = matrixAS.getAccionSemantica(estado_actual, columnaCaracter);
				
				tokenLexema = as.ejecutar(fileHelper, lexema, inputAsChar);
				
				if (proximoEstado != MatrixEstados.E) {
					estado_actual = proximoEstado;
				} else {
					estado_actual = 0;
				}
			}
			
			else
				return -1;
		}
		
		System.out.println("Se reconoce un token para " + lexema.toString() + " con el token " + tokenLexema);
		estado_actual = 0;
		
		return tokenLexema;
	}
}