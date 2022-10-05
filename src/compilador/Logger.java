package compilador;

public class Logger {
	
	public static final String GREEN = "\033[0;32m";   // GREEN
	public static final String YELLOW = "\033[0;33m";  // YELLOW
	public static final String ANSI_RESET = "\u001B[0m";
	
	private String fileLexico = new String();
	private String fileSintactico = new String();
	
	private int linea = 1;
	
	public Logger() {
		
	}
	
	public void logError(String error) {
		System.err.println("[ERROR] Linea " + linea + ": " + error);
	}
	
	public void logWarning(String warning) {
		System.out.println(YELLOW + "[WARNING] Linea " + linea + ": " + warning + ANSI_RESET);
		if (warning.contains("Lexico"))
			fileLexico += "[WARNING] Linea " + linea + ": " + warning + "\n";
		else
			fileSintactico += "[WARNING] Linea " + linea + ": " + warning + "\n";
	}
	
	public void logSuccess(String success) {
		System.out.println(GREEN + "[Success] Linea " + linea + ": " + success + ANSI_RESET);
		if (success.contains("Lexico"))
			fileLexico += "[Success] Linea " + linea + ": " + success + "\n";
		else
			fileSintactico += "[Success] Linea " + linea + ": " + success + "\n";
	}
	
	public void logPrint(String cadena, String success) {
		System.out.println(GREEN + "cadena: " + cadena + ANSI_RESET);
		System.out.println(GREEN + "[Sucess] Linea " + linea + ": " + success + ANSI_RESET);
	}
	
	public void incrementarLinea() {
		linea++;
	}
	
	public String getLexico() {
		return fileLexico;
	}
	
	public String getSintactico() {
		return fileSintactico;
	}
	
}
