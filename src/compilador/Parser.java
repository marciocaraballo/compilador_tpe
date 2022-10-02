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
   26,   26,   26,   26,   26,   27,   27,   24,   24,   28,
   29,   29,   31,   31,   20,   20,   20,   23,   23,   23,
   23,   32,   23,   21,   21,   33,   33,   34,   34,   30,
   30,   36,   30,   35,   35,   35,   35,   35,   35,   15,
   15,   15,   37,   37,   37,   38,   38,   38,   40,   41,
   41,   42,   42,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   39,   39,   10,   10,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    1,    1,
    1,    3,    2,    2,    3,    1,    4,    3,    6,    7,
    6,    1,    3,    5,    1,    3,    2,    1,    3,    2,
    2,    1,    3,    3,    2,    2,    1,    1,    2,    2,
    3,    1,    2,    1,    2,    2,    3,    1,    1,    1,
    2,    4,    1,    3,    3,    2,    1,    6,    8,    1,
    1,    3,    1,    2,    4,    3,    3,    7,    6,    6,
    6,    0,   11,    7,    9,    1,    3,    1,    2,    3,
    2,    0,    3,    1,    1,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    4,    1,
    3,    1,    1,    5,    5,    4,    4,    4,    4,    4,
    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  116,  115,    0,    0,    0,    0,    7,    8,    9,
   10,   11,    0,    0,    0,   38,    0,   42,   44,    0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
  113,    0,    0,    0,    0,    0,    0,   95,   97,   98,
    0,    0,    0,    0,   48,   61,   49,   50,    0,   39,
    0,   43,   45,    0,    0,   31,    0,    0,    4,    6,
    0,    0,   14,    0,    0,    0,   40,   46,    0,   66,
    0,   15,    0,    0,    0,    0,    0,    0,    0,    0,
  114,    0,    0,   84,   85,   86,    0,    0,   87,   88,
   89,    0,    0,    0,    0,    0,   51,    0,   56,   63,
    0,    0,   41,   47,   36,    0,   29,    0,   12,    0,
    0,   18,    0,   65,    0,  109,  107,  110,    0,  108,
    0,  106,   28,    0,    0,    0,    0,  102,  103,    0,
  100,    0,    0,    0,    0,    0,    0,    0,    0,   93,
   94,   60,   55,    0,   62,   64,    0,   34,   33,   17,
    0,    0,    0,  105,  104,    0,   27,    0,    0,    0,
   99,   72,    0,    0,    0,    0,   52,    0,    0,    0,
    0,   76,    0,   19,    0,    0,  101,    0,   70,    0,
   71,   69,   58,    0,    0,   78,    0,    0,   74,   20,
    0,    0,   68,   21,    0,   77,   79,    0,    0,   24,
    0,   59,   75,    0,    0,   26,    0,   73,
};
final static short yydgoto[] = {                          3,
    4,    5,   75,   17,   18,   19,   20,   21,   22,   23,
   24,   25,   76,  136,   44,  209,  210,   67,   68,   26,
   27,   28,   29,   30,   56,   57,   58,   31,   59,   45,
  111,  188,  183,  197,  102,   46,   47,   48,   49,   50,
  140,  141,
};
final static short yysindex[] = {                      -112,
    0,  165,    0,  -88,    0,  -28,   23,   55, -199,   63,
  200,    0,    0,  163,  -51,  -57,  165,    0,    0,    0,
    0,    0, -161,   41,  -84,    0,   45,    0,    0,   46,
   52,    0,   70, -161,  -40,   75,   78,   65,   81,   83,
    0, -141,   29,   88,   87,   94,   13,    0,    0,    0,
 -144,   18,   80,  446,    0,    0,    0,    0, -140,    0,
   82,    0,    0,   84, -206,    0,   92,   93,    0,    0,
   96,  101,    0,  165, -124,   21,    0,    0, -126,    0,
   -5,    0,  111,  102,  104,  -10,   -8,  105,   39,   77,
    0,  -88,  -34,    0,    0,    0,  -40,  -40,    0,    0,
    0,  -40,  -95,  -40,  -40,  -40,    0,  -50,    0,    0,
  427,  127,    0,    0,    0,  -90,    0,  -87,    0,   44,
  131,    0,  200,    0,  -89,    0,    0,    0,  118,    0,
  120,    0,    0,  124,  -73,  144,  142,    0,    0,   34,
    0,  128,  -88,  -99,   13,   13,  -17,  -88,  -17,    0,
    0,    0,    0,  132,    0,    0,  -40,    0,    0,    0,
  -40,  -78,  220,    0,    0, -194,    0,  145, -240,   77,
    0,    0,  138,  -88,  143,  146,    0,  167,   16,  164,
 -146,    0, -177,    0, -194,  166,    0,  -63,    0,  152,
    0,    0,    0,  153,  -40,    0,  444,  220,    0,    0,
 -240,  173,    0,    0,  174,    0,    0,  -49,  219,    0,
  -40,    0,    0, -240,  267,    0,  -88,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,   35,    0,    0,    0,   97,
    0,    0,    0,    0,    0,    0, -122,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   97,    0,    0,    0,    0,  -41,
    0,    0,   97,    0,    0,    0,  -18,    0,    0,    0,
  279,  356,  380,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   53,    0,  307,  116,    0,    0,
  139,  330,    0,    0,    0,    0,    0,    0,    0,    0,
  237,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -11,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   76,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  261,    0,
  284,    0,    0,    0,    0,    0,  297,    0,    0,    0,
    0,    0,    0,    0,    5,   28,   12,    0,   50,    0,
    0,    0,    0,  404,    0,    0,   97,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  298,    0,    0,    0,    0,
    0,    0,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  304,    0,
   97,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  305,   30,    0,    0,    2,    0,    0,    0,  255,
   11,    0,  272,    0,  -23,  -77,  133,  232,    0,  337,
  342,  346,  347,  355,  -39,    0,    0,  262,  248,  -21,
    0,    0,  176,    0,  326,    0,    1,   -4,  -61,    0,
    0,  205,
};
final static int YYTABLESIZE=724;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         96,
   96,   96,    5,   96,   42,   96,  144,   66,  153,   81,
    2,  137,   55,   83,  110,   34,  133,   96,   96,   96,
   96,   93,   92,    2,   92,   97,   92,   98,  139,   81,
  129,   16,  131,   72,    2,   12,   13,   97,   74,   98,
   92,   92,   92,   92,   82,   90,   70,   90,  128,   90,
  130,  115,   80,  124,  105,   55,  194,   39,   97,  106,
   98,  116,   35,   90,   90,   90,   90,   69,   91,   92,
   91,  156,   91,   42,  171,  108,  107,  170,  147,  134,
  149,   12,   13,   96,  198,  199,   91,   91,   91,   91,
   83,  186,   60,   16,   38,   71,   37,  145,  146,   73,
  150,  151,   43,   77,   78,   88,   92,   42,  139,   79,
   51,   37,   55,    7,   42,   84,   91,    8,   85,   35,
   89,   42,   90,   33,   55,   10,   11,  103,   80,   90,
   97,   14,   98,  112,   35,  178,  118,  179,  109,   34,
  113,  121,  114,    5,    1,  122,  123,  100,  101,   99,
  117,  125,   91,  100,  101,   99,   82,   82,   82,  119,
  126,  174,  127,  132,  182,  148,  157,  158,  160,   65,
  161,  163,    6,  205,   32,    7,  164,   37,  165,    8,
    9,  166,  196,  167,  168,  169,  172,   10,   11,  215,
  177,   12,   13,   14,   15,  180,  189,   16,  207,  182,
   35,  191,  185,  195,  192,   65,  152,  193,  202,  201,
  203,  204,  211,  213,  212,   96,   40,   41,   96,   96,
   96,   96,   96,   96,   96,   96,  143,   96,   96,   96,
   96,   96,   96,   96,   96,   96,   96,   96,   92,   33,
   32,   92,   92,   92,   92,   92,   92,   92,   92,   81,
   92,   92,   92,   92,   92,   92,   92,   92,   92,   92,
   92,   90,  214,   16,   90,   90,   90,   90,   90,   90,
   90,   90,   80,   90,   90,   90,   90,   90,   90,   90,
   90,   90,   90,   90,   91,   40,   41,   91,   91,   91,
   91,   91,   91,   91,   91,  133,   91,   91,   91,   91,
   91,   91,   91,   91,   91,   91,   91,  217,   32,   37,
   83,   36,   37,   37,   12,   13,   37,   37,   37,   40,
   41,   86,   54,   87,   37,   37,   40,   41,   37,   37,
   37,   37,   35,  138,   41,   35,   60,   22,   23,   35,
   35,   35,  181,  135,   25,  120,  216,   35,   35,  159,
   60,   35,   35,   35,   35,   61,   94,   95,   96,   62,
   63,   67,   94,   95,   96,   82,   82,   82,   64,  154,
  162,  104,   32,  208,  187,   32,    0,    0,    0,   32,
   32,   32,    0,    0,    0,  112,    0,   32,   32,    0,
    0,   32,   32,   32,   32,   16,  142,    0,   16,    0,
    0,    0,   16,   16,   16,    0,    0,    0,  111,    0,
   16,   16,    0,    0,   16,   16,   16,   16,    0,   51,
  184,    6,    7,  135,    7,    0,    8,    0,    8,    9,
    0,   30,    0,    0,   10,   11,   10,   11,    0,  200,
   12,   13,   14,   15,    0,    0,    0,  173,  175,    0,
    0,    0,  176,    0,   13,  135,   51,    0,    0,    7,
    0,    0,    0,    8,    0,    0,   52,    0,  135,    0,
    0,   10,   11,    0,   53,    0,   51,   14,  190,    7,
   53,    0,    0,    8,    0,    0,    0,    0,    0,    0,
    0,   10,   11,   67,    0,    0,   67,   14,   67,   67,
   67,   67,   67,   67,   57,    0,    0,    0,   67,   67,
   67,   67,   67,   67,   67,   67,    0,  112,    0,    0,
  112,  218,  112,  112,  112,  112,  112,  112,   54,    0,
    0,    0,  112,  112,  112,  112,  112,  112,  112,  112,
  111,    0,    0,  111,    0,  111,  111,  111,  111,  111,
  111,  155,    0,    0,    0,  111,  111,  111,  111,  111,
  111,  111,  111,   30,    0,    0,   30,    0,  206,    0,
   30,   30,   30,    0,    0,    0,    0,    0,   30,   30,
    0,    0,   30,   30,   30,   30,   13,    0,    0,   13,
    0,    0,    0,   13,   13,   13,    0,    0,    0,    0,
    0,   13,   13,    0,    0,   13,   13,   13,   13,    0,
    0,    0,   53,    0,    0,   53,    0,    0,    0,   53,
    0,    0,   53,    0,    0,    0,    0,   53,   53,   53,
   53,    0,    0,   53,    0,    0,   57,    0,    0,   57,
    0,    0,    0,   57,    0,    0,   57,    0,    0,    0,
    0,   57,   57,   57,   57,    0,    0,   57,    0,    0,
   54,    0,    0,   54,    0,    0,    0,   54,    0,    0,
   54,    0,    0,    0,    0,   54,   54,   54,   54,    0,
    0,   54,    0,   51,    0,    0,    7,    0,    0,    0,
    8,    0,    0,   52,    0,    0,    0,    0,   10,   11,
   51,   53,   51,    7,   14,    7,    0,    8,    0,    8,
    0,    0,   52,    0,    0,   10,   11,   10,   11,    0,
   53,   14,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,  125,   45,   45,   47,   41,   59,   59,   33,
  123,   89,   11,   35,   54,   44,  257,   59,   60,   61,
   62,   43,   41,  123,   43,   43,   45,   45,   90,   41,
   41,    2,   41,   23,  123,  276,  277,   43,  123,   45,
   59,   60,   61,   62,   34,   41,   17,   43,   59,   45,
   59,  258,   41,   59,   42,   54,   41,  257,   43,   47,
   45,  268,   40,   59,   60,   61,   62,  125,   41,   41,
   43,  111,   45,   45,   41,   58,   59,   44,  102,   41,
  104,  276,  277,  125,  262,  263,   59,   60,   61,   62,
   41,  169,   58,   59,   40,  257,   44,   97,   98,   59,
  105,  106,   40,   59,   59,   41,  125,   45,  170,   58,
  257,   59,  111,  260,   45,   41,  258,  264,   41,   44,
   40,   45,   40,  268,  123,  272,  273,   41,   59,  125,
   43,  278,   45,  274,   59,  157,   44,  161,   59,   44,
   59,  266,   59,  266,  257,  125,  273,   60,   61,   62,
   59,   41,  125,   60,   61,   62,   60,   61,   62,   59,
   59,  261,   59,   59,  163,  261,   40,  258,  125,  257,
   40,  261,  257,  195,   59,  260,   59,  125,   59,  264,
  265,   58,  181,  257,   41,   44,   59,  272,  273,  211,
   59,  276,  277,  278,  279,  274,   59,   59,  197,  198,
  125,   59,   58,   40,   59,  257,  257,   41,  272,   44,
   59,   59,   40,  263,   41,  257,  257,  258,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  268,
  125,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,   44,  125,  260,  261,  262,  263,  264,  265,
  266,  267,  261,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  257,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   41,    4,  257,
  261,  257,  260,  259,  276,  277,  264,  265,  266,  257,
  258,  257,  123,  259,  272,  273,  257,  258,  276,  277,
  278,  279,  257,  257,  258,  260,   58,   41,   41,  264,
  265,  266,  123,   89,   41,   74,  214,  272,  273,  118,
   14,  276,  277,  278,  279,   14,  269,  270,  271,   14,
   14,  125,  269,  270,  271,  269,  270,  271,   14,  108,
  123,   46,  257,  198,  170,  260,   -1,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,  125,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   92,   -1,  260,   -1,
   -1,   -1,  264,  265,  266,   -1,   -1,   -1,  125,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,
  166,  257,  260,  169,  260,   -1,  264,   -1,  264,  265,
   -1,  125,   -1,   -1,  272,  273,  272,  273,   -1,  185,
  276,  277,  278,  279,   -1,   -1,   -1,  143,  144,   -1,
   -1,   -1,  148,   -1,  125,  201,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,  214,   -1,
   -1,  272,  273,   -1,  275,   -1,  257,  278,  174,  260,
  125,   -1,   -1,  264,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,  257,   -1,   -1,  260,  278,  262,  263,
  264,  265,  266,  267,  125,   -1,   -1,   -1,  272,  273,
  274,  275,  276,  277,  278,  279,   -1,  257,   -1,   -1,
  260,  217,  262,  263,  264,  265,  266,  267,  125,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,
  267,  125,   -1,   -1,   -1,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,  125,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,   -1,   -1,  278,   -1,   -1,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,   -1,   -1,  278,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,
   -1,  278,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  257,  275,  257,  260,  278,  260,   -1,  264,   -1,  264,
   -1,   -1,  267,   -1,   -1,  272,  273,  272,  273,   -1,
  275,  278,   -1,  278,
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
"sentencia_ejecutable : sentencia_do ';'",
"sentencia_ejecutable : DEFER sentencia_do ';'",
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
"sentencia_do : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"sentencia_do : etiqueta ':' DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
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

//#line 253 ".\gramatica.y"

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
//#line 572 "Parser.java"
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
case 10:
//#line 40 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 11:
//#line 41 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
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
{ logger.logError("[Parser] Se esperaba una etiqueta len la sentencia break"); }
break;
case 56:
//#line 131 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 57:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 58:
//#line 136 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 59:
//#line 137 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 65:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 66:
//#line 156 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 67:
//#line 157 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 68:
//#line 161 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 69:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 70:
//#line 163 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 71:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 72:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 73:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 74:
//#line 170 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 75:
//#line 171 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 82:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 83:
//#line 187 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 104:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 105:
//#line 233 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 106:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 107:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 108:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 109:
//#line 237 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 110:
//#line 238 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 111:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 112:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 114:
//#line 245 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 909 "Parser.java"
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
