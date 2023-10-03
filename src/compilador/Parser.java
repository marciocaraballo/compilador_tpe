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
   34,   34,   34,   37,   37,   38,   38,   38,   36,   36,
   35,   29,   29,   28,   28,   28,   28,   17,   39,   39,
   39,   39,   39,   39,   22,   22,   22,   40,   40,   40,
   41,   41,   41,   42,   42,
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
    4,    3,    5,    1,    2,    1,    1,    1,    1,    3,
    2,    1,    3,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   94,   95,
   96,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   62,   63,   64,   65,    0,    0,
    0,    0,    0,   24,    0,   60,    0,   38,    0,    0,
    0,    2,    6,    0,    0,    0,   92,    0,    0,   77,
    0,  114,    0,    0,    0,    0,  110,  113,   48,    0,
    0,    0,   44,    0,    0,    0,    1,    0,    0,    0,
   61,   66,    0,    0,    0,   17,   19,   20,   21,    0,
   15,   18,   22,   23,  112,  115,    0,   99,  100,  101,
  102,    0,    0,  103,  104,    0,    0,    0,   97,   79,
    0,    0,    0,    0,   71,    0,   75,    0,   39,   45,
    0,    0,    0,   51,    0,    0,   58,   93,    0,    0,
   41,   40,    0,   82,    0,   16,    0,    0,    0,    0,
  108,  109,   91,    0,   78,    0,   72,    0,   73,   76,
    0,   67,    0,    0,    0,   50,    0,   46,    0,    0,
   81,   86,   87,   88,    0,   84,    0,    0,   89,    0,
    0,   70,    0,   69,    0,    0,    0,   42,    0,   47,
    0,   83,   85,    0,   31,    0,   80,   74,   26,    0,
   52,    0,   43,    0,    0,   90,    0,    0,    0,   30,
    0,   35,   28,    0,   34,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   80,   81,  122,   83,   84,   39,   54,  123,   64,  149,
   24,   55,  145,   25,   26,   27,   28,   29,   48,  112,
   30,  107,  108,   50,  102,  160,  155,  156,   96,   56,
   57,   58,
};
final static short yysindex[] = {                      -108,
    0,   20, -177, -161,   59, -129,  -12, -125,    0,    0,
    0,  511,    0,  371,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -11,    0,    0,    0,    0, -114,   18,
  -43,  100,  105,    0, -116,    0,  137,    0, -124,   36,
  395,    0,    0,  -37,  -43,  -97,    0,  -10,  541,    0,
 -117,    0,  -95,  125,  -40,   15,    0,    0,    0,  -41,
  -86, -140,    0,  490,  130,  -91,    0,  133,   54,  -13,
    0,    0,  -79,  139,  136,    0,    0,    0,    0,  420,
    0,    0,    0,    0,    0,    0,  -12,    0,    0,    0,
    0,  -43,  -43,    0,    0,  -43,  -43,  -43,    0,    0,
  -77,   12,   61,  138,    0, -114,    0,  -45,    0,    0,
  -43,   60,  148,    0,  -43,  151,    0,    0,  -43, -130,
    0,    0,  -67,    0,  444,    0, -185,   15,   15,   44,
    0,    0,    0, -239,    0, -140,    0,   -4,    0,    0,
  164,    0,  -91,   78,  165,    0,  166,    0,  491,  168,
    0,    0,    0,    0,  466,    0,  -12,  167,    0,   29,
  -20,    0,  173,    0,  -43,  180,  136,    0,   84,    0,
  -43,    0,    0,  -52,    0, -239,    0,    0,    0,   44,
    0, -143,    0,  184,  189,    0,  136,  194,  195,    0,
  -21,    0,    0,  198,    0,
};
final static short yyrindex[] = {                       243,
  -34,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  115,    0,    0,    0,    0,    0,    0,    0,    0,
  247,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  150,    0,  176,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -8,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  200,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   69,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  127,  213,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  222,    0,    0,
    0,    0,  246,    0,    0,  279,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  240,
    0,    0,    0,    0,  303,    0,    0,  -74,  325,    0,
    0,    0,    0,  349,    0,
};
final static short yygindex[] = {                         0,
  270,   11,  -39,   17,   26,  653,  657,    0,    0,  -47,
    0,  217,  569,    0,    0,  -78, -100, -128,    0,    0,
    0,  -31,    0,    0,  -36,    0,    0,  396,  196,  162,
  -49,  -87,  170,    0, -118,    0,    0,  172,    0,   46,
   56,    0,
};
final static int YYTABLESIZE=844;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        100,
  111,   53,   92,   68,   93,   60,   62,   53,  127,   76,
  141,   60,   69,   70,   12,  159,  113,   99,  147,   95,
  140,   94,  107,   38,   43,  105,   60,  121,   44,   92,
  117,   93,  125,   72,   46,    9,   10,   11,  182,  162,
   76,  111,  111,  111,  111,  111,  105,  111,   73,   45,
   37,   43,  135,   63,   73,  134,   97,  186,  191,   31,
  111,   98,  111,  107,  130,  107,  107,  107,  106,  177,
  184,  105,  176,  140,   77,  157,  158,  153,  174,  139,
  110,   32,  107,  144,  107,  152,   92,  105,   93,  105,
  105,  105,   25,  113,  116,   33,   92,  115,   93,  105,
   77,  169,   34,   38,  178,   77,  105,  153,  105,  106,
   37,  106,  106,  106,   49,  152,  104,  187,  188,  121,
   92,  165,   93,    4,  105,  111,   36,   35,  106,   74,
  106,   40,    3,  180,    9,   10,   11,  128,  129,  121,
   49,   75,   47,   59,   60,   77,   65,  107,    1,   54,
   77,    2,  131,  132,    3,    4,    5,   61,   66,   71,
   85,    6,   86,    7,    8,   87,    9,   10,   11,  111,
  103,  105,    4,   38,   77,   59,  114,  118,  119,  133,
   77,  137,   37,  136,  142,   37,   37,   37,   37,   37,
   37,  143,   77,  106,  146,   37,   37,   37,   37,   53,
   37,   37,   37,  150,  163,  166,  167,  171,  183,  185,
  175,  104,   77,   51,   52,   99,  179,   25,    4,   51,
   52,   33,   97,  181,  189,   88,   89,   90,   91,    9,
   10,   11,  190,    9,   10,   11,  104,  192,  193,   49,
  194,  195,    4,    4,   36,   27,    3,    2,   97,   98,
    3,   68,    5,   56,    9,   10,   11,  111,  120,    7,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  111,  111,  111,  111,   54,  111,  111,  111,   55,  107,
   57,   41,  107,  107,  107,  107,  107,  107,  107,  107,
  107,  107,  107,  107,  107,  107,  126,  107,  107,  107,
   59,  138,   32,  105,  164,  161,  105,  105,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,  105,  105,
    0,  105,  105,  105,   53,  106,  173,    0,  106,  106,
  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,
  106,  106,    0,  106,  106,  106,   33,    0,    0,   25,
    0,    0,   25,   25,   25,   25,   25,   25,    0,    0,
    0,    0,   25,   25,   25,   25,    0,   25,   25,   25,
   27,   49,    0,    0,   49,   49,   49,   49,   49,   49,
    0,    0,    0,    0,   49,   49,   49,   49,    0,   49,
   49,   49,   36,   36,    0,   74,    2,    0,    3,    3,
    5,    5,    0,   55,    0,    0,   54,   75,    7,   54,
   54,   54,   54,   54,   54,    0,    0,    0,    0,   54,
   54,   54,   54,    0,   54,   54,   54,   32,    0,    0,
    0,    0,   59,    0,    0,   59,   59,   59,   59,   59,
   59,    0,    0,    0,    0,   59,   59,   59,   59,   29,
   59,   59,   59,    0,    0,  101,   53,  106,    0,   53,
   53,   53,   53,   53,   53,    0,    0,    0,    0,   53,
   53,   53,   53,   36,   53,   53,   53,    0,   33,    0,
    0,   33,   33,   33,   33,   33,   33,    0,    0,    0,
    0,   33,   33,   33,   33,   42,   33,   33,   33,    0,
    0,    0,   27,  106,    0,   27,   27,   27,   27,   27,
   27,    0,    0,    0,    0,   27,   27,   27,   27,   67,
   27,   27,   27,    0,    0,    0,    0,    0,    0,  101,
    0,  106,    0,    0,    0,   55,    0,    0,   55,   55,
   55,   55,   55,   55,  124,    0,    0,    0,   55,   55,
   55,   55,    0,   55,   55,   55,  106,    0,    0,   32,
    0,    0,   32,   32,   32,   32,   32,   32,  151,    0,
    0,  101,   32,   32,   32,   32,    0,   32,   32,   32,
    0,   29,    0,    0,   29,   29,   29,   29,   29,   29,
  172,    0,    0,    0,   29,   29,   29,   29,    0,   29,
   29,   29,    0,    0,    0,   36,    0,    0,   36,   36,
   36,   36,   36,   36,  109,  168,    0,   82,   36,   36,
   36,   36,    0,   36,   36,   36,    0,    1,    0,    0,
    2,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,    7,    8,    0,    9,   10,   11,   82,    0,
    0,    1,    0,    0,    2,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,    0,    7,    8,    0,    9,
   10,   11,    0,    0,    0,    0,    1,    0,    0,   74,
    0,    0,    3,    4,    5,    0,    0,    0,  148,    6,
    0,   75,    8,  154,    9,   10,   11,    0,    0,    0,
    1,   78,    0,   74,    0,   79,    3,    4,    5,    0,
    0,    0,    0,    6,    0,   75,    8,  170,    9,   10,
   11,    0,    1,  154,    0,   74,    0,   78,    3,    4,
    5,   79,   78,    0,    0,    6,   79,   75,    8,    0,
    9,   10,   11,    0,    0,    0,   36,   36,    0,    2,
   74,    0,    3,    3,    5,    5,    0,    0,    0,    0,
    0,    7,   75,    0,    0,    0,    0,    1,    0,    0,
    2,    0,   78,    3,    4,    5,   79,   78,    0,    0,
    6,   79,    7,    8,    0,    9,   10,   11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
   74,   78,    0,    3,    4,   79,    0,   78,    0,    0,
    6,   79,   75,    8,    0,    9,   10,   11,    0,   78,
    0,    0,    0,   79,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   78,
    0,    0,    0,   79,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   45,   43,   41,   45,   40,  123,   45,   87,   49,
  111,   46,   44,   45,  123,  134,   66,  257,  119,   60,
  108,   62,    0,    7,   14,   62,   61,   75,   40,   43,
   44,   45,   80,   44,   46,  275,  276,  277,  167,   44,
   80,   41,   42,   43,   44,   45,    0,   47,   59,   61,
  125,   41,   41,   37,   59,   44,   42,  176,  187,   40,
   60,   47,   62,   41,   96,   43,   44,   45,    0,   41,
  171,  108,   44,  161,   49,  261,  262,  125,  157,  125,
   64,  259,   60,  115,   62,  125,   43,   41,   45,   43,
   44,   45,    0,  143,   41,  257,   43,   44,   45,  136,
   75,  149,   44,   87,  125,   80,   60,  155,   62,   41,
  123,   43,   44,   45,    0,  155,  257,  261,  262,  167,
   43,   44,   45,  264,  161,  125,  257,  257,   60,  260,
   62,  257,  263,  165,  275,  276,  277,   92,   93,  187,
  123,  272,  257,   44,   40,  120,  271,  125,  257,    0,
  125,  260,   97,   98,  263,  264,  265,  274,  123,  257,
  278,  270,  258,  272,  273,   41,  275,  276,  277,   40,
  257,  125,  264,  157,  149,    0,   44,  257,   40,  257,
  155,   44,  257,  123,  125,  260,  261,  262,  263,  264,
  265,   44,  167,  125,   44,  270,  271,  272,  273,    0,
  275,  276,  277,  271,   41,   41,   41,   40,  125,  262,
   44,  257,  187,  257,  258,  257,   44,  125,  264,  257,
  258,    0,  257,   44,   41,  266,  267,  268,  269,  275,
  276,  277,   44,  275,  276,  277,  257,   44,   44,  125,
  262,   44,    0,  264,  257,    0,    0,  260,  257,   41,
  263,  125,  265,   41,  275,  276,  277,  257,  123,  272,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
   41,   12,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   80,  275,  276,  277,
  125,  106,    0,  257,  143,  136,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   -1,  275,  276,  277,  125,  257,  155,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
  125,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,  257,  257,   -1,  260,  260,   -1,  263,  263,
  265,  265,   -1,  125,   -1,   -1,  257,  272,  272,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,   60,  257,   62,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  125,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,  125,  275,  276,  277,   -1,
   -1,   -1,  257,  108,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,  134,
   -1,  136,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  125,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,  161,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,  125,   -1,
   -1,  176,  270,  271,  272,  273,   -1,  275,  276,  277,
   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
  125,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  125,  125,   -1,   49,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,   80,   -1,
   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,  120,  270,
   -1,  272,  273,  125,  275,  276,  277,   -1,   -1,   -1,
  257,   49,   -1,  260,   -1,   49,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,  149,  275,  276,
  277,   -1,  257,  155,   -1,  260,   -1,   75,  263,  264,
  265,   75,   80,   -1,   -1,  270,   80,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,  257,  257,   -1,  260,
  260,   -1,  263,  263,  265,  265,   -1,   -1,   -1,   -1,
   -1,  272,  272,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,  120,  263,  264,  265,  120,  125,   -1,   -1,
  270,  125,  272,  273,   -1,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,  149,   -1,  263,  264,  149,   -1,  155,   -1,   -1,
  270,  155,  272,  273,   -1,  275,  276,  277,   -1,  167,
   -1,   -1,   -1,  167,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  187,
   -1,   -1,   -1,  187,
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
"cuerpo_funcion : '{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}'",
"sentencias_funcion_inalcanzable : sentencia_funcion_inalcanzable",
"sentencias_funcion_inalcanzable : sentencias_funcion_inalcanzable sentencia_funcion_inalcanzable",
"sentencia_funcion_inalcanzable : sentencia_declarativa",
"sentencia_funcion_inalcanzable : sentencia_return",
"sentencia_funcion_inalcanzable : sentencia_ejecutable_funcion",
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

//#line 263 "./src/compilador/gramatica.y"

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
//#line 662 "Parser.java"
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
case 84:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 85:
//#line 196 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 114:
//#line 258 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 115:
//#line 259 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 967 "Parser.java"
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
