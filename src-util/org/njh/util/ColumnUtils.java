package org.ma.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author 作者 zhouxl
 * @version 创建时间：2015-8-17 下午5:08:11
 */
public class ColumnUtils {

	/**
	 * 对象属性转换为字段 例如：userName to user_name
	 * @param property 字段名
	 * @return
	 */
	public static String propertyToField(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 字段转换成对象属性 例如：user_name to userName
	 * @param field
	 * @return
	 */
	public static String fieldToProperty(String field) {
		if (null == field) {
			return "";
		}
		char[] chars = field.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils
							.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String fieldToPropertyByUp(String field) {
		if (null == field) {
			return "";
		}
		return fieldToProperty(field.toLowerCase());
	}
	
	
	public static void main(String[] args) {
		String a = "userName";
		System.out.println(ColumnUtils.propertyToField(a).toUpperCase());
		String b = "user_name";
		System.out.println(ColumnUtils.fieldToProperty(b));
		
		String c = "USER_NAME";
		System.out.println(ColumnUtils.fieldToPropertyByUp(c));
	}

}
