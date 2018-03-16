package org.ma.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.util.ComplexUtil;
import org.ma.util.ExceptionUtil;

import ma.org.proxy.ano.BeanFactory;



public class CommonFilter implements Filter {
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	/**
	 * 
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		
		//处理登录
		Object isLogin = request.getSession().getAttribute("isLogin");
		String path = request.getServletPath();
		
		if("true".equals(isLogin) 
				|| BeanFactory.getNoLoginMap().containsKey(path)){
			//已经登录
			int i = path.lastIndexOf("/");
			if(i <= 0){
				return;
			}
			List<String> reqList = ComplexUtil.list();
			reqList.add(path.substring(1, i));
			reqList.add(path.substring(i + 1));
			
			if(reqList.size() > 1){
				try{
					ActionControl.invokeAction(request,response,reqList);
					
				}catch(Exception err){
					
					JSONObject json = ExceptionUtil.getCommonJson(err,"<br>");
					WebReturnUtil.returnJson(json);
					
				}
				return;
			}
			
			chain.doFilter(servletRequest, response);
			
		}else{
			toLogin(request,response);
		}

		
	}
	
	//去登录
	private void toLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		response.sendRedirect(request.getContextPath() + "/page/login.jsp");
	}

	public void destroy() {
		
	}
	

}
