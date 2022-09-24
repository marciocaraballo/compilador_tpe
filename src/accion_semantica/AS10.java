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
		
		if (TS.has(lexema.toString()))
			return TS.getToken(lexema.toString());
		else {
			TS.putCadena(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
}
