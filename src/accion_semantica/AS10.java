package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS10 extends AccionSemantica{

	public AS10(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		lexema.append(nextCharacter);
		if (TS.has(lexema.toString()))
			return TS.getToken(lexema.toString());
		else {
			TS.putCadena(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
	
	

}
