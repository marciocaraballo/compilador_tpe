package accion_semantica;

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

	public AS4(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		//nextCharacter no se usaria aca, devolver a la entrada?
		
		if (lexema.length() > 25) {
			//Warning?
		}
		
		// ver si es palabra reservada
		if (TPR.getToken(lexema.toString()) != TPR.NO_ENCONTRADO) {
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
