class FunDefList
{
	FunDef funDef;
	FunDefList funDefList;
	
	FunDefList(FunDef f, FunDefList fl)
	{
		funDef = f;
		funDefList = fl;
	}

	void printParseTree(String indent)
	{
		FunDefList p = this;
		do
		{
			p.funDef.printParseTree(indent);
			IO.displayln("\n--------------------\n");
			p = p.funDefList;
		} while ( p != null );
	}
	
	void emitInstructions()
	{
		FunDefList p = this;
		do
		{
			
			p.funDef.emitInstructions();
			p = p.funDefList;
		} while ( p != null );
	}
}