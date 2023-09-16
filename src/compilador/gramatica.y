%{
package compilador;
import java.io.File;
%}

%token ID CTE CADENA IF ELSE ENDIF PRINT VOID RETURN COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_IGUAL COMP_DISTINTO
CLASS WHILE DO INTERFACE IMPLEMENT INT ULONG FLOAT OPERADOR_MENOS

%left '+' '-'
%left '*' '/'


%start programa

%%

programa:
	'{' sentencias '}' { logger.logSuccess("[Parser] Programa correcto detectado"); }
;

sentencias:
	sentencia |
	sentencia sentencias
;

sentencia:
	sentencia_declarativa |
	sentencia_ejecutable
;

sentencia_ejecutable:
	sentencia_asignacion |
	sentencia_invocacion_funcion |
	sentencia_imprimir |
	sentencia_seleccion |
	sentencia_iterativa_do_while
;

sentencia_iterativa_do_while:
	DO bloque_sentencias_ejecutables WHILE '(' condicion ')' ','  { logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
;

sentencia_seleccion:
	IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
;

bloque_sentencias_ejecutables:
	sentencia_ejecutable |
	'{' sentencias_ejecutables '}'
;

sentencias_ejecutables:
	sentencia_ejecutable |
	sentencia_ejecutable sentencias_ejecutables
;

sentencia_imprimir:
	PRINT CADENA ',' { logger.logSuccess("[Parser] Sentencia imprimir detectada"); }
;

sentencia_invocacion_funcion:
	sentencia_objeto_identificador '(' expresion ')' ',' { logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); } |
	sentencia_objeto_identificador '(' ')' ',' { logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); } |
	sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ',' { logger.logError("[Parser] Invocacion de funcion con multiples expresion detectada, se preserva solo la primera expresion"); }
;

lista_expresiones_invocacion_funcion_exceso: 
	expresion |
	expresion ',' expresion
;

sentencia_asignacion:
	sentencia_objeto_identificador '=' expresion ',' { logger.logSuccess("[Parser] Asignacion detectada"); }
;

sentencia_objeto_identificador:
	ID |
	ID '.' sentencia_objeto_identificador
;

sentencia_declarativa:
	tipo lista_de_variables ',' { logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); } |
	declaracion_funcion |
	declaracion_clase
;

sentencia_declarativa_clase:
	tipo lista_de_variables ',' { logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); } |
	declaracion_funcion ',' |
	ID ','
;

declaracion_clase:
	CLASS ID '{' bloque_sentencias_declarativas_clase '}' { logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
;

bloque_sentencias_declarativas_clase:
	sentencia_declarativa_clase |
	sentencia_declarativa_clase bloque_sentencias_declarativas_clase
;

declaracion_funcion:
	VOID ID '(' parametro_funcion ')' '{' '}' { logger.logSuccess("[Parser] Declaracion de funcion con parametro detectado"); } |
	VOID ID '(' ')' '{' '}' { logger.logSuccess("[Parser] Declaracion de funcion sin parametro detectado"); } |
	VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')' '{' '}' { logger.logError("[Parser] Declaracion de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
;

lista_parametros_funcion_exceso: 
	parametro_funcion |
	parametro_funcion ',' lista_parametros_funcion_exceso
;

parametro_funcion:
	tipo ID
;

lista_de_variables:
	ID ';' lista_de_variables |
	ID
;

tipo:
	INT |
	ULONG |
	FLOAT |
	ID
;

condicion:
	expresion comparador expresion
;

comparador:
	COMP_MAYOR_IGUAL | 
	COMP_MENOR_IGUAL | 
	COMP_IGUAL | 
	COMP_DISTINTO |
	'>' |
	'<'
;

expresion:
	expresion '+' termino |
	expresion '-' termino |
	termino
;

termino:
	termino '*' factor |
	termino '/' factor |
	factor
;

factor:
	ID |
	ID OPERADOR_MENOS |
	constante
;

constante:
	CTE |
	'-' CTE { constanteConSigno($2.sval); }
;

%%

public static AnalizadorLexico lexico = null;
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;

public void constanteConSigno(String constante) {
	/** Check de float negativos */
	if (constante.contains(".")) {
		
		String negConstante = "-"+constante;
		Double parsedDouble = Double.parseDouble(negConstante);
		
		if (parsedDouble < -1.17549435E-38 && -3.40282347E+38 > parsedDouble && parsedDouble != 0.0) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-3.40282347E+38 < parsedDouble) {
				negConstante = new String("-3.40282347E+38");
			} else {
				negConstante =  new String("-1.17549435E-38");
			}
		}
		
		ts.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			ts.swapLexemas(constante, "0_ul");
		} else {
			String negConstante = "-"+constante;
			boolean exceptionOutOfRange = false;
			// se recibio un INT negativo
			int MIN_INT_VALUE = -(int) (Math.pow(2, 15));
			int cte = 0;

			String negConstanteValue = negConstante.toString().split("_")[0];

			try {
				cte = Integer.parseInt(negConstanteValue);
			} catch (NumberFormatException e) {
				exceptionOutOfRange = true;
			}

			if (cte < MIN_INT_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");

				ts.swapLexemas(constante, MIN_INT_VALUE + "_i");
			} else {
				ts.swapLexemas(constante, negConstante);
			}
		}
	}
}	

public int yylex() {
	return lexico.yylex(yylval);
}

public void yyerror(String error) {
	logger.logError(error);
}

public static void main(String[] args) {
	if (args.length == 0) {
		System.err.println("No se especifico ningun archivo de codigo");
	} else {
		String archivo_a_leer = args[0];
		System.out.println("Se va a leer archivo " + archivo_a_leer);
		
		FileReaderHelper fileHelper = new FileReaderHelper();
		
		boolean fileOpenSuccess = fileHelper.open(archivo_a_leer);
		
		if (fileOpenSuccess) {
			parser = new Parser();
			lexico = new AnalizadorLexico(fileHelper);
			
	        parser.run();
	
			String path = new File(archivo_a_leer).getAbsolutePath();
	        
	        Output out = new Output(path);
	        
	        String printTs = ts.print();
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintetico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", printTs);
		}
	}
}