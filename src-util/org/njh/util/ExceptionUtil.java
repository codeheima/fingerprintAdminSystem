package org.ma.util;

import net.sf.json.JSONObject;


/**
 * @author Archmage
 */
public class ExceptionUtil {

	public static JSONObject getCommonJson(Throwable err){
		return getCommonJson(err,"\r\n");
	}
	
	public static JSONObject getCommonJson(Throwable err,String splitMark){
		String errStr = getStr(err,splitMark);
		JSONObject out = new JSONObject();
		out.put("isSuccess", false);
		out.put("errMsg", errStr);
		return out;
	}
	
	public static String getCommonStr(Throwable err) {
		return getStr(err,"\r\n");
	}
	
	
	public static String getStr(Throwable err,String splitMark) {
		StringBuilder sb = new StringBuilder(err.getMessage() + splitMark);
		StackTraceElement[] eles = err.getStackTrace();
		for(StackTraceElement e : eles){
			sb.append(String.valueOf(e)).append(splitMark);
		}
		return sb.toString();
	}
		
}
