package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Accion semantica para error handling
 *
 */
public class ASE extends AccionSemantica {

	public ASE(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		// TODO Auto-generated method stub
		return 0;
	}



}
