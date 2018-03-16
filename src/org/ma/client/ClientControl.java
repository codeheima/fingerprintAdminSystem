package org.ma.client;

import java.util.HashMap;
import java.util.Map;

public class ClientControl {
	
	private Map<String,ClientBean> map = new HashMap<String,ClientBean>();
	
	
	private static ClientControl control = new ClientControl();

	
	public static ClientControl getClientControl(){
		return control;
	}
	
	public ClientBean getClient(String key){
		return map.get(key);
	}
	
	public ClientBean addClient(String key,ClientBean bean){
		map.put(key, bean);
		return bean;
	}
	
}
