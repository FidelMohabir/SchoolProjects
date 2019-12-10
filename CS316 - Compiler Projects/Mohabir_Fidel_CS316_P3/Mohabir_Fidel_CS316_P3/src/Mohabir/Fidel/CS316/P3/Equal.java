package Mohabir.Fidel.CS316.P3;
class Equal extends OperatorExp
{
	Equal(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "equal";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" equal");
	}
}