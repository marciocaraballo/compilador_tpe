package accion_semantica;

import compilador.FileReaderHelper;
import compilador.TablaPalabrasReservadas;

public class AS6 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		TablaPalabrasReservadas TPR = TablaPalabrasReservadas.getInstance();
		
		lexema.append(nextCharacter);
		
		return TPR.getToken(lexema.toString());
	}
}
