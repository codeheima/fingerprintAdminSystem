package org.ma.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Date;



/**
 * @author Archmage
 */
public class MD5 {

	private static final  char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	

	
	public static String md5(String src){
		//MD5都转成大写
		try {
			return SecretUtils.md5(src).toUpperCase();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "";
	}
	
	public static String md5(File file)throws Exception{
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	FileInputStream in = new FileInputStream(file);  
        FileChannel ch = in.getChannel();  
        try{
	        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,  
	                 file.length());  
	    	md.update(byteBuffer);
	    	byte[] tmp = md.digest();
	    	char str[] = new char[16*2];
	    	int k=0;
	    	for(int i=0;i<16;i++){
	    		byte b = tmp[i];
	    		str[k++] = hexDigits[b >>> 4 & 0xf];
	    		str[k++] = hexDigits[b & 0xf];
	    	}
	    	return new String(str).toUpperCase();
        }finally{
        	in.close();
        	ch.close();
        }
	}
	
	
	public static boolean md5Check(File file,String target)throws Exception{
		if(EmptyUtil.isEmptyFile(file) || EmptyUtil.isEmptyStr(target))
			return false;
		return md5(file).toUpperCase().equals(target.toUpperCase());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
//		System.out.println(System.currentTimeMillis());
//		Date d = DateUtils.parseDate("2015-01-01",DateUtils.DATE_FORMAT);
//		System.out.println(d);
//		System.out.println(md5("111").toLowerCase());
//		System.out.println(md5("admin").toLowerCase());
	//	698d51a19d8a121ce581499d7b701668
	//	698D51A19D8A121CE581499D7B701668
//21232f297a57a5a743894a0e4a801fc3
		
		testFile();
		
		
	}
	
	private static void pHex(String src){
		long a = Long.parseLong(src);
		String hex = Long.toHexString(a);
		System.out.println(Long.toBinaryString(a));
		System.out.println( src.length() + ":" + src +"==" + hex.length() + ":" + hex);
	}

	private static void testFile() throws Exception {
	File f = new File("E:\\work\\PMI\\应用\\new\\sqpt_db1.0.0.tar.gz");
		String md5 =  MD5.md5(f);
		System.out.println(md5);
		System.out.println(md5.length());
		/*		f = new File("E:\\work\\PMI\\应用\\new\\sqpt_web1.0.0.tar.gz");
		md5 =  MD5.md5(f);
		System.out.println(md5);*/
		
		f = new File("E:\\work\\PMI\\应用\\测试\\同步\\5416-010000-Org-All-1449270008-00245.zip");
		md5 =  MD5.md5(f);
		System.out.println(md5);
	}

}
