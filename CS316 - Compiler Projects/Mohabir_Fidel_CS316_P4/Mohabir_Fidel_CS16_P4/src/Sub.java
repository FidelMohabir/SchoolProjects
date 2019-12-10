class Sub extends OperatorExp
{
	Sub(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "-";
	}

	String getInstruction()
	{
		return "sub  " + expList.numOfExps();
	}
}