package ma.org.proxy.ano.test;

import ma.org.proxy.ano.declare.AutoField;

public class MyBookFacadeServiceTwo implements IBookFacade{

	@AutoField
	private IBookFacade xxService =null;
	
	public void addBook() {
		System.out.println("MyBookFacadeServiceTwo.addBook()");
		xxService.addBook();
		
	}

	
}