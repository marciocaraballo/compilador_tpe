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
   45,   45,   45,   45,   45,   21,   21,   20,   20,   20,
   46,   46,   46,   46,   46,   46,   28,   28,   28,   47,
   47,   47,   48,   48,   48,   50,   50,   51,   51,   52,
   52,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   49,   49,   10,   10,
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
    1,    3,    2,    2,    2,    1,    2,    3,    2,    2,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    3,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  184,  183,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   84,   86,   88,   90,
   92,  104,    0,    0,    0,    0,    0,  181,  151,  152,
  153,    0,    0,  154,  155,  156,    0,    0,    0,    0,
  162,  164,  165,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,  112,   95,   96,    0,   85,   87,
   89,   91,   93,    0,   77,    0,    0,    2,    8,    0,
   18,    0,   17,    0,    0,    5,    0,    3,  117,    0,
   19,    0,  182,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   97,    0,  102,
    0,  114,    0,   82,    0,   75,    0,   15,    0,    0,
    0,    0,    0,   23,   26,   27,    0,    0,   24,   28,
   30,   32,    0,    0,  105,    1,  116,  170,  166,  171,
    0,  168,    0,    0,    0,    0,    0,    0,    0,  160,
  161,  177,  175,  178,    0,  176,    0,  174,    0,    0,
    0,    0,   71,    0,    0,   74,    0,    0,    0,    0,
  111,  101,    0,  113,  115,    0,    0,   80,   79,    0,
    0,    0,    0,    0,   49,    0,   29,   31,   33,   35,
   21,    0,   25,    0,   36,    0,    0,  167,    0,    0,
    0,    0,    0,    0,    0,    0,  173,  172,    0,   56,
    0,    0,   55,    0,    0,   54,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    0,   22,   37,    0,  169,  145,  146,    0,  143,  147,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,    0,   60,   59,    0,   58,    0,    0,    0,
    0,  108,  110,    0,  109,    0,   65,    0,    0,    0,
    0,    0,  142,    0,  129,    0,  127,    0,    0,  132,
    0,    0,    0,  130,    0,  128,   52,    0,   68,  124,
    0,    0,    0,  106,    0,   61,    0,   50,    0,    0,
    0,    0,  140,    0,    0,    0,  125,    0,    0,    0,
  121,    0,  120,    0,    0,    0,    0,    0,  135,  133,
  137,  138,    0,  136,  134,   70,    0,  119,    0,    0,
    0,   45,    0,    0,   47,    0,  126,    0,    0,    0,
    0,   42,    0,   46,    0,    0,    0,    0,    0,   44,
   48,    0,    0,    0,    0,   38,    0,    0,    0,   43,
    0,    0,    0,    0,    0,    0,    0,   39,   40,    0,
    0,  122,    0,   41,
};
final static short yydgoto[] = {                          3,
    4,   15,  268,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  195,  138,  139,  140,  141,  142,  143,   47,
  211,  343,  269,  196,   26,  121,  111,   48,  112,  299,
  113,   76,   77,   27,   28,   29,   30,   31,   65,   66,
   67,   32,   68,  337,  215,   49,   50,   51,   52,   53,
  151,  152,
};
final static short yysindex[] = {                      -108,
    0,  981,    0,  563,  -38,    3,    5,  -14,    6,  661,
    0,    0,  436,  -40,  760,    0,    0,    0,    0,    0,
    0,    0,  -37,  111,  -51,   -6,    0,    0,    0,    0,
    0,    0,  770,  794,  -35, -219,   62,    0,    0,    0,
    0, -129,  555,    0,    0,    0,   97,   65,   23,  159,
    0,    0,    0,  158,  161,    8,  -36,  -21,  586, -122,
  102,  151, 1082,    0,    0,    0,    0,  -55,    0,    0,
    0,    0,    0, -153,    0,  166,  204,    0,    0,  213,
    0,  210,    0,  737,   18,    0,  804,    0,    0,  242,
    0,   26,    0,   64,  -27,   93,   23,   23,   23,  107,
   23,   23,  268,  303,   14,   57,  311,  -23,  320,  133,
  366,  364,  152,  353,  392,  173,  394,    0,  -20,    0,
  904,    0,   94,    0,  179,    0,  183,    0,  619,   13,
  399,  594,  847,    0,    0,    0,  316, 1013,    0,    0,
    0,    0,  317,  385,    0,    0,    0,    0,    0,    0,
  127,    0,  726,  726,  480,  726,  159,  159,  107,    0,
    0,    0,    0,    0,  386,    0,  389,    0,  391,   -1,
   38,   -1,    0,  400,   -1,    0,   -1,  415,  321,  -61,
    0,    0,  416,    0,    0,  653,  433,    0,    0,  555,
   59,   68,  586, 1082,    0,  202,    0,    0,    0,    0,
    0,  352,    0,  354,    0,  207,   61,    0,  954,    0,
  964,   96,   98,  678,  105,  110,    0,    0,   -1,    0,
  424,   -1,    0,   -1,  445,    0,   -1,  981,  368,  981,
    0,  453,   58,  454,  -25,  455,  312,    0,  451,  887,
  475,    0,    0,  594,    0,    0,    0,  983,    0,    0,
  726,  465,  726,  466,  574,  468,  113,  726,  469,  726,
  471,    0,   -1,    0,    0,   -1,    0,  981,  406,  981,
  407,    0,    0,  474,    0,  698,    0,  476,  -53,  409,
  613,  264,    0,  277,    0,  284,    0,  489,  286,    0,
  709,  491,  296,    0,  297,    0,    0,  517,    0,    0,
  504,  437,  505,    0,  642,    0,  442,    0,  525,  527,
  509,  510,    0,  513,  515,  318,    0,  523,  524,   -1,
    0,  534,    0,  954,  118, 1036,  535,  613,    0,    0,
    0,    0,  538,    0,    0,    0,  326,    0,  937,  -94,
  540,    0,  459,  -91,    0,  560,    0,  562,  481, 1109,
  342,    0,  549,    0,  559,  613,  140, 1099,  570,    0,
    0,  589,  -73,  573,  508,    0,  374, 1109,  373,    0,
  375,  516, 1099,  581,  593,  981,  532,    0,    0,  536,
  397,    0,  603,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  230,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,    0,    0,    0,    0,    0,  408,  408,    0,  605,
  827,  849,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  377,    0,  503,  412,    0,    0,  470,
    0,  526,    0,    0,    0,    0,  666,    0,    0,   66,
    0,    0,    0,    0,    0,    0,    0,    0,  -34,  -32,
    0,    0,    0,    0,    0,    0,    0,  408,    0,   79,
    0,   49,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  446,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1059,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  153,  190,  -29,    0,
    0,    0,    0,    0,  221,    0,  244,    0,    0,    0,
    0,    0,    0,    0,  408,    0,    0,    0,    0,    0,
    0,    0,  868,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1059,    0,    0,    0,    0,    0,  927,
    0,    0,    0,    0,    0,    0,    0,    0,  544,    0,
    0,    0,    0,    0,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -89,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  143,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  545,    0,    0,  408,    0,  -71,    0,    0,
    0,    0,    0,  279,    0,    0,    0,  -85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  313,    0,    0,    0,    0,    0,   87,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  408,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  347,    0,    0,    0,    0,    0,  143,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   55,   99,   -7,   -2,    0,    0,    0,    0,  587,
  182,    0,  -81,    0,  533,  537,  550,  554,   78,  -42,
 -181,    0, -179,  438,    2,  498,  -11,  -12, -154,  379,
    0,  575,    0,   31,   43,   47,   70,   71,  -52,    0,
    0,  -44,    0,    0, 1016,  656,  223,  248,  -50,    0,
    0,  494,
};
final static int YYTABLESIZE=1387;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   95,   18,  137,  108,  109,   36,  149,   64,  150,   42,
  122,  148,   18,  155,    2,  276,  117,  169,   75,  114,
  225,   81,   90,   89,  149,   58,  150,  248,  350,  148,
   18,   18,  163,  322,  170,   64,  100,   80,  182,   62,
  145,  150,   43,   69,   56,   59,  115,   42,  107,  368,
  271,   85,  191,  123,  165,   70,  202,   42,   34,   71,
   64,  230,   45,   46,   44,  118,  149,   42,  185,  230,
   42,   84,  164,  163,  163,  163,  135,  163,  221,  163,
  187,  136,   72,   73,   18,  144,  159,   87,  300,   66,
  302,  163,  163,  163,  163,  222,  171,  167,  274,  236,
   16,   92,   16,   42,  124,   42,   66,   97,  238,   98,
   97,  298,   98,   79,  125,  166,  273,  192,   64,   72,
  183,  159,   72,   67,   45,   46,   44,   69,   93,   64,
  135,   16,   79,  186,  144,  136,   72,   96,   42,  144,
   67,  122,  339,  233,   69,   35,  344,  235,    1,   97,
  239,   98,  157,   45,   46,   44,  150,  163,  280,  119,
  118,  145,  159,   69,  159,  298,  159,  208,  358,   83,
  207,  130,   64,   64,  130,   70,   62,   62,  237,   71,
  159,  159,  159,  159,   64,   79,  373,  185,   62,  158,
  118,   64,  130,  157,  123,  157,  380,  157,  103,  229,
  101,  104,   72,   73,   82,  102,  247,  307,  250,  120,
  200,  157,  157,  157,  157,  204,   74,   91,  123,   80,
  180,   37,   38,  325,  126,   18,  149,   18,  150,   35,
  158,  148,  158,  154,  158,  154,  181,   64,  309,   11,
   12,   64,   57,  179,  342,  250,  159,  127,  158,  158,
  158,  158,   11,   12,   11,   12,   36,  349,  351,   37,
   38,   54,  354,   55,  105,   18,  106,   18,  128,   37,
   38,   39,   40,   41,   11,   12,  365,  157,  107,   37,
   38,  369,  148,   38,   97,  346,   98,  111,   20,  163,
   10,  377,  163,  163,  163,  163,  163,  163,  163,  163,
  147,  163,  163,  163,  163,  163,  163,  163,  163,  163,
  163,  163,  131,  362,  158,   37,   38,  148,   38,  157,
  158,  247,  118,   18,  153,  118,  162,  118,  118,  118,
  118,  118,  118,   39,   40,   41,  250,  118,  118,  118,
  118,  118,  118,  118,  118,  180,  139,  247,  160,  161,
   37,   38,  278,  156,   97,  250,   98,  251,  252,  253,
  254,  163,   39,   40,   41,  247,  258,  259,  179,  168,
  250,  260,  261,   18,  291,  292,   83,  172,  159,  340,
  341,  159,  159,  159,  159,  159,  159,  159,  159,  173,
  159,  159,  159,  159,  159,  159,  159,  159,  159,  159,
  159,  363,  364,  107,  144,  144,  174,  175,  176,  157,
  177,   78,  157,  157,  157,  157,  157,  157,  157,  157,
   83,  157,  157,  157,  157,  157,  157,  157,  157,  157,
  157,  157,  178,  179,  180,   83,  188,  131,  193,   74,
  201,  205,  206,  228,  217,   81,  158,  218,  219,  158,
  158,  158,  158,  158,  158,  158,  158,  224,  158,  158,
  158,  158,  158,  158,  158,  158,  158,  158,  158,   20,
   78,  139,  227,  234,  231,  241,  242,  180,  243,  244,
  180,  263,  180,  180,  180,  180,  180,  180,  266,   81,
  270,  279,  180,  180,  180,  180,  180,  180,  180,  180,
  179,   83,   76,  179,   81,  179,  179,  179,  179,  179,
  179,  272,  275,  277,  281,  179,  179,  179,  179,  179,
  179,  179,  179,  285,  287,   16,  290,  294,   20,  296,
  301,  303,  304,  308,  306,  107,   78,  310,  107,  311,
  107,  107,  107,  107,  107,  107,  312,  313,  314,  317,
  107,  107,  107,  107,  107,  107,  107,  107,  318,  319,
  320,  322,  321,  323,  326,  327,  328,  329,  330,  131,
   81,  331,  131,  332,  131,  131,  131,  131,  131,  131,
  333,  334,  335,  353,  131,  131,  131,  131,  131,  131,
  131,  131,  338,  345,   20,   94,  347,  348,  352,   42,
  355,  356,  209,  139,  359,  357,  139,  360,  139,  139,
  139,  139,  139,  139,   45,   46,   44,  361,  139,  139,
  139,  139,  139,  139,  139,  139,  116,   76,  366,  367,
   42,  370,  371,   83,  372,  374,   83,  375,  376,  378,
   83,   83,   83,  110,  110,   45,   46,   44,   83,   83,
   16,  379,   83,   83,   83,   83,  381,   42,  190,  383,
  382,  384,  111,   42,   73,    4,   53,   57,   78,  197,
  203,   78,   45,   46,   44,   78,   78,   78,   45,   46,
   44,  282,  198,   78,   78,   33,  199,   78,   78,   78,
   78,  240,   60,  232,  110,    6,  209,   42,  336,    7,
  245,  189,   81,   99,    0,   81,    0,    9,   10,   81,
   81,   81,   45,   46,   44,    0,  194,   81,   81,    0,
    0,   81,   81,   81,   81,    0,   20,    0,    0,   20,
    0,    0,    0,   20,   20,   20,   60,    0,    0,    6,
  214,   20,   20,    7,    0,   20,   20,   20,   20,    0,
    0,    9,   10,    0,    0,    0,  220,   13,  223,   76,
    0,  110,   76,  226,  324,    0,   76,   76,   76,    0,
    0,    0,    0,    0,   76,   76,    0,    0,   76,   76,
   76,   76,   16,   63,    0,   16,    0,    0,    0,   16,
   16,   16,    0,    0,    0,    0,    0,   16,   16,    0,
  209,   16,   16,   16,   16,  262,    0,    0,  264,    0,
  265,   37,   38,  267,    0,    0,    0,    0,    0,    5,
  209,    0,    6,   39,   40,   41,    7,    8,    0,    0,
   60,  209,    0,    6,    9,   10,  288,    7,   11,   12,
   13,   14,   37,   38,    0,    9,   10,    0,  209,  297,
   60,   13,  110,    6,   39,   40,   41,    7,    0,  130,
   61,  134,    0,    0,    0,    9,   10,    0,   62,   37,
   38,   13,    0,    0,    0,   37,   38,    0,    0,    0,
    0,   39,   40,   41,   78,    0,    0,   39,   40,   41,
    0,    0,    0,    0,   86,    0,    0,    0,   60,    0,
    0,    6,    0,  255,  256,    7,  110,  130,    0,   37,
   38,    0,    0,    9,   10,    0,    0,   60,   88,   13,
    6,   39,   40,   41,    7,    0,    0,   61,  146,    0,
    0,    0,    9,   10,   60,   62,    0,    6,   13,  255,
  256,    7,    0,    0,    0,    0,    0,    0,    0,    9,
   10,   99,    0,    0,   60,   13,    0,    6,  305,    0,
    0,    7,    0,    0,    0,   60,    0,    0,    6,    9,
   10,  315,    7,  103,    0,   13,    0,    0,    0,    0,
    9,   10,   60,    0,    0,    6,   13,    0,    0,    7,
    0,    0,  100,    5,    0,    0,  129,    9,   10,    0,
    7,    8,  130,   13,    0,    0,    0,    0,  131,  132,
    0,  184,   11,   12,  133,   14,    5,    0,    0,    6,
    0,    0,    0,    7,    8,    0,    5,    0,  184,    6,
    0,    9,   10,    7,    8,   11,   12,   13,   14,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,    0,
    5,  146,    0,    6,    0,    0,    0,    7,    8,    0,
    5,  283,    0,    6,    0,    9,   10,    7,    8,   11,
   12,   13,   14,    0,    0,    9,   10,    0,  246,   11,
   12,   13,   14,   99,    0,    0,   99,    0,  249,    0,
   99,    0,   99,   99,    0,    0,    0,    0,   99,   99,
   99,   99,    0,   60,   99,  103,  129,  283,  103,    0,
    7,    0,  103,    0,  103,  103,    0,    0,  131,  132,
  103,  103,  103,  103,  100,    0,  103,  100,    0,    0,
    0,  100,    0,  100,  100,    0,    0,    0,    0,  100,
  100,  100,  100,   60,    0,  100,    6,    0,    0,    0,
    7,    0,  130,   61,    0,    0,    0,    0,    9,   10,
   60,   62,    0,    6,   13,    0,    0,    7,  212,  213,
   61,  216,    0,    0,    0,    9,   10,    0,   62,    0,
    0,   13,    0,  146,    0,    0,  146,    0,  141,  141,
  146,    0,    0,   60,    0,    0,    6,    0,  146,  146,
    7,    0,  130,    0,  146,    0,    0,    0,    9,   10,
   60,    0,    0,    6,   13,    0,    0,    7,    0,    0,
   60,    0,    0,    6,    0,    9,   10,    7,    0,  257,
    0,   13,    0,    0,    0,    9,   10,    5,    0,   60,
    6,   13,    6,    0,    7,    8,    7,    0,    0,    0,
    0,    0,    9,   10,    9,   10,   11,   12,   13,   14,
   13,    0,    0,    0,    0,    0,  284,    0,  286,    5,
  289,    0,  129,  293,    0,  295,    7,    8,  130,    0,
    0,    0,    0,    0,  131,  132,    0,    0,   11,   12,
  133,   14,    5,    0,    0,    6,    0,    0,    0,    7,
    8,  130,    0,    0,    0,    0,  316,    9,   10,    0,
    0,   11,   12,   13,   14,   34,    0,    0,   34,    0,
  257,    0,   34,   34,   34,    0,    0,    0,    0,    0,
   34,   34,    0,    0,   34,   34,   34,   34,   60,    0,
    0,    6,    0,    0,    0,    7,    0,    0,   61,    0,
    0,    0,    0,    9,   10,   60,   62,    0,    6,   13,
    0,    0,    7,    0,  130,   60,    0,    0,    6,    0,
    9,   10,    7,    0,    0,    0,   13,    0,    0,    0,
    9,   10,    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   43,    4,   84,   40,   41,   44,   41,   10,   41,   45,
   63,   41,   15,   41,  123,   41,   59,   41,   59,   41,
  175,   59,   35,   59,   59,   40,   59,  209,  123,   59,
   33,   34,    0,  125,   58,  125,   49,  257,   59,  125,
   85,   92,   40,   13,   40,   40,   58,   45,   41,  123,
  230,   58,   40,  125,   41,   13,  138,   45,    4,   13,
   63,  123,   60,   61,   62,    0,   41,   45,  121,  123,
   45,  123,   59,   41,   42,   43,   84,   45,   41,   47,
  123,   84,   13,   13,   87,   84,   99,   33,  268,   41,
  270,   59,   60,   61,   62,   58,  108,   41,   41,   41,
    2,   40,    4,   45,  258,   45,   58,   43,   41,   45,
   43,  266,   45,   15,  268,   59,   59,  130,  121,   41,
  119,    0,   44,   41,   60,   61,   62,   41,  258,  132,
  138,   33,   34,   40,  133,  138,   58,   41,   45,  138,
   58,  194,  324,  186,   58,  268,  326,  190,  257,   43,
  193,   45,    0,   60,   61,   62,  207,  125,  240,   58,
   59,  206,   41,  133,   43,  320,   45,   41,  350,   59,
   44,  266,  262,  263,  266,  133,  262,  263,  191,  133,
   59,   60,   61,   62,  274,   87,  368,  240,  274,    0,
  125,  194,  266,   41,  266,   43,  376,   45,   41,  261,
   42,   41,  133,  133,   23,   47,  209,  261,  211,   59,
  133,   59,   60,   61,   62,  138,  257,   36,  274,  257,
    0,  257,  258,  305,   59,  228,  261,  230,  261,  268,
   41,  261,   43,  261,   45,  261,  257,  240,  281,  276,
  277,  244,  257,    0,  326,  248,  125,   44,   59,   60,
   61,   62,  276,  277,  276,  277,   44,  339,  340,  257,
  258,  257,  344,  259,  257,  268,  259,  270,   59,  257,
  258,  269,  270,  271,  276,  277,  358,  125,    0,  257,
  258,  363,  257,  258,   43,  328,   45,   58,   59,  257,
  273,  373,  260,  261,  262,  263,  264,  265,  266,  267,
   59,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,    0,  356,  125,  257,  258,  257,  258,   97,
   98,  324,  257,  326,  261,  260,   59,  262,  263,  264,
  265,  266,  267,  269,  270,  271,  339,  272,  273,  274,
  275,  276,  277,  278,  279,  125,    0,  350,  101,  102,
  257,  258,   41,  261,   43,  358,   45,  262,  263,  262,
  263,   59,  269,  270,  271,  368,  262,  263,  125,   59,
  373,  262,  263,  376,  262,  263,    0,   58,  257,  262,
  263,  260,  261,  262,  263,  264,  265,  266,  267,  257,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  262,  263,  125,  262,  263,   41,   44,  257,  257,
   58,    0,  260,  261,  262,  263,  264,  265,  266,  267,
   44,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,   41,  261,   41,   59,  258,  125,   40,  257,
  125,  125,   58,  123,   59,    0,  257,   59,   58,  260,
  261,  262,  263,  264,  265,  266,  267,   58,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,    0,
   59,  125,   58,   41,   59,  274,  125,  257,  125,  273,
  260,   58,  262,  263,  264,  265,  266,  267,   44,   44,
  123,   41,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  125,    0,  260,   59,  262,  263,  264,  265,  266,
  267,   59,   59,   59,   40,  272,  273,  274,  275,  276,
  277,  278,  279,   59,   59,    0,   59,   59,   59,   59,
  125,  125,   59,  125,   59,  257,  125,  274,  260,  263,
  262,  263,  264,  265,  266,  267,  263,   59,  263,   59,
  272,  273,  274,  275,  276,  277,  278,  279,  263,  263,
   44,  125,   59,   59,  123,   41,   40,   59,   59,  257,
  125,   59,  260,   59,  262,  263,  264,  265,  266,  267,
  263,   59,   59,  125,  272,  273,  274,  275,  276,  277,
  278,  279,   59,   59,  125,   41,   59,  272,   59,   45,
   41,   40,  123,  257,  263,  125,  260,   59,  262,  263,
  264,  265,  266,  267,   60,   61,   62,   59,  272,  273,
  274,  275,  276,  277,  278,  279,   41,  125,   59,   41,
   45,   59,  125,  257,  261,  263,  260,  263,  123,   59,
  264,  265,  266,   57,   58,   60,   61,   62,  272,  273,
  125,   59,  276,  277,  278,  279,  125,   45,   40,  263,
  125,   59,   58,   45,  257,    0,  123,  123,  257,  133,
  138,  260,   60,   61,   62,  264,  265,  266,   60,   61,
   62,  244,  133,  272,  273,  123,  133,  276,  277,  278,
  279,  194,  257,   41,  108,  260,  123,   45,  320,  264,
  207,  127,  257,   48,   -1,  260,   -1,  272,  273,  264,
  265,  266,   60,   61,   62,   -1,  123,  272,  273,   -1,
   -1,  276,  277,  278,  279,   -1,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,  257,   -1,   -1,  260,
  261,  272,  273,  264,   -1,  276,  277,  278,  279,   -1,
   -1,  272,  273,   -1,   -1,   -1,  170,  278,  172,  257,
   -1,  175,  260,  177,  123,   -1,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,  123,   -1,  260,   -1,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
  123,  276,  277,  278,  279,  219,   -1,   -1,  222,   -1,
  224,  257,  258,  227,   -1,   -1,   -1,   -1,   -1,  257,
  123,   -1,  260,  269,  270,  271,  264,  265,   -1,   -1,
  257,  123,   -1,  260,  272,  273,  263,  264,  276,  277,
  278,  279,  257,  258,   -1,  272,  273,   -1,  123,  263,
  257,  278,  266,  260,  269,  270,  271,  264,   -1,  266,
  267,  125,   -1,   -1,   -1,  272,  273,   -1,  275,  257,
  258,  278,   -1,   -1,   -1,  257,  258,   -1,   -1,   -1,
   -1,  269,  270,  271,  125,   -1,   -1,  269,  270,  271,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,  262,  263,  264,  320,  266,   -1,  257,
  258,   -1,   -1,  272,  273,   -1,   -1,  257,  125,  278,
  260,  269,  270,  271,  264,   -1,   -1,  267,  125,   -1,
   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,  262,
  263,  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,  125,   -1,   -1,  257,  278,   -1,  260,  261,   -1,
   -1,  264,   -1,   -1,   -1,  257,   -1,   -1,  260,  272,
  273,  263,  264,  125,   -1,  278,   -1,   -1,   -1,   -1,
  272,  273,  257,   -1,   -1,  260,  278,   -1,   -1,  264,
   -1,   -1,  125,  257,   -1,   -1,  260,  272,  273,   -1,
  264,  265,  266,  278,   -1,   -1,   -1,   -1,  272,  273,
   -1,  125,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,   -1,  257,   -1,  125,  260,
   -1,  272,  273,  264,  265,  276,  277,  278,  279,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,
  257,  125,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,
  257,  125,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,  125,   -1,
  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,   -1,  257,  278,  257,  260,  125,  260,   -1,
  264,   -1,  264,   -1,  266,  267,   -1,   -1,  272,  273,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
  257,  275,   -1,  260,  278,   -1,   -1,  264,  153,  154,
  267,  156,   -1,   -1,   -1,  272,  273,   -1,  275,   -1,
   -1,  278,   -1,  257,   -1,   -1,  260,   -1,  262,  263,
  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,   -1,  266,   -1,  278,   -1,   -1,   -1,  272,  273,
  257,   -1,   -1,  260,  278,   -1,   -1,  264,   -1,   -1,
  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,  214,
   -1,  278,   -1,   -1,   -1,  272,  273,  257,   -1,  257,
  260,  278,  260,   -1,  264,  265,  264,   -1,   -1,   -1,
   -1,   -1,  272,  273,  272,  273,  276,  277,  278,  279,
  278,   -1,   -1,   -1,   -1,   -1,  251,   -1,  253,  257,
  255,   -1,  260,  258,   -1,  260,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,  291,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
  305,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,
   -1,   -1,  264,   -1,  266,  257,   -1,   -1,  260,   -1,
  272,  273,  264,   -1,   -1,   -1,  278,   -1,   -1,   -1,
  272,  273,   -1,   -1,   -1,   -1,  278,
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
"bloque_sentencias_ejecutables_seleccion : '{' '}'",
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

//#line 353 ".\gramatica.y"

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
//#line 869 "Parser.java"
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
case 145:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba sentencias ejecutables dentro del bloque if"); }
break;
case 149:
//#line 284 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 150:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 172:
//#line 331 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 332 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 174:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
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
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 178:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 179:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 339 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 182:
//#line 344 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1370 "Parser.java"
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
