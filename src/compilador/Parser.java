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






//#line 2 "./src/compilador/gramatica.y"
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
public final static short ELSE=261;
public final static short ENDIF=262;
public final static short PRINT=263;
public final static short VOID=264;
public final static short RETURN=265;
public final static short COMP_MAYOR_IGUAL=266;
public final static short COMP_MENOR_IGUAL=267;
public final static short COMP_IGUAL=268;
public final static short COMP_DISTINTO=269;
public final static short CLASS=270;
public final static short WHILE=271;
public final static short DO=272;
public final static short INTERFACE=273;
public final static short IMPLEMENT=274;
public final static short INT=275;
public final static short ULONG=276;
public final static short FLOAT=277;
public final static short OPERADOR_MENOS=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    2,    2,    4,    4,
    4,    4,    4,    4,   11,   11,   12,   12,   13,   13,
   13,   13,   13,   10,   10,    9,    9,   15,   15,    8,
    8,    8,    8,    8,    8,    8,    8,    8,   14,   14,
   14,   19,   14,   14,   14,   14,   14,   16,   16,   18,
   18,   18,   18,   18,   22,   22,   23,   23,   20,   20,
   21,   21,    7,    7,    7,    7,    6,    6,    6,    6,
    6,    6,   26,   26,    5,    5,    5,   24,   24,    3,
    3,    3,    3,   27,   27,   27,   30,   30,   30,   30,
   30,   30,   33,   33,   35,   35,   35,   35,   29,   29,
   29,   29,   29,   29,   29,   29,   36,   36,   28,   34,
   34,   34,   34,   34,   34,   34,   34,   34,   37,   37,
   37,   37,   37,   37,   40,   40,   41,   41,   41,   39,
   39,   39,   38,   32,   32,   31,   31,   31,   31,   17,
   42,   42,   42,   42,   42,   42,   25,   25,   25,   43,
   43,   43,   44,   44,   44,   45,   45,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    7,    6,    9,
    7,    8,    6,    8,    6,    8,    8,    6,    9,    7,
    8,    0,   15,    6,    8,    8,    6,    1,    3,    1,
    1,    3,    4,    5,    1,    2,    1,    1,    1,    2,
    1,    2,    3,    2,    2,    3,    5,    4,    7,    4,
    3,    6,    1,    3,    4,    3,    3,    1,    3,    1,
    1,    1,    1,    3,    2,    2,    5,    4,    3,    2,
    4,    3,    2,    3,    3,    2,    1,    2,    5,    7,
    4,    3,    6,    5,    4,    6,    1,    2,    2,    5,
    4,    7,    6,    4,    3,    4,    3,    2,    4,    3,
    3,    2,    5,    4,    1,    2,    1,    1,    1,    1,
    3,    2,    2,    1,    3,    1,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  136,  137,
  138,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   80,   81,   82,   83,    0,    0,
    0,    0,    0,   65,    0,    0,   24,    0,    0,    0,
   78,    0,   48,    0,    0,    0,   90,    0,    0,    0,
    2,    6,    0,    0,    0,  134,   86,    0,    0,  109,
    0,  156,    0,    0,    0,    0,    0,  152,  155,   66,
   63,  139,    0,  117,    0,    0,  115,    0,    0,    0,
    0,   97,    0,  107,    0,  102,    0,    0,   59,    0,
    0,    0,   92,    0,    0,   89,    0,    1,    0,    0,
   77,    0,   79,   84,    0,    0,    0,  122,   17,   19,
   20,   21,    0,    0,   15,   18,   22,   23,  154,  157,
    0,    0,  141,  142,  143,  144,    0,    0,  145,  146,
    0,    0,    0,  111,    0,  133,  116,  114,   98,    0,
    0,    0,    0,  105,  108,    0,  101,   49,   60,    0,
    0,   91,   88,   94,   68,    0,    0,   75,  135,    0,
    0,   51,   50,    0,  120,  127,  128,  129,    0,  125,
  121,    0,   16,    0,    0,    0,    0,    0,    0,    0,
    0,  150,  151,    0,  110,  130,    0,    0,    0,    0,
   99,   95,  104,    0,   87,    0,    0,   67,    0,    0,
   61,    0,    0,  124,  126,  119,    0,    0,   35,    0,
   38,    0,    0,    0,    0,  113,  132,    0,  106,  103,
    0,    0,    0,    0,    0,   52,    0,   62,    0,  123,
    0,    0,    0,    0,   31,  112,  131,  100,   26,    0,
   69,    0,    0,    0,    0,   53,   57,   58,    0,   55,
    0,   34,   36,   37,    0,   44,    0,   47,    0,    0,
   54,   56,    0,   30,    0,    0,    0,   40,    0,   28,
   45,   46,    0,    0,   39,    0,    0,    0,    0,    0,
    0,   43,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   43,  110,  111,  112,   21,   22,   23,
  114,  115,  163,  117,  118,   44,   65,  164,  269,   90,
  202,  249,  250,   24,   66,  197,   25,   82,   27,   28,
   83,   58,   48,   30,   84,   85,   60,  186,  187,  169,
  170,  131,   67,   68,   69,
};
final static short yysindex[] = {                       -68,
    0,   16,   -3,  -36,   26,  -88,  332,  -87,    0,    0,
    0,  873,    0,  592,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   11,    0,    0,    0,    0,   -7,  -65,
  -30,   29,   32,    0,  -38,  -41,    0, -117, -114, -159,
    0, -135,    0, -192,  -73, -183,    0,  -17,   52,  613,
    0,    0,  -13,  -26, -119,    0,    0,   30,  636,    0,
 -179,    0, -106,  332,  125,  128,    7,    0,    0,    0,
    0,    0,  124,    0, -101,  126,    0,  133,  131, -102,
 -159,    0,  -79,    0,  409,    0, -159,  466,    0,  825,
  143, -183,    0,   73,   74,    0, -183,    0,  159,  483,
    0,   90,    0,    0,  -51,  170,  803,    0,    0,    0,
    0,    0,  657,  679,    0,    0,    0,    0,    0,    0,
 -175,  769,    0,    0,    0,    0,   94,   94,    0,    0,
   94,   94,   94,    0,  -34,    0,    0,    0,    0,  -96,
 -159,  469,   36,    0,    0,  500,    0,    0,    0,   94,
   95,    0,    0,    0,    0,   94,  171,    0,    0,   91,
  -46,    0,    0,  -47,    0,    0,    0,    0,  700,    0,
    0,  726,    0,  332,  186,  332,  211, -158,    7,    7,
  139,    0,    0, -156,    0,    0,  -28, -159,  509,  627,
    0,    0,    0,  216,    0,  119,  218,    0,  803,  219,
    0,  836,  241,    0,    0,    0,  747,   20,    0,   35,
    0,  793,  258,  -24, -156,    0,    0,  671,    0,    0,
  261,   94,  277,   65,  782,    0,  846,    0,   94,    0,
  284,  315,  318,  105,    0,    0,    0,    0,    0,  139,
    0,  329,  803,  330, -138,    0,    0,    0,  859,    0,
  341,    0,    0,    0,  339,    0,  122,    0,  814,  345,
    0,    0,  349,    0,  354,  358,  141,    0,  145,    0,
    0,    0,  362,  368,    0,  376,  803,  148,  803,  156,
  380,    0,
};
final static short yyrindex[] = {                       429,
  -32,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,  -39,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  430,
    0,    0,    0,    0,    0,    0,    0,  343,    0,    0,
    1,    0,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  174,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  307,    0,  150,    0,
    0,  176,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  705,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  200,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   47,   69,
  393,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  394,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  222,    0,    0,    0,    0,    0,    0,    0,
  246,    0,  279,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  401,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  303,    0,    0,    0,    0,  183,
    0,    0,  546,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  571,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  432,   12,  247, 1113,  614,  617,  638,    0,    0,  470,
    0,  331,  472,    0,    0,  -25,  -85,  510,    0,    0,
    0,    0,  201,    0,  -29,    0,    0,  598,    0,    0,
  587,  373,  -15,   14,  952,   13,    0,   -2,  274,  287,
 -129,    0,   18,    9,    0,
};
final static int YYTABLESIZE=1203;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         77,
  153,   73,   74,   36,  118,   81,  185,   78,   87,  184,
   64,  139,  216,   78,   63,  215,  236,  101,   63,  215,
  141,   49,  149,  100,  102,   52,  188,   99,   78,   94,
   95,   63,   76,   78,   40,   46,   57,   47,  121,  205,
   34,  153,  153,  153,  153,  153,  147,  153,  132,   92,
   53,   93,   88,  133,   12,   31,   55,   59,   49,   49,
  153,   52,  153,  149,  194,  149,  149,  149,  148,   37,
  135,   54,   70,  104,  200,   71,  151,  205,   91,  192,
    4,  154,  149,  118,  149,  174,  175,  147,  105,  147,
  147,  147,   25,  142,  105,   97,  178,   79,  119,  146,
   72,  181,  212,  213,    4,   49,  147,   96,  147,  148,
   49,  148,  148,  148,   64,    9,   10,   11,    9,   10,
   11,   41,  259,  260,    2,  153,  196,    3,  148,    5,
  148,  199,  127,  158,  128,   63,    7,  103,   63,   79,
  182,  183,   86,  251,  179,  180,    4,  149,  208,   71,
  210,  120,  189,  190,  140,  136,   80,    9,   10,   11,
   79,  127,  222,  128,  134,  122,  137,    4,   38,   45,
  127,  147,  128,  138,  139,   76,    4,   56,    9,   10,
   11,  127,  150,  128,  217,   39,  234,  130,    1,  129,
    4,    2,  240,  148,    3,    4,    5,  152,  153,   70,
  218,    6,  155,    7,    8,  159,    9,   10,   11,  160,
   41,  217,  237,  106,  198,   72,    3,   25,   72,  195,
   35,   33,   72,  203,  139,  107,   61,   62,   72,  209,
   61,   62,   72,    9,   10,   11,    9,   10,   11,   64,
    9,   10,   11,   61,   62,   27,    9,   10,   11,   56,
    9,   10,   11,   32,  211,   33,  221,  153,  223,  225,
  153,  153,  153,  153,  153,  153,  153,  153,  153,  153,
  153,  153,  153,  153,   71,  153,  153,  153,   72,  149,
  229,  231,  149,  149,  149,  149,  149,  149,  149,  149,
  149,  149,  149,  149,  149,  149,  232,  149,  149,  149,
   76,  235,   32,  147,  239,  109,  147,  147,  147,  147,
  147,  147,  147,  147,  147,  147,  147,  147,  147,  147,
  241,  147,  147,  147,   70,  148,  242,  252,  148,  148,
  148,  148,  148,  148,  148,  148,  148,  148,  148,  148,
  148,  148,   85,  148,  148,  148,   33,   61,   62,   25,
   61,   62,   25,   25,   25,   25,   25,   25,  253,  166,
  109,  254,   25,   25,   25,   25,  255,   25,   25,   25,
   27,   64,  256,  258,   64,   64,   64,   64,   64,   64,
   72,  263,  264,  265,   64,   64,   64,   64,  268,   64,
   64,   64,  270,  123,  124,  125,  126,  271,    9,   10,
   11,  272,  273,   72,  274,  275,   71,  276,  279,   71,
   71,   71,   71,   71,   71,  166,  277,  281,  166,   71,
   71,   71,   71,  282,   71,   71,   71,   32,    4,    3,
  139,   93,   76,  140,   73,   76,   76,   76,   76,   76,
   76,   74,   42,   50,  173,   76,   76,   76,   76,  262,
   76,   76,   76,  166,   42,  143,   70,  214,  207,   70,
   70,   70,   70,   70,   70,    0,    0,   85,    0,   70,
   70,   70,   70,    0,   70,   70,   70,    0,   33,    0,
    0,   33,   33,   33,   33,   33,   33,    0,    0,    0,
    0,   33,   33,   33,   33,    0,   33,   33,   33,    0,
    0,    0,   27,    0,    0,   27,   27,   27,   27,   27,
   27,    0,    0,    0,    0,   27,   27,   27,   27,    0,
   27,   27,   27,  157,    0,  127,  156,  128,  113,    0,
  116,    0,    0,  144,    0,   72,    0,    0,   72,   72,
   72,   72,   72,   72,    0,    0,    0,    0,   72,   72,
   72,   72,    0,   72,   72,   72,    0,    0,    0,   32,
    0,    0,   32,   32,   32,   32,   32,   32,    0,    0,
    0,    0,   32,   32,   32,   32,  162,   32,   32,   32,
    0,    0,  167,  172,  168,  116,   29,    0,   41,    0,
  147,    2,    0,  191,    3,    0,    5,   26,   29,   85,
   29,    0,   85,    7,    0,   85,   85,   85,    0,   26,
    0,   26,   85,   18,   85,   85,   19,   85,   85,   85,
   18,   75,   75,   19,  193,   18,    0,   18,   19,    0,
   19,    0,  201,  219,    0,    0,   29,   20,  167,    0,
  168,  167,    0,  168,   20,   29,    0,   26,    0,   20,
    0,   20,    0,    0,    0,   18,   26,    0,   19,   75,
    0,    0,    0,   18,    0,   79,   19,    0,  162,    0,
   29,  227,    4,  228,    0,    0,  167,   18,  168,   20,
   19,    0,    0,    9,   10,   11,    0,   20,    0,    0,
    0,    0,    0,    0,  162,   41,  247,    0,  248,   29,
   29,   20,    0,   18,    0,    0,   19,    0,  224,    0,
   26,   26,  162,    0,    0,    0,   51,    0,  247,    0,
  248,   75,   79,    0,    0,   79,    0,   20,  162,    4,
    0,    0,    4,    0,  245,   18,    0,   98,   19,    0,
    9,   10,   11,    9,   10,   11,  162,    0,  162,    0,
    0,  220,  257,    0,    0,   29,   79,    0,   29,   20,
  108,    0,    0,    4,    0,   79,   26,    0,  267,   26,
   75,    0,    4,   75,    9,   10,   11,    0,    0,    0,
    0,  165,    0,    9,   10,   11,  278,   18,  280,   18,
   19,    0,   19,   29,    0,  238,    0,    0,    0,    0,
   75,   75,   29,  171,   26,   29,   29,   29,   29,   29,
   29,   20,    0,   20,    0,   29,   29,   29,   29,    0,
   29,   29,   29,    0,  204,   18,    0,   41,   19,   96,
   41,   41,   41,   41,   41,   41,    0,    0,    0,    0,
   41,   41,   41,   41,    0,   41,   41,   41,    1,   20,
  206,    2,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,    0,    7,    8,    0,    9,   10,   11,    1,
    0,  230,    2,    0,    0,    3,    4,    5,    0,    0,
    0,    0,    6,   79,    7,    8,    0,    9,   10,   11,
    4,   42,    1,    0,    0,  106,    0,    0,    3,    4,
    5,    9,   10,   11,  161,    6,    0,  107,    8,    0,
    9,   10,   11,    1,    0,   42,  106,    0,    0,    3,
    4,    5,    0,    0,    0,  161,    6,   79,  107,    8,
    0,    9,   10,   11,    4,    1,  161,    0,  106,    0,
    0,    3,    4,    5,    0,    9,   10,   11,    6,  148,
  107,    8,    0,    9,   10,   11,    1,    0,    0,  106,
  226,   96,    3,    4,    5,    0,    0,    0,   96,    6,
  246,  107,    8,    0,    9,   10,   11,    0,    0,   96,
   96,   96,    1,  261,    0,  106,    0,    0,    3,    4,
    5,    0,    0,    0,    0,    6,    0,  107,    8,    0,
    9,   10,   11,    1,    0,    0,  106,    0,    0,    3,
    4,    5,    0,    0,    0,    0,    6,    0,  107,    8,
    0,    9,   10,   11,    0,   41,    0,    0,    2,  176,
  177,    3,    0,    5,    0,    0,  145,    0,   41,  145,
    7,  106,  243,  244,    3,    0,    5,    0,    0,   41,
    0,    0,    2,  107,  233,    3,    0,    5,    0,   41,
    0,    0,  106,    0,    7,    3,    0,    5,    0,    0,
   41,    0,    0,  106,  107,  266,    3,    0,    5,    0,
    0,   41,    0,    0,    2,  107,    0,    3,    0,    5,
    0,    0,   41,  145,    0,  106,    7,  145,    3,    0,
    5,    0,   41,    0,    0,  106,    0,  107,    3,    0,
    5,    0,   17,    0,    0,   41,    0,  107,  106,    0,
    0,    3,    0,    5,   17,    0,   17,    0,    0,    1,
  107,    0,    2,    0,    0,    3,    4,    5,    0,    0,
  145,  145,    6,    0,    7,    8,    0,    9,   10,   11,
    0,    0,    0,    0,   89,    0,    0,    0,    0,    0,
    0,    0,   17,    0,    0,    0,    0,    0,    0,  145,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  149,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   40,   44,  123,   41,   40,  123,   44,
   41,   44,   41,   46,   45,   44,   41,   44,   45,   44,
  123,    8,    0,   53,   54,   14,  123,   41,   61,   45,
   46,   45,   35,   36,  123,  123,   44,  125,   64,  169,
   44,   41,   42,   43,   44,   45,    0,   47,   42,  123,
   40,  125,   40,   47,  123,   40,   46,  123,   45,   46,
   60,   50,   62,   41,  150,   43,   44,   45,    0,   44,
   73,   61,   44,   44,  160,   44,   92,  207,  271,   44,
  264,   97,   60,  123,   62,  261,  262,   41,   59,   43,
   44,   45,    0,   81,   59,   44,  122,  257,  278,   87,
  257,  131,  261,  262,  264,   92,   60,  125,   62,   41,
   97,   43,   44,   45,    0,  275,  276,  277,  275,  276,
  277,  257,  261,  262,  260,  125,  156,  263,   60,  265,
   62,   41,   43,   44,   45,   45,  272,  257,   45,  257,
  132,  133,  257,  229,  127,  128,  264,  125,  174,    0,
  176,  258,  140,  141,  257,  257,  274,  275,  276,  277,
  257,   43,   44,   45,   41,   41,   41,  264,  257,  257,
   43,  125,   45,   41,   44,    0,  264,  257,  275,  276,
  277,   43,   40,   45,  187,  274,  212,   60,  257,   62,
  264,  260,  222,  125,  263,  264,  265,  125,  125,    0,
  188,  270,   44,  272,  273,  257,  275,  276,  277,   40,
  257,  214,  215,  260,   44,  257,  263,  125,  257,  125,
  257,    0,  257,  271,  257,  272,  257,  258,  257,   44,
  257,  258,  257,  275,  276,  277,  275,  276,  277,  125,
  275,  276,  277,  257,  258,    0,  275,  276,  277,  257,
  275,  276,  277,  257,   44,  259,   41,  257,   41,   41,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
   40,  262,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,  262,  275,  276,  277,
  125,   44,    0,  257,   44,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   44,  275,  276,  277,  125,  257,  262,   44,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,    0,  275,  276,  277,  125,  257,  258,  257,
  257,  258,  260,  261,  262,  263,  264,  265,   44,  113,
  114,   44,  270,  271,  272,  273,  262,  275,  276,  277,
  125,  257,   44,   44,  260,  261,  262,  263,  264,  265,
  257,   41,   44,  262,  270,  271,  272,  273,   44,  275,
  276,  277,   44,  266,  267,  268,  269,   44,  275,  276,
  277,   44,  262,  125,  260,   44,  257,   40,  261,  260,
  261,  262,  263,  264,  265,  169,   41,  262,  172,  270,
  271,  272,  273,   44,  275,  276,  277,  125,    0,    0,
  257,  125,  257,   41,   41,  260,  261,  262,  263,  264,
  265,   41,  260,   12,  114,  270,  271,  272,  273,  249,
  275,  276,  277,  207,  123,   83,  257,  184,  172,  260,
  261,  262,  263,  264,  265,   -1,   -1,  125,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   41,   -1,   43,   44,   45,   59,   -1,
   59,   -1,   -1,  125,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,  107,  275,  276,  277,
   -1,   -1,  113,  114,  113,  114,    0,   -1,  257,   -1,
  125,  260,   -1,  125,  263,   -1,  265,    0,   12,  257,
   14,   -1,  260,  272,   -1,  263,  264,  265,   -1,   12,
   -1,   14,  270,    0,  272,  273,    0,  275,  276,  277,
    7,   35,   36,    7,  125,   12,   -1,   14,   12,   -1,
   14,   -1,  161,  125,   -1,   -1,   50,    0,  169,   -1,
  169,  172,   -1,  172,    7,   59,   -1,   50,   -1,   12,
   -1,   14,   -1,   -1,   -1,   42,   59,   -1,   42,   73,
   -1,   -1,   -1,   50,   -1,  257,   50,   -1,  199,   -1,
  125,  202,  264,  202,   -1,   -1,  207,   64,  207,   42,
   64,   -1,   -1,  275,  276,  277,   -1,   50,   -1,   -1,
   -1,   -1,   -1,   -1,  225,  125,  227,   -1,  227,  113,
  114,   64,   -1,   90,   -1,   -1,   90,   -1,  199,   -1,
  113,  114,  243,   -1,   -1,   -1,  125,   -1,  249,   -1,
  249,  135,  257,   -1,   -1,  257,   -1,   90,  259,  264,
   -1,   -1,  264,   -1,  225,  122,   -1,  125,  122,   -1,
  275,  276,  277,  275,  276,  277,  277,   -1,  279,   -1,
   -1,  125,  243,   -1,   -1,  169,  257,   -1,  172,  122,
  125,   -1,   -1,  264,   -1,  257,  169,   -1,  259,  172,
  184,   -1,  264,  187,  275,  276,  277,   -1,   -1,   -1,
   -1,  125,   -1,  275,  276,  277,  277,  174,  279,  176,
  174,   -1,  176,  207,   -1,  125,   -1,   -1,   -1,   -1,
  214,  215,  257,  125,  207,  260,  261,  262,  263,  264,
  265,  174,   -1,  176,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,  125,  212,   -1,  257,  212,  125,
  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,  271,  272,  273,   -1,  275,  276,  277,  257,  212,
  125,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,  257,
   -1,  125,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  257,  272,  273,   -1,  275,  276,  277,
  264,  123,  257,   -1,   -1,  260,   -1,   -1,  263,  264,
  265,  275,  276,  277,  123,  270,   -1,  272,  273,   -1,
  275,  276,  277,  257,   -1,  123,  260,   -1,   -1,  263,
  264,  265,   -1,   -1,   -1,  123,  270,  257,  272,  273,
   -1,  275,  276,  277,  264,  257,  123,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,  275,  276,  277,  270,  125,
  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,
  125,  257,  263,  264,  265,   -1,   -1,   -1,  264,  270,
  125,  272,  273,   -1,  275,  276,  277,   -1,   -1,  275,
  276,  277,  257,  125,   -1,  260,   -1,   -1,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,
  275,  276,  277,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,   -1,  257,   -1,   -1,  260,  261,
  262,  263,   -1,  265,   -1,   -1,   85,   -1,  257,   88,
  272,  260,  261,  262,  263,   -1,  265,   -1,   -1,  257,
   -1,   -1,  260,  272,  262,  263,   -1,  265,   -1,  257,
   -1,   -1,  260,   -1,  272,  263,   -1,  265,   -1,   -1,
  257,   -1,   -1,  260,  272,  262,  263,   -1,  265,   -1,
   -1,  257,   -1,   -1,  260,  272,   -1,  263,   -1,  265,
   -1,   -1,  257,  142,   -1,  260,  272,  146,  263,   -1,
  265,   -1,  257,   -1,   -1,  260,   -1,  272,  263,   -1,
  265,   -1,    0,   -1,   -1,  257,   -1,  272,  260,   -1,
   -1,  263,   -1,  265,   12,   -1,   14,   -1,   -1,  257,
  272,   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
  189,  190,  270,   -1,  272,  273,   -1,  275,  276,  277,
   -1,   -1,   -1,   -1,   42,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   50,   -1,   -1,   -1,   -1,   -1,   -1,  218,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   90,
};
}
final static short YYFINAL=13;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
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
null,null,null,null,null,null,null,"ID","CTE","CADENA","IF","ELSE","ENDIF",
"PRINT","VOID","RETURN","COMP_MAYOR_IGUAL","COMP_MENOR_IGUAL","COMP_IGUAL",
"COMP_DISTINTO","CLASS","WHILE","DO","INTERFACE","IMPLEMENT","INT","ULONG",
"FLOAT","OPERADOR_MENOS",
};
final static String yyrule[] = {
"$accept : programa",
"programa : '{' sentencias '}'",
"programa : sentencias '}'",
"programa : '{' sentencias",
"programa :",
"sentencias : sentencia",
"sentencias : sentencias sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : sentencia_asignacion",
"sentencia_ejecutable : sentencia_invocacion_funcion",
"sentencia_ejecutable : sentencia_imprimir",
"sentencia_ejecutable : sentencia_seleccion",
"sentencia_ejecutable : sentencia_iterativa_do_while",
"sentencia_ejecutable : sentencia_return",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : sentencia_asignacion",
"sentencia_ejecutable_funcion : sentencia_invocacion_funcion",
"sentencia_ejecutable_funcion : sentencia_imprimir",
"sentencia_ejecutable_funcion : sentencia_seleccion_funcion",
"sentencia_ejecutable_funcion : sentencia_iterativa_do_while_funcion",
"sentencia_return : RETURN ','",
"sentencia_return : RETURN",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE '(' condicion ')' ','",
"sentencia_iterativa_do_while : DO bloque_sentencias_ejecutables WHILE '(' condicion ')'",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')' ','",
"sentencia_iterativa_do_while_funcion : DO bloque_sentencias_ejecutables_funcion WHILE '(' condicion ')'",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ENDIF",
"sentencia_seleccion : IF '(' ')' bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' ')' bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' ELSE bloque_sentencias_ejecutables ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' bloque_sentencias_ejecutables ELSE ENDIF ','",
"sentencia_seleccion : IF '(' condicion ')' ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF",
"$$1 :",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF $$1 IF '(' ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' ENDIF ','",
"bloque_sentencias_ejecutables : sentencia_ejecutable",
"bloque_sentencias_ejecutables : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"bloque_sentencias_ejecutables_funcion : sentencia_return",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}'",
"sentencias_ejecutables_funcion_inalcanzable : sentencia_ejecutable_funcion_inalcanzable",
"sentencias_ejecutables_funcion_inalcanzable : sentencias_ejecutables_funcion_inalcanzable sentencia_ejecutable_funcion_inalcanzable",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_return",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_ejecutable_funcion",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"sentencia_imprimir : PRINT CADENA ','",
"sentencia_imprimir : PRINT CADENA",
"sentencia_imprimir : PRINT ','",
"sentencia_imprimir : PRINT ID ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ')'",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' ')'",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')'",
"lista_expresiones_invocacion_funcion_exceso : expresion",
"lista_expresiones_invocacion_funcion_exceso : expresion ',' expresion",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion ','",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion",
"sentencia_asignacion : sentencia_objeto_identificador '=' ','",
"sentencia_objeto_identificador : ID",
"sentencia_objeto_identificador : sentencia_objeto_identificador '.' ID",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_clase",
"sentencia_declarativa : declaracion_interfaz",
"declaracion_variable : tipo lista_de_variables ','",
"declaracion_variable : tipo lista_de_variables",
"declaracion_variable : tipo ','",
"declaracion_interfaz : INTERFACE ID '{' bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE '{' bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE '}'",
"declaracion_interfaz : INTERFACE ID bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE ID '}'",
"bloque_encabezado_funcion : encabezado_funcion ','",
"bloque_encabezado_funcion : encabezado_funcion ',' bloque_encabezado_funcion",
"sentencia_declarativa_clase : tipo lista_de_variables ','",
"sentencia_declarativa_clase : tipo lista_de_variables",
"sentencia_declarativa_clase : declaracion_funcion",
"sentencia_declarativa_clase : ID ','",
"declaracion_clase : CLASS ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT ID",
"declaracion_clase : CLASS ID IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID bloque_sentencias_declarativas_clase '}'",
"bloque_sentencias_declarativas_clase : sentencia_declarativa_clase",
"bloque_sentencias_declarativas_clase : bloque_sentencias_declarativas_clase sentencia_declarativa_clase",
"declaracion_funcion : encabezado_funcion cuerpo_funcion",
"encabezado_funcion : VOID ID '(' parametro_funcion ')'",
"encabezado_funcion : VOID ID '(' ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')'",
"encabezado_funcion : VOID '(' parametro_funcion ')'",
"encabezado_funcion : VOID '(' ')'",
"encabezado_funcion : VOID ID parametro_funcion ')'",
"encabezado_funcion : VOID ID ')'",
"encabezado_funcion : VOID ID",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return '}'",
"cuerpo_funcion : '{' sentencia_return '}'",
"cuerpo_funcion : '{' sentencias_funcion '}'",
"cuerpo_funcion : '{' '}'",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}'",
"cuerpo_funcion : '{' sentencia_return sentencias_funcion_inalcanzable '}'",
"sentencias_funcion_inalcanzable : sentencia_funcion_inalcanzable",
"sentencias_funcion_inalcanzable : sentencias_funcion_inalcanzable sentencia_funcion_inalcanzable",
"sentencia_funcion_inalcanzable : sentencia_declarativa",
"sentencia_funcion_inalcanzable : sentencia_return",
"sentencia_funcion_inalcanzable : sentencia_ejecutable_funcion",
"lista_parametros_funcion_exceso : parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso ',' parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso parametro_funcion",
"parametro_funcion : tipo ID",
"lista_de_variables : ID",
"lista_de_variables : lista_de_variables ';' ID",
"tipo : INT",
"tipo : ULONG",
"tipo : FLOAT",
"tipo : ID",
"condicion : expresion comparador expresion",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_IGUAL",
"comparador : COMP_DISTINTO",
"comparador : '>'",
"comparador : '<'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : ID OPERADOR_MENOS",
"factor : constante",
"constante : CTE",
"constante : '-' CTE",
};

//#line 310 "./src/compilador/gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = Logger.getInstance();
public static TablaDeSimbolos ts = TablaDeSimbolos.getInstance();
public static Parser parser = null;
public static int MIN_INT_VALUE = -(int) (Math.pow(2, 15));
public static int MAX_INT_VALUE = (int) (Math.pow(2, 15) - 1);

/** Chequea, para los INT, que el valor positivo no supere el valor maximo */
public void corregirConstantePositivaEntera(String constante) {
	if (constante.contains("_i")) {
		//se recibio un INT con signo positivo
		boolean exceptionOutOfRange = false;
		int cte = 0;
		String constanteValue = constante.toString().split("_")[0];

		try {
			cte = Integer.parseInt(constanteValue);
		} catch (NumberFormatException e) {
			exceptionOutOfRange = true;
		}

		if (cte > MAX_INT_VALUE || exceptionOutOfRange) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + constante + ", se trunca al valor " + MAX_INT_VALUE + "_i");

			ts.swapLexemas(constante, MAX_INT_VALUE + "_i");
		}
	}
}

public void constanteConSigno(String constante) {
	/** Check de float negativos */
	if (constante.contains(".")) {
		
		String negConstante = "-"+constante;
		Double parsedDouble = Double.parseDouble(negConstante);
		
		if (parsedDouble < -1.17549435E-38 && -3.40282347E+38 > parsedDouble) {
			logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");
			
			if (-3.40282347E+38 < parsedDouble) {
				negConstante = new String("-3.40282347E+38");
			} else {
				negConstante = new String("-1.17549435E-38");
			}
		}
		
		ts.swapLexemas(constante, negConstante);
	} else {

		if (constante.contains("_ul")) {
			//se recibio un ULONG con signo negativo
			logger.logWarning("[Parser] No se admiten ULONG con valores negativos: " + "-"+constante + ", se trunca a 0_ul");
		
			ts.swapLexemas(constante, "0_ul");
		} else {
			// se recibio un INT negativo
			String negConstante = "-"+constante;
			boolean exceptionOutOfRange = false;
			int cte = 0;

			String negConstanteValue = negConstante.toString().split("_")[0];

			try {
				cte = Integer.parseInt(negConstanteValue);
			} catch (NumberFormatException e) {
				exceptionOutOfRange = true;
			}

			if (cte < MIN_INT_VALUE || exceptionOutOfRange) {
				logger.logWarning("[Parser] Rango invalido para la constante: " + negConstante + ", se trunca al rango permitido");

				ts.swapLexemas(constante, MIN_INT_VALUE + "_i");
			} else {
				ts.swapLexemas(constante, negConstante);
			}
		}
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
		
		boolean fileOpenSuccess = fileHelper.open(archivo_a_leer);
		
		if (fileOpenSuccess) {
			parser = new Parser();
			lexico = new AnalizadorLexico(fileHelper);
			
	        parser.run();
	
			String path = new File(archivo_a_leer).getAbsolutePath();
	        
	        Output out = new Output(path);
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintactico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", ts.print());
		}
	}
}
//#line 811 "Parser.java"
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
//#line 18 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 19 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' al principio del programa"); }
break;
case 3:
//#line 20 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '}' al final del programa"); }
break;
case 4:
//#line 21 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Programa vacio"); }
break;
case 14:
//#line 40 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
break;
case 25:
//#line 63 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego del RETURN"); }
break;
case 26:
//#line 67 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 27:
//#line 68 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 28:
//#line 72 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 29:
//#line 73 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 30:
//#line 77 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 31:
//#line 78 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 32:
//#line 79 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 33:
//#line 80 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 34:
//#line 81 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF ELSE"); }
break;
case 35:
//#line 82 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF"); }
break;
case 36:
//#line 83 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 37:
//#line 84 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 38:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF"); }
break;
case 39:
//#line 89 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 40:
//#line 90 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 41:
//#line 91 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 42:
//#line 92 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 43:
//#line 93 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF ELSE"); }
break;
case 44:
//#line 94 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta condicion en sentencia IF"); }
break;
case 45:
//#line 95 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 46:
//#line 96 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF ELSE"); }
break;
case 47:
//#line 97 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Faltan sentencias ejecutables en sentencia IF"); }
break;
case 55:
//#line 114 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 56:
//#line 115 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 63:
//#line 134 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 64:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 65:
//#line 136 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta CADENA en Sentencia PRINT"); }
break;
case 66:
//#line 137 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 67:
//#line 141 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 68:
//#line 142 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 69:
//#line 143 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 70:
//#line 144 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 71:
//#line 145 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 72:
//#line 146 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 75:
//#line 155 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 76:
//#line 156 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 77:
//#line 157 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
break;
case 84:
//#line 173 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 85:
//#line 174 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); }
break;
case 86:
//#line 175 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
break;
case 88:
//#line 180 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 89:
//#line 181 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 90:
//#line 182 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 91:
//#line 183 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 92:
//#line 184 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 95:
//#line 193 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 96:
//#line 194 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en delcaracion de lista de variables en CLASS"); }
break;
case 99:
//#line 200 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 100:
//#line 201 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 101:
//#line 202 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 102:
//#line 203 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 103:
//#line 204 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en IMPLEMENT de clase"); }
break;
case 104:
//#line 205 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 105:
//#line 206 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 106:
//#line 207 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 109:
//#line 216 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 112:
//#line 222 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 113:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 114:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 115:
//#line 225 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 116:
//#line 226 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 117:
//#line 227 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 118:
//#line 228 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 121:
//#line 234 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 122:
//#line 235 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 125:
//#line 241 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 126:
//#line 242 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 156:
//#line 305 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 157:
//#line 306 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1260 "Parser.java"
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
