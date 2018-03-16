package org.ma.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 数据编码或加、解密算法
 * @author ryan
 *
 */
public class SecretUtils {
	
	/**
	 * 生成DES密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateDesKey() throws Exception{
		SecureRandom sr = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(sr);
		SecretKey key = kg.generateKey();
		return key.getEncoded();
	}
	
	/**
	 * 使用DES算法加密数据
	 * @param key	密钥
	 * @param data	待加密的数据
	 * @return		加密后的数据
	 * @throws Exception
	 */
	public static byte[] desEncrypt(String key,byte[] data) throws Exception{
		return desEncrypt(key.getBytes(),data);
	}
	/**
	 * 使用DES算法加密数据
	 * @param key	密钥
	 * @param data	待加密的数据
	 * @return		加密后的数据
	 * @throws Exception
	 */
	public static byte[] desEncrypt(byte[] key,String data) throws Exception{
		return desEncrypt(key,data.getBytes());
	}	
	/**
	 * 使用DES算法加密数据
	 * @param key	密钥
	 * @param data	待加密的数据
	 * @return		加密后的数据
	 * @throws Exception
	 */
	public static byte[] desEncrypt(String key,String data) throws Exception{
		return desEncrypt(key.getBytes(),data.getBytes());
	}	
	/**
	 * 使用DES算法加密数据
	 * @param key	密钥
	 * @param data	待加密的数据
	 * @return		加密后的数据
	 * @throws Exception
	 */
	public static byte[] desEncrypt(byte[] key,byte[] data) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey =factory.generateSecret(dks);
		Cipher chiper = Cipher.getInstance("DES");
		chiper.init(Cipher.ENCRYPT_MODE, secretKey,sr);
		return chiper.doFinal(data);
	}
	/**
	 * 使用DES算法解密数据
	 * @param key	密钥
	 * @param data	待解密的数据
	 * @return		解密后的数据
	 * @throws Exception
	 */
	public static byte[] desDecrypt(String key,byte[] data) throws Exception{
		return desDecrypt(key.getBytes(),data);
	}		
	/**
	 * 使用DES算法解密数据
	 * @param key	密钥
	 * @param data	待解密的数据
	 * @return		解密后的数据
	 * @throws Exception
	 */
	public static byte[] desDecrypt(byte[] key,String data) throws Exception{
		return desDecrypt(key,data.getBytes());
	}	
	/**
	 * 使用DES算法解密数据
	 * @param key	密钥
	 * @param data	待解密的数据
	 * @return		解密后的数据
	 * @throws Exception
	 */
	public static byte[] desDecrypt(String key,String data) throws Exception{
		return desDecrypt(key.getBytes(),data.getBytes());
	}	
	/**
	 * 使用DES算法解密数据
	 * @param key	密钥
	 * @param data	待解密的数据
	 * @return		解密后的数据
	 * @throws Exception
	 */
	public static byte[] desDecrypt(byte[] key,byte[] data) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey =factory.generateSecret(dks);
		Cipher chiper = Cipher.getInstance("DES");
		chiper.init(Cipher.DECRYPT_MODE, secretKey,sr);
		return chiper.doFinal(data);
	}
	
	/**
	 * 使用BASE64算法对字符串编码
	 * @param data	要编码的数据
	 * @return
	 * @throws Exception
	 */
	public static String base64Encode(byte[] data) throws Exception{
		if (data == null) 
			return null; 
		else
			return (new BASE64Encoder()).encode(data); 
    }
	/**
	 * 使用BASE64算法对数据解码
	 * @param s
	 * @return
	 * @throws Exception
	 */
    public static byte[] base64Decode(String s) throws Exception{
    	if (s == null) 
    		return null; 
    	
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	return decoder.decodeBuffer(s); 
    }	
    
    /**
     * 字符串MD5处理
     * @param source
     * @return
     * @throws Exception
     */
    public static String md5(String source) throws Exception{
    	char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	md.update(source.getBytes("UTF-8"));
    	byte[] tmp = md.digest();
    	char str[] = new char[16*2];
    	int k=0;
    	for(int i=0;i<16;i++){
    		byte b = tmp[i];
    		str[k++] = hexDigits[b >>> 4 & 0xf];
    		str[k++] = hexDigits[b & 0xf];
    	}
    	return new String(str);
    }

	public static void main(String[] args) throws Exception {
		System.out.println(md5("admin"));
		//密文
		String key = "gab-2010-!)@(#*$&%^-data-!@#$*&^%";
		//使用DES算法加密并BASE64编码
		String encryptPassword = "76uldBmnEH3aOGsWRtPnsw==";
		//使用BASE64解码再用DES算法解密
		System.out.println("解密后的内容："+new String(desDecrypt(key.getBytes(),base64Decode(encryptPassword))));
	}

}
