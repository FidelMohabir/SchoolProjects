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

	void emitInstructions(int OUT)
	{
		CaseList p = this;
		do
		{
			p.caseExp.emitInstructions(OUT);
			p = p.caseList;
		} while ( p != null );
	}
}