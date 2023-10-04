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



package compilador;



//#line 2 "./src/compilador/gramatica.y"
import java.io.File;
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
public final static short ELSE=261;
public final static short ENDIF=262;
public final static short PRINT=263;
public final static short VOID=264;
public final static short RETURN=265;
public final static short COMP_MAYOR_IGUAL=266;
public final static short COMP_MENOR_IGUAL=267;
public final static short COMP_IGUAL=268;
public final static short COMP_DISTINTO=269;
public final static short CLASS=270;
public final static short WHILE=271;
public final static short DO=272;
public final static short INTERFACE=273;
public final static short IMPLEMENT=274;
public final static short INT=275;
public final static short ULONG=276;
public final static short FLOAT=277;
public final static short OPERADOR_MENOS=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    1,    2,    2,    4,
    4,    4,    4,    4,    4,   11,   11,   12,   12,   13,
   13,   13,   13,   13,   10,   10,    9,    9,    9,    9,
    9,    9,    9,    9,    9,    9,    9,   15,   19,   15,
   15,   15,   15,   15,   15,   15,   15,   15,    8,    8,
    8,    8,    8,    8,    8,    8,    8,   14,   14,   14,
   20,   14,   14,   14,   14,   14,   16,   16,   16,   18,
   18,   18,   18,   18,   18,   18,   23,   23,   24,   24,
   21,   21,   22,   22,    7,    7,    7,    7,    6,    6,
    6,    6,    6,    6,   27,   27,    5,    5,    5,   25,
   25,    3,    3,    3,    3,   28,   28,   28,   31,   31,
   31,   31,   31,   31,   34,   34,   36,   36,   36,   36,
   30,   30,   30,   30,   30,   30,   30,   30,   37,   37,
   29,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   38,   38,   38,   38,   38,   38,   41,   41,   42,   42,
   42,   40,   40,   40,   39,   33,   33,   32,   32,   32,
   32,   17,   17,   17,   43,   43,   43,   43,   43,   43,
   26,   26,   26,   44,   44,   44,   45,   45,   45,   46,
   46,
};
final static short yylen[] = {                            2,
    3,    2,    2,    2,    0,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    7,    6,    6,    6,
    6,    3,    4,    2,    5,    4,    3,    7,    0,   13,
    6,    6,    3,    4,    2,    5,    4,    3,    9,    7,
    8,    6,    8,    6,    8,    8,    6,    9,    7,    8,
    0,   15,    6,    8,    8,    6,    1,    3,    1,    1,
    1,    1,    3,    4,    5,    2,    1,    2,    1,    1,
    1,    2,    1,    2,    3,    2,    2,    3,    5,    4,
    7,    4,    3,    6,    1,    3,    4,    3,    3,    1,
    3,    1,    1,    1,    1,    3,    2,    2,    5,    4,
    3,    2,    4,    3,    2,    3,    3,    2,    1,    2,
    5,    7,    4,    3,    6,    5,    4,    6,    1,    2,
    2,    5,    4,    7,    6,    4,    3,    4,    3,    2,
    4,    3,    3,    2,    5,    4,    1,    2,    1,    1,
    1,    1,    3,    2,    2,    1,    3,    1,    1,    1,
    1,    3,    2,    2,    1,    1,    1,    1,    1,    1,
    3,    3,    1,    3,    3,    1,    1,    2,    1,    1,
    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  158,  159,
  160,    0,    0,    0,    6,    8,    9,   10,   11,   12,
   13,   14,   15,    0,  102,  103,  104,  105,    0,    0,
    0,    0,    0,   87,    0,    0,   25,    0,    0,    0,
    0,    0,   34,    0,   69,   67,    0,    0,    0,  112,
    0,    0,    4,    0,    2,    7,    0,    0,    0,  156,
  108,    0,    0,  131,    0,  180,  165,  166,  167,  168,
    0,    0,  169,  170,    0,    0,    0,    0,  176,  179,
   88,   85,  161,    0,  139,    0,    0,  137,    0,    0,
    0,    0,  119,    0,  129,    0,  124,    0,    0,   32,
    0,  100,   81,    0,    0,    0,    0,    0,    0,  114,
    0,    0,  111,    0,  115,    1,    0,    0,   99,    0,
  101,  106,    0,    0,    0,  144,   18,   20,   21,   22,
    0,    0,   16,   19,   23,   24,  178,  181,    0,    0,
    0,    0,    0,    0,    0,    0,  133,    0,  155,  138,
  136,  120,    0,    0,    0,    0,  127,  130,    0,  123,
    0,   68,   82,   36,    0,   33,    0,    0,    0,  113,
  110,  116,   90,    0,    0,   97,  157,    0,    0,    0,
   45,    0,   72,   71,   70,    0,  142,  149,  150,  151,
    0,  147,  143,    0,   17,    0,    0,    0,    0,    0,
    0,    0,    0,  174,  175,    0,  132,  152,    0,    0,
    0,    0,  121,  117,  126,    0,   35,    0,    0,    0,
  109,    0,    0,   89,    0,    0,   43,   76,   83,    0,
    0,    0,    0,    0,  146,  148,  141,    0,    0,   54,
    0,   57,    0,    0,    0,    0,  135,  154,    0,  128,
  125,   29,   30,    0,   31,    0,    0,    0,    0,   73,
    0,   84,   47,    0,   44,    0,    0,  145,    0,    0,
    0,    0,   50,  134,  153,  122,   27,    0,   91,    0,
    0,    0,    0,   74,   79,   80,    0,   77,   46,    0,
    0,    0,   53,   55,   56,    0,   63,    0,   66,    0,
    0,   75,   78,   41,    0,   42,   49,    0,    0,    0,
   59,    0,   38,    0,   64,   65,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   40,
    0,   62,
};
final static short yydgoto[] = {                         13,
   14,   15,  183,   46,  128,  129,  130,   21,   22,   23,
  132,  133,  185,  135,  136,   47,   75,  186,  314,  312,
  104,  230,  287,  288,   24,   76,  223,   25,   26,   27,
   28,   29,   62,   51,   30,   95,   96,   64,  208,  209,
  191,  192,   77,   78,   79,   80,
};
final static short yysindex[] = {                       904,
    0,   13,  -27,  -38,   32, -118,  622,  -69,    0,    0,
    0,  984,    0, 1007,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,    0,    0,    0,    0,  -11,  -68,
  721,   37,   61,    0,  -28,  213,    0,  -94,  -96,  228,
   87, -162,    0,  734,    0,    0,  -30, -107, -165,    0,
   35,   64,    0, 1030,    0,    0,   -5,   97, -142,    0,
    0,   23, 1053,    0, -154,    0,    0,    0,    0,    0,
 -113,  932,    0,    0,  106,  716,  -41,   30,    0,    0,
    0,    0,    0,  234,    0, -102,  135,    0,  138,  143,
  -95,  228,    0,  -64,    0,  -91,    0,  228,  476,    0,
  658,    0,    0, -114,  147,  162,  100,  658, -165,    0,
   39,   40,    0, -165,    0,    0,  161,  356,    0,  170,
    0,    0,   47,  191,  698,    0,    0,    0,    0,    0,
 1074, 1099,    0,    0,    0,    0,    0,    0, -224,  596,
  -41,  -41,  -41,  -36,  -41,  -41,    0,  -20,    0,    0,
    0,    0,  -65,  228,  665,   63,    0,    0,  782,    0,
  258,    0,    0,    0,  264,    0,  739,  290,   42,    0,
    0,    0,    0,  -41,  288,    0,    0,  752,  326,  331,
    0,  768,    0,    0,    0,  -26,    0,    0,    0,    0,
 1120,    0,    0, 1141,    0,  932,  330,  932,  334,  -61,
   30,   30,  -36,    0,    0,  255,    0,    0,  128,  228,
  851,  936,    0,    0,    0,  343,    0,  349,  310,  354,
    0,  320,  365,    0,  963,  366,    0,    0,    0,  261,
  369,  368,  131,  658,    0,    0,    0, 1163,   43,    0,
  148,    0,  841,  378,  240,  255,    0,    0,  998,    0,
    0,    0,    0,  379,    0,  -41,  389,  172,  820,    0,
  545,    0,    0,  393,    0,  775,  403,    0,  401,  402,
  413,  185,    0,    0,    0,    0,    0,  -36,    0,  414,
  963,  417,  -54,    0,    0,    0,  806,    0,    0,  424,
  430,  438,    0,    0,    0,  440,    0,  224,    0,  881,
  450,    0,    0,    0,  452,    0,    0,  454,  455,  238,
    0,  241,    0,  235,    0,    0,  462,  468,  243,    0,
  479,  482,  963,  658,  267,  484,  963,  485,  272,    0,
  492,    0,
};
final static short yyrindex[] = {                       541,
  -14,    0,    0,    0,   96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  119,    0,  -21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  542,    0,    0,    0,    0,    0,    0,
    0,  154,    0,    0,    1,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  292,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  178,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  202,    0,    0,  283,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  510,  516,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 1078,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  307,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   73,  521,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  522,    0,    0,    0,    0,    0,    0,    0,    0,
  -37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  362,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  388,    0,    0,  412,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  524,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  436,    0,    0,    0,    0,
  306,    0,    0,    0,  280,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  335,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  561,   21,  710,  481,    8,  714,  723,    0,    0,  511,
    0,  443,  289,    0,    0,  -66,  -44, 1107,    0,    0,
    0,    0,    0,  294,    0,   -6,    0,    0,  840,    0,
    0,  976,  493,   80,   11,  595,   -1,    0,  293,  380,
  395, -160,  514,   55,  114,    0,
};
final static int YYTABLESIZE=1440;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        106,
  177,   36,   48,   71,   40,  139,  141,   18,  142,  108,
  162,   84,   85,  234,   18,  109,   34,  110,   52,   18,
  207,   18,  140,  206,  173,  100,   98,  154,   92,  161,
  236,  100,   61,  157,   56,  117,  196,  197,   99,   71,
  177,  177,  177,  177,  177,  177,  100,  177,  171,   18,
  118,  120,   31,   49,   63,   50,  161,  210,   52,   52,
  177,   18,  177,  168,  173,  173,  122,  173,  173,  173,
  144,  145,  172,  200,   56,   37,  146,  236,  114,   18,
   81,  123,  114,  114,  173,  114,  173,   48,  171,  171,
  155,  171,  171,  171,  102,   26,  159,    2,    4,   57,
    3,  140,    5,  140,   82,   59,  214,  115,  171,    7,
  171,   18,  172,  172,  121,  172,  172,  172,   86,   52,
   58,  123,  219,  137,  172,  177,  101,  111,  112,  239,
  100,  241,  172,  226,  172,   26,  203,  232,   38,  167,
  119,   71,  102,  166,  138,    2,  140,   18,    3,  173,
    5,  211,  212,  107,  149,   39,    4,    7,   86,  113,
   97,  153,   90,  170,  171,   90,  221,  222,  247,    4,
  266,  246,    4,  171,  265,  150,  272,   37,  151,   91,
    9,   10,   11,    9,   10,   11,  152,   48,  169,  267,
  164,   90,   60,  107,    4,  201,  202,  172,    4,  243,
  244,   93,  165,   18,  173,   18,  300,  301,  249,    9,
   10,   11,  141,  176,  142,   65,   66,   37,   35,   48,
   26,  291,   48,   48,   48,   48,   48,   48,   83,   32,
  178,   33,   48,   48,   48,   48,   83,   48,   48,   48,
  107,   93,  161,   86,  233,   60,    9,   10,   11,  278,
   18,   65,   66,   88,    9,   10,   11,  177,  204,  205,
  177,  177,  177,  177,  177,  177,  177,  177,  177,  177,
  177,  177,  177,  177,  147,  177,  177,  177,  107,  326,
  274,  173,   98,  246,  173,  173,  173,  173,  173,  173,
  173,  173,  173,  173,  173,  173,  173,  173,  216,  173,
  173,  173,   37,  177,  269,  171,   92,  217,  171,  171,
  171,  171,  171,  171,  171,  171,  171,  171,  171,  171,
  171,  171,   98,  171,  171,  171,   93,   87,   89,  172,
  220,  224,  172,  172,  172,  172,  172,  172,  172,  172,
  172,  172,  172,  172,  172,  172,   92,  172,  172,  172,
  254,  134,   26,   65,   66,   26,   26,   26,   26,   26,
   26,   52,  141,  256,  142,   26,   26,   26,   26,  227,
   26,   26,   26,  240,   60,   86,  148,  242,   86,   86,
   86,   86,   86,   86,   83,  260,  252,   28,   86,   86,
   86,   86,  253,   86,   86,   86,  175,  255,  141,  174,
  142,   52,    9,   10,   11,  257,  259,   98,  264,  270,
  107,   94,  263,  107,  107,  107,  107,  107,  107,  190,
  134,  273,  277,  107,  107,  107,  107,   28,  107,  107,
  107,   92,  279,  280,   37,   51,  289,   37,   37,   37,
   37,   37,   37,  292,  293,  294,  296,   37,   37,   37,
   37,   94,   37,   37,   37,  228,  295,  297,   93,   60,
  299,   93,   93,   93,   93,   93,   93,  304,  229,   83,
  305,   93,   93,   93,   93,   51,   93,   93,   93,  190,
   17,  306,  190,  307,   90,  308,   52,    9,   10,   11,
   83,    4,   17,  311,   17,  313,   83,  315,  316,  317,
  318,  248,    9,   10,   11,  320,  319,  321,    9,   10,
   11,   83,   28,  322,    9,   10,   11,  102,  262,  323,
  124,  324,  103,    3,  328,    5,  190,  327,  330,    9,
   10,   11,  125,  331,   17,  332,   94,  248,  275,   98,
    5,    3,   98,   98,   98,   98,   98,   98,  161,  286,
  163,   39,   98,   98,   98,   98,  164,   98,   98,   98,
   51,  162,   95,   92,   96,   61,   92,   92,   92,   92,
   92,   92,   54,  131,  195,  286,   92,   92,   92,   92,
  303,   92,   92,   92,  163,  245,  156,  102,  238,  143,
  124,   60,    0,    3,   60,   60,   60,   60,   60,   60,
  160,    0,  125,    0,   60,   60,   60,   60,    0,   60,
   60,   60,    0,    0,    0,    0,    0,    0,   52,    0,
    0,   52,   52,   52,   52,   52,   52,    0,    0,    0,
    0,   52,   52,   52,   52,  184,   52,   52,   52,    0,
    0,  189,  194,    0,   28,    0,    0,   28,   28,   28,
   28,   28,   28,    0,    0,    0,    0,   28,   28,   28,
   28,   44,   28,   28,   28,   43,    0,    0,   94,  284,
    0,   94,   94,   94,   94,   94,   94,    0,    0,    0,
    0,   94,   94,   94,   94,    0,   94,   94,   94,    0,
  158,    0,   51,  158,    0,   51,   51,   51,   51,   51,
   51,  189,   71,    0,  189,   51,   51,   51,   51,   16,
   51,   51,   51,   19,    0,    0,   45,   74,   42,   73,
   19,   16,   20,   16,    0,   19,    0,   19,    0,   20,
    0,    0,   90,    0,   20,  184,   20,  182,    0,    4,
  261,  181,    0,    0,   42,    0,    0,    0,  189,  158,
    9,   10,   11,  158,    0,   19,    0,    0,  141,    0,
  142,   72,    0,   16,   20,   71,    0,   19,    0,  184,
    0,  285,  127,    0,  105,   74,   20,   73,   71,  218,
   74,   45,   73,   71,    0,   19,    0,    0,    0,  213,
    0,  184,  225,   74,   20,   73,   71,  285,   74,    0,
   73,  102,    0,    0,  124,  158,  158,    3,  231,    5,
  184,   74,   71,   73,    0,  290,  125,   19,    0,   71,
  180,    0,    0,    0,    0,    0,   20,   74,    0,   73,
    0,    0,    0,  184,   74,    0,   73,  184,    0,    0,
  188,  127,    0,  158,    0,    0,    0,    0,    0,   45,
    0,    0,    1,   19,    0,    2,  198,  199,    3,    4,
    5,    0,   20,    0,    0,    6,    0,    7,    8,    0,
    9,   10,   11,    0,    0,    0,    0,   93,    1,   93,
    0,    2,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,   41,    7,    8,    0,    9,   10,   11,    0,
  188,    0,    0,  188,    0,   45,  215,   45,    0,   19,
    0,   19,    0,    0,   65,   66,    0,    0,   20,    0,
   20,   90,    0,   67,   68,   69,   70,    0,    4,    0,
  302,   93,    0,    0,    0,   93,    0,   93,   93,    9,
   10,   11,  180,    0,    0,    0,    0,  188,    0,    0,
    0,    0,   45,    0,    1,    0,   19,  124,    0,    0,
    3,    4,    5,   42,    0,   20,    0,    6,  179,  125,
    8,    0,    9,   10,   11,  250,    0,   65,   66,    0,
    0,   67,   68,   69,   70,    0,   67,   68,   69,   70,
   65,   66,   93,   93,   93,   65,   66,    0,   93,   67,
   68,   69,   70,  180,   67,   68,   69,   70,   65,   66,
   86,   86,    0,   94,    0,   94,    0,   67,   68,   69,
   70,    0,    0,    0,   65,   66,   12,    0,    0,    0,
    0,   65,   66,   67,   68,   69,   70,    0,   90,    0,
   67,   68,   69,   70,    0,    4,    0,    0,    0,   93,
   93,   93,    0,    0,   42,    0,    9,   10,   11,   86,
  251,    0,  102,    0,    0,  124,    0,   94,    3,    0,
    5,   94,    0,   94,   94,    0,    1,  125,    0,  124,
  281,  282,    3,    4,    5,  180,    0,    0,   93,    6,
    0,  125,    8,    0,    9,   10,   11,    1,    0,    0,
    2,    0,  271,    3,    4,    5,    0,   90,   53,    0,
    6,    0,    7,    8,    4,    9,   10,   11,    0,    0,
    0,    0,  276,   86,    0,    9,   10,   11,   94,   94,
   94,   55,    0,    0,   94,    0,    0,    1,    0,    0,
  124,    0,  309,    3,    4,    5,    0,    0,    0,    0,
    6,    0,  125,    8,  116,    9,   10,   11,    0,    0,
    1,    0,    0,    2,    0,    0,    3,    4,    5,    0,
    0,    0,    0,    6,    0,    7,    8,  126,    9,   10,
   11,   86,    0,    0,   86,   94,   94,   94,    1,    0,
    0,    2,   90,    0,    3,    4,    5,    0,  187,    4,
    0,    6,  118,    7,    8,    0,    9,   10,   11,    0,
    9,   10,   11,    0,    0,    0,    0,    0,    0,    1,
   86,   86,  124,  193,   94,    3,    4,    5,    0,    0,
    0,    0,    6,    0,  125,    8,    0,    9,   10,   11,
    1,    0,    0,    2,  235,    0,    3,    4,    5,    0,
    0,    0,    0,    6,   90,    7,    8,    0,    9,   10,
   11,    4,    0,    1,    0,  237,    2,    0,    0,    3,
    4,    5,    9,   10,   11,    0,    6,    0,    7,    8,
    0,    9,   10,   11,    0,    0,    1,  268,    0,    2,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    6,
    0,    7,    8,    0,    9,   10,   11,    0,    0,    1,
    0,    0,  124,    0,    0,    3,    4,    5,    0,    0,
    0,    0,    6,    0,  125,    8,    0,    9,   10,   11,
    1,  258,    0,  124,  118,    0,    3,    4,    5,    0,
    0,  118,    0,    6,    0,  125,    8,    0,    9,   10,
   11,    0,  118,  118,  118,    1,    0,    0,  124,    0,
    0,    3,    4,    5,    0,  283,    0,    0,    6,    0,
  125,    8,    0,    9,   10,   11,    1,    0,    0,  124,
    0,    0,    3,    4,    5,    0,    0,  298,    0,    6,
    0,  125,    8,    0,    9,   10,   11,    1,    0,    0,
  124,    0,    0,    3,    4,    5,  310,    0,    0,    0,
    6,    0,  125,    8,    0,    9,   10,   11,    0,    1,
    0,    0,  124,    0,    0,    3,    4,    5,    0,  325,
    0,    0,    6,  329,  125,    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   40,   40,   45,  123,   72,   43,    0,   45,   40,
  125,   40,   41,   40,    7,  123,   44,  125,    8,   12,
   41,   14,   44,   44,    0,   40,  123,  123,  123,   44,
  191,   46,   44,  125,   14,   41,  261,  262,   40,   45,
   40,   41,   42,   43,   44,   45,   61,   47,    0,   42,
   57,   58,   40,  123,  123,  125,  101,  123,   48,   49,
   60,   54,   62,  108,   40,   41,   44,   43,   44,   45,
   77,   42,    0,  140,   54,   44,   47,  238,   44,   72,
   44,   59,   44,   44,   60,   44,   62,  125,   40,   41,
   92,   43,   44,   45,  257,    0,   98,  260,  264,   40,
  263,  123,  265,  125,   44,   46,   44,   44,   60,  272,
   62,  104,   40,   41,  257,   43,   44,   45,    0,  109,
   61,   59,  167,  278,  114,  125,   40,   48,   49,  196,
   44,  198,   60,  178,   62,   40,  143,  182,  257,   40,
   44,   45,  257,   44,  258,  260,   41,  140,  263,  125,
  265,  153,  154,    0,  257,  274,  264,  272,   40,  125,
  257,  257,  257,  125,  125,  257,  125,  174,   41,  264,
   40,   44,  264,  125,   44,   41,  243,    0,   41,  274,
  275,  276,  277,  275,  276,  277,   44,  257,  109,  234,
   44,  257,  257,   40,  264,  141,  142,  125,  264,  261,
  262,    0,   41,  196,   44,  198,  261,  262,  210,  275,
  276,  277,   43,   44,   45,  257,  258,   40,  257,  257,
  125,  266,  260,  261,  262,  263,  264,  265,  257,  257,
   40,  259,  270,  271,  272,  273,  257,  275,  276,  277,
  271,   40,  257,  125,  271,  257,  275,  276,  277,  256,
  243,  257,  258,   41,  275,  276,  277,  257,  145,  146,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,   41,  275,  276,  277,  125,  324,
   41,  257,    0,   44,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  273,   41,  275,
  276,  277,  125,  257,  262,  257,    0,   44,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,   40,  275,  276,  277,  125,   35,   36,  257,
   41,   44,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   40,  275,  276,  277,
   41,   63,  257,  257,  258,  260,  261,  262,  263,  264,
  265,    0,   43,   44,   45,  270,  271,  272,  273,   44,
  275,  276,  277,   44,   40,  257,   84,   44,  260,  261,
  262,  263,  264,  265,  257,  125,   44,    0,  270,  271,
  272,  273,   44,  275,  276,  277,   41,   44,   43,   44,
   45,   40,  275,  276,  277,   41,   41,  125,   41,  262,
  257,    0,   44,  260,  261,  262,  263,  264,  265,  131,
  132,   44,   44,  270,  271,  272,  273,   40,  275,  276,
  277,  125,   44,  262,  257,    0,   44,  260,  261,  262,
  263,  264,  265,   41,   44,   44,  262,  270,  271,  272,
  273,   40,  275,  276,  277,  125,   44,   44,  257,  125,
   44,  260,  261,  262,  263,  264,  265,   44,  180,  257,
   41,  270,  271,  272,  273,   40,  275,  276,  277,  191,
    0,   44,  194,   44,  257,  262,  125,  275,  276,  277,
  257,  264,   12,   44,   14,   44,  257,   44,   44,  262,
  260,  209,  275,  276,  277,   44,  272,   40,  275,  276,
  277,  257,  125,  271,  275,  276,  277,  257,  230,   41,
  260,   40,   42,  263,   41,  265,  238,  261,   44,  275,
  276,  277,  272,  262,   54,   44,  125,  245,  246,  257,
    0,    0,  260,  261,  262,  263,  264,  265,  257,  261,
   41,  272,  270,  271,  272,  273,   41,  275,  276,  277,
  125,   41,   41,  257,   41,  260,  260,  261,  262,  263,
  264,  265,   12,   63,  132,  287,  270,  271,  272,  273,
  287,  275,  276,  277,  104,  206,   94,  257,  194,   76,
  260,  257,   -1,  263,  260,  261,  262,  263,  264,  265,
  125,   -1,  272,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,  125,  275,  276,  277,   -1,
   -1,  131,  132,   -1,  257,   -1,   -1,  260,  261,  262,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
  273,   40,  275,  276,  277,   44,   -1,   -1,  257,  125,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   96,   -1,  257,   99,   -1,  260,  261,  262,  263,  264,
  265,  191,   45,   -1,  194,  270,  271,  272,  273,    0,
  275,  276,  277,    0,   -1,   -1,    7,   60,  123,   62,
    7,   12,    0,   14,   -1,   12,   -1,   14,   -1,    7,
   -1,   -1,  257,   -1,   12,  225,   14,   40,   -1,  264,
  230,   44,   -1,   -1,  123,   -1,   -1,   -1,  238,  155,
  275,  276,  277,  159,   -1,   42,   -1,   -1,   43,   -1,
   45,   41,   -1,   54,   42,   45,   -1,   54,   -1,  259,
   -1,  261,   63,   -1,   41,   60,   54,   62,   45,   41,
   60,   72,   62,   45,   -1,   72,   -1,   -1,   -1,  125,
   -1,  281,   41,   60,   72,   62,   45,  287,   60,   -1,
   62,  257,   -1,   -1,  260,  211,  212,  263,   41,  265,
  300,   60,   45,   62,   -1,   41,  272,  104,   -1,   45,
  123,   -1,   -1,   -1,   -1,   -1,  104,   60,   -1,   62,
   -1,   -1,   -1,  323,   60,   -1,   62,  327,   -1,   -1,
  131,  132,   -1,  249,   -1,   -1,   -1,   -1,   -1,  140,
   -1,   -1,  257,  140,   -1,  260,  261,  262,  263,  264,
  265,   -1,  140,   -1,   -1,  270,   -1,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,   -1,   38,  257,   40,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
  191,   -1,   -1,  194,   -1,  196,  125,  198,   -1,  196,
   -1,  198,   -1,   -1,  257,  258,   -1,   -1,  196,   -1,
  198,  257,   -1,  266,  267,  268,  269,   -1,  264,   -1,
  125,   92,   -1,   -1,   -1,   96,   -1,   98,   99,  275,
  276,  277,  123,   -1,   -1,   -1,   -1,  238,   -1,   -1,
   -1,   -1,  243,   -1,  257,   -1,  243,  260,   -1,   -1,
  263,  264,  265,  123,   -1,  243,   -1,  270,  271,  272,
  273,   -1,  275,  276,  277,  125,   -1,  257,  258,   -1,
   -1,  266,  267,  268,  269,   -1,  266,  267,  268,  269,
  257,  258,  153,  154,  155,  257,  258,   -1,  159,  266,
  267,  268,  269,  123,  266,  267,  268,  269,  257,  258,
   35,   36,   -1,   38,   -1,   40,   -1,  266,  267,  268,
  269,   -1,   -1,   -1,  257,  258,  123,   -1,   -1,   -1,
   -1,  257,  258,  266,  267,  268,  269,   -1,  257,   -1,
  266,  267,  268,  269,   -1,  264,   -1,   -1,   -1,  210,
  211,  212,   -1,   -1,  123,   -1,  275,  276,  277,   84,
  125,   -1,  257,   -1,   -1,  260,   -1,   92,  263,   -1,
  265,   96,   -1,   98,   99,   -1,  257,  272,   -1,  260,
  261,  262,  263,  264,  265,  123,   -1,   -1,  249,  270,
   -1,  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,
  260,   -1,  262,  263,  264,  265,   -1,  257,  125,   -1,
  270,   -1,  272,  273,  264,  275,  276,  277,   -1,   -1,
   -1,   -1,  125,  148,   -1,  275,  276,  277,  153,  154,
  155,  125,   -1,   -1,  159,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,  125,  275,  276,  277,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,  125,  275,  276,
  277,  206,   -1,   -1,  209,  210,  211,  212,  257,   -1,
   -1,  260,  257,   -1,  263,  264,  265,   -1,  125,  264,
   -1,  270,  125,  272,  273,   -1,  275,  276,  277,   -1,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  245,  246,  260,  125,  249,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
  257,   -1,   -1,  260,  125,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,  257,  272,  273,   -1,  275,  276,
  277,  264,   -1,  257,   -1,  125,  260,   -1,   -1,  263,
  264,  265,  275,  276,  277,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,   -1,   -1,  257,  125,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,   -1,   -1,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
  257,  225,   -1,  260,  257,   -1,  263,  264,  265,   -1,
   -1,  264,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,   -1,  275,  276,  277,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,  259,   -1,   -1,  270,   -1,
  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,  281,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,  300,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,  323,
   -1,   -1,  270,  327,  272,  273,   -1,  275,  276,  277,
};
}
final static short YYFINAL=13;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
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
null,null,null,null,null,null,null,"ID","CTE","CADENA","IF","ELSE","ENDIF",
"PRINT","VOID","RETURN","COMP_MAYOR_IGUAL","COMP_MENOR_IGUAL","COMP_IGUAL",
"COMP_DISTINTO","CLASS","WHILE","DO","INTERFACE","IMPLEMENT","INT","ULONG",
"FLOAT","OPERADOR_MENOS",
};
final static String yyrule[] = {
"$accept : programa",
"programa : '{' sentencias '}'",
"programa : sentencias '}'",
"programa : '{' sentencias",
"programa : '{' '}'",
"programa :",
"sentencias : sentencia",
"sentencias : sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : sentencia_asignacion",
"sentencia_ejecutable : sentencia_invocacion_funcion",
"sentencia_ejecutable : sentencia_imprimir",
"sentencia_ejecutable : sentencia_seleccion",
"sentencia_ejecutable : sentencia_iterativa_do_while",
"sentencia_ejecutable : sentencia_return",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : sentencia_asignacion",
"sentencia_ejecutable_funcion : sentencia_invocacion_funcion",
"sentencia_ejecutable_funcion : sentencia_imprimir",
"sentencia_ejecutable_funcion : sentencia_seleccion_funcion",
"sentencia_ejecutable_funcion : sentencia_iterativa_do_while_funcion",
"sentencia_return : RETURN ','",
"sentencia_return : RETURN",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE '(' condicion ')' ','",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE '(' condicion ')'",
"sentencia_iterativa_do_while : DO WHILE '(' condicion ')' ','",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE '(' ')' ','",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables '(' condicion ')' ','",
"sentencia_iterativa_do_while : DO WHILE ','",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE ','",
"sentencia_iterativa_do_while : DO ','",
"sentencia_iterativa_do_while : DO '(' condicion ')' ','",
"sentencia_iterativa_do_while : DO '(' ')' ','",
"sentencia_iterativa_do_while : DO '(' ')'",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' ','",
"$$1 :",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' $$1 DO WHILE '(' condicion ')' ','",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE '(' ')' ','",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion '(' condicion ')' ','",
"sentencia_iterativa_do_while_funcion : DO WHILE ','",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE ','",
"sentencia_iterativa_do_while_funcion : DO ','",
"sentencia_iterativa_do_while_funcion : DO '(' condicion ')' ','",
"sentencia_iterativa_do_while_funcion : DO '(' ')' ','",
"sentencia_iterativa_do_while_funcion : DO '(' ')'",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF",
"sentencia_seleccion : IF '(' ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' ')' bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF",
"$$2 :",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF $$2 IF '(' ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' ENDIF ','",
"bloque_sentencias_ejecutables : sentencia_ejecutable",
"bloque_sentencias_ejecutables : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables : sentencia_declarativa",
"bloque_sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"bloque_sentencias_ejecutables_funcion : sentencia_return",
"bloque_sentencias_ejecutables_funcion : sentencia_declarativa",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}'",
"bloque_sentencias_ejecutables_funcion : '{' '}'",
"sentencias_ejecutables_funcion_inalcanzable : sentencia_ejecutable_funcion_inalcanzable",
"sentencias_ejecutables_funcion_inalcanzable : sentencias_ejecutables_funcion_inalcanzable sentencia_ejecutable_funcion_inalcanzable",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_return",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_ejecutable_funcion",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"sentencia_imprimir : PRINT CADENA ','",
"sentencia_imprimir : PRINT CADENA",
"sentencia_imprimir : PRINT ','",
"sentencia_imprimir : PRINT ID ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ')'",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' ')'",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')'",
"lista_expresiones_invocacion_funcion_exceso : expresion",
"lista_expresiones_invocacion_funcion_exceso : expresion ',' expresion",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion ','",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion",
"sentencia_asignacion : sentencia_objeto_identificador '=' ','",
"sentencia_objeto_identificador : ID",
"sentencia_objeto_identificador : sentencia_objeto_identificador '.' ID",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_clase",
"sentencia_declarativa : declaracion_interfaz",
"declaracion_variable : tipo lista_de_variables ','",
"declaracion_variable : tipo lista_de_variables",
"declaracion_variable : tipo ','",
"declaracion_interfaz : INTERFACE ID '{' bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE '{' bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE '}'",
"declaracion_interfaz : INTERFACE ID bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE ID '}'",
"bloque_encabezado_funcion : encabezado_funcion ','",
"bloque_encabezado_funcion : bloque_encabezado_funcion ',' encabezado_funcion",
"sentencia_declarativa_clase : tipo lista_de_variables ','",
"sentencia_declarativa_clase : tipo lista_de_variables",
"sentencia_declarativa_clase : declaracion_funcion",
"sentencia_declarativa_clase : ID ','",
"declaracion_clase : CLASS ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT ID",
"declaracion_clase : CLASS ID IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID bloque_sentencias_declarativas_clase '}'",
"bloque_sentencias_declarativas_clase : sentencia_declarativa_clase",
"bloque_sentencias_declarativas_clase : bloque_sentencias_declarativas_clase sentencia_declarativa_clase",
"declaracion_funcion : encabezado_funcion cuerpo_funcion",
"encabezado_funcion : VOID ID '(' parametro_funcion ')'",
"encabezado_funcion : VOID ID '(' ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')'",
"encabezado_funcion : VOID '(' parametro_funcion ')'",
"encabezado_funcion : VOID '(' ')'",
"encabezado_funcion : VOID ID parametro_funcion ')'",
"encabezado_funcion : VOID ID ')'",
"encabezado_funcion : VOID ID",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return '}'",
"cuerpo_funcion : '{' sentencia_return '}'",
"cuerpo_funcion : '{' sentencias_funcion '}'",
"cuerpo_funcion : '{' '}'",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}'",
"cuerpo_funcion : '{' sentencia_return sentencias_funcion_inalcanzable '}'",
"sentencias_funcion_inalcanzable : sentencia_funcion_inalcanzable",
"sentencias_funcion_inalcanzable : sentencias_funcion_inalcanzable sentencia_funcion_inalcanzable",
"sentencia_funcion_inalcanzable : sentencia_declarativa",
"sentencia_funcion_inalcanzable : sentencia_return",
"sentencia_funcion_inalcanzable : sentencia_ejecutable_funcion",
"lista_parametros_funcion_exceso : parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso ',' parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso parametro_funcion",
"parametro_funcion : tipo ID",
"lista_de_variables : ID",
"lista_de_variables : lista_de_variables ';' ID",
"tipo : INT",
"tipo : ULONG",
"tipo : FLOAT",
"tipo : ID",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"condicion : comparador expresion",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_IGUAL",
"comparador : COMP_DISTINTO",
"comparador : '>'",
"comparador : '<'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : ID OPERADOR_MENOS",
"factor : constante",
"constante : CTE",
"constante : '-' CTE",
};

//#line 333 "./src/compilador/gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;
public static int MIN_INT_VALUE = -(int) (Math.pow(2, 15));
public static int MAX_INT_VALUE = (int) (Math.pow(2, 15) - 1);

/** Chequea, para los INT, que el valor positivo no supere el valor maximo */
public void corregirConstantePositivaEntera(String constante) {
	if (constante.contains("_i")) {
		//se recibio un INT con signo positivo
		boolean exceptionOutOfRange = false;
		int cte = 0;
		String constanteValue = constante.toString().split("_")[0];

		try {
			cte = Integer.parseInt(constanteValue);
		} catch (NumberFormatException e) {
			exceptionOutOfRange = true;
		}

		if (cte > MAX_INT_VALUE || exceptionOutOfRange) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + constante + ", se trunca al valor " + MAX_INT_VALUE + "_i");

			ts.swapLexemas(constante, MAX_INT_VALUE + "_i");
		}
	}
}

public void constanteConSigno(String constante) {
	/** Check de float negativos */
	if (constante.contains(".")) {
		String negConstante = "-"+constante;
		ts.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			ts.swapLexemas(constante, "0_ul");
		} else {
			// se recibio un INT negativo
			String negConstante = "-"+constante;
			boolean exceptionOutOfRange = false;
			int cte = 0;

			String negConstanteValue = negConstante.toString().split("_")[0];

			try {
				cte = Integer.parseInt(negConstanteValue);
			} catch (NumberFormatException e) {
				exceptionOutOfRange = true;
			}

			if (cte < MIN_INT_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");

				ts.swapLexemas(constante, MIN_INT_VALUE + "_i");
			} else {
				ts.swapLexemas(constante, negConstante);
			}
		}
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
		
		boolean fileOpenSuccess = fileHelper.open(archivo_a_leer);
		
		if (fileOpenSuccess) {
			parser = new Parser();
			lexico = new AnalizadorLexico(fileHelper);
			
	        parser.run();
	
			String path = new File(archivo_a_leer).getAbsolutePath();
	        
	        Output out = new Output(path);
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintactico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", ts.print());
		}
	}
}
//#line 888 "Parser.java"
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
//#line 17 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 18 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba simbolo '{' al principio del programa"); }
break;
case 3:
//#line 19 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba simbolo '}' al final del programa"); }
break;
case 4:
//#line 20 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Programa vacio"); }
break;
case 5:
//#line 21 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Programa vacio"); }
break;
case 15:
//#line 40 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
break;
case 26:
//#line 63 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego del RETURN"); }
break;
case 27:
//#line 67 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 28:
//#line 68 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
break;
case 29:
//#line 69 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 30:
//#line 70 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 31:
//#line 71 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); }
break;
case 32:
//#line 72 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 33:
//#line 73 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 34:
//#line 74 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 35:
//#line 75 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 36:
//#line 76 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 37:
//#line 77 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 38:
//#line 81 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 39:
//#line 82 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
break;
case 40:
//#line 83 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 41:
//#line 84 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 42:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); }
break;
case 43:
//#line 86 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 44:
//#line 87 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 45:
//#line 88 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 46:
//#line 89 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 47:
//#line 90 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 48:
//#line 91 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 49:
//#line 95 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 50:
//#line 96 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 51:
//#line 97 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); }
break;
case 52:
//#line 98 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
break;
case 53:
//#line 99 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); }
break;
case 54:
//#line 100 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF"); }
break;
case 55:
//#line 101 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 56:
//#line 102 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 57:
//#line 103 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
break;
case 58:
//#line 107 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 59:
//#line 108 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 60:
//#line 109 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); }
break;
case 61:
//#line 110 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
break;
case 62:
//#line 111 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); }
break;
case 63:
//#line 112 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF"); }
break;
case 64:
//#line 113 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 65:
//#line 114 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 66:
//#line 115 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
break;
case 69:
//#line 121 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 72:
//#line 127 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 76:
//#line 131 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en bloque de sentencias ejecutables"); }
break;
case 77:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 78:
//#line 136 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 85:
//#line 155 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 86:
//#line 156 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en Sentencia PRINT"); }
break;
case 87:
//#line 157 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba CADENA en Sentencia PRINT"); }
break;
case 88:
//#line 158 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 89:
//#line 162 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 90:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 91:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 92:
//#line 165 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 93:
//#line 166 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 94:
//#line 167 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 97:
//#line 176 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 98:
//#line 177 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en sentenecia asignacion"); }
break;
case 99:
//#line 178 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba expresion del lado derecho en sentenecia asignacion"); }
break;
case 106:
//#line 194 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 107:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en sentenecia declaracion de variables"); }
break;
case 108:
//#line 196 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de variables en sentenecia declaracion de variables"); }
break;
case 110:
//#line 201 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de INTERFACE"); }
break;
case 111:
//#line 202 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de INTERFACE"); }
break;
case 112:
//#line 203 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de INTERFACE"); }
break;
case 113:
//#line 204 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de INTERFACE"); }
break;
case 114:
//#line 205 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de INTERFACE"); }
break;
case 117:
//#line 214 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 118:
//#line 215 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en delcaracion de lista de variables en CLASS"); }
break;
case 121:
//#line 221 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 122:
//#line 222 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 123:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 124:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 125:
//#line 225 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en IMPLEMENT de clase"); }
break;
case 126:
//#line 226 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 127:
//#line 227 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de clase"); }
break;
case 128:
//#line 228 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de clase"); }
break;
case 131:
//#line 237 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 134:
//#line 243 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 135:
//#line 244 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 136:
//#line 245 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); }
break;
case 137:
//#line 246 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); }
break;
case 138:
//#line 247 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 139:
//#line 248 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 140:
//#line 249 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 143:
//#line 255 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); }
break;
case 144:
//#line 256 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); }
break;
case 147:
//#line 262 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 148:
//#line 263 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 163:
//#line 296 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion del lado derecho de la comparacion"); }
break;
case 164:
//#line 297 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion del lado izquierdo de la comparacion"); }
break;
case 180:
//#line 328 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 181:
//#line 329 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1433 "Parser.java"
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
