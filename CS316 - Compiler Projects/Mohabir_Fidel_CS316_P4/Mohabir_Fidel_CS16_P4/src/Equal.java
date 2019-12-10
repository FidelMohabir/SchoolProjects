class Equal extends OperatorExp
{
	Equal(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "equal";
	}

	String getInstruction()
	{
		return "equal";
	}
}