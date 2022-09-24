package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
  public static void main(String[] args) throws IOException {
	  
	if (args.length == 0) {
		System.err.println("No se especifico ningun archivo de codigo");
	} else {
		String archivo_a_leer = args[0];
		System.out.println("Se va a leer archivo " + archivo_a_leer);
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(archivo_a_leer));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Logger logger = new Logger();
		TablaDeSimbolos ts = new TablaDeSimbolos();
		AnalizadorLexico lexico = new AnalizadorLexico(reader, ts, logger);
		
		int token = 0;
		
		//VER CASO CUANDO UN CARACTER QUE DEBE SER TENIDO EN CUENTA ES EL ULTIMO DEL ARCHIVO, RETORNA -1
		
		while (token != -1) {
			token = lexico.getToken();
			System.out.println("Un token: " + token);
			}	
			
			ts.print();
		  }
	}
}
