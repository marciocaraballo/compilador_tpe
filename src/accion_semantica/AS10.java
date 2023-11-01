package accion_semantica;

import compilador.Constantes;
import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;

public class AS10 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

		lexema.append(nextCharacter);

		String cadena = lexema
				.toString()
				.replaceAll("\n", "")
				.replaceAll("\r", "");

		/** Dejar el lexema con el string final que va a la TS */
		lexema.setLength(0);
		lexema.append(cadena);

		if (TS.has(cadena))
			return (int) TS.getAtributo(cadena, Constantes.TOKEN);
		else {
			TS.putLexema(cadena, Constantes.CADENA);
			return (int) TS.getAtributo(cadena, Constantes.TOKEN);
		}
	}
}
