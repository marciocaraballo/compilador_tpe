package accion_semantica;

import java.io.BufferedReader;

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
	public int ejecutar(BufferedReader reader , StringBuilder lexema, char nextCharacter) {
		
		//Devolver nextCharacter a la entrada
		System.out.println(lexema.charAt(0));

		if (lexema.charAt(0) == '.')
			lexema.insert(0, '0');
		
		String aux = lexema.toString().replace('D', 'E');
		
		Double double_ = Double.parseDouble(aux);

		// Verificar rango
		if (!(2.2250738585072014E-308 < double_&& double_< 1.7976931348623157E+308 || 
			-1.7976931348623157E+308 < double_ && double_ < -2.2250738585072014E-308)) {
			
			lexema.setLength(0);
			lexema.append('0');
			
			System.out.println("prueba");

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
