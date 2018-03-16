package org.ma.device.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveImage {
	private static final String path = "E:/test";
	
	public static int current = 7;
	
	public static void saveImage(byte[] bytes,int len) {
		
		if(bytes == null || bytes.length == 0) {
			return;
		}
		
		int i =0;
		
		String file = getFilePath();
		OutputStream os =  null;
		try {
			os = new FileOutputStream(file);
	
			/*while(i< bytes.length) {
				byte b = bytes[i];
				os.write(b);
				i++;
			}*/
			os.write(bytes,0,len);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static List<byte[]> getAllBytes(){
		
		List<byte[]> list = new ArrayList<byte[]>();
		for(int i =1; i< current + 1; i++) {
			String f = getFilePath(i);
			InputStream is = null;
			try {
				is= new FileInputStream(f);
				byte[] bs = new byte[is.available()];
				is.read(bs);
				list.add(bs);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
		}
		return list;
	}

	private static String getFilePath() {
		int cc = current;
		String result =getFilePath(cc);
		current++;
		return result;
		
	}
	
	private static String getFilePath(int cc) {
		return path + "/" + cc +".txt";
		
	}
}
