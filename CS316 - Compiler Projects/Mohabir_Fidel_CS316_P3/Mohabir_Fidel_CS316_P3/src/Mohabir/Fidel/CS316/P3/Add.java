package Mohabir.Fidel.CS316.P3;
class Add extends OperatorExp
{	
	Add(ExpList e)
	{
		expList = e;
	}
	
	String getOp()
	{
		return "+";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" add " + i);
		i = 0;
	}
}