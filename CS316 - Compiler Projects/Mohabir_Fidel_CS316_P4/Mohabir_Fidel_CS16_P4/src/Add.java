class Add extends OperatorExp
{	
	Add(ExpList e)
	{
		expList = e;
	}
	
	String getOp()
	{
		return "+";
	}

	String getInstruction()
	{
		return "add  " + expList.numOfExps();
	}
}