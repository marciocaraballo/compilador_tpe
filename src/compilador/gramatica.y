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
	sentencia_ejecutable { polaca.resetContador(); } |
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
	sentencia_ejecutable_funcion {polaca.resetContador();}
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
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.completarPasoIncompletoIteracion();
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
	DO bloque_sentencias_ejecutables_funcion_while WHILE '(' condicion ')' ','  { 
		logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada");
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.completarPasoIncompletoIteracion();	
	} |
	DO bloque_sentencias_ejecutables_funcion_while WHILE '(' condicion ')' { logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
	DO WHILE '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion_while WHILE '(' ')' ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion_while '(' condicion ')' ',' { logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); } |
	DO WHILE ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO bloque_sentencias_ejecutables_funcion_while WHILE ',' { logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); } |
	DO ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' condicion ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' ','  { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); } |
	DO '(' ')' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
;

sentencia_seleccion:
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ELSE bloque_sentencias_ejecutables ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); 
		polaca.completarPasoIncompleto();
	} |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); 
		polaca.completarPasoIncompleto(); // ESTA BIEN QUE APAREZCA BIFURCACION INCONDICIONAL ACA?
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
	IF '(' condicion ')' bloque_sentencias_ejecutables_then_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); 
		polaca.completarPasoIncompleto();
	} |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then_funcion ENDIF ',' { 
		logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada");
		polaca.completarPasoIncompleto();
	} |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then_funcion ENDIF { logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
	IF '(' ')' bloque_sentencias_ejecutables_then_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); } |
	IF '(' ')' bloque_sentencias_ejecutables_then_funcion ENDIF ',' { logger.logError("[Parser] Se esperaba condicion en sentencia IF"); } |
	IF '(' condicion ')' ELSE bloque_sentencias_ejecutables_funcion ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' bloque_sentencias_ejecutables_then_funcion ELSE ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); } |
	IF '(' condicion ')' ENDIF ',' { logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
;

bloque_sentencias_ejecutables_then:
	sentencia_ejecutable {
		// EN LAS FILMINAS ESTA INVERTIDO ... 
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
	}|
	'{' sentencias_ejecutables '}' {
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
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
		if ($1.sval.equals("DO")){
			polaca.apilar(polaca.getPosicion() + 1);
		}
		else{
			polaca.apilar(polaca.polacaSize() - polaca.getContador() + 1);
		}
		polaca.resetContador();
	}

primer_sentencia_funcion:
	sentencia_ejecutable_funcion {
		if ($1.sval.equals("DO"))
			polaca.apilar(polaca.getPosicion() + 1);
		else
			polaca.apilar(polaca.polacaSize() - polaca.getContador() + 1);
		polaca.resetContador();
	}

bloque_sentencias_ejecutables:
	sentencia_ejecutable |
	'{' sentencia_ejecutable sentencias_ejecutables '}' |
	'{' sentencia_ejecutable sentencias_ejecutables  { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	sentencia_ejecutable sentencias_ejecutables '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables dentro del bloque"); } |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
;

bloque_sentencias_ejecutables_then_funcion:
	sentencia_ejecutable_funcion {
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
	}|
	sentencia_return |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); } |
	'{' sentencias_ejecutables_funcion '}' {
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
	}|
	'{' sentencias_ejecutables_funcion sentencia_return '}' {
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
	}|
	'{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}' {
		polaca.generarPasoIncompleto("BI");
		polaca.completarPasoIncompleto();
		polaca.apilar(polaca.polacaSize() - 1);
	}|
	sentencias_ejecutables_funcion '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	sentencias_ejecutables_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' sentencias_ejecutables_funcion { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' sentencias_ejecutables_funcion sentencia_return { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables en bloque de sentencias ejecutables"); }
;

bloque_sentencias_ejecutables_funcion_while:
	'{' primer_sentencia_funcion '}'|
	sentencia_return |
	sentencia_declarativa { logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); } |
	'{' primer_sentencia_funcion sentencias_ejecutables_funcion '}' |
	'{' primer_sentencia_funcion sentencias_ejecutables_funcion sentencia_return '}' |
	'{' primer_sentencia_funcion sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}' |
	primer_sentencia_funcion sentencias_ejecutables_funcion '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	primer_sentencia_funcion sentencias_ejecutables_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaban un simbolo '{' en el bloque"); } |
	'{' primer_sentencia_funcion sentencias_ejecutables_funcion { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' primer_sentencia_funcion sentencias_ejecutables_funcion sentencia_return { logger.logError("[Parser] Se esperaban un simbolo '}' en el bloque"); } |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias ejecutables en bloque de sentencias ejecutables"); }
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
	sentencia_ejecutable |
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

		if ($1.sval.contains(".")) {
			boolean esCadenaValida = genCodigoIntermedio.esCadenaDeLlamadasValida($1.sval);

			/* FALTA CHEQUEO PARAMS, ACA IRIA TAMBIEN EL QUE HACER CON EL CASO VALIDO */
			if (esCadenaValida) {
				logger.logSuccess("[Codigo Intermedio] La cadena de llamadas " + $1.sval + " es valida ");
			} else {
				logger.logError("[Codigo Intermedio] La cadena de llamadas " + $1.sval + " no es valida ");
			}
		} else {
			String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval);
			if (!ambito.isEmpty()){
				if (!(boolean) TS.getAtributo($1.sval + ambito, Constantes.TIENE_PARAMETRO)){
					logger.logError("Cantidad de parametros incorrecta");
				}
				else {
					polaca.generarPasoIncompleto("BI");
					polaca.completarPasoIncompletoInvocacion(ambito + ":" + $1.sval + ":TAG", true);
				}
			}
		}
	} |
	sentencia_objeto_identificador '(' ')' ',' { 
		logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada");

		if ($1.sval.contains(".")) {

			boolean esCadenaValida = genCodigoIntermedio.esCadenaDeLlamadasValida($1.sval);

			/* FALTA CHEQUEO PARAMS, ACA IRIA TAMBIEN EL QUE HACER CON EL CASO VALIDO */
			if (esCadenaValida) {
				logger.logSuccess("[Codigo Intermedio] La cadena de llamadas " + $1.sval + " es valida ");
			} else {
				logger.logError("[Codigo Intermedio] La cadena de llamadas " + $1.sval + " no es valida ");
			}
		} else {
			String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval);
			if (!ambito.isEmpty()) {
				if (TS.getAtributo($1.sval + ambito, Constantes.USE) == Constantes.NOMBRE_FUNCION) {
					if ((boolean) TS.getAtributo($1.sval + ambito, Constantes.TIENE_PARAMETRO)) {
						logger.logError("[Generacion codigo] Cantidad de parametros incorrecta para la funcion " + $1.sval);
					}
					else{
						polaca.generarPasoIncompleto("BI");
						polaca.completarPasoIncompletoInvocacion(ambito + ":" + $1.sval + ":TAG", false);
					}
				} else {
					logger.logError("[Codigo intermedio] El identificador " + $1.sval + " no es una funcion");
				}
			} else {
				logger.logError("[Codigo intermedio] El identificador " + $1.sval + " no esta declarado");
			}
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
		/** Se llama a miembro de clase */ 
		if ($1.sval.contains(".")) {

			boolean esCadenaValida = genCodigoIntermedio.esCadenaDeLlamadasValida($1.sval);

			if (esCadenaValida) {

				String[] partes = $1.sval.split("\\."); 
				String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor(partes[0]);

				polaca.agregarElemento($1.sval);
				polaca.agregarElemento($2.sval);
				TS.agregarAtributo(partes[0] + ambito, Constantes.COMPROBACION_USO, true);
			}
		} else {
			String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval);
			/** "expresion" parece apilar cosas en la polaca, pero recien aca sabemos si la asignacion
				es sintacticamente correcta, capaz se pueda procesar aca la expresion o desapilar */
			if (!ambito.isEmpty()) {
				polaca.agregarElemento($1.sval + ambito);
				polaca.agregarElemento($2.sval);
				// INDICO EN LA TABLA DE SIMBOLOS QUE LA VARIABLE SE UTILIZO DEL LADO IZQUIERDO
				TS.agregarAtributo($1.sval + ambito, Constantes.COMPROBACION_USO, true);
			} else {
				polaca.removeElementos();
				logger.logError("[Codigo intermedio] El identificador " + $1.sval + " no esta declarado");
			}
		}
	} |
	sentencia_objeto_identificador '=' expresion { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia asignacion"); } |
	sentencia_objeto_identificador '=' ',' { logger.logError("[Parser] Se esperaba expresion del lado derecho en sentencia asignacion"); }
;

sentencia_objeto_identificador:
	ID {
		$$.sval = $1.sval;
	} |
	sentencia_objeto_identificador '.' ID {
		$$.sval = $1.sval + "." + $3.sval;
	}
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
		genCodigoIntermedio.agregarUsoAListaDeVariables(Constantes.USO_VARIABLE);
		genCodigoIntermedio.agregarAmbitoAListaDeVariables();
		genCodigoIntermedio.removerListaVariablesADeclarar();
	} |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un simbolo ',' en sentencia declaracion de variables"); } |
	tipo ',' { logger.logError("[Parser] Se esperaba una lista de variables en sentencia declaracion de variables"); }
;

declaracion_interfaz_encabezado:
	INTERFACE ID {
		TS.agregarAtributo($2.sval, Constantes.USE, Constantes.NOMBRE_INTERFAZ);
		TS.agregarAtributo($2.sval, Constantes.METODOS, null);
		//Agrego Ambito a identificador
		TS.swapLexemas($2.sval, $2.sval + genCodigoIntermedio.generarAmbito());
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
		genCodigoIntermedio.agregarUsoAListaDeVariables(Constantes.USO_ATRIBUTO);
		genCodigoIntermedio.agregarAmbitoAListaDeAtributos();
		genCodigoIntermedio.removerListaVariablesADeclarar();
	} |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un simbolo ',' en declaracion de lista de variables en CLASS"); } |
	declaracion_funcion |
	declaracion_funcion ',' { logger.logError("[Parser] Se encontro un simbolo inesperado ',' en declaracion de funcion en CLASS"); } | 
	ID ',' {

		String ambitoDeClase = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor($1.sval);

		if (!genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor($1.sval).isEmpty()) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");

			int nivelesDeHerencia = (int) TS.getInstance().getAtributo($1.sval + ambitoDeClase, Constantes.NIVELES_HERENCIA);

			/** Se permiten hasta 3 niveles de herencia, se hace +1 para ver si con la nueva herencia no se viola la restriccion */
			if (nivelesDeHerencia + 1 >= 3) {
				logger.logError("[Codigo Intermedio] Se superaron los niveles de herencia validos para la clase " + genCodigoIntermedio.getAmbitoClaseInterfaz());
			} else {

				String claseActual = genCodigoIntermedio.getAmbitoClaseInterfaz();
				String ambitoClaseActual = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
				String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;
				String nuevoLexema = $1.sval + ambitoClaseDefinidaActual;

				TS.getInstance().swapLexemas($1.sval, nuevoLexema);
				TS.getInstance().agregarAtributo(nuevoLexema, Constantes.TYPE, $1.sval);
				TS.getInstance().agregarAtributo(nuevoLexema, Constantes.USE, "nombre_clase");
				TS.getInstance().agregarAtributo(claseActual + ambitoClaseActual, Constantes.NIVELES_HERENCIA, nivelesDeHerencia + 1);
			}
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	}
;

declaracion_clase:
	declaracion_clase_encabezado '{' bloque_sentencias_declarativas_clase '}' { 
		logger.logSuccess("[Parser] Declaracion de clase CLASS detectado");
		
		if (genCodigoIntermedio.verificarImplementacion($1.sval)){
			logger.logSuccess("[Codigo Intermedio] Metodos declarados en interfaz fueron implementados");
		}
		else{
			logger.logError("[Codigo Intermedio] No fueron implementados todos los metodos de la interfaz");
		}
		
		genCodigoIntermedio.clearAmbitoClaseInterfaz();
	}
;

declaracion_clase_encabezado:
	CLASS ID { 
		//CHEQUEO QUE CLASE NO HAYA SIDO DECLARADA (DEBERIA CHEQUEAR USO, XQ PUEDE QUE IDENTIF PERTENEZCA A OTRA USO)
		if (!TS.has($2.sval + genCodigoIntermedio.generarAmbito())) {
			TS.agregarAtributo($2.sval, Constantes.USE, "nombre_clase");
			TS.agregarAtributo($2.sval, Constantes.IMPLEMENTA, null);
			TS.agregarAtributo($2.sval, Constantes.NIVELES_HERENCIA, 0);
			TS.swapLexemas($2.sval, $2.sval + genCodigoIntermedio.generarAmbito());
			genCodigoIntermedio.setAmbitoClaseInterfaz($2.sval);
			genCodigoIntermedio.apilarAmbito($2.sval);
			$$.sval = $2.sval;
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $2.sval);
		}
	} |
	CLASS ID IMPLEMENT ID {
		//CHEQUEO QUE CLASE NO HAYA SIDO DECLARADA (DEBERIA CHEQUEAR USO, XQ PUEDE QUE IDENTIF PERTENEZCA A OTRA USO)
		if (!TS.has($2.sval + genCodigoIntermedio.generarAmbito())) {
			if (!genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($4.sval).isEmpty()) {
				logger.logSuccess("[Codigo Intermedio] El identificador " + $4.sval + " esta declarado");
				TS.agregarAtributo($2.sval, Constantes.USE, "nombre_clase");
				TS.agregarAtributo($2.sval, Constantes.METODOS, null);
				TS.agregarAtributo($2.sval, Constantes.NIVELES_HERENCIA, 0);
				// REGISTRO EN LA TABLA DE SIMBOLOS, CUAL ES LA INTERFAZ QUE ESTA IMPLEMENTANDO
				TS.agregarAtributo($2.sval, Constantes.IMPLEMENTA, $4.sval);
				//Agrego Ambito a identificador
				TS.swapLexemas($2.sval, $2.sval + genCodigoIntermedio.generarAmbito());
				genCodigoIntermedio.setAmbitoClaseInterfaz($2.sval);
				genCodigoIntermedio.apilarAmbito($2.sval);
				$$.sval = $2.sval;
			} else {
				logger.logError("[Codigo Intermedio] El identificador " + $4.sval + " no esta declarado");
			}
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $2.sval);
		}
	} |
	CLASS { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); } |
	CLASS IMPLEMENT ID { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); } |
	CLASS ID IMPLEMENT { logger.logError("[Parser] Se esperaba un identificador en IMPLEMENT de clase"); } |
	CLASS IMPLEMENT { logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
;



bloque_sentencias_declarativas_clase:
	sentencia_declarativa_clase |
	bloque_sentencias_declarativas_clase sentencia_declarativa_clase
;

declaracion_funcion:
	encabezado_funcion cuerpo_funcion { 
		logger.logSuccess("[Parser] Declaracion de funcion detectado");
		if (genCodigoIntermedio.isPuedoDesapilar()){
			// ESTA LINEa ESTABA CON EL IF DE ARRIBA, Y NO ENTRABA NUNCA POR ESO NO SALIA DEL AMBITO, CAMBIA ALGO?
			if (genCodigoIntermedio.esDefinicionDeClase()) {
				if (genCodigoIntermedio.esMayorAMaximoNivelAnidamientoFuncionEnMetodo()) {
					logger.logError("[Parser] Se permite hasta un maximo de un nivel de anidamiento en una funcion dentro de un metodo de clase");
				}
			}
			genCodigoIntermedio.desapilarAmbito();
		}
		else 
			genCodigoIntermedio.setPuedoDesapilar();
	}
;

encabezado_funcion:
	encabezado_funcion_nombre '(' parametro_funcion ')' {
		// CHEQUEO QUE LA FUNCION NO ESTE DECLARADA
		if (!TS.has($1.sval + genCodigoIntermedio.generarAmbito())) {

			genCodigoIntermedio.apilarAmbito($1.sval);

			if (genCodigoIntermedio.esDefinicionDeClase()) {

				String claseActual = genCodigoIntermedio.getAmbitoClaseInterfaz();
				String ambitoClaseActual = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
				String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;
				String nuevoLexema = $1.sval + ambitoClaseDefinidaActual;

				genCodigoIntermedio.apilarAmbito($1.sval);
				TS.agregarAtributo($1.sval, Constantes.USE, "nombre_metodo");
				TS.agregarAtributo($1.sval, Constantes.TIENE_PARAMETRO, true);
				// Agrego Ambito a metodo
				TS.swapLexemas($1.sval, nuevoLexema);
				//Agrego Ambito a identificador
				TS.swapLexemas($3.sval, $3.sval + ambitoClaseDefinidaActual + ":" + $1.sval);
			} else {
				TS.agregarAtributo($1.sval, Constantes.USE, Constantes.NOMBRE_FUNCION);
				TS.agregarAtributo($1.sval, Constantes.TIENE_PARAMETRO, true);
				//Agrego Ambito a identificador
				TS.swapLexemas($1.sval, $1.sval + genCodigoIntermedio.generarAmbito());
				polaca.crearPolacaAmbitoNuevo(genCodigoIntermedio.generarAmbito() + ":" + $1.sval);
				//Agrego Ambito a identificador
				TS.swapLexemas($3.sval, $3.sval + genCodigoIntermedio.generarAmbito());
			}
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $1.sval);
			genCodigoIntermedio.setPuedoDesapilar();
		}
	}|
	encabezado_funcion_nombre '(' ')' {
		// CHEQUEO QUE LA FUNCION NO ESTE DECLARADA
		if (!TS.has($1.sval + genCodigoIntermedio.generarAmbito())) {
			if (genCodigoIntermedio.esDefinicionDeClase()) {

				String claseActual = genCodigoIntermedio.getAmbitoClaseInterfaz();
				String ambitoClaseActual = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
				String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;
				String nuevoLexema = $1.sval + ambitoClaseDefinidaActual;

				TS.agregarAtributo($1.sval, Constantes.USE, "nombre_metodo");
				TS.agregarAtributo($1.sval, Constantes.TIENE_PARAMETRO, false);
				genCodigoIntermedio.agregarAtributoMetodos($1.sval);
				//Agrego Ambito a identificador
				TS.swapLexemas($1.sval, nuevoLexema);
			} else {
				TS.agregarAtributo($1.sval, Constantes.USE, Constantes.NOMBRE_FUNCION);
				TS.agregarAtributo($1.sval, Constantes.TIENE_PARAMETRO, false);
				//Agrego Ambito a identificador
				TS.swapLexemas($1.sval, $1.sval + genCodigoIntermedio.generarAmbito());
				//polaca.crearPolacaAmbitoNuevo(genCodigoIntermedio.generarAmbito() + ":" + $1.sval);
			}
			genCodigoIntermedio.apilarAmbito($1.sval);
			polaca.crearPolacaAmbitoNuevo(genCodigoIntermedio.generarAmbito().toString());
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $1.sval);
			genCodigoIntermedio.setPuedoDesapilar();
		}
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

		String claseActual = genCodigoIntermedio.getAmbitoClaseInterfaz();
		String ambitoClaseActual = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
		String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;
		String nuevoLexema = $2.sval + ambitoClaseDefinidaActual;

		TS.agregarAtributo($2.sval, Constantes.USE, "nombre_metodo");
		TS.agregarAtributo($2.sval, Constantes.TIENE_PARAMETRO , true);
		TS.swapLexemas($2.sval, nuevoLexema);
		genCodigoIntermedio.agregarAtributoMetodos($2.sval);
		TS.swapLexemas($4.sval, $4.sval + ambitoClaseDefinidaActual + ":" + $2.sval);
	} |
	VOID ID '(' ')' { 
		String claseActual = genCodigoIntermedio.getAmbitoClaseInterfaz();
		String ambitoClaseActual = genCodigoIntermedio.existeIdentificadorDeClaseEnAlgunAmbitoContenedor(claseActual);
		String ambitoClaseDefinidaActual = ambitoClaseActual + ":" + claseActual;
		String nuevoLexema = $2.sval + ambitoClaseDefinidaActual;

		TS.agregarAtributo($2.sval, Constantes.USE, "nombre_metodo");
		TS.swapLexemas($2.sval, nuevoLexema);
		genCodigoIntermedio.agregarAtributoMetodos($2.sval);
	} |
	VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')' { logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); } |
	VOID '(' parametro_funcion ')' { logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); } |
	VOID '(' ')' { logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); } |
	VOID ID parametro_funcion ')' { logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); } |
	VOID ID ')' { logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
;

cuerpo_funcion:
	'{' sentencias_funcion sentencia_return '}'|
	'{' sentencia_return '}'|
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
		TS.agregarAtributo($2.sval, Constantes.USE, "nombre_parametro");
		// Agrego tipo a parametro de funcion
		TS.agregarAtributo($2.sval, Constantes.TYPE, $1.sval);
	}
;

lista_de_variables:
	ID { 
		if (!genCodigoIntermedio.variableRedeclarada($1.sval)) {
			genCodigoIntermedio.agregarVariableADeclarar($1.sval);
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $1.sval);
		}
	} |
	lista_de_variables ';' ID { 
		if (!genCodigoIntermedio.variableRedeclarada($3.sval)) {
			genCodigoIntermedio.agregarVariableADeclarar($3.sval);
		} else {
			logger.logError("[Codigo intermedio] Se intento volver a declarar el identificador " + $3.sval);
		}
	}
;

tipo:
	INT |
	ULONG |
	FLOAT |
	ID {
		if (!genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval).isEmpty()) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	}
;

condicion:
	expresion comparador expresion {
		polaca.agregarElemento($2.sval);
		polaca.generarPasoIncompleto("BF");
		polaca.apilar(polaca.polacaSize() - 1);
	} |
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
		polaca.agregarElemento($2.sval);} |
	expresion '-' termino {
		polaca.agregarElemento($2.sval);} |
	termino
;

termino:
	termino '*' factor { polaca.agregarElemento($2.sval); }|
	termino '/' factor { polaca.agregarElemento($2.sval); } |
	factor
;

factor:
	ID {

		String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval);

		if (!ambito.isEmpty()) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
			TS.removeLexema($1.sval);
			polaca.agregarElemento($1.sval + ambito);
		} else {
			polaca.removeElementos();
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	} |
	ID OPERADOR_MENOS {

		String ambito = genCodigoIntermedio.existeIdentificadorEnAlgunAmbitoContenedor($1.sval);

		if (!ambito.isEmpty()) {
			logger.logSuccess("[Codigo Intermedio] El identificador " + $1.sval + " esta declarado");
			polaca.agregarElemento($1.sval + ambito);
			polaca.agregarElemento("1");
			polaca.agregarElemento("-");

		} else {
			logger.logError("[Codigo Intermedio] El identificador " + $1.sval + " no esta declarado");
		}
	} |
	constante { polaca.agregarElemento($1.sval);} |
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
public static TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
public static Polaca polaca = Polaca.getInstance();
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

			TS.swapLexemas(constante, MAX_INT_VALUE + "_i");
		}
	}
}

public void constanteConSigno(String constante) {
	/** Check de float negativos */
	if (constante.contains(".")) {
		String negConstante = "-"+constante;
		TS.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			TS.swapLexemas(constante, "0_ul");
		} else {
			// se recibio un INT negativo
			String negConstante = "-"+constante;
			TS.swapLexemas(constante, negConstante);
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
			out.saveFile("tabla-de-simbolos.txt", TS.print());

			polaca.showPolaca();
		}
	}
}
