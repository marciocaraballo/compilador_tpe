package accion_semantica;

import java.io.BufferedReader;

import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * -Devolver a la entrada el último carácter leído 
	-Verificar tamaño de cadena
-Buscar en la TPR: 
 - Si está, devolver token de PR 
 - Si no está: 
-Buscar en la TS:
 -Si está, devolver ID + Punt TS 
 -Si no está: 
-Alta en TS 
-Devolver ID + Punt TS

 *
 *	ID es token 100
 *
 */
public class AS4 extends AccionSemantica {

	public AS4(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		if (lexema.length() > 25) {
			logger.logWarning("Has superado la cantidad maxima de caracteres para un identificador(25), se eliminaran los"
					+  " caracteres que estan mas alla de la posicion 25 ");
			//System.out.println(lexema.substring(0, 25));
			lexema.setLength(25);
		}
		
		// ver si es palabra reservada
		if (TPR.getToken(lexema.toString()) != -1) {
			return TPR.getToken(lexema.toString());
		} else {
			//ver si esta en TS
			if (TS.has(lexema.toString())) {
				return TS.getToken(lexema.toString());
			} else {
				// no esta -> se agrega identificador en TS
				TS.putIdentificador(lexema.toString());
				return TS.getToken(lexema.toString());
			}
		}
	}
}
