package Mohabir.Fidel.CS316.P3;
class Car extends OperatorExp
{
	Car(ExpList e)
	{
		expList = e;
	}

	String getOp()
	{
		return "car";
	}
	
	void emitInstructions() {
		expList.emitInstructions();
		IO.displayln(" car");
	}
}