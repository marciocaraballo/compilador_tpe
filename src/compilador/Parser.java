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
    7,    8,   13,   13,   16,   16,   14,   14,   18,   20,
   17,   17,   17,   17,   17,   17,   17,   17,   23,   24,
   24,   21,   26,   26,   22,   22,   28,   30,   30,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   15,
   15,   15,   15,   15,   32,   32,   32,   35,   35,   34,
   34,   36,   34,    9,    9,    9,   37,   37,   38,   38,
   38,   38,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,   44,   44,   44,   45,   45,   45,   45,   45,
   46,   46,   43,   43,   47,   47,   47,   47,   47,   29,
   48,   48,   31,   31,   39,   39,   39,   42,   42,   49,
   42,   27,   27,   40,   40,   40,   40,   40,   40,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   50,
   50,   50,   50,   25,   25,   19,   19,   52,   19,   51,
   51,   51,   51,   51,   51,   33,   33,   33,   53,   53,
   53,   54,   54,   54,   56,   56,   57,   57,   58,   58,
   41,   41,   41,   41,   41,   41,   41,   41,   41,   55,
   55,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    4,    1,    2,    1,    2,    1,    2,   10,    1,
    1,    1,    1,    2,    1,    2,    1,    2,    8,    1,
    4,    9,    1,    2,    1,    3,    7,    1,    4,    6,
    7,    5,    5,    5,    5,    6,    6,    6,    6,    5,
    4,    3,    3,    4,    1,    3,    5,    1,    3,    2,
    1,    0,    2,    3,    2,    2,    1,    3,    3,    2,
    2,    1,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    1,    1,    2,    4,    1,    3,    3,
    2,    1,    1,    3,    7,    6,    6,    6,    6,    1,
    1,    3,    1,    2,    4,    3,    3,    9,    8,    0,
   17,    1,    2,    8,   10,    7,    7,    7,    7,    7,
    7,    9,    9,    9,    9,    9,    9,    9,    8,    1,
    3,    2,    2,    1,    2,    3,    2,    0,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    3,    4,    1,    3,    1,    1,
    5,    5,    4,    4,    4,    4,    4,    4,    4,    1,
    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  183,  182,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,    0,   83,   85,   87,   89,
   91,  103,    0,    0,    0,    0,    0,  180,    0,    0,
    0,    0,    0,    0,  161,  163,  164,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,  111,   94,
   95,    0,   84,   86,   88,   90,   92,    0,   76,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,  116,    0,   19,    0,  181,    0,    0,    0,
  150,  151,  152,    0,    0,  153,  154,  155,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   96,    0,  101,
    0,  113,    0,   81,    0,   74,    0,   15,    0,    0,
    0,    0,    0,   31,   32,    0,    0,   23,    0,   25,
   27,   33,   35,   37,   45,    0,  104,    1,  115,  169,
  165,  170,    0,  167,    0,    0,    0,    0,    0,    0,
    0,    0,  159,  160,  176,  174,  177,    0,  175,    0,
  173,    0,    0,    0,    0,   70,    0,    0,   73,    0,
    0,    0,    0,  110,  100,    0,  112,  114,    0,    0,
   79,   78,    0,    0,    0,    0,    0,   48,    0,    0,
   34,   36,   38,   21,   22,   24,   26,   28,    0,    0,
  166,    0,    0,    0,    0,    0,    0,    0,    0,  172,
  171,    0,   55,    0,    0,   54,    0,    0,   53,    0,
    0,    0,    0,   97,    0,    0,    0,    0,    0,    0,
   62,    0,    0,    0,    0,   46,  168,  144,    0,  142,
  145,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,    0,   59,   58,    0,   57,    0,    0,
    0,    0,  107,  109,    0,  108,    0,   64,    0,    0,
    0,    0,    0,  141,    0,  128,    0,  126,    0,    0,
  131,    0,    0,    0,  129,    0,  127,   51,    0,   67,
  123,    0,    0,    0,  105,    0,   60,    0,   49,    0,
    0,    0,    0,  139,    0,    0,    0,  124,    0,    0,
    0,  120,    0,  119,    0,    0,    0,    0,    0,    0,
    0,  134,  132,  136,  137,    0,  135,  133,   69,    0,
  118,    0,    0,    0,   43,    0,    0,   47,   40,  125,
    0,    0,   30,    0,   39,    0,   44,    0,   41,    0,
   42,    0,   29,    0,    0,    0,    0,  121,
};
final static short yydgoto[] = {                          3,
    4,   15,  269,   17,  213,   19,   20,   21,   22,   23,
   24,   25,  136,  137,  138,  139,  140,  141,   41,  327,
  142,  143,  144,  328,  214,  346,  270,  145,   26,  199,
  121,  111,   42,  112,  300,  113,   70,   71,   27,   28,
   29,   30,   31,   59,   60,   61,   32,   62,  340,  218,
   99,   43,   44,   45,   46,   47,  153,  154,
};
final static short yysindex[] = {                       -76,
    0,  988,    0,  543,  -38,    4,    1,    2,   26,  612,
    0,    0,  274,  -41,  752,    0,    0,    0,    0,    0,
    0,    0,  -40,   51,  -10,   27,    0,    0,    0,    0,
    0,    0,  775,  785,   -9, -141,   92,    0,  -98,   93,
  123,  -33,  374,  173,    0,    0,    0,  130,  138,  -25,
  -37,  -24,   99,  -79,   89,  145, 1011,    0,    0,    0,
    0,  -66,    0,    0,    0,    0,    0, -103,    0,  152,
  174,    0,    0,  184,    0,  187,    0,  955,   -3,    0,
  808,    0,    0,  155,    0,  101,    0,   -6,  -19,   -4,
    0,    0,    0,   38,   38,    0,    0,    0,   38,   38,
   38,   38,  220,  234,   34,   47,  246,  -32,  262,   65,
  287,  295,   95,  332,  386,  176,  413,    0,  -35,    0,
  395,    0,    6,    0,  188,    0,  228,    0,   60,   67,
  452,  571,  316,    0,    0,  368,  369,    0,  955,    0,
    0,    0,    0,    0,    0,  437,    0,    0,    0,    0,
    0,    0,   50,    0,  735,  735,  656,  735,  173,  173,
  178,  178,    0,    0,    0,    0,    0,  451,    0,  453,
    0,  461,   84,   77,   84,    0,  463,   84,    0,   84,
  474,  399, -102,    0,    0,  477,    0,    0,  158,  498,
    0,    0,   93,  164,  464,   99, 1011,    0,  266,   80,
    0,    0,    0,    0,    0,    0,    0,    0,  268,   96,
    0, -183,    0,  935,  100,  104,  634,  149,  156,    0,
    0,   84,    0,  484,   84,    0,   84,  499,    0,   84,
  988,  421,  988,    0,  489,   70,  494,  -11,  501,  492,
    0,  522,  889,  534,   93,    0,    0,    0,  945,    0,
    0,  735,  516,  735,  518,  667,  519,  162,  735,  523,
  735,  524,    0,   84,    0,    0,   84,    0,  988,  459,
  988,  460,    0,    0,  527,    0,  687,    0,  532,  -88,
  468,   38,   -7,    0,  334,    0,  335,    0,  540,  337,
    0,  704,  545,  342,    0,  350,    0,    0,  570,    0,
    0,  557,  493,  558,    0,  593,    0,  500,    0,  581,
  724,  566,  567,    0,  576,  578,  367,    0,  582,  583,
   84,    0,  589,    0, -183,    0,  378,  373,  978,  591,
  593,    0,    0,    0,    0,  592,    0,    0,    0,  382,
    0,  918,  380,  594,    0,  531,  -82,    0,    0,    0,
  617,  538,    0,  401,    0,  610,    0,   38,    0,  613,
    0,  630,    0,  417,  556,  988,  562,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  207,  405,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   11,    0,    0,  405,
    0,    0,    0,   37,    0,    0,    0,    0,    0,    0,
  424,  424,  405,  624,  831,  850,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  355,    0,  476,
  330,    0,    0,  447,    0,  504,    0,    0,    0,    0,
  683,    0,    0,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,    0,    0,    0,    0,    0,    0,  424,    0,  117,
    0,   85,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  405,    0,  420,    0,    0,    0,  405,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  108,  131,
  -34,  -26,    0,    0,    0,    0,    0,  166,    0,  196,
    0,    0,    0,    0,    0,    0,    0,  424,    0,    0,
    0,    0,    0,    0,    0,  869,    0,    0,  405,    0,
    0,    0,  405,    0,    0,  405,    0,    0,    0,  405,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  908,    0,    0,    0,    0,    0,    0,    0,
    0,  579,    0,    0,    0,    0,    0,   87,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -61,
    0,    0,    0,    0,  405,    0,    0,    0,  186,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  580,    0,    0,  424,    0,  -80,    0,
    0,    0,    0,    0,  224,    0,    0,    0,  288,    0,
    0,  405,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  251,    0,    0,    0,    0,    0,   98,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  424,    0,    0,    0,    0,  189,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  292,    0,    0,    0,    0,
    0,  186,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  405,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   59,  577,  -16,   -2,    0,    0,    0,    0,  587,
   91,    0,    0,    0,  318,    0,  551,  561,  -39,  358,
  572,  573,  575,    0, -173,    0, -147,  505,  -18,    0,
  512,    9,    3, -158,  389,    0,  588,    0,   44,   52,
   54,   55,   74,  -17,    0,    0,  -31,    0,    0,  533,
  674,    0,  383,  381,  -27,    0,    0,  508,
};
final static int YYTABLESIZE=1289;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   89,   18,  108,  109,  147,   36,  146,   58,  172,   94,
  162,   95,   18,  117,  149,  107,  114,   69,   75,  228,
  233,  157,  147,  185,  146,  173,   97,   98,   96,  277,
   18,   18,  149,  311,  233,   39,  158,   84,  249,  122,
   50,   52,  323,   40,  122,  189,    2,  147,   39,   83,
   39,  162,  162,  162,   58,  162,   63,  162,  152,  146,
  115,  134,   34,   63,   64,   53,   65,   66,  117,  162,
  162,  162,  162,   54,  168,  135,    6,  158,   18,  158,
    7,  158,   39,  190,   79,  272,   67,  170,    9,   10,
  211,   81,  167,  210,   13,  158,  158,  158,  158,  193,
  186,  161,  162,  188,   39,  169,  194,  156,  299,   77,
  275,   39,   78,   76,  146,   74,  174,  224,   58,  245,
  146,  301,  134,  303,   39,   65,   85,   66,  274,   58,
  157,   86,  195,   88,  225,  162,  135,   39,   68,  116,
   39,  151,   65,   39,   66,   39,  119,  118,  156,  236,
  156,  342,  156,  238,  124,   68,  242,   71,  232,   87,
   71,  158,  299,   90,  125,  179,  156,  156,  156,  156,
  103,  157,  308,  157,   71,  157,   63,  147,  104,  122,
    1,  347,  152,  130,   64,  122,   65,   66,   35,  157,
  157,  157,  157,  117,   58,  178,  240,   94,  235,   95,
   63,   63,   39,  120,  239,  283,   67,  123,   39,  248,
  126,  251,   63,  149,  101,   68,   74,  127,  367,  102,
   94,  184,   95,  106,  147,  188,  146,   36,   18,   35,
   18,  105,  156,  106,  149,   91,   92,   93,   11,   12,
   58,  156,  310,   11,   12,  128,  251,   37,   38,  156,
  130,   11,   12,  156,  155,  157,  158,   48,   51,   49,
   37,   38,   37,   38,  110,   20,   18,  162,   18,   10,
  162,  162,  162,  162,  162,  162,  162,  162,  165,  162,
  162,  162,  162,  162,  162,  162,  162,  162,  162,  162,
  179,  138,  166,  158,   37,   38,  158,  158,  158,  158,
  158,  158,  158,  158,  171,  158,  158,  158,  158,  158,
  158,  158,  158,  158,  158,  158,   37,   38,  362,  175,
  178,  176,  248,   37,   38,  117,   18,  177,  117,   77,
  117,  117,  117,  117,  117,  117,   37,   38,  178,  251,
  117,  117,  117,  117,  117,  117,  117,  117,  106,   37,
   38,  179,  150,   38,   82,   37,   38,  150,   38,   11,
   12,  252,  253,   18,  156,  254,  255,  156,  156,  156,
  156,  156,  156,  156,  156,  130,  156,  156,  156,  156,
  156,  156,  156,  156,  156,  156,  156,  157,   77,  180,
  157,  157,  157,  157,  157,  157,  157,  157,   82,  157,
  157,  157,  157,  157,  157,  157,  157,  157,  157,  157,
  259,  260,   61,   82,   37,   38,  138,  261,  262,   80,
   37,   38,  179,  292,  293,  179,  181,  179,  179,  179,
  179,  179,  179,   97,   98,   96,  182,  179,  179,  179,
  179,  179,  179,  179,  179,  191,   20,  143,  143,  198,
   30,   40,  178,  183,   77,  178,  206,  178,  178,  178,
  178,  178,  178,   80,  148,  148,  148,  178,  178,  178,
  178,  178,  178,  178,  178,   75,  159,  160,   80,   82,
  106,  163,  164,  106,   68,  106,  106,  106,  106,  106,
  106,  196,  204,  205,  209,  106,  106,  106,  106,  106,
  106,  106,  106,   16,  241,   20,   94,  130,   95,  220,
  130,  221,  130,  130,  130,  130,  130,  130,  222,  187,
  227,  231,  130,  130,  130,  130,  130,  130,  130,  130,
   54,  230,  279,    6,   94,  234,   95,    7,  237,  244,
  132,  264,  267,  271,   80,    9,   10,  273,  138,   61,
   61,  138,  276,  138,  138,  138,  138,  138,  138,  278,
  281,   61,  280,  138,  138,  138,  138,  138,  138,  138,
  138,   20,   54,  282,  286,  200,  288,  291,   16,    7,
   16,  295,  297,  302,  304,  305,   77,  131,  132,   77,
  307,   73,  309,   77,   77,   77,  312,  313,  314,  315,
   75,   77,   77,  318,  319,   77,   77,   77,   77,   16,
   73,   82,  320,  321,   82,  322,  324,  323,   82,   82,
   82,  330,  329,  326,  332,  333,   82,   82,   16,  336,
   82,   82,   82,   82,  334,  344,  335,  110,  110,  343,
  337,  338,   91,   92,   93,  130,  345,  341,  349,  348,
  350,   54,  355,  351,    6,  356,  358,   73,    7,  352,
  353,   55,  359,  360,  357,   33,    9,   10,  361,   56,
  364,  363,   13,  148,  148,  148,   80,  365,  366,   80,
   72,  110,    4,   80,   80,   80,  368,  215,  216,  207,
  219,   80,   80,  197,  110,   80,   80,   80,   80,  208,
  354,   52,   56,   20,  201,  202,   20,  203,  243,  339,
   20,   20,   20,  246,  192,  325,  100,  247,   20,   20,
    0,    0,   20,   20,   20,   20,    0,    0,    0,    0,
    0,    0,   75,    0,   57,   75,    0,    0,    0,   75,
   75,   75,    0,    0,    0,    0,    0,   75,   75,  258,
    0,   75,   75,   75,   75,    0,  212,    0,    0,  223,
   16,  226,    0,   16,  110,    0,  229,   16,   16,   16,
    0,    0,    0,    0,    0,   16,   16,    0,  212,   16,
   16,   16,   16,    0,  285,    0,  287,    0,  290,  212,
    0,  294,    0,  296,    0,    0,    0,    0,    0,    5,
    0,    0,    6,    0,    0,    0,    7,    8,  263,  212,
    0,  265,    0,  266,    9,   10,  268,    0,   11,   12,
   13,   14,    0,    0,  317,    0,  212,   54,    0,    0,
    6,    0,    0,    0,    7,    0,  130,   55,  258,    0,
    0,    0,    9,   10,    0,   56,  212,    0,   13,   54,
  298,    0,    6,  110,  256,  257,    7,  212,  130,    0,
    0,    0,    0,  258,    9,   10,    0,    0,   54,    0,
   13,    6,    0,    0,    0,    7,   72,    0,   55,    0,
    0,    0,    0,    9,   10,    0,   56,    0,    0,   13,
   54,    0,    0,    6,    0,  256,  257,    7,    0,   80,
    0,    0,    0,    0,    0,    9,   10,  110,    0,   82,
    0,   13,   54,    0,    0,    6,  217,    0,    0,    7,
    0,    0,    0,   54,    0,    0,    6,    9,   10,  289,
    7,    0,  148,   13,    0,    0,    0,    0,    9,   10,
    0,    0,    0,   54,   13,    0,    6,  306,    0,    0,
    7,    0,    0,    0,    0,   98,    0,    0,    9,   10,
   54,    0,    0,    6,   13,    0,  316,    7,    0,    0,
    0,    0,    0,    0,  102,    9,   10,    0,    0,    0,
   54,   13,    0,    6,  331,    0,    0,    7,    0,    0,
    0,   54,    0,   99,    6,    9,   10,    0,    7,    0,
    0,   13,    0,    0,    0,    0,    9,   10,    5,    0,
    0,    6,   13,  187,    0,    7,    8,    0,    0,    0,
    0,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    5,  144,    0,    6,    0,    0,    0,    7,    8,
    0,    5,  284,    0,    6,    0,    9,   10,    7,    8,
   11,   12,   13,   14,    0,    0,    9,   10,    0,  250,
   11,   12,   13,   14,    5,    0,    0,    6,    0,  284,
    0,    7,    8,    0,    0,    0,    0,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,   98,    0,    0,
   98,    0,    0,    0,   98,    0,   98,   98,    0,    0,
    0,    0,   98,   98,   98,   98,  102,    0,   98,  102,
    0,    0,    0,  102,    0,  102,  102,    0,    0,    0,
    0,  102,  102,  102,  102,   99,    0,  102,   99,    0,
    0,    0,   99,    0,   99,   99,    0,    0,    0,    0,
   99,   99,   99,   99,    0,   54,   99,    0,    6,    0,
    0,    0,    7,    0,  130,   55,    0,    0,    0,    0,
    9,   10,    0,   56,  144,    0,   13,  144,    0,  140,
  140,  144,    0,    0,   54,    0,    0,    6,    0,  144,
  144,    7,    0,  130,    0,  144,    0,    0,    0,    9,
   10,   54,    0,    0,    6,   13,    0,    0,    7,    0,
    0,   54,    0,    0,    6,    0,    9,   10,    7,    0,
    0,    5,   13,    0,  129,    0,    9,   10,    7,    8,
  130,    0,   13,    0,    0,    0,  131,  132,    0,    0,
   11,   12,  133,   14,    5,    0,    0,    6,    0,    0,
    0,    7,    8,  130,    5,    0,    0,    6,    0,    9,
   10,    7,    8,   11,   12,   13,   14,    0,    0,    9,
   10,    0,    0,   11,   12,   13,   14,   54,    0,    0,
    6,    0,    0,    0,    7,    0,    0,   55,    0,    0,
    0,    0,    9,   10,    0,   56,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,    4,   40,   41,   41,   44,   41,   10,   41,   43,
    0,   45,   15,   53,   41,   41,   41,   59,   59,  178,
  123,   41,   59,   59,   59,   58,   60,   61,   62,   41,
   33,   34,   59,   41,  123,   45,    0,   35,  212,   57,
   40,   40,  125,   40,  125,   40,  123,   79,   45,   59,
   45,   41,   42,   43,   57,   45,   13,   47,   86,   78,
   52,   78,    4,  125,   13,   40,   13,   13,    0,   59,
   60,   61,   62,  257,   41,   78,  260,   41,   81,   43,
  264,   45,   45,  123,   58,  233,   13,   41,  272,  273,
   41,   33,   59,   44,  278,   59,   60,   61,   62,   40,
  119,   99,  100,  121,   45,   59,   40,    0,  267,   59,
   41,   45,  123,   23,  133,  257,  108,   41,  121,   40,
  139,  269,  139,  271,   45,   41,   36,   41,   59,  132,
    0,   40,  130,   41,   58,  125,  139,   45,   41,   41,
   45,   41,   58,   45,   58,   45,   58,   59,   41,  189,
   43,  325,   45,  193,  258,   58,  196,   41,  261,  258,
   44,  125,  321,   41,  268,    0,   59,   60,   61,   62,
   41,   41,  261,   43,   58,   45,  133,  209,   41,  197,
  257,  329,  210,  266,  133,  266,  133,  133,  268,   59,
   60,   61,   62,  125,  197,    0,  194,   43,   41,   45,
  262,  263,   45,   59,   41,  245,  133,  274,   45,  212,
   59,  214,  274,   59,   42,  257,  257,   44,  366,   47,
   43,  257,   45,    0,  261,  243,  261,   44,  231,  268,
  233,  257,  125,  259,  261,  269,  270,  271,  276,  277,
  243,  261,  282,  276,  277,   59,  249,  257,  258,  261,
    0,  276,  277,  261,  261,  125,  261,  257,  257,  259,
  257,  258,  257,  258,   58,   59,  269,  257,  271,  273,
  260,  261,  262,  263,  264,  265,  266,  267,   59,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  125,    0,   59,  257,  257,  258,  260,  261,  262,  263,
  264,  265,  266,  267,   59,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  257,  258,  358,   58,
  125,  257,  325,  257,  258,  257,  329,   41,  260,    0,
  262,  263,  264,  265,  266,  267,  257,  258,   44,  342,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  257,
  258,  257,  257,  258,    0,  257,  258,  257,  258,  276,
  277,  262,  263,  366,  257,  262,  263,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  257,   59,   58,
  260,  261,  262,  263,  264,  265,  266,  267,   44,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  262,  263,  125,   59,  257,  258,  125,  262,  263,    0,
  257,  258,  257,  262,  263,  260,   41,  262,  263,  264,
  265,  266,  267,   60,   61,   62,  261,  272,  273,  274,
  275,  276,  277,  278,  279,  258,    0,  262,  263,  132,
  262,  263,  257,   41,  125,  260,  139,  262,  263,  264,
  265,  266,  267,   44,   60,   61,   62,  272,  273,  274,
  275,  276,  277,  278,  279,    0,   94,   95,   59,  125,
  257,  101,  102,  260,  257,  262,  263,  264,  265,  266,
  267,   40,  125,  125,   58,  272,  273,  274,  275,  276,
  277,  278,  279,    0,   41,   59,   43,  257,   45,   59,
  260,   59,  262,  263,  264,  265,  266,  267,   58,  125,
   58,  123,  272,  273,  274,  275,  276,  277,  278,  279,
  257,   58,   41,  260,   43,   59,   45,  264,   41,  274,
  273,   58,   44,  123,  125,  272,  273,   59,  257,  262,
  263,  260,   59,  262,  263,  264,  265,  266,  267,   59,
  243,  274,   41,  272,  273,  274,  275,  276,  277,  278,
  279,  125,  257,   40,   59,  260,   59,   59,    2,  264,
    4,   59,   59,  125,  125,   59,  257,  272,  273,  260,
   59,   15,  125,  264,  265,  266,  263,  263,   59,  263,
  125,  272,  273,   59,  263,  276,  277,  278,  279,   33,
   34,  257,  263,   44,  260,   59,   59,  125,  264,  265,
  266,   41,  123,  306,   59,   59,  272,  273,  125,  263,
  276,  277,  278,  279,   59,  263,   59,   51,   52,  262,
   59,   59,  269,  270,  271,  266,  329,   59,  331,   59,
   59,  257,   59,  272,  260,  125,   40,   81,  264,  342,
  343,  267,  125,  263,  347,  123,  272,  273,   59,  275,
   41,   59,  278,  269,  270,  271,  257,  261,  123,  260,
  257,   58,    0,  264,  265,  266,  125,  155,  156,  139,
  158,  272,  273,  123,  108,  276,  277,  278,  279,  139,
  343,  123,  123,  257,  133,  133,  260,  133,  197,  321,
  264,  265,  266,  209,  127,  123,   43,  210,  272,  273,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  123,  260,   -1,   -1,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,  272,  273,  217,
   -1,  276,  277,  278,  279,   -1,  123,   -1,   -1,  173,
  257,  175,   -1,  260,  178,   -1,  180,  264,  265,  266,
   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,  123,  276,
  277,  278,  279,   -1,  252,   -1,  254,   -1,  256,  123,
   -1,  259,   -1,  261,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  222,  123,
   -1,  225,   -1,  227,  272,  273,  230,   -1,  276,  277,
  278,  279,   -1,   -1,  292,   -1,  123,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,  306,   -1,
   -1,   -1,  272,  273,   -1,  275,  123,   -1,  278,  257,
  264,   -1,  260,  267,  262,  263,  264,  123,  266,   -1,
   -1,   -1,   -1,  331,  272,  273,   -1,   -1,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,  125,   -1,  267,   -1,
   -1,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,  125,
   -1,   -1,   -1,   -1,   -1,  272,  273,  321,   -1,  125,
   -1,  278,  257,   -1,   -1,  260,  261,   -1,   -1,  264,
   -1,   -1,   -1,  257,   -1,   -1,  260,  272,  273,  263,
  264,   -1,  125,  278,   -1,   -1,   -1,   -1,  272,  273,
   -1,   -1,   -1,  257,  278,   -1,  260,  261,   -1,   -1,
  264,   -1,   -1,   -1,   -1,  125,   -1,   -1,  272,  273,
  257,   -1,   -1,  260,  278,   -1,  263,  264,   -1,   -1,
   -1,   -1,   -1,   -1,  125,  272,  273,   -1,   -1,   -1,
  257,  278,   -1,  260,  261,   -1,   -1,  264,   -1,   -1,
   -1,  257,   -1,  125,  260,  272,  273,   -1,  264,   -1,
   -1,  278,   -1,   -1,   -1,   -1,  272,  273,  257,   -1,
   -1,  260,  278,  125,   -1,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,   -1,   -1,  276,  277,  278,
  279,  257,  125,   -1,  260,   -1,   -1,   -1,  264,  265,
   -1,  257,  125,   -1,  260,   -1,  272,  273,  264,  265,
  276,  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,  125,
   -1,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  257,   -1,  278,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,   -1,  257,  278,   -1,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,   -1,  275,  257,   -1,  278,  260,   -1,  262,
  263,  264,   -1,   -1,  257,   -1,   -1,  260,   -1,  272,
  273,  264,   -1,  266,   -1,  278,   -1,   -1,   -1,  272,
  273,  257,   -1,   -1,  260,  278,   -1,   -1,  264,   -1,
   -1,  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,
   -1,  257,  278,   -1,  260,   -1,  272,  273,  264,  265,
  266,   -1,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,
  276,  277,  278,  279,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,  266,  257,   -1,   -1,  260,   -1,  272,
  273,  264,  265,  276,  277,  278,  279,   -1,   -1,  272,
  273,   -1,   -1,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,   -1,  275,   -1,   -1,  278,
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
"sentencias_funcion_con_return : sentencia_funcion_con_return",
"sentencias_funcion_con_return : sentencias_funcion_con_return sentencia_funcion_con_return",
"cuerpo_funcion_sin_return : sentencia_seleccion_compuesta_con_return",
"cuerpo_funcion_sin_return : sentencias_funcion_con_return sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion_con_return ELSE bloque_sentencias_ejecutables_seleccion_con_return ENDIF ';'",
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

//#line 365 ".\gramatica.y"

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
//#line 830 "Parser.java"
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
case 42:
//#line 108 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 47:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 52:
//#line 133 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 53:
//#line 134 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 54:
//#line 135 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 55:
//#line 136 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 56:
//#line 137 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 57:
//#line 138 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 58:
//#line 139 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 59:
//#line 140 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 61:
//#line 145 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 62:
//#line 146 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 63:
//#line 147 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 64:
//#line 148 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 67:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 72:
//#line 164 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 73:
//#line 165 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 74:
//#line 169 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 75:
//#line 170 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 76:
//#line 171 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 80:
//#line 181 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 81:
//#line 182 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 82:
//#line 183 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 96:
//#line 206 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 97:
//#line 207 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 98:
//#line 208 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 99:
//#line 209 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 100:
//#line 210 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 101:
//#line 214 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 102:
//#line 215 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 105:
//#line 224 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 106:
//#line 225 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 107:
//#line 226 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 108:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 109:
//#line 228 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 115:
//#line 246 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 116:
//#line 247 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 117:
//#line 248 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 118:
//#line 252 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 119:
//#line 253 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 120:
//#line 254 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 121:
//#line 255 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 124:
//#line 264 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 125:
//#line 265 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 126:
//#line 266 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 127:
//#line 267 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 128:
//#line 268 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 129:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 130:
//#line 270 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 131:
//#line 271 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 132:
//#line 272 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) para la condicion de la sentencia de seleccion"); }
break;
case 133:
//#line 273 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( para la condicion de la sentencia de seleccion"); }
break;
case 134:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia de seleccion"); }
break;
case 135:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia de seleccion"); }
break;
case 136:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada then"); }
break;
case 137:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias en la sentencia de seleccion luego de la palabra reservada else"); }
break;
case 138:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia de seleccion"); }
break;
case 139:
//#line 279 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un bloque de sentencias para el then y el else de la sentencia de seleccion"); }
break;
case 142:
//#line 285 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { para el bloque de sentencias en la sentencia if"); }
break;
case 143:
//#line 286 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } para el bloque de sentencias en la sentencia if"); }
break;
case 148:
//#line 296 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 149:
//#line 297 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 171:
//#line 343 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 172:
//#line 344 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 173:
//#line 345 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 174:
//#line 346 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 175:
//#line 347 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 176:
//#line 348 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 177:
//#line 349 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 178:
//#line 350 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 179:
//#line 351 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 181:
//#line 356 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1311 "Parser.java"
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
