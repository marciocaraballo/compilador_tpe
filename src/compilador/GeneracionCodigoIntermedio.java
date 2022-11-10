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
	
	public void printTercetos() {

		System.out.println("Tercetos generados");

		ListIterator<Terceto> it = lista_tercetos.listIterator();

		while(it.hasNext()) {
			Terceto t = it.next();

			System.out.println("[" + t.getPosicion() + "]" + "(" + t.getOperacion() + ", " + t.getOperando1() + ", " + t.getOperando2() + ")");
		}
	}
}
