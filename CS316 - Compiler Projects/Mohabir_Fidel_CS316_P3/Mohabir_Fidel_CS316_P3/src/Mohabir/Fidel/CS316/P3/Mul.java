package Mohabir.Fidel.CS316.P3;
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
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" mul " + i);
		i = 0;
	}
}