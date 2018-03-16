package org.ma.util;

import java.io.File;
import java.util.List;
import java.util.Map;



/**
 * @author Archmage
 */
public class EmptyUtil {
	public static boolean isEmptyStr(Object target){
		return target == null || "".equals(target);
	}

	public static <T> boolean isEmptyList(List<T> list) {
		return list == null || list.size() == 0 ;
	}

	public static boolean isEmptyArr(String[] arr) {
		return null == arr || arr.length == 0;
	}

	public static boolean isEmptyFile(File packet) {
		return null == packet || !packet.exists();
	}

	public static <K, V>  boolean isEmptyMap(Map<K, V> roleMap) {
		return null == roleMap || roleMap.size() == 0;
	}
	
	
	
	public static <T> T getDefaultStrVal(T target,T t){
		if(isEmptyStr(target)){
			return t;
		}
		return target;
	}
}
