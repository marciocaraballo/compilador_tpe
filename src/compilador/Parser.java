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
    0,    0,    0,    0,    1,    2,    2,    3,    3,    4,
    4,    4,    6,    6,    6,   10,   10,    7,    7,   11,
   11,   12,   13,   13,   13,   16,   16,   15,   15,    8,
    8,    8,   17,   17,   18,   18,   18,   18,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   24,   24,
   24,   25,   25,   25,   25,   25,   26,   26,   23,   23,
   28,   28,   28,   28,   28,   27,   29,   29,   31,   31,
   19,   19,   19,   22,   22,   22,   22,   33,   22,   32,
   32,   20,   20,   34,   34,   34,   34,   35,   35,   30,
   30,   37,   30,   36,   36,   36,   36,   36,   36,   14,
   14,   14,   38,   38,   38,   39,   39,   39,   41,   42,
   42,   43,   43,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    1,    1,    2,    1,    1,    1,
    1,    1,    3,    2,    2,    3,    1,    4,    3,    6,
    7,    6,    1,    3,    5,    1,    3,    2,    1,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    2,    3,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    8,    8,    0,   15,    1,
    2,    7,    9,    1,    3,    2,    2,    1,    2,    3,
    2,    0,    3,    1,    1,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    4,    1,
    3,    1,    1,    5,    5,    4,    4,    4,    4,    4,
    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  126,  125,    0,    0,    0,    0,    8,    9,   10,   11,
   12,    0,    0,    0,   39,    0,   43,   45,   47,    0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,    0,    0,    0,    0,    0,    0,  105,  107,
  108,    0,    0,    0,    0,   49,   67,   50,   51,    0,
   40,    0,   44,   46,   48,    0,   32,    0,    0,    2,
    7,    0,    0,   15,    0,    0,    0,   41,    0,    0,
    3,   72,    0,   16,    0,    0,    0,    0,    0,    0,
    0,    0,  124,    0,    0,   94,   95,   96,    0,    0,
   97,   98,   99,    0,    0,    0,    0,    0,   52,    0,
   57,   69,    0,    0,   42,   37,    0,   30,    0,   13,
    0,    0,   19,   60,    1,   71,    0,  119,  117,  120,
    0,  118,    0,  116,   29,    0,    0,    0,    0,  112,
  113,    0,  110,    0,    0,    0,    0,    0,    0,    0,
    0,  103,  104,   66,   56,    0,   68,   70,    0,    0,
   35,   34,   18,    0,    0,  115,  114,    0,   28,    0,
    0,    0,  109,    0,    0,    0,    0,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,   20,    0,
    0,  111,   81,    0,    0,    0,    0,    0,   63,   65,
    0,   64,    0,   88,    0,    0,   82,   86,   89,   21,
    0,   78,    0,    0,    0,    0,   61,   22,   85,    0,
    0,   25,    0,   76,    0,   77,   75,   83,    0,    0,
   74,   27,    0,    0,    0,    0,    0,   79,
};
final static short yydgoto[] = {                          3,
    4,   76,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   77,  138,   45,  221,  222,   68,   69,   25,   26,
   27,   28,   29,   57,   58,   59,   30,   31,   60,   46,
  113,  175,  223,  187,  188,  104,   47,   48,   49,   50,
   51,  142,  143,
};
final static short yysindex[] = {                      -106,
    0,  530,    0,  -73,  -42,   -3,  -34, -206,  -13,  302,
    0,    0, -117,  -49,  -59,  530,    0,    0,    0,    0,
    0, -185,   20,  273,    0,   28,    0,    0,    0,   43,
    0,  530,   -5,  -36, -185,  -38,   73,   81,   -6,   85,
   87,    0, -123,  105,  -40,   97,  397,   33,    0,    0,
    0, -120,   95,   86,  553,    0,    0,    0,    0, -108,
    0,  115,    0,    0,    0, -209,    0,  118,  134,    0,
    0,  135,  121,    0,  530,  -85,   57,    0,  -90,   60,
    0,    0,   74,    0,  147,  139,  152,   23,   30,  153,
  -41,  -20,    0,   90,  -37,    0,    0,    0,  -38,  -38,
    0,    0,    0,  -38,  -46,  -38,  -38,  -38,    0,  -48,
    0,    0,  490,  -11,    0,    0,  -19,    0,  -17,    0,
   92,  203,    0,    0,    0,    0,   -9,    0,    0,    0,
  189,    0,  196,    0,    0,  198,    2,  216,  239,    0,
    0,   37,    0,  530,  137, -109,   33,   33,   67,  182,
   67,    0,    0,    0,    0,  247,    0,    0,  128,  288,
    0,    0,    0,  -38,  412,    0,    0, -219,    0,  280,
 -221,  -20,    0,  530,  226,  530,  238,  530,  530,    0,
  305,   32,  315,   59, -136,    0, -104,  -71,    0, -219,
  340,    0,    0,  338,  274,  530,  275,  277,    0,    0,
  344,    0,  346,    0,  520,  412,    0,    0,    0,    0,
 -221,    0,  353,  289,  356,  367,    0,    0,    0,  165,
  391,    0,  166,    0,  378,    0,    0,    0, -221,  408,
    0,    0,  -38,  411,  326,  530,  335,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  102,    0,    0,    0,  410,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  410,    0,    0,    0,    0,
    1,    0,    0,  410,    0,    0,    0,   24,    0,    0,
    0,  393,  429,  448,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  190,    0,  227,  328,    0,
    0,  377,  250,    0,    0,    0,    0,    0,    0,  461,
    0,    0,   93,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -33,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  410,    0,    0,  354,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,  144,    0,    0,    0,    0,    0,  423,    0,
    0,    0,    0,    0,    0,    0,   47,   70,  -29,    0,
  -28,    0,    0,    0,    0,  470,    0,    0,  410,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  348,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  510,    0,    0,    0,    0,
  424,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  167,    0,    0,    0,  -98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  433,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  410,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   36,   18,    0,  486,    0,    0,    0,  -72,    6,
    0,  400,    0,   -1,  -76,  248,  357,    0,  465,  467,
  468,  469,  472,  -16,    0,    0,  373,  407,    0,    9,
    0, -102,    0,  282,  304,  443,    0,   71,   68,  -74,
    0,    0,  322,
};
final static int YYTABLESIZE=831;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        136,
  106,   35,   99,  146,  100,   39,   43,   91,   43,   67,
  155,   90,   93,  178,  139,    6,    2,  141,  137,  102,
  103,  101,   82,  102,   43,   91,   44,   73,  159,   90,
   93,   43,   83,   43,   90,  135,   36,   15,  112,   33,
   84,  106,  106,  106,   85,  106,  100,  106,  116,   32,
   40,   71,   95,  208,   11,   12,   11,   12,  117,  106,
  106,  106,  106,  131,  102,   70,  102,   80,  102,  101,
  133,   72,  201,  195,  107,  197,  198,  173,   74,  108,
  172,  130,  102,  102,  102,  102,   78,  100,  132,  100,
  200,  100,   73,  214,  191,  189,  158,  141,  137,  203,
   79,   99,  149,  100,  151,  100,  100,  100,  100,   99,
  101,  100,  101,   86,  101,  122,   99,  210,  100,   81,
   52,   87,  160,    6,   91,  106,   92,    7,  101,  101,
  101,  101,  126,  237,   93,    9,   10,  105,  137,   52,
    6,   13,    6,  121,  111,   94,    7,   34,  102,   43,
    1,  177,  110,  109,    9,   10,  137,  206,  207,   66,
   17,  174,  184,   87,   87,  114,   62,  182,  181,  147,
  148,  100,   43,  115,  152,  153,  118,  119,   35,  120,
  122,  123,   10,    5,  125,   52,    6,  127,    6,   38,
    7,    8,    7,  174,  101,  174,  174,  128,    9,   10,
    9,   10,   11,   12,   13,   14,   13,   66,  154,  193,
  129,  134,  144,  174,  150,  135,  163,   73,   41,   42,
   41,   42,   37,  145,   38,   34,   31,   91,   96,   97,
   98,   90,   93,   38,   11,   12,  140,   42,  161,   66,
  122,  234,  164,   41,   42,   41,   42,  166,   38,   14,
   88,  165,   89,  174,  167,  168,  170,  106,  169,  176,
  106,  106,  106,  106,  106,  106,  106,  106,  121,  106,
  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,
  102,    6,  171,  102,  102,  102,  102,  102,  102,  102,
  102,   62,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  100,  179,  180,  100,  100,  100,  100,
  100,  100,  100,  100,   38,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  101,   33,  183,  101,
  101,  101,  101,  101,  101,  101,  101,  190,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,  101,   73,
  194,   31,   73,   36,   73,   73,   73,   73,   73,   73,
  196,   41,   42,  199,   73,   73,   73,   73,   73,   73,
   73,   73,  122,  202,   14,  122,   17,  122,  122,  122,
  122,  122,  122,  211,   41,   42,   33,  122,  122,  122,
  122,  122,  122,  122,  122,   75,  212,   36,  213,  215,
  121,  216,  217,  121,  218,  121,  121,  121,  121,  121,
  121,  224,   36,  225,  226,  121,  121,  121,  121,  121,
  121,  121,  121,   62,   55,  227,   62,  228,   62,   62,
   62,   62,   62,   62,  229,   17,  231,  230,   62,   62,
   62,   62,   62,   62,   62,   62,   38,  233,  236,   38,
   66,  235,   33,   38,   38,   38,  102,  103,  101,  238,
    4,   38,   38,   23,   24,   38,   38,   38,   38,   92,
   92,   92,   80,   26,  121,  162,  232,   61,   36,   62,
   63,   64,  156,   31,   65,  124,   31,  220,  205,  106,
   31,   31,   31,  192,    0,   56,    0,    0,   31,   31,
    0,   17,   31,   31,   31,   31,   14,    0,    0,   14,
    0,    0,    0,   14,   14,   14,    0,    0,    0,    0,
    0,   14,   14,    0,    0,   14,   14,   14,   14,    5,
    0,    0,    6,    0,  185,    0,    7,    8,    0,    0,
   56,    0,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,    0,   54,    0,    0,    0,    0,   52,    0,
    0,    6,    0,    0,    0,    7,    0,    0,   53,    0,
    0,    0,   58,    9,   10,    0,   54,    0,    0,   13,
    0,    0,    0,    0,   33,    0,    0,   33,    0,    0,
    0,   33,   33,   33,   55,    0,    0,    0,   56,   33,
   33,    0,    0,   33,   33,   33,   33,    0,    0,    0,
   36,    0,    0,   36,  157,    0,    0,   36,   36,   36,
    0,    0,    0,    0,    0,   36,   36,    0,    0,   36,
   36,   36,   36,   17,   88,    0,   17,    0,    0,    0,
   17,   17,   17,    0,  219,    0,    0,    0,   17,   17,
  186,    0,   17,   17,   17,   17,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   96,   97,   98,   52,    0,
  204,    6,    0,  209,    0,    7,    0,    0,   92,   92,
   92,    0,    0,    9,   10,   54,    0,    0,   54,   13,
  209,  186,   54,    0,    0,   54,    0,    0,    0,    0,
   54,   54,   54,   54,   58,    0,   54,   58,    0,    0,
    0,   58,    0,    0,   58,    0,    0,    0,    0,   58,
   58,   58,   58,    0,    0,   58,   55,    0,    0,   55,
    0,    0,    0,   55,    0,    0,   55,    0,    0,    0,
    0,   55,   55,   55,   55,    0,   52,   55,    0,    6,
    0,    0,    0,    7,    0,    0,   53,    0,    0,    0,
    0,    9,   10,    0,   54,    0,   88,   13,    0,   88,
    0,   84,   84,   88,    0,    0,   52,    0,    0,    6,
    0,   88,   88,    7,    0,    0,    5,   88,    0,    6,
    0,    9,   10,    7,    8,    0,    0,   13,    0,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,   52,
    0,    0,    6,    0,    0,    0,    7,    0,    0,   53,
    0,    0,    0,    0,    9,   10,    0,   54,    0,    0,
   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   44,   43,   41,   45,   40,   45,   41,   45,   59,
   59,   41,   41,  123,   91,    0,  123,   92,   91,   60,
   61,   62,   59,    0,   45,   59,   40,   22,   40,   59,
   59,   45,   34,   45,   41,  257,   40,    2,   55,    4,
   35,   41,   42,   43,   36,   45,    0,   47,  258,  123,
  257,   16,   44,  125,  276,  277,  276,  277,  268,   59,
   60,   61,   62,   41,   41,  125,   43,   32,   45,    0,
   41,  257,   41,  176,   42,  178,  179,   41,   59,   47,
   44,   59,   59,   60,   61,   62,   59,   41,   59,   43,
   59,   45,    0,  196,  171,  168,  113,  172,  171,   41,
   58,   43,  104,   45,  106,   59,   60,   61,   62,   43,
   41,   45,   43,   41,   45,    0,   43,  190,   45,  125,
  257,   41,  114,  260,   40,  125,   40,  264,   59,   60,
   61,   62,   59,  236,  258,  272,  273,   41,  211,  257,
  125,  278,  260,    0,   59,   41,  264,  268,  125,   45,
  257,  261,   58,   59,  272,  273,  229,  262,  263,   58,
   59,  144,  164,  262,  263,  274,    0,  159,   41,   99,
  100,  125,   45,   59,  107,  108,   59,   44,   44,   59,
  266,  125,  273,  257,  125,  257,  260,   41,  260,    0,
  264,  265,  264,  176,  125,  178,  179,   59,  272,  273,
  272,  273,  276,  277,  278,  279,  278,  257,  257,  174,
   59,   59,  123,  196,  261,  257,  125,  125,  257,  258,
  257,  258,  257,  261,  259,  268,    0,  261,  269,  270,
  271,  261,  261,   44,  276,  277,  257,  258,  258,  257,
  125,  233,   40,  257,  258,  257,  258,   59,   59,    0,
  257,  261,  259,  236,   59,   58,   41,  257,  257,  123,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,   44,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,    0,   41,  260,
  261,  262,  263,  264,  265,  266,  267,   58,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  125,  260,    0,  262,  263,  264,  265,  266,  267,
  123,  257,  258,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   59,  125,  260,    0,  262,  263,  264,
  265,  266,  267,   44,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  123,   59,   44,  125,  125,
  257,  125,   59,  260,   59,  262,  263,  264,  265,  266,
  267,   59,   59,  125,   59,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,   59,  260,  263,  262,  263,
  264,  265,  266,  267,   44,   59,   59,  272,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   40,  123,  260,
   58,   41,  125,  264,  265,  266,   60,   61,   62,  125,
    0,  272,  273,   41,   41,  276,  277,  278,  279,   60,
   61,   62,  125,   41,   75,  119,  229,   13,  125,   13,
   13,   13,  110,  257,   13,   79,  260,  206,  185,   47,
  264,  265,  266,  172,   -1,   10,   -1,   -1,  272,  273,
   -1,  125,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,  123,   -1,  264,  265,   -1,   -1,
   55,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,  125,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,  125,  272,  273,   -1,  275,   -1,   -1,  278,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,  125,   -1,   -1,   -1,  113,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
  257,   -1,   -1,  260,  125,   -1,   -1,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,  125,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,  125,   -1,   -1,   -1,  272,  273,
  165,   -1,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  269,  270,  271,  257,   -1,
  185,  260,   -1,  188,   -1,  264,   -1,   -1,  269,  270,
  271,   -1,   -1,  272,  273,  257,   -1,   -1,  260,  278,
  205,  206,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,  278,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,   -1,  257,  278,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,   -1,  275,   -1,  257,  278,   -1,  260,
   -1,  262,  263,  264,   -1,   -1,  257,   -1,   -1,  260,
   -1,  272,  273,  264,   -1,   -1,  257,  278,   -1,  260,
   -1,  272,  273,  264,  265,   -1,   -1,  278,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,
  278,
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

//#line 266 ".\gramatica.y"

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
//#line 612 "Parser.java"
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
case 3:
//#line 18 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); }
break;
case 4:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); }
break;
case 13:
//#line 43 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 14:
//#line 44 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 15:
//#line 45 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 54 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 19:
//#line 55 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 25:
//#line 70 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 29:
//#line 80 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 30:
//#line 84 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 31:
//#line 85 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 32:
//#line 86 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 36:
//#line 96 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 37:
//#line 97 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 38:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 52:
//#line 121 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 53:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 54:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 55:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 56:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 57:
//#line 129 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 58:
//#line 130 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 61:
//#line 139 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 62:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 63:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 64:
//#line 142 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 65:
//#line 143 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 71:
//#line 161 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 72:
//#line 162 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 73:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 74:
//#line 167 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 75:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 76:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
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
//#line 977 "Parser.java"
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
