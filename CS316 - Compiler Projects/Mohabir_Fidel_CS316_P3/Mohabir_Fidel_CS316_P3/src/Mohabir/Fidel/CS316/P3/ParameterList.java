package Mohabir.Fidel.CS316.P3;
class ParameterList
{
	String id;
	ParameterList parameterList;
	
	ParameterList(String s, ParameterList p)
	{
		id = s;
		parameterList = p;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		IO.displayln(indent + indent.length() + " <parameter list>");
		ParameterList p = this;
		do
		{
			IO.displayln(indent1 + indent1.length() + " <parameter> " + p.id);
			p = p.parameterList;
		} while ( p != null );
	}
}