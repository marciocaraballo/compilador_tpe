package accion_semantica;

/**
 * Reconocer literal y devuelve el token.
 * 
 * Ejemplo: Para un '+' devuelve el ascii asociado
 *
 */
public class AS1 implements AccionSemantica {
	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {	
		return (int)nextCharacter;
	}
}
