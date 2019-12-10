package Mohabir.Fidel.CS316.P3;
class CaseList
{
	CaseExp caseExp;
	CaseList caseList;
	
	CaseList(CaseExp ce, CaseList cl)
	{
		caseExp = ce;
		caseList = cl;
	}
	
	void printParseTree(String indent)
	{
		CaseList p = this;
		do
		{
			p.caseExp.printParseTree(indent);
			p = p.caseList;
		} while ( p != null );
	}
	
	void emitInstructions() {
		caseExp.emitInstructions();
		if (caseList != null) {
			caseList.emitInstructions();
		}
	}
}