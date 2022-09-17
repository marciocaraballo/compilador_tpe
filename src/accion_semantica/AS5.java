package accion_semantica;

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

	public AS5(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		int cte = Integer.parseInt(lexema.toString());
		
		if (cte < 0 && cte > (Math.pow(2, 16) - 1)) {
			// Devolvemos error?
		}
		
		// Si existe lexema dentro de TS devolvemos el token
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		}
		else {
			
		// Si no existe dentro de TS, lo agregamos
			TS.putConstante(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
}
