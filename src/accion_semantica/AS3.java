package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Leer caracter y concatenar a un lexema que se fue leyendo
 *
 */
public class AS3 extends AccionSemantica {

	public AS3(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);
		
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
