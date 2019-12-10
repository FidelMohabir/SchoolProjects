class ExpList
{
	Exp exp;
	ExpList expList;
	
	ExpList(Exp e, ExpList el)
	{
		exp = e;
		expList = el;
	}

	int numOfExps()
	{
		ExpList p = this;
		int i = 0;
		while ( p != null )
		{
			i++;
			p = p.expList;
		}
		return i;
	}
	
	void printParseTree(String indent)
	{
		ExpList p = this;
		do
		{
			p.exp.printParseTree(indent);
			p = p.expList;
		} while ( p != null );
	}

	void emitInstructions()
	{
		ExpList p = this;
		do
		{
			p.exp.emitInstructions();
			p = p.expList;
		} while ( p != null );
	}
}