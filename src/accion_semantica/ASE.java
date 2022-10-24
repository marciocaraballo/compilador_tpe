package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;

public class ASE extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		Logger logger = Logger.getInstance();	
		/**
		 * El . es un caso particular, hay error en caso de que nada 
		 * que venga despues sea un numero. Si eso sucede, se cae al error
		 * y hay que devolver el caracter leido a la entrada
		 */
		if (lexema.toString().equals(".")) {
			fileHelper.reset();
		} else {
			lexema.append(nextCharacter);
		}
		
		logger.logError("[Lexico] Lexema no reconocido: " + lexema.toString() + ", se descarta.");
		lexema.setLength(0);
		
		return -1;
	}
}
