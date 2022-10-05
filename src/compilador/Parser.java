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
    7,    8,   13,   13,   14,   14,   16,   16,   17,   17,
   20,   20,   18,   18,   18,   18,   18,   18,   18,   18,
   24,   21,   21,   22,   26,   26,   23,   23,   28,   30,
   30,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   15,   15,   15,   15,   15,   32,   32,   32,   35,
   35,   34,   34,    9,    9,    9,   36,   36,   37,   37,
   37,   37,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,   43,   43,   43,   44,   44,   44,   44,   44,
   45,   45,   42,   42,   46,   46,   46,   46,   46,   29,
   47,   47,   31,   31,   38,   38,   38,   41,   41,   48,
   41,   27,   27,   39,   39,   39,   39,   39,   39,   39,
   39,   39,   39,   39,   39,   39,   39,   39,   39,   49,
   49,   49,   49,   25,   25,   19,   19,   51,   19,   50,
   50,   50,   50,   50,   50,   33,   33,   33,   52,   52,
   52,   53,   53,   53,   55,   56,   56,   57,   57,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   54,   54,
   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    2,    1,    2,    1,    2,   10,    0,
    1,    4,    1,    1,    1,    2,    1,    2,    1,    2,
    8,    1,    4,    9,    1,    2,    1,    3,    7,    1,
    4,    6,    7,    5,    5,    5,    5,    6,    6,    6,
    6,    5,    4,    3,    3,    4,    1,    3,    5,    1,
    3,    2,    1,    3,    2,    2,    1,    3,    3,    2,
    2,    1,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    1,    1,    2,    4,    1,    3,    3,
    2,    1,    1,    3,    7,    6,    6,    6,    6,    1,
    1,    3,    1,    2,    4,    3,    3,    9,    8,    0,
   17,    1,    2,    8,   10,    7,    7,    7,    7,    7,
    7,    9,    9,    9,    9,    9,    9,    9,    8,    1,
    3,    2,    2,    1,    2,    3,    2,    0,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    1,    3,    1,    1,    5,
    5,    4,    4,    4,    4,    4,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  182,  181,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   83,   85,   87,   89,
   91,  103,    0,    0,    0,    0,    0,  179,    0,    0,
    0,    0,    0,    0,  161,  163,  164,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,  111,   94,
   95,    0,   84,   86,   88,   90,   92,    0,   76,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  116,    0,   19,    0,  180,    0,    0,    0,
  150,  151,  152,    0,    0,  153,  154,  155,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   96,    0,  101,
    0,  113,    0,   81,    0,   74,    0,   15,    0,    0,
    0,    0,    0,   33,   34,    0,    0,   23,    0,   25,
   27,   35,   37,   39,   47,    0,  104,    1,  115,  168,
  169,    0,  166,    0,    0,    0,    0,    0,    0,    0,
    0,  159,  160,  175,  173,  176,    0,  174,    0,  172,
    0,    0,    0,    0,   72,    0,    0,    0,    0,    0,
    0,  110,  100,    0,  112,  114,    0,    0,   79,   78,
    0,    0,    0,    0,    0,   50,    0,    0,   36,   38,
   40,   21,   22,   24,   26,   28,    0,    0,  165,    0,
    0,    0,    0,    0,    0,    0,    0,  171,  170,    0,
   57,    0,    0,   56,    0,    0,   55,    0,    0,    0,
    0,   97,    0,    0,    0,    0,    0,    0,   64,    0,
    0,    0,    0,   48,  167,  144,    0,  142,  145,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   52,    0,   61,   60,    0,   59,    0,    0,    0,    0,
  107,  109,    0,  108,    0,   66,    0,    0,    0,    0,
    0,  141,    0,  128,    0,  126,    0,    0,  131,    0,
    0,    0,  129,    0,  127,   53,    0,   69,  123,    0,
    0,    0,  105,    0,   62,    0,   51,    0,    0,    0,
    0,  139,    0,    0,    0,  124,    0,    0,    0,  120,
    0,  119,    0,    0,    0,    0,    0,    0,    0,  134,
  132,  136,  137,    0,  135,  133,   71,    0,  118,    0,
   42,    0,    0,    0,    0,   45,    0,    0,   49,    0,
  125,    0,    0,    0,    0,    0,   31,    0,   41,    0,
   46,    0,   32,   43,    0,   44,    0,   29,    0,    0,
    0,    0,  121,
};
final static short yydgoto[] = {                          3,
    4,   15,  267,   17,  211,   19,   20,   21,   22,   23,
   24,   25,  136,  137,  341,  139,  140,  141,   41,  325,
  326,  142,  143,  144,  212,  347,  268,  145,   26,  197,
  121,  112,   42,  113,  298,   70,   71,   27,   28,   29,
   30,   31,   59,   60,   61,   32,   62,  338,  216,   99,
   43,   44,   45,   46,   47,  152,  153,
};
final static short yysindex[] = {                       -87,
    0, 1007,    0,  387,  -33,    1,  -13,   -1,   34,  642,
    0,    0,  532,  -41,  747,    0,    0,    0,    0,    0,
    0,    0,  -40,   44,  -15,   52,    0,    0,    0,    0,
    0,    0,  771,  794,   73, -129,  101,    0, -115,   86,
  108,  -17,  302,   59,    0,    0,    0,  128,  130,  -38,
  -34,  -26,   88,  -86,  147,  148, 1030,    0,    0,    0,
    0,  -60,    0,    0,    0,    0,    0, -156,    0,  159,
  182,    0,    0,  213,    0,  205,    0,  974,   17,    0,
  804,    0,    0,   18,    0,   38,    0,    7,  -31,   78,
    0,    0,    0,   79,   79,    0,    0,    0,   79,   79,
   79,   79,  265,  283,   94,   98,  288,    0,  -29,  303,
  111,  340,  350,  362,  384,  187,  410,    0,  -35,    0,
  908,    0,   69,    0,  195,    0,  203,    0,   71,   75,
  433,  272,  906,    0,    0,  358,  359,    0,  974,    0,
    0,    0,    0,    0,    0,  428,    0,    0,    0,    0,
    0,   63,    0,  730,  730,  397,  730,   59,   59,   22,
   22,    0,    0,    0,    0,    0,  436,    0,  437,    0,
  441,  -43,  109,  -43,    0,  443, -185,  -43,  453,  385,
 -102,    0,    0,  464,    0,    0,   97,  484,    0,    0,
   86,   99,  277,   88, 1030,    0,  253,   77,    0,    0,
    0,    0,    0,    0,    0,    0,  257,   38,    0,  749,
    0,  954,   30,   90,  602,   96,  104,    0,    0,  -43,
    0,  470,  -43,    0,  -43,  487,    0,  -43, 1007,  411,
 1007,    0,  476,  106,  478,  -24,  482,  481,    0,  499,
  889,  503,   86,    0,    0,    0,  964,    0,    0,  730,
  490,  730,  493,  661,  500,  160,  730,  501,  730,  502,
    0,  -43,    0,    0, -185,    0, 1007,  449, 1007,  452,
    0,    0,  519,    0,  680,    0,  528,  -75,  455,   79,
   -6,    0,  299,    0,  325,    0,  530,  327,    0,  691,
  542,  339,    0,  341,    0,    0,  559,    0,    0,  546,
  483,  551,    0,  585,    0,  488,    0,  571,  711,  555,
  557,    0,  560,  569,  355,    0,  570,  572, -185,    0,
  575,    0,  713,    0,  368,  377,  997,  576,  622,    0,
    0,    0,    0,  586,    0,    0,    0,  376,    0,  749,
    0,  383,  937,  -90,  591,    0,  531, -105,    0,  749,
    0,  613,  349,  537,  543,  -83,    0,  392,    0,  608,
    0,   79,    0,    0,  612,    0,  631,    0,  412,  553,
 1007,  554,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  204,  372,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    9,    0,    0,  372,
    0,    0,    0,   37,    0,    0,    0,    0,    0,    0,
    0,    0,  372,  620,  831,  851,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  360,    0,  498,
  450,    0,    0,  474,    0,  521,    0,  561,    0,    0,
  684,    0,    0,  164,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -37,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  110,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  372,    0,  417,    0,    0,    0,  372,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  561,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  113,  136,  -36,
  -25,    0,    0,    0,    0,    0,  192,    0,  215,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  870,    0,    0,  372,    0,    0,    0,
  372,    0,    0,  372,    0,    0,    0,  372,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  927,    0,    0,    0,    0,    0,    0,    0,    0,  562,
    0,    0,    0,    0,    0,  191,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -72,    0,    0,
    0,    0,  372,    0,    0,    0,  184,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  564,    0,    0,    0,    0,  -78,    0,    0,    0,
    0,    0,  240,    0,    0,    0,  -63,    0,    0,  372,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  291,    0,    0,    0,    0,    0,  219,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  200,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  319,    0,    0,    0,    0,    0,    0,
    0,    0,  184,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  372,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   55,  702,  -18,   -2,    0,    0,    0,    0,  648,
   50,    0,    0,    0,  468,    0,  541,  549,  -39,  347,
 -281,  565,  568,  578,   95,    0, -182,  485,   27,    0,
  507,   14,   -5, -139,  378,  592,    0,   45,   51,   68,
   80,   87,  -32,    0,    0,  -22,    0,    0,  587,  660,
    0,  255,  270,    4,    0,    0,  497,
};
final static int YYTABLESIZE=1308;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  107,  147,  146,  109,  110,   58,  162,  156,
   36,  171,   18,  117,  114,  149,  275,   69,   75,  321,
  231,  147,  146,  183,  122,   94,   50,   95,  172,   84,
   18,   18,  356,  149,  309,    2,  158,  226,   52,  340,
   40,  342,   97,   98,   96,   39,  122,  231,  270,  162,
  162,  162,   65,  162,   58,  162,  147,   63,   34,  134,
   94,   63,   95,   64,   94,  115,   95,  162,  162,  162,
  162,  108,   76,   53,  342,  135,  149,  158,   18,  158,
   65,  158,   39,  188,  299,   85,  301,   81,  186,  151,
   11,   12,   66,  160,  161,  158,  158,  158,  158,   67,
  101,  124,   77,  209,  146,  102,  208,   78,  187,   79,
  191,  125,  156,   39,  192,   39,  243,   39,   58,   39,
  134,   39,  173,   39,  193,  297,   88,   74,  116,   58,
   39,   83,   39,  162,  167,  157,  135,  233,  169,  237,
   86,   39,   87,   39,  348,  184,  273,  234,   90,  222,
   67,  236,  166,  156,  240,  156,  168,  156,  230,  146,
  130,  158,  122,  117,  272,  146,  223,   67,  103,    1,
  104,  156,  156,  156,  156,  130,  157,   63,  157,  297,
  157,   35,  130,   64,  147,  306,  238,  122,  372,   65,
   65,  178,   58,   65,  157,  157,  157,  157,   63,   63,
   65,   65,   63,  281,  119,  118,  120,  246,  186,  249,
   63,  151,   66,  123,  177,   68,   74,  126,  105,   67,
  106,  182,  108,  147,  146,  127,   18,  108,   18,  155,
  108,   68,   11,   12,   35,  149,  155,  156,   58,  106,
  308,   11,   12,   48,  249,   49,   11,   12,   68,   11,
   12,   91,   92,   93,  155,   51,   36,   37,   38,   70,
  157,  110,   20,  128,   18,  162,   18,  154,  162,  162,
  162,  162,  162,  162,  162,  162,   70,  162,  162,  162,
  162,  162,  162,  162,  162,  162,  162,  162,  117,   10,
  130,  250,  251,  158,  150,   38,  158,  158,  158,  158,
  158,  158,  158,  158,  247,  158,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,  178,  239,  138,   94,
  246,   95,  367,  164,   18,   37,   38,   37,   38,   37,
   38,   37,   38,   37,   38,   37,   38,  246,  157,  177,
  249,  165,   37,   38,   37,   38,  170,  246,  158,  159,
  249,  252,  253,   37,   38,   37,   38,  257,  258,   82,
  174,   97,   98,   96,  106,  259,  260,  175,   18,  156,
  162,  163,  156,  156,  156,  156,  156,  156,  156,  156,
  176,  156,  156,  156,  156,  156,  156,  156,  156,  156,
  156,  156,  157,  177,  195,  157,  157,  157,  157,  157,
  157,  157,  157,   82,  157,  157,  157,  157,  157,  157,
  157,  157,  157,  157,  157,  130,   80,  343,   82,  178,
  117,  290,  291,  117,  179,  117,  117,  117,  117,  117,
  117,  148,  148,  148,  353,  117,  117,  117,  117,  117,
  117,  117,  117,  138,  343,  143,  143,  180,  178,   77,
  181,  178,  189,  178,  178,  178,  178,  178,  178,   68,
   80,   31,   42,  178,  178,  178,  178,  178,  178,  178,
  178,  177,  194,   20,  177,   80,  177,  177,  177,  177,
  177,  177,  202,  203,   82,  207,  177,  177,  177,  177,
  177,  177,  177,  177,  218,  219,  106,   75,  220,  106,
  225,  106,  106,  106,  106,  106,  106,  229,   77,   33,
  228,  106,  106,  106,  106,  106,  106,  106,  106,  210,
   16,  277,  232,   94,  235,   95,  242,  262,   54,  132,
  265,    6,   20,  269,  271,    7,  274,  130,   55,  278,
  276,   80,  280,    9,   10,  138,   56,  130,  284,   13,
  130,  286,  130,  130,  130,  130,  130,  130,  289,  293,
  295,  310,  130,  130,  130,  130,  130,  130,  130,  130,
   91,   92,   93,  300,   77,  138,  302,  303,  138,  307,
  138,  138,  138,  138,  138,  138,  305,  311,  312,  313,
  138,  138,  138,  138,  138,  138,  138,  138,   20,  196,
  316,  317,  319,  318,  320,   54,  204,  321,    6,  322,
  327,  328,    7,  330,  130,  331,   82,  334,  332,   82,
    9,   10,   75,   82,   82,   82,   13,  333,  335,  344,
  336,   82,   82,  339,  349,   82,   82,   82,   82,  345,
  148,  148,  148,    5,  351,   16,    6,  352,  130,  359,
    7,    8,  362,   54,  365,  360,    6,  215,    9,   10,
    7,  363,   11,   12,   13,   14,  366,  364,    9,   10,
  368,  369,  370,   80,   13,  371,   80,  110,  373,  205,
   80,   80,   80,    4,   54,   30,   58,  206,   80,   80,
  358,  244,   80,   80,   80,   80,  337,  199,  111,  111,
  200,  241,  100,   16,  245,   16,   77,  323,  279,   77,
  201,    0,    0,   77,   77,   77,   73,    0,  190,    0,
    0,   77,   77,    0,  210,   77,   77,   77,   77,    0,
   20,    0,    0,   20,   16,   73,    0,   20,   20,   20,
  213,  214,    0,  217,  350,   20,   20,    0,    0,   20,
   20,   20,   20,    0,   75,    0,  111,   75,    0,    0,
    0,   75,   75,   75,   57,    0,    0,    0,    0,   75,
   75,  324,    0,   75,   75,   75,   75,   16,    0,    0,
   16,    0,   73,  210,   16,   16,   16,    0,   54,    0,
    0,    6,   16,   16,  346,    7,   16,   16,   16,   16,
    0,  256,  210,    9,   10,    0,    0,    0,    0,  354,
  355,  357,    0,  210,    0,  361,    0,    0,    0,  221,
  355,  224,    0,    0,  111,  227,    0,    0,    0,    0,
    0,    0,    0,  210,    0,  340,  283,    0,  285,    0,
  288,   54,    0,  292,    6,  294,  254,  255,    7,    0,
  130,    0,  210,    0,    0,    0,    9,   10,   54,    0,
    0,    6,   13,  254,  255,    7,    0,  261,    0,    0,
  263,   72,  264,    9,   10,  266,  315,    0,   54,   13,
    0,    6,    0,  254,  255,    7,    0,  130,    0,    0,
  256,    0,    0,    9,   10,   80,    0,    0,   54,   13,
    0,    6,    0,    0,    0,    7,    0,    0,   55,  296,
    0,    0,  111,    9,   10,  256,   56,   54,   82,   13,
    6,    0,    0,  287,    7,    0,    0,    0,  148,    0,
    0,    0,    9,   10,    0,    0,   54,    0,   13,    6,
  304,    0,    0,    7,    0,    0,    0,   54,    0,    0,
    6,    9,   10,  314,    7,   98,    0,   13,    0,    0,
    0,    0,    9,   10,    0,    0,  111,   54,   13,   54,
    6,  329,    6,    0,    7,  102,    7,    0,  130,    0,
    0,    0,    9,   10,    9,   10,   54,    0,   13,    6,
   13,    0,    0,    7,   99,    0,    0,    0,    0,    0,
    0,    9,   10,    5,    0,   54,    6,   13,    6,    0,
    7,    8,    7,  185,    0,    0,    0,    0,    9,   10,
    9,   10,   11,   12,   13,   14,   13,    5,    0,    0,
    6,    0,  185,    0,    7,    8,    0,    0,    0,    0,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    5,  144,    0,    6,    0,    0,    0,    7,    8,    0,
    5,  282,    0,    6,    0,    9,   10,    7,    8,   11,
   12,   13,   14,    0,    0,    9,   10,    0,  248,   11,
   12,   13,   14,    0,    0,    0,    0,   98,  282,    0,
   98,    0,    0,    0,   98,    0,   98,   98,    0,    0,
    0,    0,   98,   98,   98,   98,    0,  102,   98,    0,
  102,    0,    0,    0,  102,    0,  102,  102,    0,    0,
    0,    0,  102,  102,  102,  102,   99,    0,  102,   99,
    0,    0,    0,   99,    0,   99,   99,    0,    0,    0,
    0,   99,   99,   99,   99,   54,    0,   99,    6,    0,
    0,    0,    7,    0,  130,   55,    0,    0,    0,    0,
    9,   10,   54,   56,   54,  198,   13,    6,    0,    7,
    0,    7,    0,    0,   55,    0,    0,  131,  132,    9,
   10,    0,   56,  144,    0,   13,  144,    0,  140,  140,
  144,    0,    0,   54,    0,    0,    6,    0,  144,  144,
    7,    0,  130,    0,  144,    0,    0,    0,    9,   10,
   54,    0,    0,    6,   13,    0,    0,    7,    0,    0,
   54,    0,    0,    6,    0,    9,   10,    7,    0,    0,
    5,   13,    0,  129,    0,    9,   10,    7,    8,  130,
    0,   13,    0,    0,    0,  131,  132,    0,    0,   11,
   12,  133,   14,    5,    0,    0,    6,    0,    0,    0,
    7,    8,  130,    5,    0,    0,    6,    0,    9,   10,
    7,    8,   11,   12,   13,   14,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,   54,    0,    0,    6,
    0,    0,    0,    7,    0,    0,   55,    0,    0,    0,
    0,    9,   10,    0,   56,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   41,   41,   41,   40,   41,   10,    0,   41,
   44,   41,   15,   53,   41,   41,   41,   59,   59,  125,
  123,   59,   59,   59,   57,   43,   40,   45,   58,   35,
   33,   34,  123,   59,   41,  123,    0,  177,   40,  123,
   40,  323,   60,   61,   62,   45,  125,  123,  231,   41,
   42,   43,  125,   45,   57,   47,   79,   13,    4,   78,
   43,  125,   45,   13,   43,   52,   45,   59,   60,   61,
   62,  257,   23,   40,  356,   78,   59,   41,   81,   43,
   13,   45,   45,  123,  267,   36,  269,   33,  121,   86,
  276,  277,   13,   99,  100,   59,   60,   61,   62,   13,
   42,  258,   59,   41,   78,   47,   44,  123,   40,   58,
   40,  268,    0,   45,   40,   45,   40,   45,  121,   45,
  139,   45,  109,   45,  130,  265,   41,  257,   41,  132,
   45,   59,   45,  125,   41,    0,  139,   41,   41,   41,
   40,   45,  258,   45,  327,  119,   41,  187,   41,   41,
   41,  191,   59,   41,  194,   43,   59,   45,  261,  133,
  266,  125,  195,    0,   59,  139,   58,   58,   41,  257,
   41,   59,   60,   61,   62,  266,   41,  133,   43,  319,
   45,  268,  266,  133,  207,  261,  192,  266,  371,  262,
  263,    0,  195,  266,   59,   60,   61,   62,  262,  263,
  133,  274,  266,  243,   58,   59,   59,  210,  241,  212,
  274,  208,  133,  274,    0,  257,  257,   59,  257,  133,
  259,  257,  257,  261,  261,   44,  229,  257,  231,  261,
  257,   41,  276,  277,  268,  261,  261,  125,  241,    0,
  280,  276,  277,  257,  247,  259,  276,  277,   58,  276,
  277,  269,  270,  271,  261,  257,   44,  257,  258,   41,
  125,   58,   59,   59,  267,  257,  269,  261,  260,  261,
  262,  263,  264,  265,  266,  267,   58,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  273,
    0,  262,  263,  257,  257,  258,  260,  261,  262,  263,
  264,  265,  266,  267,  210,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  125,   41,    0,   43,
  323,   45,  362,   59,  327,  257,  258,  257,  258,  257,
  258,  257,  258,  257,  258,  257,  258,  340,  261,  125,
  343,   59,  257,  258,  257,  258,   59,  350,   94,   95,
  353,  262,  263,  257,  258,  257,  258,  262,  263,    0,
   58,   60,   61,   62,  125,  262,  263,  257,  371,  257,
  101,  102,  260,  261,  262,  263,  264,  265,  266,  267,
   41,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   44,  123,  260,  261,  262,  263,  264,
  265,  266,  267,   44,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  125,    0,  323,   59,   58,
  257,  262,  263,  260,   41,  262,  263,  264,  265,  266,
  267,   60,   61,   62,  340,  272,  273,  274,  275,  276,
  277,  278,  279,  125,  350,  262,  263,  261,  257,    0,
   41,  260,  258,  262,  263,  264,  265,  266,  267,  257,
   44,  262,  263,  272,  273,  274,  275,  276,  277,  278,
  279,  257,   40,    0,  260,   59,  262,  263,  264,  265,
  266,  267,  125,  125,  125,   58,  272,  273,  274,  275,
  276,  277,  278,  279,   59,   59,  257,    0,   58,  260,
   58,  262,  263,  264,  265,  266,  267,  123,   59,  123,
   58,  272,  273,  274,  275,  276,  277,  278,  279,  123,
    0,   41,   59,   43,   41,   45,  274,   58,  257,  273,
   44,  260,   59,  123,   59,  264,   59,  266,  267,   41,
   59,  125,   40,  272,  273,   78,  275,  257,   59,  278,
  260,   59,  262,  263,  264,  265,  266,  267,   59,   59,
   59,  263,  272,  273,  274,  275,  276,  277,  278,  279,
  269,  270,  271,  125,  125,  257,  125,   59,  260,  125,
  262,  263,  264,  265,  266,  267,   59,  263,   59,  263,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  132,
   59,  263,   44,  263,   59,  257,  139,  125,  260,   59,
  123,   41,  264,   59,  266,   59,  257,  263,   59,  260,
  272,  273,  125,  264,  265,  266,  278,   59,   59,  262,
   59,  272,  273,   59,   59,  276,  277,  278,  279,  263,
  269,  270,  271,  257,   59,  125,  260,  272,  266,   59,
  264,  265,   40,  257,  263,  125,  260,  261,  272,  273,
  264,  125,  276,  277,  278,  279,   59,  125,  272,  273,
   59,   41,  261,  257,  278,  123,  260,   58,  125,  139,
  264,  265,  266,    0,  123,  125,  123,  139,  272,  273,
  344,  207,  276,  277,  278,  279,  319,  133,   51,   52,
  133,  195,   43,    2,  208,    4,  257,  123,  241,  260,
  133,   -1,   -1,  264,  265,  266,   15,   -1,  127,   -1,
   -1,  272,  273,   -1,  123,  276,  277,  278,  279,   -1,
  257,   -1,   -1,  260,   33,   34,   -1,  264,  265,  266,
  154,  155,   -1,  157,  123,  272,  273,   -1,   -1,  276,
  277,  278,  279,   -1,  257,   -1,  109,  260,   -1,   -1,
   -1,  264,  265,  266,  123,   -1,   -1,   -1,   -1,  272,
  273,  304,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   81,  123,  264,  265,  266,   -1,  257,   -1,
   -1,  260,  272,  273,  327,  264,  276,  277,  278,  279,
   -1,  215,  123,  272,  273,   -1,   -1,   -1,   -1,  342,
  343,  344,   -1,  123,   -1,  348,   -1,   -1,   -1,  172,
  353,  174,   -1,   -1,  177,  178,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  123,  250,   -1,  252,   -1,
  254,  257,   -1,  257,  260,  259,  262,  263,  264,   -1,
  266,   -1,  123,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,  262,  263,  264,   -1,  220,   -1,   -1,
  223,  125,  225,  272,  273,  228,  290,   -1,  257,  278,
   -1,  260,   -1,  262,  263,  264,   -1,  266,   -1,   -1,
  304,   -1,   -1,  272,  273,  125,   -1,   -1,  257,  278,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,  262,
   -1,   -1,  265,  272,  273,  329,  275,  257,  125,  278,
  260,   -1,   -1,  263,  264,   -1,   -1,   -1,  125,   -1,
   -1,   -1,  272,  273,   -1,   -1,  257,   -1,  278,  260,
  261,   -1,   -1,  264,   -1,   -1,   -1,  257,   -1,   -1,
  260,  272,  273,  263,  264,  125,   -1,  278,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  319,  257,  278,  257,
  260,  261,  260,   -1,  264,  125,  264,   -1,  266,   -1,
   -1,   -1,  272,  273,  272,  273,  257,   -1,  278,  260,
  278,   -1,   -1,  264,  125,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,  257,   -1,  257,  260,  278,  260,   -1,
  264,  265,  264,  125,   -1,   -1,   -1,   -1,  272,  273,
  272,  273,  276,  277,  278,  279,  278,  257,   -1,   -1,
  260,   -1,  125,   -1,  264,  265,   -1,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,  125,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,
  257,  125,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,  276,
  277,  278,  279,   -1,   -1,   -1,   -1,  257,  125,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,   -1,  257,  278,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  257,  275,  257,  260,  278,  260,   -1,  264,
   -1,  264,   -1,   -1,  267,   -1,   -1,  272,  273,  272,
  273,   -1,  275,  257,   -1,  278,  260,   -1,  262,  263,
  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,   -1,  266,   -1,  278,   -1,   -1,   -1,  272,  273,
  257,   -1,   -1,  260,  278,   -1,   -1,  264,   -1,   -1,
  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,
  257,  278,   -1,  260,   -1,  272,  273,  264,  265,  266,
   -1,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,   -1,  275,   -1,   -1,  278,
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
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return :",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' bloque_sentencias_ejecutables_seleccion_simple_con_return sentencia_return '}'",
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
"sentencia_when : WHEN '(' condicion ')' THEN '{' sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' THEN '{' sentencias_when '}' ';' $$1 WHEN '(' condicion ')' THEN '{' sentencias_when '}'",
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

//#line 364 ".\gramatica.y"

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
//#line 109 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 49:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 54:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 55:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 56:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 57:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 58:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 59:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 60:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 61:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 63:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 64:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 65:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 66:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 69:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 73:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 74:
//#line 169 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 75:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 76:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 80:
//#line 181 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 81:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 82:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 96:
//#line 206 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 97:
//#line 207 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 98:
//#line 208 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 99:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 101:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 102:
//#line 215 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 105:
//#line 224 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 106:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 107:
//#line 226 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 108:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 109:
//#line 228 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 118:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 119:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 120:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 121:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 124:
//#line 264 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 125:
//#line 265 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 126:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 129:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 130:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 131:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 132:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 133:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 137:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 138:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 139:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 142:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 143:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 148:
//#line 296 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 149:
//#line 297 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 170:
//#line 342 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 171:
//#line 343 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 344 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 173:
//#line 345 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 174:
//#line 346 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 175:
//#line 347 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 176:
//#line 348 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 177:
//#line 349 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 178:
//#line 350 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 355 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1313 "Parser.java"
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
