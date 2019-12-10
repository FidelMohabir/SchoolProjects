public abstract class Compiler extends Parser
{
	public static int label = 0;
	public static final String indent = "\t";
	

	public static void main(String argv[])
	{
		// argv[0]: input file containing an expression of category <exp>
		// argv[1]: output file containing instruction stream or lex/syntax error messages

		setIO( argv[0], argv[1] );
		setLex();
		
		getToken();
		//Exp exp = exp(); // build a parse tree	
		Apostrophe apos = Apostrophe();
		if ( ! t.isEmpty() )
			IO.displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
			apos.emitInstructions();
			
		closeIO();
	}
}