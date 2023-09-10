package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaPalabrasReservadas;

public class AS11 extends AccionSemantica {

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		TablaPalabrasReservadas TPR = TablaPalabrasReservadas.getInstance();
		Logger logger = Logger.getInstance();

		fileHelper.reset();

		if (TPR.getToken(lexema.toString()) != -1) {
			return TPR.getToken(lexema.toString());
		} else {
			logger.logError("[Lexico] Lexema no reconocido: " + lexema.toString() + ", se descarta.");
			lexema.setLength(0);

			return -1;
		}
	}
}
