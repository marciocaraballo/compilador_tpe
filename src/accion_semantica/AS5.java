package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Devuelve ultimo caracter leido a la entrada
 * Verifica rango de las constantes
 * Cheque si su lexema esta dentro de la TS
 * - Si esta devuelve el token asociado
 * - Si no esta:
 * 	 - Alta en TS
 * 	 - Devuelve Token
 * 
 */

public class AS5 extends AccionSemantica {

	public AS5(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		int cte = 0;
		boolean exeptionOutOfRange = false;
		
		try {
			cte = Integer.parseInt(lexema.toString());
		} catch(NumberFormatException e) {
			exeptionOutOfRange = true;
		}
		
		if (cte > 65535 || exeptionOutOfRange) {
			logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString() + ", se trunca al rango permitido");
			
			lexema.setLength(0);
			lexema.append("65535");
		}
		
		//Devuelve el input a la entrada
		fileHelper.reset();
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		}
		else {
			TS.putConstante(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
}
