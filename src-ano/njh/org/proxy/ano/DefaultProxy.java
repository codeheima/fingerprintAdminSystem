package ma.org.proxy.ano;

import java.lang.reflect.Method;

import ma.org.proxy.ano.declare.InvocationHandlerExt;


public class DefaultProxy implements InvocationHandlerExt{
	
	private Object target;
	
	private Bean bean;
	
	public DefaultProxy(Bean bean) {
		this.bean = bean;
	}
	

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	public boolean isInit(){
		return null != target;
	}
	

	public Bean getBean() {
		return bean;
	}

	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		System.out.println("default proxy start ...");
		
		
		
		result = method.invoke(target, args);
		
		
		System.out.println("default proxy end ...");
		return result;
	}

	
}
