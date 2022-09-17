package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS9 extends AccionSemantica {

	public AS9(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		// TODO Auto-generated method stub
		return 0;
	}
}
