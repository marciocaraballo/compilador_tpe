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
    3,    3,    3,   26,   26,   26,   29,   29,   29,   29,
   29,   29,   32,   32,   34,   34,   34,   28,   28,   28,
   28,   28,   28,   28,   28,   35,   35,   27,   33,   33,
   33,   33,   33,   33,   33,   33,   33,   36,   36,   36,
   36,   36,   36,   39,   39,   40,   40,   40,   38,   38,
   38,   37,   31,   31,   30,   30,   30,   30,   17,   41,
   41,   41,   41,   41,   41,   24,   24,   24,   42,   42,
   42,   43,   43,   43,   44,   44,
};
final static short yylen[] = {                            2,
    3,    2,    2,    0,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    7,    6,    7,    6,    9,
    7,    8,    6,    9,    7,    8,    6,    1,    3,    1,
    1,    3,    4,    5,    1,    2,    1,    1,    1,    2,
    1,    2,    3,    2,    2,    3,    5,    4,    7,    4,
    3,    6,    1,    3,    4,    3,    3,    1,    3,    1,
    1,    1,    1,    3,    2,    2,    5,    4,    3,    2,
    4,    3,    2,    3,    3,    1,    2,    5,    7,    4,
    3,    6,    5,    4,    6,    1,    2,    2,    5,    4,
    7,    6,    4,    3,    4,    3,    2,    4,    3,    3,
    2,    5,    4,    1,    2,    1,    1,    1,    1,    3,
    2,    2,    1,    3,    1,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    1,    1,    2,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  125,  126,
  127,    0,    0,    0,    5,    7,    8,    9,   10,   11,
   12,   13,   14,    0,   70,   71,   72,   73,    0,    0,
    0,    0,    0,   55,    0,    0,   24,    0,    0,    0,
   68,    0,   38,    0,    0,    0,   80,    0,    0,    0,
    2,    6,    0,    0,    0,  123,   76,    0,    0,   98,
    0,  145,    0,    0,    0,    0,  141,  144,   56,   53,
  128,    0,  106,    0,    0,  104,    0,    0,    0,    0,
   86,    0,   96,    0,   91,    0,    0,   49,    0,    0,
    0,   82,    0,    0,   79,    0,    1,    0,    0,   67,
    0,   69,   74,    0,    0,    0,  111,   17,   19,   20,
   21,    0,    0,   15,   18,   22,   23,  143,  146,    0,
  130,  131,  132,  133,    0,    0,  134,  135,    0,    0,
    0,  100,    0,  122,  105,  103,   87,    0,    0,    0,
    0,   94,   97,    0,   90,   39,   50,    0,    0,   81,
   78,   84,   58,    0,    0,   65,  124,    0,    0,   41,
   40,    0,  109,  116,  117,  118,    0,  114,  110,    0,
   16,    0,    0,    0,    0,  139,  140,    0,   99,  119,
    0,    0,    0,    0,   88,   85,   93,    0,   77,    0,
    0,   57,    0,   51,    0,    0,  113,  115,  108,    0,
    0,    0,    0,    0,  102,  121,    0,   95,   92,    0,
    0,    0,    0,   42,    0,   52,    0,  112,    0,   31,
  101,  120,   89,   26,    0,   59,    0,   43,   47,   48,
    0,   45,    0,    0,    0,    0,   44,   46,    0,   30,
    0,   35,   28,    0,   34,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,  109,  110,  111,   21,   22,   23,
  113,  114,  166,  116,  117,   44,   64,  162,   89,  195,
  231,  232,   24,   65,  191,   25,   81,   27,   28,   82,
   58,   48,   30,   83,   84,   60,  180,  181,  167,  168,
  129,   66,   67,   68,
};
final static short yysindex[] = {                       392,
    0,   17,   -3,  -36,   36, -114,  735,  379,    0,    0,
    0,  783,    0,  504,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   33,    0,    0,    0,    0,   -7,  -47,
  -13,   45,   57,    0,  -38,  -41,    0,  -68,  -93, -124,
    0,  484,    0, -139, -101, -129,    0,   41,   98,  569,
    0,    0,  -30,  -26,  -89,    0,    0,   -5,  592,    0,
 -116,    0,  -83,  138,  128,  102,    0,    0,    0,    0,
    0,  124,    0,  -66,  154,    0,  156,  161,  -87, -124,
    0,  -43,    0, -119,    0, -124,  -65,    0,  755,  177,
 -129,    0,   95,  105,    0, -129,    0,  211,  488,    0,
   54,    0,    0,    3,  217,  736,    0,    0,    0,    0,
    0,  614,  635,    0,    0,    0,    0,    0,    0,  735,
    0,    0,    0,    0,  -13,  -13,    0,    0,  -13,  -13,
  -13,    0,  -34,    0,    0,    0,    0,  -90, -124,  334,
   78,    0,    0,  409,    0,    0,    0,  -13,  172,    0,
    0,    0,    0,  -13,  258,    0,    0,  -13,  -59,    0,
    0,   35,    0,    0,    0,    0,  659,    0,    0,  680,
    0, -212,  102,  102,   76,    0,    0,  141,    0,    0,
  -28, -124,  432,  706,    0,    0,    0,  280,    0,  316,
  310,    0,  311,    0,  756,  322,    0,    0,    0,  702,
  735,  323,  -24,  141,    0,    0,  712,    0,    0,  329,
  -13,  330,  736,    0,  769,    0,  -13,    0,  120,    0,
    0,    0,    0,    0,   76,    0,   20,    0,    0,    0,
  779,    0,  342,  340,  736,  345,    0,    0,  349,    0,
  140,    0,    0,  359,    0,
};
final static short yyrindex[] = {                       405,
  -32,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,   -6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  406,
    0,    0,    0,    0,    0,    0,    0,  343,    0,    0,
    1,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  151,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  284,    0,  150,    0,    0,
  176,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  200,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,   69,  378,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  383,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  222,    0,    0,    0,    0,    0,    0,    0,  246,
    0,  279,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  388,    0,    0,    0,    0,    0,
    0,    0,    0,  303,    0,  461,    0,    0,  526,    0,
    0,    0,    0,  547,    0,
};
final static short yygindex[] = {                         0,
  418,   37,  -31,   14,   58,  104,  581,    0,    0,  535,
    0,  318,  -54,    0,    0,  -95,  -62, -182,    0,    0,
    0,  201,    0,  -27,    0,    0,  585,    0,    0,  512,
  352,   29,   32,  443,   44,    0,   -1,  257,  272, -105,
    0,  202,  218,    0,
};
final static int YYTABLESIZE=1060;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         76,
  142,   72,   73,   36,  115,  142,  179,   68,   40,  178,
   98,  128,  205,   68,   63,  204,  221,  100,   63,  204,
   43,   91,  138,   92,  172,   99,  101,  108,   68,   86,
  227,   63,  182,   75,   77,  139,   57,  107,  103,   49,
   34,  142,  142,  142,  142,  142,  136,  142,  201,  202,
   52,  161,  241,  104,   80,   88,   31,   18,  115,  145,
  142,  198,  142,  138,   18,  138,  138,  138,  137,   18,
  133,   18,   53,   93,   94,   59,   49,   49,   55,   37,
  164,  108,  138,   87,  138,  188,   52,  136,   69,  136,
  136,  136,   25,   54,  198,  193,  125,  156,  126,   18,
   70,  175,  147,   19,  194,  219,  136,   18,  136,  137,
   19,  137,  137,  137,   54,   19,  107,   19,  125,  149,
  126,  186,   49,  140,  152,  142,  190,   49,  137,  144,
  137,   90,   78,   43,    4,  164,  104,   78,  164,    4,
  216,   96,   38,  130,    4,   19,   18,  138,  131,   61,
    9,   10,   11,   19,  233,    9,   10,   11,  161,   39,
  230,  118,    4,   85,  132,   95,   78,  102,  164,  138,
  125,  136,  126,    4,  119,   66,  230,   18,  120,  206,
  161,  183,  184,  225,    9,   10,   11,  128,   78,  127,
  134,   78,   19,  137,  135,    4,  136,   41,    4,   60,
  105,  206,  222,    3,  137,   79,    9,   10,   11,    9,
   10,   11,  106,   56,   43,   71,  148,   25,   71,  150,
   35,   33,   71,   19,  128,  207,   61,   62,   71,  151,
   61,   62,   71,    9,   10,   11,    9,   10,   11,   54,
    9,   10,   11,   61,   62,   27,    9,   10,   11,   56,
    9,   10,   11,   32,  153,   33,  158,  142,   18,  157,
  142,  142,  142,  142,  142,  142,  142,  142,  142,  142,
  142,  142,  142,  142,   61,  142,  142,  142,   62,  138,
  235,  236,  138,  138,  138,  138,  138,  138,  138,  138,
  138,  138,  138,  138,  138,  138,  189,  138,  138,  138,
   66,  192,   32,  136,   19,  196,  136,  136,  136,  136,
  136,  136,  136,  136,  136,  136,  136,  136,  136,  136,
  210,  136,  136,  136,   60,  137,  173,  174,  137,  137,
  137,  137,  137,  137,  137,  137,  137,  137,  137,  137,
  137,  137,   75,  137,  137,  137,   33,  176,  177,   25,
  212,  213,   25,   25,   25,   25,   25,   25,  125,  211,
  126,  217,   25,   25,   25,   25,  220,   25,   25,   25,
   27,   54,  224,  226,   54,   54,   54,   54,   54,   54,
   71,  234,  239,  240,   54,   54,   54,   54,  242,   54,
   54,   54,  243,  121,  122,  123,  124,   71,    9,   10,
   11,  244,  245,   62,    4,    3,   61,  128,   83,   61,
   61,   61,   61,   61,   61,    9,   10,   11,  129,   61,
   61,   61,   61,   63,   61,   61,   61,   32,   64,   50,
  171,  238,   66,  141,  203,   66,   66,   66,   66,   66,
   66,  200,    0,    0,    0,   66,   66,   66,   66,    0,
   66,   66,   66,    0,    0,    0,   60,    0,  185,   60,
   60,   60,   60,   60,   60,    0,    0,   75,    0,   60,
   60,   60,   60,    0,   60,   60,   60,    0,   33,    0,
    0,   33,   33,   33,   33,   33,   33,    0,    0,    0,
    0,   33,   33,   33,   33,    0,   33,   33,   33,    0,
    0,   46,   27,   47,    0,   27,   27,   27,   27,   27,
   27,   29,    0,    0,   12,   27,   27,   27,   27,    0,
   27,   27,   27,   29,    0,   29,  143,    0,  155,  143,
  125,  154,  126,  187,    0,   62,    0,    0,   62,   62,
   62,   62,   62,   62,    0,    0,   74,   74,   62,   62,
   62,   62,    0,   62,   62,   62,  208,    0,    0,   32,
    0,   29,   32,   32,   32,   32,   32,   32,    0,    0,
   29,    0,   32,   32,   32,   32,    0,   32,   32,   32,
   20,    0,  143,   74,   26,   37,  143,   20,    0,    0,
   78,    0,   20,  112,   20,    0,   26,    4,   26,   75,
    0,    0,   75,    0,    0,   75,   75,   75,    9,   10,
   11,    0,   75,    0,   75,   75,    0,   75,   75,   75,
    0,    0,   20,   29,   29,  143,  143,    0,   51,    0,
   20,    0,    0,    0,   26,   45,    0,    0,    0,    0,
  160,    0,    4,   26,   74,    0,  165,  170,    1,  143,
   29,    2,    0,    0,    3,    4,    5,    0,    0,    0,
    0,    6,    0,    7,    8,   78,    9,   10,   11,   20,
    0,   36,    4,    0,    0,    0,    0,    0,   29,    0,
    0,   29,    0,    9,   10,   11,    0,    0,   78,   74,
    0,    0,   74,   97,    0,    4,   26,   26,    0,    0,
   20,  165,    0,    0,  165,    0,    9,   10,   11,    0,
    0,   29,    0,    0,   74,   74,  107,   37,    0,    0,
   37,   37,   37,   37,   37,   37,    0,    0,    0,  215,
   37,   37,   37,   37,  165,   37,   37,   37,  163,    0,
   41,    0,    0,    2,    0,    0,    3,  160,    5,  229,
    0,   26,    0,    0,   26,    7,    0,    0,    0,  169,
    1,    0,    0,    2,    0,  229,    3,    4,    5,  160,
    0,    0,    0,    6,    0,    7,    8,    0,    9,   10,
   11,   20,   29,  197,   26,   29,   29,   29,   29,   29,
   29,    0,    0,    0,    0,   29,   29,   29,   29,    0,
   29,   29,   29,   36,  199,    0,   36,   36,   36,   36,
   36,   36,    0,    0,    0,    0,   36,   36,   36,   36,
    0,   36,   36,   36,    0,    1,  218,    0,    2,    0,
  209,    3,    4,    5,    0,    0,  223,    0,    6,    0,
    7,    8,    0,    9,   10,   11,    0,    0,    1,    0,
    0,  105,    0,    0,    3,    4,    5,   42,  159,    0,
    0,    6,    0,  106,    8,    0,    9,   10,   11,    0,
    1,    0,    0,  105,    0,    0,    3,    4,    5,  146,
  214,    0,    0,    6,    0,  106,    8,    0,    9,   10,
   11,    1,    0,  228,  105,    0,    0,    3,    4,    5,
    0,    0,    0,  237,    6,    0,  106,    8,    0,    9,
   10,   11,    0,    0,    0,    1,    0,    0,  105,    0,
    0,    3,    4,    5,    0,    0,    0,    0,    6,    0,
  106,    8,    0,    9,   10,   11,    1,    0,    0,  105,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    6,
    0,  106,    8,    0,    9,   10,   11,    0,    1,    0,
    0,  105,   78,    0,    3,    4,    5,    0,   78,    4,
    0,    6,    0,  106,    8,    4,    9,   10,   11,    0,
    9,   10,   11,    0,    0,    0,    9,   10,   11,    0,
    0,   41,   41,    0,    2,  105,    0,    3,    3,    5,
    5,    0,    0,    0,    0,    0,    7,  106,    0,    0,
    0,   41,   41,    0,    2,  105,    0,    3,    3,    5,
    5,    0,    0,    0,    0,   41,    7,  106,  105,    0,
    0,    3,    0,    5,    0,   41,    0,    0,  105,    1,
  106,    3,    2,    5,    0,    3,    4,    5,    0,    0,
  106,    0,    6,    0,    7,    8,    0,    9,   10,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   40,   59,  125,   41,   40,  123,   44,
   41,   44,   41,   46,   45,   44,   41,   44,   45,   44,
    7,  123,    0,  125,  120,   53,   54,   59,   61,  123,
  213,   45,  123,   35,   36,  123,   44,   44,   44,    8,
   44,   41,   42,   43,   44,   45,    0,   47,  261,  262,
   14,  106,  235,   59,  123,   42,   40,    0,  113,  125,
   60,  167,   62,   41,    7,   43,   44,   45,    0,   12,
   72,   14,   40,   45,   46,  123,   45,   46,   46,   44,
  112,  113,   60,   40,   62,  148,   50,   41,   44,   43,
   44,   45,    0,   61,  200,  158,   43,   44,   45,   42,
   44,  129,   89,    0,  159,  201,   60,   50,   62,   41,
    7,   43,   44,   45,    0,   12,  123,   14,   43,   91,
   45,   44,   91,   80,   96,  125,  154,   96,   60,   86,
   62,  271,  257,  120,  264,  167,   59,  257,  170,  264,
  195,   44,  257,   42,  264,   42,   89,  125,   47,    0,
  275,  276,  277,   50,  217,  275,  276,  277,  213,  274,
  215,  278,  264,  257,   41,  125,  257,  257,  200,  257,
   43,  125,   45,  264,  258,    0,  231,  120,   41,  181,
  235,  138,  139,  211,  275,  276,  277,   60,  257,   62,
  257,  257,   89,  125,   41,  264,   41,  257,  264,    0,
  260,  203,  204,  263,   44,  274,  275,  276,  277,  275,
  276,  277,  272,  257,  201,  257,   40,  125,  257,  125,
  257,    0,  257,  120,  257,  182,  257,  258,  257,  125,
  257,  258,  257,  275,  276,  277,  275,  276,  277,  125,
  275,  276,  277,  257,  258,    0,  275,  276,  277,  257,
  275,  276,  277,  257,   44,  259,   40,  257,  201,  257,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  273,  125,  275,  276,  277,    0,  257,
  261,  262,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  273,  125,  275,  276,  277,
  125,   44,    0,  257,  201,  271,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  273,
   41,  275,  276,  277,  125,  257,  125,  126,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  273,    0,  275,  276,  277,  125,  130,  131,  257,
   41,   41,  260,  261,  262,  263,  264,  265,   43,   44,
   45,   40,  270,  271,  272,  273,   44,  275,  276,  277,
  125,  257,   44,   44,  260,  261,  262,  263,  264,  265,
  257,  262,   41,   44,  270,  271,  272,  273,   44,  275,
  276,  277,   44,  266,  267,  268,  269,  257,  275,  276,
  277,  262,   44,  125,    0,    0,  257,  257,  125,  260,
  261,  262,  263,  264,  265,  275,  276,  277,   41,  270,
  271,  272,  273,   41,  275,  276,  277,  125,   41,   12,
  113,  231,  257,   82,  178,  260,  261,  262,  263,  264,
  265,  170,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,   -1,   -1,   -1,  257,   -1,  125,  260,
  261,  262,  263,  264,  265,   -1,   -1,  125,   -1,  270,
  271,  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,   -1,  275,  276,  277,   -1,
   -1,  123,  257,  125,   -1,  260,  261,  262,  263,  264,
  265,    0,   -1,   -1,  123,  270,  271,  272,  273,   -1,
  275,  276,  277,   12,   -1,   14,   84,   -1,   41,   87,
   43,   44,   45,  125,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,  265,   -1,   -1,   35,   36,  270,  271,
  272,  273,   -1,  275,  276,  277,  125,   -1,   -1,  257,
   -1,   50,  260,  261,  262,  263,  264,  265,   -1,   -1,
   59,   -1,  270,  271,  272,  273,   -1,  275,  276,  277,
    0,   -1,  140,   72,    0,  125,  144,    7,   -1,   -1,
  257,   -1,   12,   59,   14,   -1,   12,  264,   14,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,  275,  276,
  277,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
   -1,   -1,   42,  112,  113,  183,  184,   -1,  125,   -1,
   50,   -1,   -1,   -1,   50,  257,   -1,   -1,   -1,   -1,
  106,   -1,  264,   59,  133,   -1,  112,  113,  257,  207,
  125,  260,   -1,   -1,  263,  264,  265,   -1,   -1,   -1,
   -1,  270,   -1,  272,  273,  257,  275,  276,  277,   89,
   -1,  125,  264,   -1,   -1,   -1,   -1,   -1,  167,   -1,
   -1,  170,   -1,  275,  276,  277,   -1,   -1,  257,  178,
   -1,   -1,  181,  125,   -1,  264,  112,  113,   -1,   -1,
  120,  167,   -1,   -1,  170,   -1,  275,  276,  277,   -1,
   -1,  200,   -1,   -1,  203,  204,  125,  257,   -1,   -1,
  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,  195,
  270,  271,  272,  273,  200,  275,  276,  277,  125,   -1,
  257,   -1,   -1,  260,   -1,   -1,  263,  213,  265,  215,
   -1,  167,   -1,   -1,  170,  272,   -1,   -1,   -1,  125,
  257,   -1,   -1,  260,   -1,  231,  263,  264,  265,  235,
   -1,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,  201,  257,  125,  200,  260,  261,  262,  263,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  275,  276,  277,  257,  125,   -1,  260,  261,  262,  263,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,
   -1,  275,  276,  277,   -1,  257,  125,   -1,  260,   -1,
  125,  263,  264,  265,   -1,   -1,  125,   -1,  270,   -1,
  272,  273,   -1,  275,  276,  277,   -1,   -1,  257,   -1,
   -1,  260,   -1,   -1,  263,  264,  265,  123,  123,   -1,
   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,   -1,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,  125,
  125,   -1,   -1,  270,   -1,  272,  273,   -1,  275,  276,
  277,  257,   -1,  125,  260,   -1,   -1,  263,  264,  265,
   -1,   -1,   -1,  125,  270,   -1,  272,  273,   -1,  275,
  276,  277,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,   -1,
  272,  273,   -1,  275,  276,  277,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,   -1,   -1,   -1,   -1,  270,
   -1,  272,  273,   -1,  275,  276,  277,   -1,  257,   -1,
   -1,  260,  257,   -1,  263,  264,  265,   -1,  257,  264,
   -1,  270,   -1,  272,  273,  264,  275,  276,  277,   -1,
  275,  276,  277,   -1,   -1,   -1,  275,  276,  277,   -1,
   -1,  257,  257,   -1,  260,  260,   -1,  263,  263,  265,
  265,   -1,   -1,   -1,   -1,   -1,  272,  272,   -1,   -1,
   -1,  257,  257,   -1,  260,  260,   -1,  263,  263,  265,
  265,   -1,   -1,   -1,   -1,  257,  272,  272,  260,   -1,
   -1,  263,   -1,  265,   -1,  257,   -1,   -1,  260,  257,
  272,  263,  260,  265,   -1,  263,  264,  265,   -1,   -1,
  272,   -1,  270,   -1,  272,  273,   -1,  275,  276,  277,
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
"declaracion_interfaz : INTERFACE '{' bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE '}'",
"declaracion_interfaz : INTERFACE ID bloque_encabezado_funcion '}'",
"declaracion_interfaz : INTERFACE ID '}'",
"bloque_encabezado_funcion : encabezado_funcion ','",
"bloque_encabezado_funcion : encabezado_funcion ',' bloque_encabezado_funcion",
"sentencia_declarativa_clase : tipo lista_de_variables ','",
"sentencia_declarativa_clase : declaracion_funcion",
"sentencia_declarativa_clase : ID ','",
"declaracion_clase : CLASS ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT ID",
"declaracion_clase : CLASS ID IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS IMPLEMENT '{' bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID bloque_sentencias_declarativas_clase '}'",
"declaracion_clase : CLASS ID IMPLEMENT ID bloque_sentencias_declarativas_clase '}'",
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

//#line 299 "./src/compilador/gramatica.y"

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
//#line 756 "Parser.java"
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
//#line 134 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 61:
//#line 135 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 62:
//#line 136 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en Invocacion de funcion"); }
break;
case 65:
//#line 145 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 66:
//#line 146 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia asignacion"); }
break;
case 67:
//#line 147 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta expresion del lado derecho en sentenecia asignacion"); }
break;
case 74:
//#line 163 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 75:
//#line 164 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta ',' en sentenecia declaracion de variables"); }
break;
case 76:
//#line 165 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta lista de variables en sentenecia declaracion de variables"); }
break;
case 78:
//#line 170 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 79:
//#line 171 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 80:
//#line 172 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de INTERFACE"); }
break;
case 81:
//#line 173 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 82:
//#line 174 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de INTERFACE"); }
break;
case 85:
//#line 183 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables en CLASS detectado"); }
break;
case 88:
//#line 189 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 89:
//#line 190 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de clase CLASS detectado"); }
break;
case 90:
//#line 191 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 91:
//#line 192 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 92:
//#line 193 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en IMPLEMENT de clase"); }
break;
case 93:
//#line 194 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en declaracion de clase"); }
break;
case 94:
//#line 195 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 95:
//#line 196 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta simbolo '{' en declaracion de clase"); }
break;
case 98:
//#line 205 "./src/compilador/gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 101:
//#line 211 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 102:
//#line 212 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Encabezado de funcion con mas de 1 parametro detectado, se preserva solo el primer parametro"); }
break;
case 103:
//#line 213 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 104:
//#line 214 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta IDENTIFICADOR en el encabezado de la funcion"); }
break;
case 105:
//#line 215 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 106:
//#line 216 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 107:
//#line 217 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta '(' en el encabezado de la funcion"); }
break;
case 110:
//#line 223 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 111:
//#line 224 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Falta sentencia RETURN al final de la funcion"); }
break;
case 114:
//#line 230 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 115:
//#line 231 "./src/compilador/gramatica.y"
{ logger.logError("[Parser] Codigo inalcanzable luego del RETURN, se ignorara"); }
break;
case 145:
//#line 294 "./src/compilador/gramatica.y"
{ corregirConstantePositivaEntera(val_peek(0).sval); }
break;
case 146:
//#line 295 "./src/compilador/gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1161 "Parser.java"
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
