package accion_semantica;

import java.io.BufferedReader;

import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public abstract class AccionSemantica {
	
	TablaPalabrasReservadas TPR = null;
	TablaDeSimbolos TS = null;
	Logger logger = null;
	
	public AccionSemantica(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		this.TPR = TPR;
		this.TS = TS;
	}
	
	public AccionSemantica(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger lgr) {
		this.TPR = TPR;
		this.TS = TS;
		this.logger = lgr;
	}

	public abstract int ejecutar(BufferedReader reader, StringBuilder lexema, char inputCaracter);
}
