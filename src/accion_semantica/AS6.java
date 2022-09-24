package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * 
 * Devuelve el ultimo caracter leido a la entrada
 * 
 * Retorna el TOKEN asociado a la cadena
 *
 */

public class AS6 extends AccionSemantica {

	public AS6(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);
		
		return TPR.getToken(lexema.toString());
	}
}
