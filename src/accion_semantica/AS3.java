package accion_semantica;

public class AS3 implements AccionSemantica {

	@Override
	public int ejecutar(String nextCharacter, String lexema) {
			
		lexema.concat(nextCharacter);
		
		return -1;
	}

}
