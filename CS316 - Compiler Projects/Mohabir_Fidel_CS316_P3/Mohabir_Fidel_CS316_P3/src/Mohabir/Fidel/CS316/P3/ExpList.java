package Mohabir.Fidel.CS316.P3;
class ExpList
{
	Exp exp;
	ExpList expList;
	
	ExpList(Exp e, ExpList el)
	{
		exp = e;
		expList = el;
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
	
	void emitInstructions() {
		exp.emitInstructions();
		if(expList != null) {
			expList.emitInstructions();
		}
		Exp.i++;
	}
}