package Mohabir.Fidel.CS316.P3;
public abstract class Compiler extends Parser
{
	public static void main(String argv[])
	{
		// argv[0]: input file containing an expression of category <exp>
		// argv[1]: output file containing instruction stream or lex/syntax error messages

		setIO( argv[0], argv[1] );
		setLex();
		
		getToken();
		Exp exp = exp(); // build a parse tree		                    
		if ( ! t.isEmpty() )
			IO.displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
			exp.emitInstructions();

		closeIO();
	}
}