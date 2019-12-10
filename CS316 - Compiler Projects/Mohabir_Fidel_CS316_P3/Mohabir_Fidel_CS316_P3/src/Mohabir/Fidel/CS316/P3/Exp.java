package Mohabir.Fidel.CS316.P3;
abstract class Exp
{
	
	volatile static int i = 0;
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
	}
	abstract void emitInstructions();
}