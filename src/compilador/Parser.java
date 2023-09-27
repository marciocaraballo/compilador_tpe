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
   13,   13,   13,   13,   10,   10,    9,    9,   15,   15,
    8,    8,    8,    8,   14,   14,   14,   14,   16,   16,
   18,   18,   19,   19,   20,   20,    7,    7,    6,    6,
    6,    6,    6,    6,   23,   23,    5,    5,   21,   21,
    3,    3,    3,    3,   24,   27,   30,   30,   32,   32,
   32,   26,   26,   33,   33,   25,   31,   31,   31,   34,
   36,   36,   35,   29,   29,   28,   28,   28,   28,   17,
   37,   37,   37,   37,   37,   37,   22,   22,   22,   38,
   38,   38,   39,   39,   39,   40,   40,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    7,    6,    7,    6,
    9,    7,    8,    6,    9,    7,    8,    6,    1,    3,
    1,    3,    1,    2,    1,    2,    3,    2,    5,    4,
    7,    4,    3,    6,    1,    3,    4,    3,    1,    3,
    1,    1,    1,    1,    3,    5,    2,    3,    3,    1,
    2,    5,    7,    1,    2,    2,    5,    4,    7,    3,
    1,    3,    2,    3,    1,    1,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   87,
   88,    0,    0,    0,    0,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   61,   62,   63,   64,    0,    0,
    0,    0,    0,    0,   25,    0,    0,    0,   39,    0,
    0,    0,    2,    6,    0,    0,    0,    0,    0,   76,
   60,    0,  106,    0,    0,    0,    0,  102,  105,   47,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,   65,    0,    0,   17,   19,   20,   21,   24,
    0,    0,   18,   22,   23,  104,  107,    0,   91,   92,
   93,   94,    0,    0,   95,   96,    0,    0,    0,   89,
   78,    0,    0,    0,    0,   70,    0,    0,    0,   44,
   40,    0,    0,    0,   50,    0,    0,   57,   84,    0,
    0,   41,    0,   80,   16,    0,    0,    0,    0,  100,
  101,   83,    0,   77,    0,   71,    0,   75,   72,    0,
   66,    0,    0,    0,   49,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   69,    0,   68,    0,    0,    0,
   46,   42,    0,    0,   32,    0,   79,   73,   27,    0,
   51,    0,    0,    0,   82,    0,    0,    0,   31,    0,
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
    0,  370,    0,  -21,  370,    0,    0,    0,    0,    0,
    0,    0,    0,  -24,    0,    0,    0,    0, -149,  -12,
 -129,  -43,   88,   99,    0, -116,  -29,  -18,    0, -114,
   32,   35,    0,    0,  -37,  -43,  104,  127,  391,    0,
    0, -104,    0,  -66,  152,  -40,   11,    0,    0,    0,
  -41,  -52, -139,  -18,   81,  169,  -51,    0,  167,   76,
   98, -149,    0,  177,  -53,    0,    0,    0,    0,    0,
  100,  391,    0,    0,    0,    0,    0,  -90,    0,    0,
    0,    0,  -43,  -43,    0,    0,  -43,  -43,  -43,    0,
    0,  -27,   30,  108,  188,    0, -149, -139,  112,    0,
    0,  -43,  113,  189,    0,  -43,  197,    0,    0,  -43,
  136,    0,  -19,    0,    0, -221,   11,   11,  -30,    0,
    0,    0, -197,    0, -139,    0,  199,    0,    0,  203,
    0,  -51,  134,  207,    0,  208,  136,  125,  211,  -90,
  209,  212,  214,  132,    0,  215,    0,  -43,  216,  -53,
    0,    0,  -43,   19,    0, -197,    0,    0,    0,  -30,
    0, -117,  241,  253,    0,  -53,  258,  261,    0,   44,
    0,    0,  277,    0,
};
final static short yyrindex[] = {                       327,
  -34,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,    0,    0,  -22,    0,    0,    0,
    0,  328,    0,    0,    0,    0,  299,    0,    0,    0,
    0,    1,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,  -21,    0,    0,    0,    0,  150,    0,
  176,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  223,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   92,    0,    0,  226,    0,    0,
    0,    0,    0,    0,    0,    0,  200,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   69,  311,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  234,  319,    0,    0,    0,  236,    0,    0,    0,
  222,  321,    0,    0,    0,  246,    0,    0,  279,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  326,
    0,    0,    0,  303,    0,    0,  -74,  325,    0,    0,
    0,    0,  349,    0,
};
final static short yygindex[] = {                         0,
   72,    0,  -28,   18,  -25,  -20,   48,    0,    0,  456,
  291,    0,  -23,    0,    0,  -78,  -47, -124,  310,  235,
  350,  -11,    0,    0,  -32,    0,    0,  -33,  -58,  242,
  -48,    0,  -97,    0,  322,  228,    0,   87,   86,    0,
};
final static int YYTABLESIZE=668;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
  103,   54,   93,   69,   94,   59,   63,   54,    5,  126,
  138,   12,   93,  119,   94,   45,   31,   59,  114,   96,
   76,   95,   99,   77,   39,   83,   59,  102,   78,  107,
  106,   32,   38,   70,   71,  172,   46,  154,   59,  150,
  151,  103,  103,  103,  103,  103,   97,  103,  137,   77,
   38,  180,   98,   76,   78,   64,   77,   99,   83,  100,
  103,   78,  103,   99,  140,   99,   99,   99,   98,  121,
  134,  164,  146,  133,  107,  106,   33,    9,   10,   11,
   34,   64,   99,   42,   99,  129,   44,   97,   35,   97,
   97,   97,   26,  114,   36,   77,   79,  147,   41,  102,
   78,  107,  106,   43,  143,   39,   97,   47,   97,   98,
   49,   98,   98,   98,   48,  173,  117,  105,   93,  116,
   94,   77,   79,  147,    4,  103,   78,   37,   98,   79,
   98,   60,  102,    5,   77,    9,   10,   11,   61,   78,
   93,  118,   94,  176,  177,    1,  170,   99,    2,   53,
   77,    3,    4,    5,   67,   78,   66,   62,    6,   68,
    7,    8,   72,    9,   10,   11,   37,   39,   79,    2,
   73,   97,    3,   86,    5,   58,   93,  158,   94,  127,
  128,    7,   38,  130,  131,   38,   38,   38,   38,   38,
   38,   87,   88,   98,   79,   38,   38,   38,   38,   52,
   38,   38,   38,   37,  104,  111,   74,   79,  112,    3,
  115,    5,    4,   52,   53,  100,  120,   26,   75,   52,
   53,   34,   89,   79,  124,   89,   90,   91,   92,  132,
  135,  136,  142,    9,   10,   11,  139,  141,   37,   48,
  145,    2,  155,  156,    3,   28,    5,  159,  160,  162,
  163,  149,  165,    7,  167,  166,  168,  103,  169,  171,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  103,   53,  103,  103,  103,   54,   99,
  174,  178,   99,   99,   99,   99,   99,   99,   99,   99,
   99,   99,   99,   99,   99,   99,  179,   99,   99,   99,
   58,  181,   33,   97,  182,  183,   97,   97,   97,   97,
   97,   97,   97,   97,   97,   97,   97,   97,   97,   97,
  184,   97,   97,   97,   52,   98,    4,    3,   98,   98,
   98,   98,   98,   98,   98,   98,   98,   98,   98,   98,
   98,   98,   85,   98,   98,   98,   34,   15,   89,   26,
   74,   90,   26,   26,   26,   26,   26,   26,   67,   55,
   45,   81,   26,   26,   26,   26,   56,   26,   26,   26,
   28,   48,  125,  110,   48,   48,   48,   48,   48,   48,
   51,  161,  103,  157,   48,   48,   48,   48,    0,   48,
   48,   48,   37,  175,    0,   74,    0,    0,    3,    0,
    5,    0,    0,   54,    0,    0,   53,   75,    0,   53,
   53,   53,   53,   53,   53,    0,    0,    0,    0,   53,
   53,   53,   53,    0,   53,   53,   53,   33,    0,    0,
    0,    0,   58,    0,    0,   58,   58,   58,   58,   58,
   58,    0,    0,    0,    0,   58,   58,   58,   58,   30,
   58,   58,   58,    0,    0,    0,   52,    0,    0,   52,
   52,   52,   52,   52,   52,    0,    0,    0,    0,   52,
   52,   52,   52,   37,   52,   52,   52,    0,   34,    0,
    0,   34,   34,   34,   34,   34,   34,    0,    0,    0,
    0,   34,   34,   34,   34,    0,   34,   34,   34,    0,
    0,    0,   28,    0,   80,   28,   28,   28,   28,   28,
   28,    0,    0,    0,    0,   28,   28,   28,   28,    0,
   28,   28,   28,    0,    0,    0,    0,    0,    0,    0,
   80,    0,    0,    0,    0,   54,    0,   80,   54,   54,
   54,   54,   54,   54,    0,    0,    0,    0,   54,   54,
   54,   54,    0,   54,   54,   54,    0,    0,    0,   33,
    0,    0,   33,   33,   33,   33,   33,   33,    0,    0,
    0,    0,   33,   33,   33,   33,   80,   33,   33,   33,
    0,   30,    0,    0,   30,   30,   30,   30,   30,   30,
    0,    0,    0,    0,   30,   30,   30,   30,    0,   30,
   30,   30,   80,    0,    0,   37,    0,    0,   37,   37,
   37,   37,   37,   37,    0,   80,    0,    0,   37,   37,
   37,   37,    0,   37,   37,   37,    1,    0,    0,    2,
    0,   80,    3,    4,    5,    0,    0,    0,    0,    6,
    0,    7,    8,    0,    9,   10,   11,    1,    0,    0,
   74,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,   75,    8,    0,    9,   10,   11,
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
  258,    0,  257,  176,  125,  266,  267,  268,  269,  257,
  123,   44,   44,  275,  276,  277,  125,  125,  257,  125,
   44,  260,   44,   41,  263,    0,  265,   41,   41,  125,
   40,  271,   44,  272,   41,   44,  125,  257,   44,   44,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
  262,   41,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   44,  275,  276,  277,
  125,   44,    0,  257,   44,  262,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   44,  275,  276,  277,  125,  257,    0,    0,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,   44,  275,  276,  277,  125,  125,  257,  257,
  125,   41,  260,  261,  262,  263,  264,  265,  125,   41,
  125,   41,  270,  271,  272,  273,   41,  275,  276,  277,
  125,  257,   82,   64,  260,  261,  262,  263,  264,  265,
   31,  147,   61,  142,  270,  271,  272,  273,   -1,  275,
  276,  277,  257,  166,   -1,  260,   -1,   -1,  263,   -1,
  265,   -1,   -1,  125,   -1,   -1,  257,  272,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  125,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   -1,   -1,  257,   -1,   49,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   75,   -1,   -1,   -1,   -1,  257,   -1,   82,  260,  261,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,  121,  275,  276,  277,
   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,  147,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,   -1,  160,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,
   -1,  176,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,
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

//#line 249 "./src/compilador/gramatica.y"

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
//#line 611 "Parser.java"
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
case 26:
//#line 64 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego del RETURN"); }
break;
case 27:
//#line 68 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 28:
//#line 69 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 29:
//#line 73 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia iterativa DO WHILE detectada"); }
break;
case 30:
//#line 74 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia DO WHILE"); }
break;
case 31:
//#line 78 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 32:
//#line 79 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 33:
//#line 80 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 34:
//#line 81 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 35:
//#line 85 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF ELSE detectada"); }
break;
case 36:
//#line 86 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia seleccion IF sin ELSE detectada"); }
break;
case 37:
//#line 87 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF ELSE"); }
break;
case 38:
//#line 88 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' luego de sentencia IF sin ELSE"); }
break;
case 47:
//#line 112 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 48:
//#line 113 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 49:
//#line 117 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 50:
//#line 118 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 51:
//#line 119 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 52:
//#line 121 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 53:
//#line 122 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 54:
//#line 123 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 57:
//#line 132 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 58:
//#line 133 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 65:
//#line 149 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 69:
//#line 162 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 72:
//#line 168 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 73:
//#line 169 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 76:
//#line 178 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 79:
//#line 184 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 106:
//#line 244 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 107:
//#line 245 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 904 "Parser.java"
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
