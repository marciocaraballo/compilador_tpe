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
   21,   21,   21,   21,   21,   21,   21,   19,   19,   19,
   20,   20,   26,   26,   26,   26,   26,   26,   28,   28,
   29,   29,   29,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   13,   13,   13,   13,   13,   32,   32,
   32,   35,   35,   34,   34,   36,   34,    9,    9,    9,
   37,   37,   38,   38,   38,   38,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    5,   42,   42,   42,   30,
   30,   30,   30,   30,   31,   31,   41,   41,   43,   43,
   43,   43,   43,   27,   44,   44,   45,   45,   17,   17,
   17,   40,   40,   47,   40,   40,   40,   46,   46,   39,
   39,   48,   48,   49,   49,   24,   24,   24,   50,   50,
   50,   50,   50,   50,   33,   33,   33,   51,   51,   51,
   52,   52,   52,   54,   54,   55,   55,   56,   56,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   53,   53,
   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    4,
    5,   10,   13,   13,   16,    1,    1,    2,    8,    8,
    8,   11,   11,   15,   15,   15,   15,    9,   10,   10,
    7,    9,    1,    1,    1,    4,    4,    3,    1,    2,
    1,    1,    1,    6,    7,    5,    5,    5,    5,    6,
    6,    6,    6,    5,    4,    3,    3,    4,    1,    3,
    5,    1,    3,    2,    1,    0,    2,    3,    2,    2,
    1,    3,    3,    2,    2,    1,    1,    2,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    1,    1,    2,
    4,    1,    3,    3,    2,    1,    1,    3,    7,    6,
    6,    6,    6,    1,    1,    3,    1,    2,    4,    3,
    3,    9,    8,    0,   17,    7,    6,    1,    2,    8,
   10,    1,    3,    1,    2,    3,    2,    2,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    3,    4,    1,    3,    1,    1,    5,
    5,    4,    4,    4,    4,    4,    4,    4,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  192,  191,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,  107,  111,    0,  109,  113,
  115,  127,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  117,  118,  119,
  135,    0,  108,  112,  110,  114,  116,    0,  100,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,    0,  189,    0,  140,    0,    0,  170,  172,
  173,   19,  159,  160,  161,  162,  163,  164,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  120,    0,  125,  137,
    0,    0,  105,    0,   98,    0,   15,    0,    0,    0,
    0,    0,   23,   26,    0,    0,   24,   27,   28,   30,
   32,   34,   36,    0,    0,  128,    1,    0,  190,    0,
    0,  139,    0,    0,    0,    0,    0,  185,  183,  186,
    0,  184,    0,  182,    0,    0,    0,    0,   94,    0,
    0,   97,    0,    0,    0,    0,  134,  124,    0,  136,
  138,    0,    0,  103,  102,    0,    0,    0,    0,    0,
   63,   71,   64,    0,    0,   69,   72,   73,    0,   29,
   31,   33,   35,   37,   21,    0,   25,    0,   40,    0,
  178,  174,  179,    0,  176,    0,    0,  168,  169,    0,
    0,  181,  180,    0,   79,    0,    0,   78,    0,    0,
   77,    0,    0,    0,    0,  121,    0,    0,    0,    0,
    0,    0,   86,    0,    0,    0,   70,    0,   22,   41,
    0,    0,  175,    0,  152,    0,   74,    0,   83,   82,
    0,   81,    0,    0,    0,  147,    0,  131,  133,    0,
  132,    0,   88,    0,    0,   68,    0,    0,    0,    0,
    0,  177,  154,    0,    0,    0,   75,    0,   91,  149,
    0,    0,    0,  129,    0,   84,    0,   66,   67,    0,
    0,    0,  153,  155,    0,  150,    0,  144,    0,  143,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   93,    0,  142,   48,   38,    0,    0,    0,    0,
    0,    0,   61,    0,    0,    0,    0,  151,    0,    0,
    0,   39,    0,   49,   51,   50,    0,    0,    0,   46,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,    0,    0,   62,    0,
    0,    0,    0,    0,    0,    0,    0,   42,   59,   60,
    0,    0,    0,    0,    0,   52,    0,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   43,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,  145,   56,   57,
   54,    0,   55,    0,   45,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          3,
    4,   15,  253,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  340,  126,  127,  316,  129,  130,  131,  132,
  133,  317,  341,   89,  306,  184,  135,  185,  186,  187,
  188,  100,   90,  101,  279,  102,   60,   61,   29,   30,
   31,   51,   32,   52,  111,  254,  313,  246,  274,   91,
   78,   79,   80,   81,  204,  205,
};
final static short yysindex[] = {                       -98,
    0, 1039,    0,  -84,  -37,   43,  -31,  -14,   98,  695,
    0,    0,  349,  -58,  766,    0,    0,    0,    0,    0,
    0,    0,   -9,   72,  -64,    0,    0,   87,    0,    0,
    0,    0,  789,  813,    8, -115,  173,  110,  113,  -21,
   25,  -27,   73,  -93,   91,  111, 1085,    0,    0,    0,
    0,  -97,    0,    0,    0,    0,    0,  -53,    0,  123,
  162,    0,    0,  170,    0,  127,    0,  743,  -51,    0,
  836,    0,  180,    0,  -28,    0,  219,  124,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  204,  103,
   60,  195,  209,   -3,   17,  212,   33,  217,   34,  245,
  279,   57,  274,  293,   77,  299,    0,   -5,    0,    0,
  506,  107,    0,   92,    0,   95,    0,  348,   39,  358,
  676,  523,    0,    0,  282, 1029,    0,    0,    0,    0,
    0,    0,    0,  289,  360,    0,    0,   23,    0,   60,
   60,    0,   60,   60,  168,   60,   74,    0,    0,    0,
  367,    0,  375,    0,  378,   82,   40,   82,    0,  380,
   82,    0,   82,  382,  318, -100,    0,    0,  386,    0,
    0,  142,  405,    0,    0,  173,   49,  270,  173, 1102,
    0,    0,    0,  174, 1102,    0,    0,    0,  407,    0,
    0,    0,    0,    0,    0,  330,    0,  331,    0,  186,
    0,    0,    0,   51,    0,  124,  124,    0,    0,  585,
   74,    0,    0,   82,    0,  402,   82,    0,   82,  418,
    0,   82, 1039,  346,  859,    0,  412,   19,  422,  443,
  427,  326,    0,  446,  946,  450,    0,  173,    0,    0,
  676,   62,    0,  861,    0,   84,    0,   82,    0,    0,
   82,    0, 1039,  368, 1039,    0,  389,    0,    0,  432,
    0,  239,    0,  444,  254,    0,  399,  411,  173,  486,
  263,    0,    0,  992,  585,  479,    0,  498,    0,    0,
  485,  420,  487,    0,  714,    0,  424,    0,    0,  507,
  291,  510,    0,    0,  290,    0,   82,    0,  496,    0,
  622, 1129,  313,  314,  315,  300, 1062,  520,  733,  173,
  521,    0,  309,    0,    0,    0,  963,  524,  527,  546,
 -104,  -19,    0, 1129,  313,  315,  569,    0,  571,  350,
  489,    0,  490,    0,    0,    0,  576,  357, 1129,    0,
    0,  355,  560,  518,  519,  963,  586,  173,  528,  117,
  128,  173,  472,  587,    0,  589,  594,  535,    0,  632,
 1129,  -82,  617,  558,  625,  644,  561,    0,    0,    0,
  134,  434, 1119, 1129,  433,    0, 1129,    0,  436,  435,
  578,  579,  574,  580,  973,  645, 1002,  -81,  648, 1129,
 1039,  440,  447,  452,  592,    0,  455, 1129,    0, 1019,
  595,  660,  671,  672,  470,  675,  472,    0,    0,    0,
    0,  678,    0,  610,    0,  478,   67, 1129,  472,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  344,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  484,  484,    0,  685,  885,  904,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  482,    0,  575,
  525,    0,    0,  624,    0,  647,    0,    0,    0,    0,
  751,    0,  294,    0,    0,    0,  201,  325,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  484,    0,   85,    0,
   70,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  549,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   21,   29,    0,    0,    0,
  232,    0,  256,    0,    0,    0,    0,    0,    0,    0,
  484,    0,    0,    0,    0,    0,    0,    0,  923,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  488,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  363,  392,    0,    0,    0,
   41,    0,    0,  629,    0,    0,    0,    0,    0,   86,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  158,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  630,    0,    0,
  484,    0,  639,    0,    0,    0,    0,    0,    0,  415,
    0,    0,    0,  165,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   99,    0,    0,
    0,    0,  449,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  484,    0,    0,    0,
    0,    0,  503,    0,  505,    0,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   53,  468,  -63,   30,    0,    0,    0,    0,  715,
   80,    0,  -24,    0, -116,  -25,   -2,    2, -101,  -94,
  -92,  469,  -22,  -40, -287,  530,   14,  588, -148,   42,
   61,   13,  366, -139,  477,    0,  653,    0,  762,  763,
  764,    4,  717,    0,    0, -132,    0,  513,    0,  702,
  269,  273, -105,    0,    0,  552,
};
final static int YYTABLESIZE=1407;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
   59,   26,  106,   27,  124,   27,   36,   26,   40,  197,
   53,   27,   26,  103,   54,   28,   27,   28,  339,   96,
  192,  220,  225,   28,    2,   42,   28,  193,   28,  194,
   26,   26,  203,  342,   27,   27,  237,  151,   33,   48,
  374,  398,  128,  125,   26,  134,   28,   28,   27,   65,
  110,   49,   75,  168,  104,  150,   34,  153,   68,  260,
   28,  157,  124,  202,   97,   98,   76,   75,   26,  158,
   50,  173,   27,  155,  375,  152,   48,  259,  177,  157,
  216,  156,   37,   75,   28,   71,  237,  158,   49,  231,
  156,  243,  257,   75,  242,  182,  181,  217,  183,  156,
  128,  196,   66,  198,   75,  343,   75,   50,   26,  157,
   89,  278,   27,  105,  171,   82,  140,   75,  141,  190,
  280,  169,  282,  191,   28,   95,   90,   89,   95,  375,
   67,  228,   87,   88,   86,  230,  203,   43,  234,   92,
   48,   64,   95,   90,   69,  140,  172,  141,  108,  107,
   92,   75,   49,   93,  182,  337,   92,  278,    1,  182,
  224,  119,   87,   88,   86,  143,   87,   88,   86,  109,
  144,   50,    5,  338,   35,    6,  112,  337,  337,    7,
    8,  115,  227,  119,  119,  117,   75,    9,   10,  418,
  322,   11,   12,   13,   14,  338,  338,  270,   58,  192,
  141,   87,   88,   86,  113,  116,  193,   26,  194,  182,
  267,   27,  268,   36,  114,  182,  181,   75,  183,  138,
   26,   10,   26,   28,   27,   38,   27,   39,  290,  139,
   35,  188,   87,   88,   86,   94,   28,   95,   28,  245,
  337,   26,   41,  124,  145,   27,  119,   64,   11,   12,
   26,  167,   26,  148,   27,  187,   27,   28,  401,  304,
  303,  140,  305,  141,   73,   74,   28,  149,   28,  327,
  154,   26,   26,  273,  158,   27,   27,  142,  315,  201,
   74,  128,   87,  304,  325,  160,  326,   28,   28,   85,
  159,  332,  331,  171,  333,   73,   74,  344,  190,  345,
   11,   12,  191,  294,  245,   73,   74,  360,   11,   12,
  233,  366,  140,  162,  141,  315,   73,   74,  201,   74,
  332,  358,  161,  333,  167,  141,  337,  332,  367,   73,
   74,  163,  119,  164,  171,  171,  171,  165,  171,  166,
  171,   83,   84,   85,  338,  275,  276,  332,  383,  174,
  384,   58,  171,  171,  171,  171,  188,   11,   12,  332,
  395,  332,  165,   73,   74,  167,  264,  167,  140,  167,
  141,   83,   84,   85,  332,   83,   84,   85,  362,  363,
  187,  332,  414,  167,  167,  167,  167,  176,   26,  364,
  365,  166,   27,  332,  395,  381,  363,  179,   73,   74,
   77,  134,   20,  165,   28,  165,  195,  165,  206,  207,
   83,   84,   85,  199,  130,  208,  209,  200,  171,   87,
   87,  165,  165,  165,  165,  212,   85,   85,  210,   73,
   74,   87,  166,  213,  166,  214,  166,  219,   85,  222,
  223,   83,   84,   85,  226,  229,  238,  236,  146,  167,
  166,  166,  166,  166,  239,  240,  147,  141,  241,  248,
  141,  251,  141,  141,  141,  141,  141,  141,  255,   16,
  258,   16,  141,  141,  141,  141,  141,  141,  141,  141,
  261,  106,   63,  262,  178,  263,  265,  165,  188,  269,
  284,  188,  281,  188,  188,  188,  188,  188,  188,  285,
   16,   63,  286,  188,  188,  188,  188,  188,  188,  188,
  188,  211,  187,  283,  287,  187,  166,  187,  187,  187,
  187,  187,  187,  288,  101,  106,  291,  187,  187,  187,
  187,  187,  187,  187,  187,  289,  292,  296,   63,  130,
  106,  297,  232,  298,  299,  300,  307,  308,  104,  310,
  171,  309,  311,  171,  314,  171,  171,  171,  171,  171,
  171,  321,  171,  171,  171,  171,  171,  171,  171,  171,
  171,  171,  171,  146,   99,  318,  319,  320,  323,  328,
  329,  167,  334,  101,  167,  335,  167,  167,  167,  167,
  167,  167,  104,  167,  167,  167,  167,  167,  167,  167,
  167,  167,  167,  167,  336,   44,  106,  104,    6,  347,
  348,  349,    7,  350,  351,  352,  337,  354,  355,  165,
    9,   10,  165,   20,  165,  165,  165,  165,  165,  165,
  170,  165,  165,  165,  165,  165,  165,  165,  165,  165,
  165,  165,  356,  357,  359,  368,   16,  369,  166,  101,
  361,  166,  370,  166,  166,  166,  166,  166,  166,  371,
  166,  166,  166,  166,  166,  166,  166,  166,  166,  166,
  166,  130,  372,  104,  130,  376,  130,  130,  130,  130,
  377,  130,   20,  378,  379,  380,  130,  130,  130,  130,
  130,  130,  130,  130,  382,  386,  388,  389,  392,   99,
  390,  391,  402,  396,  393,  146,  399,  244,  146,  403,
  146,  146,  146,  146,  404,  146,  405,  406,  409,  408,
  146,  146,  146,  146,  146,  146,  146,  146,   44,  410,
  411,  189,  412,  413,  416,    7,  415,  119,  106,  417,
   96,  106,  134,  120,  121,  106,  106,  106,   20,  122,
    4,   76,   80,  106,  106,   99,   99,  106,  106,  106,
  106,   65,   44,  148,   46,    6,   47,  235,  175,    7,
  271,   16,   45,  312,   55,   56,   57,    9,   10,   44,
   46,  101,  189,   13,  101,  136,    7,  295,  101,  101,
  101,  146,  346,  272,  120,  121,  101,  101,  180,    0,
  101,  101,  101,  101,    0,  104,    0,  353,  104,    0,
    0,   99,  104,  104,  104,    0,    0,   47,    0,    0,
  104,  104,    0,    0,  104,  104,  104,  104,    0,  373,
    0,   99,    0,    0,   99,    0,  302,    0,   99,   99,
   99,   44,  385,    0,    6,  387,   99,   99,    7,    0,
   99,   99,   99,   99,    0,  324,    9,   10,  400,    0,
    0,    0,   13,    0,    0,    0,  407,  123,    0,    0,
  215,    0,  218,    0,    0,   99,    0,  221,   44,    0,
   20,  118,    0,   20,    0,    7,  419,   20,   20,   20,
   62,    0,    0,  120,  121,   20,   20,    0,    0,   20,
   20,   20,   20,   16,    0,    0,   16,    0,    0,    0,
   16,   16,   16,   70,    0,    0,    0,    0,   16,   16,
    0,    0,   16,   16,   16,   16,    0,    0,  247,    0,
    0,  249,   44,  250,    0,  118,  252,   72,    0,    7,
    0,  119,   45,    0,    0,    0,    0,  120,  121,    0,
   46,   44,    0,  122,    6,    0,    0,    0,    7,    0,
  137,   45,  277,    0,    0,   99,    9,   10,    0,   46,
   44,    0,   13,  118,    0,    0,    0,    7,    0,  119,
    0,    0,    0,  256,    0,  120,  121,    0,    0,   44,
    0,  301,  118,    0,    0,    0,    7,    0,  119,    5,
    0,    0,  118,    0,  120,  121,    7,    8,  119,  122,
  122,   99,    0,    0,  120,  121,    0,    0,   11,   12,
  122,   14,    5,    0,    0,    6,    0,    0,  126,    7,
    8,    0,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,    5,    0,  123,    6,    0,
    0,    0,    7,    8,    0,    0,    0,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,    0,    5,
  266,    0,    6,    0,    0,    0,    7,    8,    0,    0,
    0,    0,    0,    0,    9,   10,    0,  330,   11,   12,
   13,   14,    5,    0,    0,    6,    0,  394,    0,    7,
    8,    0,    0,    0,    0,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,    5,  293,   44,    6,    0,
    6,    0,    7,    8,    7,    0,  397,    0,    0,    0,
    9,   10,    9,   10,   11,   12,   13,   14,   13,    0,
    0,  122,    0,  394,  122,    0,    0,    0,  122,    0,
  122,  122,    0,    0,    0,    0,  122,  122,  122,  122,
  126,    0,  122,  126,    0,    0,    0,  126,    0,  126,
  126,    0,    0,    0,    0,  126,  126,  126,  126,  123,
    0,  126,  123,    0,    0,    0,  123,    0,  123,  123,
    0,    0,    0,    0,  123,  123,  123,  123,    0,    0,
  123,    0,   44,    0,    0,  118,    0,    0,    0,    7,
    0,  119,   45,    0,    0,    0,    0,  120,  121,   44,
   46,    0,  118,  122,    0,    0,    7,    0,  119,   44,
    0,    0,  189,    0,  120,  121,    7,    0,  119,    0,
  122,    0,    0,    0,  120,  121,    0,    0,   44,    0,
  122,    6,    0,    0,    0,    7,    0,    0,   44,    0,
    0,  189,    0,    9,   10,    7,    0,    0,    0,   13,
    0,    0,    0,  120,  121,   44,    0,    0,  189,  122,
    0,    0,    7,    0,    0,    5,    0,    0,  118,    0,
  120,  121,    7,    8,  119,    5,  122,    0,    6,    0,
  120,  121,    7,    8,   11,   12,  122,   14,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,    5,    0,
    0,  189,    0,    0,    0,    7,    8,    0,    0,    0,
    0,    0,    0,  120,  121,    0,    0,   11,   12,  122,
   14,   44,    0,    0,    6,    0,    0,    0,    7,    0,
    0,   45,    0,    0,    0,    0,    9,   10,   44,   46,
    0,  189,   13,    0,    0,    7,    0,    0,   45,    0,
    0,    0,    0,  120,  121,   44,   46,    0,  118,  122,
    0,    0,    7,    0,  119,   44,    0,    0,  189,    0,
  120,  121,    7,    0,    0,    0,  122,    0,    0,    0,
  120,  121,    0,    0,    0,    0,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   59,    4,   43,    2,   68,    4,   44,   10,   40,  126,
   13,   10,   15,   41,   13,    2,   15,    4,  123,   41,
  122,  161,  123,   10,  123,   40,   13,  122,   15,  122,
   33,   34,  138,  321,   33,   34,  185,   41,  123,   10,
  123,  123,   68,   68,   47,   68,   33,   34,   47,   59,
   47,   10,   45,   59,   42,   59,    4,   41,  123,   41,
   47,   41,  126,   41,   40,   41,   59,   45,   71,   41,
   10,  112,   71,   41,  362,   59,   47,   59,   40,   59,
   41,   41,   40,   45,   71,   33,  235,   59,   47,   41,
   58,   41,  225,   45,   44,  121,  121,   58,  121,   59,
  126,  126,   23,  126,   45,  125,   45,   47,  111,   97,
   41,  251,  111,   41,  111,   36,   43,   45,   45,  122,
  253,  108,  255,  122,  111,   41,   41,   58,   44,  417,
   59,  172,   60,   61,   62,  176,  242,   40,  179,   41,
  111,  257,   58,   58,   58,   43,   40,   45,   58,   59,
   41,   45,  111,   41,  180,  260,   58,  297,  257,  185,
  261,  266,   60,   61,   62,   42,   60,   61,   62,   59,
   47,  111,  257,  278,  268,  260,  274,  260,  260,  264,
  265,   59,   41,  266,  266,   59,   45,  272,  273,  123,
  307,  276,  277,  278,  279,  278,  278,  238,  257,  301,
    0,   60,   61,   62,  258,   44,  301,  210,  301,  235,
  235,  210,  235,   44,  268,  241,  241,   45,  241,   40,
  223,  273,  225,  210,  223,  257,  225,  259,  269,  258,
  268,    0,   60,   61,   62,  257,  223,  259,  225,  210,
  260,  244,  257,  307,   41,  244,  266,  257,  276,  277,
  253,  257,  255,   59,  253,    0,  255,  244,  391,  285,
  285,   43,  285,   45,  257,  258,  253,   59,  255,  310,
   59,  274,  275,  244,   58,  274,  275,   59,  301,  257,
  258,  307,  125,  309,  309,   41,  309,  274,  275,  125,
  257,  317,  317,    0,  317,  257,  258,  322,  301,  322,
  276,  277,  301,  274,  275,  257,  258,  348,  276,  277,
   41,  352,   43,  257,   45,  338,  257,  258,  257,  258,
  346,  346,   44,  346,    0,  125,  260,  353,  353,  257,
  258,   58,  266,   41,   41,   42,   43,  261,   45,   41,
   47,  269,  270,  271,  278,  262,  263,  373,  373,  258,
  373,  257,   59,   60,   61,   62,  125,  276,  277,  385,
  385,  387,    0,  257,  258,   41,   41,   43,   43,   45,
   45,  269,  270,  271,  400,  269,  270,  271,  262,  263,
  125,  407,  407,   59,   60,   61,   62,   40,  391,  262,
  263,    0,  391,  419,  419,  262,  263,   40,  257,  258,
   35,   58,   59,   41,  391,   43,  125,   45,  140,  141,
  269,  270,  271,  125,    0,  143,  144,   58,  125,  262,
  263,   59,   60,   61,   62,   59,  262,  263,  261,  257,
  258,  274,   41,   59,   43,   58,   45,   58,  274,   58,
  123,  269,  270,  271,   59,   41,   40,  274,    0,  125,
   59,   60,   61,   62,  125,  125,   91,  257,  273,   58,
  260,   44,  262,  263,  264,  265,  266,  267,  123,    2,
   59,    4,  272,  273,  274,  275,  276,  277,  278,  279,
   59,    0,   15,   41,  119,   59,   41,  125,  257,   40,
   59,  260,  125,  262,  263,  264,  265,  266,  267,  261,
   33,   34,   59,  272,  273,  274,  275,  276,  277,  278,
  279,  146,  257,  125,  261,  260,  125,  262,  263,  264,
  265,  266,  267,  125,    0,   44,   41,  272,  273,  274,
  275,  276,  277,  278,  279,  125,  274,   59,   71,  125,
   59,   44,  177,   59,  125,   59,  123,   41,    0,   40,
  257,  261,  263,  260,   59,  262,  263,  264,  265,  266,
  267,  262,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  125,    0,  263,  263,  263,   59,   59,
  272,  257,   59,   59,  260,   59,  262,  263,  264,  265,
  266,  267,   44,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,   59,  257,  125,   59,  260,   41,
   40,  262,  264,  125,  125,   40,  260,  263,   59,  257,
  272,  273,  260,    0,  262,  263,  264,  265,  266,  267,
  125,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  125,  125,   59,   59,    0,   59,  257,  125,
  123,  260,   59,  262,  263,  264,  265,  266,  267,  125,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,   41,  125,  260,   59,  262,  263,  264,  265,
  123,  267,   59,   59,   41,  125,  272,  273,  274,  275,
  276,  277,  278,  279,  261,  263,  261,  263,  125,  125,
  123,  123,  263,   59,  125,  257,   59,  123,  260,  263,
  262,  263,  264,  265,  263,  267,  125,  263,   59,  125,
  272,  273,  274,  275,  276,  277,  278,  279,  257,   59,
   59,  260,  263,   59,  125,  264,   59,  266,  257,  262,
  257,  260,   58,  272,  273,  264,  265,  266,  125,  278,
    0,  123,  123,  272,  273,   41,   42,  276,  277,  278,
  279,  274,  257,  125,  262,  260,  262,  180,  116,  264,
  241,  125,  267,  297,   13,   13,   13,  272,  273,  257,
  275,  257,  260,  278,  260,   69,  264,  275,  264,  265,
  266,   90,  324,  242,  272,  273,  272,  273,  123,   -1,
  276,  277,  278,  279,   -1,  257,   -1,  339,  260,   -1,
   -1,   97,  264,  265,  266,   -1,   -1,  123,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,  361,
   -1,  257,   -1,   -1,  260,   -1,  123,   -1,  264,  265,
  266,  257,  374,   -1,  260,  377,  272,  273,  264,   -1,
  276,  277,  278,  279,   -1,  123,  272,  273,  390,   -1,
   -1,   -1,  278,   -1,   -1,   -1,  398,  125,   -1,   -1,
  156,   -1,  158,   -1,   -1,  161,   -1,  163,  257,   -1,
  257,  260,   -1,  260,   -1,  264,  418,  264,  265,  266,
  125,   -1,   -1,  272,  273,  272,  273,   -1,   -1,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,  266,  125,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,  214,   -1,
   -1,  217,  257,  219,   -1,  260,  222,  125,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,
  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,
  125,  267,  248,   -1,   -1,  251,  272,  273,   -1,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,
   -1,   -1,   -1,  125,   -1,  272,  273,   -1,   -1,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  257,
   -1,   -1,  260,   -1,  272,  273,  264,  265,  266,  125,
  278,  297,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,   -1,  125,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,  125,  260,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,
  125,   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,   -1,  125,  276,  277,
  278,  279,  257,   -1,   -1,  260,   -1,  125,   -1,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,  125,  257,  260,   -1,
  260,   -1,  264,  265,  264,   -1,  125,   -1,   -1,   -1,
  272,  273,  272,  273,  276,  277,  278,  279,  278,   -1,
   -1,  257,   -1,  125,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  257,   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,
  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  257,
   -1,  278,  260,   -1,   -1,   -1,  264,   -1,  266,  267,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,   -1,   -1,
  278,   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  257,
  275,   -1,  260,  278,   -1,   -1,  264,   -1,  266,  257,
   -1,   -1,  260,   -1,  272,  273,  264,   -1,  266,   -1,
  278,   -1,   -1,   -1,  272,  273,   -1,   -1,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,   -1,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  272,  273,  257,   -1,   -1,  260,  278,
   -1,   -1,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,
  272,  273,  264,  265,  266,  257,  278,   -1,  260,   -1,
  272,  273,  264,  265,  276,  277,  278,  279,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,  275,
   -1,  260,  278,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,
   -1,   -1,  264,   -1,  266,  257,   -1,   -1,  260,   -1,
  272,  273,  264,   -1,   -1,   -1,  278,   -1,   -1,   -1,
  272,  273,   -1,   -1,   -1,   -1,  278,
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
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_ejecutable_funcion ENDIF ';'",
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
"bloque_sentencias_ejecutables_do_con_return : sentencia_seleccion_compuesta_con_return",
"bloque_sentencias_ejecutables_do_con_return : sentencias_ejecutables_do_funcion",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion '}'",
"sentencias_ejecutables_do_funcion : sentencia_ejecutable_do_funcion",
"sentencias_ejecutables_do_funcion : sentencias_ejecutables_do_funcion sentencia_ejecutable_do_funcion",
"sentencia_ejecutable_do_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_do_funcion : sentencia_break",
"sentencia_ejecutable_do_funcion : sentencia_continue",
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

//#line 373 ".\gramatica.y"

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
//#line 892 "Parser.java"
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
case 58:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 59:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 60:
//#line 124 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 61:
//#line 128 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 62:
//#line 129 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 76:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 77:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 78:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 79:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 80:
//#line 159 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 81:
//#line 160 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 82:
//#line 161 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 83:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 85:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 86:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 87:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 88:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 91:
//#line 176 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 96:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 97:
//#line 187 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 98:
//#line 191 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 99:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 100:
//#line 193 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 104:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 105:
//#line 204 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 106:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 120:
//#line 228 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 121:
//#line 229 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 122:
//#line 230 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 123:
//#line 231 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 124:
//#line 232 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 125:
//#line 236 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 126:
//#line 237 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 129:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 130:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 131:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 132:
//#line 249 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 133:
//#line 250 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 139:
//#line 268 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 140:
//#line 269 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 141:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 142:
//#line 274 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 143:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 144:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 145:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 146:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 147:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 150:
//#line 288 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 151:
//#line 289 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 157:
//#line 304 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 158:
//#line 305 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 180:
//#line 351 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 181:
//#line 352 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 182:
//#line 353 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 183:
//#line 354 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 184:
//#line 355 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 185:
//#line 356 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 186:
//#line 357 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 187:
//#line 358 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 188:
//#line 359 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 190:
//#line 364 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1341 "Parser.java"
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
