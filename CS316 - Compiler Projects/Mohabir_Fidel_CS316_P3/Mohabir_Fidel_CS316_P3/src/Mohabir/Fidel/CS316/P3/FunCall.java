package Mohabir.Fidel.CS316.P3;
class FunCall extends ListExp
{
	String funName;
	ExpList expList; // This is null if <exp list> is empty.
	
	FunCall(String s, ExpList e)
	{
		funName = s;
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";
		String indent3 = indent+"   ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " <fun call>");
		IO.displayln(indent3 + indent3.length() + " <fun name> " + funName);
		if ( expList != null )
			expList.printParseTree(indent3);
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln("funname");
	}
}