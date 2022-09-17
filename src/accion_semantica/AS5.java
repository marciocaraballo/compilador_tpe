package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS5 extends AccionSemantica {

	public AS5(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		int cte = Integer.parseInt(lexema.toString());
		
		if (cte >= 0 && cte <= (Math.pow(2, 16) - 1)) {
			
			return 0;
		}
		
		else
			return -1;

	}

}
