package Mohabir.Fidel.CS316.P3;
class And extends OperatorExp
{
	And(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "and";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" and " + i);
		i = 0;
	}
}