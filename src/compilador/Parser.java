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
    6,    6,    6,   24,   24,   24,   25,   25,   26,   26,
   23,   23,   27,   28,   28,   30,   30,   19,   19,   19,
   22,   22,   22,   22,   31,   22,   20,   20,   32,   32,
   33,   33,   29,   29,   35,   29,   34,   34,   34,   34,
   34,   34,   14,   14,   14,   36,   36,   36,   37,   37,
   37,   39,   40,   40,   41,   41,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   38,   38,    7,    7,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    3,    1,
    1,    3,    1,    4,    3,    6,    7,    6,    1,    3,
    5,    1,    3,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    2,    3,    1,    2,    1,
    2,    2,    3,    1,    2,    1,    1,    3,    2,    1,
    6,    8,    1,    1,    3,    1,    2,    4,    3,    3,
    7,    6,    6,    6,    0,   11,    7,    9,    1,    3,
    1,    2,    3,    2,    0,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    1,    3,    1,    1,    5,    5,    4,    4,
    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  109,  108,    0,    0,    0,    0,    7,    8,    0,
   10,   11,    0,   34,    0,   38,   40,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,  106,    0,    0,
    0,    0,    0,    0,   88,   90,   91,    0,    0,    0,
   44,   54,    0,   46,    0,   35,    0,   39,   41,    0,
    0,   27,    0,    0,    4,    6,    0,    0,    0,    0,
    0,   36,   42,    0,   59,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  107,    0,    0,   77,   78,   79,
    0,    0,   80,   81,   82,    0,    0,    0,    0,    0,
    0,   49,   56,    0,   45,    0,   37,   43,   32,    0,
   25,    0,    0,    9,    0,    0,   15,    0,   58,    0,
  102,  100,  103,    0,  101,    0,   99,    0,    0,    0,
    0,   95,   96,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,   86,   87,   53,   48,   55,   57,    0,
   30,   29,   12,   14,    0,    0,    0,   98,   97,    0,
   24,    0,    0,    0,   92,   65,    0,    0,    0,    0,
    0,    0,    0,    0,   69,    0,   16,    0,    0,   94,
    0,   63,    0,   64,   62,   51,    0,    0,   71,    0,
    0,   67,   17,    0,    0,   61,   18,    0,   70,   72,
    0,    0,   21,    0,   52,   68,    0,    0,   23,    0,
   66,
};
final static short yydgoto[] = {                          3,
    4,    5,   70,   17,   18,   19,   20,   68,   21,   22,
   23,   71,  130,   41,  202,  203,   63,   64,   24,   25,
   26,   27,   28,   52,   53,   54,   29,   55,   42,  104,
  181,  176,  190,   96,   43,   44,   45,   46,   47,  134,
  135,
};
final static short yysindex[] = {                      -114,
    0,  125,    0,  -87,    0, -223,   19,   61, -200,   68,
  -95,    0,    0, -158,  -52,  -45,  125,    0,    0, -175,
    0,    0,  186,    0,   27,    0,    0,   33,   42,    0,
   75,   29,   62,   63,   53,   65,   72,    0, -142,    6,
   66,   78,   85,   14,    0,    0,    0,   64,   80,  149,
    0,    0,   81,    0, -153,    0,   83,    0,    0,   92,
 -219,    0,   93,   79,    0,    0,  113,   99,  125, -141,
   34,    0,    0, -149,    0,   -5,  119,  104,  105,  -26,
  -24,  109,  -36,   84,    0,  -87,  -11,    0,    0,    0,
   29,   29,    0,    0,    0,   29,  -90,   29,   29,   29,
  -84,    0,    0,  332,    0,  134,    0,    0,    0,  -83,
    0,  -76, -175,    0,   59,  145,    0,  -95,    0,  -73,
    0,    0,    0,  131,    0,  132,    0,  136,  -65,  154,
  152,    0,    0,   31,    0,  138,  -87, -113,   14,   14,
   25,  -87,   25,    0,    0,    0,    0,    0,    0,   29,
    0,    0,    0,    0,   29,  -74,  -71,    0,    0, -264,
    0,  148, -264,   84,    0,    0,  151,  -87,  153,  155,
  167,   17,  171,  127,    0, -231,    0, -264,  169,    0,
  -57,    0,  158,    0,    0,    0,  159,   29,    0,  196,
  -71,    0,    0, -264,  274,    0,    0,  278,    0,    0,
   82,  300,    0,   29,    0,    0, -264,  281,    0,  -87,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  291,    0,    0,    0,   94,
    0,    0,    0,    0,    0,    0, -117,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   94,    0,    0,    0,    0,  -41,    0,    0,   94,
    0,    0,    0,  -18,    0,    0,    0,  289,  313,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,  290,  102,    0,    0,  298,    0,    0,    0,
    0,    0,    0,    0,    0,  218,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   74,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  244,    0,  267,    0,    0,    0,    0,
  317,    0,    0,    0,    0,    0,    0,    0,    5,   28,
   35,    0,   52,    0,    0,    0,    0,    0,    0,   94,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  319,    0,
    0,    0,    0,    0,    0,    0,    0,   94,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  320,    0,   94,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,   -1,   20,    0,    0,   13,  -28,  257,    0,    0,
    0,  302,    0,  -17,  -72,  165,  261,    0,  362,  363,
  369,  372,  374,  -21,    0,    0,  292,  276,   -6,    0,
    0,  204,    0,  353,    0,  -14,   -2,  -68,    0,    0,
  243,
};
final static int YYTABLESIZE=610;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         89,
   89,   89,   30,   89,  128,   89,   62,    5,    2,    2,
  131,   12,   13,   76,  124,  133,  126,   89,   89,   89,
   89,   16,   85,   51,   85,   77,   85,   50,  103,  138,
  191,  192,  123,   87,  125,    2,   66,   91,  109,   92,
   85,   85,   85,   85,   31,   83,   86,   83,  110,   83,
   39,  174,   74,  119,  129,   99,   36,  187,   32,   91,
  100,   92,   51,   83,   83,   83,   83,   91,   84,   92,
   84,  165,   84,   39,  164,   73,  139,  140,  141,   65,
  143,   67,  149,   89,  136,   72,   84,   84,   84,   84,
  179,   73,   76,   82,   33,  133,  144,  145,    6,   74,
   35,    7,   78,   79,   83,    8,   85,   40,   91,   33,
   92,   84,   39,   10,   11,   85,   51,   31,   97,   39,
  106,  101,  112,  118,  116,   94,   95,   93,   39,   83,
   51,  177,   31,   75,  129,  167,  169,  172,  102,  105,
  170,  107,    1,  171,   94,   95,   93,  168,    5,  193,
  108,  111,   84,   75,   75,   75,  113,  114,  117,  120,
   28,    6,  121,  122,    7,  129,  183,  127,    8,  175,
  142,   48,  146,  150,  151,   33,   10,   11,  129,   49,
   61,  198,   14,  154,  155,    6,  189,  157,    7,  158,
  159,  161,    8,  160,  162,  163,  166,  208,   31,  173,
   10,   11,  200,  175,   61,  178,   14,  186,  211,  182,
  188,  184,  194,  185,  195,   89,  196,  197,   89,   89,
   89,   89,   89,   89,   89,   89,   28,   89,   89,   89,
   89,   89,   89,   89,   89,   89,   89,   89,   85,   12,
   13,   85,   85,   85,   85,   85,   85,   85,   85,  137,
   85,   85,   85,   85,   85,   85,   85,   85,   85,   85,
   85,   83,   37,   38,   83,   83,   83,   83,   83,   83,
   83,   83,   74,   83,   83,   83,   83,   83,   83,   83,
   83,   83,   83,   83,   84,   37,   38,   84,   84,   84,
   84,   84,   84,   84,   84,   73,   84,   84,   84,   84,
   84,   84,   84,   84,   84,   84,   84,   33,   69,   80,
   33,   81,   76,  204,   33,   33,   33,   33,  205,   34,
  199,  210,   33,   33,   37,   38,   33,   33,   33,   33,
   31,   37,   38,   31,   88,   89,   90,   31,   31,   31,
  132,   38,   60,  207,  206,   31,   31,   47,   53,   31,
   31,   31,   31,   88,   89,   90,   13,   19,   28,   20,
   22,   28,   75,   75,   75,   28,   28,   28,  105,  153,
  115,  209,  152,   28,   28,   56,   57,   28,   28,   28,
   28,    6,   58,    6,    7,   59,    7,   60,    8,    9,
    8,  104,  147,  156,  201,   98,   10,   11,   10,   11,
   12,   13,   14,   15,   14,    6,  180,    0,    7,    0,
    0,    0,    8,    0,   26,   48,    0,    0,    0,    0,
   10,   11,    0,   49,    0,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,    0,    0,
    0,    0,    6,    0,    0,    7,    0,    0,    0,    8,
    9,    0,    6,    0,    0,    7,  148,   10,   11,    8,
    0,   12,   13,   14,   15,    0,    0,   10,   11,    0,
    0,    0,    0,   14,   60,    0,    0,   60,    0,   60,
   60,   60,   60,   60,   60,    0,    0,    0,    0,   60,
   60,   60,   60,   60,   60,   60,   60,    0,    0,    0,
  105,    0,    0,  105,    0,  105,  105,  105,  105,  105,
  105,    0,    0,    0,    0,  105,  105,  105,  105,  105,
  105,  105,  105,  104,    0,    0,  104,    0,  104,  104,
  104,  104,  104,  104,    0,    0,    0,    0,  104,  104,
  104,  104,  104,  104,  104,  104,   26,    0,    0,   26,
    0,    0,    0,   26,   26,   26,    0,    0,    0,    0,
    0,   26,   26,    0,    0,   26,   26,   26,   26,   50,
    0,    0,   50,    0,    0,    0,   50,    0,    0,   50,
    0,    0,    0,    0,   50,   50,   50,   50,    6,    0,
   50,    7,    0,    0,    0,    8,    0,    0,   48,    0,
    0,    0,    0,   10,   11,    0,   49,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,    4,   45,   41,   47,   59,  125,  123,  123,
   83,  276,  277,   31,   41,   84,   41,   59,   60,   61,
   62,    2,   41,   11,   43,   32,   45,  123,   50,   41,
  262,  263,   59,   40,   59,  123,   17,   43,  258,   45,
   59,   60,   61,   62,  268,   41,   41,   43,  268,   45,
   45,  123,   41,   59,   83,   42,  257,   41,   40,   43,
   47,   45,   50,   59,   60,   61,   62,   43,   41,   45,
   43,   41,   45,   45,   44,   41,   91,   92,   96,  125,
   98,  257,  104,  125,   86,   59,   59,   60,   61,   62,
  163,   59,   41,   41,   44,  164,   99,  100,  257,   58,
   40,  260,   41,   41,   40,  264,  125,   40,   43,   59,
   45,   40,   45,  272,  273,  258,  104,   44,   41,   45,
  274,   58,   44,  273,  266,   60,   61,   62,   45,  125,
  118,  160,   59,   59,  163,  137,  138,  155,   59,   59,
  142,   59,  257,  150,   60,   61,   62,  261,  266,  178,
   59,   59,  125,   60,   61,   62,   44,   59,  125,   41,
   59,  257,   59,   59,  260,  194,  168,   59,  264,  157,
  261,  267,  257,   40,  258,  125,  272,  273,  207,  275,
  257,  188,  278,  125,   40,  257,  174,  261,  260,   59,
   59,  257,  264,   58,   41,   44,   59,  204,  125,  274,
  272,  273,  190,  191,  257,   58,  278,   41,  210,   59,
   40,   59,   44,   59,  272,  257,   59,   59,  260,  261,
  262,  263,  264,  265,  266,  267,  125,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  276,
  277,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  261,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  261,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  123,  257,
  260,  259,  261,   40,  264,  265,  266,  257,   41,  259,
  125,   41,  272,  273,  257,  258,  276,  277,  278,  279,
  257,  257,  258,  260,  269,  270,  271,  264,  265,  266,
  257,  258,  125,   44,  263,  272,  273,   59,   58,  276,
  277,  278,  279,  269,  270,  271,   59,   41,  257,   41,
   41,  260,  269,  270,  271,  264,  265,  266,  125,  113,
   69,  207,  112,  272,  273,   14,   14,  276,  277,  278,
  279,  257,   14,  257,  260,   14,  260,   14,  264,  265,
  264,  125,  101,  118,  191,   43,  272,  273,  272,  273,
  276,  277,  278,  279,  278,  257,  164,   -1,  260,   -1,
   -1,   -1,  264,   -1,  125,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,   -1,   -1,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
  265,   -1,  257,   -1,   -1,  260,  125,  272,  273,  264,
   -1,  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,
   -1,   -1,   -1,  278,  257,   -1,   -1,  260,   -1,  262,
  263,  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  276,  277,  278,  279,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,  262,  263,
  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
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
"sentencia_ejecutable_do : sentencia_continue",
"sentencia_break : BREAK",
"sentencia_break : BREAK ':' etiqueta",
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

//#line 243 ".\gramatica.y"

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
//#line 539 "Parser.java"
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
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 50:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 51:
//#line 126 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 52:
//#line 127 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 58:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 59:
//#line 146 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 60:
//#line 147 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 61:
//#line 151 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 62:
//#line 152 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 63:
//#line 153 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 64:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 65:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 66:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 67:
//#line 160 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 68:
//#line 161 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 75:
//#line 176 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 76:
//#line 177 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 97:
//#line 222 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 98:
//#line 223 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 99:
//#line 224 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 100:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 101:
//#line 226 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 102:
//#line 227 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 103:
//#line 228 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 104:
//#line 229 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 105:
//#line 230 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 107:
//#line 235 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 844 "Parser.java"
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
