package compilador;

public class MatrixEstados {

  private int[][] matrixEstados = {
    {1,2,1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3,4,5,-2,-2,9,8,0,0,-1},
    {1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
  };

  public MatrixEstados() {}

  public int getEstadoSiguiente(int initialState, int column) {
    return this.matrixEstados[initialState][column];
  }
}