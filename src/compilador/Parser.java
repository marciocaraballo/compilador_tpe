package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
  public static void main(String[] args) { 
    
    String archivo_a_leer = args[0];

    System.out.println("Se va a leer archivo " + archivo_a_leer);

    BufferedReader reader = null;
  	try {
  		reader = new BufferedReader(new FileReader(archivo_a_leer));
  	} catch (FileNotFoundException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}

    char firstChar = 0;  
  	try {
  		firstChar = (char) reader.read();
  	} catch (IOException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}

    	System.out.println(firstChar);
    }
  
}
