package Mohabir.Fidel.CS316.P3;
class Cons extends OperatorExp
{
	Cons(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "cons";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" cons");
	}
}