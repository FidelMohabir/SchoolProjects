abstract class CaseExp
{
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <case exp>");
	}

	abstract void emitInstructions(int OUT);
}