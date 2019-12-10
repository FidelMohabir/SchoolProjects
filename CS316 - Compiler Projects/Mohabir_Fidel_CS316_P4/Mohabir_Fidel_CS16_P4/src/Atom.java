abstract class Atom extends Exp
{
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <atom>");
	}

	void printParseTree1(String indent)
	{
		String indent1 = indent+" ";

		IO.displayln(indent1 + indent1.length() + " <atom>");
	}
}