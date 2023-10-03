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
	'{' sentencias '}' { logger.logSuccess("[Parser] Programa correcto detectado"); } |
	sentencias '}' { logger.logError("[Parser] Falta simbolo '{' al principio del programa"); } |
	'{' sentencias { logger.logError("[Parser] Falta simbolo '}' al final del programa"); } |
	{ logger.logError("[Parser] Programa vacio"); }
;

sentencias:
	sentencia |
	sentencias sentencia
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
	sentencia_iterativa_do_while |
	sentencia_return { logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
;

sentencias_funcion:
	sentencia_funcion |
	sentencias_funcion sentencia_funcion
;

sentencia_funcion:
	sentencia_declarativa |
	sentencia_ejecutable_funcion
;

sentencia_ejecutable_funcion:
	sentencia_asignacion |
	sentencia_invocacion_funcion |
	sentencia_imprimir |
	sentencia_seleccion_funcion |
	sentencia_iterativa_do_while_funcion
;

sentencia_return:
	RETURN ',' | 
	RETURN { logger.logError("[Parser] Falta ',' luego del RETURN"); }
;

sentencia_iterativa_do_while:
	DO bloque_sentencias_ejecutables WHILE '(' condicion ')' ','  { logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); } |
	DO bloque_sentencias_ejecutables WHILE '(' condicion ')' { logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
;

sentencia_iterativa_do_while_funcion:
	DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' ','  { logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); } |
	DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' { logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
;

sentencia_seleccion:
	IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF { logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF { logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
;

sentencia_seleccion_funcion:
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF { logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF { logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
;

bloque_sentencias_ejecutables:
	sentencia_ejecutable |
	'{' sentencias_ejecutables '}'
;

bloque_sentencias_ejecutables_funcion:
	sentencia_ejecutable_funcion |
	sentencia_return |
	'{' sentencias_ejecutables_funcion '}' |
	'{' sentencias_ejecutables_funcion sentencia_return '}' |
	'{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}'
;

sentencias_ejecutables_funcion_inalcanzable:
	sentencia_ejecutable_funcion_inalcanzable { logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); } |
	sentencias_ejecutables_funcion_inalcanzable sentencia_ejecutable_funcion_inalcanzable { logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
;

sentencia_ejecutable_funcion_inalcanzable:
	sentencia_return |
	sentencia_ejecutable_funcion
;

sentencias_ejecutables:
	sentencia_ejecutable |
	sentencias_ejecutables sentencia_ejecutable
;

sentencias_ejecutables_funcion:
	sentencia_ejecutable_funcion |
	sentencias_ejecutables_funcion sentencia_ejecutable_funcion
;

sentencia_imprimir:
	PRINT CADENA ',' { logger.logSuccess("[Parser] Sentencia PRINT detectada"); } |
	PRINT CADENA { logger.logError("[Parser] Falta ',' en Sentencia PRINT"); } |
	PRINT ',' { logger.logError("[Parser] Falta CADENA en Sentencia PRINT"); } |
	PRINT ID ',' { logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
;

sentencia_invocacion_funcion:
	sentencia_objeto_identificador '(' expresion ')' ',' { logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); } |
	sentencia_objeto_identificador '(' ')' ',' { logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); } |
	sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ',' { logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); } |

	sentencia_objeto_identificador '(' expresion ')' { logger.logError("[Parser] Falta ',' en Invocacion de funcion"); } |
	sentencia_objeto_identificador '(' ')' { logger.logError("[Parser] Falta ',' en Invocacion de funcion"); } |
	sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' { logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
;

lista_expresiones_invocacion_funcion_exceso: 
	expresion |
	expresion ',' expresion
;

sentencia_asignacion:
	sentencia_objeto_identificador '=' expresion ',' { logger.logSuccess("[Parser] Asignacion detectada"); } |
	sentencia_objeto_identificador '=' expresion { logger.logError("[Parser] Falta ',' en sentenecia asignacion"); } |
	sentencia_objeto_identificador '=' ',' { logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
;

sentencia_objeto_identificador:
	ID |
	sentencia_objeto_identificador '.' ID
;

sentencia_declarativa:
	declaracion_variable |
	declaracion_funcion |
	declaracion_clase |
	declaracion_interfaz
;

declaracion_variable:
	tipo lista_de_variables ',' { logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); } |
	tipo lista_de_variables { logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); } |
	tipo ',' { logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
;

declaracion_interfaz:
	INTERFACE ID '{' bloque_encabezado_funcion '}'
;

bloque_encabezado_funcion:
	encabezado_funcion ',' |
	encabezado_funcion ',' bloque_encabezado_funcion
;

sentencia_declarativa_clase:
	tipo lista_de_variables ',' { logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); } |
	declaracion_funcion |
	ID ','
;

declaracion_clase:
	CLASS ID '{' bloque_sentencias_declarativas_clase '}' { logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); } |
	CLASS ID IMPLEMENT ID '{' bloque_sentencias_declarativas_clase '}' { logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
;

bloque_sentencias_declarativas_clase:
	sentencia_declarativa_clase |
	bloque_sentencias_declarativas_clase sentencia_declarativa_clase
;

declaracion_funcion:
	encabezado_funcion cuerpo_funcion { logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
;

encabezado_funcion:
	VOID ID '(' parametro_funcion ')' |
	VOID ID '(' ')' |
	VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID '(' parametro_funcion ')' { logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); } |
	VOID '(' ')' { logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); } |
	VOID ID parametro_funcion ')' { logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); } |
	VOID ID ')' { logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); } |
	VOID ID { logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
;

cuerpo_funcion:
	'{' sentencias_funcion sentencia_return '}' |
	'{' sentencias_funcion '}' { logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); } |
	'{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}'
;

sentencias_funcion_inalcanzable:
	sentencia_funcion_inalcanzable { logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); } |
	sentencias_funcion_inalcanzable sentencia_funcion_inalcanzable { logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
;

sentencia_funcion_inalcanzable:
	sentencia_declarativa |
	sentencia_return |
	sentencia_ejecutable_funcion
;

lista_parametros_funcion_exceso: 
	parametro_funcion |
	lista_parametros_funcion_exceso ',' parametro_funcion |
	lista_parametros_funcion_exceso parametro_funcion
;

parametro_funcion:
	tipo ID
;

lista_de_variables:
	ID |
	lista_de_variables ';' ID
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
	CTE { corregirConstantePositivaEntera($1.sval); } |
	'-' CTE { constanteConSigno($2.sval); }
;

%%

public static AnalizadorLexico lexico = null;
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;
public static int MIN_INT_VALUE = -(int) (Math.pow(2, 15));
public static int MAX_INT_VALUE = (int) (Math.pow(2, 15) - 1);

/** Chequea, para los INT, que el valor positivo no supere el valor maximo */
public void corregirConstantePositivaEntera(String constante) {
	if (constante.contains("_i")) {
		//se recibio un INT con signo positivo
		boolean exceptionOutOfRange = false;
		int cte = 0;
		String constanteValue = constante.toString().split("_")[0];

		try {
			cte = Integer.parseInt(constanteValue);
		} catch (NumberFormatException e) {
			exceptionOutOfRange = true;
		}

		if (cte > MAX_INT_VALUE || exceptionOutOfRange) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + constante + ", se trunca al valor " + MAX_INT_VALUE + "_i");

			ts.swapLexemas(constante, MAX_INT_VALUE + "_i");
		}
	}
}

public void constanteConSigno(String constante) {
	/** Check de float negativos */
	if (constante.contains(".")) {
		
		String negConstante = "-"+constante;
		Double parsedDouble = Double.parseDouble(negConstante);
		
		if (parsedDouble < -1.17549435E-38 && -3.40282347E+38 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-3.40282347E+38 < parsedDouble) {
				negConstante = new String("-3.40282347E+38");
			} else {
				negConstante = new String("-1.17549435E-38");
			}
		}
		
		ts.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			ts.swapLexemas(constante, "0_ul");
		} else {
			// se recibio un INT negativo
			String negConstante = "-"+constante;
			boolean exceptionOutOfRange = false;
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
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintactico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", ts.print());
		}
	}
}