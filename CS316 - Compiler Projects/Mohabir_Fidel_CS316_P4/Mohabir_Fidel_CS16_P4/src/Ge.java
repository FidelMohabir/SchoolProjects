class Ge extends OperatorExp
{
	Ge(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return ">=";
	}

	String getInstruction()
	{
		return "ge";
	}
}