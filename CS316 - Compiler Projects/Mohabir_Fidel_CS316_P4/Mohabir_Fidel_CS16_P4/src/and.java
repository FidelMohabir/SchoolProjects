class And extends OperatorExp
{
	And(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "and";
	}

	String getInstruction()
	{
		return "and  " + expList.numOfExps();
	}
}