package compilador;

public class MatrixEstados {
	
	private static MatrixEstados instance;
	
	public static final int F = -1;
	public static final int E = -2;	
	
	private int[][] matrixEstados = {
			   /*L*/ /*l*/ /*d*/ /*“E”*/ /*“e”*/ /* “i” */ /* “u” */ /* “l” */ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*"%"*/ /*"!"*/ /*"."*/ /*tab*/ /*bl*/ /*nl*/ /*"eof"*/  /*cr*/  /*others*/
		/* 0*/ { 18,    1,    5,     18,      1,        1,        1,        1,      1,     F,      13,      F,      F,      F,      F,      F,      F,     14,      F,      2,      3,      3,      17,     4,      8,      0,     0,    0,         F,      0,          E},
		/* 1*/ {  E,    1,    1,      1,      1,        1,        1,        1,      1,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/* 2*/ {  F,    F,    F,      F,      F,        F,        F,        F,      F,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/* 3*/ {  F,    F,    F,      F,      F,        F,        F,        F,      F,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/* 4*/ {  E,    E,    E,      E,      E,        E,        E,        E,      E,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     F,      E,      E,     E,    E,         E,      E,          E},
		/* 5*/ {  E,    E,    5,      6,      6,        E,        E,        E,      6,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      9,      E,     E,    E,         E,      E,          E},
		/* 6*/ {  E,    E,    E,      E,      E,        F,        7,        E,      E,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      E,      E,     E,    E,         E,      E,          E},
		/* 7*/ {  E,    E,    E,      E,      E,        E,        E,        F,      E,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      E,      E,     E,    E,         E,      E,          E},
		/* 8*/ {  E,    E,    9,      E,      E,        E,        E,        E,      E,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      E,      E,     E,    E,         E,      E,          E},
		/* 9*/ {  F,    F,    9,     10,     10,        F,        F,        F,      F,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/*10*/ {  E,    E,    E,      E,      E,        E,        E,        E,      E,    11,      11,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      E,      E,     E,    E,         E,      E,          E},
		/*11*/ {  E,    E,   12,      E,      E,        E,        E,        E,      E,     E,       E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,       E,     E,      E,      E,     E,    E,         E,      E,          E},
		/*12*/ {  F,    F,   12,      F,      F,        F,        F,        F,      F,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/*13*/ {  F,    F,    F,      F,      F,        F,        F,        F,      F,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/*14*/ {  F,    F,    F,      F,      F,        F,        F,        F,      F,     F,       F,     15,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F},
		/*15*/ { 15,   15,   15,     15,     15,       15,       15,       15,     15,    15,      15,     15,     16,     15,     15,     15,     15,     15,     15,     15,     15,     15,      15,    15,     15,     15,    15,   15,         E,     15,         15},
		/*16*/ { 15,   15,   15,     15,     15,       15,       15,       15,     15,    15,      15,     15,     15,     15,     15,     15,     15,      0,     15,     15,     15,     15,      15,    15,     15,     15,    15,   15,         E,     15,         15},
		/*17*/ { 17,   17,   17,     17,     17,       17,       17,       17,     17,    17,      17,     17,     17,     17,     17,     17,     17,     17,     17,     17,     17,     17,       F,    17,     17,     17,    17,   17,         E,     17,         17},
		/*18*/ { 18,    F,    F,     18,      F,        F,        F,        F,     18,     F,       F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,       F,     F,      F,      F,     F,    F,         F,      F,          F}
	};

  private MatrixEstados() {}
  
  public static MatrixEstados getInstance() {
	  if (instance == null) {
		  instance = new MatrixEstados();
	  }
	  
	  return instance;
  }

  public int getEstadoSiguiente(int initialState, int column) {
	  return this.matrixEstados[initialState][column];
  }
}