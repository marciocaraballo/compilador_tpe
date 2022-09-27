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
	nombre_programa bloque_sentencias { logger.logSuccess("Programa correcto detectado"); }
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
	tipo lista_de_variables ';' |
	funcion |
	declaracion_constantes ';'
;

lista_de_variables:
	ID ',' lista_de_variables |
	ID
;

funcion: 
	encabezado_funcion '{' cuerpo_funcion '}'
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
	parametro ',' parametro
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
	asignacion ';' |
	sentencia_break ';' |
	CONTINUE ';'
;

sentencia_break:
	BREAK |
	BREAK ':' etiqueta
;

sentencia_do:
	DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' |
	etiqueta ':' DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'
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
	ID ASIGNACION expresion { logger.logSuccess("Asignacion correcta detectada"); }
; 

sentencia_when:
	WHEN '(' condicion ')' THEN bloque_sentencias
;

seleccion:
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF |
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF
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
	termino |
	invocacion_funcion
;

termino:
	termino '*' factor |
	termino '/' factor |
	factor
;

invocacion_funcion:
	ID '(' lista_de_parametros_reales ')'
;

lista_de_parametros_reales:
	parametro_real |
	lista_de_parametros_reales ',' parametro_real
;

parametro_real:
	factor
;

imprimir:
	OUT '(' CADENA ')'
;

factor:
	ID |
	CTE
;
	
tipo:
	UINT16 |
	DOUBLE64
	
%%

public static AnalizadorLexico lexico = null;
public static Logger logger = null;

public int yylex() {
	return lexico.yylex();
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
		TablaDeSimbolos ts = new TablaDeSimbolos();
		lexico = new AnalizadorLexico(fileHelper, ts, logger, parser.yyval);
		
        parser.run();
			
		ts.print();
	}
}