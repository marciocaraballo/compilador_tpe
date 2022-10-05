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
    4,    4,    4,    4,    6,    6,    6,    6,   11,   11,
    7,    8,   13,   13,   16,   16,   14,   14,   18,   18,
   20,   20,   17,   17,   17,   17,   17,   17,   17,   17,
   23,   24,   24,   21,   26,   26,   22,   22,   28,   30,
   30,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   15,   15,   15,   15,   15,   32,   32,   32,   35,
   35,   34,   34,   36,   34,    9,    9,    9,   37,   37,
   38,   38,   38,   38,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    5,   44,   44,   44,   45,   45,   45,
   45,   45,   46,   46,   43,   43,   47,   47,   47,   47,
   47,   29,   48,   48,   31,   31,   39,   39,   39,   42,
   42,   49,   42,   27,   27,   40,   40,   40,   40,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   40,   50,   50,   50,   50,   25,   25,   19,   19,   52,
   19,   51,   51,   51,   51,   51,   51,   33,   33,   33,
   53,   53,   53,   54,   54,   54,   56,   56,   57,   57,
   58,   58,   41,   41,   41,   41,   41,   41,   41,   41,
   41,   55,   55,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    2,    1,    2,    1,    2,   10,   10,
    1,    4,    1,    1,    1,    2,    1,    2,    1,    2,
    8,    1,    4,    9,    1,    2,    1,    3,    7,    1,
    4,    6,    7,    5,    5,    5,    5,    6,    6,    6,
    6,    5,    4,    3,    3,    4,    1,    3,    5,    1,
    3,    2,    1,    0,    2,    3,    2,    2,    1,    3,
    3,    2,    2,    1,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    2,    1,    1,    1,    2,    4,    1,
    3,    3,    2,    1,    1,    3,    7,    6,    6,    6,
    6,    1,    1,    3,    1,    2,    4,    3,    3,    9,
    8,    0,   17,    1,    2,    8,   10,    7,    7,    7,
    7,    7,    7,    9,    9,    9,    9,    9,    9,    9,
    8,    1,    3,    2,    2,    1,    2,    3,    2,    0,
    3,    1,    1,    1,    1,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    3,    4,    1,    3,
    1,    1,    5,    5,    4,    4,    4,    4,    4,    4,
    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  185,  184,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   85,   87,   89,   91,
   93,  105,    0,    0,    0,    0,    0,  182,    0,    0,
    0,    0,    0,    0,  163,  165,  166,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   95,  113,   96,
   97,    0,   86,   88,   90,   92,   94,    0,   78,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  118,    0,   19,    0,  183,    0,    0,    0,
  152,  153,  154,    0,    0,  155,  156,  157,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   98,    0,  103,
    0,  115,    0,   83,    0,   76,    0,   15,    0,    0,
    0,    0,    0,   33,   34,    0,    0,   23,    0,   25,
   27,   35,   37,   39,   47,    0,  106,    1,  117,  171,
  167,  172,    0,  169,    0,    0,    0,    0,    0,    0,
    0,    0,  161,  162,  178,  176,  179,    0,  177,    0,
  175,    0,    0,    0,    0,   72,    0,    0,   75,    0,
    0,    0,    0,  112,  102,    0,  114,  116,    0,    0,
   81,   80,    0,    0,    0,    0,    0,   50,    0,    0,
   36,   38,   40,   21,   22,   24,   26,   28,    0,    0,
  168,    0,    0,    0,    0,    0,    0,    0,    0,  174,
  173,    0,   57,    0,    0,   56,    0,    0,   55,    0,
    0,    0,    0,   99,    0,    0,    0,    0,    0,    0,
   64,    0,    0,    0,    0,   48,  170,  146,    0,  144,
  147,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,   61,   60,    0,   59,    0,    0,
    0,    0,  109,  111,    0,  110,    0,   66,    0,    0,
    0,    0,    0,  143,    0,  130,    0,  128,    0,    0,
  133,    0,    0,    0,  131,    0,  129,   53,    0,   69,
  125,    0,    0,    0,  107,    0,   62,    0,   51,    0,
    0,    0,    0,  141,    0,    0,    0,  126,    0,    0,
    0,  122,    0,  121,    0,    0,    0,   31,    0,    0,
    0,    0,    0,  136,  134,  138,  139,    0,  137,  135,
   71,    0,  120,    0,    0,    0,    0,    0,    0,   45,
    0,    0,   49,    0,   42,  127,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,   46,    0,    0,   32,
   43,    0,    0,    0,   44,    0,    0,   29,    0,   30,
    0,    0,    0,    0,    0,    0,    0,    0,  123,
};
final static short yydgoto[] = {                          3,
    4,   15,  269,   17,  213,   19,   20,   21,   22,   23,
   24,   25,  136,  137,  383,  139,  140,  328,   41,  329,
  142,  143,  144,  330,  214,  351,  270,  145,   26,  199,
  121,  111,   42,  112,  300,  113,   70,   71,   27,   28,
   29,   30,   31,   59,   60,   61,   32,   62,  342,  218,
   99,   43,   44,   45,   46,   47,  153,  154,
};
final static short yysindex[] = {                       -83,
    0, 1025,    0,  556,  -38,  -30,   -7,    2,   -5,  655,
    0,    0,  435,  -48,  778,    0,    0,    0,    0,    0,
    0,    0,  -31,   14,  -35,   52,    0,    0,    0,    0,
    0,    0,  788,  812,   36, -160,   78,    0, -124,   64,
  102,  -36,  468,   47,    0,    0,    0,  113,  116,   43,
  -23,  -19,   70,  -85,   92,  111,  364,    0,    0,    0,
    0,  -95,    0,    0,    0,    0,    0, -155,    0,  146,
  163,    0,    0,  181,    0,  177,    0,  992,    5,    0,
  822,    0,    0,  154,    0,   75,    0,   45,    3,   62,
    0,    0,    0,   84,   84,    0,    0,    0,   84,   84,
   84,   84,  216,  245,   33,   39,  251,  -21,  267,   94,
  289,  291,  118,  296,  336,  148,  338,    0,    8,    0,
  922,    0,   30,    0,  136,    0,  166,    0,   32,   51,
  371,  581,  490,    0,    0,  321,  335,    0,  992,    0,
    0,    0,    0,    0,    0,  427,    0,    0,    0,    0,
    0,    0,  104,    0,  759,  759,  558,  759,   47,   47,
  161,  161,    0,    0,    0,    0,    0,  434,    0,  436,
    0,  446, -216,   81, -216,    0,  448, -216,    0, -216,
  449,  393,  -72,    0,    0,  458,    0,    0,   80,  453,
    0,    0,   64,   82,   97,   70,  364,    0,  244,   59,
    0,    0,    0,    0,    0,    0,    0,    0,  246,   88,
    0,  920,    0,  972, -102,   85,  672,   99,  101,    0,
    0, -216,    0,  476, -216,    0, -216,  488,    0, -216,
 1025,  414, 1025,    0,  480,   49,  481,    7,  483,  333,
    0,  508,  903,  510,   64,    0,    0,    0,  982,    0,
    0,  759,  493,  759,  502,  691,  504,  119,  759,  513,
  759,  520,    0, -216,    0,    0, -216,    0, 1025,  439,
 1025,  455,    0,    0,  522,    0,  701,    0,  534,  -71,
  477,   84,   12,    0,  340,    0,  341,    0,  546,  351,
    0,  720,  557,  352,    0,  355,    0,    0,  575,    0,
    0,  561,  507,  568,    0,  601,    0,  506,    0,  589,
  739,  574,  576,    0,  579,  584,  377,    0,  586,  592,
 -216,    0,  593,    0,   61, 1050,  372,    0,  391,  392,
 1015,  595,  621,    0,    0,    0,    0,  600,    0,    0,
    0,  394,    0,   64, 1048,  955,  398,  -76,  606,    0,
  543,  -91,    0,  920,    0,    0,  629,   16,  548,  553,
  409,  640,  425,  442,    0,  631,    0,   84,  749,    0,
    0,  641,   84,  647,    0,  670,  638,    0,  671,    0,
  457, 1050,  372,  459,  599,  -94, 1025,  602,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  254,  505,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  126,    0,    0,  505,
    0,    0,    0,  155,    0,    0,    0,    0,    0,    0,
 -160, -160,  505,  667,  845,  864,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   19,    0,  384,
  410,    0,    0,  533,    0,  437,    0,    0,    0,    0,
  729,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -29,    0,
    0,    0,    0,    0,    0,    0,    0, -160,    0,   73,
    0,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  505,    0,  492,    0,    0,    0,  505,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  178,  201,
  -18,    9,    0,    0,    0,    0,    0,  224,    0,  248,
    0,    0,    0,    0,    0,    0,    0, -160,    0,    0,
    0,    0,    0,    0,    0,  883,    0,    0,  505,    0,
    0,    0,  505,    0,    0,  505,    0,    0,    0,  505,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  945,    0,    0,    0,    0,    0,    0,    0,
    0,  607,    0,    0,    0,    0,    0,   91,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -98,
    0,    0,    0,    0,  505,    0,    0,    0,  145,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  608,    0,    0, -160,    0,  -89,    0,
    0,    0,    0,    0,  281,    0,    0,    0,  -82,    0,
    0,  505,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  311,    0,    0,    0,    0,    0,   95,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -160,    0,    0,    0,  505,    0,  469,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  334,    0,    0,
    0,    0,    0,  505,    0,  145,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  505,    0,    0,
    0,    0,  505,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   50,  905,  -73,   -2,    0,    0,    0,    0,  518,
   35,    0,    0,    0,  -32,    0, -125,  -77,  -37,  385,
  603,  609,  613,    0, -191,    0, -184,  525,  -74,    0,
  538,    4,  547, -139,  430,    0,  626,    0,   25,   69,
   89,  133,  149,  474,    0,    0,  -15,    0,    0,  467,
  712,    0,  319,  381,  -17,    0,    0,  550,
};
final static int YYTABLESIZE=1328;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
  141,   18,   89,  146,  134,   36,   94,   58,   95,   40,
   69,  149,   18,  207,   39,  117,  108,  109,   84,  172,
  249,  114,  148,   97,   98,   96,   65,   75,  363,  149,
   18,   18,   50,  323,   53,  124,  173,   63,  228,    2,
  148,   52,   63,  157,  186,  138,  363,  277,  272,  151,
  233,  233,  311,   34,   58,  115,  369,   76,  146,   11,
   12,  208,   84,  147,  146,  134,  185,  151,  152,  189,
   85,  193,   77,  168,   39,  135,   39,   84,   18,  170,
   39,   64,   81,  107,  301,  190,  303,   78,  101,  275,
  194,  167,  119,  102,   83,   39,   74,  169,  245,  198,
  344,   65,  124,   39,   88,   39,  206,  274,   39,   79,
  116,  174,  125,   73,   39,  151,   73,   86,   58,   39,
  235,  224,  239,   67,   39,  164,   39,  299,   39,   58,
   73,   68,   39,   87,  346,   70,  135,  241,  225,   94,
   67,   95,   90,   84,  211,   66,  352,  210,   68,  119,
  118,  236,   70,  103,  160,  238,  104,   63,  242,  252,
  253,   67,  346,   65,   65,  362,  164,  164,  164,  120,
  164,  130,  164,    1,  130,   65,  124,  158,  123,   63,
   63,  299,   35,  362,  164,  164,  164,  164,  232,  308,
  249,   63,  152,  147,   58,  160,   94,  160,   95,  160,
  159,   64,  388,   94,  126,   95,  127,  283,   68,  248,
  281,  251,  149,  160,  160,  160,  160,  119,  158,  359,
  158,   65,  158,  181,   36,   74,   37,   38,   18,   35,
   18,  149,   91,   92,   93,  128,  158,  158,  158,  158,
   58,  159,  148,  159,  310,  159,  251,  180,  345,   48,
  164,   49,   11,   12,   11,   12,   11,   12,   51,  159,
  159,  159,  159,  156,  184,   66,   18,  156,   18,  151,
  146,  134,  156,  327,  165,   84,  156,   10,   84,  160,
  108,   67,   84,   84,   84,  345,   37,   38,   37,   38,
   84,   84,   37,   38,   84,   84,   84,   84,  350,  105,
  355,  106,  158,  166,  345,  155,  358,   37,   38,  171,
  132,  112,   20,  360,  361,   37,   38,   37,   38,  367,
   37,   38,  158,  248,  175,  159,   37,   38,   18,  177,
  376,  150,   38,  140,  178,  379,   37,   38,   37,   38,
   37,   38,  135,  251,  150,   38,  254,  255,  181,  119,
  176,  248,  119,  180,  119,  119,  119,  119,  119,  119,
  259,  260,  261,  262,  119,  119,  119,  119,  119,  119,
  119,  119,  180,  279,  179,   94,  181,   95,  183,  248,
  292,  293,  164,   77,   18,  164,  164,  164,  164,  164,
  164,  164,  164,  191,  164,  164,  164,  164,  164,  164,
  164,  164,  164,  164,  164,  108,  145,  145,  182,   79,
  196,  160,  159,  160,  160,  160,  160,  160,  160,  160,
  160,  160,   68,  160,  160,  160,  160,  160,  160,  160,
  160,  160,  160,  160,  158,  132,   16,  158,  158,  158,
  158,  158,  158,  158,  158,  204,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,  158,  159,  140,  205,
  159,  159,  159,  159,  159,  159,  159,  159,   79,  159,
  159,  159,  159,  159,  159,  159,  159,  159,  159,  159,
  181,  163,  164,  181,  209,  181,  181,  181,  181,  181,
  181,   82,  220,  237,  221,  181,  181,  181,  181,  181,
  181,  181,  181,  222,  180,  227,  230,  180,   77,  180,
  180,  180,  180,  180,  180,  231,  234,  244,  132,  180,
  180,  180,  180,  180,  180,  180,  180,   97,   98,   96,
  122,  267,   20,  264,   79,   82,  271,  108,  273,  276,
  108,  278,  108,  108,  108,  108,  108,  108,  280,  282,
   82,  286,  108,  108,  108,  108,  108,  108,  108,  108,
  288,   16,  291,  302,  150,  150,  150,  132,  110,  110,
  132,  295,  132,  132,  132,  132,  132,  132,  297,  304,
  305,   84,  132,  132,  132,  132,  132,  132,  132,  132,
  140,   20,  307,  140,  188,  140,  140,  140,  140,  140,
  140,  309,  312,  313,  314,  140,  140,  140,  140,  140,
  140,  140,  140,  315,  319,  318,   82,  320,  321,  322,
   54,  215,  216,    6,  219,  110,  324,    7,  331,  332,
   55,  323,  334,  347,  335,    9,   10,  336,   56,  338,
   77,   13,  337,   77,  339,  161,  162,   77,   77,   77,
  340,  343,  348,  353,  349,   77,   77,   20,  356,   77,
   77,   77,   77,  130,  365,  357,   79,  366,  368,   79,
  122,  372,  370,   79,   79,   79,  195,  371,   33,  373,
  212,   79,   79,  258,  362,   79,   79,   79,   79,  375,
  223,   54,  226,   16,    6,  110,   16,  229,    7,  378,
   16,   16,   16,  197,  374,  380,    9,   10,   16,   16,
  381,  384,   16,   16,   16,   16,  188,  385,  285,  386,
  287,  387,  290,  326,  112,  294,  389,  296,    4,   54,
   58,   42,  364,  246,  243,  201,   91,   92,   93,  263,
  240,  202,  265,  354,  266,  203,   54,  268,   82,  200,
  341,   82,  192,    7,  100,   82,   82,   82,  317,  247,
  382,  131,  132,   82,   82,    0,    0,   82,   82,   82,
   82,    0,  258,  150,  150,  150,    0,   57,    0,    0,
    0,  298,    0,    0,  110,    0,    0,    0,    0,   20,
    0,    0,   20,    0,  212,    0,   20,   20,   20,  258,
    0,    0,    0,    0,   20,   20,    0,    0,   20,   20,
   20,   20,    5,  212,   54,    6,    0,    6,  217,    7,
    8,    7,    0,  212,    0,    0,    0,    9,   10,    9,
   10,   11,   12,   13,   14,   13,    0,   54,  110,    0,
    6,    0,  212,  258,    7,    0,  130,   55,    0,    0,
    0,    0,    9,   10,    0,   56,    0,   54,   13,    0,
  325,  212,  256,  257,    7,    0,  130,    0,    0,    0,
    0,  212,    9,   10,    0,    0,    0,   54,   13,    0,
    6,  212,  256,  257,    7,    0,  130,    0,    0,    0,
    0,    0,    9,   10,   54,    0,    0,  325,   13,  256,
  257,    7,   72,  130,    0,    0,   16,    0,   16,    9,
   10,   54,   80,    0,    6,   13,    0,    0,    7,   73,
    0,   55,    0,    0,    0,    0,    9,   10,   54,   56,
    0,    6,   13,  256,  257,    7,   82,   16,   73,    0,
    0,    0,    0,    9,   10,    0,  148,   54,    0,   13,
    6,    0,    0,  289,    7,    0,    0,   54,    0,    0,
    6,  306,    9,   10,    7,    0,    0,    0,   13,  100,
    0,    0,    9,   10,    0,    0,   54,    0,   13,    6,
    0,    0,  316,    7,    0,   73,    0,    0,  104,    0,
    0,    9,   10,    0,    0,   54,    0,   13,    6,  333,
    0,    0,    7,    0,    0,   54,    0,  101,    6,  377,
    9,   10,    7,    0,    0,   54,   13,    0,    6,    0,
    9,   10,    7,    0,    0,    0,   13,  187,    0,    0,
    9,   10,    0,    0,    5,    0,   13,    6,    0,    0,
    0,    7,    8,    0,    5,    0,  187,    6,    0,    9,
   10,    7,    8,   11,   12,   13,   14,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    0,    5,  146,
    0,    6,    0,    0,    0,    7,    8,    0,    5,  284,
    0,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,    0,    0,    9,   10,    0,  250,   11,   12,   13,
   14,  100,    0,    0,  100,    0,  284,    0,  100,    0,
  100,  100,    0,    0,    0,    0,  100,  100,  100,  100,
  104,    0,  100,  104,    0,    0,    0,  104,    0,  104,
  104,    0,    0,    0,    0,  104,  104,  104,  104,  101,
    0,  104,  101,    0,    0,    0,  101,    0,  101,  101,
    0,    0,    0,    0,  101,  101,  101,  101,    0,   54,
  101,    0,    6,    0,    0,    0,    7,    0,  130,   55,
    0,    0,    0,    0,    9,   10,   54,   56,   54,    6,
   13,    6,    0,    7,    0,    7,    0,    0,   55,    0,
    0,    9,   10,    9,   10,    0,   56,   13,    0,   13,
    0,  146,    0,    0,  146,    0,  142,  142,  146,    0,
    0,   54,    0,    0,    6,    0,  146,  146,    7,    0,
  130,    0,  146,    0,    0,    0,    9,   10,   54,    0,
    0,    6,   13,    0,    0,    7,    0,    0,   54,    0,
    0,    6,    0,    9,   10,    7,    0,    0,    5,   13,
    0,  129,    0,    9,   10,    7,    8,  130,    0,   13,
    0,    0,    0,  131,  132,    0,    0,   11,   12,  133,
   14,    5,    0,    0,    6,    0,    0,    0,    7,    8,
  130,    5,    0,    0,    6,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,    5,    0,   54,  200,    0,  325,
    0,    7,    8,    7,    0,    0,    0,    0,    0,  131,
  132,    9,   10,   11,   12,  133,   14,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   78,    4,   40,   78,   78,   44,   43,   10,   45,   40,
   59,   41,   15,  139,   45,   53,   40,   41,    0,   41,
  212,   41,   41,   60,   61,   62,  125,   59,  123,   59,
   33,   34,   40,  125,   40,  125,   58,   13,  178,  123,
   59,   40,  125,   41,  119,   78,  123,   41,  233,   41,
  123,  123,   41,    4,   57,   52,   41,   23,  133,  276,
  277,  139,   44,   79,  139,  139,   59,   59,   86,   40,
   36,   40,   59,   41,   45,   78,   45,   59,   81,   41,
   45,   13,   33,   41,  269,  123,  271,  123,   42,   41,
   40,   59,    0,   47,   59,   45,  257,   59,   40,  132,
   40,   13,  258,   45,   41,   45,  139,   59,   45,   58,
   41,  108,  268,   41,   45,   41,   44,   40,  121,   45,
   41,   41,   41,   41,   45,    0,   45,  267,   45,  132,
   58,   41,   45,  258,  326,   41,  139,   41,   58,   43,
   58,   45,   41,  125,   41,   13,  331,   44,   58,   58,
   59,  189,   58,   41,    0,  193,   41,  133,  196,  262,
  263,   13,  354,  262,  263,  260,   41,   42,   43,   59,
   45,  266,   47,  257,  266,  274,  266,    0,  274,  262,
  263,  321,  268,  260,   59,   60,   61,   62,  261,  261,
  382,  274,  210,  209,  197,   41,   43,   43,   45,   45,
    0,  133,  387,   43,   59,   45,   44,  245,  257,  212,
  243,  214,   59,   59,   60,   61,   62,  125,   41,  345,
   43,  133,   45,    0,   44,  257,  257,  258,  231,  268,
  233,  261,  269,  270,  271,   59,   59,   60,   61,   62,
  243,   41,  261,   43,  282,   45,  249,    0,  326,  257,
  125,  259,  276,  277,  276,  277,  276,  277,  257,   59,
   60,   61,   62,  261,  257,  133,  269,  261,  271,  261,
  345,  345,  261,  306,   59,  257,  261,  273,  260,  125,
    0,  133,  264,  265,  266,  363,  257,  258,  257,  258,
  272,  273,  257,  258,  276,  277,  278,  279,  331,  257,
  333,  259,  125,   59,  382,  261,  344,  257,  258,   59,
    0,   58,   59,  346,  347,  257,  258,  257,  258,  352,
  257,  258,  261,  326,   58,  125,  257,  258,  331,   41,
  368,  257,  258,    0,   44,  373,  257,  258,  257,  258,
  257,  258,  345,  346,  257,  258,  262,  263,  125,  257,
  257,  354,  260,   58,  262,  263,  264,  265,  266,  267,
  262,  263,  262,  263,  272,  273,  274,  275,  276,  277,
  278,  279,  125,   41,  257,   43,   41,   45,   41,  382,
  262,  263,  257,    0,  387,  260,  261,  262,  263,  264,
  265,  266,  267,  258,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  125,  262,  263,  261,    0,
   40,  257,   94,   95,  260,  261,  262,  263,  264,  265,
  266,  267,  257,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  125,    0,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  125,  125,
  260,  261,  262,  263,  264,  265,  266,  267,   59,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  101,  102,  260,   58,  262,  263,  264,  265,  266,
  267,    0,   59,   41,   59,  272,  273,  274,  275,  276,
  277,  278,  279,   58,  257,   58,   58,  260,  125,  262,
  263,  264,  265,  266,  267,  123,   59,  274,  273,  272,
  273,  274,  275,  276,  277,  278,  279,   60,   61,   62,
   57,   44,    0,   58,  125,   44,  123,  257,   59,   59,
  260,   59,  262,  263,  264,  265,  266,  267,   41,   40,
   59,   59,  272,  273,  274,  275,  276,  277,  278,  279,
   59,  125,   59,  125,   60,   61,   62,  257,   51,   52,
  260,   59,  262,  263,  264,  265,  266,  267,   59,  125,
   59,   35,  272,  273,  274,  275,  276,  277,  278,  279,
  257,   59,   59,  260,  121,  262,  263,  264,  265,  266,
  267,  125,  263,  263,   59,  272,  273,  274,  275,  276,
  277,  278,  279,  263,  263,   59,  125,  263,   44,   59,
  257,  155,  156,  260,  158,  108,   59,  264,  123,   41,
  267,  125,   59,  262,   59,  272,  273,   59,  275,  263,
  257,  278,   59,  260,   59,   99,  100,  264,  265,  266,
   59,   59,  262,   59,  263,  272,  273,  125,   59,  276,
  277,  278,  279,  266,   59,  272,  257,  125,   40,  260,
  197,  263,  125,  264,  265,  266,  130,  125,  123,   40,
  123,  272,  273,  217,  260,  276,  277,  278,  279,   59,
  173,  257,  175,  257,  260,  178,  260,  180,  264,   59,
  264,  265,  266,  123,  263,   59,  272,  273,  272,  273,
   41,   41,  276,  277,  278,  279,  243,  261,  252,  261,
  254,  123,  256,  123,   58,  259,  125,  261,    0,  123,
  123,  263,  348,  209,  197,  133,  269,  270,  271,  222,
  194,  133,  225,  123,  227,  133,  257,  230,  257,  260,
  321,  260,  127,  264,   43,  264,  265,  266,  292,  210,
  123,  272,  273,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  306,  269,  270,  271,   -1,  123,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  257,
   -1,   -1,  260,   -1,  123,   -1,  264,  265,  266,  333,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,  123,  257,  260,   -1,  260,  261,  264,
  265,  264,   -1,  123,   -1,   -1,   -1,  272,  273,  272,
  273,  276,  277,  278,  279,  278,   -1,  257,  321,   -1,
  260,   -1,  123,  377,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,   -1,  275,   -1,  257,  278,   -1,
  260,  123,  262,  263,  264,   -1,  266,   -1,   -1,   -1,
   -1,  123,  272,  273,   -1,   -1,   -1,  257,  278,   -1,
  260,  123,  262,  263,  264,   -1,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,  257,   -1,   -1,  260,  278,  262,
  263,  264,  125,  266,   -1,   -1,    2,   -1,    4,  272,
  273,  257,  125,   -1,  260,  278,   -1,   -1,  264,   15,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,  262,  263,  264,  125,   33,   34,   -1,
   -1,   -1,   -1,  272,  273,   -1,  125,  257,   -1,  278,
  260,   -1,   -1,  263,  264,   -1,   -1,  257,   -1,   -1,
  260,  261,  272,  273,  264,   -1,   -1,   -1,  278,  125,
   -1,   -1,  272,  273,   -1,   -1,  257,   -1,  278,  260,
   -1,   -1,  263,  264,   -1,   81,   -1,   -1,  125,   -1,
   -1,  272,  273,   -1,   -1,  257,   -1,  278,  260,  261,
   -1,   -1,  264,   -1,   -1,  257,   -1,  125,  260,  261,
  272,  273,  264,   -1,   -1,  257,  278,   -1,  260,   -1,
  272,  273,  264,   -1,   -1,   -1,  278,  125,   -1,   -1,
  272,  273,   -1,   -1,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,  265,   -1,  257,   -1,  125,  260,   -1,  272,
  273,  264,  265,  276,  277,  278,  279,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,  125,
   -1,  260,   -1,   -1,   -1,  264,  265,   -1,  257,  125,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,   -1,  272,  273,   -1,  125,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,  125,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,  257,
  278,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  257,  275,  257,  260,
  278,  260,   -1,  264,   -1,  264,   -1,   -1,  267,   -1,
   -1,  272,  273,  272,  273,   -1,  275,  278,   -1,  278,
   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,
   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,
  266,   -1,  278,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,   -1,   -1,  264,   -1,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,  257,  278,
   -1,  260,   -1,  272,  273,  264,  265,  266,   -1,  278,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
  266,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,  257,  260,   -1,  260,
   -1,  264,  265,  264,   -1,   -1,   -1,   -1,   -1,  272,
  273,  272,  273,  276,  277,  278,  279,  278,
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
"sentencias : sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : sentencia_declarativa_variables",
"sentencia_declarativa : funcion_con_return",
"sentencia_declarativa : funcion_sin_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_con_return : encabezado_funcion '{' cuerpo_funcion_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' cuerpo_funcion_sin_return '}'",
"cuerpo_funcion_con_return : sentencia_return",
"cuerpo_funcion_con_return : sentencias_funcion_con_return sentencia_return",
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE sentencia_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_seleccion_compuesta_con_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' sentencia_seleccion_compuesta_con_return sentencia_funcion_con_return '}'",
"sentencia_funcion_con_return : sentencia_declarativa",
"sentencia_funcion_con_return : sentencia_ejecutable",
"sentencia_funcion_con_return : sentencia_when_con_return",
"sentencia_funcion_con_return : DEFER sentencia_when_con_return",
"sentencia_funcion_con_return : sentencia_do_con_return",
"sentencia_funcion_con_return : DEFER sentencia_do_con_return",
"sentencia_funcion_con_return : sentencia_seleccion_simple_con_return",
"sentencia_funcion_con_return : DEFER sentencia_seleccion_simple_con_return",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_simple_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_simple_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_simple_con_return : '{' sentencias_ejecutables sentencia_return '}'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : sentencias_when sentencia_return",
"sentencia_do_con_return : sentencia_do_simple_con_return",
"sentencia_do_con_return : etiqueta ':' sentencia_do_simple_con_return",
"sentencia_do_simple_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"bloque_sentencias_ejecutables_do_con_return : sentencia_return",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do sentencia_return '}'",
"encabezado_funcion : FUN ID '(' ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':' tipo",
"encabezado_funcion : FUN ID '(' ')' ':'",
"encabezado_funcion : FUN '(' ')' ':' tipo",
"encabezado_funcion : FUN ID ')' ':' tipo",
"encabezado_funcion : FUN ID '(' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':'",
"encabezado_funcion : FUN '(' lista_de_parametros ')' ':' tipo",
"encabezado_funcion : FUN ID lista_de_parametros ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ':' tipo",
"sentencia_return : RETURN '(' expresion ')' ';'",
"sentencia_return : RETURN '(' expresion ')'",
"sentencia_return : RETURN expresion ')'",
"sentencia_return : RETURN '(' expresion",
"sentencia_return : RETURN '(' ')' ';'",
"lista_de_parametros : parametro",
"lista_de_parametros : parametro ',' parametro",
"lista_de_parametros : parametro ',' parametro ',' lista_parametros_exceso",
"lista_parametros_exceso : parametro",
"lista_parametros_exceso : parametro ',' lista_parametros_exceso",
"parametro : tipo ID",
"parametro : tipo",
"$$1 :",
"parametro : $$1 ID",
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
"sentencia_when : WHEN '(' condicion ')' THEN '{' sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}' ';'",
"$$2 :",
"sentencia_when : WHEN '(' ')' THEN '{' sentencias_when '}' ';' $$2 WHEN '(' condicion ')' THEN '{' sentencias_when '}'",
"sentencias_when : sentencia",
"sentencias_when : sentencia sentencias_when",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN ENDIF ';'",
"seleccion : IF '(' condicion THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN ELSE ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_seleccion : sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"$$3 :",
"condicion : $$3 comparador expresion",
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
"invocacion_funcion : ID '(' ')'",
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

//#line 367 ".\gramatica.y"

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
//#line 846 "Parser.java"
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
case 15:
//#line 45 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 16:
//#line 46 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 17:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 48 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 21:
//#line 57 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 22:
//#line 61 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 44:
//#line 110 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 49:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 54:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 55:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 56:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 57:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 58:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 59:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 60:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 61:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 63:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 64:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 65:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 66:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 69:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 74:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 75:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 76:
//#line 171 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 77:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 78:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 82:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 83:
//#line 184 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 84:
//#line 185 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 98:
//#line 208 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 99:
//#line 209 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 100:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 101:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 102:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 103:
//#line 216 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 104:
//#line 217 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 107:
//#line 226 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 108:
//#line 227 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 109:
//#line 228 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 110:
//#line 229 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 111:
//#line 230 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 118:
//#line 249 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 119:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 120:
//#line 254 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 121:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 122:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 123:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 126:
//#line 266 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 127:
//#line 267 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 128:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 129:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 130:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 131:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 132:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 133:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 134:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 137:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 138:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 139:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 140:
//#line 280 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 141:
//#line 281 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 144:
//#line 287 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 145:
//#line 288 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 150:
//#line 298 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 151:
//#line 299 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 173:
//#line 345 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 174:
//#line 346 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 175:
//#line 347 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 176:
//#line 348 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 177:
//#line 349 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 178:
//#line 350 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 179:
//#line 351 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 180:
//#line 352 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 181:
//#line 353 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 183:
//#line 358 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1327 "Parser.java"
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
