class Le extends OperatorExp
{
	Le(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "<=";
	}

	String getInstruction()
	{
		return "le";
	}
}