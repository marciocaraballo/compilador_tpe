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
   16,   16,   16,   16,   16,   16,   16,   22,   22,    8,
    8,   23,   23,   23,   23,   25,   25,   25,   21,   21,
   21,   21,   21,   21,   21,   21,   19,   19,   19,   20,
   20,   26,   26,   26,   26,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   13,   13,   13,   13,   13,
   30,   30,   30,   33,   33,   32,   32,   34,   32,    9,
    9,    9,   35,   35,   36,   36,   36,   36,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   28,   28,
   28,   40,   40,   40,   40,   40,   41,   41,   39,   39,
   42,   42,   42,   42,   42,   27,   43,   43,   29,   29,
   17,   17,   17,   38,   38,   45,   38,   38,   38,   44,
   44,   37,   37,   46,   46,   47,   47,   24,   24,   24,
   48,   48,   48,   48,   48,   48,   31,   31,   31,   49,
   49,   49,   50,   50,   50,   52,   52,   53,   53,   54,
   54,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   51,   51,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    4,
    5,   10,   13,   13,   16,    1,    1,    2,    8,    8,
   11,   11,   15,   15,   15,   15,    9,   10,   10,    7,
    9,    1,    1,    4,    3,    6,    7,    5,    5,    5,
    5,    6,    6,    6,    6,    5,    4,    3,    3,    4,
    1,    3,    5,    1,    3,    2,    1,    0,    2,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    0,   17,    7,    6,    1,
    2,    8,   10,    1,    3,    1,    2,    3,    2,    2,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    3,    4,    1,    3,    1,
    1,    5,    5,    4,    4,    4,    4,    4,    4,    4,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  184,  183,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,   99,  103,    0,  101,  105,
  107,  119,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  109,  127,  110,
  111,    0,  100,  104,  102,  106,  108,    0,   92,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,    0,  181,    0,  132,    0,    0,  162,  164,
  165,   19,  151,  152,  153,  154,  155,  156,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  112,    0,  117,  129,
    0,    0,   97,    0,   90,    0,   15,    0,    0,    0,
    0,    0,   23,   26,    0,    0,   24,   27,   28,   30,
   32,   34,   36,    0,    0,  120,    1,    0,  182,    0,
    0,  131,    0,    0,    0,    0,    0,  177,  175,  178,
    0,  176,    0,  174,    0,    0,    0,    0,   86,    0,
    0,   89,    0,    0,    0,    0,  126,  116,    0,  128,
  130,    0,    0,   95,   94,    0,    0,    0,    0,    0,
   62,    0,   63,    0,   29,   31,   33,   35,   37,   21,
    0,   25,    0,   40,    0,  170,  166,  171,    0,  168,
    0,    0,  160,  161,    0,    0,  173,  172,    0,   71,
    0,    0,   70,    0,    0,   69,    0,    0,    0,    0,
  113,    0,    0,    0,    0,    0,    0,   78,    0,    0,
    0,    0,   22,   41,    0,    0,  167,    0,  144,    0,
   66,    0,   75,   74,    0,   73,    0,    0,    0,  139,
    0,  123,  125,    0,  124,    0,   80,    0,    0,   65,
    0,    0,    0,    0,  169,  146,    0,    0,    0,   67,
    0,   83,  141,    0,    0,    0,  121,    0,   76,    0,
   64,    0,    0,    0,  145,  147,    0,  142,    0,  136,
    0,  135,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   85,    0,  134,    0,   48,   38,    0,
    0,    0,    0,    0,   60,    0,    0,    0,    0,  143,
    0,    0,    0,    0,   39,    0,   49,   50,    0,   46,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   57,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,   42,   58,   59,
    0,    0,    0,    0,    0,    0,   51,    0,   52,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   43,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,  137,    0,   55,
   56,   53,    0,   54,    0,   45,    0,
};
final static short yydgoto[] = {                          3,
    4,   15,  247,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  330,  126,  127,  309,   26,   27,  131,  132,
  133,  310,  331,   89,  298,  182,   28,  110,  111,  100,
   90,  101,  272,  102,   60,   61,   29,   30,   31,   50,
   51,   32,   52,  248,  305,  240,  267,   91,   78,   79,
   80,   81,  199,  200,
};
final static short yysindex[] = {                       -79,
    0, 1031,    0,  569,  -31,    1,  -25,  -30,   29,  726,
    0,    0,  -71,  -47,  671,    0,    0,    0,    0,    0,
    0,    0,  -43,   32,  -58,    0,    0,   45,    0,    0,
    0,    0,  771,  781,   30, -150,   23,   69,  105,  -11,
  -34,  -24,  738, -119,   20,  101,  171,    0,    0,    0,
    0, -118,    0,    0,    0,    0,    0, -226,    0,  123,
  120,    0,    0,  147,    0,  136,    0,  746,  -68,    0,
  804,    0,  168,    0,  -36,    0,   55,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  177,   -8,
   60,  169,  182,   -2,   18,  185,  -37,  192,    3,  214,
  213,    7,  216,  238,   24,  249,    0,  -40,    0,    0,
  945,   56,    0,   44,    0,   38,    0,  267,   26,  280,
  -98,  596,    0,    0,  180, 1008,    0,    0,    0,    0,
    0,    0,    0,  197,  271,    0,    0,   52,    0,   60,
   60,    0,   60,   60,   63,   60,   90,    0,    0,    0,
  273,    0,  276,    0,  282, -182,    9, -182,    0,  286,
 -182,    0, -182,  300,  215,  -89,    0,    0,  305,    0,
    0,  763,  325,    0,    0,   23,   79,   98,   23,  171,
    0,  106,    0,  342,    0,    0,    0,    0,    0,    0,
  259,    0,  261,    0,  127,    0,    0,    0,   -1,    0,
   57,   57,    0,    0,  -53,   90,    0,    0, -182,    0,
  332, -182,    0, -182,  352,    0, -182, 1031,  279,  828,
    0,  348,   31,  360,  380,  363,  149,    0,  382,  926,
  384,   23,    0,    0,  -98,   89,    0,  579,    0,    6,
    0, -182,    0,    0, -182,    0, 1031,  301, 1031,    0,
  302,    0,    0,  366,    0,  175,    0,  371,  186,    0,
  307,   23,  407,  188,    0,    0,  706,  -53,  400,    0,
  419,    0,    0,  405,  340,  409,    0,   83,    0,  343,
    0,  428,  217,  436,    0,    0,  211,    0, -182,    0,
  421,    0,  445,  226,  873,  224,  225,  227, 1041,  431,
 -103,   23,  433,    0,  228,    0,   23,    0,    0,  627,
  439,  443,   93,  -99,    0,  873,  224,  225,  464,    0,
  466,  467,  245,  385,    0,  386,    0,    0,  873,    0,
    0,  250,  455,  390,  391,  627,  462,   23,  263,  399,
   14,  112, 1064,  468,    0,  475,  476,  422,    0,  484,
  173,  873,  231,  489,  427,  498,  435,    0,    0,    0,
  126,  297,  873, 1074,  873,  310,    0,  873,    0,  317,
  447,  459, 1064,  469,  470,  962,  533,  972,  534,  873,
 1031,  477,  341,  344,  346,  480,    0,  349,    0,  989,
  481,  338,  552,  555,  558,  361,  581,    0,  337,    0,
    0,    0,  588,    0,  873,    0, 1064,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  139,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  392,  392,    0,  590,  851,  875,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  537,    0,  638,
  512,    0,    0,  601,    0,  661,    0,    0,    0,    0,
  650,    0,  356,    0,    0,    0,  266,  411,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  392,    0,    4,    0,
   95,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  695,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,   47,    0,    0,    0,
  289,    0,  312,    0,    0,    0,    0,    0,    0,    0,
  392,    0,    0,    0,    0,    0,    0,    0,  901,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  434,  458,    0,    0,    0,   70,    0,    0,  -58,    0,
    0,    0,    0,    0,   96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  536,    0,    0,  392,    0,  538,    0,    0,    0,
    0,    0,    0,  379,    0,    0,    0,  336,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   97,    0,    0,    0,    0,  483,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  392,    0,
    0,    0,    0,    0,    0,  402,  403,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   25,  117,  -66,   -9,    0,    0,    0,    0,  -33,
   10,    0,  -65,    0, -112,  -45,  -13,    5,  544,  545,
  547,  895,  -63,  -32, -277,  437,   77,   37,  490,  -15,
   -4, -137,  404,    0,  563,    0,  689,  701,  703,    0,
    0,  648,    0, -198,    0,  451,    0,  651,  264,  265,
 -110,    0,    0,  502,
};
final static int YYTABLESIZE=1352;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   48,  124,  125,  155,  134,   97,   98,   99,   99,   42,
  106,   59,   36,  192,   40,   65,  103,   54,  168,  316,
  156,  251,  128,  215,  180,  333,  104,  198,   34,   96,
   77,  113,   66,  220,  140,  332,  141,   48,  151,  237,
   37,  114,  236,    2,   87,   82,   49,   87,  273,  211,
  275,   87,   88,   86,  129,  181,  150,   71,  153,  124,
  191,   87,  193,   99,   68,  177,  212,   75,   43,  238,
   75,  254,  130,  149,   75,  366,  152,  108,  107,  173,
  128,  157,   87,   88,   86,   79,  147,  150,   76,  253,
   67,  149,  197,   11,   12,  172,   75,  140,  143,  141,
   75,   48,   69,  144,   75,  150,   64,  271,  185,   92,
  148,   48,  129,  142,  178,   87,   88,   86,   16,  226,
   16,  366,  210,   75,  213,  198,  186,   99,  148,  216,
  130,   63,  140,   75,  141,   81,   82,   84,  228,  223,
  140,  206,  141,  225,  135,   93,  229,  171,   35,   16,
   63,  271,   81,   82,   84,  112,  293,  183,   44,  109,
  293,    6,  119,  116,  261,    7,  119,  119,   45,  181,
   48,  219,  227,    9,   10,  241,   46,    1,  243,   13,
  244,  115,  391,  246,  169,   44,  314,   63,    6,  258,
   36,  140,    7,  141,  117,  239,  126,   20,  135,  263,
    9,   10,  135,   44,   10,  295,    6,  138,  270,   58,
    7,   99,  296,   64,  297,  329,  167,  145,    9,   10,
   48,  139,   79,   79,   13,   48,   41,  148,  266,  282,
  308,   38,  124,   39,   79,  317,   35,  318,   11,   12,
  149,   11,   12,  154,  324,   94,  326,   95,  334,  158,
  335,   11,   12,  128,  160,   99,  161,  286,  239,  159,
   83,   84,   85,  162,  325,  133,  171,  268,  269,  319,
  348,  183,  326,  163,  322,  353,  354,  357,  164,   73,
   74,  129,   73,   74,  165,  129,   73,   74,  180,  166,
  325,   83,   84,   85,   58,  363,  129,  325,  374,  130,
  375,  174,  129,  130,  190,  350,  176,  382,  196,   74,
  386,  179,   73,   74,  130,  129,   73,   74,  325,  179,
  130,  194,  129,  205,   83,   84,   85,  325,  195,  129,
  325,  207,  325,  130,  208,   73,   74,  218,  129,  209,
  130,  386,  293,  214,  325,  196,   74,  130,  119,  129,
  129,  129,  293,  365,  129,  163,  130,  217,  119,  129,
  294,  325,  129,  221,  129,  224,  129,  130,  130,  130,
  294,  135,  130,  355,  356,  135,  129,  130,  122,  231,
  130,  232,  130,  233,  130,  234,  135,  371,  354,  242,
  133,  129,  135,  129,  130,  245,  163,  163,  163,  235,
  163,  249,  163,  201,  202,  135,  252,  203,  204,  130,
  159,  130,  135,  180,  163,  163,  163,  163,  255,  135,
  256,  257,  259,  262,  277,  274,  276,   44,  135,  279,
    6,  281,  293,  157,    7,  278,  179,   45,  119,  135,
  135,  135,    9,   10,  135,   46,  280,  283,   13,  135,
  294,  159,  135,  159,  135,  159,  135,  158,  288,  405,
   77,  284,  289,  290,  291,  299,  135,  292,  300,  159,
  159,  159,  159,  303,  157,  302,  157,  301,  157,  306,
  163,  135,  138,  135,  307,  293,  311,  312,  313,  315,
  293,  320,  157,  157,  157,  157,  119,  327,  158,  321,
  158,  328,  158,  122,  337,  338,  340,  339,  294,  341,
  342,   93,  344,  345,  346,  347,  158,  158,  158,  158,
  349,  352,  133,  351,  362,  133,  358,  133,  133,  133,
  133,  133,  133,  359,  360,  159,   98,  133,  133,  133,
  133,  133,  133,  133,  133,  180,  361,  367,  180,  368,
  180,  180,  180,  180,  180,  180,  369,  372,  157,  370,
  180,  180,  180,  180,  180,  180,  180,  180,  179,  380,
   93,  179,  377,  179,  179,  179,  179,  179,  179,  379,
   98,  381,  158,  179,  179,  179,  179,  179,  179,  179,
  179,  387,  389,  383,  384,   98,  293,   77,   77,  399,
   20,  392,  119,  393,  396,  398,  394,  138,  395,   77,
  400,  397,  163,  401,  294,  163,  402,  163,  163,  163,
  163,  163,  163,  403,  163,  163,  163,  163,  163,  163,
  163,  163,  163,  163,  163,  122,   93,   91,  122,  404,
  122,  122,  122,  122,  122,  122,  406,  126,   88,    4,
  122,  122,  122,  122,  122,  122,  122,  122,   72,   20,
   16,   98,  140,   46,   47,  187,  188,  159,  189,  230,
  159,  264,  159,  159,  159,  159,  159,  159,  175,  159,
  159,  159,  159,  159,  159,  159,  159,  159,  159,  159,
  157,   33,  304,  157,   96,  157,  157,  157,  157,  157,
  157,   55,  157,  157,  157,  157,  157,  157,  157,  157,
  157,  157,  157,   56,  158,   57,  136,  158,  287,  158,
  158,  158,  158,  158,  158,   20,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,  158,  265,   96,  138,
  146,    0,  138,    0,  138,  138,  138,  138,  138,  138,
    0,  323,    0,   96,  138,  138,  138,  138,  138,  138,
  138,  138,   91,    0,    0,    0,    0,    0,   93,    0,
    0,   93,    0,    0,    0,   93,   93,   93,  105,    0,
    0,    0,   75,   93,   93,   16,    0,   93,   93,   93,
   93,    0,    0,   98,    0,   62,   98,   87,   88,   86,
   98,   98,   98,  222,    0,    0,    0,   75,   98,   98,
    0,    0,   98,   98,   98,   98,    0,    0,    0,   96,
    0,    0,   87,   88,   86,    5,    0,    0,    6,    0,
  285,    0,    7,    8,    0,   44,    0,    0,    6,    0,
    9,   10,    7,    0,   11,   12,   13,   14,   47,    0,
    9,   10,   44,    0,    0,  184,   13,   20,    0,    7,
   20,    0,    0,    0,   20,   20,   20,  120,  121,    0,
  123,    0,   20,   20,    0,    0,   20,   20,   20,   20,
    0,    0,    0,   44,    0,    0,  118,    0,    0,    0,
    7,    0,  119,    0,   91,   70,    0,   91,  120,  121,
    0,   91,   91,   91,  122,   72,    0,    0,    0,   91,
   91,    0,    0,   91,   91,   91,   91,   16,    0,    0,
   16,    0,    0,    0,   16,   16,   16,    5,  137,    0,
    6,    0,   16,   16,    7,    8,   16,   16,   16,   16,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    0,   96,  250,    0,   96,    0,    0,    0,   96,   96,
   96,    0,   44,    0,    0,    6,   96,   96,    0,    7,
   96,   96,   96,   96,    0,  114,    0,    9,   10,    0,
    0,    0,   44,   13,    0,    6,    0,    0,    0,    7,
    0,    0,   45,    0,   73,   74,    0,    9,   10,  118,
   46,    0,    5,   13,    0,  118,   83,   84,   85,    7,
    8,  119,    0,    0,    0,    0,    0,  120,  121,   73,
   74,   11,   12,  122,   14,  115,    0,    5,    0,    0,
    6,   83,   84,   85,    7,    8,    0,    5,    0,    0,
    6,    0,    9,   10,    7,    8,   11,   12,   13,   14,
  260,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    5,    0,    0,    6,    0,    0,    0,    7,    8,  170,
    0,    0,    0,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,    0,    5,    0,  385,    6,    0,    0,
    0,    7,    8,    0,    0,    0,  388,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,  114,    0,    0,
  114,    0,    0,  385,  114,    0,  114,  114,    0,    0,
    0,    0,  114,  114,  114,  114,    0,    0,  114,   44,
    0,  118,  184,    0,  118,    0,    7,    0,  118,    0,
  118,  118,    0,    0,  120,  121,  118,  118,  118,  118,
  122,    0,  118,    0,    0,    0,    0,  115,    0,    0,
  115,    0,    0,    0,  115,    0,  115,  115,    0,    0,
    0,    0,  115,  115,  115,  115,    0,    0,  115,    0,
    0,    0,   44,    0,    0,    6,    0,    0,    0,    7,
    0,  119,   45,    0,    0,    0,    0,    9,   10,    0,
   46,   44,    0,   13,    6,    0,    0,    0,    7,    0,
  336,   45,    0,    0,    0,    0,    9,   10,   44,   46,
    0,  184,   13,  343,    0,    7,    0,  119,   44,    0,
    0,  184,    0,  120,  121,    7,    0,    0,    0,  122,
    0,    0,    0,  120,  121,   44,  364,    0,  184,  122,
    0,    0,    7,    0,    0,    0,    0,  373,    0,  376,
  120,  121,  378,    0,    5,    0,  122,  118,    0,    0,
    0,    7,    8,  119,  390,    0,    0,    0,    0,  120,
  121,    0,    0,   11,   12,  122,   14,    5,    0,    0,
    6,    0,    0,    0,    7,    8,    0,    5,    0,  407,
  184,    0,    9,   10,    7,    8,   11,   12,   13,   14,
    0,    0,  120,  121,    0,    0,   11,   12,  122,   14,
   44,    0,    0,  184,    0,    0,    0,    7,    0,  119,
   44,    0,    0,  118,    0,  120,  121,    7,    0,  119,
    0,  122,    0,    0,    0,  120,  121,    0,    0,    0,
    0,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         13,
   10,   68,   68,   41,   68,   40,   41,   41,   42,   40,
   43,   59,   44,  126,   40,   59,   41,   13,   59,  123,
   58,  220,   68,  161,  123,  125,   42,  138,    4,   41,
   35,  258,   23,  123,   43,  313,   45,   47,   41,   41,
   40,  268,   44,  123,   41,   36,   10,   44,  247,   41,
  249,   60,   61,   62,   68,  121,   59,   33,   41,  126,
  126,   58,  126,   97,  123,   40,   58,   45,   40,  123,
   45,   41,   68,   41,   45,  353,   59,   58,   59,  112,
  126,   97,   60,   61,   62,  125,   91,   41,   59,   59,
   59,   59,   41,  276,  277,   40,   45,   43,   42,   45,
   45,  111,   58,   47,   45,   59,  257,  245,  122,   41,
   41,  121,  126,   59,  119,   60,   61,   62,    2,   41,
    4,  399,  156,   45,  158,  236,  122,  161,   59,  163,
  126,   15,   43,   45,   45,   41,   41,   41,   41,  172,
   43,  146,   45,  176,   68,   41,  179,  111,  268,   33,
   34,  289,   58,   58,   58,  274,  260,  121,  257,   59,
  260,  260,  266,   44,  230,  264,  266,  266,  267,  235,
  180,  261,  177,  272,  273,  209,  275,  257,  212,  278,
  214,   59,  381,  217,  108,  257,  299,   71,  260,   41,
   44,   43,  264,   45,   59,  205,   58,   59,  122,  232,
  272,  273,  126,  257,  273,  123,  260,   40,  242,  257,
  264,  245,  278,  257,  278,  123,  257,   41,  272,  273,
  230,  258,  262,  263,  278,  235,  257,   59,  238,  262,
  294,  257,  299,  259,  274,  301,  268,  301,  276,  277,
   59,  276,  277,   59,  310,  257,  310,  259,  314,   58,
  314,  276,  277,  299,   41,  289,   44,  267,  268,  257,
  269,  270,  271,  257,  310,    0,  230,  262,  263,  302,
  336,  235,  336,   58,  307,  262,  263,  343,   41,  257,
  258,  295,  257,  258,  261,  299,  257,  258,    0,   41,
  336,  269,  270,  271,  257,  123,  310,  343,  364,  295,
  364,  258,  316,  299,  125,  338,   40,  373,  257,  258,
  376,    0,  257,  258,  310,  329,  257,  258,  364,   40,
  316,  125,  336,  261,  269,  270,  271,  373,   58,  343,
  376,   59,  378,  329,   59,  257,  258,  123,  352,   58,
  336,  407,  260,   58,  390,  257,  258,  343,  266,  363,
  364,  365,  260,  123,  368,    0,  352,   58,  266,  373,
  278,  407,  376,   59,  378,   41,  380,  363,  364,  365,
  278,  295,  368,  262,  263,  299,  390,  373,    0,  274,
  376,   40,  378,  125,  380,  125,  310,  262,  263,   58,
  125,  405,  316,  407,  390,   44,   41,   42,   43,  273,
   45,  123,   47,  140,  141,  329,   59,  143,  144,  405,
    0,  407,  336,  125,   59,   60,   61,   62,   59,  343,
   41,   59,   41,   40,   59,  125,  125,  257,  352,   59,
  260,  125,  260,    0,  264,  261,  125,  267,  266,  363,
  364,  365,  272,  273,  368,  275,  261,   41,  278,  373,
  278,   41,  376,   43,  378,   45,  380,    0,   59,  123,
  125,  274,   44,   59,  125,  123,  390,   59,   41,   59,
   60,   61,   62,  263,   41,   40,   43,  261,   45,   59,
  125,  405,    0,  407,   40,  260,  263,  263,  262,   59,
  260,   59,   59,   60,   61,   62,  266,   59,   41,  272,
   43,   59,   45,  125,   41,   40,  262,   41,  278,  125,
  125,    0,  263,   59,  125,  125,   59,   60,   61,   62,
   59,  123,  257,  261,   41,  260,   59,  262,  263,  264,
  265,  266,  267,   59,   59,  125,    0,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  125,   59,  260,  123,
  262,  263,  264,  265,  266,  267,   59,  261,  125,  125,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  123,
   59,  260,  263,  262,  263,  264,  265,  266,  267,  263,
   44,  123,  125,  272,  273,  274,  275,  276,  277,  278,
  279,   59,   59,  125,  125,   59,  260,  262,  263,  262,
    0,  125,  266,  263,  125,  125,  263,  125,  263,  274,
   59,  263,  257,   59,  278,  260,   59,  262,  263,  264,
  265,  266,  267,  263,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  125,    0,  260,   59,
  262,  263,  264,  265,  266,  267,   59,   58,  257,    0,
  272,  273,  274,  275,  276,  277,  278,  279,  123,   59,
    0,  125,  125,  262,  262,  122,  122,  257,  122,  180,
  260,  235,  262,  263,  264,  265,  266,  267,  116,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  123,  289,  260,    0,  262,  263,  264,  265,  266,
  267,   13,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,   13,  257,   13,   69,  260,  268,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  236,   44,  257,
   90,   -1,  260,   -1,  262,  263,  264,  265,  266,  267,
   -1,  125,   -1,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  125,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,   41,   -1,
   -1,   -1,   45,  272,  273,  125,   -1,  276,  277,  278,
  279,   -1,   -1,  257,   -1,  125,  260,   60,   61,   62,
  264,  265,  266,   41,   -1,   -1,   -1,   45,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,  125,
   -1,   -1,   60,   61,   62,  257,   -1,   -1,  260,   -1,
  125,   -1,  264,  265,   -1,  257,   -1,   -1,  260,   -1,
  272,  273,  264,   -1,  276,  277,  278,  279,  123,   -1,
  272,  273,  257,   -1,   -1,  260,  278,  257,   -1,  264,
  260,   -1,   -1,   -1,  264,  265,  266,  272,  273,   -1,
  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,  266,   -1,  257,  125,   -1,  260,  272,  273,
   -1,  264,  265,  266,  278,  125,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,  265,  266,  257,  125,   -1,
  260,   -1,  272,  273,  264,  265,  276,  277,  278,  279,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,  257,  125,   -1,  260,   -1,   -1,   -1,  264,  265,
  266,   -1,  257,   -1,   -1,  260,  272,  273,   -1,  264,
  276,  277,  278,  279,   -1,  125,   -1,  272,  273,   -1,
   -1,   -1,  257,  278,   -1,  260,   -1,   -1,   -1,  264,
   -1,   -1,  267,   -1,  257,  258,   -1,  272,  273,  125,
  275,   -1,  257,  278,   -1,  260,  269,  270,  271,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,  257,
  258,  276,  277,  278,  279,  125,   -1,  257,   -1,   -1,
  260,  269,  270,  271,  264,  265,   -1,  257,   -1,   -1,
  260,   -1,  272,  273,  264,  265,  276,  277,  278,  279,
  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  125,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,   -1,  257,   -1,  125,  260,   -1,   -1,
   -1,  264,  265,   -1,   -1,   -1,  125,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,  125,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,  257,
   -1,  257,  260,   -1,  260,   -1,  264,   -1,  264,   -1,
  266,  267,   -1,   -1,  272,  273,  272,  273,  274,  275,
  278,   -1,  278,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
  316,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,  329,   -1,  264,   -1,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  272,  273,  257,  352,   -1,  260,  278,
   -1,   -1,  264,   -1,   -1,   -1,   -1,  363,   -1,  365,
  272,  273,  368,   -1,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,  265,  266,  380,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,  265,   -1,  257,   -1,  405,
  260,   -1,  272,  273,  264,  265,  276,  277,  278,  279,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,  266,
   -1,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,
   -1,  278,
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
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_return",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return_simple : DEFER sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_return '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';'",
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

//#line 359 ".\gramatica.y"

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
//#line 869 "Parser.java"
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
case 40:
//#line 92 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 41:
//#line 93 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 57:
//#line 121 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 58:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 59:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 60:
//#line 127 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 61:
//#line 128 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 68:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 69:
//#line 142 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 70:
//#line 143 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 71:
//#line 144 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 72:
//#line 145 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 73:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 74:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 75:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 77:
//#line 153 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 78:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 79:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 80:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 83:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 88:
//#line 172 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 89:
//#line 173 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 90:
//#line 177 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 91:
//#line 178 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 92:
//#line 179 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 96:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 97:
//#line 190 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 98:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 112:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 113:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 114:
//#line 216 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 115:
//#line 217 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 116:
//#line 218 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 117:
//#line 222 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 118:
//#line 223 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 121:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 122:
//#line 233 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 123:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 124:
//#line 235 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 125:
//#line 236 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 131:
//#line 254 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 132:
//#line 255 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 133:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 135:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 136:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 137:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 138:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 142:
//#line 274 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 143:
//#line 275 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 149:
//#line 290 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 150:
//#line 291 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 172:
//#line 337 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 338 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 174:
//#line 339 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 175:
//#line 340 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 176:
//#line 341 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 177:
//#line 342 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 178:
//#line 343 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 179:
//#line 344 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 345 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 182:
//#line 350 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1318 "Parser.java"
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
