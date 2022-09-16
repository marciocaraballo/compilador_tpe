package accion_semantica;

public class AS4 implements AccionSemantica {

	@Override
	public int ejecutar(String nextCharacter, String lexema) {
		
		if (lexema.length() > 25) {
			//Warning?
		}
		
		if (TPR.getToken(lexema) != -1) {
			
			return TPR.getToken(lexema);
			
		}
		
		/*
		else {
			if (TS.get() != -1)
				return TS.get();
			else {
				TS.add()
				return TS.get();
			}
		}*/
		
		
		return 0;
		
	}

}
