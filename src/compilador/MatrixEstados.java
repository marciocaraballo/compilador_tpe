package compilador;

public class MatrixEstados {
	/**
	 * Representacion de la matrix de estados. 
	 * 
	 * Consideraciones
	 * 
	 * Estado final F se representa con -1
	 * Estado de error E se representa con -2
	 * 
	 * Referencias utiles
	 * 
	 * L: letras mayusculas
	 * l: letras minusculas
	 * d: digitos
	 * "D": la letra D mayuscula
	 * nl: new line
	 * bl: blank space
	 * $: indica fin de archivo
	 */
	private int[][] matrixEstados = {
			   /*L*/ /*l*/ /*d*/ /*"D"*/ /*“_”*/ /*"+"*/ /*"-"*/ /*"{"*/ /*"}"*/ /*"("*/ /*")"*/ /*";"*/ /*"/"*/ /*"*"*/ /*","*/ /*"="*/ /*">"*/ /*"<"*/ /*":"*/ /*"!"*/ /*"."*/ /*"'"*/ /*tab*/ /*bl*/ /*nl*/ /*"$"*/
		/* 0*/ {  1,    1,    2,      1,     -2,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,      3,      4,      5,     -2,     -2,      9,      8,      0,     0,     0,     -1},
		/* 1*/ {  1,    1,    1,      1,      1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
		/* 2*/ { -1,   -1,    2,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     10,     -1,     -1,    -1,    -1,     -1},
		/* 3*/ { -1,   -1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
		/* 4*/ { -1,   -1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
		/* 5*/ { -1,   -1,   -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,      6,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
		/* 6*/ {  6,    6,    6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,       6,     6,      6,      7,      6,      6,      6,      6,      6,      6,     6,     6,     -1},
		/* 7*/ {  6,    6,    6,      6,      6,      6,      6,      6,      6,      6,      6,      6,      6,       6,     6,      6,      0,      6,      6,      6,      6,      6,      6,     6,     6,     -1},
	    /* 8*/ {  8,    8,    8,      8,      8,      8,      8,      8,      8,      8,      8,      8,      8,       8,     8,      8,      8,      8,      8,      8,      8,     -1,      8,     8,    -2,     -1},
	    /* 9*/ { -2,   -2,   10,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,      -2,    -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,    -2,    -2,     -2},
	    /*10*/ { -1,   -1,   10,     11,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
	    /*11*/ { -2,   -2,   12,     -2,     -2,     13,     13,     -2,     -2,     -2,     -2,     -2,     -2,      -2,    -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,    -2,    -2,     -2},
	    /*12*/ { -1,   -1,   12,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,      -1,    -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,     -1,    -1,    -1,     -1},
	    /*13*/ { -2,   -2,   12,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,      -2,    -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,     -2,    -2,    -2,     -2}
	};

  public MatrixEstados() {}

  /**
   * A partir de un estado inicial y un valor de columna, obtener el proximo estado
   * 
   * @TODO deberia tomar el char leido y no column, falta hacer la asociacion de char leido
   * a alguna columna
   * 
   * @param initialState 
   * @param column
   * @return
   */
  public int getEstadoSiguiente(int initialState, int column) {
    return this.matrixEstados[initialState][column];
  }
}