package Mohabir.Fidel.CS316.P3;
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
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" sub " + i);
		i = 0;
	}
}