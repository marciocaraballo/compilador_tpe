package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Accion semantica para error handling\
 */
public class ASE extends AccionSemantica {

	public ASE(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}
	
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		int nextCharInt = 0;
		String aux = "";
		if (lexema.length() != 0)
			aux = lexema.substring(lexema.length() - 1);
		else
			aux = String.valueOf(nextCharacter);
		
		if (aux.equals(".")) {
			
			logger.logWarning("Caracter invalido, luego del punto debe seguir un digito. Se descartaran los proximos"
							 + " Caracteres hasta encontrar un digito");
			
			while (!Character.isDigit(nextCharacter)) {
				nextCharInt = fileHelper.nextChar();
				nextCharacter = (char)nextCharInt;
			}	
			
		}
		
		else if (aux.equals("+")|| aux.equals("-")) {
			System.out.println("Luego de + o - debe continuar un digito");
			
			while (!Character.isDigit(nextCharacter)) {
				nextCharInt = fileHelper.nextChar();
				nextCharacter = (char)nextCharInt;
			}
		}
		
		else if (aux.equals("D")) {
			
			logger.logWarning("Luego de una \"D\" debe seguir un digito, un \"+\" o un \"-\""
					 + " Se descartaran los proximos Caracteres hasta encontrar un digito");
			
			while (nextCharacter != '+' && nextCharacter != '-' && !Character.isDigit(nextCharacter)) {
				nextCharInt = fileHelper.nextChar();
				nextCharacter = (char)nextCharInt;
			}
		}
		
		else if (nextCharacter == '_') {
			logger.logWarning("Caracter Invalido , el simbolo '_' solo puede estar dentro "
							 + "de un identificador o una cadena de caracteres, se descartara el caracter");
			
			nextCharInt = fileHelper.nextChar();
			nextCharacter = (char)nextCharInt;
		}
		
		else if (nextCharacter == '!' || nextCharacter == ':') {
			
			logger.logWarning("Los caracteres \"!\" y \":\" solo pueden estar "
							 + "despues de \"=\", o dentro de una cadena, se descartara el caracter");
			
			nextCharInt = fileHelper.nextChar();
			nextCharacter = (char)nextCharInt;
		}
		
		else if (nextCharacter == '\n') {
			logger.logWarning("Cadena de caracteres no puede contener saltos de linea");
		}
		
		else
			logger.logWarning("Caracter no definido");

		return -1;
	}
}
