abstract class SExp
{
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <S exp>");
	}

	abstract void emitInstructions();
}