class FunDef
{
	Header header;
	Exp exp;
	
	FunDef(Header h, Exp e)
	{
		header = h;
		exp = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <fun def>");		
		header.printParseTree(indent1);
		exp.printParseTree(indent1);
	}

	public void emitInstructions() {
		// TODO Auto-generated method stub
		
	}
}