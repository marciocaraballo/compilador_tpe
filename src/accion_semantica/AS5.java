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
		
		int cte = Integer.parseInt(lexema.toString());
		
		if (cte > (Math.pow(2, 16) - 1)) {
			logger.logError("[Lexico] Se supero el maximo valor para la constante: " + cte + ", se descarta" );
			
			lexema.setLength(0);
			
			return -1;
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
