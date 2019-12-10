class Or extends OperatorExp
{
	Or(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "or";
	}

	String getInstruction()
	{
		return "or  " + expList.numOfExps();
	}
}