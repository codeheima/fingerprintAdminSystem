package ma.org.proxy.ano.test;

import ma.org.proxy.ano.BeanFactory;



public class TestAnoProxy {
	
	
	public static void main(String[] args) {
		
		test();
	}
	
	public static void test(){
		BeanFactory.init("ma.org.proxy.ano");
		MyAction action = (MyAction)BeanFactory.getBean("helloWorld");
		
		action.JustDoIt();
		
		System.out.println("xxxxxxxxxxxxxxxxxxx");
		
	//	action.JustDoIt();
		
		
	}
	

}

