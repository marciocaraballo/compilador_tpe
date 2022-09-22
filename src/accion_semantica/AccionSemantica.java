package accion_semantica;

import java.io.BufferedReader;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public abstract class AccionSemantica {
	
	TablaPalabrasReservadas TPR = null;
	TablaDeSimbolos TS = null;
	
	public AccionSemantica(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		this.TPR = TPR;
		this.TS = TS;
	}

	public abstract int ejecutar(BufferedReader reader, StringBuilder lexema, char inputCaracter);
}
