class Gt extends OperatorExp
{
	Gt(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return ">";
	}

	String getInstruction()
	{
		return "gt";
	}
}