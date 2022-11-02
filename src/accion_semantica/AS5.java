package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS5 extends AccionSemantica {

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		int cte = 0;
		boolean exeptionOutOfRange = false;
		
		try {
			cte = Integer.parseInt(lexema.toString());
		} catch(NumberFormatException e) {
			exeptionOutOfRange = true;
		}
		
		if (cte > 65535 || exeptionOutOfRange) {
			logger.logWarning("[Lexico] Se supero el maximo valor para la constante: " + lexema.toString() + ", se trunca al rango permitido");
			
			lexema.setLength(0);
			lexema.append("65535");
		}
		
		fileHelper.reset();
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		}
		else {
			TS.putConstante(lexema.toString());
			TS.putTipo(lexema.toString(), "ui16");
			return TS.getToken(lexema.toString());
		}
	}
}
