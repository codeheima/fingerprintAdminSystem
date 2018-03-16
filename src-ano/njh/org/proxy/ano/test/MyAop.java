package ma.org.proxy.ano.test;

import java.lang.reflect.Method;

import ma.org.proxy.ano.declare.Aop;
import ma.org.proxy.ano.declare.InvocationHandlerExt;

//@Aop( targetBean = {"*Service"})
public class MyAop { //implements InvocationHandlerExt{
	
	private static ThreadLocal<String> invokePre = new ThreadLocal<String>(); 
	
	private static ThreadLocal<String> invokeSuff = new ThreadLocal<String>(); 
	
	private Object target = null;
	
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	
	public Object getTarget() {
		return target;
	}
	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		boolean isInvokePre = "true".equals(invokePre.get());
		if(!isInvokePre){
			System.out.println("start trans...");
			invokePre.set("true");
		}else{
			System.out.println("doNothing();");
		}
		
		Object result = null;
	//	System.out.println("MyAop ... 开始");
		
		result = method.invoke(target, args);
		
	//	System.out.println("MyAop ... 结束");
		boolean isInvokeSuff =   "true".equals(invokeSuff.get());
		if(!isInvokeSuff){
			System.out.println("end trans...");
			invokeSuff.set("true");
		}else{
			System.out.println("doNothing();");
		}
		
		
		return result;
	}


	

}
