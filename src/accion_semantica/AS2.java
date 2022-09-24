package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Inicializar string, y añadir caracter leido
 *
 */
public class AS2 extends AccionSemantica {

	public AS2(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.setLength(0);
		lexema.append(nextCharacter);

		return -1;		
	}
}
