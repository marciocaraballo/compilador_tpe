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
   13,   13,   13,   13,   10,   10,    9,    9,   15,   15,
    8,    8,    8,    8,   14,   14,   14,   14,   16,   16,
   18,   18,   19,   19,   20,   20,    7,    6,    6,    6,
   23,   23,    5,    5,   21,   21,    3,    3,    3,    3,
   24,   27,   30,   30,   32,   32,   32,   26,   26,   33,
   33,   25,   31,   31,   31,   34,   36,   36,   35,   29,
   29,   28,   28,   28,   28,   17,   37,   37,   37,   37,
   37,   37,   22,   22,   22,   38,   38,   38,   39,   39,
   39,   40,   40,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    7,    6,    7,    6,
    9,    7,    8,    6,    9,    7,    8,    6,    1,    3,
    1,    3,    1,    2,    1,    2,    3,    5,    4,    7,
    1,    3,    4,    3,    1,    3,    1,    1,    1,    1,
    3,    5,    2,    3,    3,    1,    2,    5,    7,    1,
    2,    2,    5,    4,    7,    3,    1,    3,    2,    3,
    1,    1,    1,    1,    1,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    2,
    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   82,   83,
   84,    0,    0,    0,    0,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   57,   58,   59,   60,    0,    0,
    0,    0,    0,    0,   25,    0,    0,    0,   39,    0,
    0,    0,    2,    6,    0,    0,    0,    0,    0,   72,
   56,    0,  102,    0,    0,    0,    0,   98,  101,   47,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,   61,    0,    0,   17,   19,   20,   21,   24,
    0,    0,   18,   22,   23,  100,  103,    0,   87,   88,
   89,   90,    0,    0,   91,   92,    0,    0,    0,   85,
   74,    0,    0,    0,    0,   66,    0,    0,    0,   44,
   40,    0,    0,    0,   49,    0,    0,   53,   80,    0,
    0,   41,    0,   76,   16,    0,    0,    0,    0,   96,
   97,   79,    0,   73,    0,   67,    0,   71,   68,    0,
   62,    0,    0,    0,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   65,    0,   64,    0,    0,    0,
   46,   42,    0,    0,   32,    0,   75,   69,   27,    0,
   50,    0,    0,    0,   78,    0,    0,    0,   31,    0,
   36,   29,    0,   35,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   81,   82,  122,   84,   85,   40,   55,  123,   65,  148,
   24,   56,  144,   25,   26,   27,   28,   29,   48,  113,
   30,  108,  109,   50,  152,  153,   97,   57,   58,   59,
};
final static short yysindex[] = {                      -111,
  -29,   -8, -182, -176,   45, -162,  -90, -158,    0,    0,
    0,  267,    0,  -21,  267,    0,    0,    0,    0,    0,
    0,    0,    0,  -24,    0,    0,    0,    0, -149,  -12,
 -129,  -43,   88,   99,    0, -116,  -29,  -19,    0, -114,
   32,   35,    0,    0,  -37,  -43,  104,  127,  288,    0,
    0, -104,    0,  -66,  152,  -40,   11,    0,    0,    0,
  -41,  -52, -139,  -19,   81,  169,  -51,    0,  167,   76,
   98, -149,    0,  177,  -53,    0,    0,    0,    0,    0,
   97,  288,    0,    0,    0,    0,    0,  -90,    0,    0,
    0,    0,  -43,  -43,    0,    0,  -43,  -43,  -43,    0,
    0,  -27,   30,  102,  187,    0, -149, -139,  107,    0,
    0,  -43,  108,  193,    0,  -43,  199,    0,    0,  -43,
  -18,    0,  -16,    0,    0, -221,   11,   11,  -30,    0,
    0,    0, -197,    0, -139,    0,  204,    0,    0,  208,
    0,  -51,  134,  209,    0,  210,  -18,  131,  212,  -90,
  213,  215,  219,  154,    0,  237,    0,  -43,  238,  -53,
    0,    0,  -43,   40,    0, -197,    0,    0,    0,  -30,
    0, -117,  256,  259,    0,  -53,  261,  262,    0,   59,
    0,    0,  283,    0,
};
final static short yyrindex[] = {                       328,
  -34,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -22,    0,    0,    0,
    0,  343,    0,    0,    0,    0,  304,    0,    0,    0,
    0,    1,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,  -21,    0,    0,    0,    0,    0,    0,
  115,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  224,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   94,    0,    0,  227,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   69,  318,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  235,  320,    0,    0,    0,  242,    0,    0,    0,
  150,  321,    0,    0,    0,  176,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  332,
    0,    0,    0,  200,    0,    0,  -74,  222,    0,    0,
    0,    0,  246,    0,
};
final static short yygindex[] = {                         0,
   72,    0,  -28,   18,  -25,  -20,   48,    0,    0,  353,
  292,    0,  -23,    0,    0,  -78,  -47, -124,  317,  236,
  351,  -11,    0,    0,  -32,    0,    0,  -33,  -58,  247,
  -48,    0,  -97,    0,  323,  228,    0,   87,   86,    0,
};
final static int YYTABLESIZE=565;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
   99,   54,   93,   69,   94,   55,   63,   54,    5,  126,
  138,   12,   93,  119,   94,   45,   31,   55,  114,   96,
   76,   95,   95,   77,   39,   83,   55,  102,   78,  107,
  106,   32,   38,   70,   71,  172,   46,  154,   55,  150,
  151,   99,   99,   99,   99,   99,   93,   99,  137,   77,
   38,  180,   98,   76,   78,   64,   77,   99,   83,  100,
   99,   78,   99,   95,  140,   95,   95,   95,   94,  121,
  134,  164,  146,  133,  107,  106,   33,    9,   10,   11,
   34,   64,   95,   42,   95,  129,   44,   93,   35,   93,
   93,   93,   26,  114,   36,   77,   79,  147,   41,  102,
   78,  107,  106,   43,  143,   39,   93,   47,   93,   94,
   49,   94,   94,   94,   54,  173,  117,  105,   93,  116,
   94,   77,   79,  147,    4,   99,   78,   37,   94,   79,
   94,   60,  102,    5,   77,    9,   10,   11,   61,   78,
   93,  118,   94,  176,  177,    1,  170,   95,    2,   34,
   77,    3,    4,    5,   67,   78,   66,   62,    6,   68,
    7,    8,   72,    9,   10,   11,   37,   39,   79,    2,
   73,   93,    3,   86,    5,   28,   93,  158,   94,  127,
  128,    7,   38,  130,  131,   38,   38,   38,   38,   38,
   38,   87,   88,   94,   79,   38,   38,   38,   38,   33,
   38,   38,   38,   37,  104,  111,   74,   79,  112,    3,
  115,    5,    4,   52,   53,  100,  120,   26,   75,   52,
   53,  124,   85,   79,  135,   89,   90,   91,   92,  132,
  136,  139,  141,    9,   10,   11,  142,   37,   37,   54,
    2,   74,  145,    3,    3,    5,    5,  155,  156,  159,
  160,  163,    7,   75,  149,  162,  165,   99,  166,  167,
   99,   99,   99,   99,   99,   99,   99,   99,   99,   99,
   99,   99,   99,   99,   34,   99,   99,   99,  168,   95,
  169,  171,   95,   95,   95,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   95,   95,  178,   95,   95,   95,
   28,  174,  179,   93,  181,  182,   93,   93,   93,   93,
   93,   93,   93,   93,   93,   93,   93,   93,   93,   93,
  183,   93,   93,   93,   33,   94,  184,    4,   94,   94,
   94,   94,   94,   94,   94,   94,   94,   94,   94,   94,
   94,   94,    3,   94,   94,   94,   30,   81,   15,   26,
   85,   70,   26,   26,   26,   26,   26,   26,   86,   63,
   51,   77,   26,   26,   26,   26,   45,   26,   26,   26,
   37,   54,   52,  125,   54,   54,   54,   54,   54,   54,
  110,   51,  161,  103,   54,   54,   54,   54,  157,   54,
   54,   54,    0,  175,    0,    0,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,   34,    0,    0,   34,
   34,   34,   34,   34,   34,    0,    0,    0,    0,   34,
   34,   34,   34,    0,   34,   34,   34,   80,    0,    0,
    0,    0,   28,    0,   80,   28,   28,   28,   28,   28,
   28,    0,    0,    0,    0,   28,   28,   28,   28,    0,
   28,   28,   28,    0,    0,    0,   33,    0,    0,   33,
   33,   33,   33,   33,   33,    0,    0,    0,    0,   33,
   33,   33,   33,   80,   33,   33,   33,    0,   30,    0,
    0,   30,   30,   30,   30,   30,   30,    0,    0,    0,
    0,   30,   30,   30,   30,    0,   30,   30,   30,   80,
    0,    0,   37,    0,    0,   37,   37,   37,   37,   37,
   37,    0,   80,    0,    0,   37,   37,   37,   37,    0,
   37,   37,   37,    1,    0,    0,    2,    0,   80,    3,
    4,    5,    0,    0,    0,    0,    6,    0,    7,    8,
    0,    9,   10,   11,    1,    0,    0,   74,    0,    0,
    3,    4,    5,    0,    0,    0,    0,    6,    0,   75,
    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   45,   43,   41,   45,   40,  123,   45,    0,   88,
  108,  123,   43,   72,   45,   40,   46,   40,   67,   60,
   49,   62,    0,   49,    7,   49,   61,   61,   49,   63,
   63,   40,  123,   45,   46,  160,   61,  135,   61,  261,
  262,   41,   42,   43,   44,   45,    0,   47,  107,   75,
  125,  176,   42,   82,   75,   38,   82,   47,   82,  257,
   60,   82,   62,   41,  112,   43,   44,   45,    0,  123,
   41,  150,  120,   44,  108,  108,  259,  275,  276,  277,
  257,   64,   60,   12,   62,   97,   15,   41,   44,   43,
   44,   45,    0,  142,  257,  121,   49,  121,  257,  133,
  121,  135,  135,  125,  116,   88,   60,  257,   62,   41,
  123,   43,   44,   45,    0,  163,   41,  257,   43,   44,
   45,  147,   75,  147,  264,  125,  147,  257,   60,   82,
   62,   44,  166,  125,  160,  275,  276,  277,   40,  160,
   43,   44,   45,  261,  262,  257,  158,  125,  260,    0,
  176,  263,  264,  265,  123,  176,  271,  274,  270,  125,
  272,  273,   59,  275,  276,  277,  257,  150,  121,  260,
   44,  125,  263,  278,  265,    0,   43,   44,   45,   93,
   94,  272,  257,   98,   99,  260,  261,  262,  263,  264,
  265,  258,   41,  125,  147,  270,  271,  272,  273,    0,
  275,  276,  277,  257,  257,  125,  260,  160,   40,  263,
   44,  265,  264,  257,  258,  257,   40,  125,  272,  257,
  258,  125,  257,  176,  123,  266,  267,  268,  269,  257,
   44,  125,  125,  275,  276,  277,   44,  257,  257,  125,
  260,  260,   44,  263,  263,  265,  265,   44,   41,   41,
   41,   40,  272,  272,  271,  125,   44,  257,   44,   41,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,  125,  257,
   44,   44,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   41,  275,  276,  277,
  125,  262,   44,  257,   44,   44,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
  262,  275,  276,  277,  125,  257,   44,    0,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,    0,  275,  276,  277,  125,   44,  125,  257,
  257,  125,  260,  261,  262,  263,  264,  265,   41,  125,
   41,   41,  270,  271,  272,  273,  125,  275,  276,  277,
  125,  257,   41,   82,  260,  261,  262,  263,  264,  265,
   64,   31,  147,   61,  270,  271,  272,  273,  142,  275,
  276,  277,   -1,  166,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   49,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   75,   -1,   -1,
   -1,   -1,  257,   -1,   82,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  121,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,  147,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,  160,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,  257,   -1,   -1,  260,   -1,  176,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,
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
"sentencias : sentencia sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : sentencia_asignacion",
"sentencia_ejecutable : sentencia_invocacion_funcion",
"sentencia_ejecutable : sentencia_imprimir",
"sentencia_ejecutable : sentencia_seleccion",
"sentencia_ejecutable : sentencia_iterativa_do_while",
"sentencia_ejecutable : sentencia_return",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencia_funcion sentencias_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : sentencia_asignacion",
"sentencia_ejecutable_funcion : sentencia_invocacion_funcion",
"sentencia_ejecutable_funcion : sentencia_imprimir",
"sentencia_ejecutable_funcion : sentencia_seleccion_funcion",
"sentencia_ejecutable_funcion : sentencia_iterativa_do_while_funcion",
"sentencia_ejecutable_funcion : sentencia_return",
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
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF",
"bloque_sentencias_ejecutables : sentencia_ejecutable",
"bloque_sentencias_ejecutables : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable sentencias_ejecutables",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion sentencias_ejecutables_funcion",
"sentencia_imprimir : PRINT CADENA ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' ')' ','",
"sentencia_invocacion_funcion : sentencia_objeto_identificador '(' expresion ',' lista_expresiones_invocacion_funcion_exceso ')' ','",
"lista_expresiones_invocacion_funcion_exceso : expresion",
"lista_expresiones_invocacion_funcion_exceso : expresion ',' expresion",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion ','",
"sentencia_asignacion : sentencia_objeto_identificador '=' expresion",
"sentencia_objeto_identificador : ID",
"sentencia_objeto_identificador : ID '.' sentencia_objeto_identificador",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_clase",
"sentencia_declarativa : declaracion_interfaz",
"declaracion_variable : tipo lista_de_variables ','",
"declaracion_interfaz : INTERFACE ID '{' bloque_encabezado_funcion '}'",
"bloque_encabezado_funcion : encabezado_funcion ','",
"bloque_encabezado_funcion : encabezado_funcion ',' bloque_encabezado_funcion",
"sentencia_declarativa_clase : tipo lista_de_variables ','",
"sentencia_declarativa_clase : declaracion_funcion",
"sentencia_declarativa_clase : ID ','",
"declaracion_clase : CLASS ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_sentencias_declarativas_clase '}'",
"bloque_sentencias_declarativas_clase : sentencia_declarativa_clase",
"bloque_sentencias_declarativas_clase : sentencia_declarativa_clase bloque_sentencias_declarativas_clase",
"declaracion_funcion : encabezado_funcion cuerpo_funcion",
"encabezado_funcion : VOID ID '(' parametro_funcion ')'",
"encabezado_funcion : VOID ID '(' ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')'",
"cuerpo_funcion : '{' sentencias_funcion '}'",
"lista_parametros_funcion_exceso : parametro_funcion",
"lista_parametros_funcion_exceso : parametro_funcion ',' lista_parametros_funcion_exceso",
"parametro_funcion : tipo ID",
"lista_de_variables : ID ';' lista_de_variables",
"lista_de_variables : ID",
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

//#line 244 ".\gramatica.y"

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
	        
	        String printTs = ts.print();
	        
	        out.saveFile("codigo-lexico.txt", logger.getLexico());
			out.saveFile("codigo-sintetico.txt", logger.getSintactico());
			out.saveFile("tabla-de-simbolos.txt", printTs);
		}
	}
}
//#line 589 "Parser.java"
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
//#line 18 ".\gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' al principio del programa"); }
break;
case 3:
//#line 20 ".\gramatica.y"
{ logger.logError("[Parser] Falta simbolo '}' al final del programa"); }
break;
case 4:
//#line 21 ".\gramatica.y"
{ logger.logError("[Parser] Programa vacio"); }
break;
case 14:
//#line 40 ".\gramatica.y"
{ logger.logError("[Parser] Sentencia RETURN fuera de funcion"); }
break;
case 26:
//#line 64 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego del RETURN"); }
break;
case 27:
//#line 68 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 28:
//#line 69 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 29:
//#line 73 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 30:
//#line 74 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 31:
//#line 78 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 32:
//#line 79 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 33:
//#line 80 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 34:
//#line 81 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 35:
//#line 85 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 36:
//#line 86 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 37:
//#line 87 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 38:
//#line 88 ".\gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 47:
//#line 112 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia imprimir detectada"); }
break;
case 48:
//#line 116 ".\gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 49:
//#line 117 ".\gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 50:
//#line 118 ".\gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 53:
//#line 127 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 54:
//#line 128 ".\gramatica.y"
{ logger.logSuccess("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 61:
//#line 144 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 65:
//#line 157 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 68:
//#line 163 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 69:
//#line 164 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 72:
//#line 173 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 75:
//#line 179 ".\gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 102:
//#line 239 ".\gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 103:
//#line 240 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 866 "Parser.java"
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
