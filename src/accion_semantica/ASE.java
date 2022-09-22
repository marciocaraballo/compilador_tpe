package accion_semantica;

import java.io.BufferedReader;

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
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		if (lexema.substring(lexema.length()).equals(".")) {
			System.out.println("Caracter invalido, luego del punto debe seguir un digito");
		}
		
		else if (lexema.substring(lexema.length()) == "+" || lexema.substring(lexema.length()) == "-") {
			System.out.println("Luego de + o - debe continuar un digito");
		}
		
		else if (lexema.substring(lexema.length()) == "D") {
			System.out.println("Luego de una \"D\" debe seguir un digito, un \"+\" o un \"-\"");
		}
		
		else if (nextCharacter == '_') {
			System.out.println("Caracter Invalido , el simbolo '_' solo puede estar dentro "
							 + "de un identificador o una cadena de caracteres");
		}
		
		else if (nextCharacter == '!' || nextCharacter == ':') {
			System.out.println("Los caracteres \"!\" y \":\" solo pueden estar "
							 + "despues de \"=\", o dentro de una cadena, se descartara el caracter");
		}
		
		else if (nextCharacter == '\n') {
			System.out.println("Cadena de caracteres no puede contener saltos de linea");
		}
		
		else
			System.out.println("Caracter no definido");
		
		//System.exit(0);

		return 0;
	}
}
