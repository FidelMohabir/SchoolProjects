package Mohabir.Fidel.CS316.P3;
class Div extends OperatorExp
{
	Div(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "/";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" div " + i);
		i = 0;
	}
}