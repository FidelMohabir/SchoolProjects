package Mohabir.Fidel.CS316.P3;
class Ge extends OperatorExp
{
	Ge(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return ">=";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" ge");
	}
}