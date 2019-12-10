class Eq extends OperatorExp
{
	Eq(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "=";
	}

	String getInstruction()
	{
		return "eq";
	}
}