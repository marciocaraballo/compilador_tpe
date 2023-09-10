package compilador;

import java.lang.Character;

import accion_semantica.AccionSemantica;

public class AnalizadorLexico {
	
	private FileReaderHelper fileHelper = null;
	private static MatrixEstados matrixEstados = MatrixEstados.getInstance();
	private static MatrixAccionesSemanticas matrixAS = MatrixAccionesSemanticas.getInstance();
	private static Logger logger = Logger.getInstance();
	
	private static int estado_actual = 0;
	
	private static StringBuilder lexema = new StringBuilder("");
	private static int tokenLexema = -1;
	private static int inputCaracter = 0;
	
	private static int obtenerColumnaCaracter(int inputCaracter) {
		
		/* EOF -> $ columna 28 */
		if (inputCaracter == -1) {
			return 28;
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
		
		/* caracter E */
		if (input == 'E') {
			return 3;
		}
		
		/* caracter e */
		if (input == 'e') {
			return 4;
		}
		
		/* caracter i = columna 5 */
		if (input == 'i') {
			return 5;
		}
		
		/* caracter u = columna 6 */
		if (input == 'u') {
			return 6;
		}
		
		/* caracter l = columna 7 */
		if (input == 'l') {
			return 7;
		}
		
		/* caracter _ = columna 8 */
		if (input == '_') {
			return 8;
		}
		
		/* caracter + = columna 9 */
		if (input == '+') {
			return 9;
		}
		
		/* caracter - = columna 10 */
		if (input == '-') {
			return 10;
		}
		
		/* caracter { = columna 11 */
		if (input == '{') {
			return 11;
		}
		
		/* caracter } = columna 12 */
		if (input == '}') {
			return 12;
		}
		
		/* caracter ( = columna 13 */
		if (input == '(') {
			return 13;
		}
		
		/* caracter ) = columna 14 */
		if (input == ')') {
			return 14;
		}
		
		/* caracter ; = columna 15 */
		if (input == ';') {
			return 15;
		}
		
		/* caracter / = columna 16 */
		if (input == '/') {
			return 16;
		}
		
		/* caracter * = columna 17 */
		if (input == '*') {
			return 17;
		}
		
		/* caracter > = columna 18 */
		if (input == ',') {
			return 18;
		}
		
		/* caracter = = columna 19 */
		if (input == '=') {
			return 19;
		}
		
		/* caracter > = columna 20 */
		if (input == '>') {
			return 20;
		}
		
		/* caracter < = columna 21 */
		if (input == '<') {
			return 21;
		}
		
		/* caracter % = columna 22 */
		if (input == '%') {
			return 22;
		}
		
		/* caracter ! = columna 23 */
		if (input == '!') {
			return 23;
		}
		
		/* caracter . = columna 24 */
		if (input == '.') {
			return 24;
		}
	
		/* caracter tab = columna 25 */
		if (input == '\t') {
			return 25;
		}
		
		/* blank space = columna 26 */
		if (input == ' ') {
			return 26;
		}
		
		/* new line = columna 26 */
		if (input == '\n') {
			return 27;
		}
		
		/* carriage return = columna 29 */
		if (input == '\r') {
			return 29;
		}
		
		/* Char desconocido = columna 30 */
		return 30;
	}
	
	public AnalizadorLexico(FileReaderHelper fileHelper) {
		this.fileHelper = fileHelper;
	};
	
	public boolean hasNext() {
		return inputCaracter != -1;
	}

	public int yylex(Parser parser) {
		while (estado_actual != MatrixEstados.F && hasNext()) {
			
			inputCaracter = fileHelper.nextChar();
			
			char inputAsChar = (char)inputCaracter;
			
			int columnaCaracter = obtenerColumnaCaracter(inputCaracter);
				
			if (inputAsChar == '\n') {
				logger.incrementarLinea();
			}
			
			int proximoEstado = matrixEstados.getEstadoSiguiente(estado_actual, columnaCaracter);
			
			//System.out.println("Caracter: " + inputAsChar + " estado actual: " + estado_actual + " proximo estado: " + proximoEstado);
			
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
		
		if (tokenLexema != -1) {
			logger.logSuccess("[Lexico] Se reconoce un token para " + lexema.toString() + " con el token " + tokenLexema);
		}
		
		String[] par = {lexema.toString(), ""};

		parser.yylval = new ParserVal(par);
		
		estado_actual = 0;
		lexema.setLength(0);
		
		return tokenLexema;
	}
}