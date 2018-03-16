package org.ma.app.action;


import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.Req;
import net.sf.json.JSONObject;

import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.db.jdbc.DataObject;

@Action(name = "test")
public class TestAction {

	
	@Req(url = "exe.do")
	public void exe(){
		
		DataObject params = ActionControl.getParams();
		
		System.out.println(params);
		
		System.out.println("exe.do");
		JSONObject json = new JSONObject();
		json.put("123", "555");
		json.put("666", "777");
		json.put("name", "7买买提");
//		throw new RuntimeException("haha");
		WebReturnUtil.returnJson(json);
	}
	
	
	@Req(url = "pop.do")
	public void pop(){
		
		
	}
	
}
