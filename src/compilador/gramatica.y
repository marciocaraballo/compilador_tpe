%{
package compilador;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_DISTINTO
WHEN DO UNTIL CONTINUE DOUBLE64 UINT16 DEFER CONST 

%left '+' '-'
%left '*' '/'

%start programa

%%

programa: 
	nombre_programa bloque_sentencias { logger.logSuccess("[Parser] Programa correcto detectado"); } |
	bloque_sentencias { logger.logError("[Parser] Se esperaba nombre del programa"); }
;

nombre_programa: 
	ID
;

bloque_sentencias: 
	'{' sentencias '}'
;

sentencias: 
	sentencia |
	sentencia sentencias
;

sentencia:
	sentencia_declarativa |
	sentencia_ejecutable
;

sentencia_declarativa:
	tipo lista_de_variables ';' { logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); } |
	funcion { logger.logSuccess("[Parser] Declaracion de funcion detectado"); } |
	declaracion_constantes ';' { logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
;

lista_de_variables:
	ID ',' lista_de_variables |
	ID
;

funcion: 
	encabezado_funcion '{' cuerpo_funcion '}' |
	encabezado_funcion cuerpo_funcion '}' { logger.logError("[Parser] Se esperaba { en la funcion"); }
;

encabezado_funcion:
	FUN ID '(' ')' ':' tipo |
	FUN ID '(' lista_de_parametros ')' ':' tipo
;

cuerpo_funcion:
	sentencias RETURN'(' expresion ')'';'
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
	tipo ID
;

declaracion_constantes:
	CONST lista_declaracion_constantes
;

lista_declaracion_constantes:
	declaracion_constante |
	declaracion_constante ',' lista_declaracion_constantes
;

declaracion_constante:
	ID ASIGNACION CTE
;

sentencia_ejecutable:
	asignacion ';' |
	DEFER asignacion ';' |
	seleccion ';' |
	DEFER seleccion ';' |
	imprimir ';' |
	DEFER imprimir ';' |
	sentencia_when ';' |
	DEFER sentencia_when ';' |
	sentencia_do ';' |
	DEFER sentencia_do ';'
;

sentencia_ejecutable_do:
	sentencia_ejecutable |
	sentencia_break ';' |
	CONTINUE ';'
;

sentencia_break:
	BREAK |
	BREAK ':' etiqueta
;

sentencia_do:
	DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' { logger.logSuccess("[Parser] Sentencia do until detectada"); } |
	etiqueta ':' DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' { logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
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
	ID ASIGNACION expresion { logger.logSuccess("[Parser] Asignacion detectada"); } |
	ID ASIGNACION {logger.logError("[Parser] Asignacion incorrecta porque falta la expresion");}
; 

sentencia_when:
	WHEN '(' condicion ')' THEN bloque_sentencias { logger.logSuccess("[Parser] Sentencia when detectada"); }
;

seleccion:
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF { logger.logSuccess("[Parser] Sentencia if then detectada"); } |
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF { logger.logSuccess("[Parser] Sentencia if then else detectada"); }
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
	expresion comparador expresion
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
	OUT '(' CADENA ')' { logger.logSuccess("[Parser] Sentencia out detectada"); } |
	OUT '(' ID ')' { logger.logSuccess("[Parser] Sentencia out detectada"); }
;

constante:
	CTE |
	'-' CTE { constanteConSigno($2.sval); }
;
	
tipo:
	UINT16 |
	DOUBLE64
	
%%

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		ts.swapLexemas(constante, "-"+constante);
	} else {
		logger.logError("[Lexico] No se admiten ui16 con valores negativos: " + "-"+constante);
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
			
		ts.print();
	}
}