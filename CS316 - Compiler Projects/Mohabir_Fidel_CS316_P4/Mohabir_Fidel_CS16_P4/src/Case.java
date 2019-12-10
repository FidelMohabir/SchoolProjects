class Case extends CaseExp
{
	Exp exp1;
	Exp exp2;
	
	Case(Exp e1, Exp e2)
	{
		exp1 = e1;
		exp2 = e2;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);	
		exp1.printParseTree(indent1);
		exp2.printParseTree(indent1);
	}

	void emitInstructions(int OUT)
	{
		exp1.emitInstructions();

		int CASE_LABEL = ++Compiler.label;
		IO.displayln(Compiler.indent + "if_f goto " + CASE_LABEL);

		exp2.emitInstructions();

		IO.displayln(Compiler.indent + "goto " + OUT);
		IO.displayln(CASE_LABEL + ":");
	}
}