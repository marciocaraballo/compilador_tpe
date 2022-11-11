package compilador;

public class Tupla {
	
	private String e1;
	private String e2;
	private String e3;
	
	
	public Tupla(String e1, String e2, String e3) {
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}


	public String getElem(int value) {
		if (value == 1)
			return e1;
		if (value == 2)
			return e2;
		return e3;
	}
	
}
