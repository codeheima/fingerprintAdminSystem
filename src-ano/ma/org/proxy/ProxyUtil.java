package ma.org.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyUtil {
	
	public static Object bind(Class<?> target,InvocationHandler proxy) {
		return Proxy.newProxyInstance(
				target.getClassLoader(),
				target.getInterfaces(), proxy );
		
	}
}