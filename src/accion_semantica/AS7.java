package accion_semantica;

import compilador.FileReaderHelper;
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
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {	
		fileHelper.reset();
		return (int) lexema.charAt(0);
	}
}
