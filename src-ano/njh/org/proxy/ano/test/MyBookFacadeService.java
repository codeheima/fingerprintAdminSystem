package ma.org.proxy.ano.test;

import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Service;

@Service(name = "myBookFacadeService" )
public class MyBookFacadeService implements IBookFacade{

	@AutoField
	private IBookFacade xxService =null;
	
	public void addBook() {
		System.out.println("MyBookFacadeService.addBook()");
		xxService.addBook();
	}

}
