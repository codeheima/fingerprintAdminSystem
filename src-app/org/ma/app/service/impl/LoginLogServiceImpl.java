package org.ma.app.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ma.app.service.LoginLogService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.dao.BaseOptDao;
import org.ma.device.FingerDevice;
import org.ma.util.DateUtils;
import org.ma.util.EmptyUtil;

import ma.org.proxy.ano.declare.Service;


@Service(name = "loginlogService" )
public class LoginLogServiceImpl implements LoginLogService {

	private volatile List<DataObject> tempCusList = null;
	private static final long decTime = 1000 * 10;
	private volatile long lastRefreshTime = 0;
	
	public synchronized List<DataObject> refreshMemory() {
		long currentTime = System.currentTimeMillis();
		if( (currentTime - lastRefreshTime) > decTime) {
			tempCusList = BaseOptDao.executeQuery("platformdb.customer.queryByCode", new DataObject());
			lastRefreshTime = currentTime;
		}
		return tempCusList;
	}
	
	public String login(DataObject param) {
		String cusCode = param.getString("cusCode");
		String mac = param.getString("mac");
		if(EmptyUtil.isEmptyStr(mac)) {
			return getFalse() +"|未上传mac信息";
		}
		List<DataObject> cusList =  null;
		if(!EmptyUtil.isEmptyStr(cusCode)) {
			DataObject cusParam = new DataObject().setValue("cusCode", cusCode);
			cusList = BaseOptDao.executeQuery("platformdb.customer.queryByCode", cusParam);
		}else {
			cusList = refreshMemory();
		}
		if(EmptyUtil.isEmptyList(cusList)) {
			return getFalse() +"|未找到指纹数据";
		}
		
		
		
		//查看上一位使用者是否登出，如果没有，记录上一次的登出信息....
		String clientId = param.getString("mac");
		DataObject clientParam = new DataObject().setValue("clientId", clientId);
		List<DataObject> clientList = BaseOptDao.executeQuery("platformdb.customer.queryLastLogin", clientParam);
		Date now = new Date();
		String nowStr = DateUtils.formatDate(now, DateUtils.SHORT_DATE_FORMAT);
		String nowStrDec = DateUtils.formatDate(DateUtils.add(now,Calendar.SECOND,-1), DateUtils.SHORT_DATE_FORMAT);
		if(!EmptyUtil.isEmptyList(clientList)) {
		
			logoutByLoginList(clientList,param,nowStrDec);
		
		}
		
/*
		data.setValue("ip", ip);
		data.setValue("mac", mac);
		data.setValue("cpu", cpu);
		data.setValue("zfBytes", zfBytes);
		data.setValue("cusCode", cusCode);
*/		
		byte[] cusBytes = param.getBytes("zfBytes");
		
		DataObject tempCus = null;
		boolean isCompare = false;
		for(DataObject c : cusList) {
			byte[] arr = c.getBytes("zfArr");
			isCompare = FingerDevice.getInstance().compare(cusBytes, arr);
			if(isCompare) {
				tempCus = c;
				break;
			}
		}
		
		if(isCompare) {
			tempCus.setValue("ip", param.getString("ip"));
			tempCus.setValue("mac", mac);
			tempCus.setValue("cpu", param.getString("cpu"));
			tempCus.setValue("clientId", param.getString("mac"));
			tempCus.setValue("logType", "1");
			tempCus.setValue("logTime",nowStr);
			BaseOptDao.insert("zfdb.loginLog", tempCus);
			BaseOptDao.insert("zfdb.loginLogCurrent", tempCus);
			
			return getTrue()+"|指纹匹配成功";
		}else {
			return getFalse() +"|指纹不匹配";
		}
		
	}

	


	public String logout(DataObject param) {
		
		String clientId = param.getString("mac");
		DataObject cusParam = new DataObject().setValue("clientId", clientId);
		List<DataObject> cusList = BaseOptDao.executeQuery("platformdb.customer.queryLastLogin", cusParam);
		
		if(cusList.isEmpty()) {
			return getFalse() + "|没有登入记录";
		}
		String nowStr = DateUtils.formatDate(new Date(), DateUtils.SHORT_DATE_FORMAT);
		logoutByLoginList(cusList,param,nowStr);
		

	//	delLastLogin
		
		return getTrue()+ "|登出成功";
	}

	
	/**
	 * 登出
	 * @param cusList
	 * @param param
	 */
	private void logoutByLoginList(List<DataObject> cusList, DataObject param,String nowStr) {
		String clientId = param.getString("mac");
		DataObject cusParam = new DataObject().setValue("clientId", clientId);
		
		DataObject data = cusList.get(0);
		data.setValue("ip", param.getString("ip"));
		data.setValue("mac", param.getString("mac"));
		data.setValue("cpu", param.getString("cpu"));
		data.setValue("clientId", clientId);
		data.setValue("logType", "2");
		data.setValue("logTime",nowStr );
		BaseOptDao.insert("zfdb.loginLog", data);
		BaseOptDao.execute("platformdb.customer.delLastLogin", cusParam);
	}




	private String getFalse() {
		return String.valueOf(false);
	}
	
	private String getTrue() {
		return String.valueOf(true);
	}

}
