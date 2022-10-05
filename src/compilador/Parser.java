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
    7,    8,   13,   13,   14,   14,   16,   16,   17,   17,
   20,   18,   18,   18,   18,   18,   18,   18,   18,   23,
   24,   24,   21,   26,   26,   22,   22,   28,   30,   30,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   15,   15,   15,   15,   15,   32,   32,   32,   35,   35,
   34,   34,    9,    9,    9,   36,   36,   37,   37,   37,
   37,    5,    5,    5,    5,    5,    5,    5,    5,    5,
    5,   43,   43,   43,   44,   44,   44,   44,   44,   45,
   45,   42,   42,   46,   46,   46,   46,   46,   29,   47,
   47,   31,   31,   38,   38,   38,   41,   41,   48,   41,
   27,   27,   39,   39,   39,   39,   39,   39,   39,   39,
   39,   39,   39,   39,   39,   39,   39,   39,   49,   49,
   49,   49,   25,   25,   19,   19,   51,   19,   50,   50,
   50,   50,   50,   50,   33,   33,   33,   52,   52,   52,
   53,   53,   53,   55,   55,   56,   56,   57,   57,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   54,   54,
   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    2,    1,    2,    1,    2,   10,    0,
    1,    1,    1,    1,    2,    1,    2,    1,    2,    8,
    1,    4,    9,    1,    2,    1,    3,    7,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    4,    3,    3,    4,    1,    3,    5,    1,    3,
    2,    1,    3,    2,    2,    1,    3,    3,    2,    2,
    1,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    1,    1,    2,    4,    1,    3,    3,    2,
    1,    1,    3,    7,    6,    6,    6,    6,    1,    1,
    3,    1,    2,    4,    3,    3,    9,    8,    0,   17,
    1,    2,    8,   10,    7,    7,    7,    7,    7,    7,
    9,    9,    9,    9,    9,    9,    9,    8,    1,    3,
    2,    2,    1,    2,    3,    2,    0,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    3,    4,    1,    3,    1,    1,    5,
    5,    4,    4,    4,    4,    4,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  182,  181,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   82,   84,   86,   88,
   90,  102,    0,    0,    0,    0,    0,  179,    0,    0,
    0,    0,    0,    0,  160,  162,  163,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   92,  110,   93,
   94,    0,   83,   85,   87,   89,   91,    0,   75,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  115,    0,   19,    0,  180,    0,    0,    0,
  149,  150,  151,    0,    0,  152,  153,  154,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   72,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   95,    0,  100,
    0,  112,    0,   80,    0,   73,    0,   15,    0,    0,
    0,    0,    0,   32,   33,    0,    0,   23,    0,   25,
   27,   34,   36,   38,   46,    0,  103,    1,  114,  168,
  164,  169,    0,  166,    0,    0,    0,    0,    0,    0,
    0,    0,  158,  159,  175,  173,  176,    0,  174,    0,
  172,    0,    0,    0,    0,   71,    0,    0,    0,    0,
    0,    0,  109,   99,    0,  111,  113,    0,    0,   78,
   77,    0,    0,    0,    0,    0,   49,    0,    0,   35,
   37,   39,   21,   22,   24,   26,   28,    0,    0,  165,
    0,    0,    0,    0,    0,    0,    0,    0,  171,  170,
    0,   56,    0,    0,   55,    0,    0,   54,    0,    0,
    0,    0,   96,    0,    0,    0,    0,    0,    0,   63,
    0,    0,    0,    0,   47,  167,  143,    0,  141,  144,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,    0,   60,   59,    0,   58,    0,    0,    0,
    0,  106,  108,    0,  107,    0,   65,    0,    0,    0,
    0,    0,  140,    0,  127,    0,  125,    0,    0,  130,
    0,    0,    0,  128,    0,  126,   52,    0,   68,  122,
    0,    0,    0,  104,    0,   61,    0,   50,    0,    0,
    0,    0,  138,    0,    0,    0,  123,    0,    0,    0,
  119,    0,  118,    0,    0,    0,    0,    0,    0,    0,
  133,  131,  135,  136,    0,  134,  132,   70,    0,  117,
    0,    0,    0,   44,    0,    0,   48,   41,  124,    0,
    0,   31,    0,   40,    0,   45,    0,   42,    0,   43,
    0,   29,    0,    0,    0,    0,  120,
};
final static short yydgoto[] = {                          3,
    4,   15,  268,   17,  212,   19,   20,   21,   22,   23,
   24,   25,  136,  137,  138,  139,  140,  141,   41,  326,
  142,  143,  144,  327,  213,  345,  269,  145,   26,  198,
  121,  112,   42,  113,  299,   70,   71,   27,   28,   29,
   30,   31,   59,   60,   61,   32,   62,  339,  217,   99,
   43,   44,   45,   46,   47,  153,  154,
};
final static short yysindex[] = {                       -84,
    0,  902,    0,  383,  -41,  -35,  -20,   -6,   60,  569,
    0,    0,  319,  -55,  666,    0,    0,    0,    0,    0,
    0,    0,  -53,   63,  -62,   75,    0,    0,    0,    0,
    0,    0,  676,  703,   66, -139,  111,    0, -123,   72,
   98,  -10,  408,  116,    0,    0,    0,  129,  138,  -23,
  -24,  -19,   83,  -82,   85,  142,  302,    0,    0,    0,
    0,  -71,    0,    0,    0,    0,    0, -173,    0,  149,
  168,    0,    0,  173,    0,  161,    0,  869,  -26,    0,
  713,    0,    0,   13,    0,   95,    0,  -12,  -17,    8,
    0,    0,    0,  102,  102,    0,    0,    0,  102,  102,
  102,  102,  182,  212,   18,   19,  218,    0,  -22,  222,
   44,  248,  259,  257,  279,   81,  286,    0,  -47,    0,
  822,    0,   47,    0,  164,    0,  108,    0,   49,   56,
  292,  552,  434,    0,    0,  262,  310,    0,  869,    0,
    0,    0,    0,    0,    0,  380,    0,    0,    0,    0,
    0,    0,  118,    0,  646,  646,  385,  646,  116,  116,
   69,   69,    0,    0,    0,    0,    0,  386,    0,  399,
    0,  403,  -94,   12,  -94,    0,  427, -203,  -94,  429,
  359,  -81,    0,    0,  435,    0,    0,   97,  468,    0,
    0,   72,  100,  609,   83,  302,    0,  245,   59,    0,
    0,    0,    0,    0,    0,    0,    0,  269,  263,    0,
  366,    0,  849,  -74,   48,  588,   99,  127,    0,    0,
  -94,    0,  472,  -94,    0,  -94,  488,    0,  -94,  902,
  431,  902,    0,  501,   34,  505,  -16,  506,  654,    0,
  526,  803,  532,   72,    0,    0,    0,  859,    0,    0,
  646,  509,  646,  523,  599,  536,  150,  646,  537,  646,
  542,    0,  -94,    0,    0, -203,    0,  902,  477,  902,
  479,    0,    0,  546,    0,  622,    0,  548,  -80,  483,
  102,  -13,    0,  348,    0,  351,    0,  556,  354,    0,
  624,  566,  364,    0,  374,    0,    0,  597,    0,    0,
  592,  539,  594,    0,  410,    0,  557,    0,  628,  644,
  606,  612,    0,  625,  626,  418,    0,  627,  630, -203,
    0,  631,    0,  366,    0,  425,  440,  892,  645,  410,
    0,    0,    0,    0,  651,    0,    0,    0,  433,    0,
  346,  455,  667,    0,  602,  -95,    0,    0,    0,  688,
  605,    0,  470,    0,  675,    0,  102,    0,  680,    0,
  699,    0,  481,  620,  902,  621,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  204,  528,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   21,    0,    0,  528,
    0,    0,    0,  107,    0,    0,    0,    0,    0,    0,
    0,    0,  528,  693,  739,  764,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  436,    0,  321,
  459,    0,    0,  484,    0,  356,    0,  633,    0,    0,
  753,    0,    0,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   65,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  528,    0,  511,    0,    0,    0,  528,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  633,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  131,  154,
  -30,  -18,    0,    0,    0,    0,    0,  177,    0,  200,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  784,    0,    0,  528,    0,    0,
    0,  528,    0,    0,  528,    0,    0,    0,  528,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  839,    0,    0,    0,    0,    0,    0,    0,    0,
  632,    0,    0,    0,    0,    0,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -56,    0,
    0,    0,    0,  528,    0,    0,    0,  185,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  636,    0,    0,    0,    0,  -88,    0,    0,
    0,    0,    0,  226,    0,    0,    0,    2,    0,    0,
  528,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  250,    0,    0,    0,    0,    0,   74,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  234,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  274,    0,    0,    0,    0,    0,
  185,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  528,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   53,  777,  -29,   -2,    0,    0,    0,    0,   46,
   93,    0,    0,    0,  424,    0,  634,  635,  -39,  422,
  639,  647,  649,    0, -204,    0,  303,  577,  -31,    0,
  590,   -4,   -9, -163,  473,  668,    0,   27,   31,   32,
   52,   54,   77,    0,    0,  -33,    0,    0,  562,  751,
    0,  450,  456,  -48,    0,    0,  587,
};
final static int YYTABLESIZE=1181;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,   36,   69,   40,   75,  248,   58,  146,   39,
  145,  184,   18,  117,  227,  109,  110,  107,  172,   50,
  161,  114,  148,  157,  276,   84,  146,  310,  145,  322,
   18,   18,   94,   52,   95,  173,  121,  152,    2,   63,
  148,  232,  232,   64,   65,  147,  146,  115,  134,   97,
   98,   96,  223,  108,   58,   94,   34,   95,  168,  170,
   78,  161,  161,  161,   66,  161,   67,  161,   64,  224,
  116,  149,   11,   12,  274,  135,  167,  169,   18,  161,
  161,  161,  161,  189,  124,   81,  188,  185,  192,  161,
  162,   39,  273,   39,  125,  193,  111,  111,  244,   53,
   39,  146,  298,   39,  174,   66,  157,  146,   67,  134,
   39,   94,   88,   95,   69,   76,   39,   74,   58,  341,
  194,   77,   66,  116,   83,   67,   62,   39,   85,   58,
  155,   69,   79,  122,   87,  151,  135,  234,   90,   39,
  238,   39,  119,  118,   39,  161,   39,  157,  235,  157,
   86,  157,  237,  156,  111,  241,  298,  101,  210,   63,
  152,  209,  102,   64,   65,  157,  157,  157,  157,  103,
  130,  155,    1,  155,  147,  155,  178,  121,  104,  231,
  307,   11,   12,  239,   66,   35,   67,  251,  252,  155,
  155,  155,  155,   58,  156,  116,  156,  187,  156,  177,
  120,   68,  123,   74,  282,   64,   64,  126,  247,  183,
  250,  127,  156,  156,  156,  156,   36,   64,  222,  128,
  225,   37,   38,  111,  228,  105,   35,   18,  146,   18,
  145,  157,  108,  105,  108,  106,   48,  108,   49,   58,
  165,  309,  148,  156,  156,  250,   10,  156,  155,  129,
   51,   11,   12,   11,   12,  155,   11,   12,   91,   92,
   93,  109,   20,   62,   62,   18,  262,   18,  158,  264,
  166,  265,  122,  137,  267,   62,  171,  161,  156,  175,
  161,  161,  161,  161,  161,  161,  161,  161,  177,  161,
  161,  161,  161,  161,  161,  161,  161,  161,  161,  161,
  176,  178,  178,   37,   38,   37,   38,   39,  297,  253,
  254,  111,   37,   38,  179,   37,   38,  361,  187,  180,
   74,  247,   37,   38,  177,   18,  182,  116,   37,   38,
  116,  195,  116,  116,  116,  116,  116,  116,  250,   37,
   38,  181,  116,  116,  116,  116,  116,  116,  116,  116,
  105,  150,   38,   37,   38,   16,   37,   38,   37,   38,
  258,  259,   18,  157,   68,  111,  157,  157,  157,  157,
  157,  157,  157,  157,  129,  157,  157,  157,  157,  157,
  157,  157,  157,  157,  157,  157,  203,  155,  260,  261,
  155,  155,  155,  155,  155,  155,  155,  155,  137,  155,
  155,  155,  155,  155,  155,  155,  155,  155,  155,  155,
  156,  291,  292,  156,  156,  156,  156,  156,  156,  156,
  156,  190,  156,  156,  156,  156,  156,  156,  156,  156,
  156,  156,  156,  178,  204,   81,  178,  208,  178,  178,
  178,  178,  178,  178,  219,   74,  142,  142,  178,  178,
  178,  178,  178,  178,  178,  178,  177,  220,   76,  177,
  221,  177,  177,  177,  177,  177,  177,   97,   98,   96,
  283,  177,  177,  177,  177,  177,  177,  177,  177,   81,
   16,  230,  105,   20,  226,  105,  229,  105,  105,  105,
  105,  105,  105,  233,   81,   31,   41,  105,  105,  105,
  105,  105,  105,  105,  105,   33,  129,  211,  236,  129,
   79,  129,  129,  129,  129,  129,  129,   76,  243,  150,
   38,  129,  129,  129,  129,  129,  129,  129,  129,  263,
  137,  266,  324,  137,  271,  137,  137,  137,  137,  137,
  137,  132,   20,  159,  160,  137,  137,  137,  137,  137,
  137,  137,  137,  270,   79,  197,  163,  164,   54,  272,
   81,    6,  205,  275,  277,    7,  279,  285,   55,   79,
  300,  281,  302,    9,   10,   54,   56,   74,    6,   13,
   74,  287,    7,   76,   74,   74,   74,  147,  147,  147,
    9,   10,   74,   74,  290,  294,   74,   74,   74,   74,
  296,  301,   54,  303,  304,    6,  306,  308,   20,    7,
  311,  130,   16,  312,  313,   16,  314,    9,   10,   16,
   16,   16,   54,   13,  317,    6,  318,   16,   16,    7,
  346,   16,   16,   16,   16,   79,  319,    9,   10,    5,
  320,   54,    6,   13,    6,  216,    7,    8,    7,  240,
  321,   94,  323,   95,    9,   10,    9,   10,   11,   12,
   13,   14,   13,  322,  331,  280,   54,  366,  329,    6,
  332,  255,  256,    7,  196,  130,   91,   92,   93,  328,
  335,    9,   10,  333,  334,  336,  342,   13,  337,  340,
   54,   57,   81,  199,  278,   81,   94,    7,   95,   81,
   81,   81,  343,  347,  350,  131,  132,   81,   81,  349,
  211,   81,   81,   81,   81,   76,  214,  215,   76,  218,
  130,  211,   76,   76,   76,  354,  355,  357,  325,  358,
   76,   76,  359,  360,   76,   76,   76,   76,  362,  363,
   20,  364,  365,   20,  211,  367,  211,   20,   20,   20,
  109,  344,    4,  348,   53,   20,   20,   30,   57,   20,
   20,   20,   20,  353,  351,  352,  211,   79,  211,  356,
   79,  200,  206,  207,   79,   79,   79,  257,   16,  201,
   16,  202,   79,   79,  245,  242,   79,   79,   79,   79,
   72,   73,  338,  100,  191,  246,  147,  147,  147,    0,
   80,    0,    0,    0,    0,    0,    0,    0,   54,   16,
   73,    6,  284,    0,  286,    7,  289,  130,   55,  293,
    0,  295,    0,    9,   10,   54,   56,   82,    6,   13,
    0,    0,    7,    0,    0,   55,    0,  148,    0,    0,
    9,   10,    0,   56,   54,    0,   13,    6,    0,  255,
  256,    7,  316,    0,    0,   54,    0,   73,    6,    9,
   10,  288,    7,   97,    0,   13,  257,    0,    0,    0,
    9,   10,    0,    0,    0,    0,   13,    0,   54,    0,
   54,    6,  305,    6,    0,    7,  315,    7,  101,    0,
    0,  257,    0,    9,   10,    9,   10,    0,    0,   13,
   54,   13,   54,    6,  330,    6,    0,    7,   98,    7,
    0,    0,    0,    0,    0,    9,   10,    9,   10,    0,
    0,   13,    5,   13,    0,    6,    0,  186,    0,    7,
    8,    0,    5,    0,    0,    6,    0,    9,   10,    7,
    8,   11,   12,   13,   14,    0,  186,    9,   10,    0,
    0,   11,   12,   13,   14,    0,    0,    0,    0,    5,
    0,    0,    6,  143,    0,    0,    7,    8,    0,    5,
    0,    0,    6,  249,    9,   10,    7,    8,   11,   12,
   13,   14,    0,  283,    9,   10,    0,    0,   11,   12,
   13,   14,    0,    0,    0,   97,    0,    0,   97,    0,
    0,    0,   97,    0,   97,   97,    0,    0,    0,    0,
   97,   97,   97,   97,    0,    0,   97,    0,    0,    0,
  101,    0,    0,  101,    0,    0,    0,  101,    0,  101,
  101,    0,    0,    0,    0,  101,  101,  101,  101,    0,
   98,  101,    0,   98,    0,    0,    0,   98,    0,   98,
   98,    0,    0,    0,    0,   98,   98,   98,   98,   54,
    0,   98,    6,    0,    0,    0,    7,    0,  130,   55,
    0,    0,    0,    0,    9,   10,    0,   56,   54,    0,
   13,    6,    0,    0,    0,    7,    0,    0,   55,    0,
    0,    0,    0,    9,   10,  143,   56,    0,  143,   13,
  139,  139,  143,    0,    0,   54,    0,    0,    6,    0,
  143,  143,    7,    0,    0,   54,  143,    0,    6,    0,
    9,   10,    7,    0,    0,    5,   13,    0,  129,    0,
    9,   10,    7,    8,  130,    0,   13,    0,    0,    0,
  131,  132,    0,    0,   11,   12,  133,   14,    5,    0,
    0,    6,    0,    0,    0,    7,    8,  130,    5,    0,
    0,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   44,   59,   40,   59,  211,   10,   41,   45,
   41,   59,   15,   53,  178,   40,   41,   41,   41,   40,
    0,   41,   41,   41,   41,   35,   59,   41,   59,  125,
   33,   34,   43,   40,   45,   58,  125,   86,  123,   13,
   59,  123,  123,   13,   13,   79,   78,   52,   78,   60,
   61,   62,   41,  257,   57,   43,    4,   45,   41,   41,
  123,   41,   42,   43,   13,   45,   13,   47,  125,   58,
    0,   59,  276,  277,   41,   78,   59,   59,   81,   59,
   60,   61,   62,  123,  258,   33,   40,  119,   40,   99,
  100,   45,   59,   45,  268,   40,   51,   52,   40,   40,
   45,  133,  266,   45,  109,   41,    0,  139,   41,  139,
   45,   43,   41,   45,   41,   23,   45,  257,  121,  324,
  130,   59,   58,   41,   59,   58,  125,   45,   36,  132,
    0,   58,   58,   57,  258,   41,  139,   41,   41,   45,
   41,   45,   58,   59,   45,  125,   45,   41,  188,   43,
   40,   45,  192,    0,  109,  195,  320,   42,   41,  133,
  209,   44,   47,  133,  133,   59,   60,   61,   62,   41,
  266,   41,  257,   43,  208,   45,    0,  266,   41,  261,
  261,  276,  277,  193,  133,  268,  133,  262,  263,   59,
   60,   61,   62,  196,   41,  125,   43,  121,   45,    0,
   59,  257,  274,  257,  244,  262,  263,   59,  211,  257,
  213,   44,   59,   60,   61,   62,   44,  274,  173,   59,
  175,  257,  258,  178,  179,    0,  268,  230,  261,  232,
  261,  125,  257,  257,  257,  259,  257,  257,  259,  242,
   59,  281,  261,  261,  261,  248,  273,  261,  261,    0,
  257,  276,  277,  276,  277,  125,  276,  277,  269,  270,
  271,   58,   59,  262,  263,  268,  221,  270,  261,  224,
   59,  226,  196,    0,  229,  274,   59,  257,  125,   58,
  260,  261,  262,  263,  264,  265,  266,  267,   41,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  125,   44,  257,  258,  257,  258,   45,  263,  262,
  263,  266,  257,  258,   58,  257,  258,  357,  242,   41,
    0,  324,  257,  258,  125,  328,   41,  257,  257,  258,
  260,   40,  262,  263,  264,  265,  266,  267,  341,  257,
  258,  261,  272,  273,  274,  275,  276,  277,  278,  279,
  125,  257,  258,  257,  258,    0,  257,  258,  257,  258,
  262,  263,  365,  257,  257,  320,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  125,  257,  262,  263,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  262,  263,  260,  261,  262,  263,  264,  265,  266,
  267,  258,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  125,    0,  260,   58,  262,  263,
  264,  265,  266,  267,   59,  125,  262,  263,  272,  273,
  274,  275,  276,  277,  278,  279,  257,   59,    0,  260,
   58,  262,  263,  264,  265,  266,  267,   60,   61,   62,
  125,  272,  273,  274,  275,  276,  277,  278,  279,   44,
  125,  123,  257,    0,   58,  260,   58,  262,  263,  264,
  265,  266,  267,   59,   59,  262,  263,  272,  273,  274,
  275,  276,  277,  278,  279,  123,  257,  123,   41,  260,
    0,  262,  263,  264,  265,  266,  267,   59,  274,  257,
  258,  272,  273,  274,  275,  276,  277,  278,  279,   58,
  257,   44,  123,  260,  232,  262,  263,  264,  265,  266,
  267,  273,   59,   94,   95,  272,  273,  274,  275,  276,
  277,  278,  279,  123,   44,  132,  101,  102,  257,   59,
  125,  260,  139,   59,   59,  264,   41,   59,  267,   59,
  268,   40,  270,  272,  273,  257,  275,  257,  260,  278,
  260,   59,  264,  125,  264,  265,  266,   60,   61,   62,
  272,  273,  272,  273,   59,   59,  276,  277,  278,  279,
   59,  125,  257,  125,   59,  260,   59,  125,  125,  264,
  263,  266,  257,  263,   59,  260,  263,  272,  273,  264,
  265,  266,  257,  278,   59,  260,  263,  272,  273,  264,
  328,  276,  277,  278,  279,  125,  263,  272,  273,  257,
   44,  257,  260,  278,  260,  261,  264,  265,  264,   41,
   59,   43,   59,   45,  272,  273,  272,  273,  276,  277,
  278,  279,  278,  125,   59,  242,  257,  365,   41,  260,
   59,  262,  263,  264,  123,  266,  269,  270,  271,  123,
  263,  272,  273,   59,   59,   59,  262,  278,   59,   59,
  257,  123,  257,  260,   41,  260,   43,  264,   45,  264,
  265,  266,  263,   59,  272,  272,  273,  272,  273,   59,
  123,  276,  277,  278,  279,  257,  155,  156,  260,  158,
  266,  123,  264,  265,  266,   59,  125,   40,  305,  125,
  272,  273,  263,   59,  276,  277,  278,  279,   59,   41,
  257,  261,  123,  260,  123,  125,  123,  264,  265,  266,
   58,  328,    0,  330,  123,  272,  273,  125,  123,  276,
  277,  278,  279,  342,  341,  342,  123,  257,  123,  346,
  260,  133,  139,  139,  264,  265,  266,  216,    2,  133,
    4,  133,  272,  273,  208,  196,  276,  277,  278,  279,
  125,   15,  320,   43,  127,  209,  269,  270,  271,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   33,
   34,  260,  251,   -1,  253,  264,  255,  266,  267,  258,
   -1,  260,   -1,  272,  273,  257,  275,  125,  260,  278,
   -1,   -1,  264,   -1,   -1,  267,   -1,  125,   -1,   -1,
  272,  273,   -1,  275,  257,   -1,  278,  260,   -1,  262,
  263,  264,  291,   -1,   -1,  257,   -1,   81,  260,  272,
  273,  263,  264,  125,   -1,  278,  305,   -1,   -1,   -1,
  272,  273,   -1,   -1,   -1,   -1,  278,   -1,  257,   -1,
  257,  260,  261,  260,   -1,  264,  263,  264,  125,   -1,
   -1,  330,   -1,  272,  273,  272,  273,   -1,   -1,  278,
  257,  278,  257,  260,  261,  260,   -1,  264,  125,  264,
   -1,   -1,   -1,   -1,   -1,  272,  273,  272,  273,   -1,
   -1,  278,  257,  278,   -1,  260,   -1,  125,   -1,  264,
  265,   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,
  265,  276,  277,  278,  279,   -1,  125,  272,  273,   -1,
   -1,  276,  277,  278,  279,   -1,   -1,   -1,   -1,  257,
   -1,   -1,  260,  125,   -1,   -1,  264,  265,   -1,  257,
   -1,   -1,  260,  125,  272,  273,  264,  265,  276,  277,
  278,  279,   -1,  125,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,   -1,   -1,  278,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,
  257,  278,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,   -1,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,
  262,  263,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,
  272,  273,  264,   -1,   -1,  257,  278,   -1,  260,   -1,
  272,  273,  264,   -1,   -1,  257,  278,   -1,  260,   -1,
  272,  273,  264,  265,  266,   -1,  278,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,
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
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return :",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"sentencia_funcion_con_return : sentencia_declarativa",
"sentencia_funcion_con_return : sentencia_ejecutable",
"sentencia_funcion_con_return : sentencia_when_con_return",
"sentencia_funcion_con_return : DEFER sentencia_when_con_return",
"sentencia_funcion_con_return : sentencia_do_con_return",
"sentencia_funcion_con_return : DEFER sentencia_do_con_return",
"sentencia_funcion_con_return : sentencia_seleccion_simple_con_return",
"sentencia_funcion_con_return : DEFER sentencia_seleccion_simple_con_return",
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
"sentencia_when : WHEN '(' condicion ')' THEN '{' sentencias_when '}' ';'",
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}' ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' THEN '{' sentencias_when '}' ';' $$1 WHEN '(' condicion ')' THEN '{' sentencias_when '}'",
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

//#line 364 ".\gramatica.y"

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
//#line 809 "Parser.java"
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
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 48:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 53:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 54:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 55:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 56:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 57:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 58:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 59:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 60:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 62:
//#line 145 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 63:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 64:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 65:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 68:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 72:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 73:
//#line 168 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 74:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 75:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 79:
//#line 180 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 80:
//#line 181 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 81:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 95:
//#line 205 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 96:
//#line 206 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 97:
//#line 207 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 98:
//#line 208 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 99:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 100:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 101:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 104:
//#line 223 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 105:
//#line 224 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 106:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 107:
//#line 226 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 108:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 114:
//#line 245 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 116:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 117:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 118:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 119:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 120:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 123:
//#line 263 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 124:
//#line 264 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 125:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 126:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 128:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 129:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 130:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 131:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 132:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 133:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 134:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 137:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 138:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 141:
//#line 284 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 142:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 147:
//#line 295 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 148:
//#line 296 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 170:
//#line 342 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 171:
//#line 343 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 344 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 173:
//#line 345 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 174:
//#line 346 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
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
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 178:
//#line 350 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 355 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1286 "Parser.java"
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
