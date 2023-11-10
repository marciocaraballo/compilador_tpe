package compilador;

public class GeneracionCodigo {
  
  private static GeneracionCodigo instance = null;

  public static GeneracionCodigo getInstance() {
    if (instance == null) {
        instance = new GeneracionCodigo();
    }

    return instance;
  }

  public void generar() {
    
  }
}
