package Mohabir.Fidel.CS316.P3;
class Cond extends ListExp
{
	CaseList caseList;
	
	Cond(CaseList c)
	{
		caseList = c;
	}
	
	void printParseTree(String indent)
	{
		String indent2 = indent+"  ";

		super.printParseTree(indent);
		IO.displayln(indent2 + indent2.length() + " cond");
		IO.displayln(indent2 + indent2.length() + " <case list>");		
		caseList.printParseTree(indent2+" ");
	}
	
	void emitInstructions() {
		caseList.emitInstructions();
		IO.displayln("1:");
	}
}