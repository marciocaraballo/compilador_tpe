package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Devuelve ultimo caracter leido a la entrada
 * Verifica rango de las constantes doubles
 * Cheque si su lexema esta dentro de la TS
 * - Si esta devuelve el token asociado
 * - Si no esta:
 * 	 - Alta en TS
 * 	 - Devuelve Token
 */
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
		
		if (!(parsedDouble == 0.0 ||
			(1.7976931348623157E+308 < parsedDouble && parsedDouble < 2.2250738585072014E-308) ||
			(-1.7976931348623157E+308 < parsedDouble && parsedDouble < -2.2250738585072014E-308)
			)) {

			logger.logError("Rango invalido para la constante: " + lexema.toString() + ", se descarta" );
			lexema.setLength(0);
			
			return -1;
		}
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		} else {
			TS.putConstante(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
}
