package org.ma.util;


/**
 * @author Archmage
 */

public class HttpResult {
	private int responseCode;
	/**
	 * 连接失败：（连接超时）
	 */
	private boolean isTimeOut = false;
	
	private boolean isCommonResp = true;
	
	private String responseMsg;
	
	
	public HttpResult(){
		
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public boolean isTimeOut() {
		return isTimeOut;
	}
	public void setTimeOut(boolean isTimeOut) {
		this.isTimeOut = isTimeOut;
	}
	public boolean isCommonResp() {
		return isCommonResp;
	}
	public void setCommonResp(boolean isCommonResp) {
		this.isCommonResp = isCommonResp;
	}
	
	
}