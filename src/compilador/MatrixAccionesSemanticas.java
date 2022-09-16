package compilador;

import accion_semantica.*;

public class MatrixAccionesSemanticas {
	
	private AS1 AS1 = new AS1();
	private AS2 AS2 = new AS2();
	private AS3 AS3 = new AS3();
	private AS4 AS4 = new AS4();
	private AS5 AS5 = new AS5();
	private AS6 AS6 = new AS6();
	private AS7 AS7 = new AS7();
	private AS8 AS8 = new AS8();
	private AS9 AS9 = new AS9();
	private ASE ASE = new ASE();
	
	private AccionSemantica[][] matrixAccionesSemanticas = {
			   /*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*"$"*/
		/* 0*/ {AS2,  AS2,  AS2,    AS2,   null,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS1,    AS2,    AS2,    AS2,    ASE,    ASE,    AS2,    AS2,    AS9,   AS9,   AS9,   null},
		/* 1*/ {AS3,  AS3,  AS3,    AS3,   null,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,    AS4,   AS4,   AS4,   null},
	};

  public MatrixAccionesSemanticas() {}

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