package Mohabir.Fidel.CS316.P3;
class Eq extends OperatorExp
{
	Eq(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "=";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" eq");
		i = 0;
	}
}