package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public abstract class AccionSemantica {
	
	TablaPalabrasReservadas TPR = TablaPalabrasReservadas.getInstance();
	TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
	Logger logger = Logger.getInstance();
	
	public AccionSemantica() {}

	public abstract int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char inputCaracter);
}
