package org.ma.app.action;


import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.app.service.CommonService;
import org.ma.app.service.LoginLogService;
import org.ma.db.jdbc.DataObject;
import org.ma.util.EmptyUtil;
import org.ma.util.JSONUtil;

import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;
import net.sf.json.JSONArray;
/**
 * 指纹：登入、登出
 *
 */
@Action(name = "loginLog")
public class LoginLogAction {

	@AutoField
	private CommonService commonService = null;
	
	@AutoField
	private LoginLogService loginlogService = null;
	
	
	@Req(url = "refreshMemory")
	public void refreshMemory(){
		
		loginlogService.refreshMemory();
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true));
	 
	}
	
	/**
	 * 登入
	 */
	@Req(url = "login.do",type="nologin")
	public void login(){
		DataObject params = ActionControl.getParams();
		
		String cusCode = params.getString("cusCode");
		
		/*if(EmptyUtil.isEmptyStr(cusCode)) {
			WebReturnUtil.returnString("false|编码不能为null"); 
			return;
		}*/
		
		String by = params.getString("zfByte");
		if(EmptyUtil.isEmptyStr(by)) {
			WebReturnUtil.returnString("false|指纹流未传入"); 
			return;
		}
		byte [] zfBytes = createByStr(by);
		
		System.out.println(zfBytes.length);
		
	
		String ip = params.getString("ip");
		String mac = params.getString("mac");
		String cpu = params.getString("cpu");
		
		DataObject data = new DataObject();
		data.setValue("ip", ip);
		data.setValue("mac", mac);
		data.setValue("cpu", cpu);
		data.setValue("zfBytes", zfBytes);
		data.setValue("cusCode", cusCode);
		
		WebReturnUtil.returnString(loginlogService.login(data));
		
	}
	
	private byte[] createByStr(String by) {
		JSONArray arr = JSONArray.fromObject(by);
		byte[] bs = new byte[arr.size()];
		for(int i =0; i< arr.size(); i++) {
			bs[i]= (byte)arr.getInt(i);
		}
		return bs;
	}

	/**
	 * 登出
	 */
	@Req(url = "logout.do",type="nologin")
	public void logout(){
		DataObject params = ActionControl.getParams();
		
		String ip = params.getString("ip");
		String mac = params.getString("mac");
		String cpu = params.getString("cpu");
		if(EmptyUtil.isEmptyStr(mac)) {
			WebReturnUtil.returnString("false|mac不能为null"); 
			return;
		}
		DataObject data = new DataObject();
		data.setValue("ip", ip);
		data.setValue("mac", mac);
		data.setValue("cpu", cpu);
		
		WebReturnUtil.returnString(loginlogService.logout(data));
		
	}
	
	  
}
