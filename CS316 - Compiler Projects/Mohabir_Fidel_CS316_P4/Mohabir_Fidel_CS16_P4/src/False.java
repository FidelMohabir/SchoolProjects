class False extends Atom
{	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " #f");
	}

	void printParseTree1(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree1(indent);
		IO.displayln(indent2 + indent2.length() + " #f");
	}

	void emitInstructions()
	{
		IO.displayln(Compiler.indent + "push  " + "#f");
	}
}