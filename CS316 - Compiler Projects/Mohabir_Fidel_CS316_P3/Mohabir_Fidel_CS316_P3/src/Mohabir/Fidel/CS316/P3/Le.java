package Mohabir.Fidel.CS316.P3;
class Le extends OperatorExp
{
	Le(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "<=";
	}
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln("le");
	}
}