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
    7,    7,    7,   14,   14,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,    8,    8,   19,   19,   19,
   19,   18,   18,   16,   22,   22,   17,   17,   24,   24,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   13,   13,   13,   13,   13,   27,   27,   27,   30,   30,
   29,   29,   31,   29,    9,    9,    9,   32,   32,   33,
   33,   33,   33,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,   39,   39,   39,   40,   40,   40,   40,
   40,   41,   41,   38,   38,   42,   42,   42,   42,   42,
   25,   43,   43,   26,   26,   34,   34,   34,   37,   37,
   44,   37,   37,   37,   23,   23,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   35,   35,   45,   45,   45,   45,   45,   21,   21,   20,
   20,   20,   46,   46,   46,   46,   46,   46,   28,   28,
   28,   47,   47,   47,   48,   48,   48,   50,   50,   51,
   51,   52,   52,   36,   36,   36,   36,   36,   36,   36,
   36,   36,   49,   49,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    4,    5,   10,   13,   13,
   16,    8,   11,    9,    1,    2,    7,    9,    1,    4,
    6,    7,    5,    5,    5,    5,    6,    6,    6,    6,
    5,    4,    3,    3,    4,    1,    3,    5,    1,    3,
    2,    1,    0,    2,    3,    2,    2,    1,    3,    3,
    2,    2,    1,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    1,    1,    2,    4,    1,    3,
    3,    2,    1,    1,    3,    7,    6,    6,    6,    6,
    1,    1,    3,    1,    2,    4,    3,    3,    9,    8,
    0,   17,    7,    6,    1,    2,    8,   10,    7,    7,
    7,    7,    7,    7,    9,    9,    9,    9,    9,    9,
    9,    8,    1,    3,    2,    2,    2,    1,    2,    3,
    2,    2,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    3,    4,    1,
    3,    1,    1,    5,    5,    4,    4,    4,    4,    4,
    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  186,  185,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   84,   86,   88,   90,
   92,  104,    0,    0,    0,    0,    0,  183,  153,  154,
  155,    0,    0,  156,  157,  158,    0,    0,    0,    0,
  164,  166,  167,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,  112,   95,   96,    0,   85,   87,
   89,   91,   93,    0,   77,    0,    0,    2,    8,    0,
   18,    0,   17,    0,    0,    5,    0,    3,  117,    0,
   19,    0,  184,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   97,    0,  102,
    0,  114,    0,   82,    0,   75,    0,   15,    0,    0,
    0,    0,    0,   23,   26,   27,    0,    0,   24,   28,
   30,   32,    0,    0,  105,    1,  116,  172,  168,  173,
    0,  170,    0,    0,    0,    0,    0,    0,    0,  162,
  163,  179,  177,  180,    0,  178,    0,  176,    0,    0,
    0,    0,   71,    0,    0,   74,    0,    0,    0,    0,
  111,  101,    0,  113,  115,    0,    0,   80,   79,    0,
    0,    0,    0,    0,   49,    0,   29,   31,   33,   35,
   21,    0,   25,    0,   36,    0,    0,  169,    0,    0,
    0,    0,    0,    0,    0,    0,  175,  174,    0,   56,
    0,    0,   55,    0,    0,   54,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    0,   22,   37,    0,  171,  147,  148,    0,  145,  149,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,    0,   60,   59,    0,   58,    0,    0,    0,
  124,    0,  108,  110,    0,  109,    0,   65,    0,    0,
    0,    0,    0,  144,    0,  131,    0,  129,    0,    0,
  134,    0,    0,    0,  132,    0,  130,   52,    0,   68,
  126,    0,    0,    0,  106,    0,   61,    0,   50,    0,
    0,    0,    0,  142,    0,    0,    0,  127,    0,    0,
    0,  121,    0,  120,    0,    0,    0,    0,    0,  137,
  135,  139,  140,    0,  138,  136,   70,    0,  119,    0,
    0,    0,   45,    0,    0,   47,    0,  128,    0,    0,
    0,    0,   42,    0,   46,    0,    0,    0,    0,    0,
   44,   48,    0,    0,    0,    0,   38,    0,    0,    0,
   43,    0,    0,    0,    0,    0,    0,    0,   39,   40,
    0,    0,  122,    0,   41,
};
final static short yydgoto[] = {                          3,
    4,   15,  268,   17,  210,   19,   20,   21,   22,   23,
   24,   25,  195,  138,  139,  140,  141,  142,  143,   47,
  211,  344,  269,  196,   26,  121,  111,   48,  112,  300,
  113,   76,   77,   27,   28,   29,   30,   31,   65,   66,
   67,   32,   68,  338,  215,   49,   50,   51,   52,   53,
  151,  152,
};
final static short yysindex[] = {                       -68,
    0, 1123,    0,   83,  -41,   60,    8,   16,   18,  674,
    0,    0,  508,  -39,  787,    0,    0,    0,    0,    0,
    0,    0,   -5,   32,  -17,   70,    0,    0,    0,    0,
    0,    0,  810,  820,  -38, -126,   97,    0,    0,    0,
    0, -118,  603,    0,    0,    0,  109,   -9,   26,   96,
    0,    0,    0,  131,  140,   21,  -29,  -26,  634,  -80,
   94,  127,  747,    0,    0,    0,    0,  -72,    0,    0,
    0,    0,    0, -174,    0,  132,  161,    0,    0,  169,
    0,  170,    0,  777,  -14,    0,  843,    0,    0,   90,
    0,  -35,    0,   27,   -3,   45,   26,   26,   26,   -6,
   26,   26,  210,  220,    1,   31,  234,  -32,  163,   46,
  275,  282,   89,  269,  295,   91,  309,    0,   17,    0,
  984,    0,   63,    0,  100,    0,  106,    0,  596,   19,
  331,  621,  866,    0,    0,    0,  259, 1067,    0,    0,
    0,    0,  273,  362,    0,    0,    0,    0,    0,    0,
   66,    0,  767,  767,  701,  767,   96,   96,   -6,    0,
    0,    0,    0,    0,  363,    0,  367,    0,  371, -121,
    5, -121,    0,  381, -121,    0, -121,  394,  352,  -95,
    0,    0,  419,    0,    0,  656,  444,    0,    0,  603,
   24,  296,  634,  747,    0,  224,    0,    0,    0,    0,
    0,  379,    0,  389,    0,  258,   28,    0, 1028,    0,
 1048,   82,  103,  691,  107,  162,    0,    0, -121,    0,
  475, -121,    0, -121,  496,    0, -121, 1123,  420,  868,
    0,  483,   42,  495,    2,  498,  378,    0,  514,  965,
  518,    0,    0,  621,    0,    0,    0, 1050,    0,    0,
  767,  500,  767,  501,  718,  502,  225,  767,  504,  767,
  505,    0, -121,    0,    0, -121,    0, 1123,  441, 1123,
    0,  448,    0,    0,  516,    0,  728,    0,  517,  -82,
  461,  600,  322,    0,  334,    0,  335,    0,  540,  347,
    0,  745,  560,  357,    0,  358,    0,    0,  588,    0,
    0,  574,  510,  578,    0,  486,    0,  519,    0,  602,
  609,  595,  607,    0,  608,  610,  396,    0,  611,  612,
 -121,    0,  613,    0, 1028,  249, 1090,  617,  600,    0,
    0,    0,    0,  618,    0,    0,    0,  408,    0, 1017,
 -104,  622,    0,  543, -101,    0,  641,    0,  643,  561,
 -146,  422,    0,  629,    0,  630,  600,  261,  651,  631,
    0,    0,  657,  -83,  648,  577,    0,  447, -146,  446,
    0,  449,  587,  651,  652,  654, 1123,  589,    0,    0,
  590,  457,    0,  662,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  152,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  116,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  139,
    0,    0,    0,    0,    0,    0,  466,  466,    0,  667,
  891,  910,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  497,    0,  374,  427,    0,    0,  553,
    0,  462,    0,    0,    0,    0,  729,    0,    0,   35,
    0,    0,    0,    0,    0,    0,    0,    0,  -37,  -36,
    0,    0,    0,    0,    0,    0,    0,  466,    0,   55,
    0,   39,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  530,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1113,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  171,  194,  -12,    0,
    0,    0,    0,    0,  217,    0,  243,    0,    0,    0,
    0,    0,    0,    0,  466,    0,    0,    0,    0,    0,
    0,    0,  932,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1113,    0,    0,    0,    0,    0, 1006,
    0,    0,    0,    0,    0,    0,    0,    0,  614,    0,
    0,    0,    0,    0,   71,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  263,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  619,    0,    0,  466,    0,  -98,    0,    0,
    0,    0,    0,    0,  272,    0,    0,    0,  239,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  305,    0,    0,    0,    0,    0,   76,    0,
    0,    0,    0,  328,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  466,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  351,    0,    0,    0,    0,    0,  263,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   53,  320,  -34,   -2,    0,    0,    0,    0,  616,
   79,    0,  -70,    0,  592,  598,  599,  620,   36,  -42,
 -184,    0, -181,  489,  -40,  542,  -13,   43, -157,  424,
    0,  624,    0,   54,   57,   62,   64,   75,  -47,    0,
    0,  -52,    0,    0,  958,  699,  193,  426,  -62,    0,
    0,  548,
};
final static int YYTABLESIZE=1402;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   95,   18,   36,  151,  152,  149,   42,   64,  169,   42,
  108,  109,   18,  137,  114,  122,  117,  225,  351,   75,
   89,  151,  152,  323,  248,  170,  125,  230,  150,  150,
   18,   18,  145,   97,  118,   98,   97,  155,   98,  369,
  230,  165,  277,  144,  115,  221,  150,   56,  272,  135,
   45,   46,   44,   81,    2,   58,   34,   59,  191,  164,
   64,  107,  222,   42,  236,   64,   69,  202,   42,   70,
   42,  167,   42,  185,   71,  182,   72,   90,  183,   66,
  187,  136,  275,  124,   18,   87,  301,   73,  303,  166,
   83,  100,  144,  125,  171,   72,   66,  144,   72,   43,
  274,   82,  186,  135,   42,   84,  208,   42,  299,  207,
   60,   67,   72,    6,   91,  165,   69,    7,   64,   45,
   46,   44,   45,   46,   44,    9,   10,   85,   67,   64,
   80,   13,   97,   69,   98,  136,   92,  101,  161,   93,
  340,  159,  102,  233,  150,  345,  122,  235,  147,   96,
  239,  119,  118,  145,   11,   12,  165,  165,  165,  118,
  165,  130,  165,  299,  130,  229,  359,  125,  200,  281,
  159,  103,  192,  204,  165,  165,  165,  165,  308,  161,
  104,  161,  130,  161,  374,  120,   69,   35,    1,   70,
  126,   64,  185,  160,   71,  381,   72,  161,  161,  161,
  161,  123,   64,   64,  127,   33,  247,   73,  250,  111,
   20,  159,   36,  159,   64,  159,  182,   74,   37,   38,
  172,  148,   38,  151,  152,   18,   35,   18,  128,  159,
  159,  159,  159,  237,  160,  326,  160,   64,  160,  310,
  165,   64,  181,   11,   12,  250,   11,   12,  150,   11,
   12,   80,  160,  160,  160,  160,  343,  154,   10,   39,
   40,   41,  154,  161,   54,   18,   55,   18,  162,  350,
  352,  107,   57,  181,  355,   37,   38,  105,  163,  106,
   37,   38,   37,   38,  148,   38,  347,  153,  366,  157,
  158,  118,  168,  370,  118,  159,  118,  118,  118,  118,
  118,  118,  173,  378,  133,  156,  118,  118,  118,  118,
  118,  118,  118,  118,  363,  174,   37,   38,  160,   37,
   38,   16,  247,   16,   18,  175,  177,  123,   39,   40,
   41,   39,   40,   41,   79,  178,  238,  250,   97,    5,
   98,  182,    6,  251,  252,  176,    7,    8,  247,  180,
  141,  179,   16,   79,    9,   10,  250,  188,   11,   12,
   13,   14,   74,   62,  253,  254,  247,  181,  258,  259,
  193,  250,  165,   76,   18,  165,  165,  165,  165,  165,
  165,  165,  165,  201,  165,  165,  165,  165,  165,  165,
  165,  165,  165,  165,  165,  161,  107,  205,  161,  161,
  161,  161,  161,  161,  161,  161,   79,  161,  161,  161,
  161,  161,  161,  161,  161,  161,  161,  161,  279,  206,
   97,  217,   98,  260,  261,  218,   78,  159,  219,  133,
  159,  159,  159,  159,  159,  159,  159,  159,  224,  159,
  159,  159,  159,  159,  159,  159,  159,  159,  159,  159,
  160,  227,  123,  160,  160,  160,  160,  160,  160,  160,
  160,   16,  160,  160,  160,  160,  160,  160,  160,  160,
  160,  160,  160,  182,  228,  141,  182,  231,  182,  182,
  182,  182,  182,  182,  234,   78,  292,  293,  182,  182,
  182,  182,  182,  182,  182,  182,   83,  241,   76,  181,
   62,   62,  181,  242,  181,  181,  181,  181,  181,  181,
  341,  342,   62,  243,  181,  181,  181,  181,  181,  181,
  181,  181,  364,  365,  146,  146,  160,  161,  107,   81,
  244,  107,  263,  107,  107,  107,  107,  107,  107,  266,
   83,  273,  270,  107,  107,  107,  107,  107,  107,  107,
  107,   78,   20,  276,  280,   83,  278,  282,  286,  288,
  291,  133,  295,  297,  133,  302,  133,  133,  133,  133,
  133,  133,  304,   81,  305,  307,  133,  133,  133,  133,
  133,  133,  133,  133,  123,  309,   16,  123,   81,  123,
  123,  123,  123,  123,  123,  311,  312,  313,  314,  123,
  123,  123,  123,  123,  123,  123,  123,  141,  325,  315,
  141,   20,  141,  141,  141,  141,  141,  141,  318,  319,
  320,   83,  141,  141,  141,  141,  141,  141,  141,  141,
   76,  321,  322,   76,  323,  190,  324,   76,   76,   76,
   42,  327,  328,   94,   42,   76,   76,   42,  329,   76,
   76,   76,   76,  330,   81,   45,   46,   44,  334,   45,
   46,   44,   45,   46,   44,  331,  332,  354,  333,  335,
  336,  339,  110,  110,  116,  346,  348,   20,   42,  349,
  353,  356,  357,   78,  360,  358,   78,  361,  362,  367,
   78,   78,   78,   45,   46,   44,  232,  368,   78,   78,
   42,  372,   78,   78,   78,   78,  371,  373,  375,  377,
  379,  376,  380,  382,  383,   45,   46,   44,   16,  384,
  385,   16,   73,  110,  111,   16,   16,   16,    4,  203,
  197,  198,  283,   16,   16,  240,   53,   16,   16,   16,
   16,   57,   60,  194,  337,    6,   99,  255,  256,    7,
  189,  130,  199,   83,  245,    0,   83,    9,   10,    0,
   83,   83,   83,   13,   60,    0,    0,    6,   83,   83,
    0,    7,   83,   83,   83,   83,    0,    0,    0,    9,
   10,    0,    0,    0,    0,  220,   81,  223,    0,   81,
  110,    0,  226,   81,   81,   81,   63,    0,    0,    0,
    0,   81,   81,    0,    0,   81,   81,   81,   81,   20,
    0,    0,   20,  209,    0,    0,   20,   20,   20,    0,
    0,    0,    0,  209,   20,   20,    0,    0,   20,   20,
   20,   20,    0,    0,  262,    0,    0,  264,    0,  265,
  209,    0,  267,    0,    0,    0,    0,    0,    0,    0,
  209,    0,   37,   38,    0,    0,   37,   38,    0,   37,
   38,    0,    0,    0,   39,   40,   41,  209,   39,   40,
   41,   39,   40,   41,    0,    0,    0,   60,  298,    0,
    6,  110,    0,    0,    7,    0,  130,   61,    0,  209,
   37,   38,    9,   10,    0,   62,    0,    0,   13,    0,
    0,  134,   39,   40,   41,    0,    0,   60,    0,    0,
    6,   78,   37,   38,    7,    0,  130,    0,    0,    0,
    0,    0,    9,   10,   39,   40,   41,    0,   13,    0,
   60,    0,    0,    6,   86,    0,  110,    7,    0,    0,
   61,    0,    0,    0,   88,    9,   10,   60,   62,    0,
    6,   13,  255,  256,    7,    0,    0,   60,    0,    0,
    6,  214,    9,   10,    7,    0,    0,  146,   13,    0,
    0,    0,    9,   10,   60,    0,    0,    6,   13,    0,
  289,    7,    0,    0,   60,    0,    0,    6,  306,    9,
   10,    7,  271,    0,    0,   13,    0,    0,    0,    9,
   10,   60,    0,   60,    6,   13,    6,  316,    7,    0,
    7,    0,    0,   61,    0,   99,    9,   10,    9,   10,
    0,   62,   13,   60,   13,    0,    6,    0,    0,    0,
    7,    0,    0,    5,  103,    0,  129,    0,    9,   10,
    7,    8,  130,    5,   13,    0,    6,    0,  131,  132,
    7,    8,   11,   12,  133,   14,  100,    0,    9,   10,
    0,    0,   11,   12,   13,   14,    5,    0,    0,    6,
    0,    0,    0,    7,    8,    0,    5,    0,    0,    6,
    0,    9,   10,    7,    8,   11,   12,   13,   14,  184,
    0,    9,   10,    0,    0,   11,   12,   13,   14,    5,
    0,    0,    6,    0,    0,    0,    7,    8,  184,    0,
  212,  213,    0,  216,    9,   10,    0,    0,   11,   12,
   13,   14,   60,    0,    5,  129,    0,    6,    0,    7,
  148,    7,    8,    0,    0,    0,    0,  131,  132,    9,
   10,  284,    0,   11,   12,   13,   14,   99,    0,    0,
   99,    0,  246,    0,   99,    0,   99,   99,    0,    0,
    0,    0,   99,   99,   99,   99,  103,    0,   99,  103,
    0,  257,  249,  103,  284,  103,  103,    0,    0,    0,
    0,  103,  103,  103,  103,    0,    0,  103,  100,    0,
    0,  100,    0,    0,    0,  100,    0,  100,  100,    0,
    0,    0,    0,  100,  100,  100,  100,    0,  285,  100,
  287,    0,  290,    0,    0,  294,    0,  296,    0,    0,
    0,   60,    0,    0,    6,    0,    0,    0,    7,    0,
  130,   61,    0,    0,    0,    0,    9,   10,    0,   62,
   60,    0,   13,    6,    0,    0,    0,    7,    0,  317,
   61,    0,    0,    0,    0,    9,   10,    0,   62,    0,
    0,   13,  148,  257,    0,  148,    0,  143,  143,  148,
    0,    0,    0,   60,    0,    0,    6,  148,  148,    0,
    7,    0,  130,  148,   60,    0,    0,    6,    9,   10,
    0,    7,    0,    0,   13,    0,    0,    0,    0,    9,
   10,    0,    0,    0,   60,   13,   60,    6,    0,    6,
    0,    7,    0,    7,    0,    0,    0,    0,    0,    9,
   10,    9,   10,    5,    0,   13,  129,   13,    0,    0,
    7,    8,  130,    0,    0,    0,    0,    0,  131,  132,
    0,    0,   11,   12,  133,   14,    5,    0,    0,    6,
    0,    0,    0,    7,    8,  130,    0,    0,    0,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,   34,
    0,    0,   34,    0,    0,    0,   34,   34,   34,    5,
    0,    0,    6,    0,   34,   34,    7,    8,   34,   34,
   34,   34,    0,    0,    9,   10,    0,    0,   11,   12,
   13,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   43,    4,   44,   41,   41,   41,   45,   10,   41,   45,
   40,   41,   15,   84,   41,   63,   59,  175,  123,   59,
   59,   59,   59,  125,  209,   58,  125,  123,   41,   92,
   33,   34,   85,   43,    0,   45,   43,   41,   45,  123,
  123,   41,   41,   84,   58,   41,   59,   40,  230,   84,
   60,   61,   62,   59,  123,   40,    4,   40,   40,   59,
   63,   41,   58,   45,   41,  125,   13,  138,   45,   13,
   45,   41,   45,  121,   13,   59,   13,   35,  119,   41,
  123,   84,   41,  258,   87,   33,  268,   13,  270,   59,
   59,   49,  133,  268,  108,   41,   58,  138,   44,   40,
   59,   23,   40,  138,   45,  123,   41,   45,  266,   44,
  257,   41,   58,  260,   36,    0,   41,  264,  121,   60,
   61,   62,   60,   61,   62,  272,  273,   58,   58,  132,
  257,  278,   43,   58,   45,  138,   40,   42,    0,  258,
  325,   99,   47,  186,  207,  327,  194,  190,   59,   41,
  193,   58,   59,  206,  276,  277,   41,   42,   43,  125,
   45,  266,   47,  321,  266,  261,  351,  266,  133,  240,
    0,   41,  130,  138,   59,   60,   61,   62,  261,   41,
   41,   43,  266,   45,  369,   59,  133,  268,  257,  133,
   59,  194,  240,    0,  133,  377,  133,   59,   60,   61,
   62,  274,  262,  263,   44,  123,  209,  133,  211,   58,
   59,   41,   44,   43,  274,   45,    0,  257,  257,  258,
   58,  257,  258,  261,  261,  228,  268,  230,   59,   59,
   60,   61,   62,  191,   41,  306,   43,  240,   45,  282,
  125,  244,    0,  276,  277,  248,  276,  277,  261,  276,
  277,  257,   59,   60,   61,   62,  327,  261,  273,  269,
  270,  271,  261,  125,  257,  268,  259,  270,   59,  340,
  341,    0,  257,  257,  345,  257,  258,  257,   59,  259,
  257,  258,  257,  258,  257,  258,  329,  261,  359,   97,
   98,  257,   59,  364,  260,  125,  262,  263,  264,  265,
  266,  267,  257,  374,    0,  261,  272,  273,  274,  275,
  276,  277,  278,  279,  357,   41,  257,  258,  125,  257,
  258,    2,  325,    4,  327,   44,   58,    0,  269,  270,
  271,  269,  270,  271,   15,   41,   41,  340,   43,  257,
   45,  125,  260,  262,  263,  257,  264,  265,  351,   41,
    0,  261,   33,   34,  272,  273,  359,  258,  276,  277,
  278,  279,  257,  125,  262,  263,  369,  125,  262,  263,
   40,  374,  257,    0,  377,  260,  261,  262,  263,  264,
  265,  266,  267,  125,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  257,  125,  125,  260,  261,
  262,  263,  264,  265,  266,  267,   87,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   41,   58,
   43,   59,   45,  262,  263,   59,    0,  257,   58,  125,
  260,  261,  262,  263,  264,  265,  266,  267,   58,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,   58,  125,  260,  261,  262,  263,  264,  265,  266,
  267,    0,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  257,  123,  125,  260,   59,  262,  263,
  264,  265,  266,  267,   41,   59,  262,  263,  272,  273,
  274,  275,  276,  277,  278,  279,    0,  274,  125,  257,
  262,  263,  260,  125,  262,  263,  264,  265,  266,  267,
  262,  263,  274,  125,  272,  273,  274,  275,  276,  277,
  278,  279,  262,  263,  262,  263,  101,  102,  257,    0,
  273,  260,   58,  262,  263,  264,  265,  266,  267,   44,
   44,   59,  123,  272,  273,  274,  275,  276,  277,  278,
  279,  125,    0,   59,   41,   59,   59,   40,   59,   59,
   59,  257,   59,   59,  260,  125,  262,  263,  264,  265,
  266,  267,  125,   44,   59,   59,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  125,  125,  260,   59,  262,
  263,  264,  265,  266,  267,  274,  263,  263,   59,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  123,  263,
  260,   59,  262,  263,  264,  265,  266,  267,   59,  263,
  263,  125,  272,  273,  274,  275,  276,  277,  278,  279,
  257,   44,   59,  260,  125,   40,   59,  264,  265,  266,
   45,  123,   41,   41,   45,  272,  273,   45,   40,  276,
  277,  278,  279,   59,  125,   60,   61,   62,  263,   60,
   61,   62,   60,   61,   62,   59,   59,  125,   59,   59,
   59,   59,   57,   58,   41,   59,   59,  125,   45,  272,
   59,   41,   40,  257,  263,  125,  260,   59,   59,   59,
  264,  265,  266,   60,   61,   62,   41,   41,  272,  273,
   45,  125,  276,  277,  278,  279,   59,  261,  263,  123,
   59,  263,   59,  125,  125,   60,   61,   62,  257,  263,
   59,  260,  257,  108,   58,  264,  265,  266,    0,  138,
  133,  133,  244,  272,  273,  194,  123,  276,  277,  278,
  279,  123,  257,  123,  321,  260,   48,  262,  263,  264,
  127,  266,  133,  257,  207,   -1,  260,  272,  273,   -1,
  264,  265,  266,  278,  257,   -1,   -1,  260,  272,  273,
   -1,  264,  276,  277,  278,  279,   -1,   -1,   -1,  272,
  273,   -1,   -1,   -1,   -1,  170,  257,  172,   -1,  260,
  175,   -1,  177,  264,  265,  266,  123,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,  123,   -1,   -1,  264,  265,  266,   -1,
   -1,   -1,   -1,  123,  272,  273,   -1,   -1,  276,  277,
  278,  279,   -1,   -1,  219,   -1,   -1,  222,   -1,  224,
  123,   -1,  227,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  257,  258,   -1,   -1,  257,  258,   -1,  257,
  258,   -1,   -1,   -1,  269,  270,  271,  123,  269,  270,
  271,  269,  270,  271,   -1,   -1,   -1,  257,  263,   -1,
  260,  266,   -1,   -1,  264,   -1,  266,  267,   -1,  123,
  257,  258,  272,  273,   -1,  275,   -1,   -1,  278,   -1,
   -1,  125,  269,  270,  271,   -1,   -1,  257,   -1,   -1,
  260,  125,  257,  258,  264,   -1,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,  269,  270,  271,   -1,  278,   -1,
  257,   -1,   -1,  260,  125,   -1,  321,  264,   -1,   -1,
  267,   -1,   -1,   -1,  125,  272,  273,  257,  275,   -1,
  260,  278,  262,  263,  264,   -1,   -1,  257,   -1,   -1,
  260,  261,  272,  273,  264,   -1,   -1,  125,  278,   -1,
   -1,   -1,  272,  273,  257,   -1,   -1,  260,  278,   -1,
  263,  264,   -1,   -1,  257,   -1,   -1,  260,  261,  272,
  273,  264,  125,   -1,   -1,  278,   -1,   -1,   -1,  272,
  273,  257,   -1,  257,  260,  278,  260,  263,  264,   -1,
  264,   -1,   -1,  267,   -1,  125,  272,  273,  272,  273,
   -1,  275,  278,  257,  278,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  257,  125,   -1,  260,   -1,  272,  273,
  264,  265,  266,  257,  278,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,  125,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,   -1,  257,   -1,   -1,  260,
   -1,  272,  273,  264,  265,  276,  277,  278,  279,  125,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  125,   -1,
  153,  154,   -1,  156,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,  257,  260,   -1,  260,   -1,  264,
  125,  264,  265,   -1,   -1,   -1,   -1,  272,  273,  272,
  273,  125,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,  125,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,  260,
   -1,  214,  125,  264,  125,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,   -1,   -1,  278,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,   -1,  251,  278,
  253,   -1,  255,   -1,   -1,  258,   -1,  260,   -1,   -1,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  292,
  267,   -1,   -1,   -1,   -1,  272,  273,   -1,  275,   -1,
   -1,  278,  257,  306,   -1,  260,   -1,  262,  263,  264,
   -1,   -1,   -1,  257,   -1,   -1,  260,  272,  273,   -1,
  264,   -1,  266,  278,  257,   -1,   -1,  260,  272,  273,
   -1,  264,   -1,   -1,  278,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,   -1,  257,  278,  257,  260,   -1,  260,
   -1,  264,   -1,  264,   -1,   -1,   -1,   -1,   -1,  272,
  273,  272,  273,  257,   -1,  278,  260,  278,   -1,   -1,
  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,  257,
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
"funcion_con_return : encabezado_funcion '{' sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' sentencias_funcion sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' '}'",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable",
"sentencia_funcion : sentencia_when_con_return",
"sentencia_funcion : DEFER sentencia_when_con_return",
"sentencia_funcion : sentencia_do_con_return",
"sentencia_funcion : DEFER sentencia_do_con_return",
"sentencia_funcion : sentencia_seleccion_simple_con_return",
"sentencia_funcion : DEFER sentencia_seleccion_simple_con_return",
"sentencia_funcion : sentencia_seleccion_compuesta_con_return",
"sentencia_funcion : DEFER sentencia_seleccion_compuesta_con_return",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE sentencia_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE sentencia_return ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_return ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ELSE '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables sentencia_return '}' ENDIF ';'",
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
"bloque_sentencias_ejecutables_seleccion : '{' '}'",
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
//#line 875 "Parser.java"
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
case 36:
//#line 82 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 37:
//#line 83 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 44:
//#line 99 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 47:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 48:
//#line 109 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 53:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 54:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 55:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 56:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 57:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 58:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 59:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 60:
//#line 127 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 62:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 63:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 64:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 65:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 68:
//#line 141 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 73:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 74:
//#line 152 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 75:
//#line 156 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 76:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 77:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 81:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 82:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 83:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 97:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 98:
//#line 194 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 99:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 101:
//#line 197 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 102:
//#line 201 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 103:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 106:
//#line 211 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 107:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 108:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 109:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 110:
//#line 215 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 116:
//#line 233 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 117:
//#line 234 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 118:
//#line 235 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 119:
//#line 239 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 120:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 121:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 122:
//#line 242 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 123:
//#line 243 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 124:
//#line 244 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 127:
//#line 253 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 128:
//#line 254 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 129:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 130:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 131:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 132:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 133:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 135:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 136:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 137:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 138:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 140:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 141:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 142:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 145:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 146:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 147:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba sentencias ejecutables dentro del bloque if"); }
break;
case 151:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 152:
//#line 287 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 174:
//#line 333 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 175:
//#line 334 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 176:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 177:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 178:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 179:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 180:
//#line 339 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 181:
//#line 340 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 182:
//#line 341 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 184:
//#line 346 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1384 "Parser.java"
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
