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
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   12,   13,   13,   13,   16,   16,   15,   15,    8,    8,
    8,   17,   17,   18,   18,   18,   18,    5,    5,    5,
    5,    5,    5,    5,    5,    5,    5,   24,   24,   24,
   25,   25,   25,   25,   25,   26,   26,   23,   23,   28,
   28,   28,   28,   28,   27,   29,   29,   31,   31,   19,
   19,   19,   22,   22,   33,   22,   32,   32,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   34,   34,   34,   34,   35,   35,
   30,   30,   37,   30,   36,   36,   36,   36,   36,   36,
   14,   14,   14,   38,   38,   38,   39,   39,   39,   41,
   42,   42,   43,   43,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    6,    1,    3,    5,    1,    3,    2,    1,    3,    2,
    2,    1,    3,    3,    2,    2,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    1,    1,
    2,    4,    1,    3,    3,    2,    1,    1,    3,    7,
    6,    6,    6,    6,    1,    1,    3,    1,    2,    4,
    3,    3,    9,    8,    0,   17,    1,    2,    8,   10,
    7,    7,    7,    7,    7,    7,    9,    9,    9,    9,
    9,    9,    9,    8,    1,    3,    2,    2,    1,    2,
    3,    2,    0,    3,    1,    1,    1,    1,    1,    1,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    4,
    1,    3,    1,    1,    5,    5,    4,    4,    4,    4,
    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  147,  146,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   48,   50,   52,   54,   56,    0,
   68,    0,    0,    0,    0,    0,  144,    0,    0,    0,
    0,    0,    0,  126,  128,  129,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,   76,   59,   60,
    0,   49,   51,   53,   55,   57,    0,   41,    0,    0,
    2,    8,    0,   17,    0,   16,    0,    0,    5,    0,
    3,   81,    0,   18,    0,  145,    0,    0,  115,  116,
  117,    0,    0,  118,  119,  120,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   38,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   61,    0,   66,   78,
    0,    0,   46,    0,   39,    0,   14,    0,    0,   69,
    1,   80,  133,  134,    0,  131,    0,    0,    0,    0,
    0,    0,    0,    0,  124,  125,  140,  138,  141,    0,
  139,    0,  137,    0,    0,    0,    0,   37,    0,    0,
    0,    0,    0,    0,   75,   65,    0,   77,   79,    0,
    0,   44,   43,    0,   20,    0,  130,    0,    0,    0,
    0,    0,    0,    0,    0,  136,  135,    0,   26,    0,
    0,   25,    0,    0,   24,    0,    0,    0,    0,   62,
    0,    0,    0,    0,  132,  109,    0,    0,    0,  107,
  110,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,    0,   30,   29,    0,   28,    0,    0,    0,    0,
   72,   74,    0,   73,    0,  106,    0,   93,    0,   91,
    0,    0,   96,    0,    0,    0,   94,    0,   92,   22,
    0,   34,   88,    0,    0,    0,   70,    0,    0,    0,
  104,    0,    0,    0,   89,    0,    0,    0,   85,    0,
   84,   31,   99,   97,  101,  102,    0,  100,   98,   36,
    0,   83,   90,    0,    0,    0,    0,    0,    0,    0,
   86,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,  179,   19,   20,   21,   22,   23,
   24,  129,  111,   40,  112,  252,   69,   70,   25,   26,
   27,   28,   29,   58,   59,   60,   30,   31,   61,   41,
  121,  228,  281,  180,  181,   97,   42,   43,   44,   45,
   46,  135,  136,
};
final static short yysindex[] = {                       -96,
    0,  669,    0,  262,  -38,   25,  -21,   -6,   26,  314,
    0,    0,  256,  -49,  -68,  669,    0,    0,    0,    0,
    0,  -43,    9,  -51,    0,    0,    0,    0,    0,   38,
    0,  560,  -16,  137, -124,  103,    0, -112,   53,  -25,
  109,  138,   60,    0,    0,    0,  111,  114,   -3,  -36,
  -29,  113, -103,  104,  112,  693,    0,    0,    0,    0,
  -80,    0,    0,    0,    0,    0, -133,    0,  142,  162,
    0,    0,  165,    0,  154,    0,  669,  -53,    0,   97,
    0,    0,   72,    0,    2,    0,   -8,  -24,    0,    0,
    0,   78,   78,    0,    0,    0,   78,   -4,   78,   78,
   78,  170,  172,   15,   17,  180,    0,  -34,  192,   27,
  255,  276,  275,  328,  132,  331,    0,  -33,    0,    0,
  625,  102,    0,  146,    0,  148,    0,  140,  285,    0,
    0,    0,    0,    0,  107,    0,  550,  550,  500,   60,
   60,   84,  550,   84,    0,    0,    0,    0,    0,  353,
    0,  355,    0,  357, -108,   42, -108,    0,  369, -198,
 -108,  380,  316, -102,    0,    0,  382,    0,    0,  125,
  401,    0,    0,  412,    0,    2,    0,  427,    0,  -82,
  652,  -78,  340,  -76,  -74,    0,    0, -108,    0,  395,
 -108,    0, -108,  410,    0, -108,  669,  342,  669,    0,
  407,   23,  416,   78,    0,    0,  671,  550,  418,    0,
    0,  550,  419,  502,  431,  -72,  550,  432,  550,  441,
    0, -108,    0,    0, -198,    0,  669,  368,  669,  376,
    0,    0,  443,    0,   89,    0,  249,    0,  251,    0,
  456,  258,    0,  531,  459,  260,    0,  261,    0,    0,
  481,    0,    0,  471,  406,  473,    0,  474,  477,  478,
    0,  485,  486,  284,    0,  489,  490, -198,    0,  494,
    0,    0,    0,    0,    0,    0,  495,    0,    0,    0,
  283,    0,    0,  516,   78,  519,  296,  438,  669,  442,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  134,  155,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,  155,    0,
    0,    0,   28,    0,    0,    0,    0,    0,    0,    0,
    0,  155,  508, -100,  586,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  392,    0,  362,  286,
    0,    0,  417,    0,  468,    0,    0,    0,    0,  568,
    0,    0,  101,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -28,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  155,    0,  444,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   52,
   77,  -26,    0,   -9,    0,    0,    0,    0,    0,  124,
    0,  156,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  606,    0,    0,  155,
    0,    0,    0,    0,    0,    0,    0,    0,  642,    0,
    0,    0,    0,    0,    0,    0,    0,  447,    0,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  452,    0,    0,    0,    0,  451,    0,    0,    0,
    0,    0,  183,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  207,    0,    0,    0,    0,    0,
   63,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  232,    0,    0,    0,
    0,    0,    0,    0,  155,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    7, -148,    0,   -2,    0,    0,    0,  594,   45,
    0,    0,  -22,    6, -119,  311,  454,    0,  569,  570,
  571,  572,  575,  -11,    0,    0,  472,  513,    0,  -30,
    0, -144,    0,  434,  420,  552,    0,  119,  118,  -32,
    0,    0,  423,
};
final static int YYTABLESIZE=971;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
  127,   18,    7,  108,  109,   35,  154,   57,   88,   68,
   33,  113,  112,   18,  111,   74,  139,   92,   49,   93,
  199,  116,   72,  155,   63,  166,    2,  123,  114,   18,
  112,  114,  111,   51,   95,   96,   94,  106,   80,   83,
  194,  127,  127,  127,  120,  127,   38,  127,  227,  114,
  227,  121,  134,   57,  230,  150,   71,  152,  107,  127,
  127,  127,  127,  233,   39,   52,   75,   76,  123,   38,
  123,   77,  123,  149,   18,  151,  122,   11,   12,   84,
  227,  232,  190,  128,  255,  156,  123,  123,  123,  123,
   32,  171,  121,   87,  121,   78,  121,   38,   33,  191,
   82,  100,  142,   35,  144,  251,  101,   32,   81,  169,
  121,  121,  121,  121,   92,   33,   93,  122,   57,  122,
   35,  122,   38,  143,  123,  127,   92,    7,   93,  258,
  132,   92,   73,   93,  124,  122,  122,  122,  122,  202,
  227,  170,   85,  134,  290,   86,   38,  177,  251,   98,
  176,  102,  123,  115,  103,  142,   63,   38,  198,   63,
    1,  118,  117,   63,   34,  201,   63,   11,   12,   38,
  119,   63,   63,   63,   63,  206,  121,   63,  211,  208,
  209,   38,   71,  212,  213,  217,  218,  219,  220,  244,
  245,   75,   19,  122,   18,   82,   18,   95,   96,   94,
  125,  122,  108,  108,  211,  126,   95,   67,   35,  235,
  140,  141,  127,   73,  113,  113,  113,  145,  146,   10,
  107,  131,  107,  165,   18,   82,   18,  107,  147,   34,
  148,  103,  112,  253,  111,   47,  138,   48,  153,   11,
   12,   11,   12,   89,   90,   91,   11,   12,  143,  157,
   50,  114,  137,  104,  286,  105,  143,  127,  133,   37,
  127,  127,  127,  127,  127,  127,  127,  127,    7,  127,
  127,  127,  127,  127,  127,  127,  127,  127,  127,  127,
  142,   36,   37,  158,  123,   42,   18,  123,  123,  123,
  123,  123,  123,  123,  123,  159,  123,  123,  123,  123,
  123,  123,  123,  123,  123,  123,  123,   71,  121,   36,
   37,  121,  121,  121,  121,  121,  121,  121,  121,  160,
  121,  121,  121,  121,  121,  121,  121,  121,  121,  121,
  121,   95,  161,  122,   36,   37,  122,  122,  122,  122,
  122,  122,  122,  122,   42,  122,  122,  122,  122,  122,
  122,  122,  122,  122,  122,  122,  103,   82,   36,   37,
   82,   40,   82,   82,   82,   82,   82,   82,  162,   36,
   37,  164,   82,   82,   82,   82,   82,   82,   82,   82,
  143,   36,   37,  143,   32,  143,  143,  143,  143,  143,
  143,   47,  163,   36,   37,  143,  143,  143,  143,  143,
  143,  143,  143,  172,   67,  174,   89,   90,   91,  175,
   42,  186,  142,  187,  188,  142,   19,  142,  142,  142,
  142,  142,  142,  113,  113,  113,  193,  142,  142,  142,
  142,  142,  142,  142,  142,   47,   56,  196,  197,   71,
  200,  203,   71,   45,   71,   71,   71,   71,   71,   71,
   47,  204,  222,  225,   71,   71,   71,   71,   71,   71,
   71,   71,  178,   95,  229,  231,   95,   15,   95,   95,
   95,   95,   95,   95,  234,   19,  238,  240,   95,   95,
   95,   95,   95,   95,   95,   95,   40,   45,  103,  243,
  247,  103,  254,  103,  103,  103,  103,  103,  103,  249,
  256,  257,   45,  103,  103,  103,  103,  103,  103,  103,
  103,  259,   53,  260,  261,    6,   47,  265,    5,    7,
  262,    6,  266,  267,  268,    7,    8,    9,   10,  269,
  270,  271,  272,    9,   10,  273,  274,   11,   12,   13,
   14,   19,   42,  275,  276,   42,  277,  278,  279,   42,
   42,   42,  282,  283,  284,  285,  288,   42,   42,  287,
  289,   42,   42,   42,   42,   75,  291,    4,   45,   23,
   53,  182,  184,    6,   27,   87,  185,    7,  280,  173,
   54,   62,   63,   64,   65,    9,   10,   66,   55,  167,
  130,   13,   15,   99,    0,    0,   53,  207,  205,    6,
    0,  214,  215,    7,    0,    0,    0,    0,    0,    0,
    0,    9,   10,    0,    0,    0,  216,   13,   40,    0,
    0,   40,  178,    0,  178,   40,   40,   40,    0,    0,
    0,    0,    0,   40,   40,    0,    0,   40,   40,   40,
   40,  237,    0,  110,  110,  239,    0,  242,   47,    0,
  246,   47,  248,  178,    0,   47,   47,   47,    0,    0,
    0,    0,    0,   47,   47,    0,    0,   47,   47,   47,
   47,    0,  178,   19,    0,    0,   19,  264,    0,    0,
   19,   19,   19,   53,   79,    0,    6,    0,   19,   19,
    7,    0,   19,   19,   19,   19,    0,    0,    9,   10,
   45,  110,    0,   45,   13,    0,    0,   45,   45,   45,
   67,    0,    0,    0,    0,   45,   45,    0,    0,   45,
   45,   45,   45,    0,   15,    0,    0,   15,    0,    0,
   64,   15,   15,   15,    0,    0,    0,    0,    0,   15,
   15,    0,    0,   15,   15,   15,   15,    0,  189,  168,
  192,    0,    0,  110,  195,    0,   53,    0,   53,    6,
  183,    6,    0,    7,  241,    7,  109,    0,    0,    0,
    0,    9,   10,    9,   10,    0,  210,   13,    0,   13,
    0,  221,    0,    0,  223,    0,  224,   53,    0,  226,
    6,    0,    0,  263,    7,  236,    0,    0,    0,    0,
    0,    0,    9,   10,    0,    0,   53,    0,   13,    6,
    0,    0,    0,    7,    0,  250,    5,    0,  110,    6,
    0,    9,   10,    7,    8,    0,    0,   13,    0,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,    0,
    0,    0,   67,    0,    0,   67,    0,    0,    0,   67,
    0,    0,   67,    0,    0,    0,    0,   67,   67,   67,
   67,  110,   64,   67,    0,   64,    0,    0,    0,   64,
    0,    0,   64,    0,    0,    0,    0,   64,   64,   64,
   64,   53,    0,   64,    6,    0,    0,    0,    7,    0,
    0,   54,    0,    0,    0,    0,    9,   10,  109,   55,
    0,  109,   13,  105,  105,  109,    0,    0,   53,    0,
    0,    6,    0,  109,  109,    7,    0,    0,    0,  109,
    0,    0,    0,    9,   10,    5,    0,   53,    6,   13,
    6,    0,    7,    8,    7,    0,    0,    0,    0,    0,
    9,   10,    9,   10,   11,   12,   13,   14,   13,   53,
    0,    0,    6,    0,    0,    0,    7,    0,    0,   54,
    0,    0,    0,    0,    9,   10,    0,   55,    0,    0,
   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,    4,    0,   40,   41,   44,   41,   10,   39,   59,
    4,   41,   41,   16,   41,   59,   41,   43,   40,   45,
  123,   52,   16,   58,  125,   59,  123,    0,   51,   32,
   59,   41,   59,   40,   60,   61,   62,   41,   32,   34,
  160,   41,   42,   43,   56,   45,   45,   47,  197,   59,
  199,    0,   85,   56,  199,   41,  125,   41,  257,   59,
   60,   61,   62,   41,   40,   40,   22,   59,   41,   45,
   43,  123,   45,   59,   77,   59,    0,  276,  277,   35,
  229,   59,   41,   77,  229,  108,   59,   60,   61,   62,
   41,  122,   41,   41,   43,   58,   45,   45,   41,   58,
    0,   42,   97,   41,   99,  225,   47,   58,  125,  121,
   59,   60,   61,   62,   43,   58,   45,   41,  121,   43,
   58,   45,   45,    0,  258,  125,   43,  125,   45,   41,
   59,   43,  257,   45,  268,   59,   60,   61,   62,  170,
  289,   40,   40,  176,  289,  258,   45,   41,  268,   41,
   44,   41,  125,   41,   41,    0,  257,   45,  261,  260,
  257,   58,   59,  264,  268,   41,  267,  276,  277,   45,
   59,  272,  273,  274,  275,  178,  125,  278,  181,  262,
  263,   45,    0,  262,  263,  262,  263,  262,  263,  262,
  263,   58,   59,  274,  197,   59,  199,   60,   61,   62,
   59,  125,  262,  263,  207,   44,    0,  257,   44,  204,
   92,   93,   59,  257,   60,   61,   62,  100,  101,  273,
  257,  125,  257,  257,  227,  125,  229,  257,   59,  268,
   59,    0,  261,  227,  261,  257,  261,  259,   59,  276,
  277,  276,  277,  269,  270,  271,  276,  277,  125,   58,
  257,  261,  261,  257,  285,  259,  261,  257,  257,  258,
  260,  261,  262,  263,  264,  265,  266,  267,  266,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  125,  257,  258,  257,  257,    0,  289,  260,  261,  262,
  263,  264,  265,  266,  267,   41,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  125,  257,  257,
  258,  260,  261,  262,  263,  264,  265,  266,  267,   44,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  125,   58,  257,  257,  258,  260,  261,  262,  263,
  264,  265,  266,  267,   59,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  125,  257,  257,  258,
  260,    0,  262,  263,  264,  265,  266,  267,   41,  257,
  258,   41,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  257,  258,  260,  123,  262,  263,  264,  265,  266,
  267,    0,  261,  257,  258,  272,  273,  274,  275,  276,
  277,  278,  279,  258,  257,  266,  269,  270,  271,  125,
  125,   59,  257,   59,   58,  260,    0,  262,  263,  264,
  265,  266,  267,  269,  270,  271,   58,  272,  273,  274,
  275,  276,  277,  278,  279,   44,  123,   58,  123,  257,
   59,   41,  260,    0,  262,  263,  264,  265,  266,  267,
   59,   40,   58,   44,  272,  273,  274,  275,  276,  277,
  278,  279,  123,  257,  123,   59,  260,    0,  262,  263,
  264,  265,  266,  267,   59,   59,   59,   59,  272,  273,
  274,  275,  276,  277,  278,  279,  125,   44,  257,   59,
   59,  260,  125,  262,  263,  264,  265,  266,  267,   59,
  125,   59,   59,  272,  273,  274,  275,  276,  277,  278,
  279,  263,  257,  263,   59,  260,  125,   59,  257,  264,
  263,  260,  263,  263,   44,  264,  265,  272,  273,   59,
  125,   59,   59,  272,  273,   59,   59,  276,  277,  278,
  279,  125,  257,   59,   59,  260,  263,   59,   59,  264,
  265,  266,   59,   59,  272,   40,  261,  272,  273,   41,
  123,  276,  277,  278,  279,   58,  125,    0,  125,  123,
  257,  138,  139,  260,  123,  125,  143,  264,  268,  126,
  267,   13,   13,   13,   13,  272,  273,   13,  275,  118,
   78,  278,  125,   42,   -1,   -1,  257,  178,  176,  260,
   -1,  262,  263,  264,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,   -1,  183,  278,  257,   -1,
   -1,  260,  123,   -1,  123,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  208,   -1,   50,   51,  212,   -1,  214,  257,   -1,
  217,  260,  219,  123,   -1,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  123,  257,   -1,   -1,  260,  244,   -1,   -1,
  264,  265,  266,  257,  125,   -1,  260,   -1,  272,  273,
  264,   -1,  276,  277,  278,  279,   -1,   -1,  272,  273,
  257,  108,   -1,  260,  278,   -1,   -1,  264,  265,  266,
  125,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,   -1,  257,   -1,   -1,  260,   -1,   -1,
  125,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  155,  125,
  157,   -1,   -1,  160,  161,   -1,  257,   -1,  257,  260,
  261,  260,   -1,  264,  263,  264,  125,   -1,   -1,   -1,
   -1,  272,  273,  272,  273,   -1,  125,  278,   -1,  278,
   -1,  188,   -1,   -1,  191,   -1,  193,  257,   -1,  196,
  260,   -1,   -1,  263,  264,  125,   -1,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  257,   -1,  278,  260,
   -1,   -1,   -1,  264,   -1,  222,  257,   -1,  225,  260,
   -1,  272,  273,  264,  265,   -1,   -1,  278,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  268,  257,  278,   -1,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,  262,  263,  264,   -1,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  272,  273,  257,   -1,  257,  260,  278,
  260,   -1,  264,  265,  264,   -1,   -1,   -1,   -1,   -1,
  272,  273,  272,  273,  276,  277,  278,  279,  278,  257,
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

//#line 287 ".\gramatica.y"

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
//#line 701 "Parser.java"
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
case 34:
//#line 79 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 38:
//#line 89 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 39:
//#line 93 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 40:
//#line 94 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 41:
//#line 95 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 45:
//#line 105 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 46:
//#line 106 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 47:
//#line 107 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 61:
//#line 130 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 62:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 63:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 64:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 65:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 66:
//#line 138 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 67:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 70:
//#line 148 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 71:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 72:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 73:
//#line 151 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 74:
//#line 152 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 80:
//#line 170 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 81:
//#line 171 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 82:
//#line 172 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 83:
//#line 176 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 84:
//#line 177 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 85:
//#line 178 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 86:
//#line 179 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 89:
//#line 188 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 90:
//#line 189 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 91:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 92:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 93:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 94:
//#line 193 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 95:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 96:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 97:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 98:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 99:
//#line 198 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 100:
//#line 199 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 101:
//#line 200 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 102:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 103:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 104:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 107:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 108:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 113:
//#line 220 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 114:
//#line 221 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 135:
//#line 266 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 136:
//#line 267 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 137:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 138:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 139:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 140:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 141:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 142:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 143:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 145:
//#line 279 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1118 "Parser.java"
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
