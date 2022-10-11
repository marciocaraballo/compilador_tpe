%{
package compilador;
import java.io.File;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_DISTINTO
WHEN DO UNTIL CONTINUE DOUBLE64 UINT16 DEFER CONST 

%left '+' '-'
%left '*' '/'

%start programa

%%

programa: 
	nombre_programa '{' sentencias '}' { logger.logSuccess("[Parser] Programa correcto detectado"); } |
	'{' sentencias '}' { logger.logError("[Parser] Se esperaba un identificador nombre del programa"); } |
	nombre_programa sentencias '}' { logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); } | 
	nombre_programa '{' sentencias { logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); } |
	nombre_programa '{' '}' { logger.logError("[Parser] Se esperaban sentencias del programa"); }
;

nombre_programa: 
	ID
;

sentencias: 
	sentencia |
	sentencias sentencia
;

sentencia:
	sentencia_declarativa |
	sentencia_ejecutable
;

sentencia_declarativa:
	sentencia_declarativa_variables |
	funcion_con_return |
	funcion_sin_return |
	declaracion_constantes
;

sentencia_declarativa_variables:
	tipo lista_de_variables ';' { logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); } |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); } |
	lista_de_variables ';' { logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); } |
	tipo ';' { logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
;

lista_de_variables:
	ID ',' lista_de_variables |
	ID
;

funcion_con_return:
	encabezado_funcion '{' sentencia_return '}' { logger.logSuccess("[Parser] Declaracion de funcion detectado"); } |
	encabezado_funcion '{' sentencias_funcion sentencia_return '}' { logger.logSuccess("[Parser] Declaracion de funcion detectado"); } |
	encabezado_funcion '{' '}' { logger.logError("[Parser] Se esperaban sentencias en la funcion"); }
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
	asignacion |
	DEFER asignacion |
	imprimir |
	DEFER imprimir |
	sentencia_when_con_return |
	DEFER sentencia_when_con_return |
	sentencia_do_con_return |
	DEFER sentencia_do_con_return |
	sentencia_seleccion_simple_con_return |
	DEFER sentencia_seleccion_simple_con_return
;

sentencias_ejecutables_funcion:
	sentencia_ejecutable_funcion |
	sentencias_ejecutables_funcion sentencia_ejecutable_funcion
;

funcion_sin_return:
	encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}' { logger.logSuccess("[Parser] Declaracion de funcion detectado"); } |
	encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}' { logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
;

sentencia_seleccion_compuesta_con_return:
	IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';' |
	IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'
;

sentencia_seleccion_compuesta_con_return_simple:
	sentencia_return |
	sentencia_seleccion_compuesta_con_return |
	DEFER sentencia_seleccion_compuesta_con_return
;

sentencia_seleccion_simple_con_return:
	IF '(' condicion ')' THEN sentencia_return ENDIF ';' |
	IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' |
	IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'
;

sentencia_when_con_return:
	WHEN '(' condicion ')' THEN '{' sentencia_funcion '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_return '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); }
;

sentencia_do_con_return:
	DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';' { logger.logSuccess("[Parser] Sentencia do until detectada"); } |
	etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';' { logger.logSuccess("[Parser] Sentencia do until detectada"); }
;

bloque_sentencias_ejecutables_do_con_return:
	sentencia_return |	
	sentencia_ejecutable_do |
	'{' sentencias_ejecutables_do sentencia_return '}' |
	'{' sentencias_ejecutables_do '}'
;

encabezado_funcion:
	FUN ID '(' ')' ':' tipo |
	FUN ID '(' lista_de_parametros ')' ':' tipo |
	FUN ID '(' ')' ':' { logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); } |
	FUN '(' ')' ':' tipo { logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); } |
	FUN ID ')' ':' tipo { logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); } |
	FUN ID '(' ':' tipo { logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); } |
	FUN ID '(' lista_de_parametros ')' ':' { logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); } |
	FUN '(' lista_de_parametros ')' ':' tipo { logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); } |
	FUN ID lista_de_parametros ')' ':' tipo { logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); } |
	FUN ID '(' lista_de_parametros ':' tipo { logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
;

sentencia_return:
	RETURN '(' expresion ')' ';' |
	RETURN '(' expresion ')' { logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); } |
	RETURN  expresion ')' { logger.logError("[Parser] Se esperaba un ( en la sentencia return"); } |
	RETURN '(' expresion { logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }  |
	RETURN '(' ')' ';' { logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
;

lista_de_parametros:
	parametro |
	parametro ',' parametro |
	parametro ',' parametro ',' lista_parametros_exceso { logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
;

lista_parametros_exceso: 
	parametro |
	parametro ',' lista_parametros_exceso 
;

parametro:
	tipo ID |
	tipo | { logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
	ID { logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
;

declaracion_constantes:
	CONST lista_declaracion_constantes ';' { logger.logSuccess("[Parser] Declaracion de constantes detectado"); } |
	CONST lista_declaracion_constantes { logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); } |
	CONST ';' { logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
;

lista_declaracion_constantes:
	declaracion_constante |
	declaracion_constante ',' lista_declaracion_constantes
;

declaracion_constante:
	ID ASIGNACION CTE |
	ID ASIGNACION { logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); } |
	ID CTE { logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); } |
	ID { logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
;

sentencia_ejecutable:
	asignacion |
	DEFER asignacion |
	seleccion |
	DEFER seleccion |
	imprimir |
	DEFER imprimir |
	sentencia_when |
	DEFER sentencia_when |
	sentencia_do |
	DEFER sentencia_do
;

sentencia_ejecutable_do:
	sentencia_ejecutable |
	sentencia_break |
	sentencia_continue
;

sentencia_break:
	BREAK ';' { logger.logSuccess("[Parser] Sentencia break detectada"); } |
	BREAK ':' etiqueta ';' { logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }  |
	BREAK { logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); } |
	BREAK ':' etiqueta { logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); } |
	BREAK ':' ';' { logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); } 
;

sentencia_continue:
	CONTINUE ';' { logger.logSuccess("[Parser] Sentencia continue detectada"); } |
	CONTINUE { logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
;

sentencia_do:
	sentencia_do_simple |
	etiqueta ':' sentencia_do_simple
;

sentencia_do_simple:
	DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' ';' { logger.logSuccess("[Parser] Sentencia do until detectada"); } |
	DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' { logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); } |
	DO bloque_sentencias_ejecutables_do UNTIL '(' ')' ';' { logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); } |
	DO bloque_sentencias_ejecutables_do UNTIL condicion ')' ';' { logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); } |
	DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ';' { logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
;

etiqueta:
	ID
;

bloque_sentencias_ejecutables_do:
	sentencia_ejecutable_do |	
	'{' sentencias_ejecutables_do '}'
;

sentencias_ejecutables_do:
	sentencia_ejecutable_do |
	sentencias_ejecutables_do sentencia_ejecutable_do
;

asignacion:
	ID ASIGNACION expresion ';' { logger.logSuccess("[Parser] Asignacion detectada"); } |
	ID ASIGNACION ';' {logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");} |
	ID ASIGNACION expresion { logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
; 

sentencia_when:
	WHEN '(' condicion ')' THEN '{' sentencias_when '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN '(' condicion ')' '{' sentencias_when '}' ';' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN '(' ')' THEN '{' sentencias_when '}' ';' { logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
	WHEN '(' condicion ')' THEN '{' sentencias_when '}' { logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); } |
	WHEN '(' condicion ')' '{' sentencias_when '}' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN '(' condicion ')' '{' '}' { logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
;

sentencias_when:
	sentencia |
	sentencia sentencias_when
;

seleccion:
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';' { logger.logSuccess("[Parser] Sentencia if then detectada"); } |
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';' { logger.logSuccess("[Parser] Sentencia if then else detectada"); }
;

bloque_sentencias_ejecutables_seleccion:
	sentencia_ejecutable |	
	'{' sentencias_ejecutables '}'
;

sentencias_ejecutables:
	sentencia_ejecutable |
	sentencias_ejecutables sentencia_ejecutable
;

condicion:
	expresion comparador expresion |
	expresion comparador { logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); } |
	comparador expresion { logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
;

comparador:
	COMP_MAYOR_IGUAL |
	COMP_MENOR_IGUAL |
	COMP_DISTINTO |
	'>' |
	'<' |
	'='
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
	constante |
	invocacion_funcion
;

invocacion_funcion:
	ID '(' ')' |
	ID '(' lista_de_parametros_reales ')'
;

lista_de_parametros_reales:
	parametro_real |
	lista_de_parametros_reales ',' parametro_real
;

parametro_real:
	ID |
	constante
;

imprimir:
	OUT '(' CADENA ')' ';' { logger.logSuccess("[Parser] Sentencia out detectada"); } |
	OUT '(' ID ')' ';' { logger.logSuccess("[Parser] Sentencia out detectada"); } |
	OUT '(' ')'  ';' { logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); } |
	OUT CADENA ')'  ';' { logger.logError("[Parser] Se esperaba un ( en la sentencia out"); } |
	OUT '(' CADENA  ';' { logger.logError("[Parser] Se esperaba un ) en la sentencia out"); } |
	OUT ID ')'  ';' { logger.logError("[Parser] Se esperaba un ( en la sentencia out"); } |
	OUT '(' ID  ';' { logger.logError("[Parser] Se esperaba un ) en la sentencia out"); } |
	OUT '(' CADENA ')' { logger.logError("[Parser] Se espera un ; al final de la sentencia out"); } |
	OUT '(' ID ')' { logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
;

constante:
	CTE |
	'-' CTE { constanteConSigno($2.sval); }
;
	
tipo:
	UINT16 |
	DOUBLE64
;

%%

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		
		String negConstante = "-"+constante;
		
		Double parsedDouble = Double.parseDouble(negConstante.replace('D', 'E'));
		
		if (parsedDouble < -2.2250738585072014E-308 && -1.7976931348623157E+308 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-1.7976931348623157E+308 < parsedDouble) {
				negConstante = new String("-1.7976931348623157D+308");
			} else {
				negConstante =  new String("-2.2250738585072014D-308");
			}
		}
		
		ts.swapLexemas(constante, negConstante);
	} else {
		//se recibio un uint que fue aceptado por el lexico pero resulta ser negativo
		logger.logWarning("[Parser] No se admiten ui16 con valores negativos: " + "-"+constante + ", se trunca a 0");
		
		ts.swapLexemas(constante, "0");
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
		
		fileHelper.open(archivo_a_leer);
		
		Parser parser = new Parser();
		logger = new Logger();
		ts = new TablaDeSimbolos();
		lexico = new AnalizadorLexico(fileHelper, ts, logger);
		
        parser.run();

		String path = new File(archivo_a_leer).getAbsolutePath().replaceAll(args[0],"");
        
        Output out = new Output(path);
        
        String printTs = ts.print();
        
        
        out.saveFile("codigo-lexico.txt", logger.getLexico());
		out.saveFile("codigo-sintetico.txt", logger.getSintactico());
		out.saveFile("tabla-de-simbolos.txt", printTs);
        
		System.out.println(printTs);
	}
}