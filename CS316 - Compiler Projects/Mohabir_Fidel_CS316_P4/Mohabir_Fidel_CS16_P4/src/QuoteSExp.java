class QuoteSExp extends Exp
{
	SExp sExp;

	QuoteSExp(SExp se)
	{
		sExp = se;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " '");
		sExp.printParseTree(indent1);
	}

	void emitInstructions()
	{
		sExp.emitInstructions();
	}
}