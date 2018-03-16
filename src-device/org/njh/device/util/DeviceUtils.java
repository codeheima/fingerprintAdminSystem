package org.ma.device.util;

public class DeviceUtils {
	public static int byteArrayToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;  
	    // "|="按位或赋值。  
	    number |= ((bytes[1] << 8) & 0xFF00);  
	    number |= ((bytes[2] << 16) & 0xFF0000);  
	    number |= ((bytes[3] << 24) & 0xFF000000);  
	    return number;  
	 }

	
	
}
