package Mohabir.Fidel.CS316.P3;
class Case extends CaseExp
{
	Exp exp1;
	Exp exp2;
	
	Case(Exp e1, Exp e2)
	{
		exp1 = e1;
		exp2 = e2;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);	
		exp1.printParseTree(indent1);
		exp2.printParseTree(indent1);
	}
	
	void emitInstructions() {
		exp1.emitInstructions();
		i++;
		IO.displayln("	if_f goto " + i);

		exp2.emitInstructions();
		IO.displayln("	goto 1");
		IO.displayln(":");
	}
}