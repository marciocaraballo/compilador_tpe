package accion_semantica;

import compilador.Constantes;
import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS4 extends AccionSemantica {

	public static final int MAX_CHARS = 20;

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();

		fileHelper.reset();

		if (lexema.length() > MAX_CHARS) {
			logger.logWarning("Se ha superado la cantidad maxima de caracteres para un identificador (" + MAX_CHARS
					+ "), se eliminaran los"
					+ " caracteres que estan mas alla de la posicion " + MAX_CHARS);
			lexema.setLength(MAX_CHARS);
		}

		if (TS.has(lexema.toString())) {
			return (int) TS.getAtributo(lexema.toString(), Constantes.TOKEN);
		} else {
			TS.putLexema(lexema.toString(), Constantes.IDENTIFICADOR);
			return (int) TS.getAtributo(lexema.toString(), Constantes.TOKEN);
		}
	}
}
