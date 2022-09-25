package accion_semantica;

import compilador.FileReaderHelper;
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
 * 
 *
 */

public class AS8 extends AccionSemantica {

	public AS8(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {

		if (lexema.charAt(0) == '.') {
			lexema.insert(0, '0');
		}
		
		String aux = lexema.toString().replace('D', 'E');
		
		Double double_ = Double.parseDouble(aux);

		//devolver char a la entrada
		fileHelper.reset();
		
		// Verificar rango
		if (!(2.2250738585072014E-308 < double_ && double_< 1.7976931348623157E+308 || 
			-1.7976931348623157E+308 < double_ && double_ < -2.2250738585072014E-308)) {
			
			logger.logError("Rango invalido para la constante: " + double_ + ", se descarta" );
			
			lexema.setLength(0);
			
			return -1;
		}
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		} else {
			TS.putConstante(lexema.toString());  // DEBERIA HABER UNA FUNCION PARA AGREGAR DOBLES? O USAMOS EL DE LAS CTES?
			return TS.getToken(lexema.toString());
		}
	}
}
