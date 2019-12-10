class ConsExp extends SExp
{
	SExp carExp;
	SExp cdrExp;

	ConsExp(SExp carE, SExp cdrE)
	{
		carExp = carE;
		cdrExp = cdrE;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		carExp.printParseTree(indent1);
		cdrExp.printParseTree(indent1);
	}

	void emitInstructions()
	{
		carExp.emitInstructions();
		cdrExp.emitInstructions();
		IO.displayln(Compiler.indent + "cons");
	}
}