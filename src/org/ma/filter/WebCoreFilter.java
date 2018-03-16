package org.ma.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ma.db.jdbc.SessionFactory;



public class WebCoreFilter implements Filter {
	/**
	 * 
	 */
	String encode = "UTF-8";
	
	
	/**
	 *
	 */
	String resourceVersion = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		encode = filterConfig.getInitParameter("encode");
		if(encode==null || encode.length()==0)
			encode = "UTF-8";
		
		resourceVersion = filterConfig.getInitParameter("resource-version");
		if(resourceVersion==null || resourceVersion.length()==0)
			resourceVersion = null;
	}
	/**
	 * 
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		
//		String path = request.getServletPath();
	
		servletRequest.setCharacterEncoding(encode);
		try{
			chain.doFilter(servletRequest, response);
		}catch(Exception err){
			throw new RuntimeException(err);
		}finally{
			SessionFactory.closeSession();
		}
		
	}

	public void destroy() {
		
	}
}
