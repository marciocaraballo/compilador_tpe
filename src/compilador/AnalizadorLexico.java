package compilador;

import java.lang.Character;

import accion_semantica.AccionSemantica;

public class AnalizadorLexico {
	
	private static FileReaderHelper fileHelper = null;
	private static MatrixEstados matrixEstados = MatrixEstados.getInstance();
	private static MatrixAccionesSemanticas matrixAS = MatrixAccionesSemanticas.getInstance();
	private static Logger logger = Logger.getInstance();
	
	private static int estado_actual = 0;
	
	private static StringBuilder lexema = new StringBuilder("");
	private static int tokenLexema = -1;
	private static int inputCaracter = 0;
	
	private static int obtenerColumnaCaracter(int inputCaracter) {
		
		/* EOF -> $ columna 27 */
		if (inputCaracter == -1) {
			return 27;
		}
		
		char input = (char)inputCaracter;
		
		/* mayusculas L = columna 0 */
		if (Character.isUpperCase(input) && input != 'E') {
			return 0;
		}
		
		/* minusculas l = columna 1 */
		if (Character.isLowerCase(input) && input != 'e' && input != 'i' && input != 'u' && input != 'l') {
			return 1;
		}
		
		/* digito d = columna 2 */
		if (Character.isDigit(input)) {
			return 2;
		}
		
		/* caracter e o E */
		if (input == 'E' || input == 'e') {
			return 3;
		}
		
		/* caracter i = columna 4 */
		if (input == 'i') {
			return 4;
		}
		
		/* caracter u = columna 5 */
		if (input == 'u') {
			return 5;
		}
		
		/* caracter l = columna 6 */
		if (input == 'l') {
			return 6;
		}
		
		/* caracter _ = columna 7 */
		if (input == '_') {
			return 7;
		}
		
		/* caracter + = columna 8 */
		if (input == '+') {
			return 8;
		}
		
		/* caracter - = columna 9 */
		if (input == '-') {
			return 9;
		}
		
		/* caracter { = columna 10 */
		if (input == '{') {
			return 10;
		}
		
		/* caracter } = columna 11 */
		if (input == '}') {
			return 11;
		}
		
		/* caracter ( = columna 12 */
		if (input == '(') {
			return 12;
		}
		
		/* caracter ) = columna 13 */
		if (input == ')') {
			return 13;
		}
		
		/* caracter ; = columna 14 */
		if (input == ';') {
			return 14;
		}
		
		/* caracter / = columna 15 */
		if (input == '/') {
			return 15;
		}
		
		/* caracter * = columna 16 */
		if (input == '*') {
			return 16;
		}
		
		/* caracter > = columna 17 */
		if (input == ',') {
			return 17;
		}
		
		/* caracter = = columna 18 */
		if (input == '=') {
			return 18;
		}
		
		/* caracter > = columna 19 */
		if (input == '>') {
			return 19;
		}
		
		/* caracter < = columna 20 */
		if (input == '<') {
			return 20;
		}
		
		/* caracter % = columna 21 */
		if (input == '%') {
			return 21;
		}
		
		/* caracter ! = columna 22 */
		if (input == '!') {
			return 22;
		}
		
		/* caracter . = columna 23 */
		if (input == '.') {
			return 23;
		}
	
		/* caracter tab = columna 24 */
		if (input == '\t') {
			return 24;
		}
		
		/* blank space = columna 25 */
		if (input == ' ') {
			return 25;
		}
		
		/* new line = columna 26 */
		if (input == '\n') {
			return 26;
		}
		
		/* carriage return = columna 28 */
		if (input == '\r') {
			return 28;
		}
		
		/* Char desconocido = columna 29 */
		return 29;
	}
	
	public AnalizadorLexico(FileReaderHelper fileHelper) {
		this.fileHelper = fileHelper;
	};
	
	public boolean hasNext() {
		return inputCaracter != -1;
	}
	
//	public int yylex(Parser parser) {
//
//		while (estado_actual != MatrixEstados.F) {
//			
//			inputCaracter = fileHelper.nextChar();
//			
//			char inputAsChar = (char)inputCaracter;
//			
//			int columnaCaracter = obtenerColumnaCaracter(inputCaracter);
//				
//			if (inputAsChar == '\n') {
//				logger.incrementarLinea();
//			}
//			
//			int proximoEstado = matrixEstados.getEstadoSiguiente(estado_actual, columnaCaracter);
//						
//			AccionSemantica as = matrixAS.getAccionSemantica(estado_actual, columnaCaracter);
//			
//			tokenLexema = as.ejecutar(fileHelper, lexema, inputAsChar);
//			
//			//Se llega a un estado final que deberia reconocer token pero hay error
//			//de rango, luego se ignora el token y se vuelve al inicio
//			if (proximoEstado == MatrixEstados.F && tokenLexema == -1) {
//				estado_actual = 0;
//			} else {
//				if (proximoEstado != MatrixEstados.E) {
//					estado_actual = proximoEstado;
//				} else {
//					estado_actual = 0;
//				}
//			}
//		}
//		
//		//Se llego al EOF, no se reconocen mas tokens
//		if (!hasNext()) {
//			return 0;
//		}
//		
//		//String[] par = {lexema.toString(), ""};
//
//		//parser.yylval = new ParserVal(par);
//		
//		logger.logSuccess("[Lexico] Se reconoce un token para " + lexema.toString() + " con el token " + tokenLexema);
//		
//		estado_actual = 0;
//		lexema.setLength(0);
//		
//		return tokenLexema;
//	}

	public int getToken() {
		while (estado_actual != MatrixEstados.F) {
			
			inputCaracter = fileHelper.nextChar();
			
			char inputAsChar = (char)inputCaracter;
			
			int columnaCaracter = obtenerColumnaCaracter(inputCaracter);
				
			if (inputAsChar == '\n') {
				logger.incrementarLinea();
			}
			
			int proximoEstado = matrixEstados.getEstadoSiguiente(estado_actual, columnaCaracter);
			
			AccionSemantica as = matrixAS.getAccionSemantica(estado_actual, columnaCaracter);
			
			tokenLexema = as.ejecutar(fileHelper, lexema, inputAsChar);
			
			//Se llega a un estado final que deberia reconocer token pero hay error
			//de rango, luego se ignora el token y se vuelve al inicio
			if (proximoEstado == MatrixEstados.F && tokenLexema == -1) {
				estado_actual = 0;
			} else {
				if (proximoEstado != MatrixEstados.E) {
					estado_actual = proximoEstado;
				} else {
					estado_actual = 0;
				}
			}
		}
		
		logger.logSuccess("[Lexico] Se reconoce un token para " + lexema.toString() + " con el token " + tokenLexema);
		
		//String[] par = {lexema.toString(), ""};

		//parser.yylval = new ParserVal(par);
		
		estado_actual = 0;
		lexema.setLength(0);
		
		return tokenLexema;
	}
}