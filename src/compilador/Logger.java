package compilador;

public class Logger {
	
	private int linea = 0;
	
	public Logger() {}
	
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
