package Mohabir.Fidel.CS316.P3;
class ElseCase extends CaseExp
{
	Exp exp;
	
	ElseCase(Exp e)
	{
		exp = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " else");
		exp.printParseTree(indent1);
	}
	
	void emitInstructions() {
		exp.emitInstructions();
		IO.displayln(" goto 1");
	}
}