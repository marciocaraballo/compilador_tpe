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
    4,    4,    4,    6,    6,    6,   10,   10,    7,    7,
   11,   11,   12,   13,   13,   13,   16,   16,   15,   15,
    8,    8,    8,   17,   17,   18,   18,   18,   18,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,   24,
   24,   24,   25,   25,   25,   25,   25,   26,   26,   23,
   23,   28,   28,   28,   28,   28,   27,   29,   29,   31,
   31,   19,   19,   19,   22,   22,   22,   22,   33,   22,
   32,   32,   20,   20,   34,   34,   34,   34,   35,   35,
   30,   30,   37,   30,   36,   36,   36,   36,   36,   36,
   14,   14,   14,   38,   38,   38,   39,   39,   39,   41,
   42,   42,   43,   43,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    3,    1,    4,    3,
    6,    7,    6,    1,    3,    5,    1,    3,    2,    1,
    3,    2,    2,    1,    3,    3,    2,    2,    1,    1,
    2,    2,    3,    1,    2,    1,    2,    1,    2,    1,
    1,    1,    2,    4,    1,    3,    3,    2,    1,    1,
    3,    7,    6,    6,    6,    6,    1,    1,    3,    1,
    2,    4,    3,    3,    9,    8,    8,    8,    0,   15,
    1,    2,    7,    9,    1,    3,    2,    2,    1,    2,
    3,    2,    0,    3,    1,    1,    1,    1,    1,    1,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    4,
    1,    3,    1,    1,    5,    5,    4,    4,    4,    4,
    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  127,  126,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   40,    0,   44,   46,   48,    0,
   60,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  124,    0,    0,    0,    0,    0,    0,  106,  108,
  109,    0,    0,    0,    0,   50,   68,   51,   52,    0,
   41,    0,   45,   47,   49,    0,   33,    0,    0,    2,
    8,    0,    0,   16,    0,    0,    0,   42,    0,    5,
    0,    3,   73,    0,   17,    0,    0,    0,    0,    0,
    0,    0,    0,  125,    0,    0,   95,   96,   97,    0,
    0,   98,   99,  100,    0,    0,    0,    0,    0,   53,
    0,   58,   70,    0,    0,   43,   38,    0,   31,    0,
   14,    0,    0,   20,   61,    1,   72,    0,  120,  118,
  121,    0,  119,    0,  117,   30,    0,    0,    0,    0,
  113,  114,    0,  111,    0,    0,    0,    0,    0,    0,
    0,    0,  104,  105,   67,   57,    0,   69,   71,    0,
    0,   36,   35,   19,    0,    0,  116,  115,    0,   29,
    0,    0,    0,  110,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,    0,    0,    0,    0,   21,
    0,    0,  112,   82,    0,    0,    0,    0,    0,   64,
   66,    0,   65,    0,   89,    0,    0,   83,   87,   90,
   22,    0,   79,    0,    0,    0,    0,   62,   23,   86,
    0,    0,   26,    0,   77,    0,   78,   76,   84,    0,
    0,   75,   28,    0,    0,    0,    0,    0,   80,
};
final static short yydgoto[] = {                          3,
    4,   76,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   77,  139,   45,  222,  223,   68,   69,   25,   26,
   27,   28,   29,   57,   58,   59,   30,   31,   60,   46,
  114,  176,  224,  188,  189,  105,   47,   48,   49,   50,
   51,  143,  144,
};
final static short yysindex[] = {                      -106,
    0, -117,    0,  -73,  -42,    9,  -34, -191,  -13,  302,
    0,    0,  284,  -48,  -30, -117,    0,    0,    0,    0,
    0, -137,   63,  273,    0,   69,    0,    0,    0,   75,
    0,  429,   10,  -36, -137,  -38,  101,  104,   -6,  112,
  114,    0,  -90,  105,  -40,  130,  397,   77,    0,    0,
    0,  -94,   23,  119,  547,    0,    0,    0,    0,  -95,
    0,  121,    0,    0,    0, -194,    0,  122,  138,    0,
    0,  139,  126,    0, -117,  -78,   71,    0,  -76,    0,
   73,    0,    0,   14,    0,  153,  151,  154,   -8,   12,
  155,  -41,  -20,    0,   92,  -37,    0,    0,    0,  -38,
  -38,    0,    0,    0,  -38,  -44,  -38,  -38,  -38,    0,
  -45,    0,    0,  510,  -11,    0,    0,  -19,    0,  -17,
    0,  117,  203,    0,    0,    0,    0,   -9,    0,    0,
    0,  189,    0,  195,    0,    0,  197,   -1,  218,  213,
    0,    0,   31,    0, -117,  137, -108,   77,   77,   51,
  160,   51,    0,    0,    0,    0,  246,    0,    0,  128,
  265,    0,    0,    0,  -38,  412,    0,    0, -174,    0,
  271, -221,  -20,    0, -117,  226, -117,  215, -117, -117,
    0,  305,   17,  315,   82,  216,    0, -158,  -71,    0,
 -174,  317,    0,    0,  325,  272, -117,  274,  275,    0,
    0,  343,    0,  344,    0,  537,  412,    0,    0,    0,
    0, -221,    0,  346,  287,  355,  356,    0,    0,    0,
  163,  384,    0,  165,    0,  376,    0,    0,    0, -221,
  398,    0,    0,  -38,  407,  326, -117,  327,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   80,    0,    0,    0,  410,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  410,    0,    0,    0,    0,
    1,    0,    0,  410,    0,    0,    0,   24,    0,    0,
    0,  393,  453,  472,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  190,    0,  227,  328,    0,
    0,  377,  250,    0,    0,    0,    0,    0,    0,    0,
  460,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -33,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  410,    0,    0,  354,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  116,    0,  144,    0,    0,    0,    0,    0,  420,
    0,    0,    0,    0,    0,    0,    0,   47,   70,  -29,
    0,  -28,    0,    0,    0,    0,  491,    0,    0,  410,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  339,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  527,    0,    0,    0,
    0,  424,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  167,    0,    0,    0,  -99,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  433,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  410,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   36, -100,    0,  485,    0,    0,    0,  -55,   56,
    0,  400,    0,    5,  -51,  247,  358,    0,  468,  469,
  470,  473,  477,  -27,    0,    0,  374,  417,    0,  -26,
    0,  -79,    0,  290,  312,  454,    0,   65,   68,  -74,
    0,    0,  335,
};
final static int YYTABLESIZE=825;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        137,
  107,   35,  100,  147,  101,   39,   43,   92,   43,   86,
   67,   91,   94,  156,  179,    7,    2,   96,  142,  103,
  104,  102,   83,  103,   43,   92,   44,  113,  160,   91,
   94,   43,  132,   43,   91,  136,  138,   15,   84,   33,
  140,  107,  107,  107,  175,  107,  101,  107,   36,   32,
  131,   71,  134,  209,   11,   12,  100,  202,  101,  107,
  107,  107,  107,  117,  103,   40,  103,   81,  103,  102,
  133,  174,  127,  118,  173,  201,  175,   73,  175,  175,
  111,  110,  103,  103,  103,  103,  159,  101,  161,  101,
   85,  101,   74,  100,   70,  101,  175,  196,  142,  198,
  199,   11,   12,  207,  208,  101,  101,  101,  101,  150,
  102,  152,  102,  190,  102,  123,  138,  215,  108,   72,
  192,   74,  204,  109,  100,  107,  101,   78,  102,  102,
  102,  102,   79,  183,   82,  211,  175,   67,   18,    5,
    7,   87,    6,  122,   88,   95,    7,    8,  103,   43,
    1,   92,  178,   93,    9,   10,  138,  238,   11,   12,
   13,   14,   88,   88,  148,  149,   63,   94,  182,  185,
  106,  101,   43,   34,  138,  153,  154,  112,  115,  116,
  119,  120,   35,    5,  121,   52,    6,  123,    6,   39,
    7,    8,    7,  128,  102,  124,   10,  126,    9,   10,
    9,   10,   11,   12,   13,   14,   13,  235,   66,  129,
  194,  155,  130,  135,  145,  136,  151,   74,   41,   42,
   41,   42,   37,  146,   38,   34,   32,   92,   97,   98,
   99,   91,   94,   39,   11,   12,  141,   42,  162,   66,
  123,  164,  165,   41,   42,   41,   42,  167,   39,   15,
   89,  166,   90,  168,  169,  170,  172,  107,  171,  177,
  107,  107,  107,  107,  107,  107,  107,  107,  122,  107,
  107,  107,  107,  107,  107,  107,  107,  107,  107,  107,
  103,    7,  180,  103,  103,  103,  103,  103,  103,  103,
  103,   63,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  101,  181,  184,  101,  101,  101,  101,
  101,  101,  101,  101,   39,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,  101,  102,   34,  191,  102,
  102,  102,  102,  102,  102,  102,  102,  197,  102,  102,
  102,  102,  102,  102,  102,  102,  102,  102,  102,   74,
  195,   32,   74,   37,   74,   74,   74,   74,   74,   74,
  212,   41,   42,  200,   74,   74,   74,   74,   74,   74,
   74,   74,  123,  203,   15,  123,   18,  123,  123,  123,
  123,  123,  123,  213,   41,   42,   34,  123,  123,  123,
  123,  123,  123,  123,  123,   75,  214,   37,  216,  217,
  122,  218,  219,  122,  225,  122,  122,  122,  122,  122,
  122,  226,   37,  227,  228,  122,  122,  122,  122,  122,
  122,  122,  122,   63,   55,  229,   63,  230,   63,   63,
   63,   63,   63,   63,  232,   18,  231,  234,   63,   63,
   63,   63,   63,   63,   63,   63,   39,  236,  237,   39,
   67,  239,   34,   39,   39,   39,  103,  104,  102,    4,
   24,   39,   39,   81,   25,   39,   39,   39,   39,   93,
   93,   93,   52,   27,  122,    6,  233,  163,   37,    7,
   61,   62,   63,   32,  157,   64,   32,    9,   10,   65,
   32,   32,   32,   13,   56,  125,  221,  206,   32,   32,
  107,   18,   32,   32,   32,   32,   15,  193,    0,   15,
    0,    0,    0,   15,   15,   15,    0,    0,    0,    0,
    0,   15,   15,    0,    0,   15,   15,   15,   15,    5,
    0,    0,    6,    0,  186,    0,    7,    8,    0,   56,
   52,    0,    0,    6,    9,   10,    0,    7,   11,   12,
   13,   14,    0,   80,    0,    9,   10,    0,   52,    0,
    0,    6,    0,    0,    0,    7,    0,    0,   53,    0,
    0,    0,    0,    9,   10,    0,   54,   55,    0,   13,
    0,    0,    0,    0,   34,    0,    0,   34,    0,    0,
    0,   34,   34,   34,    0,    0,   59,    0,   56,   34,
   34,    0,    0,   34,   34,   34,   34,    0,    0,    0,
   37,    0,    0,   37,    0,   56,    0,   37,   37,   37,
    0,    0,    0,    0,    0,   37,   37,    0,    0,   37,
   37,   37,   37,   18,  158,    0,   18,    0,    0,    0,
   18,   18,   18,    0,    0,    0,    0,    0,   18,   18,
  187,   89,   18,   18,   18,   18,    0,    0,    0,    0,
    0,  220,    0,    0,    0,   97,   98,   99,   52,    0,
  205,    6,    0,  210,    0,    7,    0,    0,   93,   93,
   93,    0,    0,    9,   10,    5,    0,    0,    6,   13,
  210,  187,    7,    8,    0,    0,    0,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,    0,   55,
    0,    0,   55,    0,    0,    0,   55,    0,    0,   55,
    0,    0,    0,    0,   55,   55,   55,   55,   59,    0,
   55,   59,    0,    0,    0,   59,    0,    0,   59,    0,
    0,    0,    0,   59,   59,   59,   59,   56,    0,   59,
   56,    0,    0,    0,   56,    0,    0,   56,    0,    0,
    0,    0,   56,   56,   56,   56,   52,    0,   56,    6,
    0,    0,    0,    7,    0,    0,   53,    0,    0,    0,
    0,    9,   10,   89,   54,    0,   89,   13,   85,   85,
   89,    0,    0,   52,    0,    0,    6,    0,   89,   89,
    7,    0,    0,   52,   89,    0,    6,    0,    9,   10,
    7,    0,    0,   53,   13,    0,    0,    0,    9,   10,
    0,   54,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   44,   43,   41,   45,   40,   45,   41,   45,   36,
   59,   41,   41,   59,  123,    0,  123,   44,   93,   60,
   61,   62,   59,    0,   45,   59,   40,   55,   40,   59,
   59,   45,   41,   45,   41,  257,   92,    2,   34,    4,
   92,   41,   42,   43,  145,   45,    0,   47,   40,  123,
   59,   16,   41,  125,  276,  277,   43,   41,   45,   59,
   60,   61,   62,  258,   41,  257,   43,   32,   45,    0,
   59,   41,   59,  268,   44,   59,  177,   22,  179,  180,
   58,   59,   59,   60,   61,   62,  114,   41,  115,   43,
   35,   45,    0,   43,  125,   45,  197,  177,  173,  179,
  180,  276,  277,  262,  263,   59,   60,   61,   62,  105,
   41,  107,   43,  169,   45,    0,  172,  197,   42,  257,
  172,   59,   41,   47,   43,  125,   45,   59,   59,   60,
   61,   62,   58,  160,  125,  191,  237,   58,   59,  257,
  125,   41,  260,    0,   41,   41,  264,  265,  125,   45,
  257,   40,  261,   40,  272,  273,  212,  237,  276,  277,
  278,  279,  262,  263,  100,  101,    0,  258,   41,  165,
   41,  125,   45,  268,  230,  108,  109,   59,  274,   59,
   59,   44,   44,  257,   59,  257,  260,  266,  260,    0,
  264,  265,  264,   41,  125,  125,  273,  125,  272,  273,
  272,  273,  276,  277,  278,  279,  278,  234,  257,   59,
  175,  257,   59,   59,  123,  257,  261,  125,  257,  258,
  257,  258,  257,  261,  259,  268,    0,  261,  269,  270,
  271,  261,  261,   44,  276,  277,  257,  258,  258,  257,
  125,  125,   40,  257,  258,  257,  258,   59,   59,    0,
  257,  261,  259,   59,   58,  257,   44,  257,   41,  123,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,  123,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   41,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,    0,   58,  260,
  261,  262,  263,  264,  265,  266,  267,  123,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  125,  260,    0,  262,  263,  264,  265,  266,  267,
   44,  257,  258,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   59,  125,  260,    0,  262,  263,  264,
  265,  266,  267,   59,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  123,  125,   44,  125,  125,
  257,   59,   59,  260,   59,  262,  263,  264,  265,  266,
  267,  125,   59,   59,   59,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,  263,  260,   44,  262,  263,
  264,  265,  266,  267,   59,   59,  272,   40,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   41,  123,  260,
   58,  125,  125,  264,  265,  266,   60,   61,   62,    0,
   41,  272,  273,  125,   41,  276,  277,  278,  279,   60,
   61,   62,  257,   41,   75,  260,  230,  120,  125,  264,
   13,   13,   13,  257,  111,   13,  260,  272,  273,   13,
  264,  265,  266,  278,   10,   79,  207,  186,  272,  273,
   47,  125,  276,  277,  278,  279,  257,  173,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,  123,   -1,  264,  265,   -1,   55,
  257,   -1,   -1,  260,  272,  273,   -1,  264,  276,  277,
  278,  279,   -1,  125,   -1,  272,  273,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,   -1,  275,  125,   -1,  278,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,   -1,   -1,  125,   -1,  114,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,  125,   -1,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,  125,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
  166,  125,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,  125,   -1,   -1,   -1,  269,  270,  271,  257,   -1,
  186,  260,   -1,  189,   -1,  264,   -1,   -1,  269,  270,
  271,   -1,   -1,  272,  273,  257,   -1,   -1,  260,  278,
  206,  207,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  257,  275,   -1,  260,  278,  262,  263,
  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,   -1,   -1,  257,  278,   -1,  260,   -1,  272,  273,
  264,   -1,   -1,  267,  278,   -1,   -1,   -1,  272,  273,
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
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : DEFER seleccion ';'",
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
"sentencia_when : WHEN condicion ')' THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' bloque_sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' '{' bloque_sentencias_when '}' ';' $$1 WHEN '(' condicion ')' '{' bloque_sentencias_when '}'",
"bloque_sentencias_when : sentencia",
"bloque_sentencias_when : sentencia sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF",
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

//#line 267 ".\gramatica.y"

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
//#line 611 "Parser.java"
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
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 16:
//#line 46 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 19:
//#line 55 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 20:
//#line 56 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 26:
//#line 71 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 30:
//#line 81 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 31:
//#line 85 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 32:
//#line 86 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 37:
//#line 97 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 38:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 39:
//#line 99 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 53:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 54:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 55:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 56:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 57:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 58:
//#line 130 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 59:
//#line 131 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 62:
//#line 140 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 63:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 64:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 65:
//#line 143 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 66:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 72:
//#line 162 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 73:
//#line 163 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 74:
//#line 164 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 75:
//#line 168 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 76:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 77:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 78:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 79:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 80:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 83:
//#line 182 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 84:
//#line 183 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 87:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 88:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 93:
//#line 200 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 94:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 118:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 119:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 120:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 121:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 122:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 123:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 125:
//#line 259 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 980 "Parser.java"
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
