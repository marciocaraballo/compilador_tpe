package compilador;

public class MatrixMult {
	
	private static MatrixMult instance;
	
	
	public static MatrixMult getInstance() {
		if (instance == null) {
			instance = new MatrixMult();
		}
		 
		return instance;
	}

	private Tupla[][] matrixMult = {
							/*ui16*/						/*f64*/
			/*ui16*/		{new Tupla("", "ui16", ""), 	new Tupla("itof", "f64", "")},
			/*f64*/			{new Tupla("", "f64", "itof"),	new Tupla("", "f64", "")												 }	
	};
	
	private MatrixMult() {
		
	}
	
	public String getElement(String v1, String v2, int v3) {
		if (v1.contains("ui16") && v2.contains("ui16"))
			return matrixMult[0][0].getElem(v3);
		if (v1.contains("ui16") && v2.contains("f64"))
			return matrixMult[0][1].getElem(v3);
		if (v1.contains("f64") && v2.contains("ui16"))
			return matrixMult[1][0].getElem(v3);
		if (v1.contains("f64") && v2.contains("f64"))
			return matrixMult[1][1].getElem(v3);
		return "";
	}
	
}
