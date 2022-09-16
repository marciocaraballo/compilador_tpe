package accion_semantica;

public class AS2 implements AccionSemantica {

	@Override
	public int ejecutar(String nextCharacter, String lexema) {
		
		
		lexema = "";
		lexema.concat(nextCharacter);
		
		return -1;		
	}

}
