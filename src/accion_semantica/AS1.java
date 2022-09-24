package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Reconocer literal y devuelve el token.
 * 
 * Ejemplo: Para un '+' devuelve el ascii asociado
 *
 */
public class AS1 extends AccionSemantica {
	public AS1(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		lexema.setLength(0);
		lexema.append(nextCharacter);
		
		return (int)nextCharacter;
	}
}
