package accion_semantica;

import compilador.FileReaderHelper;
import compilador.Logger;
import compilador.TablaDeSimbolos;

public class AS8 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		Logger logger = Logger.getInstance();
		String formattedDouble = lexema.toString().replace('D', 'E');
		
		Double parsedDouble = Double.parseDouble(formattedDouble);
		
		//devolver char a la entrada
		fileHelper.reset();
		
		if (parsedDouble != 0 && (parsedDouble > 3.40282347E+38 || parsedDouble < 1.17549435E-38)) {
			logger.logWarning("[Lexico] Rango invalido para la constante: " + lexema.toString() + ", se trunca al rango permitido");
			lexema.setLength(0);
			//double es mayor que el maximo permitido
			if (parsedDouble > 3.40282347E+38) {
				lexema.append("3.40282347E+38");
			} else {
				//double es menor que el minimo permitido
				lexema.append("1.17549435E-38");
			}
		}
		
		if (TS.has(lexema.toString())) {
			return TS.getToken(lexema.toString());
		} else {
			TS.putConstante(lexema.toString());
			TS.putTipo(lexema.toString(), "f64");
			return TS.getToken(lexema.toString());
		}
	}
}
