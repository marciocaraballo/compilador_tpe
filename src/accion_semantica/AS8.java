package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS8 extends AccionSemantica {

	public AS8(TablaPalabrasReservadas TPR, TablaDeSimbolos TS, Logger logger) {
		super(TPR, TS, logger);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		String formattedDouble = lexema.toString().replace('D', 'E');
		
		Double parsedDouble = Double.parseDouble(formattedDouble);
		
		//devolver char a la entrada
		fileHelper.reset();
		
		if (parsedDouble != 0 && (parsedDouble > 1.7976931348623157E+308 || parsedDouble < 2.2250738585072014E-308)) {
			logger.logWarning("[Lexico] Rango invalido para la constante: " + lexema.toString() + ", se trunca al rango permitido");
			lexema.setLength(0);
			//double es mayor que el maximo permitido
			if (parsedDouble > 1.7976931348623157E+308) {
				lexema.append("1.7976931348623157D+308");
			} else {
				//double es menor que el minimo permitido
				lexema.append("2.2250738585072014D-308");
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
