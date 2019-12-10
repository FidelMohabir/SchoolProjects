class Int extends Atom
{
	int intElem;
	
	Int(int i)
	{
		intElem = i;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " " + intElem);
	}

	void printParseTree1(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree1(indent);
		IO.displayln(indent2 + indent2.length() + " " + intElem);
	}

	void emitInstructions()
	{
		IO.displayln(Compiler.indent + "push  " + intElem);
	}
}