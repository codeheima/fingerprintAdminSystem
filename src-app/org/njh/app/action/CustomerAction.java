package org.ma.app.action;


import java.util.Date;
import java.util.List;

import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.app.service.CommonService;
import org.ma.db.jdbc.DataObject;
import org.ma.device.FingerDevice;
import org.ma.util.DateUtils;
import org.ma.util.EmptyUtil;
import org.ma.util.JSONUtil;

import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;

@Action(name = "cus")
public class CustomerAction {

	@AutoField
	private CommonService commonService = null;
	
	
	/**
	 * 指纹初始化
	 */
	@Req(url = "currentZf.do")
	public void currentZf(){
		DataObject params = ActionControl.getParams();
		String cusId = params.getString("cusId");
		if(EmptyUtil.isEmptyStr(cusId)){
			WebReturnUtil.returnJson(JSONUtil.getCommonOut(false));
			return ;
		}
		
		List<DataObject> list = commonService.executeQuery("zfdb", "Select * FROM CUSTOMER_CURRENT ");
		if(!EmptyUtil.isEmptyList(list)) {
			String t = list.get(0).getString("cusId");
			if(t.equals(cusId)) {
				return;
			}
		}
		
		commonService.execute("zfdb", "DELETE FROM CUSTOMER_CURRENT ");
		
		commonService.insert("zfdb.customerCurrent", params);
		
	}
	
	/**
	 * 合并指纹
	 */
	@Req(url = "unionZf.do")
	public void unionZf() {
		List<DataObject> list = commonService.executeQuery("zfdb", "select * from CUSTOMER_CURRENT");
		if(EmptyUtil.isEmptyList(list)) {
			WebReturnUtil.returnJson(JSONUtil.getCommonOut(false,"没有待合并的数据"));
			return;
		}
		
		
		for(DataObject d : list) {
			String cusId = d.getString("cusId");
			byte[] zf1 = d.getBytes("zf1");
			byte[] zf2 = d.getBytes("zf2");
			byte[] zf3 = d.getBytes("zf3");
			if(zf1== null ||zf2 == null || zf3 == null) {
				WebReturnUtil.returnJson(JSONUtil.getCommonOut(false,"合并的数据不足3个指纹"));
				return;
			}
			byte[] target = FingerDevice.getInstance().merge(zf1, zf2, zf3);
			if(target == null) {
				WebReturnUtil.returnJson(JSONUtil.getCommonOut(false,"合并不成功，请重新连接设备"));
				return;
			}
			DataObject zf = new DataObject();
			zf.setValue("cusId", cusId);
			zf.setValue("zfArr", target);
			zf.setValue("regTime", DateUtils.formatDate(new Date(), DateUtils.SHORT_DATE_FORMAT));
			
			commonService.insert("zfdb.customerZf", zf);
			
			String cc =  commonService.executeQuery("zfdb","SELECT count(*) as cc from CUSTOMER_ZF WHERE CUS_ID = '"+ cusId +"'")
			 	.get(0).getString("cc");
			
			commonService.execute("zfdb"," UPDATE customer SET ZF_COUNT = " + cc + " WHERE CUS_ID = '"+ cusId +"'");
			 
		}
		
		
		
		
		commonService.execute("zfdb", "DELETE FROM CUSTOMER_CURRENT");
		
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true,"合并成功"));
	}
	
	/**
	 * 删除指纹
	 */
	@Req(url = "delZf.do")
	public void delZf() {
		DataObject params = ActionControl.getParams();
		String cusId = params.getString("cusId");
		
		DataObject d = new DataObject();
		d.setValue("cusId", cusId);
		d.setValue("zfCount", "0");
		
		commonService.execute("zfdb", "DELETE FROM CUSTOMER_ZF WHERE CUS_ID = '" +  cusId +"'");
		
		commonService.updateWithNotNull("zfdb.customer", d);
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true,"删除指纹成功"));
	}
	  
}
