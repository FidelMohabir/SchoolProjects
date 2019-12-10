class Cons extends OperatorExp
{
	Cons(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "cons";
	}

	String getInstruction()
	{
		return "cons";
	}
}