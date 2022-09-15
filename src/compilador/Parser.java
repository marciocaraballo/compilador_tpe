package compilador;

import java.io.BufferedReader;
import java.io.FileReader;

public class Parser {
  public static void main(String[] args) { 
    
    String archivo_a_leer = args[0];
    //String delim = "/n";

    System.out.println("Se va a leer archivo " + archivo_a_leer);

    BufferedReader reader = new BufferedReader(new FileReader(archivo_a_leer));

    int firstChar = reader.read();

    System.out.println(firstChar);
    
    // String[] result = archivo_a_leer.split(delim);
    // for (String resultvalue : result) {
    //   System.out.println(resultvalue);
    }
  
}
