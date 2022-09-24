package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * 
 * Devuelve el ultimo caracter leido a la entrada
 * 
 * Retorna el TOKEN asociado a la cadena
 *
 */

public class AS6 extends AccionSemantica {

	public AS6(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		
		//Devolver nextCharacter
		lexema.append(nextCharacter);
		
		int nextCharInt = 0;
		
		try {
			nextCharInt = reader.read();
			nextCharacter = (char) nextCharInt;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnalizadorLexico.modifPos(nextCharInt);
		
		return TPR.getToken(lexema.toString());
	}



}
