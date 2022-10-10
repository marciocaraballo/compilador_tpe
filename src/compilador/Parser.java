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
   44,   37,   23,   23,   35,   35,   35,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   45,   45,   45,   45,   21,   21,   20,   20,   47,   20,
   46,   46,   46,   46,   46,   46,   28,   28,   28,   48,
   48,   48,   49,   49,   49,   51,   51,   52,   52,   53,
   53,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   50,   50,   10,   10,
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
    0,    0,    0,   23,   26,   27,    0,    0,   24,   28,
   30,   32,    0,    0,  105,    1,  116,  170,  166,  171,
    0,  168,    0,    0,    0,    0,    0,    0,    0,    0,
  160,  161,  177,  175,  178,    0,  176,    0,  174,    0,
    0,    0,    0,   71,    0,    0,   74,    0,    0,    0,
    0,  111,  101,    0,  113,  115,    0,    0,   80,   79,
    0,    0,    0,    0,    0,   49,    0,   29,   31,   33,
   35,   21,    0,   25,    0,   36,    0,    0,  167,    0,
    0,    0,    0,    0,    0,    0,    0,  173,  172,    0,
   56,    0,    0,   55,    0,    0,   54,    0,    0,    0,
    0,   98,    0,    0,    0,    0,    0,    0,   63,    0,
    0,    0,   22,   37,    0,  169,  145,    0,  143,  146,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,    0,   60,   59,    0,   58,    0,    0,    0,
    0,  108,  110,    0,  109,    0,   65,    0,    0,    0,
    0,    0,  142,    0,  129,    0,  127,    0,    0,  132,
    0,    0,    0,  130,    0,  128,   52,    0,   68,  124,
    0,    0,    0,  106,    0,   61,    0,   50,    0,    0,
    0,    0,  140,    0,    0,    0,  125,    0,    0,    0,
  121,    0,  120,    0,    0,    0,    0,    0,  135,  133,
  137,  138,    0,  136,  134,   70,    0,  119,    0,    0,
    0,   45,    0,    0,   47,    0,  126,    0,    0,    0,
    0,   42,    0,   46,    0,    0,    0,    0,    0,   44,
   48,    0,    0,    0,    0,   38,    0,    0,    0,   43,
    0,    0,    0,    0,    0,    0,    0,   39,   40,    0,
    0,  122,    0,   41,
};
final static short yydgoto[] = {                          3,
    4,   15,  268,   17,  211,   19,   20,   21,   22,   23,
   24,   25,  196,  138,  139,  140,  141,  142,  143,   41,
  212,  343,  269,  197,   26,  121,  111,   42,  112,  299,
  113,   70,   71,   27,   28,   29,   30,   31,   59,   60,
   61,   32,   62,  337,  216,   99,   43,   44,   45,   46,
   47,  151,  152,
};
final static short yysindex[] = {                      -103,
    0,  934,    0,  403,  -40,    4,   -4,  -33,   19,  549,
    0,    0,  795,  -41,  652,    0,    0,    0,    0,    0,
    0,    0,  -38,   -6,  -85,   56,    0,    0,    0,    0,
    0,    0,  675,  685,   -8, -152,   86,    0, -126,   53,
  100,    7,   84,   41,    0,    0,    0,  123,  131,   23,
  -29,  -17,   55,  -90,   31,  129,  450,    0,    0,    0,
    0,  -92,    0,    0,    0,    0,    0, -166,    0,  144,
  165,    0,    0,  174,    0,  182,    0,  642,  -16,    0,
  708,    0,    0,  410,    0,   77,    0,    6,  -35,   18,
    0,    0,    0,   27,   27,    0,    0,    0,   27,   27,
   27,   27,  228,  244,    2,   40,  257,  -32,  267,  104,
  316,  326,  116,  387,  454,  241,  463,    0,  -19,    0,
  816,    0,   17,    0,  247,    0,  260,    0,   33,   35,
  479,  426,  814,    0,    0,    0,  417,  878,    0,    0,
    0,    0,  437,  505,    0,    0,    0,    0,    0,    0,
  145,    0,  632,  632,  356,  632,   41,   41,  169,  169,
    0,    0,    0,    0,    0,  506,    0,  526,    0,  528,
 -231,   50, -231,    0,  530, -231,    0, -231,  531,  467,
  -88,    0,    0,  543,    0,    0,   92,  557,    0,    0,
   53,   94,  172,   55,  450,    0,  333,    0,    0,    0,
    0,    0,  483,    0,  486,    0,  339,   37,    0,  337,
    0,  378,  -30,   34,  566,   38,  106,    0,    0, -231,
    0,  556, -231,    0, -231,  575,    0, -231,  934,  498,
  934,    0,  563,   44,  567,  -24,  571,  317,    0,  606,
  797,  597,    0,    0,  426,    0,    0,  846,    0,    0,
  632,  589,  632,  590,  577,  593,  134,  632,  594,  632,
  598,    0, -231,    0,    0, -231,    0,  934,  533,  934,
  540,    0,    0,  610,    0,  596,    0,  611,  -69,  546,
   27,  399,    0,  411,    0,  414,    0,  619,  421,    0,
  615,  626,  424,    0,  425,    0,    0,  647,    0,    0,
  635,  570,  637,    0,  539,    0,  574,    0,  662,  665,
  650,  653,    0,  656,  659,  443,    0,  661,  667, -231,
    0,  670,    0,  337,  157,  901,  672,   27,    0,    0,
    0,    0,  673,    0,    0,    0,  452,    0,  710, -101,
  674,    0,  612,  -97,    0,  680,    0,  699,  616,  337,
  477,    0,  686,    0,  693,   27,  202,  957,  697,    0,
    0,  703,  -96,  701,  636,    0,  501,  337,  500,    0,
  511,  641,  957,  709,  716,  934,  654,    0,    0,  658,
  515,    0,  725,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  288,  303,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  115,    0,    0,  303,
    0,    0,    0,  138,    0,    0,    0,    0,    0,    0,
  529,  529,  303,  727,  733,  752,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   42,    0,  327,
  493,    0,    0,  516,    0,  367,    0,    0,    0,    0,
  787,    0,    0,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,    0,    0,    0,    0,    0,    0,  529,    0,   63,
    0,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  303,    0,  470,    0,    0,    0,  303,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  924,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  161,  211,  -26,  -25,
    0,    0,    0,    0,    0,  184,    0,  234,    0,    0,
    0,    0,    0,    0,    0,  529,    0,    0,    0,    0,
    0,    0,    0,  772,    0,    0,  303,    0,    0,    0,
  303,    0,    0,  303,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  924,    0,    0,    0,    0,    0,
  835,    0,    0,    0,    0,    0,    0,    0,    0,  668,
    0,    0,    0,    0,    0,   76,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  204,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  681,    0,    0,  529,    0,  -95,    0,    0,
    0,    0,    0,  258,    0,    0,    0,  180,    0,    0,
  303,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  281,    0,    0,    0,    0,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  529,
    0,    0,    0,    0,    0,    0,    0,  303,    0,    0,
    0,    0,  304,    0,    0,    0,    0,    0,  204,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  303,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   73,  621,   -7,   -2,    0,    0,    0,    0,  488,
   93,    0,  -75,    0,  660,  657,  664,  682,  -13,  -39,
 -200,    0, -175,  562,    9,  623,  -11,   13, -157,  494,
    0,  692,    0,   16,   26,   47,   57,   61,  -10,    0,
    0,  -54,    0,    0,  881,  777,    0,  433,  449,  -21,
    0,    0,  617,
};
final static int YYTABLESIZE=1235;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  137,   36,  148,  155,   52,   58,  170,  248,
  108,  109,   18,  117,  147,  150,  276,   69,  226,    2,
   75,  350,  148,  114,  145,  171,  368,  322,   63,  123,
   18,   18,  147,  150,  231,   50,   39,   78,   64,  183,
  115,   83,  166,   40,   11,   12,  122,   84,   39,   94,
   83,   95,   77,  231,   58,  271,  187,   64,   53,   65,
  165,   39,  203,  107,  150,  118,   97,   98,   96,   66,
  135,   39,  191,   67,  192,  136,   34,   39,   18,   39,
  168,   39,  101,  188,  274,   83,  144,  102,  119,  118,
  222,  124,  300,   88,  302,  116,  172,   39,  167,   39,
   83,  125,  273,   72,   74,   81,   72,  223,  298,   66,
  186,  159,  160,   79,  163,   76,   67,  149,   58,  201,
   72,   39,   69,  339,  205,   86,   66,  184,   85,   58,
  135,   87,  233,   67,  237,  136,   39,  159,   39,   69,
   90,  144,  193,   97,   98,   96,  144,  234,   63,  358,
  344,  236,  145,    1,  240,  163,  163,  163,   64,  163,
  157,  163,  298,  103,  130,  280,   83,  373,  130,  130,
  123,  104,  230,  163,  163,  163,  163,   35,  159,   65,
  159,  123,  159,  180,  122,  209,  150,  120,  208,   66,
  118,  307,   58,   67,   64,   64,  159,  159,  159,  159,
  380,  157,  126,  157,  238,  157,   64,  247,  127,  250,
  158,   94,  239,   95,   94,   68,   95,   36,   74,  157,
  157,  157,  157,   51,  148,  154,   18,   35,   18,  325,
  186,  251,  252,  179,  147,  150,  154,  182,   58,  163,
  128,  309,   58,   11,   12,  250,   11,   12,   37,   38,
  342,  158,   48,  158,   49,  158,   10,  107,   11,   12,
   37,   38,  159,  349,  351,   18,  153,   18,  354,  158,
  158,  158,  158,   37,   38,   91,   92,   93,  156,  105,
  131,  106,  365,   37,   38,  157,  163,  369,  346,   37,
   38,   37,   38,  148,   38,  253,  254,  377,   83,  258,
  259,   83,  164,  139,   62,   83,   83,   83,  180,   37,
   38,   37,   38,   83,   83,  169,  362,   83,   83,   83,
   83,  247,  118,   18,  173,  118,   76,  118,  118,  118,
  118,  118,  118,  148,   38,  158,  250,  118,  118,  118,
  118,  118,  118,  118,  118,  111,   20,  247,   37,   38,
   37,   38,   91,   92,   93,  250,  175,  278,  179,   94,
  174,   95,  149,  149,  149,  247,   16,  260,  261,  176,
  250,  163,  177,   18,  163,  163,  163,  163,  163,  163,
  163,  163,  107,  163,  163,  163,  163,  163,  163,  163,
  163,  163,  163,  163,  159,  291,  292,  159,  159,  159,
  159,  159,  159,  159,  159,  131,  159,  159,  159,  159,
  159,  159,  159,  159,  159,  159,  159,  157,  340,  341,
  157,  157,  157,  157,  157,  157,  157,  157,  139,  157,
  157,  157,  157,  157,  157,  157,  157,  157,  157,  157,
  180,   62,   62,  180,  178,  180,  180,  180,  180,  180,
  180,   76,   94,   62,   95,  180,  180,  180,  180,  180,
  180,  180,  180,  363,  364,  144,  144,  158,  147,   81,
  158,  158,  158,  158,  158,  158,  158,  158,  210,  158,
  158,  158,  158,  158,  158,  158,  158,  158,  158,  158,
  179,   16,   78,  179,  179,  179,  179,  179,  179,  179,
  179,  180,  249,  181,  189,  179,  179,  179,  179,  179,
  179,  179,  179,   81,  107,   20,   68,  107,  194,  107,
  107,  107,  107,  107,  107,   33,  157,  158,   81,  107,
  107,  107,  107,  107,  107,  107,  107,  131,  110,  110,
  131,  202,  131,  131,  131,  131,  131,  131,  195,  161,
  162,   78,  131,  131,  131,  131,  131,  131,  131,  131,
  139,  206,  207,  139,  218,  139,  139,  139,  139,  139,
  139,  149,  149,  149,   20,  139,  139,  139,  139,  139,
  139,  139,  139,   76,  219,  220,   76,  225,  228,  229,
   76,   76,   76,   54,   81,  110,    6,  235,   76,   76,
    7,  232,   76,   76,   76,   76,  242,  243,    9,   10,
  244,  245,   54,  263,   13,    6,  215,   78,  266,    7,
  270,  272,   16,   16,   16,  275,   16,    9,   10,  277,
   16,   16,   16,   13,   54,   73,  281,    6,   16,   16,
   20,    7,   16,   16,   16,   16,  279,  285,  287,    9,
   10,  290,  294,   16,   73,   13,  296,  301,  221,    5,
  224,  324,    6,  110,  303,  227,    7,    8,  304,  306,
  308,   57,  310,  311,    9,   10,  312,  313,   11,   12,
   13,   14,   54,  314,  317,    6,  318,  319,  210,    7,
  320,  130,   55,  321,  322,  323,  326,    9,   10,  210,
   56,   73,  327,   13,  328,  333,   54,  262,  329,    6,
  264,  330,  265,    7,  331,  267,   55,  332,  210,  334,
  355,    9,   10,  348,   56,  335,   81,   13,  338,   81,
  345,  347,  352,   81,   81,   81,  353,  210,  356,  359,
  357,   81,   81,  367,  360,   81,   81,   81,   81,   78,
  297,  361,   78,  110,  210,  366,   78,   78,   78,  370,
  371,  372,  374,  376,   78,   78,  134,  378,   78,   78,
   78,   78,   20,  375,  379,   20,   72,  383,  381,   20,
   20,   20,  382,  384,  111,   73,    4,   20,   20,  198,
   53,   20,   20,   20,   20,   54,  199,  204,    6,   80,
  255,  256,    7,   57,  130,   54,  282,  110,    6,   82,
    9,   10,    7,  336,  200,   55,   13,  241,  190,  100,
    9,   10,   54,   56,  246,    6,   13,  255,  256,    7,
    0,    0,  146,   54,  283,    0,    6,    9,   10,  288,
    7,    0,    0,   13,    0,    0,    0,    0,    9,   10,
    0,    0,   54,    0,   13,    6,  305,   99,    0,    7,
    0,    0,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   54,    0,   13,    6,    0,  103,  315,    7,    0,
    0,    0,    0,    0,    0,    0,    9,   10,   54,    0,
    0,    6,   13,    0,    0,    7,  100,    0,    5,    0,
    0,  129,    0,    9,   10,    7,    8,  130,    5,   13,
    0,    6,    0,  131,  132,    7,    8,   11,   12,  133,
   14,  185,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    5,    0,    0,    6,    0,    0,    0,    7,    8,
  185,    5,    0,    0,    6,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,  145,
   11,   12,   13,   14,    5,    0,   54,    6,    0,    6,
  283,    7,    8,    7,    0,  130,    0,    0,    0,    9,
   10,    9,   10,   11,   12,   13,   14,   13,    0,   99,
    0,    0,   99,    0,    0,    0,   99,    0,   99,   99,
    0,    0,    0,    0,   99,   99,   99,   99,  103,    0,
   99,  103,    0,    0,    0,  103,    0,  103,  103,    0,
    0,    0,    0,  103,  103,  103,  103,    0,  100,  103,
    0,  100,    0,  213,  214,  100,  217,  100,  100,    0,
    0,    0,    0,  100,  100,  100,  100,    0,    0,  100,
    0,   54,    0,   54,    6,    0,    6,    0,    7,    0,
    7,    0,  130,   55,    0,    0,    9,   10,    9,   10,
   54,   56,   54,  129,   13,    6,    0,    7,    0,    7,
    0,    0,   55,    0,    0,  131,  132,    9,   10,    0,
   56,  145,    0,   13,  145,  257,  141,  141,  145,    0,
    0,    0,   54,    0,    0,    6,  145,  145,    0,    7,
    0,    0,  145,    0,    0,    0,    0,    9,   10,    0,
    0,    0,    0,   13,    0,    0,    0,    0,    0,    0,
    0,  284,    0,  286,    5,  289,    0,  129,  293,    0,
  295,    7,    8,  130,    0,    0,    0,    0,    0,  131,
  132,    0,    0,   11,   12,  133,   14,    5,    0,    0,
    6,    0,    0,    0,    7,    8,  130,    0,    0,    0,
    0,  316,    9,   10,    0,    0,   11,   12,   13,   14,
   34,    0,    0,   34,    0,  257,    0,   34,   34,   34,
    5,    0,    0,    6,    0,   34,   34,    7,    8,   34,
   34,   34,   34,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,   54,    0,    0,    6,    0,    0,    0,
    7,    0,  130,    0,    0,    0,    0,    0,    9,   10,
    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   78,   44,   41,   41,   40,   10,   41,  210,
   40,   41,   15,   53,   41,   41,   41,   59,  176,  123,
   59,  123,   59,   41,   79,   58,  123,  125,   13,  125,
   33,   34,   59,   59,  123,   40,   45,  123,   13,   59,
   52,    0,   41,   40,  276,  277,   57,   35,   45,   43,
   59,   45,   59,  123,   57,  231,   40,  125,   40,   13,
   59,   45,  138,   41,   86,    0,   60,   61,   62,   13,
   78,   45,   40,   13,   40,   78,    4,   45,   81,   45,
   41,   45,   42,  123,   41,   44,   78,   47,   58,   59,
   41,  258,  268,   41,  270,   41,  108,   45,   59,   45,
   59,  268,   59,   41,  257,   33,   44,   58,  266,   41,
  121,   99,  100,   58,    0,   23,   41,   41,  121,  133,
   58,   45,   41,  324,  138,   40,   58,  119,   36,  132,
  138,  258,   41,   58,   41,  138,   45,    0,   45,   58,
   41,  133,  130,   60,   61,   62,  138,  187,  133,  350,
  326,  191,  207,  257,  194,   41,   42,   43,  133,   45,
    0,   47,  320,   41,  266,  241,  125,  368,  266,  266,
  266,   41,  261,   59,   60,   61,   62,  268,   41,  133,
   43,  274,   45,    0,  195,   41,  208,   59,   44,  133,
  125,  261,  195,  133,  262,  263,   59,   60,   61,   62,
  376,   41,   59,   43,  192,   45,  274,  210,   44,  212,
    0,   43,   41,   45,   43,  257,   45,   44,  257,   59,
   60,   61,   62,  257,  261,  261,  229,  268,  231,  305,
  241,  262,  263,    0,  261,  261,  261,  257,  241,  125,
   59,  281,  245,  276,  277,  248,  276,  277,  257,  258,
  326,   41,  257,   43,  259,   45,  273,    0,  276,  277,
  257,  258,  125,  339,  340,  268,  261,  270,  344,   59,
   60,   61,   62,  257,  258,  269,  270,  271,  261,  257,
    0,  259,  358,  257,  258,  125,   59,  363,  328,  257,
  258,  257,  258,  257,  258,  262,  263,  373,  257,  262,
  263,  260,   59,    0,  125,  264,  265,  266,  125,  257,
  258,  257,  258,  272,  273,   59,  356,  276,  277,  278,
  279,  324,  257,  326,   58,  260,    0,  262,  263,  264,
  265,  266,  267,  257,  258,  125,  339,  272,  273,  274,
  275,  276,  277,  278,  279,   58,   59,  350,  257,  258,
  257,  258,  269,  270,  271,  358,   41,   41,  125,   43,
  257,   45,   60,   61,   62,  368,    0,  262,  263,   44,
  373,  257,  257,  376,  260,  261,  262,  263,  264,  265,
  266,  267,  125,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  262,  263,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  262,  263,
  260,  261,  262,  263,  264,  265,  266,  267,  125,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  262,  263,  260,   58,  262,  263,  264,  265,  266,
  267,  125,   43,  274,   45,  272,  273,  274,  275,  276,
  277,  278,  279,  262,  263,  262,  263,  257,   59,    0,
  260,  261,  262,  263,  264,  265,  266,  267,  123,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  125,    0,  260,   41,  262,  263,  264,  265,  266,
  267,  261,  125,   41,  258,  272,  273,  274,  275,  276,
  277,  278,  279,   44,  257,    0,  257,  260,   40,  262,
  263,  264,  265,  266,  267,  123,   94,   95,   59,  272,
  273,  274,  275,  276,  277,  278,  279,  257,   51,   52,
  260,  125,  262,  263,  264,  265,  266,  267,  123,  101,
  102,   59,  272,  273,  274,  275,  276,  277,  278,  279,
  257,  125,   58,  260,   59,  262,  263,  264,  265,  266,
  267,  269,  270,  271,   59,  272,  273,  274,  275,  276,
  277,  278,  279,  257,   59,   58,  260,   58,   58,  123,
  264,  265,  266,  257,  125,  108,  260,   41,  272,  273,
  264,   59,  276,  277,  278,  279,  274,  125,  272,  273,
  125,  273,  257,   58,  278,  260,  261,  125,   44,  264,
  123,   59,    2,  257,    4,   59,  260,  272,  273,   59,
  264,  265,  266,  278,  257,   15,   40,  260,  272,  273,
  125,  264,  276,  277,  278,  279,   41,   59,   59,  272,
  273,   59,   59,   33,   34,  278,   59,  125,  171,  257,
  173,  123,  260,  176,  125,  178,  264,  265,   59,   59,
  125,  123,  274,  263,  272,  273,  263,   59,  276,  277,
  278,  279,  257,  263,   59,  260,  263,  263,  123,  264,
   44,  266,  267,   59,  125,   59,  123,  272,  273,  123,
  275,   81,   41,  278,   40,  263,  257,  220,   59,  260,
  223,   59,  225,  264,   59,  228,  267,   59,  123,   59,
   41,  272,  273,  272,  275,   59,  257,  278,   59,  260,
   59,   59,   59,  264,  265,  266,  125,  123,   40,  263,
  125,  272,  273,   41,   59,  276,  277,  278,  279,  257,
  263,   59,  260,  266,  123,   59,  264,  265,  266,   59,
  125,  261,  263,  123,  272,  273,  125,   59,  276,  277,
  278,  279,  257,  263,   59,  260,  125,  263,  125,  264,
  265,  266,  125,   59,   58,  257,    0,  272,  273,  133,
  123,  276,  277,  278,  279,  257,  133,  138,  260,  125,
  262,  263,  264,  123,  266,  257,  245,  320,  260,  125,
  272,  273,  264,  320,  133,  267,  278,  195,  127,   43,
  272,  273,  257,  275,  208,  260,  278,  262,  263,  264,
   -1,   -1,  125,  257,  125,   -1,  260,  272,  273,  263,
  264,   -1,   -1,  278,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  257,   -1,  278,  260,  261,  125,   -1,  264,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  257,   -1,  278,  260,   -1,  125,  263,  264,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,   -1,   -1,  264,  125,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  266,  257,  278,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,  125,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
  125,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,
  276,  277,  278,  279,  257,   -1,  257,  260,   -1,  260,
  125,  264,  265,  264,   -1,  266,   -1,   -1,   -1,  272,
  273,  272,  273,  276,  277,  278,  279,  278,   -1,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,   -1,  257,  278,
   -1,  260,   -1,  153,  154,  264,  156,  266,  267,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,   -1,   -1,  278,
   -1,  257,   -1,  257,  260,   -1,  260,   -1,  264,   -1,
  264,   -1,  266,  267,   -1,   -1,  272,  273,  272,  273,
  257,  275,  257,  260,  278,  260,   -1,  264,   -1,  264,
   -1,   -1,  267,   -1,   -1,  272,  273,  272,  273,   -1,
  275,  257,   -1,  278,  260,  215,  262,  263,  264,   -1,
   -1,   -1,  257,   -1,   -1,  260,  272,  273,   -1,  264,
   -1,   -1,  278,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  251,   -1,  253,  257,  255,   -1,  260,  258,   -1,
  260,  264,  265,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,
   -1,  291,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,  305,   -1,  264,  265,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,   -1,   -1,  278,
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

//#line 352 ".\gramatica.y"

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
//#line 839 "Parser.java"
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
case 125:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 126:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 127:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 129:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 130:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 131:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 132:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 133:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 135:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 136:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 137:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 138:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 139:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 140:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 143:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 144:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 149:
//#line 283 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 150:
//#line 284 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 172:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 331 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 174:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 175:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 176:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 177:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 178:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 179:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 180:
//#line 338 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 182:
//#line 343 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1336 "Parser.java"
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
