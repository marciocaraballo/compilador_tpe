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
    0,    0,    1,    2,    3,    3,    4,    4,    5,    5,
    5,    7,    7,    7,   11,   11,    8,    8,   12,   12,
   13,   14,   14,   14,   17,   17,   16,   16,    9,    9,
    9,   18,   18,   19,   19,   19,   19,    6,    6,    6,
    6,    6,    6,    6,    6,    6,    6,   25,   25,   25,
   26,   26,   26,   26,   26,   27,   27,   24,   24,   29,
   29,   29,   29,   29,   28,   30,   30,   32,   32,   20,
   20,   20,   23,   23,   23,   23,   33,   23,   21,   21,
   34,   34,   35,   35,   31,   31,   37,   31,   36,   36,
   36,   36,   36,   36,   15,   15,   15,   38,   38,   38,
   39,   39,   39,   41,   42,   42,   43,   43,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   40,   40,   10,
   10,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    3,    2,    2,    3,    1,    4,    3,    6,    7,
    6,    1,    3,    5,    1,    3,    2,    1,    3,    2,
    2,    1,    3,    3,    2,    2,    1,    1,    2,    2,
    3,    1,    2,    1,    2,    1,    2,    1,    1,    1,
    2,    4,    1,    3,    3,    2,    1,    1,    3,    7,
    6,    6,    6,    6,    1,    1,    3,    1,    2,    4,
    3,    3,    7,    6,    6,    6,    0,   11,    7,    9,
    1,    3,    1,    2,    3,    2,    0,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    4,    1,    3,    1,    1,    5,    5,
    4,    4,    4,    4,    4,    4,    4,    1,    2,    1,
    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  121,  120,    0,    0,    0,    0,    7,    8,    9,
   10,   11,    0,    0,    0,   38,    0,   42,   44,   46,
    0,   58,    1,    0,    0,    0,    0,    0,    0,    0,
    0,  118,    0,    0,    0,    0,    0,    0,  100,  102,
  103,    0,    0,    0,    0,   48,   66,   49,   50,    0,
   39,    0,   43,   45,   47,    0,   31,    0,    0,    4,
    6,    0,    0,   14,    0,    0,    0,   40,    0,   71,
    0,   15,    0,    0,    0,    0,    0,    0,    0,    0,
  119,    0,    0,   89,   90,   91,    0,    0,   92,   93,
   94,    0,    0,    0,    0,    0,   51,    0,   56,   68,
    0,    0,   41,   36,    0,   29,    0,   12,    0,    0,
   18,   59,   70,    0,  114,  112,  115,    0,  113,    0,
  111,   28,    0,    0,    0,    0,  107,  108,    0,  105,
    0,    0,    0,    0,    0,    0,    0,    0,   98,   99,
   65,   55,    0,   67,   69,    0,    0,   34,   33,   17,
    0,    0,  110,  109,    0,   27,    0,    0,    0,  104,
   77,    0,    0,    0,    0,   52,    0,    0,    0,    0,
    0,   81,    0,   19,    0,    0,  106,    0,   75,    0,
   76,   74,   62,   64,    0,   63,    0,   83,    0,    0,
   79,   20,    0,    0,   73,   60,   21,   82,   84,    0,
    0,   24,    0,   80,    0,    0,   26,    0,   78,
};
final static short yydgoto[] = {                          3,
    4,    5,   76,   17,   18,   19,   20,   21,   22,   23,
   24,   25,   77,  135,   45,  211,  212,   68,   69,   26,
   27,   28,   29,   30,   57,   58,   59,   31,   32,   60,
   46,  111,  188,  183,  199,  102,   47,   48,   49,   50,
   51,  139,  140,
};
final static short yysindex[] = {                       -97,
    0,  469,    0,  -99,    0,  -28,  -10,   64, -219,   77,
  185,    0,    0,  -88,  -54,  -70,  469,    0,    0,    0,
    0,    0, -198,   19,  -83,    0,   23,    0,    0,    0,
   14,    0,    0,   87, -198,   89,   58,   83,   65,   96,
   99,    0, -130,   29,   88,  102,   94,   10,    0,    0,
    0, -124,    3,   86, -159,    0,    0,    0,    0, -122,
    0,  103,    0,    0,    0, -236,    0,  104,  117,    0,
    0,  120,  106,    0,  469, -100,   42,    0, -105,    0,
   -6,    0,  129,  112,  114,  -12,   -8,  124,   39,   93,
    0,  -99,  -34,    0,    0,    0,   89,   89,    0,    0,
    0,   89,  -82,   89,   89,   89,    0,  -51,    0,    0,
  429,   81,    0,    0,  -72,    0,  -66,    0,   72,  159,
    0,    0,    0,  -61,    0,    0,    0,  143,    0,  145,
    0,    0,  147,  -50,  167,  165,    0,    0,  -13,    0,
  151,  -99, -114,   10,   10,   13,  -99,   13,    0,    0,
    0,    0,  152,    0,    0,   70,  171,    0,    0,    0,
   89,  150,    0,    0, -200,    0,  155, -242,   93,    0,
    0,  156,  -99,  158,  191,    0,  250,   -5,  255,   84,
  175,    0, -177,    0, -200,  170,    0,   90,    0,  297,
    0,    0,    0,    0,  301,    0,  302,    0,  452,  150,
    0,    0, -242,  330,    0,    0,    0,    0,    0,  108,
  328,    0,   89,    0, -242,  333,    0,  -99,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,   36,    0,    0,    0,   97,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   97,    0,    0,    0,    0,
  -41,    0,    0,   97,    0,    0,    0,  -18,    0,    0,
    0,  317,  367,  387,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,    0,  319,  116,    0,
    0,  139,  342,    0,    0,    0,    0,    0,    0,    0,
  212,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   97,    0,    0,   76,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  244,    0,  273,
    0,    0,    0,    0,    0,  336,    0,    0,    0,    0,
    0,    0,    0,    5,   28,   51,    0,   59,    0,    0,
    0,    0,  409,    0,    0,   97,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  337,    0,    0,    0,    0,
    0,    0,    0,    0,  296,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  338,    0,   97,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   45,   43,    0,    0,  332,    0,    0,    0,  512,
   40,    0,  308,    0,  -21,  -75,  169,  268,    0,  372,
  376,  377,  383,  386,  -43,    0,    0,  293,  323,    0,
  -33,    0,    0,  206,    0,  361,    0,   44,   -3,  -73,
    0,    0,  240,
};
final static int YYTABLESIZE=748;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
  101,  101,   83,  101,   67,  101,  143,  152,    2,    5,
   93,  110,   81,  136,  132,   35,  138,  101,  101,  101,
  101,  114,   97,    2,   97,    2,   97,  170,  128,   36,
  169,  115,  130,   12,   13,  195,   97,   40,   98,   75,
   97,   97,   97,   97,   16,   95,  127,   95,   33,   95,
  129,  105,  123,  194,   70,   97,  106,   98,   72,   71,
  108,  107,   73,   95,   95,   95,   95,  155,   96,   92,
   96,   79,   96,   43,   82,   12,   13,   74,  157,  133,
  146,   78,  148,  101,  200,  201,   96,   96,   96,   96,
   86,   85,  186,   65,   16,  138,   37,   52,   84,   88,
    7,  149,  150,   39,    8,   88,   97,   53,   86,   85,
  177,   37,   10,   11,   43,   54,   44,   88,   14,   35,
  156,   43,  178,   85,  197,   43,   97,   91,   98,   95,
   97,   43,   98,   43,   35,   89,  141,   43,   90,  180,
  144,  145,  103,   34,  109,   80,  173,  100,  101,   99,
    5,  112,   96,  100,  101,   99,   87,   87,   87,    1,
  117,  113,  116,   35,  118,  120,  121,   11,   52,  124,
  125,    7,  126,    6,   32,    8,    7,   37,  147,  216,
    8,    9,  131,   10,   11,  158,  172,  174,   10,   11,
   66,  175,   12,   13,   14,   15,  160,   16,  161,  162,
   35,  163,   66,  164,  165,  151,  166,  167,  168,  171,
  176,  179,  185,  203,  189,  101,  191,  190,  101,  101,
  101,  101,  101,  101,  101,  101,  142,  101,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,   97,   34,
   32,   97,   97,   97,   97,   97,   97,   97,   97,  192,
   97,   97,   97,   97,   97,   97,   97,   97,   97,   97,
   97,   95,  219,   16,   95,   95,   95,   95,   95,   95,
   95,   95,  181,   95,   95,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   96,   41,   42,   96,   96,   96,
   96,   96,   96,   96,   96,  132,   96,   96,   96,   96,
   96,   96,   96,   96,   96,   96,   96,   55,  193,   37,
   86,   85,   37,  196,   12,   13,   37,   37,   37,   88,
   37,   86,   38,   87,   37,   37,   41,   42,   37,   37,
   37,   37,   35,   41,   42,   35,   72,   41,   42,   35,
   35,   35,   56,   41,   42,   41,   42,   35,   35,  137,
   42,   35,   35,   35,   35,  205,   94,   95,   96,  206,
  207,  204,   94,   95,   96,   87,   87,   87,  117,  213,
  214,  215,   32,  218,   65,   32,   22,   23,   25,   32,
   32,   32,  119,  217,  159,   61,   56,   32,   32,   62,
   63,   32,   32,   32,   32,   16,   64,  116,   16,   65,
  153,  122,   16,   16,   16,  210,   52,  104,  187,    7,
   16,   16,    0,    8,   16,   16,   16,   16,    0,    0,
   61,   10,   11,    0,    0,    0,    0,   14,    0,    0,
    0,   52,    0,    0,    7,    0,    0,    0,    8,    0,
    0,   52,   56,   30,    7,    0,   10,   11,    8,    0,
    0,   53,   14,    0,    0,    0,   10,   11,    0,   54,
    0,    0,   14,    0,    0,    0,   13,    0,   72,    0,
    0,   72,    0,   72,   72,   72,   72,   72,   72,    0,
    0,    0,    0,   72,   72,   72,   72,   72,   72,   72,
   72,   53,    0,  182,    0,    0,    0,    0,    0,    0,
  117,    0,    0,  117,    0,  117,  117,  117,  117,  117,
  117,   57,  198,    0,    0,  117,  117,  117,  117,  117,
  117,  117,  117,    0,    0,    0,    0,    0,    0,  116,
  209,  182,  116,   54,  116,  116,  116,  116,  116,  116,
    0,    0,    0,    0,  116,  116,  116,  116,  116,  116,
  116,  116,   61,  154,    0,   61,    0,   61,   61,   61,
   61,   61,   61,    0,    0,    0,    0,   61,   61,   61,
   61,   61,   61,   61,   61,   30,  208,    0,   30,    0,
    0,    0,   30,   30,   30,    0,    0,    0,    0,    0,
   30,   30,    0,    0,   30,   30,   30,   30,   13,    0,
  134,   13,    0,    0,    0,   13,   13,   13,    0,    0,
    0,    0,    0,   13,   13,    0,    0,   13,   13,   13,
   13,    0,    0,   53,    0,    0,   53,    0,    0,    0,
   53,    0,    0,   53,    0,    0,    0,    0,   53,   53,
   53,   53,    0,   57,   53,    0,   57,    0,    0,    0,
   57,    0,    0,   57,    0,    0,    0,    0,   57,   57,
   57,   57,    0,    0,   57,   54,    0,    0,   54,    0,
    0,    0,   54,    0,    0,   54,  184,    0,    0,  134,
   54,   54,   54,   54,    0,   52,   54,    0,    7,    0,
    0,    0,    8,    0,    0,   53,  202,    0,    0,    0,
   10,   11,    0,   54,    0,    0,   14,    0,   52,    0,
    0,    7,    0,    0,  134,    8,    0,    0,    0,    0,
    0,    0,    0,   10,   11,    6,  134,    0,    7,   14,
    0,    0,    8,    9,    0,    0,    0,    0,    0,    0,
   10,   11,    0,    0,   12,   13,   14,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   36,   45,   59,   47,   41,   59,  123,  125,
   44,   55,   34,   89,  257,   44,   90,   59,   60,   61,
   62,  258,   41,  123,   43,  123,   45,   41,   41,   40,
   44,  268,   41,  276,  277,   41,   43,  257,   45,  123,
   59,   60,   61,   62,    2,   41,   59,   43,    4,   45,
   59,   42,   59,   59,  125,   43,   47,   45,  257,   17,
   58,   59,   23,   59,   60,   61,   62,  111,   41,   41,
   43,   58,   45,   45,   35,  276,  277,   59,  112,   41,
  102,   59,  104,  125,  262,  263,   59,   60,   61,   62,
   41,   41,  168,   58,   59,  169,   44,  257,   41,   41,
  260,  105,  106,   40,  264,   41,  125,  267,   59,   59,
   41,   59,  272,  273,   45,  275,   40,   59,  278,   44,
   40,   45,  156,   41,   41,   45,   43,  258,   45,  125,
   43,   45,   45,   45,   59,   40,   92,   45,   40,  161,
   97,   98,   41,  268,   59,   59,  261,   60,   61,   62,
  266,  274,  125,   60,   61,   62,   60,   61,   62,  257,
   44,   59,   59,   44,   59,  266,  125,  273,  257,   41,
   59,  260,   59,  257,   59,  264,  260,  125,  261,  213,
  264,  265,   59,  272,  273,  258,  142,  143,  272,  273,
  257,  147,  276,  277,  278,  279,  125,   59,   40,  261,
  125,   59,  257,   59,   58,  257,  257,   41,   44,   59,
   59,   41,   58,   44,   59,  257,   59,  173,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  268,
  125,  260,  261,  262,  263,  264,  265,  266,  267,   59,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  218,  125,  260,  261,  262,  263,  264,  265,
  266,  267,  123,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  257,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  123,   59,  257,
  261,  261,  260,   59,  276,  277,  264,  265,  266,  261,
  257,  257,  259,  259,  272,  273,  257,  258,  276,  277,
  278,  279,  257,  257,  258,  260,  125,  257,  258,  264,
  265,  266,   11,  257,  258,  257,  258,  272,  273,  257,
  258,  276,  277,  278,  279,   59,  269,  270,  271,   59,
   59,  272,  269,  270,  271,  269,  270,  271,  125,   40,
  263,   44,  257,   41,   58,  260,   41,   41,   41,  264,
  265,  266,   75,  215,  117,   14,   55,  272,  273,   14,
   14,  276,  277,  278,  279,  257,   14,  125,  260,   14,
  108,   79,  264,  265,  266,  200,  257,   47,  169,  260,
  272,  273,   -1,  264,  276,  277,  278,  279,   -1,   -1,
  125,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,   -1,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
   -1,  257,  111,  125,  260,   -1,  272,  273,  264,   -1,
   -1,  267,  278,   -1,   -1,   -1,  272,  273,   -1,  275,
   -1,   -1,  278,   -1,   -1,   -1,  125,   -1,  257,   -1,
   -1,  260,   -1,  262,  263,  264,  265,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,  278,
  279,  125,   -1,  162,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,
  267,  125,  181,   -1,   -1,  272,  273,  274,  275,  276,
  277,  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  199,  200,  260,  125,  262,  263,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  125,   -1,  260,   -1,  262,  263,  264,
  265,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  125,   -1,  260,   -1,
   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   89,  260,   -1,   -1,   -1,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,   -1,  257,  278,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,   -1,   -1,  278,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,  165,   -1,   -1,  168,
  272,  273,  274,  275,   -1,  257,  278,   -1,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,  185,   -1,   -1,   -1,
  272,  273,   -1,  275,   -1,   -1,  278,   -1,  257,   -1,
   -1,  260,   -1,   -1,  203,  264,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  257,  215,   -1,  260,  278,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,
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
"programa : nombre_programa bloque_sentencias",
"programa : bloque_sentencias",
"nombre_programa : ID",
"bloque_sentencias : '{' sentencias '}'",
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
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : DEFER seleccion ';'",
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
"sentencia_when : WHEN '(' condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion ')' bloque_sentencias ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' bloque_sentencias ';' $$1 WHEN '(' condicion ')' bloque_sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
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

//#line 261 ".\gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		ts.swapLexemas(constante, "-"+constante);
	} else {
		logger.logError("[Lexico] No se admiten ui16 con valores negativos: " + "-"+constante);
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
//#line 583 "Parser.java"
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
{ logger.logError("[Parser] Se esperaba nombre del programa"); }
break;
case 12:
//#line 45 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 13:
//#line 46 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 14:
//#line 47 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 17:
//#line 56 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 18:
//#line 57 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 24:
//#line 72 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 28:
//#line 82 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 29:
//#line 86 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 30:
//#line 87 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 31:
//#line 88 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 35:
//#line 98 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 36:
//#line 99 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 37:
//#line 100 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 51:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 52:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 53:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 54:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 55:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 56:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 57:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 60:
//#line 141 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 61:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 62:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 63:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 64:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 70:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 71:
//#line 164 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 72:
//#line 165 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 73:
//#line 169 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 74:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 75:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 76:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 77:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 78:
//#line 174 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 79:
//#line 178 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 80:
//#line 179 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 87:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 88:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 109:
//#line 240 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 110:
//#line 241 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 111:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 112:
//#line 243 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 113:
//#line 244 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 114:
//#line 245 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 119:
//#line 253 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 932 "Parser.java"
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
