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
    0,    0,    0,    0,    0,    1,    2,    2,    3,    3,
    4,    4,    4,    6,    6,    6,    6,   10,   10,    7,
    7,   11,   11,   12,   13,   13,   13,   16,   16,   15,
   15,    8,    8,    8,   17,   17,   18,   18,   18,   18,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
   24,   24,   24,   25,   25,   25,   25,   25,   26,   26,
   23,   23,   28,   28,   28,   28,   28,   27,   29,   29,
   31,   31,   19,   19,   19,   22,   22,   33,   22,   32,
   32,   20,   20,   34,   34,   34,   34,   35,   35,   30,
   30,   37,   30,   36,   36,   36,   36,   36,   36,   14,
   14,   14,   38,   38,   38,   39,   39,   39,   41,   42,
   42,   43,   43,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    3,    6,    7,    6,    1,    3,    5,    1,    3,    2,
    1,    3,    2,    2,    1,    3,    3,    2,    2,    1,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    1,    1,    2,    4,    1,    3,    3,    2,    1,
    1,    3,    7,    6,    6,    6,    6,    1,    1,    3,
    1,    2,    4,    3,    3,    9,    8,    0,   17,    1,
    2,    8,   10,    1,    3,    2,    2,    1,    2,    3,
    2,    0,    3,    1,    1,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    4,    1,
    3,    1,    1,    5,    5,    4,    4,    4,    4,    4,
    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  126,  125,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   41,   43,   45,   47,   49,    0,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   51,   69,   52,   53,    0,
   42,   44,   46,   48,   50,    0,   34,    0,    0,    2,
    8,    0,   17,    0,   16,    0,    0,    0,    0,    5,
    0,    3,    0,  123,    0,   74,    0,    0,  105,  107,
  108,   18,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   54,    0,   59,   71,    0,    0,   39,
    0,   32,    0,   14,    0,    0,   21,   62,    1,    0,
  124,    0,    0,   73,    0,    0,   94,   95,   96,   97,
   98,   99,    0,    0,    0,  119,  117,  120,    0,  118,
    0,  116,   31,    0,    0,    0,    0,    0,    0,   68,
   58,    0,   70,   72,    0,    0,   37,   36,   20,    0,
  112,  113,    0,  110,    0,    0,  103,  104,    0,    0,
    0,  115,  114,    0,   30,    0,    0,    0,    0,    0,
   55,    0,    0,    0,    0,    0,  109,    0,    0,    0,
    0,   22,    0,    0,    0,    0,    0,    0,   65,   67,
    0,   66,    0,  111,   88,    0,    0,    0,   86,   89,
   23,    0,   81,    0,    0,    0,   63,   24,   85,    0,
   82,    0,   27,   78,    0,   77,    0,    0,    0,   76,
   83,   29,    0,    0,    0,    0,    0,    0,    0,   79,
};
final static short yydgoto[] = {                          3,
    4,   67,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   68,  136,   83,  212,  213,   58,   59,   25,   26,
   27,   28,   29,   47,   48,   49,   30,   31,   50,   84,
   98,  186,  219,  180,  181,  123,   85,   78,   79,   80,
   81,  153,  154,
};
final static short yysindex[] = {                      -106,
    0,  524,    0,  273,  -36,   19,  -38, -216,   28,  -89,
    0,    0,  -58,  -52,  -53,  524,    0,    0,    0,    0,
    0,  -50,   20,  302,    0,    0,    0,    0,    0,   33,
    0,  394,   -3,  -30, -145,  -20,   99,  115,  -37,  119,
  -18, -108,   69,  103,  547,    0,    0,    0,    0, -105,
    0,    0,    0,    0,    0, -192,    0,  104,  126,    0,
    0,  129,    0,  117,    0,  524,  -92,   52,  -94,    0,
   55,    0,  141,    0,  -71,    0,   44,   77,    0,    0,
    0,    0,  -40,  147,  -23,  132,  133,   -8,   -6,  134,
  -41,  -67,  156,    0,  -49,    0,    0,  477,  -34,    0,
  -60,    0,  -54,    0,   79,  169,    0,    0,    0,  -13,
    0,  -20,  -20,    0,  -20,  -20,    0,    0,    0,    0,
    0,    0,  -20,  -51,  -20,    0,    0,    0,  152,    0,
  153,    0,    0,  155,  -32,  176,  207,  130, -111,    0,
    0,  193,    0,    0,  -15,  213,    0,    0,    0,  -20,
    0,    0,   54,    0,   77,   77,    0,    0,   90,  364,
   90,    0,    0, -139,    0,  197, -202,  524,  136,  524,
    0,  198,   -1,  201,   80,  -13,    0,  200,    0, -120,
  275,    0, -139,  212,  524,  137,  524,  158,    0,    0,
  246,    0,  247,    0,    0,  514,  364,  249,    0,    0,
    0, -202,    0,  270,  206,  279,    0,    0,    0,   89,
    0,  320,    0,    0,  315,    0,  318, -202,  113,    0,
    0,    0,  346,  -20,  356,  138,  280,  524,  277,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   87,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   92,    0,    0,    0,    0,
   92,  347,  417,  436,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  190,    0,  250,  226,    0,
    0,  328,    0,  384,    0,    0,    0,    0,    0,    0,
  412,    0,    1,    0,    0,    0,   93,   24,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   92,    0,
  354,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   37,    0,    0,    0,    0,    0,  116,    0,
  144,    0,    0,    0,    0,    0,  373,    0,    0,    0,
    0,  458,    0,    0,   92,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,   70,    0,    0,   40,    0,
   41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  497,    0,
    0,    0,    0,  374,  301,    0,    0,    0,    0,    0,
  167,    0,    0,    0,    0, -115,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  387,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   48, -114,    0,    4,    0,    0,    0,  -63,   83,
    0,  369,    0,   11,  -73,  218,  334,    0,  425,  435,
  438,  439,  445,   12,    0,    0,  366,  380,    0,  -28,
    0, -151,    0,  262,  287,  385,    0,   45,   50,  -79,
    0,    0,  295,
};
final static int YYTABLESIZE=825;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        134,
  106,   39,  112,   90,  113,  145,   57,   35,   63,  141,
   75,  170,   93,   46,   75,    7,    2,  137,  188,  121,
  122,  120,   92,  102,   75,  172,   75,  135,   76,   75,
  152,   75,  129,   45,  131,  205,  121,  122,  120,  191,
   40,  106,  106,  106,   77,  106,  100,  106,   46,   15,
  128,   33,  130,  185,  133,  185,   97,  190,   36,  106,
  106,  106,  106,   61,  102,  100,  102,   41,  102,  101,
  146,   60,  185,   11,   12,  101,  229,   91,   65,   71,
   90,   93,  102,  102,  102,  102,  112,  100,  113,  100,
   69,  100,   75,  184,  177,   91,  152,  176,   90,   93,
  182,   46,  114,  135,   64,  100,  100,  100,  100,  144,
  101,   62,  101,  185,  101,  122,  173,   82,  115,  201,
  193,   72,  112,  116,  113,  106,   95,   94,  101,  101,
  101,  101,  112,  159,  113,  161,   11,   12,  135,   86,
    7,  197,  198,  121,   68,   19,   87,   87,  102,  169,
    1,   92,   92,   92,  135,   87,  155,  156,   91,   34,
  175,   96,  102,  179,  157,  158,   64,   42,   99,  103,
    6,  100,   35,  106,    7,  104,  107,   43,   10,  109,
  110,  195,    9,   10,  200,   44,  111,  124,   13,   40,
  126,  127,  132,  138,  101,  225,  139,  147,   42,  200,
  179,    6,   56,  149,   56,    7,   62,  140,  150,  160,
  162,  163,  164,    9,   10,  133,  166,   75,   37,   88,
   38,   89,   73,   74,  165,   35,   73,   74,  117,  118,
  119,   34,  203,   40,   11,   12,   73,   74,   73,   74,
  122,   73,   74,  151,   74,  117,  118,  119,   40,   33,
  167,  171,  168,  174,  183,  202,  189,  106,  187,  192,
  106,  204,  106,  106,  106,  106,  106,  106,  121,  106,
  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,
  102,    7,  206,  102,   35,  102,  102,  102,  102,  102,
  102,   64,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  100,  207,  208,  100,  211,  100,  100,
  100,  100,  100,  100,   40,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  101,   19,  214,  101,
  215,  101,  101,  101,  101,  101,  101,  216,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,  101,   75,
   35,  217,   75,   38,   75,   75,   75,   75,   75,   75,
   92,   92,   92,  218,   75,   75,   75,   75,   75,   75,
   75,   75,  122,  220,   33,  122,  221,  122,  122,  122,
  122,  122,  122,   15,  223,  224,   19,  122,  122,  122,
  122,  122,  122,  122,  122,   32,  226,   38,  227,  199,
  121,  230,  228,  121,   68,  121,  121,  121,  121,  121,
  121,    4,   38,   25,   26,  121,  121,  121,  121,  121,
  121,  121,  121,   64,   66,   80,   64,   28,   64,   64,
   64,   64,   64,   64,  105,  222,  148,   51,   64,   64,
   64,   64,   64,   64,   64,   64,   40,   52,  108,   40,
   53,   54,   19,   40,   40,   40,   42,   55,  210,    6,
  142,   40,   40,    7,  196,   40,   40,   40,   40,  125,
  194,    9,   10,    0,    0,    0,    0,   13,   38,    0,
    0,    0,   35,    0,    0,   35,  178,    0,    0,   35,
   35,   35,    0,    0,    0,    0,    0,   35,   35,    0,
    0,   35,   35,   35,   35,    0,   33,    0,   15,   33,
    0,    0,    0,   33,   33,   33,    0,    0,   70,    0,
    0,   33,   33,    0,    0,   33,   33,   33,   33,    5,
    0,   42,    6,    0,    6,    0,    7,    8,    7,    0,
    0,   56,    0,    0,    9,   10,    9,   10,   11,   12,
   13,   14,   13,    0,    0,    0,    0,    0,    5,    0,
   60,    6,    0,    0,    0,    7,    8,    0,    0,    0,
    0,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   57,    0,   19,    0,    0,   19,    0,    0,
    0,   19,   19,   19,    0,    0,    0,    0,    0,   19,
   19,  143,    0,   19,   19,   19,   19,    0,    0,    0,
   38,    0,    0,   38,    0,    0,    0,   38,   38,   38,
   42,   88,    0,    6,    0,   38,   38,    7,    0,   38,
   38,   38,   38,    0,    0,    9,   10,    0,  209,    0,
   15,   13,    0,   15,    0,    0,    0,   15,   15,   15,
    5,    0,    0,    6,    0,   15,   15,    7,    8,   15,
   15,   15,   15,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,   56,    0,    0,   56,    0,    0,    0,
   56,    0,    0,   56,    0,    0,    0,    0,   56,   56,
   56,   56,   60,    0,   56,   60,    0,    0,    0,   60,
    0,    0,   60,    0,    0,    0,    0,   60,   60,   60,
   60,    0,    0,   60,   57,    0,    0,   57,    0,    0,
    0,   57,    0,    0,   57,    0,    0,    0,    0,   57,
   57,   57,   57,   42,    0,   57,    6,    0,    0,    0,
    7,    0,    0,   43,    0,    0,    0,    0,    9,   10,
    0,   44,    0,   88,   13,    0,   88,    0,   84,   84,
   88,    0,    0,    0,    0,    0,    0,    0,   88,   88,
   42,    0,    0,    6,   88,    0,    0,    7,    0,    0,
    5,    0,    0,    6,    0,    9,   10,    7,    8,    0,
    0,   13,    0,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,   42,    0,    0,    6,    0,    0,    0,
    7,    0,    0,   43,    0,    0,    0,    0,    9,   10,
    0,   44,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   43,   41,   45,   40,   59,   44,   59,   59,
   45,  123,   41,   10,   45,    0,  123,   91,  170,   60,
   61,   62,   41,    0,   45,   41,   45,   91,   59,   45,
  110,   45,   41,  123,   41,  187,   60,   61,   62,   41,
  257,   41,   42,   43,   34,   45,    0,   47,   45,    2,
   59,    4,   59,  168,  257,  170,   45,   59,   40,   59,
   60,   61,   62,   16,   41,  258,   43,   40,   45,    0,
   99,  125,  187,  276,  277,  268,  228,   41,   59,   32,
   41,   41,   59,   60,   61,   62,   43,   41,   45,   43,
   58,   45,    0,  167,   41,   59,  176,   44,   59,   59,
  164,   98,   59,  167,   22,   59,   60,   61,   62,   98,
   41,  257,   43,  228,   45,    0,  145,   35,   42,  183,
   41,  125,   43,   47,   45,  125,   58,   59,   59,   60,
   61,   62,   43,  123,   45,  125,  276,  277,  202,   41,
  125,  262,  263,    0,   58,   59,  262,  263,  125,  261,
  257,   60,   61,   62,  218,   41,  112,  113,   40,  268,
  150,   59,   59,  160,  115,  116,    0,  257,  274,   44,
  260,  125,   44,  266,  264,   59,  125,  267,  273,  125,
   40,  178,  272,  273,  181,  275,  258,   41,  278,    0,
   59,   59,   59,  261,  125,  224,   41,  258,  257,  196,
  197,  260,  257,  125,  257,  264,  257,  257,   40,  261,
   59,   59,   58,  272,  273,  257,   41,  125,  257,  257,
  259,  259,  257,  258,  257,    0,  257,  258,  269,  270,
  271,  268,  185,   44,  276,  277,  257,  258,  257,  258,
  125,  257,  258,  257,  258,  269,  270,  271,   59,    0,
   44,   59,  123,   41,   58,   44,   59,  257,  123,   59,
  260,  125,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,  125,  260,   59,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   59,  260,   59,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,    0,   59,  260,
  125,  262,  263,  264,  265,  266,  267,   59,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  263,  260,    0,  262,  263,  264,  265,  266,  267,
  269,  270,  271,   44,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   59,  125,  260,   59,  262,  263,  264,
  265,  266,  267,    0,  272,   40,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  123,   41,   44,  261,  125,
  257,  125,  123,  260,   58,  262,  263,  264,  265,  266,
  267,    0,   59,   41,   41,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,  125,  260,   41,  262,  263,
  264,  265,  266,  267,   66,  218,  103,   13,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   13,   69,  260,
   13,   13,  125,  264,  265,  266,  257,   13,  197,  260,
   95,  272,  273,  264,  178,  276,  277,  278,  279,   85,
  176,  272,  273,   -1,   -1,   -1,   -1,  278,  125,   -1,
   -1,   -1,  257,   -1,   -1,  260,  123,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,   -1,  257,   -1,  125,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,  125,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,  257,  260,   -1,  260,   -1,  264,  265,  264,   -1,
   -1,  125,   -1,   -1,  272,  273,  272,  273,  276,  277,
  278,  279,  278,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  125,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  125,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,  125,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
  257,  125,   -1,  260,   -1,  272,  273,  264,   -1,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,   -1,
  257,  278,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,   -1,   -1,  278,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
   -1,  275,   -1,  257,  278,   -1,  260,   -1,  262,  263,
  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
  257,   -1,   -1,  260,  278,   -1,   -1,  264,   -1,   -1,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,   -1,
   -1,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
   -1,  275,   -1,   -1,  278,
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
"programa : nombre_programa '{' sentencias '}'",
"programa : '{' sentencias '}'",
"programa : nombre_programa sentencias '}'",
"programa : nombre_programa '{' sentencias",
"programa : nombre_programa '{' '}'",
"nombre_programa : ID",
"sentencias : sentencia",
"sentencias : sentencia sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : sentencia_declarativa_variables",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
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
"parametro : ID",
"declaracion_constantes : CONST lista_declaracion_constantes ';'",
"declaracion_constantes : CONST lista_declaracion_constantes",
"declaracion_constantes : CONST ';'",
"lista_declaracion_constantes : declaracion_constante",
"lista_declaracion_constantes : declaracion_constante ',' lista_declaracion_constantes",
"declaracion_constante : ID ASIGNACION CTE",
"declaracion_constante : ID ASIGNACION",
"declaracion_constante : ID CTE",
"declaracion_constante : ID",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : DEFER asignacion",
"sentencia_ejecutable : seleccion",
"sentencia_ejecutable : DEFER seleccion",
"sentencia_ejecutable : imprimir",
"sentencia_ejecutable : DEFER imprimir",
"sentencia_ejecutable : sentencia_when",
"sentencia_ejecutable : DEFER sentencia_when",
"sentencia_ejecutable : sentencia_do",
"sentencia_ejecutable : DEFER sentencia_do",
"sentencia_ejecutable_do : sentencia_ejecutable",
"sentencia_ejecutable_do : sentencia_break",
"sentencia_ejecutable_do : sentencia_continue",
"sentencia_break : BREAK ';'",
"sentencia_break : BREAK ':' etiqueta ';'",
"sentencia_break : BREAK",
"sentencia_break : BREAK ':' etiqueta",
"sentencia_break : BREAK ':' ';'",
"sentencia_continue : CONTINUE ';'",
"sentencia_continue : CONTINUE",
"sentencia_do : sentencia_do_simple",
"sentencia_do : etiqueta ':' sentencia_do_simple",
"sentencia_do_simple : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')' ';'",
"sentencia_do_simple : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"sentencia_do_simple : DO bloque_sentencias_ejecutables_do UNTIL '(' ')' ';'",
"sentencia_do_simple : DO bloque_sentencias_ejecutables_do UNTIL condicion ')' ';'",
"sentencia_do_simple : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ';'",
"etiqueta : ID",
"bloque_sentencias_ejecutables_do : sentencia_ejecutable_do",
"bloque_sentencias_ejecutables_do : '{' sentencias_ejecutables_do '}'",
"sentencias_ejecutables_do : sentencia_ejecutable_do",
"sentencias_ejecutables_do : sentencias_ejecutables_do sentencia_ejecutable_do",
"asignacion : ID ASIGNACION expresion ';'",
"asignacion : ID ASIGNACION ';'",
"asignacion : ID ASIGNACION expresion",
"sentencia_when : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' bloque_sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' THEN '{' bloque_sentencias_when '}' ';' $$1 WHEN '(' condicion ')' THEN '{' bloque_sentencias_when '}'",
"bloque_sentencias_when : sentencia",
"bloque_sentencias_when : sentencia sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_seleccion : sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"$$2 :",
"condicion : $$2 comparador expresion",
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
"imprimir : OUT '(' CADENA ')' ';'",
"imprimir : OUT '(' ID ')' ';'",
"imprimir : OUT '(' ')' ';'",
"imprimir : OUT CADENA ')' ';'",
"imprimir : OUT '(' CADENA ';'",
"imprimir : OUT ID ')' ';'",
"imprimir : OUT '(' ID ';'",
"imprimir : OUT '(' CADENA ')'",
"imprimir : OUT '(' ID ')'",
"constante : CTE",
"constante : '-' CTE",
"tipo : UINT16",
"tipo : DOUBLE64",
};

//#line 266 ".\gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		
		String negConstante = "-"+constante;
		
		Double parsedDouble = Double.parseDouble(negConstante.replace('D', 'E'));
		
		if (parsedDouble < -2.2250738585072014E-308 && -1.7976931348623157E+308 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-1.7976931348623157E+308 < parsedDouble) {
				negConstante = new String("-1.7976931348623157D+308");
			} else {
				negConstante =  new String("-2.2250738585072014D-308");
			}
		}
		
		ts.swapLexemas(constante, negConstante);
	} else {
		//se recibio un uint que fue aceptado por el lexico pero resulta ser negativo
		logger.logWarning("[Parser] No se admiten ui16 con valores negativos: " + "-"+constante + ", se trunca a 0");
		
		ts.swapLexemas(constante, "0");
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
//#line 625 "Parser.java"
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
{ logger.logError("[Parser] Se esperaba un identificador nombre del programa"); }
break;
case 3:
//#line 18 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); }
break;
case 4:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); }
break;
case 5:
//#line 20 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias del programa"); }
break;
case 14:
//#line 44 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 15:
//#line 45 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 16:
//#line 46 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 17:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 20:
//#line 56 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 21:
//#line 57 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 27:
//#line 72 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 31:
//#line 82 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 32:
//#line 86 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 34:
//#line 88 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 38:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 39:
//#line 99 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 40:
//#line 100 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 54:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 55:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 56:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 57:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 58:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 59:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 60:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 63:
//#line 141 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 64:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 65:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 66:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 67:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 73:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 74:
//#line 164 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 75:
//#line 165 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 76:
//#line 169 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 77:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 78:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 79:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 82:
//#line 181 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 83:
//#line 182 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 86:
//#line 188 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 87:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 92:
//#line 199 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 93:
//#line 200 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 114:
//#line 245 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 118:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 119:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 120:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 121:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 122:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 124:
//#line 258 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 990 "Parser.java"
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
