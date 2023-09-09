package compilador;

import accion_semantica.*;

public class MatrixAccionesSemanticas {
	
	private static MatrixAccionesSemanticas instance;
	
	private AccionSemantica[][] matrixAccionesSemanticas = new AccionSemantica[19][29];

	private MatrixAccionesSemanticas() {
		
		AS1 AS1 = new AS1();
		AS2 AS2 = new AS2();
		AS3 AS3 = new AS3();
		AS4 AS4 = new AS4();
		AS5 AS5 = new AS5();
		AS6 AS6 = new AS6();
		AS7 AS7 = new AS7();
		AS8 AS8 = new AS8();
		AS9 AS9 = new AS9();
		AS10 AS10 = new AS10();
		AS11 AS11 = new AS11();
		ASE ASE = new ASE();
															/*L*/ /*l*/ /*d*/ 	/*"E"*/ /*e */  /*“i”*/ /*"u"*/ /*"l"*/ /*"_"*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*"%"*/ /*"!"*/ /*"."*/	/*tab*/ /*bl*/ /*nl*/ /*eof*/ /*cr*/ /*others*/
		matrixAccionesSemanticas[0] = new AccionSemantica[] { AS2,  AS2,  AS2,   AS2,    AS2,	   AS2,    AS2,	 AS2, 	 AS2, 	 AS1, 	 AS1,	 AS1,	 AS1,	 AS1, 	 AS1, 	 AS1, 	 AS1,	 AS2, 	 AS1, 	 AS2, 	 AS2, 	 AS2, 	 AS2, 	AS2,  	AS2, 	AS9,   AS9,   AS9, 	  AS1,	 AS9,	 ASE };
		matrixAccionesSemanticas[1] = new AccionSemantica[] { ASE,  AS3,  AS3,	 AS3,	 AS3,	   AS3,	   AS3,	 AS3,	 AS3,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	 AS4,	AS4,	AS4,	AS4,   AS4,   AS4,    AS4,	 AS4,	 AS4 };
		matrixAccionesSemanticas[2] = new AccionSemantica[] { AS7,  AS7,  AS7, 	 AS7,	 AS7,	   AS7,	   AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7, 	 AS7, 	 AS7, 	 AS6, 	 AS7, 	 AS7, 	 AS7, 	AS7, 	AS7,	AS7,   AS7,   AS7,	  AS7, 	 AS7, 	 AS7 };
		matrixAccionesSemanticas[3] = new AccionSemantica[] { AS7,  AS7,  AS7, 	 AS7,	 AS7,	   AS7,	   AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7, 	 AS7, 	 AS7, 	 AS6, 	 AS7, 	 AS7, 	 AS7, 	AS7, 	AS7,	AS7,   AS7,   AS7,	  AS7, 	 AS7, 	 AS7 };
		matrixAccionesSemanticas[4] = new AccionSemantica[] { ASE,  ASE,  ASE, 	 ASE,	 ASE,	   ASE,	   ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	AS6, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[5] = new AccionSemantica[] { ASE,  ASE,  ASE, 	 ASE,	 ASE,	   ASE,	   ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	AS3,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[6] = new AccionSemantica[] { ASE,  ASE,  ASE, 	 ASE,	 ASE,	   AS5,	   AS3,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[7] = new AccionSemantica[] { ASE,  ASE,  ASE, 	 ASE,	 ASE,	   ASE,	   ASE,	 AS5,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[8] = new AccionSemantica[] { ASE,  ASE,  AS3, 	 ASE,	 ASE,	   ASE,	   ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[9] = new AccionSemantica[] { AS8,  AS8,  AS3, 	 AS3,	 AS3,	   AS8,	   AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	AS8, 	AS8,	AS8,   AS8,   AS8,	  AS8, 	 AS8, 	 AS8 };
		matrixAccionesSemanticas[10] = new AccionSemantica[]{ ASE,  ASE,  ASE, 	 ASE,	 ASE,	   ASE,	   ASE,	 ASE,	 ASE,	 AS3,	 AS3,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[11] = new AccionSemantica[]{ ASE,  ASE,  AS3, 	 ASE,	 ASE,	   ASE,	   ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE,	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	 ASE, 	ASE, 	ASE,	ASE,   ASE,   ASE,	  ASE, 	 ASE, 	 ASE };
		matrixAccionesSemanticas[12] = new AccionSemantica[]{ AS8,  AS8,  AS3, 	 AS8,	 AS8,	   AS8,	   AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8,	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	 AS8, 	AS8, 	AS8,	AS8,   AS8,   AS8,	  AS8, 	 AS8, 	 AS8 };
		matrixAccionesSemanticas[13] = new AccionSemantica[]{ AS7,  AS7,  AS7, 	 AS7,	 AS7,	   AS7,	   AS7,	 AS7,	 AS7,	 AS7,	 AS6,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	AS7, 	AS7,	AS7,   AS7,   AS7,	  AS7, 	 AS7, 	 AS7 };
		matrixAccionesSemanticas[14] = new AccionSemantica[]{ AS7,  AS7,  AS7, 	 AS7,	 AS7,	   AS7,	   AS7,	 AS7,	 AS7,	 AS7,	 AS7,	 AS3,	 AS7,	 AS7,	 AS7,	 AS7,	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	 AS7, 	AS7, 	AS7,	AS7,   AS7,   AS7,	  AS7, 	 AS7, 	 AS7 };
		matrixAccionesSemanticas[15] = new AccionSemantica[]{ AS3,  AS3,  AS3, 	 AS3,	 AS3,	   AS3,	   AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	AS3, 	AS3,	AS3,   AS3,   AS3,	  AS3, 	 AS3, 	 AS3 };
		matrixAccionesSemanticas[16] = new AccionSemantica[]{ AS3,  AS3,  AS3, 	 AS3,	 AS3,	   AS3,	   AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3, 	 AS9, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	AS3, 	AS3,	AS3,   AS3,   AS3,	  AS3, 	 AS3, 	 AS3 };
		matrixAccionesSemanticas[17] = new AccionSemantica[]{ AS3,  AS3,  AS3, 	 AS3,	 AS3,	   AS3,	   AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3,	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS3, 	 AS10, 	AS3, 	AS3,	AS3,   AS3,   AS3,	  AS3, 	 AS3, 	 AS3 };
		matrixAccionesSemanticas[18] = new AccionSemantica[]{ AS3, AS11, AS11,  AS11,   AS11,	  AS11,   AS11,  AS11,   AS3,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,   AS11,  AS11,   AS11,  AS11,  AS11,   AS11,  AS11,   AS11 };
	} 

	public static MatrixAccionesSemanticas getInstance() {
		if (instance == null) {
			instance = new MatrixAccionesSemanticas();
		}
		
		return instance;
	}
	
	public AccionSemantica getAccionSemantica(int initialState, int column) {
		return this.matrixAccionesSemanticas[initialState][column];
	}
}