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
    0,    0,    1,    2,    3,    3,    4,    4,    5,    5,
    5,    8,    8,    9,    9,   11,   11,   12,   13,   13,
   13,   16,   16,   15,   10,   17,   17,   18,    6,    6,
    6,    6,    6,    6,    6,    6,    6,    6,   24,   24,
   24,   25,   25,   23,   23,   26,   27,   27,   29,   29,
   19,   19,   19,   22,   22,   22,   22,   30,   22,   20,
   20,   31,   31,   32,   32,   28,   33,   33,   33,   33,
   33,   33,   14,   14,   14,   34,   34,   34,   35,   35,
   35,   37,   38,   38,   39,   39,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   36,   36,    7,    7,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    3,    1,
    2,    3,    1,    4,    3,    6,    7,    6,    1,    3,
    5,    1,    3,    2,    2,    1,    3,    3,    1,    2,
    2,    3,    1,    2,    1,    2,    2,    3,    1,    2,
    2,    1,    3,    6,    8,    1,    1,    3,    1,    2,
    4,    3,    3,    7,    6,    6,    6,    0,   11,    7,
    9,    1,    3,    1,    2,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    4,    1,    3,    1,    1,    5,    5,    4,    4,
    4,    4,    4,    4,    4,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,   99,   98,    0,    0,    0,    0,    7,    8,    0,
   10,    0,    0,   29,    0,   33,   35,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,   96,    0,    0,
    0,    0,    0,   78,   80,   81,    0,    0,    0,   39,
   47,    0,    0,   30,    0,   34,   36,    0,    0,   25,
    0,    4,    6,    0,    0,   11,    0,    0,    0,   31,
   37,    0,   52,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   97,    0,    0,   67,   68,   69,    0,    0,
   70,   71,   72,    0,    0,    0,    0,    0,   41,   49,
    0,   40,    0,   32,   38,    0,    0,    0,    9,    0,
    0,   15,    0,   51,    0,   92,   90,   93,    0,   91,
    0,   89,    0,    0,    0,    0,   85,   86,    0,   83,
    0,    0,    0,    0,    0,    0,    0,   76,   77,   46,
   43,   48,   50,    0,   28,   27,   12,   14,    0,    0,
    0,   88,   87,    0,   24,    0,    0,    0,   82,   58,
    0,    0,    0,    0,    0,    0,    0,    0,   62,    0,
   16,    0,    0,   84,    0,   56,    0,   57,   55,   44,
    0,    0,   64,    0,    0,   60,   17,    0,    0,   54,
   18,    0,   63,   65,    0,    0,   21,    0,   45,   61,
    0,    0,   23,    0,   59,
};
final static short yydgoto[] = {                          3,
    4,    5,   68,   17,   18,   19,   20,   65,   21,   22,
   23,   69,  125,   41,  196,  197,   60,   61,   24,   25,
   26,   27,   28,   51,   52,   29,   53,   42,  101,  175,
  170,  184,   94,   43,   44,   45,   46,  129,  130,
};
final static short yysindex[] = {                      -111,
    0, -151,    0,  -95,    0, -230,    9,   55, -212,   58,
  -66,    0,    0,  113, -194,  -50, -151,    0,    0, -179,
    0,   33,  -92,    0,   35,    0,    0,   37,   39,    0,
   60,   29,   67,   74,   63,   59,   70,    0, -142,    6,
   40,   76,   12,    0,    0,    0,   65,   72,   71,    0,
    0,   75, -156,    0,   77,    0,    0,   78, -139,    0,
   89,    0,    0,   97,   84,    0, -151, -117,   32,    0,
    0, -119,    0,   -6,  118,  105,  112,  -26,   -7,  116,
  -36,   66,    0,  -95,  -34,    0,    0,    0,   29,   29,
    0,    0,    0,   29,  -91,   29,   29,  -81,    0,    0,
 -112,    0,  137,    0,    0,  -80, -194, -179,    0,   57,
  143,    0,  -66,    0,  -73,    0,    0,    0,  130,    0,
  131,    0,  135,  -60,  158,  156,    0,    0,  -12,    0,
  144,  -95, -114,   12,   12,   13,  -95,    0,    0,    0,
    0,    0,    0,   29,    0,    0,    0,    0,   29,  -72,
   69,    0,    0, -241,    0,  146, -241,   66,    0,    0,
  149,  -95,  151,  155,  164,   27,  175,  111,    0, -201,
    0, -241,  173,    0,  -54,    0,  249,    0,    0,    0,
  254,   29,    0, -122,   69,    0,    0, -241,  279,    0,
    0,  280,    0,    0,   62,  283,    0,   29,    0,    0,
 -241,  289,    0,  -95,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  274,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,  -18,    0,    0,    0,  275,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  277,    0,    0,  278,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   88,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  148,    0,
  171,    0,    0,    0,    0,  298,    0,    0,    0,    0,
    0,    0,    0,    5,   28,  -11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  299,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  315,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    7,   38,    0,    0,   11,  297,  250,    0,    0,
    0,  290,    0,  -17,  -71,  168,  252,    0,  358,  360,
  362,  365,  366,  -25,    0,  284,  268,  -24,    0,    0,
  202,    0,    0,  -10,  -15,  -65,    0,    0,  230,
};
final static int YYTABLESIZE=498;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         79,
   79,   79,  193,   79,  123,   79,  133,   75,    2,  126,
   30,    2,  142,   74,  119,   85,  128,   79,   79,   79,
   79,   50,   75,  100,   75,    5,   75,    2,  159,   66,
   67,  158,  118,  121,   12,   13,   89,   31,   90,   16,
   75,   75,   75,   75,   36,   73,   84,   73,   32,   73,
   39,  120,  114,   96,   63,   89,   49,   90,   97,   50,
  185,  186,   59,   73,   73,   73,   73,  181,   74,   89,
   74,   90,   74,   39,   62,  143,  136,   64,  134,  135,
  138,  139,   89,   79,   90,  173,   74,   74,   74,   74,
  131,   66,  128,   70,   35,   71,   72,   40,   81,   92,
   93,   91,   39,   80,   39,    6,   75,   76,    7,   82,
   39,   50,    8,    9,   77,   83,   95,  103,   73,  165,
   10,   11,   98,   50,   12,   13,   14,   15,  106,   73,
   99,  166,  107,  102,    6,  104,  105,    7,  161,  163,
  108,    8,  109,  164,    6,    1,  162,    7,  111,   10,
   11,    8,   74,  113,   47,   14,  112,  192,  115,   10,
   11,  169,   48,  116,    6,   14,    5,    7,  177,  137,
  117,    8,    9,  202,  122,  140,  144,  145,  183,   10,
   11,  148,  149,   12,   13,   14,   15,  151,  152,  153,
    6,  168,  154,    7,  194,  169,  155,    8,  156,  157,
   47,  167,  160,  172,  180,   10,   11,  176,   48,  178,
  205,   14,   53,  179,  182,   79,  188,  189,   79,   79,
   79,   79,   79,   79,   79,   79,  132,   79,   79,   79,
   79,   79,   79,   79,   79,   79,   79,   79,   75,   12,
   13,   75,   75,   75,   75,   75,   75,   75,   75,   66,
   75,   75,   75,   75,   75,   75,   75,   75,   75,   75,
   75,   73,   37,   38,   73,   73,   73,   73,   73,   73,
   73,   73,   95,   73,   73,   73,   73,   73,   73,   73,
   73,   73,   73,   73,   74,   37,   38,   74,   74,   74,
   74,   74,   74,   74,   74,   94,   74,   74,   74,   74,
   74,   74,   74,   74,   74,   74,   74,  190,   86,   87,
   88,   33,  191,   34,   37,   38,   37,   38,  198,   78,
  199,   79,  127,   38,  200,    6,  201,    6,    7,  204,
    7,   46,    8,   42,    8,   26,   13,   47,   19,   20,
   10,   11,   10,   11,   53,   48,   14,   53,   14,   53,
   53,   53,   53,   53,   53,   22,  110,  147,  146,   53,
   53,   53,   53,   53,   53,   53,   53,    6,  203,    6,
    7,   54,    7,   55,    8,   56,    8,  124,   57,   58,
  150,  141,   10,   11,   10,   11,  195,  174,   14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   95,    0,    0,   95,    0,   95,
   95,   95,   95,   95,   95,    0,    0,    0,    0,   95,
   95,   95,   95,   95,   95,   95,   95,   94,    0,    0,
   94,    0,   94,   94,   94,   94,   94,   94,    0,    0,
    0,    0,   94,   94,   94,   94,   94,   94,   94,   94,
  171,    0,    0,  124,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  187,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  124,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  124,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,  125,   45,   41,   47,   41,   32,  123,   81,
    4,  123,  125,   31,   41,   40,   82,   59,   60,   61,
   62,   11,   41,   49,   43,  125,   45,  123,   41,   41,
  123,   44,   59,   41,  276,  277,   43,  268,   45,    2,
   59,   60,   61,   62,  257,   41,   41,   43,   40,   45,
   45,   59,   59,   42,   17,   43,  123,   45,   47,   49,
  262,  263,  257,   59,   60,   61,   62,   41,   41,   43,
   43,   45,   45,   45,  125,  101,   94,  257,   89,   90,
   96,   97,   43,  125,   45,  157,   59,   60,   61,   62,
   84,   59,  158,   59,   40,   59,   58,   40,   40,   60,
   61,   62,   45,   41,   45,  257,  125,   41,  260,   40,
   45,  101,  264,  265,   41,  258,   41,  274,   59,  144,
  272,  273,   58,  113,  276,  277,  278,  279,  268,  125,
   59,  149,   44,   59,  257,   59,   59,  260,  132,  133,
   44,  264,   59,  137,  257,  257,  261,  260,  266,  272,
  273,  264,  125,  273,  267,  278,  125,  182,   41,  272,
  273,  151,  275,   59,  257,  278,  266,  260,  162,  261,
   59,  264,  265,  198,   59,  257,   40,  258,  168,  272,
  273,  125,   40,  276,  277,  278,  279,  261,   59,   59,
  257,  123,   58,  260,  184,  185,  257,  264,   41,   44,
  267,  274,   59,   58,   41,  272,  273,   59,  275,   59,
  204,  278,  125,   59,   40,  257,   44,  272,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  276,
  277,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  125,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   59,  269,  270,
  271,  257,   59,  259,  257,  258,  257,  258,   40,  257,
   41,  259,  257,  258,  263,  257,   44,  257,  260,   41,
  260,   58,  264,   59,  264,   59,   59,  267,   41,   41,
  272,  273,  272,  273,  257,  275,  278,  260,  278,  262,
  263,  264,  265,  266,  267,   41,   67,  108,  107,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  201,  257,
  260,   14,  260,   14,  264,   14,  264,   81,   14,   14,
  113,   98,  272,  273,  272,  273,  185,  158,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,  262,
  263,  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  276,  277,  278,  279,  257,   -1,   -1,
  260,   -1,  262,  263,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,  278,  279,
  154,   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  172,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  188,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  201,
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
"programa : nombre_programa bloque_sentencias",
"programa : bloque_sentencias",
"nombre_programa : ID",
"bloque_sentencias : '{' sentencias '}'",
"sentencias : sentencia",
"sentencias : sentencia sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : tipo lista_de_variables ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_constantes ';'",
"lista_de_variables : ID ',' lista_de_variables",
"lista_de_variables : ID",
"funcion : encabezado_funcion '{' cuerpo_funcion '}'",
"funcion : encabezado_funcion cuerpo_funcion '}'",
"encabezado_funcion : FUN ID '(' ')' ':' tipo",
"encabezado_funcion : FUN ID '(' lista_de_parametros ')' ':' tipo",
"cuerpo_funcion : sentencias RETURN '(' expresion ')' ';'",
"lista_de_parametros : parametro",
"lista_de_parametros : parametro ',' parametro",
"lista_de_parametros : parametro ',' parametro ',' lista_parametros_exceso",
"lista_parametros_exceso : parametro",
"lista_parametros_exceso : parametro ',' lista_parametros_exceso",
"parametro : tipo ID",
"declaracion_constantes : CONST lista_declaracion_constantes",
"lista_declaracion_constantes : declaracion_constante",
"lista_declaracion_constantes : declaracion_constante ',' lista_declaracion_constantes",
"declaracion_constante : ID ASIGNACION CTE",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : DEFER asignacion",
"sentencia_ejecutable : seleccion ';'",
"sentencia_ejecutable : DEFER seleccion ';'",
"sentencia_ejecutable : imprimir",
"sentencia_ejecutable : DEFER imprimir",
"sentencia_ejecutable : sentencia_when",
"sentencia_ejecutable : DEFER sentencia_when",
"sentencia_ejecutable : sentencia_do ';'",
"sentencia_ejecutable : DEFER sentencia_do ';'",
"sentencia_ejecutable_do : sentencia_ejecutable",
"sentencia_ejecutable_do : sentencia_break ';'",
"sentencia_ejecutable_do : CONTINUE ';'",
"sentencia_break : BREAK",
"sentencia_break : BREAK ':' etiqueta",
"sentencia_do : DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"sentencia_do : etiqueta ':' DO bloque_sentencias_ejecutables_do UNTIL '(' condicion ')'",
"etiqueta : ID",
"bloque_sentencias_ejecutables_do : sentencia_ejecutable_do",
"bloque_sentencias_ejecutables_do : '{' sentencias_ejecutables_do '}'",
"sentencias_ejecutables_do : sentencia_ejecutable_do",
"sentencias_ejecutables_do : sentencias_ejecutables_do sentencia_ejecutable_do",
"asignacion : ID ASIGNACION expresion ';'",
"asignacion : ID ASIGNACION ';'",
"asignacion : ID ASIGNACION expresion",
"sentencia_when : WHEN '(' condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN condicion ')' THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion THEN bloque_sentencias ';'",
"sentencia_when : WHEN '(' condicion ')' bloque_sentencias ';'",
"$$1 :",
"sentencia_when : WHEN '(' ')' bloque_sentencias ';' $$1 WHEN '(' condicion ')' bloque_sentencias",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
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

//#line 231 ".\gramatica.y"

public static AnalizadorLexico lexico = null;
public static Logger logger = null;
public static TablaDeSimbolos ts = null;

public void constanteConSigno(String constante) {
	if (constante.contains(".")) {
		ts.swapLexemas(constante, "-"+constante);
	} else {
		logger.logError("[Lexico] No se admiten ui16 con valores negativos: " + "-"+constante);
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
//#line 500 "Parser.java"
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
{ logger.logError("[Parser] Se esperaba nombre del programa"); }
break;
case 9:
//#line 39 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 10:
//#line 40 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 11:
//#line 41 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 15:
//#line 51 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba { en la funcion"); }
break;
case 21:
//#line 66 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 44:
//#line 116 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 45:
//#line 117 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 51:
//#line 135 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 52:
//#line 136 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 53:
//#line 137 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 54:
//#line 141 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 55:
//#line 142 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 56:
//#line 143 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 57:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 58:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 59:
//#line 146 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 60:
//#line 150 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 61:
//#line 151 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 87:
//#line 210 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 88:
//#line 211 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 89:
//#line 212 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 90:
//#line 213 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 91:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 92:
//#line 215 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 93:
//#line 216 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 94:
//#line 217 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 95:
//#line 218 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 97:
//#line 223 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 769 "Parser.java"
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
