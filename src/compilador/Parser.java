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






//#line 2 "./src/compilador/gramatica.y"
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
    0,    0,    0,    0,    1,    1,    2,    2,    4,    4,
    4,    4,    4,    4,   11,   11,   12,   12,   13,   13,
   13,   13,   13,   10,   10,    9,    9,    9,    9,    9,
    9,    9,    9,    9,    9,    9,   15,   19,   15,   15,
   15,   15,   15,   15,   15,   15,   15,    8,    8,    8,
    8,    8,    8,    8,    8,    8,   14,   14,   14,   20,
   14,   14,   14,   14,   14,   16,   16,   16,   18,   18,
   18,   18,   18,   18,   18,   23,   23,   24,   24,   21,
   21,   22,   22,    7,    7,    7,    7,    6,    6,    6,
    6,    6,    6,   27,   27,    5,    5,    5,   25,   25,
    3,    3,    3,    3,   28,   28,   28,   31,   31,   31,
   31,   31,   31,   34,   34,   36,   36,   36,   36,   30,
   30,   30,   30,   30,   30,   30,   30,   37,   37,   29,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   38,
   38,   38,   38,   38,   38,   41,   41,   42,   42,   42,
   40,   40,   40,   39,   33,   33,   32,   32,   32,   32,
   17,   17,   17,   43,   43,   43,   43,   43,   43,   26,
   26,   26,   44,   44,   44,   45,   45,   45,   46,   46,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    6,    6,    6,
    3,    4,    2,    5,    4,    3,    7,    0,   13,    6,
    6,    3,    4,    2,    5,    4,    3,    9,    7,    8,
    6,    8,    6,    8,    8,    6,    9,    7,    8,    0,
   15,    6,    8,    8,    6,    1,    3,    1,    1,    1,
    1,    3,    4,    5,    2,    1,    2,    1,    1,    1,
    2,    1,    2,    3,    2,    2,    3,    5,    4,    7,
    4,    3,    6,    1,    3,    4,    3,    3,    1,    3,
    1,    1,    1,    1,    3,    2,    2,    5,    4,    3,
    2,    4,    3,    2,    3,    3,    2,    1,    2,    5,
    7,    4,    3,    6,    5,    4,    6,    1,    2,    2,
    5,    4,    7,    6,    4,    3,    4,    3,    2,    4,
    3,    3,    2,    5,    4,    1,    2,    1,    1,    1,
    1,    3,    2,    2,    1,    3,    1,    1,    1,    1,
    3,    2,    2,    1,    1,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  157,  158,
  159,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,  101,  102,  103,  104,    0,    0,
    0,    0,    0,   86,    0,    0,   24,    0,    0,    0,
    0,    0,   33,    0,   68,   66,    0,    0,    0,  111,
    0,    0,    0,    2,    6,    0,    0,    0,  155,  107,
    0,    0,  130,    0,  179,  164,  165,  166,  167,    0,
    0,  168,  169,    0,    0,    0,    0,  175,  178,   87,
   84,  160,    0,  138,    0,    0,  136,    0,    0,    0,
    0,  118,    0,  128,    0,  123,    0,    0,   31,    0,
   99,   80,    0,    0,    0,    0,    0,    0,  113,    0,
    0,  110,    0,    1,    0,    0,   98,    0,  100,  105,
    0,    0,    0,  143,   17,   19,   20,   21,    0,    0,
   15,   18,   22,   23,  177,  180,    0,    0,    0,    0,
    0,    0,    0,    0,  132,    0,  154,  137,  135,  119,
    0,    0,    0,    0,  126,  129,    0,  122,    0,   67,
   81,   35,    0,   32,    0,    0,    0,  112,  109,  115,
   89,    0,    0,   96,  156,    0,    0,    0,   44,    0,
   71,   70,   69,    0,  141,  148,  149,  150,    0,  146,
  142,    0,   16,    0,    0,    0,    0,    0,    0,    0,
    0,  173,  174,    0,  131,  151,    0,    0,    0,    0,
  120,  116,  125,    0,   34,    0,    0,    0,  108,    0,
    0,   88,    0,    0,   42,   75,   82,    0,    0,    0,
    0,    0,  145,  147,  140,    0,    0,   53,    0,   56,
    0,    0,    0,    0,  134,  153,    0,  127,  124,   28,
   29,    0,   30,    0,    0,    0,    0,   72,    0,   83,
   46,    0,   43,    0,    0,  144,    0,    0,    0,    0,
   49,  133,  152,  121,   26,    0,   90,    0,    0,    0,
    0,   73,   78,   79,    0,   76,   45,    0,    0,    0,
   52,   54,   55,    0,   62,    0,   65,    0,    0,   74,
   77,   40,    0,   41,   48,    0,    0,    0,   58,    0,
   37,    0,   63,   64,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,    0,   61,
};
final static short yydgoto[] = {                         13,
   14,   15,  181,   46,  126,  127,  128,   21,   22,   23,
  130,  131,  183,  133,  134,   47,   74,  184,  312,  310,
  103,  228,  285,  286,   24,   75,  221,   25,   26,   27,
   28,   29,   61,   51,   30,   94,   95,   63,  206,  207,
  189,  190,   76,   77,   78,   79,
};
final static short yysindex[] = {                       909,
    0,  -32,  -30,  -34,  -19, -118,  503,  -86,    0,    0,
    0, 1158,    0,  979,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   35,    0,    0,    0,    0,  -22,  -89,
  675,    8,   16,    0,  -39,  -18,    0,  -91, -106,  300,
   -9,  225,    0,  688,    0,    0,  -37,  -85, -195,    0,
  -27,   67, 1000,    0,    0,  -25,   -3, -117,    0,    0,
   17, 1021,    0, -123,    0,    0,    0,    0,    0, -100,
  932,    0,    0,  120,  520,  -16,   11,    0,    0,    0,
    0,    0,  149,    0,  -95,  124,    0,  129,  130,  -93,
  300,    0,  -81,    0,  238,    0,  300,  273,    0,  819,
    0,    0,  226,  138,  148,   40,  819, -195,    0,   69,
   71,    0, -195,    0,  158,  526,    0,   64,    0,    0,
  -65,  165,  534,    0,    0,    0,    0,    0, 1042, 1063,
    0,    0,    0,    0,    0,    0, -176,  639,  -16,  -16,
  -16,   77,  -16,  -16,    0,  -26,    0,    0,    0,    0,
  -64,  300,  277,   18,    0,    0,  696,    0,  173,    0,
    0,    0,  171,    0,  740,  175,   94,    0,    0,    0,
    0,  -16,  184,    0,    0,  767,  201,  721,    0,  772,
    0,    0,    0,  -31,    0,    0,    0,    0, 1085,    0,
    0, 1107,    0,  932,  216,  932,  241, -131,   11,   11,
   77,    0,    0, -152,    0,    0,  128,  300,  792,  936,
    0,    0,    0,  257,    0,  261,  176,  264,    0,   83,
  206,    0,  958,  268,    0,    0,    0,  489,  285,  290,
   59,  819,    0,    0,    0, 1128,   70,    0,   90,    0,
  867,  309,  136, -152,    0,    0, 1099,    0,    0,    0,
    0,  311,    0,  -16,  312,  104,  846,    0,  720,    0,
    0,  333,    0,  796,  337,    0,  342,  344,  353,  139,
    0,    0,    0,    0,    0,   77,    0,  356,  958,  363,
 -114,    0,    0,    0, 1154,    0,    0,  364,  368,  366,
    0,    0,    0,  372,    0,  161,    0,  888,  391,    0,
    0,    0,  392,    0,    0,  393,  396,  185,    0,  -35,
    0,  178,    0,    0,  404,  415,  188,    0,  420,  422,
  958,  819,  208,  436,  958,  440,  230,    0,  449,    0,
};
final static short yyrindex[] = {                       496,
  -33,    0,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  119,    0,   -8,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  505,    0,    0,    0,    0,    0,    0,    0,
  157,    0,    0,    4,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  255,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  181,    0,    0,    0,    0,    0,    0,
    0,    0,  395,    0,  203,    0,    0,  246,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  484,  148,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1133,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  324,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,   73,
  486,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  490,
    0,    0,    0,    0,    0,    0,    0,    0,  590,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  347,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  375,    0,    0,  399,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  492,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  431,    0,    0,    0,    0,  275,    0,
    0,    0,  256,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  613,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  524,   29,   12,   92,  487,  698,  711,    0,    0,  498,
    0,  409,  -29,    0,    0,  -50,  -44,  247,    0,    0,
    0,    0,    0,  259,    0,  111,    0,    0,  870,    0,
    0,  891,  453,   30,  722,  842,  441,    0,   19,  338,
  359, -179,  480,   14,   66,    0,
};
final static int YYTABLESIZE=1435;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
   83,   84,  107,  176,   40,   36,   99,   31,  232,  234,
  160,   16,   99,   34,  205,  115,   97,  204,   45,   70,
  137,   60,   87,   16,   37,   16,  172,   99,   70,  152,
  100,   91,  132,   62,   99,  139,   49,  108,   50,  109,
  117,   70,   55,  176,  176,  176,  176,  176,  176,  170,
  176,   80,  143,   86,   88,  159,  234,  144,  208,   81,
  120,  212,  166,  176,   16,  176,  172,  172,    4,  172,
  172,  172,  171,  125,   56,  121,  121,  110,  111,  165,
   58,   55,   45,  164,  194,  195,  172,  198,  172,  170,
  170,   17,  170,  170,  170,   57,   25,  112,  264,  188,
  132,  146,  263,   17,   82,   17,  139,  174,  140,  170,
  113,  170,  171,  171,  139,  171,  171,  171,   85,  139,
  217,  140,    9,   10,   11,  139,  254,  140,  176,  241,
  242,  224,  171,  102,  171,  230,   25,  167,   38,  119,
  186,  125,  170,  237,   17,  239,  298,  299,  227,   45,
   96,  172,  199,  200,  135,   39,  106,  136,   85,  188,
  138,  147,  188,  151,  148,   89,  116,  118,  245,  149,
   48,  244,    4,  150,  170,   59,  272,    4,    4,  244,
   36,  162,   90,    9,   10,   11,  142,  265,  163,  145,
  270,  175,   89,  168,  161,  169,  106,  171,  260,    4,
  186,  171,   92,  186,  176,   45,  188,   45,  202,  203,
    9,   10,   11,  214,  215,  218,  252,   82,  219,  289,
   36,   25,   35,  160,  316,  246,   32,  222,   33,  284,
   82,   64,   65,  106,   59,    9,   10,   11,   82,  231,
   64,   65,   92,   85,  225,   97,  255,  186,    9,   10,
   11,  201,   45,   64,   65,  284,    9,   10,   11,  238,
  176,  246,  273,  176,  176,  176,  176,  176,  176,  176,
  176,  176,  176,  176,  176,  176,  176,  324,  176,  176,
  176,  106,  220,  172,  240,   97,  172,  172,  172,  172,
  172,  172,  172,  172,  172,  172,  172,  172,  172,  172,
  250,  172,  172,  172,  251,   36,  170,  253,  257,  170,
  170,  170,  170,  170,  170,  170,  170,  170,  170,  170,
  170,  170,  170,   91,  170,  170,  170,   92,  261,  171,
  262,  267,  171,  171,  171,  171,  171,  171,  171,  171,
  171,  171,  171,  171,  171,  171,   51,  171,  171,  171,
  160,  268,  271,   25,  275,  277,   25,   25,   25,   25,
   25,   25,  155,   91,  276,  278,   25,   25,   25,   25,
   97,   25,   25,   25,   27,   85,  287,  290,   85,   85,
   85,   85,   85,   85,   82,  291,   51,  292,   85,   85,
   85,   85,   82,   85,   85,   85,  293,  158,   93,  295,
  294,  211,    9,   10,   11,   82,  297,  302,  303,  304,
    9,   10,   11,  106,   27,  305,  106,  106,  106,  106,
  106,  106,  306,    9,   10,   11,  106,  106,  106,  106,
   50,  106,  106,  106,  309,  311,  313,   36,   93,  314,
   36,   36,   36,   36,   36,   36,  315,  318,   91,  317,
   36,   36,   36,   36,  319,   36,   36,   36,  320,   92,
  321,  322,   92,   92,   92,   92,   92,   92,  325,  256,
   50,   51,   92,   92,   92,   92,  326,   92,   92,   92,
   98,  101,  101,  328,    2,    2,   18,    3,    3,    5,
    5,  329,  330,   18,   89,    4,    7,    7,   18,   27,
   18,    4,   97,  281,    3,   97,   97,   97,   97,   97,
   97,  160,    9,   10,   11,   97,   97,   97,   97,  114,
   97,   97,   97,   93,  162,  296,  161,   38,   18,   89,
   94,  153,   95,   89,   60,   53,    4,  157,  193,   18,
    4,  243,   44,  301,  308,  154,   43,    9,   10,   11,
  236,    9,   10,   11,  141,   50,   89,   18,    0,  129,
    0,    0,  139,    4,  140,    0,  173,  323,  139,  172,
  140,  327,    0,  180,    9,   10,   11,  179,    0,   73,
   91,   72,    0,   91,   91,   91,   91,   91,   91,   18,
    0,  209,  210,   91,   91,   91,   91,    0,   91,   91,
   91,    0,    0,   51,    0,    0,   51,   51,   51,   51,
   51,   51,    0,  258,    0,    0,   51,   51,   51,   51,
  182,   51,   51,   51,   18,   42,  187,  192,    0,   47,
    0,   27,    0,    0,   27,   27,   27,   27,   27,   27,
    0,    0,    0,    0,   27,   27,   27,   27,  247,   27,
   27,   27,   59,    0,    0,   93,  178,    0,   93,   93,
   93,   93,   93,   93,    0,    0,    0,    0,   93,   93,
   93,   93,    0,   93,   93,   93,    0,    0,    0,    0,
   18,    0,   18,    0,    0,    0,  187,   50,    0,  187,
   50,   50,   50,   50,   50,   50,    0,   19,    0,    0,
   50,   50,   50,   50,   19,   50,   50,   50,    0,   19,
   20,   19,    0,    0,   47,   71,    0,   20,    0,   70,
  182,    0,   20,    0,   20,  259,    0,   18,  104,   52,
    0,    0,   70,  187,   73,    0,   72,   59,    0,   19,
    0,    0,    0,    0,    0,  101,    0,   73,  122,   72,
   19,    3,   20,    5,  182,    0,  283,    0,    0,    1,
  123,   42,    2,   20,    0,    3,    4,    5,   19,   52,
   52,    0,    6,   41,    7,    8,  182,    9,   10,   11,
  216,   20,  283,    0,   70,   66,   67,   68,   69,    0,
    1,    0,    0,  122,    0,  182,    3,    4,    5,   73,
   19,   72,    0,    6,  177,  123,    8,  223,    9,   10,
   11,   70,  229,   20,    0,    0,   70,    0,  182,    0,
  213,    0,  182,    0,    0,    0,   73,    0,   72,   52,
    0,   73,    0,   72,   52,   19,  288,    0,    0,    0,
   70,    0,    0,    0,  282,  226,   47,    0,   20,   47,
   47,   47,   47,   47,   47,   73,    0,   72,    0,   47,
   47,   47,   47,   70,   47,   47,   47,    0,    0,   59,
    0,    0,   59,   59,   59,   59,   59,   59,   73,    0,
   72,    0,   59,   59,   59,   59,    0,   59,   59,   59,
    0,   19,    0,   19,    0,    1,    0,    0,    2,  196,
  197,    3,    4,    5,   20,    0,   20,   92,    6,   92,
    7,    8,    0,    9,   10,   11,  248,    0,    0,    0,
    0,    0,    0,    0,    0,   85,   85,    0,   93,    0,
   93,   64,   65,    0,    0,    0,  156,    0,   19,  156,
   66,   67,   68,   69,   64,   65,    0,    0,    0,    0,
    0,   20,   89,   66,   67,   68,   69,    0,    0,    4,
   92,    0,    0,    0,   92,    0,   92,   92,  178,    0,
    9,   10,   11,   85,    0,    0,  101,  101,    0,  122,
  122,   93,    3,    3,    5,   93,    0,   93,   93,   42,
    0,  123,  123,    0,  156,    0,   64,   65,  156,    0,
    0,    0,    0,    0,    0,   66,   67,   68,   69,    0,
  178,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   92,   92,   64,   65,    0,   92,    0,   64,   65,
    0,   12,   66,   67,   68,   69,   85,   66,   67,   68,
   69,   93,   93,   93,    0,    0,    0,   93,   89,    0,
  156,  156,   64,   65,   42,    4,    0,    0,    0,    0,
  249,   66,   67,   68,   69,    0,    9,   10,   11,    0,
    0,    0,    0,    0,    0,   64,   65,   92,   92,   92,
  178,    0,    0,    0,   66,   67,   68,   69,  156,    0,
    0,    0,    0,    0,   85,    0,    0,   85,   93,   93,
   93,    0,    1,   54,    0,  122,  279,  280,    3,    4,
    5,    0,    0,    0,    0,    6,   92,  123,    8,    0,
    9,   10,   11,    1,  114,    0,    2,    0,  269,    3,
    4,    5,    0,   85,   85,    0,    6,   93,    7,    8,
    0,    9,   10,   11,    1,  124,    0,  122,    0,  307,
    3,    4,    5,    0,    0,    0,    0,    6,    0,  123,
    8,    0,    9,   10,   11,    1,  185,    0,    2,    0,
    0,    3,    4,    5,    0,    0,    0,    0,    6,    0,
    7,    8,    0,    9,   10,   11,    0,  191,    1,    0,
    0,    2,   89,    0,    3,    4,    5,    0,    0,    4,
    0,    6,    0,    7,    8,    0,    9,   10,   11,  233,
    9,   10,   11,    0,    1,    0,    0,  122,    0,    0,
    3,    4,    5,  274,    0,    0,    0,    6,    0,  123,
    8,  235,    9,   10,   11,    1,    0,    0,    2,    0,
    0,    3,    4,    5,    0,    0,    0,    0,    6,    0,
    7,    8,  266,    9,   10,   11,    1,  117,    0,    2,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    6,
    0,    7,    8,    0,    9,   10,   11,    1,  300,    0,
  122,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,  123,    8,    0,    9,   10,   11,    1,    0,
    0,  122,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,    0,  123,    8,    0,    9,   10,   11,    1,
    0,    0,  122,    0,    0,    3,    4,    5,    0,    0,
    0,    0,    6,    0,  123,    8,    0,    9,   10,   11,
    0,    1,    0,    0,  122,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,   89,  123,    8,    0,    9,
   10,   11,    4,    1,    0,    0,  122,    0,    0,    3,
    4,    5,    0,    9,   10,   11,    6,    0,  123,    8,
    0,    9,   10,   11,    1,    0,    0,  122,    0,  117,
    3,    4,    5,    0,    0,    0,  117,    6,    0,  123,
    8,    0,    9,   10,   11,    0,    0,  117,  117,  117,
  101,    0,    0,  122,    1,    0,    3,    2,    5,    0,
    3,    4,    5,    0,    0,  123,    0,    6,    0,    7,
    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
   40,   41,   40,    0,  123,   40,   40,   40,   40,  189,
   44,    0,   46,   44,   41,   41,  123,   44,    7,   45,
   71,   44,   41,   12,   44,   14,    0,   61,   45,  123,
   40,  123,   62,  123,   44,   44,  123,  123,  125,  125,
   44,   45,   14,   40,   41,   42,   43,   44,   45,    0,
   47,   44,   42,   35,   36,  100,  236,   47,  123,   44,
   44,   44,  107,   60,   53,   62,   40,   41,  264,   43,
   44,   45,    0,   62,   40,   59,   59,   48,   49,   40,
   46,   53,   71,   44,  261,  262,   60,  138,   62,   40,
   41,    0,   43,   44,   45,   61,    0,  125,   40,  129,
  130,   83,   44,   12,  257,   14,   43,   44,   45,   60,
   44,   62,   40,   41,  123,   43,   44,   45,    0,   43,
  165,   45,  275,  276,  277,   43,   44,   45,  125,  261,
  262,  176,   60,   42,   62,  180,   40,  108,  257,  257,
  129,  130,  113,  194,   53,  196,  261,  262,  178,  138,
  257,  125,  139,  140,  278,  274,    0,  258,   40,  189,
   41,  257,  192,  257,   41,  257,   56,   57,   41,   41,
  257,   44,  264,   44,  125,  257,   41,  264,  264,   44,
    0,   44,  274,  275,  276,  277,   76,  232,   41,   41,
  241,  257,  257,  125,  103,  125,   40,  125,  228,  264,
  189,   44,    0,  192,   40,  194,  236,  196,  143,  144,
  275,  276,  277,   41,   44,   41,   41,  257,  125,  264,
   40,  125,  257,  257,  260,  207,  257,   44,  259,  259,
  257,  257,  258,  271,  257,  275,  276,  277,  257,  271,
  257,  258,   40,  125,   44,    0,   41,  236,  275,  276,
  277,  141,  241,  257,  258,  285,  275,  276,  277,   44,
  257,  243,  244,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  272,  273,  322,  275,  276,
  277,  125,  172,  257,   44,   40,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   44,  275,  276,  277,   44,  125,  257,   44,   41,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  272,  273,    0,  275,  276,  277,  125,   44,  257,
   41,  262,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,    0,  275,  276,  277,
  125,  262,   44,  257,   44,   44,  260,  261,  262,  263,
  264,  265,  125,   40,  254,  262,  270,  271,  272,  273,
  125,  275,  276,  277,    0,  257,   44,   41,  260,  261,
  262,  263,  264,  265,  257,   44,   40,   44,  270,  271,
  272,  273,  257,  275,  276,  277,   44,  125,    0,   44,
  262,  125,  275,  276,  277,  257,   44,   44,   41,   44,
  275,  276,  277,  257,   40,   44,  260,  261,  262,  263,
  264,  265,  262,  275,  276,  277,  270,  271,  272,  273,
    0,  275,  276,  277,   44,   44,   44,  257,   40,   44,
  260,  261,  262,  263,  264,  265,  262,   44,  125,  272,
  270,  271,  272,  273,   40,  275,  276,  277,  271,  257,
   41,   40,  260,  261,  262,  263,  264,  265,  261,  223,
   40,  125,  270,  271,  272,  273,   41,  275,  276,  277,
   40,  257,  257,   44,  260,  260,    0,  263,  263,  265,
  265,  262,   44,    7,  257,    0,  272,  272,   12,  125,
   14,  264,  257,  257,    0,  260,  261,  262,  263,  264,
  265,  257,  275,  276,  277,  270,  271,  272,  273,  125,
  275,  276,  277,  125,   41,  279,   41,  272,   42,  257,
   41,   91,   41,  257,  260,   12,  264,   97,  130,   53,
  264,  204,   40,  285,  298,   93,   44,  275,  276,  277,
  192,  275,  276,  277,   75,  125,  257,   71,   -1,   62,
   -1,   -1,   43,  264,   45,   -1,   41,  321,   43,   44,
   45,  325,   -1,   40,  275,  276,  277,   44,   -1,   60,
  257,   62,   -1,  260,  261,  262,  263,  264,  265,  103,
   -1,  151,  152,  270,  271,  272,  273,   -1,  275,  276,
  277,   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,
  264,  265,   -1,  125,   -1,   -1,  270,  271,  272,  273,
  123,  275,  276,  277,  138,  123,  129,  130,   -1,   40,
   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,  208,  275,
  276,  277,   40,   -1,   -1,  257,  123,   -1,  260,  261,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,   -1,   -1,
  194,   -1,  196,   -1,   -1,   -1,  189,  257,   -1,  192,
  260,  261,  262,  263,  264,  265,   -1,    0,   -1,   -1,
  270,  271,  272,  273,    7,  275,  276,  277,   -1,   12,
    0,   14,   -1,   -1,  125,   41,   -1,    7,   -1,   45,
  223,   -1,   12,   -1,   14,  228,   -1,  241,   41,    8,
   -1,   -1,   45,  236,   60,   -1,   62,  125,   -1,   42,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   60,  260,   62,
   53,  263,   42,  265,  257,   -1,  259,   -1,   -1,  257,
  272,  123,  260,   53,   -1,  263,  264,  265,   71,   48,
   49,   -1,  270,  271,  272,  273,  279,  275,  276,  277,
   41,   71,  285,   -1,   45,  266,  267,  268,  269,   -1,
  257,   -1,   -1,  260,   -1,  298,  263,  264,  265,   60,
  103,   62,   -1,  270,  271,  272,  273,   41,  275,  276,
  277,   45,   41,  103,   -1,   -1,   45,   -1,  321,   -1,
  125,   -1,  325,   -1,   -1,   -1,   60,   -1,   62,  108,
   -1,   60,   -1,   62,  113,  138,   41,   -1,   -1,   -1,
   45,   -1,   -1,   -1,  125,  125,  257,   -1,  138,  260,
  261,  262,  263,  264,  265,   60,   -1,   62,   -1,  270,
  271,  272,  273,   45,  275,  276,  277,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   60,   -1,
   62,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
   -1,  194,   -1,  196,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  194,   -1,  196,   38,  270,   40,
  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   35,   36,   -1,   38,   -1,
   40,  257,  258,   -1,   -1,   -1,   95,   -1,  241,   98,
  266,  267,  268,  269,  257,  258,   -1,   -1,   -1,   -1,
   -1,  241,  257,  266,  267,  268,  269,   -1,   -1,  264,
   91,   -1,   -1,   -1,   95,   -1,   97,   98,  123,   -1,
  275,  276,  277,   83,   -1,   -1,  257,  257,   -1,  260,
  260,   91,  263,  263,  265,   95,   -1,   97,   98,  123,
   -1,  272,  272,   -1,  153,   -1,  257,  258,  157,   -1,
   -1,   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
  123,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  151,  152,  153,  257,  258,   -1,  157,   -1,  257,  258,
   -1,  123,  266,  267,  268,  269,  146,  266,  267,  268,
  269,  151,  152,  153,   -1,   -1,   -1,  157,  257,   -1,
  209,  210,  257,  258,  123,  264,   -1,   -1,   -1,   -1,
  125,  266,  267,  268,  269,   -1,  275,  276,  277,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  208,  209,  210,
  123,   -1,   -1,   -1,  266,  267,  268,  269,  247,   -1,
   -1,   -1,   -1,   -1,  204,   -1,   -1,  207,  208,  209,
  210,   -1,  257,  125,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  247,  272,  273,   -1,
  275,  276,  277,  257,  125,   -1,  260,   -1,  262,  263,
  264,  265,   -1,  243,  244,   -1,  270,  247,  272,  273,
   -1,  275,  276,  277,  257,  125,   -1,  260,   -1,  262,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,  257,  125,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,
  272,  273,   -1,  275,  276,  277,   -1,  125,  257,   -1,
   -1,  260,  257,   -1,  263,  264,  265,   -1,   -1,  264,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,  125,
  275,  276,  277,   -1,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,  265,  125,   -1,   -1,   -1,  270,   -1,  272,
  273,  125,  275,  276,  277,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,
  272,  273,  125,  275,  276,  277,  257,  125,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,  257,  125,   -1,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,  257,   -1,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  257,  272,  273,   -1,  275,
  276,  277,  264,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,  265,   -1,  275,  276,  277,  270,   -1,  272,  273,
   -1,  275,  276,  277,  257,   -1,   -1,  260,   -1,  257,
  263,  264,  265,   -1,   -1,   -1,  264,  270,   -1,  272,
  273,   -1,  275,  276,  277,   -1,   -1,  275,  276,  277,
  257,   -1,   -1,  260,  257,   -1,  263,  260,  265,   -1,
  263,  264,  265,   -1,   -1,  272,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,
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
"bloque_encabezado_funcion : encabezado_funcion ',' bloque_encabezado_funcion",
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
		Double parsedDouble = Double.parseDouble(negConstante);
		
		if (parsedDouble < -1.17549435E-38 && -3.40282347E+38 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-3.40282347E+38 < parsedDouble) {
				negConstante = new String("-3.40282347E+38");
			} else {
				negConstante = new String("-1.17549435E-38");
			}
		}
		
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
//#line 896 "Parser.java"
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
//#line 18 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 19 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' al principio del programa"); }
break;
case 3:
//#line 20 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '}' al final del programa"); }
break;
case 4:
//#line 21 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Programa vacio"); }
break;
case 14:
//#line 40 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
break;
case 25:
//#line 63 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego del RETURN"); }
break;
case 26:
//#line 67 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 27:
//#line 68 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 28:
//#line 69 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 29:
//#line 70 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia DO WHILE"); }
break;
case 30:
//#line 71 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta WHILE en sentencia DO WHILE"); }
break;
case 31:
//#line 72 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 32:
//#line 73 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia DO WHILE"); }
break;
case 33:
//#line 74 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 34:
//#line 75 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 35:
//#line 76 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 36:
//#line 77 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 37:
//#line 81 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 38:
//#line 82 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 39:
//#line 83 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 40:
//#line 84 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia DO WHILE"); }
break;
case 41:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta WHILE en sentencia DO WHILE"); }
break;
case 42:
//#line 86 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 43:
//#line 87 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia DO WHILE"); }
break;
case 44:
//#line 88 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 45:
//#line 89 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 46:
//#line 90 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 47:
//#line 91 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia DO WHILE"); }
break;
case 48:
//#line 95 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 49:
//#line 96 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 50:
//#line 97 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 51:
//#line 98 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 52:
//#line 99 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF ELSE"); }
break;
case 53:
//#line 100 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF"); }
break;
case 54:
//#line 101 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 55:
//#line 102 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 56:
//#line 103 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF"); }
break;
case 57:
//#line 107 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 58:
//#line 108 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 59:
//#line 109 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 60:
//#line 110 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 61:
//#line 111 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF ELSE"); }
break;
case 62:
//#line 112 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF"); }
break;
case 63:
//#line 113 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 64:
//#line 114 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 65:
//#line 115 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF"); }
break;
case 68:
//#line 121 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 71:
//#line 127 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 75:
//#line 131 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en bloque de sentencias ejecutables"); }
break;
case 76:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 77:
//#line 136 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 84:
//#line 155 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 85:
//#line 156 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 86:
//#line 157 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta CADENA en Sentencia PRINT"); }
break;
case 87:
//#line 158 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 88:
//#line 162 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 89:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 90:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 91:
//#line 165 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 92:
//#line 166 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 93:
//#line 167 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 96:
//#line 176 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 97:
//#line 177 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 98:
//#line 178 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
break;
case 105:
//#line 194 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 106:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); }
break;
case 107:
//#line 196 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
break;
case 109:
//#line 201 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 110:
//#line 202 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 111:
//#line 203 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 112:
//#line 204 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 113:
//#line 205 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 116:
//#line 214 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 117:
//#line 215 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en delcaracion de lista de variables en CLASS"); }
break;
case 120:
//#line 221 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 121:
//#line 222 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 122:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 123:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 124:
//#line 225 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en IMPLEMENT de clase"); }
break;
case 125:
//#line 226 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 126:
//#line 227 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 127:
//#line 228 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 130:
//#line 237 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 133:
//#line 243 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 134:
//#line 244 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 135:
//#line 245 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 136:
//#line 246 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 137:
//#line 247 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 138:
//#line 248 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 139:
//#line 249 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 142:
//#line 255 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 143:
//#line 256 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 146:
//#line 262 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 147:
//#line 263 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 162:
//#line 296 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 163:
//#line 297 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 179:
//#line 328 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 180:
//#line 329 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1437 "Parser.java"
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
