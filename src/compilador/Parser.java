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
    7,    8,   14,   15,   15,   15,   15,   15,   15,   16,
   21,   21,   17,   17,   23,   23,   18,   18,   25,   27,
   27,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   19,   13,   29,   29,   29,   32,   32,   31,   31,
    9,    9,    9,   33,   33,   34,   34,   34,   34,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,   40,
   40,   40,   41,   41,   41,   41,   41,   42,   42,   39,
   39,   43,   43,   43,   43,   43,   26,   44,   44,   28,
   28,   35,   35,   35,   38,   38,   45,   38,   22,   22,
   36,   36,   36,   36,   36,   36,   36,   36,   36,   36,
   36,   36,   36,   36,   36,   36,   46,   46,   46,   46,
   24,   24,   20,   20,   48,   20,   47,   47,   47,   47,
   47,   47,   30,   30,   30,   49,   49,   49,   50,   50,
   50,   52,   53,   53,   54,   54,   37,   37,   37,   37,
   37,   37,   37,   37,   37,   51,   51,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    1,    2,    1,    2,    2,    3,    9,
    1,    2,    9,   10,    1,    4,    1,    3,    7,    1,
    4,    6,    7,    5,    5,    5,    5,    6,    6,    6,
    6,    5,    2,    1,    3,    5,    1,    3,    2,    1,
    3,    2,    2,    1,    3,    3,    2,    2,    1,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    1,    1,    2,    4,    1,    3,    3,    2,    1,    1,
    3,    7,    6,    6,    6,    6,    1,    1,    3,    1,
    2,    4,    3,    3,    9,    8,    0,   17,    1,    2,
    8,   10,    7,    7,    7,    7,    7,    7,    9,    9,
    9,    9,    9,    9,    9,    8,    1,    3,    2,    2,
    1,    2,    3,    2,    0,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    1,    3,    1,    1,    5,    5,    4,    4,
    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  169,  168,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   70,   72,   74,   76,
   78,   90,    0,    0,    0,    0,    0,  166,    0,    0,
    0,    0,    0,    0,  148,  150,  151,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,   98,   81,
   82,    0,   71,   73,   75,   77,   79,    0,   63,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  103,    0,   19,    0,  167,    0,    0,    0,
  137,  138,  139,    0,    0,  140,  141,  142,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   83,    0,   88,
    0,  100,    0,   68,    0,   61,    0,   15,    0,    0,
    0,    0,    0,    0,    0,   23,   24,   26,    0,   37,
    0,   91,    1,  102,  155,  156,    0,  153,    0,    0,
    0,    0,    0,    0,    0,    0,  146,  147,  162,  160,
  163,    0,  161,    0,  159,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,   97,   87,    0,   99,
  101,    0,    0,   66,   65,    0,    0,    0,    0,   40,
    0,   25,   27,    0,   53,   21,   22,   28,    0,    0,
  152,    0,    0,    0,    0,    0,    0,    0,    0,  158,
  157,    0,   47,    0,    0,   46,    0,    0,   45,    0,
    0,    0,    0,   84,    0,    0,    0,    0,    0,    0,
    0,    0,   29,   38,  154,  131,    0,  129,  132,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   42,    0,   51,   50,    0,   49,    0,    0,    0,    0,
   94,   96,    0,   95,    0,    0,    0,    0,    0,  128,
    0,  115,    0,  113,    0,    0,  118,    0,    0,    0,
  116,    0,  114,   43,    0,   56,    0,    0,    0,    0,
   92,    0,    0,    0,   41,    0,    0,    0,  126,    0,
    0,    0,  111,    0,    0,    0,  107,    0,  106,    0,
   35,    0,    0,   52,    0,  121,  119,  123,  124,    0,
  122,  120,   58,    0,  105,    0,    0,    0,   31,    0,
    0,   39,  112,    0,    0,    0,    0,    0,    0,   32,
    0,   36,    0,    0,   33,   30,    0,   34,    0,    0,
    0,    0,  108,
};
final static short yydgoto[] = {                          3,
    4,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  134,  135,  136,  137,  138,  139,  311,   41,
  330,  258,  312,  204,  140,   26,  191,  121,  112,   42,
  113,  286,   70,   71,   27,   28,   29,   30,   31,   59,
   60,   61,   32,   62,  324,  208,   99,   43,   44,   45,
   46,   47,  147,  148,
};
final static short yysindex[] = {                       -89,
    0,  922,    0,  -76,  -39,   17,  -11,  -36,    5,  592,
    0,    0,   71,  -47,  702,    0,    0,    0,    0,    0,
    0,    0,  -46,   60,    9,   66,    0,    0,    0,    0,
    0,    0,  712,  736,   42,  -97,  129,    0,  -84,  -21,
  144,    7,   68,   12,    0,    0,    0,  146,  153,   22,
  -23,   -6,   36,  -86,  161,  154, -164,    0,    0,    0,
    0,  -22,    0,    0,    0,    0,    0, -219,    0,  184,
  223,    0,    0,  236,    0,  233,    0,  948,   29,    0,
  746,    0,    0,   54,    0,   27,    0,   21,  -19,   44,
    0,    0,    0,   57,   57,    0,    0,    0,   57,   57,
   57,   57,  248,  257,   -8,    2,  271,    0,  -18,  284,
  115,  333,  362,  350,  369,  158,  391,    0,  -45,    0,
  843,    0,   30,    0,  186,    0,  188,    0,   33,  406,
  145,  190,  912,  323,  324,    0,    0,    0,  187,    0,
  397,    0,    0,    0,    0,    0,   76,    0,  685,  685,
  636,  685,   12,   12,   93,   93,    0,    0,    0,    0,
    0,  398,    0,  399,    0,  401, -188,   24, -188,    0,
  402, -151, -188,  414,  363, -115,    0,    0,  428,    0,
    0,   38,  415,    0,    0,  -21,   36,  448, -164,    0,
  215,    0,    0,  187,    0,    0,    0,    0,  217,   27,
    0,  972,    0,  494,   70,   78,  617,   83,   85,    0,
    0, -188,    0,  433, -188,    0, -188,  449,    0, -188,
  922,  371,  922,    0,  439,   15,  440,    1,  451,   57,
  826,  460,    0,    0,    0,    0,  882,    0,    0,  685,
  442,  685,  443,  647,  445,   88,  685,  446,  685,  447,
    0, -188,    0,    0, -151,    0,  922,  383,  922,  393,
    0,    0,  461,    0,  666,  -96,  173,  394,   57,    0,
  268,    0,  278,    0,  475,  279,    0,  683,  485,  292,
    0,  293,    0,    0,  522,    0,  922,  508,  453,  509,
    0,  582,  456,  523,    0,  540,  525,  526,    0,  528,
  535,  334,    0,  537,  547, -151,    0,  548,    0,  972,
    0,  134,  912,    0,  549,    0,    0,    0,    0,  551,
    0,    0,    0,  339,    0,  872, -108,  554,    0,  489,
 -119,    0,    0,  575,  491,  972,  355,  187,  562,    0,
   57,    0,  950,  569,    0,    0,  588,    0,  370,  507,
  922,  515,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  202,  130,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,    0,    0,  130,
    0,    0,    0,  116,    0,    0,    0,    0,    0,    0,
    0,    0,  130,  584,  769,  788,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  452,    0,  384,
  484,    0,    0,  553,    0,  407,    0,    0,    0,    0,
  643,    0,    0,   46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -34,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  130,    0,  521,    0,    0,    0,  130,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  164,  204,  -31,   -4,    0,    0,    0,    0,
    0,  250,    0,  273,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  807,    0,
    0,  130,    0,    0,    0,  130,  130,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  862,    0,    0,    0,    0,    0,    0,    0,
    0,  524,    0,    0,    0,    0,    0,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  141,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  529,    0,    0,    0,    0, -104,    0,    0,    0,
    0,    0,  297,    0,    0,    0,    0,    0,  130,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  326,    0,
    0,    0,    0,    0,   69,    0,  -99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  360,
    0,    0,    0,    0,    0,  141,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -2,  -15,    0,   -9,    0,    0,    0,    0,  623,
   62,    0,    0,    0,    0,  513,  519,  527,  364,  -37,
    0, -165,  327, -166,  454,  -48,    0,  466,  -14,   56,
 -140,  352,  538,    0,   31,   40,   47,   51,   67,  -16,
    0,    0,  -68,    0,    0,  926,  625,    0,  319,  314,
  -77,    0,    0,  469,
};
final static int YYTABLESIZE=1250;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
   58,   34,   89,   52,   36,  308,  134,  223,  146,  133,
  142,   69,   75,  178,  336,  117,  109,  110,   73,   88,
  109,  151,  166,   39,  134,  110,  223,  133,   50,  141,
   81,  218,  162,    2,  114,  237,  136,  115,  124,  167,
  122,  265,  164,   63,   53,  104,   33,   58,  125,   94,
  161,   95,   64,  101,  136,  263,   40,  260,  102,   65,
  163,   39,  107,   66,  214,   73,   97,   98,   96,  182,
  179,   39,  186,  262,   39,  133,  116,   39,  225,   67,
   39,  215,   39,  141,   76,  183,   39,   11,   12,   54,
   84,  149,   54,  289,  168,    6,   94,   85,   95,    7,
   83,   39,   55,   55,  181,  108,   54,    9,   10,   57,
   56,   58,  144,   13,  285,  145,  201,   73,   77,  200,
   55,   58,  146,   79,   11,   12,   57,   97,   98,   96,
  142,   78,  149,  149,  149,   94,  149,   95,  149,  203,
  203,  203,  203,  326,  226,  222,  188,  331,  228,  229,
  149,  149,  149,  149,  155,  156,  145,  188,  145,   74,
  145,  109,   63,  143,  293,  285,  110,    1,   86,  343,
  104,   64,  122,   87,  145,  145,  145,  145,   65,   58,
    5,   35,   66,    6,   90,  352,  103,    7,    8,  135,
  135,  135,  236,  104,  239,    9,   10,  203,   67,   11,
   12,   13,   14,  144,  143,  257,  143,  257,  143,   68,
   74,  177,  120,  294,  181,   94,  149,   95,  119,  118,
   51,   58,  143,  143,  143,  143,  134,  239,   35,  133,
  203,  296,  203,  108,  203,   37,   38,  203,  108,  203,
  145,  150,  126,  257,  144,   48,  144,   49,  144,  165,
  108,  123,   11,   12,  287,  203,  136,   11,   12,   97,
   20,  150,  144,  144,  144,  144,  127,  189,  203,   11,
   12,   73,  164,   37,   38,   91,   92,   93,  105,   36,
  106,  149,  203,  145,   38,  267,   37,   38,  143,   37,
   38,  128,   37,   38,   37,   38,   93,  257,   37,   38,
  236,   10,  104,  347,  152,  104,  159,  104,  104,  104,
  104,  104,  104,   37,   38,  160,  239,  104,  104,  104,
  104,  104,  104,  104,  104,  117,  236,   54,  144,  165,
    6,  240,  241,  239,    7,  257,   91,   92,   93,  242,
  243,  169,    9,   10,  247,  248,  249,  250,  149,  278,
  279,  149,  149,  149,  149,  149,  149,  149,  149,  125,
  149,  149,  149,  149,  149,  149,  149,  149,  149,  149,
  149,  170,  145,  171,  165,  145,  145,  145,  145,  145,
  145,  145,  145,   62,  145,  145,  145,  145,  145,  145,
  145,  145,  145,  145,  145,  327,  328,  164,  135,  135,
  135,   54,  130,  130,    6,  172,   16,  173,    7,  174,
  188,   55,  153,  154,  157,  158,    9,   10,  175,   56,
  143,   93,   13,  143,  143,  143,  143,  143,  143,  143,
  143,  176,  143,  143,  143,  143,  143,  143,  143,  143,
  143,  143,  143,  184,   68,  187,   54,  196,  197,  129,
  117,   69,  188,    7,  199,  227,  210,  211,  212,  217,
  144,  130,  131,  144,  144,  144,  144,  144,  144,  144,
  144,  220,  144,  144,  144,  144,  144,  144,  144,  144,
  144,  144,  144,   64,  125,  221,  224,  230,  232,  131,
  252,  266,  255,  259,  190,   69,  195,  261,  264,  269,
  272,  274,  198,  277,  281,  283,  165,  288,   62,  165,
   69,  165,  165,  165,  165,  165,  165,  290,  295,  291,
   67,  165,  165,  165,  165,  165,  165,  165,  165,  164,
  297,   16,  164,  299,  164,  164,  164,  164,  164,  164,
  298,  300,   64,  303,  164,  164,  164,  164,  164,  164,
  164,  164,   20,   93,  304,  305,   93,  233,   93,   93,
   93,   93,   93,   93,   67,  306,  307,  309,   93,   93,
   93,   93,   93,   93,   93,   93,   69,  308,  313,   67,
  315,  314,  117,  316,  317,  117,  318,  117,  117,  117,
  117,  117,  117,  319,  268,  321,  320,  117,  117,  117,
  117,  117,  117,  117,  117,  322,  325,  332,   64,  333,
  334,   20,  338,  339,  341,  342,  125,  344,  238,  125,
  346,  125,  125,  125,  125,  125,  125,  348,  349,  351,
  350,  125,  125,  125,  125,  125,  125,  125,  125,  353,
   62,   97,    4,   62,  192,   67,   44,   62,   62,   62,
  193,   48,  234,  337,  231,   62,   62,  323,  194,   62,
   62,   62,   62,   16,  185,    0,   16,  100,  235,    0,
   16,   16,   16,  111,  111,    0,  329,   20,   16,   16,
    0,    0,   16,   16,   16,   16,    0,    0,    0,  335,
    0,    0,    0,    0,  340,    0,    0,    0,    0,    0,
    0,  345,    0,    0,  310,    0,  335,    0,   69,    0,
    0,   69,    0,    0,   57,   69,   69,   69,    0,    0,
    0,    0,    0,   69,   69,    0,    0,   69,   69,   69,
   69,  111,    0,    0,    0,    0,    0,    0,    0,  202,
   64,    0,    0,   64,    0,    0,    0,   64,   64,   64,
   54,    0,    0,    6,    0,   64,   64,    7,  202,   64,
   64,   64,   64,    0,    0,    9,   10,    0,    0,  202,
    0,   13,    0,    0,    0,    0,    0,   67,    0,    0,
   67,    0,    0,    0,   67,   67,   67,    0,  202,  213,
    0,  216,   67,   67,  111,  219,   67,   67,   67,   67,
    0,    0,    0,    0,    0,  202,    0,  202,    0,   20,
    0,    0,   20,    0,    0,    0,   20,   20,   20,    0,
    0,    0,    0,    0,   20,   20,   72,    0,   20,   20,
   20,   20,    0,    0,  251,    0,   80,  253,   54,  254,
    0,    6,  256,  244,  245,    7,    0,  188,   54,    0,
    0,    6,    0,    9,   10,    7,    0,    0,   55,   13,
   82,    0,    0,    9,   10,    0,   56,    0,    0,   13,
  143,    0,    0,   54,  284,    0,    6,  111,  244,  245,
    7,    0,    0,    0,    0,    0,    0,    0,    9,   10,
    0,    0,   54,   85,   13,    6,  207,    0,    0,    7,
    0,    0,    0,   54,    0,    0,    6,    9,   10,  275,
    7,    0,   89,   13,    0,    0,    0,    0,    9,   10,
    0,    0,   54,    0,   13,    6,  292,    0,  111,    7,
    0,   86,    0,    0,    0,    0,    0,    9,   10,   54,
    0,   54,    6,   13,    6,  301,    7,    0,    7,    0,
  180,    0,    0,    0,    9,   10,    9,   10,    5,    0,
   13,    6,   13,    0,    0,    7,    8,  180,    5,    0,
    0,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,    0,    0,    9,   10,    0,  131,   11,   12,   13,
   14,    0,    5,    0,    0,    6,  270,    0,    0,    7,
    8,    0,    5,    0,    0,    6,  270,    9,   10,    7,
    8,   11,   12,   13,   14,    0,    0,    9,   10,    0,
    0,   11,   12,   13,   14,   85,    0,    0,   85,    0,
    0,    0,   85,    0,   85,   85,    0,    0,    0,    0,
   85,   85,   85,   85,   89,    0,   85,   89,    0,    0,
    0,   89,    0,   89,   89,    0,    0,    0,    0,   89,
   89,   89,   89,   86,    0,   89,   86,    0,    0,    0,
   86,    0,   86,   86,  205,  206,    0,  209,   86,   86,
   86,   86,   54,    0,   86,    6,    0,    0,    0,    7,
    0,  188,   55,    0,    0,    0,    0,    9,   10,   54,
   56,    0,    6,   13,    0,    0,    7,    0,    0,   55,
    0,    0,    0,    0,    9,   10,    0,   56,  131,    0,
   13,  131,    0,  127,  127,  131,    0,    0,   54,    0,
    0,    6,  246,  131,  131,    7,    0,  188,   54,  131,
    0,    6,    0,    9,   10,    7,    0,    0,    0,   13,
    0,    0,    0,    9,   10,    0,    0,    0,    0,   13,
    0,    0,    0,    0,    0,  271,    0,  273,    5,  276,
    0,    6,  280,    0,  282,    7,    8,  188,    5,    0,
    0,    6,    0,    9,   10,    7,    8,   11,   12,   13,
   14,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,    0,  302,    5,    0,   54,  129,    0,    6,
    0,    7,    8,    7,    0,  188,    0,  246,    0,  130,
  131,    9,   10,   11,   12,  132,   14,   13,   54,    0,
    0,    6,    0,    0,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    9,   10,    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   10,    4,   40,   40,   44,  125,   41,  123,   86,   41,
   79,   59,   59,   59,  123,   53,   40,   41,   34,   41,
  125,   41,   41,   45,   59,  125,  123,   59,   40,   78,
   33,  172,   41,  123,   41,  202,   41,   52,  258,   58,
   57,   41,   41,   13,   40,    0,  123,   57,  268,   43,
   59,   45,   13,   42,   59,   41,   40,  223,   47,   13,
   59,   45,   41,   13,   41,   81,   60,   61,   62,   40,
  119,   45,   40,   59,   45,   78,   41,   45,   41,   13,
   45,   58,   45,  132,   23,  123,   45,  276,  277,   41,
   35,    0,  257,  259,  109,  260,   43,   36,   45,  264,
   59,   45,  267,   41,  121,  257,   58,  272,  273,   41,
  275,  121,   59,  278,  255,    0,   41,  133,   59,   44,
   58,  131,  200,   58,  276,  277,   58,   60,   61,   62,
  199,  123,   41,   42,   43,   43,   45,   45,   47,  149,
  150,  151,  152,  310,  182,  261,  266,  313,  186,  187,
   59,   60,   61,   62,   99,  100,   41,  266,   43,  257,
   45,  266,  132,    0,  261,  306,  266,  257,   40,  336,
  125,  132,  189,  258,   59,   60,   61,   62,  132,  189,
  257,  268,  132,  260,   41,  351,   41,  264,  265,   60,
   61,   62,  202,   41,  204,  272,  273,  207,  132,  276,
  277,  278,  279,    0,   41,  221,   43,  223,   45,  257,
  257,  257,   59,   41,  231,   43,  125,   45,   58,   59,
  257,  231,   59,   60,   61,   62,  261,  237,  268,  261,
  240,  269,  242,  257,  244,  257,  258,  247,  257,  249,
  125,  261,   59,  259,   41,  257,   43,  259,   45,    0,
  257,  274,  276,  277,  257,  265,  261,  276,  277,   58,
   59,  261,   59,   60,   61,   62,   44,  123,  278,  276,
  277,  287,    0,  257,  258,  269,  270,  271,  257,   44,
  259,  261,  292,  257,  258,  230,  257,  258,  125,  257,
  258,   59,  257,  258,  257,  258,    0,  313,  257,  258,
  310,  273,  257,  341,  261,  260,   59,  262,  263,  264,
  265,  266,  267,  257,  258,   59,  326,  272,  273,  274,
  275,  276,  277,  278,  279,    0,  336,  257,  125,   59,
  260,  262,  263,  343,  264,  351,  269,  270,  271,  262,
  263,   58,  272,  273,  262,  263,  262,  263,  257,  262,
  263,  260,  261,  262,  263,  264,  265,  266,  267,    0,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,   41,  125,  260,  261,  262,  263,  264,
  265,  266,  267,    0,  269,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  262,  263,  125,  269,  270,
  271,  257,  262,  263,  260,   44,    0,   58,  264,   41,
  266,  267,   94,   95,  101,  102,  272,  273,  261,  275,
  257,  125,  278,  260,  261,  262,  263,  264,  265,  266,
  267,   41,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  258,  257,   40,  257,  125,  125,  260,
  125,    0,  266,  264,   58,   41,   59,   59,   58,   58,
  257,  272,  273,  260,  261,  262,  263,  264,  265,  266,
  267,   58,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,    0,  125,  123,   59,   40,  274,  273,
   58,   41,   44,  123,  131,   44,  133,   59,   59,   40,
   59,   59,  139,   59,   59,   59,  257,  125,  125,  260,
   59,  262,  263,  264,  265,  266,  267,  125,  125,   59,
    0,  272,  273,  274,  275,  276,  277,  278,  279,  257,
  263,  125,  260,   59,  262,  263,  264,  265,  266,  267,
  263,  263,   59,   59,  272,  273,  274,  275,  276,  277,
  278,  279,    0,  257,  263,  263,  260,  194,  262,  263,
  264,  265,  266,  267,   44,   44,   59,   59,  272,  273,
  274,  275,  276,  277,  278,  279,  125,  125,  123,   59,
   41,   59,  257,   59,   59,  260,   59,  262,  263,  264,
  265,  266,  267,   59,  231,   59,  263,  272,  273,  274,
  275,  276,  277,  278,  279,   59,   59,   59,  125,   59,
  272,   59,   59,  125,   40,  125,  257,  263,  125,  260,
   59,  262,  263,  264,  265,  266,  267,   59,   41,  123,
  261,  272,  273,  274,  275,  276,  277,  278,  279,  125,
  257,   58,    0,  260,  132,  125,  123,  264,  265,  266,
  132,  123,  199,  327,  189,  272,  273,  306,  132,  276,
  277,  278,  279,  257,  127,   -1,  260,   43,  200,   -1,
  264,  265,  266,   51,   52,   -1,  313,  125,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,  326,
   -1,   -1,   -1,   -1,  331,   -1,   -1,   -1,   -1,   -1,
   -1,  338,   -1,   -1,  123,   -1,  343,   -1,  257,   -1,
   -1,  260,   -1,   -1,  123,  264,  265,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  109,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,  123,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,   -1,  123,
   -1,  278,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,  265,  266,   -1,  123,  167,
   -1,  169,  272,  273,  172,  173,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,   -1,  123,   -1,  123,   -1,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,   -1,
   -1,   -1,   -1,   -1,  272,  273,  125,   -1,  276,  277,
  278,  279,   -1,   -1,  212,   -1,  125,  215,  257,  217,
   -1,  260,  220,  262,  263,  264,   -1,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,  267,  278,
  125,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
  125,   -1,   -1,  257,  252,   -1,  260,  255,  262,  263,
  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  257,  125,  278,  260,  261,   -1,   -1,  264,
   -1,   -1,   -1,  257,   -1,   -1,  260,  272,  273,  263,
  264,   -1,  125,  278,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,  257,   -1,  278,  260,  261,   -1,  306,  264,
   -1,  125,   -1,   -1,   -1,   -1,   -1,  272,  273,  257,
   -1,  257,  260,  278,  260,  263,  264,   -1,  264,   -1,
  125,   -1,   -1,   -1,  272,  273,  272,  273,  257,   -1,
  278,  260,  278,   -1,   -1,  264,  265,  125,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,   -1,  272,  273,   -1,  125,  276,  277,  278,
  279,   -1,  257,   -1,   -1,  260,  125,   -1,   -1,  264,
  265,   -1,  257,   -1,   -1,  260,  125,  272,  273,  264,
  265,  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,  266,  267,  149,  150,   -1,  152,  272,  273,
  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,  264,
   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,  257,
  275,   -1,  260,  278,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,   -1,  275,  257,   -1,
  278,  260,   -1,  262,  263,  264,   -1,   -1,  257,   -1,
   -1,  260,  207,  272,  273,  264,   -1,  266,  257,  278,
   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,
   -1,   -1,   -1,   -1,   -1,  240,   -1,  242,  257,  244,
   -1,  260,  247,   -1,  249,  264,  265,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,   -1,  278,  257,   -1,  257,  260,   -1,  260,
   -1,  264,  265,  264,   -1,  266,   -1,  292,   -1,  272,
  273,  272,  273,  276,  277,  278,  279,  278,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,  278,
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
"sentencia_ejecutable_con_return : sentencia_when_con_return",
"sentencia_ejecutable_con_return : DEFER sentencia_when_con_return",
"sentencia_ejecutable_con_return : seleccion_con_return",
"sentencia_ejecutable_con_return : DEFER seleccion_con_return",
"sentencia_ejecutable_con_return : sentencia_do_con_return sentencia_return",
"sentencia_ejecutable_con_return : DEFER sentencia_do_con_return sentencia_return",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' bloque_sentencias_when_con_return '}' ';'",
"bloque_sentencias_when_con_return : sentencia_return",
"bloque_sentencias_when_con_return : bloque_sentencias_when sentencia_return",
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

//#line 342 ".\gramatica.y"

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
//#line 801 "Parser.java"
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
case 30:
//#line 78 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 33:
//#line 87 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 34:
//#line 88 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 39:
//#line 102 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 56:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 60:
//#line 144 ".\gramatica.y"
{ logger.logError("[Parser] Se espereaba un tipo para el parametro"); }
break;
case 61:
//#line 148 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 62:
//#line 149 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 63:
//#line 150 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 67:
//#line 160 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 68:
//#line 161 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 69:
//#line 162 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 83:
//#line 185 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 84:
//#line 186 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 85:
//#line 187 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 86:
//#line 188 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 87:
//#line 189 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 88:
//#line 193 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 89:
//#line 194 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 92:
//#line 203 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 93:
//#line 204 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 94:
//#line 205 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 95:
//#line 206 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 96:
//#line 207 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 102:
//#line 225 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 103:
//#line 226 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 104:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 105:
//#line 231 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 106:
//#line 232 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 107:
//#line 233 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 108:
//#line 234 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 111:
//#line 243 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 112:
//#line 244 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 113:
//#line 245 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 114:
//#line 246 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 115:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 116:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 117:
//#line 249 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 118:
//#line 250 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 119:
//#line 251 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 120:
//#line 252 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 121:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 122:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 123:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 124:
//#line 256 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 125:
//#line 257 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 126:
//#line 258 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 129:
//#line 264 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 130:
//#line 265 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 157:
//#line 321 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 158:
//#line 322 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 159:
//#line 323 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 160:
//#line 324 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 161:
//#line 325 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 162:
//#line 326 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 163:
//#line 327 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 164:
//#line 328 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 165:
//#line 329 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 167:
//#line 334 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1238 "Parser.java"
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
