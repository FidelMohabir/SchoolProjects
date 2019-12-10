package Mohabir.Fidel.CS316.P3;
class Cdr extends OperatorExp
{
	Cdr(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "cdr";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" cdr");
	}
}