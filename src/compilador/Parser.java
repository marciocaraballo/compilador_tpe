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
    7,    8,   14,   15,   15,   15,   15,   15,   15,   16,
   21,   21,   17,   17,   23,   23,   18,   18,   25,   27,
   27,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   19,   19,   19,   19,   19,   13,   29,   29,   29,
   32,   32,   31,   31,    9,    9,    9,   33,   33,   34,
   34,   34,   34,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   40,   40,   40,   41,   41,   41,   41,
   41,   42,   42,   39,   39,   43,   43,   43,   43,   43,
   26,   44,   44,   28,   28,   35,   35,   35,   38,   38,
   45,   38,   22,   22,   36,   36,   36,   36,   36,   36,
   36,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   46,   46,   46,   46,   24,   24,   20,   20,   48,   20,
   47,   47,   47,   47,   47,   47,   30,   30,   30,   49,
   49,   49,   50,   50,   50,   52,   53,   53,   54,   54,
   37,   37,   37,   37,   37,   37,   37,   37,   37,   51,
   51,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    1,    2,    1,    2,    2,    3,    9,
    1,    2,    9,   10,    1,    4,    1,    3,    7,    1,
    4,    6,    7,    5,    5,    5,    5,    6,    6,    6,
    6,    5,    4,    3,    3,    4,    2,    1,    3,    5,
    1,    3,    2,    1,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    1,    2,    8,   10,    7,    7,    7,    7,
    7,    7,    9,    9,    9,    9,    9,    9,    9,    8,
    1,    3,    2,    2,    1,    2,    3,    2,    0,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  173,  172,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   74,   76,   78,   80,
   82,   94,    0,    0,    0,    0,    0,  170,    0,    0,
    0,    0,    0,    0,  152,  154,  155,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,  102,   85,
   86,    0,   75,   77,   79,   81,   83,    0,   67,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  107,    0,   19,    0,  171,    0,    0,    0,
  141,  142,  143,    0,    0,  144,  145,  146,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   87,    0,   92,
    0,  104,    0,   72,    0,   65,    0,   15,    0,    0,
    0,    0,    0,    0,    0,   23,   24,   26,    0,   37,
    0,   95,    1,  106,  159,  160,    0,  157,    0,    0,
    0,    0,    0,    0,    0,    0,  150,  151,  166,  164,
  167,    0,  165,    0,  163,    0,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    0,  101,   91,    0,  103,
  105,    0,    0,   70,   69,    0,    0,    0,    0,   40,
    0,   25,   27,    0,   57,   21,   22,   28,    0,    0,
  156,    0,    0,    0,    0,    0,    0,    0,    0,  162,
  161,    0,   47,    0,    0,   46,    0,    0,   45,    0,
    0,    0,    0,   88,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   29,   38,  158,  135,    0,  133,  136,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   42,    0,   51,   50,    0,   49,    0,    0,    0,
    0,   98,  100,    0,   99,    0,    0,    0,    0,   54,
    0,    0,  132,    0,  119,    0,  117,    0,    0,  122,
    0,    0,    0,  120,    0,  118,   43,    0,   60,    0,
    0,    0,    0,   96,    0,    0,   56,    0,   41,    0,
    0,    0,  130,    0,    0,    0,  115,    0,    0,    0,
  111,    0,  110,    0,   35,    0,    0,   52,    0,  125,
  123,  127,  128,    0,  126,  124,   62,    0,  109,    0,
    0,    0,   31,    0,    0,   39,  116,    0,    0,    0,
    0,    0,    0,   32,    0,   36,    0,    0,   33,   30,
    0,   34,    0,    0,    0,    0,  112,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  134,  135,  136,  137,  138,  139,  315,   41,
  334,  259,  316,  204,  140,   26,  191,  121,  112,   42,
  113,  289,   70,   71,   27,   28,   29,   30,   31,   59,
   60,   61,   32,   62,  328,  208,   99,   43,   44,   45,
   46,   47,  147,  148,
};
final static short yysindex[] = {                       -96,
    0,  933,    0,  370,  -40,   19,   11,  -20,    9,  612,
    0,    0,  183,  -54,  698,    0,    0,    0,    0,    0,
    0,    0,  -50,   40,  -42,   66,    0,    0,    0,    0,
    0,    0,  721,  731,   58, -136,   90,    0, -126,   34,
   97,   -4,   65,  -14,    0,    0,    0,  103,  116,   28,
  -27,   -3,   37,  -82,   55,  113,  979,    0,    0,    0,
    0,  -80,    0,    0,    0,    0,    0, -228,    0,  146,
  165,    0,    0,  178,    0,  188,    0,  956,  -49,    0,
  756,    0,    0,  154,    0,   71,    0,   -6,  -23,    8,
    0,    0,    0,   75,   75,    0,    0,    0,   75,   75,
   75,   75,  219,  222,    2,    4,  229,    0,  -24,  240,
   44,  263,  265,  249,  272,   56,  279,    0,  -47,    0,
  858,    0,   22,    0,   69,    0,   73,    0,   25,  306,
  476,  297,  923,  217,  223,    0,    0,    0,   85,    0,
  316,    0,    0,    0,    0,    0,   30,    0,  688,  688,
  -91,  688,  -14,  -14,   86,   86,    0,    0,    0,    0,
    0,  301,    0,  324,    0,  337,  129,   27,  129,    0,
  342, -169,  129,  345,  323, -115,    0,    0,  394,    0,
    0,   39,  413,    0,    0,   34,   37,   32,  979,    0,
  185,    0,    0,   85,    0,    0,    0,    0,  184,   71,
    0,  496,    0,  571,  145,  147,  638,  149,  152,    0,
    0,  129,    0,  402,  129,    0,  129,  437,    0,  129,
  933,  360,  933,    0,  425,   14,  448,  -18,  455,   42,
  296,  839,  477,    0,    0,    0,    0,  897,    0,    0,
  688,  457,  688,  459,  657,  460,  155,  688,  473,  688,
  480,    0,  129,    0,    0, -169,    0,  933,  415,  933,
  416,    0,    0,  483,    0,  372,  -69,  497,  302,    0,
  430,   75,    0,  295,    0,  299,    0,  504,  303,    0,
  668,  505,  304,    0,  305,    0,    0,  528,    0,  933,
  514,  458,  523,    0,  602,  461,    0,  526,    0,  553,
  536,  537,    0,  538,  539,  339,    0,  541,  544, -169,
    0,  546,    0,  496,    0,  179,  923,    0,  554,    0,
    0,    0,    0,  556,    0,    0,    0,  340,    0,  887,
 -102,  565,    0,  500,  -99,    0,    0,  586,  503,  496,
  374,   85,  579,    0,   75,    0,  996,  580,    0,    0,
  599,    0,  380,  531,  933,  532,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  200,  128,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,    0,    0,  128,
    0,    0,    0,  115,    0,    0,    0,    0,    0,    0,
    0,    0,  128,  593,  779,  798,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,  530,
  427,    0,    0,  506,    0,  560,    0,    0,    0,    0,
  653,    0,    0,  225,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  128,    0,  450,    0,    0,    0,  128,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  159,  201,  -34,  -30,    0,    0,    0,    0,
    0,  248,    0,  271,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  817,    0,
    0,  128,    0,    0,    0,  128,  128,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  877,    0,    0,    0,    0,    0,    0,    0,
    0,  533,    0,    0,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  182,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  535,    0,    0,    0,    0,  -88,    0,    0,
    0,    0,    0,  314,    0,    0,    0,    0,  -78,    0,
    0,  128,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  344,    0,    0,    0,    0,    0,   70,    0,  -83,
    0,    0,    0,    0,    0,    0,    0,  139,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  404,    0,    0,    0,    0,    0,  182,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  128,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -2,  -15,    0,   -9,    0,    0,    0,    0,  643,
   87,    0,    0,    0,    0,  527,  540,  542,  420,  -37,
    0, -208,  329, -149,  463,  -28,    0,  474,   -8,   63,
 -162,  355,  548,    0,   47,   80,   82,   83,   84,  -21,
    0,    0,  -44,    0,    0,  936,  630,    0,  354,  350,
  -64,    0,    0,  486,
};
final static int YYTABLESIZE=1274;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
   58,   34,   89,   36,   69,  138,  137,  223,   75,  218,
  140,  178,  109,  110,  261,  117,  166,  151,   73,   52,
  340,  146,  266,  138,  137,  312,    2,  101,  140,  124,
   81,  202,  102,  167,  142,  122,  113,  114,   94,  125,
   95,  114,  162,  115,  164,   73,   55,   58,   53,  141,
   50,  292,  238,  223,  264,   97,   98,   96,   40,   63,
  161,  182,  163,   39,  186,   73,   39,  214,  107,   39,
  201,  230,  263,  200,   88,  133,   39,  116,   39,  225,
   78,   39,  268,   39,  215,  183,   39,  108,   58,   73,
  179,  153,   64,  288,   65,   66,   67,   84,   77,  181,
  168,   59,   39,  141,   73,   58,   11,   12,  335,   76,
   61,   58,  119,  118,  149,   39,   83,   73,   59,   39,
   74,   58,   85,   79,   97,   98,   96,   61,   94,   86,
   95,   87,  153,  153,  153,  146,  153,   90,  153,  203,
  203,  203,  203,  103,  226,  222,  356,  288,  228,  229,
  153,  153,  153,  153,  142,  149,  104,  149,  147,  149,
    1,  155,  156,  188,  330,   54,  188,  122,    6,  207,
   73,  120,    7,  149,  149,  149,  149,  113,   63,   58,
    9,   10,  114,   55,   55,   35,   13,  139,  139,  139,
  347,  296,  237,  123,  240,   55,   94,  203,   95,  147,
  148,  147,   68,  147,  126,  258,   74,  258,  127,  177,
  181,   64,  144,   65,   66,   67,  153,  147,  147,  147,
  147,   36,   58,   10,  108,  138,  137,   35,  240,  108,
  140,  203,  108,  203,  300,  203,   51,  150,  203,  149,
  203,  148,  150,  148,  258,  148,  128,  169,   11,   12,
  231,   11,   12,  108,  149,  290,  203,  101,   20,  148,
  148,  148,  148,   53,   91,   92,   93,   48,  152,   49,
  168,  203,   11,   12,   73,   37,   38,  159,   37,   38,
  160,   37,   38,  147,  105,  203,  106,  165,   37,   38,
   37,   38,  269,   37,   38,   37,   38,  169,   37,   38,
  170,  258,   73,  171,  237,   73,  173,  351,  172,   73,
   73,   73,  174,   97,   37,   38,  175,   73,   73,  176,
  240,   73,   73,   73,   73,  148,  184,  145,   38,   68,
  237,   37,   38,   91,   92,   93,  270,  240,   94,  258,
   95,  196,  298,  121,   94,  187,   95,  197,  153,  108,
  188,  153,  153,  153,  153,  153,  153,  153,  153,  210,
  153,  153,  153,  153,  153,  153,  153,  153,  153,  153,
  153,  149,  169,  199,  149,  149,  149,  149,  149,  149,
  149,  149,  211,  149,  149,  149,  149,  149,  149,  149,
  149,  149,  149,  149,  212,  168,  139,  139,  139,  217,
   53,   53,  220,  129,   11,   12,  241,  242,  243,  244,
  248,  249,   53,  250,  251,  147,  281,  282,  147,  147,
  147,  147,  147,  147,  147,  147,   68,  147,  147,  147,
  147,  147,  147,  147,  147,  147,  147,  147,   97,   54,
  331,  332,    6,  134,  134,  221,    7,  153,  154,   71,
  157,  158,  224,  227,    9,   10,  131,  148,  233,  253,
  148,  148,  148,  148,  148,  148,  148,  148,  121,  148,
  148,  148,  148,  148,  148,  148,  148,  148,  148,  148,
  256,  108,  260,  262,  108,   68,  108,  108,  108,  108,
  108,  108,   33,   71,  202,  267,  108,  108,  108,  108,
  108,  108,  108,  108,  169,   20,  265,  169,   71,  169,
  169,  169,  169,  169,  169,  275,  272,  277,  280,  169,
  169,  169,  169,  169,  169,  169,  169,  168,  129,   66,
  168,  284,  168,  168,  168,  168,  168,  168,  286,  291,
  293,  294,  168,  168,  168,  168,  168,  168,  168,  168,
  190,   68,  195,   54,  299,  297,  129,  301,  198,   16,
    7,  302,  303,  307,   20,  304,  308,  309,  130,  131,
   97,  310,  311,   97,   71,   97,   97,   97,   97,   97,
   97,  313,  312,  317,  318,   97,   97,   97,   97,   97,
   97,   97,   97,  319,  320,  321,  322,  323,  189,  325,
  121,  324,  326,  121,  329,  121,  121,  121,  121,  121,
  121,  338,  336,  234,  337,  121,  121,  121,  121,  121,
  121,  121,  121,  342,  343,  345,    5,  346,   54,    6,
   20,    6,  295,    7,    8,    7,  348,  350,  352,  353,
  354,    9,   10,    9,   10,   11,   12,   13,   14,   13,
  101,  271,    4,  355,   66,   44,  357,   48,  192,  341,
  129,  235,  232,  129,  327,  129,  129,  129,  129,  129,
  129,  193,  100,  194,  185,  129,  129,  129,  129,  129,
  129,  129,  129,   68,   16,  236,   68,    0,    0,    0,
   68,   68,   68,  111,  111,  239,    0,    0,   68,   68,
    0,    0,   68,   68,   68,   68,   71,    0,    0,   71,
    0,    0,    0,   71,   71,   71,    0,    0,    0,    0,
    0,   71,   71,    0,  314,   71,   71,   71,   71,    0,
    0,    0,   54,    0,   57,    6,  333,    0,    0,    7,
    0,  188,   55,    0,    0,    0,    0,    9,   10,  339,
   56,  111,   54,   13,  344,    6,    0,    0,    0,    7,
  202,  349,   20,    0,    0,   20,  339,    9,   10,   20,
   20,   20,    0,   13,    0,    0,    0,   20,   20,  202,
    0,   20,   20,   20,   20,    0,   66,    0,    0,   66,
  202,    0,    0,   66,   66,   66,    0,    0,    0,    0,
    0,   66,   66,    0,    0,   66,   66,   66,   66,  213,
  202,  216,    0,    0,  111,  219,   16,    0,    0,   16,
    0,    0,   72,   16,   16,   16,    0,   54,    0,    0,
    6,   16,   16,    0,    7,   16,   16,   16,   16,    0,
    0,    0,    9,   10,    0,   80,    0,    0,   13,    0,
    0,    0,    0,    0,  252,   82,    0,  254,   54,  255,
    0,    6,  257,  245,  246,    7,    0,  188,   54,    0,
    0,    6,    0,    9,   10,    7,    0,    0,   55,   13,
  143,    0,    0,    9,   10,    0,   56,    0,    0,   13,
    0,    0,    0,    0,   54,  287,    0,    6,  111,  245,
  246,    7,    0,   89,    0,    0,    0,    0,    0,    9,
   10,    0,    0,   54,    0,   13,    6,    0,    0,  278,
    7,    0,   93,    0,   54,    0,    0,    6,    9,   10,
  305,    7,    0,    0,   13,    0,    0,    0,    0,    9,
   10,   90,    0,    0,   54,   13,    0,    6,    0,    0,
    0,    7,  111,    0,    5,    0,    0,    6,    0,    9,
   10,    7,    8,  180,    0,   13,    0,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    5,    0,    0,
    6,    0,  180,    0,    7,    8,    0,    5,    0,    0,
    6,    0,    9,   10,    7,    8,   11,   12,   13,   14,
    0,  135,    9,   10,    0,    0,   11,   12,   13,   14,
    0,  273,    5,    0,    0,    6,    0,    0,    0,    7,
    8,  273,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,   89,    0,    0,   89,    0,
    0,    0,   89,    0,   89,   89,    0,    0,    0,    0,
   89,   89,   89,   89,   93,    0,   89,   93,    0,    0,
    0,   93,    0,   93,   93,    0,    0,    0,    0,   93,
   93,   93,   93,   90,    0,   93,   90,    0,    0,    0,
   90,    0,   90,   90,  205,  206,    0,  209,   90,   90,
   90,   90,    0,    0,   90,   54,    0,    0,    6,    0,
    0,    0,    7,    0,  188,   55,    0,    0,    0,    0,
    9,   10,    0,   56,   54,    0,   13,    6,    0,    0,
    0,    7,    0,    0,   55,    0,    0,    0,    0,    9,
   10,    0,   56,  135,    0,   13,  135,    0,  131,  131,
  135,    0,  247,   54,    0,    0,    6,    0,  135,  135,
    7,    0,  188,   54,  135,    0,    6,    0,    9,   10,
    7,    0,    0,    0,   13,    0,    0,    0,    9,   10,
    0,    0,    0,    0,   13,    0,  274,    0,  276,    5,
  279,    0,    6,  283,    0,  285,    7,    8,  188,    5,
    0,    0,    6,    0,    9,   10,    7,    8,   11,   12,
   13,   14,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,    5,    0,    0,  129,  306,    0,    0,    7,
    8,    0,    0,    0,    0,    0,    0,  130,  131,    0,
  247,   11,   12,  132,   14,   54,    0,    0,    6,    0,
    0,    0,    7,    0,    0,   55,    0,    0,    0,    0,
    9,   10,   54,   56,    0,    6,   13,    0,    0,    7,
    0,  188,    0,    0,    0,    0,    0,    9,   10,    0,
    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   10,    4,   40,   44,   59,   41,   41,  123,   59,  172,
   41,   59,   40,   41,  223,   53,   41,   41,   34,   40,
  123,   86,   41,   59,   59,  125,  123,   42,   59,  258,
   33,  123,   47,   58,   79,   57,  125,   41,   43,  268,
   45,  125,   41,   52,   41,    0,  125,   57,   40,   78,
   40,  260,  202,  123,   41,   60,   61,   62,   40,   13,
   59,   40,   59,   45,   40,   81,   45,   41,   41,   45,
   41,   40,   59,   44,   41,   78,   45,   41,   45,   41,
  123,   45,   41,   45,   58,  123,   45,  257,   41,   44,
  119,    0,   13,  256,   13,   13,   13,   35,   59,  121,
  109,   41,   45,  132,   59,   58,  276,  277,  317,   23,
   41,  121,   58,   59,    0,   45,   59,  133,   58,   45,
  257,  131,   36,   58,   60,   61,   62,   58,   43,   40,
   45,  258,   41,   42,   43,  200,   45,   41,   47,  149,
  150,  151,  152,   41,  182,  261,  355,  310,  186,  187,
   59,   60,   61,   62,  199,   41,   41,   43,    0,   45,
  257,   99,  100,  266,  314,  257,  266,  189,  260,  261,
  125,   59,  264,   59,   60,   61,   62,  266,  132,  189,
  272,  273,  266,  262,  263,  268,  278,   60,   61,   62,
  340,  261,  202,  274,  204,  274,   43,  207,   45,   41,
    0,   43,  257,   45,   59,  221,  257,  223,   44,  257,
  232,  132,   59,  132,  132,  132,  125,   59,   60,   61,
   62,   44,  232,  273,    0,  261,  261,  268,  238,  257,
  261,  241,  257,  243,  272,  245,  257,  261,  248,  125,
  250,   41,  261,   43,  260,   45,   59,    0,  276,  277,
  188,  276,  277,  257,  261,  258,  266,   58,   59,   59,
   60,   61,   62,  125,  269,  270,  271,  257,  261,  259,
    0,  281,  276,  277,  290,  257,  258,   59,  257,  258,
   59,  257,  258,  125,  257,  295,  259,   59,  257,  258,
  257,  258,  230,  257,  258,  257,  258,   58,  257,  258,
  257,  317,  257,   41,  314,  260,   58,  345,   44,  264,
  265,  266,   41,    0,  257,  258,  261,  272,  273,   41,
  330,  276,  277,  278,  279,  125,  258,  257,  258,  257,
  340,  257,  258,  269,  270,  271,   41,  347,   43,  355,
   45,  125,   41,    0,   43,   40,   45,  125,  257,  125,
  266,  260,  261,  262,  263,  264,  265,  266,  267,   59,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  125,   58,  260,  261,  262,  263,  264,  265,
  266,  267,   59,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,   58,  125,  269,  270,  271,   58,
  262,  263,   58,    0,  276,  277,  262,  263,  262,  263,
  262,  263,  274,  262,  263,  257,  262,  263,  260,  261,
  262,  263,  264,  265,  266,  267,    0,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  257,
  262,  263,  260,  262,  263,  123,  264,   94,   95,    0,
  101,  102,   59,   41,  272,  273,  273,  257,  274,   58,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
   44,  257,  123,   59,  260,   59,  262,  263,  264,  265,
  266,  267,  123,   44,  123,   41,  272,  273,  274,  275,
  276,  277,  278,  279,  257,    0,   59,  260,   59,  262,
  263,  264,  265,  266,  267,   59,   40,   59,   59,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  125,    0,
  260,   59,  262,  263,  264,  265,  266,  267,   59,  125,
  125,   59,  272,  273,  274,  275,  276,  277,  278,  279,
  131,  125,  133,  257,  125,   59,  260,  263,  139,    0,
  264,  263,   59,   59,   59,  263,  263,  263,  272,  273,
  257,   44,   59,  260,  125,  262,  263,  264,  265,  266,
  267,   59,  125,  123,   59,  272,  273,  274,  275,  276,
  277,  278,  279,   41,   59,   59,   59,   59,  123,   59,
  257,  263,   59,  260,   59,  262,  263,  264,  265,  266,
  267,  272,   59,  194,   59,  272,  273,  274,  275,  276,
  277,  278,  279,   59,  125,   40,  257,  125,  257,  260,
  125,  260,  261,  264,  265,  264,  263,   59,   59,   41,
  261,  272,  273,  272,  273,  276,  277,  278,  279,  278,
   58,  232,    0,  123,  125,  123,  125,  123,  132,  331,
  257,  199,  189,  260,  310,  262,  263,  264,  265,  266,
  267,  132,   43,  132,  127,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  125,  200,  260,   -1,   -1,   -1,
  264,  265,  266,   51,   52,  125,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,  123,  276,  277,  278,  279,   -1,
   -1,   -1,  257,   -1,  123,  260,  317,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  330,
  275,  109,  257,  278,  335,  260,   -1,   -1,   -1,  264,
  123,  342,  257,   -1,   -1,  260,  347,  272,  273,  264,
  265,  266,   -1,  278,   -1,   -1,   -1,  272,  273,  123,
   -1,  276,  277,  278,  279,   -1,  257,   -1,   -1,  260,
  123,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  167,
  123,  169,   -1,   -1,  172,  173,  257,   -1,   -1,  260,
   -1,   -1,  125,  264,  265,  266,   -1,  257,   -1,   -1,
  260,  272,  273,   -1,  264,  276,  277,  278,  279,   -1,
   -1,   -1,  272,  273,   -1,  125,   -1,   -1,  278,   -1,
   -1,   -1,   -1,   -1,  212,  125,   -1,  215,  257,  217,
   -1,  260,  220,  262,  263,  264,   -1,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,  267,  278,
  125,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
   -1,   -1,   -1,   -1,  257,  253,   -1,  260,  256,  262,
  263,  264,   -1,  125,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  257,   -1,  278,  260,   -1,   -1,  263,
  264,   -1,  125,   -1,  257,   -1,   -1,  260,  272,  273,
  263,  264,   -1,   -1,  278,   -1,   -1,   -1,   -1,  272,
  273,  125,   -1,   -1,  257,  278,   -1,  260,   -1,   -1,
   -1,  264,  310,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,  265,  125,   -1,  278,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,  125,   -1,  264,  265,   -1,  257,   -1,   -1,
  260,   -1,  272,  273,  264,  265,  276,  277,  278,  279,
   -1,  125,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,  125,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
  265,  125,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,  266,  267,  149,  150,   -1,  152,  272,  273,
  274,  275,   -1,   -1,  278,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,   -1,  275,  257,   -1,  278,  260,   -1,  262,  263,
  264,   -1,  207,  257,   -1,   -1,  260,   -1,  272,  273,
  264,   -1,  266,  257,  278,   -1,  260,   -1,  272,  273,
  264,   -1,   -1,   -1,  278,   -1,   -1,   -1,  272,  273,
   -1,   -1,   -1,   -1,  278,   -1,  241,   -1,  243,  257,
  245,   -1,  260,  248,   -1,  250,  264,  265,  266,  257,
   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,  277,
  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,  281,   -1,   -1,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
  295,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,  257,  275,   -1,  260,  278,   -1,   -1,  264,
   -1,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,   -1,   -1,  278,
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
"cuerpo_funcion_sentencias_con_return : sentencia_ejecutable_con_return",
"sentencia_ejecutable_con_return : sentencia_when_con_return",
"sentencia_ejecutable_con_return : DEFER sentencia_when_con_return",
"sentencia_ejecutable_con_return : seleccion_con_return",
"sentencia_ejecutable_con_return : DEFER seleccion_con_return",
"sentencia_ejecutable_con_return : sentencia_do_con_return sentencia_return",
"sentencia_ejecutable_con_return : DEFER sentencia_do_con_return sentencia_return",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : bloque_sentencias_when sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';' sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' sentencias_ejecutables sentencia_return '}'",
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

//#line 347 ".\gramatica.y"

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
//#line 813 "Parser.java"
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
case 30:
//#line 78 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 34:
//#line 88 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 39:
//#line 102 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 44:
//#line 113 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 45:
//#line 114 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 46:
//#line 115 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 47:
//#line 116 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 48:
//#line 117 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 49:
//#line 118 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 50:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 51:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 53:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 54:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 55:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 56:
//#line 128 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 60:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 64:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 65:
//#line 152 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 66:
//#line 153 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 67:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 71:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 72:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 73:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 87:
//#line 189 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 88:
//#line 190 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 89:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 90:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 91:
//#line 193 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 92:
//#line 197 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 93:
//#line 198 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 96:
//#line 207 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 97:
//#line 208 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 98:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 99:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 100:
//#line 211 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 106:
//#line 229 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 107:
//#line 230 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 108:
//#line 231 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 109:
//#line 235 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 110:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 111:
//#line 237 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 112:
//#line 238 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 115:
//#line 247 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 116:
//#line 248 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 117:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 118:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 119:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 120:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 121:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 122:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 123:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 124:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 125:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 126:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 127:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 128:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 129:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 130:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 133:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 134:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 139:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 140:
//#line 280 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 161:
//#line 325 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 162:
//#line 326 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 163:
//#line 327 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 164:
//#line 328 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 165:
//#line 329 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 166:
//#line 330 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 167:
//#line 331 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 168:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 169:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 171:
//#line 338 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1298 "Parser.java"
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
