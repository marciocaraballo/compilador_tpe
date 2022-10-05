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
    4,    4,    4,    4,    6,    6,    6,    6,   11,   11,
    7,    8,   13,   13,   16,   16,   17,   17,   17,   17,
   17,   17,   17,   17,   14,   14,   21,   23,   23,   20,
   25,   25,   18,   26,   26,   19,   19,   28,   30,   30,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   15,   15,   15,   15,   15,   32,   32,   32,   35,   35,
   34,   34,   36,   34,    9,    9,    9,   37,   37,   38,
   38,   38,   38,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   44,   44,   44,   45,   45,   45,   45,
   45,   46,   46,   43,   43,   47,   47,   47,   47,   47,
   29,   48,   48,   31,   31,   39,   39,   39,   42,   42,
   49,   42,   27,   27,   40,   40,   40,   40,   40,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   50,   50,   50,   50,   24,   24,   22,   22,   52,   22,
   51,   51,   51,   51,   51,   51,   33,   33,   33,   53,
   53,   53,   54,   54,   54,   56,   56,   57,   57,   58,
   58,   41,   41,   41,   41,   41,   41,   41,   41,   41,
   55,   55,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    2,    1,    2,    1,    1,    1,    2,
    1,    2,    1,    2,    1,    2,   10,    1,    4,    8,
    1,    4,    9,    1,    2,    1,    3,    7,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    4,    3,    3,    4,    1,    3,    5,    1,    3,
    2,    1,    0,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    1,    2,    8,   10,    7,    7,    7,    7,
    7,    7,    9,    9,    9,    9,    9,    9,    9,    8,
    1,    3,    2,    2,    1,    2,    3,    2,    0,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    3,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  184,  183,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   84,   86,   88,   90,
   92,  104,    0,    0,    0,    0,    0,  181,    0,    0,
    0,    0,    0,    0,  162,  164,  165,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,  112,   95,
   96,    0,   85,   87,   89,   91,   93,    0,   77,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  117,    0,   19,    0,  182,    0,    0,    0,
  151,  152,  153,    0,    0,  154,  155,  156,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   97,    0,  102,
    0,  114,    0,   82,    0,   75,    0,   15,    0,    0,
    0,    0,    0,   27,   28,    0,    0,   23,    0,   25,
   29,   31,   33,   35,   46,    0,  105,    1,  116,  170,
  166,  171,    0,  168,    0,    0,    0,    0,    0,    0,
    0,    0,  160,  161,  177,  175,  178,    0,  176,    0,
  174,    0,    0,    0,    0,   71,    0,    0,   74,    0,
    0,    0,    0,  111,  101,    0,  113,  115,    0,    0,
   80,   79,    0,    0,    0,    0,    0,   49,    0,    0,
   30,   32,   34,   21,   22,   24,   26,   36,    0,    0,
  167,    0,    0,    0,    0,    0,    0,    0,    0,  173,
  172,    0,   56,    0,    0,   55,    0,    0,   54,    0,
    0,    0,    0,   98,    0,    0,    0,    0,    0,    0,
   63,    0,    0,    0,    0,   47,  169,  145,    0,  143,
  146,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   51,    0,   60,   59,    0,   58,    0,    0,
    0,    0,  108,  110,    0,  109,    0,   65,    0,    0,
    0,    0,    0,  142,    0,  129,    0,  127,    0,    0,
  132,    0,    0,    0,  130,    0,  128,   52,    0,   68,
  124,    0,    0,    0,  106,    0,   61,    0,   50,    0,
    0,    0,    0,  140,    0,    0,    0,  125,    0,    0,
    0,  121,    0,  120,    0,    0,    0,    0,    0,    0,
    0,  135,  133,  137,  138,    0,  136,  134,   70,    0,
  119,    0,    0,    0,   44,    0,    0,   48,    0,   41,
  126,    0,    0,    0,   38,    0,   40,    0,   45,    0,
    0,    0,    0,    0,   43,    0,    0,    0,   37,   42,
    0,   39,    0,    0,    0,  122,
};
final static short yydgoto[] = {                          3,
    4,   15,  269,   17,  213,   19,   20,   21,   22,   23,
   24,   25,  136,  137,  138,  139,  140,  141,  142,  143,
  144,   41,  327,  214,  328,  346,  270,  145,   26,  199,
  121,  111,   42,  112,  300,  113,   70,   71,   27,   28,
   29,   30,   31,   59,   60,   61,   32,   62,  340,  218,
   99,   43,   44,   45,   46,   47,  153,  154,
};
final static short yysindex[] = {                       -63,
    0,  975,    0,  -89,  -38,  -35,  -19,   -7,   -1,  660,
    0,    0,  459,  -40,  701,    0,    0,    0,    0,    0,
    0,    0,    8,   49,  -80,    5,    0,    0,    0,    0,
    0,    0,  754,  764,   93, -191,   75,    0, -183,   72,
  110,  -36,   98,   27,    0,    0,    0,  115,  180,    7,
  -25,  -21,   77, -134,  298,   85,  388,    0,    0,    0,
    0, -135,    0,    0,    0,    0,    0, -217,    0,  211,
  213,    0,    0,  261,    0,  274,    0,  942,   63,    0,
  787,    0,    0,  283,    0,   80,    0,   59,  -24,  109,
    0,    0,    0,   38,   38,    0,    0,    0,   38,   38,
   38,   38,  315,  319,   44,   45,  343,  -23,  348,  177,
  406,  409,  220,  427,  445,  236,  457,    0,   22,    0,
  892,    0,    2,    0,  242,    0,  251,    0,    4,   61,
  469,  573,  658,    0,    0,  385,  394,    0,  942,    0,
    0,    0,    0,    0,    0,  464,    0,    0,    0,    0,
    0,    0,  335,    0,  734,  734,  -87,  734,   27,   27,
  320,  320,    0,    0,    0,    0,    0,  465,    0,  474,
    0,  476,  124,   47,  124,    0,  485,  124,    0,  124,
  489,  422,  -83,    0,    0,  491,    0,    0,   86,  507,
    0,    0,   72,   88,  102,   77,  388,    0,  277,   67,
    0,    0,    0,    0,    0,    0,    0,    0,  280,   96,
    0,  732,    0,  920,  142,  149,  679,  151,  153,    0,
    0,  124,    0,  502,  124,    0,  124,  517,    0,  124,
  975,  439,  975,    0,  504,  307,  513,  -12,  515,  330,
    0,  534,  875,  536,   72,    0,    0,    0,  931,    0,
    0,  734,  518,  734,  520,  690,  523,  157,  734,  530,
  734,  531,    0,  124,    0,    0,  124,    0,  975,  466,
  975,  467,    0,    0,  542,    0,  349,    0,  546,  -46,
  478,   38,   -3,    0,  344,    0,  345,    0,  552,  351,
    0,  712,  558,  355,    0,  357,    0,    0,  582,    0,
    0,  569,  505,  570,    0,  614,    0,  524,    0,  608,
  592,  584,  591,    0,  597,  598,  378,    0,  599,  603,
  124,    0,  606,    0,  732,    0,  397,  405,  965,  611,
  631,    0,    0,    0,    0,  615,    0,    0,    0,  403,
    0,  789,  -73,  617,    0,  553, -102,    0,  732,    0,
    0,  637,  557,  732,    0,  424,    0,  630,    0,  789,
   38,    0,  431,  633,    0,  565,  652,  574,    0,    0,
  433,    0,  575,  975,  577,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  363,  139,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   11,    0,    0,  139,
    0,    0,    0,   37,    0,    0,    0,    0,    0,    0,
  443,  443,  139,  648,  812,  831,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  359,    0,  407,
  448,    0,    0,  521,    0,  544,    0,    0,    0,    0,
  707,    0,    0,  192,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -37,    0,
    0,    0,    0,    0,    0,    0,    0,  443,    0,   91,
    0,   53,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  139,    0,  487,    0,    0,    0,  139,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  120,  166,
  -29,  -13,    0,    0,    0,    0,    0,  216,    0,  239,
    0,    0,    0,    0,    0,    0,    0,  443,    0,    0,
    0,    0,    0,    0,    0,  856,    0,    0,  139,    0,
    0,    0,  139,    0,    0,  139,    0,    0,    0,  139,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  909,    0,    0,    0,    0,    0,    0,    0,
    0,  588,    0,    0,    0,    0,    0,  114,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -66,
    0,    0,    0,    0,  139,    0,    0,    0,  162,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  594,    0,    0,  443,    0,  -61,    0,
    0,    0,    0,    0,  263,    0,    0,    0,  -60,    0,
    0,  139,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  292,    0,    0,    0,    0,    0,  291,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  443,    0,    0,    0,    0,  188,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  321,    0,    0,    0,    0,
    0,  162,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  162,
  139,  198,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   57,  600,  -16,   -2,    0,    0,    0,    0,  602,
   66,    0,    0,    0,  512,    0,  579,  589,  595,  596,
  607,  -39,  387,    6,    0,    0, -176,  525,    9,    0,
  538,   16,   10, -175,  412,    0,  612,    0,   87,  103,
  113,  206,  229,  -30,    0,    0,   35,    0,    0,  484,
  698,    0,  368,  373,   83,    0,    0,  532,
};
final static int YYTABLESIZE=1254;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  228,  148,   40,   36,   94,   58,   95,   39,
  163,  147,   18,  117,  108,  109,  157,  172,   69,  114,
   50,  148,  323,   97,   98,   96,  122,  150,  277,  147,
   18,   18,   52,   33,  173,  212,  159,  311,   53,  233,
  124,  189,   78,  193,   84,  150,   39,  107,   39,  354,
  125,  163,  163,  163,   58,  163,  272,  163,   64,    2,
   34,  134,   79,  123,   62,   74,   75,  115,  101,  163,
  163,  163,  163,  102,   87,  135,  233,  159,   18,  159,
  185,  159,   39,  190,  168,  170,  146,  224,   76,   81,
  188,  299,  301,   66,  303,  159,  159,  159,  159,   63,
  194,   85,  167,  169,  225,   39,  245,   77,  161,  162,
   66,   39,   88,  147,   86,   64,   39,  116,   58,  157,
  151,   39,  134,  174,   39,   65,  235,  186,  239,   58,
   39,   72,   39,   35,   72,  163,  135,   39,  123,  195,
   39,  146,  241,  120,   94,  299,   95,  146,   72,  236,
   90,   83,  347,  238,   67,  103,  242,   97,   98,   96,
  157,  159,  157,  130,  157,  158,  122,    5,  152,   54,
    6,   67,    6,  217,    7,    8,    7,  232,  157,  157,
  157,  157,    9,   10,    9,   10,   11,   12,   13,   14,
   13,  118,  130,    1,   58,   64,   64,  375,  149,  149,
  149,   62,   62,  240,  123,  283,  158,   64,  158,  248,
  158,  251,  188,   62,  308,  180,   68,  249,   66,   63,
  104,   37,   38,  148,  158,  158,  158,  158,   18,   35,
   18,  147,   91,   92,   93,   64,  156,   48,  179,   49,
   58,   67,  310,  147,  157,   65,  251,  150,  156,   51,
   11,   12,   11,   12,   11,   12,  127,  156,   37,   38,
   37,   38,  107,  105,   74,  106,   18,  163,   18,  126,
  163,  163,  163,  163,  163,  163,  163,  163,  184,  163,
  163,  163,  163,  163,  163,  163,  163,  163,  163,  163,
  158,  131,  152,  159,   37,   38,  159,  159,  159,  159,
  159,  159,  159,  159,   36,  159,  159,  159,  159,  159,
  159,  159,  159,  159,  159,  159,  118,   37,   38,  155,
  139,  367,  248,   37,   38,   94,   18,   95,   37,   38,
  342,   69,  128,   37,   38,   10,  150,   38,   66,  251,
  180,  149,   37,   38,   37,   38,  248,  275,   69,   37,
   38,  248,  150,   38,  360,  119,  118,  251,   83,  363,
  251,   67,   94,  179,   95,  274,   91,   92,   93,  158,
  279,   18,   94,  165,   95,  211,  157,  166,  210,  157,
  157,  157,  157,  157,  157,  157,  157,  107,  157,  157,
  157,  157,  157,  157,  157,  157,  157,  157,  157,   11,
   12,  171,   83,  252,  253,  175,   76,  149,  149,  149,
  254,  255,  259,  260,  261,  262,  131,   83,  292,  293,
  111,   20,  158,  144,  144,  158,  158,  158,  158,  158,
  158,  158,  158,  176,  158,  158,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  139,  177,   78,  118,   38,
   41,  118,  178,  118,  118,  118,  118,  118,  118,   39,
   42,  159,  160,  118,  118,  118,  118,  118,  118,  118,
  118,  212,  180,  163,  164,  180,  179,  180,  180,  180,
  180,  180,  180,   83,  180,  181,   81,  180,  180,  180,
  180,  180,  180,  180,  180,  179,  182,  183,  179,  191,
  179,  179,  179,  179,  179,  179,   78,   68,  196,  204,
  179,  179,  179,  179,  179,  179,  179,  179,  205,  107,
   20,  209,  107,  220,  107,  107,  107,  107,  107,  107,
   81,   76,  221,  222,  107,  107,  107,  107,  107,  107,
  107,  107,  227,   16,  231,   81,  230,  237,  131,  234,
  244,  131,  132,  131,  131,  131,  131,  131,  131,  264,
  267,  271,  273,  131,  131,  131,  131,  131,  131,  131,
  131,  276,   78,  278,  280,  282,  286,  139,  288,   20,
  139,  291,  139,  139,  139,  139,  139,  139,  295,  297,
  302,  304,  139,  139,  139,  139,  139,  139,  139,  139,
  305,   16,  309,   16,  307,   54,  312,  313,    6,  306,
  314,   81,    7,  315,   73,   83,  318,  319,   83,  320,
    9,   10,   83,   83,   83,  321,   13,  322,  324,  323,
   83,   83,   16,   73,   83,   83,   83,   83,  215,  216,
  336,  219,  332,  198,   54,   20,  329,    6,  330,  333,
  206,    7,  110,  110,   55,  334,  335,  337,  343,    9,
   10,  338,   56,   76,  341,   13,   76,  344,   16,  348,
   76,   76,   76,  351,  352,  357,  361,  358,   76,   76,
   73,  362,   76,   76,   76,   76,  364,   54,  365,  370,
    6,  369,  371,  373,    7,  197,  130,  374,  372,   73,
  258,  376,    9,   10,   78,  111,    4,   78,   13,  110,
   53,   78,   78,   78,  212,   54,   57,  207,    6,   78,
   78,  201,    7,   78,   78,   78,   78,  202,  203,  356,
    9,   10,  339,  246,  243,  285,  325,  287,  192,  290,
  100,  247,  294,   81,  296,  208,   81,    0,    0,    0,
   81,   81,   81,  349,  281,    0,    0,    0,   81,   81,
    0,    0,   81,   81,   81,   81,    0,    0,    0,    0,
    0,    0,    0,    0,  223,  317,  226,   20,    0,  110,
   20,  229,   57,    0,   20,   20,   20,    0,    0,  258,
    0,    0,   20,   20,    0,    0,   20,   20,   20,   20,
   16,  212,    0,   16,    0,    0,    0,   16,   16,   16,
    0,    0,  212,    0,  258,   16,   16,  326,    0,   16,
   16,   16,   16,  263,    0,   72,  265,    0,  266,   54,
    0,  268,    6,    0,  212,    0,    7,    0,  130,   55,
  345,    0,  350,    0,    9,   10,    0,   56,   54,    0,
   13,    6,  331,  353,  355,    7,  212,    0,  359,    0,
    0,    0,    0,    9,   10,  298,    0,    0,  110,   13,
   54,  366,    0,    6,  368,  256,  257,    7,   80,  130,
    0,    0,    0,    0,    0,    9,   10,   54,   82,    0,
    6,   13,  256,  257,    7,    0,  130,    0,    0,    0,
    0,    0,    9,   10,    0,    0,    0,    0,   13,    0,
    0,  148,    0,  284,   54,    0,   54,  200,    0,    6,
    0,    7,  110,    7,    0,    0,   55,    0,    0,  131,
  132,    9,   10,    0,   56,   54,   99,   13,    6,    0,
  256,  257,    7,    0,    0,    0,   54,    0,    0,    6,
    9,   10,  289,    7,    0,  103,   13,    5,    0,    0,
    6,    9,   10,    0,    7,    8,    0,   13,   54,    0,
    0,    6,    9,   10,  316,    7,   11,   12,   13,   14,
  100,    0,    0,    9,   10,    0,    0,    0,   54,   13,
   54,    6,    0,    6,    0,    7,    0,    7,    0,  187,
    0,    0,    0,    9,   10,    9,   10,    0,    0,   13,
    5,   13,    0,    6,    0,    0,  187,    7,    8,    0,
    5,    0,    0,    6,    0,    9,   10,    7,    8,   11,
   12,   13,   14,  145,    0,    9,   10,    0,    0,   11,
   12,   13,   14,    5,  250,   54,    6,    0,    6,    0,
    7,    8,    7,    0,  130,  284,    0,    0,    9,   10,
    9,   10,   11,   12,   13,   14,   13,    0,   99,    0,
    0,   99,    0,    0,    0,   99,    0,   99,   99,    0,
    0,    0,    0,   99,   99,   99,   99,  103,    0,   99,
  103,    0,    0,    0,  103,    0,  103,  103,    0,    0,
    0,    0,  103,  103,  103,  103,    0,    0,  103,    0,
    0,    0,  100,    0,    0,  100,    0,    0,    0,  100,
    0,  100,  100,    0,    0,    0,    0,  100,  100,  100,
  100,   54,    0,  100,    6,    0,    0,    0,    7,    0,
  130,   55,    0,    0,    0,    0,    9,   10,   54,   56,
    0,    6,   13,    0,    0,    7,    0,    0,   55,    0,
    0,    0,    0,    9,   10,  145,   56,    0,  145,   13,
  141,  141,  145,    0,    0,    0,   54,    0,    0,    6,
  145,  145,    0,    7,    0,    0,  145,   54,    0,    0,
    6,    9,   10,    0,    7,    0,    0,   13,    5,    0,
    0,  129,    9,   10,    0,    7,    8,  130,   13,    0,
    0,    0,    0,  131,  132,    0,    0,   11,   12,  133,
   14,    5,    0,    0,    6,    0,    0,    0,    7,    8,
  130,    5,    0,    0,    6,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,  178,   41,   40,   44,   43,   10,   45,   45,
    0,   41,   15,   53,   40,   41,   41,   41,   59,   41,
   40,   59,  125,   60,   61,   62,   57,   41,   41,   59,
   33,   34,   40,  123,   58,  123,    0,   41,   40,  123,
  258,   40,  123,   40,   35,   59,   45,   41,   45,  123,
  268,   41,   42,   43,   57,   45,  233,   47,  125,  123,
    4,   78,   58,  125,  125,  257,   59,   52,   42,   59,
   60,   61,   62,   47,  258,   78,  123,   41,   81,   43,
   59,   45,   45,  123,   41,   41,   78,   41,   23,   33,
  121,  267,  269,   41,  271,   59,   60,   61,   62,   13,
   40,   36,   59,   59,   58,   45,   40,   59,   99,  100,
   58,   45,   41,   79,   40,   13,   45,   41,  121,    0,
   41,   45,  139,  108,   45,   13,   41,  119,   41,  132,
   45,   41,   45,  268,   44,  125,  139,   45,  274,  130,
   45,  133,   41,   59,   43,  321,   45,  139,   58,  189,
   41,   59,  329,  193,   41,   41,  196,   60,   61,   62,
   41,  125,   43,  266,   45,    0,  197,  257,   86,  257,
  260,   58,  260,  261,  264,  265,  264,  261,   59,   60,
   61,   62,  272,  273,  272,  273,  276,  277,  278,  279,
  278,    0,  266,  257,  197,  262,  263,  374,   60,   61,
   62,  262,  263,  194,  266,  245,   41,  274,   43,  212,
   45,  214,  243,  274,  261,    0,  257,  212,   13,  133,
   41,  257,  258,  261,   59,   60,   61,   62,  231,  268,
  233,  261,  269,  270,  271,  133,  261,  257,    0,  259,
  243,   13,  282,  209,  125,  133,  249,  261,  261,  257,
  276,  277,  276,  277,  276,  277,   44,  261,  257,  258,
  257,  258,    0,  257,  257,  259,  269,  257,  271,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  257,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  125,    0,  210,  257,  257,  258,  260,  261,  262,  263,
  264,  265,  266,  267,   44,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  125,  257,  258,  261,
    0,  361,  325,  257,  258,   43,  329,   45,  257,  258,
  325,   41,   59,  257,  258,  273,  257,  258,  133,  342,
  125,   59,  257,  258,  257,  258,  349,   41,   58,  257,
  258,  354,  257,  258,  349,   58,   59,  360,    0,  354,
  363,  133,   43,  125,   45,   59,  269,  270,  271,  261,
   41,  374,   43,   59,   45,   41,  257,   59,   44,  260,
  261,  262,  263,  264,  265,  266,  267,  125,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  276,
  277,   59,   44,  262,  263,   58,    0,  269,  270,  271,
  262,  263,  262,  263,  262,  263,  125,   59,  262,  263,
   58,   59,  257,  262,  263,  260,  261,  262,  263,  264,
  265,  266,  267,  257,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  125,   41,    0,  257,  262,
  263,  260,   44,  262,  263,  264,  265,  266,  267,  262,
  263,   94,   95,  272,  273,  274,  275,  276,  277,  278,
  279,  123,  257,  101,  102,  260,  257,  262,  263,  264,
  265,  266,  267,  125,   58,   41,    0,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  261,   41,  260,  258,
  262,  263,  264,  265,  266,  267,   59,  257,   40,  125,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  257,
    0,   58,  260,   59,  262,  263,  264,  265,  266,  267,
   44,  125,   59,   58,  272,  273,  274,  275,  276,  277,
  278,  279,   58,    0,  123,   59,   58,   41,  257,   59,
  274,  260,  273,  262,  263,  264,  265,  266,  267,   58,
   44,  123,   59,  272,  273,  274,  275,  276,  277,  278,
  279,   59,  125,   59,   41,   40,   59,  257,   59,   59,
  260,   59,  262,  263,  264,  265,  266,  267,   59,   59,
  125,  125,  272,  273,  274,  275,  276,  277,  278,  279,
   59,    2,  125,    4,   59,  257,  263,  263,  260,  261,
   59,  125,  264,  263,   15,  257,   59,  263,  260,  263,
  272,  273,  264,  265,  266,   44,  278,   59,   59,  125,
  272,  273,   33,   34,  276,  277,  278,  279,  155,  156,
  263,  158,   59,  132,  257,  125,  123,  260,   41,   59,
  139,  264,   51,   52,  267,   59,   59,   59,  262,  272,
  273,   59,  275,  257,   59,  278,  260,  263,  125,   59,
  264,  265,  266,   59,  272,   59,   40,  125,  272,  273,
   81,  125,  276,  277,  278,  279,  263,  257,   59,  125,
  260,   59,   41,  261,  264,  123,  266,  123,  125,  257,
  217,  125,  272,  273,  257,   58,    0,  260,  278,  108,
  123,  264,  265,  266,  123,  257,  123,  139,  260,  272,
  273,  133,  264,  276,  277,  278,  279,  133,  133,  343,
  272,  273,  321,  209,  197,  252,  123,  254,  127,  256,
   43,  210,  259,  257,  261,  139,  260,   -1,   -1,   -1,
  264,  265,  266,  123,  243,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  173,  292,  175,  257,   -1,  178,
  260,  180,  123,   -1,  264,  265,  266,   -1,   -1,  306,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,  123,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
   -1,   -1,  123,   -1,  331,  272,  273,  306,   -1,  276,
  277,  278,  279,  222,   -1,  125,  225,   -1,  227,  257,
   -1,  230,  260,   -1,  123,   -1,  264,   -1,  266,  267,
  329,   -1,  331,   -1,  272,  273,   -1,  275,  257,   -1,
  278,  260,  261,  342,  343,  264,  123,   -1,  347,   -1,
   -1,   -1,   -1,  272,  273,  264,   -1,   -1,  267,  278,
  257,  360,   -1,  260,  363,  262,  263,  264,  125,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,  257,  125,   -1,
  260,  278,  262,  263,  264,   -1,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,
   -1,  125,   -1,  125,  257,   -1,  257,  260,   -1,  260,
   -1,  264,  321,  264,   -1,   -1,  267,   -1,   -1,  272,
  273,  272,  273,   -1,  275,  257,  125,  278,  260,   -1,
  262,  263,  264,   -1,   -1,   -1,  257,   -1,   -1,  260,
  272,  273,  263,  264,   -1,  125,  278,  257,   -1,   -1,
  260,  272,  273,   -1,  264,  265,   -1,  278,  257,   -1,
   -1,  260,  272,  273,  263,  264,  276,  277,  278,  279,
  125,   -1,   -1,  272,  273,   -1,   -1,   -1,  257,  278,
  257,  260,   -1,  260,   -1,  264,   -1,  264,   -1,  125,
   -1,   -1,   -1,  272,  273,  272,  273,   -1,   -1,  278,
  257,  278,   -1,  260,   -1,   -1,  125,  264,  265,   -1,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,  125,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,  125,  257,  260,   -1,  260,   -1,
  264,  265,  264,   -1,  266,  125,   -1,   -1,  272,  273,
  272,  273,  276,  277,  278,  279,  278,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,
  262,  263,  264,   -1,   -1,   -1,  257,   -1,   -1,  260,
  272,  273,   -1,  264,   -1,   -1,  278,  257,   -1,   -1,
  260,  272,  273,   -1,  264,   -1,   -1,  278,  257,   -1,
   -1,  260,  272,  273,   -1,  264,  265,  266,  278,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
  266,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,
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
"funcion_con_return : encabezado_funcion '{' cuerpo_funcion_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' cuerpo_funcion_sin_return '}'",
"cuerpo_funcion_con_return : sentencia_return",
"cuerpo_funcion_con_return : sentencias_funcion_con_return sentencia_return",
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
"sentencia_funcion_con_return : sentencia_declarativa",
"sentencia_funcion_con_return : sentencia_ejecutable",
"sentencia_funcion_con_return : sentencia_when_con_return",
"sentencia_funcion_con_return : DEFER sentencia_when_con_return",
"sentencia_funcion_con_return : sentencia_do_con_return",
"sentencia_funcion_con_return : DEFER sentencia_do_con_return",
"sentencia_funcion_con_return : sentencia_seleccion_simple_con_return",
"sentencia_funcion_con_return : DEFER sentencia_seleccion_simple_con_return",
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' sentencias_ejecutables sentencia_return '}'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_simple_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_simple_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_simple_con_return : '{' sentencias_ejecutables sentencia_return '}'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : sentencias_when sentencia_return",
"sentencia_do_con_return : sentencia_do_simple_con_return",
"sentencia_do_con_return : etiqueta ':' sentencia_do_simple_con_return",
"sentencia_do_simple_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"bloque_sentencias_ejecutables_do_con_return : sentencia_return",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do sentencia_return '}'",
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
"sentencias_when : sentencia",
"sentencias_when : sentencia sentencias_when",
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
"$$3 :",
"condicion : $$3 comparador expresion",
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

//#line 366 ".\gramatica.y"

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
//#line 828 "Parser.java"
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
case 15:
//#line 45 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 16:
//#line 46 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 17:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 48 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 21:
//#line 57 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 22:
//#line 61 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 43:
//#line 109 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 48:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 53:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 54:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 55:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 56:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 57:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 58:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 59:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 60:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 62:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 63:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 64:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 65:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 68:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 73:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 74:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 75:
//#line 170 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 76:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 77:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 81:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 82:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 83:
//#line 184 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 97:
//#line 207 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 98:
//#line 208 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 99:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 101:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 102:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 103:
//#line 216 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 106:
//#line 225 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 107:
//#line 226 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 108:
//#line 227 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 109:
//#line 228 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 110:
//#line 229 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 117:
//#line 248 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 118:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 119:
//#line 253 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 120:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 121:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 122:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 125:
//#line 265 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 126:
//#line 266 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 127:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 129:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 130:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 131:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 132:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 133:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 137:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 138:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 139:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 140:
//#line 280 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 143:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 144:
//#line 287 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 149:
//#line 297 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 150:
//#line 298 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 172:
//#line 344 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 345 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 174:
//#line 346 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 175:
//#line 347 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 176:
//#line 348 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 177:
//#line 349 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 178:
//#line 350 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 179:
//#line 351 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 352 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 182:
//#line 357 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1309 "Parser.java"
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
