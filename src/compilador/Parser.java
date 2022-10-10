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
   18,   16,   22,   22,   17,   17,   24,   24,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   13,   13,
   13,   13,   13,   27,   27,   27,   30,   30,   29,   29,
   31,   29,    9,    9,    9,   32,   32,   33,   33,   33,
   33,    5,    5,    5,    5,    5,    5,    5,    5,    5,
    5,   39,   39,   39,   40,   40,   40,   40,   40,   41,
   41,   38,   38,   42,   42,   42,   42,   42,   25,   43,
   43,   26,   26,   34,   34,   34,   37,   37,   44,   37,
   23,   23,   35,   35,   35,   35,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   45,   45,
   45,   45,   21,   21,   20,   20,   47,   20,   46,   46,
   46,   46,   46,   46,   28,   28,   28,   48,   48,   48,
   49,   49,   49,   51,   51,   52,   52,   53,   53,   36,
   36,   36,   36,   36,   36,   36,   36,   36,   50,   50,
   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    1,    2,    1,    1,    1,    2,    1,    2,
    1,    2,    1,    2,    4,    5,   10,   13,   13,   16,
   19,    9,    1,    2,    7,    9,    1,    4,    6,    7,
    5,    5,    5,    5,    6,    6,    6,    6,    5,    4,
    3,    3,    4,    1,    3,    5,    1,    3,    2,    1,
    0,    2,    3,    2,    2,    1,    3,    3,    2,    2,
    1,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    1,    1,    2,    4,    1,    3,    3,    2,
    1,    1,    3,    7,    6,    6,    6,    6,    1,    1,
    3,    1,    2,    4,    3,    3,    9,    8,    0,   17,
    1,    2,    8,   10,    7,    7,    7,    7,    7,    7,
    9,    9,    9,    9,    9,    9,    9,    8,    1,    3,
    2,    2,    1,    2,    3,    2,    0,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    3,    4,    1,    3,    1,    1,    5,
    5,    4,    4,    4,    4,    4,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  182,  181,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   82,   84,   86,   88,
   90,  102,    0,    0,    0,    0,    0,  179,    0,    0,
    0,    0,    0,    0,  160,  162,  163,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   92,  110,   93,
   94,    0,   83,   85,   87,   89,   91,    0,   75,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  115,    0,   19,    0,  180,    0,    0,    0,
  149,  150,  151,    0,    0,  152,  153,  154,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   95,    0,  100,
    0,  112,    0,   80,    0,   73,    0,   15,    0,    0,
    0,    0,    0,   25,   26,    0,    0,   23,   27,   29,
   31,    0,    0,  103,    1,  114,  168,  164,  169,    0,
  166,    0,    0,    0,    0,    0,    0,    0,    0,  158,
  159,  175,  173,  176,    0,  174,    0,  172,    0,    0,
    0,    0,   69,    0,    0,   72,    0,    0,    0,    0,
  109,   99,    0,  111,  113,    0,    0,   78,   77,    0,
    0,    0,    0,    0,   47,    0,   28,   30,   32,   34,
   21,    0,   24,    0,   35,    0,    0,  165,    0,    0,
    0,    0,    0,    0,    0,    0,  171,  170,    0,   54,
    0,    0,   53,    0,    0,   52,    0,    0,    0,    0,
   96,    0,    0,    0,    0,    0,    0,   61,    0,    0,
    0,   22,   36,    0,  167,  143,    0,  141,  144,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,    0,   58,   57,    0,   56,    0,    0,    0,    0,
  106,  108,    0,  107,    0,   63,    0,    0,    0,    0,
    0,  140,    0,  127,    0,  125,    0,    0,  130,    0,
    0,    0,  128,    0,  126,   50,    0,   66,  122,    0,
    0,    0,  104,    0,   59,    0,   48,    0,    0,    0,
    0,  138,    0,    0,    0,  123,    0,    0,    0,  119,
    0,  118,    0,    0,    0,    0,    0,  133,  131,  135,
  136,    0,  134,  132,   68,    0,  117,    0,    0,    0,
   43,    0,    0,   45,    0,  124,    0,    0,    0,    0,
    0,    0,   44,    0,    0,    0,    0,    0,    0,   42,
   46,    0,    0,    0,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   38,   39,
    0,    0,    0,    0,  120,    0,    0,   40,    0,    0,
    0,   41,
};
final static short yydgoto[] = {                          3,
    4,   15,  267,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  195,  137,  138,  139,  140,  141,  142,   41,
  211,  342,  268,  196,   26,  121,  111,   42,  112,  298,
  113,   70,   71,   27,   28,   29,   30,   31,   59,   60,
   61,   32,   62,  336,  215,   99,   43,   44,   45,   46,
   47,  150,  151,
};
final static short yysindex[] = {                       -63,
    0,  972,    0,  572,  -25,    1,  -34,  -20,   81,  632,
    0,    0,  669,  -47,  484,    0,    0,    0,    0,    0,
    0,    0,  -41,   -1,  -70,   76,    0,    0,    0,    0,
    0,    0,  717,  727,   49,  -95,  156,    0, -103,   16,
  125,  -38,   88,  114,    0,    0,    0,  177,  180,    4,
  -37,  -24,   29,  -12,   83,  168,  995,    0,    0,    0,
    0,  -44,    0,    0,    0,    0,    0, -206,    0,  185,
  190,    0,    0,  204,    0,  207,    0,  911,    3,    0,
  752,    0,    0,  161,    0,   45,    0,   24,   -7,   35,
    0,    0,    0,   67,   67,    0,    0,    0,   67,   67,
   67,   67,  213,  240,   13,   18,  242,  -30,  246,   63,
  276,  296,   99,  268,  307,  100,  322,    0,  -33,    0,
  833,    0,   11,    0,  107,    0,  113,    0,   26,   40,
  344,  612,  850,    0,    0,  256,  911,    0,    0,    0,
    0,  269,  350,    0,    0,    0,    0,    0,    0,   51,
    0,  602,  602,  454,  602,  114,  114,  248,  248,    0,
    0,    0,    0,    0,  361,    0,  377,    0,  397,   58,
   52,   58,    0,  402,   58,    0,   58,  409,  345,  -87,
    0,    0,  405,    0,    0,   55,  428,    0,    0,   16,
   57,   46,   29,  995,    0,  196,    0,    0,    0,    0,
    0,  348,    0,  353,    0,  202,   71,    0, 1012,    0,
  882,   69,   80,  652,   91,  115,    0,    0,   58,    0,
  429,   58,    0,   58,  455,    0,   58,  972,  379,  972,
    0,  441,   23,  445,   -6,  446,  319,    0,  467,  700,
  477,    0,    0,  612,    0,    0,  901,    0,    0,  602,
  470,  602,  471,  671,  475,  117,  602,  476,  602,  478,
    0,   58,    0,    0,   58,    0,  972,  411,  972,  413,
    0,    0,  481,    0,  542,    0,  482,  -79,  418,   67,
  278,    0,  287,    0,  290,    0,  504,  301,    0,  690,
  506,  304,    0,  305,    0,    0,  526,    0,    0,  512,
  447,  514,    0,  199,    0,  451,    0,  535,  539,  527,
  528,    0,  529,  530,  337,    0,  543,  551,   58,    0,
  552,    0, 1012,  148,  939,  553,   67,    0,    0,    0,
    0,  563,    0,    0,    0,  349,    0,  872, -102,  564,
    0,  499, -109,    0,  584,    0,  586,  502, 1012,  365,
  369,  571,    0,  575,   67,  373, -135,  578,  591,    0,
    0,  603,  -76,  521,    0,   67,  395, 1012,  396,  398,
  619,  541, -135,  608,  615,  401,  972,  550,    0,    0,
  565,  561,  426, 1012,    0,  631, -135,    0,  566,  430,
  633,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  314,   98,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  126,    0,    0,   98,
    0,    0,    0,  152,    0,    0,    0,    0,    0,    0,
  437,  437,   98,  638,  775,  794,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,  532,
  444,    0,    0,  474,    0,  562,    0,    0,    0,    0,
  699,    0,    0,  175,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -32,    0,
    0,    0,    0,    0,    0,    0,    0,  437,    0,   60,
    0,   82,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   98,    0,  507,    0,    0,    0,   98,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  962,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  219,  249,  -26,  -11,    0,
    0,    0,    0,    0,  282,    0,  318,    0,    0,    0,
    0,    0,    0,    0,  437,    0,    0,    0,    0,    0,
    0,    0,  813,    0,    0,   98,    0,    0,    0,   98,
    0,    0,   98,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  962,    0,    0,    0,    0,    0,  855,
    0,    0,    0,    0,    0,    0,    0,    0,  577,    0,
    0,    0,    0,    0,   87,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -85,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  171,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  579,    0,    0,  437,    0,  -96,    0,    0,    0,
    0,    0,  341,    0,    0,    0,  -83,    0,    0,   98,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  376,    0,    0,    0,    0,    0,   95,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  437,    0,
    0,    0,    0,    0,    0,    0,   98,    0,    0,    0,
    0,  406,    0,    0,    0,    0,    0,  171,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   98,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,   34,  643,  -28,   -2,    0,    0,    0,    0,  646,
  110,    0,  -68,    0,  568,  570,  573,  574,  -22,  -39,
 -184,    0, -162,  468,  -13,  519,   -9,   14, -138,  400,
    0,  601,    0,   50,   68,   70,   75,   84,  -18,    0,
    0,   -4,    0,    0,  943,  686,    0,  351,  356,   -8,
    0,    0,  523,
};
final static int YYTABLESIZE=1290;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  108,  109,   94,   50,   95,   58,  146,  136,
  169,   69,   18,  117,  145,  321,  114,   75,   36,   52,
  349,   97,   98,   96,  247,  182,  146,  170,  121,  148,
   18,   18,  145,  154,  275,  230,  225,   34,  122,   62,
   40,   60,  115,  230,  107,   39,  368,  148,   84,  134,
  186,  124,   78,  165,   58,   39,   88,   77,  167,    2,
   39,  125,   63,  273,  143,  190,   81,  270,  202,  116,
   39,  164,   81,   39,  144,  135,  166,  149,   18,  191,
   64,  272,   65,  187,   39,  148,  238,   66,   94,   39,
   95,  208,  221,   39,  207,  232,   67,  236,  171,   39,
   70,   39,  185,   70,  299,  183,  301,   83,  134,  222,
  200,   39,  158,  159,  204,   39,   81,   70,   58,  143,
   53,   54,   64,  143,    6,  161,  297,   65,    7,   58,
  130,   81,   76,   79,  135,   67,    9,   10,  338,   64,
  119,  118,   13,  192,   65,   85,  233,   97,   98,   96,
  235,  157,   67,  239,   87,  101,  130,  147,  147,  147,
  102,   74,  343,  130,  357,   90,  161,  161,  161,  121,
  161,  279,  161,  229,  116,  122,   62,   62,   60,   60,
  297,  306,   63,  373,  161,  161,  161,  161,   62,  130,
   60,   58,  157,    1,  157,   86,  157,   81,  149,  387,
   64,  144,   65,   94,  237,   95,  246,   66,  249,   68,
  157,  157,  157,  157,  382,   74,   67,  103,  155,  146,
  104,  185,   48,  181,   49,   18,  120,   18,  146,  123,
   91,   92,   93,  127,  145,  324,   51,   58,   11,   12,
  308,   58,   35,  126,  249,   11,   12,   36,  156,  148,
  161,   11,   12,  153,  153,   35,  341,   37,   38,  155,
  105,  155,  106,  155,   18,  128,   18,   37,   38,  348,
  350,  162,   37,   38,  353,   10,  157,  155,  155,  155,
  155,  178,   37,   38,  152,   37,   38,  345,  364,  156,
   94,  156,   95,  156,  369,  155,   37,   38,  163,  116,
  168,  147,   38,  172,  378,   37,   38,  156,  156,  156,
  156,   37,   38,   37,   38,  362,  174,  177,  389,  173,
  246,  323,   18,   37,   38,  177,  371,  147,   38,   81,
  250,  251,   81,   11,   12,  249,   81,   81,   81,  175,
  105,  252,  253,  155,   81,   81,  246,  178,   81,   81,
   81,   81,  257,  258,  249,  176,   91,   92,   93,  277,
  179,   94,  180,   95,  188,  246,  147,  147,  147,   68,
  249,  109,   20,  156,   18,  129,  259,  260,  290,  291,
  201,  246,  161,  193,  249,  161,  161,  161,  161,  161,
  161,  161,  161,  205,  161,  161,  161,  161,  161,  161,
  161,  161,  161,  161,  161,  137,  178,  206,  157,  339,
  340,  157,  157,  157,  157,  157,  157,  157,  157,  217,
  157,  157,  157,  157,  157,  157,  157,  157,  157,  157,
  157,  116,  142,  142,  116,  218,  116,  116,  116,  116,
  116,  116,  177,   76,  156,  157,  116,  116,  116,  116,
  116,  116,  116,  116,  219,   54,  160,  161,    6,  224,
  254,  255,    7,  231,  130,  105,  227,  228,  234,  241,
    9,   10,  242,   20,  244,  155,   13,  243,  155,  155,
  155,  155,  155,  155,  155,  155,  262,  155,  155,  155,
  155,  155,  155,  155,  155,  155,  155,  155,  265,  271,
  129,  269,   76,  274,  276,  156,   79,  278,  156,  156,
  156,  156,  156,  156,  156,  156,  280,  156,  156,  156,
  156,  156,  156,  156,  156,  156,  156,  156,  284,  286,
  137,   74,   20,  289,  293,  300,  295,  302,  178,  303,
  305,  178,  307,  178,  178,  178,  178,  178,  178,  310,
   79,  309,  311,  178,  178,  178,  178,  178,  178,  178,
  178,   16,  312,  313,  316,   79,  317,  318,   76,  319,
  320,  321,  322,  325,  177,  326,  209,  177,  327,  177,
  177,  177,  177,  177,  177,  328,  329,  330,  331,  177,
  177,  177,  177,  177,  177,  177,  177,  105,   20,  332,
  105,  333,  105,  105,  105,  105,  105,  105,   72,  334,
  337,  344,  105,  105,  105,  105,  105,  105,  105,  105,
  347,  346,  351,  352,  354,  355,  356,  358,  359,  360,
  366,   79,  129,  361,  363,  129,  365,  129,  129,  129,
  129,  129,  129,  367,   16,  370,   16,  129,  129,  129,
  129,  129,  129,  129,  129,  372,   74,   73,  374,  376,
  375,  381,  137,  377,  209,  137,  379,  137,  137,  137,
  137,  137,  137,  380,  383,   16,   73,  137,  137,  137,
  137,  137,  137,  137,  137,  385,   16,  384,  386,  388,
  390,  392,  391,   71,   33,  109,  110,  110,    4,   51,
   76,   55,  197,   76,  203,  198,  199,   76,   76,   76,
   54,  281,  240,    6,  214,   76,   76,    7,  335,   76,
   76,   76,   76,   73,  209,    9,   10,  189,  100,  245,
   20,   13,    0,   20,  194,    0,    0,   20,   20,   20,
    5,    0,    0,    6,    0,   20,   20,    7,    8,   20,
   20,   20,   20,  110,   57,    9,   10,    0,    0,   11,
   12,   13,   14,   79,    0,    0,   79,    0,    0,    0,
   79,   79,   79,    0,  209,    0,    0,    0,   79,   79,
    0,    0,   79,   79,   79,   79,    0,    0,   74,    0,
    0,   74,    0,  209,    0,   74,   74,   74,   54,    0,
    0,    6,  304,   74,   74,    7,    0,   74,   74,   74,
   74,    0,  209,    9,   10,  220,    0,  223,   16,   13,
  110,   16,  226,    0,  184,   16,   16,   16,    5,    0,
    0,    6,    0,   16,   16,    7,    8,   16,   16,   16,
   16,   80,    0,    9,   10,    0,    0,   11,   12,   13,
   14,   82,    0,    0,    0,    0,    0,    0,   54,    0,
    0,    6,    0,    0,  261,    7,    0,  263,   54,  264,
    0,    6,  266,    9,   10,    7,  145,  130,   55,   13,
    0,    0,    0,    9,   10,    0,   56,    0,   54,   13,
    0,    6,    0,    0,    0,    7,    0,    0,   55,   97,
    0,    0,    0,    9,   10,    0,   56,  296,   54,   13,
  110,    6,    0,  254,  255,    7,    0,    0,  101,    0,
    0,    0,    0,    9,   10,   54,    0,   54,    6,   13,
    6,    0,    7,  287,    7,    0,    0,   98,    0,    0,
    9,   10,    9,   10,    0,    0,   54,    0,   13,    6,
    0,    0,  314,    7,    0,    0,   54,  184,    0,    6,
    0,    9,   10,    7,  110,  130,   55,   13,    0,    0,
    0,    9,   10,    5,   56,    0,    6,   13,    0,  143,
    7,    8,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,   11,   12,   13,   14,  282,    0,    9,   10,
    0,    0,   11,   12,   13,   14,  248,    0,    5,    0,
    0,    6,    0,    0,    0,    7,    8,    0,    0,    0,
    0,    0,    0,    9,   10,  282,    0,   11,   12,   13,
   14,   97,    0,    0,   97,    0,    0,    0,   97,    0,
   97,   97,    0,    0,    0,    0,   97,   97,   97,   97,
  101,    0,   97,  101,    0,    0,    0,  101,    0,  101,
  101,    0,    0,    0,    0,  101,  101,  101,  101,   98,
    0,  101,   98,    0,    0,    0,   98,    0,   98,   98,
    0,    0,    0,    0,   98,   98,   98,   98,    0,   54,
   98,    0,    6,    0,  212,  213,    7,  216,    0,   55,
    0,    0,    0,    0,    9,   10,   54,   56,    0,  129,
   13,  143,    0,    7,  143,    0,  139,  139,  143,    0,
    0,  131,  132,    0,    0,    0,  143,  143,   54,    0,
    0,    6,  143,    0,    0,    7,    0,  130,   54,    0,
    0,    6,    0,    9,   10,    7,    0,    0,    0,   13,
    0,    0,    0,    9,   10,    0,  256,   54,    0,   13,
    6,    0,    0,    0,    7,    0,    0,    5,    0,    0,
  129,    0,    9,   10,    7,    8,  130,    0,   13,    0,
    0,    0,  131,  132,    0,    0,   11,   12,  133,   14,
    0,    0,  283,    0,  285,    5,  288,    0,    6,  292,
    0,  294,    7,    8,  130,    0,    0,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,   33,    0,
    0,   33,    0,    0,    0,   33,   33,   33,    5,    0,
    0,    6,  315,   33,   33,    7,    8,   33,   33,   33,
   33,    0,    0,    9,   10,    0,  256,   11,   12,   13,
   14,   54,    0,    0,    6,    0,    0,    0,    7,    0,
    0,   55,    0,    0,    0,    0,    9,   10,   54,   56,
    0,    6,   13,    0,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    9,   10,    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   40,   41,   43,   40,   45,   10,   41,   78,
   41,   59,   15,   53,   41,  125,   41,   59,   44,   40,
  123,   60,   61,   62,  209,   59,   59,   58,  125,   41,
   33,   34,   59,   41,   41,  123,  175,    4,   57,  125,
   40,  125,   52,  123,   41,   45,  123,   59,   35,   78,
   40,  258,  123,   41,   57,   45,   41,   59,   41,  123,
   45,  268,   13,   41,   78,   40,   33,  230,  137,   41,
   45,   59,    0,   45,   79,   78,   59,   86,   81,   40,
   13,   59,   13,  123,   45,   41,   41,   13,   43,   45,
   45,   41,   41,   45,   44,   41,   13,   41,  108,   45,
   41,   45,  121,   44,  267,  119,  269,   59,  137,   58,
  133,   45,   99,  100,  137,   45,   44,   58,  121,  133,
   40,  257,   41,  137,  260,    0,  265,   41,  264,  132,
  266,   59,   23,   58,  137,   41,  272,  273,  323,   58,
   58,   59,  278,  130,   58,   36,  186,   60,   61,   62,
  190,    0,   58,  193,  258,   42,  266,   60,   61,   62,
   47,  257,  325,  266,  349,   41,   41,   42,   43,  266,
   45,  240,   47,  261,    0,  194,  262,  263,  262,  263,
  319,  261,  133,  368,   59,   60,   61,   62,  274,  266,
  274,  194,   41,  257,   43,   40,   45,  125,  207,  384,
  133,  206,  133,   43,  191,   45,  209,  133,  211,  257,
   59,   60,   61,   62,  377,  257,  133,   41,    0,   59,
   41,  240,  257,  257,  259,  228,   59,  230,  261,  274,
  269,  270,  271,   44,  261,  304,  257,  240,  276,  277,
  280,  244,  268,   59,  247,  276,  277,   44,    0,  261,
  125,  276,  277,  261,  261,  268,  325,  257,  258,   41,
  257,   43,  259,   45,  267,   59,  269,  257,  258,  338,
  339,   59,  257,  258,  343,  273,  125,   59,   60,   61,
   62,    0,  257,  258,  261,  257,  258,  327,  357,   41,
   43,   43,   45,   45,  363,  261,  257,  258,   59,  125,
   59,  257,  258,   58,  373,  257,  258,   59,   60,   61,
   62,  257,  258,  257,  258,  355,   41,    0,  387,  257,
  323,  123,  325,  257,  258,   58,  366,  257,  258,  257,
  262,  263,  260,  276,  277,  338,  264,  265,  266,   44,
    0,  262,  263,  125,  272,  273,  349,   41,  276,  277,
  278,  279,  262,  263,  357,  257,  269,  270,  271,   41,
  261,   43,   41,   45,  258,  368,  269,  270,  271,  257,
  373,   58,   59,  125,  377,    0,  262,  263,  262,  263,
  125,  384,  257,   40,  387,  260,  261,  262,  263,  264,
  265,  266,  267,  125,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,    0,  125,   58,  257,  262,
  263,  260,  261,  262,  263,  264,  265,  266,  267,   59,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  262,  263,  260,   59,  262,  263,  264,  265,
  266,  267,  125,    0,   94,   95,  272,  273,  274,  275,
  276,  277,  278,  279,   58,  257,  101,  102,  260,   58,
  262,  263,  264,   59,  266,  125,   58,  123,   41,  274,
  272,  273,  125,    0,  273,  257,  278,  125,  260,  261,
  262,  263,  264,  265,  266,  267,   58,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   44,   59,
  125,  123,   59,   59,   59,  257,    0,   41,  260,  261,
  262,  263,  264,  265,  266,  267,   40,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   59,   59,
  125,    0,   59,   59,   59,  125,   59,  125,  257,   59,
   59,  260,  125,  262,  263,  264,  265,  266,  267,  263,
   44,  274,  263,  272,  273,  274,  275,  276,  277,  278,
  279,    0,   59,  263,   59,   59,  263,  263,  125,   44,
   59,  125,   59,  123,  257,   41,  123,  260,   40,  262,
  263,  264,  265,  266,  267,   59,   59,   59,   59,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  125,  263,
  260,   59,  262,  263,  264,  265,  266,  267,  125,   59,
   59,   59,  272,  273,  274,  275,  276,  277,  278,  279,
  272,   59,   59,  125,   41,   40,  125,  263,  260,   59,
   40,  125,  257,   59,  262,  260,   59,  262,  263,  264,
  265,  266,  267,   41,    2,  125,    4,  272,  273,  274,
  275,  276,  277,  278,  279,  261,  125,   15,  263,   41,
  263,  261,  257,  123,  123,  260,   59,  262,  263,  264,
  265,  266,  267,   59,  125,   33,   34,  272,  273,  274,
  275,  276,  277,  278,  279,  125,  125,  123,  263,   59,
  125,   59,  263,  257,  123,   58,   51,   52,    0,  123,
  257,  123,  133,  260,  137,  133,  133,  264,  265,  266,
  257,  244,  194,  260,  261,  272,  273,  264,  319,  276,
  277,  278,  279,   81,  123,  272,  273,  127,   43,  207,
  257,  278,   -1,  260,  123,   -1,   -1,  264,  265,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,  108,  123,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,  123,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,  257,   -1,
   -1,  260,   -1,  123,   -1,  264,  265,  266,  257,   -1,
   -1,  260,  261,  272,  273,  264,   -1,  276,  277,  278,
  279,   -1,  123,  272,  273,  170,   -1,  172,  257,  278,
  175,  260,  177,   -1,  125,  264,  265,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  125,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,  219,  264,   -1,  222,  257,  224,
   -1,  260,  227,  272,  273,  264,  125,  266,  267,  278,
   -1,   -1,   -1,  272,  273,   -1,  275,   -1,  257,  278,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,  125,
   -1,   -1,   -1,  272,  273,   -1,  275,  262,  257,  278,
  265,  260,   -1,  262,  263,  264,   -1,   -1,  125,   -1,
   -1,   -1,   -1,  272,  273,  257,   -1,  257,  260,  278,
  260,   -1,  264,  263,  264,   -1,   -1,  125,   -1,   -1,
  272,  273,  272,  273,   -1,   -1,  257,   -1,  278,  260,
   -1,   -1,  263,  264,   -1,   -1,  257,  125,   -1,  260,
   -1,  272,  273,  264,  319,  266,  267,  278,   -1,   -1,
   -1,  272,  273,  257,  275,   -1,  260,  278,   -1,  125,
  264,  265,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,  125,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  125,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  125,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,  257,
  278,   -1,  260,   -1,  152,  153,  264,  155,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,
  278,  257,   -1,  264,  260,   -1,  262,  263,  264,   -1,
   -1,  272,  273,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,   -1,   -1,  264,   -1,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  272,  273,   -1,  214,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,   -1,  257,   -1,   -1,
  260,   -1,  272,  273,  264,  265,  266,   -1,  278,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,  250,   -1,  252,  257,  254,   -1,  260,  257,
   -1,  259,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,  257,   -1,
   -1,  260,  290,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,   -1,  272,  273,   -1,  304,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,   -1,   -1,  264,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,
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
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';' IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
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
//#line 850 "Parser.java"
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
case 42:
//#line 98 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 45:
//#line 107 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 46:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 51:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 52:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 53:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 54:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 55:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 56:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 57:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 58:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 60:
//#line 131 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 61:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 62:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 63:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 66:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 71:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 72:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 73:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 74:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 75:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 79:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 80:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 81:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 95:
//#line 192 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 96:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 97:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 98:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 99:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 100:
//#line 200 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 101:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 104:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 105:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 106:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 107:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 108:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 114:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 115:
//#line 233 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 116:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 117:
//#line 238 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 118:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 119:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 120:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 123:
//#line 250 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 124:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 125:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 126:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 128:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 129:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 130:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 131:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 132:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 133:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 134:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 135:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 136:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 137:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 138:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 141:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 142:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 147:
//#line 282 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 148:
//#line 283 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 170:
//#line 329 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 171:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 331 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 173:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 174:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 175:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 176:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 177:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 178:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 342 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1343 "Parser.java"
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
