package accion_semantica;

/**
 * Leer caracter y concatenar a un lexema que se fue leyendo
 *
 */
public class AS3 implements AccionSemantica {

	@Override
	public int ejecutar(char nextCharacter, StringBuffer lexema) {
			
		lexema.append(nextCharacter);
		
		return -1;
	}

}
