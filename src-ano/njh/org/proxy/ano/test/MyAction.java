package ma.org.proxy.ano.test;

import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;

@Action(name = "helloWorld")
public class MyAction {
	
	@AutoField
	private IBookFacade myBookFacadeService =null;
	
	
	@AutoField (name = "xxService")
	private IBookFacade abcdefghijk =null;
	
	@Req (url="justDoIt.do")
	public void JustDoIt(){
		System.out.println("start JustDoIt");
		myBookFacadeService.addBook();
		System.out.println("over JustDoIt");
	}
	
	
	public void addBook(){
		abcdefghijk.addBook();
	}
	
	
	
}
