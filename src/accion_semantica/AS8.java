package accion_semantica;

import compilador.Constantes;
import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS8 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		String formattedDouble = lexema.toString();

		Double parsedDouble = Double.parseDouble(formattedDouble);

		fileHelper.reset();

		if (parsedDouble != 0 && (parsedDouble > 3.40282347E+38 || parsedDouble < 1.17549435E-38)) {
			logger.logWarning("[Lexico] Rango invalido para la constante: " + lexema.toString()
					+ ", se trunca al rango permitido");
			lexema.setLength(0);
			// double es mayor que el maximo permitido
			if (parsedDouble > 3.40282347E+38) {
				lexema.append("3.40282347E+38");
			} else {
				// double es menor que el minimo permitido
				lexema.append("1.17549435E-38");
			}
		}

		if (TS.has(lexema.toString())) {
			return (int) TS.getAtributo(lexema.toString(), Constantes.TOKEN);
		} else {
			TS.putLexema(lexema.toString(), Constantes.CONSTANTE);
			TS.agregarAtributo(lexema.toString(), Constantes.TYPE, "FLOAT");
			return (int) TS.getAtributo(lexema.toString(), Constantes.TOKEN);
		}
	}
}
