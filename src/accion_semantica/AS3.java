package accion_semantica;

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
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		lexema.append(nextCharacter);
		
		return -1;
	}

}
