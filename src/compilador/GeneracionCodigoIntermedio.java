package compilador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

public class GeneracionCodigoIntermedio {
    
	private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();
	private ArrayList<Terceto> lista_tercetos = new ArrayList<Terceto>();
	private Stack<Terceto> pila_tercetos = new Stack<Terceto>();
	private Stack<Integer> pila_posiciones = new Stack<Integer>();
	private Stack<ArrayList<Terceto>> pila_breaks_do = new Stack<ArrayList<Terceto>>();
	private MatrixMult matrixMult = MatrixMult.getInstance();
	private int posicionTerceto = 0;

	private static GeneracionCodigoIntermedio instance = null; 

	private GeneracionCodigoIntermedio() {}

	public static GeneracionCodigoIntermedio getInstance() {
		if (instance == null) {
			instance = new GeneracionCodigoIntermedio();
		}

		return instance;
	}

	public void agregarVariableADeclarar(String variable) {
		lista_variables_a_declarar.add(variable);
	}

	public void agregarTipoAParametro(String parametro, String tipo) {
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		TS.putTipo(parametro, tipo);
		agregarUsoAIdentificador(parametro, "nombre_parametro_funcion");
	}

	public void agregarTipoAListaDeVariables(String type) {

		Iterator<String> it = lista_variables_a_declarar.iterator();
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

		while(it.hasNext()) {
			String variableActual = it.next();
			TS.putTipo(variableActual, type);
			agregarUsoAIdentificador(variableActual, "variable");
		}

		lista_variables_a_declarar.clear();
	}
	
	public void setTipo(String cte, String tipo) {
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		if (TS.has(cte))
			TS.putTipo(cte, tipo);
		else
			System.out.println("LA VARIABLE NO FUE DECLARADA" + cte);
	}

	public void agregarUsoAIdentificador(String identificador, String uso) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

		TS.putIdentificadorUso(identificador, uso);
	}

	public void agregarTerceto(Terceto t) {
		t.setPosicion(posicionTerceto);
		posicionTerceto++;
		lista_tercetos.add(t);
	} 

	public int getTamanioListaTercetos() {
		return lista_tercetos.size();
	}
	
	public void apilarTerceto(Terceto t) {
		pila_tercetos.push(t);
	}

	public Terceto desapilarTerceto() {
		return pila_tercetos.pop();
	}
	

	public void apilarPosicionTerceto(int pos) {
		pila_posiciones.push(pos);
	}

	public int desapilarPosicionTerceto() {
		return pila_posiciones.pop();
	}

	public int getUltimaPosicionTerceto() {
		return pila_posiciones.lastElement();
	}

	public void iniciarListaTercetosBreakDo() {
		ArrayList<Terceto> lista_tercetos_break = new ArrayList<Terceto>();

		pila_breaks_do.push(lista_tercetos_break);
	}

	public void agregarTercetoBreakAListaTercetosBreakDo(Terceto breakTerceto) {
		ArrayList<Terceto> lista_tercetos_break = pila_breaks_do.lastElement();

		lista_tercetos_break.add(breakTerceto);
	}

	public ArrayList<Terceto> getListaTercetosBreakDo() {
		return pila_breaks_do.pop();
	}
	
	public Terceto crearTercetoConversion(int value, String tipo1, String tipo2, String operando) {
		if (matrixMult.getElement(tipo1, tipo2, value) != "")
			return new Terceto(matrixMult.getElement(tipo1, tipo2, value), operando, "-"); 
		return null;
	}
	
	public void printTercetos() {

		System.out.println("Tercetos generados");

		ListIterator<Terceto> it = lista_tercetos.listIterator();

		while(it.hasNext()) {
			Terceto t = it.next();

			System.out.println("[" + t.getPosicion() + "]" + "(" + t.getOperacion() + ", " + t.getOperando1() + ", " + t.getOperando2() + ")");
		}
	}
	
	public void AgregarTercetoExpresiones(String v0_0, String v0_1, String v1_0, String v1_1, String v3_0, String v3_1) {
		
		Terceto t1 = 	crearTercetoConversion(1, v1_1, v3_1, v1_0);
		Terceto tipo = 	crearTercetoConversion(2, v1_1, v3_1, "");
		Terceto f1 = 	crearTercetoConversion(3, v1_1, v3_1, v3_0);

		Terceto terceto = new Terceto("+", v1_0, v3_0);

		
		if (t1 != null){
			terceto.setOperando1("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
			agregarTerceto(t1);
		}
		if (f1 != null){
			terceto.setOperando2("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
			agregarTerceto(f1);
		}
		
		v0_0 = "[" + instance.getTamanioListaTercetos() + "]";
		
		
		instance.agregarTerceto(terceto);

		v0_1 = tipo.getOperacion();
		
	}
}