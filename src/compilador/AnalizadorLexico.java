package compilador;
//package accion_semantica;

import compilador.MatrixEstados;

public class AnalizadorLexico {
  public static void main(String[] args) {
  
    //private int[][] matrizEstado;
    //private AccionSemantica[][] matrizAccionesSemanticas;
  
   //  String archivo_a_leer = args[0];
    
   //  String cond1 = "/n";
   //  String cond2 = " ";
   //  String[] arrResult = archivo_a_leer.split(cond1);
   //  String[][] CompleteResult = new String[13][];
   //  int i = 0;
   //  for (String linea : arrResult) {
   //    CompleteResult[i] = linea.split(cond2);
   //    for (String lpm : CompleteResult[i]){
   //      System.out.println(lpm);
   //    }
   //    System.out.println(CompleteResult[0][1]);
   //    i++;
   //  }

    MatrixEstados matrixEstados = new MatrixEstados();

    System.out.println(matrixEstados.getEstadoSiguiente(1, 1));
    
  }
}
