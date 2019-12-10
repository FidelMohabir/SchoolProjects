class Lt extends OperatorExp
{
	Lt(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "<";
	}

	String getInstruction()
	{
		return "lt";
	}
}