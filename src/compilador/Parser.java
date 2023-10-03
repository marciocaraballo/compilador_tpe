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
   18,   18,   18,   18,   21,   21,   22,   22,   19,   19,
   20,   20,    7,    7,    7,    7,    6,    6,    6,    6,
    6,    6,   25,   25,    5,    5,    5,   23,   23,    3,
    3,    3,    3,   26,   26,   26,   29,   32,   32,   34,
   34,   34,   28,   28,   35,   35,   27,   33,   33,   33,
   36,   36,   36,   39,   39,   40,   40,   40,   38,   38,
   37,   31,   31,   30,   30,   30,   30,   17,   41,   41,
   41,   41,   41,   41,   24,   24,   24,   42,   42,   42,
   43,   43,   43,   44,   44,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    7,    6,    9,
    7,    8,    6,    9,    7,    8,    6,    1,    3,    1,
    1,    3,    4,    5,    1,    2,    1,    1,    1,    2,
    1,    2,    3,    2,    2,    3,    5,    4,    7,    4,
    3,    6,    1,    3,    4,    3,    3,    1,    3,    1,
    1,    1,    1,    3,    2,    2,    5,    2,    3,    3,
    1,    2,    5,    7,    1,    2,    2,    5,    4,    7,
    4,    3,    5,    1,    2,    1,    1,    1,    1,    3,
    2,    1,    3,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  104,  105,
  106,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   70,   71,   72,   73,    0,    0,
    0,    0,    0,   55,    0,   24,    0,   68,    0,   38,
    0,    0,    0,    2,    6,    0,    0,    0,  102,   76,
    0,    0,   87,    0,  124,    0,    0,    0,    0,  120,
  123,   56,   53,    0,    0,    0,   49,    0,    0,    0,
    1,    0,    0,   67,    0,   69,   74,    0,    0,    0,
   17,   19,   20,   21,    0,   15,   18,   22,   23,  122,
  125,    0,  109,  110,  111,  112,    0,    0,  113,  114,
    0,    0,    0,  107,   89,    0,    0,    0,    0,   81,
    0,   85,    0,   39,   50,    0,    0,    0,   58,    0,
    0,   65,  103,    0,    0,   41,   40,    0,   92,    0,
   16,    0,    0,    0,    0,  118,  119,  101,    0,   88,
    0,   82,    0,   83,   86,    0,   77,    0,    0,    0,
   57,    0,   51,    0,    0,   91,   96,   97,   98,    0,
   94,    0,    0,   99,    0,    0,   80,    0,   79,    0,
    0,    0,   42,    0,   52,    0,   93,   95,    0,   31,
    0,   90,   84,   26,    0,   59,    0,   43,   47,   48,
    0,   45,    0,    0,  100,    0,    0,   44,   46,    0,
   30,    0,   35,   28,    0,   34,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   82,   83,   84,   21,   22,   23,
   85,   86,  127,   88,   89,   41,   57,  128,   68,  154,
  191,  192,   24,   58,  150,   25,   26,   27,   28,   29,
   51,  117,   30,  112,  113,   53,  107,  165,  160,  161,
  101,   59,   60,   61,
};
final static short yysindex[] = {                      -102,
    0,   -2,  -42, -226,   13, -192,  -67, -153,    0,    0,
    0,  594,    0,  444,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -10,    0,    0,    0,    0,  -38,  -28,
  -19,   67,   76,    0,   97,    0, -109,    0, -119,    0,
 -141,   20,  465,    0,    0,  -34,  -27, -112,    0,    0,
   -9,  624,    0, -129,    0, -107,  125,  -40,   12,    0,
    0,    0,    0,  -41,  -90, -159,    0,  550,  137,  -86,
    0,  136,   78,    0,   90,    0,    0,  -75,  148,  -15,
    0,    0,    0,    0,  486,    0,    0,    0,    0,    0,
    0,  -67,    0,    0,    0,    0,  -19,  -19,    0,    0,
  -19,  -19,  -19,    0,    0,  -68,   34,   72,  153,    0,
  -56,    0,  -73,    0,    0,  -19,   81,  163,    0,  -19,
  165,    0,    0,  -19, -223,    0,    0,  -58,    0,  507,
    0, -182,   12,   12,   41,    0,    0,    0, -204,    0,
 -159,    0,   18,    0,    0,  179,    0,  -86,  142,  184,
    0,  192,    0,  551,  197,    0,    0,    0,    0,  528,
    0,  -67,  199,    0,   98,  -65,    0,  203,    0,  -19,
  205,  -15,    0,  561,    0,  -19,    0,    0,  -21,    0,
 -204,    0,    0,    0,   41,    0, -162,    0,    0,    0,
  574,    0,  211,  209,    0,  -15,  210,    0,    0,  212,
    0,   -7,    0,    0,  215,    0,
};
final static short yyrindex[] = {                       260,
  -36,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  281,    0,    0,    0,    0,    0,    0,    0,
  343,    0,    0,    1,    0,    0,    0,    0,   23,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  150,    0,    0,  176,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  200,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,   69,  256,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  180,  265,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  222,    0,    0,    0,    0,  246,    0,    0,
  279,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  280,    0,    0,    0,    0,    0,
    0,    0,    0,  303,    0,    0,  366,    0,    0,  395,
    0,    0,    0,    0,  420,    0,
};
final static short yygindex[] = {                         0,
  315,   15,   84,    2,   89,  140,  582,    0,    0,  689,
    0,  243,  702,    0,    0,  -80, -100, -157,    0,    0,
    0,  157,    0,  -14,    0,    0,  -39,    0,    0,  522,
  238,  204,  -51,  -85,  218,    0, -126,    0,    0,  191,
    0,   27,   57,    0,
};
final static int YYTABLESIZE=901;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
  121,   34,   97,   68,   98,   50,   72,  107,   40,   68,
   56,  132,  164,   66,  187,  146,   74,   56,  118,  100,
   12,   99,  117,  152,   68,   56,  110,  145,   45,   46,
   35,   73,   75,   38,   77,   48,   79,   31,  202,    3,
   67,  121,  121,  121,  121,  121,  115,  121,   80,   78,
   47,  144,  104,  102,  195,   39,   36,   45,  103,  183,
  121,  167,  121,  117,   37,  117,  117,  117,  116,  115,
    9,   10,   11,  110,  140,  193,   78,  139,  162,  163,
  145,  179,  117,   97,  117,   98,  135,  115,   18,  115,
  115,  115,   25,   40,   52,   18,  118,  109,  196,  197,
   18,  110,   18,   42,    4,  149,  115,  125,  115,  116,
   62,  116,  116,  116,   54,    9,   10,   11,  121,   63,
   97,  120,   98,  133,  134,  121,  110,   18,  116,   69,
  116,   18,   97,  122,   98,   81,   64,   38,  182,   19,
    2,  181,   70,    3,   76,    5,   19,  117,   90,   61,
   91,   19,    7,   19,    1,  185,   18,    2,  136,  137,
    3,    4,    5,   40,   65,   92,  108,    6,   81,    7,
    8,  115,    9,   10,   11,   66,  116,    4,   19,  119,
   18,  123,   19,  109,   97,  170,   98,  124,  138,   38,
    4,  109,    2,  116,  141,    3,  142,    5,    4,   60,
   49,    9,   10,   11,    7,  147,  148,   19,  151,    9,
   10,   11,  155,  157,   32,  104,   33,   25,   49,  168,
  107,   33,   54,   55,  171,   93,   94,   95,   96,   54,
   55,   19,  172,    9,   10,   11,  176,   54,   55,   54,
  194,   38,  180,  157,   79,   27,  184,    3,  186,    5,
   18,  200,  201,  203,  205,  204,   80,  121,  206,    4,
  121,  121,  121,  121,  121,  121,  121,  121,  121,  121,
  121,  121,  121,  121,   61,  121,  121,  121,   62,  117,
    3,  107,  117,  117,  117,  117,  117,  117,  117,  117,
  117,  117,  117,  117,  117,  117,  108,  117,  117,  117,
   66,   19,   32,  115,   78,   63,  115,  115,  115,  115,
  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,
   64,  115,  115,  115,   60,  116,   43,  131,  116,  116,
  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,
  116,  116,   75,  116,  116,  116,   33,  199,  143,   25,
  178,  169,   25,   25,   25,   25,   25,   25,  166,    0,
    0,    0,   25,   25,   25,   25,    0,   25,   25,   25,
   27,   54,    0,    0,   54,   54,   54,   54,   54,   54,
    0,    0,    0,    0,   54,   54,   54,   54,    0,   54,
   54,   54,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,    0,    0,   61,    0,    0,   61,
   61,   61,   61,   61,   61,    0,    0,    0,    0,   61,
   61,   61,   61,    0,   61,   61,   61,   32,    0,    0,
    0,    0,   66,    0,    0,   66,   66,   66,   66,   66,
   66,    0,    0,    0,    0,   66,   66,   66,   66,    0,
   66,   66,   66,    0,    0,    0,   60,    0,    0,   60,
   60,   60,   60,   60,   60,    0,    0,   75,    0,   60,
   60,   60,   60,    0,   60,   60,   60,    0,   33,    0,
    0,   33,   33,   33,   33,   33,   33,    0,    0,    0,
   37,   33,   33,   33,   33,    0,   33,   33,   33,    0,
    0,    0,   27,    0,    0,   27,   27,   27,   27,   27,
   27,    0,    0,    0,    0,   27,   27,   27,   27,   29,
   27,   27,   27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,    0,    0,   62,   62,
   62,   62,   62,   62,   36,    0,    0,    0,   62,   62,
   62,   62,    0,   62,   62,   62,    0,    0,    0,   32,
    0,    0,   32,   32,   32,   32,   32,   32,   44,    0,
    0,    0,   32,   32,   32,   32,    0,   32,   32,   32,
    0,   20,    0,    0,    0,  106,    0,  111,   20,   71,
    0,    0,    0,   20,    0,   20,    0,    0,    0,   75,
    0,    0,   75,    0,    0,   75,   75,   75,    0,    0,
  129,    0,   75,    0,   75,   75,    0,   75,   75,   75,
   20,    0,   37,    0,   20,   37,   37,   37,   37,   37,
   37,  156,    0,    0,  111,   37,   37,   37,   37,    0,
   37,   37,   37,    0,    0,    0,    0,    0,    0,   20,
    0,   29,  177,    0,   29,   29,   29,   29,   29,   29,
  106,    0,  111,    0,   29,   29,   29,   29,    0,   29,
   29,   29,    0,   20,  114,  173,   36,    0,    0,   36,
   36,   36,   36,   36,   36,  188,    0,  111,    0,   36,
   36,   36,   36,    0,   36,   36,   36,    0,  198,    0,
    1,    0,  106,    2,    0,    0,    3,    4,    5,    0,
    0,    0,    0,    6,    0,    7,    8,    0,    9,   10,
   11,    1,    0,    0,    2,    0,    0,    3,    4,    5,
    0,    0,    0,    0,    6,    0,    7,    8,    0,    9,
   10,   11,    1,   20,    0,   79,    0,    0,    3,    4,
    5,    0,    0,   87,    0,    6,    0,   80,    8,    0,
    9,   10,   11,    1,    0,    0,   79,    0,  126,    3,
    4,    5,    0,  130,    0,    0,    6,    0,   80,    8,
    0,    9,   10,   11,    1,    0,   87,   79,    0,    0,
    3,    4,    5,    0,    0,    0,    0,    6,    0,   80,
    8,    0,    9,   10,   11,    0,   38,   38,    0,    2,
   79,    0,    3,    3,    5,    5,    0,   38,  158,    0,
   79,    7,   80,    3,    0,    5,  153,    0,    0,    0,
   38,  159,   80,   79,    0,    0,    3,    0,    5,    0,
    0,    0,  174,    0,    0,   80,    0,    0,  158,    0,
    1,    0,    0,    2,    0,  175,    3,    4,    5,    0,
  126,  159,  189,    6,    0,    7,    8,    0,    9,   10,
   11,    0,    0,    0,    0,  190,    0,    0,    0,  189,
    1,    0,    0,   79,  126,    0,    3,    4,    0,    0,
    0,    0,  190,    6,    0,   80,    8,    0,    9,   10,
   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   44,   43,   40,   45,   44,   41,   44,    7,   46,
   45,   92,  139,  123,  172,  116,   44,   45,   70,   60,
  123,   62,    0,  124,   61,   45,   66,  113,   14,   40,
  257,   46,   47,  257,   44,   46,  260,   40,  196,  263,
   39,   41,   42,   43,   44,   45,    0,   47,  272,   59,
   61,  125,  257,   42,  181,  123,   44,   43,   47,  125,
   60,   44,   62,   41,  257,   43,   44,   45,    0,   68,
  275,  276,  277,  113,   41,  176,   59,   44,  261,  262,
  166,  162,   60,   43,   62,   45,  101,   41,    0,   43,
   44,   45,    0,   92,  123,    7,  148,  257,  261,  262,
   12,  141,   14,  257,  264,  120,   60,  123,   62,   41,
   44,   43,   44,   45,    0,  275,  276,  277,   41,   44,
   43,   44,   45,   97,   98,  125,  166,   39,   60,  271,
   62,   43,   43,   44,   45,   52,   40,  257,   41,    0,
  260,   44,  123,  263,  257,  265,    7,  125,  278,    0,
  258,   12,  272,   14,  257,  170,   68,  260,  102,  103,
  263,  264,  265,  162,  274,   41,  257,  270,   85,  272,
  273,  125,  275,  276,  277,    0,   40,  264,   39,   44,
   92,  257,   43,  257,   43,   44,   45,   40,  257,  257,
  264,  257,  260,  125,  123,  263,   44,  265,  264,    0,
  257,  275,  276,  277,  272,  125,   44,   68,   44,  275,
  276,  277,  271,  130,  257,  257,  259,  125,  257,   41,
  257,    0,  257,  258,   41,  266,  267,  268,  269,  257,
  258,   92,   41,  275,  276,  277,   40,  257,  258,  125,
  262,  257,   44,  160,  260,    0,   44,  263,   44,  265,
  162,   41,   44,   44,  262,   44,  272,  257,   44,    0,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
    0,  257,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   41,  275,  276,  277,
  125,  162,    0,  257,  125,   41,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   41,  275,  276,  277,  125,  257,   12,   85,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,    0,  275,  276,  277,  125,  191,  111,  257,
  160,  148,  260,  261,  262,  263,  264,  265,  141,   -1,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
  125,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  125,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,  125,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
  125,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  125,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  125,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,  275,  276,  277,   -1,   -1,   -1,  257,
   -1,   -1,  260,  261,  262,  263,  264,  265,  125,   -1,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
   -1,    0,   -1,   -1,   -1,   64,   -1,   66,    7,  125,
   -1,   -1,   -1,   12,   -1,   14,   -1,   -1,   -1,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
  125,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
   39,   -1,  257,   -1,   43,  260,  261,  262,  263,  264,
  265,  125,   -1,   -1,  113,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   68,
   -1,  257,  125,   -1,  260,  261,  262,  263,  264,  265,
  139,   -1,  141,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,   92,  125,  125,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,  125,   -1,  166,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,  125,   -1,
  257,   -1,  181,  260,   -1,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,
  276,  277,  257,  162,   -1,  260,   -1,   -1,  263,  264,
  265,   -1,   -1,   52,   -1,  270,   -1,  272,  273,   -1,
  275,  276,  277,  257,   -1,   -1,  260,   -1,   80,  263,
  264,  265,   -1,   85,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,  257,   -1,   85,  260,   -1,   -1,
  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,  277,   -1,  257,  257,   -1,  260,
  260,   -1,  263,  263,  265,  265,   -1,  257,  130,   -1,
  260,  272,  272,  263,   -1,  265,  125,   -1,   -1,   -1,
  257,  130,  272,  260,   -1,   -1,  263,   -1,  265,   -1,
   -1,   -1,  154,   -1,   -1,  272,   -1,   -1,  160,   -1,
  257,   -1,   -1,  260,   -1,  154,  263,  264,  265,   -1,
  172,  160,  174,  270,   -1,  272,  273,   -1,  275,  276,
  277,   -1,   -1,   -1,   -1,  174,   -1,   -1,   -1,  191,
  257,   -1,   -1,  260,  196,   -1,  263,  264,   -1,   -1,
   -1,   -1,  191,  270,   -1,  272,  273,   -1,  275,  276,
  277,
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
"bloque_sentencias_ejecutables_funcion : '{' sentencias_ejecutables_funcion sentencia_return sentencias_ejecutables_funcion_inalcanzable '}'",
"sentencias_ejecutables_funcion_inalcanzable : sentencia_ejecutable_funcion_inalcanzable",
"sentencias_ejecutables_funcion_inalcanzable : sentencias_ejecutables_funcion_inalcanzable sentencia_ejecutable_funcion_inalcanzable",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_return",
"sentencia_ejecutable_funcion_inalcanzable : sentencia_ejecutable_funcion",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"sentencia_imprimir : PRINT CADENA ','",
"sentencia_imprimir : PRINT CADENA",
"sentencia_imprimir : PRINT ','",
"sentencia_imprimir : PRINT ID ','",
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
"sentencia_asignacion : sentencia_objeto_identificador '=' ','",
"sentencia_objeto_identificador : ID",
"sentencia_objeto_identificador : sentencia_objeto_identificador '.' ID",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_clase",
"sentencia_declarativa : declaracion_interfaz",
"declaracion_variable : tipo lista_de_variables ','",
"declaracion_variable : tipo lista_de_variables",
"declaracion_variable : tipo ','",
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

//#line 279 "./src/compilador/gramatica.y"

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
//#line 689 "Parser.java"
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
case 45:
//#line 104 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 46:
//#line 105 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 53:
//#line 124 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Sentencia PRINT detectada"); }
break;
case 54:
//#line 125 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Sentencia PRINT"); }
break;
case 55:
//#line 126 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta CADENA en Sentencia PRINT"); }
break;
case 56:
//#line 127 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Se esperaba una CADENA y se encontro un IDENTIFICADOR en sentencia PRINT"); }
break;
case 57:
//#line 131 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion con expresion detectada"); }
break;
case 58:
//#line 132 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Invocacion de funcion sin expresion detectada"); }
break;
case 59:
//#line 133 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Invocacion de funcion con multiples expresiones detectada, se preserva solo la primera expresion"); }
break;
case 60:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 61:
//#line 136 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 62:
//#line 137 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 65:
//#line 146 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 66:
//#line 147 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 67:
//#line 148 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
break;
case 74:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 75:
//#line 165 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); }
break;
case 76:
//#line 166 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
break;
case 80:
//#line 179 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 83:
//#line 185 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 84:
//#line 186 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 87:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 90:
//#line 201 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 92:
//#line 206 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 94:
//#line 211 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 95:
//#line 212 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 124:
//#line 274 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 125:
//#line 275 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1022 "Parser.java"
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
