package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Accion semantica para error handling
 *
 */
public class ASE extends AccionSemantica {

	public ASE(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		if (nextCharacter == '_') {
			// Caracter Invalido , el simbolo "_" solo puede estar dentro de un identificador o una cadena de caracteres
		}
		
		if (nextCharacter == '!' || nextCharacter == ':') {
			// Los caracteres "!" y ":" solo pueden estar despues de "=", o dentro de una cadena
		}
		
		if (nextCharacter == '\n') {
			// Cadena de caracteres no puede tener salto de linea
		}
		
		if (lexema.substring(lexema.length()) == ".") {
			// Caracter invalido, luego del punto debe seguir un digito
		}
		
		if (lexema.substring(lexema.length()) == "+" || lexema.substring(lexema.length()) == "-") {
			// Luego de + o - debe seguir un digito
		}
		
		if (lexema.substring(lexema.length()) == "D") {
			// Luego de una "D" debe seguir un digito, un "+" o un "-"
		}
		
		
		
		
		
		return -1;
	}



}
