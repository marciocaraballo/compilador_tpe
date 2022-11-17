package compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

public class GeneracionCodigoIntermedio {
    
	private boolean debeApilarTercetoDefer = false; 
	private Logger logger = Logger.getInstance();
	private ArrayList<String> lista_variables_a_declarar = new ArrayList<String>();
	private ArrayList<Terceto> lista_tercetos = new ArrayList<Terceto>();
	private ArrayList<String> listaCheckParametros = new ArrayList<String>();
	private Stack<Terceto> pila_tercetos = new Stack<Terceto>();
	private Stack<Integer> pila_posiciones = new Stack<Integer>();
	private Stack<ArrayList<Terceto>> pila_breaks_do = new Stack<ArrayList<Terceto>>();
	private HashMap<String, ArrayList<Terceto>> do_con_etiqueta = new HashMap<String, ArrayList<Terceto>>();
	private Stack<ArrayList<Terceto>> defer_tercetos_ambitos = new Stack<ArrayList<Terceto>>();
	private MatrixMult matrixMult = MatrixMult.getInstance();
	private StringBuilder ambito = new StringBuilder();
	private HashMap<String, String> listaParametros = new HashMap<String, String>();
	
	
	private int posicionTerceto = 0;

	private static GeneracionCodigoIntermedio instance = null; 

	private GeneracionCodigoIntermedio() {}

	public static GeneracionCodigoIntermedio getInstance() {
		if (instance == null) {
			instance = new GeneracionCodigoIntermedio();
		}

		return instance;
	}

	public boolean debeApilarTercetoDefer() {
		return debeApilarTercetoDefer;
	}

	public void setApilarTercetoDefer(boolean debeApilar) {
		debeApilarTercetoDefer = debeApilar;
	}

	public void apilarAmbitoParaDefer() {
		ArrayList<Terceto> tercetosDefer = new ArrayList<Terceto>();

		defer_tercetos_ambitos.push(tercetosDefer);
	}

	public ArrayList<Terceto> desapilarAmbitoParaDefer() {
		return defer_tercetos_ambitos.pop();
	}

	public void agregarTercetoParaDeferAmbitoActual(Terceto t) {
		ArrayList<Terceto> tercetosDeferAmbitoActual = defer_tercetos_ambitos.lastElement();
			
		tercetosDeferAmbitoActual.add(t);
	}
	
	public void checkParametros(/**String lexema*/) {
		
	}
	
	public void addCheckParametros(String tipo) {
		listaCheckParametros.add(tipo);
	}

	public void agregarVariableADeclarar(String variable) {
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		if (TS.has(variable + ambito.toString())) {
			logger.logError("[Generacion de codigo] La variable " + variable + " ya fue declarada");
		}
		else
			lista_variables_a_declarar.add(variable);
	}
	
	public void agregarAmbitoParametros() {
		Set<String> listKeys = listaParametros.keySet();
		Iterator<String> it = listKeys.iterator();
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		while(it.hasNext()) {
			String parametro = it.next();
			TS.putTipo(parametro, listaParametros.get(parametro));
			TS.swapLexemas(parametro, parametro + this.ambito.toString());
			agregarUsoAIdentificador(parametro + this.ambito, "parametro");
		}
		listaParametros.clear();
	}
	
	public void agregarParametro(String parametro, String tipo) {
		listaParametros.put(parametro, tipo);
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
			TS.swapLexemas(variableActual, (variableActual + this.ambito.toString()));
			agregarUsoAIdentificador(variableActual + this.ambito, "variable");
		}

		lista_variables_a_declarar.clear();
	}
	
	public void setTipo(String cte, String tipo) {
		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
		if (TS.has(cte))
			TS.putTipo(cte, tipo);
		else 
			logger.logError("[Generacion de codigo] Variable no declarada: " + cte);
	}

	public void agregarUsoAIdentificador(String identificador, String uso) {

		TablaDeSimbolos TS = TablaDeSimbolos.getInstance();

		TS.putIdentificadorUso(identificador, uso);
	}

	public void agregarTerceto(Terceto t) {
		if (debeApilarTercetoDefer()) {
			agregarTercetoParaDeferAmbitoActual(t);
		} else {
			t.setPosicion(posicionTerceto);
			posicionTerceto++;
			lista_tercetos.add(t);
		}
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

	public void agregarDoConEtiqueta(String etiqueta) {
		do_con_etiqueta.put(etiqueta, new ArrayList<Terceto>());
	}

	public void agregarTercetoBreakAEtiquetaDo(String etiqueta, Terceto t) {
		ArrayList<Terceto> tercetos_por_etiqueta = do_con_etiqueta.get(etiqueta);

		tercetos_por_etiqueta.add(t);
	}

	public ArrayList<Terceto> getTercetosBreakDeEtiquetaDo(String etiqueta) {
		return do_con_etiqueta.get(etiqueta);
	}

	public void borrarEtiquetaDo(String etiqueta) {
		do_con_etiqueta.remove(etiqueta);
	}
	
	public Terceto crearTercetoConversion(int value, String tipo1, String tipo2, String operando) {
		if (matrixMult.getElement(tipo1, tipo2, value) != "")
			return new Terceto(matrixMult.getElement(tipo1, tipo2, value), operando, "-"); 
		return null;
	}
	
	public String[] agregarTercetoExpresiones(String v0_0, String v0_1, String v1_0, String v1_1, String v3_0, String v3_1, String simbolo) {
		
		Terceto t1 = 	this.crearTercetoConversion(1, v1_1, v3_1, v1_0);
		Terceto tipo = 	this.crearTercetoConversion(2, v1_1, v3_1, "");
		Terceto f1 = 	this.crearTercetoConversion(3, v1_1, v3_1, v3_0);

		TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
		
		Terceto terceto = new Terceto(simbolo, v1_0, v3_0);
		
		ts.getTipo(v1_0);
		
		if (t1 != null){
			terceto.setOperando1("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
			t1.setOperando1(t1.getOperando1());
			agregarTerceto(t1);
		}
		if (f1 != null){
			terceto.setOperando2("[" + String.valueOf(instance.getTamanioListaTercetos()) + "]");
			f1.setOperando1(f1.getOperando1());
			agregarTerceto(f1);
		}

		v0_0 = "[" + getTamanioListaTercetos() + "]";
		
		agregarTerceto(terceto);
		
		v0_1 = tipo.getOperacion();
		
		String[] par = {v0_0, v0_1};
		
		return par;
		
	}
	
	public void nuevoAmbito(String nuevo_ambito) {
		ambito.append(":").append(nuevo_ambito);	
	}
	
	public void salirAmbito() {
		int indice = ambito.lastIndexOf(":");
		ambito.delete(indice, ambito.length());
	}
	
	public void salirAmbitoAux(StringBuilder lexema) {
		int indice = ambito.lastIndexOf(":");
		indice = lexema.lastIndexOf(":");
		if (indice != -1)
			lexema.delete(indice, lexema.length());
		else
			lexema.setLength(0);
	}

	public StringBuilder getAmbito() {
		return ambito;
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
