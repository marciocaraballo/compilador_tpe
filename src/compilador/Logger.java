package compilador;

public class Logger {

	private AnalizadorLexico lexico = null;
	
	private int linea = 0;
	
	public Logger() {}
	
	public void setLexico(AnalizadorLexico lex) {
		lexico = lex;
	}
	
	public void logError(String error) {
		System.err.println("[ERROR] Linea " + linea + ": " + error);
	}
	
	public void logWarning(String warning) {
		System.out.println("[WARNING] Linea " + linea + ": " + warning);
	}
	
	public void incrementarLinea() {
		linea++;
	}
}
