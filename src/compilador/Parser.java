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
    5,    8,    8,    9,    9,   11,   11,   12,   13,   13,
   13,   16,   16,   15,   10,   10,   10,   17,   17,   18,
   18,   18,   18,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,   24,   24,   24,   25,   25,   23,   23,
   26,   27,   27,   29,   29,   19,   19,   19,   22,   22,
   22,   22,   30,   22,   20,   20,   31,   31,   32,   32,
   28,   33,   33,   33,   33,   33,   33,   14,   14,   14,
   34,   34,   34,   35,   35,   35,   37,   38,   38,   39,
   39,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   36,   36,    7,    7,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    3,    1,
    1,    3,    1,    4,    3,    6,    7,    6,    1,    3,
    5,    1,    3,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    2,    3,    1,    2,    1,
    2,    2,    3,    1,    2,    2,    1,    3,    6,    8,
    1,    1,    3,    1,    2,    4,    3,    3,    7,    6,
    6,    6,    0,   11,    7,    9,    1,    3,    1,    2,
    3,    1,    1,    1,    1,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  104,  103,    0,    0,    0,    0,    7,    8,    0,
   10,   11,    0,   34,    0,   38,   40,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,  101,    0,    0,
    0,    0,    0,   83,   85,   86,    0,    0,    0,   44,
   52,    0,    0,   35,    0,   39,   41,    0,    0,   27,
    0,    0,    4,    6,    0,    0,    0,    0,    0,   36,
   42,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  102,    0,    0,   72,   73,   74,    0,    0,
   75,   76,   77,    0,    0,    0,    0,    0,   46,   54,
    0,   45,    0,   37,   43,   32,    0,   25,    0,    0,
    9,    0,    0,   15,    0,   56,    0,   97,   95,   98,
    0,   96,    0,   94,    0,    0,    0,    0,   90,   91,
    0,   88,    0,    0,    0,    0,    0,    0,    0,   81,
   82,   51,   48,   53,   55,    0,   30,   29,   12,   14,
    0,    0,    0,   93,   92,    0,   24,    0,    0,    0,
   87,   63,    0,    0,    0,    0,    0,    0,    0,    0,
   67,    0,   16,    0,    0,   89,    0,   61,    0,   62,
   60,   49,    0,    0,   69,    0,    0,   65,   17,    0,
    0,   59,   18,    0,   68,   70,    0,    0,   21,    0,
   50,   66,    0,    0,   23,    0,   64,
};
final static short yydgoto[] = {                          3,
    4,    5,   68,   17,   18,   19,   20,   66,   21,   22,
   23,   69,  127,   41,  198,  199,   61,   62,   24,   25,
   26,   27,   28,   51,   52,   29,   53,   42,  101,  177,
  172,  186,   94,   43,   44,   45,   46,  131,  132,
};
final static short yysindex[] = {                      -114,
    0,  131,    0,  -94,    0, -212,   20,   53, -185,   56,
  -78,    0,    0,  -55,  -56,  -47,  131,    0,    0, -178,
    0,    0, -110,    0,   23,    0,    0,   24,   27,    0,
   75,   29,   58,   59,   61,   54,   63,    0, -154,    6,
   66,   64,  -14,    0,    0,    0,   48,   49,  307,    0,
    0,   55, -162,    0,   57,    0,    0,   60, -232,    0,
   65,   71,    0,    0,   73,   70,  131, -143,   10,    0,
    0, -148,    0,   -5,   90,   78,   79,  -10,   -7,   80,
  -36,   68,    0,  -94,  -11,    0,    0,    0,   29,   29,
    0,    0,    0,   29, -121,   29,   29, -113,    0,    0,
  290,    0,  105,    0,    0,    0, -100,    0, -109, -178,
    0,   32,  132,    0,  -78,    0,  -88,    0,    0,    0,
  119,    0,  122,    0,  125,  -72,  146,  144,    0,    0,
   17,    0,  137,  -94, -101,  -14,  -14,   -8,  -94,    0,
    0,    0,    0,    0,    0,   29,    0,    0,    0,    0,
   29,  -84,  173,    0,    0, -214,    0,  145, -214,   68,
    0,    0,  147,  -94,  151,  152,  163,   14,  172,  -80,
    0, -186,    0, -214,  170,    0,  -57,    0,  260,    0,
    0,    0,  263,   29,    0, -108,  173,    0,    0, -214,
  301,    0,    0,  302,    0,    0,   82,  298,    0,   29,
    0,    0, -214,  308,    0,  -94,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  296,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,  -18,    0,    0,    0,  289,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,    0,
  267,  102,    0,    0,  297,    0,    0,    0,    0,    0,
    0,    0,    0,  196,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   74,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  219,    0,  244,    0,    0,    0,    0,  314,    0,    0,
    0,    0,    0,    0,    0,    5,   28,   12,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  316,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  317,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    7,   22,    0,    0,   21,  228,  250,    0,    0,
    0,  294,    0,  -19,  -73,  160,  255,    0,  351,  356,
  357,  358,  359,  -33,    0,  278,  262,  -25,    0,    0,
  195,    0,    0,   -9,    1,  -68,    0,    0,  223,
};
final static int YYTABLESIZE=585;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         84,
   84,   84,   60,   84,  125,   84,   75,  128,    2,    5,
   30,   74,   67,  130,   85,  100,  195,   84,   84,   84,
   84,    2,   80,   16,   80,  106,   80,   96,    2,  135,
  121,   50,   97,  123,   89,  107,   90,   89,   64,   90,
   80,   80,   80,   80,   49,   78,   84,   78,  120,   78,
   39,  122,   71,  116,  183,   31,   89,  161,   90,   32,
  160,   12,   13,   78,   78,   78,   78,  145,   79,   50,
   79,   36,   79,   39,  138,  187,  188,   63,   65,  136,
  137,   70,   71,   84,   72,  175,   79,   79,   79,   79,
  133,  130,   35,   81,   33,   40,  140,  141,   76,   77,
   39,   80,   82,   83,   95,   98,   80,   99,   89,   33,
   90,  103,   39,  102,  109,  104,  110,   31,  105,   39,
  167,   50,  113,  108,  115,   92,   93,   91,  111,   78,
  117,  168,   31,   73,  114,   50,  118,  119,  124,  139,
  163,  165,    1,  142,  146,  166,    6,   59,    6,    7,
    5,    7,   79,    8,    9,    8,  150,  147,  194,  164,
   28,   10,   11,   10,   11,   12,   13,   14,   15,   14,
  179,  151,  153,  171,  204,   33,    6,  154,    6,    7,
  155,    7,  156,    8,  157,    8,  158,  159,   47,  169,
  185,   10,   11,   10,   11,  162,   48,   14,   31,   14,
   59,    6,  174,  182,    7,  178,  196,  171,    8,  180,
  181,  184,  207,  190,  191,   84,   10,   11,   84,   84,
   84,   84,   84,   84,   84,   84,   28,   84,   84,   84,
   84,   84,   84,   84,   84,   84,   84,   84,   80,   12,
   13,   80,   80,   80,   80,   80,   80,   80,   80,  134,
   80,   80,   80,   80,   80,   80,   80,   80,   80,   80,
   80,   78,   37,   38,   78,   78,   78,   78,   78,   78,
   78,   78,   71,   78,   78,   78,   78,   78,   78,   78,
   78,   78,   78,   78,   79,   37,   38,   79,   79,   79,
   79,   79,   79,   79,   79,  170,   79,   79,   79,   79,
   79,   79,   79,   79,   79,   79,   79,   33,  126,   33,
   33,   34,   37,   38,   33,   33,   33,   78,  192,   79,
   58,  193,   33,   33,  129,   38,   33,   33,   33,   33,
   31,   37,   38,   31,   86,   87,   88,   31,   31,   31,
  200,  203,  201,  100,  202,   31,   31,   47,  206,   31,
   31,   31,   31,   51,   19,   13,   20,   22,   28,  149,
  112,   28,  205,  148,   54,   28,   28,   28,   99,   55,
   56,   57,   58,   28,   28,  143,  152,   28,   28,   28,
   28,  197,  176,  173,    0,    0,  126,    6,    0,    0,
    7,   26,    0,    0,    8,    9,    0,    0,    0,    0,
    0,  189,   10,   11,    0,    0,   12,   13,   14,   15,
    0,    0,    0,    0,  144,    0,    0,  126,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    6,
  126,    0,    7,    0,    0,    0,    8,    0,    0,    0,
    0,    0,    0,    0,   10,   11,    0,    0,    0,    0,
   14,    0,   58,    0,    0,   58,    0,   58,   58,   58,
   58,   58,   58,    0,    0,    0,    0,   58,   58,   58,
   58,   58,   58,   58,   58,  100,    0,    0,  100,    0,
  100,  100,  100,  100,  100,  100,    0,    0,    0,    0,
  100,  100,  100,  100,  100,  100,  100,  100,    0,    0,
   99,    0,    0,   99,    0,   99,   99,   99,   99,   99,
   99,    0,    0,    0,    0,   99,   99,   99,   99,   99,
   99,   99,   99,   26,    0,    0,   26,    0,    0,    0,
   26,   26,   26,    0,    0,    0,    0,    0,   26,   26,
    0,    0,   26,   26,   26,   26,    6,    0,    0,    7,
    0,    0,    0,    8,    0,    0,   47,    0,    0,    0,
    0,   10,   11,    6,   48,    0,    7,   14,    0,    0,
    8,    0,    0,   47,    0,    0,    0,    0,   10,   11,
    0,   48,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   41,   47,   32,   81,  123,  125,
    4,   31,  123,   82,   40,   49,  125,   59,   60,   61,
   62,  123,   41,    2,   43,  258,   45,   42,  123,   41,
   41,   11,   47,   41,   43,  268,   45,   43,   17,   45,
   59,   60,   61,   62,  123,   41,   41,   43,   59,   45,
   45,   59,   41,   59,   41,  268,   43,   41,   45,   40,
   44,  276,  277,   59,   60,   61,   62,  101,   41,   49,
   43,  257,   45,   45,   94,  262,  263,  125,  257,   89,
   90,   59,   59,  125,   58,  159,   59,   60,   61,   62,
   84,  160,   40,   40,   44,   40,   96,   97,   41,   41,
   45,   41,   40,  258,   41,   58,  125,   59,   43,   59,
   45,  274,   45,   59,   44,   59,   44,   44,   59,   45,
  146,  101,  266,   59,  273,   60,   61,   62,   59,  125,
   41,  151,   59,   59,  125,  115,   59,   59,   59,  261,
  134,  135,  257,  257,   40,  139,  257,  257,  257,  260,
  266,  260,  125,  264,  265,  264,  125,  258,  184,  261,
   59,  272,  273,  272,  273,  276,  277,  278,  279,  278,
  164,   40,  261,  153,  200,  125,  257,   59,  257,  260,
   59,  260,   58,  264,  257,  264,   41,   44,  267,  274,
  170,  272,  273,  272,  273,   59,  275,  278,  125,  278,
  257,  257,   58,   41,  260,   59,  186,  187,  264,   59,
   59,   40,  206,   44,  272,  257,  272,  273,  260,  261,
  262,  263,  264,  265,  266,  267,  125,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  276,
  277,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  261,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  123,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,   81,  257,
  260,  259,  257,  258,  264,  265,  266,  257,   59,  259,
  125,   59,  272,  273,  257,  258,  276,  277,  278,  279,
  257,  257,  258,  260,  269,  270,  271,  264,  265,  266,
   40,   44,   41,  125,  263,  272,  273,   59,   41,  276,
  277,  278,  279,   58,   41,   59,   41,   41,  257,  110,
   67,  260,  203,  109,   14,  264,  265,  266,  125,   14,
   14,   14,   14,  272,  273,   98,  115,  276,  277,  278,
  279,  187,  160,  156,   -1,   -1,  159,  257,   -1,   -1,
  260,  125,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,
   -1,  174,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  125,   -1,   -1,  190,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  203,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,
  278,   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,
  265,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
  262,  263,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  276,  277,  278,  279,   -1,   -1,
  257,   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  257,  275,   -1,  260,  278,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
   -1,  275,   -1,   -1,  278,
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
"sentencia_declarativa : tipo lista_de_variables ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_constantes",
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
"sentencia_ejecutable_do : sentencia_break ';'",
"sentencia_ejecutable_do : CONTINUE ';'",
"sentencia_break : BREAK",
"sentencia_break : BREAK ':' etiqueta",
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

//#line 236 ".\gramatica.y"

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
//#line 525 "Parser.java"
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
case 9:
//#line 39 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 10:
//#line 40 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 11:
//#line 41 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 15:
//#line 51 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 21:
//#line 66 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 26:
//#line 80 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 27:
//#line 81 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 31:
//#line 91 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 32:
//#line 92 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 33:
//#line 93 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 49:
//#line 121 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 50:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 56:
//#line 140 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 57:
//#line 141 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 58:
//#line 142 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 59:
//#line 146 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 60:
//#line 147 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 61:
//#line 148 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 62:
//#line 149 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 63:
//#line 150 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 64:
//#line 151 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 65:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 66:
//#line 156 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 92:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 93:
//#line 216 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 94:
//#line 217 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 95:
//#line 218 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 96:
//#line 219 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 97:
//#line 220 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 98:
//#line 221 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 99:
//#line 222 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 100:
//#line 223 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 102:
//#line 228 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 814 "Parser.java"
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
