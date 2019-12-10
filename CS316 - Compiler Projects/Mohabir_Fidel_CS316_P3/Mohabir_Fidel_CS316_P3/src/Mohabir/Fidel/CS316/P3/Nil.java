package Mohabir.Fidel.CS316.P3;
class Nil extends SExp
{
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " ()");
	}
	
	void emitInstructions() {
		IO.displayln(" push nil");
	}
}