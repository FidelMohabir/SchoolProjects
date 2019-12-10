package Mohabir.Fidel.CS316.P3;
class Not extends OperatorExp
{
	Not(ExpList el)
	{
		expList = el;
	}

	String getOp()
	{
		return "not";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" not " + i);
		i = 0;
	}
}