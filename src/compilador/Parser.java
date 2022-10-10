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
   16,   16,   16,   16,    8,    8,   20,   19,   17,   23,
   23,   18,   18,   25,   25,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   13,   13,   13,   13,   13,
   28,   28,   28,   31,   31,   30,   30,   32,   30,    9,
    9,    9,   33,   33,   34,   34,   34,   34,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,   40,   40,
   40,   41,   41,   41,   41,   41,   42,   42,   39,   39,
   43,   43,   43,   43,   43,   26,   44,   44,   27,   27,
   35,   35,   35,   38,   38,   45,   38,   24,   24,   36,
   36,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   36,   36,   36,   36,   36,   46,   46,   46,   46,   22,
   22,   21,   21,   48,   21,   47,   47,   47,   47,   47,
   47,   29,   29,   29,   49,   49,   49,   50,   50,   50,
   52,   52,   53,   53,   54,   54,   37,   37,   37,   37,
   37,   37,   37,   37,   37,   51,   51,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    0,   10,    1,    2,    1,    1,    1,    2,    1,    2,
    1,    2,    1,    2,    4,    5,   52,   19,    9,    1,
    2,    7,    9,    1,    4,    6,    7,    5,    5,    5,
    5,    6,    6,    6,    6,    5,    4,    3,    3,    4,
    1,    3,    5,    1,    3,    2,    1,    0,    2,    3,
    2,    2,    1,    3,    3,    2,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    1,
    1,    2,    4,    1,    3,    3,    2,    1,    1,    3,
    7,    6,    6,    6,    6,    1,    1,    3,    1,    2,
    4,    3,    3,    9,    8,    0,   17,    1,    2,    8,
   10,    7,    7,    7,    7,    7,    7,    9,    9,    9,
    9,    9,    9,    9,    8,    1,    3,    2,    2,    1,
    2,    3,    2,    0,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    3,    4,    1,    3,    1,    1,    5,    5,    4,    4,
    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  179,  178,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   79,   81,   83,   85,
   87,   99,    0,    0,    0,    0,    0,  176,    0,    0,
    0,    0,    0,    0,  157,  159,  160,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   89,  107,   90,
   91,    0,   80,   82,   84,   86,   88,    0,   72,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  112,    0,   19,    0,  177,    0,    0,    0,
  146,  147,  148,    0,    0,  149,  150,  151,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   92,    0,   97,
    0,  109,    0,   77,    0,   70,    0,   15,    0,    0,
    0,    0,    0,   25,   26,    0,    0,   23,   27,   29,
   31,    0,    0,  100,    1,  111,  165,  161,  166,    0,
  163,    0,    0,    0,    0,    0,    0,    0,    0,  155,
  156,  172,  170,  173,    0,  171,    0,  169,    0,    0,
    0,    0,   66,    0,    0,   69,    0,    0,    0,    0,
  106,   96,    0,  108,  110,    0,    0,   75,   74,    0,
    0,    0,    0,    0,   44,    0,   28,   30,   32,   34,
   21,   24,    0,   35,    0,    0,  162,    0,    0,    0,
    0,    0,    0,    0,    0,  168,  167,    0,   51,    0,
    0,   50,    0,    0,   49,    0,    0,    0,    0,   93,
    0,    0,    0,    0,    0,    0,   58,    0,    0,    0,
    0,   36,    0,  164,  140,    0,  138,  141,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
    0,   55,   54,    0,   53,    0,    0,    0,    0,  103,
  105,    0,  104,    0,   60,    0,    0,    0,    0,    0,
    0,  137,    0,  124,    0,  122,    0,    0,  127,    0,
    0,    0,  125,    0,  123,   47,    0,   63,  119,    0,
    0,    0,  101,    0,   56,    0,   45,    0,    0,    0,
    0,    0,  135,    0,    0,    0,  120,    0,    0,    0,
  116,    0,  115,    0,    0,    0,    0,   33,    0,  130,
  128,  132,  133,    0,  131,  129,   65,    0,  114,    0,
    0,   40,    0,    0,   42,    0,    0,  121,    0,    0,
    0,    0,   41,   22,    0,    0,    0,    0,   39,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  117,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   37,
};
final static short yydgoto[] = {                          3,
    4,   15,  266,   17,  209,   19,   20,   21,   22,   23,
   24,   25,  195,  241,  137,  138,  139,  140,  141,  328,
   41,  210,  343,  267,  196,   26,  121,  111,   42,  112,
  298,  113,   70,   71,   27,   28,   29,   30,   31,   59,
   60,   61,   32,   62,  338,  214,   99,   43,   44,   45,
   46,   47,  150,  151,
};
final static short yysindex[] = {                       -95,
    0,  955,    0,  -97,  -35,   38,  -33,  -28,   26,  526,
    0,    0,   98,  -44,  695,    0,    0,    0,    0,    0,
    0,    0,  -42,  -20,  -51,   22,    0,    0,    0,    0,
    0,    0,  719,  742,   36, -175,   56,    0, -169,   63,
   64,   32,   78,  146,    0,    0,    0,   76,   82,   -1,
   -7,    1,   73, -157,  163,   62, 1011,    0,    0,    0,
    0, -142,    0,    0,    0,    0,    0, -201,    0,   87,
   93,    0,    0,  106,    0,  110,    0,  922, -121,    0,
  771,    0,    0,   28,    0,   75,    0, -100,  -25,  -90,
    0,    0,    0,   52,   52,    0,    0,    0,   52,   52,
   52,   52,  135,  152,    2,    6,  175,   10,  192,   11,
  231,  236,   27,  246,  248,   50,  251,    0,  -37,    0,
  875,    0,   58,    0,   48,    0,   79,    0,   61,   70,
  298,  420,  471,    0,    0,  219,  978,    0,    0,    0,
    0,  229,  303,    0,    0,    0,    0,    0,    0,  247,
    0,  685,  685,  633,  685,  146,  146,   17,   17,    0,
    0,    0,    0,    0,  308,    0,  315,    0,  314,   80,
   44,   80,    0,  320,   80,    0,   80,  324,  271,  -87,
    0,    0,  347,    0,    0,   83,  368,    0,    0,   63,
   88,  160,   73, 1011,    0,  137,    0,    0,    0,    0,
    0,    0,  300,    0,  164,   77,    0,  454,    0,  902,
   97,  102,  543,  118,  122,    0,    0,   80,    0,  397,
   80,    0,   80,  423,    0,   80,  955,  346,  955,    0,
  413,   15,  447,  -16,  459,  220,    0,  441,  856,  481,
  258,    0,  420,    0,    0,  912,    0,    0,  685,  474,
  685,  485,  635,  487,  153,  685,  497,  685,  508,    0,
   80,    0,    0,   80,    0,  955,  405,  955,  444,    0,
    0,  512,    0,  655,    0,  513,  -70,  449,   52,  461,
  309,    0,  319,    0,  330,    0,  535,  332,    0,  666,
  537,  336,    0,  338,    0,    0,  558,    0,    0,  552,
  488,  555,    0,  507,    0,  500,    0,  583,  978,  587,
  569,  578,    0,  579,  580,  388,    0,  591,  594,   80,
    0,  595,    0,  179,  945,  597,  922,    0,   52,    0,
    0,    0,    0,  598,    0,    0,    0,  386,    0,  396,
  605,    0,  540, -107,    0,  544,  629,    0,  636,  412,
  419,  623,    0,    0,  626,   52,  630,  648,    0,    0,
  649,  431,   52,  435,  657,  658,  582,   52,  440,  955,
  661,  600,  585,  456,  454,    0,  601, 1028,  454,  609,
 1028,  462,  611,  645,  478,    0,  396,  479,  682,  493,
  707,   52,  713,  494,  396,  486,  634,  454, 1028,  637,
  496,  702,  503,  728,   52,  731,  514,  651,  454, 1028,
  652,  519,  653,  454, 1028,  659,  524,  723,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  387,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  157,    0,    0,   81,
    0,    0,    0,  187,    0,    0,    0,    0,    0,    0,
   79,   79,   81,  730,  795,  818,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  553,    0,  395,
  473,    0,    0,  604,    0,  443,    0,    0,    0,    0,
  791,    0,    0,  126,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -38,    0,
    0,    0,    0,    0,    0,    0,    0,   79,    0,   90,
    0,   49,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   81,    0,  581,    0,    0,    0,   81,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  988,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  214,  238,  -22,  -18,    0,
    0,    0,    0,    0,  262,    0,  285,    0,    0,    0,
    0,    0,    0,    0,   79,    0,    0,    0,    0,    0,
    0,    0,  837,    0,    0,   81,    0,    0,    0,   81,
    0,    0,   81,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  988,    0,    0,    0,    0,    0,  892,    0,
    0,    0,    0,    0,    0,    0,    0,  669,    0,    0,
    0,    0,    0,   86,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -79,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  234,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  671,    0,    0,   79,    0, -101,    0,    0,    0,    0,
    0,  313,    0,    0,    0,  -77,    0,    0,   81,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  343,    0,    0,    0,    0,    0,   95,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   79,
    0,    0,    0,    0,    0,    0,    0,    0,   81,    0,
    0,    0,    0,  369,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,    0,    0,    0,    0,
    0,    0,   81,    0,    0,    0,    0,   81,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   81,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   31,  679,  -67,   -2,    0,    0,    0,    0,  714,
   89,  554,  -73,    0,  502, -117,  663,  664,  676,  -24,
  -39, -202,    0, -180,  559,  -74,  618,   -8,  340, -165,
  504,    0,  687,    0,   16,   25,   37,   39,   45,  -30,
    0,    0,  -49,    0,    0,  887,  780,    0,  460,  464,
  -17,    0,    0,  621,
};
final static int YYTABLESIZE=1306;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  143,  143,  136,  246,   50,   58,   36,  224,
  134,   52,   18,  117,   69,  154,   75,  322,  142,  202,
  143,  182,  145,  118,  274,   33,  122,    2,   63,  144,
   18,   18,  108,  109,   34,  229,  142,   64,   77,  107,
  145,  114,  165,  115,  183,   59,  167,   57,  269,   65,
  169,   66,  229,  142,   58,  272,  124,   67,  143,   94,
  164,   95,  143,   81,  166,   53,  125,  170,  149,  134,
   94,   78,   95,  271,   94,  135,   95,   40,   18,   79,
   39,   74,   39,  187,  220,  299,  146,  301,   87,   61,
  185,   97,   98,   96,   83,   86,   39,  186,  297,  171,
  190,  221,   39,   88,   90,   39,   61,   39,  200,  191,
   35,   76,  203,  116,   39,  148,  103,   39,   58,   39,
  120,   39,  104,  231,   85,  113,   62,   39,  235,   58,
   67,  123,   39,   67,  135,   64,  127,   97,   98,   96,
  144,  144,  144,   62,  344,  126,  232,   67,   63,   36,
  234,   10,   64,  238,  297,  144,  158,   64,  130,    5,
  152,    1,    6,  122,  118,  278,    7,    8,  128,   65,
  155,   66,  378,  228,    9,   10,  381,   67,   11,   12,
   13,   14,   59,   59,   57,   57,  154,  101,  149,  373,
  306,   58,  102,  162,   59,  399,   57,  158,  158,  158,
  237,  158,   94,  158,   95,  245,  410,  248,  185,  202,
  163,  415,   68,  152,   74,  158,  158,  158,  158,  181,
  119,  118,  143,   48,   18,   49,   18,  154,   51,  154,
  324,  154,   35,  168,  143,  153,   58,  153,  142,  308,
   58,  134,  145,  248,  153,  154,  154,  154,  154,  172,
  113,  342,  143,  346,  152,  105,  152,  106,  152,  134,
  276,  175,   94,   18,   95,   18,  350,  173,   11,   12,
  353,  174,  152,  152,  152,  152,   11,   12,  153,  175,
  153,  158,  153,  176,  174,   11,   12,  207,  178,  347,
  206,  180,   37,   38,   37,   38,  153,  153,  153,  153,
   91,   92,   93,  177,  380,  188,  135,  383,   37,   38,
  179,  154,  102,  388,   37,   38,  361,   37,   38,   37,
   38,  396,   18,  366,  135,  400,   37,   38,  371,   37,
   38,  147,   38,  147,   38,   68,  411,  193,  152,   37,
   38,  416,  126,  201,   37,   38,   91,   92,   93,  144,
  144,  144,  393,  204,   54,   11,   12,    6,  249,  250,
  205,    7,  153,  251,  252,  406,  216,   18,  134,    9,
   10,  218,  245,  217,   84,  248,  245,  223,  248,  256,
  257,  226,  113,  258,  259,  113,  175,  113,  113,  113,
  113,  113,  113,  227,   71,  245,  248,  113,  113,  113,
  113,  113,  113,  113,  113,  230,  245,  248,  233,  174,
  240,  245,  248,  158,  290,  291,  158,  158,  158,  158,
  158,  158,  158,  158,  242,  158,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,  243,  102,  158,  159,
  340,  341,   16,  154,  106,   20,  154,  154,  154,  154,
  154,  154,  154,  154,  261,  154,  154,  154,  154,  154,
  154,  154,  154,  154,  154,  154,  264,  126,  268,  192,
  152,  270,   73,  152,  152,  152,  152,  152,  152,  152,
  152,  277,  152,  152,  152,  152,  152,  152,  152,  152,
  152,  152,  152,  134,  153,  139,  139,  153,  153,  153,
  153,  153,  153,  153,  153,  273,  153,  153,  153,  153,
  153,  153,  153,  153,  153,  153,  153,  275,  175,   71,
  279,  175,    8,  175,  175,  175,  175,  175,  175,  300,
  236,   73,  284,  175,  175,  175,  175,  175,  175,  175,
  175,  174,  194,  286,  174,  289,  174,  174,  174,  174,
  174,  174,   78,  156,  157,  293,  174,  174,  174,  174,
  174,  174,  174,  174,  160,  161,  295,   16,  302,  102,
  303,  305,  102,  307,  102,  102,  102,  102,  102,  102,
   76,  311,  310,  309,  102,  102,  102,  102,  102,  102,
  102,  102,  312,  313,  314,  317,   78,   73,  318,  126,
  319,  320,  126,   20,  126,  126,  126,  126,  126,  126,
  321,   78,  322,  323,  126,  126,  126,  126,  126,  126,
  126,  126,  325,  326,   76,  134,  329,  330,  134,  208,
  134,  134,  134,  134,  134,  134,  331,  332,  333,   76,
  134,  134,  134,  134,  134,  134,  134,  134,   57,  335,
  334,   71,  336,  339,   71,  345,  348,  349,   71,   71,
   71,  130,   20,  351,  352,  208,   71,   71,  354,  355,
   71,   71,   71,   71,  357,  356,   54,   78,  358,    6,
   16,  359,   16,    7,  360,  130,   55,  363,  362,  364,
  365,    9,   10,   73,   56,  367,  368,   13,  369,   16,
  372,  374,   16,  386,  370,   76,   16,   16,   16,  376,
   54,   16,   73,    6,   16,   16,  377,    7,   16,   16,
   16,   16,  375,  379,  384,    9,   10,   54,   20,   73,
  129,   13,   73,  382,    7,  385,   73,   73,   73,  387,
  390,  389,  131,  132,   73,   73,  392,  397,   73,   73,
   73,   73,  391,  394,  395,  208,  398,  208,  402,   73,
  403,  401,  404,   54,  110,  110,    6,  405,  253,  254,
    7,  407,  130,  409,  408,  414,  412,  208,    9,   10,
  413,  419,   54,  417,   13,    6,  418,  106,  208,    7,
    4,   48,   55,   52,  280,  197,  198,    9,   10,   54,
   56,  281,    6,   13,  253,  254,    7,  208,  199,   78,
  327,  239,   78,  189,    9,   10,   78,   78,   78,   72,
   13,  110,  100,  337,   78,   78,  244,    0,   78,   78,
   78,   78,    0,    0,    0,    0,    0,   76,    0,    0,
   76,    0,    0,   80,   76,   76,   76,    0,    0,    0,
    0,    0,   76,   76,    0,    0,   76,   76,   76,   76,
   20,    0,    0,   20,    0,    0,   82,   20,   20,   20,
    0,    0,    0,    0,    0,   20,   20,    0,    0,   20,
   20,   20,   20,  219,    0,  222,    0,    0,  110,   54,
  225,   54,    6,  213,    6,  145,    7,  287,    7,    0,
    0,    0,    0,    0,    9,   10,    9,   10,    0,    0,
   13,   54,   13,    0,    6,  304,    0,    0,    7,   94,
    0,    0,   54,    0,    0,    6,    9,   10,  315,    7,
    0,  260,   13,    0,  262,    0,  263,    9,   10,  265,
    0,   54,   98,   13,    6,    0,    0,    0,    7,    0,
    0,    5,    0,    0,    6,    0,    9,   10,    7,    8,
    0,   95,   13,    0,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,  296,    5,    0,  110,    6,    0,
  184,    0,    7,    8,    0,    0,    0,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,    5,  184,
    0,    6,    0,    0,    0,    7,    8,    0,    0,    0,
    0,    0,    0,    9,   10,    0,  140,   11,   12,   13,
   14,    0,    0,    0,    0,    0,  247,    5,    0,    0,
    6,    0,    0,  110,    7,    8,  282,    0,  211,  212,
    0,  215,    9,   10,    0,    0,   11,   12,   13,   14,
    0,   94,    0,    0,   94,    0,    0,    0,   94,    0,
   94,   94,    0,    0,    0,    0,   94,   94,   94,   94,
    0,    0,   94,    0,   98,    0,    0,   98,    0,    0,
    0,   98,    0,   98,   98,    0,    0,    0,    0,   98,
   98,   98,   98,   95,    0,   98,   95,    0,    0,  255,
   95,    0,   95,   95,    0,    0,    0,    0,   95,   95,
   95,   95,   54,    0,   95,    6,    0,    0,    0,    7,
    0,  130,   55,    0,    0,    0,    0,    9,   10,    0,
   56,   54,    0,   13,    6,  283,    0,  285,    7,  288,
    0,   55,  292,    0,  294,    0,    9,   10,  140,   56,
    0,  140,   13,  136,  136,  140,    0,    0,   54,    0,
    0,    6,    0,  140,  140,    7,    0,    0,   54,  140,
    0,    6,    0,    9,   10,    7,  316,    0,    5,   13,
    0,  129,    0,    9,   10,    7,    8,  130,    0,   13,
  255,    0,    0,  131,  132,    0,    0,   11,   12,  133,
   14,    5,    0,    0,    6,    0,    0,    0,    7,    8,
  130,    5,    0,    0,    6,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,    0,
   11,   12,   13,   14,    5,    0,    0,  129,    0,    0,
    0,    7,    8,    0,   33,    0,    0,   33,    0,  131,
  132,   33,   33,   11,   12,  133,   14,    0,    0,   33,
   33,    0,    0,   33,   33,   33,   33,   54,    0,    0,
    6,    0,    0,    0,    7,    0,    0,   55,    0,    0,
    0,    0,    9,   10,   54,   56,    0,    6,   13,    0,
    0,    7,    0,  130,    0,    0,    0,    0,    0,    9,
   10,    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   41,   78,   78,  208,   40,   10,   44,  175,
   78,   40,   15,   53,   59,   41,   59,  125,   41,  137,
   59,   59,   41,  125,   41,  123,   57,  123,   13,   79,
   33,   34,   40,   41,    4,  123,   59,   13,   59,   41,
   59,   41,   41,   52,  119,  125,   41,  125,  229,   13,
   41,   13,  123,   78,   57,   41,  258,   13,  133,   43,
   59,   45,  137,   33,   59,   40,  268,   58,   86,  137,
   43,  123,   45,   59,   43,   78,   45,   40,   81,   58,
   45,  257,   45,  123,   41,  266,   59,  268,  258,   41,
  121,   60,   61,   62,   59,   40,   45,   40,  264,  108,
   40,   58,   45,   41,   41,   45,   58,   45,  133,   40,
  268,   23,  137,   41,   45,   41,   41,   45,  121,   45,
   59,   45,   41,   41,   36,    0,   41,   45,   41,  132,
   41,  274,   45,   44,  137,   41,   44,   60,   61,   62,
   60,   61,   62,   58,  325,   59,  186,   58,  133,   44,
  190,  273,   58,  193,  320,  205,    0,  133,  266,  257,
  261,  257,  260,  194,  266,  239,  264,  265,   59,  133,
  261,  133,  375,  261,  272,  273,  379,  133,  276,  277,
  278,  279,  262,  263,  262,  263,    0,   42,  206,  370,
  261,  194,   47,   59,  274,  398,  274,   41,   42,   43,
   41,   45,   43,   47,   45,  208,  409,  210,  239,  327,
   59,  414,  257,    0,  257,   59,   60,   61,   62,  257,
   58,   59,  261,  257,  227,  259,  229,   41,  257,   43,
  304,   45,  268,   59,  309,  261,  239,    0,  261,  279,
  243,  309,  261,  246,  261,   59,   60,   61,   62,   58,
  125,  325,  327,  327,   41,  257,   43,  259,   45,  327,
   41,    0,   43,  266,   45,  268,  340,  257,  276,  277,
  344,   41,   59,   60,   61,   62,  276,  277,   41,   44,
   43,  125,   45,  257,    0,  276,  277,   41,   41,  329,
   44,   41,  257,  258,  257,  258,   59,   60,   61,   62,
  269,  270,  271,   58,  378,  258,  309,  381,  257,  258,
  261,  125,    0,  387,  257,  258,  356,  257,  258,  257,
  258,  395,  325,  363,  327,  399,  257,  258,  368,  257,
  258,  257,  258,  257,  258,  257,  410,   40,  125,  257,
  258,  415,    0,  125,  257,  258,  269,  270,  271,  269,
  270,  271,  392,  125,  257,  276,  277,  260,  262,  263,
   58,  264,  125,  262,  263,  405,   59,  370,    0,  272,
  273,   58,  375,   59,   35,  378,  379,   58,  381,  262,
  263,   58,  257,  262,  263,  260,  125,  262,  263,  264,
  265,  266,  267,  123,    0,  398,  399,  272,  273,  274,
  275,  276,  277,  278,  279,   59,  409,  410,   41,  125,
  274,  414,  415,  257,  262,  263,  260,  261,  262,  263,
  264,  265,  266,  267,  125,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  273,  125,   99,  100,
  262,  263,    0,  257,   58,   59,  260,  261,  262,  263,
  264,  265,  266,  267,   58,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,   44,  125,  123,  130,
  257,   59,    0,  260,  261,  262,  263,  264,  265,  266,
  267,   41,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  125,  257,  262,  263,  260,  261,  262,
  263,  264,  265,  266,  267,   59,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   59,  257,  125,
   40,  260,  265,  262,  263,  264,  265,  266,  267,  125,
  191,   59,   59,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  123,   59,  260,   59,  262,  263,  264,  265,
  266,  267,    0,   94,   95,   59,  272,  273,  274,  275,
  276,  277,  278,  279,  101,  102,   59,  125,  125,  257,
   59,   59,  260,  125,  262,  263,  264,  265,  266,  267,
    0,  263,  274,  123,  272,  273,  274,  275,  276,  277,
  278,  279,  263,   59,  263,   59,   44,  125,  263,  257,
  263,   44,  260,    0,  262,  263,  264,  265,  266,  267,
   59,   59,  125,   59,  272,  273,  274,  275,  276,  277,
  278,  279,  123,   41,   44,  257,   40,   59,  260,  123,
  262,  263,  264,  265,  266,  267,   59,   59,   59,   59,
  272,  273,  274,  275,  276,  277,  278,  279,  123,   59,
  263,  257,   59,   59,  260,   59,   59,  272,  264,  265,
  266,  266,   59,   59,  125,  123,  272,  273,  125,   41,
  276,  277,  278,  279,  263,   40,  257,  125,  260,  260,
    2,   59,    4,  264,   59,  266,  267,   40,   59,   41,
  260,  272,  273,   15,  275,  261,   40,  278,   41,  257,
  261,   41,  260,   59,  123,  125,  264,  265,  266,  125,
  257,   33,   34,  260,  272,  273,  261,  264,  276,  277,
  278,  279,  123,  123,  263,  272,  273,  257,  125,  257,
  260,  278,  260,  125,  264,  125,  264,  265,  266,  262,
   59,  263,  272,  273,  272,  273,   40,  262,  276,  277,
  278,  279,  260,   41,  261,  123,  123,  123,  263,   81,
   59,  125,  260,  257,   51,   52,  260,   40,  262,  263,
  264,   41,  266,  123,  261,  123,  125,  123,  272,  273,
  262,   59,  257,  125,  278,  260,  263,   58,  123,  264,
    0,  123,  267,  123,  241,  133,  133,  272,  273,  257,
  275,  243,  260,  278,  262,  263,  264,  123,  133,  257,
  309,  194,  260,  127,  272,  273,  264,  265,  266,  125,
  278,  108,   43,  320,  272,  273,  206,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,   -1,  125,  264,  265,  266,   -1,   -1,   -1,
   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,
  257,   -1,   -1,  260,   -1,   -1,  125,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,   -1,  276,
  277,  278,  279,  170,   -1,  172,   -1,   -1,  175,  257,
  177,  257,  260,  261,  260,  125,  264,  263,  264,   -1,
   -1,   -1,   -1,   -1,  272,  273,  272,  273,   -1,   -1,
  278,  257,  278,   -1,  260,  261,   -1,   -1,  264,  125,
   -1,   -1,  257,   -1,   -1,  260,  272,  273,  263,  264,
   -1,  218,  278,   -1,  221,   -1,  223,  272,  273,  226,
   -1,  257,  125,  278,  260,   -1,   -1,   -1,  264,   -1,
   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
   -1,  125,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  261,  257,   -1,  264,  260,   -1,
  125,   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,   -1,   -1,  276,  277,  278,  279,  257,  125,
   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,  125,  276,  277,  278,
  279,   -1,   -1,   -1,   -1,   -1,  125,  257,   -1,   -1,
  260,   -1,   -1,  320,  264,  265,  125,   -1,  152,  153,
   -1,  155,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  266,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
   -1,   -1,  278,   -1,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,  213,
  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,   -1,
  275,  257,   -1,  278,  260,  249,   -1,  251,  264,  253,
   -1,  267,  256,   -1,  258,   -1,  272,  273,  257,  275,
   -1,  260,  278,  262,  263,  264,   -1,   -1,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,  257,  278,
   -1,  260,   -1,  272,  273,  264,  290,   -1,  257,  278,
   -1,  260,   -1,  272,  273,  264,  265,  266,   -1,  278,
  304,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
  266,  257,   -1,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,  265,  276,  277,  278,  279,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,  257,  275,   -1,  260,  278,   -1,
   -1,  264,   -1,  266,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,   -1,   -1,  278,
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
"funcion_con_return : encabezado_funcion '{' sentencia_return '}' $$1 encabezado_funcion '{' sentencias_funcion sentencia_return '}'",
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

//#line 351 ".\gramatica.y"

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
//#line 855 "Parser.java"
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
case 36:
//#line 82 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 39:
//#line 98 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 42:
//#line 107 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 43:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 48:
//#line 119 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 49:
//#line 120 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 50:
//#line 121 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 51:
//#line 122 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 52:
//#line 123 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 53:
//#line 124 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 54:
//#line 125 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 55:
//#line 126 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 57:
//#line 131 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 58:
//#line 132 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 59:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 60:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 63:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 68:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 69:
//#line 151 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 70:
//#line 155 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 71:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 72:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 76:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 77:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 78:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 92:
//#line 192 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 93:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 94:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 95:
//#line 195 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 96:
//#line 196 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 97:
//#line 200 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 98:
//#line 201 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 101:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 102:
//#line 211 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 103:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 104:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 105:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 111:
//#line 232 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 112:
//#line 233 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 113:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 114:
//#line 238 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 115:
//#line 239 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 116:
//#line 240 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 117:
//#line 241 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 120:
//#line 250 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 121:
//#line 251 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 122:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 123:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 124:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 125:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 126:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 127:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 128:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 129:
//#line 259 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 130:
//#line 260 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 131:
//#line 261 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 132:
//#line 262 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 133:
//#line 263 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 134:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 135:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 138:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 139:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 144:
//#line 282 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 145:
//#line 283 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 167:
//#line 329 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 168:
//#line 330 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 169:
//#line 331 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 170:
//#line 332 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 171:
//#line 333 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 172:
//#line 334 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 173:
//#line 335 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 174:
//#line 336 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 175:
//#line 337 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 177:
//#line 342 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1348 "Parser.java"
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
