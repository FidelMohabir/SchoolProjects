class Mul extends OperatorExp
{
	Mul(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "*";
	}

	String getInstruction()
	{
		return "mul  " + expList.numOfExps();
	}
}