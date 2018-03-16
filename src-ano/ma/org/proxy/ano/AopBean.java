package ma.org.proxy.ano;

import java.util.regex.Pattern;

import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class AopBean {
	
	private  Class<?> proxyClass = null;
	
	
	private String [] regStrArr = null;
	
	private Pattern [] patterns = null;
	
	
	public void setReg(String [] ss){
		regStrArr = ss;
		patterns = new Pattern[ss.length];
		for(int i = 0 ; i< ss.length; i++){
			patterns[i] = Pattern.compile(ss[i].replaceAll("\\*", "\\\\w*"));
		}
	}

	public InvocationHandlerExt getProxy() {
		Object proxy = null;
		try {
			proxy = proxyClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return (InvocationHandlerExt)proxy;
	}

	public void setProxyClass(Class<?> c) {
		this.proxyClass = c;
	}

	public Pattern[] getPatterns() {
		return patterns;
	}

	public String[] getRegStrArr() {
		return regStrArr;
	}
	
	
	public boolean matches(String text){
		if(patterns == null){
			System.out.println("AopBean :" + proxyClass.getName() + "; patterns is null");
			return false;
		}
		for(Pattern p : patterns){
			if(p.matcher(text).matches()){
				return true;
			}
		}
		return false;
	}
	
}
