class Floatp extends Atom
{
	float floatElem;
	
	Floatp(float f)
	{
		floatElem = f;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " " + floatElem);
	}

	void printParseTree1(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree1(indent);
		IO.displayln(indent2 + indent2.length() + " " + floatElem);
	}

	void emitInstructions()
	{
		IO.displayln(Compiler.indent + "push  " + floatElem);
	}
}