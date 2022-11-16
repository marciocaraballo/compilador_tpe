%{
package compilador;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_DISTINTO
WHEN DO UNTIL CONTINUE DOUBLE64 UINT16 DEFER CONST 

%left '+' '-'
%left '*' '/'


%start programa

%%

programa: 
	nombre_programa '{' sentencias '}' { 
		logger.logSuccess("[Parser] Programa correcto detectado");
		ArrayList<Terceto> deferTercetosAmbitoPrograma = instance.desapilarAmbitoParaDefer();
		Iterator<Terceto> it = deferTercetosAmbitoPrograma.listIterator();

		while(it.hasNext()) {
			Terceto tercetoDeferPrograma = it.next();
			instance.agregarTerceto(tercetoDeferPrograma);
		}
	} |
	'{' sentencias '}' { logger.logError("[Parser] Se esperaba un identificador nombre del programa"); } |
	nombre_programa sentencias '}' { logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); } | 
	nombre_programa '{' sentencias { logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); } |
	nombre_programa '{' '}' { logger.logError("[Parser] Se esperaban sentencias del programa"); } |
	{ logger.logError("[Parser] Se esperaban sentencias del programa"); }
;

nombre_programa: 
	ID {	
		instance.agregarUsoAIdentificador($1.obj[0], "nombre_programa");
		instance.apilarAmbitoParaDefer();
		instance.nuevoAmbito($1.obj[0]);
	}
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
	tipo lista_de_variables ';' { 
		logger.logSuccess("[Parser] Declaracion de lista de variables detectado");
		instance.agregarTipoAListaDeVariables($1.obj[1]);
	} |
	tipo lista_de_variables { logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); } |
	lista_de_variables ';' { logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); } |
	tipo ';' { logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
;

lista_de_variables:
	ID ',' lista_de_variables { 
		instance.agregarVariableADeclarar($1.obj[0]);
	} |
	ID {
		instance.agregarVariableADeclarar($1.obj[0]);
	}
;

funcion_con_return:
	encabezado_funcion '{' sentencia_return '}' { 
		logger.logSuccess("[Parser] Declaracion de funcion detectado");  
		instance.salirAmbito();
		instance.agregarTerceto(new Terceto("Fin Funcion", "-", "-"));
		} |
	encabezado_funcion '{' sentencias_funcion sentencia_return '}' { 
		logger.logSuccess("[Parser] Declaracion de funcion detectado"); 
		instance.salirAmbito();
		instance.agregarTerceto(new Terceto("Fin Funcion", "-", "-"));
	} |
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
    IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';' { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return_simple '}' ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return_simple '}' ENDIF ';'  { logger.logSuccess("[Parser] Sentencia seleccion detectada"); } |
	IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF  { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF  { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF  { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF  { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return_simple '}' ENDIF { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); } |
    IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return_simple '}' ENDIF  { logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
;

sentencia_seleccion_compuesta_con_return_simple:
	sentencia_return |
	sentencia_seleccion_compuesta_con_return |
	DEFER sentencia_seleccion_compuesta_con_return
;

sentencia_seleccion_simple_con_return:
	IF condicion THEN sentencia_return ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN sentencia_seleccion_compuesta_con_return ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN sentencia_ejecutable_funcion ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_ejecutable_funcion ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE sentencia_ejecutable_funcion ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN sentencia_ejecutable_funcion ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN sentencia_ejecutable_funcion ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); } |
	IF condicion THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';' { logger.logSuccess("[Parser] Sentencia de seleccion detectada"); }
;

sentencia_when_con_return:
	WHEN condicion THEN '{' sentencia_funcion '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN condicion THEN '{' sentencia_funcion sentencia_return '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN condicion THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN condicion THEN '{' sentencia_funcion '}' { logger.logError("[Parser] Se esperaba ; al final de la sentencia when"); } |
	WHEN condicion THEN '{' sentencia_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaba ; al final de la sentencia when"); } |
	WHEN condicion THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' { logger.logError("[Parser] Se esperaba ; al final de la sentencia when"); } |
	WHEN condicion THEN '{' '}' ';' { logger.logError("[Parser] Se esperaban sentencias en la sentencia when"); } |
	WHEN condicion '{' sentencia_funcion '}' ';' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN condicion '{' sentencia_funcion sentencia_return '}' ';' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN condicion '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
;

sentencia_do_con_return:
	keyword_do bloque_sentencias_ejecutables_do_con_return UNTIL condicion ';' 
	{ 	logger.logSuccess("[Parser] Sentencia do until detectada"); 
		Terceto tercetoDo = instance.desapilarTerceto();
		int posPrimerTerceto = instance.desapilarPosicionTerceto();

		tercetoDo.setOperando2("[" + posPrimerTerceto + "]");

		ArrayList<Terceto> lista_breaks_do = instance.getListaTercetosBreakDo();
		Iterator<Terceto> it = lista_breaks_do.listIterator();

		while (it.hasNext()) {
			Terceto breakTerceto = it.next();
			breakTerceto.setOperando1("[" + instance.getTamanioListaTercetos() + "]");
		}
	
	} |
	sentencia_do_etiqueta ':' keyword_do bloque_sentencias_ejecutables_do_con_return UNTIL condicion ';'
	{ 
		logger.logSuccess("[Parser] Sentencia do until detectada");	} |
	keyword_do bloque_sentencias_ejecutables_do_con_return UNTIL condicion { logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); } |
	sentencia_do_etiqueta ':' keyword_do bloque_sentencias_ejecutables_do_con_return UNTIL condicion { logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
;

bloque_sentencias_ejecutables_do_con_return:
	sentencia_return |
	sentencia_seleccion_compuesta_con_return |
	sentencias_ejecutables_do_funcion |
	'{' sentencias_ejecutables_do_funcion sentencia_return '}' |
	'{' sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return '}' |
	'{' sentencias_ejecutables_do_funcion '}' |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias en la sentencia do"); } |
	'{' sentencias_ejecutables_do_funcion sentencia_return { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	sentencias_ejecutables_do_funcion sentencia_return '}' { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	'{' sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return '}' { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	'{' sentencias_ejecutables_do_funcion { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	sentencias_ejecutables_do_funcion '}' { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); }
;

sentencias_ejecutables_do_funcion:
	sentencia_ejecutable_do_funcion |
	sentencias_ejecutables_do_funcion sentencia_ejecutable_do_funcion
;

sentencia_ejecutable_do_funcion:
	sentencia_ejecutable_funcion |
	sentencia_break |
	sentencia_continue
;

encabezado_funcion:
	FUN ID '(' ')' ':' tipo {
		instance.agregarUsoAIdentificador($2.obj[0], "nombre_funcion");
		instance.setTipo($2.obj[0], $6.obj[0]);
		instance.nuevoAmbito($2.obj[0]);
		instance.agregarTerceto(new Terceto("FUNCION", $2.obj[0], "-"));
	} | 
	FUN ID '(' lista_de_parametros ')' ':' tipo {
		instance.agregarUsoAIdentificador($2.obj[0], "nombre_funcion");
		instance.setTipo($2.obj[0], $7.obj[0]);
	if (ts.has($2.obj[0] + instance.getAmbito().toString())) {
		logger.logError("[Generacion de codigo] La funcion " + $2.obj[0] + " ya fue declarada");
	}
	else{
		instance.agregarTerceto(new Terceto("FUNCION", $2.obj[0], "-"));
		ts.swapLexemas($2.obj[0], $2.obj[0] + instance.getAmbito().toString());
		instance.nuevoAmbito($2.obj[0]);
		instance.agregarAmbitoParametros();
	}
	} |
	FUN ID '(' lista_de_parametros ')' tipo { logger.logError("[Parser] Se esperaba un : antes del tipo para la funcion"); } |
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
	tipo ID {
		instance.agregarParametro($2.obj[0], $1.obj[0]);
	} |
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
	ID ASIGNACION CTE{
		

	}|
	ID ASIGNACION { logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); } |
	ID CTE { logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); } |
	ID { logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); } |
	ID '=' CTE
;

sentencia_ejecutable:
	sentencia_ejecutable_simple |
	keyword_defer sentencia_ejecutable_simple {
		instance.setApilarTercetoDefer(false);
	}
;

keyword_defer:
	DEFER {
		instance.setApilarTercetoDefer(true);
	}
;

sentencia_ejecutable_simple:
	asignacion |
	seleccion |
	imprimir |
	sentencia_when |
	sentencia_do
;

sentencia_ejecutable_do:
	sentencia_ejecutable |
	sentencia_break |
	sentencia_continue
;

sentencia_break:
	BREAK ';' { 
		logger.logSuccess("[Parser] Sentencia break detectada"); 
		Terceto tercetoBreak = new Terceto("BI", "-", "-");

		instance.agregarTercetoBreakAListaTercetosBreakDo(tercetoBreak);
		instance.agregarTerceto(tercetoBreak);
	} |
	BREAK ':' etiqueta ';' { 
		logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); 

		Terceto tercetoBreak = new Terceto("BI", "-", "-");

		instance.agregarTerceto(tercetoBreak);
		instance.agregarTercetoBreakAEtiquetaDo($3.obj[0], tercetoBreak);
	}  |
	BREAK { logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); } |
	BREAK ':' etiqueta { logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); } |
	BREAK ':' ';' { logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); } 
;

sentencia_continue:
	CONTINUE ';' { 
		logger.logSuccess("[Parser] Sentencia continue detectada"); 

		int posPrimeraInstruccionDo = instance.getUltimaPosicionTerceto();

		Terceto tercetoContinue = new Terceto("BI", "[" + String.valueOf(posPrimeraInstruccionDo) + "]", "-");
		instance.agregarTerceto(tercetoContinue);
	} |
	CONTINUE { logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
;

sentencia_do:
	sentencia_do_simple |
	sentencia_do_etiqueta ':' sentencia_do_simple {
		ArrayList<Terceto> tercetos_etiqueta_break = instance.getTercetosBreakDeEtiquetaDo($1.obj[0]);
		Iterator<Terceto> it = tercetos_etiqueta_break.listIterator();

		while(it.hasNext()) {
			Terceto tercetoBreakConEtiqueta = it.next();
			tercetoBreakConEtiqueta.setOperando1("[" + instance.getTamanioListaTercetos() + "]");
		}

		instance.borrarEtiquetaDo($1.obj[0]);
	}
;

sentencia_do_etiqueta:
	etiqueta {
		instance.agregarDoConEtiqueta($1.obj[0]);
		$$.obj[0] = $1.obj[0];
	}
;

sentencia_do_simple:
	keyword_do bloque_sentencias_ejecutables_do UNTIL condicion ';' { 
		logger.logSuccess("[Parser] Sentencia do until detectada"); 
		Terceto tercetoDo = instance.desapilarTerceto();
		int posPrimerTerceto = instance.desapilarPosicionTerceto();

		tercetoDo.setOperando2("[" + posPrimerTerceto + "]");

		ArrayList<Terceto> lista_breaks_do = instance.getListaTercetosBreakDo();
		Iterator<Terceto> it = lista_breaks_do.listIterator();

		while (it.hasNext()) {
			Terceto breakTerceto = it.next();
			breakTerceto.setOperando1("[" + instance.getTamanioListaTercetos() + "]");
		}
	} |
	keyword_do bloque_sentencias_ejecutables_do UNTIL condicion { logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
;

keyword_do: 
	DO {
		instance.apilarPosicionTerceto(instance.getTamanioListaTercetos());
		instance.iniciarListaTercetosBreakDo();
	}
;

etiqueta:
	ID {
		instance.agregarUsoAIdentificador($1.obj[0], "nombre_etiqueta");
	}
;

bloque_sentencias_ejecutables_do:
	sentencia_ejecutable_do |	
	'{' sentencias_ejecutables_do '}' |
	'{' sentencias_ejecutables_do  { logger.logError("[Parser] Se esperaba un } al final de la sentencia do"); } |
	sentencias_ejecutables_do '}' { logger.logError("[Parser] Se esperaba un { en la sentencia do"); }
;

sentencias_ejecutables_do:
	sentencia_ejecutable_do |
	sentencias_ejecutables_do sentencia_ejecutable_do
;

asignacion:
	ID ASIGNACION expresion ';'
	{ 
		logger.logSuccess("[Parser] Asignacion detectada"); 

		String[] par = ts.getTipo($1.obj[0]);
		$1.obj[0] = par[0];
		$1.obj[1] = par[1];

		if (!$3.obj[0].contains("[") && ts.getToken($3.obj[0]) != 258){
			String[] par = ts.getTipo($3.obj[0]);
			$3.obj[0] = par[0];
			$3.obj[1] = par[1];
		}

		Terceto terceto = new Terceto(":=", $1.obj[0], $3.obj[0]);

		if ($1.obj[1] == null)
			logger.logError("[Generacion de Codigo] La variable " + $1.obj[0] + " No fue declarada" );
		else if ($3.obj[1] == null)
			logger.logError("[Generacion de Codigo] La variable " + $3.obj[0] + " No fue declarada" );
		else if ($1.obj[1].contains("ui16") && $3.obj[1].contains("ui16")){
			instance.agregarTerceto(terceto);
			$$.obj[1] = "ui16";
		}
		else if ($1.obj[1].contains("f64") && $3.obj[1].contains("f64")){
			instance.agregarTerceto(terceto);
			$$.obj[1] = "f64";
		}
		else if ($1.obj[1].contains("ui16") && $3.obj[1].contains("f64")){
			logger.logError("[Generacion de codigo] Incompatibilidad de tipos");
		}
		else if ($1.obj[1].contains("f64") && $3.obj[1].contains("ui16")){
			Terceto aux = new Terceto("itof", $3.obj[0], "-");
			terceto.setOperando2("[" + String.valueOf(instance.getTamanioListaTercetos() + "]"));
			instance.agregarTerceto(aux);
			instance.agregarTerceto(terceto);
			$$.obj[1] = "f64";
		}
		$$.obj[0] = $1.obj[0];
	} |
	ID ASIGNACION ';' {logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");} |
	ID ASIGNACION expresion { logger.logError("[Parser] Se espera un ; al final de la asignacion"); } |
	ID '=' expresion ';' { logger.logError("[Parser] Se espera el simbolo =: en lugar de = para la asignacion"); } |
	ID '=' expresion { logger.logError("[Parser] Se espera el simbolo =: en lugar de = para la asignacion"); }
; 

sentencia_when:
	WHEN condicion THEN '{' sentencias_when '}' ';' { logger.logSuccess("[Parser] Sentencia when detectada"); } |
	WHEN condicion '{' sentencias_when '}' ';' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN condicion THEN '{' sentencias_when '}' { logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); } |
	WHEN condicion '{' sentencias_when '}' { logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); } |
	WHEN condicion '{' '}' { logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
;

sentencias_when:
	sentencia |
	sentencia sentencias_when
;

seleccion:
	IF condicion THEN bloque_sentencias_ejecutables_seleccion_then ENDIF ';'
	{ 
		logger.logSuccess("[Parser] Sentencia if then detectada");

		Terceto bi = instance.desapilarTerceto();
		bi.setOperando1("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
	} |
	IF condicion THEN bloque_sentencias_ejecutables_seleccion_then ELSE bloque_sentencias_ejecutables_seleccion_else ENDIF ';' { 
		logger.logSuccess("[Parser] Sentencia if then else detectada"); 
		Terceto bi = instance.desapilarTerceto();
		bi.setOperando1("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
	} |
	IF condicion THEN bloque_sentencias_ejecutables_seleccion_then ENDIF { logger.logError("[Parser] Se esperaba un ; al final de la sentencia seleccion"); } |
	IF condicion THEN bloque_sentencias_ejecutables_seleccion_then ELSE bloque_sentencias_ejecutables_seleccion_else ENDIF { logger.logError("[Parser] Se esperaba un ; al final de la sentencia seleccion"); } |
	IF condicion THEN ENDIF ';' { logger.logError("[Parser] Se esperaban sentencias en la sentencia seleccion"); } |
	IF condicion bloque_sentencias_ejecutables_seleccion_then ENDIF ';' { logger.logError("[Parser] Se esperaban la palabra reservada then en la sentencia seleccion"); } |
	IF condicion bloque_sentencias_ejecutables_seleccion_then ELSE bloque_sentencias_ejecutables_seleccion_else ENDIF ';' { logger.logError("[Parser] Se esperaban la palabra reservada then en la sentencia seleccion"); } |
	IF condicion THEN ELSE ENDIF ';' { logger.logError("[Parser] Se esperaban sentencias en la sentencia seleccion"); } 
;

bloque_sentencias_ejecutables_seleccion:
	sentencia_ejecutable |	
	'{' sentencias_ejecutables '}' |
	'{' '}' { logger.logError("[Parser] Se esperaban sentencias dentro de la seleccion"); } |
	'{' sentencias_ejecutables { logger.logError("[Parser] Se esperaba un } al final de la seleccion"); } |
	sentencias_ejecutables '}' { logger.logError("[Parser] Se esperaba un { en la seleccion"); }
;

sentencias_ejecutables:
	sentencia_ejecutable |
	sentencias_ejecutables sentencia_ejecutable
;

bloque_sentencias_ejecutables_seleccion_then:
	bloque_sentencias_ejecutables_seleccion {


		Terceto t = instance.desapilarTerceto();

		t.setOperando2("[" + String.valueOf(instance.getTamanioListaTercetos() + 1) + "]");

		Terceto bi = new Terceto("BI", "-", "-");

		instance.agregarTerceto(bi);
		instance.apilarTerceto(bi);
	}
;

bloque_sentencias_ejecutables_seleccion_else:
	bloque_sentencias_ejecutables_seleccion
;

condicion:
	'(' expresion comparador expresion ')'
	{
		if (!$2.obj[0].contains("[")){
			String[] par = ts.getTipo($2.obj[0]);
			$2.obj[0] = par[0];
			$2.obj[1] = par[1];
		}

		if (!$4.obj[0].contains("[")){
			String[] par1 = ts.getTipo($4.obj[0]);
			$4.obj[0] = par1[0];
			$4.obj[1] = par1[1];
		}

		System.out.println("asdasdas" + $2.obj[0] + " " + $4.obj[0]);

		int tercetoPosicion = 0;

		Terceto terceto = new Terceto($3.obj[0], $2.obj[0], $4.obj[0]);

		tercetoPosicion = instance.getTamanioListaTercetos();

		instance.agregarTerceto(terceto);
		
		$$.obj[0] = "[" + tercetoPosicion + "]";

		terceto = new Terceto("BF", "[" + tercetoPosicion + "]", "-");

		instance.agregarTerceto(terceto);
		instance.apilarTerceto(terceto);

		$$.obj[0] = "[" + tercetoPosicion + "]";
	} |
	'(' expresion comparador ')' { logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); } |
	'(' comparador expresion ')' { logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); } |
	expresion comparador expresion ')' { logger.logError("[Parser] Se esperaba un ( al comienzo de la comparacion"); } |
	'(' expresion comparador expresion { logger.logError("[Parser] Se esperaba un ) al final de la comparacion"); } |
	'(' ')' { logger.logError("[Parser] Se esperaba una comparacion"); }
;

comparador:
	COMP_MAYOR_IGUAL {$$.obj[0] = ">=";}|
	COMP_MENOR_IGUAL {$$.obj[0] = "<=";}|
	COMP_DISTINTO {$$.obj[0] = "=!";}|
	'>' {$$.obj[0] = ">";}|
	'<' {$$.obj[0] = "<";}|
	'=' {$$.obj[0] = "=";}
;

expresion:
	expresion '+' termino {

		if (!$1.obj[0].contains("[") && ts.getToken($1.obj[0]) != 258){
			String[] par = ts.getTipo($1.obj[0]);
			$1.obj[0] = par[0];
			$1.obj[1] = par[1];
		}
		if (!$3.obj[0].contains("[") && ts.getToken($3.obj[0]) != 258){
			String[] par = ts.getTipo($3.obj[0]);
			$3.obj[0] = par[0];
			$3.obj[1] = par[1];
		}

		$$.obj = instance.AgregarTercetoExpresiones($$.obj[0], $$.obj[1], $1.obj[0], $1.obj[1], $3.obj[0], $3.obj[1], "+");

	} |
	expresion '-' termino {

		if (!$1.obj[0].contains("[") && ts.getToken($1.obj[0]) != 258){
			String[] par = ts.getTipo($1.obj[0]);
			$1.obj[0] = par[0];
			$1.obj[1] = par[1];
		}
		if (!$3.obj[0].contains("[") && ts.getToken($3.obj[0]) != 258){
			String[] par = ts.getTipo($3.obj[0]);
			$3.obj[0] = par[0];
			$3.obj[1] = par[1];
		}


		$$.obj = instance.AgregarTercetoExpresiones($$.obj[0], $$.obj[1], $1.obj[0], $1.obj[1], $3.obj[0], $3.obj[1], "-");
	} |
	termino {
		
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = $1.obj[1];
	}
;

termino:
	termino '*' factor {

		if (!$1.obj[0].contains("[") && ts.getToken($1.obj[0]) != 258){
			String[] par = ts.getTipo($1.obj[0]);
			$1.obj[0] = par[0];
			$1.obj[1] = par[1];
		}
		if (!$3.obj[0].contains("[") && ts.getToken($3.obj[0]) != 258){
			String[] par = ts.getTipo($3.obj[0]);
			$3.obj[0] = par[0];
			$3.obj[1] = par[1];
		}

		$$.obj = instance.AgregarTercetoExpresiones($$.obj[0], $$.obj[1], $1.obj[0], $1.obj[1], $3.obj[0], $3.obj[1], "*");

	} |
	termino '/' factor {

		if (!$1.obj[0].contains("[") && ts.getToken($1.obj[0]) != 258){
			String[] par = ts.getTipo($1.obj[0]);
			$1.obj[0] = par[0];
			$1.obj[1] = par[1];
		}
		if (!$3.obj[0].contains("[") && ts.getToken($3.obj[0]) != 258){
			String[] par = ts.getTipo($3.obj[0]);
			$3.obj[0] = par[0];
			$3.obj[1] = par[1];
		}

		$$.obj = instance.AgregarTercetoExpresiones($$.obj[0], $$.obj[1], $1.obj[0], $1.obj[1], $3.obj[0], $3.obj[1], "/");
	} |
	factor {
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = $1.obj[1];
	}
;

factor:
	ID {
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = ts.getTipo($1.obj[0])[1];
	} |
	constante {
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = ts.getTipoCte($1.obj[0]);
	} |
	invocacion_funcion{
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = ts.getTipo($1.obj[0])[1];
	}
;

invocacion_funcion:
	ID '(' ')' {
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = ts.getTipo($1.obj[0])[1];
	}|
	ID '(' lista_de_parametros_reales ')' {
		$$.obj[0] = $1.obj[0];
		$$.obj[1] = ts.getTipo($1.obj[0])[1];
	}
;

lista_de_parametros_reales:
	parametro_real |
	lista_de_parametros_reales ',' parametro_real
;

parametro_real:
	ID {
		instance.addCheckParametros(ts.getTipo($1.obj[0])[0]);
	}|
	constante{
		instance.addCheckParametros(ts.getTipo2($1.obj[0]));
	}
;

imprimir:
	OUT '(' CADENA ')' ';' { 
		logger.logSuccess("[Parser] Sentencia out detectada"); 

		Terceto out = new Terceto("out", $3.obj[0], "-");

		if (instance.debeApilarTercetoDefer()) {
			instance.agregarTercetoParaDeferAmbitoActual(out);
		} else {
			instance.agregarTerceto(out);
		}
	} |
	OUT '(' ID ')' ';' { 
		logger.logSuccess("[Parser] Sentencia out detectada"); 

		Terceto out = new Terceto("out", $3.obj[0], "-");

		if (instance.debeApilarTercetoDefer()) {
			instance.agregarTercetoParaDeferAmbitoActual(out);
		} else {
			instance.agregarTerceto(out);
		}
	} |
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
	'-' CTE { constanteConSigno($2.obj[0]); }
;
	
tipo:
	UINT16 {
		$$.obj[1] = "ui16";
	}|
	DOUBLE64{
		$$.obj[1] = "f64";
	}
;

%%

public static AnalizadorLexico lexico = null;
public static GeneracionCodigoIntermedio instance = GeneracionCodigoIntermedio.getInstance();
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;

public static StringBuilder negConstante = new StringBuilder();

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {

		negConstante.setLength(0);
		negConstante.append("-");
		negConstante.append(constante);
		
		Double parsedDouble = Double.parseDouble(negConstante.toString().replace('D', 'E'));
		
		if (parsedDouble < -2.2250738585072014E-308 && -1.7976931348623157E+308 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-1.7976931348623157E+308 < parsedDouble) {
				negConstante.setLength(0);
				negConstante.append("-1.7976931348623157D+308");
			} else {
				negConstante.setLength(0);
				negConstante.append("-2.2250738585072014D-308");
			}
		}
		
		ts.swapLexemas(constante, negConstante.toString());
	} else {
		//se recibio un uint que fue aceptado por el lexico pero resulta ser negativo
		logger.logWarning("[Parser] No se admiten ui16 con obj[0]es negativos: " + "-"+constante + ", se trunca a 0");
		
		ts.swapLexemas(constante, "0");
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
	        
	        String printTs = ts.print();
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintetico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", printTs);
	        
			System.out.println(printTs);

			GeneracionCodigoIntermedio instance = GeneracionCodigoIntermedio.getInstance();

			instance.printTercetos();
		}
	}
}