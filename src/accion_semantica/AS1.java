package accion_semantica;

public class AS1 implements AccionSemantica {

	@Override
	public int ejecutar(String nextCharacter, String lexema) {
		
		
		return TPR.getToken(nextCharacter);
		
	}

}
