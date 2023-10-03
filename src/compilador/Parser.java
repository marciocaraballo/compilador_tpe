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
   14,   14,   14,   14,   14,   16,   16,   18,   18,   18,
   18,   18,   23,   23,   24,   24,   21,   21,   22,   22,
    7,    7,    7,    7,    6,    6,    6,    6,    6,    6,
   27,   27,    5,    5,    5,   25,   25,    3,    3,    3,
    3,   28,   28,   28,   31,   31,   31,   31,   31,   31,
   34,   34,   36,   36,   36,   36,   30,   30,   30,   30,
   30,   30,   30,   30,   37,   37,   29,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   38,   38,   38,   38,
   38,   38,   41,   41,   42,   42,   42,   40,   40,   40,
   39,   33,   33,   32,   32,   32,   32,   17,   43,   43,
   43,   43,   43,   43,   26,   26,   26,   44,   44,   44,
   45,   45,   45,   46,   46,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    6,    6,    6,
    3,    4,    2,    5,    4,    3,    7,    0,   13,    6,
    6,    3,    4,    2,    5,    4,    3,    9,    7,    8,
    6,    8,    6,    8,    8,    6,    9,    7,    8,    0,
   15,    6,    8,    8,    6,    1,    3,    1,    1,    3,
    4,    5,    1,    2,    1,    1,    1,    2,    1,    2,
    3,    2,    2,    3,    5,    4,    7,    4,    3,    6,
    1,    3,    4,    3,    3,    1,    3,    1,    1,    1,
    1,    3,    2,    2,    5,    4,    3,    2,    4,    3,
    2,    3,    3,    2,    1,    2,    5,    7,    4,    3,
    6,    5,    4,    6,    1,    2,    2,    5,    4,    7,
    6,    4,    3,    4,    3,    2,    4,    3,    3,    2,
    5,    4,    1,    2,    1,    1,    1,    1,    3,    2,
    2,    1,    3,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  154,  155,
  156,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   98,   99,  100,  101,    0,    0,
    0,    0,    0,   83,    0,    0,   24,    0,    0,    0,
   96,    0,    0,   33,    0,   66,    0,    0,    0,  108,
    0,    0,    0,    2,    6,    0,    0,    0,  152,  104,
    0,    0,  127,    0,  174,    0,    0,    0,    0,    0,
  170,  173,   84,   81,  157,    0,  135,    0,    0,  133,
    0,    0,    0,    0,  115,    0,  125,    0,  120,    0,
    0,   31,    0,   77,    0,    0,    0,    0,    0,    0,
  110,    0,    0,  107,    0,    1,    0,    0,   95,    0,
   97,  102,    0,    0,    0,  140,   17,   19,   20,   21,
    0,    0,   15,   18,   22,   23,  172,  175,    0,    0,
  159,  160,  161,  162,    0,    0,  163,  164,    0,    0,
    0,  129,    0,  151,  134,  132,  116,    0,    0,    0,
    0,  123,  126,    0,  119,    0,   67,   78,   35,    0,
   32,    0,    0,    0,  109,  106,  112,   86,    0,    0,
   93,  153,    0,    0,    0,   44,    0,   69,   68,    0,
  138,  145,  146,  147,    0,  143,  139,    0,   16,    0,
    0,    0,    0,    0,    0,    0,    0,  168,  169,    0,
  128,  148,    0,    0,    0,    0,  117,  113,  122,    0,
   34,    0,    0,    0,  105,    0,    0,   85,    0,    0,
   42,   79,    0,    0,    0,    0,    0,  142,  144,  137,
    0,    0,   53,    0,   56,    0,    0,    0,    0,  131,
  150,    0,  124,  121,   28,   29,    0,   30,    0,    0,
    0,    0,   70,    0,   80,   46,    0,   43,    0,    0,
  141,    0,    0,    0,    0,   49,  130,  149,  118,   26,
    0,   87,    0,    0,    0,    0,   71,   75,   76,    0,
   73,   45,    0,    0,    0,   52,   54,   55,    0,   62,
    0,   65,    0,    0,   72,   74,   40,    0,   41,   48,
    0,    0,    0,   58,    0,   37,    0,   63,   64,    0,
    0,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,   61,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   46,  118,  119,  120,   21,   22,   23,
  122,  123,  179,  125,  126,   47,   68,  180,  307,  305,
   95,  223,  280,  281,   24,   69,  217,   25,   85,   27,
   28,   86,   61,   51,   30,   87,   88,   63,  202,  203,
  185,  186,  139,   70,   71,   72,
};
final static short yysindex[] = {                       642,
    0,  -30,   -4,  -34,  -14,  -65,  489,  -66,    0,    0,
    0,  926,    0,  665,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   35,    0,    0,    0,    0,  -36,  -84,
  -23,   28,   62,    0,  -37,  -25,    0,  468,  -94,  318,
    0,   39,  344,    0,  -13,    0,  -40,  -52, -147,    0,
    2,   92,  688,    0,    0,   94,  171, -116,    0,    0,
   45,  709,    0, -131,    0, -101,  888,  119,  628,   77,
    0,    0,    0,    0,    0,  284,    0,  -91,  128,    0,
  130,  133,  -92,  318,    0,  -76,    0,  551,    0,  318,
  589,    0,  -28,    0,  265,  140,  153,   98,  -28, -147,
    0,   79,   81,    0, -147,    0,  166,    8,    0,  157,
    0,    0,  -43,  177,  539,    0,    0,    0,    0,    0,
  732,  753,    0,    0,    0,    0,    0,    0, -162,  578,
    0,    0,    0,    0,  -28,  -28,    0,    0,  -28,  -28,
  -28,    0,  -29,    0,    0,    0,    0,  -89,  318,  656,
   61,    0,    0,  723,    0,  239,    0,    0,    0,  193,
    0,  114,  285,  135,    0,    0,    0,    0,  -28,  304,
    0,    0,  117,  305, -132,    0,  138,    0,    0,  -38,
    0,    0,    0,    0,  775,    0,    0,  798,    0,  888,
  315,  888,  317, -108,   77,   77,  131,    0,    0, -154,
    0,    0,  126,  318,  767,  824,    0,    0,    0,  338,
    0,  341,  287,  350,    0,  182,  288,    0,  889,  321,
    0,    0,  902,  353,  358,  108,  -28,    0,    0,    0,
  819,   21,    0,  146,    0,  867,  365,  141, -154,    0,
    0,  829,    0,    0,    0,    0,  375,    0,  -28,  387,
  170,  853,    0,  912,    0,    0,  395,    0,  148,  399,
    0,  397,  398,  407,  190,    0,    0,    0,    0,    0,
  131,    0,  410,  889,  411,   -5,    0,    0,    0,  916,
    0,    0,  419,  423,  421,    0,    0,    0,  426,    0,
  215,    0,  871,  434,    0,    0,    0,  444,    0,    0,
  449,  453,  236,    0,  240,    0,  229,    0,    0,  464,
  470,  245,    0,  479,  471,  889,  -28,  260,  485,  889,
  483,  269,    0,  491,    0,
};
final static short yyrindex[] = {                       532,
  -35,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,    0,  -11,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  538,    0,    0,    0,    0,    0,    0,    0,
  279,    0,    0,    1,    0,    0,    0,    0,    0,   24,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  289,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  150,    0,    0,    0,    0,
    0,    0,    0,    0,  420,    0,  173,    0,    0,  196,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  845,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  219,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,   70,  507,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  509,    0,    0,    0,    0,
    0,    0,    0,  422,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  242,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  360,    0,    0,  384,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  517,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  447,    0,
    0,    0,    0,  302,    0,    0,    0,  291,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  610,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  554,   48,  -24, 1144,    7,   13,  585,    0,    0,  543,
    0,  446,  667,    0,    0,  -12,  441,  531,    0,    0,
    0,    0,    0,  290,    0,  418,    0,    0,   23,    0,
    0,  553,  487,   29,   46,  614,  529,    0,  488,  371,
  386, -149,    0,  167,  165,    0,
};
final static int YYTABLESIZE=1239;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         99,
  171,  227,   76,   77,   96,   36,   18,   60,  157,   31,
   96,  201,   19,   18,  200,   80,   66,   67,   18,   19,
   18,   66,   26,  167,   19,   96,   19,   96,   90,   37,
  149,   66,  136,  204,   26,  229,   26,  117,   62,   34,
  171,  171,  171,  171,  171,  171,  165,  171,  170,   18,
  135,  169,  136,   52,  129,   19,   49,   40,   50,   18,
  171,   55,  171,  167,  167,   19,  167,  167,  167,  166,
  100,   73,  101,   18,   56,   26,  102,  103,   93,   19,
   58,  229,   92,  167,   26,  167,  165,  165,  112,  165,
  165,  165,   25,   52,   52,   57,  182,  117,  190,  191,
   55,   18,   75,  113,  208,   74,  165,   19,  165,  166,
  166,  136,  166,  166,  166,   82,    4,  194,  140,  113,
    9,   10,   11,  141,   41,  171,  104,  114,  164,  166,
    3,  166,   25,  167,  107,  105,   18,  162,   66,  115,
  111,  161,   19,   26,   26,   52,  127,  259,  167,   36,
   52,  258,  236,  237,  212,   82,  128,  219,   66,  130,
  182,   66,   89,  182,  148,  144,  240,   82,  145,  239,
  146,  165,   89,  135,    4,  136,  147,  232,  224,  234,
   59,  267,   66,  159,  239,    9,   10,   11,  283,   36,
   48,   38,   66,  160,  166,   94,   18,    4,   18,  135,
  171,  136,   19,  165,   19,  166,  182,   26,   39,  168,
   26,    4,   89,  172,  109,   66,  173,   25,   88,   75,
   59,  157,   35,  265,  135,  249,  136,   75,   64,   65,
   98,   75,  226,   64,   65,   94,  211,    9,   10,   11,
   82,   51,   18,   64,   65,    9,   10,   11,   19,    9,
   10,   11,   32,   26,   33,  293,  294,  171,   88,  215,
  171,  171,  171,  171,  171,  171,  171,  171,  171,  171,
  171,  171,  171,  171,   36,  171,  171,  171,  103,  210,
  167,   51,  262,  167,  167,  167,  167,  167,  167,  167,
  167,  167,  167,  167,  167,  167,  167,   89,  167,  167,
  167,  195,  196,  165,  198,  199,  165,  165,  165,  165,
  165,  165,  165,  165,  165,  165,  165,  165,  165,  165,
   94,  165,  165,  165,  142,  214,  166,  247,  250,  166,
  166,  166,  166,  166,  166,  166,  166,  166,  166,  166,
  166,  166,  166,   88,  166,  166,  166,  218,  221,   25,
   64,   65,   25,   25,   25,   25,   25,   25,  233,   27,
  235,  252,   25,   25,   25,   25,   51,   25,   25,   25,
   64,   65,   82,   64,   65,   82,   82,   82,   82,   82,
   82,  245,   75,   90,  246,   82,   82,   82,   82,  157,
   82,   82,   82,  248,   64,   65,  256,   75,  257,   27,
    9,   10,   11,  103,   64,   65,   36,  263,  266,   36,
   36,   36,   36,   36,   36,    9,   10,   11,  270,   36,
   36,   36,   36,   90,   36,   36,   36,   64,   65,   89,
  272,  273,   89,   89,   89,   89,   89,   89,  282,  285,
  286,  287,   89,   89,   89,   89,   50,   89,   89,   89,
  288,  289,   94,  290,  292,   94,   94,   94,   94,   94,
   94,   47,  297,  298,  299,   94,   94,   94,   94,  300,
   94,   94,   94,  108,  110,   88,  301,  304,   88,   88,
   88,   88,   88,   88,   27,   97,   50,  306,   88,   88,
   88,   88,  308,   88,   88,   88,  309,  310,   51,  311,
  312,   51,   51,   51,   51,   51,   51,  313,   90,  314,
  317,   51,   51,   51,   51,  315,   51,   51,   51,  316,
  320,   41,   79,   81,    2,  321,  323,    3,   45,    5,
  324,    4,   44,  156,  325,  103,    7,    3,  103,  163,
   75,  103,  103,  103,  111,  157,   47,  158,  103,   91,
  103,  103,   29,  103,  103,  103,  197,   92,    9,   10,
   11,   60,   38,  143,   29,   53,   29,  189,   91,  296,
  238,   50,  151,  231,   82,    0,    0,    0,  177,    0,
    0,    4,  176,    0,   20,    0,  216,   78,   78,    0,
   84,   20,    9,   10,   11,    0,   20,    0,   20,    0,
   41,    0,  213,    2,  121,   29,    3,    0,    5,    0,
    0,   43,  150,  220,   29,    7,   27,  225,  154,   27,
   27,   27,   27,   27,   27,    0,    0,   20,   78,   27,
   27,   27,   27,    0,   27,   27,   27,   20,    0,    0,
   90,    0,    0,   90,   90,   90,   90,   90,   90,   59,
    0,   20,    0,   90,   90,   90,   90,  178,   90,   90,
   90,  175,    0,  183,  188,    0,  271,  260,    0,    0,
  135,    0,  136,   29,   29,  152,  205,  206,   47,   20,
    0,   47,   47,   47,   47,   47,   47,  138,    0,  137,
  241,   47,   47,   47,   47,   78,   47,   47,   47,  284,
   43,  153,    0,   50,  153,    0,   50,   50,   50,   50,
   50,   50,    0,  155,   20,    0,   50,   50,   50,   50,
    0,   50,   50,   50,   82,  241,  268,  183,  124,    0,
  183,    4,  242,    0,   59,    0,    0,   29,    0,    0,
   29,   83,    9,   10,   11,   41,    0,    0,    2,  251,
    0,    3,   78,    5,    0,   78,    0,  319,    0,   42,
    7,  178,    0,  153,   12,  254,    0,  153,    0,    0,
    0,    0,    0,  183,   20,    0,   20,    0,    0,    0,
  207,    0,  276,   29,    0,    0,    0,  184,  124,   54,
   78,   78,    0,    0,  178,   41,  278,    0,  114,    0,
    0,    3,    0,    5,  291,    0,    0,   82,    0,  174,
  115,    0,  106,    0,    4,    0,  178,    0,  153,  153,
   20,    0,  278,  303,    0,    9,   10,   11,    0,    0,
    0,    0,    0,  116,   41,  178,    0,    2,  192,  193,
    3,  222,    5,    0,    0,   82,  318,  209,    0,    7,
  322,  184,    4,    0,  184,  153,  181,    0,  178,    0,
    0,    0,  178,    9,   10,   11,   59,    0,    0,   59,
   59,   59,   59,   59,   59,    0,    0,  187,    0,   59,
   59,   59,   59,    0,   59,   59,   59,    0,    0,  255,
    0,  243,    0,  131,  132,  133,  134,  184,    1,  228,
    0,    2,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,   82,    7,    8,    0,    9,   10,   11,    4,
  279,    1,  230,    0,    2,    0,    0,    3,    4,    5,
    9,   10,   11,    0,    6,    0,    7,    8,    0,    9,
   10,   11,    0,  261,    1,    0,  279,    2,  244,    0,
    3,    4,    5,  269,    0,    0,    0,    6,    0,    7,
    8,    0,    9,   10,   11,    1,    0,    0,  114,  114,
    0,    3,    4,    5,    0,  175,    0,    0,    6,   82,
  115,    8,    0,    9,   10,   11,    4,    0,    1,   43,
    0,  114,    0,  175,    3,    4,    5,    9,   10,   11,
    0,    6,    0,  115,    8,    0,    9,   10,   11,    1,
   43,  175,  114,    0,    0,    3,    4,    5,    0,    0,
    0,    0,    6,   82,  115,    8,  253,    9,   10,   11,
    4,    1,    0,    0,  114,    0,  277,    3,    4,    5,
  295,    9,   10,   11,    6,    0,  115,    8,    0,    9,
   10,   11,    0,    0,    1,    0,    0,  114,    0,    0,
    3,    4,    5,    0,    0,    0,    0,    6,    0,  115,
    8,    0,    9,   10,   11,    1,    0,    0,  114,    0,
   82,    3,    4,    5,    0,   82,    0,    4,    6,    0,
  115,    8,    4,    9,   10,   11,    0,    0,    9,   10,
   11,  114,    0,    9,   10,   11,    0,    0,  114,   41,
    0,    0,  114,  274,  275,    3,    0,    5,    0,  114,
  114,  114,    0,   41,  115,    0,    2,   41,  264,    3,
  114,    5,  302,    3,    0,    5,    0,    0,    7,    0,
    0,    0,  115,   17,   41,   41,    0,    2,  114,    0,
    3,    3,    5,    5,    0,   17,    0,   17,   41,    7,
  115,  114,    0,    0,    3,    0,    5,    0,   41,    0,
    0,  114,   41,  115,    3,  114,    5,    0,    3,    0,
    5,    0,    1,  115,    0,    2,   94,  115,    3,    4,
    5,    0,    0,    0,    0,    6,   17,    7,    8,    0,
    9,   10,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  158,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   41,   40,   40,    0,   44,   44,   40,
   46,   41,    0,    7,   44,   41,   45,   41,   12,    7,
   14,   45,    0,    0,   12,   61,   14,   41,  123,   44,
  123,   45,   44,  123,   12,  185,   14,   62,  123,   44,
   40,   41,   42,   43,   44,   45,    0,   47,   41,   43,
   43,   44,   45,    8,   67,   43,  123,  123,  125,   53,
   60,   14,   62,   40,   41,   53,   43,   44,   45,    0,
  123,   44,  125,   67,   40,   53,   48,   49,   40,   67,
   46,  231,   44,   60,   62,   62,   40,   41,   44,   43,
   44,   45,    0,   48,   49,   61,  121,  122,  261,  262,
   53,   95,  257,   59,   44,   44,   60,   95,   62,   40,
   41,  123,   43,   44,   45,    0,  264,  130,   42,   59,
  275,  276,  277,   47,  257,  125,  125,  260,  100,   60,
  263,   62,   40,  105,   41,   44,  130,   40,   45,  272,
  257,   44,  130,  121,  122,  100,  278,   40,  125,    0,
  105,   44,  261,  262,   41,   40,  258,   41,   45,   41,
  185,   45,  257,  188,  257,  257,   41,  257,   41,   44,
   41,  125,    0,   43,  264,   45,   44,  190,   41,  192,
  257,   41,   45,   44,   44,  275,  276,  277,   41,   40,
  257,  257,   45,   41,  125,    0,  190,  264,  192,   43,
   44,   45,  190,  125,  192,  125,  231,  185,  274,   44,
  188,  264,   40,  257,   44,   45,   40,  125,    0,  257,
  257,  257,  257,  236,   43,   44,   45,  257,  257,  258,
  271,  257,  271,  257,  258,   40,   44,  275,  276,  277,
  125,    0,  236,  257,  258,  275,  276,  277,  236,  275,
  276,  277,  257,  231,  259,  261,  262,  257,   40,  125,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,   41,
  257,   40,  262,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  272,  273,  125,  275,  276,
  277,  135,  136,  257,  140,  141,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
  125,  275,  276,  277,   41,   41,  257,   41,   41,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  272,  273,  125,  275,  276,  277,   44,   44,  257,
  257,  258,  260,  261,  262,  263,  264,  265,   44,    0,
   44,   41,  270,  271,  272,  273,  125,  275,  276,  277,
  257,  258,  257,  257,  258,  260,  261,  262,  263,  264,
  265,   44,  257,    0,   44,  270,  271,  272,  273,  125,
  275,  276,  277,   44,  257,  258,   44,  257,   41,   40,
  275,  276,  277,  125,  257,  258,  257,  262,   44,  260,
  261,  262,  263,  264,  265,  275,  276,  277,   44,  270,
  271,  272,  273,   40,  275,  276,  277,  257,  258,  257,
   44,  262,  260,  261,  262,  263,  264,  265,   44,   41,
   44,   44,  270,  271,  272,  273,    0,  275,  276,  277,
   44,  262,  257,   44,   44,  260,  261,  262,  263,  264,
  265,   40,   44,   41,   44,  270,  271,  272,  273,   44,
  275,  276,  277,   56,   57,  257,  262,   44,  260,  261,
  262,  263,  264,  265,  125,   45,   40,   44,  270,  271,
  272,  273,   44,  275,  276,  277,   44,  262,  257,  260,
  272,  260,  261,  262,  263,  264,  265,   44,  125,   40,
   40,  270,  271,  272,  273,  271,  275,  276,  277,   41,
  261,  257,   35,   36,  260,   41,   44,  263,   40,  265,
  262,    0,   44,   93,   44,  257,  272,    0,  260,   99,
  257,  263,  264,  265,  125,  257,  125,   41,  270,   41,
  272,  273,    0,  275,  276,  277,  139,   41,  275,  276,
  277,  260,  272,   76,   12,   12,   14,  122,   40,  280,
  200,  125,   86,  188,  257,   -1,   -1,   -1,   40,   -1,
   -1,  264,   44,   -1,    0,   -1,  169,   35,   36,   -1,
  123,    7,  275,  276,  277,   -1,   12,   -1,   14,   -1,
  257,   -1,  162,  260,   62,   53,  263,   -1,  265,   -1,
   -1,  123,   84,  173,   62,  272,  257,  177,   90,  260,
  261,  262,  263,  264,  265,   -1,   -1,   43,   76,  270,
  271,  272,  273,   -1,  275,  276,  277,   53,   -1,   -1,
  257,   -1,   -1,  260,  261,  262,  263,  264,  265,   40,
   -1,   67,   -1,  270,  271,  272,  273,  115,  275,  276,
  277,  123,   -1,  121,  122,   -1,  249,  227,   -1,   -1,
   43,   -1,   45,  121,  122,  125,  148,  149,  257,   95,
   -1,  260,  261,  262,  263,  264,  265,   60,   -1,   62,
  203,  270,  271,  272,  273,  143,  275,  276,  277,  259,
  123,   88,   -1,  257,   91,   -1,  260,  261,  262,  263,
  264,  265,   -1,  125,  130,   -1,  270,  271,  272,  273,
   -1,  275,  276,  277,  257,  238,  239,  185,   62,   -1,
  188,  264,  204,   -1,  125,   -1,   -1,  185,   -1,   -1,
  188,  274,  275,  276,  277,  257,   -1,   -1,  260,  219,
   -1,  263,  200,  265,   -1,  203,   -1,  317,   -1,  271,
  272,  219,   -1,  150,  123,  223,   -1,  154,   -1,   -1,
   -1,   -1,   -1,  231,  190,   -1,  192,   -1,   -1,   -1,
  125,   -1,  252,  231,   -1,   -1,   -1,  121,  122,  125,
  238,  239,   -1,   -1,  252,  257,  254,   -1,  260,   -1,
   -1,  263,   -1,  265,  274,   -1,   -1,  257,   -1,  271,
  272,   -1,  125,   -1,  264,   -1,  274,   -1,  205,  206,
  236,   -1,  280,  293,   -1,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,  125,  257,  293,   -1,  260,  261,  262,
  263,  175,  265,   -1,   -1,  257,  316,  125,   -1,  272,
  320,  185,  264,   -1,  188,  242,  125,   -1,  316,   -1,
   -1,   -1,  320,  275,  276,  277,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,  125,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,   -1,  223,
   -1,  125,   -1,  266,  267,  268,  269,  231,  257,  125,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  257,  272,  273,   -1,  275,  276,  277,  264,
  254,  257,  125,   -1,  260,   -1,   -1,  263,  264,  265,
  275,  276,  277,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,  125,  257,   -1,  280,  260,  125,   -1,
  263,  264,  265,  125,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,  125,
   -1,  263,  264,  265,   -1,  123,   -1,   -1,  270,  257,
  272,  273,   -1,  275,  276,  277,  264,   -1,  257,  123,
   -1,  260,   -1,  123,  263,  264,  265,  275,  276,  277,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,  257,
  123,  123,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  257,  272,  273,  125,  275,  276,  277,
  264,  257,   -1,   -1,  260,   -1,  125,  263,  264,  265,
  125,  275,  276,  277,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,   -1,
  257,  263,  264,  265,   -1,  257,   -1,  264,  270,   -1,
  272,  273,  264,  275,  276,  277,   -1,   -1,  275,  276,
  277,  257,   -1,  275,  276,  277,   -1,   -1,  264,  257,
   -1,   -1,  260,  261,  262,  263,   -1,  265,   -1,  275,
  276,  277,   -1,  257,  272,   -1,  260,  257,  262,  263,
  260,  265,  262,  263,   -1,  265,   -1,   -1,  272,   -1,
   -1,   -1,  272,    0,  257,  257,   -1,  260,  260,   -1,
  263,  263,  265,  265,   -1,   12,   -1,   14,  257,  272,
  272,  260,   -1,   -1,  263,   -1,  265,   -1,  257,   -1,
   -1,  260,  257,  272,  263,  260,  265,   -1,  263,   -1,
  265,   -1,  257,  272,   -1,  260,   43,  272,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,   53,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   95,
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
"bloque_sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"bloque_sentencias_ejecutables_funcion : sentencia_return",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}'",
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

//#line 328 "./src/compilador/gramatica.y"

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
//#line 851 "Parser.java"
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
case 73:
//#line 132 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 74:
//#line 133 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 81:
//#line 152 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 82:
//#line 153 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 83:
//#line 154 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta CADENA en Sentencia PRINT"); }
break;
case 84:
//#line 155 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 85:
//#line 159 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 86:
//#line 160 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 87:
//#line 161 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 88:
//#line 162 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 89:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 90:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 93:
//#line 173 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 94:
//#line 174 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 95:
//#line 175 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
break;
case 102:
//#line 191 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 103:
//#line 192 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); }
break;
case 104:
//#line 193 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
break;
case 106:
//#line 198 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 107:
//#line 199 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 108:
//#line 200 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 109:
//#line 201 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 110:
//#line 202 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 113:
//#line 211 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 114:
//#line 212 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en delcaracion de lista de variables en CLASS"); }
break;
case 117:
//#line 218 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 118:
//#line 219 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 119:
//#line 220 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 120:
//#line 221 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 121:
//#line 222 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en IMPLEMENT de clase"); }
break;
case 122:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 123:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 124:
//#line 225 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 127:
//#line 234 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 130:
//#line 240 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 131:
//#line 241 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 132:
//#line 242 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 133:
//#line 243 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 134:
//#line 244 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 135:
//#line 245 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 136:
//#line 246 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 139:
//#line 252 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 140:
//#line 253 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 143:
//#line 259 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 144:
//#line 260 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 174:
//#line 323 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 175:
//#line 324 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1372 "Parser.java"
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
