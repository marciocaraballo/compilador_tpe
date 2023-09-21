package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS5 extends AccionSemantica {
	// El maximo positivo deberia ser 32767 pero se deja en 32768 para que luego el
	// sintactico resuelva el rango en el caso de que sea negativo (pueden
	// colisionar)
	public static final int MAX_INT_VALUE = (int) (Math.pow(2, 15));
	public static final long MAX_INT_UNSIGNED_VALUE = (long) (Math.pow(2, 32) - 1);

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		boolean exceptionOutOfRange = false;

		lexema.append(nextCharacter);

		String lexemaValue = lexema.toString().split("_")[0];

		/** Formatos: _i */
		if (lexema.toString().contains("_i")) {

			int cte = 0;

			try {
				cte = Integer.parseInt(lexemaValue);
			} catch (NumberFormatException e) {
				exceptionOutOfRange = true;
			}

			if (cte > MAX_INT_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString()
						+ ", se trunca valor " + MAX_INT_VALUE + "_i");

				lexema.setLength(0);
				lexema.append(MAX_INT_VALUE + "_i");
			}
		} else {
			/** Formatos: _ul */
			long cte = 0;

			try {
				cte = Long.parseLong(lexemaValue);
			} catch (NumberFormatException e) {
				exceptionOutOfRange = true;
			}

			if (cte > MAX_INT_UNSIGNED_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString()
						+ ", se trunca al valor " + MAX_INT_UNSIGNED_VALUE + "_ul");

				lexema.setLength(0);
				lexema.append(MAX_INT_UNSIGNED_VALUE + "_ul");
			}
		}

		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		} else {
			TS.putConstante(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
}
