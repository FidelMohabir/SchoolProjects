
public class Apostrophe extends Exp {
	FunDefList funDefList;
	Exp exp;
	
	Apostrophe(FunDefList f, Exp e) {
		funDefList = f;
		exp = e;
	}
	

	@Override
	void emitInstructions() {
		// TODO Auto-generated method stub
		l = (int) funcMap.get(funDefList.funDef.header.funName);
		IO.displayln(l + ":");
		exp.emitInstructions();
	}
}
