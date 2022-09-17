package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * 
 * Concatena caracter leido con el lexema actual y devuelve el TOKEN
 *
 */

public class AS7 extends AccionSemantica {

	public AS7(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		lexema.append(nextCharacter);
		return TPR.getToken(lexema.toString());
		
	}



}
