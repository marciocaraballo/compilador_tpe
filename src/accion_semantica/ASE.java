package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Accion semantica para error handling
 * En caso de detectar un error, el token reconocido hasta el momento se
 * descarta y se informara por pantalla. Luego, se podra seguir 
 * leyendo caracteres y reconociendo errores
 */
public class ASE extends AccionSemantica {

	public ASE(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}
	
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);
		logger.logError("Lexema no reconocido: " + lexema.toString() + ", se descarta.");
		lexema.setLength(0);
		
		return -1;
	}
}
