package org.ma.app.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class WebReturnUtil {
	
	public static void returnJson(JSONObject json){
		HttpServletResponse resp = ActionControl.getResp();
		try {
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().write(json.toString());
		//	resp.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void returnString(String str){
		HttpServletResponse resp = ActionControl.getResp();
		try {
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重定向
	 * @param path
	 */
	public static void sendRedirect(String path){
		HttpServletRequest req = ActionControl.getReq();
		HttpServletResponse resp = ActionControl.getResp();
		try {
			resp.sendRedirect(req.getContextPath() + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void requestDispatcher(String path){
		HttpServletRequest req = ActionControl.getReq();
		HttpServletResponse resp = ActionControl.getResp();
		try {
			req.getRequestDispatcher(path).forward(req,resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
