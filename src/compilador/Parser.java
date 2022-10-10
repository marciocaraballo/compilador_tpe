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
import java.io.File;
//#line 20 "Parser.java"




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
    7,    7,   14,   14,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,    8,    8,   19,   19,   19,   19,
   18,   18,   16,   22,   22,   17,   17,   24,   24,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   13,
   13,   13,   13,   13,   27,   27,   27,   30,   30,   29,
   29,   31,   29,    9,    9,    9,   32,   32,   33,   33,
   33,   33,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,   39,   39,   39,   40,   40,   40,   40,   40,
   41,   41,   38,   38,   42,   42,   42,   42,   42,   25,
   43,   43,   26,   26,   34,   34,   34,   37,   37,   44,
   37,   23,   23,   35,   35,   35,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   45,
   45,   45,   45,   21,   21,   20,   20,   47,   20,   46,
   46,   46,   46,   46,   46,   28,   28,   28,   48,   48,
   48,   49,   49,   49,   51,   51,   52,   52,   53,   53,
   36,   36,   36,   36,   36,   36,   36,   36,   36,   50,
   50,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    1,    2,    1,    1,    1,    2,    1,    2,
    1,    2,    1,    2,    4,    5,   10,   13,   13,   16,
    8,   11,    9,    1,    2,    7,    9,    1,    4,    6,
    7,    5,    5,    5,    5,    6,    6,    6,    6,    5,
    4,    3,    3,    4,    1,    3,    5,    1,    3,    2,
    1,    0,    2,    3,    2,    2,    1,    3,    3,    2,
    2,    1,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    1,    1,    2,    4,    1,    3,    3,
    2,    1,    1,    3,    7,    6,    6,    6,    6,    1,
    1,    3,    1,    2,    4,    3,    3,    9,    8,    0,
   17,    1,    2,    8,   10,    7,    7,    7,    7,    7,
    7,    9,    9,    9,    9,    9,    9,    9,    8,    1,
    3,    2,    2,    1,    2,    3,    2,    0,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    3,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  183,  182,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   83,   85,   87,   89,
   91,  103,    0,    0,    0,    0,    0,  180,    0,    0,
    0,    0,    0,    0,  161,  163,  164,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,  111,   94,
   95,    0,   84,   86,   88,   90,   92,    0,   76,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  116,    0,   19,    0,  181,    0,    0,    0,
  150,  151,  152,    0,    0,  153,  154,  155,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   96,    0,  101,
    0,  113,    0,   81,    0,   74,    0,   15,    0,    0,
    0,    0,    0,   25,   26,    0,    0,   23,   27,   29,
   31,    0,    0,  104,    1,  115,  169,  165,  170,    0,
  167,    0,    0,    0,    0,    0,    0,    0,    0,  159,
  160,  176,  174,  177,    0,  175,    0,  173,    0,    0,
    0,    0,   70,    0,    0,   73,    0,    0,    0,    0,
  110,  100,    0,  112,  114,    0,    0,   79,   78,    0,
    0,    0,    0,    0,   48,    0,   28,   30,   32,   34,
   21,    0,   24,    0,   35,    0,    0,  166,    0,    0,
    0,    0,    0,    0,    0,    0,  172,  171,    0,   55,
    0,    0,   54,    0,    0,   53,    0,    0,    0,    0,
   97,    0,    0,    0,    0,    0,    0,   62,    0,    0,
    0,   22,   36,    0,  168,  144,    0,  142,  145,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   50,    0,   59,   58,    0,   57,    0,    0,    0,    0,
  107,  109,    0,  108,    0,   64,    0,    0,    0,    0,
    0,  141,    0,  128,    0,  126,    0,    0,  131,    0,
    0,    0,  129,    0,  127,   51,    0,   67,  123,    0,
    0,    0,  105,    0,   60,    0,   49,    0,    0,    0,
    0,  139,    0,    0,    0,  124,    0,    0,    0,  120,
    0,  119,    0,    0,    0,    0,    0,  134,  132,  136,
  137,    0,  135,  133,   69,    0,  118,    0,    0,    0,
   44,    0,    0,   46,    0,  125,    0,    0,    0,    0,
   41,    0,   45,    0,    0,    0,    0,    0,   43,   47,
    0,    0,    0,    0,   37,    0,    0,    0,   42,    0,
    0,    0,    0,    0,    0,    0,   38,   39,    0,    0,
  121,    0,   40,
};
final static short yydgoto[] = {                          3,
    4,   15,  267,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  195,  137,  138,  139,  140,  141,  142,   41,
  211,  342,  268,  196,   26,  121,  111,   42,  112,  298,
  113,   70,   71,   27,   28,   29,   30,   31,   59,   60,
   61,   32,   62,  336,  215,   99,   43,   44,   45,   46,
   47,  150,  151,
};
final static short yysindex[] = {                       -94,
    0,  906,    0,  425,  -38,   16,  -23,  -20,   10,  555,
    0,    0,  419,  -43,  637,    0,    0,    0,    0,    0,
    0,    0,  -41,   30,  -64,   68,    0,    0,    0,    0,
    0,    0,  660,  683,   42, -139,   99,    0, -130,   52,
  101,    6,   83,  144,    0,    0,    0,  109,  112,   -5,
  -30,  -16,   54, -122,   47,  110,  929,    0,    0,    0,
    0,  -97,    0,    0,    0,    0,    0, -176,    0,  128,
  150,    0,    0,  174,    0,  141,    0,  849,  -69,    0,
  693,    0,    0,  409,    0,   76,    0,  -21,  -36,   20,
    0,    0,    0,   26,   26,    0,    0,    0,   26,   26,
   26,   26,  197,  207,    1,   49,  227,  -18,  236,   34,
  261,  271,   67,  298,  319,  108,  331,    0,  -40,    0,
  796,    0,   32,    0,  186,    0,  237,    0,   35,   38,
  478,  538,  662,    0,    0,  402,  849,    0,    0,    0,
    0,  416,  504,    0,    0,    0,    0,    0,    0,    3,
    0,  627,  627,  355,  627,  144,  144,  170,  170,    0,
    0,    0,    0,    0,  505,    0,  529,    0,  535,  -33,
    5,  -33,    0,  537,  -33,    0,  -33,  539,  473,  -99,
    0,    0,  541,    0,    0,   88,  560,    0,    0,   52,
   91,  316,   54,  929,    0,  333,    0,    0,    0,    0,
    0,  484,    0,  485,    0,  338,   93,    0,  755,    0,
  436,  -14,  105,  572,  133,  156,    0,    0,  -33,    0,
  556,  -33,    0,  -33,  569,    0,  -33,  906,  495,  906,
    0,  561,   66,  562,  -34,  563,  544,    0,  584,  779,
  589,    0,    0,  538,    0,    0,  459,    0,    0,  627,
  565,  627,  575,  583,  576,  201,  627,  577,  627,  578,
    0,  -33,    0,    0,  -33,    0,  906,  516,  906,  521,
    0,    0,  588,    0,  605,    0,  592,  -89,  523,   26,
  378,    0,  391,    0,  395,    0,  601,  399,    0,  607,
  610,  401,    0,  411,    0,    0,  628,    0,    0,  618,
  559,  621,    0,  393,    0,  558,    0,  645,  647,  629,
  635,    0,  640,  646,  444,    0,  651,  652,  -33,    0,
  653,    0,  755,  203,  872,  654,   26,    0,    0,    0,
    0,  656,    0,    0,    0,  446,    0,  826, -102,  665,
    0,  596,  -98,    0,  686,    0,  696,  613,  755,  476,
    0,  681,    0,  684,   26,  239,  946,  685,    0,    0,
  710,  -95,  694,  630,    0,  493,  755,  496,    0,  503,
  638,  946,  708,  714,  906,  649,    0,    0,  657,  513,
    0,  718,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  220,  302,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  114,    0,    0,  302,
    0,    0,    0,  137,    0,    0,    0,    0,    0,    0,
  526,  526,  302,  720,  716,  735,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,    0,  326,
  492,    0,    0,  515,    0,  366,    0,    0,    0,    0,
  784,    0,    0,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -37,    0,
    0,    0,    0,    0,    0,    0,    0,  526,    0,   69,
    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  302,    0,  469,    0,    0,    0,  302,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  896,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  160,  210,  -29,  -26,    0,
    0,    0,    0,    0,  183,    0,  233,    0,    0,    0,
    0,    0,    0,    0,  526,    0,    0,    0,    0,    0,
    0,    0,  757,    0,    0,  302,    0,    0,    0,  302,
    0,    0,  302,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  896,    0,    0,    0,    0,    0,  816,
    0,    0,    0,    0,    0,    0,    0,    0,  663,    0,
    0,    0,    0,    0,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  241,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  666,    0,    0,  526,    0,  -87,    0,    0,    0,
    0,    0,  257,    0,    0,    0,  179,    0,    0,  302,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  280,    0,    0,    0,    0,    0,   90,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  526,    0,
    0,    0,    0,    0,    0,    0,  302,    0,    0,    0,
    0,  303,    0,    0,    0,    0,    0,  241,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  302,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   48,  107,  -35,   -2,    0,    0,    0,    0,  498,
   22,    0,  -75,    0,  659,  664,  667,  668,  -13,  -39,
 -200,    0, -173,  546,  530,  609,  -17,    4, -149,  480,
    0,  679,    0,   51,   56,   60,   73,   75,   -9,    0,
    0,  -25,    0,    0,  851,  764,    0,  431,  437,  -49,
    0,    0,  602,
};
final static int YYTABLESIZE=1224;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  136,  147,  154,   36,  275,   58,  247,  108,
  109,  146,   18,  117,  149,   69,   50,   75,  182,   52,
  349,  147,  169,  230,  114,  225,  321,  367,    2,  146,
   18,   18,  149,  230,  115,  107,  149,  122,   84,  170,
   82,  165,  134,  208,   76,  221,  207,  122,   94,   53,
   95,   34,   65,  144,   58,   40,  270,   85,   78,  164,
   39,  202,  222,   63,  117,   97,   98,   96,   64,   65,
   39,  186,   65,   63,  190,  135,   39,  191,   18,   39,
   81,  124,   39,  187,   82,   66,   39,   67,   77,  167,
  171,  125,   88,  299,  116,  301,   39,   66,   39,   82,
   83,  134,  158,  159,  119,  118,  273,  166,   16,   71,
   16,  185,   71,  162,   66,  297,  148,   74,   58,  200,
   39,   73,  338,  204,  272,   79,   71,   87,  232,   58,
   68,  236,   39,  192,  135,   39,  158,   39,   86,   16,
   73,   90,   97,   98,   96,   35,  233,   68,  357,  103,
  235,  343,  104,  239,  162,  162,  162,  149,  162,  156,
  162,  229,    1,  130,  279,   82,  372,  130,  120,  297,
  130,  306,  162,  162,  162,  162,  123,  158,  122,  158,
  144,  158,  179,   63,  122,  101,  126,   73,   64,  117,
  102,   58,   65,  127,  237,  158,  158,  158,  158,  128,
  156,  379,  156,   10,  156,   66,  246,   67,  249,  157,
   63,   63,   94,   68,   95,   74,  181,   36,  156,  156,
  156,  156,   63,  147,  153,   18,  153,   18,  324,   35,
  185,  146,  178,   48,  149,   49,   51,   58,  162,  152,
  308,   58,   11,   12,  249,   11,   12,  250,  251,  341,
  157,  105,  157,  106,  157,  162,  106,   11,   12,   11,
   12,  158,  348,  350,   18,  163,   18,  353,  157,  157,
  157,  157,   37,   38,   91,   92,   93,  110,   20,  130,
  155,  364,   37,   38,  156,  168,  368,  345,   37,   38,
  173,   37,   38,  172,   37,   38,  376,   82,   37,   38,
   82,  174,  138,   61,   82,   82,   82,  179,   37,   38,
   37,   38,   82,   82,  175,  361,   82,   82,   82,   82,
  246,  117,   18,  176,  117,   75,  117,  117,  117,  117,
  117,  117,  147,   38,  157,  249,  117,  117,  117,  117,
  117,  117,  117,  117,   37,   38,  246,   37,   38,  147,
   38,   91,   92,   93,  249,  177,  238,  178,   94,  178,
   95,  148,  148,  148,  246,   16,  252,  253,  179,  249,
  162,  180,   18,  162,  162,  162,  162,  162,  162,  162,
  162,  106,  162,  162,  162,  162,  162,  162,  162,  162,
  162,  162,  162,  158,  257,  258,  158,  158,  158,  158,
  158,  158,  158,  158,  130,  158,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,  156,  259,  260,  156,
  156,  156,  156,  156,  156,  156,  156,  138,  156,  156,
  156,  156,  156,  156,  156,  156,  156,  156,  156,  179,
   61,   61,  179,  188,  179,  179,  179,  179,  179,  179,
   75,   94,   61,   95,  179,  179,  179,  179,  179,  179,
  179,  179,  290,  291,  339,  340,  157,  146,   80,  157,
  157,  157,  157,  157,  157,  157,  157,  209,  157,  157,
  157,  157,  157,  157,  157,  157,  157,  157,  157,  178,
   16,   77,  178,   68,  178,  178,  178,  178,  178,  178,
  362,  363,  143,  143,  178,  178,  178,  178,  178,  178,
  178,  178,   80,  106,   20,  323,  106,  193,  106,  106,
  106,  106,  106,  106,  156,  157,  201,   80,  106,  106,
  106,  106,  106,  106,  106,  106,  130,  160,  161,  130,
  205,  130,  130,  130,  130,  130,  130,   33,  110,  110,
   77,  130,  130,  130,  130,  130,  130,  130,  130,  138,
  248,  206,  138,  217,  138,  138,  138,  138,  138,  138,
  148,  148,  148,   20,  138,  138,  138,  138,  138,  138,
  138,  138,   75,  282,  277,   75,   94,  218,   95,   75,
   75,   75,  219,   80,  224,  228,  227,   75,   75,  231,
  234,   75,   75,   75,   75,  110,  241,  143,  242,  243,
  244,   54,  265,  262,    6,  214,   77,  269,    7,  271,
  274,  276,   16,  284,  278,   16,    9,   10,  280,   16,
   16,   16,   13,  286,  289,  293,  295,   16,   16,   20,
  300,   16,   16,   16,   16,  302,  303,  307,  183,   54,
  305,  309,    6,  310,  254,  255,    7,  311,  130,  312,
  194,  313,  143,  317,    9,   10,  143,  220,  316,  223,
   13,  319,  110,  318,  226,   54,  320,   57,    6,  322,
  325,    5,    7,  321,    6,  326,  327,  328,    7,    8,
    9,   10,   54,  329,  209,    6,    9,   10,  330,    7,
   11,   12,   13,   14,  331,  209,  332,    9,   10,  333,
  334,  337,  344,   13,  346,   54,  261,  347,    6,  263,
  352,  264,    7,  351,  266,   80,  354,  209,   80,  209,
    9,   10,   80,   80,   80,  355,   13,  356,  358,  359,
   80,   80,  360,  365,   80,   80,   80,   80,   77,  209,
  366,   77,  369,  371,  370,   77,   77,   77,  373,  296,
  375,   72,  110,   77,   77,  374,  377,   77,   77,   77,
   77,   20,  378,  380,   20,  382,  383,  110,   20,   20,
   20,  381,   72,    4,   80,   52,   20,   20,   56,  281,
   20,   20,   20,   20,   54,  203,  197,    6,  335,  198,
  199,    7,  240,  130,   55,  189,  100,   82,  245,    9,
   10,   54,   56,    0,    6,   13,  110,  145,    7,    0,
    0,   55,    0,    0,    0,    0,    9,   10,   54,   56,
    0,    6,   13,  254,  255,    7,    0,    0,    0,   54,
   98,    0,    6,    9,   10,  287,    7,    0,    0,   13,
    0,    0,    0,    0,    9,   10,    0,    0,    0,  102,
   13,   54,    0,   54,    6,  304,    6,    0,    7,  314,
    7,    0,    0,    0,    0,    0,    9,   10,    9,   10,
    0,   99,   13,   54,   13,    0,    6,    0,    0,    0,
    7,    0,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,    0,  184,   13,    0,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,    5,    0,   54,    6,
  184,  129,    0,    7,    8,    7,    0,    0,    0,    0,
    0,    9,   10,  131,  132,   11,   12,   13,   14,    5,
  144,    0,    6,    0,    0,    0,    7,    8,    0,    5,
  282,    0,    6,    0,    9,   10,    7,    8,   11,   12,
   13,   14,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,   98,    0,    0,   98,    0,    0,    0,   98,
    0,   98,   98,    0,    0,    0,    0,   98,   98,   98,
   98,  102,    0,   98,  102,    0,    0,    0,  102,    0,
  102,  102,  212,  213,    0,  216,  102,  102,  102,  102,
    0,   54,  102,   99,    6,    0,   99,    0,    7,    0,
   99,    0,   99,   99,    0,    0,    9,   10,   99,   99,
   99,   99,   13,    0,   99,   54,    0,    0,    6,    0,
    0,    0,    7,    0,  130,   55,    0,    0,    0,    0,
    9,   10,   54,   56,    0,    6,   13,    0,    0,    7,
    0,    0,   55,    0,  256,    0,    0,    9,   10,    0,
   56,    0,  144,   13,    0,  144,    0,  140,  140,  144,
    0,    0,   54,    0,    0,    6,    0,  144,  144,    7,
    0,  130,    0,  144,    0,    0,    0,    9,   10,    0,
  283,    0,  285,   13,  288,    5,    0,  292,  129,  294,
    0,    0,    7,    8,  130,    0,    0,    0,    0,    0,
  131,  132,    0,    0,   11,   12,  133,   14,    5,    0,
    0,    6,    0,    0,    0,    7,    8,  130,    0,    0,
  315,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   33,    0,  256,   33,    0,    0,    0,   33,
   33,   33,    5,    0,    0,    6,    0,   33,   33,    7,
    8,   33,   33,   33,   33,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,   54,    0,    0,    6,    0,
    0,    0,    7,    0,    0,   55,    0,    0,    0,    0,
    9,   10,   54,   56,    0,    6,   13,    0,    0,    7,
    0,  130,    0,    0,    0,    0,    0,    9,   10,    0,
    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   78,   41,   41,   44,   41,   10,  209,   40,
   41,   41,   15,   53,   41,   59,   40,   59,   59,   40,
  123,   59,   41,  123,   41,  175,  125,  123,  123,   59,
   33,   34,   59,  123,   52,   41,   86,  125,   35,   58,
    0,   41,   78,   41,   23,   41,   44,   57,   43,   40,
   45,    4,   41,   79,   57,   40,  230,   36,  123,   59,
   45,  137,   58,   13,    0,   60,   61,   62,   13,   58,
   45,   40,   13,  125,   40,   78,   45,   40,   81,   45,
   33,  258,   45,  123,   44,   13,   45,   13,   59,   41,
  108,  268,   41,  267,   41,  269,   45,   41,   45,   59,
   59,  137,   99,  100,   58,   59,   41,   59,    2,   41,
    4,  121,   44,    0,   58,  265,   41,  257,  121,  133,
   45,   15,  323,  137,   59,   58,   58,  258,   41,  132,
   41,   41,   45,  130,  137,   45,    0,   45,   40,   33,
   34,   41,   60,   61,   62,  268,  186,   58,  349,   41,
  190,  325,   41,  193,   41,   42,   43,  207,   45,    0,
   47,  261,  257,  266,  240,  125,  367,  266,   59,  319,
  266,  261,   59,   60,   61,   62,  274,   41,  266,   43,
  206,   45,    0,  133,  194,   42,   59,   81,  133,  125,
   47,  194,  133,   44,  191,   59,   60,   61,   62,   59,
   41,  375,   43,  273,   45,  133,  209,  133,  211,    0,
  262,  263,   43,  257,   45,  257,  257,   44,   59,   60,
   61,   62,  274,  261,  261,  228,  261,  230,  304,  268,
  240,  261,    0,  257,  261,  259,  257,  240,  125,  261,
  280,  244,  276,  277,  247,  276,  277,  262,  263,  325,
   41,  257,   43,  259,   45,   59,    0,  276,  277,  276,
  277,  125,  338,  339,  267,   59,  269,  343,   59,   60,
   61,   62,  257,  258,  269,  270,  271,   58,   59,    0,
  261,  357,  257,  258,  125,   59,  362,  327,  257,  258,
  257,  257,  258,   58,  257,  258,  372,  257,  257,  258,
  260,   41,    0,  125,  264,  265,  266,  125,  257,  258,
  257,  258,  272,  273,   44,  355,  276,  277,  278,  279,
  323,  257,  325,  257,  260,    0,  262,  263,  264,  265,
  266,  267,  257,  258,  125,  338,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  258,  349,  257,  258,  257,
  258,  269,  270,  271,  357,   58,   41,  125,   43,   41,
   45,   60,   61,   62,  367,    0,  262,  263,  261,  372,
  257,   41,  375,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  262,  263,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  262,  263,  260,
  261,  262,  263,  264,  265,  266,  267,  125,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  262,  263,  260,  258,  262,  263,  264,  265,  266,  267,
  125,   43,  274,   45,  272,  273,  274,  275,  276,  277,
  278,  279,  262,  263,  262,  263,  257,   59,    0,  260,
  261,  262,  263,  264,  265,  266,  267,  123,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,    0,  260,  257,  262,  263,  264,  265,  266,  267,
  262,  263,  262,  263,  272,  273,  274,  275,  276,  277,
  278,  279,   44,  257,    0,  123,  260,   40,  262,  263,
  264,  265,  266,  267,   94,   95,  125,   59,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  101,  102,  260,
  125,  262,  263,  264,  265,  266,  267,  123,   51,   52,
   59,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,   58,  260,   59,  262,  263,  264,  265,  266,  267,
  269,  270,  271,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  125,   41,  260,   43,   59,   45,  264,
  265,  266,   58,  125,   58,  123,   58,  272,  273,   59,
   41,  276,  277,  278,  279,  108,  274,   78,  125,  125,
  273,  257,   44,   58,  260,  261,  125,  123,  264,   59,
   59,   59,  257,   59,   41,  260,  272,  273,   40,  264,
  265,  266,  278,   59,   59,   59,   59,  272,  273,  125,
  125,  276,  277,  278,  279,  125,   59,  125,  119,  257,
   59,  274,  260,  263,  262,  263,  264,  263,  266,   59,
  123,  263,  133,  263,  272,  273,  137,  170,   59,  172,
  278,   44,  175,  263,  177,  257,   59,  123,  260,   59,
  123,  257,  264,  125,  260,   41,   40,   59,  264,  265,
  272,  273,  257,   59,  123,  260,  272,  273,   59,  264,
  276,  277,  278,  279,   59,  123,  263,  272,  273,   59,
   59,   59,   59,  278,   59,  257,  219,  272,  260,  222,
  125,  224,  264,   59,  227,  257,   41,  123,  260,  123,
  272,  273,  264,  265,  266,   40,  278,  125,  263,   59,
  272,  273,   59,   59,  276,  277,  278,  279,  257,  123,
   41,  260,   59,  261,  125,  264,  265,  266,  263,  262,
  123,  125,  265,  272,  273,  263,   59,  276,  277,  278,
  279,  257,   59,  125,  260,  263,   59,   58,  264,  265,
  266,  125,  257,    0,  125,  123,  272,  273,  123,  244,
  276,  277,  278,  279,  257,  137,  133,  260,  319,  133,
  133,  264,  194,  266,  267,  127,   43,  125,  207,  272,
  273,  257,  275,   -1,  260,  278,  319,  125,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,  262,  263,  264,   -1,   -1,   -1,  257,
  125,   -1,  260,  272,  273,  263,  264,   -1,   -1,  278,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,  125,
  278,  257,   -1,  257,  260,  261,  260,   -1,  264,  263,
  264,   -1,   -1,   -1,   -1,   -1,  272,  273,  272,  273,
   -1,  125,  278,  257,  278,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,   -1,  125,  278,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,  257,  260,
  125,  260,   -1,  264,  265,  264,   -1,   -1,   -1,   -1,
   -1,  272,  273,  272,  273,  276,  277,  278,  279,  257,
  125,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,  257,
  125,   -1,  260,   -1,  272,  273,  264,  265,  276,  277,
  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,  152,  153,   -1,  155,  272,  273,  274,  275,
   -1,  257,  278,  257,  260,   -1,  260,   -1,  264,   -1,
  264,   -1,  266,  267,   -1,   -1,  272,  273,  272,  273,
  274,  275,  278,   -1,  278,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  257,  275,   -1,  260,  278,   -1,   -1,  264,
   -1,   -1,  267,   -1,  214,   -1,   -1,  272,  273,   -1,
  275,   -1,  257,  278,   -1,  260,   -1,  262,  263,  264,
   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,
   -1,  266,   -1,  278,   -1,   -1,   -1,  272,  273,   -1,
  250,   -1,  252,  278,  254,  257,   -1,  257,  260,  259,
   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,   -1,   -1,
  290,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  257,   -1,  304,  260,   -1,   -1,   -1,  264,
  265,  266,  257,   -1,   -1,  260,   -1,  272,  273,  264,
  265,  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
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
"sentencia_declarativa : funcion_con_return",
"sentencia_declarativa : funcion_sin_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_con_return : encabezado_funcion '{' sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' sentencias_funcion sentencia_return '}'",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable",
"sentencia_funcion : sentencia_when_con_return",
"sentencia_funcion : DEFER sentencia_when_con_return",
"sentencia_funcion : sentencia_do_con_return",
"sentencia_funcion : DEFER sentencia_do_con_return",
"sentencia_funcion : sentencia_seleccion_simple_con_return",
"sentencia_funcion : DEFER sentencia_seleccion_simple_con_return",
"sentencia_funcion : sentencia_seleccion_compuesta_con_return",
"sentencia_funcion : DEFER sentencia_seleccion_compuesta_con_return",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE sentencia_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE sentencia_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : sentencias_when sentencia_return",
"sentencia_do_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"sentencia_do_con_return : etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
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

//#line 351 ".\gramatica.y"

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

		String path = new File(archivo_a_leer).getAbsolutePath().replaceAll(args[0],"");
        
        Output out = new Output(path);
        
        String printTs = ts.print();
        
        
        out.saveFile("codigo-lexico.txt", logger.getLexico());
		out.saveFile("codigo-sintetico.txt", logger.getSintactico());
		out.saveFile("tabla-de-simbolos.txt", printTs);
        
		System.out.println(printTs);
	}
}
//#line 836 "Parser.java"
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
//#line 17 ".\gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 18 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre del programa"); }
break;
case 3:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); }
break;
case 4:
//#line 20 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); }
break;
case 5:
//#line 21 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias del programa"); }
break;
case 15:
//#line 46 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 16:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 17:
//#line 48 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 49 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 21:
//#line 58 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 22:
//#line 59 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 35:
//#line 81 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 36:
//#line 82 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 43:
//#line 98 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 46:
//#line 107 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 47:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 52:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 53:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 54:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 55:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 56:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 57:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 58:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 59:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 61:
//#line 131 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 62:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 63:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 64:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 67:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 72:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 73:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 74:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 75:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 76:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 80:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 81:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 82:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 96:
//#line 192 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 97:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 98:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 99:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 101:
//#line 200 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 102:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 105:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 106:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 107:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 108:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 109:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 115:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 116:
//#line 233 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 117:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 118:
//#line 238 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 119:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 120:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 121:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 124:
//#line 250 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 125:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 126:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 129:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 130:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 131:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 132:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 133:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 135:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 136:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 137:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 138:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 142:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 143:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 148:
//#line 282 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 149:
//#line 283 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 171:
//#line 329 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 331 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 174:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 175:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 176:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 177:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 178:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 179:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 181:
//#line 342 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1329 "Parser.java"
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
