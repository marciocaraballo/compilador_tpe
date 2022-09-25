package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS10 extends AccionSemantica{

	public AS10(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);
		
		String cadena = lexema.toString().replaceAll("\'", "");
		
		if (TS.has(cadena))
			return TS.getToken(cadena);
		else {
			TS.putCadena(cadena);
			return TS.getToken(cadena);
		}
	}
}
