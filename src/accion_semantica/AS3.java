package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Leer caracter y concatenar a un lexema que se fue leyendo
 *
 */
public class AS3 extends AccionSemantica {

	public AS3(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);

		return -1;
	}
}
