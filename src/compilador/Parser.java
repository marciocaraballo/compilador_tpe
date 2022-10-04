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
    7,    8,   14,   15,   15,   15,   16,   16,   20,   20,
   17,   17,   22,   24,   24,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   18,   13,   26,   26,   26,
   29,   29,   28,   28,    9,    9,    9,   30,   30,   31,
   31,   31,   31,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   37,   37,   37,   38,   38,   38,   38,
   38,   39,   39,   36,   36,   40,   40,   40,   40,   40,
   23,   41,   41,   25,   25,   32,   32,   32,   35,   35,
   43,   35,   42,   42,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   33,   33,
   44,   44,   44,   44,   21,   21,   19,   19,   46,   19,
   45,   45,   45,   45,   45,   45,   27,   27,   27,   47,
   47,   47,   48,   48,   48,   50,   51,   51,   52,   52,
   34,   34,   34,   34,   34,   34,   34,   34,   34,   49,
   49,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    1,    2,    3,    9,   10,    1,    4,
    1,    3,    7,    1,    4,    6,    7,    5,    5,    5,
    5,    6,    6,    6,    6,    5,    2,    1,    3,    5,
    1,    3,    2,    1,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    1,    2,    8,   10,    7,    7,    7,    7,
    7,    7,    9,    9,    9,    9,    9,    9,    9,    8,
    1,    3,    2,    2,    1,    2,    3,    2,    0,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  163,  162,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   64,   66,   68,   70,
   72,   84,    0,    0,    0,    0,    0,  160,    0,    0,
    0,    0,    0,    0,  142,  144,  145,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   74,   92,   75,
   76,    0,   65,   67,   69,   71,   73,    0,   57,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,   97,    0,   19,    0,  161,    0,    0,    0,
  131,  132,  133,    0,    0,  134,  135,  136,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   77,    0,   82,
    0,   94,    0,   62,    0,   55,    0,   15,    0,    0,
    0,    0,    0,    0,   23,   24,    0,   31,    0,   85,
    1,   96,  149,  150,    0,  147,    0,    0,    0,    0,
    0,    0,    0,    0,  140,  141,  156,  154,  157,    0,
  155,    0,  153,    0,    0,    0,    0,   53,    0,    0,
    0,    0,    0,    0,   91,   81,    0,   93,   95,    0,
    0,   60,   59,    0,    0,    0,   34,    0,    0,   47,
   21,   22,   25,    0,    0,  146,    0,    0,    0,    0,
    0,    0,    0,    0,  152,  151,    0,   41,    0,    0,
   40,    0,    0,   39,    0,    0,    0,    0,   78,    0,
    0,    0,    0,    0,    0,    0,   26,   32,  148,  125,
    0,  123,  126,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   36,    0,   45,   44,    0,   43,
    0,    0,    0,    0,   88,   90,    0,   89,    0,    0,
    0,    0,  122,    0,  109,    0,  107,    0,    0,  112,
    0,    0,    0,  110,    0,  108,   37,    0,   50,    0,
    0,    0,    0,   86,    0,    0,   35,    0,    0,    0,
  120,    0,    0,    0,  105,    0,    0,    0,  101,    0,
  100,    0,   29,    0,   46,    0,  115,  113,  117,  118,
    0,  116,  114,   52,    0,   99,    0,    0,    0,   33,
  106,    0,    0,    0,    0,    0,    0,   30,    0,    0,
   27,    0,   28,    0,    0,    0,    0,  102,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  133,  134,  135,  136,  137,  303,   41,  304,
  199,  138,   26,  188,  121,  112,   42,  113,  279,   70,
   71,   27,   28,   29,   30,   31,   59,   60,   61,   32,
   62,  252,  315,  203,   99,   43,   44,   45,   46,   47,
  145,  146,
};
final static short yysindex[] = {                      -120,
    0,  940,    0,  569,  -33,   15,   12,   -4,    9,  635,
    0,    0,   59,  -51,  725,    0,    0,    0,    0,    0,
    0,    0,  -50,  -43,  -85,   70,    0,    0,    0,    0,
    0,    0,  735,  759,   41, -201,   61,    0, -181,   20,
   72,   -3,   35,   84,    0,    0,    0,   89,  102,   31,
  -34,  -18,   37, -115,  115,  101,  210,    0,    0,    0,
    0, -126,    0,    0,    0,    0,    0, -169,    0,  119,
  136,    0,    0,  140,    0,  128,    0,  965,  -77,    0,
  786,    0,    0,  354,    0,   43,    0,  -56,  -12,  -44,
    0,    0,    0,   45,   45,    0,    0,    0,   45,   45,
   45,   45,  151,  153,  -16,   -8,  181,    0,  -21,  141,
    7,  229,  213,  225,  244,   46,  268,    0,  -49,    0,
 -103,    0,   22,    0,   60,    0,   65,    0,   24,  616,
  433,  930,  192,  232,    0,    0,  108,    0,  307,    0,
    0,    0,    0,    0,    6,    0,  715,  715,  579,  715,
   84,   84,   62,   62,    0,    0,    0,    0,    0,  328,
    0,  331,    0,  340,  -62,   29,  -62,    0,  342, -183,
  -62,  352,  289, -109,    0,    0,  355,    0,    0,   39,
  374,    0,    0,   20,  377,  210,    0,  144,  108,    0,
    0,    0,    0,  154,   43,    0,  375,    0,  890,   -2,
   64,  502,   66,   91,    0,    0,  -62,    0,  382,  -62,
    0,  -62,  397,    0,  -62,  940,  321,  940,    0,  406,
    4,  410,  -10,   45,  871,  431,    0,    0,    0,    0,
  901,    0,    0,  715,  414,  715,  416,  658,  417,   93,
  715,  419,  715,  420,    0,  -62,    0,    0, -183,    0,
  940,  356,  940,  359,    0,    0,  421,    0,  677,  168,
  361,   45,    0,  228,    0,  231,    0,  442,  240,    0,
  696,  445,  252,    0,  254,    0,    0,  448,    0,  940,
  459,  394,  465,    0,  399,  473,    0,  492,  485,  488,
    0,  495,  496,  293,    0,  498,  507, -183,    0,  508,
    0,  375,    0,   97,    0,  510,    0,    0,    0,    0,
  511,    0,    0,    0,  303,    0,  761, -111,  523,    0,
    0,  543,  460,  375,  332,  108,   45,    0,  788,  525,
    0,  553,    0,  335,  476,  940,  475,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  216,   42,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,    0,    0,   42,
    0,    0,    0,  106,    0,    0,    0,    0,    0,    0,
    0,    0,   42,  544,  810,  829,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  409,    0,  364,
  472,    0,    0,  513,    0,  545,    0,    0,    0,    0,
  610,    0,    0,  129,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   42,    0,  443,    0,    0,    0,   42,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  159,  185,  -20,  -13,    0,    0,    0,    0,    0,  233,
    0,  263,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  848,    0,    0,   42,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -81,    0,    0,
    0,    0,    0,    0,    0,    0,  489,    0,    0,    0,
    0,    0,   67,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   99,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  499,    0,    0,    0,    0,
  486,    0,    0,    0,    0,    0,  286,    0,    0,    0,
    0,   42,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  314,    0,    0,    0,    0,    0,   69,    0,  501,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  341,    0,    0,    0,    0,    0,   99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    1,  -15,    0,   -9,    0,    0,    0,    0,  660,
   18,    0,    0,    0,    0,    0,  500,  384,  -38,  305,
 -179,  439,    5,    0,  441,  -28,   63, -140,  336,  517,
    0,   13,   19,   40,   55,   58,  -30,    0,    0,  -75,
    0, -142,    0,  950,  602,    0,  197,  223,  -73,    0,
    0,  451,
};
final static int YYTABLESIZE=1244;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
   58,   89,    2,  140,   34,  109,  110,   69,   75,  176,
   36,  324,  144,  218,  117,   77,  128,  231,   73,  164,
  127,  178,  114,  115,  160,   63,  122,  130,  149,  213,
  259,   64,  162,   81,  128,   52,  165,   78,  127,   94,
   76,   95,  159,  125,  257,  130,  196,   58,   53,  195,
  161,   50,   65,   85,   40,   74,   97,   98,   96,   39,
   88,  180,  256,  184,   39,   73,   39,   66,   39,  209,
   67,  107,  143,  108,   48,  254,   87,  116,  132,  220,
  166,   39,  139,   39,  181,   39,  210,   39,  124,   39,
  179,   48,   11,   12,   97,   98,   96,   84,  125,   83,
   86,  129,  129,  129,   94,  139,   95,   49,  278,   51,
  282,   58,   90,  143,  143,  143,   73,  143,  140,  143,
   58,  144,  317,  177,   49,  101,   51,   79,   98,  103,
  102,  143,  143,  143,  143,  139,    1,  198,  198,  198,
  198,  221,  104,   63,  329,  223,  139,  123,  139,   64,
  139,  217,   35,   54,  185,  122,    6,  278,  137,  120,
    7,  153,  154,   55,  139,  139,  139,  139,    9,   10,
   65,   56,  119,  118,   13,  125,   58,  126,  125,  127,
  121,  121,  125,   36,  138,   66,  128,  230,   67,  233,
  125,  125,  198,  337,  179,   10,  125,  143,  167,  137,
  251,  137,  251,  137,  147,   68,   74,  175,  286,  157,
   94,  158,   95,   11,   12,   58,  150,  137,  137,  137,
  137,  233,  108,  288,  198,  138,  198,  138,  198,  138,
  139,  198,  159,  198,   35,  108,  128,  251,  108,  163,
  127,   11,   12,  138,  138,  138,  138,  130,  148,  198,
  148,  280,   51,   98,   11,   12,  170,   11,   12,  234,
  235,  198,  158,  168,   73,   91,   92,   93,   48,  169,
   49,   37,   38,   91,   20,  198,   37,   38,   37,   38,
   37,   38,  171,  137,  172,   87,  260,  105,  332,  106,
  151,  152,  230,   37,   38,   37,   38,   37,   38,  143,
   38,   37,   38,   91,   92,   93,  173,  233,  174,  138,
  129,  129,  129,  111,  230,   54,  191,  182,    6,  233,
  251,   68,    7,  155,  156,  236,  237,  241,  242,  143,
    9,   10,  143,  143,  143,  143,  143,  143,  143,  143,
  119,  143,  143,  143,  143,  143,  143,  143,  143,  143,
  143,  143,  243,  244,  271,  272,  192,  159,  318,  319,
  124,  124,  139,   56,  194,  139,  139,  139,  139,  139,
  139,  139,  139,  185,  139,  139,  139,  139,  139,  139,
  139,  139,  139,  139,  139,   98,  205,  158,   98,  206,
   98,   98,   98,   98,   98,   98,   94,  207,   95,  212,
   98,   98,   98,   98,   98,   98,   98,   98,   63,  215,
   87,  216,  142,  219,  222,  137,  224,  226,  137,  137,
  137,  137,  137,  137,  137,  137,  130,  137,  137,  137,
  137,  137,  137,  137,  137,  137,  137,  137,  111,  246,
  249,  138,   61,  253,  138,  138,  138,  138,  138,  138,
  138,  138,   63,  138,  138,  138,  138,  138,  138,  138,
  138,  138,  138,  138,  255,  119,   54,   63,  258,    6,
  262,   58,  265,    7,  267,  270,   55,  274,  276,  284,
  281,    9,   10,  283,   56,  287,   61,   13,   56,  159,
  289,  298,  159,  290,  159,  159,  159,  159,  159,  159,
  291,   61,  292,  295,  159,  159,  159,  159,  159,  159,
  159,  159,   20,  187,  296,  190,  297,  299,  300,  158,
  193,  302,  158,  301,  158,  158,  158,  158,  158,  158,
   58,  305,  306,   63,  158,  158,  158,  158,  158,  158,
  158,  158,   87,  307,   16,   87,  308,   87,   87,   87,
   87,   87,   87,  309,  310,  311,  312,   87,   87,   87,
   87,   87,   87,   87,   87,  313,  316,   61,  320,  321,
  111,   20,  227,  111,  322,  111,  111,  111,  111,  111,
  111,  326,  327,  333,  328,  111,  111,  111,  111,  111,
  111,  111,  111,  334,  330,  335,   58,  119,  336,  338,
  119,   91,  119,  119,  119,  119,  119,  119,  261,    4,
  103,   38,  119,  119,  119,  119,  119,  119,  119,  119,
   56,   42,  325,   56,  197,  104,  225,   56,   56,   56,
  189,   54,  228,  314,    6,   56,   56,   20,    7,   56,
   56,   56,   56,  183,  100,  229,    9,   10,    0,    0,
    0,    0,   13,    0,    0,   54,    0,    0,    6,    0,
  238,  239,    7,    0,  185,   63,    0,    0,   63,   16,
    9,   10,   63,   63,   63,    0,   13,    0,    0,    0,
   63,   63,    0,    0,   63,   63,   63,   63,    0,   54,
    0,   33,    6,    0,    0,    0,    7,    0,    0,   61,
  323,  197,   61,    0,    9,  130,   61,   61,   61,  331,
  111,  111,  323,    0,   61,   61,    0,    0,   61,   61,
   61,   61,    0,    0,    0,    0,    0,    0,   58,    0,
    0,   58,    0,    0,    0,   58,   58,   58,  186,    0,
    0,    0,    0,   58,   58,    0,    0,   58,   58,   58,
   58,    0,    0,    0,    0,    0,    0,   57,   54,    0,
    0,    6,    0,  238,  239,    7,    0,    0,  111,   20,
    0,    0,   20,    9,   10,    0,   20,   20,   20,   13,
  197,    0,    0,    0,   20,   20,    0,    0,   20,   20,
   20,   20,    0,    0,    0,    0,    0,    0,    0,  197,
    0,   16,    0,    0,   16,    0,    0,    0,   16,   16,
   16,    0,    0,    0,    0,    0,   16,   16,  197,    0,
   16,   16,   16,   16,  208,    5,  211,    0,    6,  111,
  214,    0,    7,    8,    0,   54,    0,  197,    6,  202,
    9,   10,    7,    0,   11,   12,   13,   14,    0,   72,
    9,   10,    0,    0,    0,    0,   13,    0,    0,   80,
    0,    0,    0,    0,    0,    0,  245,    0,    0,  247,
    0,  248,   54,    0,  250,    6,    0,    0,    0,    7,
    0,  185,   55,   82,    0,  263,    0,    9,   10,    0,
   56,   54,    0,   13,    6,    0,    0,    0,    7,    0,
    0,   55,    0,    0,    0,  277,    9,   10,  111,   56,
  141,    0,   13,    0,   54,    0,    0,    6,    0,    0,
  268,    7,    0,    0,    0,    0,    0,    0,    0,    9,
   10,    0,    0,   54,   79,   13,    6,  285,    0,    0,
    7,    0,    0,    0,    0,    0,    0,    0,    9,   10,
    0,    0,   54,   83,   13,    6,    0,  111,  293,    7,
    0,    0,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   54,   80,   13,    6,    0,    0,    0,    7,    0,
    0,    5,    0,    0,    6,    0,    9,   10,    7,    8,
    0,    5,   13,    0,    6,  178,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,  232,    5,    0,   54,    6,    0,
    6,    0,    7,    8,    7,  263,  185,    0,    0,    0,
    9,   10,    9,   10,   11,   12,   13,   14,   13,    0,
    0,    0,    5,    0,   54,    6,    0,    6,    0,    7,
    8,    7,    0,  185,    0,    0,    0,    9,   10,    9,
   10,   11,   12,   13,   14,   13,   79,    0,    0,   79,
    0,    0,    0,   79,    0,   79,   79,    0,    0,    0,
    0,   79,   79,   79,   79,   83,    0,   79,   83,    0,
    0,    0,   83,    0,   83,   83,  200,  201,    0,  204,
   83,   83,   83,   83,   80,    0,   83,   80,    0,    0,
    0,   80,    0,   80,   80,    0,    0,    0,    0,   80,
   80,   80,   80,    0,    0,   80,    0,   54,    0,    0,
    6,    0,    0,    0,    7,    0,  185,   55,    0,    0,
    0,    0,    9,   10,    0,   56,   54,    0,   13,    6,
    0,  240,    0,    7,    0,    0,    0,   54,    0,    0,
    6,    9,   10,    0,    7,    0,    0,   13,    0,    0,
    0,    0,    9,   10,    0,    0,    0,    0,   13,    0,
    0,    0,    0,  264,    0,  266,    5,  269,    0,    6,
  273,    0,  275,    7,    8,  185,    5,    0,    0,    6,
    0,    9,   10,    7,    8,   11,   12,   13,   14,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,    0,
  294,    5,    0,    0,  129,    0,    0,    0,    7,    8,
    0,    0,    0,    0,  240,    0,    9,  130,    0,    0,
   11,   12,  131,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   10,   40,  123,   79,    4,   40,   41,   59,   59,   59,
   44,  123,   86,  123,   53,   59,   41,  197,   34,   41,
   41,  125,   41,   52,   41,   13,   57,   41,   41,  170,
   41,   13,   41,   33,   59,   40,   58,  123,   59,   43,
   23,   45,   59,  125,   41,   59,   41,   57,   40,   44,
   59,   40,   13,   36,   40,  257,   60,   61,   62,   45,
   41,   40,   59,   40,   45,   81,   45,   13,   45,   41,
   13,   41,    0,  257,   41,  218,  258,   41,   78,   41,
  109,   45,   78,   45,  123,   45,   58,   45,  258,   45,
  121,   58,  276,  277,   60,   61,   62,   35,  268,   59,
   40,   60,   61,   62,   43,    0,   45,   41,  249,   41,
  253,  121,   41,   41,   42,   43,  132,   45,  194,   47,
  130,  195,  302,  119,   58,   42,   58,   58,    0,   41,
   47,   59,   60,   61,   62,  131,  257,  147,  148,  149,
  150,  180,   41,  131,  324,  184,   41,  274,   43,  131,
   45,  261,  268,  257,  266,  186,  260,  298,    0,   59,
  264,   99,  100,  267,   59,   60,   61,   62,  272,  273,
  131,  275,   58,   59,  278,  257,  186,   59,  260,   44,
  262,  263,  264,   44,    0,  131,   59,  197,  131,  199,
  272,  273,  202,  336,  225,  273,  278,  125,   58,   41,
  216,   43,  218,   45,  261,  257,  257,  257,   41,   59,
   43,   59,   45,  276,  277,  225,  261,   59,   60,   61,
   62,  231,  257,  262,  234,   41,  236,   43,  238,   45,
  125,  241,    0,  243,  268,  257,  261,  253,  257,   59,
  261,  276,  277,   59,   60,   61,   62,  261,  261,  259,
  261,  251,  257,  125,  276,  277,   44,  276,  277,  262,
  263,  271,    0,  257,  280,  269,  270,  271,  257,   41,
  259,  257,  258,   58,   59,  285,  257,  258,  257,  258,
  257,  258,   58,  125,   41,    0,  224,  257,  327,  259,
   94,   95,  302,  257,  258,  257,  258,  257,  258,  257,
  258,  257,  258,  269,  270,  271,  261,  317,   41,  125,
  269,  270,  271,    0,  324,  257,  125,  258,  260,  329,
  336,  257,  264,  101,  102,  262,  263,  262,  263,  257,
  272,  273,  260,  261,  262,  263,  264,  265,  266,  267,
    0,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  262,  263,  262,  263,  125,  125,  262,  263,
  262,  263,  257,    0,   58,  260,  261,  262,  263,  264,
  265,  266,  267,  266,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  257,   59,  125,  260,   59,
  262,  263,  264,  265,  266,  267,   43,   58,   45,   58,
  272,  273,  274,  275,  276,  277,  278,  279,    0,   58,
  125,  123,   59,   59,   41,  257,   40,  274,  260,  261,
  262,  263,  264,  265,  266,  267,  273,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,   58,
   44,  257,    0,  123,  260,  261,  262,  263,  264,  265,
  266,  267,   44,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,   59,  125,  257,   59,   59,  260,
   40,    0,   59,  264,   59,   59,  267,   59,   59,   59,
  125,  272,  273,  125,  275,  125,   44,  278,  125,  257,
  263,   44,  260,  263,  262,  263,  264,  265,  266,  267,
   59,   59,  263,   59,  272,  273,  274,  275,  276,  277,
  278,  279,    0,  130,  263,  132,  263,   59,  125,  257,
  137,  123,  260,   59,  262,  263,  264,  265,  266,  267,
   59,   59,   41,  125,  272,  273,  274,  275,  276,  277,
  278,  279,  257,   59,    0,  260,   59,  262,  263,  264,
  265,  266,  267,   59,   59,  263,   59,  272,  273,  274,
  275,  276,  277,  278,  279,   59,   59,  125,   59,   59,
  257,   59,  189,  260,  272,  262,  263,  264,  265,  266,
  267,   59,   40,   59,  125,  272,  273,  274,  275,  276,
  277,  278,  279,   41,  263,  261,  125,  257,  123,  125,
  260,   58,  262,  263,  264,  265,  266,  267,  225,    0,
  125,  123,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  123,  318,  260,  123,  125,  186,  264,  265,  266,
  131,  257,  194,  298,  260,  272,  273,  125,  264,  276,
  277,  278,  279,  127,   43,  195,  272,  273,   -1,   -1,
   -1,   -1,  278,   -1,   -1,  257,   -1,   -1,  260,   -1,
  262,  263,  264,   -1,  266,  257,   -1,   -1,  260,  125,
  272,  273,  264,  265,  266,   -1,  278,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,
   -1,  123,  260,   -1,   -1,   -1,  264,   -1,   -1,  257,
  317,  123,  260,   -1,  272,  273,  264,  265,  266,  326,
   51,   52,  329,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,  266,  123,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,   -1,   -1,   -1,   -1,   -1,  123,  257,   -1,
   -1,  260,   -1,  262,  263,  264,   -1,   -1,  109,  257,
   -1,   -1,  260,  272,  273,   -1,  264,  265,  266,  278,
  123,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
  266,   -1,   -1,   -1,   -1,   -1,  272,  273,  123,   -1,
  276,  277,  278,  279,  165,  257,  167,   -1,  260,  170,
  171,   -1,  264,  265,   -1,  257,   -1,  123,  260,  261,
  272,  273,  264,   -1,  276,  277,  278,  279,   -1,  125,
  272,  273,   -1,   -1,   -1,   -1,  278,   -1,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,  207,   -1,   -1,  210,
   -1,  212,  257,   -1,  215,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,  125,   -1,  125,   -1,  272,  273,   -1,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,   -1,  246,  272,  273,  249,  275,
  125,   -1,  278,   -1,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  257,  125,  278,  260,  261,   -1,   -1,
  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  257,  125,  278,  260,   -1,  298,  263,  264,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  257,  125,  278,  260,   -1,   -1,   -1,  264,   -1,
   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
   -1,  257,  278,   -1,  260,  125,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  125,  257,   -1,  257,  260,   -1,
  260,   -1,  264,  265,  264,  125,  266,   -1,   -1,   -1,
  272,  273,  272,  273,  276,  277,  278,  279,  278,   -1,
   -1,   -1,  257,   -1,  257,  260,   -1,  260,   -1,  264,
  265,  264,   -1,  266,   -1,   -1,   -1,  272,  273,  272,
  273,  276,  277,  278,  279,  278,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,  147,  148,   -1,  150,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,  278,   -1,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,   -1,  275,  257,   -1,  278,  260,
   -1,  202,   -1,  264,   -1,   -1,   -1,  257,   -1,   -1,
  260,  272,  273,   -1,  264,   -1,   -1,  278,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,   -1,
   -1,   -1,   -1,  234,   -1,  236,  257,  238,   -1,  260,
  241,   -1,  243,  264,  265,  266,  257,   -1,   -1,  260,
   -1,  272,  273,  264,  265,  276,  277,  278,  279,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,
  271,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
   -1,   -1,   -1,   -1,  285,   -1,  272,  273,   -1,   -1,
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
"sentencia_declarativa : funcion_return_simple",
"sentencia_declarativa : funcion_sentencias_con_return",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa_variables : tipo lista_de_variables ';'",
"sentencia_declarativa_variables : tipo lista_de_variables",
"sentencia_declarativa_variables : lista_de_variables ';'",
"sentencia_declarativa_variables : tipo ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion_return_simple : encabezado_funcion '{' cuerpo_funcion_return_simple '}'",
"funcion_sentencias_con_return : encabezado_funcion '{' cuerpo_funcion_sentencias_con_return '}'",
"cuerpo_funcion_sentencias_con_return : sentencia_ejecutable_con_return",
"sentencia_ejecutable_con_return : seleccion_con_return",
"sentencia_ejecutable_con_return : sentencia_do_con_return sentencia_return",
"sentencia_ejecutable_con_return : DEFER sentencia_do_con_return sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';' sentencia_return",
"seleccion_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion_con_return : sentencia_return",
"bloque_sentencias_ejecutables_seleccion_con_return : '{' sentencias_ejecutables sentencia_return '}'",
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
"cuerpo_funcion_return_simple : sentencias sentencia_return",
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

//#line 330 ".\gramatica.y"

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
//#line 789 "Parser.java"
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
case 27:
//#line 75 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 28:
//#line 76 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 33:
//#line 90 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 50:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 54:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 55:
//#line 136 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 56:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 57:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 61:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 62:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 63:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 77:
//#line 173 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 78:
//#line 174 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 79:
//#line 175 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 80:
//#line 176 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 81:
//#line 177 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 82:
//#line 181 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 83:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 86:
//#line 191 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 87:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 88:
//#line 193 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 89:
//#line 194 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 90:
//#line 195 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 96:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 97:
//#line 214 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 98:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 99:
//#line 219 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 100:
//#line 220 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 101:
//#line 221 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 102:
//#line 222 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 105:
//#line 231 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 106:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 107:
//#line 233 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 108:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 109:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 110:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 111:
//#line 237 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 112:
//#line 238 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 113:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 114:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 115:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 116:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 117:
//#line 243 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 118:
//#line 244 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 119:
//#line 245 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 120:
//#line 246 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 123:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 124:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 129:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 130:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 151:
//#line 309 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 152:
//#line 310 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 153:
//#line 311 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 154:
//#line 312 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 155:
//#line 313 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 156:
//#line 314 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 157:
//#line 315 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 158:
//#line 316 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 159:
//#line 317 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 161:
//#line 322 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1222 "Parser.java"
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
