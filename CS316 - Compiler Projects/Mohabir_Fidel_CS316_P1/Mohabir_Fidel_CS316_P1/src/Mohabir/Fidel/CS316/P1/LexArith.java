/*
 * Fidel Mohabir
 * CS 316 Project 1
 * CunyID 12077025
 */

package Mohabir.Fidel.CS316.P1;

import java.util.Hashtable;

/**
 

This class is a lexical analyzer for the tokens defined by the grammar:

⟨letter⟩ → a | b | ... | z | A | B | ... | Z 
⟨digit⟩ → 0 | 1 | ... | 9 
⟨basic id⟩ → ⟨letter⟩ {⟨letter⟩ | ⟨digit⟩} 
⟨letters and digits⟩ → {⟨letter⟩ | ⟨digit⟩}+ 
⟨id⟩ → ⟨basic id⟩ { ("_" | "−") ⟨letters and digits⟩ }    // Note: "_" is the underscore char, "−" is the hyphen (i.e. minus) char. 
⟨int⟩ → [+|−] {⟨digit⟩}+ 
⟨float⟩ → [+|−] ( {⟨digit⟩}+ "." {⟨digit⟩}  |  "." {⟨digit⟩}+ ) 
⟨floatE⟩ → ⟨float⟩ (e|E) [+|−] {⟨digit⟩}+ 
⟨add⟩ → + 
⟨sub⟩ → − 
⟨mul⟩ → * 
⟨div⟩ → / 
⟨lt⟩ → "<" 
⟨le⟩ → "<=" 
⟨gt⟩ → ">" 
⟨ge⟩ → ">=" 
⟨eq⟩ → "=" 
⟨LParen⟩ → "(" 
⟨RParen⟩ → ")" 
⟨quote⟩ → " ' "    // the single quote char 
⟨false⟩ → "#f" | "#F" 
⟨true⟩ → "#t" | "#T" 
⟨period⟩ → "." 

This class implements a DFA that will accept the above tokens.

The DFA states are represented by the Enum type "State".
The DFA has the following 10 final states represented by enum-type literals:

state     token accepted

Id               identifer
Int              integers
Float            floats without exponentiation part
FloatE           floats with exponentiation part
Add              +
Sub              -
Mul              *
Div              /
LParen           (
RParen           )
Lt				 <
Gt               >
Le 			  	 <=
Ge				 >=
Eq				 =
Quote 			 '
False			 #F or #f 
True 			 #T or #t 
Period			 .
Keyword_define   define
Keyword_if 	  	 if
Keyword_cond     cond
Keyword_else     else
Keyword_and      and
Keyword_or       or
Keyword_not      not
Keyword_equal    equal
Keyword_car      car
Keyword_cdr      cdr
Keyword_cons     cons

The DFA also uses the following 4 non-final states:

state      			string recognized

Start      			the empty string
PlusMinusPeriod		float parts ending with a . after an add or sub
E          			float parts ending with E or e
EPlusMinus 			float parts ending with + or - in exponentiation part
UnderScoreMinus		identifier ending with _ or -
Sharp				parts ending with # 

The function "driver" operates the DFA. 
The function "nextState" returns the next state given the current state and the input character.

To recognize a different token set, modify the following:

  enum type "State" and function "isFinal"
  function "nextState" 

The functions "driver", "getToken" remain the same.

**/


public abstract class LexArith extends IO
{
	public enum State 
       	{ 
	  // non-final states     ordinal number

		Start,             // 0
		PlusMinusPeriod,   // 1
		E,                 // 2
		EPlusMinus,        // 3
		UnderScoreMinus,   // 4
		Sharp,			   // 5
		
		

	  // final states

		Id,                // 6
		Int,               // 7
		Float,             // 8
		FloatE,            // 9
		Add,               // 10
		Sub,               // 11
		Mul,               // 12
		Div,               // 13
		LParen,            // 14
		RParen,            // 15
		Lt,				   // 16
		Gt,                // 17
		Le, 			   // 18
		Ge,				   // 19
		Eq,				   // 20
		Quote, 			   // 21
		False,			   // 22
		True, 			   // 23
		Period,			   // 24
		Keyword_define,    // 25
		Keyword_if, 	   // 26
		Keyword_cond,      // 27
		Keyword_else,      // 28
		Keyword_and,       // 29
		Keyword_or,        // 30
		Keyword_not,       // 31
		Keyword_equal,     // 32
		Keyword_car,       // 33
		Keyword_cdr,       // 34
		Keyword_cons,      // 35
		

		UNDEF;

		private boolean isFinal()
		{
			return ( this.compareTo(State.Id) >= 0 );  
		}	
	}

	// By enumerating the non-final states first and then the final states,
	// test for a final state can be done by testing if the state's ordinal number
	// is greater than or equal to that of Id.

	// The following variables of "IO" class are used:

	//   static int a; the current input character
	//   static char c; used to convert the variable "a" to the char type whenever necessary

	public static String t; // holds an extracted token
	public static State state; // the current state of the FA

	private static int driver()
	// This is the driver of the FA. 
	// If a valid token is found, assigns it to "t" and returns 1.
	// If an invalid token is found, assigns it to "t" and returns 0.
	// If end-of-stream is reached without finding any non-whitespace character, returns -1.

	{
		
		/*
		 * Hashtable that checks if the keyword is stored and if so it returns a special character
		 * that is defined in the ID case of nextState
		 */
		Hashtable<String, Character> keywords = new Hashtable<String, Character>();
		keywords.put("define", '{');
		keywords.put("if", '}');
		keywords.put("cond", '[');
		keywords.put("else", ']');
		keywords.put("and", '?');
		keywords.put("or", '@');
		keywords.put("not", '|');
		keywords.put("equal", '$');
		keywords.put("car", '&');
		keywords.put("cdr", '^');
		keywords.put("cons", '!');
		
		State nextSt; // the next state of the FA
		t = "";
		state = State.Start;

		if ( Character.isWhitespace((char) a) )
			a = getChar(); // get the next non-whitespace character
		if ( a == -1 ) // end-of-stream is reached
			return -1;

		while ( a != -1 ) // do the body if "a" is not end-of-stream
		{
			c = (char) a;
			nextSt = nextState( state, c, false); //boolean set to false so it doesn't accidentally go into keyword state
			if ( nextSt == State.UNDEF ) // The FA will halt.
			{
				if ( state.isFinal() ) {
					if (keywords.containsKey(t)) { //if the string t is a keyword 
						state = nextState(state, keywords.get(t), true); //change state from ID to that keyword
					}
					return 1; // valid token extracted
					
				}
				else // "c" is an unexpected character
				{
					t = t+c;
					a = getNextChar();
					return 0; // invalid token found
				}
			}
			else // The FA will go on.
			{
				state = nextSt;
				t = t+c;
				a = getNextChar();
			}
		}

		// end-of-stream is reached while a token is being extracted
		
		/*
		 * In the case that the keyword is the very last thing in an input file, the while loop
		 * is broken before it can check if the string contains a keyword. Since this piece of code
		 * remedies that exact situation, I inserted a check here too.
		 */
		if ( state.isFinal() ) {
			if (keywords.containsKey(t)) { //if the string t is a keyword 
				state = nextState(state, keywords.get(t), true); //change state from ID to that keyword
			}
			return 1; // valid token extracted
		}
		else
			return 0; // invalid token found
	} // end driver

	public static void getToken()

	// Extract the next token using the driver of the FA.
	// If an invalid token is found, issue an error message.

	{
		int i = driver();
		if ( i == 0 )
			displayln(t + " : Lexical Error, invalid token");
	}

	private static State nextState(State s, char c, boolean b)

	// Returns the next state of the FA given the current state and input char;
	// if the next state is undefined, UNDEF is returned.

	{
		switch( state )
		{
		case Start:
			if ( Character.isLetter(c) )
				return State.Id;
			else if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '+' )
				return State.Add;
			else if ( c == '-' )
				return State.Sub;
			else if ( c == '*' )
				return State.Mul;
			else if ( c == '/' )
				return State.Div;
			else if ( c == '(' )
				return State.LParen;
			else if ( c == ')' )
				return State.RParen;
			else if ( c == '<')
				return State.Lt;
			else if (c == '>') 
				return State.Gt;
			else if (c == '=')
				return State.Eq;
			else if (c == '\'')
				return State.Quote;
			else if (c == '#')
				return State.Sharp;
			else if (c == '.')
				return State.Period;
			else
				return State.UNDEF;
		case Id:
			if ( Character.isLetterOrDigit(c) )
				return State.Id;
			
			/*
			 * If the input string is a keyword then these are the DFA 
			 * transition functions in nextState. I added the boolean b 
			 * for redudancy control. (eg. if the input string is "true?"
			 * it will not transition to the the keyword_and state unless
			 * the FA has halted and is in a final state)
			 */
			else if (c == '{' && b)
				return State.Keyword_define;
			else if (c == '}' && b)
				return State.Keyword_if;
			else if (c == '[' && b)
				return State.Keyword_cond;
			else if (c == ']' && b)
				return State.Keyword_else;
			else if (c == '?' && b)
				return State.Keyword_and;
			else if (c == '@' && b)
				return State.Keyword_or;
			else if (c == '|' && b)
				return State.Keyword_not;
			else if (c == '$' && b)
				return State.Keyword_equal;
			else if (c == '&' && b)
				return State.Keyword_car;
			else if (c == '^'  && b)
				return State.Keyword_cdr;
			else if (c == '!' && b)
				return State.Keyword_cons;
			else if (c == '_' || c == '-')
				return State.UnderScoreMinus;
			else
				return State.UNDEF;
		case Int:
			if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '.' )
				return State.Float;
			else
				return State.UNDEF;
		case Float:
			if ( Character.isDigit(c) )
				return State.Float;
			else if ( c == 'e' || c == 'E' )
				return State.E;
			else
				return State.UNDEF;
		case FloatE:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
		case Add:
			if (Character.isDigit(c))
				return State.Int;
			else if (c == '.')
				return State.PlusMinusPeriod;
			else
				return State.UNDEF;
		case Sub:
			if (Character.isDigit(c))
				return State.Int;
			else if (c == '.')
				return State.PlusMinusPeriod;
			else
				return State.UNDEF;
		case Lt:
			if (c == '=')
				return State.Le;
			else
				return State.UNDEF;
		case Gt:
			if (c == '=')
				return State.Ge;
			else
				return State.UNDEF;
		case Sharp:
			if (c ==  'f' || c == 'F')
				return State.False;
			else if (c == 't' || c == 'T') 
				return State.True;
		case Period:
			if ( Character.isDigit(c) )
				return State.Float;
			else
				return State.UNDEF;
		case PlusMinusPeriod:
			if ( Character.isDigit(c))
				return State.Float;
			else 
				return State.UNDEF;
		case E:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else if ( c == '+' || c == '-' )
				return State.EPlusMinus;
			else
				return State.UNDEF;
		case EPlusMinus:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
		case UnderScoreMinus:
			if (Character.isLetterOrDigit(c))
				return State.Id;
			else
				return State.UNDEF;
		
		
		default:
			return State.UNDEF;
		}
	} // end nextState

	public static void main(String argv[])

	{		
		// argv[0]: input file containing source code using tokens defined above
		// argv[1]: output file displaying a list of the tokens 

		setIO( argv[0], argv[1] );
		
		int i;

		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 )
				displayln( t+"   : "+state.toString() );
			else if ( i == 0 )
				displayln( t+" : Lexical Error, invalid token");
		} 

		closeIO();
	}
} 
