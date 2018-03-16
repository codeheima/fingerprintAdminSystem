package ma.org.proxy.ano.test;

import ma.org.proxy.ano.declare.Service;

@Service(name = "xxService" )
public class XxService implements IBookFacade{

	public void addBook() {
		System.out.println("xxService.addBook()");
	}

}
