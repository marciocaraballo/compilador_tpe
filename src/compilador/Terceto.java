package compilador;

public class Terceto {
    
    private String operacion;
    private String operando1;
    private String operando2;
    private int posicion;

    public Terceto() {}

    public Terceto(String op, String op1, String op2) {
        setOperacion(op);
        setOperando1(op1);
        setOperando2(op2);
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getOperando1() {
		return operando1;
	}

	public void setOperando1(String operando1) {
		this.operando1 = operando1;
	}

	public String getOperando2() {
		return operando2;
	}

	public void setOperando2(String operando2) {
		this.operando2 = operando2;
	}


}
