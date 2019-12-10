class Cdr extends OperatorExp
{
	Cdr(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "cdr";
	}

	String getInstruction()
	{
		return "cdr";
	}
}