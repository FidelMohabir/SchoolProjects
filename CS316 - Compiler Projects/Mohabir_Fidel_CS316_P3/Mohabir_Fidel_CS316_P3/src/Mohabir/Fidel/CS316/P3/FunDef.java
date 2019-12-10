package Mohabir.Fidel.CS316.P3;
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
}