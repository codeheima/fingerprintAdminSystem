package org.ma.util;

import java.util.UUID;


/**
 * 处理UUID
 * @author Archmage
 */
public class MyUUID {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
