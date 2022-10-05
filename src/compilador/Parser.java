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
    4,    4,    4,    6,    6,    6,    6,   10,   10,    7,
   12,   12,   14,   14,   15,   15,   15,   15,   15,   15,
   15,   15,   18,   20,   20,   16,   22,   22,   17,   17,
   24,   26,   26,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   13,   13,   13,   13,   13,   28,   28,
   28,   31,   31,   30,   30,    8,    8,    8,   32,   32,
   33,   33,   33,   33,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    5,   39,   39,   39,   40,   40,   40,
   40,   40,   41,   41,   38,   38,   42,   42,   42,   42,
   42,   25,   43,   43,   27,   27,   34,   34,   34,   37,
   37,   44,   37,   23,   23,   35,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   35,   45,   45,   45,   45,   21,   21,   19,   19,   47,
   19,   46,   46,   46,   46,   46,   46,   29,   29,   29,
   48,   48,   48,   49,   49,   49,   51,   52,   52,   53,
   53,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   50,   50,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    1,    2,    1,    2,    1,    1,    1,    2,    1,    2,
    1,    2,    8,    1,    4,    9,    1,    2,    1,    3,
    7,    1,    4,    6,    7,    5,    5,    5,    5,    6,
    6,    6,    6,    5,    4,    3,    3,    4,    1,    3,
    5,    1,    3,    2,    1,    3,    2,    2,    1,    3,
    3,    2,    2,    1,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    2,    1,    1,    1,    2,    4,    1,
    3,    3,    2,    1,    1,    3,    7,    6,    6,    6,
    6,    1,    1,    3,    1,    2,    4,    3,    3,    9,
    8,    0,   17,    1,    2,    8,   10,    7,    7,    7,
    7,    7,    7,    9,    9,    9,    9,    9,    9,    9,
    8,    1,    3,    2,    2,    1,    2,    3,    2,    0,
    3,    1,    1,    1,    1,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  174,  173,    0,    0,    0,    7,    9,   10,   11,   12,
   13,    0,    0,    0,    0,   75,   77,   79,   81,   83,
   95,    0,    0,    0,    0,    0,  171,    0,    0,    0,
    0,    0,    0,  153,  155,  156,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   85,  103,   86,   87,
    0,   76,   78,   80,   82,   84,    0,   68,    0,    0,
    2,    8,    0,   17,    0,   16,    0,    0,    5,    0,
    3,  108,    0,   18,    0,  172,    0,    0,    0,  142,
  143,  144,    0,    0,  145,  146,  147,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   88,    0,   93,    0,
  105,    0,   73,    0,   66,    0,   14,    0,    0,    0,
    0,    0,   25,   26,    0,   21,    0,   23,   27,   29,
   31,   39,    0,   96,    1,  107,  160,  161,    0,  158,
    0,    0,    0,    0,    0,    0,    0,    0,  151,  152,
  167,  165,  168,    0,  166,    0,  164,    0,    0,    0,
    0,   64,    0,    0,    0,    0,    0,    0,  102,   92,
    0,  104,  106,    0,    0,   71,   70,    0,    0,    0,
    0,    0,   42,    0,   28,   30,   32,   20,   22,   24,
    0,    0,  157,    0,    0,    0,    0,    0,    0,    0,
    0,  163,  162,    0,   49,    0,    0,   48,    0,    0,
   47,    0,    0,    0,    0,   89,    0,    0,    0,    0,
    0,    0,   56,    0,    0,    0,   40,  159,  136,    0,
  134,  137,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   44,    0,   53,   52,    0,   51,    0,
    0,    0,    0,   99,  101,    0,  100,    0,   58,    0,
    0,    0,    0,  133,    0,  120,    0,  118,    0,    0,
  123,    0,    0,    0,  121,    0,  119,   45,    0,   61,
  115,    0,    0,    0,   97,    0,   54,    0,   43,    0,
    0,    0,  131,    0,    0,    0,  116,    0,    0,    0,
  112,    0,  111,    0,   34,    0,    0,    0,  126,  124,
  128,  129,    0,  127,  125,   63,    0,  110,    0,    0,
   37,    0,    0,   41,  117,    0,    0,   33,    0,   38,
    0,   35,   36,    0,    0,    0,    0,    0,  113,
};
final static short yydgoto[] = {                          3,
    4,   15,  260,   17,  205,   19,   20,   21,   22,   23,
   24,  135,  136,  137,  138,  139,  140,  141,   40,  316,
  206,  332,  261,  142,   25,  194,  120,  111,   41,  112,
  290,   69,   70,   26,   27,   28,   29,   30,   58,   59,
   60,   31,   61,  327,  210,   98,   42,   43,   44,   45,
   46,  149,  150,
};
final static short yysindex[] = {                       -83,
    0,  913,    0,  368,  -41,   -4,    2,   -1,   21,  438,
    0,    0,  273,  -52,  638,    0,    0,    0,    0,    0,
    0,  -32,   41,  -68,   47,    0,    0,    0,    0,    0,
    0,  648,  673,   35, -150,   93,    0, -121,   40,   98,
  -28,   61,   55,    0,    0,    0,  111,  122,  -23,  -31,
  -25,   42,  -95,  156,   88,  936,    0,    0,    0,    0,
  -97,    0,    0,    0,    0,    0, -196,    0,  126,  151,
    0,    0,  153,    0,  133,    0,  863,  -73,    0,  696,
    0,    0,  294,    0,  -39,    0,  -58,  -30,  -24,    0,
    0,    0,    6,    6,    0,    0,    0,    6,    6,    6,
    6,  198,  231,   58,  117,  243,    0,  -29,  247,   89,
  272,  300,  290,  310,  102,  313,    0,   30,    0,  804,
    0,   28,    0,  149,    0,  144,    0,   31,   37,  392,
  544,  366,    0,    0,  315,    0,  863,    0,    0,    0,
    0,    0,  384,    0,    0,    0,    0,    0,   15,    0,
  628,  628,  416,  628,   55,   55,   53,   53,    0,    0,
    0,    0,    0,  398,    0,  399,    0,  397,  -60,   86,
  -60,    0,  409, -166,  -60,  411,  347,  -78,    0,    0,
  421,    0,    0,   68,  440,    0,    0,   40,   71,   63,
   42,  936,    0,  209,    0,    0,    0,    0,    0,    0,
  217,  -39,    0,  780,    0,  833,  -33,   87,  580,  115,
  137,    0,    0,  -60,    0,  434,  -60,    0,  -60,  458,
    0,  -60,  913,  380,  913,    0,  449,  127,  450,   -6,
  452,  295,    0,  479,  782,  494,    0,    0,    0,  853,
    0,    0,  628,  476,  628,  484,  483,  485,  141,  628,
  488,  628,  491,    0,  -60,    0,    0, -166,    0,  913,
  417,  913,  424,    0,    0,  500,    0,  599,    0,  501,
  -74,  437,    6,    0,  309,    0,  312,    0,  514,  318,
    0,  618,  517,  319,    0,  320,    0,    0,  540,    0,
    0,  529,  464,  535,    0,  563,    0,  472,    0,  556,
  542,  543,    0,  545,  546,  336,    0,  551,  552, -166,
    0,  553,    0,  780,    0,  350,  890,  555,    0,    0,
    0,    0,  558,    0,    0,    0,  352,    0,  396,  568,
    0,  504, -101,    0,    0,  591,  509,    0,  576,    0,
    6,    0,    0,  596,  381,  525,  913,  524,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  283,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    5,    0,    0,   64,    0,
    0,    0,   95,    0,    0,    0,    0,    0,    0,    0,
    0,   64,  592,  720,  739,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  343,    0,  314,  493,
    0,    0,  518,    0,  406,    0,    0,    0,    0,  651,
    0,    0,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   91,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   64,    0,  460,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  119,  148,  -22,  -21,    0,    0,
    0,    0,    0,  171,    0,  199,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  758,    0,    0,   64,    0,    0,    0,   64,    0,    0,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  823,    0,    0,    0,    0,    0,
    0,    0,    0,  531,    0,    0,    0,    0,    0,  143,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -104,    0,    0,    0,    0,    0,    0,    0,  167,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  532,    0,    0,    0,    0, -100,
    0,    0,    0,    0,    0,  222,    0,    0,    0, -102,
    0,    0,   64,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  250,    0,    0,    0,    0,    0,  155,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  291,    0,    0,    0,    0,    0,  167,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   82, 1100,  -34,   -2,    0,    0,    0,  490,  106,
    0,    0,  401,    0,  515,  526,  549,  559,  -38,    0,
 -147,    0, -172,  456,  -17,    0,  495,   23,  407, -116,
  365,  564,    0,   50,   56,   66,   79,   80,   14,    0,
    0,  -50,    0,    0,  869,  650,    0,  358,  353,  -59,
    0,    0,  497,
};
final static int YYTABLESIZE=1214;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   88,   18,   35,  139,  154,   38,   68,   57,  108,  109,
  153,  168,   18,  116,   93,  113,   94,  106,  138,  141,
   57,  139,   55,  312,  114,  148,   74,  144,  169,   18,
   18,   96,   97,   95,  268,   39,  138,  141,   51,    2,
   38,   49,  133,  109,  225,  154,  154,  154,  225,  154,
   38,  154,  263,   57,   77,  203,  240,  220,  202,  143,
   52,  123,   62,  154,  154,  154,  154,  184,   63,  121,
  188,  124,   38,  114,  134,   38,  189,   18,   64,   38,
   87,   38,  115,  185,   38,   33,   38,  291,  180,  293,
  107,   65,   66,   82,  150,   93,  100,   94,  164,   76,
  181,  101,  133,  233,   78,   93,   73,   94,  227,   11,
   12,  231,   38,   80,  143,   38,  163,   57,  148,  143,
   96,   97,   95,  140,  140,  140,  216,   75,   57,  154,
  170,   59,   85,  183,  134,  150,   86,  150,   89,  150,
   84,  289,  148,  217,  333,  228,  119,  149,   59,  230,
  144,  102,  234,  150,  150,  150,  150,  166,   57,  148,
   55,  148,  103,  148,  129,  114,  329,  266,  109,   57,
  170,   55,   34,    1,  348,  165,  122,  148,  148,  148,
  148,   62,  224,   60,  125,  265,  298,   63,  149,   57,
  149,  127,  149,  289,  126,   62,   35,   64,  169,   10,
   60,  239,  151,  242,   67,  121,  149,  149,  149,  149,
   65,   66,   62,  118,  117,   11,   12,  147,   37,  150,
   18,   98,   18,  139,   73,  107,   34,  107,  243,  244,
  152,  107,   57,  104,  300,  105,  154,  242,  138,  141,
   90,   91,   92,  148,   11,   12,   11,   12,  183,  122,
   11,   12,   36,   37,  152,   50,  161,   18,   47,   18,
   48,  154,   36,   37,  154,  154,  154,  154,  154,  154,
  154,  154,  149,  154,  154,  154,  154,  154,  154,  154,
  154,  154,  154,  154,   36,   37,  179,   36,   37,  162,
  130,   36,   37,   36,   37,  170,   36,   37,   36,   37,
  109,  167,  344,  109,  171,  109,  109,  109,  109,  109,
  109,  239,  173,   67,   18,  109,  109,  109,  109,  109,
  109,  109,  109,  169,   36,   37,  242,   36,   37,   90,
   91,   92,  140,  140,  140,  270,   93,   93,   94,   94,
  102,   19,   74,  174,   18,  172,   98,  175,  245,  246,
  176,  150,  146,  178,  150,  150,  150,  150,  150,  150,
  150,  150,  177,  150,  150,  150,  150,  150,  150,  150,
  150,  150,  150,  150,  122,  148,  250,  251,  148,  148,
  148,  148,  148,  148,  148,  148,   74,  148,  148,  148,
  148,  148,  148,  148,  148,  148,  148,  148,  252,  253,
   67,   74,  282,  283,  149,   15,  186,  149,  149,  149,
  149,  149,  149,  149,  149,  130,  149,  149,  149,  149,
  149,  149,  149,  149,  149,  149,  149,  170,  135,  135,
  170,  191,  170,  170,  170,  170,  170,  170,   67,  198,
   83,  201,  170,  170,  170,  170,  170,  170,  170,  170,
  155,  156,  159,  160,  214,  169,  212,  213,  169,   72,
  169,  169,  169,  169,  169,  169,  219,   74,  222,  223,
  169,  169,  169,  169,  169,  169,  169,  169,   98,  226,
  229,   98,  236,   98,   98,   98,   98,   98,   98,  131,
   32,  255,   69,   98,   98,   98,   98,   98,   98,   98,
   98,  258,  262,   72,  157,  158,  122,  264,  267,  122,
  269,  122,  122,  122,  122,  122,  122,   19,   72,  271,
  274,  122,  122,  122,  122,  122,  122,  122,  122,   53,
   15,  193,    6,  273,  276,  190,    7,  199,  204,  110,
  110,  292,  278,  281,    9,   10,  285,  130,  294,  287,
  130,   69,  130,  130,  130,  130,  130,  130,  295,  297,
   56,  299,  130,  130,  130,  130,  130,  130,  130,  130,
   67,  301,  303,   67,  302,  307,   19,   67,   67,   67,
  304,  308,  309,  310,   72,   67,   67,  311,  312,   67,
   67,   67,   67,  313,  317,  232,  318,  110,  323,   74,
  319,  320,   74,  321,  322,  204,   74,   74,   74,  324,
  325,  328,  330,  334,   74,   74,  335,   69,   74,   74,
   74,   74,   53,  336,    5,  128,  338,    6,  339,    7,
  341,    7,    8,  342,  343,  272,  345,  130,  131,    9,
   10,  346,   19,   11,   12,   13,   14,  347,  349,  102,
    4,  200,   53,   46,   50,    6,  237,  195,  215,    7,
  218,  129,   15,  110,  221,   15,  192,    9,   10,   15,
   15,   15,   53,   13,  326,    6,  209,   15,   15,    7,
  196,   15,   15,   15,   15,  314,  235,    9,   10,  187,
  197,   99,    0,   13,   53,    0,  315,    6,  238,    0,
    0,    7,  204,  254,   54,    0,  256,    0,  257,    9,
   10,  259,   55,    0,    0,   13,   72,  331,    0,   72,
    0,  204,    0,   72,   72,   72,    0,    0,    0,  337,
    0,   72,   72,  340,    0,   72,   72,   72,   72,   53,
  204,    0,    6,    0,  288,  279,    7,  110,    0,   69,
  204,    0,   69,    0,    9,   10,   69,   69,   69,    0,
   13,    0,   71,    0,   69,   69,    0,    0,   69,   69,
   69,   69,   79,    0,   19,    0,    0,   19,    0,    0,
    0,   19,   19,   19,    0,    0,    0,    0,    0,   19,
   19,    0,    0,   19,   19,   19,   19,   81,    0,  110,
   53,    0,    0,    6,    0,    0,    0,    7,    0,  129,
   54,    0,    0,    0,    0,    9,   10,    0,   55,   53,
  145,   13,    6,    0,  247,  248,    7,    0,  129,    0,
    0,    0,    0,    0,    9,   10,   53,    0,    0,    6,
   13,  247,  248,    7,   90,    0,    0,    0,    0,    0,
    0,    9,   10,    0,    0,   53,    0,   13,    6,  296,
    0,    0,    7,   94,    0,    0,    0,    0,    0,    0,
    9,   10,    0,    0,   53,    0,   13,    6,    0,    0,
  305,    7,   91,    0,   53,    0,    0,    6,    0,    9,
   10,    7,    0,    0,    5,   13,    0,    6,    0,    9,
   10,    7,    8,    0,    5,   13,  182,    6,    0,    9,
   10,    7,    8,   11,   12,   13,   14,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    0,  182,    5,
    0,    0,    6,    0,    0,    0,    7,    8,    0,    0,
    0,    0,    0,    0,    9,   10,    0,  136,   11,   12,
   13,   14,    5,    0,    0,    6,    0,  241,    0,    7,
    8,    0,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,    0,   90,  274,    0,   90,
    0,    0,    0,   90,    0,   90,   90,    0,    0,    0,
    0,   90,   90,   90,   90,   94,    0,   90,   94,    0,
    0,    0,   94,    0,   94,   94,    0,    0,    0,    0,
   94,   94,   94,   94,   91,    0,   94,   91,    0,  207,
  208,   91,  211,   91,   91,    0,    0,    0,    0,   91,
   91,   91,   91,    0,    0,   91,   53,    0,   53,    6,
    0,    6,    0,    7,    0,    7,    0,  129,   54,    0,
    0,    9,   10,    9,   10,    0,   55,   13,    0,   13,
   53,    0,    0,    6,    0,    0,    0,    7,    0,    0,
   54,    0,    0,    0,    0,    9,   10,  249,   55,  136,
    0,   13,  136,    0,  132,  132,  136,    0,    0,   53,
    0,    0,    6,    0,  136,  136,    7,    0,    0,    0,
  136,   16,    0,   16,    9,   10,    0,    0,    0,   53,
   13,  275,    6,  277,   72,  280,    7,    0,  284,    5,
  286,    0,  128,    0,    9,   10,    7,    8,  129,    0,
   13,   16,   72,    0,  130,  131,    0,    0,   11,   12,
  132,   14,    0,    0,    0,    0,    5,    0,    0,    6,
  306,    0,    0,    7,    8,  129,    0,    0,    0,    0,
    0,    9,   10,    0,  249,   11,   12,   13,   14,    5,
    0,    0,    6,    0,    0,    0,    7,    8,    0,   72,
    0,    0,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,   53,    0,    0,    6,    0,    0,    0,    7,
    0,    0,   54,    0,    0,    0,    0,    9,   10,    0,
   55,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   39,    4,   44,   41,    0,   45,   59,   10,   40,   41,
   41,   41,   15,   52,   43,   41,   45,   41,   41,   41,
  125,   59,  125,  125,  125,   85,   59,   78,   58,   32,
   33,   60,   61,   62,   41,   40,   59,   59,   40,  123,
   45,   40,   77,    0,  123,   41,   42,   43,  123,   45,
   45,   47,  225,   56,  123,   41,  204,  174,   44,   77,
   40,  258,   13,   59,   60,   61,   62,   40,   13,   56,
   40,  268,   45,   51,   77,   45,   40,   80,   13,   45,
   41,   45,   41,  122,   45,    4,   45,  260,   59,  262,
  257,   13,   13,   59,    0,   43,   42,   45,   41,   59,
  118,   47,  137,   41,   58,   43,  257,   45,   41,  276,
  277,   41,   45,   32,  132,   45,   59,  120,    0,  137,
   60,   61,   62,   60,   61,   62,   41,   22,  131,  125,
  108,   41,   40,  120,  137,   41,  258,   43,   41,   45,
   35,  258,  202,   58,  317,  184,   59,    0,   58,  188,
  201,   41,  191,   59,   60,   61,   62,   41,  263,   41,
  263,   43,   41,   45,  266,  266,  314,   41,  125,  274,
    0,  274,  268,  257,  347,   59,  274,   59,   60,   61,
   62,  132,  261,   41,   59,   59,  261,  132,   41,  192,
   43,   59,   45,  310,   44,   41,   44,  132,    0,  273,
   58,  204,  261,  206,  257,  192,   59,   60,   61,   62,
  132,  132,   58,   58,   59,  276,  277,  257,  258,  125,
  223,    0,  225,  261,  257,  257,  268,  257,  262,  263,
  261,  257,  235,  257,  273,  259,  261,  240,  261,  261,
  269,  270,  271,  125,  276,  277,  276,  277,  235,    0,
  276,  277,  257,  258,  261,  257,   59,  260,  257,  262,
  259,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  125,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  258,  257,  257,  258,   59,
    0,  257,  258,  257,  258,  125,  257,  258,  257,  258,
  257,   59,  341,  260,   58,  262,  263,  264,  265,  266,
  267,  314,   41,    0,  317,  272,  273,  274,  275,  276,
  277,  278,  279,  125,  257,  258,  329,  257,  258,  269,
  270,  271,  269,  270,  271,   41,   43,   43,   45,   45,
   58,   59,    0,   44,  347,  257,  125,   58,  262,  263,
   41,  257,   59,   41,  260,  261,  262,  263,  264,  265,
  266,  267,  261,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  125,  257,  262,  263,  260,  261,
  262,  263,  264,  265,  266,  267,   44,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  262,  263,
  257,   59,  262,  263,  257,    0,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  262,  263,
  260,   40,  262,  263,  264,  265,  266,  267,  125,  125,
   34,   58,  272,  273,  274,  275,  276,  277,  278,  279,
   93,   94,  100,  101,   58,  257,   59,   59,  260,    0,
  262,  263,  264,  265,  266,  267,   58,  125,   58,  123,
  272,  273,  274,  275,  276,  277,  278,  279,  257,   59,
   41,  260,  274,  262,  263,  264,  265,  266,  267,  273,
  123,   58,    0,  272,  273,  274,  275,  276,  277,  278,
  279,   44,  123,   44,   98,   99,  257,   59,   59,  260,
   59,  262,  263,  264,  265,  266,  267,    0,   59,   41,
  125,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  131,  260,   40,   59,  129,  264,  137,  123,   50,
   51,  125,   59,   59,  272,  273,   59,  257,  125,   59,
  260,   59,  262,  263,  264,  265,  266,  267,   59,   59,
  123,  125,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  263,   59,  260,  263,   59,   59,  264,  265,  266,
  263,  263,  263,   44,  125,  272,  273,   59,  125,  276,
  277,  278,  279,   59,  123,  189,   41,  108,  263,  257,
   59,   59,  260,   59,   59,  123,  264,  265,  266,   59,
   59,   59,  263,   59,  272,  273,   59,  125,  276,  277,
  278,  279,  257,  272,  257,  260,   59,  260,  125,  264,
   40,  264,  265,  125,   59,  235,   41,  272,  273,  272,
  273,  261,  125,  276,  277,  278,  279,  123,  125,   58,
    0,  137,  257,  123,  123,  260,  201,  132,  169,  264,
  171,  266,  257,  174,  175,  260,  123,  272,  273,  264,
  265,  266,  257,  278,  310,  260,  261,  272,  273,  264,
  132,  276,  277,  278,  279,  123,  192,  272,  273,  126,
  132,   42,   -1,  278,  257,   -1,  296,  260,  202,   -1,
   -1,  264,  123,  214,  267,   -1,  217,   -1,  219,  272,
  273,  222,  275,   -1,   -1,  278,  257,  317,   -1,  260,
   -1,  123,   -1,  264,  265,  266,   -1,   -1,   -1,  329,
   -1,  272,  273,  333,   -1,  276,  277,  278,  279,  257,
  123,   -1,  260,   -1,  255,  263,  264,  258,   -1,  257,
  123,   -1,  260,   -1,  272,  273,  264,  265,  266,   -1,
  278,   -1,  125,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  125,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  125,   -1,  310,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,  257,
  125,  278,  260,   -1,  262,  263,  264,   -1,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,  257,   -1,   -1,  260,
  278,  262,  263,  264,  125,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  257,   -1,  278,  260,  261,
   -1,   -1,  264,  125,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  257,   -1,  278,  260,   -1,   -1,
  263,  264,  125,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,   -1,   -1,  257,  278,   -1,  260,   -1,  272,
  273,  264,  265,   -1,  257,  278,  125,  260,   -1,  272,
  273,  264,  265,  276,  277,  278,  279,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  125,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,  125,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,  125,   -1,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,   -1,  257,  125,   -1,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,  151,
  152,  264,  154,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,  278,  257,   -1,  257,  260,
   -1,  260,   -1,  264,   -1,  264,   -1,  266,  267,   -1,
   -1,  272,  273,  272,  273,   -1,  275,  278,   -1,  278,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,
  267,   -1,   -1,   -1,   -1,  272,  273,  209,  275,  257,
   -1,  278,  260,   -1,  262,  263,  264,   -1,   -1,  257,
   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,
  278,    2,   -1,    4,  272,  273,   -1,   -1,   -1,  257,
  278,  243,  260,  245,   15,  247,  264,   -1,  250,  257,
  252,   -1,  260,   -1,  272,  273,  264,  265,  266,   -1,
  278,   32,   33,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
  282,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,  296,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   80,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,
  275,   -1,   -1,  278,
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
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_con_return : encabezado_funcion '{' cuerpo_funcion_con_return '}'",
"cuerpo_funcion_con_return : sentencia_return",
"cuerpo_funcion_con_return : sentencias_funcion_con_return sentencia_return",
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
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

//#line 345 ".\gramatica.y"

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
//#line 799 "Parser.java"
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
case 14:
//#line 44 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 15:
//#line 45 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 16:
//#line 46 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 17:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 20:
//#line 56 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 36:
//#line 90 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 41:
//#line 104 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 46:
//#line 115 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 47:
//#line 116 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 48:
//#line 117 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 49:
//#line 118 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 50:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 51:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 52:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 53:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 55:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 56:
//#line 128 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 57:
//#line 129 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 58:
//#line 130 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 61:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 65:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 66:
//#line 150 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 67:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 68:
//#line 152 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 72:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 73:
//#line 163 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 74:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 88:
//#line 187 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 89:
//#line 188 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 90:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 91:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 92:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 93:
//#line 195 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 94:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 97:
//#line 205 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 98:
//#line 206 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 99:
//#line 207 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 100:
//#line 208 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 101:
//#line 209 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 107:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 108:
//#line 228 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 109:
//#line 229 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 110:
//#line 233 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 111:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 112:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 113:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 116:
//#line 245 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 117:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 118:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 119:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 120:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 121:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 122:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 123:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 124:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 125:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 126:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 127:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 128:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 129:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 130:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 131:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 134:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 135:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 140:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 141:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 162:
//#line 323 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 163:
//#line 324 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 164:
//#line 325 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 165:
//#line 326 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 166:
//#line 327 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 167:
//#line 328 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 168:
//#line 329 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 169:
//#line 330 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 170:
//#line 331 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 172:
//#line 336 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1272 "Parser.java"
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
