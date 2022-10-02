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
   13,   16,   16,   15,   10,   10,   10,   17,   17,   18,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
   24,   24,   24,   25,   25,   23,   23,   26,   27,   27,
   29,   29,   19,   19,   19,   22,   22,   22,   22,   30,
   22,   20,   20,   31,   31,   32,   32,   28,   33,   33,
   33,   33,   33,   33,   14,   14,   14,   34,   34,   34,
   35,   35,   35,   37,   38,   38,   39,   39,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   36,   36,    7,
    7,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    1,    2,    1,    1,    3,    1,
    1,    3,    1,    4,    3,    6,    7,    6,    1,    3,
    5,    1,    3,    2,    3,    2,    2,    1,    3,    3,
    1,    2,    2,    3,    1,    2,    1,    2,    2,    3,
    1,    2,    2,    1,    3,    6,    8,    1,    1,    3,
    1,    2,    4,    3,    3,    7,    6,    6,    6,    0,
   11,    7,    9,    1,    3,    1,    2,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    4,    1,    3,    1,    1,    5,    5,
    4,    4,    4,    4,    4,    4,    4,    1,    2,    1,
    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,  101,  100,    0,    0,    0,    0,    7,    8,    0,
   10,   11,    0,   31,    0,   35,   37,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,   98,    0,    0,
    0,    0,    0,   80,   82,   83,    0,    0,    0,   41,
   49,    0,    0,   32,    0,   36,   38,    0,    0,   27,
    0,    0,    4,    6,    0,    0,    0,    0,    0,   33,
   39,    0,   54,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   99,    0,    0,   69,   70,   71,    0,    0,
   72,   73,   74,    0,    0,    0,    0,    0,   43,   51,
    0,   42,    0,   34,   40,    0,   25,    0,    0,    9,
    0,    0,   15,    0,   53,    0,   94,   92,   95,    0,
   93,    0,   91,    0,    0,    0,    0,   87,   88,    0,
   85,    0,    0,    0,    0,    0,    0,    0,   78,   79,
   48,   45,   50,   52,    0,   30,   29,   12,   14,    0,
    0,    0,   90,   89,    0,   24,    0,    0,    0,   84,
   60,    0,    0,    0,    0,    0,    0,    0,    0,   64,
    0,   16,    0,    0,   86,    0,   58,    0,   59,   57,
   46,    0,    0,   66,    0,    0,   62,   17,    0,    0,
   56,   18,    0,   65,   67,    0,    0,   21,    0,   47,
   63,    0,    0,   23,    0,   61,
};
final static short yydgoto[] = {                          3,
    4,    5,   68,   17,   18,   19,   20,   66,   21,   22,
   23,   69,  126,   41,  197,  198,   61,   62,   24,   25,
   26,   27,   28,   51,   52,   29,   53,   42,  101,  176,
  171,  185,   94,   43,   44,   45,   46,  130,  131,
};
final static short yysindex[] = {                      -114,
    0,  111,    0, -111,    0, -207,   23,   57, -212,   65,
  -86,    0,    0,  106,  -56,  -57,  111,    0,    0, -182,
    0,    0, -110,    0,   18,    0,    0,   20,   24,    0,
   72,   29,   45,   54,   67,   56,   58,    0, -155,    6,
   40,   63,  -14,    0,    0,    0,   48,   50,  242,    0,
    0,   52, -161,    0,   59,    0,    0,   61, -152,    0,
   66,   82,    0,    0,   85,   73,  111, -142,   11,    0,
    0, -136,    0,   13,   97,   83,   87,  -10,   -7,   89,
  -36,   78,    0, -111,  -34,    0,    0,    0,   29,   29,
    0,    0,    0,   29, -122,   29,   29, -113,    0,    0,
  223,    0,  109,    0,    0, -102,    0, -100, -182,    0,
   33,  120,    0,  -86,    0,  -97,    0,    0,    0,  102,
    0,  113,    0,  107,  -81,  136,  135,    0,    0,  -15,
    0,  123, -111, -109,  -14,  -14,   17, -111,    0,    0,
    0,    0,    0,    0,   29,    0,    0,    0,    0,   29,
  -91,  -69,    0,    0, -241,    0,  126, -241,   78,    0,
    0,  134, -111,  137,  138,  144,   14,  154, -145,    0,
 -223,    0, -241,  155,    0,  -74,    0,  141,    0,    0,
    0,  143,   29,    0,  240,  -69,    0,    0, -241,  168,
    0,    0,  169,    0,    0,  -52,  170,    0,   29,    0,
    0, -241,  172,    0, -111,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  157,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,  -18,    0,    0,    0,  158,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  200,   55,    0,    0,  159,    0,    0,    0,    0,    0,
    0,    0,    0,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  148,
    0,  171,    0,    0,    0,    0,  272,    0,    0,    0,
    0,    0,    0,    0,    5,   28,  -11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  276,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  277,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    7,   36,    0,    0,   21,  227,  229,    0,    0,
    0,  274,    0,  -16,  -64,  147,  231,    0,  336,  337,
  346,  347,  348,  -25,    0,  266,  253,  -24,    0,    0,
  183,    0,    0,   -9,   -4,  -60,    0,    0,  213,
};
final static int YYTABLESIZE=520;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         81,
   81,   81,   60,   81,  124,   81,  134,   75,    2,    5,
   30,    2,   67,    2,   74,   85,  127,   81,   81,   81,
   81,  129,   77,  100,   77,  160,   77,   96,  159,   68,
  120,   50,   97,  122,   12,   13,   49,   16,  186,  187,
   77,   77,   77,   77,   36,   75,   84,   75,  119,   75,
   39,  121,   64,  169,  182,   89,   89,   90,   90,   89,
   31,   90,   32,   75,   75,   75,   75,   63,   76,   50,
   76,  115,   76,   39,   65,  144,   70,  137,   71,  135,
  136,   72,   89,   81,   90,   76,   76,   76,   76,   76,
  132,  139,  140,  174,   77,   81,   35,   82,  129,   92,
   93,   91,   83,   95,   40,   98,   77,   80,   99,   39,
  102,    6,  103,   28,    7,  106,   39,  104,    8,  105,
  166,   50,   39,  112,  107,  108,   10,   11,  109,   75,
   73,  110,   14,  167,   50,  113,  114,  116,  138,  162,
  164,  117,    1,  141,  165,  118,    6,  123,  145,    7,
    5,  163,   76,    8,    9,  146,   59,  149,  193,  150,
  153,   10,   11,  152,  155,   12,   13,   14,   15,  178,
    6,  154,  170,    7,  203,  156,  157,    8,  158,   28,
   47,  161,  168,  173,  181,   10,   11,    6,   48,  184,
    7,   14,  177,  183,    8,  179,  180,  190,  189,  191,
   59,  192,   10,   11,   55,  195,  170,  199,   14,  200,
  201,  206,  205,  202,   48,   81,   44,   13,   81,   81,
   81,   81,   81,   81,   81,   81,  133,   81,   81,   81,
   81,   81,   81,   81,   81,   81,   81,   81,   77,   12,
   13,   77,   77,   77,   77,   77,   77,   77,   77,   68,
   77,   77,   77,   77,   77,   77,   77,   77,   77,   77,
   77,   75,   37,   38,   75,   75,   75,   75,   75,   75,
   75,   75,   97,   75,   75,   75,   75,   75,   75,   75,
   75,   75,   75,   75,   76,   37,   38,   76,   76,   76,
   76,   76,   76,   76,   76,   96,   76,   76,   76,   76,
   76,   76,   76,   76,   76,   76,   76,  125,   86,   87,
   88,   28,   19,   33,   28,   34,   20,   22,   28,   28,
   28,   37,   38,   78,   26,   79,   28,   28,   37,   38,
   28,   28,   28,   28,  128,   38,   55,  148,  147,   55,
  111,   55,   55,   55,   55,   55,   55,  143,  204,   54,
   55,   55,   55,   55,   55,   55,   55,   55,   55,   56,
   57,   58,    6,  142,  194,    7,  151,    6,  196,    8,
    7,  175,    0,    0,    8,    9,    0,   10,   11,    0,
    0,  172,   10,   11,  125,    0,   12,   13,   14,   15,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  188,
    0,    0,    0,    0,   97,    0,    0,   97,    0,   97,
   97,   97,   97,   97,   97,  125,    0,    0,    0,   97,
   97,   97,   97,   97,   97,   97,   97,   96,  125,    0,
   96,    0,   96,   96,   96,   96,   96,   96,    0,    0,
    0,    0,   96,   96,   96,   96,   96,   96,   96,   96,
    0,    0,    0,    0,    0,    0,   26,    0,    0,   26,
    0,    0,    0,   26,   26,   26,    0,    0,    0,    0,
    0,   26,   26,    0,    0,   26,   26,   26,   26,    6,
    0,    0,    7,    0,    0,    0,    8,    0,    0,   47,
    0,    0,    0,    0,   10,   11,    6,   48,    6,    7,
   14,    7,    0,    8,    0,    8,    0,    0,   47,    0,
    0,   10,   11,   10,   11,    0,   48,   14,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   41,   47,   41,   32,  123,  125,
    4,  123,  123,  123,   31,   40,   81,   59,   60,   61,
   62,   82,   41,   49,   43,   41,   45,   42,   44,   41,
   41,   11,   47,   41,  276,  277,  123,    2,  262,  263,
   59,   60,   61,   62,  257,   41,   41,   43,   59,   45,
   45,   59,   17,  123,   41,   43,   43,   45,   45,   43,
  268,   45,   40,   59,   60,   61,   62,  125,   41,   49,
   43,   59,   45,   45,  257,  101,   59,   94,   59,   89,
   90,   58,   43,  125,   45,   41,   59,   60,   61,   62,
   84,   96,   97,  158,   41,   40,   40,   40,  159,   60,
   61,   62,  258,   41,   40,   58,  125,   41,   59,   45,
   59,  257,  274,   59,  260,  268,   45,   59,  264,   59,
  145,  101,   45,  266,   59,   44,  272,  273,   44,  125,
   59,   59,  278,  150,  114,  125,  273,   41,  261,  133,
  134,   59,  257,  257,  138,   59,  257,   59,   40,  260,
  266,  261,  125,  264,  265,  258,  257,  125,  183,   40,
   59,  272,  273,  261,   58,  276,  277,  278,  279,  163,
  257,   59,  152,  260,  199,  257,   41,  264,   44,  125,
  267,   59,  274,   58,   41,  272,  273,  257,  275,  169,
  260,  278,   59,   40,  264,   59,   59,  272,   44,   59,
  257,   59,  272,  273,  125,  185,  186,   40,  278,   41,
  263,  205,   41,   44,   58,  257,   59,   59,  260,  261,
  262,  263,  264,  265,  266,  267,  261,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  257,  276,
  277,  260,  261,  262,  263,  264,  265,  266,  267,  261,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  257,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  125,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  257,  258,  260,  261,  262,
  263,  264,  265,  266,  267,  125,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   81,  269,  270,
  271,  257,   41,  257,  260,  259,   41,   41,  264,  265,
  266,  257,  258,  257,  125,  259,  272,  273,  257,  258,
  276,  277,  278,  279,  257,  258,  257,  109,  108,  260,
   67,  262,  263,  264,  265,  266,  267,  125,  202,   14,
   14,  272,  273,  274,  275,  276,  277,  278,  279,   14,
   14,   14,  257,   98,  125,  260,  114,  257,  186,  264,
  260,  159,   -1,   -1,  264,  265,   -1,  272,  273,   -1,
   -1,  155,  272,  273,  158,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,  262,
  263,  264,  265,  266,  267,  189,   -1,   -1,   -1,  272,
  273,  274,  275,  276,  277,  278,  279,  257,  202,   -1,
  260,   -1,  262,  263,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,  266,   -1,   -1,   -1,   -1,
   -1,  272,  273,   -1,   -1,  276,  277,  278,  279,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,   -1,   -1,  272,  273,  257,  275,  257,  260,
  278,  260,   -1,  264,   -1,  264,   -1,   -1,  267,   -1,
   -1,  272,  273,  272,  273,   -1,  275,  278,   -1,  278,
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
"sentencia_declarativa : declaracion_constantes",
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
"declaracion_constantes : CONST lista_declaracion_constantes ';'",
"declaracion_constantes : CONST lista_declaracion_constantes",
"declaracion_constantes : CONST ';'",
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

//#line 233 ".\gramatica.y"

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
//#line 508 "Parser.java"
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
case 26:
//#line 80 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 27:
//#line 81 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 46:
//#line 118 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 47:
//#line 119 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until con etiqueta detectada"); }
break;
case 53:
//#line 137 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 54:
//#line 138 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 55:
//#line 139 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 56:
//#line 143 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 57:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion de la sentencia when"); }
break;
case 58:
//#line 145 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion de la sentencia when"); }
break;
case 59:
//#line 146 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 60:
//#line 147 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 61:
//#line 148 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 62:
//#line 152 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 63:
//#line 153 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 89:
//#line 212 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 90:
//#line 213 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 91:
//#line 214 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
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
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 95:
//#line 218 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 96:
//#line 219 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 97:
//#line 220 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 99:
//#line 225 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 785 "Parser.java"
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
