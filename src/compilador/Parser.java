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
    7,    7,    7,   14,   14,   15,   15,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   22,   22,    8,
    8,   23,   23,   23,   23,   25,   25,   25,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   19,
   19,   19,   20,   20,   26,   26,   26,   26,   26,   26,
   28,   28,   29,   29,   29,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   13,   13,   13,   13,   13,
   32,   32,   32,   35,   35,   34,   34,   36,   34,    9,
    9,    9,   37,   37,   38,   38,   38,   38,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   42,   42,
   42,   30,   30,   30,   30,   30,   31,   31,   41,   41,
   43,   43,   43,   43,   43,   27,   44,   44,   45,   45,
   17,   17,   17,   40,   40,   47,   40,   40,   40,   46,
   46,   39,   39,   48,   48,   49,   49,   24,   24,   24,
   50,   50,   50,   50,   50,   50,   33,   33,   33,   51,
   51,   51,   52,   52,   52,   54,   54,   55,   55,   56,
   56,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   53,   53,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    4,
    5,   10,   13,   13,   16,    1,    1,    2,    8,    8,
    8,   11,   11,   15,   13,   13,   15,   15,   15,    9,
   10,   10,    7,    9,    1,    1,    1,    4,    4,    3,
    1,    2,    1,    1,    1,    6,    7,    5,    5,    5,
    5,    6,    6,    6,    6,    5,    4,    3,    3,    4,
    1,    3,    5,    1,    3,    2,    1,    0,    2,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    0,   17,    7,    6,    1,
    2,    8,   10,    1,    3,    1,    2,    3,    2,    2,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    3,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  194,  193,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,  109,  113,    0,  111,  115,
  117,  129,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  119,  120,  121,
  137,    0,  110,  114,  112,  116,  118,    0,  102,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,    0,  191,    0,  142,    0,    0,  172,  174,
  175,   19,  161,  162,  163,  164,  165,  166,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  122,    0,  127,  139,
    0,    0,  107,    0,  100,    0,   15,    0,    0,    0,
    0,    0,   23,   26,    0,    0,   24,   27,   28,   30,
   32,   34,   36,    0,    0,  130,    1,    0,  192,    0,
    0,  141,    0,    0,    0,    0,    0,  187,  185,  188,
    0,  186,    0,  184,    0,    0,    0,    0,   96,    0,
    0,   99,    0,    0,    0,    0,  136,  126,    0,  138,
  140,    0,    0,  105,  104,    0,    0,    0,    0,    0,
   65,   73,   66,    0,    0,   71,   74,   75,    0,   29,
   31,   33,   35,   37,   21,    0,   25,    0,   40,    0,
  180,  176,  181,    0,  178,    0,    0,  170,  171,    0,
    0,  183,  182,    0,   81,    0,    0,   80,    0,    0,
   79,    0,    0,    0,    0,  123,    0,    0,    0,    0,
    0,    0,   88,    0,    0,    0,   72,    0,   22,   41,
    0,    0,  177,    0,  154,    0,   76,    0,   85,   84,
    0,   83,    0,    0,    0,  149,    0,  133,  135,    0,
  134,    0,   90,    0,    0,   70,    0,    0,    0,    0,
    0,  179,  156,    0,    0,    0,   77,    0,   93,  151,
    0,    0,    0,  131,    0,   86,    0,   68,   69,    0,
    0,    0,  155,  157,    0,  152,    0,  146,    0,  145,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   95,    0,  144,   48,   38,    0,    0,    0,    0,
    0,    0,   63,    0,    0,    0,    0,  153,    0,    0,
    0,   39,    0,   49,   51,   50,    0,    0,    0,   46,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,    0,    0,    0,   64,    0,
    0,    0,    0,    0,    0,    0,    0,   42,   61,   62,
    0,    0,    0,    0,    0,    0,   52,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
   43,    0,   56,    0,   44,    0,    0,    0,    0,    0,
    0,    0,    0,  147,   58,   59,   54,    0,   57,    0,
   45,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          3,
    4,   15,  253,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  340,  126,  127,  316,  129,  130,  131,  132,
  133,  317,  341,   89,  306,  184,  135,  185,  186,  187,
  188,  100,   90,  101,  279,  102,   60,   61,   29,   30,
   31,   51,   32,   52,  111,  254,  313,  246,  274,   91,
   78,   79,   80,   81,  204,  205,
};
final static short yysindex[] = {                       -90,
    0, 1035,    0,  671,  -37,   43,  -21,  -35,  103,  696,
    0,    0,  288,  -39,  774,    0,    0,    0,    0,    0,
    0,    0,   -9,   64,   33,    0,    0,  100,    0,    0,
    0,    0,  804,  827,    7,  -95,  569,  132,  135,  -31,
   17,  -27,   73,  -78,   58,  120, 1068,    0,    0,    0,
    0,  -63,    0,    0,    0,    0,    0, -177,    0,  137,
  210,    0,    0,  212,    0,  184,    0,  751,  -11,    0,
  837,    0,  228,    0,   13,    0,  237,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  233,  104,
  -24,  219,  245,   27,   34,  248,  -32,  232,   68,  278,
  285,   80,  283,  308,   92,  314,    0,   -5,    0,    0,
  948,  108,    0,  109,    0,  106,    0,  336,   44,  351,
  540,  407,    0,    0,  247, 1012,    0,    0,    0,    0,
    0,    0,    0,  264,  337,    0,    0,   53,    0,  -24,
  -24,    0,  -24,  -24,  162,  -24,   37,    0,    0,    0,
  343,    0,  366,    0,  373, -125,   87, -125,    0,  375,
 -125,    0, -125,  378,  315, -101,    0,    0,  385,    0,
    0,  273,  396,    0,    0,  569,   65,  272,  569, 1085,
    0,    0,    0,  172, 1085,    0,    0,    0,  409,    0,
    0,    0,    0,    0,    0,  325,    0,  326,    0,  179,
    0,    0,    0,   35,    0,   60,   60,    0,    0,  175,
   37,    0,    0, -125,    0,  397, -125,    0, -125,  410,
    0, -125, 1035,  346,  860,    0,  401,   46,  411,  434,
  417,  349,    0,  437,  724,  446,    0,  569,    0,    0,
  540,   82,    0,  199,    0,  -57,    0, -125,    0,    0,
 -125,    0, 1035,  360, 1035,    0,  362,    0,    0,  429,
    0,  229,    0,  430,  230,    0,  371,  379,  569,  474,
  242,    0,    0,  965,  175,  458,    0,  475,    0,    0,
  461,  404,  463,    0,  -79,    0,  418,    0,    0,  491,
  289,  509,    0,    0,  290,    0, -125,    0,  495,    0,
  776,  905,  292,  293,  295,  297, 1045,  504,  361,  569,
  505,    0,  279,    0,    0,    0,  201,  506,  508,  518,
  -89,  -69,    0,  905,  292,  295,  527,    0,  551,  338,
  477,    0,  478,    0,    0,    0,  572,  357,  905,    0,
    0,  356,  561,  497,  498,  201,  565,  569,  512,   23,
   83,  569, 1102,  567,    0,  573,  578,  515,    0,  601,
  905,  492,  603,  722,  606,  625,  543,    0,    0,    0,
   96,  408, 1112,  905,  412,  413,    0,  905,  414,    0,
  428,  438,  741,  547,  548,  549,  862,  644,  645,  982,
  647,  -86,  652,  905, 1035,  450,  451,  453,  602,    0,
    0,  466,    0,  905,    0,  993,  609,  667,  677,  678,
  476,  689, 1102,    0,    0,    0,    0,  691,    0,  626,
    0,  493,  -46,  905, 1102,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  324,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  496,  496,    0,  699,  885,  907,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  503,    0,  636,
  590,    0,    0,  613,    0,  661,    0,    0,    0,    0,
  754,    0,  141,    0,    0,    0,  235,  309,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  496,    0,   30,    0,
   97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  557,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   49,   62,    0,    0,    0,
  261,    0,  332,    0,    0,    0,    0,    0,    0,    0,
  496,    0,    0,    0,    0,    0,    0,    0,  929,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  485,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  381,  421,    0,    0,    0,
   71,    0,    0,  639,    0,    0,    0,    0,    0,   99,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  122,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  643,    0,    0,
  496,    0,  646,    0,    0,    0,    0,    0,    0,  445,
    0,    0,    0,  231,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  105,    0,    0,
    0,    0,  468,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  496,    0,    0,    0,
    0,    0,  510,    0,  511,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   59, 1041,  -67,   31,    0,    0,    0,    0,  761,
  114,    0,  -25,    0, -103,  -26,   -2,    2,  -94,  -84,
  -71,  870,  -22,  -40, -251,  533,   14,  597, -120,   20,
   50,   22,  916, -122,  481,    0,  668,    0,  770,  772,
  775,   15,  718,    0,    0, -200,    0,  514,    0,  700,
  287,  286,  -98,    0,    0,  550,
};
final static int YYTABLESIZE=1390;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
  124,   26,  106,   27,   42,   27,   36,   26,  155,   96,
   53,   27,   26,  103,   54,   28,   27,   28,   40,   59,
   75,  225,  197,   28,  257,  156,   28,  192,   28,   49,
   26,   26,    2,  339,   27,   27,  404,  193,  220,  203,
   48,  128,  125,  302,   26,  134,   28,   28,   27,   65,
  194,   75,  280,  168,  282,  343,   97,   98,  124,   50,
   28,  110,   34,  104,  237,   76,   49,  151,   26,  342,
   97,  173,   27,   97,  153,  243,  424,   48,  242,  140,
  113,  141,   37,  177,   28,  150,  260,   97,   75,  159,
  114,   71,  152,  202,  182,  181,   50,   75,  183,  128,
  196,  143,  160,  198,  259,  231,  144,  159,   26,   75,
  376,  158,   27,  105,  237,  108,  107,   75,  157,  190,
  160,  169,   67,  191,   28,  171,   75,  216,  278,  158,
   49,  228,   87,   88,   86,  230,   66,   91,  234,   92,
  173,   48,   43,  203,  217,   94,  140,  172,  141,   82,
   11,   12,   75,  182,   91,   68,   92,   69,  182,  224,
   50,   64,   94,   87,   88,   86,    1,   87,   88,   86,
  337,  376,   92,  337,  278,   93,  119,   44,  109,  119,
  118,  173,  173,  173,    7,  173,  119,  173,  338,   35,
  337,  338,  120,  121,  407,  115,  119,  270,  301,  173,
  173,  173,  173,  322,  275,  276,  192,   26,  182,  267,
  112,   27,  268,  337,  182,  181,  193,   58,  183,  119,
   26,   41,   26,   28,   27,   94,   27,   95,  290,  194,
   35,  338,   73,   74,  143,   38,   28,   39,   28,  124,
  245,   26,  117,   11,   12,   27,   89,   64,   11,   12,
   26,  167,   26,  116,   27,   36,   27,   28,  304,  303,
  190,   10,  305,   73,   74,  173,   28,  138,   28,  327,
  139,   26,   26,  145,  273,   27,   27,  148,  315,  140,
  128,  141,  304,  325,  362,  363,  326,   28,   28,  158,
  332,  331,   11,   12,  333,  142,  344,  244,  190,  345,
   73,   74,  191,  149,  294,  245,  154,  360,  169,  201,
   74,  366,  233,  227,  140,  315,  141,   75,  160,  332,
  358,   73,   74,  333,  159,  330,  332,  367,  161,   73,
   74,  189,   87,   88,   86,  375,  162,  379,  201,   74,
  163,   83,   84,   85,  364,  365,  332,  385,  164,  169,
  386,  169,  165,  169,  166,   87,  375,  383,  363,  143,
  332,  399,   58,  332,   73,   74,  174,  169,  169,  169,
  169,  195,   83,   84,   85,  176,   83,   84,   85,  332,
  167,  136,   20,   89,   89,  190,  332,  420,  199,  264,
  179,  140,   26,  141,  200,   89,   27,  173,  332,  399,
  173,  212,  173,  173,  173,  173,  173,  173,   28,  173,
  173,  173,  173,  173,  173,  173,  173,  173,  173,  173,
  168,  167,  210,  167,  213,  167,  206,  207,  208,  209,
  214,   44,  219,  169,    6,  222,  229,  223,    7,  167,
  167,  167,  167,  226,  132,  236,    9,   10,  238,  239,
  240,  241,   13,  251,  248,   44,  189,   44,    6,  258,
  118,  168,    7,  168,    7,  168,  119,  148,  255,  261,
    9,   10,  120,  121,  262,  263,   13,  265,  122,  168,
  168,  168,  168,  324,  281,  269,  283,  284,  286,  285,
  287,  143,   87,   87,  143,  288,  143,  143,  143,  143,
  143,  143,  108,  289,   87,  167,  143,  143,  143,  143,
  143,  143,  143,  143,  291,  292,  296,  190,  297,  298,
  190,  300,  190,  190,  190,  190,  190,  190,  299,   73,
   74,  308,  190,  190,  190,  190,  190,  190,  190,  190,
  307,   83,   84,   85,   44,  168,  108,    6,  310,  309,
  329,    7,  311,  314,  318,  319,  106,  320,  321,    9,
   10,  108,  323,  328,  334,  169,  335,  347,  169,  132,
  169,  169,  169,  169,  169,  169,  336,  169,  169,  169,
  169,  169,  169,  169,  169,  169,  169,  169,  189,  103,
  348,  189,  148,  189,  189,  189,  189,  189,  189,  349,
  106,  350,  351,  189,  189,  189,  189,  189,  189,  189,
  189,  352,   20,   75,  374,  106,  337,   44,  354,  355,
  118,  356,  357,  359,    7,  368,  119,  108,   87,   88,
   86,  369,  120,  121,  361,  101,  370,  167,  122,  371,
  167,  372,  167,  167,  167,  167,  167,  167,  103,  167,
  167,  167,  167,  167,  167,  167,  167,  167,  167,  167,
   16,  377,  180,   44,  380,  381,  189,  382,  384,  395,
    7,   20,  396,  397,  388,  389,  391,  168,  120,  121,
  168,  106,  168,  168,  168,  168,  168,  168,  392,  168,
  168,  168,  168,  168,  168,  168,  168,  168,  168,  168,
  393,  132,  400,  401,  132,  403,  132,  132,  132,  132,
  405,  132,  408,  409,  103,  410,  132,  132,  132,  132,
  132,  132,  132,  132,  148,  415,  411,  148,  412,  148,
  148,  148,  148,  414,  148,  416,  417,   20,  418,  148,
  148,  148,  148,  148,  148,  148,  148,  419,   44,  421,
  422,  118,   98,    4,  423,    7,  136,  119,   67,  108,
  101,   78,  108,  120,  121,   82,  108,  108,  108,  301,
  150,   46,   47,  271,  108,  108,  235,  312,  108,  108,
  108,  108,   55,  175,   56,   16,  136,   57,  295,  146,
    0,  272,    0,   33,    0,    0,   44,    0,    0,  118,
    0,   99,   99,    7,    0,  119,   45,    0,    0,    0,
    0,  120,  121,  106,   46,    0,  106,  122,   47,    0,
  106,  106,  106,    0,    0,   73,   74,    0,  106,  106,
    0,    0,  106,  106,  106,  106,    0,   83,   84,   85,
    0,    0,    0,    0,  378,    0,  103,    0,  266,  103,
    0,    0,    0,  103,  103,  103,    0,   99,    0,    0,
    0,  103,  103,  394,    0,  103,  103,  103,  103,   20,
    0,    0,   20,    0,    0,  123,   20,   20,   20,    0,
    0,    0,    0,    0,   20,   20,    0,    0,   20,   20,
   20,   20,  101,    0,    0,  101,    0,    0,   62,  101,
  101,  101,    0,    0,    0,    0,    0,  101,  101,    0,
    0,  101,  101,  101,  101,    0,  215,   16,  218,    0,
   16,   99,    0,  221,   16,   16,   16,    5,   70,    0,
    6,    0,   16,   16,    7,    8,   16,   16,   16,   16,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
   77,   72,   44,    0,    0,    6,    0,    0,    0,    7,
    0,  137,   45,    0,    0,    0,    0,    9,   10,    0,
   46,    0,    0,   13,  247,    0,    0,  249,   44,  250,
   44,  189,  252,  118,  256,    7,  398,    7,    0,  119,
   45,    0,    0,  120,  121,  120,  121,   44,   46,  122,
  189,  122,    0,    0,    7,    0,  147,    5,  277,  124,
  118,   99,  120,  121,    7,    8,  119,    0,  122,    0,
    0,    0,  120,  121,    0,    0,   11,   12,  122,   14,
    5,  128,   44,    6,  178,  118,    0,    7,    8,    7,
    0,    0,   16,    0,   16,    9,   10,  120,  121,   11,
   12,   13,   14,  125,    0,   63,    0,   99,    0,    0,
    5,  211,    0,    6,    0,    0,    0,    7,    8,    0,
    0,    0,  170,   16,   63,    9,   10,    0,    0,   11,
   12,   13,   14,    5,    0,    0,    6,    0,    0,  293,
    7,    8,  232,    5,    0,    0,    6,    0,    9,   10,
    7,    8,   11,   12,   13,   14,  402,    0,    9,   10,
    0,   63,   11,   12,   13,   14,    5,  398,   44,    6,
    0,  189,    0,    7,    8,    7,    0,  119,    0,    0,
    0,    9,   10,  120,  121,   11,   12,   13,   14,  122,
    0,  124,    0,    0,  124,    0,    0,    0,  124,    0,
  124,  124,    0,    0,    0,    0,  124,  124,  124,  124,
    0,   44,  124,  128,  189,    0,  128,    0,    7,    0,
  128,    0,  128,  128,    0,    0,  120,  121,  128,  128,
  128,  128,  122,    0,  128,  125,    0,    0,  125,    0,
    0,    0,  125,  346,  125,  125,    0,    0,    0,    0,
  125,  125,  125,  125,   44,    0,  125,    6,  353,    0,
    0,    7,    0,    0,   45,    0,    0,    0,    0,    9,
   10,   44,   46,    0,    6,   13,    0,    0,    7,    0,
  373,    0,    0,    0,    0,    0,    9,   10,   44,    0,
    0,  189,   13,  387,    0,    7,    0,  390,    0,   44,
    0,    0,  189,  120,  121,    0,    7,    0,    0,  122,
    0,    0,    0,  406,  120,  121,    0,    0,    5,    0,
  122,  118,    0,  413,    0,    7,    8,  119,    0,    0,
    0,    0,    0,  120,  121,    0,    0,   11,   12,  122,
   14,    5,    0,  425,    6,    0,    0,    0,    7,    8,
    0,    5,    0,    0,  189,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,  120,  121,    0,    0,
   11,   12,  122,   14,   44,    0,    0,    6,    0,    0,
    0,    7,    0,    0,   45,    0,    0,    0,    0,    9,
   10,   44,   46,    0,  189,   13,    0,    0,    7,    0,
    0,   45,    0,    0,    0,    0,  120,  121,   44,   46,
    0,  189,  122,    0,    0,    7,    0,  119,   44,    0,
    0,  118,    0,  120,  121,    7,    0,  119,    0,  122,
    0,    0,    0,  120,  121,    0,    0,    0,    0,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   68,    4,   43,    2,   40,    4,   44,   10,   41,   41,
   13,   10,   15,   41,   13,    2,   15,    4,   40,   59,
   45,  123,  126,   10,  225,   58,   13,  122,   15,   10,
   33,   34,  123,  123,   33,   34,  123,  122,  161,  138,
   10,   68,   68,  123,   47,   68,   33,   34,   47,   59,
  122,   45,  253,   59,  255,  125,   40,   41,  126,   10,
   47,   47,    4,   42,  185,   59,   47,   41,   71,  321,
   41,  112,   71,   44,   41,   41,  123,   47,   44,   43,
  258,   45,   40,   40,   71,   59,   41,   58,   45,   41,
  268,   33,   59,   41,  121,  121,   47,   45,  121,  126,
  126,   42,   41,  126,   59,   41,   47,   59,  111,   45,
  362,   41,  111,   41,  235,   58,   59,   45,   97,  122,
   59,  108,   59,  122,  111,  111,   45,   41,  251,   59,
  111,  172,   60,   61,   62,  176,   23,   41,  179,   41,
    0,  111,   40,  242,   58,   41,   43,   40,   45,   36,
  276,  277,   45,  180,   58,  123,   58,   58,  185,  261,
  111,  257,   58,   60,   61,   62,  257,   60,   61,   62,
  260,  423,   41,  260,  297,   41,  266,  257,   59,  266,
  260,   41,   42,   43,  264,   45,  266,   47,  278,  268,
  260,  278,  272,  273,  395,   59,  266,  238,  278,   59,
   60,   61,   62,  307,  262,  263,  301,  210,  235,  235,
  274,  210,  235,  260,  241,  241,  301,  257,  241,  266,
  223,  257,  225,  210,  223,  257,  225,  259,  269,  301,
  268,  278,  257,  258,    0,  257,  223,  259,  225,  307,
  210,  244,   59,  276,  277,  244,  125,  257,  276,  277,
  253,  257,  255,   44,  253,   44,  255,  244,  285,  285,
    0,  273,  285,  257,  258,  125,  253,   40,  255,  310,
  258,  274,  275,   41,  244,  274,  275,   59,  301,   43,
  307,   45,  309,  309,  262,  263,  309,  274,  275,   58,
  317,  317,  276,  277,  317,   59,  322,  123,  301,  322,
  257,  258,  301,   59,  274,  275,   59,  348,    0,  257,
  258,  352,   41,   41,   43,  338,   45,   45,   41,  346,
  346,  257,  258,  346,  257,  125,  353,  353,   44,  257,
  258,    0,   60,   61,   62,  362,  257,  364,  257,  258,
   58,  269,  270,  271,  262,  263,  373,  373,   41,   41,
  373,   43,  261,   45,   41,  125,  383,  262,  263,  125,
  387,  387,  257,  390,  257,  258,  258,   59,   60,   61,
   62,  125,  269,  270,  271,   40,  269,  270,  271,  406,
    0,   58,   59,  262,  263,  125,  413,  413,  125,   41,
   40,   43,  395,   45,   58,  274,  395,  257,  425,  425,
  260,   59,  262,  263,  264,  265,  266,  267,  395,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
    0,   41,  261,   43,   59,   45,  140,  141,  143,  144,
   58,  257,   58,  125,  260,   58,   41,  123,  264,   59,
   60,   61,   62,   59,    0,  274,  272,  273,   40,  125,
  125,  273,  278,   44,   58,  257,  125,  257,  260,   59,
  260,   41,  264,   43,  264,   45,  266,    0,  123,   59,
  272,  273,  272,  273,   41,   59,  278,   41,  278,   59,
   60,   61,   62,  123,  125,   40,  125,   59,   59,  261,
  261,  257,  262,  263,  260,  125,  262,  263,  264,  265,
  266,  267,    0,  125,  274,  125,  272,  273,  274,  275,
  276,  277,  278,  279,   41,  274,   59,  257,   44,   59,
  260,   59,  262,  263,  264,  265,  266,  267,  125,  257,
  258,   41,  272,  273,  274,  275,  276,  277,  278,  279,
  123,  269,  270,  271,  257,  125,   44,  260,   40,  261,
  272,  264,  263,   59,  263,  263,    0,  263,  262,  272,
  273,   59,   59,   59,   59,  257,   59,   41,  260,  125,
  262,  263,  264,  265,  266,  267,   59,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,    0,
   40,  260,  125,  262,  263,  264,  265,  266,  267,  262,
   44,  125,  125,  272,  273,  274,  275,  276,  277,  278,
  279,   40,    0,   45,  123,   59,  260,  257,  263,   59,
  260,  125,  125,   59,  264,   59,  266,  125,   60,   61,
   62,   59,  272,  273,  123,    0,   59,  257,  278,  125,
  260,   41,  262,  263,  264,  265,  266,  267,   59,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
    0,   59,  123,  257,   59,   41,  260,  125,  261,  123,
  264,   59,  125,  125,  263,  263,  263,  257,  272,  273,
  260,  125,  262,  263,  264,  265,  266,  267,  261,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  263,  257,   59,   59,  260,   59,  262,  263,  264,  265,
   59,  267,  263,  263,  125,  263,  272,  273,  274,  275,
  276,  277,  278,  279,  257,   59,  125,  260,  263,  262,
  263,  264,  265,  125,  267,   59,   59,  125,  263,  272,
  273,  274,  275,  276,  277,  278,  279,   59,  257,   59,
  125,  260,  257,    0,  262,  264,   58,  266,  274,  257,
  125,  123,  260,  272,  273,  123,  264,  265,  266,  278,
  125,  262,  262,  241,  272,  273,  180,  297,  276,  277,
  278,  279,   13,  116,   13,  125,   69,   13,  275,   90,
   -1,  242,   -1,  123,   -1,   -1,  257,   -1,   -1,  260,
   -1,   41,   42,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  257,  275,   -1,  260,  278,  123,   -1,
  264,  265,  266,   -1,   -1,  257,  258,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,  269,  270,  271,
   -1,   -1,   -1,   -1,  123,   -1,  257,   -1,  125,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   97,   -1,   -1,
   -1,  272,  273,  123,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,  125,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,   -1,  125,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,   -1,  156,  257,  158,   -1,
  260,  161,   -1,  163,  264,  265,  266,  257,  125,   -1,
  260,   -1,  272,  273,  264,  265,  276,  277,  278,  279,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   35,  125,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  125,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,
  275,   -1,   -1,  278,  214,   -1,   -1,  217,  257,  219,
  257,  260,  222,  260,  125,  264,  125,  264,   -1,  266,
  267,   -1,   -1,  272,  273,  272,  273,  257,  275,  278,
  260,  278,   -1,   -1,  264,   -1,   91,  257,  248,  125,
  260,  251,  272,  273,  264,  265,  266,   -1,  278,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,  125,  257,  260,  119,  260,   -1,  264,  265,  264,
   -1,   -1,    2,   -1,    4,  272,  273,  272,  273,  276,
  277,  278,  279,  125,   -1,   15,   -1,  297,   -1,   -1,
  257,  146,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,
   -1,   -1,  125,   33,   34,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,  125,
  264,  265,  177,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,  125,   -1,  272,  273,
   -1,   71,  276,  277,  278,  279,  257,  125,  257,  260,
   -1,  260,   -1,  264,  265,  264,   -1,  266,   -1,   -1,
   -1,  272,  273,  272,  273,  276,  277,  278,  279,  278,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
   -1,  257,  278,  257,  260,   -1,  260,   -1,  264,   -1,
  264,   -1,  266,  267,   -1,   -1,  272,  273,  272,  273,
  274,  275,  278,   -1,  278,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,  324,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,  339,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  257,  275,   -1,  260,  278,   -1,   -1,  264,   -1,
  361,   -1,   -1,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,  374,   -1,  264,   -1,  378,   -1,  257,
   -1,   -1,  260,  272,  273,   -1,  264,   -1,   -1,  278,
   -1,   -1,   -1,  394,  272,  273,   -1,   -1,  257,   -1,
  278,  260,   -1,  404,   -1,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,  424,  260,   -1,   -1,   -1,  264,  265,
   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  257,  275,   -1,  260,  278,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,   -1,   -1,  264,   -1,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,  266,   -1,  278,
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
"funcion_con_return : encabezado_funcion '{' '}'",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : asignacion",
"sentencia_ejecutable_funcion : DEFER asignacion",
"sentencia_ejecutable_funcion : imprimir",
"sentencia_ejecutable_funcion : DEFER imprimir",
"sentencia_ejecutable_funcion : sentencia_when_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_when_con_return",
"sentencia_ejecutable_funcion : sentencia_do_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_do_con_return",
"sentencia_ejecutable_funcion : sentencia_seleccion_simple_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_seleccion_simple_con_return",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_return",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return_simple : DEFER sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_ejecutable_funcion ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_ejecutable_funcion ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE sentencia_ejecutable_funcion ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_return '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';'",
"sentencia_do_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"sentencia_do_con_return : etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"bloque_sentencias_ejecutables_do_con_return : sentencia_return",
"bloque_sentencias_ejecutables_do_con_return : sentencia_seleccion_compuesta_con_return",
"bloque_sentencias_ejecutables_do_con_return : sentencias_ejecutables_do_funcion",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion '}'",
"sentencias_ejecutables_do_funcion : sentencia_ejecutable_do_funcion",
"sentencias_ejecutables_do_funcion : sentencias_ejecutables_do_funcion sentencia_ejecutable_do_funcion",
"sentencia_ejecutable_do_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_do_funcion : sentencia_break",
"sentencia_ejecutable_do_funcion : sentencia_continue",
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
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}'",
"sentencia_when : WHEN '(' condicion ')' '{' '}'",
"sentencias_when : sentencia",
"sentencias_when : sentencia sentencias_when",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
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

//#line 375 ".\gramatica.y"

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
//#line 893 "Parser.java"
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
case 40:
//#line 92 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 41:
//#line 93 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 60:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 61:
//#line 125 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 62:
//#line 126 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 63:
//#line 130 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 64:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 78:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 79:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 80:
//#line 159 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 81:
//#line 160 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 82:
//#line 161 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 83:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 84:
//#line 163 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 85:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 87:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 88:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 89:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 90:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 93:
//#line 178 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 98:
//#line 188 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 99:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 100:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 101:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 102:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 106:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 107:
//#line 206 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 108:
//#line 207 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 122:
//#line 230 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 123:
//#line 231 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 124:
//#line 232 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 125:
//#line 233 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 126:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 127:
//#line 238 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 128:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 131:
//#line 248 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 132:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 133:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 134:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 135:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 141:
//#line 270 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 142:
//#line 271 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 143:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 144:
//#line 276 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 145:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 146:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 147:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 148:
//#line 280 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 149:
//#line 281 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 152:
//#line 290 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 153:
//#line 291 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 159:
//#line 306 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 160:
//#line 307 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 182:
//#line 353 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 183:
//#line 354 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 184:
//#line 355 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 185:
//#line 356 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 186:
//#line 357 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 187:
//#line 358 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 188:
//#line 359 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 189:
//#line 360 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 190:
//#line 361 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 192:
//#line 366 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1342 "Parser.java"
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
