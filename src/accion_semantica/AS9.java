package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * 
 * Descarta los caracteres que no son tenidos en cuenta (Espacios en blanco, Tabulaciones, Saltos de Linea, Comentarios)
 *
 */

public class AS9 extends AccionSemantica {

	public AS9(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		lexema.setLength(0); 
		
		try {
			nextCharacter = (char) reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnalizadorLexico.modifPos(nextCharacter);
		
		return -1;
	}
}
