package accion_semantica;

/**
 * Inicializar string, y añadir caracter leido
 *
 */
public class AS2 implements AccionSemantica {

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		lexema.setLength(0);
		lexema.append(nextCharacter);
		
		return -1;		
	}
}
