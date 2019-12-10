package Mohabir.Fidel.CS316.P3;
abstract class CaseExp
{
	volatile static int i = 1;
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <case exp>");
	}
	
	abstract void emitInstructions();
}