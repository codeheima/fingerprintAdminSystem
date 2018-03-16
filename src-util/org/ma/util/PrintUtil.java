package org.ma.util;

import java.util.List;

public class PrintUtil {
	public static <T> void pList(List<T> list,Obj2Str<T> obj2Str){
		for(int i = 0 ; i< list.size(); i++){
			T o = list.get(i);
			if(obj2Str != null){
				System.out.println(obj2Str.obj2Str(o));
			}else
				System.out.println(o);
		}
	}
	
	
	public interface Obj2Str<W>{
		String obj2Str(W o);
	}
}
