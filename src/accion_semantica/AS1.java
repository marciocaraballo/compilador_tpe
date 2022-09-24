package accion_semantica;

import java.io.BufferedReader;
import java.io.IOException;

import compilador.AnalizadorLexico;
import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Reconocer literal y devuelve el token.
 * 
 * Ejemplo: Para un '+' devuelve el ascii asociado
 *
 */
public class AS1 extends AccionSemantica {
	public AS1(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(BufferedReader reader, StringBuilder lexema, char nextCharacter) {
		char aux = nextCharacter;
		lexema.setLength(0);
		lexema.append(aux);
		
		int nextCharInt = 0;
		
		try {
			nextCharInt = reader.read();
			nextCharacter = (char)nextCharInt;
		} catch (IOException e) {
			// TODO Auto-generated catch block	e.printStackTrace();
		}
		
		AnalizadorLexico.modifPos(nextCharInt);
		
		return (int)aux;
	}
}
