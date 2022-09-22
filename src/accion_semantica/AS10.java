package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

public class AS10 extends AccionSemantica{

	public AS10(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		char aux = nextCharacter;
		
		try {
			nextCharacter = (char) reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnalizadorLexico.modifPos(nextCharacter);
		
		
		lexema.append(aux);
		if (TS.has(lexema.toString()))
			return TS.getToken(lexema.toString());
		else {
			TS.putCadena(lexema.toString());
			return TS.getToken(lexema.toString());
		}
	}
	
	

}
