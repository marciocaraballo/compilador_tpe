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
   32,   20,   20,   20,   20,   20,   20,   20,   20,   34,
   34,   34,   34,   35,   35,   30,   30,   37,   30,   36,
   36,   36,   36,   36,   36,   14,   14,   14,   38,   38,
   38,   39,   39,   39,   41,   42,   42,   43,   43,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   40,   40,
    9,    9,
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
    2,    8,   10,    7,    7,    7,    7,    7,    7,    1,
    3,    2,    2,    1,    2,    3,    2,    0,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    4,    1,    3,    1,    1,    5,
    5,    4,    4,    4,    4,    4,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  132,  131,    0,    0,    0,    0,    9,   10,   11,   12,
   13,    0,    0,    0,   41,   43,   45,   47,   49,    0,
   61,    0,    0,    0,    0,    0,  129,    0,    0,    0,
    0,    0,    0,  111,  113,  114,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   51,   69,   52,   53,    0,
   42,   44,   46,   48,   50,    0,   34,    0,    0,    2,
    8,    0,   17,    0,   16,    0,    0,    0,    0,    5,
    0,    3,   74,    0,   18,    0,  130,    0,    0,  100,
  101,  102,    0,    0,  103,  104,  105,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,    0,   59,   71,    0,    0,   39,    0,   32,    0,
   14,    0,    0,   21,   62,    1,   73,  118,  119,    0,
  116,    0,    0,    0,    0,    0,    0,    0,    0,  109,
  110,  125,  123,  126,    0,  124,    0,  122,   31,    0,
    0,    0,    0,    0,    0,   68,   58,    0,   70,   72,
    0,    0,   37,   36,   20,    0,    0,  115,    0,    0,
    0,    0,    0,    0,    0,    0,  121,  120,    0,   30,
    0,    0,    0,    0,    0,   55,    0,    0,    0,    0,
  117,   94,    0,    0,   92,   95,    0,    0,    0,    0,
    0,   22,    0,    0,    0,    0,    0,    0,   65,   67,
    0,   66,    0,   91,   86,   84,   89,    0,    0,   87,
   85,   23,    0,   81,    0,    0,    0,   63,   24,    0,
   82,    0,   27,   78,    0,   77,    0,    0,    0,   76,
   83,   29,    0,    0,    0,    0,    0,    0,    0,   79,
};
final static short yydgoto[] = {                          3,
    4,   77,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   78,  152,   40,  232,  233,   68,   69,   25,   26,
   27,   28,   29,   57,   58,   59,   30,   31,   60,   41,
  115,  206,  239,  171,  172,   98,   42,   43,   44,   45,
   46,  130,  131,
};
final static short yysindex[] = {                      -106,
    0,  637,    0,  -73,  -42,  -13,  -34, -238,  -12,  312,
    0,    0,  217,  -49,  -90,  637,    0,    0,    0,    0,
    0,  -48,   22,  273,    0,    0,    0,    0,    0,  -19,
    0,  497,  -15,  -36, -160,   83,    0, -130,   -5,  -40,
   95,  399,   58,    0,    0,    0,   99,  104,   -8,   94,
  105, -121,   59,  106,  660,    0,    0,    0,    0, -122,
    0,    0,    0,    0,    0, -190,    0,  107,  120,    0,
    0,  127,    0,  115,    0,  637,  -91,   51,  -96,    0,
   54,    0,    0,   44,    0,  -38,    0,  -83,  -37,    0,
    0,    0,  -20,  -20,    0,    0,    0,  -20,  -81,  -20,
  -20,  -20,  122,  123,   15,   18,  126,  -41,  -67,  147,
    0,  -45,    0,    0,  580,  -11,    0,  -61,    0,  -59,
    0,   71,  170,    0,    0,    0,    0,    0,    0,   81,
    0,  487,  487,  468,   58,   58,   53,  487,   53,    0,
    0,    0,    0,    0,  152,    0,  154,    0,    0,  156,
  -23,  174,  173,  117, -108,    0,    0,  180,    0,    0,
  128,  202,    0,    0,    0,  -20,  -38,    0,  -71,    0,
   -9,  610,   -7,  470,   -6,   -4,    0,    0, -138,    0,
  225, -182,  637,  125,  637,    0,  201,   32,  246,   92,
    0,    0,  627,  247,    0,    0,  269,  270, -120,  279,
  292,    0, -138,  317,  637,  239,  637,  249,    0,    0,
  325,    0,  328,    0,    0,    0,    0,  487,  338,    0,
    0,    0, -182,    0,  340,  275,  343,    0,    0,  140,
    0,  361,    0,    0,  353,    0,  356, -182,  153,    0,
    0,    0,  386,  -20,  387,  177,  326,  637,  323,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   96,  451,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,  451,    0,
    0,    0,   24,    0,    0,    0,    0,    0,    0,    0,
  451,  393,  520,  539,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  354,    0,  227,  377,    0,
    0,  437,    0,  250,    0,    0,    0,    0,    0,    0,
  471,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -33,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  451,    0,  414,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,   70,  -29,    0,  -28,    0,
    0,    0,    0,    0,  116,    0,  144,    0,    0,    0,
    0,    0,  429,    0,    0,    0,    0,  558,    0,    0,
  451,    0,    0,    0,    0,    0,    0,    0,    0,  599,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -105,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  431,  350,    0,    0,    0,    0,    0,
  167,    0,    0,    0,    0,    0,    0,    0,  190,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  439,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  451,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   50, -128,    0,  466,    0,    0,    0,  -55,   29,
    0,  402,    0,    4,  -63,  244,  363,    0,  472,  473,
  475,  481,  482,  -14,    0,    0,  385,  419,    0,   -2,
    0, -127,    0,  -62,  332,  467,    0,   67,   61,  -68,
    0,    0,  341,
};
final static int YYTABLESIZE=938;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        150,
  112,   35,   93,  134,   94,   49,   38,   97,   38,   67,
   73,   96,   99,  157,  185,    7,    2,  129,   50,   96,
   97,   95,   83,  108,   38,   97,   39,   51,  161,   96,
   99,   38,  107,   38,   70,   88,   89,   84,   79,   38,
  114,  112,  112,  112,  153,  112,  106,  112,  110,   32,
   74,   15,  151,   33,  205,  145,  205,  208,  147,  112,
  112,  112,  112,   85,  108,   71,  108,  117,  108,  107,
  173,  175,  211,  144,  149,  176,  146,  118,  205,  226,
   75,   81,  108,  108,  108,  108,   93,  106,   94,  106,
  210,  106,   75,   11,   12,   93,   72,   94,  129,  101,
  160,  137,  127,  139,  102,  106,  106,  106,  106,   82,
  107,  199,  107,  162,  107,  128,  112,  111,  204,  205,
  249,  168,   86,  202,  167,  112,  151,   87,  107,  107,
  107,  107,  213,  108,   93,   99,   94,   11,   12,  103,
    7,  218,  219,  127,  104,  109,   34,  222,  108,   38,
    1,  116,  184,   68,   19,  230,   93,   93,  188,  135,
  136,  140,  141,  120,  113,  119,   64,  151,  187,  190,
   35,  106,   38,  121,  123,  124,   10,  132,  126,  138,
  142,  143,  151,    5,  148,   52,    6,  155,    6,   88,
    7,    8,    7,  154,  107,  165,  163,   66,    9,   10,
    9,   10,   11,   12,   13,   14,   13,   66,   72,  166,
  177,  156,  178,  179,  181,  149,  182,   75,  128,   37,
   36,   37,   47,  133,   48,   34,   33,   97,   90,   91,
   92,   96,   99,  180,   11,   12,   36,   37,  186,  183,
  128,  245,  189,   36,   37,   36,   37,  207,  105,   15,
  106,   36,   37,  194,  224,  197,  200,  112,  201,  209,
  112,  112,  112,  112,  112,  112,  112,  112,  127,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  108,    7,  203,  108,  108,  108,  108,  108,  108,  108,
  108,   64,  108,  108,  108,  108,  108,  108,  108,  108,
  108,  108,  108,  106,  212,  215,  106,  106,  106,  106,
  106,  106,  106,  106,   88,  106,  106,  106,  106,  106,
  106,  106,  106,  106,  106,  106,  107,  216,  217,  107,
  107,  107,  107,  107,  107,  107,  107,  220,  107,  107,
  107,  107,  107,  107,  107,  107,  107,  107,  107,   75,
  221,   33,   75,   40,   75,   75,   75,   75,   75,   75,
  223,   36,   37,  225,   75,   75,   75,   75,   75,   75,
   75,   75,  128,  227,   15,  128,   35,  128,  128,  128,
  128,  128,  128,  228,   36,   37,  229,  128,  128,  128,
  128,  128,  128,  128,  128,   76,  231,   40,  234,  235,
  127,  236,  237,  127,  238,  127,  127,  127,  127,  127,
  127,  240,   40,   38,  241,  127,  127,  127,  127,  127,
  127,  127,  127,   64,  243,  244,   64,  246,   64,   64,
   64,   64,   64,   64,   55,   35,   19,  247,   64,   64,
   64,   64,   64,   64,   64,   64,   88,  250,  248,   88,
   68,   88,   88,   88,   88,   88,   88,   38,   96,   97,
   95,   88,   88,   88,   88,   88,   88,   88,   88,   25,
    4,   26,   38,   52,   80,   56,    6,  122,   40,   28,
    7,  242,  164,   33,   61,   62,   33,   63,    9,   10,
   33,   33,   33,   64,   65,   19,  158,  125,   33,   33,
  193,   35,   33,   33,   33,   33,   15,  191,  100,   15,
   98,   98,   98,   15,   15,   15,    0,    0,    0,    0,
   56,   15,   15,    0,    0,   15,   15,   15,   15,    5,
    0,    0,    6,    0,    0,    0,    7,    8,   38,    0,
    0,    0,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   19,    0,    0,    0,    0,    0,    0,   52,    0,
    0,    6,    0,    0,    0,    7,    0,    0,   53,    0,
   56,    0,    0,    9,   10,    0,   54,    0,    0,   13,
  169,    0,  169,    0,    0,    0,    0,  170,  170,  170,
    0,    0,    0,  170,    0,    0,    0,    0,    0,  169,
   40,    0,    0,   40,    0,    0,    0,   40,   40,   40,
    0,   80,    0,    0,    0,   40,   40,    0,    0,   40,
   40,   40,   40,   35,  192,    0,   35,  196,    0,  170,
   35,   35,   35,    0,   56,    0,    0,    0,   35,   35,
    0,    0,   35,   35,   35,   35,    0,    0,  196,    0,
    0,    0,    0,   60,    0,    0,    0,   90,   91,   92,
   38,    0,    0,   38,    0,    0,    0,   38,   38,   38,
    0,    0,   57,  170,    0,   38,   38,    0,    0,   38,
   38,   38,   38,   19,    0,    0,   19,    0,    0,    0,
   19,   19,   19,    0,  159,    0,    0,    0,   19,   19,
    0,    0,   19,   19,   19,   19,    0,    0,    0,   98,
   98,   98,    0,   94,   52,    0,   52,    6,  174,    6,
    0,    7,  198,    7,  195,    0,    0,    0,    0,    9,
   10,    9,   10,   52,    0,   13,    6,   13,    0,    0,
    7,  214,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,    0,    0,   13,    0,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,   56,    0,    0,   56,
    0,    0,    0,   56,    0,    0,   56,    0,    0,    0,
    0,   56,   56,   56,   56,   60,    0,   56,   60,    0,
    0,    0,   60,    0,    0,   60,    0,    0,    0,    0,
   60,   60,   60,   60,   57,    0,   60,   57,    0,    0,
    0,   57,    0,    0,   57,    0,    0,    0,    0,   57,
   57,   57,   57,    0,    0,   57,   52,    0,    0,    6,
    0,    0,    0,    7,    0,    0,   53,    0,    0,    0,
    0,    9,   10,    0,   54,   94,    0,   13,   94,    0,
   90,   90,   94,    0,    0,    0,   52,    0,    0,    6,
   94,   94,    0,    7,    0,    0,   94,    0,    0,    0,
    0,    9,   10,   52,    0,    0,    6,   13,    0,    0,
    7,    0,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,    0,    0,   13,    0,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,   52,    0,    0,    6,
    0,    0,    0,    7,    0,    0,   53,    0,    0,    0,
    0,    9,   10,    0,   54,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   44,   43,   41,   45,   40,   45,   41,   45,   59,
   59,   41,   41,   59,  123,    0,  123,   86,  257,   60,
   61,   62,   59,    0,   45,   59,   40,   40,   40,   59,
   59,   45,   41,   45,  125,   41,   39,   34,   58,   45,
   55,   41,   42,   43,  108,   45,    0,   47,   51,  123,
   22,    2,  108,    4,  183,   41,  185,  185,   41,   59,
   60,   61,   62,   35,   41,   16,   43,  258,   45,    0,
  133,  134,   41,   59,  257,  138,   59,  268,  207,  207,
   59,   32,   59,   60,   61,   62,   43,   41,   45,   43,
   59,   45,    0,  276,  277,   43,  257,   45,  167,   42,
  115,   98,   59,  100,   47,   59,   60,   61,   62,  125,
   41,  174,   43,  116,   45,    0,   58,   59,  182,  248,
  248,   41,   40,  179,   44,  125,  182,  258,   59,   60,
   61,   62,   41,   40,   43,   41,   45,  276,  277,   41,
  125,  262,  263,    0,   41,   41,  268,  203,  125,   45,
  257,  274,  261,   58,   59,  218,  262,  263,  161,   93,
   94,  101,  102,   44,   59,   59,    0,  223,   41,  166,
   44,  125,   45,   59,  266,  125,  273,  261,  125,  261,
   59,   59,  238,  257,   59,  257,  260,   41,  260,    0,
  264,  265,  264,  261,  125,  125,  258,  257,  272,  273,
  272,  273,  276,  277,  278,  279,  278,  257,  257,   40,
   59,  257,   59,   58,   41,  257,   44,  125,  257,  258,
  257,  258,  257,  261,  259,  268,    0,  261,  269,  270,
  271,  261,  261,  257,  276,  277,  257,  258,   59,  123,
  125,  244,   41,  257,  258,  257,  258,  123,  257,    0,
  259,  257,  258,  263,  205,  263,  263,  257,  263,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  266,   58,  260,  261,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   59,   59,  260,
  261,  262,  263,  264,  265,  266,  267,   59,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  257,
   59,  125,  260,    0,  262,  263,  264,  265,  266,  267,
   44,  257,  258,  125,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  125,  125,  260,    0,  262,  263,  264,
  265,  266,  267,   59,  257,  258,   59,  272,  273,  274,
  275,  276,  277,  278,  279,  123,   59,   44,   59,  125,
  257,   59,  263,  260,   44,  262,  263,  264,  265,  266,
  267,   59,   59,    0,   59,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  272,   40,  260,   41,  262,  263,
  264,  265,  266,  267,  123,   59,    0,  261,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  125,  123,  260,
   58,  262,  263,  264,  265,  266,  267,   44,   60,   61,
   62,  272,  273,  274,  275,  276,  277,  278,  279,   41,
    0,   41,   59,  257,  125,   10,  260,   76,  125,   41,
  264,  238,  120,  257,   13,   13,  260,   13,  272,  273,
  264,  265,  266,   13,   13,   59,  112,   79,  272,  273,
  169,  125,  276,  277,  278,  279,  257,  167,   42,  260,
   60,   61,   62,  264,  265,  266,   -1,   -1,   -1,   -1,
   55,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  125,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
  115,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
  123,   -1,  123,   -1,   -1,   -1,   -1,  132,  133,  134,
   -1,   -1,   -1,  138,   -1,   -1,   -1,   -1,   -1,  123,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
   -1,  125,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,  169,   -1,  260,  172,   -1,  174,
  264,  265,  266,   -1,  125,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,  193,   -1,
   -1,   -1,   -1,  125,   -1,   -1,   -1,  269,  270,  271,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
   -1,   -1,  125,  218,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,   -1,  125,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,  269,
  270,  271,   -1,  125,  257,   -1,  257,  260,  261,  260,
   -1,  264,  263,  264,  125,   -1,   -1,   -1,   -1,  272,
  273,  272,  273,  257,   -1,  278,  260,  278,   -1,   -1,
  264,  125,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,   -1,   -1,  278,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,  278,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
   -1,  272,  273,   -1,  275,  257,   -1,  278,  260,   -1,
  262,  263,  264,   -1,   -1,   -1,  257,   -1,   -1,  260,
  272,  273,   -1,  264,   -1,   -1,  278,   -1,   -1,   -1,
   -1,  272,  273,  257,   -1,   -1,  260,  278,   -1,   -1,
  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,   -1,   -1,  278,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,
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

//#line 272 ".\gramatica.y"

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
//#line 661 "Parser.java"
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
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion"); }
break;
case 92:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 93:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 98:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 99:
//#line 206 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 120:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 121:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 122:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 123:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 124:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 125:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 126:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 127:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 128:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 130:
//#line 264 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1050 "Parser.java"
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
