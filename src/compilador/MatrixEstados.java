package compilador;

public class MatrixEstados {
	
	private static MatrixEstados instance;
	
	public static final int F = -1;
	public static final int E = -2;
	
	private int[][] matrixEstados = {
			   /*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*"eof"*/  /*cr*/  /*others*/
		/* 0*/ {  1,    1,    2,      1,      E,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      3,      4,      5,      F,      E,      9,      8,      0,     0,     0,        F,	  0,		  E},
		/* 1*/ {  1,    1,    1,      1,      1,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     F,     F,        F, 	  F,		  F},
		/* 2*/ {  F,    F,    2,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     10,      F,      F,     F,     F,        F,      F,		  E},
		/* 3*/ {  F,    F,    F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     F,     F,        F,      F,		  E},
		/* 4*/ {  F,    F,    F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     F,     F,        F,      F,		  E},
		/* 5*/ {  F,    F,    F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      6,      F,      F,      F,      F,      F,     F,     F,        F,      F,		  E},
		/* 6*/ {  6,    6,    6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      7,      6,      6,      6,      6,      6,      6,     6,     6,        F,      6,		  6},
		/* 7*/ {  6,    6,    6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      0,      6,      6,      6,      6,      6,      6,     6,     6,        F,      6,		  E},
	    /* 8*/ {  8,    8,    8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      F,      8,     8,     E,        E,      E, 		  8},
	    /* 9*/ {  E,    E,   10,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,     E,     E,        E,      E,		  E},
	    /*10*/ {  F,    F,   10,     11,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     F,     F,        F,      F,		  E},
	    /*11*/ {  E,    E,   12,      E,      E,     13,     13,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,     E,     E,        E,      E,		  E},
	    /*12*/ {  F,    F,   12,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,      F,     F,     F,        F,      F,		  E},
	    /*13*/ {  E,    E,   12,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,      E,     E,     E,        E,      E,		  E}
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