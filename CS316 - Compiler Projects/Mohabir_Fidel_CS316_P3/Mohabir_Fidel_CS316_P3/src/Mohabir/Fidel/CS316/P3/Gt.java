package Mohabir.Fidel.CS316.P3;
class Gt extends OperatorExp
{
	Gt(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return ">";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" gt");
	}
}