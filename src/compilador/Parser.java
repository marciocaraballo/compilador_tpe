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
    0,    0,    1,    2,    3,    3,    4,    4,    5,    5,
    5,    8,    8,    9,    9,   11,   11,   12,   13,   13,
   13,   16,   16,   15,   10,   17,   17,   18,    6,    6,
    6,    6,    6,    6,    6,    6,    6,    6,   24,   24,
   24,   25,   25,   23,   23,   26,   27,   27,   29,   29,
   19,   19,   19,   22,   22,   22,   22,   30,   22,   20,
   20,   31,   31,   32,   32,   28,   33,   33,   33,   33,
   33,   33,   14,   14,   14,   34,   34,   34,   35,   35,
   35,   37,   38,   38,   39,   39,   21,   21,   36,   36,
    7,    7,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    3,    1,
    2,    3,    1,    4,    3,    6,    7,    6,    1,    3,
    5,    1,    3,    2,    2,    1,    3,    3,    1,    2,
    2,    3,    2,    3,    1,    2,    2,    3,    1,    2,
    2,    1,    3,    6,    8,    1,    1,    3,    1,    2,
    4,    3,    3,    7,    6,    6,    6,    0,   11,    7,
    9,    1,    3,    1,    2,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    1,    3,    1,    1,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,   92,   91,    0,    0,    0,    0,    7,    8,    0,
   10,    0,    0,   29,    0,    0,   35,    0,    0,    1,
    0,    0,    0,    0,    0,   89,    0,    0,    0,    0,
    0,   78,   80,   81,    0,    0,    0,   39,   47,    0,
    0,   30,    0,    0,   36,    0,    0,   25,    0,    4,
    6,    0,    0,   11,    0,    0,    0,   31,   33,   37,
    0,   52,    0,    0,    0,    0,    0,    0,   90,    0,
    0,   67,   68,   69,    0,    0,   70,   71,   72,    0,
    0,    0,    0,    0,   41,   49,    0,   40,    0,   32,
   34,   38,    0,    0,    0,    9,    0,    0,   15,    0,
   51,    0,   88,   87,    0,    0,    0,    0,   85,   86,
    0,   83,    0,    0,    0,    0,    0,    0,    0,   76,
   77,   46,   43,   48,   50,    0,   28,   27,   12,   14,
    0,    0,    0,    0,   24,    0,    0,    0,   82,   58,
    0,    0,    0,    0,    0,    0,    0,    0,   62,    0,
   16,    0,    0,   84,    0,   56,    0,   57,   55,   44,
    0,    0,   64,    0,    0,   60,   17,    0,    0,   54,
   18,    0,   63,   65,    0,    0,   21,    0,   45,   61,
    0,    0,   23,    0,   59,
};
final static short yydgoto[] = {                          3,
    4,    5,   66,   17,   18,   19,   20,   63,   21,   22,
   23,   67,  117,   39,  186,  187,   58,   59,   24,   25,
   26,   27,   28,   49,   50,   29,   51,   40,   97,  165,
  160,  174,   90,   41,   42,   43,   44,  121,  122,
};
final static short yysindex[] = {                      -114,
    0,  102,    0,  -94,    0, -228,   21,   23, -189,   58,
  -78,    0,    0,  -59, -185,  -50,  102,    0,    0, -171,
    0,   34, -108,    0,   36,   37,    0,   38,   46,    0,
   60,   29, -204,   66,   70,    0, -147,    6,   40,   72,
  -14,    0,    0,    0,   57,   59, -131,    0,    0,   61,
 -158,    0,   68,   73,    0,   75, -151,    0,   78,    0,
    0,   87,   76,    0,  102, -129,   14,    0,    0,    0,
 -133,    0,   -7,  107,  113,  117,  -36,   63,    0,  -94,
  -34,    0,    0,    0,   29,   29,    0,    0,    0,   29,
 -102,   29,   29,  -96,    0,    0,   85,    0,  122,    0,
    0,    0,  -95, -185, -171,    0,   42,  132,    0,  -78,
    0,  -88,    0,    0,  116,  -82,  137,  136,    0,    0,
  -10,    0,  124,  -94, -115,  -14,  -14,   11,  -94,    0,
    0,    0,    0,    0,    0,   29,    0,    0,    0,    0,
   29,  -93,  -61, -219,    0,  126, -219,   63,    0,    0,
  129,  -94,  131,  133,  144,   -6,  151,  104,    0, -203,
    0, -219,  158,    0,  -68,    0,  147,    0,    0,    0,
  149,   29,    0,  148,  -61,    0,    0, -219,  167,    0,
    0,  168,    0,    0,  -48,  174,    0,   29,    0,    0,
 -219,  255,    0,  -94,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  250,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -111,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,    0,    0,
  -18,    0,    0,    0,  253,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  254,    0,
    0,  264,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   62,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  273,    0,    0,
    0,    0,    0,    0,    0,    5,   28,  -11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  289,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  290,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -1,   15,    0,    0,    2,  -53,  227,    0,    0,
    0,  268,    0,  -20,  -65,  152,  240,    0,  332,  333,
  334,  336,  337,  -21,    0,  259,  244,  -22,    0,    0,
  180,    0,    0,   -8,  -12,  -56,    0,    0,  208,
};
final static int YYTABLESIZE=426;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         79,
   79,   79,   30,   79,  115,   79,  125,    2,    2,   74,
   73,  118,   48,    5,   65,   81,   16,   79,   79,   79,
   79,  120,   75,  116,   75,   96,   75,   92,    2,   66,
  149,   61,   93,  148,  171,   85,   85,   86,   86,   31,
   75,   75,   75,   75,   47,   73,   80,   73,   48,   73,
   37,  111,   75,   85,   76,   86,   12,   13,  175,  176,
   32,  158,   33,   73,   73,   73,   73,   34,   74,  128,
   74,   57,   74,   37,   60,  135,  126,  127,  123,  130,
  131,  163,   85,   79,   86,   62,   74,   74,   74,   74,
  161,  120,   64,  116,   68,   69,   70,   38,   48,   88,
   89,   87,   37,   71,   37,   77,   75,   37,  177,   78,
   79,   48,   91,  155,   94,   99,  103,   95,   72,   98,
  156,  104,  151,  153,  116,    6,  100,  154,    7,   73,
  105,  101,    8,  102,  106,   45,  108,  116,  109,  110,
   10,   11,    1,   46,  159,  152,   14,  112,    6,  182,
  167,    7,   74,  113,    5,    8,    9,  114,  129,  173,
  132,  136,  137,   10,   11,  192,  140,   12,   13,   14,
   15,  141,  143,  144,  145,  184,  159,  146,    6,  147,
  157,    7,  150,  162,  170,    8,   53,  166,   45,  168,
  172,  169,  195,   10,   11,    6,   46,    6,    7,   14,
    7,  178,    8,  179,    8,  180,  188,  181,  189,  134,
   10,   11,   10,   11,  190,   79,   14,  191,   79,   79,
   79,   79,   79,   79,   79,   79,  124,   79,   79,   79,
   79,   79,   79,   79,   79,   79,   79,   79,   75,   12,
   13,   75,   75,   75,   75,   75,   75,   75,   75,   66,
   75,   75,   75,   75,   75,   75,   75,   75,   75,   75,
   75,   73,   35,   36,   73,   73,   73,   73,   73,   73,
   73,   73,  183,   73,   73,   73,   73,   73,   73,   73,
   73,   73,   73,   73,   74,   35,   36,   74,   74,   74,
   74,   74,   74,   74,   74,  194,   74,   74,   74,   74,
   74,   74,   74,   74,   74,   74,   74,   46,   82,   83,
   84,   42,   26,   19,   35,   36,   35,   36,   53,  119,
   36,   53,   13,   53,   53,   53,   53,   53,   53,   20,
   22,  139,  107,   53,   53,   53,   53,   53,   53,   53,
   53,    6,  193,  138,    7,   52,   53,   54,    8,   55,
   56,   45,  133,  142,  185,  164,   10,   11,    6,   46,
    6,    7,   14,    7,    0,    8,    9,    8,    0,    0,
    0,    0,    0,   10,   11,   10,   11,   12,   13,   14,
   15,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    0,    0,    7,    0,    0,
    0,    8,    0,    0,    0,    0,    0,    0,    0,   10,
   11,    0,    0,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,    4,   45,   41,   47,   41,  123,  123,   32,
   31,   77,   11,  125,  123,   38,    2,   59,   60,   61,
   62,   78,   41,   77,   43,   47,   45,   42,  123,   41,
   41,   17,   47,   44,   41,   43,   43,   45,   45,  268,
   59,   60,   61,   62,  123,   41,   41,   43,   47,   45,
   45,   59,  257,   43,  259,   45,  276,  277,  262,  263,
   40,  123,   40,   59,   60,   61,   62,  257,   41,   90,
   43,  257,   45,   45,  125,   97,   85,   86,   80,   92,
   93,  147,   43,  125,   45,  257,   59,   60,   61,   62,
  144,  148,   59,  147,   59,   59,   59,   40,   97,   60,
   61,   62,   45,   58,   45,   40,  125,   45,  162,   40,
  258,  110,   41,  136,   58,  274,  268,   59,   59,   59,
  141,   44,  124,  125,  178,  257,   59,  129,  260,  125,
   44,   59,  264,   59,   59,  267,  266,  191,  125,  273,
  272,  273,  257,  275,  143,  261,  278,   41,  257,  172,
  152,  260,  125,   41,  266,  264,  265,   41,  261,  158,
  257,   40,  258,  272,  273,  188,  125,  276,  277,  278,
  279,   40,  261,   58,  257,  174,  175,   41,  257,   44,
  274,  260,   59,   58,   41,  264,  125,   59,  267,   59,
   40,   59,  194,  272,  273,  257,  275,  257,  260,  278,
  260,   44,  264,  272,  264,   59,   40,   59,   41,  125,
  272,  273,  272,  273,  263,  257,  278,   44,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  276,
  277,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  125,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,   41,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   58,  269,  270,
  271,   59,   59,   41,  257,  258,  257,  258,  257,  257,
  258,  260,   59,  262,  263,  264,  265,  266,  267,   41,
   41,  105,   65,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  191,  104,  260,   14,   14,   14,  264,   14,
   14,  267,   94,  110,  175,  148,  272,  273,  257,  275,
  257,  260,  278,  260,   -1,  264,  265,  264,   -1,   -1,
   -1,   -1,   -1,  272,  273,  272,  273,  276,  277,  278,
  279,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,   -1,   -1,  278,
};
}
final static short YYFINAL=3;
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
"programa : bloque_sentencias",
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
"funcion : encabezado_funcion cuerpo_funcion '}'",
"encabezado_funcion : FUN ID '(' ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':' tipo",
"cuerpo_funcion : sentencias RETURN '(' expresion ')' ';'",
"lista_de_parametros : parametro",
"lista_de_parametros : parametro ',' parametro",
"lista_de_parametros : parametro ',' parametro ',' lista_parametros_exceso",
"lista_parametros_exceso : parametro",
"lista_parametros_exceso : parametro ',' lista_parametros_exceso",
"parametro : tipo ID",
"declaracion_constantes : CONST lista_declaracion_constantes",
"lista_declaracion_constantes : declaracion_constante",
"lista_declaracion_constantes : declaracion_constante ',' lista_declaracion_constantes",
"declaracion_constante : ID ASIGNACION CTE",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : DEFER asignacion",
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : DEFER seleccion ';'",
"sentencia_ejecutable : imprimir ';'",
"sentencia_ejecutable : DEFER imprimir ';'",
"sentencia_ejecutable : sentencia_when",
"sentencia_ejecutable : DEFER sentencia_when",
"sentencia_ejecutable : sentencia_do ';'",
"sentencia_ejecutable : DEFER sentencia_do ';'",
"sentencia_ejecutable_do : sentencia_ejecutable",
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
"asignacion : ID ASIGNACION expresion ';'",
"asignacion : ID ASIGNACION ';'",
"asignacion : ID ASIGNACION expresion",
"sentencia_when : WHEN '(' condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion ')' bloque_sentencias ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' bloque_sentencias ';' $$1 WHEN '(' condicion ')' bloque_sentencias",
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
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : constante",
"factor : invocacion_funcion",
"invocacion_funcion : ID '(' lista_de_parametros_reales ')'",
"lista_de_parametros_reales : parametro_real",
"lista_de_parametros_reales : lista_de_parametros_reales ',' parametro_real",
"parametro_real : ID",
"parametro_real : constante",
"imprimir : OUT '(' CADENA ')'",
"imprimir : OUT '(' ID ')'",
"constante : CTE",
"constante : '-' CTE",
"tipo : UINT16",
"tipo : DOUBLE64",
};

//#line 224 ".\gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		ts.swapLexemas(constante, "-"+constante);
	} else {
		logger.logError("[Lexico] No se admiten ui16 con valores negativos: " + "-"+constante);
	}
}	

public int yylex() {
	return lexico.yylex(yylval);
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
		ts = new TablaDeSimbolos();
		lexico = new AnalizadorLexico(fileHelper, ts, logger);
		
        parser.run();
			
		ts.print();
	}
}
//#line 476 "Parser.java"
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
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 17 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba nombre del programa"); }
break;
case 9:
//#line 39 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 10:
//#line 40 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 11:
//#line 41 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 15:
//#line 51 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 21:
//#line 66 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 44:
//#line 116 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 45:
//#line 117 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 51:
//#line 135 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 52:
//#line 136 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 53:
//#line 137 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 54:
//#line 141 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 55:
//#line 142 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 56:
//#line 143 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 57:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 58:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 59:
//#line 146 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 60:
//#line 150 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 61:
//#line 151 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 87:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 88:
//#line 211 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 90:
//#line 216 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 717 "Parser.java"
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
