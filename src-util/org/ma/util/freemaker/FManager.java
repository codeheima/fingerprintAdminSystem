package org.ma.util.freemaker;

import java.util.List;
import java.util.Map;

import org.ma.util.ComplexUtil;

/**
 * 
 * @author Archmage
 */
public class FManager {
	/**
	 * 
	 */
	private Map<String,Map<String,FModel>> map = ComplexUtil.map();
	
	/**
	 * 当为debug模式时，每次调用模板时都会从xml中重新读取
	 */
	private boolean debug = false;
	
	/**
	 * 主要用来检测是否加重了
	 */
	private List<FModel> list = ComplexUtil.list();
	
	
	public FModel findModel(String ns,String name){
		if(map.containsKey(ns)){
			return map.get(ns).get(name);
		}
		return null;
	}
	
	public void readUrls(List<String> urls){
		FReader.readURL(this,urls);
	}
	
	public boolean addModel(FModel model){
		if(!model.check()){
			return false;
		}
		Map<String,FModel> modelMap =  map.get(model.getNs());
		if(modelMap == null){
			modelMap= ComplexUtil.map();
			map.put(model.getNs(), modelMap);
		}
		modelMap.put(model.getName(), model);
		list.add(model);
		model.setFm(this);
		return true;
	}
	
	
	public String toString() {
		StringBuilder s = new StringBuilder("FManager: modelsize=").append(list.size()).append("\n");
		for(FModel m : list){
			s.append(m).append("\n");
		}
		return s.toString();
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}
	
	
}
