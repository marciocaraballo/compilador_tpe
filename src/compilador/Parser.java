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
    8,    8,    8,   14,   14,   14,   14,   16,   16,   18,
   18,   18,   18,   19,   19,   20,   20,    7,    7,    6,
    6,    6,    6,    6,    6,   23,   23,    5,    5,   21,
   21,    3,    3,    3,    3,   24,   27,   30,   30,   32,
   32,   32,   26,   26,   33,   33,   25,   31,   31,   31,
   34,   34,   36,   36,   35,   29,   29,   28,   28,   28,
   28,   17,   37,   37,   37,   37,   37,   37,   22,   22,
   22,   38,   38,   38,   39,   39,   39,   40,   40,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    7,    6,    9,
    7,    8,    6,    9,    7,    8,    6,    1,    3,    1,
    1,    3,    4,    1,    2,    1,    2,    3,    2,    5,
    4,    7,    4,    3,    6,    1,    3,    4,    3,    1,
    3,    1,    1,    1,    1,    3,    5,    2,    3,    3,
    1,    2,    5,    7,    1,    2,    2,    5,    4,    7,
    4,    3,    1,    3,    2,    1,    3,    1,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   88,   89,
   90,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   62,   63,   64,   65,    0,    0,
    0,    0,    0,   24,    0,   60,    0,   38,    0,    0,
    0,    2,    6,    0,    0,    0,   86,    0,    0,   77,
    0,  108,    0,    0,    0,    0,  104,  107,   48,    0,
    0,    0,   44,    0,    0,    0,    1,    0,    0,    0,
   61,   66,    0,    0,    0,   17,   19,   20,   21,    0,
   15,   18,   22,   23,  106,  109,    0,   93,   94,   95,
   96,    0,    0,   97,   98,    0,    0,    0,   91,   79,
    0,    0,    0,    0,   71,    0,   75,    0,   39,   45,
    0,    0,    0,   51,    0,    0,   58,   87,    0,    0,
   41,   40,    0,   82,    0,   16,    0,    0,    0,    0,
  102,  103,   85,    0,   78,    0,   72,    0,   73,   76,
    0,   67,    0,    0,    0,   50,    0,   46,    0,    0,
   81,    0,    0,   83,    0,    0,   70,    0,   69,    0,
    0,    0,   42,    0,   47,    0,    0,   31,    0,   80,
   74,   26,    0,   52,    0,   43,    0,    0,   84,    0,
    0,    0,   30,    0,   35,   28,    0,   34,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   80,   81,  122,   83,   84,   39,   54,  123,   64,  149,
   24,   55,  145,   25,   26,   27,   28,   29,   48,  112,
   30,  107,  108,   50,  102,  155,   96,   56,   57,   58,
};
final static short yysindex[] = {                      -108,
    0,   -4, -177, -159,   57, -151,  -12, -149,    0,    0,
    0,  455,    0,  371,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -5,    0,    0,    0,    0, -136,    7,
  -43,   88,  104,    0, -116,    0, -138,    0, -125,   38,
  395,    0,    0,  -37,  -43, -103,    0,   13,  480,    0,
 -115,    0,  -88,  132,  -40,   18,    0,    0,    0,  -41,
  -83,  141,    0,  444,  135,  -87,    0,  138,   92,   51,
    0,    0,  -78,  140,  136,    0,    0,    0,    0,  420,
    0,    0,    0,    0,    0,    0,  -12,    0,    0,    0,
    0,  -43,  -43,    0,    0,  -43,  -43,  -43,    0,    0,
  -76,   -7,   70,  148,    0, -136,    0,  -45,    0,    0,
  -43,   79,  151,    0,  -43,  161,    0,    0,  -43,  137,
    0,    0,  -65,    0,   82,    0, -236,   18,   18,   31,
    0,    0,    0, -244,    0,  141,    0,   43,    0,    0,
  167,    0,  -87,   95,  168,    0,  169,    0,  445,  171,
    0,  -12,  173,    0,   14,  -20,    0,  180,    0,  -43,
  181,  136,    0,  108,    0,  -43,  -49,    0, -244,    0,
    0,    0,   31,    0, -158,    0,  197,  195,    0,  136,
  198,  199,    0,  -13,    0,    0,  203,    0,
};
final static short yyrindex[] = {                       241,
  -34,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  115,    0,    0,    0,    0,    0,    0,    0,    0,
  250,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  150,    0,  176,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   25,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  200,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   69,  211,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  129,  240,    0,    0,    0,    0,    0,    0,
    0,    0,  222,    0,    0,    0,    0,  246,    0,    0,
  279,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  256,    0,    0,    0,    0,  303,    0,    0,
  -74,  325,    0,    0,    0,    0,  349,    0,
};
final static short yygindex[] = {                         0,
  290,   -3,  -30,   33,  -21,    4,  574,    0,    0,   -2,
    0,  225,  -31,    0,    0,  -77,  -95, -141,    0,    0,
    0,  -15,    0,    0,   15,    0,    0,  322,  215,  163,
  -57,  -94,  191,    0, -117,    0,    0,   24,   45,    0,
};
final static int YYTABLESIZE=757;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        100,
  105,   53,   92,   68,   93,   60,   62,   53,  113,  127,
   43,   60,   99,  140,   12,  141,  154,   82,   76,   95,
  175,   94,  101,  147,  152,  153,   60,   77,   69,   70,
    9,   10,   11,  135,   44,   31,  134,   43,  184,   38,
   46,  105,  105,  105,  105,  105,   99,  105,   82,   76,
   37,  179,   78,   77,  170,   45,   72,  169,   77,   97,
  105,  140,  105,  101,   98,  101,  101,  101,  100,   63,
  177,   73,  121,   92,  167,   93,  105,  125,   78,  139,
  130,   32,  101,   78,  101,  113,  157,   99,  148,   99,
   99,   99,   25,   92,  117,   93,  110,   33,   77,  144,
   34,   73,  180,  181,  171,   35,   99,   40,   99,  100,
   37,  100,  100,  100,   49,  128,  129,  165,   36,   38,
   47,    2,  105,   78,    3,  105,    5,   77,  100,   49,
  100,   59,  116,    7,   92,  115,   93,   92,  160,   93,
   77,  131,  132,   60,  173,   65,  164,  101,    1,   54,
  105,    2,   78,   71,    3,    4,    5,   61,   77,  121,
   66,    6,   85,    7,    8,   78,    9,   10,   11,   86,
  105,   99,   87,  103,  111,   59,    4,  121,  118,  119,
  133,  114,   37,   78,   38,   37,   37,   37,   37,   37,
   37,  137,  136,  100,  143,   37,   37,   37,   37,   53,
   37,   37,   37,  142,  146,  150,  151,  158,  161,  162,
  166,  104,  178,   51,   52,   99,  168,   25,    4,   51,
   52,   33,   91,  172,  174,   88,   89,   90,   91,    9,
   10,   11,  176,    9,   10,   11,  104,  182,  183,   49,
    4,  185,  186,    4,   36,   27,  188,    2,  187,    3,
    3,   92,    5,   68,    9,   10,   11,  105,  120,    7,
  105,  105,  105,  105,  105,  105,  105,  105,  105,  105,
  105,  105,  105,  105,   54,  105,  105,  105,   55,  101,
   56,   91,  101,  101,  101,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,  101,   57,  101,  101,  101,
   59,   41,   32,   99,  126,  159,   99,   99,   99,   99,
   99,   99,   99,   99,   99,   99,   99,   99,   99,   99,
  138,   99,   99,   99,   53,  100,  156,    0,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,
  100,  100,    0,  100,  100,  100,   33,    0,    0,   25,
    0,    0,   25,   25,   25,   25,   25,   25,    0,    0,
    0,    0,   25,   25,   25,   25,    0,   25,   25,   25,
   27,   49,    0,    0,   49,   49,   49,   49,   49,   49,
    0,  101,    0,  106,   49,   49,   49,   49,    0,   49,
   49,   49,   36,   36,    0,   74,   74,  104,    3,    3,
    5,    0,    0,   55,    4,    0,   54,   75,   75,   54,
   54,   54,   54,   54,   54,    9,   10,   11,    0,   54,
   54,   54,   54,    0,   54,   54,   54,   32,    0,  106,
    0,    0,   59,    0,    0,   59,   59,   59,   59,   59,
   59,    0,    0,    0,    0,   59,   59,   59,   59,   29,
   59,   59,   59,    0,    0,  101,   53,  106,    0,   53,
   53,   53,   53,   53,   53,    0,    0,    0,    0,   53,
   53,   53,   53,   36,   53,   53,   53,  106,   33,    0,
    0,   33,   33,   33,   33,   33,   33,    0,    0,    0,
  101,   33,   33,   33,   33,   42,   33,   33,   33,    0,
    0,    0,   27,    0,    0,   27,   27,   27,   27,   27,
   27,    0,    0,    0,    0,   27,   27,   27,   27,   67,
   27,   27,   27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   55,    0,    0,   55,   55,
   55,   55,   55,   55,  124,    0,    0,    0,   55,   55,
   55,   55,    0,   55,   55,   55,    0,    0,    0,   32,
    0,    0,   32,   32,   32,   32,   32,   32,  109,  163,
    0,    0,   32,   32,   32,   32,    0,   32,   32,   32,
    0,   29,    0,    0,   29,   29,   29,   29,   29,   29,
    0,    0,    0,    0,   29,   29,   29,   29,    0,   29,
   29,   29,    0,    0,    0,   36,    0,    0,   36,   36,
   36,   36,   36,   36,    0,    0,    0,    0,   36,   36,
   36,   36,   79,   36,   36,   36,    0,    1,    0,    0,
    2,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,    7,    8,    0,    9,   10,   11,   79,    0,
    0,    1,    0,   79,    2,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,    0,    7,    8,    0,    9,
   10,   11,    0,    0,    0,    0,    1,    0,    0,   74,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    6,
    0,   75,    8,   79,    9,   10,   11,    0,    0,    0,
   36,   36,    0,    2,   74,    0,    3,    3,    5,    5,
    0,    1,    0,    0,    2,    7,   75,    3,    4,    5,
    0,    0,   79,    0,    6,    0,    7,    8,    0,    9,
   10,   11,    0,    0,    0,   79,    1,    0,    0,   74,
    0,    0,    3,    4,    0,    0,    0,    0,    0,    6,
    0,   75,    8,   79,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   45,   43,   41,   45,   40,  123,   45,   66,   87,
   14,   46,  257,  108,  123,  111,  134,   49,   49,   60,
  162,   62,    0,  119,  261,  262,   61,   49,   44,   45,
  275,  276,  277,   41,   40,   40,   44,   41,  180,    7,
   46,   41,   42,   43,   44,   45,    0,   47,   80,   80,
  125,  169,   49,   75,   41,   61,   44,   44,   80,   42,
   60,  156,   62,   41,   47,   43,   44,   45,    0,   37,
  166,   59,   75,   43,  152,   45,   62,   80,   75,  125,
   96,  259,   60,   80,   62,  143,   44,   41,  120,   43,
   44,   45,    0,   43,   44,   45,   64,  257,  120,  115,
   44,   59,  261,  262,  125,  257,   60,  257,   62,   41,
  123,   43,   44,   45,    0,   92,   93,  149,  257,   87,
  257,  260,  108,  120,  263,  125,  265,  149,   60,  123,
   62,   44,   41,  272,   43,   44,   45,   43,   44,   45,
  162,   97,   98,   40,  160,  271,  149,  125,  257,    0,
  136,  260,  149,  257,  263,  264,  265,  274,  180,  162,
  123,  270,  278,  272,  273,  162,  275,  276,  277,  258,
  156,  125,   41,  257,   40,    0,  264,  180,  257,   40,
  257,   44,  257,  180,  152,  260,  261,  262,  263,  264,
  265,   44,  123,  125,   44,  270,  271,  272,  273,    0,
  275,  276,  277,  125,   44,  271,  125,   41,   41,   41,
   40,  257,  262,  257,  258,  257,   44,  125,  264,  257,
  258,    0,  257,   44,   44,  266,  267,  268,  269,  275,
  276,  277,  125,  275,  276,  277,  257,   41,   44,  125,
    0,   44,   44,  264,  257,    0,   44,  260,  262,    0,
  263,   41,  265,  125,  275,  276,  277,  257,  123,  272,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
   41,  257,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   41,  275,  276,  277,
  125,   12,    0,  257,   80,  143,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
  106,  275,  276,  277,  125,  257,  136,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
  125,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   60,   -1,   62,  270,  271,  272,  273,   -1,  275,
  276,  277,  257,  257,   -1,  260,  260,  257,  263,  263,
  265,   -1,   -1,  125,  264,   -1,  257,  272,  272,  260,
  261,  262,  263,  264,  265,  275,  276,  277,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,  108,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,  134,  257,  136,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  125,  275,  276,  277,  156,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
  169,  270,  271,  272,  273,  125,  275,  276,  277,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  125,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,  125,  125,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,   49,  275,  276,  277,   -1,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,   75,   -1,
   -1,  257,   -1,   80,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,  120,  275,  276,  277,   -1,   -1,   -1,
  257,  257,   -1,  260,  260,   -1,  263,  263,  265,  265,
   -1,  257,   -1,   -1,  260,  272,  272,  263,  264,  265,
   -1,   -1,  149,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,  162,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,  180,  275,  276,  277,
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
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF ','",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ELSE bloque_sentencias_ejecutables_funcion ENDIF",
"sentencia_seleccion_funcion : IF '(' condicion ')' bloque_sentencias_ejecutables_funcion ENDIF",
"bloque_sentencias_ejecutables : sentencia_ejecutable",
"bloque_sentencias_ejecutables : '{' sentencias_ejecutables '}'",
"bloque_sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"bloque_sentencias_ejecutables_funcion : sentencia_return",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion '}'",
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"sentencia_imprimir : PRINT CADENA ','",
"sentencia_imprimir : PRINT CADENA",
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
"sentencia_objeto_identificador : ID",
"sentencia_objeto_identificador : sentencia_objeto_identificador '.' ID",
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
"bloque_sentencias_declarativas_clase : bloque_sentencias_declarativas_clase sentencia_declarativa_clase",
"declaracion_funcion : encabezado_funcion cuerpo_funcion",
"encabezado_funcion : VOID ID '(' parametro_funcion ')'",
"encabezado_funcion : VOID ID '(' ')'",
"encabezado_funcion : VOID ID '(' parametro_funcion ',' lista_parametros_funcion_exceso ')'",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return '}'",
"cuerpo_funcion : '{' sentencias_funcion '}'",
"lista_parametros_funcion_exceso : parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso ',' parametro_funcion",
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

//#line 251 "./src/compilador/gramatica.y"

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
//#line 631 "Parser.java"
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
//#line 84 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 35:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 36:
//#line 86 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 37:
//#line 87 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 48:
//#line 113 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 49:
//#line 114 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 50:
//#line 118 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 51:
//#line 119 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 52:
//#line 120 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 53:
//#line 122 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 54:
//#line 123 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 55:
//#line 124 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 58:
//#line 133 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 59:
//#line 134 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 66:
//#line 150 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 70:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 73:
//#line 169 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 74:
//#line 170 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 77:
//#line 179 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 80:
//#line 185 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 82:
//#line 190 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 108:
//#line 246 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 109:
//#line 247 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 928 "Parser.java"
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
