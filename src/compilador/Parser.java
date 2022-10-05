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
    7,    8,   14,   15,   15,   15,   15,   15,   15,   16,
   21,   21,   17,   17,   23,   23,   18,   18,   25,   27,
   27,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   19,   19,   19,   19,   19,   13,   31,   31,   29,
   29,   29,   33,   33,   32,   32,    9,    9,    9,   34,
   34,   35,   35,   35,   35,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    5,   41,   41,   41,   42,   42,
   42,   42,   42,   43,   43,   40,   40,   44,   44,   44,
   44,   44,   26,   45,   45,   28,   28,   36,   36,   36,
   39,   39,   46,   39,   22,   22,   37,   37,   37,   37,
   37,   37,   37,   37,   37,   37,   37,   37,   37,   37,
   37,   37,   47,   47,   47,   47,   24,   24,   20,   20,
   49,   20,   48,   48,   48,   48,   48,   48,   30,   30,
   30,   50,   50,   50,   51,   51,   51,   53,   54,   54,
   55,   55,   38,   38,   38,   38,   38,   38,   38,   38,
   38,   52,   52,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    1,    2,    1,    2,    2,    3,    9,
    1,    2,    9,   10,    1,    4,    1,    3,    7,    1,
    4,    6,    7,    5,    5,    5,    5,    6,    6,    6,
    6,    5,    4,    3,    3,    4,    2,    1,    2,    1,
    3,    5,    1,    3,    2,    1,    3,    2,    2,    1,
    3,    3,    2,    2,    1,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    1,    1,    2,    4,
    1,    3,    3,    2,    1,    1,    3,    7,    6,    6,
    6,    6,    1,    1,    3,    1,    2,    4,    3,    3,
    9,    8,    0,   17,    1,    2,    8,   10,    7,    7,
    7,    7,    7,    7,    9,    9,    9,    9,    9,    9,
    9,    8,    1,    3,    2,    2,    1,    2,    3,    2,
    0,    3,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    4,    1,    3,
    1,    1,    5,    5,    4,    4,    4,    4,    4,    4,
    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  175,  174,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   76,   78,   80,   82,
   84,   96,    0,    0,    0,    0,    0,  172,    0,    0,
    0,    0,    0,    0,  154,  156,  157,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,  104,   87,
   88,    0,   77,   79,   81,   83,   85,    0,   69,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  109,    0,   19,    0,  173,    0,    0,    0,
  143,  144,  145,    0,    0,  146,  147,  148,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   66,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   89,    0,   94,
    0,  106,    0,   74,    0,   67,    0,   15,    0,    0,
    0,    0,   58,    0,    0,   23,   24,   26,    0,   37,
    0,    0,   97,    1,  108,  161,  162,    0,  159,    0,
    0,    0,    0,    0,    0,    0,    0,  152,  153,  168,
  166,  169,    0,  167,    0,  165,    0,    0,    0,    0,
   65,    0,    0,    0,    0,    0,    0,  103,   93,    0,
  105,  107,    0,    0,   72,   71,    0,    0,    0,    0,
   40,    0,   25,   27,    0,   21,   22,   28,    0,   59,
   57,    0,  158,    0,    0,    0,    0,    0,    0,    0,
    0,  164,  163,    0,   47,    0,    0,   46,    0,    0,
   45,    0,    0,    0,    0,   90,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   38,  160,  137,    0,
  135,  138,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,    0,   51,   50,    0,   49,    0,
    0,    0,    0,  100,  102,    0,  101,    0,    0,    0,
    0,   54,    0,    0,  134,    0,  121,    0,  119,    0,
    0,  124,    0,    0,    0,  122,    0,  120,   43,    0,
   62,  116,    0,    0,    0,   98,    0,    0,   56,    0,
   41,    0,    0,    0,  132,    0,    0,    0,  117,    0,
    0,    0,  113,    0,  112,    0,   35,    0,    0,   52,
    0,  127,  125,  129,  130,    0,  128,  126,   64,    0,
  111,    0,    0,    0,   31,    0,    0,   39,  118,    0,
    0,    0,    0,    0,    0,   32,    0,   36,    0,    0,
   33,   30,    0,   34,    0,    0,    0,    0,  114,
};
final static short yydgoto[] = {                          3,
    4,   15,  260,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  134,  135,  136,  137,  138,  139,  317,   41,
  336,  261,  318,  206,  140,   26,  192,  121,  112,   42,
  142,  113,  291,   70,   71,   27,   28,   29,   30,   31,
   59,   60,   61,   32,   62,  330,  210,   99,   43,   44,
   45,   46,   47,  148,  149,
};
final static short yysindex[] = {                      -101,
    0,  885,    0,  367,  -41,    9,  -39,   -7,   -3,  589,
    0,    0,  400,  -55,  377,    0,    0,    0,    0,    0,
    0,    0,  -54,   31,  -11,   42,    0,    0,    0,    0,
    0,    0,  554,  672,   79, -160,   64,    0, -138,   58,
   87,  -16,  480,   84,    0,    0,    0,   93,   98,    2,
  -32,  -25,   60, -121,  -17,   96,  324,    0,    0,    0,
    0, -111,    0,    0,    0,    0,    0, -143,    0,  109,
  126,    0,    0,  135,    0,  131,    0,  908,  -64,    0,
  682,    0,    0,  138,    0,    5,    0,  -45,  -30,  -42,
    0,    0,    0,    7,    7,    0,    0,    0,    7,    7,
    7,    7,  180,  187,   -1,   16,  201,    0,  -28,  212,
   43,  261,  263,  270,  278,   70,  292,    0,  -47,    0,
  788,    0,   11,    0,   86,    0,   91,    0,   46,  320,
  481,  522,    0,  259,  271,    0,    0,    0,  148,    0,
  346,  861,    0,    0,    0,    0,    0,  170,    0,  662,
  662,  614,  662,   84,   84,   39,   39,    0,    0,    0,
    0,    0,  368,    0,  376,    0,  384,  -20,   30,  -20,
    0,  387, -168,  -20,  396,  333,  -95,    0,    0,  408,
    0,    0,   72,  418,    0,    0,   58,   60,   53,  324,
    0,  194,    0,    0,  148,    0,    0,    0,  196,    0,
    0,    5,    0,  707,    0,  827,  -40,   12,  498,   15,
   88,    0,    0,  -20,    0,  421,  -20,    0,  -20,  436,
    0,  -20,  885,  359,  885,    0,  430,   18,  432,  -18,
  451,   77,  297,  771,  463,    0,    0,    0,    0,  838,
    0,    0,  662,  446,  662,  447,  616,  449,  112,  662,
  456,  662,  458,    0,  -20,    0,    0, -168,    0,  885,
  393,  885,  403,    0,    0,  472,    0,  643,  -89,  474,
  302,    0,  427,    7,    0,  280,    0,  294,    0,  500,
  299,    0,  645,  501,  306,    0,  307,    0,    0,  528,
    0,    0,  524,  460,  530,    0,  579,  464,    0,  531,
    0,  551,  534,  535,    0,  536,  541,  340,    0,  549,
  552, -168,    0,  553,    0,  707,    0,  136,  861,    0,
  556,    0,    0,    0,    0,  557,    0,    0,    0,  351,
    0,  816, -106,  566,    0,  503, -118,    0,    0,  586,
  504,  707,  372,  148,  577,    0,    7,    0,  931,  588,
    0,    0,  597,    0,  390,  525,  885,  533,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  343,  494,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   19,    0,    0,  494,
    0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
    0,    0,  494,  601,  709,  729,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  457,    0,  405,
   48,    0,    0,  341,    0,  428,    0,    0,    0,    0,
  661,    0,    0,  174,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   65,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  494,    0,  527,    0,    0,    0,  494,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  116,  146,  -27,  -23,    0,    0,    0,
    0,    0,  198,    0,  221,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  752,
    0,    0,  494,    0,    0,    0,  494,  494,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  805,    0,    0,    0,    0,    0,
    0,    0,    0,  540,    0,    0,    0,    0,    0,   78,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  167,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  543,    0,    0,    0,    0, -104,
    0,    0,    0,    0,    0,  247,    0,    0,    0,    0,
  -70,    0,    0,  494,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  272,    0,    0,    0,    0,    0,  124,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -62,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  301,    0,    0,    0,    0,
    0,  167,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  494,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   35, 1070,    0,  -10,    0,    0,    0,    0,  558,
   51,    0,    0,    0,    0,  542,  544,  548,  491,  -38,
    0, -190,  335, -184,  476,   -5,    0,  497,  -26,   -4,
    0, -148,  378,  562,    0,   52,   54,   56,   63,   81,
  -19,    0,    0,  -69,    0,    0,  870,  648,    0,  338,
  342,  -33,    0,    0,  493,
};
final static int YYTABLESIZE=1212;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   50,   89,   36,   69,   75,  140,  314,  109,  110,  143,
  152,  179,  167,  139,  117,  114,  342,  142,  155,  240,
  115,    2,  268,  140,  220,  115,   94,  225,   95,  168,
   84,  139,   52,  225,  263,  142,   53,  122,   34,  163,
  119,  118,  107,   97,   98,   96,   58,   70,   40,   39,
  183,   39,  147,   39,   55,   39,  165,  162,  266,  155,
  155,  155,   53,  155,   63,  155,   64,   81,   65,  292,
  216,  294,  141,   76,  164,   66,  265,  155,  155,  155,
  155,   94,  169,   95,  184,  187,   85,  217,  108,   77,
   39,  151,  232,   67,  156,  157,   74,   39,   88,   79,
  116,  182,   39,   86,   39,   60,   70,   11,   12,  290,
   58,   78,  227,  180,  124,  149,   39,  270,   61,   87,
   58,   39,   60,   39,  125,  101,  141,   90,  337,  143,
  102,  332,  151,  103,  151,   61,  151,   83,  104,  205,
  205,  205,  205,  155,  228,  150,   35,  189,  230,  231,
  151,  151,  151,  151,  120,    1,  149,  349,  149,  189,
  149,  115,  123,  290,   63,  224,  358,  126,  147,  127,
  122,  298,   70,  110,  149,  149,  149,  149,   36,   58,
   94,   63,   95,   63,  233,   64,  150,   65,  150,  128,
  150,   55,   55,  239,   66,  242,  145,  171,  205,   53,
   53,   68,   74,   55,  150,  150,  150,  150,   10,  178,
  203,   53,   67,  202,  182,  150,  151,   48,  153,   49,
  170,  243,  244,   58,  108,  140,   35,  271,  108,  242,
  151,  108,  205,  139,  205,  302,  205,  142,  160,  205,
  149,  205,  151,   11,   12,  161,   99,   11,   12,   51,
   11,   12,   91,   92,   93,   11,   12,  205,  105,  166,
  106,  146,   38,   37,   38,   37,   38,   37,   38,  170,
  150,  123,  205,  245,  246,  155,  250,  251,  155,  155,
  155,  155,  155,  155,  155,  155,  205,  155,  155,  155,
  155,  155,  155,  155,  155,  155,  155,  155,  110,  171,
  131,  172,   37,   38,   70,  239,  173,   70,  353,   37,
   38,   70,   70,   70,   37,   38,   37,   38,  175,   70,
   70,  242,  171,   70,   70,   70,   70,  174,   37,   38,
  176,  239,  177,   37,   38,   37,   38,  272,  242,   94,
   20,   95,  300,  185,   94,  170,   95,   68,  151,  252,
  253,  151,  151,  151,  151,  151,  151,  151,  151,  188,
  151,  151,  151,  151,  151,  151,  151,  151,  151,  151,
  151,   99,  149,  283,  284,  149,  149,  149,  149,  149,
  149,  149,  149,  196,  149,  149,  149,  149,  149,  149,
  149,  149,  149,  149,  149,  197,  123,  333,  334,   20,
  103,   20,  150,  199,   68,  150,  150,  150,  150,  150,
  150,  150,  150,  189,  150,  150,  150,  150,  150,  150,
  150,  150,  150,  150,  150,  131,  212,   16,  136,  136,
  110,  154,  155,  110,  213,  110,  110,  110,  110,  110,
  110,  214,  158,  159,  219,  110,  110,  110,  110,  110,
  110,  110,  110,  222,  171,  223,   75,  171,  229,  171,
  171,  171,  171,  171,  171,   20,  226,  235,  131,  171,
  171,  171,  171,  171,  171,  171,  171,  170,  255,  258,
  170,  262,  170,  170,  170,  170,  170,  170,  264,   33,
  267,  269,  170,  170,  170,  170,  170,  170,  170,  170,
   75,   72,  274,   99,  277,  279,   99,  282,   99,   99,
   99,   99,   99,   99,  286,   75,  288,  293,   99,   99,
   99,   99,   99,   99,   99,   99,   73,  295,  123,   68,
  296,  123,  299,  123,  123,  123,  123,  123,  123,   97,
   98,   96,  303,  123,  123,  123,  123,  123,  123,  123,
  123,  301,   16,  141,  141,  141,  304,  131,  305,  309,
  131,  306,  131,  131,  131,  131,  131,  131,  310,  311,
   73,  312,  131,  131,  131,  131,  131,  131,  131,  131,
   54,   75,  313,    6,  314,   73,  319,    7,  315,  320,
   55,  321,  322,  323,  324,    9,   10,   20,   56,  325,
   20,   13,  326,  190,   20,   20,   20,  327,  111,  111,
  328,  331,   20,   20,  338,  339,   20,   20,   20,   20,
  204,  191,  340,    5,  344,  347,    6,  345,  348,  198,
    7,    8,  201,    5,  350,  352,    6,  355,    9,   10,
    7,    8,   11,   12,   13,   14,  354,  357,    9,   10,
  356,   73,   11,   12,   13,   14,   54,  359,  103,    6,
    4,   68,   44,    7,   68,   48,  111,  343,   68,   68,
   68,    9,   10,  193,  237,  194,   68,   68,   80,  195,
   68,   68,   68,   68,   16,  236,  234,   16,  186,  329,
  100,   16,   16,   16,  238,    0,    0,    0,    0,   16,
   16,  316,    0,   16,   16,   16,   16,    0,    0,    0,
    0,   57,    0,   75,    0,    0,   75,    0,    0,    0,
   75,   75,   75,    0,  273,  215,    0,  218,   75,   75,
  111,  221,   75,   75,   75,   75,  204,   54,  204,    0,
    6,    0,    0,    0,    7,    0,  189,   55,   91,   92,
   93,    0,    9,   10,   54,   56,    0,    6,   13,  247,
  248,    7,  141,  141,  141,  204,    0,  204,    0,    9,
   10,  254,    0,    0,  256,   13,  257,    0,   54,  259,
    0,  129,    0,   73,  204,    7,   73,    0,    0,    0,
   73,   73,   73,  130,  131,    0,   82,    0,   73,   73,
    0,    0,   73,   73,   73,   73,  144,    0,    0,  335,
    5,    0,  289,    6,    0,  111,    0,    7,    8,    0,
    0,    0,  341,    0,    0,    9,   10,  346,    0,   11,
   12,   13,   14,   91,  351,   54,    0,    0,    6,  341,
  247,  248,    7,    0,  189,   54,    0,    0,    6,    0,
    9,   10,    7,   95,    0,   55,   13,    0,    0,    0,
    9,   10,    0,   56,    0,    0,   13,    0,    0,  111,
   54,    0,   54,    6,  209,    6,   92,    7,  280,    7,
    0,    0,    0,    0,    0,    9,   10,    9,   10,    0,
    0,   13,    0,   13,    0,  181,    0,    0,    0,   54,
    0,   54,    6,  297,    6,    0,    7,  307,    7,    0,
    0,    0,  181,    0,    9,   10,    9,   10,   54,    0,
   13,    6,   13,    0,    0,    7,    0,    0,    5,  137,
    0,    6,    0,    9,   10,    7,    8,    0,    5,   13,
  275,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,  241,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,  275,   54,    0,   91,    6,    0,   91,    0,
    7,    0,   91,    0,   91,   91,    0,    0,    9,   10,
   91,   91,   91,   91,   13,   95,   91,    0,   95,    0,
    0,    0,   95,    0,   95,   95,    0,    0,    0,    0,
   95,   95,   95,   95,    0,    0,   95,    0,   92,    0,
    0,   92,    0,    0,    0,   92,    0,   92,   92,  207,
  208,    0,  211,   92,   92,   92,   92,   54,    0,   92,
    6,    0,    0,    0,    7,    0,  189,   55,    0,    0,
    0,    0,    9,   10,   54,   56,    0,    6,   13,    0,
    0,    7,    0,    0,   55,    0,    0,    0,    0,    9,
   10,  137,   56,    0,  137,   13,  133,  133,  137,    0,
    0,   16,   54,   16,    0,    6,  137,  137,  249,    7,
    0,  189,  137,   54,   73,    0,    6,    9,   10,    0,
    7,    0,    0,   13,   54,    0,    0,    6,    9,   10,
    0,    7,   16,   73,   13,    0,    0,    0,    0,    9,
   10,    0,  276,    0,  278,   13,  281,    5,    0,  285,
    6,  287,    0,    0,    7,    8,  189,    0,    0,    0,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    0,    5,    0,    0,    6,    0,    0,  133,    7,    8,
   73,    0,  308,    0,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,    5,    0,  249,  129,    0,    0,
    0,    7,    8,    0,    0,    0,    0,    0,    0,  130,
  131,    0,    0,   11,   12,  132,   14,   54,    0,    0,
    6,    0,    0,    0,    7,    0,  189,    0,    0,    0,
    0,    0,    9,   10,    0,    0,    0,    0,   13,    0,
    0,  200,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   40,   40,   44,   59,   59,   41,  125,   40,   41,   79,
   41,   59,   41,   41,   53,   41,  123,   41,    0,  204,
  125,  123,   41,   59,  173,   52,   43,  123,   45,   58,
   35,   59,   40,  123,  225,   59,   40,   57,    4,   41,
   58,   59,   41,   60,   61,   62,   57,    0,   40,   45,
   40,   45,   86,   45,  125,   45,   41,   59,   41,   41,
   42,   43,  125,   45,   13,   47,   13,   33,   13,  260,
   41,  262,   78,   23,   59,   13,   59,   59,   60,   61,
   62,   43,  109,   45,  123,   40,   36,   58,  257,   59,
   45,    0,   40,   13,   99,  100,  257,   45,   41,   58,
   41,  121,   45,   40,   45,   41,   59,  276,  277,  258,
  121,  123,   41,  119,  258,    0,   45,   41,   41,  258,
  131,   45,   58,   45,  268,   42,  132,   41,  319,  199,
   47,  316,   41,   41,   43,   58,   45,   59,   41,  150,
  151,  152,  153,  125,  183,    0,  268,  266,  187,  188,
   59,   60,   61,   62,   59,  257,   41,  342,   43,  266,
   45,  266,  274,  312,   41,  261,  357,   59,  202,   44,
  190,  261,  125,    0,   59,   60,   61,   62,   44,  190,
   43,   58,   45,  132,  189,  132,   41,  132,   43,   59,
   45,  262,  263,  204,  132,  206,   59,    0,  209,  262,
  263,  257,  257,  274,   59,   60,   61,   62,  273,  257,
   41,  274,  132,   44,  234,  261,  125,  257,  261,  259,
    0,  262,  263,  234,  257,  261,  268,  232,  257,  240,
  261,  257,  243,  261,  245,  274,  247,  261,   59,  250,
  125,  252,  261,  276,  277,   59,    0,  276,  277,  257,
  276,  277,  269,  270,  271,  276,  277,  268,  257,   59,
  259,  257,  258,  257,  258,  257,  258,  257,  258,   58,
  125,    0,  283,  262,  263,  257,  262,  263,  260,  261,
  262,  263,  264,  265,  266,  267,  297,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  257,
    0,   41,  257,  258,  257,  316,   44,  260,  347,  257,
  258,  264,  265,  266,  257,  258,  257,  258,   41,  272,
  273,  332,  125,  276,  277,  278,  279,   58,  257,  258,
  261,  342,   41,  257,  258,  257,  258,   41,  349,   43,
    0,   45,   41,  258,   43,  125,   45,  257,  257,  262,
  263,  260,  261,  262,  263,  264,  265,  266,  267,   40,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  125,  257,  262,  263,  260,  261,  262,  263,  264,
  265,  266,  267,  125,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  125,  125,  262,  263,   59,
   58,   59,  257,   58,    0,  260,  261,  262,  263,  264,
  265,  266,  267,  266,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  125,   59,    0,  262,  263,
  257,   94,   95,  260,   59,  262,  263,  264,  265,  266,
  267,   58,  101,  102,   58,  272,  273,  274,  275,  276,
  277,  278,  279,   58,  257,  123,    0,  260,   41,  262,
  263,  264,  265,  266,  267,  125,   59,  274,  273,  272,
  273,  274,  275,  276,  277,  278,  279,  257,   58,   44,
  260,  123,  262,  263,  264,  265,  266,  267,   59,  123,
   59,   41,  272,  273,  274,  275,  276,  277,  278,  279,
   44,  125,   40,  257,   59,   59,  260,   59,  262,  263,
  264,  265,  266,  267,   59,   59,   59,  125,  272,  273,
  274,  275,  276,  277,  278,  279,    0,  125,  257,  125,
   59,  260,   59,  262,  263,  264,  265,  266,  267,   60,
   61,   62,  263,  272,  273,  274,  275,  276,  277,  278,
  279,  125,  125,   60,   61,   62,  263,  257,   59,   59,
  260,  263,  262,  263,  264,  265,  266,  267,  263,  263,
   44,   44,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  125,   59,  260,  125,   59,  123,  264,   59,   59,
  267,   41,   59,   59,   59,  272,  273,  257,  275,   59,
  260,  278,  263,  123,  264,  265,  266,   59,   51,   52,
   59,   59,  272,  273,   59,   59,  276,  277,  278,  279,
  123,  131,  272,  257,   59,   40,  260,  125,  125,  139,
  264,  265,  142,  257,  263,   59,  260,   41,  272,  273,
  264,  265,  276,  277,  278,  279,   59,  123,  272,  273,
  261,  125,  276,  277,  278,  279,  257,  125,   58,  260,
    0,  257,  123,  264,  260,  123,  109,  333,  264,  265,
  266,  272,  273,  132,  199,  132,  272,  273,  125,  132,
  276,  277,  278,  279,  257,  195,  190,  260,  127,  312,
   43,  264,  265,  266,  202,   -1,   -1,   -1,   -1,  272,
  273,  123,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
   -1,  123,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,  234,  168,   -1,  170,  272,  273,
  173,  174,  276,  277,  278,  279,  123,  257,  123,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,  269,  270,
  271,   -1,  272,  273,  257,  275,   -1,  260,  278,  262,
  263,  264,  269,  270,  271,  123,   -1,  123,   -1,  272,
  273,  214,   -1,   -1,  217,  278,  219,   -1,  257,  222,
   -1,  260,   -1,  257,  123,  264,  260,   -1,   -1,   -1,
  264,  265,  266,  272,  273,   -1,  125,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  125,   -1,   -1,  319,
  257,   -1,  255,  260,   -1,  258,   -1,  264,  265,   -1,
   -1,   -1,  332,   -1,   -1,  272,  273,  337,   -1,  276,
  277,  278,  279,  125,  344,  257,   -1,   -1,  260,  349,
  262,  263,  264,   -1,  266,  257,   -1,   -1,  260,   -1,
  272,  273,  264,  125,   -1,  267,  278,   -1,   -1,   -1,
  272,  273,   -1,  275,   -1,   -1,  278,   -1,   -1,  312,
  257,   -1,  257,  260,  261,  260,  125,  264,  263,  264,
   -1,   -1,   -1,   -1,   -1,  272,  273,  272,  273,   -1,
   -1,  278,   -1,  278,   -1,  125,   -1,   -1,   -1,  257,
   -1,  257,  260,  261,  260,   -1,  264,  263,  264,   -1,
   -1,   -1,  125,   -1,  272,  273,  272,  273,  257,   -1,
  278,  260,  278,   -1,   -1,  264,   -1,   -1,  257,  125,
   -1,  260,   -1,  272,  273,  264,  265,   -1,  257,  278,
  125,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  125,  257,   -1,  257,  260,   -1,  260,   -1,
  264,   -1,  264,   -1,  266,  267,   -1,   -1,  272,  273,
  272,  273,  274,  275,  278,  257,  278,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,   -1,   -1,  278,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,  150,
  151,   -1,  153,  272,  273,  274,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  257,  275,   -1,  260,  278,  262,  263,  264,   -1,
   -1,    2,  257,    4,   -1,  260,  272,  273,  209,  264,
   -1,  266,  278,  257,   15,   -1,  260,  272,  273,   -1,
  264,   -1,   -1,  278,  257,   -1,   -1,  260,  272,  273,
   -1,  264,   33,   34,  278,   -1,   -1,   -1,   -1,  272,
  273,   -1,  243,   -1,  245,  278,  247,  257,   -1,  250,
  260,  252,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,  257,   -1,   -1,  260,   -1,   -1,   78,  264,  265,
   81,   -1,  283,   -1,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,  297,  260,   -1,   -1,
   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,
   -1,  142,
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
"sentencia_declarativa : funcion_return_simple",
"sentencia_declarativa : funcion_sentencias_con_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_return_simple : encabezado_funcion '{' cuerpo_funcion_return_simple '}'",
"funcion_sentencias_con_return : encabezado_funcion '{' cuerpo_funcion_sentencias_con_return '}'",
"cuerpo_funcion_sentencias_con_return : sentencia_ejecutable_con_return",
"sentencia_ejecutable_con_return : sentencia_when_con_return",
"sentencia_ejecutable_con_return : DEFER sentencia_when_con_return",
"sentencia_ejecutable_con_return : seleccion_con_return",
"sentencia_ejecutable_con_return : DEFER seleccion_con_return",
"sentencia_ejecutable_con_return : sentencia_do_con_return sentencia_return",
"sentencia_ejecutable_con_return : DEFER sentencia_do_con_return sentencia_return",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : sentencias_when sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';' sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' sentencias_ejecutables sentencia_return '}'",
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
"cuerpo_funcion_return_simple : sentencias_funcion sentencia_return",
"sentencias_funcion : sentencia",
"sentencias_funcion : sentencias_funcion sentencia",
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
			
		ts.print();
	}
}
//#line 803 "Parser.java"
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
case 30:
//#line 78 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 34:
//#line 88 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 39:
//#line 102 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 44:
//#line 113 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 45:
//#line 114 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 46:
//#line 115 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 47:
//#line 116 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 48:
//#line 117 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 49:
//#line 118 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 50:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 51:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 53:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 54:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 55:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 56:
//#line 128 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 62:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 66:
//#line 153 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 67:
//#line 157 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 68:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 69:
//#line 159 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 73:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 74:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 75:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 89:
//#line 194 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 90:
//#line 195 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 91:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 92:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 93:
//#line 198 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 94:
//#line 202 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 95:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 98:
//#line 212 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 99:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 100:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 101:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 102:
//#line 216 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 108:
//#line 234 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 109:
//#line 235 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 110:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 111:
//#line 240 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 112:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 113:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 114:
//#line 243 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 117:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 118:
//#line 253 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 119:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 120:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 121:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 122:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 123:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 124:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 125:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 126:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 128:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 129:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 130:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 131:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 132:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 135:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 136:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 141:
//#line 284 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 142:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 163:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 164:
//#line 331 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 165:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 166:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 167:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 168:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 169:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 170:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 171:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 173:
//#line 343 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1288 "Parser.java"
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
