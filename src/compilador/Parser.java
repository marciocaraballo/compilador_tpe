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
   31,   31,   19,   19,   19,   22,   22,   22,   22,   33,
   22,   32,   32,   20,   20,   34,   34,   34,   34,   35,
   35,   30,   30,   37,   30,   36,   36,   36,   36,   36,
   36,   14,   14,   14,   38,   38,   38,   39,   39,   39,
   41,   42,   42,   43,   43,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   40,   40,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    3,    1,    4,
    3,    6,    7,    6,    1,    3,    5,    1,    3,    2,
    1,    3,    2,    2,    1,    3,    3,    2,    2,    1,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    1,    1,    2,    4,    1,    3,    3,    2,    1,
    1,    3,    7,    6,    6,    6,    6,    1,    1,    3,
    1,    2,    4,    3,    3,    9,    8,    8,    8,    0,
   15,    1,    2,    8,   10,    1,    3,    2,    2,    1,
    2,    3,    2,    0,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    4,    1,    3,    1,    1,    5,    5,    4,    4,    4,
    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  128,  127,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   41,   43,   45,   47,   49,    0,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  125,    0,    0,    0,    0,    0,    0,  107,  109,
  110,    0,    0,    0,    0,   51,   69,   52,   53,    0,
   42,   44,   46,   48,   50,    0,   34,    0,    0,    2,
    8,    0,   17,    0,   16,    0,    0,    0,    0,    5,
    0,    3,   74,    0,   18,    0,    0,    0,    0,    0,
    0,    0,    0,  126,    0,    0,   96,   97,   98,    0,
    0,   99,  100,  101,    0,    0,    0,    0,    0,   54,
    0,   59,   71,    0,    0,   39,    0,   32,    0,   14,
    0,    0,   21,   62,    1,   73,    0,  121,  119,  122,
    0,  120,    0,  118,   31,    0,    0,    0,    0,  114,
  115,    0,  112,    0,    0,    0,    0,    0,    0,    0,
    0,  105,  106,   68,   58,    0,   70,   72,    0,    0,
   37,   36,   20,    0,    0,  117,  116,    0,   30,    0,
    0,    0,  111,    0,    0,    0,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,  113,   83,    0,    0,    0,    0,    0,   65,   67,
    0,   66,    0,   90,    0,    0,    0,   88,   91,   23,
    0,   80,    0,    0,    0,    0,   63,   24,   87,    0,
   84,    0,   27,    0,   78,    0,   79,   77,    0,    0,
    0,   76,   85,   29,    0,    0,    0,    0,    0,   81,
};
final static short yydgoto[] = {                          3,
    4,   77,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   78,  138,   45,  222,  223,   68,   69,   25,   26,
   27,   28,   29,   57,   58,   59,   30,   31,   60,   46,
  114,  175,  224,  187,  188,  105,   47,   48,   49,   50,
   51,  142,  143,
};
final static short yysindex[] = {                      -106,
    0,  300,    0,  -72,  -42,   36,  -34, -160,  -13, -112,
    0,    0,  352,  -45,  -24,  300,    0,    0,    0,    0,
    0,  -44,   51,  273,    0,    0,    0,    0,    0,   54,
    0,  418,   -8,  -36, -138,  -38,   84,   97,   -6,   96,
  102,    0, -102,  105,  -40,  121,  397,   32,    0,    0,
    0, -103,   45,  109,  530,    0,    0,    0,    0,  -94,
    0,    0,    0,    0,    0, -177,    0,  111,  137,    0,
    0,  138,    0,  125,    0,  300,  -80,   62,  -84,    0,
   66,    0,    0,   35,    0,  155,  139,  143,   -1,   12,
  151,  -41,  -20,    0,   80,  -37,    0,    0,    0,  -38,
  -38,    0,    0,    0,  -38,  -50,  -38,  -38,  -38,    0,
  -18,    0,    0,  503,  -11,    0,  -16,    0,  -17,    0,
   92,  203,    0,    0,    0,    0,   -9,    0,    0,    0,
  189,    0,  195,    0,    0,  197,    2,  215,  213,    0,
    0,   58,    0,  300,  160,  -90,   32,   32,   30,  182,
   30,    0,    0,    0,    0,  201,    0,    0,  128,  265,
    0,    0,    0,  -38,  302,    0,    0, -149,    0,  271,
 -220,  -20,    0,  300,  226,  300,  238,  300,  300,    0,
  279,   18,  305,   55,  216,    0, -128,  275,    0, -149,
  330,    0,    0,  325,  272,  300,  274,  277,    0,    0,
  344,    0,  346,    0,  387,  302,  353,    0,    0,    0,
 -220,    0,  355,  290,  367,  369,    0,    0,    0,  172,
    0,  393,    0,  166,    0,  389,    0,    0,  390, -220,
  411,    0,    0,    0,  -38,  419,  329,  300,  336,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   95,    0,    0,    0,  410,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  410,    0,    0,    0,    0,
    1,    0,    0,  410,    0,    0,    0,   24,    0,    0,
    0,  406,  442,  461,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  190,    0,  227,  328,    0,
    0,  377,    0,  250,    0,    0,    0,    0,    0,    0,
  465,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -33,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  410,    0,  354,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,  144,    0,    0,    0,    0,    0,  433,    0,
    0,    0,    0,    0,    0,    0,   47,   70,  -29,    0,
  -28,    0,    0,    0,    0,  483,    0,    0,  410,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -8,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  520,    0,    0,    0,    0,
  434,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  167,    0,    0,    0, -104,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  436,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  410,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   34,  -39,    0,    9,    0,    0,    0,  -47,   60,
    0,  402,    0,   15,  -53,  251,  363,    0,  470,  472,
  473,  477,  482,  -27,    0,    0,  385,  422,    0,  -26,
    0, -124,    0,  291,  313,  462,    0,   75,   69,  -25,
    0,    0,  339,
};
final static int YYTABLESIZE=808;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        136,
  108,   35,  100,  146,  101,   39,   43,   93,   43,   86,
   55,   92,   95,   67,   73,    7,    2,   96,   56,  103,
  104,  102,   83,  104,   43,   93,   44,  113,  159,   92,
   95,   43,  178,   43,   91,   15,  135,   33,  139,  131,
  155,  108,  108,  108,  137,  108,  102,  108,   84,   71,
   32,  195,  133,  197,  198,   11,   12,  130,  201,  108,
  108,  108,  108,   56,  104,   81,  104,  141,  104,  103,
  132,  214,  100,  108,  101,   36,  200,  100,  109,  101,
  116,   74,  104,  104,  104,  104,  158,  102,  160,  102,
  117,  102,   75,  126,   85,  203,   40,  100,  173,  101,
   70,  172,  111,  110,  174,  102,  102,  102,  102,   75,
  103,   79,  103,  239,  103,  124,   82,  191,   72,  149,
  189,  151,   56,  137,   87,  108,   11,   12,  103,  103,
  103,  103,  182,  206,  207,   92,  174,   88,  174,  174,
    7,   93,  210,  123,   52,   95,  141,    6,  104,   43,
    1,    7,   68,   19,   53,   94,  174,   89,   89,    9,
   10,  106,   54,  137,   34,   13,   64,  112,  181,  118,
  177,  102,   43,  186,  147,  148,  152,  153,  184,  115,
  119,   35,  137,  120,    5,  122,  123,    6,   10,   40,
  125,    7,    8,  204,  103,  127,  209,  128,  174,    9,
   10,  129,  144,   11,   12,   13,   14,  193,  236,  134,
  150,   66,   72,  209,  186,  135,  163,   75,   41,   42,
   41,   42,   37,  145,   38,   34,   33,   93,   97,   98,
   99,   92,   95,   40,   11,   12,  140,   42,  154,   66,
  124,  161,  164,   41,   42,   41,   42,  166,   40,   15,
   89,  165,   90,  167,  168,  170,  171,  108,  169,  180,
  108,  108,  108,  108,  108,  108,  108,  108,  123,  108,
  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,
  104,    7,  176,  104,  104,  104,  104,  104,  104,  104,
  104,   64,  104,  104,  104,  104,  104,  104,  104,  104,
  104,  104,  104,  102,  179,  183,  102,  102,  102,  102,
  102,  102,  102,  102,   40,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  102,  102,  103,   35,  190,  103,
  103,  103,  103,  103,  103,  103,  103,  199,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,  103,   75,
  194,   33,   75,   38,   75,   75,   75,   75,   75,   75,
  196,   41,   42,  202,   75,   75,   75,   75,   75,   75,
   75,   75,  124,  211,   15,  124,   19,  124,  124,  124,
  124,  124,  124,  212,   41,   42,   35,  124,  124,  124,
  124,  124,  124,  124,  124,   76,  213,   38,  215,  208,
  123,  216,  217,  123,  218,  123,  123,  123,  123,  123,
  123,  221,   38,  225,  226,  123,  123,  123,  123,  123,
  123,  123,  123,   64,  185,  227,   64,  228,   64,   64,
   64,   64,   64,   64,  229,   19,  230,  231,   64,   64,
   64,   64,   64,   64,   64,   64,   40,  232,  233,   40,
  235,  238,   35,   40,   40,   40,  103,  104,  102,  237,
  240,   40,   40,   68,    4,   40,   40,   40,   40,   94,
   94,   94,   52,   25,   26,    6,   28,  121,   38,    7,
  234,  162,   61,   33,   62,   63,   33,    9,   10,   64,
   33,   33,   33,   13,   65,  156,  220,  205,   33,   33,
  124,   19,   33,   33,   33,   33,   15,    0,  107,   15,
  192,  219,    0,   15,   15,   15,    0,    0,    0,    0,
    0,   15,   15,    0,    0,   15,   15,   15,   15,    5,
    0,   52,    6,    0,    6,    0,    7,    8,    7,    0,
    0,    0,   80,    0,    9,   10,    9,   10,   11,   12,
   13,   14,   13,    0,    0,    0,    5,    0,   52,    6,
    0,    6,    0,    7,    8,    7,   56,    0,    0,    0,
    0,    9,   10,    9,   10,   11,   12,   13,   14,   13,
    0,    0,    0,    0,   35,   60,    0,   35,    0,    0,
    0,   35,   35,   35,    0,    0,    0,    0,    0,   35,
   35,    0,    0,   35,   35,   35,   35,   57,   52,    0,
   38,    6,    0,   38,    0,    7,    0,   38,   38,   38,
    0,    0,    0,    9,   10,   38,   38,  157,    0,   38,
   38,   38,   38,   19,    0,    0,   19,    0,    0,    0,
   19,   19,   19,   52,   90,    0,    6,    0,   19,   19,
    7,    0,   19,   19,   19,   19,    0,    0,    9,   10,
    0,    0,    0,    0,   13,   97,   98,   99,    0,    0,
    0,    0,    0,    0,    5,    0,    0,    6,   94,   94,
   94,    7,    8,    0,    0,    0,    0,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,    0,   56,    0,
    0,   56,    0,    0,    0,   56,    0,    0,   56,    0,
    0,    0,    0,   56,   56,   56,   56,   60,    0,   56,
   60,    0,    0,    0,   60,    0,    0,   60,    0,    0,
    0,    0,   60,   60,   60,   60,    0,    0,   60,   57,
    0,    0,   57,    0,    0,    0,   57,    0,    0,   57,
    0,    0,    0,    0,   57,   57,   57,   57,    0,   52,
   57,    0,    6,    0,    0,    0,    7,    0,    0,   53,
    0,    0,    0,    0,    9,   10,   90,   54,    0,   90,
   13,   86,   86,   90,    0,    0,   52,    0,    0,    6,
    0,   90,   90,    7,    0,    0,   53,   90,    0,    0,
    0,    9,   10,    0,   54,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   44,   43,   41,   45,   40,   45,   41,   45,   36,
  123,   41,   41,   59,   59,    0,  123,   44,   10,   60,
   61,   62,   59,    0,   45,   59,   40,   55,   40,   59,
   59,   45,  123,   45,   41,    2,  257,    4,   92,   41,
   59,   41,   42,   43,   92,   45,    0,   47,   34,   16,
  123,  176,   41,  178,  179,  276,  277,   59,   41,   59,
   60,   61,   62,   55,   41,   32,   43,   93,   45,    0,
   59,  196,   43,   42,   45,   40,   59,   43,   47,   45,
  258,   22,   59,   60,   61,   62,  114,   41,  115,   43,
  268,   45,    0,   59,   35,   41,  257,   43,   41,   45,
  125,   44,   58,   59,  144,   59,   60,   61,   62,   59,
   41,   58,   43,  238,   45,    0,  125,  171,  257,  105,
  168,  107,  114,  171,   41,  125,  276,  277,   59,   60,
   61,   62,  159,  262,  263,   40,  176,   41,  178,  179,
  125,   40,  190,    0,  257,   41,  172,  260,  125,   45,
  257,  264,   58,   59,  267,  258,  196,  262,  263,  272,
  273,   41,  275,  211,  268,  278,    0,   59,   41,   59,
  261,  125,   45,  165,  100,  101,  108,  109,  164,  274,
   44,   44,  230,   59,  257,  266,  125,  260,  273,    0,
  125,  264,  265,  185,  125,   41,  188,   59,  238,  272,
  273,   59,  123,  276,  277,  278,  279,  174,  235,   59,
  261,  257,  257,  205,  206,  257,  125,  125,  257,  258,
  257,  258,  257,  261,  259,  268,    0,  261,  269,  270,
  271,  261,  261,   44,  276,  277,  257,  258,  257,  257,
  125,  258,   40,  257,  258,  257,  258,   59,   59,    0,
  257,  261,  259,   59,   58,   41,   44,  257,  257,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,  123,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,   41,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,    0,   58,  260,
  261,  262,  263,  264,  265,  266,  267,   59,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  125,  125,  260,    0,  262,  263,  264,  265,  266,  267,
  123,  257,  258,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   44,  125,  260,    0,  262,  263,  264,
  265,  266,  267,   59,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  123,  125,   44,  125,  125,
  257,  125,   59,  260,   59,  262,  263,  264,  265,  266,
  267,   59,   59,   59,  125,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,   59,  260,   59,  262,  263,
  264,  265,  266,  267,  263,   59,   44,  272,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   59,   59,  260,
   40,  123,  125,  264,  265,  266,   60,   61,   62,   41,
  125,  272,  273,   58,    0,  276,  277,  278,  279,   60,
   61,   62,  257,   41,   41,  260,   41,   76,  125,  264,
  230,  119,   13,  257,   13,   13,  260,  272,  273,   13,
  264,  265,  266,  278,   13,  111,  206,  185,  272,  273,
   79,  125,  276,  277,  278,  279,  257,   -1,   47,  260,
  172,  125,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,  257,  260,   -1,  260,   -1,  264,  265,  264,   -1,
   -1,   -1,  125,   -1,  272,  273,  272,  273,  276,  277,
  278,  279,  278,   -1,   -1,   -1,  257,   -1,  257,  260,
   -1,  260,   -1,  264,  265,  264,  125,   -1,   -1,   -1,
   -1,  272,  273,  272,  273,  276,  277,  278,  279,  278,
   -1,   -1,   -1,   -1,  257,  125,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  125,  257,   -1,
  257,  260,   -1,  260,   -1,  264,   -1,  264,  265,  266,
   -1,   -1,   -1,  272,  273,  272,  273,  125,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,  257,  125,   -1,  260,   -1,  272,  273,
  264,   -1,  276,  277,  278,  279,   -1,   -1,  272,  273,
   -1,   -1,   -1,   -1,  278,  269,  270,  271,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  269,  270,
  271,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,  257,
  278,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,
  278,  262,  263,  264,   -1,   -1,  257,   -1,   -1,  260,
   -1,  272,  273,  264,   -1,   -1,  267,  278,   -1,   -1,
   -1,  272,  273,   -1,  275,   -1,   -1,  278,
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
"sentencia_when : WHEN condicion ')' THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion THEN '{' bloque_sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' bloque_sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' '{' bloque_sentencias_when '}' ';' $$1 WHEN '(' condicion ')' '{' bloque_sentencias_when '}'",
"bloque_sentencias_when : sentencia",
"bloque_sentencias_when : sentencia sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
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

//#line 268 ".\gramatica.y"

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
//#line 626 "Parser.java"
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
{ logger.logError("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 78:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 79:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 80:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 81:
//#line 174 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 84:
//#line 183 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 85:
//#line 184 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 88:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 89:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 94:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 95:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 118:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 119:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 120:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 121:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 122:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 123:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 124:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 126:
//#line 260 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 999 "Parser.java"
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
