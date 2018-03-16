package org.ma.db.jdbc.bean;

import org.ma.util.freemaker.FModel;
import org.ma.util.freemaker.FSimpleContext;

public class ModelBean {

	private FModel model;
	
	private String dbName;
	private String name;
	private String ns;
	
	public ModelBean(String script){
		
		int last = script.lastIndexOf(".");
		 name = script.substring(last + 1);
		 ns = script.substring(0,last);
		
		int first = script.indexOf(".");
		 dbName = script.substring(0,first);
		
		
		 model = FSimpleContext.fm.findModel(ns,name);
	}

	public FModel getModel() {
		return model;
	}

	public void setModel(FModel model) {
		this.model = model;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}
	
	
	
}
