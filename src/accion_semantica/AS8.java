package accion_semantica;

import compilador.TablaDeSimbolos;
import compilador.TablaPalabrasReservadas;

/**
 * Devuelve ultimo caracter leido a la entrada
 * Verifica rango de las constantes doubles
 * Cheque si su lexema esta dentro de la TS
 * - Si esta devuelve el token asociado
 * - Si no esta:
 * 	 - Alta en TS
 * 	 - Devuelve Token
 * 
 *
 */

public class AS8 extends AccionSemantica {

	public AS8(TablaPalabrasReservadas TPR, TablaDeSimbolos TS) {
		super(TPR, TS);
	}

	@Override
	public int ejecutar(char nextCharacter, StringBuilder lexema) {
		
		//Devolver nextCharacter a la entrada
		
		int double_ = Integer.parseInt(lexema.toString());
		
		// Verificar rango
		if (!(2.2250738585072014D-308 < double_&& double_< 1.7976931348623157D+308 && 
			-1.7976931348623157D+308 < double_ && double_ < -2.2250738585072014D-308)) {
			
			//Devolvemos Error?
			
		}
		
		
		// Buscamos en la TS, si existe el lexema, devolvemos el token.
		if (TS.has(lexema.toString()))
			return TS.getToken(lexema.toString());
		
		
		// Si no existe, lo agregamos a TS y luego devolvemos el Token
		
		else {
			TS.putConstante(lexema.toString());  // DEBERIA HABER UNA FUNCION PARA AGREGAR DOBLES? O USAMOS EL DE LAS CTES?
			return TS.getToken(lexema.toString());
		}
		
	}



}
