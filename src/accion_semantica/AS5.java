package accion_semantica;

public class AS5 implements AccionSemantica {

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		int cte = Integer.parseInt(lexema.toString());
		
		if (cte >= 0 && cte <= (Math.pow(2, 16) - 1)) {
			
			return 0;
		}
		
		else
			return -1;

	}

}
