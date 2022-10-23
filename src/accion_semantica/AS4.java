package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS4 extends AccionSemantica {

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		TablaPalabrasReservadas TPR = TablaPalabrasReservadas.getInstance();
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		
		fileHelper.reset();
		
		if (TPR.getToken(lexema.toString()) != -1) {
			return TPR.getToken(lexema.toString());
		} else {
			if (lexema.length() > 25) {
				logger.logWarning("Se ha superado la cantidad maxima de caracteres para un identificador(25), se eliminaran los"
						+  " caracteres que estan mas alla de la posicion 25 ");
				lexema.setLength(25);
			}
			
			if (TS.has(lexema.toString())) {
				return TS.getToken(lexema.toString());
			} else {
				TS.putIdentificador(lexema.toString());
				return TS.getToken(lexema.toString());
			}
		}
	}
}
