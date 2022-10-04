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
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   14,   12,   13,   13,   13,   17,   17,   16,   16,    8,
    8,    8,   18,   18,   19,   19,   19,   19,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   25,   25,
   25,   26,   26,   26,   26,   26,   27,   27,   24,   24,
   29,   29,   29,   29,   29,   28,   30,   30,   32,   32,
   20,   20,   20,   23,   23,   34,   23,   33,   33,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   35,   35,   35,   35,   36,
   36,   31,   31,   38,   31,   37,   37,   37,   37,   37,
   37,   15,   15,   15,   39,   39,   39,   40,   40,   40,
   42,   43,   43,   44,   44,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   41,   41,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    2,    1,    3,    5,    1,    3,    2,    1,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    0,   17,    1,    2,    8,
   10,    7,    7,    7,    7,    7,    7,    9,    9,    9,
    9,    9,    9,    9,    8,    1,    3,    2,    2,    1,
    2,    3,    2,    0,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    4,    1,    3,    1,    1,    5,    5,    4,    4,    4,
    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  148,  147,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   49,   51,   53,   55,   57,    0,
   69,    0,    0,    0,    0,    0,  145,    0,    0,    0,
    0,    0,    0,  127,  129,  130,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,   77,   60,   61,
    0,   50,   52,   54,   56,   58,    0,   42,    0,    0,
    2,    8,    0,   17,    0,   16,    0,    0,    5,    0,
    3,   82,    0,   18,    0,  146,    0,    0,  116,  117,
  118,    0,    0,  119,  120,  121,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   62,    0,   67,   79,
    0,    0,   47,    0,   40,    0,   14,    0,    0,   70,
    1,   81,  134,  135,    0,  132,    0,    0,    0,    0,
    0,    0,    0,    0,  125,  126,  141,  139,  142,    0,
  140,    0,  138,    0,    0,    0,    0,   38,    0,    0,
    0,    0,    0,    0,   76,   66,    0,   78,   80,    0,
    0,   45,   44,    0,   32,   20,    0,  131,    0,    0,
    0,    0,    0,    0,    0,    0,  137,  136,    0,   26,
    0,    0,   25,    0,    0,   24,    0,    0,    0,    0,
   63,    0,    0,    0,    0,  133,  110,    0,    0,    0,
  108,  111,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   21,    0,   30,   29,    0,   28,    0,    0,    0,
    0,   73,   75,    0,   74,    0,  107,    0,   94,    0,
   92,    0,    0,   97,    0,    0,    0,   95,    0,   93,
   22,    0,   35,   89,    0,    0,    0,   71,    0,    0,
    0,  105,    0,    0,    0,   90,    0,    0,    0,   86,
    0,   85,   31,  100,   98,  102,  103,    0,  101,   99,
   37,    0,   84,   91,    0,    0,    0,    0,    0,    0,
    0,   87,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,  180,   19,   20,   21,   22,   23,
   24,  129,  111,  175,   40,  112,  253,   69,   70,   25,
   26,   27,   28,   29,   58,   59,   60,   30,   31,   61,
   41,  121,  229,  282,  181,  182,   97,   42,   43,   44,
   45,   46,  135,  136,
};
final static short yysindex[] = {                       -89,
    0,  672,    0, -113,  -31,  134,   -5,  -13,   10,  341,
    0,    0, -130,  -50,  -80,  672,    0,    0,    0,    0,
    0,  -39,   12,  -43,    0,    0,    0,    0,    0,   18,
    0,  520,  -19,  152, -132,  109,    0, -118,  148,  169,
  112,  476,   63,    0,    0,    0,  120,  142,  -25,  -34,
  -30,  154,  -93,  114,  126,  695,    0,    0,    0,    0,
  -84,    0,    0,    0,    0,    0, -101,    0,  133,  150,
    0,    0,  161,    0,  158,    0,  672,  -51,    0,   95,
    0,    0,   58,    0,    2,    0,  -37,  -16,    0,    0,
    0,  267,  267,    0,    0,    0,  267,   -8,  267,  267,
  267,  196,  198,   24,   25,  223,    0,  -36,  229,   40,
  280,  301,  303,  318,  121,  330,    0,  -38,    0,    0,
  619,  146,    0,  136,    0,  127,    0,  141,  299,    0,
    0,    0,    0,    0,   67,    0,  503,  503,  455,   63,
   63,  103,  503,  103,    0,    0,    0,    0,    0,  368,
    0,  383,    0,  385,  -95,   28,  -95,    0,  395, -219,
  -95,  396,  332,  -92,    0,    0,  407,    0,    0,  168,
  426,    0,    0,  429,    0,    0,    2,    0, -205,    0,
  -75, -102,  -62,  365,  -59,  -47,    0,    0,  -95,    0,
  419,  -95,    0,  -95,  434,    0,  -95,  672,  356,  672,
    0,  432,   38,  433,  267,    0,    0,  376,  503,  435,
    0,    0,  503,  443,  479,  444,   22,  503,  456,  503,
  458,    0,  -95,    0,    0, -219,    0,  672,  389,  672,
  394,    0,    0,  464,    0,   88,    0,  263,    0,  264,
    0,  471,  268,    0,  501,  480,  278,    0,  287,    0,
    0,  507,    0,    0,  493,  428,  495,    0,  498,  499,
  504,    0,  505,  506,  304,    0,  510,  513, -219,    0,
  514,    0,    0,    0,    0,    0,    0,  515,    0,    0,
    0,  294,    0,    0,  539,  267,  540,  319,  459,  672,
  460,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  191,  484,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,  484,    0,
    0,    0,   29,    0,    0,    0,    0,    0,    0,    0,
    0,  484,  528,  550,  572,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  393,    0,  256,  311,
    0,    0,  417,    0,  283,    0,    0,    0,    0,  591,
    0,    0,  100,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -26,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   37,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  484,    0,  445,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   77,  -23,    0,  -22,    0,    0,    0,    0,    0,  123,
    0,  156,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  595,    0,    0,  484,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  643,
    0,    0,    0,    0,    0,    0,    0,    0,  469,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  472,    0,    0,    0,    0,  474,    0,    0,
    0,    0,    0,  184,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  208,    0,    0,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  233,    0,    0,
    0,    0,    0,    0,    0,  484,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    8, -149,    0,   -2,    0,    0,    0,  543,  110,
    0,    0,   13,    0,    5, -119,  327,  477,    0,  584,
  587,  593,  594,  596,  -28,    0,    0,  486,  532,    0,
  -35,    0, -174,    0,  715,  436,  569,    0,  276,  314,
  -53,    0,    0,  440,
};
final static int YYTABLESIZE=973;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
  128,   18,    7,   88,  154,  108,  109,   57,   68,   32,
  113,   33,   35,   18,  113,  106,  116,  112,  115,   74,
  166,  155,  211,   72,  139,  231,   51,  120,  124,   18,
  200,  134,  113,    2,   49,  112,  115,  107,   83,   80,
  195,  128,  128,  128,   71,  128,   38,  128,  228,   52,
  228,   53,  122,   57,    6,  256,   11,   12,    7,  128,
  128,  128,  128,  114,  150,  152,    9,   10,  191,  124,
   76,  124,   13,  124,   18,   78,  123,   33,  234,   77,
  228,   34,  149,  151,  128,  192,  171,  124,  124,  124,
  124,   36,  169,  122,   33,  122,  233,  122,   34,   83,
   92,  142,   93,  144,  100,   81,  252,  178,   36,  101,
  177,  122,  122,  122,  122,  291,  132,  123,   57,  123,
  156,  123,  144,  134,   73,  128,   53,    7,  259,    6,
   92,   75,   93,    7,  203,  123,  123,  123,  123,   86,
  228,    9,   10,    5,   84,   92,    6,   93,   85,  252,
    7,    8,   98,  124,   53,  143,  123,    6,    9,   10,
  102,    7,   11,   12,   13,   14,  124,    1,  199,    9,
   10,  118,  117,   39,   34,   13,  207,  122,   38,  212,
   11,   12,  103,   72,  119,  170,  209,  210,   87,  122,
   38,  125,   38,  126,  115,   18,   38,   18,   38,  213,
  214,  123,  218,  219,   35,  212,   67,   96,  202,  236,
   82,   92,   38,   93,  220,  221,  127,   73,  165,  131,
  107,   10,  107,  137,   83,   18,  107,   18,   95,   96,
   94,  104,  104,  105,  113,  254,   34,  112,  115,   11,
   12,   11,   12,   50,  138,   11,   12,  144,   76,   19,
  287,   47,  143,   48,  147,   41,  148,  128,  133,   37,
  128,  128,  128,  128,  128,  128,  128,  128,    7,  128,
  128,  128,  128,  128,  128,  128,  128,  128,  128,  128,
  143,  153,   15,  245,  246,  124,  157,   18,  124,  124,
  124,  124,  124,  124,  124,  124,  158,  124,  124,  124,
  124,  124,  124,  124,  124,  124,  124,  124,   72,  122,
   43,   38,  122,  122,  122,  122,  122,  122,  122,  122,
  159,  122,  122,  122,  122,  122,  122,  122,  122,  122,
  122,  122,   96,  123,  109,  109,  123,  123,  123,  123,
  123,  123,  123,  123,  160,  123,  123,  123,  123,  123,
  123,  123,  123,  123,  123,  123,   83,  104,  162,   83,
  161,   83,   83,   83,   83,   83,   83,  140,  141,   43,
  164,   83,   83,   83,   83,   83,   83,   83,   83,  144,
   41,  163,  144,   67,  144,  144,  144,  144,  144,  144,
   36,   37,   48,  172,  144,  144,  144,  144,  144,  144,
  144,  144,   36,   37,   36,   37,  174,   15,   36,   37,
   36,   37,  143,  145,  146,  143,   19,  143,  143,  143,
  143,  143,  143,  176,   36,   37,  187,  143,  143,  143,
  143,  143,  143,  143,  143,   43,   48,   89,   90,   91,
   72,  188,  189,   72,   46,   72,   72,   72,   72,   72,
   72,   48,  194,  197,  198,   72,   72,   72,   72,   72,
   72,   72,   72,   56,   96,  201,  204,   96,  205,   96,
   96,   96,   96,   96,   96,   19,  223,  226,  230,   96,
   96,   96,   96,   96,   96,   96,   96,  179,   46,  104,
  232,  235,  104,  239,  104,  104,  104,  104,  104,  104,
  237,  241,  244,   46,  104,  104,  104,  104,  104,  104,
  104,  104,   41,  255,  248,   41,  250,   48,  257,   41,
   41,   41,  258,   36,   37,  260,  261,   41,   41,  262,
  263,   41,   41,   41,   41,   95,   96,   94,  266,   15,
  267,   19,   15,  114,  114,  114,   15,   15,   15,  268,
  269,  270,  271,  272,   15,   15,  273,  274,   15,   15,
   15,   15,  275,  276,  277,  285,  278,   43,  279,   46,
   43,  280,  283,  284,   43,   43,   43,  179,  286,  289,
  288,  290,   43,   43,  292,   76,   43,   43,   43,   43,
    4,   23,  110,  110,   27,  281,   62,   53,   88,   63,
    6,  179,  173,  167,    7,   64,   65,   54,   66,  130,
   99,    0,    9,   10,  208,   55,  206,    0,   13,    0,
    0,   53,    0,  179,    6,  179,  215,  216,    7,    0,
    0,    0,   53,    0,    0,    6,    9,   10,    0,    7,
    0,    0,   13,    0,   79,    0,    0,    9,   10,   48,
  110,    0,   48,   13,    0,    0,   48,   48,   48,    0,
    0,    0,    0,    0,   48,   48,    0,    0,   48,   48,
   48,   48,    0,   19,   64,    0,   19,    0,    0,    0,
   19,   19,   19,    0,    0,    0,    0,    0,   19,   19,
    0,    0,   19,   19,   19,   19,   68,  190,    0,  193,
    0,   46,  110,  196,   46,    0,    0,    0,   46,   46,
   46,   53,    0,    0,    6,  184,   46,   46,    7,   65,
   46,   46,   46,   46,    0,    0,    9,   10,    0,    0,
    0,  222,   13,    0,  224,   53,  225,    0,    6,  227,
    0,  242,    7,  168,   89,   90,   91,    0,    0,    0,
    9,   10,  114,  114,  114,    0,   13,   53,    0,   53,
    6,    0,    6,  264,    7,  251,    7,  110,  110,    0,
    0,    0,    9,   10,    9,   10,    5,    0,   13,    6,
   13,    0,    0,    7,    8,    0,    0,    0,    0,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,    0,
    0,    0,    0,    0,    0,    0,   64,    0,    0,   64,
    0,  110,    0,   64,    0,    0,   64,    0,    0,    0,
    0,   64,   64,   64,   64,    0,    0,   64,   68,    0,
    0,   68,    0,    0,    0,   68,    0,    0,   68,    0,
    0,    0,    0,   68,   68,   68,   68,    0,    0,   68,
    0,   65,  183,  185,   65,    0,    0,  186,   65,    0,
    0,   65,    0,    0,    0,    0,   65,   65,   65,   65,
    0,    0,   65,    0,    0,   53,    0,    0,    6,    0,
    0,    0,    7,    0,    0,   54,    0,    0,    0,    0,
    9,   10,    0,   55,    0,    0,   13,    0,  217,  110,
    0,    0,  110,    0,  106,  106,  110,    0,    0,    0,
    0,    0,    0,    0,  110,  110,    0,    0,    0,    0,
  110,    0,    0,  238,    0,    0,    0,  240,    5,  243,
    0,    6,  247,    0,  249,    7,    8,    0,    0,    0,
    0,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,   53,    0,    0,    6,    0,    0,    0,    7,  265,
    0,   54,    0,    0,    0,    0,    9,   10,    0,   55,
    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,    4,    0,   39,   41,   40,   41,   10,   59,  123,
   41,    4,   44,   16,   41,   41,   52,   41,   41,   59,
   59,   58,  125,   16,   41,  200,   40,   56,    0,   32,
  123,   85,   59,  123,   40,   59,   59,  257,   34,   32,
  160,   41,   42,   43,  125,   45,   45,   47,  198,   40,
  200,  257,    0,   56,  260,  230,  276,  277,  264,   59,
   60,   61,   62,   51,   41,   41,  272,  273,   41,   41,
   59,   43,  278,   45,   77,   58,    0,   41,   41,  123,
  230,   41,   59,   59,   77,   58,  122,   59,   60,   61,
   62,   41,  121,   41,   58,   43,   59,   45,   58,    0,
   43,   97,   45,   99,   42,  125,  226,   41,   58,   47,
   44,   59,   60,   61,   62,  290,   59,   41,  121,   43,
  108,   45,    0,  177,  257,  125,  257,  125,   41,  260,
   43,   22,   45,  264,  170,   59,   60,   61,   62,  258,
  290,  272,  273,  257,   35,   43,  260,   45,   40,  269,
  264,  265,   41,  125,  257,    0,  258,  260,  272,  273,
   41,  264,  276,  277,  278,  279,  268,  257,  261,  272,
  273,   58,   59,   40,  268,  278,  179,  125,   45,  182,
  276,  277,   41,    0,   59,   40,  262,  263,   41,  274,
   45,   59,   45,   44,   41,  198,   45,  200,   45,  262,
  263,  125,  262,  263,   44,  208,  257,    0,   41,  205,
   59,   43,   45,   45,  262,  263,   59,  257,  257,  125,
  257,  273,  257,  261,  125,  228,  257,  230,   60,   61,
   62,  257,    0,  259,  261,  228,  268,  261,  261,  276,
  277,  276,  277,  257,  261,  276,  277,  125,   58,   59,
  286,  257,  261,  259,   59,    0,   59,  257,  257,  258,
  260,  261,  262,  263,  264,  265,  266,  267,  266,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  125,   59,    0,  262,  263,  257,   58,  290,  260,  261,
  262,  263,  264,  265,  266,  267,  257,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  257,
    0,   45,  260,  261,  262,  263,  264,  265,  266,  267,
   41,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  125,  257,  262,  263,  260,  261,  262,  263,
  264,  265,  266,  267,   44,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  125,   41,  260,
   58,  262,  263,  264,  265,  266,  267,   92,   93,   59,
   41,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  261,  260,  257,  262,  263,  264,  265,  266,  267,
  257,  258,    0,  258,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  258,  257,  258,  266,  125,  257,  258,
  257,  258,  257,  100,  101,  260,    0,  262,  263,  264,
  265,  266,  267,  125,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  125,   44,  269,  270,  271,
  257,   59,   58,  260,    0,  262,  263,  264,  265,  266,
  267,   59,   58,   58,  123,  272,  273,  274,  275,  276,
  277,  278,  279,  123,  257,   59,   41,  260,   40,  262,
  263,  264,  265,  266,  267,   59,   58,   44,  123,  272,
  273,  274,  275,  276,  277,  278,  279,  123,   44,  257,
   59,   59,  260,   59,  262,  263,  264,  265,  266,  267,
  125,   59,   59,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  125,   59,  260,   59,  125,  125,  264,
  265,  266,   59,  257,  258,  263,  263,  272,  273,   59,
  263,  276,  277,  278,  279,   60,   61,   62,   59,  257,
  263,  125,  260,   60,   61,   62,  264,  265,  266,  263,
   44,   59,  125,   59,  272,  273,   59,   59,  276,  277,
  278,  279,   59,   59,   59,  272,  263,  257,   59,  125,
  260,   59,   59,   59,  264,  265,  266,  123,   40,  261,
   41,  123,  272,  273,  125,   58,  276,  277,  278,  279,
    0,  123,   50,   51,  123,  269,   13,  257,  125,   13,
  260,  123,  126,  118,  264,   13,   13,  267,   13,   78,
   42,   -1,  272,  273,  179,  275,  177,   -1,  278,   -1,
   -1,  257,   -1,  123,  260,  123,  262,  263,  264,   -1,
   -1,   -1,  257,   -1,   -1,  260,  272,  273,   -1,  264,
   -1,   -1,  278,   -1,  125,   -1,   -1,  272,  273,  257,
  108,   -1,  260,  278,   -1,   -1,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,  257,  125,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  125,  155,   -1,  157,
   -1,  257,  160,  161,  260,   -1,   -1,   -1,  264,  265,
  266,  257,   -1,   -1,  260,  261,  272,  273,  264,  125,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
   -1,  189,  278,   -1,  192,  257,  194,   -1,  260,  197,
   -1,  263,  264,  125,  269,  270,  271,   -1,   -1,   -1,
  272,  273,  269,  270,  271,   -1,  278,  257,   -1,  257,
  260,   -1,  260,  263,  264,  223,  264,  125,  226,   -1,
   -1,   -1,  272,  273,  272,  273,  257,   -1,  278,  260,
  278,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
   -1,  269,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,   -1,   -1,  278,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,
   -1,  257,  138,  139,  260,   -1,   -1,  143,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
   -1,   -1,  278,   -1,   -1,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,   -1,   -1,  278,   -1,  184,  257,
   -1,   -1,  260,   -1,  262,  263,  264,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,
  278,   -1,   -1,  209,   -1,   -1,   -1,  213,  257,  215,
   -1,  260,  218,   -1,  220,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  245,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,
   -1,   -1,  278,
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
"sentencias : sentencia sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : sentencia_declarativa_variables",
"sentencia_declarativa : funcion_return_simple",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_return_simple : encabezado_funcion '{' cuerpo_funcion_return_simple '}'",
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
"cuerpo_funcion_return_simple : sentencias sentencia_return",
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
"sentencia_when : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' bloque_sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' THEN '{' bloque_sentencias_when '}' ';' $$1 WHEN '(' condicion ')' THEN '{' bloque_sentencias_when '}'",
"bloque_sentencias_when : sentencia",
"bloque_sentencias_when : sentencia sentencias",
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

//#line 291 ".\gramatica.y"

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
//#line 702 "Parser.java"
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
case 35:
//#line 83 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 39:
//#line 93 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 40:
//#line 97 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 41:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 42:
//#line 99 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 46:
//#line 109 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 47:
//#line 110 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 48:
//#line 111 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 62:
//#line 134 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 63:
//#line 135 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 64:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 65:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 66:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 67:
//#line 142 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 68:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 71:
//#line 152 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 72:
//#line 153 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 73:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 74:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 75:
//#line 156 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 81:
//#line 174 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 82:
//#line 175 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 83:
//#line 176 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 84:
//#line 180 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 85:
//#line 181 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 86:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 87:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 90:
//#line 192 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 91:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 92:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 93:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 94:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 95:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 96:
//#line 198 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 97:
//#line 199 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 98:
//#line 200 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 99:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 100:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 101:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 102:
//#line 204 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 103:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 104:
//#line 206 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 105:
//#line 207 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 108:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 109:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 114:
//#line 224 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 115:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 136:
//#line 270 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 137:
//#line 271 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 138:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 139:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 140:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 141:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 142:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 143:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 144:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 146:
//#line 283 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1119 "Parser.java"
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
