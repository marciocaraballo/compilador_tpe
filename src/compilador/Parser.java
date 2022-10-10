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
   14,    7,   15,   15,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,    8,   21,   21,   20,   19,   17,
   24,   24,   18,   18,   26,   26,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   13,   13,   13,   13,
   13,   29,   29,   29,   32,   32,   31,   31,   33,   31,
    9,    9,    9,   34,   34,   35,   35,   35,   35,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,   41,
   41,   41,   42,   42,   42,   42,   42,   43,   43,   40,
   40,   44,   44,   44,   44,   44,   27,   45,   45,   28,
   28,   36,   36,   36,   39,   39,   46,   39,   25,   25,
   37,   37,   37,   37,   37,   37,   37,   37,   37,   37,
   37,   37,   37,   37,   37,   37,   47,   47,   47,   47,
   23,   23,   22,   22,   49,   22,   48,   48,   48,   48,
   48,   48,   30,   30,   30,   50,   50,   50,   51,   51,
   51,   53,   53,   54,   54,   55,   55,   38,   38,   38,
   38,   38,   38,   38,   38,   38,   52,   52,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    0,   10,    1,    2,    1,    1,    1,    2,    1,    2,
    1,    2,    1,    2,    4,    1,    2,   52,   19,    9,
    1,    2,    7,    9,    1,    4,    6,    7,    5,    5,
    5,    5,    6,    6,    6,    6,    5,    4,    3,    3,
    4,    1,    3,    5,    1,    3,    2,    1,    0,    2,
    3,    2,    2,    1,    3,    3,    2,    2,    1,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    1,    1,    2,    4,    1,    3,    3,    2,    1,    1,
    3,    7,    6,    6,    6,    6,    1,    1,    3,    1,
    2,    4,    3,    3,    9,    8,    0,   17,    1,    2,
    8,   10,    7,    7,    7,    7,    7,    7,    9,    9,
    9,    9,    9,    9,    9,    8,    1,    3,    2,    2,
    1,    2,    3,    2,    0,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    3,    4,    1,    3,    1,    1,    5,    5,    4,
    4,    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  180,  179,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   80,   82,   84,   86,
   88,  100,    0,    0,    0,    0,    0,  177,    0,    0,
    0,    0,    0,    0,  158,  160,  161,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   90,  108,   91,
   92,    0,   81,   83,   85,   87,   89,    0,   73,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  113,    0,   19,    0,  178,    0,    0,    0,
  147,  148,  149,    0,    0,  150,  151,  152,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,   98,
    0,  110,    0,   78,    0,   71,    0,   15,    0,    0,
    0,    0,    0,   25,   26,    0,    0,   23,   27,   29,
   31,    0,    0,    0,  101,    1,  112,  166,  162,  167,
    0,  164,    0,    0,    0,    0,    0,    0,    0,    0,
  156,  157,  173,  171,  174,    0,  172,    0,  170,    0,
    0,    0,    0,   67,    0,    0,   70,    0,    0,    0,
    0,  107,   97,    0,  109,  111,    0,    0,   76,   75,
    0,    0,    0,    0,    0,   45,    0,   28,   30,   32,
   34,   21,   24,    0,   35,    0,    0,  163,    0,    0,
    0,    0,    0,    0,    0,    0,  169,  168,    0,   52,
    0,    0,   51,    0,    0,   50,    0,    0,    0,    0,
   94,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,  165,  141,    0,  139,  142,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,   56,   55,    0,   54,    0,    0,    0,    0,  104,
  106,    0,  105,    0,   61,    0,    0,    0,    0,    0,
    0,  138,    0,  125,    0,  123,    0,    0,  128,    0,
    0,    0,  126,    0,  124,   48,    0,   64,  120,    0,
    0,    0,  102,    0,   57,    0,   46,    0,    0,    0,
    0,    0,  136,    0,    0,    0,  121,    0,    0,    0,
  117,    0,  116,    0,    0,    0,    0,   33,    0,  131,
  129,  133,  134,    0,  132,  130,   66,    0,  115,    0,
    0,   41,    0,    0,   43,    0,    0,  122,    0,    0,
    0,    0,   42,   22,    0,    0,    0,    0,   40,   44,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  118,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   38,
};
final static short yydgoto[] = {                          3,
    4,   15,  266,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  196,  242,  137,  138,  139,  140,  141,  328,
  143,   41,  211,  343,  267,  197,   26,  121,  111,   42,
  112,  298,  113,   70,   71,   27,   28,   29,   30,   31,
   59,   60,   61,   32,   62,  338,  215,   99,   43,   44,
   45,   46,   47,  151,  152,
};
final static short yysindex[] = {                      -104,
    0, 1003,    0,  498,  -29,   24,   16,  -23,   33,  633,
    0,    0,  430,  -37,  693,    0,    0,    0,    0,    0,
    0,    0,   -6,   18,  -42,   52,    0,    0,    0,    0,
    0,    0,  718,  742,   46, -159,   85,    0, -129,   58,
   99,  -10,  385,   54,    0,    0,    0,  104,  109,   17,
  -31,  -20,   61, -121,  127,  111,  114,    0,    0,    0,
    0,  -83,    0,    0,    0,    0,    0, -124,    0,  130,
  162,    0,    0,  174,    0,  163,    0,  953,  -48,    0,
  752,    0,    0,   29,    0,   63,    0,    4,  -38,    8,
    0,    0,    0,   77,   77,    0,    0,    0,   77,   77,
   77,   77,  219,  226,  -15,   34,  242,  -34,  248,   81,
  286,  297,   87,  293,  324,   93,  326,    0,    1,    0,
  897,    0,   26,    0,  125,    0,  112,    0,   40,   42,
  332,  616,  716,    0,    0,  260, 1013,    0,    0,    0,
    0,    0,  265,  348,    0,    0,    0,    0,    0,    0,
  124,    0,  508,  508,  573,  508,   54,   54,   -3,   -3,
    0,    0,    0,    0,    0,  351,    0,  363,    0,  378,
 -182,   68, -182,    0,  390, -182,    0, -182,  425,  422,
  -86,    0,    0,  427,    0,    0,   73,  505,    0,    0,
   58,   75,  159,   61,  114,    0,  273,    0,    0,    0,
    0,    0,    0,    0,    0,  275,  106,    0,  131,    0,
  778,  -51,  -33,  527,  -30,  195,    0,    0, -182,    0,
  499, -182,    0, -182,  514,    0, -182, 1003,  436, 1003,
    0,  502,   48,  503,  -14,  506,  251,    0,  525,  880,
  528,  302,  616,    0,    0,  933,    0,    0,  508,  510,
  508,  512,  650,  513,  199,  508,  515,  508,  522,    0,
 -182,    0,    0, -182,    0, 1003,  458, 1003,  469,    0,
    0,  536,    0,  660,    0,  539,  -85,  494,   77,  497,
  349,    0,  361,    0,  362,    0,  570,  375,    0,  683,
  581,  388,    0,  396,    0,    0,  597,    0,    0,  594,
  535,  602,    0, -100,    0,  541,    0,  625, 1013,  627,
  609,  613,    0,  614,  615,  412,    0,  617,  620, -182,
    0,  621,    0,  209,  980,  626,  953,    0,   77,    0,
    0,    0,    0,  630,    0,    0,    0,  414,    0,  431,
  632,    0,  568, -107,    0,  579,  657,    0,  666,  446,
  450,  656,    0,    0,  658,   77,  661,  681,    0,    0,
  685,  462,   77,  466,  688,  692,  606,   77,  482, 1003,
  703,  623,  622,  489,  131,    0,  628,  488,  131,  634,
  488,  486,  642,  698,  516,    0,  431,  519,  720,  532,
  745,   77,  747,  533,  431,  531,  673,  131,  488,  672,
  544,  749,  549,  761,   77,  771,  552,  696,  131,  488,
  697,  559,  701,  131,  488,  700,  569,  767,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  204,  471,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  154,    0,    0,  471,
    0,    0,    0,  227,    0,    0,    0,    0,    0,    0,
  574,  574,  471,  780,  823,  842,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  538,    0,  405,
  459,    0,    0,  593,    0,  435,    0,    0,    0,    0,
  844,    0,    0,   83,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -25,    0,
    0,    0,    0,    0,    0,    0,    0,  574,    0,   98,
    0,   70,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  471,    0,  563,    0,    0,    0,  471,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  776,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  250,  339,  -13,  -12,
    0,    0,    0,    0,    0,  177,    0,  203,    0,    0,
    0,    0,    0,    0,    0,  574,    0,    0,    0,    0,
    0,    0,    0,  861,    0,    0,  471,    0,    0,    0,
  471,    0,    0,  471,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  800,    0,    0,    0,    0,    0,  914,
    0,    0,    0,    0,    0,    0,    0,    0,  724,    0,
    0,    0,    0,    0,   74,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -82,    0,    0,    0,
    0,    0,    0,    0,    0,  246,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  725,    0,    0,  574,    0,  -95,    0,    0,    0,    0,
    0,  277,    0,    0,    0,  211,    0,    0,  471,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  313,    0,    0,    0,    0,    0,  100,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  574,
    0,    0,    0,    0,    0,    0,    0,    0,  471,    0,
    0,    0,    0,  370,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  471,    0,    0,    0,    0,
    0,    0,  471,    0,    0,    0,    0,  471,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  471,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  471,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   53,  624,  -72,   -2,    0,    0,    0,    0,  -40,
  101,  607,  -73,    0,  543, -117,  721,  722,  723,  -16,
    0,  -39,  355,    0, -176,  618,  -74,  665,   15,   13,
 -137,  542,    0,  736,    0,   28,   36,   57,   65,   84,
  -21,    0,    0,  -18,    0,    0,  951,  821,    0,  299,
  434,  -61,    0,    0,  667,
};
final static int YYTABLESIZE=1292;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  155,  144,  136,  134,  170,   58,  108,  109,
  110,  110,   18,  117,   36,  144,   52,  322,    2,  203,
  114,   69,  209,  171,  150,  166,  274,  143,  146,  119,
   18,   18,   94,  144,   95,  122,  230,  230,  225,   94,
   63,   95,   60,  165,  184,  143,  146,   84,   64,   97,
   98,   96,   75,  269,   58,   50,   34,  107,  144,  183,
  145,  142,  144,   40,  134,  187,  115,  110,   39,   65,
   39,   94,   53,   95,  168,  135,   77,   66,   18,  191,
   78,  192,  114,  188,   39,   81,   39,  147,  272,  299,
   39,  301,  167,   11,   12,  101,   67,   74,   88,  186,
  102,  116,   39,  149,   83,   39,  271,   39,  221,   79,
   62,  159,  160,  232,   63,  236,  201,   39,   58,   39,
  204,   39,  172,   76,   86,  222,  297,   62,   87,   58,
  220,   63,  223,  124,  135,  110,   85,  226,   68,   90,
   65,   68,  193,  125,  103,  150,   35,  233,  344,  104,
   39,  235,    1,  159,  239,   68,   54,   65,  130,    6,
   63,  253,  254,    7,  208,  130,  278,  207,   64,  120,
  119,    9,   10,  122,  229,  306,  176,   13,  260,   60,
   60,  262,  297,  263,  119,  118,  265,  145,  126,   65,
  123,   60,   58,  373,  159,  159,  159,   66,  159,  238,
  159,   94,  175,   95,  237,  127,  245,  114,  248,  203,
  249,  250,  159,  159,  159,  159,   67,   36,  186,   68,
  296,  128,  154,  110,   10,   18,  155,   18,  251,  252,
  324,  256,  257,   51,  144,  144,  134,   58,   35,  308,
   58,   11,   12,  248,   11,   12,  154,  143,  146,  153,
   74,  342,  144,  346,  134,   11,   12,  182,   91,   92,
   93,  107,   20,   18,  153,   18,  350,  155,  156,  155,
  353,  155,   48,  105,   49,  106,  103,  163,  159,  110,
   37,   38,   37,   38,  164,  155,  155,  155,  155,  347,
  153,  276,  153,   94,  153,   95,   37,   38,   37,   38,
  169,  176,   37,   38,  380,  173,  135,  383,  153,  153,
  153,  153,  127,  388,   37,   38,  361,   37,   38,  148,
   38,  396,   18,  366,  135,  400,  175,  175,  371,   37,
   38,   37,   38,   37,   38,   58,  411,  174,  154,  114,
  176,  416,  114,  177,  114,  114,  114,  114,  114,  114,
  178,  155,  393,  180,  114,  114,  114,  114,  114,  114,
  114,  114,  148,   38,  179,  406,  181,   18,   68,  135,
   54,  194,  245,    6,  153,  248,  245,    7,  248,  154,
   55,  154,  189,  154,  202,    9,   10,   54,   56,  205,
    6,   13,  157,  158,    7,  245,  248,  154,  154,  154,
  154,  103,    9,   10,   72,  206,  245,  248,   13,  217,
  159,  245,  248,  159,  159,  159,  159,  159,  159,  159,
  159,  218,  159,  159,  159,  159,  159,  159,  159,  159,
  159,  159,  159,  176,   16,  219,  176,  127,  176,  176,
  176,  176,  176,  176,   97,   98,   96,  224,  176,  176,
  176,  176,  176,  176,  176,  176,  258,  259,   74,  175,
  290,  291,  175,  154,  175,  175,  175,  175,  175,  175,
  340,  341,   58,   58,  175,  175,  175,  175,  175,  175,
  175,  175,  227,  155,   58,  231,  155,  155,  155,  155,
  155,  155,  155,  155,  135,  155,  155,  155,  155,  155,
  155,  155,  155,  155,  155,  155,  153,  140,  140,  153,
  153,  153,  153,  153,  153,  153,  153,   74,  153,  153,
  153,  153,  153,  153,  153,  153,  153,  153,  153,   72,
  145,  145,  145,  103,  161,  162,  103,   79,  103,  103,
  103,  103,  103,  103,  228,  234,  241,  243,  103,  103,
  103,  103,  103,  103,  103,  103,  261,  264,  268,   16,
  270,  273,   77,  246,  275,  277,    8,  279,  284,  127,
  286,  289,  127,  293,  127,  127,  127,  127,  127,  127,
  295,   79,  300,   74,  127,  127,  127,  127,  127,  127,
  127,  127,   20,  302,  303,  154,   79,  305,  154,  154,
  154,  154,  154,  154,  154,  154,   77,  154,  154,  154,
  154,  154,  154,  154,  154,  154,  154,  154,  307,  309,
   33,   77,  310,  311,  312,   16,  135,   16,  313,  135,
  209,  135,  135,  135,  135,  135,  135,  314,   73,  317,
  320,  135,  135,  135,  135,  135,  135,  135,  135,  209,
  318,   20,  321,   91,   92,   93,   16,   73,  319,  322,
  323,   72,   79,  325,   72,  326,  329,  330,   72,   72,
   72,  331,  332,  333,  334,  335,   72,   72,  336,  339,
   72,   72,   72,   72,  345,  349,   54,   77,  348,    6,
  351,   16,  352,    7,   16,  209,  130,  355,   16,   16,
   16,    9,   10,  354,   73,  356,   16,   16,  357,  358,
   16,   16,   16,   16,  359,   74,  360,   20,   74,  362,
  363,  365,   74,   74,   74,  364,  367,  368,  370,  378,
   74,   74,  369,  381,   74,   74,   74,   74,  195,  145,
  145,  145,  372,  374,   54,  375,  376,    6,  384,  377,
  379,    7,  399,  130,    5,   57,  386,    6,  382,    9,
   10,    7,    8,  410,   54,   13,  385,    6,  415,    9,
   10,    7,  209,   11,   12,   13,   14,  387,  390,    9,
   10,  389,  209,   54,  392,   13,    6,  394,  253,  254,
    7,  391,  397,  395,   79,  398,  401,   79,    9,   10,
  405,   79,   79,   79,   13,  209,  402,  403,  404,   79,
   79,  407,  408,   79,   79,   79,   79,   72,  409,   77,
  413,  412,   77,  414,  417,  419,   77,   77,   77,   54,
   69,  418,    6,  214,   77,   77,    7,  107,   77,   77,
   77,   77,   80,    4,    9,   10,   49,   53,  280,   20,
   13,  327,   20,  198,  199,  200,   20,   20,   20,  240,
  281,  337,  190,  100,   20,   20,   82,    0,   20,   20,
   20,   20,   54,  244,    0,    6,  146,    0,    0,    7,
    0,  130,   55,    0,    0,    0,    0,    9,   10,   54,
   56,    0,    6,   13,    0,    0,    7,    0,    0,   55,
   36,    0,  247,    0,    9,   10,   54,   56,    0,    6,
   13,    0,  287,    7,    0,    0,   54,    0,    0,    6,
  304,    9,   10,    7,   37,    0,    0,   13,    0,    0,
    0,    9,   10,    0,    0,    0,    0,   13,    0,   54,
    0,    0,    6,    0,    0,  315,    7,   95,    0,    5,
    0,    0,    6,    0,    9,   10,    7,    8,    0,    0,
   13,    0,    0,    0,    9,   10,   99,    0,   11,   12,
   13,   14,   54,    0,    5,  129,    0,    6,    0,    7,
    0,    7,    8,    0,    0,   96,    0,  131,  132,    9,
   10,    0,    0,   11,   12,   13,   14,    0,    5,    0,
    0,    6,    0,    0,  185,    7,    8,    0,    5,    0,
    0,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,  185,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   33,    0,   54,   33,    0,    6,  141,   33,
   33,    7,    0,    0,    0,    0,    0,   33,   33,    9,
   10,   33,   33,   33,   33,   13,   33,  282,    0,   33,
    0,    0,    0,   33,   33,    0,    0,    0,    0,    0,
    0,   33,   33,    0,    0,   33,   33,   33,   33,   95,
    0,    0,   95,    0,    0,    0,   95,    0,   95,   95,
    0,    0,    0,    0,   95,   95,   95,   95,   99,    0,
   95,   99,    0,  212,  213,   99,  216,   99,   99,    0,
    0,    0,    0,   99,   99,   99,   99,   96,    0,   99,
   96,    0,    0,    0,   96,    0,   96,   96,    0,    0,
    0,    0,   96,   96,   96,   96,   54,    0,   96,    6,
    0,    0,    0,    7,    0,  130,   55,    0,    0,    0,
    0,    9,   10,   54,   56,    0,    6,   13,    0,    0,
    7,    0,    0,   55,  255,    0,    0,    0,    9,   10,
  141,   56,    0,  141,   13,  137,  137,  141,    0,    0,
    0,    0,    0,    0,    0,  141,  141,    0,    0,   54,
    0,  141,    6,    0,    0,    0,    7,    0,    0,  283,
    0,  285,    0,  288,    9,   10,  292,    0,  294,    5,
   13,    0,  129,    0,    0,    0,    7,    8,  130,    0,
    0,    0,    0,    0,  131,  132,    0,    0,   11,   12,
  133,   14,    0,    0,    0,    0,    5,    0,    0,    6,
  316,    0,    0,    7,    8,  130,    0,    0,    0,    0,
    0,    9,   10,    0,  255,   11,   12,   13,   14,    5,
    0,    0,    6,    0,    0,    0,    7,    8,    0,    5,
    0,    0,  129,    0,    9,   10,    7,    8,   11,   12,
   13,   14,    0,    0,  131,  132,    0,    0,   11,   12,
  133,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   41,   78,   78,   78,   41,   10,   40,   41,
   51,   52,   15,   53,   44,   41,   40,  125,  123,  137,
   41,   59,  123,   58,   86,   41,   41,   41,   41,  125,
   33,   34,   43,   59,   45,   57,  123,  123,  176,   43,
   13,   45,  125,   59,  119,   59,   59,   35,   13,   60,
   61,   62,   59,  230,   57,   40,    4,   41,  133,   59,
   79,   78,  137,   40,  137,   40,   52,  108,   45,   13,
   45,   43,   40,   45,   41,   78,   59,   13,   81,   40,
  123,   40,    0,  123,   45,   33,   45,   59,   41,  266,
   45,  268,   59,  276,  277,   42,   13,  257,   41,  121,
   47,   41,   45,   41,   59,   45,   59,   45,   41,   58,
   41,   99,  100,   41,   41,   41,  133,   45,  121,   45,
  137,   45,  108,   23,   40,   58,  264,   58,  258,  132,
  171,   58,  173,  258,  137,  176,   36,  178,   41,   41,
   41,   44,  130,  268,   41,  207,  268,  187,  325,   41,
   45,  191,  257,    0,  194,   58,  257,   58,  266,  260,
  133,  262,  263,  264,   41,  266,  240,   44,  133,   59,
  266,  272,  273,  195,  261,  261,    0,  278,  219,  262,
  263,  222,  320,  224,   58,   59,  227,  206,   59,  133,
  274,  274,  195,  370,   41,   42,   43,  133,   45,   41,
   47,   43,    0,   45,  192,   44,  209,  125,  211,  327,
  262,  263,   59,   60,   61,   62,  133,   44,  240,  257,
  261,   59,  261,  264,  273,  228,    0,  230,  262,  263,
  304,  262,  263,  257,  309,  261,  309,  240,  268,  279,
  243,  276,  277,  246,  276,  277,  261,  261,  261,    0,
  257,  325,  327,  327,  327,  276,  277,  257,  269,  270,
  271,   58,   59,  266,  261,  268,  340,   41,  261,   43,
  344,   45,  257,  257,  259,  259,    0,   59,  125,  320,
  257,  258,  257,  258,   59,   59,   60,   61,   62,  329,
   41,   41,   43,   43,   45,   45,  257,  258,  257,  258,
   59,  125,  257,  258,  378,   58,  309,  381,   59,   60,
   61,   62,    0,  387,  257,  258,  356,  257,  258,  257,
  258,  395,  325,  363,  327,  399,   41,  125,  368,  257,
  258,  257,  258,  257,  258,  125,  410,  257,    0,  257,
   44,  415,  260,  257,  262,  263,  264,  265,  266,  267,
   58,  125,  392,  261,  272,  273,  274,  275,  276,  277,
  278,  279,  257,  258,   41,  405,   41,  370,  257,    0,
  257,   40,  375,  260,  125,  378,  379,  264,  381,   41,
  267,   43,  258,   45,  125,  272,  273,  257,  275,  125,
  260,  278,   94,   95,  264,  398,  399,   59,   60,   61,
   62,  125,  272,  273,    0,   58,  409,  410,  278,   59,
  257,  414,  415,  260,  261,  262,  263,  264,  265,  266,
  267,   59,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,    0,   58,  260,  125,  262,  263,
  264,  265,  266,  267,   60,   61,   62,   58,  272,  273,
  274,  275,  276,  277,  278,  279,  262,  263,    0,  257,
  262,  263,  260,  125,  262,  263,  264,  265,  266,  267,
  262,  263,  262,  263,  272,  273,  274,  275,  276,  277,
  278,  279,   58,  257,  274,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  262,  263,  260,
  261,  262,  263,  264,  265,  266,  267,   59,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  125,
   60,   61,   62,  257,  101,  102,  260,    0,  262,  263,
  264,  265,  266,  267,  123,   41,  274,  273,  272,  273,
  274,  275,  276,  277,  278,  279,   58,   44,  123,  125,
   59,   59,    0,  209,   59,   41,  265,   40,   59,  257,
   59,   59,  260,   59,  262,  263,  264,  265,  266,  267,
   59,   44,  125,  125,  272,  273,  274,  275,  276,  277,
  278,  279,    0,  125,   59,  257,   59,   59,  260,  261,
  262,  263,  264,  265,  266,  267,   44,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  123,
  123,   59,  274,  263,  263,    2,  257,    4,   59,  260,
  123,  262,  263,  264,  265,  266,  267,  263,   15,   59,
   44,  272,  273,  274,  275,  276,  277,  278,  279,  123,
  263,   59,   59,  269,  270,  271,   33,   34,  263,  125,
   59,  257,  125,  123,  260,   41,   40,   59,  264,  265,
  266,   59,   59,   59,  263,   59,  272,  273,   59,   59,
  276,  277,  278,  279,   59,  272,  257,  125,   59,  260,
   59,  257,  125,  264,  260,  123,  266,   41,  264,  265,
  266,  272,  273,  125,   81,   40,  272,  273,  263,  260,
  276,  277,  278,  279,   59,  257,   59,  125,  260,   59,
   40,  260,  264,  265,  266,   41,  261,   40,  123,  375,
  272,  273,   41,  379,  276,  277,  278,  279,  123,  269,
  270,  271,  261,   41,  257,  123,  125,  260,  263,  261,
  123,  264,  398,  266,  257,  123,   59,  260,  125,  272,
  273,  264,  265,  409,  257,  278,  125,  260,  414,  272,
  273,  264,  123,  276,  277,  278,  279,  262,   59,  272,
  273,  263,  123,  257,   40,  278,  260,   41,  262,  263,
  264,  260,  262,  261,  257,  123,  125,  260,  272,  273,
   40,  264,  265,  266,  278,  123,  263,   59,  260,  272,
  273,   41,  261,  276,  277,  278,  279,  125,  123,  257,
  262,  125,  260,  123,  125,   59,  264,  265,  266,  257,
  257,  263,  260,  261,  272,  273,  264,   58,  276,  277,
  278,  279,  125,    0,  272,  273,  123,  123,  242,  257,
  278,  309,  260,  133,  133,  133,  264,  265,  266,  195,
  243,  320,  127,   43,  272,  273,  125,   -1,  276,  277,
  278,  279,  257,  207,   -1,  260,  125,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  257,
  275,   -1,  260,  278,   -1,   -1,  264,   -1,   -1,  267,
  125,   -1,  125,   -1,  272,  273,  257,  275,   -1,  260,
  278,   -1,  263,  264,   -1,   -1,  257,   -1,   -1,  260,
  261,  272,  273,  264,  125,   -1,   -1,  278,   -1,   -1,
   -1,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  125,   -1,  257,
   -1,   -1,  260,   -1,  272,  273,  264,  265,   -1,   -1,
  278,   -1,   -1,   -1,  272,  273,  125,   -1,  276,  277,
  278,  279,  257,   -1,  257,  260,   -1,  260,   -1,  264,
   -1,  264,  265,   -1,   -1,  125,   -1,  272,  273,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,   -1,
   -1,  260,   -1,   -1,  125,  264,  265,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  257,   -1,  257,  260,   -1,  260,  125,  264,
  265,  264,   -1,   -1,   -1,   -1,   -1,  272,  273,  272,
  273,  276,  277,  278,  279,  278,  257,  125,   -1,  260,
   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,  153,  154,  264,  156,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  257,  275,   -1,  260,  278,   -1,   -1,
  264,   -1,   -1,  267,  214,   -1,   -1,   -1,  272,  273,
  257,  275,   -1,  260,  278,  262,  263,  264,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,   -1,  249,
   -1,  251,   -1,  253,  272,  273,  256,   -1,  258,  257,
  278,   -1,  260,   -1,   -1,   -1,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
  290,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,  304,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,  257,
   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,  277,
  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,
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
"$$1 :",
"funcion_con_return : encabezado_funcion '{' sentencia_return '}' $$1 encabezado_funcion '{' sentencias_funcion_con_return sentencia_return '}'",
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
"sentencia_funcion_con_return : sentencia_seleccion_compuesta_con_return",
"sentencia_funcion_con_return : DEFER sentencia_seleccion_compuesta_con_return",
"funcion_sin_return : encabezado_funcion '{' cuerpo_funcion_sin_return '}'",
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE sentencia_return ENDIF ';' IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE sentencia_return ENDIF ';' IF '(' condicion ')' THEN sentencia_return ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';' IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';' IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : sentencias_when sentencia_return",
"sentencia_do_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"sentencia_do_con_return : etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
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
"$$2 :",
"parametro : $$2 ID",
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
"$$3 :",
"sentencia_when : WHEN '(' ')' THEN '{' sentencias_when '}' ';' $$3 WHEN '(' condicion ')' THEN '{' sentencias_when '}'",
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
"$$4 :",
"condicion : $$4 comparador expresion",
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

//#line 355 ".\gramatica.y"

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
//#line 854 "Parser.java"
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
case 35:
//#line 81 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 40:
//#line 102 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 43:
//#line 111 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 44:
//#line 112 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 49:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 50:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 51:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 52:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 53:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 54:
//#line 128 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 55:
//#line 129 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 56:
//#line 130 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 58:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 59:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 60:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 61:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 64:
//#line 144 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 69:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 70:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 71:
//#line 159 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 72:
//#line 160 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 73:
//#line 161 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 77:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 78:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 79:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 93:
//#line 196 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 94:
//#line 197 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 95:
//#line 198 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 96:
//#line 199 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 97:
//#line 200 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 98:
//#line 204 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 99:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 102:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 103:
//#line 215 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 104:
//#line 216 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 105:
//#line 217 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 106:
//#line 218 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 112:
//#line 236 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 113:
//#line 237 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 114:
//#line 238 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 115:
//#line 242 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 116:
//#line 243 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 117:
//#line 244 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 118:
//#line 245 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 121:
//#line 254 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 122:
//#line 255 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 123:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 124:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 125:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 126:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 127:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 128:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 129:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 130:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 131:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 132:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 133:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 134:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 135:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 136:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 139:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 140:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 145:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 146:
//#line 287 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 168:
//#line 333 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 169:
//#line 334 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 170:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 171:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 172:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 173:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 174:
//#line 339 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 175:
//#line 340 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 176:
//#line 341 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 178:
//#line 346 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1343 "Parser.java"
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
