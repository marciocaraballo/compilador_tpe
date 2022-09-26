programa: 
	nombre_programa bloque_sentencias
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
	ID ',' lista_de_variables;
	ID
;

funcion: 
	encabezado_funcion '{' cuerpo_funcion '}'
;

encabezado_funcion:
	FUN ID '(' ')' ':' tipo |
	FUN ID '(' lista_de_parametros ')' ':' <tipo>
;

cuerpo_funcion:
	sentencias '(' retorno ')';
;

lista_de_parametros:
	parametro |
	parametro, parametro
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
	nombre_de_constante =: valor_de_constante
;

nombre_constante:
	ID
;

valor_de_constante:
	integer |
	double
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
	etiqueta ':' BREAK
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
	sentencia_ejecutable_do sentencias_ejecutables_do
;

asignacion:
	ID =: expresion
; 

sentencia_when:
	WHEN '(' condicion ')' THEN bloque_sentencias
;

seleccion:
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion END_IF |
	IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion END_IF
;

bloque_sentencias_ejecutables_seleccion:
	sentencia_ejecutable |	
	'{' sentencias_ejecutables '}'
;

sentencias_ejecutables:
	sentencia_ejecutable sentencias_ejecutables
;

condicion:
	expresion COMP expresion
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
	parametro_real, lista_de_parametros_reales
;

parametro_real:
	factor
;

imprimir:
	out '(' CADENA ')'
;

factor:
	ID |
	CTE
;
	
tipo:
	UI16 |
	F64