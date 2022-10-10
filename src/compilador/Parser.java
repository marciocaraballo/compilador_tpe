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
    7,    7,    7,   14,   14,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,    8,    8,   19,   19,   19,
   19,   18,   18,   16,   22,   22,   17,   17,   24,   24,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   13,   13,   13,   13,   13,   27,   27,   27,   30,   30,
   29,   29,   31,   29,    9,    9,    9,   32,   32,   33,
   33,   33,   33,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   39,   39,   39,   40,   40,   40,   40,
   40,   41,   41,   38,   38,   42,   42,   42,   42,   42,
   25,   43,   43,   26,   26,   34,   34,   34,   37,   37,
   44,   37,   23,   23,   35,   35,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   45,   45,   45,   45,   21,   21,   20,   20,   20,   46,
   46,   46,   46,   46,   46,   28,   28,   28,   47,   47,
   47,   48,   48,   48,   50,   50,   51,   51,   52,   52,
   36,   36,   36,   36,   36,   36,   36,   36,   36,   49,
   49,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    4,    5,   10,   13,   13,
   16,    8,   11,    9,    1,    2,    7,    9,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    4,    3,    3,    4,    1,    3,    5,    1,    3,
    2,    1,    0,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    1,    2,    8,   10,    7,    7,    7,    7,
    7,    7,    9,    9,    9,    9,    9,    9,    9,    8,
    1,    3,    2,    2,    1,    2,    3,    2,    2,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    3,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  183,  182,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   84,   86,   88,   90,
   92,  104,    0,    0,    0,    0,    0,  180,  150,  151,
  152,    0,    0,  153,  154,  155,    0,    0,    0,    0,
  161,  163,  164,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,  112,   95,   96,    0,   85,   87,
   89,   91,   93,    0,   77,    0,    0,    2,    8,    0,
   18,    0,   17,    0,    0,    5,    0,    3,  117,    0,
   19,    0,  181,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   97,    0,  102,
    0,  114,    0,   82,    0,   75,    0,   15,    0,    0,
    0,    0,    0,   23,   26,   27,    0,    0,   24,   28,
   30,   32,    0,    0,  105,    1,  116,  169,  165,  170,
    0,  167,    0,    0,    0,    0,    0,    0,    0,  159,
  160,  176,  174,  177,    0,  175,    0,  173,    0,    0,
    0,    0,   71,    0,    0,   74,    0,    0,    0,    0,
  111,  101,    0,  113,  115,    0,    0,   80,   79,    0,
    0,    0,    0,    0,   49,    0,   29,   31,   33,   35,
   21,    0,   25,    0,   36,    0,    0,  166,    0,    0,
    0,    0,    0,    0,    0,    0,  172,  171,    0,   56,
    0,    0,   55,    0,    0,   54,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    0,   22,   37,    0,  168,  145,    0,  143,  146,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,   60,   59,    0,   58,    0,    0,    0,    0,
  108,  110,    0,  109,    0,   65,    0,    0,    0,    0,
    0,  142,    0,  129,    0,  127,    0,    0,  132,    0,
    0,    0,  130,    0,  128,   52,    0,   68,  124,    0,
    0,    0,  106,    0,   61,    0,   50,    0,    0,    0,
    0,  140,    0,    0,    0,  125,    0,    0,    0,  121,
    0,  120,    0,    0,    0,    0,    0,  135,  133,  137,
  138,    0,  136,  134,   70,    0,  119,    0,    0,    0,
   45,    0,    0,   47,    0,  126,    0,    0,    0,    0,
   42,    0,   46,    0,    0,    0,    0,    0,   44,   48,
    0,    0,    0,    0,   38,    0,    0,    0,   43,    0,
    0,    0,    0,    0,    0,    0,   39,   40,    0,    0,
  122,    0,   41,
};
final static short yydgoto[] = {                          3,
    4,   15,  267,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  195,  138,  139,  140,  141,  142,  143,   47,
  211,  342,  268,  196,   26,  121,  111,   48,  112,  298,
  113,   76,   77,   27,   28,   29,   30,   31,   65,   66,
   67,   32,   68,  336,  215,   49,   50,   51,   52,   53,
  151,  152,
};
final static short yysindex[] = {                       -35,
    0, 1010,    0,  558,  -37,  -34,    4,  -39,    1,  664,
    0,    0,  552,  -49,  716,    0,    0,    0,    0,    0,
    0,    0,  -47,   31,  -25,   43,    0,    0,    0,    0,
    0,    0,  745,  768,  -41, -126,   95,    0,    0,    0,
    0, -115,  585,    0,    0,    0,  119,    2,   96,   57,
    0,    0,    0,  123,  164,   17,  -24,  -19,  617,   21,
  302,  174,  925,    0,    0,    0,    0,  -12,    0,    0,
    0,    0,    0, -215,    0,  195,  240,    0,    0,  258,
    0,  265,    0,  600,   53,    0,  778,    0,    0,  112,
    0,   62,    0,   97,  -32,  106,   96,   96,   96,   69,
   96,   96,  319,  322,   33,   50,  331,  -21,  344,  149,
  367,  390,  154,  378,  399,  183,  416,    0,  -36,    0,
  102,    0,   11,    0,  212,    0,  224,    0,  611,   60,
  448,  -87,  739,    0,    0,    0,  386,  954,    0,    0,
    0,    0,  389,  465,    0,    0,    0,    0,    0,    0,
   98,    0,  705,  705,  482,  705,   57,   57,   69,    0,
    0,    0,    0,    0,  467,    0,  468,    0,  466,   72,
   93,   72,    0,  470,   72,    0,   72,  471,  407,  -83,
    0,    0,  473,    0,    0,  639,  492,    0,    0,  585,
   76,  245,  617,  925,    0,  260,    0,    0,    0,    0,
    0,  412,    0,  413,    0,  267,   99,    0, 1043,    0,
  906,  101,  109,  683,  170,  176,    0,    0,   72,    0,
  491,   72,    0,   72,  506,    0,   72, 1010,  439, 1010,
    0,  504,   79,  505,    5,  507,  362,    0,  524,  862,
  527,    0,    0,  -87,    0,    0,  923,    0,    0,  705,
  509,  705,  510,  589,  512,  180,  705,  513,  705,  518,
    0,   72,    0,    0,   72,    0, 1010,  459, 1010,  460,
    0,    0,  528,    0,  634,    0,  537,  -74,  474,  290,
  324,    0,  337,    0,  338,    0,  543,  341,    0,  694,
  548,  351,    0,  352,    0,    0,  572,    0,    0,  569,
  514,  570,    0,  654,    0,  494,    0,  590,  592,  575,
  576,    0,  578,  579,  394,    0,  601,  602,   72,    0,
  604,    0, 1043,  185,  977,  605,  290,    0,    0,    0,
    0,  606,    0,    0,    0,  387,    0,  889, -108,  607,
    0,  544,  -70,    0,  627,    0,  645,  565, 1043,  428,
    0,  633,    0,  636,  290,  211, 1033,  637,    0,    0,
  652,  -90,  638,  573,    0,  442, 1043,  441,    0,  443,
  584, 1033,  649,  655, 1010,  588,    0,    0,  591,  452,
    0,  660,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  417,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,    0,    0,    0,    0,    0,  463,  463,    0,  668,
  804,  823,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  376,    0,  502,  410,    0,    0,  472,
    0,  525,    0,    0,    0,    0,  727,    0,    0,   65,
    0,    0,    0,    0,    0,    0,    0,    0,  -20,  -17,
    0,    0,    0,    0,    0,    0,    0,  463,    0,   82,
    0,  104,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  445,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1000,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  152,  189,    9,    0,
    0,    0,    0,    0,  220,    0,  243,    0,    0,    0,
    0,    0,    0,    0,  463,    0,    0,    0,    0,    0,
    0,    0,  843,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1000,    0,    0,    0,    0,    0,  879,
    0,    0,    0,    0,    0,    0,    0,    0,  608,    0,
    0,    0,    0,    0,  161,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  216,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  610,    0,    0,  463,    0,  -68,    0,    0,    0,
    0,    0,  279,    0,    0,    0,  312,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  316,    0,    0,    0,    0,    0,  234,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  463,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  346,    0,    0,    0,    0,    0,  216,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   56,  114,  -14,   -2,    0,    0,    0,    0,  586,
   74,    0,  -79,    0,  596,  595,  597,  614,  -27,  -40,
 -195,    0, -200,  496,   -6,  541,   29,    3, -150,  422,
    0,  625,    0,   16,   35,   41,   67,   73,   45,    0,
    0,  -50,    0,    0,  958,  717,  393,  411,   -8,    0,
    0,  546,
};
final static int YYTABLESIZE=1321;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   58,   18,   95,   42,  137,   43,   36,   64,  155,   75,
   42,   81,   18,  247,  349,  108,  109,   89,  117,  169,
  148,  114,  182,  149,  225,   45,   46,   44,   69,  270,
   18,   18,  367,  162,  145,  194,  170,   90,  148,  230,
   59,  149,  124,   56,   97,  275,   98,   70,  230,  147,
  186,  100,  125,   71,  321,   42,  123,  107,  202,   34,
   64,   45,   46,   44,  118,   64,  299,  147,  301,  135,
   45,   46,   44,  165,  162,  162,  162,  144,  162,   72,
  162,  136,  187,  150,   18,   73,  115,    2,   87,   83,
  167,  164,  162,  162,  162,  162,   82,   84,  101,  191,
   85,  159,  149,  102,   42,  200,   42,  122,  166,   91,
  204,   97,  183,   98,  297,   16,  236,   16,   64,  273,
   42,  158,   72,  135,  343,   72,  144,  338,   79,   64,
   80,  144,  192,  221,   92,  136,  171,  272,  208,   72,
   42,  207,   93,   42,   66,  233,   16,   79,   69,  235,
  222,  156,  239,  357,   97,  145,   98,  130,  162,   96,
  279,   66,  158,  103,  158,  185,  158,   70,  297,   60,
  147,  372,    6,   71,  379,  130,    7,  229,  130,   61,
  158,  158,  158,  158,    9,   10,  306,   62,  157,  118,
   13,   64,  156,  237,  156,  130,  156,  123,  150,   72,
   79,   67,   64,   64,  104,   73,  246,   74,  249,   80,
  156,  156,  156,  156,   64,   37,   38,   57,   67,  179,
  181,    1,   37,   38,  324,   18,  184,   18,  154,  157,
   35,  157,  120,  157,   39,   40,   41,   64,  122,  308,
  148,   64,  178,  149,  249,  341,  158,  157,  157,  157,
  157,   11,   12,  126,   11,   12,   11,   12,  348,  350,
   54,  123,   55,  353,   18,  154,   18,   37,   38,  147,
   39,   40,   41,  105,   69,  106,  156,  364,  107,   39,
   40,   41,  368,  127,  185,  238,  345,   97,   35,   98,
  162,   69,  376,  162,  162,  162,  162,  162,  162,  162,
  162,   36,  162,  162,  162,  162,  162,  162,  162,  162,
  162,  162,  162,  157,  361,  131,   37,   38,  148,   38,
  246,  118,   18,  128,  118,   10,  118,  118,  118,  118,
  118,  118,   37,   38,   42,  249,  118,  118,  118,  118,
  118,  118,  118,  118,  179,  139,  246,   11,   12,   45,
   46,   44,   37,   38,  249,  148,   38,  153,   60,  119,
  118,    6,  250,  251,  246,    7,  156,  178,   61,  249,
  252,  253,   18,    9,   10,   83,   62,  162,  158,   13,
  163,  158,  158,  158,  158,  158,  158,  158,  158,  168,
  158,  158,  158,  158,  158,  158,  158,  158,  158,  158,
  158,  172,  277,  107,   97,  173,   98,  174,  156,   78,
  176,  156,  156,  156,  156,  156,  156,  156,  156,   83,
  156,  156,  156,  156,  156,  156,  156,  156,  156,  156,
  156,  257,  258,  175,   83,  177,   62,  259,  260,  178,
  131,  290,  291,  179,   81,  157,  339,  340,  157,  157,
  157,  157,  157,  157,  157,  157,  180,  157,  157,  157,
  157,  157,  157,  157,  157,  157,  157,  157,   78,  188,
  139,   20,  362,  363,  111,   20,  179,  144,  144,  179,
   74,  179,  179,  179,  179,  179,  179,  193,   81,  157,
  158,  179,  179,  179,  179,  179,  179,  179,  179,  178,
   83,   76,  178,   81,  178,  178,  178,  178,  178,  178,
  201,  160,  161,  205,  178,  178,  178,  178,  178,  178,
  178,  178,  206,  219,   16,  217,  218,  224,  227,  228,
   20,  231,  234,  241,   78,  107,  242,  243,  107,  244,
  107,  107,  107,  107,  107,  107,   37,   38,  262,  265,
  107,  107,  107,  107,  107,  107,  107,  107,   39,   40,
   41,  269,  271,  274,  278,  276,  280,  284,  286,   81,
  289,  293,  131,   62,   62,  131,  295,  131,  131,  131,
  131,  131,  131,  300,  302,   62,  303,  131,  131,  131,
  131,  131,  131,  131,  131,  305,   20,  309,  307,  310,
  311,  312,  139,  313,  209,  139,  316,  139,  139,  139,
  139,  139,  139,  317,  318,  319,  325,  139,  139,  139,
  139,  139,  139,  139,  139,   94,   76,  320,  322,   42,
  326,  327,   83,  328,  329,   83,  330,  331,  321,   83,
   83,   83,  110,  110,   45,   46,   44,   83,   83,   16,
  190,   83,   83,   83,   83,   42,  332,  116,  347,  333,
  334,   42,  337,  344,  346,  351,   78,  354,  352,   78,
   45,   46,   44,   78,   78,   78,   45,   46,   44,  232,
   33,   78,   78,   42,  355,   78,   78,   78,   78,  356,
  358,  359,  366,  110,  360,  365,  369,  370,   45,   46,
   44,   81,  371,  373,   81,  374,  375,  377,   81,   81,
   81,  209,  380,  378,  382,  381,   81,   81,  383,   73,
   81,   81,   81,   81,  134,  111,    4,  197,   20,  198,
   53,   20,   57,  203,  240,   20,   20,   20,   60,  281,
  335,    6,  214,   20,   20,    7,  199,   20,   20,   20,
   20,  189,  245,    9,   10,  220,  209,  223,   76,   13,
  110,   76,  226,    0,   99,   76,   76,   76,    0,    0,
    0,    0,    0,   76,   76,    0,  323,   76,   76,   76,
   76,   16,    0,    0,   16,    0,   63,    0,   16,   16,
   16,    0,    0,    0,    0,    0,   16,   16,    0,    0,
   16,   16,   16,   16,  261,  209,    0,  263,   60,  264,
    0,    6,  266,    0,    5,    7,  209,    6,    0,    0,
    0,    7,    8,    9,   10,    0,    0,  209,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    0,    0,    0,
   78,   37,   38,    0,    0,   60,    0,  296,    6,    0,
  110,  287,    7,   39,   40,   41,    5,    0,    0,  129,
    9,   10,    0,    7,    8,  130,   13,   37,   38,   86,
    0,  131,  132,   37,   38,   11,   12,  133,   14,   39,
   40,   41,    0,    0,    0,   39,   40,   41,    0,    0,
   60,    0,   88,    6,  304,   37,   38,    7,    0,    0,
    0,    0,  146,    0,  110,    9,   10,   39,   40,   41,
   60,   13,    0,    6,    0,  254,  255,    7,    0,  130,
   60,    0,    0,    6,    0,    9,   10,    7,   99,    0,
   61,   13,    0,    0,    0,    9,   10,    0,   62,   60,
    0,   13,    6,    0,  254,  255,    7,  103,    0,    0,
   60,    0,    0,    6,    9,   10,  314,    7,    0,    0,
   13,   60,    0,    0,    6,    9,   10,  100,    7,    0,
    0,   13,    5,    0,    0,    6,    9,   10,    0,    7,
    8,    0,   13,    0,    0,    0,  184,    9,   10,    0,
    0,   11,   12,   13,   14,   60,    0,    0,  129,    0,
    0,    5,    7,  145,    6,    0,    0,    0,    7,    8,
  131,  132,    0,  282,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,    5,    0,    0,    6,    0,    0,
  248,    7,    8,    0,    5,    0,    0,    6,    0,    9,
   10,    7,    8,   11,   12,   13,   14,  282,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    0,    0,    0,
   99,    0,    0,   99,    0,    0,    0,   99,    0,   99,
   99,    0,    0,    0,    0,   99,   99,   99,   99,  103,
    0,   99,  103,    0,    0,    0,  103,    0,  103,  103,
    0,    0,    0,    0,  103,  103,  103,  103,    0,  100,
  103,    0,  100,    0,    0,    0,  100,    0,  100,  100,
  212,  213,    0,  216,  100,  100,  100,  100,   60,    0,
  100,    6,    0,    0,    0,    7,    0,  130,   61,    0,
    0,    0,    0,    9,   10,  145,   62,    0,  145,   13,
  141,  141,  145,    0,    0,   60,    0,    0,    6,    0,
  145,  145,    7,    0,  130,    0,  145,    0,    0,    0,
    9,   10,   60,    0,    0,    6,   13,    0,    0,    7,
    0,  256,    0,    0,    0,    0,    0,    9,   10,   60,
    0,   60,    6,   13,    6,    0,    7,    0,    7,    0,
    0,   61,    0,    0,    9,   10,    9,   10,    0,   62,
   13,    0,   13,    0,    0,    0,    0,  283,    0,  285,
    5,  288,    0,  129,  292,    0,  294,    7,    8,  130,
    0,    0,    0,    0,    0,  131,  132,    0,    0,   11,
   12,  133,   14,    5,    0,    0,    6,    0,    0,    0,
    7,    8,  130,    0,    0,    0,    0,  315,    9,   10,
    0,    0,   11,   12,   13,   14,   34,    0,    0,   34,
    0,  256,    0,   34,   34,   34,    5,    0,    0,    6,
    0,   34,   34,    7,    8,   34,   34,   34,   34,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,   60,
    0,    0,    6,    0,    0,    0,    7,    0,  130,   60,
    0,    0,    6,    0,    9,   10,    7,    0,    0,    0,
   13,    0,    0,    0,    9,   10,    0,    0,    0,    0,
   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   43,   45,   84,   40,   44,   10,   41,   59,
   45,   59,   15,  209,  123,   40,   41,   59,   59,   41,
   41,   41,   59,   41,  175,   60,   61,   62,   13,  230,
   33,   34,  123,    0,   85,  123,   58,   35,   59,  123,
   40,   59,  258,   40,   43,   41,   45,   13,  123,   41,
   40,   49,  268,   13,  125,   45,  125,   41,  138,    4,
   63,   60,   61,   62,    0,  125,  267,   59,  269,   84,
   60,   61,   62,   41,   41,   42,   43,   84,   45,   13,
   47,   84,  123,   92,   87,   13,   58,  123,   33,   59,
   41,   59,   59,   60,   61,   62,   23,  123,   42,   40,
   58,   99,   41,   47,   45,  133,   45,   63,   59,   36,
  138,   43,  119,   45,  265,    2,   41,    4,  121,   41,
   45,    0,   41,  138,  325,   44,  133,  323,   15,  132,
  257,  138,  130,   41,   40,  138,  108,   59,   41,   58,
   45,   44,  258,   45,   41,  186,   33,   34,  133,  190,
   58,    0,  193,  349,   43,  206,   45,  266,  125,   41,
  240,   58,   41,   41,   43,  121,   45,  133,  319,  257,
   59,  367,  260,  133,  375,  266,  264,  261,  266,  267,
   59,   60,   61,   62,  272,  273,  261,  275,    0,  125,
  278,  194,   41,  191,   43,  266,   45,  266,  207,  133,
   87,   41,  262,  263,   41,  133,  209,  257,  211,  257,
   59,   60,   61,   62,  274,  257,  258,  257,   58,    0,
  257,  257,  257,  258,  304,  228,  125,  230,  261,   41,
  268,   43,   59,   45,  269,  270,  271,  240,  194,  280,
  261,  244,    0,  261,  247,  325,  125,   59,   60,   61,
   62,  276,  277,   59,  276,  277,  276,  277,  338,  339,
  257,  274,  259,  343,  267,  261,  269,  257,  258,  261,
  269,  270,  271,  257,   41,  259,  125,  357,    0,  269,
  270,  271,  362,   44,  240,   41,  327,   43,  268,   45,
  257,   58,  372,  260,  261,  262,  263,  264,  265,  266,
  267,   44,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  125,  355,    0,  257,  258,  257,  258,
  323,  257,  325,   59,  260,  273,  262,  263,  264,  265,
  266,  267,  257,  258,   45,  338,  272,  273,  274,  275,
  276,  277,  278,  279,  125,    0,  349,  276,  277,   60,
   61,   62,  257,  258,  357,  257,  258,  261,  257,   58,
   59,  260,  262,  263,  367,  264,  261,  125,  267,  372,
  262,  263,  375,  272,  273,    0,  275,   59,  257,  278,
   59,  260,  261,  262,  263,  264,  265,  266,  267,   59,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,   58,   41,  125,   43,  257,   45,   41,  257,    0,
  257,  260,  261,  262,  263,  264,  265,  266,  267,   44,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  262,  263,   44,   59,   58,  125,  262,  263,   41,
  125,  262,  263,  261,    0,  257,  262,  263,  260,  261,
  262,  263,  264,  265,  266,  267,   41,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   59,  258,
  125,    0,  262,  263,   58,   59,  257,  262,  263,  260,
  257,  262,  263,  264,  265,  266,  267,   40,   44,   97,
   98,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,    0,  260,   59,  262,  263,  264,  265,  266,  267,
  125,  101,  102,  125,  272,  273,  274,  275,  276,  277,
  278,  279,   58,   58,    0,   59,   59,   58,   58,  123,
   59,   59,   41,  274,  125,  257,  125,  125,  260,  273,
  262,  263,  264,  265,  266,  267,  257,  258,   58,   44,
  272,  273,  274,  275,  276,  277,  278,  279,  269,  270,
  271,  123,   59,   59,   41,   59,   40,   59,   59,  125,
   59,   59,  257,  262,  263,  260,   59,  262,  263,  264,
  265,  266,  267,  125,  125,  274,   59,  272,  273,  274,
  275,  276,  277,  278,  279,   59,  125,  274,  125,  263,
  263,   59,  257,  263,  123,  260,   59,  262,  263,  264,
  265,  266,  267,  263,  263,   44,  123,  272,  273,  274,
  275,  276,  277,  278,  279,   41,  125,   59,   59,   45,
   41,   40,  257,   59,   59,  260,   59,   59,  125,  264,
  265,  266,   57,   58,   60,   61,   62,  272,  273,  125,
   40,  276,  277,  278,  279,   45,  263,   41,  272,   59,
   59,   45,   59,   59,   59,   59,  257,   41,  125,  260,
   60,   61,   62,  264,  265,  266,   60,   61,   62,   41,
  123,  272,  273,   45,   40,  276,  277,  278,  279,  125,
  263,   59,   41,  108,   59,   59,   59,  125,   60,   61,
   62,  257,  261,  263,  260,  263,  123,   59,  264,  265,
  266,  123,  125,   59,  263,  125,  272,  273,   59,  257,
  276,  277,  278,  279,  125,   58,    0,  133,  257,  133,
  123,  260,  123,  138,  194,  264,  265,  266,  257,  244,
  319,  260,  261,  272,  273,  264,  133,  276,  277,  278,
  279,  127,  207,  272,  273,  170,  123,  172,  257,  278,
  175,  260,  177,   -1,   48,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,  123,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,  123,   -1,  264,  265,
  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  219,  123,   -1,  222,  257,  224,
   -1,  260,  227,   -1,  257,  264,  123,  260,   -1,   -1,
   -1,  264,  265,  272,  273,   -1,   -1,  123,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
  125,  257,  258,   -1,   -1,  257,   -1,  262,  260,   -1,
  265,  263,  264,  269,  270,  271,  257,   -1,   -1,  260,
  272,  273,   -1,  264,  265,  266,  278,  257,  258,  125,
   -1,  272,  273,  257,  258,  276,  277,  278,  279,  269,
  270,  271,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,
  257,   -1,  125,  260,  261,  257,  258,  264,   -1,   -1,
   -1,   -1,  125,   -1,  319,  272,  273,  269,  270,  271,
  257,  278,   -1,  260,   -1,  262,  263,  264,   -1,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  125,   -1,
  267,  278,   -1,   -1,   -1,  272,  273,   -1,  275,  257,
   -1,  278,  260,   -1,  262,  263,  264,  125,   -1,   -1,
  257,   -1,   -1,  260,  272,  273,  263,  264,   -1,   -1,
  278,  257,   -1,   -1,  260,  272,  273,  125,  264,   -1,
   -1,  278,  257,   -1,   -1,  260,  272,  273,   -1,  264,
  265,   -1,  278,   -1,   -1,   -1,  125,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
   -1,  257,  264,  125,  260,   -1,   -1,   -1,  264,  265,
  272,  273,   -1,  125,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,
  125,  264,  265,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,  265,  276,  277,  278,  279,  125,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,  257,
  278,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
  153,  154,   -1,  156,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,
  262,  263,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,
  272,  273,  264,   -1,  266,   -1,  278,   -1,   -1,   -1,
  272,  273,  257,   -1,   -1,  260,  278,   -1,   -1,  264,
   -1,  214,   -1,   -1,   -1,   -1,   -1,  272,  273,  257,
   -1,  257,  260,  278,  260,   -1,  264,   -1,  264,   -1,
   -1,  267,   -1,   -1,  272,  273,  272,  273,   -1,  275,
  278,   -1,  278,   -1,   -1,   -1,   -1,  250,   -1,  252,
  257,  254,   -1,  260,  257,   -1,  259,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,  290,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,  304,   -1,  264,  265,  266,  257,   -1,   -1,  260,
   -1,  272,  273,  264,  265,  276,  277,  278,  279,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  257,
   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,
  278,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,
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
"funcion_con_return : encabezado_funcion '{' '}'",
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
"condicion : comparador expresion",
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

//#line 352 ".\gramatica.y"

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
//#line 856 "Parser.java"
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
case 23:
//#line 60 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias en la funcion"); }
break;
case 36:
//#line 82 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 37:
//#line 83 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 44:
//#line 99 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 47:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 48:
//#line 109 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 53:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 54:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 55:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 56:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 57:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 58:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 59:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 60:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 62:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 63:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 64:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 65:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 68:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 73:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 74:
//#line 152 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 75:
//#line 156 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 76:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 77:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 81:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 82:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 83:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 97:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 98:
//#line 194 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 99:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 101:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 102:
//#line 201 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 103:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 106:
//#line 211 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 107:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 108:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 109:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 110:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 116:
//#line 233 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 117:
//#line 234 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 118:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 119:
//#line 239 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 120:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 121:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 122:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 125:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 126:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 127:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 129:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 130:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 131:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 132:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 133:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 135:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 136:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 137:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 138:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 140:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 143:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 144:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 148:
//#line 283 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 149:
//#line 284 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 171:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 331 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 174:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 175:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 176:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 177:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 178:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 179:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 181:
//#line 343 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1353 "Parser.java"
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
