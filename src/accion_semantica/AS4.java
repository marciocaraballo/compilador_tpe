package accion_semantica;

import compilador.FileReaderHelper;
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
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		if (lexema.length() > 25) {
			logger.logWarning("Has superado la cantidad maxima de caracteres para un identificador(25), se eliminaran los"
					+  " caracteres que estan mas alla de la posicion 25 ");
			lexema.setLength(25);
		}
		
		//Devuelve el input a la entrada
		fileHelper.reset();
		
		if (TPR.getToken(lexema.toString()) != -1) {
			return TPR.getToken(lexema.toString());
		} else {
			if (TS.has(lexema.toString())) {
				return TS.getToken(lexema.toString());
			} else {
				TS.putIdentificador(lexema.toString());
				return TS.getToken(lexema.toString());
			}
		}
	}
}
