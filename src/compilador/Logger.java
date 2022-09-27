package compilador;

public class Logger {
	
	public static final String GREEN = "\033[0;32m";   // GREEN
	public static final String YELLOW = "\033[0;33m";  // YELLOW
	public static final String ANSI_RESET = "\u001B[0m";
	
	private int linea = 1;
	
	public Logger() {}
	
	public void logError(String error) {
		System.err.println("[ERROR] Linea " + linea + ": " + error);
	}
	
	public void logWarning(String warning) {
		System.out.println(YELLOW + "[WARNING] Linea " + linea + ": " + warning +ANSI_RESET);
	}
	
	public void logSuccess(String success) {
		System.out.println(GREEN + "[Success] Linea " + linea + ": " + success + ANSI_RESET);
	}
	
	public void incrementarLinea() {
		linea++;
	}
}
