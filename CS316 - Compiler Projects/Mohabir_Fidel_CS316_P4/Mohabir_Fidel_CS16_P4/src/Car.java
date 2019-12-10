class Car extends OperatorExp
{
	Car(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "car";
	}

	String getInstruction()
	{
		return "car";
	}
}