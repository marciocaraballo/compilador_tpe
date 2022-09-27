//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 ".\gramatica.y"
package compilador;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short CADENA=259;
public final static short IF=260;
public final static short THEN=261;
public final static short ELSE=262;
public final static short ENDIF=263;
public final static short OUT=264;
public final static short FUN=265;
public final static short RETURN=266;
public final static short BREAK=267;
public final static short ASIGNACION=268;
public final static short COMP_MAYOR_IGUAL=269;
public final static short COMP_MENOR_IGUAL=270;
public final static short COMP_DISTINTO=271;
public final static short WHEN=272;
public final static short DO=273;
public final static short UNTIL=274;
public final static short CONTINUE=275;
public final static short DOUBLE64=276;
public final static short UINT16=277;
public final static short DEFER=278;
public final static short CONST=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    4,    4,    5,    5,    5,
    8,    8,    9,   11,   11,   12,   13,   13,   15,   10,
   16,   16,   17,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,   23,   23,   23,   24,   24,   22,   22,
   25,   26,   26,   28,   28,   18,   21,   19,   19,   29,
   29,   30,   30,   27,   31,   31,   31,   31,   31,   31,
   14,   14,   14,   14,   32,   32,   32,   33,   35,   35,
   36,   20,   34,   34,    7,    7,
};
final static short yylen[] = {                            2,
    2,    1,    3,    1,    2,    1,    1,    3,    1,    2,
    3,    1,    4,    6,    7,    6,    1,    3,    2,    2,
    1,    3,    3,    2,    3,    2,    3,    2,    3,    2,
    3,    2,    3,    2,    2,    2,    1,    3,    6,    8,
    1,    1,    3,    1,    2,    3,    6,    7,    9,    1,
    3,    1,    2,    3,    1,    1,    1,    1,    1,    1,
    3,    3,    1,    1,    3,    3,    1,    4,    1,    3,
    1,    4,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    1,    0,    0,    0,    0,    0,
    0,   76,   75,    0,    0,    0,    0,    6,    7,    0,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   42,
    0,    0,    0,    0,    0,    0,    0,    0,   20,    0,
    3,    5,    0,    0,   10,    0,   24,   26,   28,   30,
   32,    0,    0,   74,    0,    0,   64,   67,    0,    0,
    0,    0,    0,    0,   36,   44,    0,   34,   35,    0,
   25,   27,   29,   31,   33,    0,    0,    0,    8,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   56,   57,
   58,   59,   60,    0,    0,   72,    0,    0,    0,    0,
    0,   41,   38,   43,   45,    0,   23,   22,   11,    0,
   13,    0,   73,   71,    0,   69,    0,    0,   65,   66,
    0,    0,    0,   19,    0,    0,    0,    0,    0,    0,
    0,   68,    0,   50,    0,   14,    0,   18,   47,   39,
    0,    0,   70,   52,    0,    0,   48,   15,    0,    0,
   51,   53,    0,   16,   40,   49,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   17,   18,   19,   20,   54,   21,   22,
   23,   91,  109,   69,  110,   49,   50,   24,   25,   26,
   27,   28,   40,   41,   29,   42,   70,   77,  145,  155,
  104,   66,   67,   68,  125,  126,
};
final static short yysindex[] = {                      -212,
    0,    0,  -65, -164,    0, -187,   51,   55, -160,   66,
  -86,    0,    0, -174, -147,  -14, -164,    0,    0, -141,
    0,   58,   -4,   62,   63,   64,   67,   68,   70, -183,
 -183, -134,   89, -183, -187,   73,   74, -204,   75,    0,
   76, -142,   77,   78,   80,   81,   83, -125,    0,  100,
    0,    0,  102,   88,    0, -164,    0,    0,    0,    0,
    0, -123,  108,    0,    7,    4,    0,    0,  -37,  114,
  116,  -41,  119,  -96,    0,    0,  -99,    0,    0,  122,
    0,    0,    0,    0,    0,  -95, -147, -141,    0, -100,
   42,  -86, -179, -179, -179, -179, -179,    0,    0,    0,
    0,    0,    0, -183,  -92,    0,  115,  -85,  133,  131,
  -84,    0,    0,    0,    0, -183,    0,    0,    0,  138,
    0,  -94,    0,    0,   18,    0,    4,    4,    0,    0,
    7, -119, -192,    0,  121, -192,  -65,  141, -183,  143,
 -179,    0, -196,    0, -175,    0, -192,    0,    0,    0,
   24, -183,    0,    0, -108, -119,    0,    0,  125,  144,
    0,    0,  -77,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  129,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  132,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  134,
    0,    0,  135,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -40,    0,  136,  -32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  147,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -27,   -5,    0,    0,
  149,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   59,   14,    0,    0,  -83,  -29,  104,    0,    0,
    0,    0,    0,  -15,   61,  111,    0,   28,  185,  186,
  187,  188,    3,    0,  130,  113,  -22,    0,   47,    0,
    0,    8,    0,  -49,    0,   65,
};
final static int YYTABLESIZE=266;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        107,
   73,   73,   73,  143,   73,   94,   73,   95,   63,    4,
   63,   73,   63,   61,   65,   61,  161,   61,   73,   73,
   73,   73,  102,  103,  101,  114,   63,   63,   63,   63,
   52,   61,   61,   61,   61,   62,   38,   62,   39,   62,
   76,   43,  108,  124,    1,   96,  129,  130,  144,   94,
   97,   95,   35,   62,   62,   62,   62,    4,  142,  154,
    6,  141,   36,    7,  159,   39,   94,    8,   95,   90,
   37,  162,  144,   63,   64,   10,   11,  123,   64,  115,
   30,   14,    6,   12,   13,    7,  156,  157,  131,    8,
   31,  124,    6,  138,   32,    7,   33,   10,   11,    8,
    9,  127,  128,  146,   39,   34,  108,   10,   11,   48,
   51,   12,   13,   14,   15,   53,   55,  158,   56,   39,
   57,   58,   59,  151,   71,   60,   61,   62,   72,  160,
   74,   80,   75,   78,   79,   81,   82,    6,   83,   84,
    7,   85,   86,   87,    8,   88,   89,   93,    6,   92,
    4,    7,   10,   11,  105,    8,  106,   35,   14,  111,
  112,  116,  117,   10,   11,  120,  121,   36,  132,   14,
   35,  134,  133,  135,  136,   37,  137,  139,  147,  140,
   36,  150,  152,  164,  165,  166,   41,   17,   37,   54,
   37,  119,   21,   12,   46,  149,  148,  118,   44,   45,
   46,   47,  163,  113,  122,  153,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   73,   73,
   73,   98,   99,  100,   12,   13,   63,   63,   63,    0,
    0,   61,   61,   61,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,   62,   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   41,   42,   43,  123,   45,   43,   47,   45,   41,  125,
   43,   34,   45,   41,   30,   43,  125,   45,   59,   60,
   61,   62,   60,   61,   62,  125,   59,   60,   61,   62,
   17,   59,   60,   61,   62,   41,  123,   43,   11,   45,
   38,   14,   72,   93,  257,   42,   96,   97,  132,   43,
   47,   45,  257,   59,   60,   61,   62,  123,   41,  143,
  257,   44,  267,  260,   41,   38,   43,  264,   45,   56,
  275,  155,  156,  257,  258,  272,  273,  257,  258,   77,
  268,  278,  257,  276,  277,  260,  262,  263,  104,  264,
   40,  141,  257,  116,   40,  260,  257,  272,  273,  264,
  265,   94,   95,  133,   77,   40,  136,  272,  273,  257,
  125,  276,  277,  278,  279,  257,   59,  147,  123,   92,
   59,   59,   59,  139,  259,   59,   59,   58,   40,  152,
   58,  274,   59,   59,   59,   59,   59,  257,   59,   59,
  260,   59,  268,   44,  264,   44,   59,   40,  257,  273,
  266,  260,  272,  273,   41,  264,   41,  257,  278,   41,
  257,   40,  258,  272,  273,  266,  125,  267,  261,  278,
  257,  257,   58,   41,   44,  275,  261,   40,   58,  274,
  267,   41,   40,   59,   41,  263,   58,   41,  275,   41,
   59,   88,   59,   59,   59,  137,  136,   87,   14,   14,
   14,   14,  156,   74,   92,  141,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  269,  270,
  271,  269,  270,  271,  276,  277,  269,  270,  271,   -1,
   -1,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  269,  270,  271,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","CADENA","IF","THEN","ELSE",
"ENDIF","OUT","FUN","RETURN","BREAK","ASIGNACION","COMP_MAYOR_IGUAL",
"COMP_MENOR_IGUAL","COMP_DISTINTO","WHEN","DO","UNTIL","CONTINUE","DOUBLE64",
"UINT16","DEFER","CONST",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_programa bloque_sentencias",
"nombre_programa : ID",
"bloque_sentencias : '{' sentencias '}'",
"sentencias : sentencia",
"sentencias : sentencia sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_de_variables ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_constantes ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion : encabezado_funcion '{' cuerpo_funcion '}'",
"encabezado_funcion : FUN ID '(' ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':' tipo",
"cuerpo_funcion : sentencias RETURN '(' expresion ')' ';'",
"lista_de_parametros : parametro",
"lista_de_parametros : parametro ',' parametro",
"parametro : tipo ID",
"declaracion_constantes : CONST lista_declaracion_constantes",
"lista_declaracion_constantes : declaracion_constante",
"lista_declaracion_constantes : declaracion_constante ',' lista_declaracion_constantes",
"declaracion_constante : ID ASIGNACION CTE",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : DEFER asignacion ';'",
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : DEFER seleccion ';'",
"sentencia_ejecutable : imprimir ';'",
"sentencia_ejecutable : DEFER imprimir ';'",
"sentencia_ejecutable : sentencia_when ';'",
"sentencia_ejecutable : DEFER sentencia_when ';'",
"sentencia_ejecutable : sentencia_do ';'",
"sentencia_ejecutable : DEFER sentencia_do ';'",
"sentencia_ejecutable_do : asignacion ';'",
"sentencia_ejecutable_do : sentencia_break ';'",
"sentencia_ejecutable_do : CONTINUE ';'",
"sentencia_break : BREAK",
"sentencia_break : BREAK ':' etiqueta",
"sentencia_do : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"sentencia_do : etiqueta ':' DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"etiqueta : ID",
"bloque_sentencias_ejecutables_do : sentencia_ejecutable_do",
"bloque_sentencias_ejecutables_do : '{' sentencias_ejecutables_do '}'",
"sentencias_ejecutables_do : sentencia_ejecutable_do",
"sentencias_ejecutables_do : sentencias_ejecutables_do sentencia_ejecutable_do",
"asignacion : ID ASIGNACION expresion",
"sentencia_when : WHEN '(' condicion ')' THEN bloque_sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_DISTINTO",
"comparador : '>'",
"comparador : '<'",
"comparador : '='",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : invocacion_funcion",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"invocacion_funcion : ID '(' lista_de_parametros_reales ')'",
"lista_de_parametros_reales : parametro_real",
"lista_de_parametros_reales : lista_de_parametros_reales ',' parametro_real",
"parametro_real : factor",
"imprimir : OUT '(' CADENA ')'",
"factor : ID",
"factor : CTE",
"tipo : UINT16",
"tipo : DOUBLE64",
};

//#line 202 ".\gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = null;

public int yylex() {
	return lexico.yylex();
}

public void yyerror(String error) {
	logger.logError(error);
}


public static void main(String[] args) {
	if (args.length == 0) {
		System.err.println("No se especifico ningun archivo de codigo");
	} else {
		String archivo_a_leer = args[0];
		System.out.println("Se va a leer archivo " + archivo_a_leer);
		
		FileReaderHelper fileHelper = new FileReaderHelper();
		
		fileHelper.open(archivo_a_leer);
		
		Parser parser = new Parser();
		logger = new Logger();
		TablaDeSimbolos ts = new TablaDeSimbolos();
		lexico = new AnalizadorLexico(fileHelper, ts, logger, parser.yyval);
		
        parser.run();
			
		ts.print();
	}
}
//#line 407 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 16 ".\gramatica.y"
{ logger.logSuccess("Programa correcto detectado"); }
break;
case 46:
//#line 127 ".\gramatica.y"
{ logger.logSuccess("Asignacion correcta detectada"); }
break;
//#line 564 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
