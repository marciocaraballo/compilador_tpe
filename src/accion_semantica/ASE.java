package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Accion semantica para error handling
 */
public class ASE extends AccionSemantica {

	public ASE(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}
	
	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		String aux = "";
		if (lexema.length() != 0)
			aux = lexema.substring(lexema.length() - 1);
		else
			aux = String.valueOf(nextCharacter);
		
		if (aux.equals(".")) {
			
			logger.logWarning("Caracter invalido, luego del punsto debe seguir un digito. Se descartaran los proximos"
							 + " Caracteres hasta encontrar un digito");
			
			while (!Character.isDigit(nextCharacter)) {
				try {
					nextCharacter = (char) reader.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			AnalizadorLexico.modifPos(nextCharacter);
			
		}
		
		else if (aux.equals("+")|| aux.equals("-")) {
			System.out.println("Luego de + o - debe continuar un digito");
			
			while (!Character.isDigit(nextCharacter)) {
				try {
					nextCharacter = (char) reader.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			AnalizadorLexico.modifPos(nextCharacter);
		}
		
		else if (aux.equals("D")) {
			
			logger.logWarning("Luego de una \"D\" debe seguir un digito, un \"+\" o un \"-\""
					 + " Se descartaran los proximos Caracteres hasta encontrar un digito");
			
			while (nextCharacter != '+' && nextCharacter != '-' && !Character.isDigit(nextCharacter)) {
				try {
					nextCharacter = (char) reader.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			AnalizadorLexico.modifPos(nextCharacter);
		}
		
		else if (nextCharacter == '_') {
			logger.logWarning("Caracter Invalido , el simbolo '_' solo puede estar dentro "
							 + "de un identificador o una cadena de caracteres, se descartara el caracter");
			
			try {
				nextCharacter = (char) reader.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AnalizadorLexico.modifPos(nextCharacter);
		}
		
		else if (nextCharacter == '!' || nextCharacter == ':') {
			
			logger.logWarning("Los caracteres \"!\" y \":\" solo pueden estar "
							 + "despues de \"=\", o dentro de una cadena, se descartara el caracter");
			
			try {
				nextCharacter = (char) reader.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AnalizadorLexico.modifPos(nextCharacter);
			
		}
		
		else if (nextCharacter == '\n') {
			logger.logWarning("Cadena de caracteres no puede contener saltos de linea");
			AnalizadorLexico.modifPos('\'');
		}
		
		else
			logger.logWarning("Caracter no definido");
		
		//System.exit(0);

		return -1;
	}
}
