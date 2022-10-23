package compilador;

public class Logger {
	
	private static Logger instance;
	
	public static final String GREEN = "\033[0;32m";   // GREEN
	public static final String YELLOW = "\033[0;33m";  // YELLOW
	public static final String ANSI_RESET = "\u001B[0m";
	
	private StringBuilder fileLexico = new StringBuilder();
	private StringBuilder fileSintactico = new StringBuilder();
	
	private int linea = 1;
	
	private Logger() {}
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		
		return instance;
	}
	
	public void logStartUpError(String error) {
		System.err.println("[Startup] " + error);
	}
	
	public void logError(String error) {
		System.err.println("[ERROR] Linea " + linea + ": " + error);
	}
	
	public void logWarning(String warning) {
		System.out.println(YELLOW + "[WARNING] Linea " + linea + ": " + warning + ANSI_RESET);
		if (warning.contains("Lexico"))
			fileLexico.append("[WARNING] Linea " + linea + ": " + warning + "\n");
		else
			fileSintactico.append("[WARNING] Linea " + linea + ": " + warning + "\n");
	}
	
	public void logSuccess(String success) {
		System.out.println(GREEN + "[Success] Linea " + linea + ": " + success + ANSI_RESET);
		if (success.contains("Lexico"))
			fileLexico.append("[Success] Linea " + linea + ": " + success + "\n");
		else
			fileSintactico.append("[Success] Linea " + linea + ": " + success + "\n");
	}
	
	public void incrementarLinea() {
		linea++;
	}
	
	public String getLexico() {
		return fileLexico.toString();
	}
	
	public String getSintactico() {
		return fileSintactico.toString();
	}
}
