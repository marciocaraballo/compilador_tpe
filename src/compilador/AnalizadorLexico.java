package compilador;

public class AnalizadorLexico {
	
	private MatrixEstados matrixEstados = new MatrixEstados();
	
	public AnalizadorLexico() {};
	
	public int getToken() {
		
		System.out.println(matrixEstados.getEstadoSiguiente(1, 1));
		
		//@TODO return next token
		return 0;
	}
}
