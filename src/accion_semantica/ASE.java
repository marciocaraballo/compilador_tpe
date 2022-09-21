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
		
		System.out.println();
		
		if (lexema.substring(lexema.length() - 1).equals(".")) {
			System.out.println("Caracter invalido, luego del punto debe seguir un digito");
		}
		
		if (lexema.substring(lexema.length()) == "+" || lexema.substring(lexema.length()) == "-") {
			System.out.println("Luego de + o - debe continuar un digito");
		}
		
		if (lexema.substring(lexema.length()) == "D") {
			System.out.println("Luego de una \"D\" debe seguir un digito, un \"+\" o un \"-\"");
		}
		
		if (nextCharacter == '_') {
			System.out.println("Caracter Invalido , el simbolo '_' solo puede estar dentro "
							 + "de un identificador o una cadena de caracteres");
		}
		
		if (nextCharacter == '!' || nextCharacter == ':') {
			System.out.println("Los caracteres \"!\" y \":\" solo pueden estar "
							 + "despues de \"=\", o dentro de una cadena ");
		}
		
		if (nextCharacter == '\n') {
			System.out.println("Cadena de caracteres no puede contener saltos de linea");
		}
		
		System.out.println("Caracter no definido");
		
		System.exit(0);

		return 0;
	}
}
