package Mohabir.Fidel.CS316.P3;
abstract class OperatorExp extends ListExp
{
	ExpList expList; // This is null if <exp list> is empty.

	abstract String getOp();

	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";
		String indent3 = indent+"   ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " <operator exp>");
		IO.displayln(indent3 + indent3.length() + " " + getOp());
		if ( expList != null )
			expList.printParseTree(indent3);
	}
	
	void emitInstructions() {
		expList.emitInstructions();
	}
}