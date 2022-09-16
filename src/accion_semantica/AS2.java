package accion_semantica;

/**
 * Inicializar string, y añadir caracter leido
 *
 */
public class AS2 implements AccionSemantica {

	@Override
	public int ejecutar(char nextCharacter, String lexema) {
		
		lexema = "";
		lexema += nextCharacter;
		
		//probar con append si no anda
		
		return -1;		
	}
}
