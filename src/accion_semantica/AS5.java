package accion_semantica;

public class AS5 implements AccionSemantica {

	@Override
	public int ejecutar(String nextCharacter, String lexema) {
		
		int cte = Integer.parseInt(lexema);
		
		if (cte >= 0 && cte <= (Math.pow(2, 16) - 1)) {
			
			return 0;
		}
		
		else
			return -1;

	}

}
