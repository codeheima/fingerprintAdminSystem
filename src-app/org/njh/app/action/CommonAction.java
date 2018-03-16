package org.ma.app.action;


import java.util.ArrayList;
import java.util.List;

import org.ma.app.control.ActionControl;
import org.ma.app.control.WebReturnUtil;
import org.ma.app.service.CommonService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.util.EmptyUtil;
import org.ma.util.JSONUtil;

import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.Req;
import net.sf.json.JSONObject;

@Action(name = "common")
public class CommonAction {

	@AutoField
	private CommonService commonService = null;
	
	
	@Req(url = "queryPage.do")
	public void queryPage(){
		DataObject params = ActionControl.getParams();
		int currentPage =  null == params.getObject("currentPage")  ? 1 : params.getInt("currentPage");
		int pageSize =  null == params.getObject("pageSize")  ? 1 : params.getInt("pageSize");
		String scriptId = params.getString("scriptId");
		
		Pagination pg = commonService.queryPage(scriptId,params, currentPage, pageSize);
		
		JSONObject json = JSONObject.fromObject(pg);
		WebReturnUtil.returnJson( json);
	}
	
	@Req(url = "querySql.do")
	public void querySql() {
		DataObject params = ActionControl.getParams();
		String sql = params.getString("sql");
		String dbName = params.getString("dbName");
		List<DataObject> list = commonService.executeQuery(dbName, sql);
		JSONObject out = new JSONObject();
		if(EmptyUtil.isEmptyList(list)) {
			
			out.put("count", 0);
			out.put("datas", new ArrayList<DataObject>());
		}else {
			out.put("count",list.size());
			list.get(0).getBytes("zf1");
			out.put("datas", list);
		}
		
		WebReturnUtil.returnJson(out);
	}
	
	@Req(url = "exeSql.do")
	public void exeSql() {
		DataObject params = ActionControl.getParams();
		String sql = params.getString("sql");
		String dbName = params.getString("dbName");
		commonService.execute(dbName, sql);
		
		WebReturnUtil.returnJson(JSONUtil.getCommonOut(true));
	}
	
	@Req(url = "insert.do")
	public void insert(){
		DataObject params = ActionControl.getParams();
		String tableId = params.getString("tableId");
		commonService.insert(tableId, params);
		
		JSONObject json = JSONUtil.getCommonOut(true, "保存成功");
		WebReturnUtil.returnJson( json);
		
	}
	
	
	@Req(url = "update.do")
	public void update(){
		DataObject params = ActionControl.getParams();
		String tableId = params.getString("tableId");
		commonService.update(tableId, params);
		
		JSONObject json = JSONUtil.getCommonOut(true, "修改成功");
		WebReturnUtil.returnJson( json);
	}
	
	
	@Req(url = "updateWithNotNull.do")
	public void updateWithNotNull(){
		DataObject params = ActionControl.getParams();
		String tableId = params.getString("tableId");
		commonService.updateWithNotNull(tableId, params);
		
		JSONObject json = JSONUtil.getCommonOut(true, "修改成功");
		WebReturnUtil.returnJson( json);
		commonService.updateWithNotNull("", params);
	}
	

	@Req(url = "del.do")
	public void del(){
		DataObject params = ActionControl.getParams();
		String tableId = params.getString("tableId");
		commonService.delete(tableId, params);
		JSONObject json = JSONUtil.getCommonOut(true, "删除成功");
		WebReturnUtil.returnJson( json);
	}
}

