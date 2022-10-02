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
    7,   11,   11,   12,   13,   13,   13,   16,   16,   15,
   15,    8,    8,    8,   17,   17,   18,   18,   18,   18,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
   24,   24,   24,   25,   25,   25,   25,   25,   26,   26,
   23,   23,   28,   28,   28,   28,   28,   27,   29,   29,
   31,   31,   19,   19,   19,   22,   22,   33,   22,   32,
   32,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   34,   34,   34,
   34,   35,   35,   30,   30,   37,   30,   36,   36,   36,
   36,   36,   36,   14,   14,   14,   38,   38,   38,   39,
   39,   39,   41,   42,   42,   43,   43,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    3,    6,    7,    6,    1,    3,    5,    1,    3,    2,
    1,    3,    2,    2,    1,    3,    3,    2,    2,    1,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    1,    1,    2,    4,    1,    3,    3,    2,    1,
    1,    3,    7,    6,    6,    6,    6,    1,    1,    3,
    1,    2,    4,    3,    3,    9,    8,    0,   17,    1,
    2,    8,   10,    7,    7,    7,    7,    7,    7,    9,
    9,    9,    9,    9,    9,    9,    8,    1,    3,    2,
    2,    1,    2,    3,    2,    0,    3,    1,    1,    1,
    1,    1,    1,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    4,    1,    3,    1,    1,    5,    5,    4,
    4,    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  140,  139,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   41,   43,   45,   47,   49,    0,
   61,    0,    0,    0,    0,    0,  137,    0,    0,    0,
    0,    0,    0,  119,  121,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   51,   69,   52,   53,    0,
   42,   44,   46,   48,   50,    0,   34,    0,    0,    2,
    8,    0,   17,    0,   16,    0,    0,    0,    0,    5,
    0,    3,   74,    0,   18,    0,  138,    0,    0,  108,
  109,  110,    0,    0,  111,  112,  113,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,    0,   59,   71,    0,    0,   39,    0,   32,    0,
   14,    0,    0,   21,   62,    1,   73,  126,  127,    0,
  124,    0,    0,    0,    0,    0,    0,    0,    0,  117,
  118,  133,  131,  134,    0,  132,    0,  130,   31,    0,
    0,    0,    0,    0,    0,   68,   58,    0,   70,   72,
    0,    0,   37,   36,   20,    0,    0,  123,    0,    0,
    0,    0,    0,    0,    0,    0,  129,  128,    0,   30,
    0,    0,    0,    0,    0,   55,    0,    0,    0,    0,
  125,  102,    0,    0,    0,  100,  103,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,    0,    0,    0,
    0,    0,    0,   65,   67,    0,   66,    0,   99,    0,
   86,    0,   84,    0,    0,   89,    0,    0,    0,   87,
    0,   85,   23,    0,   81,    0,    0,    0,   63,   24,
    0,    0,   97,    0,    0,    0,   82,    0,    0,    0,
   27,   78,    0,   77,   92,   90,   94,   95,    0,   93,
   91,    0,    0,   76,   83,   29,    0,    0,    0,    0,
    0,    0,    0,   79,
};
final static short yydgoto[] = {                          3,
    4,   77,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   78,  152,   40,  250,  251,   68,   69,   25,   26,
   27,   28,   29,   57,   58,   59,   30,   31,   60,   41,
  115,  211,  263,  171,  172,   98,   42,   43,   44,   45,
   46,  130,  131,
};
final static short yysindex[] = {                      -105,
    0,  628,    0,  277,  -40,   -8,  -27, -228,   11,  -96,
    0,    0,  387,  -51,  -59,  628,    0,    0,    0,    0,
    0,  -33,   22,  302,    0,    0,    0,    0,    0,   17,
    0,  508,  -30,   -6, -147,   72,    0, -144,  105,  -26,
   92,  -50,    8,    0,    0,    0,   99,  104,  -19,   85,
  117, -133,   61,   89,  651,    0,    0,    0,    0, -117,
    0,    0,    0,    0,    0, -181,    0,  107,  119,    0,
    0,  126,    0,  124,    0,  628, -115,   53,  -86,    0,
   63,    0,    0,   13,    0,   -4,    0,  -72,  -35,    0,
    0,    0,  139,  139,    0,    0,    0,  139,  -70,  139,
  139,  139,  133,  135,   23,   30,  138,  -41,  -60,  161,
    0,  -28,    0,    0,  599,   94,    0,  -48,    0,  -46,
    0,   82,  172,    0,    0,    0,    0,    0,    0,   77,
    0,  279,  279,  312,    8,    8,   58,  279,   58,    0,
    0,    0,    0,    0,  156,    0,  158,    0,    0,  164,
  -24,  193,  198,  114, -114,    0,    0,  188,    0,    0,
  128,  207,    0,    0,    0,  139,   -4,    0,  250,    0,
 -140,  358, -135,  -64, -125, -120,    0,    0, -123,    0,
  197, -203,  628,  134,  628,    0,  200,   39,  201,   59,
    0,    0,  510,  279,  224,    0,    0,  279,  246,  348,
  247, -107,  279,  269,  279,  270,    0, -123,  212,  628,
  229,  628,  236,    0,    0,  325,    0,  328,    0,  136,
    0,  140,    0,  339,  142,    0,  404,  367,  165,    0,
  174,    0,    0, -203,    0,  389,  324,  392,    0,    0,
  399,  401,    0,  413,  422,  230,    0,  435,  436,  454,
    0,    0,  441,    0,    0,    0,    0,    0,  442,    0,
    0, -203,  234,    0,    0,    0,  468,  139,  472,  248,
  396,  628,  400,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  101,  143,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,  143,    0,
    0,    0,   24,    0,    0,    0,    0,    0,    0,    0,
  143,  463,  532,  558,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  415,    0,  239,  377,    0,
    0,  461,    0,  484,    0,    0,    0,    0,    0,    0,
  524,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  143,    0,  438,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,   70,  -36,    0,  -34,    0,
    0,    0,    0,    0,  116,    0,  144,    0,    0,    0,
    0,    0,  485,    0,    0,    0,    0,  580,    0,    0,
  143,    0,    0,    0,    0,    0,    0,    0,    0,  618,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -82,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  488,  408,
    0,    0,    0,    0,    0,  167,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  190,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  497,
    0,    0,    0,    0,    0,    0,    0,    0,  213,    0,
    0,    0,    0,    0,    0,    0,    0,  143,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   36, -155,    0,  597,    0,    0,    0,  424,   56,
    0,  469,    0,   -1,  -88,  282,  426,    0,  534,  535,
  545,  547,  548,  -10,    0,    0,  452,  486,    0,  -37,
    0, -136,    0,  397,  414,  526,    0,   81,   84,  -71,
    0,    0,  403,
};
final static int YYTABLESIZE=929;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        150,
  120,   89,  105,   35,  104,  134,  107,   67,  185,   96,
   97,   95,   49,  110,  129,    7,   93,    2,   94,  153,
  105,  107,  104,  116,  107,   73,   55,  210,   50,  210,
  157,   39,   84,   96,   97,   95,   38,   15,   38,   33,
   38,  120,  120,  120,  114,  120,  114,  120,  213,  101,
   51,   71,   83,  149,  102,   93,  210,   94,  169,  120,
  120,  120,  120,  145,  116,   70,  116,   81,  116,  115,
  147,  127,   11,   12,   79,  237,  117,   74,  162,  216,
   75,  144,  116,  116,  116,  116,  118,  114,  146,  114,
   85,  114,   75,  209,   82,  129,  137,  215,  139,  218,
   93,   93,   94,   94,  160,  114,  114,  114,  114,   72,
  115,   86,  115,   87,  115,  136,  210,  168,  112,  111,
  167,  194,  195,  188,  108,  120,  198,  199,  115,  115,
  115,  115,   99,  161,   34,  273,  203,  204,   38,  103,
    7,  205,  206,  135,  104,   88,  184,  113,  116,   38,
  123,    1,   11,   12,  227,  228,  116,  109,   68,   19,
   52,   38,  120,    6,  190,  119,   64,    7,  187,   35,
   53,  114,   38,  135,  136,    9,   10,  124,   54,  101,
  101,   13,  121,   38,  140,  141,   10,  126,  132,   88,
  138,  142,   52,  143,  115,    6,  148,  200,  201,    7,
  154,  155,  106,  106,  106,   66,  165,    9,   10,  163,
   66,  166,   96,   13,  177,  149,  178,   75,   90,   91,
   92,  179,  105,   72,  104,  133,  107,   34,  156,   47,
  269,   48,  180,  181,   11,   12,  183,  105,   33,  106,
  136,  182,   90,   91,   92,  235,  186,  189,   36,   37,
   36,   37,  128,   37,  208,  234,  212,  120,  214,  217,
  120,  120,  120,  120,  120,  120,  120,  120,  135,  120,
  120,  120,  120,  120,  120,  120,  120,  120,  120,  120,
  116,    7,  221,  116,  116,  116,  116,  116,  116,  116,
  116,   64,  116,  116,  116,  116,  116,  116,  116,  116,
  116,  116,  116,  114,  223,  226,  114,  114,  114,  114,
  114,  114,  114,  114,   88,  114,  114,  114,  114,  114,
  114,  114,  114,  114,  114,  114,  115,  230,  232,  115,
  115,  115,  115,  115,  115,  115,  115,   96,  115,  115,
  115,  115,  115,  115,  115,  115,  115,  115,  115,   75,
   36,   37,   75,  236,   75,   75,   75,   75,   75,   75,
  238,   36,   37,   33,   75,   75,   75,   75,   75,   75,
   75,   75,  136,   36,   37,  136,   35,  136,  136,  136,
  136,  136,  136,  239,   36,   37,  240,  136,  136,  136,
  136,  136,  136,  136,  136,   36,   37,  243,  241,   32,
  135,  169,  242,  135,  244,  135,  135,  135,  135,  135,
  135,  106,  106,  106,   40,  135,  135,  135,  135,  135,
  135,  135,  135,   64,   76,  247,   64,  248,   64,   64,
   64,   64,   64,   64,  169,   35,  249,   38,   64,   64,
   64,   64,   64,   64,   64,   64,   88,  252,  253,   88,
  254,   88,   88,   88,   88,   88,   88,  255,   40,  256,
   19,   88,   88,   88,   88,   88,   88,   88,   88,   96,
  169,  257,   96,   40,   96,   96,   96,   96,   96,   96,
  258,   38,  196,   15,   96,   96,   96,   96,   96,   96,
   96,   96,  259,  260,  261,   33,   38,  262,   33,  264,
  265,   35,   33,   33,   33,  267,   52,  268,  271,    6,
   33,   33,  270,    7,   33,   33,   33,   33,  272,   19,
   68,    9,   10,    4,  274,   25,  169,   13,   26,  173,
  175,  151,   80,    5,  176,   52,    6,   28,    6,   40,
    7,    8,    7,  266,  122,  164,   61,   62,    9,   10,
    9,   10,   11,   12,   13,   14,   13,   63,    5,   64,
   65,    6,   38,  158,  125,    7,    8,  100,   52,  191,
  202,    6,  174,    9,   10,    7,    0,   11,   12,   13,
   14,    0,  193,    9,   10,   19,    0,    0,    0,   13,
  220,    0,    0,    0,  222,    0,  225,    0,    0,  229,
    0,  231,  207,    0,   52,  151,   56,    6,   15,    0,
  224,    7,    0,    0,   52,    0,    0,    6,    0,    9,
   10,    7,    0,  246,    0,   13,    0,    0,    0,    9,
   10,  233,   80,   35,  219,   13,   35,    0,    0,    0,
   35,   35,   35,   52,    0,    0,    6,    0,   35,   35,
    7,   56,   35,   35,   35,   35,   56,  151,    9,   10,
   52,    0,    0,    6,    0,    0,  245,    7,    0,    0,
    0,   40,    0,    0,   40,    9,   10,    0,   40,   40,
   40,   13,   60,    0,    0,  151,   40,   40,    0,    0,
   40,   40,   40,   40,   38,    0,    0,   38,    0,    0,
    0,   38,   38,   38,   57,    0,    0,    0,    0,   38,
   38,   56,    0,   38,   38,   38,   38,   19,    0,    0,
   19,    0,    0,  159,   19,   19,   19,    0,  170,  170,
  170,    0,   19,   19,  170,    0,   19,   19,   19,   19,
   15,    0,  102,   15,    0,    0,    0,   15,   15,   15,
    0,    0,    0,    0,    0,   15,   15,    0,    0,   15,
   15,   15,   15,    0,    5,  192,   52,    6,  197,    6,
  170,    7,    8,    7,    0,    0,    0,    0,    0,    9,
   10,    9,   10,   11,   12,   13,   14,   13,   56,  197,
  170,   56,    0,    0,  170,   56,  170,    0,   56,  170,
    0,  170,    0,   56,   56,   56,   56,    0,    0,   56,
    0,    0,    0,    0,   60,    0,    0,   60,    0,    0,
    0,   60,    0,  170,   60,    0,    0,    0,    0,   60,
   60,   60,   60,    0,    0,   60,   57,    0,    0,   57,
    0,    0,    0,   57,    0,    0,   57,    0,    0,    0,
    0,   57,   57,   57,   57,   52,    0,   57,    6,    0,
    0,    0,    7,    0,    0,   53,    0,    0,    0,    0,
    9,   10,    0,   54,  102,    0,   13,  102,    0,   98,
   98,  102,    0,    0,    5,    0,    0,    6,    0,  102,
  102,    7,    8,    0,    0,  102,    0,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,   52,    0,    0,
    6,    0,    0,    0,    7,    0,    0,   53,    0,    0,
    0,    0,    9,   10,    0,   54,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   39,   41,   44,   41,   41,   41,   59,  123,   60,
   61,   62,   40,   51,   86,    0,   43,  123,   45,  108,
   59,   41,   59,    0,   59,   59,  123,  183,  257,  185,
   59,   40,   34,   60,   61,   62,   45,    2,   45,    4,
   45,   41,   42,   43,   55,   45,    0,   47,  185,   42,
   40,   16,   59,  257,   47,   43,  212,   45,  123,   59,
   60,   61,   62,   41,   41,  125,   43,   32,   45,    0,
   41,   59,  276,  277,   58,  212,  258,   22,  116,   41,
   59,   59,   59,   60,   61,   62,  268,   41,   59,   43,
   35,   45,    0,  182,  125,  167,   98,   59,  100,   41,
   43,   43,   45,   45,  115,   59,   60,   61,   62,  257,
   41,   40,   43,  258,   45,    0,  272,   41,   58,   59,
   44,  262,  263,  161,   40,  125,  262,  263,   59,   60,
   61,   62,   41,   40,  268,  272,  262,  263,   45,   41,
  125,  262,  263,    0,   41,   41,  261,   59,  125,   45,
  266,  257,  276,  277,  262,  263,  274,   41,   58,   59,
  257,   45,   44,  260,  166,   59,    0,  264,   41,   44,
  267,  125,   45,   93,   94,  272,  273,  125,  275,  262,
  263,  278,   59,   45,  101,  102,  273,  125,  261,    0,
  261,   59,  257,   59,  125,  260,   59,  262,  263,  264,
  261,   41,   60,   61,   62,  257,  125,  272,  273,  258,
  257,   40,    0,  278,   59,  257,   59,  125,  269,  270,
  271,   58,  261,  257,  261,  261,  261,  268,  257,  257,
  268,  259,  257,   41,  276,  277,  123,  257,    0,  259,
  125,   44,  269,  270,  271,  210,   59,   41,  257,  258,
  257,  258,  257,  258,   58,   44,  123,  257,   59,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,   59,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   59,   59,  260,
  261,  262,  263,  264,  265,  266,  267,  125,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  257,  258,  260,  125,  262,  263,  264,  265,  266,  267,
  125,  257,  258,  125,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  257,  258,  260,    0,  262,  263,  264,
  265,  266,  267,   59,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  258,   59,  263,  123,
  257,  123,  263,  260,  263,  262,  263,  264,  265,  266,
  267,  269,  270,  271,    0,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,   59,  260,  263,  262,  263,
  264,  265,  266,  267,  123,   59,  263,    0,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   59,  125,  260,
   59,  262,  263,  264,  265,  266,  267,   59,   44,   59,
    0,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  123,   59,  260,   59,  262,  263,  264,  265,  266,  267,
   59,   44,  125,    0,  272,  273,  274,  275,  276,  277,
  278,  279,  263,   59,   59,  257,   59,   44,  260,   59,
   59,  125,  264,  265,  266,  272,  257,   40,  261,  260,
  272,  273,   41,  264,  276,  277,  278,  279,  123,   59,
   58,  272,  273,    0,  125,   41,  123,  278,   41,  133,
  134,  108,  125,  257,  138,  257,  260,   41,  260,  125,
  264,  265,  264,  262,   76,  120,   13,   13,  272,  273,
  272,  273,  276,  277,  278,  279,  278,   13,  257,   13,
   13,  260,  125,  112,   79,  264,  265,   42,  257,  167,
  174,  260,  261,  272,  273,  264,   -1,  276,  277,  278,
  279,   -1,  169,  272,  273,  125,   -1,   -1,   -1,  278,
  194,   -1,   -1,   -1,  198,   -1,  200,   -1,   -1,  203,
   -1,  205,  179,   -1,  257,  182,   10,  260,  125,   -1,
  263,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,   -1,  227,   -1,  278,   -1,   -1,   -1,  272,
  273,  208,  125,  257,  125,  278,  260,   -1,   -1,   -1,
  264,  265,  266,  257,   -1,   -1,  260,   -1,  272,  273,
  264,   55,  276,  277,  278,  279,  125,  234,  272,  273,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,
   -1,  257,   -1,   -1,  260,  272,  273,   -1,  264,  265,
  266,  278,  125,   -1,   -1,  262,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,  125,   -1,   -1,   -1,   -1,  272,
  273,  115,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,  125,  264,  265,  266,   -1,  132,  133,
  134,   -1,  272,  273,  138,   -1,  276,  277,  278,  279,
  257,   -1,  125,  260,   -1,   -1,   -1,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,   -1,  257,  169,  257,  260,  172,  260,
  174,  264,  265,  264,   -1,   -1,   -1,   -1,   -1,  272,
  273,  272,  273,  276,  277,  278,  279,  278,  257,  193,
  194,  260,   -1,   -1,  198,  264,  200,   -1,  267,  203,
   -1,  205,   -1,  272,  273,  274,  275,   -1,   -1,  278,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,  227,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,  278,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,  257,   -1,  278,  260,   -1,  262,
  263,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,  265,   -1,   -1,  278,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
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
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion : encabezado_funcion '{' cuerpo_funcion '}'",
"funcion : encabezado_funcion cuerpo_funcion '}'",
"encabezado_funcion : FUN ID '(' ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':' tipo",
"cuerpo_funcion : sentencias RETURN '(' expresion ')' ';'",
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

//#line 280 ".\gramatica.y"

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
//#line 676 "Parser.java"
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
case 21:
//#line 57 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 27:
//#line 72 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 31:
//#line 82 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 32:
//#line 86 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 34:
//#line 88 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 38:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 39:
//#line 99 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 40:
//#line 100 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 54:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 55:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 56:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 57:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 58:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 59:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 60:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 63:
//#line 141 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 64:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 65:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 66:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 67:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 73:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 74:
//#line 164 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 75:
//#line 165 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 76:
//#line 169 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 77:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 78:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 79:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 82:
//#line 181 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 83:
//#line 182 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 84:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 85:
//#line 184 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 86:
//#line 185 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 87:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 88:
//#line 187 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 89:
//#line 188 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 90:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 91:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 92:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 93:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 94:
//#line 193 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 95:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 96:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 97:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 100:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 101:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 106:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 107:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 128:
//#line 259 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 129:
//#line 260 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 130:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 131:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 132:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 133:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 134:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 135:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 136:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 138:
//#line 272 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1097 "Parser.java"
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
