package Mohabir.Fidel.CS316.P3;
class Header
{
	String funName;
	ParameterList parameterList; // This is null if <parameter list> is empty.
	
	Header(String s, ParameterList p)
	{
		funName = s;
		parameterList = p;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <header>");
		IO.displayln(indent1 + indent1.length() + " <fun name> " + funName);
		if ( parameterList != null )	
			parameterList.printParseTree(indent1);
	}
}