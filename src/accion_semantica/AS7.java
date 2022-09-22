package accion_semantica;

import java.io.BufferedReader;

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
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {	
		
		return (int) lexema.charAt(0);
		
	}
}
