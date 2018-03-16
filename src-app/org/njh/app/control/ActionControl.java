package org.ma.app.control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ma.db.jdbc.DataObject;

import ma.org.proxy.ano.Bean;
import ma.org.proxy.ano.BeanFactory;

public class ActionControl {

	private static ThreadLocal<HttpServletRequest> req = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> resp = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<HttpSession> s = new ThreadLocal<HttpSession>();
	
	
	/**
	 * 处理action跳转
	 * @param request
	 * @param response
	 * @param reqList
	 */
	public static void invokeAction(HttpServletRequest request,
			HttpServletResponse response, List<String> reqList) {
		HttpSession session = request.getSession();
		
		req.set(request);
		resp.set(response);
		s.set(session);
		String beanName =  reqList.get(0);
		String reqUrl = reqList.get(1);
	
		
		Bean bean = BeanFactory.getBeanBase(beanName);
		if(bean == null){
			throw new RuntimeException("there is no bean:" + beanName);
		}
		Method m = bean.getByReq(reqUrl);
		if(m == null){
			throw new RuntimeException("there is no method:" + beanName  + "/" + reqUrl);
		}
		try {
			m.invoke(bean.getTarget(), null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}

	public static HttpServletRequest getReq() {
		return req.get();
	}

	public static HttpServletResponse getResp() {
		return resp.get();
	}

	public static HttpSession getSession() {
		return s.get();
	}

	public static DataObject getParams() {
		HttpServletRequest req = ActionControl.getReq();
		
		Map<String,Object> map = req.getParameterMap();
		
		DataObject params = new DataObject(map);
		return params;
	}
	
	
	

}
