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
		TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
		AnalizadorLexico lexico = new AnalizadorLexico(fileHelper);
		
		while (lexico.hasNext()) {
			//System.out.println("Token detectado: " + lexico.getToken());
			lexico.getToken();
		}	
			
		ts.print();
		}
	}
}