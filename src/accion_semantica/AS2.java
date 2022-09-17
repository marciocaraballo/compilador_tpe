package accion_semantica;

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
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		lexema.setLength(0);
		lexema.append(nextCharacter);
		
		return -1;		
	}
}
