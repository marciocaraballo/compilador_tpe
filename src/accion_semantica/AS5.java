package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS5 extends AccionSemantica {
	
	public static final int MAX_INT_VALUE = (int)(Math.pow(2, 15) - 1);
	public static final int MAX_INT_UNSIGNED_VALUE = (int)(Math.pow(2,32) - 1);

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		int cte = 0;
		boolean exceptionOutOfRange = false;
		
		/** Formatos: _i */
		if (lexema.toString().contains("_i")) {
			lexema.setLength(lexema.toString().length() - 2);
			
			try {
				cte = Integer.parseInt(lexema.toString());
			} catch(NumberFormatException e) {
				exceptionOutOfRange = true;
			}
			
			if (cte > MAX_INT_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString() + ", se trunca al rango permitido");
				
				lexema.setLength(0);
				lexema.append(MAX_INT_VALUE);
			}
		} else {
			/** Formatos: _ul */
			lexema.setLength(lexema.toString().length() - 3);
			
			try {
				cte = Integer.parseInt(lexema.toString());
			} catch(NumberFormatException e) {
				exceptionOutOfRange = true;
			}
			
			if (cte > MAX_INT_UNSIGNED_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString() + ", se trunca al rango permitido");
				
				lexema.setLength(0);
				lexema.append(MAX_INT_UNSIGNED_VALUE);
			}
		}
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		}
		else {
			TS.putConstante(lexema.toString());
			//TS.putTipo(lexema.toString(), "ui16");
			return TS.getToken(lexema.toString());
		}
	}
}
