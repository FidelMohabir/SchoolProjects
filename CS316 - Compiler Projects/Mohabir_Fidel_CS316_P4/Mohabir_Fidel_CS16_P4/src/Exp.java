import java.util.HashMap;

abstract class Exp
{
	public volatile static HashMap funcMap;
	public volatile static HashMap paraMap;
	public volatile static HashMap integerMap;
	public volatile static int i = 0;
	public volatile static int n = 0;
	public volatile static int k = 0;
	public volatile static int l = 0;
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <exp>");
	}

	abstract void emitInstructions();
}