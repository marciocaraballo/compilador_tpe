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
    0,  110,    0,  114,    1,    0,    0,   98,    0,  100,
  105,    0,    0,    0,  143,   17,   19,   20,   21,    0,
    0,   15,   18,   22,   23,  177,  180,    0,    0,    0,
    0,    0,    0,    0,    0,  132,    0,  154,  137,  135,
  119,    0,    0,    0,    0,  126,  129,    0,  122,    0,
   67,   81,   35,    0,   32,    0,    0,    0,  112,  109,
  115,   89,    0,    0,   96,  156,    0,    0,    0,   44,
    0,   71,   70,   69,    0,  141,  148,  149,  150,    0,
  146,  142,    0,   16,    0,    0,    0,    0,    0,    0,
    0,    0,  173,  174,    0,  131,  151,    0,    0,    0,
    0,  120,  116,  125,    0,   34,    0,    0,    0,  108,
    0,    0,   88,    0,    0,   42,   75,   82,    0,    0,
    0,    0,    0,  145,  147,  140,    0,    0,   53,    0,
   56,    0,    0,    0,    0,  134,  153,    0,  127,  124,
   28,   29,    0,   30,    0,    0,    0,    0,   72,    0,
   83,   46,    0,   43,    0,    0,  144,    0,    0,    0,
    0,   49,  133,  152,  121,   26,    0,   90,    0,    0,
    0,    0,   73,   78,   79,    0,   76,   45,    0,    0,
    0,   52,   54,   55,    0,   62,    0,   65,    0,    0,
   74,   77,   40,    0,   41,   48,    0,    0,    0,   58,
    0,   37,    0,   63,   64,    0,    0,    0,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,    0,
   61,
};
final static short yydgoto[] = {                         13,
   14,   15,  182,   46,  127,  128,  129,   21,   22,   23,
  131,  132,  184,  134,  135,   47,   74,  185,  313,  311,
  103,  229,  286,  287,   24,   75,  222,   25,   26,   27,
   28,   29,   61,   51,   30,   94,   95,   63,  207,  208,
  190,  191,   76,   77,   78,   79,
};
final static short yysindex[] = {                       -80,
    0,   57,  -33,  -30,   66,  -63,  615,  -85,    0,    0,
    0, 1129,    0,  941,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,    0,    0,    0,    0,  -25,   -7,
  -38,   87,  106,    0,  -39,  137,    0,  288, -106, -171,
   43,  227,    0,  486,    0,    0,  -36,  -64, -122,    0,
   10,  119,  963,    0,    0,   -9,   95, -101,    0,    0,
   11,  984,    0, -109,    0,    0,    0,    0,    0,  -87,
  897,    0,    0,  133,  513,  -15,   -3,    0,    0,    0,
    0,    0,  150,    0,  -76,  166,    0,  171,  189,  -90,
 -171,    0,  -16,    0,  239,    0, -171,  555,    0,  700,
    0,    0,  553,  202,  210,  161,  700, -122,    0,   20,
   34,    0, -122,    0,    0,  212,   84,    0,  104,    0,
    0,   27,  246,  642,    0,    0,    0,    0,    0, 1011,
 1033,    0,    0,    0,    0,    0,    0, -204,  753,  -15,
  -15,  -15,  121,  -15,  -15,    0,  -23,    0,    0,    0,
    0,  -89, -171,  586,   18,    0,    0,  770,    0,  261,
    0,    0,    0,  262,    0,  506,  289,   36,    0,    0,
    0,    0,  -15,  310,    0,    0,  669,  312,  918,    0,
  674,    0,    0,    0,  -31,    0,    0,    0,    0, 1055,
    0,    0, 1077,    0,  897,  313,  897,  322,  -45,   -3,
   -3,  121,    0,    0, -168,    0,    0,  -18, -171,  775,
  848,    0,    0,    0,  323,    0,  334,  291,  335,    0,
  358,  292,    0,  919,  346,    0,    0,    0,  719,  345,
  357,  169,  700,    0,    0,    0, 1099,  146,    0,  147,
    0,  838,  355,  129, -168,    0,    0, 1025,    0,    0,
    0,    0,  366,    0,  -15,  373,  162,  810,    0,  732,
    0,    0,  392,    0,  687,  397,    0,  393,  404,  405,
  179,    0,    0,    0,    0,    0,  121,    0,  407,  919,
  412,   -1,    0,    0,    0, 1125,    0,    0,  416,  421,
  419,    0,    0,    0,  426,    0,  209,    0,  870,  434,
    0,    0,    0,  438,    0,    0,  439,  441,  224,    0,
  229,    0,  221,    0,    0,  453,  458,  234,    0,  465,
  473,  919,  700,  260,  485,  919,  489,  267,    0,  490,
    0,
};
final static short yyrindex[] = {                       532,
  -32,    0,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  120,    0,   -2,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  536,    0,    0,    0,    0,    0,    0,    0,
  158,    0,    0,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  280,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  182,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  204,    0,    0,  247,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  497,  502,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, 1104,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  325,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   74,  503,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  508,    0,    0,    0,    0,    0,    0,    0,    0,  529,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  348,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  376,    0,    0,  400,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  512,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  432,    0,    0,    0,    0,  279,
    0,    0,    0,  283,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  576,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  549,   23,   13,  112,  488,  528,  706,    0,    0,  498,
    0,  436,  636,    0,    0,  -40,  -44,  973,    0,    0,
    0,    0,    0,  285,    0,  -41,    0,    0,  883,    0,
    0,  911,  479,   54,   33,  396,  777,    0,  543,  369,
  383, -184,  505,  123,  165,    0,
};
final static int YYTABLESIZE=1406;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
   83,   84,   71,  107,  176,  235,   70,   99,  233,   36,
   34,  160,   16,   99,  117,  119,   97,  206,   60,   45,
  205,   73,  246,   72,   16,  245,   16,  172,   99,   70,
  138,  116,  153,  209,  143,   70,   55,   49,  144,   50,
   52,  139,   12,  145,  176,  176,  176,  176,  176,  176,
  170,  176,  235,  113,  121,  160,  195,  196,  108,   40,
  109,  213,  167,  113,  176,   16,  176,  172,  172,  122,
  172,  172,  172,  171,  126,   55,  122,  113,   56,  113,
   52,   52,  100,   45,   58,   89,   99,  172,   82,  172,
  170,  170,    4,  170,  170,  170,   31,   25,  199,   57,
  202,  110,  111,    9,   10,   11,    9,   10,   11,   37,
  170,   17,  170,  171,  171,   62,  171,  171,  171,   85,
  139,  218,  139,   17,  174,   17,  140,  173,  141,  176,
   80,  221,  225,  171,  112,  171,  231,   25,  118,   70,
   52,    4,  187,  126,  169,  171,  140,  175,  141,   81,
   96,   45,  172,  102,  238,  120,  240,  106,  170,   85,
  220,  168,  114,  140,   17,  141,  152,   89,  136,  273,
  137,   48,  245,  139,    4,  170,    1,   87,    4,    2,
  148,   36,    3,    4,    5,    9,   10,   11,  266,    6,
  146,    7,    8,   38,    9,   10,   11,  106,  171,    4,
  166,  271,  187,   92,  165,  187,  149,   45,  265,   45,
   39,  150,  264,  277,  162,  242,  243,   82,   64,   65,
  290,   36,   25,   32,  160,   33,   35,   66,   67,   68,
   69,   59,  151,   82,  106,    9,   10,   11,   82,  232,
   59,   64,   65,   92,   85,  163,   97,   64,   65,  187,
  164,    9,   10,   11,   45,  172,    9,   10,   11,  299,
  300,  176,  200,  201,  176,  176,  176,  176,  176,  176,
  176,  176,  176,  176,  176,  176,  176,  176,  325,  176,
  176,  176,  106,  176,  172,  177,   97,  172,  172,  172,
  172,  172,  172,  172,  172,  172,  172,  172,  172,  172,
  172,  215,  172,  172,  172,  216,   36,  170,  203,  204,
  170,  170,  170,  170,  170,  170,  170,  170,  170,  170,
  170,  170,  170,  170,   91,  170,  170,  170,   92,  219,
  171,  253,  256,  171,  171,  171,  171,  171,  171,  171,
  171,  171,  171,  171,  171,  171,  171,   51,  171,  171,
  171,   64,   65,  223,   25,  226,  239,   25,   25,   25,
   25,   25,   25,  156,   91,  241,  251,   25,   25,   25,
   25,   97,   25,   25,   25,   27,   85,  252,  254,   85,
   85,   85,   85,   85,   85,   82,  258,   51,  262,   85,
   85,   85,   85,   82,   85,   85,   85,  263,  272,   93,
  140,  255,  141,    9,   10,   11,   82,  268,  269,  276,
   91,    9,   10,   11,  106,   27,  278,  106,  106,  106,
  106,  106,  106,  279,    9,   10,   11,  106,  106,  106,
  106,   50,  106,  106,  106,  288,  292,  291,   36,   93,
  295,   36,   36,   36,   36,   36,   36,  293,  294,   91,
  296,   36,   36,   36,   36,  298,   36,   36,   36,  303,
   92,  304,  305,   92,   92,   92,   92,   92,   92,  306,
  307,   50,   51,   92,   92,   92,   92,  310,   92,   92,
   92,  312,  314,  101,  315,  316,    2,   18,  317,    3,
  157,    5,  318,  157,   18,   89,  319,  320,    7,   18,
   27,   18,    4,   97,  321,  322,   97,   97,   97,   97,
   97,   97,  323,    9,   10,   11,   97,   97,   97,   97,
  326,   97,   97,   97,   93,  327,  104,   19,  330,   18,
   70,    4,  329,  331,   19,    3,  160,  162,   60,   19,
   18,   19,  163,  161,   89,   73,  217,   72,   94,  157,
   70,    4,   95,  157,   38,  140,   50,  141,   18,  130,
   53,   90,    9,   10,   11,   73,  194,   72,   47,   19,
  302,  155,   73,  244,   72,  237,    0,   86,   88,  142,
   19,   91,    0,    0,   91,   91,   91,   91,   91,   91,
   18,    0,    0,    0,   91,   91,   91,   91,   19,   91,
   91,   91,    0,    0,   51,  157,  157,   51,   51,   51,
   51,   51,   51,    0,    0,   59,    0,   51,   51,   51,
   51,  183,   51,   51,   51,  147,   18,  188,  193,    0,
   19,    0,   27,    0,    0,   27,   27,   27,   27,   27,
   27,    0,    0,  157,    0,   27,   27,   27,   27,    0,
   27,   27,   27,   47,   44,    0,   93,    0,   43,   93,
   93,   93,   93,   93,   93,    0,   19,    0,    0,   93,
   93,   93,   93,    0,   93,   93,   93,  161,    0,  159,
    0,  181,   18,    0,   18,  180,    0,  188,   50,    0,
  188,   50,   50,   50,   50,   50,   50,  133,    0,    0,
   59,   50,   50,   50,   50,   20,   50,   50,   50,  224,
  212,    0,   20,   70,  230,    0,    0,   20,   70,   20,
    0,  183,   19,    0,   19,    0,  260,  289,   73,   18,
   72,   70,    0,   73,  188,   72,    0,   42,    0,    0,
    0,    0,   64,   65,   70,    0,   73,   20,   72,    0,
  247,   66,   67,   68,   69,  183,    0,  284,   20,   73,
    0,   72,   64,   65,  179,  189,  133,    0,    0,   19,
    0,   66,   67,   68,   69,    0,   20,  183,   66,   67,
   68,   69,    0,  284,    0,   47,  247,  274,   47,   47,
   47,   47,   47,   47,    0,    0,  183,    0,   47,   47,
   47,   47,    0,   47,   47,   47,    0,    0,   20,  101,
    0,   89,    2,    0,  228,    3,   98,    5,    4,  183,
    0,    0,    0,  183,    7,  189,    0,    0,  189,    9,
   10,   11,   59,    0,    0,   59,   59,   59,   59,   59,
   59,    0,   89,  259,   20,   59,   59,   59,   59,    4,
   59,   59,   59,    0,    0,    0,  283,    0,    0,    0,
    9,   10,   11,    0,  261,    0,    0,  154,    0,    0,
    0,    1,  189,  158,    2,   42,    0,    3,    4,    5,
    0,    0,    0,    0,    6,   41,    7,    8,    0,    9,
   10,   11,    0,    0,  214,  285,    0,    0,    1,  249,
   20,  123,   20,    0,    3,    4,    5,    0,    0,    0,
    0,    6,  178,  124,    8,    0,    9,   10,   11,    0,
   92,  285,   92,    0,    0,   64,   65,    0,  210,  211,
   64,   65,  179,    0,   66,   67,   68,   69,    0,   66,
   67,   68,   69,   64,   65,   85,   85,   20,   93,    0,
   93,    0,   66,   67,   68,   69,   64,   65,    0,    0,
   42,    0,    0,    0,    0,   66,   67,   68,   69,    0,
    0,    0,  250,   92,    0,  101,    0,   92,  123,   92,
   92,    3,    0,    5,    0,  248,    0,    0,  101,    0,
  124,  123,  179,   85,    3,    0,    5,    0,    0,    0,
    0,   93,    0,  124,    0,   93,    0,   93,   93,    1,
    0,    0,    2,  197,  198,    3,    4,    5,    0,   42,
    0,    0,    6,    0,    7,    8,   89,    9,   10,   11,
    0,   89,    0,    4,   92,   92,   92,    0,    4,    0,
   92,  179,  227,    0,    9,   10,   11,    0,    0,    9,
   10,   11,    0,    0,    0,    0,    0,   85,    0,    0,
    0,    0,   93,   93,   93,   54,    1,    0,   93,  123,
  280,  281,    3,    4,    5,    0,    0,    0,    0,    6,
    0,  124,    8,    0,    9,   10,   11,  115,    0,    0,
    0,   92,   92,   92,    1,    0,    0,    2,    0,  270,
    3,    4,    5,    0,   89,    0,    0,    6,  125,    7,
    8,    4,    9,   10,   11,   85,    0,    0,   85,   93,
   93,   93,    9,   10,   11,    0,    1,    0,    0,  123,
   92,  308,    3,    4,    5,  186,    0,    0,    0,    6,
    0,  124,    8,    0,    9,   10,   11,    0,    0,  275,
    0,    0,    0,    1,   85,   85,    2,  192,   93,    3,
    4,    5,    0,    0,    0,    0,    6,    0,    7,    8,
    0,    9,   10,   11,  101,    1,    0,  123,  123,  234,
    3,    3,    4,    5,    0,    0,    0,    0,    6,  124,
  124,    8,    0,    9,   10,   11,  257,    1,    0,    0,
    2,  236,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,    7,    8,    0,    9,   10,   11,    0,    1,
    0,    0,    2,  267,    0,    3,    4,    5,  117,    0,
  282,    0,    6,    0,    7,    8,    0,    9,   10,   11,
    1,    0,    0,  123,    0,    0,    3,    4,    5,  301,
    0,    0,  297,    6,    0,  124,    8,    0,    9,   10,
   11,    0,    0,    0,    0,    0,    0,    1,    0,    0,
  123,  309,    0,    3,    4,    5,    0,    0,    0,    0,
    6,   89,  124,    8,    0,    9,   10,   11,    4,    1,
    0,    0,  123,    0,  324,    3,    4,    5,  328,    9,
   10,   11,    6,    0,  124,    8,    0,    9,   10,   11,
    0,    1,    0,    0,  123,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,    0,  124,    8,    0,    9,
   10,   11,    0,    1,    0,    0,  123,    0,    0,    3,
    4,    5,    0,    0,    0,    0,    6,    0,  124,    8,
    0,    9,   10,   11,    0,    1,    0,    0,  123,    0,
  117,    3,    4,    5,    0,    0,    0,  117,    6,    0,
  124,    8,    0,    9,   10,   11,    0,    0,  117,  117,
  117,  101,    0,    0,  123,    1,    0,    3,    2,    5,
    0,    3,    4,    5,    0,    0,  124,    0,    6,    0,
    7,    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
   40,   41,   41,   40,    0,  190,   45,   40,   40,   40,
   44,   44,    0,   46,   56,   57,  123,   41,   44,    7,
   44,   60,   41,   62,   12,   44,   14,    0,   61,   45,
   71,   41,  123,  123,   76,   45,   14,  123,   42,  125,
    8,   44,  123,   47,   40,   41,   42,   43,   44,   45,
    0,   47,  237,   44,   44,  100,  261,  262,  123,  123,
  125,   44,  107,   44,   60,   53,   62,   40,   41,   59,
   43,   44,   45,    0,   62,   53,   59,   44,   40,   44,
   48,   49,   40,   71,   46,  257,   44,   60,  257,   62,
   40,   41,  264,   43,   44,   45,   40,    0,  139,   61,
  142,   48,   49,  275,  276,  277,  275,  276,  277,   44,
   60,    0,   62,   40,   41,  123,   43,   44,   45,    0,
  123,  166,  125,   12,   41,   14,   43,   44,   45,  125,
   44,  173,  177,   60,  125,   62,  181,   40,   44,   45,
  108,  264,  130,  131,  125,  113,   43,   44,   45,   44,
  257,  139,  125,   42,  195,  257,  197,    0,  125,   40,
  125,  108,   44,   43,   53,   45,  257,  257,  278,   41,
  258,  257,   44,   41,  264,  125,  257,   41,  264,  260,
  257,    0,  263,  264,  265,  275,  276,  277,  233,  270,
   41,  272,  273,  257,  275,  276,  277,   40,  125,  264,
   40,  242,  190,    0,   44,  193,   41,  195,   40,  197,
  274,   41,   44,  255,  103,  261,  262,  257,  257,  258,
  265,   40,  125,  257,  257,  259,  257,  266,  267,  268,
  269,  257,   44,  257,  271,  275,  276,  277,  257,  271,
  257,  257,  258,   40,  125,   44,    0,  257,  258,  237,
   41,  275,  276,  277,  242,   44,  275,  276,  277,  261,
  262,  257,  140,  141,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  273,  323,  275,
  276,  277,  125,  257,  257,   40,   40,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  272,
  273,   41,  275,  276,  277,   44,  125,  257,  144,  145,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,    0,  275,  276,  277,  125,   41,
  257,   41,   41,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  272,  273,    0,  275,  276,
  277,  257,  258,   44,  257,   44,   44,  260,  261,  262,
  263,  264,  265,  125,   40,   44,   44,  270,  271,  272,
  273,  125,  275,  276,  277,    0,  257,   44,   44,  260,
  261,  262,  263,  264,  265,  257,   41,   40,   44,  270,
  271,  272,  273,  257,  275,  276,  277,   41,   44,    0,
   43,   44,   45,  275,  276,  277,  257,  262,  262,   44,
  123,  275,  276,  277,  257,   40,   44,  260,  261,  262,
  263,  264,  265,  262,  275,  276,  277,  270,  271,  272,
  273,    0,  275,  276,  277,   44,   44,   41,  257,   40,
  262,  260,  261,  262,  263,  264,  265,   44,   44,  125,
   44,  270,  271,  272,  273,   44,  275,  276,  277,   44,
  257,   41,   44,  260,  261,  262,  263,  264,  265,   44,
  262,   40,  125,  270,  271,  272,  273,   44,  275,  276,
  277,   44,   44,  257,   44,  262,  260,    0,  260,  263,
   95,  265,  272,   98,    7,  257,   44,   40,  272,   12,
  125,   14,  264,  257,  271,   41,  260,  261,  262,  263,
  264,  265,   40,  275,  276,  277,  270,  271,  272,  273,
  261,  275,  276,  277,  125,   41,   41,    0,  262,   42,
   45,    0,   44,   44,    7,    0,  257,   41,  260,   12,
   53,   14,   41,   41,  257,   60,   41,   62,   41,  154,
   45,  264,   41,  158,  272,   43,  125,   45,   71,   62,
   12,  274,  275,  276,  277,   60,  131,   62,   40,   42,
  286,   93,   60,  205,   62,  193,   -1,   35,   36,   75,
   53,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
  103,   -1,   -1,   -1,  270,  271,  272,  273,   71,  275,
  276,  277,   -1,   -1,  257,  210,  211,  260,  261,  262,
  263,  264,  265,   -1,   -1,   40,   -1,  270,  271,  272,
  273,  124,  275,  276,  277,   83,  139,  130,  131,   -1,
  103,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,  248,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,  125,   40,   -1,  257,   -1,   44,  260,
  261,  262,  263,  264,  265,   -1,  139,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,  125,
   -1,   40,  195,   -1,  197,   44,   -1,  190,  257,   -1,
  193,  260,  261,  262,  263,  264,  265,   62,   -1,   -1,
  125,  270,  271,  272,  273,    0,  275,  276,  277,   41,
  125,   -1,    7,   45,   41,   -1,   -1,   12,   45,   14,
   -1,  224,  195,   -1,  197,   -1,  229,   41,   60,  242,
   62,   45,   -1,   60,  237,   62,   -1,  123,   -1,   -1,
   -1,   -1,  257,  258,   45,   -1,   60,   42,   62,   -1,
  208,  266,  267,  268,  269,  258,   -1,  260,   53,   60,
   -1,   62,  257,  258,  123,  130,  131,   -1,   -1,  242,
   -1,  266,  267,  268,  269,   -1,   71,  280,  266,  267,
  268,  269,   -1,  286,   -1,  257,  244,  245,  260,  261,
  262,  263,  264,  265,   -1,   -1,  299,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,  103,  257,
   -1,  257,  260,   -1,  179,  263,   40,  265,  264,  322,
   -1,   -1,   -1,  326,  272,  190,   -1,   -1,  193,  275,
  276,  277,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,  257,  125,  139,  270,  271,  272,  273,  264,
  275,  276,  277,   -1,   -1,   -1,  125,   -1,   -1,   -1,
  275,  276,  277,   -1,  229,   -1,   -1,   91,   -1,   -1,
   -1,  257,  237,   97,  260,  123,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   -1,  125,  260,   -1,   -1,  257,  125,
  195,  260,  197,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   38,  286,   40,   -1,   -1,  257,  258,   -1,  152,  153,
  257,  258,  123,   -1,  266,  267,  268,  269,   -1,  266,
  267,  268,  269,  257,  258,   35,   36,  242,   38,   -1,
   40,   -1,  266,  267,  268,  269,  257,  258,   -1,   -1,
  123,   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,
   -1,   -1,  125,   91,   -1,  257,   -1,   95,  260,   97,
   98,  263,   -1,  265,   -1,  209,   -1,   -1,  257,   -1,
  272,  260,  123,   83,  263,   -1,  265,   -1,   -1,   -1,
   -1,   91,   -1,  272,   -1,   95,   -1,   97,   98,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,  123,
   -1,   -1,  270,   -1,  272,  273,  257,  275,  276,  277,
   -1,  257,   -1,  264,  152,  153,  154,   -1,  264,   -1,
  158,  123,  125,   -1,  275,  276,  277,   -1,   -1,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,  147,   -1,   -1,
   -1,   -1,  152,  153,  154,  125,  257,   -1,  158,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,
   -1,  209,  210,  211,  257,   -1,   -1,  260,   -1,  262,
  263,  264,  265,   -1,  257,   -1,   -1,  270,  125,  272,
  273,  264,  275,  276,  277,  205,   -1,   -1,  208,  209,
  210,  211,  275,  276,  277,   -1,  257,   -1,   -1,  260,
  248,  262,  263,  264,  265,  125,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,   -1,   -1,  125,
   -1,   -1,   -1,  257,  244,  245,  260,  125,  248,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,  257,  257,   -1,  260,  260,  125,
  263,  263,  264,  265,   -1,   -1,   -1,   -1,  270,  272,
  272,  273,   -1,  275,  276,  277,  224,  257,   -1,   -1,
  260,  125,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,  257,
   -1,   -1,  260,  125,   -1,  263,  264,  265,  125,   -1,
  258,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,  125,
   -1,   -1,  280,  270,   -1,  272,  273,   -1,  275,  276,
  277,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,  299,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,  257,  272,  273,   -1,  275,  276,  277,  264,  257,
   -1,   -1,  260,   -1,  322,  263,  264,  265,  326,  275,
  276,  277,  270,   -1,  272,  273,   -1,  275,  276,  277,
   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,   -1,  257,   -1,   -1,  260,   -1,
  257,  263,  264,  265,   -1,   -1,   -1,  264,  270,   -1,
  272,  273,   -1,  275,  276,  277,   -1,   -1,  275,  276,
  277,  257,   -1,   -1,  260,  257,   -1,  263,  260,  265,
   -1,  263,  264,  265,   -1,   -1,  272,   -1,  270,   -1,
  272,  273,   -1,  275,  276,  277,
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

//#line 332 "./src/compilador/gramatica.y"

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
//#line 892 "Parser.java"
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
case 14:
//#line 39 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
break;
case 25:
//#line 62 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego del RETURN"); }
break;
case 26:
//#line 66 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 27:
//#line 67 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
break;
case 28:
//#line 68 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 29:
//#line 69 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 30:
//#line 70 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); }
break;
case 31:
//#line 71 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 32:
//#line 72 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 33:
//#line 73 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
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
//#line 80 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 38:
//#line 81 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia DO WHILE"); }
break;
case 39:
//#line 82 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 40:
//#line 83 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 41:
//#line 84 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba WHILE en sentencia DO WHILE"); }
break;
case 42:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
break;
case 43:
//#line 86 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia DO WHILE"); }
break;
case 44:
//#line 87 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia DO WHILE"); }
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
//#line 94 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 49:
//#line 95 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 50:
//#line 96 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); }
break;
case 51:
//#line 97 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
break;
case 52:
//#line 98 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); }
break;
case 53:
//#line 99 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF"); }
break;
case 54:
//#line 100 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 55:
//#line 101 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 56:
//#line 102 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
break;
case 57:
//#line 106 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 58:
//#line 107 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 59:
//#line 108 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF ELSE"); }
break;
case 60:
//#line 109 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba ',' luego de sentencia IF sin ELSE"); }
break;
case 61:
//#line 110 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF ELSE"); }
break;
case 62:
//#line 111 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba condicion en sentencia IF"); }
break;
case 63:
//#line 112 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 64:
//#line 113 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF ELSE"); }
break;
case 65:
//#line 114 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en sentencia IF"); }
break;
case 68:
//#line 120 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 71:
//#line 126 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] No se permiten declaraciones de variables dentro de bloque de sentencias ejecutables"); }
break;
case 75:
//#line 130 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias ejecutables en bloque de sentencias ejecutables"); }
break;
case 76:
//#line 134 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 77:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 84:
//#line 154 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 85:
//#line 155 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en Sentencia PRINT"); }
break;
case 86:
//#line 156 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba CADENA en Sentencia PRINT"); }
break;
case 87:
//#line 157 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 88:
//#line 161 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 89:
//#line 162 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 90:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 91:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 92:
//#line 165 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 93:
//#line 166 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en invocacion de funcion"); }
break;
case 96:
//#line 175 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 97:
//#line 176 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en sentenecia asignacion"); }
break;
case 98:
//#line 177 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba expresion del lado derecho en sentenecia asignacion"); }
break;
case 105:
//#line 193 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 106:
//#line 194 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en sentenecia declaracion de variables"); }
break;
case 107:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de variables en sentenecia declaracion de variables"); }
break;
case 109:
//#line 200 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de INTERFACE"); }
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
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de INTERFACE"); }
break;
case 113:
//#line 204 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de INTERFACE"); }
break;
case 116:
//#line 213 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 117:
//#line 214 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo ',' en delcaracion de lista de variables en CLASS"); }
break;
case 120:
//#line 220 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 121:
//#line 221 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 122:
//#line 222 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 123:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 124:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en IMPLEMENT de clase"); }
break;
case 125:
//#line 225 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en declaracion de clase"); }
break;
case 126:
//#line 226 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de clase"); }
break;
case 127:
//#line 227 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '{' en declaracion de clase"); }
break;
case 130:
//#line 236 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 133:
//#line 242 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 134:
//#line 243 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 135:
//#line 244 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); }
break;
case 136:
//#line 245 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador en el encabezado de la funcion"); }
break;
case 137:
//#line 246 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 138:
//#line 247 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 139:
//#line 248 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba un simbolo '(' en el encabezado de la funcion"); }
break;
case 142:
//#line 254 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); }
break;
case 143:
//#line 255 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una sentencia RETURN al final de la funcion"); }
break;
case 146:
//#line 261 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 147:
//#line 262 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 162:
//#line 295 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion del lado derecho de la comparacion"); }
break;
case 163:
//#line 296 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion del lado izquierdo de la comparacion"); }
break;
case 179:
//#line 327 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 180:
//#line 328 "./src/compilador/gramatica.y"
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
