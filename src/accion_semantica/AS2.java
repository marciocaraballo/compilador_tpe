package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Inicializar string, y añadir caracter leido
 *
 */
public class AS2 extends AccionSemantica {

	public AS2(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		lexema.setLength(0);
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

		return -1;		
	}
}
