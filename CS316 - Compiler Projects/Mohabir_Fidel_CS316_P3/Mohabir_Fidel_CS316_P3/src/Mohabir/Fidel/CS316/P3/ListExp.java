package Mohabir.Fidel.CS316.P3;
abstract class ListExp extends Exp
{
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <list exp>");
	}
	
	abstract void emitInstructions();
}