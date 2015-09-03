package example;
import fit.*;
public class HelloWorldFromFitNesse extends ColumnFixture{

	public String firstWord;
	public String secondWord;
	
	public String together(){
		return firstWord+ " " + secondWord;
	}
}
