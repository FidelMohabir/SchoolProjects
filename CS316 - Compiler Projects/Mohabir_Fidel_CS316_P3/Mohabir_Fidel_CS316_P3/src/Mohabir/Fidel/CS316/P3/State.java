package Mohabir.Fidel.CS316.P3;
public enum State 
{ 
// final states    ordinal number  token accepted 

	Add,             // 0          +
	Sub,             // 1          -
	Mul,             // 2          *
	Div,             // 3          /
	Lt,              // 4          <
	Le,              // 5          <=
	Gt,              // 6          >
	Ge,              // 7          >=
	Eq,              // 8          =
	Id,              // 9          identifiers
	Int,             // 10         integers
	Float,           // 11         floats without exponentiation part
	FloatE,          // 12         floats with exponentiation part
	LParen,          // 13         (
	RParen,          // 14         )
	Quote,           // 15         '
	False,           // 16         #f, #F
	True,            // 17         #t, #T
	Period,          // 18         .

// non-final states                string recognized    

	Start,           // 19      the empty string
	PlusMinusPeriod, // 20      "+.", "-."
	E,               // 21      float parts ending with E or e
	EPlusMinus,      // 22      float parts ending with + or - in exponentiation part
	UnderscoreMinus, // 23      identifier parts ending with "_" or "-"
	Sharp,           // 24      #

// keyword states

	Keyword_define,
	Keyword_if,
	Keyword_cond,
	Keyword_else,
	Keyword_and,
	Keyword_or,
	Keyword_not,
	Keyword_equal,
	Keyword_car,
	Keyword_cdr,
	Keyword_cons,

	UNDEF;

	boolean isFinal()
	{
		return this.compareTo(State.Period) <= 0;  
	}

	boolean isOperator()
	{
		return ( this.compareTo(Eq) <= 0 || this.compareTo(Keyword_and) >= 0 );
	}

	boolean isCompOp()
	{
		return ( this.compareTo(Lt) >= 0 && this.compareTo(Eq) <= 0 );
	}
}

// By enumerating the final states first and then the non-final states,
// test for a final state can be done by testing if the state's ordinal number
// is less than or equal to that of Period.