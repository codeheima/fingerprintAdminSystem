package org.ma.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * @author Archmage
 */
public class JSONUtil {
	
	public static void each(JSONObject jo,IEach each){
		Set set = jo.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Map.Entry<String,Object> entry = (Map.Entry<String,Object>)it.next();
			each.each(entry.getKey(),entry.getValue());
		}
	}
	
	public static JSONObject getCommonOut(boolean isSuccess){
		return getCommonOut(isSuccess,null);
	}
	
	/**
	 * 
	 * @param isSuccess
	 * @param msg
	 * @return
	 */
	public static JSONObject getCommonOut(boolean isSuccess,String msg){
		JSONObject out = new JSONObject();
		out.put("isSuccess", isSuccess);
		if(!EmptyUtil.isEmptyStr(msg)){
			out.put("msg", msg);
		}
		return out;
	}
	

	
	public static PackJson initPackJson(JSONObject jo){
		return new PackJson(jo);
	}
	
	public interface IEach{
		public void each(String key,Object val);
	}



	
	
	public static class PackJson{
		private JSONObject target;
		
		public PackJson(JSONObject jo){
			target = jo;
		}
		
		public PackJson put(Object key,Object val){
			target.put(key, val);
			return this;
		}
		
		public JSONObject getJson(){
			return target;
		}
	}
	
	/*
	 * 判断字符串是不是JSON
	 * @param chatData
	 * @return
	 */
	public static boolean isJSONObject(String str) {
		try {
			JSONObject js = JSONObject.fromObject(str);
			return true;
		} catch (JSONException e) {
			
		}
		return false;
	}
}
