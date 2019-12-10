package Mohabir.Fidel.CS316.P3;
class Lt extends OperatorExp
{
	Lt(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "<";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" lt");
		i = 0;
	}
}