package Mohabir.Fidel.CS316.P3;
class AtomicSExp extends SExp
{
	Atom atom;
	
	AtomicSExp(Atom a)
	{
		atom = a;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent+" ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <Atomic S Exp>");
		atom.printParseTree1(indent1);
	}
	
	void emitInstructions() {
		atom.emitInstructions();
	}
}