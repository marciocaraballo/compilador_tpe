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
   34,   34,   34,   34,   35,   35,   31,   31,   37,   31,
   36,   36,   36,   36,   36,   36,   15,   15,   15,   38,
   38,   38,   39,   39,   39,   41,   42,   42,   43,   43,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   40,
   40,   10,   10,
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
    1,    3,    2,    2,    1,    2,    3,    2,    0,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  123,  122,    0,    0,    0,    0,    7,    8,    9,
   10,   11,    0,    0,    0,   38,    0,   42,   44,   46,
    0,   58,    1,    0,    0,    0,    0,    0,    0,    0,
    0,  120,    0,    0,    0,    0,    0,    0,  102,  104,
  105,    0,    0,    0,    0,   48,   66,   49,   50,    0,
   39,    0,   43,   45,   47,    0,   31,    0,    0,    4,
    6,    0,    0,   14,    0,    0,    0,   40,    0,   71,
    0,   15,    0,    0,    0,    0,    0,    0,    0,    0,
  121,    0,    0,   91,   92,   93,    0,    0,   94,   95,
   96,    0,    0,    0,    0,    0,   51,    0,   56,   68,
    0,    0,   41,   36,    0,   29,    0,   12,    0,    0,
   18,   59,   70,    0,  116,  114,  117,    0,  115,    0,
  113,   28,    0,    0,    0,    0,  109,  110,    0,  107,
    0,    0,    0,    0,    0,    0,    0,    0,  100,  101,
   65,   55,    0,   67,   69,    0,    0,   34,   33,   17,
    0,    0,  112,  111,    0,   27,    0,    0,    0,  106,
   77,    0,    0,    0,    0,   52,    0,    0,    0,    0,
    0,    0,    0,    0,   19,    0,    0,  108,    0,   75,
    0,   76,   74,   62,   64,    0,   63,    0,   85,    0,
    0,   79,   83,   86,   20,    0,    0,   73,   60,   21,
   82,    0,    0,   24,    0,   80,    0,    0,   26,    0,
   78,
};
final static short yydgoto[] = {                          3,
    4,    5,   76,   17,   18,   19,   20,   21,   22,   23,
   24,   25,   77,  135,   45,  213,  214,   68,   69,   26,
   27,   28,   29,   30,   57,   58,   59,   31,   32,   60,
   46,  111,  189,  183,  184,  102,   47,   48,   49,   50,
   51,  139,  140,
};
final static short yysindex[] = {                       -97,
    0,  162,    0,  -85,    0,  -28,   23,   64, -179,   77,
  185,    0,    0,  164,  -51,  -39,  162,    0,    0,    0,
    0,    0, -162,   46,  -88,    0,   49,    0,    0,    0,
   56,    0,    0,   87, -162,  -40,   75,   78,   65,   96,
   98,    0, -129,   29,   88,   99,   94,   14,    0,    0,
    0, -131,   18,   80,  480,    0,    0,    0,    0, -133,
    0,   84,    0,    0,    0, -234,    0,   85,  101,    0,
    0,  103,  102,    0,  162, -104,   41,    0, -110,    0,
   -6,    0,  124,  108,  109,  -12,  -10,  111,   39,   89,
    0,  -85,  -34,    0,    0,    0,  -40,  -40,    0,    0,
    0,  -40,  -90,  -40,  -40,  -40,    0,  -50,    0,    0,
  428,   81,    0,    0,  -84,    0,  -78,    0,   48,  140,
    0,    0,    0,  -80,    0,    0,    0,  128,    0,  133,
    0,    0,  136,  -62,  156,  155,    0,    0,   31,    0,
  145,  -85, -109,   14,   14,  -15,  -85,  -15,    0,    0,
    0,    0,  146,    0,    0,   70,  169,    0,    0,    0,
  -40,  -64,    0,    0, -195,    0,  153, -225,   89,    0,
    0,  154,  -85,  191,  214,    0,  250,   -5,  255,   17,
  426,    0, -169,  461,    0, -195,  168,    0,  -57,    0,
  278,    0,    0,    0,    0,  291,    0,  297,    0,  478,
  -64,    0,    0,    0,    0, -225,  320,    0,    0,    0,
    0,  107,  317,    0,  -40,    0, -225,  321,    0,  -85,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,   40,    0,    0,    0,   97,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   97,    0,    0,    0,    0,
  -41,    0,    0,   97,    0,    0,    0,  -18,    0,    0,
    0,  314,  364,  387,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,    0,  318,  116,    0,
    0,  139,  341,    0,    0,    0,    0,    0,    0,    0,
  218,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   97,    0,    0,   76,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  244,    0,  272,
    0,    0,    0,    0,    0,  333,    0,    0,    0,    0,
    0,    0,    0,    5,   28,   51,    0,   59,    0,    0,
    0,    0,  406,    0,    0,   97,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  451,    0,    0,    0,    0,  334,    0,    0,    0,
    0,    0,    0,    0,    0,  295,    0,    0,    0, -160,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  336,    0,   97,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  259,   38,    0,    0,    2,    0,    0,    0,  282,
   10,    0,  303,    0,  -19,  -72,  166,  262,    0,  370,
  371,  372,  373,  376,  -43,    0,    0,  283,  319,    0,
  -33,    0,    0,  199,  226,  361,    0,   27,   22,  -68,
    0,    0,  240,
};
final static int YYTABLESIZE=758;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        103,
  103,  103,   83,  103,   43,  103,  143,   67,  152,    5,
   93,  110,   56,    2,   81,   35,  136,  103,  103,  103,
  103,  138,   99,  114,   99,    2,   99,   97,  128,   98,
  130,  132,   73,  115,   75,  196,   97,    2,   98,   16,
   99,   99,   99,   99,   82,   97,  127,   97,  129,   97,
   12,   13,  123,  195,   71,  105,   56,  198,  181,   97,
  106,   98,   36,   97,   97,   97,   97,  155,   98,   92,
   98,  170,   98,   43,  169,  108,  107,   40,  157,  133,
   12,   13,  146,  103,  148,   70,   98,   98,   98,   98,
   88,   87,  201,  202,   72,  187,   37,   65,   16,   90,
  138,   84,   84,   39,   74,   88,   99,   78,   88,   87,
  177,   37,   56,   79,   43,   84,   44,   90,   85,   35,
  156,   43,  178,  144,  145,   43,  149,  150,   91,   97,
   97,   43,   98,   43,   35,   89,   34,   90,  109,  103,
  112,  180,  113,  116,  117,   80,   35,  100,  101,   99,
    5,  173,   98,  100,  101,   99,   89,   89,   89,    1,
  118,  120,   11,  182,  124,  121,  125,  126,    6,  131,
  147,    7,  160,  158,   32,    8,    9,   37,   66,  161,
  162,  218,  199,   10,   11,  204,  163,   12,   13,   14,
   15,  164,   52,  165,  166,    7,  167,   16,  168,    8,
   35,  204,  182,  171,  176,   66,  151,   10,   11,  179,
  186,  206,  190,   14,  207,  103,   41,   42,  103,  103,
  103,  103,  103,  103,  103,  103,  142,  103,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,   99,   34,
   32,   99,   99,   99,   99,   99,   99,   99,   99,  192,
   99,   99,   99,   99,   99,   99,   99,   99,   99,   99,
   99,   97,   33,   16,   97,   97,   97,   97,   97,   97,
   97,   97,  193,   97,   97,   97,   97,   97,   97,   97,
   97,   97,   97,   97,   98,   41,   42,   98,   98,   98,
   98,   98,   98,   98,   98,  132,   98,   98,   98,   98,
   98,   98,   98,   98,   98,   98,   98,   55,  194,   37,
   88,   87,   37,  197,   12,   13,   37,   37,   37,   90,
   37,   86,   38,   87,   37,   37,   41,   42,   37,   37,
   37,   37,   35,   41,   42,   35,  208,   41,   42,   35,
   35,   35,   72,   41,   42,  137,   42,   35,   35,  209,
  141,   35,   35,   35,   35,  210,   94,   95,   96,  215,
  217,  220,   94,   95,   96,   89,   89,   89,  119,  216,
  134,   65,   32,   22,   23,   32,   25,  119,  159,   32,
   32,   32,  219,   61,   62,   63,   64,   32,   32,   65,
  153,   32,   32,   32,   32,   16,  118,  122,   16,  212,
  172,  174,   16,   16,   16,  175,  200,  104,  188,    0,
   16,   16,    0,    0,   16,   16,   16,   16,    6,   61,
   52,    7,    0,    7,    0,    8,    9,    8,    0,    0,
    0,  191,    0,   10,   11,   10,   11,   12,   13,   14,
   15,   52,   30,    0,    7,    0,  185,    0,    8,  134,
    0,   53,    0,    0,    0,    0,   10,   11,    0,   54,
    0,    0,   14,    0,    0,   13,    0,  205,    0,    0,
    0,    0,    0,    0,   72,    0,    0,   72,  221,   72,
   72,   72,   72,   72,   72,    0,    0,  134,   53,   72,
   72,   72,   72,   72,   72,   72,   72,    0,  134,    0,
  119,    0,    0,  119,    0,  119,  119,  119,  119,  119,
  119,   57,    0,    0,    0,  119,  119,  119,  119,  119,
  119,  119,  119,    0,    0,    0,    0,    0,  118,    0,
   54,  118,    0,  118,  118,  118,  118,  118,  118,    0,
    0,    0,    0,  118,  118,  118,  118,  118,  118,  118,
  118,   61,  154,    0,   61,    0,   61,   61,   61,   61,
   61,   61,    0,    0,    0,    0,   61,   61,   61,   61,
   61,   61,   61,   61,   30,   85,    0,   30,    0,    0,
    0,   30,   30,   30,    0,  203,    0,    0,    0,   30,
   30,    0,    0,   30,   30,   30,   30,   13,    0,    0,
   13,    0,  211,    0,   13,   13,   13,    0,    0,    0,
    0,    0,   13,   13,    0,    0,   13,   13,   13,   13,
   53,    0,    0,   53,    0,    0,    0,   53,    0,    0,
   53,    0,    0,    0,    0,   53,   53,   53,   53,    0,
    0,   53,    0,   57,    0,    0,   57,    0,    0,    0,
   57,    0,    0,   57,    0,    0,    0,    0,   57,   57,
   57,   57,   54,    0,   57,   54,    0,    0,    0,   54,
    0,    0,   54,    0,    0,    0,    0,   54,   54,   54,
   54,    0,   52,   54,   52,    7,    0,    7,    0,    8,
    0,    8,    0,    0,   53,    0,    0,   10,   11,   10,
   11,    0,   54,   14,    0,   14,    0,   85,    0,    0,
   85,    0,   81,   81,   85,    0,    0,   52,    0,    0,
    7,    0,   85,   85,    8,    0,    0,    0,   85,    0,
    0,    0,   10,   11,   52,    0,   52,    7,   14,    7,
    0,    8,    0,    8,    0,    0,   53,    0,    0,   10,
   11,   10,   11,    0,   54,   14,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   36,   45,   45,   47,   41,   59,   59,  125,
   44,   55,   11,  123,   34,   44,   89,   59,   60,   61,
   62,   90,   41,  258,   43,  123,   45,   43,   41,   45,
   41,  257,   23,  268,  123,   41,   43,  123,   45,    2,
   59,   60,   61,   62,   35,   41,   59,   43,   59,   45,
  276,  277,   59,   59,   17,   42,   55,   41,  123,   43,
   47,   45,   40,   59,   60,   61,   62,  111,   41,   41,
   43,   41,   45,   45,   44,   58,   59,  257,  112,   41,
  276,  277,  102,  125,  104,  125,   59,   60,   61,   62,
   41,   41,  262,  263,  257,  168,   44,   58,   59,   41,
  169,  262,  263,   40,   59,   41,  125,   59,   59,   59,
   41,   59,  111,   58,   45,   41,   40,   59,   41,   44,
   40,   45,  156,   97,   98,   45,  105,  106,  258,  125,
   43,   45,   45,   45,   59,   40,  268,   40,   59,   41,
  274,  161,   59,   59,   44,   59,   44,   60,   61,   62,
  266,  261,  125,   60,   61,   62,   60,   61,   62,  257,
   59,  266,  273,  162,   41,  125,   59,   59,  257,   59,
  261,  260,  125,  258,   59,  264,  265,  125,  257,   40,
  261,  215,  181,  272,  273,  184,   59,  276,  277,  278,
  279,   59,  257,   58,  257,  260,   41,   59,   44,  264,
  125,  200,  201,   59,   59,  257,  257,  272,  273,   41,
   58,   44,   59,  278,  272,  257,  257,  258,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  268,
  125,  260,  261,  262,  263,  264,  265,  266,  267,   59,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,    4,  125,  260,  261,  262,  263,  264,  265,
  266,  267,   59,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  257,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  123,   59,  257,
  261,  261,  260,   59,  276,  277,  264,  265,  266,  261,
  257,  257,  259,  259,  272,  273,  257,  258,  276,  277,
  278,  279,  257,  257,  258,  260,   59,  257,  258,  264,
  265,  266,  125,  257,  258,  257,  258,  272,  273,   59,
   92,  276,  277,  278,  279,   59,  269,  270,  271,   40,
   44,   41,  269,  270,  271,  269,  270,  271,  125,  263,
   89,   58,  257,   41,   41,  260,   41,   75,  117,  264,
  265,  266,  217,   14,   14,   14,   14,  272,  273,   14,
  108,  276,  277,  278,  279,  257,  125,   79,  260,  201,
  142,  143,  264,  265,  266,  147,  181,   47,  169,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,  125,
  257,  260,   -1,  260,   -1,  264,  265,  264,   -1,   -1,
   -1,  173,   -1,  272,  273,  272,  273,  276,  277,  278,
  279,  257,  125,   -1,  260,   -1,  165,   -1,  264,  168,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,
   -1,   -1,  278,   -1,   -1,  125,   -1,  186,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  220,  262,
  263,  264,  265,  266,  267,   -1,   -1,  206,  125,  272,
  273,  274,  275,  276,  277,  278,  279,   -1,  217,   -1,
  257,   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,
  267,  125,   -1,   -1,   -1,  272,  273,  274,  275,  276,
  277,  278,  279,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  125,  260,   -1,  262,  263,  264,  265,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  125,   -1,  260,   -1,  262,  263,  264,  265,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  125,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,   -1,  125,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,  125,   -1,  264,  265,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,
   -1,  278,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,   -1,  257,  278,  257,  260,   -1,  260,   -1,  264,
   -1,  264,   -1,   -1,  267,   -1,   -1,  272,  273,  272,
  273,   -1,  275,  278,   -1,  278,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  257,   -1,   -1,
  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,   -1,
   -1,   -1,  272,  273,  257,   -1,  257,  260,  278,  260,
   -1,  264,   -1,  264,   -1,   -1,  267,   -1,   -1,  272,
  273,  272,  273,   -1,  275,  278,   -1,  278,
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

//#line 263 ".\gramatica.y"

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
//#line 590 "Parser.java"
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
case 83:
//#line 185 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 84:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 89:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 90:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 111:
//#line 242 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 112:
//#line 243 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 113:
//#line 244 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
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
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 118:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 119:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 121:
//#line 255 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 947 "Parser.java"
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
