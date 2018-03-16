package ma.org.proxy.ano;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ma.org.proxy.CollectionUtil;
import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.Aop;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;
import ma.org.proxy.ano.declare.Service;

import org.ma.util.ComplexUtil;
import org.ma.util.ReflectUtil;

public class BeanFactory {
	private static Map<String,Bean> beanMap = new HashMap<String,Bean>();
	
	private static Set<AopBean> aopBeanSet = ComplexUtil.set();
	
	private static Map<String,Bean> noLoginUrlMap = new HashMap<String,Bean>();
	
	public static void init(String pack){
		Set<Class<?>> set = ReflectUtil.getClasses(pack);
		
		Set<Class<?>> serviceSet = ComplexUtil.set();
		Set<Class<?>> actionSet = ComplexUtil.set();
		Set<Class<?>> aopSet = ComplexUtil.set();
		
		for(Class<?> c : set){
			boolean isService = c.isAnnotationPresent(Service.class);
			  
			boolean isAction = c.isAnnotationPresent(Action.class);
			if(isService){
			//	addService(c);
				serviceSet.add(c);
			}else if(isAction){
				actionSet.add(c);
			//	addAction(c);
			}else if(c.isAnnotationPresent(Aop.class)){
				aopSet.add(c);
			}
		}
		
		for(Class<?> c : aopSet){
			addAop(c);
		}
		
		for(Class<?> c : serviceSet){
			addService(c);
		}
		
		for(Class<?> c : actionSet){
			addAction(c);
		}
		
		//处理aop
	   // doAop();
		
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMapEntry<String,Bean>() {
			public void opt(String beanName, Bean bean) {
				bean.initFields();
			}
		});
		
	}
	
	
	private static void doAop() {
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMapEntry<String,Bean>() {
			public void opt(String beanName, Bean bean) {
				for(AopBean aopBean : aopBeanSet){
					if(aopBean.matches(beanName)){
						bean.addHandler(aopBean.getProxy());
					}
				}
				if(bean.isNeedDefaultAop()){
					bean.addHandler(new DefaultProxy(bean));
				}
			}
		});
		
	}


	private static void addAop(Class<?> c) {
		AopBean aopBean = new AopBean();
		aopBean.setProxyClass(c);
		Aop aop = (Aop)c.getAnnotation(Aop.class);
		aopBean.setReg(aop.targetBean());
		aopBeanSet.add(aopBean);
	}


	public static Bean getBeanBase(String beanName){
		if(beanMap.containsKey(beanName)){
			return beanMap.get(beanName);
		}else {
			return null;
		}
	}

	public static Object getBean(String beanName){
		if(beanMap.containsKey(beanName)){
			return beanMap.get(beanName).getTarget();
		}else {
			return null;
		}
	}
	
	
	private static void addService(Class<?> c) {
		Service service = (Service)c.getAnnotation(Service.class);
		String name = service.name();
		Object target = null;
		try {
			target = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		addField(target);
		Bean bean = null;
		if(beanMap.containsKey(name)){
			bean = (Bean)beanMap.get(name);
			bean.setTarget(target);
			//,new DefaultProxy());
		//	bean.initFields();
		}else{
			//bean =Bean.createBean(name, target, new DefaultProxy());
			bean = Bean.createBean(name);
			bean.setTarget(target);
			beanMap.put(name, bean);
		}
		bean.setNeedDefaultAop(true);
		bean.setOrgClass(c);
		
	}




	private static void addAction(Class<?> c) {
		Action action = (Action)c.getAnnotation(Action.class);
		String name = action.name();
		Object target = null;
		try {
			target = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		addField(target);
		Bean bean = Bean.createBean(name);
		bean.setOrgClass(c);
		bean.setTarget(target);
		bean.initReqMethod(c);
		
		beanMap.put(name, bean);
		
	}

	private static void addField(Object target){
		Class<?> c = target.getClass();
		Field[] fields =c.getDeclaredFields();
		
		if(fields != null){
			for(Field f : fields){
				if(f.isAnnotationPresent(AutoField.class)){
					AutoField af = f.getAnnotation(AutoField.class);
					String beanName = null;
					if(!AutoField.Default_Mark.equals(af.name())){
						beanName = af.name();
					}else{
						beanName = f.getName();
					}
					try {
						
						Bean bean = null;
						if(beanMap.containsKey(beanName)){
							bean = beanMap.get(beanName);
						}else{
							bean = Bean.createBean(beanName);
							beanMap.put(beanName, bean);
						}
						bean.addField(f, target);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	public static Map<String, Bean> getNoLoginMap() {
		return noLoginUrlMap;
	}



}
