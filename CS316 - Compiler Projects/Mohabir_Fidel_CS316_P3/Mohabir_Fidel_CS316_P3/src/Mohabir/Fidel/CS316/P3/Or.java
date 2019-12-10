package Mohabir.Fidel.CS316.P3;
class Or extends OperatorExp
{
	Or(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "or";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" or " + i);
		i = 0;
	}
}