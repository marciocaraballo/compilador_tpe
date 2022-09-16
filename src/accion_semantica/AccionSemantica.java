package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public interface AccionSemantica {
	
	TablaPalabrasReservadas TPR = new TablaPalabrasReservadas();
	TablaDeSimbolos TS = new TablaDeSimbolos();

	public abstract int ejecutar(char nextCharacter, String lexema);

}
