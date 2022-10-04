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
    7,    8,   14,   15,   15,   16,   16,   18,   20,   20,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   17,   13,   23,   23,   23,   26,   26,   25,   25,    9,
    9,    9,   27,   27,   28,   28,   28,   28,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   34,   34,
   34,   35,   35,   35,   35,   35,   36,   36,   33,   33,
   37,   37,   37,   37,   37,   19,   38,   38,   22,   22,
   29,   29,   29,   32,   32,   40,   32,   39,   39,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   30,   30,   41,   41,   41,   41,   42,
   42,   21,   21,   44,   21,   43,   43,   43,   43,   43,
   43,   24,   24,   24,   45,   45,   45,   46,   46,   46,
   48,   49,   49,   50,   50,   31,   31,   31,   31,   31,
   31,   31,   31,   31,   47,   47,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    2,    2,    3,    1,    3,    7,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    2,    1,    3,    5,    1,    3,    2,    1,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    0,   17,    1,    2,    8,
   10,    7,    7,    7,    7,    7,    7,    9,    9,    9,
    9,    9,    9,    9,    8,    1,    3,    2,    2,    1,
    2,    3,    2,    0,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    4,    1,    3,    1,    1,    5,    5,    4,    4,    4,
    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  158,  157,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   59,   61,   63,   65,
   67,   79,    0,    0,    0,    0,    0,  155,    0,    0,
    0,    0,    0,    0,  137,  139,  140,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,   87,   70,
   71,    0,   60,   62,   64,   66,   68,    0,   52,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,   92,    0,   19,    0,  156,    0,    0,    0,
  126,  127,  128,    0,    0,  129,  130,  131,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   72,    0,   77,
    0,   89,    0,   57,    0,   50,    0,   15,    0,    0,
    0,   80,    1,   91,  144,  145,    0,  142,    0,    0,
    0,    0,    0,    0,    0,    0,  135,  136,  151,  149,
  152,    0,  150,    0,  148,    0,    0,    0,    0,   48,
    0,    0,    0,    0,    0,    0,   86,   76,    0,   88,
   90,    0,    0,   55,   54,    0,    0,    0,   23,    0,
   42,   26,    0,   21,   22,    0,  141,    0,    0,    0,
    0,    0,    0,    0,    0,  147,  146,   36,    0,    0,
    0,   35,    0,    0,   34,    0,    0,    0,    0,   73,
    0,    0,    0,    0,    0,   29,    0,    0,   24,    0,
  143,  120,    0,    0,    0,  118,  121,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   31,   40,    0,   39,
    0,   38,    0,    0,    0,    0,   83,   85,    0,   84,
    0,    0,    0,   25,   27,  117,    0,  104,    0,  102,
    0,    0,  107,    0,    0,    0,  105,    0,  103,   32,
    0,   45,    0,    0,    0,    0,   81,    0,    0,    0,
    0,    0,  115,    0,    0,    0,  100,    0,    0,    0,
   96,    0,   95,   41,   30,    0,  110,  108,  112,  113,
    0,  111,  109,   47,    0,   94,    0,  101,    0,   28,
    0,    0,    0,    0,    0,    0,   97,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  130,  131,  179,  180,  181,  182,   26,  217,
   41,  121,  112,   42,  113,  272,   70,   71,   27,   28,
   29,   30,   31,   59,   60,   61,   32,   62,  244,  305,
  190,  191,   99,   43,   44,   45,   46,   47,  137,  138,
};
final static short yysindex[] = {                       -85,
    0,  796,    0,  263,  -39,  -10,  -25,  -24,  -23,  519,
    0,    0,  312,  -41,  574,    0,    0,    0,    0,    0,
    0,    0,  -34,  -20,  -64,   26,    0,    0,    0,    0,
    0,    0,  597,  607,  152, -181,   58,    0, -154,  126,
   69,  -33,  333,  131,    0,    0,    0,   88,   94,   -8,
  -37,  -32,  148, -131,  118,   84,  819,    0,    0,    0,
    0, -128,    0,    0,    0,    0,    0, -124,    0,   97,
  130,    0,    0,  139,    0,  103,    0,  796,  -68,    0,
  631,    0,    0,  116,    0,   33,    0,  -63,  -30,  -51,
    0,    0,    0,  154,  154,    0,    0,    0,  154,  154,
  154,  154,  169,  197,   24,   30,  202,    0,  -35,  149,
  -44,  176,  238,  227,  283,   77,  320,    0,   -5,    0,
  731,    0,   29,    0,  105,    0,  113,    0,  786,  247,
  289,    0,    0,    0,    0,    0,   16,    0,  563,  563,
  360,  563,  131,  131,  104,  104,    0,    0,    0,    0,
    0,  384,    0,  389,    0,  -75,  380,   65,  -75,    0,
  400, -189,  -75,  412,  350, -101,    0,    0,  423,    0,
    0,  150,  452,    0,    0,  454,  495,  337,    0,  237,
    0,    0,  446,    0,    0,   33,    0,  265,    0,  -54,
  430,  164, -109,  166,  179,    0,    0,    0,  -75,  -75,
  447,    0,  -75,  462,    0,  -75,  796,  401,  796,    0,
  460,   54,  467,  154,  819,    0,  257,  237,    0,  259,
    0,    0,  633,  563,  474,    0,    0,  563,  475,  541,
  487,  183,  563,  488,  563,  490,    0,    0,  -75,    0,
 -189,    0,  796,  425,  796,  426,    0,    0,  497,    0,
  145,  712,  517,    0,    0,    0,  295,    0,  296,    0,
  503,  300,    0,  543,  509,  307,    0,  310,    0,    0,
  531,    0,  796,  518,  457,  522,    0,  524,  463,  154,
  530,  536,    0,  539,  540,  344,    0,  549,  552, -189,
    0,  553,    0,    0,    0,  559,    0,    0,    0,    0,
  555,    0,    0,    0,  341,    0,  556,    0,  576,    0,
  154,  578,  361,  500,  796,  501,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  358,  456,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    0,    0,  456,
    0,    0,    0,   32,    0,    0,    0,    0,    0,    0,
    0,    0,  456,  567,  655,  674,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  396,    0,  371,
  288,    0,    0,  314,    0,  419,    0,    0,    0,    0,
  627,    0,    0,  102,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -7,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   68,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  456,    0,  471,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   56,   79,   -4,   -1,    0,    0,    0,    0,
    0,  125,    0,  158,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  693,    0,
    0,  456,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  753,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  506,    0,
    0,    0,    0,   70,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  194,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  507,    0,
    0,    0,  514,    0,    0,    0,    0,    0,  187,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  212,    0,    0,    0,    0,    0,
   78,    0,  515,    0,    0,    0,    0,    0,    0,  456,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  235,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  456,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    3,  -15,    0,   -9,    0,    0,    0,    0,  554,
   67,    0,    0,    0,    0,  468, -110,  414,  -78,    0,
  -27,  436,   -2,  -14, -120,  351,  525,    0,    7,   18,
   40,   92,  137,  -49,    0,    0,  -55,    0, -163,    0,
  814,  466,  602,    0,  373,  379,   -6,    0,    0,  469,
};
final static int YYTABLESIZE=1097;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
   58,  138,  109,  110,   36,  157,   34,  122,  114,   94,
  141,   95,   89,  188,   50,   52,   53,   69,   73,   63,
   84,  209,  156,  132,   75,  117,   97,   98,   96,   40,
   64,  134,  107,  123,   39,   81,  122,    2,   77,  125,
  169,  204,  138,  138,  138,  246,  138,   58,  138,  115,
  183,  123,   65,  168,  122,  132,  187,  125,   78,  186,
  138,  138,  138,  138,  152,   73,  216,  108,  172,  219,
  154,  171,  134,   39,  134,   74,  134,   39,  133,  136,
  129,  275,  151,   79,  145,  146,   11,   12,  153,   76,
  134,  134,  134,  134,  249,  173,  132,   86,  132,  183,
  132,   93,   85,   87,   66,  201,  158,  254,   43,   90,
   44,   58,  248,   73,  132,  132,  132,  132,   46,  133,
  271,  133,  200,  133,  154,   43,  138,   44,  103,  189,
  189,  189,  189,  124,  104,   46,   35,  133,  133,  133,
  133,  279,  120,  125,  212,  123,   94,   54,   95,   67,
    6,  316,  230,  231,    7,  126,  134,  153,   94,  208,
   95,  128,    9,   10,  132,  122,   88,   58,   13,  271,
   39,    1,  101,  127,  134,  119,  118,  102,  222,  136,
  132,  227,   36,  189,   63,  278,   82,   94,  116,   95,
  211,  243,   39,  243,   39,   64,   39,  139,   39,  251,
   11,   12,  171,  133,   10,   58,  159,  224,  225,  142,
   83,  106,  160,  227,  189,   68,  161,   65,  189,  108,
  189,  108,   74,  189,  108,  189,   93,  149,   35,  243,
  140,   48,   51,   49,  114,   91,   92,   93,   11,   12,
   11,   12,   58,   11,   12,  273,   37,   38,  105,  154,
  106,  167,  296,  123,  189,  150,  122,   73,  138,  125,
  155,  138,  138,  138,  138,  138,  138,  138,  138,   66,
  138,  138,  138,  138,  138,  138,  138,  138,  138,  138,
  138,  162,  153,  312,  163,   37,   38,   53,  134,  135,
   38,  134,  134,  134,  134,  134,  134,  134,  134,  243,
  134,  134,  134,  134,  134,  134,  134,  134,  134,  134,
  134,   82,  132,   20,   67,  132,  132,  132,  132,  132,
  132,  132,  132,  164,  132,  132,  132,  132,  132,  132,
  132,  132,  132,  132,  132,  133,  106,  165,  133,  133,
  133,  133,  133,  133,  133,  133,   53,  133,  133,  133,
  133,  133,  133,  133,  133,  133,  133,  133,   93,  114,
  166,   93,  174,   93,   93,   93,   93,   93,   93,   68,
   51,  184,   20,   93,   93,   93,   93,   93,   93,   93,
   93,  154,   37,   38,  154,   33,  154,  154,  154,  154,
  154,  154,   97,   98,   96,   58,  154,  154,  154,  154,
  154,  154,  154,  154,   37,   38,   37,   38,   37,   38,
   37,   38,   53,  185,  153,   86,   20,  153,   16,  153,
  153,  153,  153,  153,  153,  228,  229,  233,  234,  153,
  153,  153,  153,  153,  153,  153,  153,  199,   20,   58,
  235,  236,  196,   82,  264,  265,   82,  197,   82,   82,
   82,   82,   82,   82,   58,  119,  119,  203,   82,   82,
   82,   82,   82,   82,   82,   82,  143,  144,  106,  206,
   56,  106,  207,  106,  106,  106,  106,  106,  106,  147,
  148,  210,  188,  106,  106,  106,  106,  106,  106,  106,
  106,  114,  213,  214,  114,   51,  114,  114,  114,  114,
  114,  114,  176,  220,  239,  241,  114,  114,  114,  114,
  114,  114,  114,  114,   56,  124,  124,  124,  247,    5,
   58,   54,    6,  245,    6,  250,    7,    8,    7,   56,
  253,  177,  258,  260,    9,   10,    9,   10,   11,   12,
   13,   14,   13,   16,   53,  263,  267,   53,  269,  274,
  276,   53,   53,   53,  226,  277,  280,  281,  282,   53,
   53,  283,  284,   53,   53,   53,   53,  287,   54,  288,
   20,    6,  289,   20,  290,    7,  291,   20,   20,   20,
  293,  292,  294,    9,   10,   20,   20,  295,  297,   20,
   20,   20,   20,   54,  298,   56,    6,  299,  300,  307,
    7,   91,   92,   93,  111,  111,  301,  302,    9,  177,
  303,  306,  309,  308,  310,  311,   54,  215,  313,    6,
  193,  314,  315,    7,   86,  317,    4,   51,   33,   37,
   51,    9,   10,  255,   51,   51,   51,   13,   98,   99,
  304,   57,   51,   51,  100,  218,   51,   51,   51,   51,
  252,  175,   58,  223,  221,   58,    0,    0,    0,   58,
   58,   58,  111,  188,    0,  188,    0,   58,   58,    0,
    0,   58,   58,   58,   58,   16,    0,    0,   16,    0,
    0,    0,   16,   16,   16,  188,   54,    0,    0,    6,
   16,   16,    0,    7,   16,   16,   16,   16,   72,    0,
    0,    9,   10,    0,    0,    0,    0,   13,    0,  198,
    0,    0,  202,    0,    0,  111,  205,    0,    0,    0,
    0,   80,    0,    0,  124,  124,  124,   56,    0,    0,
   56,   82,    0,    0,   56,   56,   56,    0,    0,    0,
    0,    0,   56,   56,    0,    0,   56,   56,   56,   56,
    0,   54,  237,  238,    6,  133,  240,  256,    7,  242,
  176,   55,    0,    0,    0,    0,    9,   10,    0,   56,
    0,    0,   13,    0,    0,   54,    0,    0,    6,   74,
    0,    0,    7,    0,    0,   55,    0,    0,    0,    0,
    9,   10,  270,   56,  111,    0,   13,   54,   78,   54,
    6,    0,    6,  261,    7,  285,    7,    0,    0,    0,
    0,    0,    9,   10,    9,   10,    0,   75,   13,   54,
   13,    0,    6,    0,    0,    0,    7,    0,    0,    0,
    5,    0,    0,    6,    9,   10,  170,    7,    8,    0,
   13,    0,    0,  111,    0,    9,   10,    0,    0,   11,
   12,   13,   14,    5,    0,  170,    6,    0,    0,    0,
    7,    8,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,   11,   12,   13,   14,    0,  120,    9,   10,
    0,    0,   11,   12,   13,   14,    0,    5,    0,   54,
    6,    0,    6,    0,    7,    8,    7,    0,    0,    0,
    0,    0,    9,   10,    9,   10,   11,   12,   13,   14,
   13,   74,    0,    0,   74,    0,    0,    0,   74,    0,
   74,   74,    0,    0,    0,    0,   74,   74,   74,   74,
   78,    0,   74,   78,    0,    0,    0,   78,    0,   78,
   78,    0,    0,    0,    0,   78,   78,   78,   78,   75,
    0,   78,   75,  192,  194,  195,   75,    0,   75,   75,
    0,    0,    0,    0,   75,   75,   75,   75,   54,    0,
   75,    6,    0,    0,    0,    7,    0,  176,   55,    0,
    0,    0,    0,    9,   10,    0,   56,   54,    0,   13,
    6,    0,    0,    0,    7,    0,    0,   55,    0,    0,
    0,    0,    9,   10,    0,   56,  232,    0,   13,  120,
    0,    0,  120,    0,  116,  116,  120,    0,    0,    0,
    0,    0,    0,    0,  120,  120,    0,    0,    0,    0,
  120,    0,    0,    0,    0,    0,    0,  257,    0,    0,
    0,  259,    5,  262,    0,    6,  266,    0,  268,    7,
    8,  176,    5,    0,    0,    6,    0,    9,  177,    7,
    8,   11,   12,  178,   14,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,   54,    0,  286,    6,    0,
    0,    0,    7,    0,    0,   55,    0,    0,    0,    0,
    9,   10,    0,   56,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   10,    0,   40,   41,   44,   41,    4,   57,   41,   43,
   41,   45,   40,  123,   40,   40,   40,   59,   34,   13,
   35,  123,   58,   79,   59,   53,   60,   61,   62,   40,
   13,    0,   41,   41,   45,   33,   41,  123,   59,   41,
  119,  162,   41,   42,   43,  209,   45,   57,   47,   52,
  129,   59,   13,   59,   59,    0,   41,   59,  123,   44,
   59,   60,   61,   62,   41,   81,  177,  257,   40,  180,
   41,  121,   41,   45,   43,  257,   45,   45,    0,   86,
   78,  245,   59,   58,   99,  100,  276,  277,   59,   23,
   59,   60,   61,   62,   41,  123,   41,   40,   43,  178,
   45,    0,   36,  258,   13,   41,  109,  218,   41,   41,
   41,  121,   59,  129,   59,   60,   61,   62,   41,   41,
  241,   43,   58,   45,    0,   58,  125,   58,   41,  139,
  140,  141,  142,  258,   41,   58,  268,   59,   60,   61,
   62,  252,   59,  268,  172,  274,   43,  257,   45,   13,
  260,  315,  262,  263,  264,   59,  125,    0,   43,  261,
   45,   59,  272,  273,  220,  215,   41,  177,  278,  290,
   45,  257,   42,   44,   59,   58,   59,   47,  188,  186,
  125,  191,   44,  193,  178,   41,    0,   43,   41,   45,
   41,  207,   45,  209,   45,  178,   45,  261,   45,  214,
  276,  277,  252,  125,  273,  215,   58,  262,  263,  261,
   59,    0,  257,  223,  224,  257,   41,  178,  228,  257,
  230,  257,  257,  233,  257,  235,  125,   59,  268,  245,
  261,  257,  257,  259,    0,  269,  270,  271,  276,  277,
  276,  277,  252,  276,  277,  243,  257,  258,  257,  125,
  259,  257,  280,  261,  264,   59,  261,  273,  257,  261,
   59,  260,  261,  262,  263,  264,  265,  266,  267,  178,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,   44,  125,  311,   58,  257,  258,    0,  257,  257,
  258,  260,  261,  262,  263,  264,  265,  266,  267,  315,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  125,  257,    0,  178,  260,  261,  262,  263,  264,
  265,  266,  267,   41,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  125,  261,  260,  261,
  262,  263,  264,  265,  266,  267,   59,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  125,
   41,  260,  258,  262,  263,  264,  265,  266,  267,  257,
    0,  125,   59,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  123,  262,  263,  264,  265,
  266,  267,   60,   61,   62,    0,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  258,  257,  258,  257,  258,
  257,  258,  125,  125,  257,   58,   59,  260,    0,  262,
  263,  264,  265,  266,  267,  262,  263,  262,  263,  272,
  273,  274,  275,  276,  277,  278,  279,   58,  125,   44,
  262,  263,   59,  257,  262,  263,  260,   59,  262,  263,
  264,  265,  266,  267,   59,  262,  263,   58,  272,  273,
  274,  275,  276,  277,  278,  279,   94,   95,  257,   58,
    0,  260,  123,  262,  263,  264,  265,  266,  267,  101,
  102,   59,  123,  272,  273,  274,  275,  276,  277,  278,
  279,  257,   41,   40,  260,  125,  262,  263,  264,  265,
  266,  267,  266,   58,   58,   44,  272,  273,  274,  275,
  276,  277,  278,  279,   44,   60,   61,   62,   59,  257,
  125,  257,  260,  123,  260,   59,  264,  265,  264,   59,
  274,  273,   59,   59,  272,  273,  272,  273,  276,  277,
  278,  279,  278,  125,  257,   59,   59,  260,   59,  125,
  125,  264,  265,  266,  125,   59,   40,  263,  263,  272,
  273,   59,  263,  276,  277,  278,  279,   59,  257,  263,
  257,  260,  263,  260,   44,  264,   59,  264,  265,  266,
   59,  125,   59,  272,  273,  272,  273,  125,   59,  276,
  277,  278,  279,  257,   59,  125,  260,   59,   59,   41,
  264,  269,  270,  271,   51,   52,  263,   59,  272,  273,
   59,   59,  272,   59,   59,   40,  257,  123,   41,  260,
  261,  261,  123,  264,   58,  125,    0,  257,  123,  123,
  260,  272,  273,  220,  264,  265,  266,  278,  125,  125,
  290,  123,  272,  273,   43,  178,  276,  277,  278,  279,
  215,  127,  257,  188,  186,  260,   -1,   -1,   -1,  264,
  265,  266,  109,  123,   -1,  123,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,  265,  266,  123,  257,   -1,   -1,  260,
  272,  273,   -1,  264,  276,  277,  278,  279,  125,   -1,
   -1,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,  156,
   -1,   -1,  159,   -1,   -1,  162,  163,   -1,   -1,   -1,
   -1,  125,   -1,   -1,  269,  270,  271,  257,   -1,   -1,
  260,  125,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,  257,  199,  200,  260,  125,  203,  125,  264,  206,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,
   -1,   -1,  278,   -1,   -1,  257,   -1,   -1,  260,  125,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,  239,  275,  241,   -1,  278,  257,  125,  257,
  260,   -1,  260,  263,  264,  263,  264,   -1,   -1,   -1,
   -1,   -1,  272,  273,  272,  273,   -1,  125,  278,  257,
  278,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,   -1,
  257,   -1,   -1,  260,  272,  273,  125,  264,  265,   -1,
  278,   -1,   -1,  290,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,  125,  260,   -1,   -1,   -1,
  264,  265,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,   -1,  125,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,  257,   -1,  257,
  260,   -1,  260,   -1,  264,  265,  264,   -1,   -1,   -1,
   -1,   -1,  272,  273,  272,  273,  276,  277,  278,  279,
  278,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,  140,  141,  142,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,   -1,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,   -1,  275,  193,   -1,  278,  257,
   -1,   -1,  260,   -1,  262,  263,  264,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,
  278,   -1,   -1,   -1,   -1,   -1,   -1,  224,   -1,   -1,
   -1,  228,  257,  230,   -1,  260,  233,   -1,  235,  264,
  265,  266,  257,   -1,   -1,  260,   -1,  272,  273,  264,
  265,  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,  264,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,   -1,   -1,  278,
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
"sentencia_declarativa : funcion_return_simple",
"sentencia_declarativa : funcion_sentencias_con_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_return_simple : encabezado_funcion '{' cuerpo_funcion_return_simple '}'",
"funcion_sentencias_con_return : encabezado_funcion '{' cuerpo_funcion_sentencias_con_return '}'",
"cuerpo_funcion_sentencias_con_return : sentencias sentencia_ejecutable_con_return",
"sentencia_ejecutable_con_return : sentencia_do_con_return sentencia_return",
"sentencia_ejecutable_con_return : DEFER sentencia_do_con_return sentencia_return",
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
"cuerpo_funcion_return_simple : sentencias sentencia_return",
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

//#line 319 ".\gramatica.y"

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
//#line 744 "Parser.java"
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
case 28:
//#line 79 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 45:
//#line 111 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 49:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 50:
//#line 125 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 51:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 52:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 56:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 57:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 58:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 72:
//#line 162 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 73:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 74:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 75:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 76:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 77:
//#line 170 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 78:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 81:
//#line 180 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 82:
//#line 181 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 83:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 84:
//#line 183 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 85:
//#line 184 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 91:
//#line 202 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 92:
//#line 203 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 93:
//#line 204 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 94:
//#line 208 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 95:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 96:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 97:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 100:
//#line 220 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 101:
//#line 221 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 102:
//#line 222 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 103:
//#line 223 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 104:
//#line 224 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 105:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 106:
//#line 226 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 107:
//#line 227 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 108:
//#line 228 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 109:
//#line 229 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 110:
//#line 230 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 111:
//#line 231 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 112:
//#line 232 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 113:
//#line 233 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 114:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 115:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 118:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 119:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 124:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 125:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 146:
//#line 298 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 147:
//#line 299 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 148:
//#line 300 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 149:
//#line 301 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 150:
//#line 302 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 151:
//#line 303 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 152:
//#line 304 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 153:
//#line 305 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 154:
//#line 306 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 156:
//#line 311 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1169 "Parser.java"
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
