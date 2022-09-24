package compilador;

import java.io.IOException;

public class Parser {
  public static void main(String[] args) throws IOException {
	  
	if (args.length == 0) {
		System.err.println("No se especifico ningun archivo de codigo");
	} else {
		String archivo_a_leer = args[0];
		System.out.println("Se va a leer archivo " + archivo_a_leer);
		
		FileReaderHelper fileHelper = new FileReaderHelper();
		
		fileHelper.open(archivo_a_leer);
		
		Logger logger = new Logger();
		TablaDeSimbolos ts = new TablaDeSimbolos();
		AnalizadorLexico lexico = new AnalizadorLexico(fileHelper, ts, logger);
		
		while (lexico.hasNext()) {
			System.out.println("Un token: " + lexico.getToken());
		}	
			
		ts.print();
		}
	}
}
