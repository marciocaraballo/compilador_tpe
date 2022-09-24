package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * 
 * Descarta los caracteres que no son tenidos en cuenta (Espacios en blanco, Tabulaciones, Saltos de Linea, Comentarios)
 *
 */

public class AS9 extends AccionSemantica {

	public AS9(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.setLength(0); 
		
		return -1;
	}
}
