%{
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
	sentencias '}' { logger.logError("[Parser] Se esperaba simbolo '{' al principio del programa"); } |
	'{' sentencias { logger.logError("[Parser] Se esperaba simbolo '}' al final del programa"); } |
	'{' '}' { logger.logError("[Parser] Programa vacio"); } |
	{ logger.logError("[Parser] Programa vacio"); }
;

sentencias:
	sentencia |
	sentencias sentencia
;

sentencia:
	sentencia_declarativa |
	sentencia_ejecutable { genCodigoIntermedio.resetContador(); } |
	error ',' { logger.logError("[Parser] Error de sintaxis en la sentencia"); }
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
	RETURN { logger.logError("[Parser] Se esperaba un simbolo ',' luego del RETURN"); }
;

sentencia_iterativa_do_while:
	DO bloque_sentencias_ejecutables_while WHILE '(' condicion ')' ','  { 
		logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); 
		genCodigoIntermedio.generarPasoIncompleto("BI");
		genCodigoIntermedio.completarPasoIncompleto();
		genCodigoIntermedio.completarPasoIncompletoIteracion();
		} |
	DO bloque_sentencias_ejecutables_while WHILE '(' condicion ')' { logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); } |
	DO WHILE '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_while WHILE '(' ')' ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_while '(' condicion ')' ',' { logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); } |
	DO WHILE ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_while WHILE ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
;

sentencia_iterativa_do_while_funcion:
	DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' ','  { logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); } |
	DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' { logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
	DO WHILE '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion WHILE '(' ')' ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion '(' condicion ')' ',' { logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); } |
	DO WHILE ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion WHILE ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
;

sentencia_seleccion:
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ELSE bloque_sentencias_ejecutables ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); 
		genCodigoIntermedio.completarPasoIncompleto();
	} |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); 
		genCodigoIntermedio.completarPasoIncompleto(); // ESTA BIEN QUE APAREZCA BIFURCACION INCONDICIONAL ACA?
	} |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ELSE bloque_sentencias_ejecutables ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); } |
	IF '(' ')' bloque_sentencias_ejecutables_then ELSE bloque_sentencias_ejecutables ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); } |
	IF '(' ')' bloque_sentencias_ejecutables_then ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF"); } |
	IF '(' condicion ')' ELSE bloque_sentencias_ejecutables ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ELSE ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
;

sentencia_seleccion_funcion:
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
	IF '(' ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); } |
	IF '(' ')' bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF"); } |
	IF '(' condicion ')' ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
;

bloque_sentencias_ejecutables_then:
	sentencia_ejecutable {
		// EN LAS FILMINAS ESTA INVERTIDO ... 
		genCodigoIntermedio.generarPasoIncompleto("BI");
		genCodigoIntermedio.completarPasoIncompleto();
		genCodigoIntermedio.apilar(genCodigoIntermedio.polacaSize() - 1);
	}|
	'{' sentencias_ejecutables '}' {
		genCodigoIntermedio.generarPasoIncompleto("BI");
		genCodigoIntermedio.completarPasoIncompleto();
		genCodigoIntermedio.apilar(genCodigoIntermedio.polacaSize() - 1);
	}|
	'{' sentencias_ejecutables  { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	sentencias_ejecutables '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables dentro del bloque"); } |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
;

bloque_sentencias_ejecutables_while:
	'{' primer_sentencia '}' |
	'{' primer_sentencia sentencias_ejecutables '}' |
	'{' primer_sentencia sentencias_ejecutables  { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	primer_sentencia sentencias_ejecutables '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables dentro del bloque"); } |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
;

primer_sentencia:
	sentencia_ejecutable {
		genCodigoIntermedio.apilar(genCodigoIntermedio.polacaSize() - genCodigoIntermedio.getContador() + 1);
		genCodigoIntermedio.resetContador();
	}

bloque_sentencias_ejecutables:
	sentencia_ejecutable |
	'{' sentencia_ejecutable sentencias_ejecutables '}' |
	'{' sentencia_ejecutable sentencias_ejecutables  { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	sentencia_ejecutable sentencias_ejecutables '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables dentro del bloque"); } |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
;

bloque_sentencias_ejecutables_funcion:
	sentencia_ejecutable_funcion |
	sentencia_return |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); } |
	'{' sentencias_ejecutables_funcion '}' |
	'{' sentencias_ejecutables_funcion sentencia_return '}' |
	'{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}' |
	sentencias_ejecutables_funcion '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	sentencias_ejecutables_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' sentencias_ejecutables_funcion { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' sentencias_ejecutables_funcion sentencia_return { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables en bloque de sentencias ejecutables"); }
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
	sentencia_ejecutable { genCodigoIntermedio.resetContador(); } |
	sentencias_ejecutables sentencia_ejecutable |
	sentencia_declarativa { logger.logError("[Parser] No se aceptan declaraciones de variables en sentencias ejecutables"); } |
	sentencias_ejecutables sentencia_declarativa { logger.logError("[Parser] No se aceptan declaraciones de variables en sentencias ejecutables"); }
;

sentencias_ejecutables_funcion:
	sentencia_ejecutable_funcion |
	sentencias_ejecutables_funcion sentencia_ejecutable_funcion |
	sentencia_declarativa { logger.logError("[Parser] No se aceptan declaraciones de variables en sentencias ejecutables"); } |
	sentencias_ejecutables_funcion sentencia_declarativa { logger.logError("[Parser] No se aceptan declaraciones de variables en sentencias ejecutables"); }
;

sentencia_imprimir:
	PRINT CADENA ',' { logger.logSuccess("[Parser] Sentencia PRINT detectada"); } |
	PRINT CADENA { logger.logError("[Parser] Se esperaba un simbolo ',' en Sentencia PRINT"); } |
	PRINT ',' { logger.logError("[Parser] Se esperaba CADENA en Sentencia PRINT"); } |
	PRINT ID ',' { logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); } |
	PRINT ID { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia PRINT"); } |
	PRINT constante ',' { logger.logError("[Parser] Se esperaba una CADENA y se encontro una constante en sentencia PRINT"); } |
	PRINT constante { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia PRINT"); }
;

sentencia_invocacion_funcion:
	sentencia_objeto_identificador '(' expresion ')' ',' { 
		logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); 
		if (!genCodigoIntermedio.verificarParametros($1.sval)){
			logger.logError("Cantidad de parametros incorrecta");
		}
	} |
	sentencia_objeto_identificador '(' ')' ',' { 
		logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada");
		if (genCodigoIntermedio.verificarParametros($1.sval)){
			logger.logError("Cantidad de parametros incorrecta");
		}

	} |
	sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ',' { logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); } |
	sentencia_objeto_identificador '(' expresion ')' { logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); } |
	sentencia_objeto_identificador '(' ')' { logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); } |
	sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' { logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
;

lista_expresiones_invocacion_funcion_exceso: 
	expresion |
	lista_expresiones_invocacion_funcion_exceso ',' expresion
;

sentencia_asignacion:
	sentencia_objeto_identificador '=' expresion ',' { 
		logger.logSuccess("[Parser] Asignacion detectada");
		genCodigoIntermedio.agregarElemento($1.sval);
		genCodigoIntermedio.agregarElemento($2.sval);
		genCodigoIntermedio.incrementarContador();	
		genCodigoIntermedio.incrementarContador();
		genCodigoIntermedio.comprobacionUso($1.sval);
	} |
	sentencia_objeto_identificador '=' expresion { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia asignacion"); } |
	sentencia_objeto_identificador '=' ',' { logger.logError("[Parser] Se esperaba expresion del lado derecho en sentencia asignacion"); }
;

sentencia_objeto_identificador:
	ID {
		if (genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval)) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
			genCodigoIntermedio.borrarLexemaDeclarado($1.sval);
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	} |
	sentencia_objeto_identificador '.' ID
;

sentencia_declarativa:
	declaracion_variable |
	declaracion_funcion |
	declaracion_clase |
	declaracion_interfaz
;

declaracion_variable:
	tipo lista_de_variables ',' { 
		logger.logSuccess("[Parser] Declaracion de lista de variables detectado");
		genCodigoIntermedio.agregarTipoAListaDeVariables($1.sval);
		genCodigoIntermedio.agregarUsoAListaDeVariables("variable");
		genCodigoIntermedio.agregarAmbitoAListaDeVariables();
		genCodigoIntermedio.removerListaVariablesADeclarar();
	} |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia declaracion de variables"); } |
	tipo ',' { logger.logError("[Parser] Se esperaba una lista de variables en sentencia declaracion de variables"); }
;

declaracion_interfaz_encabezado:
	INTERFACE ID {
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_interfaz");
		genCodigoIntermedio.agregarAmbitoAIdentificador($2.sval);
		genCodigoIntermedio.setAmbitoClaseInterfaz($2.sval);
	} |
	INTERFACE { logger.logError("[Parser] Se esperaba un identificador en declaracion de INTERFACE"); }
;

declaracion_interfaz:
	declaracion_interfaz_encabezado '{' bloque_encabezado_funcion_declaracion_interfaz '}' { 
		logger.logSuccess("[Parser] Declaracion de INTERFACE detectada");
		genCodigoIntermedio.clearAmbitoClaseInterfaz();
	} |
	declaracion_interfaz_encabezado '}'  { logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de INTERFACE"); }
;

bloque_encabezado_funcion_declaracion_interfaz:
	encabezado_funcion_declaracion_interfaz |
	bloque_encabezado_funcion_declaracion_interfaz encabezado_funcion_declaracion_interfaz
;

encabezado_funcion_declaracion_interfaz:
	encabezado_funcion_interfaz ',' |
	encabezado_funcion_interfaz { logger.logError("[Parser] Se esperaba un simbolo ',' en declaracion de metodo en CLASS"); }
;

sentencia_declarativa_clase:
	tipo lista_de_variables ',' { 
		logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); 
		genCodigoIntermedio.agregarTipoAListaDeVariables($1.sval);
		genCodigoIntermedio.agregarUsoAListaDeVariables("atributo");
		genCodigoIntermedio.agregarAmbitoAListaDeAtributos();
		genCodigoIntermedio.removerListaVariablesADeclarar();
	} |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un simbolo ',' en declaracion de lista de variables en CLASS"); } |
	declaracion_funcion |
	declaracion_funcion ',' { logger.logError("[Parser] Se encontro un simbolo inesperado ',' en declaracion de funcion en CLASS"); } | 
	ID ',' {
		if (genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval)) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " existe en el ambito");
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado en el ambito");
		}
	}
;

declaracion_clase_encabezado:
	CLASS ID {  
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_clase");
		genCodigoIntermedio.agregarAmbitoAIdentificador($2.sval);
		genCodigoIntermedio.setAmbitoClaseInterfaz($2.sval);
		genCodigoIntermedio.apilarAmbito($2.sval);
	} |
	CLASS ID IMPLEMENT ID {
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_clase");
		genCodigoIntermedio.agregarAmbitoAIdentificador($2.sval);
		genCodigoIntermedio.setAmbitoClaseInterfaz($2.sval);
		genCodigoIntermedio.apilarAmbito($2.sval);
	} |
	CLASS { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); } |
	CLASS IMPLEMENT ID { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); } |
	CLASS ID IMPLEMENT { logger.logError("[Parser] Se esperaba un identificador en IMPLEMENT de clase"); } |
	CLASS IMPLEMENT { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
;

declaracion_clase:
	declaracion_clase_encabezado '{' bloque_sentencias_declarativas_clase '}' { 
		logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); 
		genCodigoIntermedio.clearAmbitoClaseInterfaz();
	}
;

bloque_sentencias_declarativas_clase:
	sentencia_declarativa_clase |
	bloque_sentencias_declarativas_clase sentencia_declarativa_clase
;

declaracion_funcion:
	encabezado_funcion cuerpo_funcion { 
		logger.logSuccess("[Parser] Declaracion de funcion detectado");
		genCodigoIntermedio.desapilarAmbito(); 
	}
;

encabezado_funcion:
	encabezado_funcion_nombre '(' parametro_funcion ')' {
		if (genCodigoIntermedio.esDefinicionDeClase()) {
			genCodigoIntermedio.agregarUsoAIdentificador($1.sval, "nombre_metodo");
			genCodigoIntermedio.agregarAmbitoAIdentificadorMetodo($1.sval);	
		} else {
			genCodigoIntermedio.agregarUsoAIdentificador($1.sval, "nombre_funcion");
			genCodigoIntermedio.agregarAmbitoAIdentificador($1.sval);
		}
		genCodigoIntermedio.modificarCantidadParametros($1.sval);
		genCodigoIntermedio.apilarAmbito($1.sval);
		genCodigoIntermedio.agregarAmbitoAIdentificador($3.sval);
	}|
	encabezado_funcion_nombre '(' ')' {
		if (genCodigoIntermedio.esDefinicionDeClase()) {
			genCodigoIntermedio.agregarUsoAIdentificador($1.sval, "nombre_metodo");
			genCodigoIntermedio.agregarAmbitoAIdentificadorMetodo($1.sval);	
		} else {
			genCodigoIntermedio.agregarUsoAIdentificador($1.sval, "nombre_funcion");
			genCodigoIntermedio.agregarAmbitoAIdentificador($1.sval);
		}
		genCodigoIntermedio.apilarAmbito($1.sval);

	}|
	encabezado_funcion_nombre '(' parametro_funcion ',' lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	encabezado_funcion_nombre '(' parametro_funcion lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	encabezado_funcion_nombre ')' { logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
;

encabezado_funcion_nombre:
	VOID ID {
		$$.sval = $2.sval;
	} |
	VOID {
		logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); 
	}
;

encabezado_funcion_interfaz:
	VOID ID '(' parametro_funcion ')' { 
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_metodo");
		genCodigoIntermedio.agregarAmbitoAIdentificadorMetodo($2.sval);
	} |
	VOID ID '(' ')' { 
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_metodo");
		genCodigoIntermedio.agregarAmbitoAIdentificadorMetodo($2.sval);
	} |
	VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID '(' parametro_funcion ')' { logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); } |
	VOID '(' ')' { logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); } |
	VOID ID parametro_funcion ')' { logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); } |
	VOID ID ')' { logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
;

cuerpo_funcion:
	'{' sentencias_funcion sentencia_return '}' |
	'{' sentencia_return '}' |
	'{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}' |
	'{' sentencia_return sentencias_funcion_inalcanzable '}' |
	'{' sentencias_funcion '}' { logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); } |
	'{' '}' { logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); } |
 	sentencias_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); } |
	sentencia_return '}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); } |
	sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); } |
	sentencia_return sentencias_funcion_inalcanzable '}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); } |
	sentencias_funcion '}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); } |
	'}' { logger.logError("[Parser] Se esperaba un simbolo '{' en el cuerpo de la funcion"); }
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
	tipo ID { 
		$$.sval = $2.sval;
		genCodigoIntermedio.agregarUsoAIdentificador($2.sval, "nombre_parametro");
		genCodigoIntermedio.agregarTipoAParametroDeFuncion($2.sval, $1.sval);
	}
;

lista_de_variables:
	ID { 
		if (!genCodigoIntermedio.variableRedeclarada($1.sval)) {
			genCodigoIntermedio.agregarVariableADeclarar($1.sval);
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar la variable " + $1.sval);
		}
	} |
	lista_de_variables ';' ID { 
		if (!genCodigoIntermedio.variableRedeclarada($3.sval)) {
			genCodigoIntermedio.agregarVariableADeclarar($3.sval);
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar la variable " + $3.sval);
		}
	}
;

tipo:
	INT |
	ULONG |
	FLOAT |
	ID {
		if (genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval)) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	}
;

condicion:
	expresion comparador expresion {
		genCodigoIntermedio.agregarElemento($2.sval);
		genCodigoIntermedio.generarPasoIncompleto("BF");
		genCodigoIntermedio.apilar(genCodigoIntermedio.polacaSize() - 1);
	}|
	expresion comparador { logger.logError("[Parser] Se esperaba una expresion del lado derecho de la comparacion"); } |
	comparador expresion { logger.logError("[Parser] Se esperaba una expresion del lado izquierdo de la comparacion"); } |
	expresion '=' expresion { logger.logError("[Parser] Se esperaba un comparador valido en la comparacion"); }
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
	expresion '+' termino { 
		genCodigoIntermedio.agregarElemento($2.sval); genCodigoIntermedio.incrementarContador(); } |
	expresion '-' termino { 
		genCodigoIntermedio.agregarElemento($2.sval); genCodigoIntermedio.incrementarContador(); } |
	termino
;

termino:
	termino '*' factor { genCodigoIntermedio.agregarElemento($2.sval); genCodigoIntermedio.incrementarContador();}|
	termino '/' factor { genCodigoIntermedio.agregarElemento($2.sval); genCodigoIntermedio.incrementarContador();} |
	factor
;

factor:
	ID {
		if (genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval)) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
			genCodigoIntermedio.borrarLexemaDeclarado($1.sval);
			genCodigoIntermedio.agregarElemento($1.sval); genCodigoIntermedio.incrementarContador();
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	} |
	ID OPERADOR_MENOS {
		if (genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval)) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
			genCodigoIntermedio.agregarElemento($1.sval);
			genCodigoIntermedio.agregarElemento("1");
			genCodigoIntermedio.agregarElemento("-");

		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	} |
	constante { genCodigoIntermedio.agregarElemento($1.sval); genCodigoIntermedio.incrementarContador(); } |
	'(' expresion ')' { logger.logError("[Parser] No se admiten expresiones entre parentesis"); }
;

constante:
	CTE { corregirConstantePositivaEntera($1.sval); } |
	'-' CTE { constanteConSigno($2.sval); }
;

%%

public static AnalizadorLexico lexico = null;
public static GeneracionCodigoIntermedio genCodigoIntermedio = GeneracionCodigoIntermedio.getInstance();
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;
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
		ts.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			ts.swapLexemas(constante, "0_ul");
		} else {
			// se recibio un INT negativo
			String negConstante = "-"+constante;
			ts.swapLexemas(constante, negConstante);
		}
	}
}	

public int yylex() {
	return lexico.yylex(parser);
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

			genCodigoIntermedio.showPolaca();
		}
	}
}