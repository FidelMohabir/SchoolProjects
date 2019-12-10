class Div extends OperatorExp
{
	Div(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "/";
	}

	String getInstruction()
	{
		return "div  " + expList.numOfExps();
	}
}