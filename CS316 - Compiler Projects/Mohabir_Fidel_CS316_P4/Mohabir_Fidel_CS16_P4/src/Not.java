class Not extends OperatorExp
{
	Not(ExpList el)
	{
		expList = el;
	}

	String getOp()
	{
		return "not";
	}

	String getInstruction()
	{
		return "not";
	}
}