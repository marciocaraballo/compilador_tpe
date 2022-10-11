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
import java.io.File;
//#line 20 "Parser.java"




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
    4,    4,    4,    4,    6,    6,    6,    6,   11,   11,
    7,    7,    7,   14,   14,   15,   15,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   23,
   23,    8,    8,   22,   22,   22,   22,   22,   25,   25,
   21,   21,   19,   19,   20,   20,   27,   27,   27,   27,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   13,   13,   13,   13,   13,   31,   31,   31,   34,   34,
   33,   33,   35,   33,    9,    9,    9,   36,   36,   37,
   37,   37,   37,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   29,   29,   29,   41,   41,   41,   41,
   41,   42,   42,   40,   40,   43,   43,   43,   43,   43,
   28,   44,   44,   30,   30,   17,   17,   17,   39,   39,
   45,   39,   39,   39,   26,   26,   38,   38,   46,   46,
   47,   47,   24,   24,   24,   48,   48,   48,   48,   48,
   48,   32,   32,   32,   49,   49,   49,   50,   50,   50,
   52,   52,   53,   53,   54,   54,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   51,   51,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    2,    4,    5,   10,   13,   13,   16,   10,    1,    1,
    8,   11,    9,   10,    7,    9,    1,    1,    4,    3,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    4,    3,    3,    4,    1,    3,    5,    1,    3,
    2,    1,    0,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    7,    6,    1,    2,    8,   10,    1,    3,
    1,    2,    3,    2,    2,    1,    1,    1,    1,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    3,    4,    1,    3,    1,    1,    5,    5,    4,    4,
    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  179,  178,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,   94,   98,    0,   96,  100,
  102,  114,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  104,  122,  105,
  106,    0,   95,   99,   97,  101,  103,    0,   87,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,    0,  176,    0,  127,    0,    0,  157,  159,
  160,   19,  146,  147,  148,  149,  150,  151,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  107,    0,  112,  124,
    0,    0,   92,    0,   85,    0,   15,    0,    0,    0,
    0,    0,   23,   26,    0,    0,   24,   27,   28,   30,
   32,   34,   36,    0,    0,  115,    1,    0,  177,    0,
    0,  126,    0,    0,    0,    0,    0,  172,  170,  173,
    0,  171,    0,  169,    0,    0,    0,    0,   81,    0,
    0,   84,    0,    0,    0,    0,  121,  111,    0,  123,
  125,    0,    0,   90,   89,    0,    0,    0,    0,    0,
   57,    0,   58,   29,   31,   33,   35,   37,   39,   21,
    0,   25,    0,   42,    0,  165,  161,  166,    0,  163,
    0,    0,  155,  156,    0,    0,  168,  167,    0,   66,
    0,    0,   65,    0,    0,   64,    0,    0,    0,    0,
  108,    0,    0,    0,    0,    0,    0,   73,    0,    0,
    0,   22,   43,    0,    0,  162,    0,  139,    0,   61,
    0,   70,   69,    0,   68,    0,    0,    0,  134,    0,
  118,  120,    0,  119,    0,   75,    0,    0,   60,    0,
    0,    0,  164,  141,    0,    0,    0,   62,    0,   78,
  136,    0,    0,    0,  116,    0,   71,    0,   59,    0,
    0,  140,  142,    0,  137,    0,  131,    0,  130,    0,
    0,    0,   50,    0,    0,    0,    0,    0,   80,    0,
  129,    0,   40,   38,    0,    0,    0,    0,   55,    0,
  138,    0,    0,    0,   41,   51,    0,   49,    0,   38,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   53,    0,   56,    0,    0,    0,    0,    0,   48,   44,
   54,    0,    0,    0,    0,   52,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   45,   46,    0,    0,    0,
  132,    0,   47,
};
final static short yydgoto[] = {                          3,
    4,   15,  246,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  318,  126,  127,  303,   26,   27,  131,  132,
  133,  304,  305,   89,  294,  247,  182,   28,  110,  111,
  100,   90,  101,  270,  102,   60,   61,   29,   30,   31,
   50,   51,   32,   52,  300,  239,  265,   91,   78,   79,
   80,   81,  199,  200,
};
final static short yysindex[] = {                      -115,
    0,  864,    0,  469,  -34,   14,  -19,  -26,   26,  346,
    0,    0,  492,  -53,  630,    0,    0,    0,    0,    0,
    0,    0,  -46,   31,  -28,    0,    0,   53,    0,    0,
    0,    0,  654,  664,   33, -155,  590,   82,   98,  -36,
  -29,  -25,  566, -121,  100,   96,  887,    0,    0,    0,
    0, -110,    0,    0,    0,    0,    0,  -74,    0,  127,
  144,    0,    0,  152,    0,  139,    0,  383,  -71,    0,
  687,    0,  166,    0,  -50,    0,  142,   44,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  171,   -9,
   48,  160,  165,   30,   38,  167,  -40,  170,  -22,  198,
  201,   -8,  195,  218,    7,  224,    0,  -37,    0,    0,
  814,   43,    0,   16,    0,   23,    0,  253,  -15,  256,
  419,  593,    0,    0,  178,  831,    0,    0,    0,    0,
    0,    0,    0,  194,  266,    0,    0,   18,    0,   48,
   48,    0,   48,   48,   50,   48,   -3,    0,    0,    0,
  276,    0,  279,    0,  274,  -59,    4,  -59,    0,  284,
  -59,    0,  -59,  285,  222, -108,    0,    0,  306,    0,
    0,  571,  325,    0,    0,  590,   27,   -2,  590,  887,
    0,   94,    0,    0,    0,    0,    0,    0,    0,    0,
  263,    0,  287,    0,  116,    0,    0,    0,   74,    0,
   44,   44,    0,    0,  620,   -3,    0,    0,  -59,    0,
  333,  -59,    0,  -59,  369,    0,  -59,  864,  292,  713,
    0,  363,   41,  379,  404,  389,  162,    0,  420,  795,
  418,    0,    0,  419,   63,    0,  417,    0,    9,    0,
  -59,    0,    0,  -59,    0,  864,  343,  864,    0,  345,
    0,    0,  412,    0,  220,    0,  423,  225,    0,  358,
  590,  211,    0,    0,  321,  620,  429,    0,  452,    0,
    0,  439,  384,  460,    0, -104,    0,  408,    0,  491,
  504,    0,    0,  290,    0,  -59,    0,  495,    0,  515,
  689,  294,    0,  296,  864,  500,  590,  505,    0,  291,
    0,  590,    0,    0,  715,  506,  598, -101,    0,  525,
    0,  528,  530,  444,    0,    0,  689,    0,  309,    0,
  327,  514,  466,  518,  590,  334,   54,  715,  537,  538,
    0,  539,    0,  559, -103,  -90,  542,  477,    0,    0,
    0,  347,  689,  689,  341,    0,  351,  494,  715,  715,
  556,  561,  864,  498,  511,    0,    0,  513,  367,  362,
    0,  580,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  264,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  385,  385,    0,  583,  738,  757,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  436,    0,  310,
  459,    0,    0,  520,    0,  543,    0,    0,    0,    0,
  644,    0,   84,    0,    0,    0,  177,  107,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  385,    0,  -12,    0,
   80,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  497,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  854,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   55,   60,    0,    0,    0,
  200,    0,  227,    0,    0,    0,    0,    0,    0,    0,
  385,    0,    0,    0,    0,    0,    0,    0,  776,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  854,    0,    0,    0,    0,    0,    0,    0,
  130,  154,    0,    0,    0,   76,    0,    0,  523,    0,
    0,    0,    0,    0,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -102,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  531,    0,    0,  385,    0,  -88,    0,    0,    0,    0,
    0,    0,  250,    0,    0,    0,  -81,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   93,    0,
    0,    0,    0,  273,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  385,    0,    0,    0,    0,
    0,  391,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   24,   65,  -20,  400,    0,    0,    0,    0,  669,
   51,    0,  -51,    0,  532,  -41,  -13,  -10,  535,  541,
  547,  -66, -267,  -39, -272,  314,  430,  -61,   -1,  490,
  -16,    3, -112,  386,    0,  555,    0,  660,  662,  665,
    0,    0,  611,    0,    0,  416,    0,  594,  186,  185,
 -107,    0,    0,  453,
};
final static int YYTABLESIZE=1165;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
  155,  134,   54,  106,   96,   59,  135,    2,   49,   36,
   97,   98,   65,   42,  220,  103,  125,  156,  291,  343,
   40,  168,   74,  322,  177,  104,  128,   34,   82,   75,
  198,   82,  344,  140,  321,  141,  135,   77,  228,  140,
  140,  141,  141,   72,  211,   82,  169,  124,  215,  328,
   87,   88,   86,   37,  129,  189,   71,  130,  197,  193,
  135,  212,   75,  345,  135,   43,   16,  226,   16,  181,
  151,   75,  173,   66,  191,  349,  350,   75,  153,   63,
  157,  253,  172,  158,  128,  143,   82,   75,  150,   67,
  144,   76,   75,  147,   68,  144,  152,   16,   63,  252,
  145,   64,   87,   88,   86,  124,  154,   75,  184,  171,
   69,  185,  129,  144,  236,  130,  143,  235,  145,  183,
   76,  178,   92,   77,  158,  158,  158,  198,  158,  152,
  158,  269,  223,   79,  143,   63,  225,   76,   93,  229,
   77,    1,  158,  158,  158,  158,   35,  154,  206,  154,
   79,  154,  219,  153,  109,  290,  290,  108,  107,   74,
   74,  119,  119,  112,  119,  154,  154,  154,  154,  290,
  152,   74,  152,  269,  152,  119,  128,  135,  260,  227,
   72,   72,  181,  113,  140,  115,  141,  116,  152,  152,
  152,  152,   72,  114,  153,   36,  153,  117,  153,  175,
  142,   10,  257,   58,  140,  138,  141,  139,  158,  293,
   64,  145,  153,  153,  153,  153,   11,   12,  148,  167,
   94,  280,   95,  149,  292,  154,  174,  158,  171,  135,
   41,  154,  183,   35,  159,   11,   12,   38,  160,   39,
  320,   73,   74,  135,  161,  135,   11,   12,  162,  117,
   11,   12,  163,  314,  152,  135,  323,  310,  164,   83,
   84,   85,  313,  315,  166,  319,  135,  165,  293,  293,
  266,  267,  133,  174,  196,   74,  338,  129,  153,   58,
  130,  135,  135,   73,   74,  334,  315,  135,  135,   73,
   74,  129,  176,  129,  130,  179,  130,  354,  355,   73,
   74,  128,  190,  129,   73,   74,  130,  315,  315,   86,
  205,   83,   84,   85,  129,  336,  337,  130,  194,  196,
   74,  121,   20,  195,  175,  201,  202,  203,  204,  129,
  129,  209,  130,  130,  207,  129,  129,  208,  130,  130,
  158,  214,  217,  158,  218,  158,  158,  158,  158,  158,
  158,  174,  158,  158,  158,  158,  158,  158,  158,  158,
  158,  158,  158,  154,  221,  224,  154,  231,  154,  154,
  154,  154,  154,  154,  117,  154,  154,  154,  154,  154,
  154,  154,  154,  154,  154,  154,  152,  232,  234,  152,
  241,  152,  152,  152,  152,  152,  152,  133,  152,  152,
  152,  152,  152,  152,  152,  152,  152,  152,  152,   48,
  153,  233,  244,  153,  248,  153,  153,  153,  153,  153,
  153,  251,  153,  153,  153,  153,  153,  153,  153,  153,
  153,  153,  153,  128,   86,   93,  128,  254,  128,  128,
  128,  128,  128,  128,  255,  282,   48,  256,  128,  128,
  128,  128,  128,  128,  128,  128,  175,  261,   88,  175,
  258,  175,  175,  175,  175,  175,  175,  272,   47,  274,
  275,  175,  175,  175,  175,  175,  175,  175,  175,   93,
  276,  277,  279,  174,  281,  278,  174,  285,  174,  174,
  174,  174,  174,  174,   93,  286,   91,  287,  174,  174,
  174,  174,  174,  174,  174,  174,  117,  123,  288,  117,
   48,  117,  117,  117,  117,  117,  117,   88,  289,   20,
   48,  117,  117,  117,  117,  117,  117,  117,  117,  133,
  295,  296,  133,  250,  133,  133,  133,  133,  133,  133,
   91,  180,   16,  297,  133,  133,  133,  133,  133,  133,
  133,  133,  298,  301,  302,   91,  306,  307,  309,  271,
   93,  273,  312,  311,  316,  324,   86,  325,  327,   86,
  326,  329,  331,   86,   86,   86,  333,   44,   20,   48,
    6,   86,   86,   88,    7,   86,   86,   86,   86,  330,
  332,   33,    9,   10,  335,  339,  340,  341,   13,  342,
  346,  347,   44,  351,  238,    6,  105,  348,  308,    7,
   75,  222,   45,  352,  356,   75,  353,    9,   10,  357,
   46,   91,  359,   13,  362,   87,   88,   86,  336,   48,
   87,   88,   86,   48,   75,  360,  264,  361,  363,    5,
  121,   83,  118,    4,   20,   63,    7,    8,  119,   87,
   88,   86,   49,   67,  120,  121,  186,  192,   11,   12,
  122,   14,  187,  262,  283,  238,  358,   16,  188,  230,
  175,  299,   55,   44,   56,   44,    6,   57,    6,  136,
    7,  284,    7,  146,  119,   45,    0,  263,    9,   10,
    9,   10,   93,   46,   13,   93,   13,    0,    0,   93,
   93,   93,    0,    0,    0,    0,    0,   93,   93,   99,
   99,   93,   93,   93,   93,   88,    0,    0,   88,    0,
  317,    0,   88,   88,   88,    5,    0,    0,    6,    0,
   88,   88,    7,    8,   88,   88,   88,   88,    0,    0,
    9,   10,  237,    0,   11,   12,   13,   14,   44,    0,
    0,    6,    0,   91,   62,    7,   91,    0,    0,    0,
   91,   91,   91,    9,   10,   99,    0,    0,   91,   91,
    0,    0,   91,   91,   91,   91,   20,    0,   70,   20,
    0,    0,    0,   20,   20,   20,    0,    0,   72,    0,
    0,   20,   20,    0,    0,   20,   20,   20,   20,   16,
    0,    0,   16,    0,    0,    0,   16,   16,   16,    0,
    0,  137,    0,    0,   16,   16,    0,    0,   16,   16,
   16,   16,   73,   74,  210,    0,  213,   73,   74,   99,
    0,  216,    0,    0,   83,   84,   85,  249,    0,   83,
   84,   85,    0,    0,    0,    0,   73,   74,    0,   44,
    0,    0,  118,    0,   44,    0,    7,  118,   83,   84,
   85,    7,  109,  119,  120,  121,    0,    0,    0,  120,
  121,    0,    0,    0,    0,  122,   44,  240,    0,    6,
  242,  113,  243,    7,    0,  245,    5,    0,    0,    6,
    0,    9,   10,    7,    8,    0,    0,   13,    0,    0,
  110,    9,   10,    0,    0,   11,   12,   13,   14,  268,
    5,    0,   99,    6,    0,    0,    0,    7,    8,  259,
    5,    0,    0,    6,    0,    9,   10,    7,    8,   11,
   12,   13,   14,    0,    0,    9,   10,    0,  170,   11,
   12,   13,   14,    5,    0,   44,    6,    0,  118,    0,
    7,    8,    7,    0,   99,    0,    0,    0,    9,   10,
  120,  121,   11,   12,   13,   14,  122,    0,    0,    5,
    0,   44,    6,    0,  118,    0,    7,    8,    7,    0,
  119,    0,    0,    0,    9,   10,  120,  121,   11,   12,
   13,   14,  122,    0,  109,    0,    0,  109,    0,    0,
    0,  109,    0,  109,  109,    0,    0,    0,    0,  109,
  109,  109,  109,  113,    0,  109,  113,    0,    0,    0,
  113,    0,  113,  113,    0,    0,    0,    0,  113,  113,
  113,  113,  110,    0,  113,  110,    0,    0,    0,  110,
    0,  110,  110,    0,    0,    0,    0,  110,  110,  110,
  110,   44,    0,  110,    6,    0,    0,    0,    7,    0,
  119,   45,    0,    0,    0,    0,    9,   10,    0,   46,
   44,    0,   13,    6,    0,    0,    0,    7,    0,    0,
   45,    0,    0,    0,    0,    9,   10,    5,   46,    0,
  118,   13,    0,    0,    7,    8,  119,    0,    0,    0,
    0,    0,  120,  121,    0,    0,   11,   12,  122,   14,
   38,    0,    0,   38,    0,    0,    0,   38,   38,   38,
    5,    0,    0,    6,    0,   38,   38,    7,    8,   38,
   38,   38,   38,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,   44,    0,    0,    6,    0,    0,    0,
    7,    0,    0,   45,    0,    0,    0,    0,    9,   10,
    0,   46,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         13,
   41,   68,   13,   43,   41,   59,   68,  123,   10,   44,
   40,   41,   59,   40,  123,   41,   68,   58,  123,  123,
   40,   59,  125,  125,   40,   42,   68,    4,   41,   45,
  138,   44,  123,   43,  307,   45,  125,   35,   41,   43,
   43,   45,   45,  125,   41,   58,  108,   68,  161,  317,
   60,   61,   62,   40,   68,  122,   33,   68,   41,  126,
  122,   58,   45,  336,  126,   40,    2,   41,    4,  121,
   41,   45,  112,   23,  126,  343,  344,   45,   41,   15,
   97,   41,   40,    0,  126,   42,   36,   45,   59,   59,
   47,   59,   45,   91,  123,   41,   59,   33,   34,   59,
   41,  257,   60,   61,   62,  126,    0,   45,  122,  111,
   58,  122,  126,   59,   41,  126,   41,   44,   59,  121,
   41,  119,   41,   41,   41,   42,   43,  235,   45,    0,
   47,  244,  172,   41,   59,   71,  176,   58,   41,  179,
   58,  257,   59,   60,   61,   62,  268,   41,  146,   43,
   58,   45,  261,    0,   59,  260,  260,   58,   59,  262,
  263,  266,  266,  274,  266,   59,   60,   61,   62,  260,
   41,  274,   43,  286,   45,  266,    0,  266,  230,  177,
  262,  263,  234,  258,   43,   59,   45,   44,   59,   60,
   61,   62,  274,  268,   41,   44,   43,   59,   45,    0,
   59,  273,   41,  257,   43,   40,   45,  258,  125,  276,
  257,   41,   59,   60,   61,   62,  276,  277,   59,  257,
  257,  261,  259,   59,  276,   59,    0,   58,  230,  291,
  257,  125,  234,  268,  257,  276,  277,  257,   41,  259,
  307,  257,  258,  305,   44,  307,  276,  277,  257,    0,
  276,  277,   58,  305,  125,  317,  308,  297,   41,  269,
  270,  271,  302,  305,   41,  307,  328,  261,  335,  336,
  262,  263,    0,  258,  257,  258,  328,  291,  125,  257,
  291,  343,  344,  257,  258,  325,  328,  349,  350,  257,
  258,  305,   40,  307,  305,   40,  307,  349,  350,  257,
  258,  125,  125,  317,  257,  258,  317,  349,  350,    0,
  261,  269,  270,  271,  328,  262,  263,  328,  125,  257,
  258,   58,   59,   58,  125,  140,  141,  143,  144,  343,
  344,   58,  343,  344,   59,  349,  350,   59,  349,  350,
  257,   58,   58,  260,  123,  262,  263,  264,  265,  266,
  267,  125,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   41,  260,  274,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  125,  273,  260,
   58,  262,  263,  264,  265,  266,  267,  125,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,   10,
  257,  125,   44,  260,  123,  262,  263,  264,  265,  266,
  267,   59,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  125,    0,  260,   59,  262,  263,
  264,  265,  266,  267,   41,  125,   47,   59,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   40,    0,  260,
   41,  262,  263,  264,  265,  266,  267,  125,  123,  125,
   59,  272,  273,  274,  275,  276,  277,  278,  279,   44,
  261,   59,  125,  257,  274,  261,  260,   59,  262,  263,
  264,  265,  266,  267,   59,   44,    0,   59,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  125,  125,  260,
  111,  262,  263,  264,  265,  266,  267,   59,   59,    0,
  121,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  123,   41,  260,  220,  262,  263,  264,  265,  266,  267,
   44,  123,    0,   40,  272,  273,  274,  275,  276,  277,
  278,  279,  263,   59,   40,   59,  263,  262,   59,  246,
  125,  248,  272,   59,   59,   41,  257,   40,  125,  260,
   41,  263,   59,  264,  265,  266,   59,  257,   59,  180,
  260,  272,  273,  125,  264,  276,  277,  278,  279,  263,
  125,  123,  272,  273,  261,   59,   59,   59,  278,   41,
   59,  125,  257,  263,  205,  260,   41,  261,  295,  264,
   45,   41,  267,  263,   59,   45,  123,  272,  273,   59,
  275,  125,  125,  278,  263,   60,   61,   62,  262,  230,
   60,   61,   62,  234,   45,  125,  237,  125,   59,  257,
   58,  257,  260,    0,  125,  123,  264,  265,  266,   60,
   61,   62,  262,  123,  272,  273,  122,  126,  276,  277,
  278,  279,  122,  234,  265,  266,  353,  125,  122,  180,
  116,  286,   13,  257,   13,  257,  260,   13,  260,   69,
  264,  266,  264,   90,  266,  267,   -1,  235,  272,  273,
  272,  273,  257,  275,  278,  260,  278,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,   41,
   42,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
  123,   -1,  264,  265,  266,  257,   -1,   -1,  260,   -1,
  272,  273,  264,  265,  276,  277,  278,  279,   -1,   -1,
  272,  273,  123,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,  257,  125,  264,  260,   -1,   -1,   -1,
  264,  265,  266,  272,  273,   97,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,  125,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,  125,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,   -1,
   -1,  125,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,  258,  156,   -1,  158,  257,  258,  161,
   -1,  163,   -1,   -1,  269,  270,  271,  125,   -1,  269,
  270,  271,   -1,   -1,   -1,   -1,  257,  258,   -1,  257,
   -1,   -1,  260,   -1,  257,   -1,  264,  260,  269,  270,
  271,  264,  125,  266,  272,  273,   -1,   -1,   -1,  272,
  273,   -1,   -1,   -1,   -1,  278,  257,  209,   -1,  260,
  212,  125,  214,  264,   -1,  217,  257,   -1,   -1,  260,
   -1,  272,  273,  264,  265,   -1,   -1,  278,   -1,   -1,
  125,  272,  273,   -1,   -1,  276,  277,  278,  279,  241,
  257,   -1,  244,  260,   -1,   -1,   -1,  264,  265,  125,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,  276,
  277,  278,  279,  257,   -1,  257,  260,   -1,  260,   -1,
  264,  265,  264,   -1,  286,   -1,   -1,   -1,  272,  273,
  272,  273,  276,  277,  278,  279,  278,   -1,   -1,  257,
   -1,  257,  260,   -1,  260,   -1,  264,  265,  264,   -1,
  266,   -1,   -1,   -1,  272,  273,  272,  273,  276,  277,
  278,  279,  278,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,   -1,
  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,   -1,
  260,  278,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
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
"sentencia_declarativa : funcion_sin_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_con_return : encabezado_funcion '{' sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' sentencias_funcion sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' '}'",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : asignacion",
"sentencia_ejecutable_funcion : DEFER asignacion",
"sentencia_ejecutable_funcion : imprimir",
"sentencia_ejecutable_funcion : DEFER imprimir",
"sentencia_ejecutable_funcion : sentencia_when_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_when_con_return",
"sentencia_ejecutable_funcion : sentencia_do_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_do_con_return",
"sentencia_ejecutable_funcion : sentencia_seleccion_simple_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_seleccion_simple_con_return",
"sentencia_ejecutable_funcion : sentencia_seleccion_compuesta_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_seleccion_compuesta_con_return",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_ejecutable_funcion ENDIF ';'",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_return",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencias_when '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencias_when sentencia_return '}' ';'",
"sentencia_do_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"sentencia_do_con_return : etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"bloque_sentencias_ejecutables_do_con_return : sentencia_return",
"bloque_sentencias_ejecutables_do_con_return : sentencia_ejecutable_do",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do sentencia_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do '}'",
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
"parametro : tipo",
"$$1 :",
"parametro : $$1 ID",
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
"$$2 :",
"sentencia_when : WHEN '(' ')' THEN '{' sentencias_when '}' ';' $$2 WHEN '(' condicion ')' THEN '{' sentencias_when '}'",
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}'",
"sentencia_when : WHEN '(' condicion ')' '{' '}'",
"sentencias_when : sentencia",
"sentencias_when : sentencia sentencias_when",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"condicion : comparador expresion",
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
"invocacion_funcion : ID '(' ')'",
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

//#line 354 ".\gramatica.y"

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

		String path = new File(archivo_a_leer).getAbsolutePath().replaceAll(args[0],"");
        
        Output out = new Output(path);
        
        String printTs = ts.print();
        
        
        out.saveFile("codigo-lexico.txt", logger.getLexico());
		out.saveFile("codigo-sintetico.txt", logger.getSintactico());
		out.saveFile("tabla-de-simbolos.txt", printTs);
        
		System.out.println(printTs);
	}
}
//#line 812 "Parser.java"
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
//#line 17 ".\gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 18 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre del programa"); }
break;
case 3:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); }
break;
case 4:
//#line 20 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); }
break;
case 5:
//#line 21 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias del programa"); }
break;
case 15:
//#line 46 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 16:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 17:
//#line 48 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 49 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 21:
//#line 58 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 22:
//#line 59 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 23:
//#line 60 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias en la funcion"); }
break;
case 42:
//#line 94 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 43:
//#line 95 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 53:
//#line 117 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 54:
//#line 118 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 55:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 56:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 63:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 64:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 65:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 66:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 67:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 68:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 69:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 70:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 72:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 73:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 74:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 75:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 78:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 83:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 84:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 85:
//#line 172 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 86:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 87:
//#line 174 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 91:
//#line 184 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 92:
//#line 185 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 93:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 107:
//#line 209 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 108:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 109:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 110:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 111:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 112:
//#line 217 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 113:
//#line 218 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 116:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 117:
//#line 228 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 118:
//#line 229 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 119:
//#line 230 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 120:
//#line 231 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 126:
//#line 249 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 127:
//#line 250 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 128:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 129:
//#line 255 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 130:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 131:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 132:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 133:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 137:
//#line 269 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 138:
//#line 270 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 144:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 145:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 167:
//#line 332 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 168:
//#line 333 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 169:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 170:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 171:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 172:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 173:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 174:
//#line 339 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 175:
//#line 340 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 177:
//#line 345 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1257 "Parser.java"
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
