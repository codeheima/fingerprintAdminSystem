package org.ma.app.action;


import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;
import net.sf.json.JSONObject;

import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.app.service.CommonService;
import org.ma.app.service.CustomerService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.device.FingerDevice;
import org.ma.util.EmptyUtil;
import org.ma.util.JSONUtil;

@Action(name = "device")
public class DeviceAction {

	@AutoField
	private CommonService commonService = null;
	
	
	@Req(url = "open.do")
	public void open(){
		boolean isOpen = FingerDevice.getInstance().open();
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true,String.valueOf(isOpen)));
	}
	
	@Req(url = "close.do")
	public void close(){
		FingerDevice.getInstance().close();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true,"false"));
	}
	
	
	@Req(url = "isOpen.do")
	public void isOpen(){
		boolean isOpen = FingerDevice.getInstance().isOpen();
		
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true,String.valueOf(isOpen)));
	}
	
	
}
