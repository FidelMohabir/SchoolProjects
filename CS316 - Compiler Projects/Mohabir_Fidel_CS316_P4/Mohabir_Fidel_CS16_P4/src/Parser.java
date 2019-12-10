/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<fun def list> --> <fun def> | <fun def> <fun def list> 
<fun def> --> "(" "define" <header> <exp> ")" 
<header> --> "(" <fun name> <parameter list> ")" 
<fun name> --> <id> 
<parameter list> --> epsilon | <id> <parameter list> 
<exp> --> <atom> | <quote> <S exp> | <list exp> 
<atom> --> <id> | <int> | <float> | <floatE> | <false> | <true> 
<S exp> --> <atomic S exp> | "(" [ <S exp> <period> <S exp> ] ")" 
<atomic S exp> --> <atom> 
<list exp> --> "(" <list exp inside> ")" 
<list exp inside> --> <if> | <cond> | <fun call> | <operator exp> 
<if> --> "if" <exp> <exp> <exp> 
<cond> --> "cond" <case list> 
<case list> --> <case exp> | <case exp> <case list> 
<case exp> --> "(" ( <case> | <else case> ) ")" 
<case> --> <exp> <exp> 
<else case> --> "else" <exp> 
<fun call> --> <fun name> <exp list> 
<operator exp> --> <operator> <exp list> 
<operator> --> + | - | * | / | < | <= | > | >= | = | "and" | "or" | "not" | "equal" | "car" | "cdr" | "cons" 
<exp list> --> epsilon | <exp> <exp list> 

The definitions of the tokens are given in the lexical analyzer class file "LexAnalyzer.java".

The following variables and functions of the "LexAnalyzer" class are used:

static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token
static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

The program will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 
The string variable "indent" will keep track of the correct number of blanks for indentation and
will be passed to parse functions corresponding to syntactic categories.

**/


import java.util.*;

public abstract class Parser extends LexAnalyzer
{
	static final False false_ = new False();
	static final True  true_  = new True();
	static final Nil   nil_   = new Nil();

	static boolean syntaxErrorFound = false;

 
	public static FunDefList funDefList()
	
	// <fun def list> --> <fun def> | <fun def> <fun def list>
		
	{
		FunDef funDef = funDef();
		if ( state == State.LParen )
		{
			FunDefList funDefList = funDefList();
			return new FunDefList(funDef, funDefList);
		}
		else
			return new FunDefList(funDef, null);
	}
	
	public static Apostrophe Apostrophe() {
		return null;
	}

	public static FunDef funDef()

	// <fun def> --> "(" "define" <header> <exp> ")"

	{
		if ( state == State.LParen )
		{
			getToken();
			if ( state == State.Keyword_define )
			{
				getToken();
				Header header = header();
				Exp exp = exp();
				if ( state == State.RParen )
				{
					getToken();
					return new FunDef(header, exp);
				}
				else
				{
					errorMsg(3);
					return null;
				}
			}
			else
			{
				errorMsg(2);
				return null;
			}
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static Header header()

	// <header> --> "(" <fun name> <parameter list> ")" 
	// <fun name> --> <id> 

	{
		if ( state == State.LParen )
		{
			getToken();
			if ( state == State.Id )
			{
				String funName = t;
				getToken();
				Exp.funcMap.put(funName, ++Exp.k);
				ParameterList parameterList = parameterList();
				if ( state == State.RParen )
				{
					getToken();
					return new Header(funName, parameterList);
				}
				else
				{
					errorMsg(3);
					return null;
				}
			}
			else
			{
				errorMsg(4);
				return null;
			}
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static ParameterList parameterList()

	// <parameter list> --> epsilon | <id> <parameter list> 

	{
		if ( state == State.Id )
		{
			String id = t;
			getToken();
			ParameterList parameterList = parameterList();
			return new ParameterList(id, parameterList);
		}		
		else // <parameter list> is epsilon
			return null;	
	}
	
	public static Exp exp()

	// <exp> --> <atom> | <quote> <S exp> | <list exp>
	// <atom> --> <id> | <int> | <float> | <floatE> | <false> | <true>
	
	{
		switch ( state )
		{
			case Id:
				Id id = new Id(t);
				getToken();				
				return id;
				
			case Int:
				Int intElem = new Int(Integer.parseInt(t));
				getToken();
				return intElem;
				
			case Float: case FloatE:
				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return floatElem;

			case False:
				getToken();
				return false_;

			case True:
				getToken();
				return true_;

			case Quote:
				getToken();
				SExp sExp = sExp();
				return new QuoteSExp(sExp);

			case LParen:
				getToken();
				return listExp();
			
			default:
				errorMsg(6);
				return null;
		}
	}

	static boolean beginsSExp()
	{
		return state == State.Id || state == State.Int || state == State.Float || state == State.FloatE ||
		       state == State.False || state == State.True || state == State.LParen;
	}

	public static SExp sExp()

	// <S exp> --> <atomic S exp> | "(" [ <S exp> <period> <S exp> ] ")"
	// <atomic S exp> --> <atom>

	{
		switch ( state )
		{
			case Id:
				Id id = new Id(t);
				getToken();				
				return new AtomicSExp(id);
				
			case Int:
				Int intElem = new Int(Integer.parseInt(t));
				getToken();
				return new AtomicSExp(intElem);
				
			case Float: case FloatE:
				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return new AtomicSExp(floatElem);

			case False:
				getToken();
				return new AtomicSExp(false_);

			case True:
				getToken();
				return new AtomicSExp(true_);

			case LParen:
				getToken();
				if ( beginsSExp() )
				{
					ConsExp consExp = consExp();
					if ( state == State.RParen )
					{
						getToken();
						return consExp;
					}
					else
					{
						errorMsg(3);
						return null;
					}
				}
				else if ( state == State.RParen )
				{
					getToken();
					return nil_;
				}
				else
				{
					errorMsg(7);
					return null;
				}

			default:
				errorMsg(8);
				return null;
		}
	}

	public static ConsExp consExp()

	// <S exp> <period> <S exp>

	{
		SExp sExp1 = sExp();
		if ( state == State.Period )
		{
			getToken();
			SExp sExp2 = sExp();
			return new ConsExp(sExp1, sExp2);
		}
		else
		{
			errorMsg(9);
			return null;
		}
	}

	public static ListExp listExp()

	// <list exp> --> "(" <list exp inside> ")"

	{
		ListExp listExpInside = listExpInside();
		if ( state == State.RParen )
		{
			getToken();
			return listExpInside;
		}
		else
		{
			errorMsg(3);
			return null;
		}
	}

	public static ListExp listExpInside()
	
	// <list exp inside> --> <if> | <cond> | <fun call> | <operator exp>

	{
		switch ( state )
		{
		case Keyword_if:
			getToken();
			return if_();
		
		case Keyword_cond:
			getToken();
			return cond();

		case Id:
			return funCall();

		case Add: case Sub: case Mul: case Div: case Lt: case Le: case Gt: case Ge: case Eq:
		case Keyword_and: case Keyword_or: case Keyword_not: case Keyword_equal:
		case Keyword_car: case Keyword_cdr: case Keyword_cons:	

			return operatorExp();

		default:
			errorMsg(5);
			return null;
		}
	}

	public static If if_()

	// <if> --> "if" <exp> <exp> <exp>

	{
		Exp exp1 = exp();
		Exp exp2 = exp();
		Exp exp3 = exp();
		return new If(exp1, exp2, exp3);
	}

	public static Cond cond()

	// <cond> --> "cond" <case list> 

	{
		CaseList caseList = caseList();
		return new Cond(caseList);
	}

	public static CaseList caseList()

	// <case list> --> <case exp> | <case exp> <case list>

	{
		CaseExp caseExp = caseExp();
		if ( state == State.LParen )
		{
			CaseList caseList = caseList();
			return new CaseList(caseExp, caseList);
		}
		else
			return new CaseList(caseExp, null);
	}

	public static CaseExp caseExp()

	// <case exp> --> "(" ( <exp> <exp> | "else" <exp> ) ")"

	{
		if ( state == State.LParen )
		{
			getToken();
			CaseExp caseExp;
			if ( state == State.Keyword_else )
			{
				getToken();
				Exp exp = exp();
				caseExp = new ElseCase(exp);
			}
			else
			{
				Exp exp1 = exp();
				Exp exp2 = exp();
				caseExp = new Case(exp1, exp2);
			}
			if ( state == State.RParen )
			{
				getToken();
				return caseExp;
			}
			else
			{
				errorMsg(3);
				return null;
			}
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static FunCall funCall()

	// <fun call> --> <fun name> <exp list>

	{
		String funName = t;
		getToken();
		ExpList expList= expList();
		return new FunCall(funName, expList);
	}

	public static OperatorExp operatorExp()

	// <operator exp> --> <operator> <exp list>
	// <operator> --> + | - | * | / | < | <= | > | >= | = | "and" | "or" | "not" | "equal" | "car" | "cdr" | "cons"
	

	{
		State op_state = state;
		getToken();
		ExpList expList = expList();

		switch ( op_state )
		{
		case Add: return new Add(expList);
		case Sub: return new Sub(expList);
		case Mul: return new Mul(expList);
		case Div: return new Div(expList);
		case Lt:  return new Lt(expList);
		case Le:  return new Le(expList);
		case Gt:  return new Gt(expList);
		case Ge:  return new Ge(expList);
		case Eq:  return new Eq(expList);
		case Keyword_and:   return new And(expList);
		case Keyword_or:    return new Or(expList);
		case Keyword_not:   return new Not(expList);
		case Keyword_equal: return new Equal(expList);
		case Keyword_car:   return new Car(expList);
		case Keyword_cdr:   return new Cdr(expList);
		default: /* case Keyword_cons: */  return new Cons(expList);
		}
	}

	static boolean beginsExp()
	{
		return ( state.compareTo(State.Id) >= 0 && state.compareTo(State.LParen) <= 0 ) ||
		       ( state.compareTo(State.Quote) >= 0 && state.compareTo(State.True) <= 0 );
	}

	public static ExpList expList()

	// <exp list> --> epsilon | <exp> <exp list>

	{
		if ( beginsExp() )
		{
			Exp exp = exp();
			ExpList expList = expList();
			return new ExpList(exp, expList);
		}		
		else // <exp list> is epsilon
			return null;	
	}

	public static void errorMsg(int i)
	{
		syntaxErrorFound = true;

		display(t + " : Syntax Error, unexpected symbol where");

		switch ( i )
		{
		case 1:	displayln(" ( expected"); return;
		case 2: displayln(" define expected"); return;
		case 3: displayln(" ) expected"); return;
		case 4: displayln(" function name expected"); return;
		case 5: displayln(" if, cond, function name, or operator expected"); return;		
		case 6: displayln(" identifier, integer, float, bool literal, quote, or ( expected"); return;
		case 7: displayln(" identifier, integer, float, bool literal, or ) expected"); return;
		case 8: displayln(" identifier, integer, float, bool literal, or ( expected"); return;
		case 9: displayln(" . expected"); return;
		}
	}

	public static void main(String argv[])

	// argv[0]: input file containing a string of category <fun def list>
	// argv[1]: output file displaying a parse tree or lex/syntax error messages

	{
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		FunDefList funDefList = funDefList();
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
			funDefList.printParseTree("");
		
		closeIO();
	}
}