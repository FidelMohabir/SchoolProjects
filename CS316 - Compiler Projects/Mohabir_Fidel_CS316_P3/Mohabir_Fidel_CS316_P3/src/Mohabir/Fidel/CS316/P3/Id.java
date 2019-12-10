package Mohabir.Fidel.CS316.P3;
class Id extends Atom
{
	String id;
	
	Id(String s)
	{
		id = s;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " " + id);
	}

	void printParseTree1(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree1(indent);
		IO.displayln(indent2 + indent2.length() + " " + id);
	}
	
	void emitInstructions() {
		IO.displayln(" push " + id);
	}
}