package compilador;

import accion_semantica.*;

public class MatrixAccionesSemanticas {
	
//	private AS1 AS1 = new AS1();
//	private AS2 AS2 = new AS2();
//	private AS3 AS3 = new AS3();
//	private AS4 AS4 = new AS4();
//	private AS5 AS5 = new AS5();
//	private AS6 AS6 = new AS6();
//	private AS7 AS7 = new AS7();
//	private AS8 AS8 = new AS8();
//	private AS9 AS9 = new AS9();
//	private ASE ASE = new ASE();
	
//	private AccionSemantica[][] matrixAccionesSemanticas = {
//			   /*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*"$"*/
//		/* 0*/ {AS2,  AS2,  AS2,    AS2,   null,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS2,    AS2,    AS2,    ASE,    ASE,    AS2,    AS2,    AS9,   AS9,   AS9,   null},
//		/* 1*/ {AS3,  AS3,  AS3,    AS3,   null,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,   AS4,   AS4,   null},
//		/* 2*/ {AS4,  AS4,  AS3,    AS5,   null,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,   AS5,   AS5,   null}
//	};
	
	private AccionSemantica[][] matrixAccionesSemanticas = new AccionSemantica[25][25];

	public MatrixAccionesSemanticas(TablaDeSimbolos ts, TablaPalabrasReservadas tpr) {
		
		AS1 AS1 = new AS1(tpr, ts);
		AS2 AS2 = new AS2(tpr, ts);
		AS3 AS3 = new AS3(tpr, ts);
		AS4 AS4 = new AS4(tpr, ts);
		AS5 AS5 = new AS5(tpr, ts);
		AS6 AS6 = new AS6(tpr, ts);
		AS7 AS7 = new AS7(tpr, ts);
		AS8 AS8 = new AS8(tpr, ts);
		AS9 AS9 = new AS9(tpr, ts);
		ASE ASE = new ASE(tpr, ts);
															/*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*"$"*/
		matrixAccionesSemanticas[0] = new AccionSemantica[]{ AS2,  AS2,  AS2,    AS2,   ASE,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS2,    AS2,    AS2,    ASE,    ASE,    AS2,    AS2,    AS9,   AS9,   AS9,   null};
		matrixAccionesSemanticas[1] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,   AS3,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,   AS4,   AS4,   null};
		matrixAccionesSemanticas[2] = new AccionSemantica[]{ AS4,  AS4,  AS3,    AS5,   AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,   AS5,   AS5,   null};	
		matrixAccionesSemanticas[3] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,   AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS6,    AS7,    AS7,    AS7,   AS7,   AS7,   null};
		matrixAccionesSemanticas[4] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,   AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,   AS7,   AS7,   null};
		matrixAccionesSemanticas[5] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,   AS7,    AS7,	 AS7,    AS7,    AS7, 	 AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS7,    AS3,    AS7,    AS7,    AS7,    AS7,    AS7,   AS7,   AS7,   null};
		matrixAccionesSemanticas[6] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,   AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,   AS3,   AS3,   null}; 
		matrixAccionesSemanticas[7] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,   AS3,    AS3,    AS3,    AS3,    AS3,    AS3,	 AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS9,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,   AS3,   AS3,   null};
		matrixAccionesSemanticas[8] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,   AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS6,    AS3,   AS3,   AS8,   null};
		matrixAccionesSemanticas[9] = new AccionSemantica[]{ ASE , ASE,  AS3,    ASE,   ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,   null};
		matrixAccionesSemanticas[10] = new AccionSemantica[]{AS8,  AS8,  AS3,    AS3,   AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,   AS8,   AS8,   null};
		matrixAccionesSemanticas[11] = new AccionSemantica[]{ASE,  ASE,  AS3,    ASE,   ASE,    AS3,    AS3,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,   null};
		matrixAccionesSemanticas[12] = new AccionSemantica[]{AS8,  AS8,  AS3,    AS8,   AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,   AS8,   AS8,   null};
		matrixAccionesSemanticas[13] = new AccionSemantica[]{ASE,  ASE,  AS3,    ASE,   ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,   null};
	} 

	/**
	* A partir de un estado inicial y un valor de columna, obtener la AS a ejecutar
	* 
	* @param initialState 
	* @param column
	* @return
	*/
	public AccionSemantica getAccionSemantica(int initialState, int column) {
		return this.matrixAccionesSemanticas[initialState][column];
	}
}