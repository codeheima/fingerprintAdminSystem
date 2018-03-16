package org.ma.util;


/**
 * @author Archmage
 * 处理字符串末尾零
 */
public class CodeUtil {

	/**
	 * 去掉右边的0
	 * @param src
	 * @return
	 */
	public static String delRightZero(String src){
		if(src == null)
			return null;
		int l = src.length();
		int end = l;
		for(int i = l -1; i >= 0; i--){
			if(src.charAt(i) == '0'){
				end--;
			}else{
				break;
			}
		}
		if(end >0){
			return src.substring(0,end);
		}else
			return "";
	}
	
	
	/**
	 * 补全右边的0
	 * @param src len 字符串总长度
	 * @return
	 */
	public static String addRightZero(String src,int len){
		if(src == null)
			return null;
		int l = src.length();
		if(l > len){
			return src.substring(0,len);
		}
		if(l == len)
			return src;
		int count = len - l;
		StringBuilder sb = new StringBuilder(src);
		for(int i = 0; i <count; i++){
			sb.append("0");
		}
		return sb.toString();
	}
	
	public static void main(String [] args){
		System.out.println(delRightZero("012300"));
		System.out.println(delRightZero("00000"));
		System.out.println(delRightZero("000050"));
		System.out.println(delRightZero("0000033"));
		System.out.println(delRightZero("11200000"));
		System.out.println(delRightZero(null));
		
		System.out.println(addRightZero(null,12));
		System.out.println(addRightZero("12309",12));
		System.out.println(addRightZero("330200",12));
	}
}
