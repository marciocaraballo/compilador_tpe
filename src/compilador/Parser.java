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
    7,    7,    7,   14,   14,   15,   15,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   22,   22,    8,
    8,   23,   23,   23,   23,   25,   25,   25,   21,   21,
   21,   21,   21,   21,   21,   21,   19,   19,   19,   20,
   20,   26,   26,   26,   26,   26,   26,   28,   28,   29,
   29,   29,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   13,   13,   13,   13,   13,   32,   32,   32,
   35,   35,   34,   34,   36,   34,    9,    9,    9,   37,
   37,   38,   38,   38,   38,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    5,   42,   42,   42,   30,   30,
   30,   30,   30,   31,   31,   41,   41,   43,   43,   43,
   43,   43,   27,   44,   44,   45,   45,   17,   17,   17,
   40,   40,   47,   40,   40,   40,   46,   46,   39,   39,
   48,   48,   49,   49,   24,   24,   24,   50,   50,   50,
   50,   50,   50,   33,   33,   33,   51,   51,   51,   52,
   52,   52,   54,   54,   55,   55,   56,   56,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   53,   53,   10,
   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    2,    2,    2,    3,    1,
    4,    5,    3,    1,    2,    1,    1,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    4,
    5,   10,   13,   13,   16,    1,    1,    2,    8,    8,
   11,   11,   15,   15,   15,   15,    9,   10,   10,    7,
    9,    1,    1,    1,    4,    4,    3,    1,    2,    1,
    1,    1,    6,    7,    5,    5,    5,    5,    6,    6,
    6,    6,    5,    4,    3,    3,    4,    1,    3,    5,
    1,    3,    2,    1,    0,    2,    3,    2,    2,    1,
    3,    3,    2,    2,    1,    1,    2,    1,    2,    1,
    2,    1,    2,    1,    2,    1,    1,    1,    2,    4,
    1,    3,    3,    2,    1,    1,    3,    7,    6,    6,
    6,    6,    1,    1,    3,    1,    2,    4,    3,    3,
    9,    8,    0,   17,    7,    6,    1,    2,    8,   10,
    1,    3,    1,    2,    3,    2,    2,    1,    1,    1,
    1,    1,    1,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    3,    4,    1,    3,    1,    1,    5,    5,
    4,    4,    4,    4,    4,    4,    4,    1,    2,    1,
    1,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  191,  190,    0,    0,    0,    7,    9,   10,   11,   12,
   13,   14,    0,    0,    0,  106,  110,    0,  108,  112,
  114,  126,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  116,  117,  118,
  134,    0,  107,  111,  109,  113,  115,    0,   99,    0,
    0,    2,    8,    0,   18,    0,   17,    0,    0,    5,
    0,    3,    0,  188,    0,  139,    0,    0,  169,  171,
  172,   19,  158,  159,  160,  161,  162,  163,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  119,    0,  124,  136,
    0,    0,  104,    0,   97,    0,   15,    0,    0,    0,
    0,    0,   23,   26,    0,    0,   24,   27,   28,   30,
   32,   34,   36,    0,    0,  127,    1,    0,  189,    0,
    0,  138,    0,    0,    0,    0,    0,  184,  182,  185,
    0,  183,    0,  181,    0,    0,    0,    0,   93,    0,
    0,   96,    0,    0,    0,    0,  133,  123,    0,  135,
  137,    0,    0,  102,  101,    0,    0,    0,    0,    0,
   62,   70,   63,    0,    0,   68,   71,   72,    0,   29,
   31,   33,   35,   37,   21,    0,   25,    0,   40,    0,
  177,  173,  178,    0,  175,    0,    0,  167,  168,    0,
    0,  180,  179,    0,   78,    0,    0,   77,    0,    0,
   76,    0,    0,    0,    0,  120,    0,    0,    0,    0,
    0,    0,   85,    0,    0,    0,   69,    0,   22,   41,
    0,    0,  174,    0,  151,    0,   73,    0,   82,   81,
    0,   80,    0,    0,    0,  146,    0,  130,  132,    0,
  131,    0,   87,    0,    0,   67,    0,    0,    0,    0,
    0,  176,  153,    0,    0,    0,   74,    0,   90,  148,
    0,    0,    0,  128,    0,   83,    0,   65,   66,    0,
    0,    0,  152,  154,    0,  149,    0,  143,    0,  142,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   92,    0,  141,    0,   48,   38,    0,    0,    0,
    0,    0,   60,    0,    0,    0,    0,  150,    0,    0,
    0,    0,   39,    0,   49,   50,    0,   46,   47,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,    0,    0,    0,   61,    0,    0,    0,
    0,    0,    0,    0,    0,   42,   58,   59,    0,    0,
    0,    0,    0,    0,   51,    0,   52,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   43,    0,   44,    0,    0,    0,
    0,    0,    0,    0,    0,  144,    0,   55,   56,   53,
    0,   54,    0,   45,    0,
};
final static short yydgoto[] = {                          3,
    4,   15,  253,   17,   18,   19,   20,   21,   22,   23,
   24,   25,  338,  126,  127,  317,  129,  130,  131,  132,
  133,  318,  339,   89,  306,  184,  135,  185,  186,  187,
  188,  100,   90,  101,  279,  102,   60,   61,   29,   30,
   31,   51,   32,   52,  111,  254,  313,  246,  274,   91,
   78,   79,   80,   81,  204,  205,
};
final static short yysindex[] = {                      -102,
    0,  912,    0,  493,  -30,   15,  -37,  -21,   19,  282,
    0,    0,  616,  -52,  591,    0,    0,    0,    0,    0,
    0,    0,   -3,   48,  -79,    0,    0,  125,    0,    0,
    0,    0,  652,  704,  -17, -114,  679,  148,  161,   11,
  -15,    5,  113,  -61,   23,  150,  959,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,   28,    0,  186,
  185,    0,    0,  212,    0,  216,    0,  694,   18,    0,
  727,    0,  258,    0,   42,    0,  122,   61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  264,   84,
   21,  253,  271,   38,   55,  279,   17,  265,   75,  306,
  322,  115,  317,  338,  129,  345,    0,    6,    0,    0,
  837,   50,    0,  138,    0,  147,    0,  368,   44,  380,
  -80, 1023,    0,    0,  298,  149,    0,    0,    0,    0,
    0,    0,    0,  308,  382,    0,    0,   53,    0,   21,
   21,    0,   21,   21,  191,   21,   85,    0,    0,    0,
  394,    0,  396,    0,  399, -115,   16, -115,    0,  402,
 -115,    0, -115,  409,  346,  -51,    0,    0,  411,    0,
    0,  676,  438,    0,    0,  679,   60,  300,  679,  976,
    0,    0,    0,  206,  976,    0,    0,    0,  441,    0,
    0,    0,    0,    0,    0,  359,    0,  360,    0,  214,
    0,    0,    0,   93,    0,   61,   61,    0,    0,  435,
   85,    0,    0, -115,    0,  436, -115,    0, -115,  451,
    0, -115,  912,  373,  737,    0,  448,   58,  449,  456,
  452,  307,    0,  479,  817,  470,    0,  679,    0,    0,
  -80,   71,    0, 1003,    0,  -93,    0, -115,    0,    0,
 -115,    0,  912,  398,  912,    0,  408,    0,    0,  462,
    0,  275,    0,  476,  276,    0,  413,  416,  679,  502,
  270,    0,    0,  864,  435,  486,    0,  503,    0,    0,
  491,  426,  494,    0,  107,    0,  457,    0,    0,  541,
  324,  546,    0,    0,  325,    0, -115,    0,  528,    0,
  549,  334, 1013,  350,  352,  339,  936,  578, -103,  679,
  590,    0,  378,    0,  679,    0,    0,  571,  592,  593,
  274,  -72,    0, 1013,  350,  352,  620,    0,  623,  624,
  404,  543,    0,  550,    0,    0, 1013,    0,    0,  415,
  617,  562,  563,  571,  630,  679,  429,  568,   73,  100,
 -125,  634,    0,  635,  638,  577,    0,  668,  444, 1013,
  468,  653,  600,  656,  619,    0,    0,    0,  102,  464,
 1013,  993, 1013,  482,    0, 1013,    0,  485,  626,  628,
 -125,  627,  629,  854,  683,  883,  696, 1013,  912,  637,
  497,  500,  505,  648,    0,  511,    0,  893,  650,  514,
  719,  721,  722,  520,  725,    0,  469,    0,    0,    0,
  726,    0, 1013,    0, -125,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  310,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  532,  532,    0,  733,  760,  779,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  581,    0,  522,
  454,    0,    0,  642,    0,  548,    0,    0,    0,    0,
  792,    0,  172,    0,    0,    0,  199,  299,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  532,    0,  127,    0,
  170,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  618,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   77,   90,    0,    0,    0,
  226,    0,  252,    0,    0,    0,    0,    0,    0,    0,
  532,    0,    0,    0,    0,    0,    0,    0,  798,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  523,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  333,  357,    0,    0,    0,
  101,    0,    0,  673,    0,    0,    0,    0,    0,  202,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -84,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  680,    0,    0,
  532,    0,  677,    0,    0,    0,    0,    0,    0,  381,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  273,    0,    0,
    0,    0,  407,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  532,    0,    0,    0,
    0,    0,    0,  544,  545,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   67,  397,  -63,   -9,    0,    0,    0,    0,  542,
   57,    0,  -35,    0, -116,  -59,   -2,    2,  682,  687,
  688,  807,  -38,  -20, -284,  570,   14,  636, -131,   29,
   40,   22,  -13, -101,  518,    0,  701,    0,  805,  809,
  810,    4,  761,    0,    0, -185,    0,  557,    0,  743,
  248,  267, -104,    0,    0,  594,
};
final static int YYTABLESIZE=1296;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
   48,   26,   40,   27,  124,   27,   59,   26,  128,  197,
   53,   27,   26,   36,   54,   28,   27,   28,   42,  324,
    2,   77,  106,   28,   97,   98,   28,   75,   28,  134,
   26,   26,  125,  203,   27,   27,  340,   48,   49,  257,
   86,   76,  180,   68,   26,  103,   28,   28,   27,   50,
  110,   96,  341,  237,   37,   65,  216,  155,   43,  220,
   28,  182,  124,  104,  168,   75,  128,  280,   26,  282,
   34,  225,   27,  217,  156,   49,  374,  147,  151,   66,
  108,  107,  183,  177,   28,  181,   50,  198,   75,  172,
  196,  173,   82,  202,   75,  153,  150,   75,  260,   71,
  231,   48,  143,  237,   75,  178,   67,  144,   26,   87,
   88,   86,   27,  152,  171,   75,  259,  156,  157,  190,
  182,  169,  374,  191,   28,  182,  140,  140,  141,  141,
  157,   44,  211,  243,  189,  156,  242,  203,    7,   49,
  119,  155,   64,   87,   88,   86,  120,  121,  157,  278,
   50,  228,  122,  105,    1,  230,  301,   75,  234,  155,
   11,   12,  119,  232,  140,   84,  141,   94,  275,  276,
   94,  170,   87,   88,   86,  182,   44,   86,   86,  118,
  142,  182,   69,    7,   94,  119,   45,  301,   92,   86,
  322,  120,  121,  119,   46,  278,  268,  122,  140,  267,
  245,   93,  183,  399,   58,  181,   35,   26,  109,  224,
   88,   27,  170,  170,  170,  112,  170,  270,  170,   38,
   26,   39,   26,   28,   27,  187,   27,   88,  116,  303,
  170,  170,  170,  170,  273,   41,   28,   35,   28,   73,
   74,   26,   89,  124,  115,   27,  305,  128,  290,  304,
   26,  186,   26,   64,   27,   36,   27,   28,  333,   89,
   11,   12,  167,  316,  294,  245,   28,   94,   28,   95,
  326,   26,   26,  325,  117,   27,   27,   73,   74,  334,
   11,   12,  332,  343,  333,  113,  342,   28,   28,  327,
   10,  333,   11,   12,  330,  114,  170,  138,  166,  139,
   73,   74,   84,   84,  145,  334,   73,   74,  356,  201,
   74,  148,  333,   91,   84,  365,   73,   74,   83,   84,
   85,  333,  158,  140,  333,  358,  333,  201,   74,  149,
   91,  159,  164,  383,  361,  362,  382,  154,  333,  166,
  233,  166,  140,  166,  141,  390,  160,  264,  394,  140,
  187,  141,   83,   84,   85,  333,  165,  166,  166,  166,
  166,  363,  364,  379,  362,  161,  301,  133,   20,   73,
   74,  162,  119,  164,  163,  164,  186,  164,  164,  394,
  129,   83,   84,   85,  302,  166,   26,  206,  207,  165,
   27,  164,  164,  164,  164,  174,  337,  165,   16,  165,
   16,  165,   28,   58,   47,    5,  145,  176,  118,  208,
  209,   63,    7,    8,  119,  165,  165,  165,  165,  179,
  120,  121,  195,  166,   11,   12,  122,   14,  170,   16,
   63,  170,  199,  170,  170,  170,  170,  170,  170,  200,
  170,  170,  170,  170,  170,  170,  170,  170,  170,  170,
  170,  210,  212,  100,  213,  140,  214,  164,  140,  219,
  140,  140,  140,  140,  140,  140,  222,   63,  223,  226,
  140,  140,  140,  140,  140,  140,  140,  140,  229,  236,
  238,  165,  187,  239,  240,  187,  241,  187,  187,  187,
  187,  187,  187,  248,  251,  255,  262,  187,  187,  187,
  187,  187,  187,  187,  187,  129,  258,  261,  186,  269,
  263,  186,  100,  186,  186,  186,  186,  186,  186,  265,
  284,   98,  281,  186,  186,  186,  186,  186,  186,  186,
  186,  145,  283,  301,  286,  285,  287,  288,   44,  119,
  289,    6,  291,  292,  296,    7,  297,   16,   45,  298,
  299,  302,  300,    9,   10,  166,   46,  244,  166,   13,
  166,  166,  166,  166,  166,  166,  371,  166,  166,  166,
  166,  166,  166,  166,  166,  166,  166,  166,  100,  307,
  105,  308,   99,   99,  309,  310,  314,  311,  315,  164,
  373,  413,  164,  301,  164,  164,  164,  164,  164,  164,
  321,  164,  164,  164,  164,  164,  164,  164,  164,  164,
  164,  164,  319,  165,  320,   33,  165,  103,  165,  165,
  165,  165,  165,  165,  105,  165,  165,  165,  165,  165,
  165,  165,  165,  165,  165,  165,  323,  129,   99,  105,
  129,   20,  129,  129,  129,  129,   98,  129,  328,  329,
  335,  336,  129,  129,  129,  129,  129,  129,  129,  129,
  345,  103,  346,  145,  347,  348,  145,  349,  145,  145,
  145,  145,   16,  145,  350,  353,  103,  352,  145,  145,
  145,  145,  145,  145,  145,  145,  354,  355,  357,  359,
  360,   44,  366,  367,    6,  331,  368,  215,    7,  218,
   20,  369,   99,  301,  221,  105,    9,   10,  370,  119,
  100,  375,   13,  100,  377,   62,  227,  100,  100,  100,
   75,  302,  376,   75,  380,  100,  100,  301,  301,  100,
  100,  100,  100,  119,  119,   87,   88,   86,   87,   88,
   86,  395,  103,  378,  385,  302,  302,  387,  388,    5,
  389,  391,    6,  392,  397,  247,    7,    8,  249,  401,
  250,  400,  402,  252,    9,   10,   20,  403,   11,   12,
   13,   14,  404,  405,  406,  407,   70,  408,   98,  409,
  410,   98,  411,  412,  414,   98,   98,   98,   95,  277,
  133,    4,   99,   98,   98,   75,   64,   98,   98,   98,
   98,  147,   79,  192,   16,   46,   47,   16,  193,  194,
  271,   16,   16,   16,  312,  235,  175,   55,  123,   16,
   16,   56,   57,   16,   16,   16,   16,   44,   72,  136,
  118,  295,  146,    0,    7,  272,  119,  105,   99,    0,
  105,    0,  120,  121,  105,  105,  105,    5,  122,    0,
    6,  137,  105,  105,    7,    8,  105,  105,  105,  105,
    0,  256,    9,   10,    0,    0,   11,   12,   13,   14,
    0,    0,   44,    0,  103,    6,    0,  103,    0,    7,
    0,  103,  103,  103,  121,    0,    0,    9,   10,  103,
  103,    0,    0,  103,  103,  103,  103,    0,   20,    0,
    0,   20,    0,  125,    0,   20,   20,   20,    5,    0,
    0,    6,    0,   20,   20,    7,    8,   20,   20,   20,
   20,    0,  122,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   73,   74,    0,   73,   74,    0,    0,    0,
    0,  266,    0,    0,   83,   84,   85,   83,   84,   85,
    5,    0,    0,  118,    0,    0,    0,    7,    8,  119,
    5,  170,    0,    6,    0,  120,  121,    7,    8,   11,
   12,  122,   14,    0,    0,    9,   10,    0,  393,   11,
   12,   13,   14,    5,    0,    0,    6,    0,  293,    0,
    7,    8,    0,    5,    0,    0,    6,    0,    9,   10,
    7,    8,   11,   12,   13,   14,    0,  396,    9,   10,
    0,    0,   11,   12,   13,   14,  121,  393,    0,  121,
    0,    0,    0,  121,    0,  121,  121,    0,    0,    0,
    0,  121,  121,  121,  121,  125,    0,  121,  125,    0,
    0,    0,  125,    0,  125,  125,    0,    0,    0,    0,
  125,  125,  125,  125,  122,    0,  125,  122,    0,    0,
    0,  122,    0,  122,  122,    0,    0,    0,    0,  122,
  122,  122,  122,   44,    0,  122,  118,    0,    0,    0,
    7,    0,  119,   45,    0,    0,    0,    0,  120,  121,
    0,   46,    0,   44,  122,    0,    6,    0,    0,    0,
    7,    0,    0,   45,    0,    0,    0,    0,    9,   10,
   44,   46,    0,  189,   13,    0,    0,    7,    0,  119,
   44,    0,    0,    6,    0,  120,  121,    7,    0,    0,
  344,  122,    0,    0,    0,    9,   10,    0,    0,   44,
    0,   13,  189,  351,    0,    0,    7,    0,    0,   44,
    0,    0,  189,    0,  120,  121,    7,    0,    0,    0,
  122,    0,    0,    0,  120,  121,  372,    0,    5,    0,
  122,    6,    0,    0,    0,    7,    8,  381,    0,  384,
    0,    0,  386,    9,   10,    0,    0,   11,   12,   13,
   14,    0,    5,    0,  398,  189,    0,    0,    0,    7,
    8,    0,    0,    0,    0,    0,    0,  120,  121,    0,
    0,   11,   12,  122,   14,   44,    0,    0,    6,  415,
    0,    0,    7,    0,    0,   45,    0,    0,    0,    0,
    9,   10,   44,   46,    0,  189,   13,    0,    0,    7,
    0,    0,   45,    0,    0,    0,    0,  120,  121,   44,
   46,    0,  118,  122,    0,    0,    7,    0,  119,   44,
    0,    0,    6,    0,  120,  121,    7,    0,    0,   44,
  122,    0,  189,    0,    9,   10,    7,    0,    0,   44,
   13,    0,  189,    0,  120,  121,    7,    0,    0,    0,
  122,    0,    0,    0,  120,  121,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   10,    4,   40,    2,   68,    4,   59,   10,   68,  126,
   13,   10,   15,   44,   13,    2,   15,    4,   40,  123,
  123,   35,   43,   10,   40,   41,   13,   45,   15,   68,
   33,   34,   68,  138,   33,   34,  321,   47,   10,  225,
  125,   59,  123,  123,   47,   41,   33,   34,   47,   10,
   47,   41,  125,  185,   40,   59,   41,   41,   40,  161,
   47,  121,  126,   42,   59,   45,  126,  253,   71,  255,
    4,  123,   71,   58,   58,   47,  361,   91,   41,   23,
   58,   59,  121,   40,   71,  121,   47,  126,   45,   40,
  126,  112,   36,   41,   45,   41,   59,   45,   41,   33,
   41,  111,   42,  235,   45,  119,   59,   47,  111,   60,
   61,   62,  111,   59,  111,   45,   59,   41,   97,  122,
  180,  108,  407,  122,  111,  185,   43,   43,   45,   45,
   41,  257,  146,   41,  260,   59,   44,  242,  264,  111,
  266,   41,  257,   60,   61,   62,  272,  273,   59,  251,
  111,  172,  278,   41,  257,  176,  260,   45,  179,   59,
  276,  277,  266,  177,   43,  125,   45,   41,  262,  263,
   44,    0,   60,   61,   62,  235,  257,  262,  263,  260,
   59,  241,   58,  264,   58,  266,  267,  260,   41,  274,
  307,  272,  273,  266,  275,  297,  235,  278,    0,  235,
  210,   41,  241,  389,  257,  241,  268,  210,   59,  261,
   41,  210,   41,   42,   43,  274,   45,  238,   47,  257,
  223,  259,  225,  210,  223,    0,  225,   58,   44,  123,
   59,   60,   61,   62,  244,  257,  223,  268,  225,  257,
  258,  244,   41,  307,   59,  244,  285,  307,  269,  285,
  253,    0,  255,  257,  253,   44,  255,  244,  318,   58,
  276,  277,  257,  302,  274,  275,  253,  257,  255,  259,
  309,  274,  275,  309,   59,  274,  275,  257,  258,  318,
  276,  277,  318,  322,  344,  258,  322,  274,  275,  310,
  273,  351,  276,  277,  315,  268,  125,   40,    0,  258,
  257,  258,  262,  263,   41,  344,  257,  258,  344,  257,
  258,   59,  372,   41,  274,  351,  257,  258,  269,  270,
  271,  381,   58,  125,  384,  346,  386,  257,  258,   59,
   58,  257,    0,  372,  262,  263,  372,   59,  398,   41,
   41,   43,   43,   45,   45,  381,   41,   41,  384,   43,
  125,   45,  269,  270,  271,  415,    0,   59,   60,   61,
   62,  262,  263,  262,  263,   44,  260,   58,   59,  257,
  258,  257,  266,   41,   58,   43,  125,   45,   41,  415,
    0,  269,  270,  271,  278,   41,  389,  140,  141,  261,
  389,   59,   60,   61,   62,  258,  123,   41,    2,   43,
    4,   45,  389,  257,  123,  257,    0,   40,  260,  143,
  144,   15,  264,  265,  266,   59,   60,   61,   62,   40,
  272,  273,  125,  125,  276,  277,  278,  279,  257,   33,
   34,  260,  125,  262,  263,  264,  265,  266,  267,   58,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  261,   59,    0,   59,  257,   58,  125,  260,   58,
  262,  263,  264,  265,  266,  267,   58,   71,  123,   59,
  272,  273,  274,  275,  276,  277,  278,  279,   41,  274,
   40,  125,  257,  125,  125,  260,  273,  262,  263,  264,
  265,  266,  267,   58,   44,  123,   41,  272,  273,  274,
  275,  276,  277,  278,  279,  125,   59,   59,  257,   40,
   59,  260,   59,  262,  263,  264,  265,  266,  267,   41,
   59,    0,  125,  272,  273,  274,  275,  276,  277,  278,
  279,  125,  125,  260,   59,  261,  261,  125,  257,  266,
  125,  260,   41,  274,   59,  264,   44,    0,  267,   59,
  125,  278,   59,  272,  273,  257,  275,  123,  260,  278,
  262,  263,  264,  265,  266,  267,  123,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  125,  123,
    0,   41,   41,   42,  261,   40,   59,  263,   40,  257,
  123,  123,  260,  260,  262,  263,  264,  265,  266,  267,
  262,  269,  270,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  263,  257,  263,  123,  260,    0,  262,  263,
  264,  265,  266,  267,   44,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,   59,  257,   97,   59,
  260,    0,  262,  263,  264,  265,  125,  267,   59,  272,
   59,   59,  272,  273,  274,  275,  276,  277,  278,  279,
   41,   44,   40,  257,   41,  262,  260,  125,  262,  263,
  264,  265,  125,  267,  125,   59,   59,  263,  272,  273,
  274,  275,  276,  277,  278,  279,  125,  125,   59,  261,
  123,  257,   59,   59,  260,  125,   59,  156,  264,  158,
   59,  125,  161,  260,  163,  125,  272,  273,   41,  266,
  257,   59,  278,  260,   59,  125,   41,  264,  265,  266,
   45,  278,  123,   45,  261,  272,  273,  260,  260,  276,
  277,  278,  279,  266,  266,   60,   61,   62,   60,   61,
   62,   59,  125,  125,  263,  278,  278,  263,  123,  257,
  123,  125,  260,  125,   59,  214,  264,  265,  217,  263,
  219,  125,  263,  222,  272,  273,  125,  263,  276,  277,
  278,  279,  125,  263,  125,  262,  125,   59,  257,   59,
   59,  260,  263,   59,   59,  264,  265,  266,  257,  248,
   58,    0,  251,  272,  273,  123,  274,  276,  277,  278,
  279,  125,  123,  122,  257,  262,  262,  260,  122,  122,
  241,  264,  265,  266,  297,  180,  116,   13,  125,  272,
  273,   13,   13,  276,  277,  278,  279,  257,  125,   69,
  260,  275,   90,   -1,  264,  242,  266,  257,  297,   -1,
  260,   -1,  272,  273,  264,  265,  266,  257,  278,   -1,
  260,  125,  272,  273,  264,  265,  276,  277,  278,  279,
   -1,  125,  272,  273,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,  257,   -1,  257,  260,   -1,  260,   -1,  264,
   -1,  264,  265,  266,  125,   -1,   -1,  272,  273,  272,
  273,   -1,   -1,  276,  277,  278,  279,   -1,  257,   -1,
   -1,  260,   -1,  125,   -1,  264,  265,  266,  257,   -1,
   -1,  260,   -1,  272,  273,  264,  265,  276,  277,  278,
  279,   -1,  125,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  257,  258,   -1,  257,  258,   -1,   -1,   -1,
   -1,  125,   -1,   -1,  269,  270,  271,  269,  270,  271,
  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,  266,
  257,  125,   -1,  260,   -1,  272,  273,  264,  265,  276,
  277,  278,  279,   -1,   -1,  272,  273,   -1,  125,  276,
  277,  278,  279,  257,   -1,   -1,  260,   -1,  125,   -1,
  264,  265,   -1,  257,   -1,   -1,  260,   -1,  272,  273,
  264,  265,  276,  277,  278,  279,   -1,  125,  272,  273,
   -1,   -1,  276,  277,  278,  279,  257,  125,   -1,  260,
   -1,   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,  272,  273,  274,  275,  257,   -1,  278,  260,   -1,
   -1,   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,
   -1,  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  257,   -1,  278,  260,   -1,   -1,   -1,
  264,   -1,  266,  267,   -1,   -1,   -1,   -1,  272,  273,
   -1,  275,   -1,  257,  278,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,
  257,  275,   -1,  260,  278,   -1,   -1,  264,   -1,  266,
  257,   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,
  324,  278,   -1,   -1,   -1,  272,  273,   -1,   -1,  257,
   -1,  278,  260,  337,   -1,   -1,  264,   -1,   -1,  257,
   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,
  278,   -1,   -1,   -1,  272,  273,  360,   -1,  257,   -1,
  278,  260,   -1,   -1,   -1,  264,  265,  371,   -1,  373,
   -1,   -1,  376,  272,  273,   -1,   -1,  276,  277,  278,
  279,   -1,  257,   -1,  388,  260,   -1,   -1,   -1,  264,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,   -1,
   -1,  276,  277,  278,  279,  257,   -1,   -1,  260,  413,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
  272,  273,  257,  275,   -1,  260,  278,   -1,   -1,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  272,  273,  257,
  275,   -1,  260,  278,   -1,   -1,  264,   -1,  266,  257,
   -1,   -1,  260,   -1,  272,  273,  264,   -1,   -1,  257,
  278,   -1,  260,   -1,  272,  273,  264,   -1,   -1,  257,
  278,   -1,  260,   -1,  272,  273,  264,   -1,   -1,   -1,
  278,   -1,   -1,   -1,  272,  273,
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
"funcion_con_return : encabezado_funcion '{' sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' sentencias_funcion sentencia_return '}'",
"funcion_con_return : encabezado_funcion '{' '}'",
"sentencias_funcion : sentencia_funcion",
"sentencias_funcion : sentencias_funcion sentencia_funcion",
"sentencia_funcion : sentencia_declarativa",
"sentencia_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_funcion : asignacion",
"sentencia_ejecutable_funcion : DEFER asignacion",
"sentencia_ejecutable_funcion : imprimir",
"sentencia_ejecutable_funcion : DEFER imprimir",
"sentencia_ejecutable_funcion : sentencia_when_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_when_con_return",
"sentencia_ejecutable_funcion : sentencia_do_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_do_con_return",
"sentencia_ejecutable_funcion : sentencia_seleccion_simple_con_return",
"sentencia_ejecutable_funcion : DEFER sentencia_seleccion_simple_con_return",
"sentencias_ejecutables_funcion : sentencia_ejecutable_funcion",
"sentencias_ejecutables_funcion : sentencias_ejecutables_funcion sentencia_ejecutable_funcion",
"funcion_sin_return : encabezado_funcion '{' sentencia_seleccion_compuesta_con_return '}'",
"funcion_sin_return : encabezado_funcion '{' sentencias_funcion sentencia_seleccion_compuesta_con_return '}'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE sentencia_seleccion_compuesta_con_return_simple ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return_simple ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_return",
"sentencia_seleccion_compuesta_con_return_simple : sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_compuesta_con_return_simple : DEFER sentencia_seleccion_compuesta_con_return",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN sentencia_seleccion_compuesta_con_return ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ELSE '{' sentencias_ejecutables_funcion '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_return '}' ENDIF ';'",
"sentencia_seleccion_simple_con_return : IF '(' condicion ')' THEN '{' sentencias_ejecutables_funcion '}' ELSE '{' sentencias_ejecutables_funcion sentencia_seleccion_compuesta_con_return '}' ENDIF ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_return '}' ';'",
"sentencia_when_con_return : WHEN '(' condicion ')' THEN '{' sentencia_funcion sentencia_seleccion_compuesta_con_return '}' ';'",
"sentencia_do_con_return : DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"sentencia_do_con_return : etiqueta ':' DO bloque_sentencias_ejecutables_do_con_return UNTIL '(' condicion ')' ';'",
"bloque_sentencias_ejecutables_do_con_return : sentencia_return",
"bloque_sentencias_ejecutables_do_con_return : sentencia_seleccion_compuesta_con_return",
"bloque_sentencias_ejecutables_do_con_return : sentencias_ejecutables_do_funcion",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion sentencia_seleccion_compuesta_con_return '}'",
"bloque_sentencias_ejecutables_do_con_return : '{' sentencias_ejecutables_do_funcion '}'",
"sentencias_ejecutables_do_funcion : sentencia_ejecutable_do_funcion",
"sentencias_ejecutables_do_funcion : sentencias_ejecutables_do_funcion sentencia_ejecutable_do_funcion",
"sentencia_ejecutable_do_funcion : sentencia_ejecutable_funcion",
"sentencia_ejecutable_do_funcion : sentencia_break",
"sentencia_ejecutable_do_funcion : sentencia_continue",
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
"sentencia_when : WHEN '(' condicion ')' '{' sentencias_when '}'",
"sentencia_when : WHEN '(' condicion ')' '{' '}'",
"sentencias_when : sentencia",
"sentencias_when : sentencia sentencias_when",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"seleccion : IF '(' condicion ')' THEN bloque_sentencias_ejecutables_seleccion ELSE bloque_sentencias_ejecutables_seleccion ENDIF ';'",
"bloque_sentencias_ejecutables_seleccion : sentencia_ejecutable",
"bloque_sentencias_ejecutables_seleccion : '{' sentencias_ejecutables '}'",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"condicion : comparador expresion",
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

//#line 372 ".\gramatica.y"

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

		String path = new File(archivo_a_leer).getAbsolutePath().replaceAll(args[0],"");
        
        Output out = new Output(path);
        
        String printTs = ts.print();
        
        
        out.saveFile("codigo-lexico.txt", logger.getLexico());
		out.saveFile("codigo-sintetico.txt", logger.getSintactico());
		out.saveFile("tabla-de-simbolos.txt", printTs);
        
		System.out.println(printTs);
	}
}
//#line 869 "Parser.java"
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
//#line 17 ".\gramatica.y"
{ logger.logSuccess("[Parser] Programa correcto detectado"); }
break;
case 2:
//#line 18 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre del programa"); }
break;
case 3:
//#line 19 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un { antes de las sentencias del programa"); }
break;
case 4:
//#line 20 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un } al final de las sentencias del programa"); }
break;
case 5:
//#line 21 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias del programa"); }
break;
case 15:
//#line 46 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de lista de variables detectado"); }
break;
case 16:
//#line 47 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la lista de variables"); }
break;
case 17:
//#line 48 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo para la lista de variables"); }
break;
case 18:
//#line 49 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una variable o lista de variables"); }
break;
case 21:
//#line 58 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 22:
//#line 59 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 23:
//#line 60 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias en la funcion"); }
break;
case 40:
//#line 92 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 41:
//#line 93 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de funcion detectado"); }
break;
case 57:
//#line 121 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 58:
//#line 122 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 59:
//#line 123 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 60:
//#line 127 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 61:
//#line 128 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 75:
//#line 154 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 76:
//#line 155 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 77:
//#line 156 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 78:
//#line 157 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 79:
//#line 158 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo de return para la funcion"); }
break;
case 80:
//#line 159 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para la funcion"); }
break;
case 81:
//#line 160 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( luego del nombre de la funcion"); }
break;
case 82:
//#line 161 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) luego del nombre para la funcion"); }
break;
case 84:
//#line 166 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia return"); }
break;
case 85:
//#line 167 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia return"); }
break;
case 86:
//#line 168 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia return"); }
break;
case 87:
//#line 169 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una expresion en la sentencia return"); }
break;
case 90:
//#line 175 ".\gramatica.y"
{ logger.logError("[Parser] Hay mas de 2 parametros en la funcion"); }
break;
case 95:
//#line 185 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un identificador nombre para el parametro"); }
break;
case 96:
//#line 186 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un tipo valido para el parametro"); }
break;
case 97:
//#line 190 ".\gramatica.y"
{ logger.logSuccess("[Parser] Declaracion de constantes detectado"); }
break;
case 98:
//#line 191 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba ; al final de la declaracion de constantes"); }
break;
case 99:
//#line 192 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una lista de declaracion de constantes"); }
break;
case 103:
//#line 202 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una constante del lado derecho de la asignacion"); }
break;
case 104:
//#line 203 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba el simbolo asignacion en la declaracion de constantes"); }
break;
case 105:
//#line 204 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una asignacion en la declaracion de constantes"); }
break;
case 119:
//#line 227 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break detectada"); }
break;
case 120:
//#line 228 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia break con etiqueta detectada"); }
break;
case 121:
//#line 229 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 122:
//#line 230 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; luego de la sentencia break"); }
break;
case 123:
//#line 231 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una etiqueta en la sentencia break"); }
break;
case 124:
//#line 235 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia continue detectada"); }
break;
case 125:
//#line 236 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia continue"); }
break;
case 128:
//#line 245 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia do until detectada"); }
break;
case 129:
//#line 246 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia do"); }
break;
case 130:
//#line 247 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion para la sentencia do"); }
break;
case 131:
//#line 248 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ( en la condicion para la sentencia do"); }
break;
case 132:
//#line 249 ".\gramatica.y"
{ logger.logSuccess("[Parser] Se esperaba un ) en la condicion para la sentencia do"); }
break;
case 138:
//#line 267 ".\gramatica.y"
{ logger.logSuccess("[Parser] Asignacion detectada"); }
break;
case 139:
//#line 268 ".\gramatica.y"
{logger.logError("[Parser] Se espera una expresion del lado derecho de la asignacion");}
break;
case 140:
//#line 269 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la asignacion"); }
break;
case 141:
//#line 273 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia when detectada"); }
break;
case 142:
//#line 274 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 143:
//#line 275 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una condicion en la sentencia when"); }
break;
case 144:
//#line 276 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ; al final de la sentencia when"); }
break;
case 145:
//#line 277 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba la palabra reservada then en la sentencia when"); }
break;
case 146:
//#line 278 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaban sentencias dentro del when"); }
break;
case 149:
//#line 287 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then detectada"); }
break;
case 150:
//#line 288 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia if then else detectada"); }
break;
case 156:
//#line 303 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado derecho de la comparacion"); }
break;
case 157:
//#line 304 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un expresion del lado izquierdo de la comparacion"); }
break;
case 179:
//#line 350 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 180:
//#line 351 ".\gramatica.y"
{ logger.logSuccess("[Parser] Sentencia out detectada"); }
break;
case 181:
//#line 352 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba una cadena o identificador en la sentencia out"); }
break;
case 182:
//#line 353 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 183:
//#line 354 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 184:
//#line 355 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ( en la sentencia out"); }
break;
case 185:
//#line 356 ".\gramatica.y"
{ logger.logError("[Parser] Se esperaba un ) en la sentencia out"); }
break;
case 186:
//#line 357 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 187:
//#line 358 ".\gramatica.y"
{ logger.logError("[Parser] Se espera un ; al final de la sentencia out"); }
break;
case 189:
//#line 363 ".\gramatica.y"
{ constanteConSigno(val_peek(0).sval); }
break;
//#line 1318 "Parser.java"
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
