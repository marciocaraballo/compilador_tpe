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
   33,   33,   33,   33,   33,   33,   36,   36,   36,   36,
   36,   36,   39,   39,   40,   40,   40,   38,   38,   38,
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
    6,    4,    3,    4,    3,    2,    4,    3,    3,    2,
    5,    4,    1,    2,    1,    1,    1,    1,    3,    2,
    2,    1,    3,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  114,  115,
  116,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   70,   71,   72,   73,    0,    0,
    0,    0,    0,   55,    0,    0,   24,    0,   68,    0,
   38,    0,    0,    0,    2,    6,    0,    0,    0,  112,
   76,    0,    0,   87,    0,  134,    0,    0,    0,    0,
  130,  133,   56,   53,  117,    0,   95,    0,    0,   93,
    0,    0,    0,   49,    0,    0,    0,    1,    0,    0,
   67,    0,   69,   74,    0,    0,    0,  100,   17,   19,
   20,   21,    0,    0,   15,   18,   22,   23,  132,  135,
    0,  119,  120,  121,  122,    0,    0,  123,  124,    0,
    0,    0,   89,    0,  111,   94,   92,    0,    0,   81,
    0,   85,    0,   39,   50,    0,    0,    0,   58,    0,
    0,   65,  113,    0,    0,   41,   40,    0,   98,  105,
  106,  107,    0,  103,   99,    0,   16,    0,    0,    0,
    0,  128,  129,    0,   88,  108,    0,    0,   82,    0,
   83,   86,    0,   77,    0,    0,    0,   57,    0,   51,
    0,    0,  102,  104,   97,    0,    0,    0,    0,    0,
   91,  110,    0,   80,    0,   79,    0,    0,    0,   42,
    0,   52,    0,  101,    0,   31,   90,  109,   84,   26,
    0,   59,    0,   43,   47,   48,    0,   45,    0,    0,
    0,    0,   44,   46,    0,   30,    0,   35,   28,    0,
   34,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   90,   91,   92,   21,   22,   23,
   94,   95,  142,   97,   98,   42,   58,  138,   75,  171,
  207,  208,   24,   59,  167,   25,   26,   27,   28,   29,
   52,  127,   30,  122,  123,   54,  156,  157,  143,  144,
  110,   60,   61,   62,
};
final static short yysindex[] = {                       365,
    0,   22,   -3,  -36,  -11, -170,  -64, -163,    0,    0,
    0,  722,    0,  501,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -10,    0,    0,    0,    0,   -7,  -21,
  -13,   83,   91,    0,  -38,  -41,    0, -114,    0,  339,
    0, -132,   21,  522,    0,    0,  -30,  -26, -106,    0,
    0,   -9,  543,    0, -123,    0,  -96,  122,  128,   39,
    0,    0,    0,    0,    0,  124,    0,  -93,  127,    0,
  129,  -83, -134,    0,  420,  140,  -82,    0,  137,   60,
    0,   35,    0,    0,  -71,  147,  -48,    0,    0,    0,
    0,    0,  564,  587,    0,    0,    0,    0,    0,    0,
  -64,    0,    0,    0,    0,  -13,  -13,    0,    0,  -13,
  -13,  -13,    0,  -34,    0,    0,    0,   68,  148,    0,
  -62,    0, -119,    0,    0,  -13,   73,  158,    0,  -13,
  159,    0,    0,  -13, -135,    0,    0,  -67,    0,    0,
    0,    0,  609,    0,    0,  634,    0, -208,   39,   39,
   63,    0,    0, -180,    0,    0,  -28, -134,    0,   30,
    0,    0,  164,    0,  -82,   89,  165,    0,  166,    0,
  677,  170,    0,    0,    0,  655,  -64,  167,  -24, -180,
    0,    0,  -98,    0,  169,    0,  -13,  182,  -48,    0,
  687,    0,  -13,    0,  -42,    0,    0,    0,    0,    0,
   63,    0, -144,    0,    0,    0,  697,    0,  173,  186,
  -48,  211,    0,    0,  213,    0,   19,    0,    0,  215,
    0,
};
final static short yyrindex[] = {                       260,
  -32,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,    0,  282,    0,    0,    0,    0,    0,    0,
    0,  343,    0,    0,    1,    0,    0,    0,    0,   23,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  150,    0,
    0,  176,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   45,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  200,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   47,   69,
  256,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  180,  265,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  222,    0,    0,
    0,    0,    0,    0,  246,    0,    0,  279,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  280,    0,    0,    0,    0,    0,    0,    0,    0,  303,
    0,  434,    0,    0,  456,    0,    0,    0,    0,  480,
    0,
};
final static short yygindex[] = {                         0,
  315,   11,  493,   -2,  513,  577,  583,    0,    0,  -22,
    0,  234,  475,    0,    0,  -79,  -74, -155,    0,    0,
    0,  141,    0,   10,    0,    0,  -47,    0,    0,  810,
  228,  187,  -49,  -99,  193,    0,    4,  205,  214,  -94,
    0,   40,   41,    0,
};
final static int YYTABLESIZE=999;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
  131,   66,   67,   36,   41,  161,  155,   68,   73,  154,
   79,  117,  181,   68,   57,  180,  197,   81,   57,  180,
   96,  148,  127,  162,   46,  120,  199,  128,   68,   47,
   93,   57,   37,  203,   84,   49,   51,   74,   69,   71,
   34,  131,  131,  131,  131,  131,  125,  131,  174,   85,
   48,  163,  177,  178,   46,  217,   80,   82,   40,  169,
  131,   31,  131,  127,  136,  127,  127,  127,  126,  114,
  141,  146,  125,  184,  135,  120,   65,  106,  132,  107,
  111,  174,  127,  162,  127,  112,   38,  125,   85,  125,
  125,  125,   25,   43,    9,   10,   11,  195,   41,   96,
  131,   53,  106,  130,  107,  106,  125,  107,  125,  126,
  120,  126,  126,  126,   54,  128,  211,  212,  209,  151,
  141,   39,  119,  141,   86,  131,   63,    3,  126,    4,
  126,  106,  187,  107,   64,  120,   87,  119,   76,  166,
    9,   10,   11,   77,    4,  149,  150,  127,  191,   61,
   83,  152,  153,  141,   99,    9,   10,   11,  119,   72,
  182,  100,  101,  115,  113,    4,  136,  116,  205,  117,
  106,  125,  107,  118,   41,   66,    9,   10,   11,  126,
  129,    4,  182,  198,  205,  133,  134,  109,  136,  108,
  158,  159,   39,  126,   50,    2,  201,  164,    3,   60,
    5,  165,  168,  172,  185,  188,  189,    7,   39,  193,
  196,   86,  200,  215,    3,   65,    5,   25,   65,  210,
   35,   33,   65,   87,  117,  202,   55,   56,   65,  216,
   55,   56,   65,    9,   10,   11,    9,   10,   11,   54,
    9,   10,   11,   55,   56,   27,    9,   10,   11,   50,
    9,   10,   11,   32,  218,   33,  219,  131,  221,    4,
  131,  131,  131,  131,  131,  131,  131,  131,  131,  131,
  131,  131,  131,  131,   61,  131,  131,  131,   62,  127,
  220,    3,  127,  127,  127,  127,  127,  127,  127,  127,
  127,  127,  127,  127,  127,  127,  118,  127,  127,  127,
   66,  117,   32,  125,   78,   63,  125,  125,  125,  125,
  125,  125,  125,  125,  125,  125,  125,  125,  125,  125,
   64,  125,  125,  125,   60,  126,   44,  147,  126,  126,
  126,  126,  126,  126,  126,  126,  126,  126,  126,  126,
  126,  126,   75,  126,  126,  126,   33,  214,  160,   25,
  183,  186,   25,   25,   25,   25,   25,   25,  179,  176,
    0,    0,   25,   25,   25,   25,    0,   25,   25,   25,
   27,   54,    0,    0,   54,   54,   54,   54,   54,   54,
   65,    0,    0,    0,   54,   54,   54,   54,    0,   54,
   54,   54,    0,  102,  103,  104,  105,    0,    9,   10,
   11,    0,    0,   62,    0,    0,   61,    0,    0,   61,
   61,   61,   61,   61,   61,    0,    0,    0,    0,   61,
   61,   61,   61,    0,   61,   61,   61,   32,    0,    0,
    0,    0,   66,    0,    0,   66,   66,   66,   66,   66,
   66,    0,    0,    0,    0,   66,   66,   66,   66,    0,
   66,   66,   66,    0,    0,    0,   60,    0,    0,   60,
   60,   60,   60,   60,   60,    0,    0,   75,    0,   60,
   60,   60,   60,    0,   60,   60,   60,    0,   33,    0,
    0,   33,   33,   33,   33,   33,   33,   12,    0,    0,
    0,   33,   33,   33,   33,    0,   33,   33,   33,    0,
    0,    0,   27,    0,    0,   27,   27,   27,   27,   27,
   27,    0,   18,    0,    0,   27,   27,   27,   27,   18,
   27,   27,   27,    0,   18,    0,   18,   96,    0,    0,
    0,    0,    0,    0,    0,   62,    0,    0,   62,   62,
   62,   62,   62,   62,  124,   89,    0,    0,   62,   62,
   62,   62,   18,   62,   62,   62,   18,    0,   37,   32,
    0,  137,   32,   32,   32,   32,   32,   32,   96,    0,
    0,    0,   32,   32,   32,   32,   19,   32,   32,   32,
   29,    0,   20,   19,    0,  140,   89,   18,   19,   20,
   19,    0,    0,    0,   20,   39,   20,    0,    2,   75,
    0,    3,   75,    5,   36,   75,   75,   75,    0,  170,
    7,    0,   75,   18,   75,   75,   19,   75,   75,   75,
   19,    1,   20,    0,    2,   45,   20,    3,    4,    5,
    0,    0,    0,    0,    6,  140,    7,    8,  140,    9,
   10,   11,    0,    0,    0,  192,   78,    0,    0,    0,
    0,   19,    0,    0,    0,    0,    0,   20,    0,    0,
    0,    0,    0,  137,    0,  206,    0,   88,  140,    0,
    0,    0,    0,    0,    0,    0,   39,   19,    0,    2,
    0,  206,    3,   20,    5,  137,    0,    0,  139,   18,
   37,    7,    0,   37,   37,   37,   37,   37,   37,    0,
    0,    0,    0,   37,   37,   37,   37,    0,   37,   37,
   37,  145,   29,    0,    0,   29,   29,   29,   29,   29,
   29,    0,    0,    0,    0,   29,   29,   29,   29,    0,
   29,   29,   29,  173,    0,    0,   36,    0,    0,   36,
   36,   36,   36,   36,   36,    0,    0,    0,    0,   36,
   36,   36,   36,   19,   36,   36,   36,    1,  175,   20,
    2,    0,    0,    3,    4,    5,    0,    0,    0,    0,
    6,    0,    7,    8,    0,    9,   10,   11,    1,  194,
    0,    2,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,    0,    7,    8,    0,    9,   10,   11,    1,
    0,  190,   86,    0,    0,    3,    4,    5,    0,    0,
    0,  204,    6,    0,   87,    8,    0,    9,   10,   11,
    1,  213,    0,   86,    0,    0,    3,    4,    5,    0,
    0,    0,    0,    6,    0,   87,    8,    0,    9,   10,
   11,    0,    0,    1,   68,   68,   86,    0,    0,    3,
    4,    5,    0,    0,    0,    0,    6,    0,   87,    8,
    0,    9,   10,   11,    0,    1,    0,    0,   86,    0,
    0,    3,    4,    5,    0,   68,    0,    0,    6,    0,
   87,    8,  121,    9,   10,   11,    0,    0,    0,    0,
    1,    0,    0,   86,    0,    0,    3,    4,    5,    0,
    0,    0,    0,    6,    0,   87,    8,    0,    9,   10,
   11,    1,    0,    0,   86,    0,    0,    3,    4,    5,
    0,    0,    0,   68,    6,    0,   87,    8,    0,    9,
   10,   11,  121,   39,    0,    0,   86,    0,    0,    3,
    0,    5,    0,   39,    0,    0,   86,    0,   87,    3,
    0,    5,    0,   39,    0,    0,   86,    0,   87,    3,
    0,    5,    0,   68,    0,    0,   68,  121,   87,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    2,    0,    0,    3,    4,    5,    0,   68,   68,
    0,    6,  121,    7,    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   40,    7,  125,   41,   40,  123,   44,
   41,   44,   41,   46,   45,   44,   41,   44,   45,   44,
   44,  101,    0,  123,   14,   73,  125,   77,   61,   40,
   53,   45,   44,  189,   44,   46,   44,   40,   35,   36,
   44,   41,   42,   43,   44,   45,    0,   47,  143,   59,
   61,  126,  261,  262,   44,  211,   47,   48,  123,  134,
   60,   40,   62,   41,   87,   43,   44,   45,    0,   66,
   93,   94,   75,   44,  123,  123,  257,   43,   44,   45,
   42,  176,   60,  183,   62,   47,  257,   41,   59,   43,
   44,   45,    0,  257,  275,  276,  277,  177,  101,  123,
   41,  123,   43,   44,   45,   43,   60,   45,   62,   41,
  158,   43,   44,   45,    0,  165,  261,  262,  193,  110,
  143,  257,  257,  146,  260,  125,   44,  263,   60,  264,
   62,   43,   44,   45,   44,  183,  272,  257,  271,  130,
  275,  276,  277,  123,  264,  106,  107,  125,  171,    0,
  257,  111,  112,  176,  278,  275,  276,  277,  257,  274,
  157,  258,   41,  257,   41,  264,  189,   41,  191,   41,
   43,  125,   45,  257,  177,    0,  275,  276,  277,   40,
   44,  264,  179,  180,  207,  257,   40,   60,  211,   62,
  123,   44,  257,  125,  257,  260,  187,  125,  263,    0,
  265,   44,   44,  271,   41,   41,   41,  272,  257,   40,
   44,  260,   44,   41,  263,  257,  265,  125,  257,  262,
  257,    0,  257,  272,  257,   44,  257,  258,  257,   44,
  257,  258,  257,  275,  276,  277,  275,  276,  277,  125,
  275,  276,  277,  257,  258,    0,  275,  276,  277,  257,
  275,  276,  277,  257,   44,  259,   44,  257,   44,    0,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
  262,    0,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,   41,  275,  276,  277,
  125,  257,    0,  257,  125,   41,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   41,  275,  276,  277,  125,  257,   12,   94,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,    0,  275,  276,  277,  125,  207,  121,  257,
  158,  165,  260,  261,  262,  263,  264,  265,  154,  146,
   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
  125,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
  257,   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,
  276,  277,   -1,  266,  267,  268,  269,   -1,  275,  276,
  277,   -1,   -1,  125,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,  125,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,  123,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,    0,   -1,   -1,  270,  271,  272,  273,    7,
  275,  276,  277,   -1,   12,   -1,   14,   53,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,  125,   53,   -1,   -1,  270,  271,
  272,  273,   40,  275,  276,  277,   44,   -1,  125,  257,
   -1,   87,  260,  261,  262,  263,  264,  265,   94,   -1,
   -1,   -1,  270,  271,  272,  273,    0,  275,  276,  277,
  125,   -1,    0,    7,   -1,   93,   94,   75,   12,    7,
   14,   -1,   -1,   -1,   12,  257,   14,   -1,  260,  257,
   -1,  263,  260,  265,  125,  263,  264,  265,   -1,  135,
  272,   -1,  270,  101,  272,  273,   40,  275,  276,  277,
   44,  257,   40,   -1,  260,  125,   44,  263,  264,  265,
   -1,   -1,   -1,   -1,  270,  143,  272,  273,  146,  275,
  276,  277,   -1,   -1,   -1,  171,  125,   -1,   -1,   -1,
   -1,   75,   -1,   -1,   -1,   -1,   -1,   75,   -1,   -1,
   -1,   -1,   -1,  189,   -1,  191,   -1,  125,  176,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  101,   -1,  260,
   -1,  207,  263,  101,  265,  211,   -1,   -1,  125,  177,
  257,  272,   -1,  260,  261,  262,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,  273,   -1,  275,  276,
  277,  125,  257,   -1,   -1,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,  125,   -1,   -1,  257,   -1,   -1,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  177,  275,  276,  277,  257,  125,  177,
  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,
  270,   -1,  272,  273,   -1,  275,  276,  277,  257,  125,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,  257,
   -1,  125,  260,   -1,   -1,  263,  264,  265,   -1,   -1,
   -1,  125,  270,   -1,  272,  273,   -1,  275,  276,  277,
  257,  125,   -1,  260,   -1,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,   -1,   -1,  257,   35,   36,  260,   -1,   -1,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,   -1,  272,  273,
   -1,  275,  276,  277,   -1,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,   66,   -1,   -1,  270,   -1,
  272,  273,   73,  275,  276,  277,   -1,   -1,   -1,   -1,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,  114,  270,   -1,  272,  273,   -1,  275,
  276,  277,  123,  257,   -1,   -1,  260,   -1,   -1,  263,
   -1,  265,   -1,  257,   -1,   -1,  260,   -1,  272,  263,
   -1,  265,   -1,  257,   -1,   -1,  260,   -1,  272,  263,
   -1,  265,   -1,  154,   -1,   -1,  157,  158,  272,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,  263,  264,  265,   -1,  179,  180,
   -1,  270,  183,  272,  273,   -1,  275,  276,  277,
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
"encabezado_funcion : VOID ID '(' parametro_funcion lista_parametros_funcion_exceso ')'",
"encabezado_funcion : VOID '(' parametro_funcion ')'",
"encabezado_funcion : VOID '(' ')'",
"encabezado_funcion : VOID ID parametro_funcion ')'",
"encabezado_funcion : VOID ID ')'",
"encabezado_funcion : VOID ID",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return '}'",
"cuerpo_funcion : '{' sentencia_return '}'",
"cuerpo_funcion : '{' sentencias_funcion '}'",
"cuerpo_funcion : '{' '}'",
"cuerpo_funcion : '{' sentencias_funcion sentencia_return sentencias_funcion_inalcanzable '}'",
"cuerpo_funcion : '{' sentencia_return sentencias_funcion_inalcanzable '}'",
"sentencias_funcion_inalcanzable : sentencia_funcion_inalcanzable",
"sentencias_funcion_inalcanzable : sentencias_funcion_inalcanzable sentencia_funcion_inalcanzable",
"sentencia_funcion_inalcanzable : sentencia_declarativa",
"sentencia_funcion_inalcanzable : sentencia_return",
"sentencia_funcion_inalcanzable : sentencia_ejecutable_funcion",
"lista_parametros_funcion_exceso : parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso ',' parametro_funcion",
"lista_parametros_funcion_exceso : lista_parametros_funcion_exceso parametro_funcion",
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

//#line 289 "./src/compilador/gramatica.y"

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
//#line 725 "Parser.java"
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
case 91:
//#line 202 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 92:
//#line 203 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 93:
//#line 204 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 94:
//#line 205 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 95:
//#line 206 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 96:
//#line 207 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 99:
//#line 213 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 100:
//#line 214 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 103:
//#line 220 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 104:
//#line 221 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 134:
//#line 284 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 135:
//#line 285 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1086 "Parser.java"
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
