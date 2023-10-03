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
   17,   43,   43,   43,   43,   43,   43,   26,   26,   26,
   44,   44,   44,   45,   45,   45,   46,   46,
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
    3,    1,    1,    1,    1,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  157,  158,
  159,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,  101,  102,  103,  104,    0,    0,
    0,    0,    0,   86,    0,    0,   24,    0,    0,    0,
    0,    0,   33,    0,   68,   66,    0,    0,    0,  111,
    0,    0,    0,    2,    6,    0,    0,    0,  155,  107,
    0,    0,  130,    0,  177,    0,    0,    0,    0,    0,
  173,  176,   87,   84,  160,    0,  138,    0,    0,  136,
    0,    0,    0,    0,  118,    0,  128,    0,  123,    0,
    0,   31,    0,   99,   80,    0,    0,    0,    0,    0,
    0,  113,    0,    0,  110,    0,    1,    0,    0,   98,
    0,  100,  105,    0,    0,    0,  143,   17,   19,   20,
   21,    0,    0,   15,   18,   22,   23,  175,  178,    0,
    0,  162,  163,  164,  165,    0,    0,  166,  167,    0,
    0,    0,  132,    0,  154,  137,  135,  119,    0,    0,
    0,    0,  126,  129,    0,  122,    0,   67,   81,   35,
    0,   32,    0,    0,    0,  112,  109,  115,   89,    0,
    0,   96,  156,    0,    0,    0,   44,    0,   71,   70,
   69,    0,  141,  148,  149,  150,    0,  146,  142,    0,
   16,    0,    0,    0,    0,    0,    0,    0,    0,  171,
  172,    0,  131,  151,    0,    0,    0,    0,  120,  116,
  125,    0,   34,    0,    0,    0,  108,    0,    0,   88,
    0,    0,   42,   75,   82,    0,    0,    0,    0,    0,
  145,  147,  140,    0,    0,   53,    0,   56,    0,    0,
    0,    0,  134,  153,    0,  127,  124,   28,   29,    0,
   30,    0,    0,    0,    0,   72,    0,   83,   46,    0,
   43,    0,    0,  144,    0,    0,    0,    0,   49,  133,
  152,  121,   26,    0,   90,    0,    0,    0,    0,   73,
   78,   79,    0,   76,   45,    0,    0,    0,   52,   54,
   55,    0,   62,    0,   65,    0,    0,   74,   77,   40,
    0,   41,   48,    0,    0,    0,   58,    0,   37,    0,
   63,   64,    0,    0,    0,   57,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,   61,
};
final static short yydgoto[] = {                         13,
   14,   15,  179,   46,  119,  120,  121,   21,   22,   23,
  123,  124,  181,  126,  127,   47,   68,  182,  310,  308,
   96,  226,  283,  284,   24,   69,  219,   25,   26,   27,
   28,   29,   61,   51,   30,   87,   88,   63,  204,  205,
  187,  188,  140,   70,   71,   72,
};
final static short yysindex[] = {                       663,
    0,   -6,  102,  -40,    6, -113,  629, -100,    0,    0,
    0, 1091,    0,  862,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   33,    0,    0,    0,    0,  -36,  -64,
  -28,   31,   45,    0,  -37,  141,    0,  276, -103, -156,
   83,  599,    0,   -5,    0,    0,  -38,  -95, -187,    0,
   -3,   55,  886,    0,    0,  117,  327, -155,    0,    0,
   22,  907,    0, -166,    0, -141,  818,   88,  482,   53,
    0,    0,    0,    0,    0,  284,    0, -123,  106,    0,
  110,  109,  -97, -156,    0, -102,    0,  577,    0, -156,
  713,    0,  -23,    0,    0, -120,  124,  135,   84,  -23,
 -187,    0,   60,   62,    0, -187,    0,  134,  487,    0,
  160,    0,    0,  -68,  151,  685,    0,    0,    0,    0,
    0,  929,  951,    0,    0,    0,    0,    0,    0, -165,
  721,    0,    0,    0,    0,  -23,  -23,    0,    0,  -23,
  -23,  -23,    0,  -32,    0,    0,    0,    0,  552, -156,
  854,   39,    0,    0, 1020,    0,  152,    0,    0,    0,
  170,    0,  139,  181,   99,    0,    0,    0,    0,  -23,
  184,    0,    0,  147,  213,  -86,    0,  171,    0,    0,
    0,  -34,    0,    0,    0,    0,  973,    0,    0,  995,
    0,  818,  216,  818,  235, -157,   53,   53,    8,    0,
    0, -219,    0,    0,  -26, -156, 1030, 1044,    0,    0,
    0,  236,    0,  239,  261,  259,    0,  211,  265,    0,
  840,  285,    0,    0,    0,  -63,  304,  287,   91,  -23,
    0,    0,    0, 1016,   67,    0,   87,    0,  774,  307,
  126, -219,    0,    0, 1053,    0,    0,    0,    0,  308,
    0,  -23,  318,  120,  753,    0,  340,    0,    0,  341,
    0,  182,  349,    0,  362,  364,  365,  157,    0,    0,
    0,    0,    0,    8,    0,  387,  840,  388,  -96,    0,
    0,    0, 1078,    0,    0,  398,  410,  408,    0,    0,
    0,  411,    0,  192,    0,  796,  418,    0,    0,    0,
  419,    0,    0,  430,  433,  224,    0,  228,    0,  221,
    0,    0,  454,  460,  230,    0,  480,  471,  840,  -23,
  263,  485,  840,  491,  275,    0,  494,    0,
};
final static short yyrindex[] = {                       545,
   -9,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,    0,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  546,    0,    0,    0,    0,    0,    0,    0,
  150,    0,    0,    1,    0,    0,    0,    0,    0,   24,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  290,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  173,    0,    0,    0,
    0,    0,    0,    0,    0,  423,    0,  196,    0,    0,
  219,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, 1070,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  242,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   70,  135,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  524,    0,    0,
    0,    0,    0,    0,    0,    0,  424,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  360,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  384,
    0,    0,  447,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  526,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  470,    0,    0,    0,    0,  298,    0,    0,    0,
  291,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  517,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  559,    2,  691,  554,    7,  522,  562,    0,    0,  549,
    0,  450,  -51,    0,    0,  -13,  -15,  220,    0,    0,
    0,    0,    0,  294,    0,  338,    0,    0,  758,    0,
    0,  767,  484,  507,  718,  432,   -8,    0,  558,  376,
  389, -154,    0,   71,   69,    0,
};
final static int YYTABLESIZE=1368;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
  174,  100,   76,   77,  158,  230,   18,   60,  203,   40,
  125,  202,   67,   18,  243,   55,   66,  242,   18,   90,
   18,   66,   49,  170,   50,  150,  139,  101,   98,  102,
   99,   91,  232,   31,  160,   97,   99,   75,  224,   66,
  174,  174,  174,  174,  174,  174,  168,  174,   18,   37,
  136,   99,  137,  130,   55,    9,   10,   11,   62,   18,
  174,  256,  174,  170,  170,  113,  170,  170,  170,  169,
  186,  125,   56,   18,   73,  151,    4,  157,   58,  232,
  114,  155,  210,  170,  164,  170,  168,  168,   74,  168,
  168,  168,   25,   57,  141,  192,  193,  114,  106,  142,
   82,  112,   18,  239,  240,  139,  168,    4,  168,  169,
  169,  128,  169,  169,  169,   85,  129,  196,    9,   10,
   11,  105,   93,  163,  225,  174,   92,  162,  131,  169,
  262,  169,   25,  145,  261,  186,   94,   18,  186,    2,
  207,  208,    3,   38,    5,   34,  146,  215,  170,  106,
  147,    7,  148,   89,   59,   85,   48,  108,  222,  149,
   39,   66,  228,    4,  296,  297,  270,  160,    4,  242,
   94,  168,   36,  115,  258,  161,    3,  169,  235,  214,
  237,   80,  186,   66,  166,  116,  167,  221,  173,  106,
  174,   66,  212,   94,  169,   92,  115,  245,   18,    3,
   18,    5,  136,  172,  137,  282,  197,  198,  116,  200,
  201,  227,   36,  213,  263,   66,   35,   25,   97,   75,
   59,  216,  286,  217,   75,  268,   66,  220,   64,   65,
   75,  282,   99,   64,   65,   92,  229,    9,   10,   11,
   85,   91,    9,   10,   11,   18,  287,  160,    9,   10,
   11,   64,   65,  136,  252,  137,  223,  174,   97,  236,
  174,  174,  174,  174,  174,  174,  174,  174,  174,  174,
  174,  174,  174,  174,  106,  174,  174,  174,  238,  248,
  170,   91,  249,  170,  170,  170,  170,  170,  170,  170,
  170,  170,  170,  170,  170,  170,  170,   36,  170,  170,
  170,  250,  251,  168,  322,  253,  168,  168,  168,  168,
  168,  168,  168,  168,  168,  168,  168,  168,  168,  168,
   92,  168,  168,  168,  143,  255,  169,  260,  265,  169,
  169,  169,  169,  169,  169,  169,  169,  169,  169,  169,
  169,  169,  169,   97,  169,  169,  169,  259,  266,   25,
  269,  273,   25,   25,   25,   25,   25,   25,   32,   51,
   33,  275,   25,   25,   25,   25,   91,   25,   25,   25,
  110,   66,   85,   64,   65,   85,   85,   85,   85,   85,
   85,  276,   75,   27,  285,   85,   85,   85,   85,  288,
   85,   85,   85,  109,  111,   64,   65,   75,   84,   51,
    9,   10,   11,   64,   65,  289,  106,  290,  291,  106,
  106,  106,  106,  106,  106,    9,   10,   11,  292,  106,
  106,  106,  106,   27,  106,  106,  106,   64,   65,   36,
  293,  295,   36,   36,   36,   36,   36,   36,   64,   65,
  254,  300,   36,   36,   36,   36,   93,   36,   36,   36,
  301,  302,   92,  304,  303,   92,   92,   92,   92,   92,
   92,  307,  309,   47,  280,   92,   92,   92,   92,   50,
   92,   92,   92,  311,  279,   97,  312,  199,   97,   97,
   97,   97,   97,   97,   51,  313,   93,  314,   97,   97,
   97,   97,  315,   97,   97,   97,  294,  316,   91,  317,
  318,   91,   91,   91,   91,   91,   91,  218,   27,   50,
  320,   91,   91,   91,   91,  306,   91,   91,   91,  154,
  319,   19,  154,  323,  136,  324,  137,  171,   19,  136,
  170,  137,   82,   19,  326,   19,  327,  328,  321,    4,
   75,  139,  325,  138,    4,    3,  160,  114,   47,   83,
    9,   10,   11,   17,  103,  104,   59,   60,    9,   10,
   11,   20,   38,   19,   94,   17,   95,   17,   20,  152,
   53,   93,  191,   20,   19,   20,  299,  241,  234,    0,
    0,    0,  154,   64,   65,    0,  154,    0,   19,  274,
    0,    0,   79,   81,   50,   95,   94,    0,    0,  115,
    0,    0,    3,   20,    5,    0,   17,  165,    0,    0,
  122,  116,  168,    0,   20,    0,   51,   19,    0,   51,
   51,   51,   51,   51,   51,    0,    0,    0,   20,   51,
   51,   51,   51,  144,   51,   51,   51,    0,  154,  154,
   27,   59,    0,   27,   27,   27,   27,   27,   27,  159,
    0,    0,   19,   27,   27,   27,   27,   20,   27,   27,
   27,    0,    0,    0,  180,    0,    0,    0,   44,    0,
  185,  190,   43,    0,  206,    0,  154,    0,    0,    0,
   47,    0,    0,   47,   47,   47,   47,   47,   47,    0,
   16,    0,   20,   47,   47,   47,   47,   45,   47,   47,
   47,  153,   16,   93,   16,    0,   93,   93,   93,   93,
   93,   93,    0,   19,    0,   19,   93,   93,   93,   93,
    0,   93,   93,   93,  178,   52,   50,    0,  177,   50,
   50,   50,   50,   50,   50,  185,    0,    0,  185,   50,
   50,   50,   50,   16,   50,   50,   50,  132,  133,  134,
  135,   42,  118,   20,    0,   20,    0,   45,    0,    0,
   19,    0,  244,    0,    0,   52,   52,    0,    0,  180,
    0,    0,    0,   59,  257,    0,   59,   59,   59,   59,
   59,   59,  185,    0,    0,   12,   59,   59,   59,   59,
    0,   59,   59,   59,    0,   85,    0,   85,  244,  271,
   20,   78,   78,  180,   86,  281,   86,  176,   82,    0,
    0,    0,  184,  118,    0,    4,    0,    0,   52,    0,
    0,   45,    0,   52,    0,  180,    9,   10,   11,    0,
    0,  281,    0,   82,    0,    0,    0,  156,    0,    0,
    4,   85,   78,   42,  180,   85,    0,   85,   85,    0,
   86,    9,   10,   11,   86,   94,   86,   86,    2,    0,
    0,    3,    0,    5,    0,    0,    0,  180,    0,    0,
    7,  180,    0,    0,    0,  176,    0,  184,    0,    0,
  184,    0,   45,    0,   45,    1,    0,    0,    2,    0,
    0,    3,    4,    5,    0,    0,   42,    0,    6,   41,
    7,    8,    0,    9,   10,   11,   85,   85,   85,    0,
   78,    0,   85,    0,    0,   86,   86,   86,  176,    1,
    0,   86,    2,    0,  184,    3,    4,    5,    0,   45,
    0,    0,    6,    0,    7,    8,    0,    9,   10,   11,
   42,    1,    0,    0,  115,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,  175,  116,    8,    0,    9,
   10,   11,  176,   85,   85,   85,    0,    0,   78,   82,
    0,   78,   86,   86,   86,    0,    4,    1,  209,    0,
    2,  194,  195,    3,    4,    5,   54,    9,   10,   11,
    6,    0,    7,    8,    0,    9,   10,   11,    0,    0,
    0,    0,   85,    0,    0,    0,    0,   78,   78,    1,
  107,   86,  115,  277,  278,    3,    4,    5,    0,    0,
    0,    0,    6,    0,  116,    8,    0,    9,   10,   11,
    1,  117,    0,    2,    0,  267,    3,    4,    5,    0,
    0,    0,    0,    6,    0,    7,    8,    0,    9,   10,
   11,    0,    1,  183,    0,  115,    0,  305,    3,    4,
    5,    0,    0,    0,    0,    6,    0,  116,    8,    0,
    9,   10,   11,    0,    1,  189,    0,    2,    0,    0,
    3,    4,    5,    0,    0,    0,    0,    6,    0,    7,
    8,    0,    9,   10,   11,    0,    1,  231,    0,  115,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    6,
   82,  116,    8,    0,    9,   10,   11,    4,    1,  233,
    0,    2,    0,    0,    3,    4,    5,    0,    9,   10,
   11,    6,    0,    7,    8,    0,    9,   10,   11,    0,
  264,    0,    1,    0,  211,    2,    0,    0,    3,    4,
    5,    0,    0,    0,  246,    6,    0,    7,    8,    0,
    9,   10,   11,    1,    0,    0,  115,    0,  247,    3,
    4,    5,    0,    0,    0,    0,    6,  272,  116,    8,
    0,    9,   10,   11,    0,    1,    0,    0,  115,    0,
    0,    3,    4,    5,  117,    0,    0,    0,    6,    0,
  116,    8,  298,    9,   10,   11,    0,    1,    0,    0,
  115,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,  116,    8,    0,    9,   10,   11,    0,    1,
    0,    0,  115,    0,    0,    3,    4,    5,    0,    0,
    0,    0,    6,    0,  116,    8,    0,    9,   10,   11,
    0,    1,    0,    0,  115,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,    0,  116,    8,    0,    9,
   10,   11,    1,    0,    0,  115,   82,    0,    3,    4,
    5,    0,    0,    4,    0,    6,   82,  116,    8,    0,
    9,   10,   11,    4,    9,   10,   11,    0,    0,    0,
   82,    0,    0,    0,    9,   10,   11,    4,    0,   82,
    0,    0,    0,    0,    0,    0,    4,    0,    9,   10,
   11,    0,    0,    0,    0,    0,  117,    9,   10,   11,
    0,    0,    0,  117,   94,    0,    0,  115,    0,    0,
    3,    0,    5,    0,  117,  117,  117,    1,    0,  116,
    2,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,    7,    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   41,  125,   40,    0,   44,   41,  123,
   62,   44,   41,    7,   41,   14,   45,   44,   12,  123,
   14,   45,  123,    0,  125,  123,   44,  123,   44,  125,
   40,   40,  187,   40,   44,   41,   46,  257,  125,   45,
   40,   41,   42,   43,   44,   45,    0,   47,   42,   44,
   43,   61,   45,   67,   53,  275,  276,  277,  123,   53,
   60,  125,   62,   40,   41,   44,   43,   44,   45,    0,
  122,  123,   40,   67,   44,   84,  264,   93,   46,  234,
   59,   90,   44,   60,  100,   62,   40,   41,   44,   43,
   44,   45,    0,   61,   42,  261,  262,   59,   44,   47,
  257,  257,   96,  261,  262,  123,   60,  264,   62,   40,
   41,  278,   43,   44,   45,    0,  258,  131,  275,  276,
  277,  125,   40,   40,  176,  125,   44,   44,   41,   60,
   40,   62,   40,  257,   44,  187,  257,  131,  190,  260,
  149,  150,  263,  257,  265,   44,   41,  163,  125,    0,
   41,  272,   44,  257,  257,   40,  257,   41,  174,  257,
  274,   45,  178,  264,  261,  262,   41,   44,  264,   44,
  257,  125,    0,  260,  226,   41,  263,   44,  192,   41,
  194,   41,  234,   45,  125,  272,  125,   41,  257,   40,
   40,   45,   41,  257,  125,    0,  260,  206,  192,  263,
  194,  265,   43,   44,   45,  257,  136,  137,  272,  141,
  142,   41,   40,   44,  230,   45,  257,  125,    0,  257,
  257,   41,   41,  125,  257,  239,   45,   44,  257,  258,
  257,  283,  271,  257,  258,   40,  271,  275,  276,  277,
  125,    0,  275,  276,  277,  239,  262,  257,  275,  276,
  277,  257,  258,   43,   44,   45,   44,  257,   40,   44,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,   44,   44,
  257,   40,   44,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,  272,  273,  125,  275,  276,
  277,   41,   44,  257,  320,   41,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
  125,  275,  276,  277,   41,   41,  257,   41,  262,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  272,  273,  125,  275,  276,  277,   44,  262,  257,
   44,   44,  260,  261,  262,  263,  264,  265,  257,    0,
  259,   44,  270,  271,  272,  273,  125,  275,  276,  277,
   44,   45,  257,  257,  258,  260,  261,  262,  263,  264,
  265,  262,  257,    0,   44,  270,  271,  272,  273,   41,
  275,  276,  277,   56,   57,  257,  258,  257,  123,   40,
  275,  276,  277,  257,  258,   44,  257,   44,   44,  260,
  261,  262,  263,  264,  265,  275,  276,  277,  262,  270,
  271,  272,  273,   40,  275,  276,  277,  257,  258,  257,
   44,   44,  260,  261,  262,  263,  264,  265,  257,  258,
  221,   44,  270,  271,  272,  273,    0,  275,  276,  277,
   41,   44,  257,  262,   44,  260,  261,  262,  263,  264,
  265,   44,   44,   40,  125,  270,  271,  272,  273,    0,
  275,  276,  277,   44,  255,  257,   44,  140,  260,  261,
  262,  263,  264,  265,  125,  262,   40,  260,  270,  271,
  272,  273,  272,  275,  276,  277,  277,   44,  257,   40,
  271,  260,  261,  262,  263,  264,  265,  170,  125,   40,
   40,  270,  271,  272,  273,  296,  275,  276,  277,   88,
   41,    0,   91,  261,   43,   41,   45,   41,    7,   43,
   44,   45,  257,   12,   44,   14,  262,   44,  319,  264,
  257,   60,  323,   62,    0,    0,  257,  125,  125,  274,
  275,  276,  277,    0,   48,   49,   40,  260,  275,  276,
  277,    0,  272,   42,   41,   12,   41,   14,    7,   86,
   12,  125,  123,   12,   53,   14,  283,  202,  190,   -1,
   -1,   -1,  151,  257,  258,   -1,  155,   -1,   67,  252,
   -1,   -1,   35,   36,  125,   42,  257,   -1,   -1,  260,
   -1,   -1,  263,   42,  265,   -1,   53,  101,   -1,   -1,
   62,  272,  106,   -1,   53,   -1,  257,   96,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   67,  270,
  271,  272,  273,   76,  275,  276,  277,   -1,  207,  208,
  257,  125,   -1,  260,  261,  262,  263,  264,  265,   96,
   -1,   -1,  131,  270,  271,  272,  273,   96,  275,  276,
  277,   -1,   -1,   -1,  116,   -1,   -1,   -1,   40,   -1,
  122,  123,   44,   -1,  123,   -1,  245,   -1,   -1,   -1,
  257,   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,
    0,   -1,  131,  270,  271,  272,  273,    7,  275,  276,
  277,  125,   12,  257,   14,   -1,  260,  261,  262,  263,
  264,  265,   -1,  192,   -1,  194,  270,  271,  272,  273,
   -1,  275,  276,  277,   40,    8,  257,   -1,   44,  260,
  261,  262,  263,  264,  265,  187,   -1,   -1,  190,  270,
  271,  272,  273,   53,  275,  276,  277,  266,  267,  268,
  269,  123,   62,  192,   -1,  194,   -1,   67,   -1,   -1,
  239,   -1,  205,   -1,   -1,   48,   49,   -1,   -1,  221,
   -1,   -1,   -1,  257,  226,   -1,  260,  261,  262,  263,
  264,  265,  234,   -1,   -1,  123,  270,  271,  272,  273,
   -1,  275,  276,  277,   -1,   38,   -1,   40,  241,  242,
  239,   35,   36,  255,   38,  257,   40,  123,  257,   -1,
   -1,   -1,  122,  123,   -1,  264,   -1,   -1,  101,   -1,
   -1,  131,   -1,  106,   -1,  277,  275,  276,  277,   -1,
   -1,  283,   -1,  257,   -1,   -1,   -1,  125,   -1,   -1,
  264,   84,   76,  123,  296,   88,   -1,   90,   91,   -1,
   84,  275,  276,  277,   88,  257,   90,   91,  260,   -1,
   -1,  263,   -1,  265,   -1,   -1,   -1,  319,   -1,   -1,
  272,  323,   -1,   -1,   -1,  123,   -1,  187,   -1,   -1,
  190,   -1,  192,   -1,  194,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,   -1,  123,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,  149,  150,  151,   -1,
  144,   -1,  155,   -1,   -1,  149,  150,  151,  123,  257,
   -1,  155,  260,   -1,  234,  263,  264,  265,   -1,  239,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
  123,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,  123,  206,  207,  208,   -1,   -1,  202,  257,
   -1,  205,  206,  207,  208,   -1,  264,  257,  125,   -1,
  260,  261,  262,  263,  264,  265,  125,  275,  276,  277,
  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,   -1,
   -1,   -1,  245,   -1,   -1,   -1,   -1,  241,  242,  257,
  125,  245,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
  257,  125,   -1,  260,   -1,  262,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,   -1,  257,  125,   -1,  260,   -1,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,
  275,  276,  277,   -1,  257,  125,   -1,  260,   -1,   -1,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,   -1,  257,  125,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  257,  272,  273,   -1,  275,  276,  277,  264,  257,  125,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,  275,  276,
  277,  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,
  125,   -1,  257,   -1,  125,  260,   -1,   -1,  263,  264,
  265,   -1,   -1,   -1,  125,  270,   -1,  272,  273,   -1,
  275,  276,  277,  257,   -1,   -1,  260,   -1,  125,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,  125,  272,  273,
   -1,  275,  276,  277,   -1,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,  125,   -1,   -1,   -1,  270,   -1,
  272,  273,  125,  275,  276,  277,   -1,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,  257,   -1,   -1,  260,  257,   -1,  263,  264,
  265,   -1,   -1,  264,   -1,  270,  257,  272,  273,   -1,
  275,  276,  277,  264,  275,  276,  277,   -1,   -1,   -1,
  257,   -1,   -1,   -1,  275,  276,  277,  264,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,  264,   -1,  275,  276,
  277,   -1,   -1,   -1,   -1,   -1,  257,  275,  276,  277,
   -1,   -1,   -1,  264,  257,   -1,   -1,  260,   -1,   -1,
  263,   -1,  265,   -1,  275,  276,  277,  257,   -1,  272,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,
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

//#line 331 "./src/compilador/gramatica.y"

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
//#line 880 "Parser.java"
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
case 177:
//#line 326 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 178:
//#line 327 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1413 "Parser.java"
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
