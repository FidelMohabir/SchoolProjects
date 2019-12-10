class If extends ListExp
{
	Exp exp1;
	Exp exp2;
	Exp exp3;
	
	If(Exp e1, Exp e2, Exp e3)
	{
		exp1 = e1;
		exp2 = e2;
		exp3 = e3;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " if");		
		exp1.printParseTree(indent2);
		exp2.printParseTree(indent2);
		exp3.printParseTree(indent2);
	}

	void emitInstructions()
	{
		exp1.emitInstructions();

		int ELSE = ++Compiler.label;
		IO.displayln(Compiler.indent + "if_f goto " + ELSE);

		exp2.emitInstructions();

		int OUT = ++Compiler.label;
		IO.displayln(Compiler.indent + "goto " + OUT);
		IO.displayln(ELSE+":");

		exp3.emitInstructions();

		IO.displayln(OUT + ":");
	}
}