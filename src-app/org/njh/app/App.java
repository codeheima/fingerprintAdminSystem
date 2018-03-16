package org.ma.app;

import org.dom4j.Document;
import org.dom4j.Element;
import org.ma.util.Dom4jUtil;
import org.ma.util.MD5;

public enum App {
	INSTANCE;
	
	private String password = "900150983CD24FB0D6963F7D28E17F72"; //"DCAE8C6D2DBD4B2644796BC50648EFD3";
	
	//device : 匹配率
	private int compareSuccess = 75;
	
	public void init(){
		Document doc = Dom4jUtil.getCpDoc("app.xml");
		Element root = doc.getRootElement();
		password = Dom4jUtil.attrVal(root, "admin/password", "val", "fffff");
		
		compareSuccess = Dom4jUtil.attrValInteger(root, "device/compareSuccess", "val", 75);
		
		
	}
	
	public int getCompareSuccess(){
		return compareSuccess;
	}
	
	public String getPassword(){
		return password;
	}
	
	public static void main(String [] args){
		System.out.println(MD5.md5("abc"));
	}

}
