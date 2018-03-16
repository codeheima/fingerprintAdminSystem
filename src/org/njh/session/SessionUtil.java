package org.ma.session;

import javax.servlet.http.HttpServletRequest;

import org.ma.app.App;
import org.ma.util.MD5;

public class SessionUtil {
	
	public static final String Mark_userType = "userType";
	public static final String Mark_user = "user";
	public static final String Mark_admin = "admin";
	
	
	public static void addUserSession(HttpServletRequest req){
		req.getSession().setAttribute("userType", Mark_user);
		
	}
	

	public static void addAdminSession(HttpServletRequest req){
		req.getSession().setAttribute("userType", Mark_admin);
		
	}
	
	public static boolean loginAdmin(HttpServletRequest req){
		String password = req.getParameter("inputPassword");
	
		if(App.INSTANCE.getPassword().equals(MD5.md5(password))){
			req.getSession().setAttribute("isLogin", "true");
			return true;
		}
		return false;
	}

	/**
	 * 是否是管理员
	 * @param req
	 * @return
	 */
	public static boolean isAdmin(HttpServletRequest req){
		if(Mark_admin == req.getSession().getAttribute(Mark_userType)){
			return true;
		}
		return false;
	}
	
	public static void loginOut(HttpServletRequest req){
		req.getSession().setAttribute("userType", Mark_user);
		
	}
	
	public static void main(String [] agrs){
		String s = "123123123123123";
		System.out.println(MD5.md5(s));
	}
	
	
}
