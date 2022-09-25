package compilador;

import accion_semantica.*;

public class MatrixAccionesSemanticas {
	
	private AccionSemantica[][] matrixAccionesSemanticas = new AccionSemantica[14][25];

	public MatrixAccionesSemanticas(TablaDeSimbolos ts, TablaPalabrasReservadas tpr, Logger logger) {
		
		AS1 AS1 = new AS1(tpr, ts);
		AS2 AS2 = new AS2(tpr, ts);
		AS3 AS3 = new AS3(tpr, ts);
		AS4 AS4 = new AS4(tpr, ts, logger);
		AS5 AS5 = new AS5(tpr, ts, logger);
		AS6 AS6 = new AS6(tpr, ts);
		AS7 AS7 = new AS7(tpr, ts);
		AS8 AS8 = new AS8(tpr, ts);
		AS9 AS9 = new AS9(tpr, ts);
		AS10 AS10 = new AS10(tpr, ts);
		ASE ASE = new ASE(tpr, ts, logger);
															/*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*eof*/ /*cr*/ /*others*/
		matrixAccionesSemanticas[0] = new AccionSemantica[]{ AS2,  AS2,  AS2,    AS2,    ASE,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS2,    AS2,    AS2,    ASE,    ASE,    AS2,    AS2,    AS9,   AS9,   AS9,    AS1,	  AS9,		ASE};
		matrixAccionesSemanticas[1] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,    AS3,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,   AS4,   AS4,    AS4,   AS4,		ASE};
		matrixAccionesSemanticas[2] = new AccionSemantica[]{ AS4,  AS4,  AS3,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS5,    AS3,    AS5,    AS5,   AS5,   AS5,    AS5,   AS5,		ASE};	
		matrixAccionesSemanticas[3] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS6,    AS7,    AS7,    AS7,   AS7,   AS7,    AS7,   AS7,		ASE};
		matrixAccionesSemanticas[4] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS7,   AS7,   AS7,    AS7,	  AS7,		ASE};
		matrixAccionesSemanticas[5] = new AccionSemantica[]{ AS7,  AS7,  AS7,    AS7,    AS7,    AS7,	 AS7,    AS7,    AS7, 	 AS7,    AS7,    AS7,    AS7,    AS7,    AS7,    AS6,    AS7,    AS3,    AS7,    AS7,    AS7,    AS7,    AS7,   AS7,   AS7,    AS7,   AS7,		ASE};
		matrixAccionesSemanticas[6] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,   AS3,   AS3,    AS3,   AS3,		ASE}; 
		matrixAccionesSemanticas[7] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,	 AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS9,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,   AS3,   AS3,    AS3,   AS3,		ASE};
		matrixAccionesSemanticas[8] = new AccionSemantica[]{ AS3,  AS3,  AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,    AS3,   AS10,    AS3,   AS3,   ASE,    ASE,   ASE,		AS3};
		matrixAccionesSemanticas[9] = new AccionSemantica[]{ ASE , ASE,  AS3,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,    ASE,   ASE,		ASE};
		matrixAccionesSemanticas[10] = new AccionSemantica[]{AS8,  AS8,  AS3,    AS3,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,   AS8,   AS8,    AS8,   AS8,		ASE};
		matrixAccionesSemanticas[11] = new AccionSemantica[]{ASE,  ASE,  AS3,    ASE,    ASE,    AS3,    AS3,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,    ASE,   ASE,		ASE};
		matrixAccionesSemanticas[12] = new AccionSemantica[]{AS8,  AS8,  AS3,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,    AS8,   AS8,   AS8,    AS8,   AS8,		ASE};
		matrixAccionesSemanticas[13] = new AccionSemantica[]{ASE,  ASE,  AS3,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,    ASE,   ASE,   ASE,    ASE,   ASE,		ASE};
	} 

	public AccionSemantica getAccionSemantica(int initialState, int column) {
		return this.matrixAccionesSemanticas[initialState][column];
	}
}