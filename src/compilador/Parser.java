package compilador;

public class Parser {
  public static void main(String[] args) { 
    
    String archivo_a_leer = args[0];
    String delim = "/n";

    System.out.println("Se va a leer archivo " + archivo_a_leer);
    String[] result = archivo_a_leer.split(delim);
    for (String resultvalue : result) {
      System.out.println(resultvalue);
    }
  }
}
