package ma.org.proxy.ano.declare;

import java.lang.reflect.InvocationHandler;

import ma.org.proxy.ano.Bean;

public interface InvocationHandlerExt extends InvocationHandler {
	
	void setTarget(Object target);
	
	Object getTarget();
	
	Bean getBean();
	
	
	
}
