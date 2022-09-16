package accion_semantica;

/**
 * -Devolver a la entrada el último carácter leído 
	-Verificar tamaño de cadena
-Buscar en la TPR: 
 - Si está, devolver token de PR 
 - Si no está: 
-Buscar en la TS:
 -Si está, devolver ID + Punt TS 
 -Si no está: 
-Alta en TS 
-Devolver ID + Punt TS

 *
 *	ID es token 100
 *
 */
public class AS4 implements AccionSemantica {

	@Override
	public int ejecutar(char nextCharacter, String lexema) {
		
		//nextCharacter no se usaria aca, devolver a la entrada?
		
		if (lexema.length() > 25) {
			//Warning?
		}
		
		// ver si es palabra reservada
		if (TPR.getToken(lexema) != -1) {
			
			return TPR.getToken(lexema);
			
		} else {
			return 100;
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
		
		
		//return 0;
		
	}

}
