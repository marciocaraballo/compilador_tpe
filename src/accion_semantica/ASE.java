package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;

public class ASE extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		Logger logger = Logger.getInstance();
		
		lexema.append(nextCharacter);
		logger.logError("[Lexico] Lexema no reconocido: " + lexema.toString() + ", se descarta.");
		lexema.setLength(0);
		
		return -1;
	}
}
