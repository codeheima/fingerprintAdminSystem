package org.ma.db.jdbc.pool.init;

import java.io.UnsupportedEncodingException;

import org.dom4j.Node;
import org.ma.db.jdbc.dialect.Dialect;
import org.ma.db.jdbc.dialect.DialectFactory;
import org.ma.util.Dom4jUtil;

public class SinglePoolConfig {
	
	private String dbName;
	
	private String driverclass;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String dbType;
	
	private String method;   //jdbc | hbase | xxx
	
	private int maxActive = 15;
	private int minActive =  1;
	
	private String encode;
	
	private int maxUnitlength;
	
	private Dialect dialect = null;
	
	public SinglePoolConfig(){
		
	}
	
	public SinglePoolConfig(Node n) {
		initByNode(n);
	}
	
	
	

	public String getDriverclass() {
		return driverclass;
	}
	public void setDriverclass(String driverclass) {
		this.driverclass = driverclass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMinActive() {
		return minActive;
	}
	public void setMinActive(int minActive) {
		this.minActive = minActive;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	
	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public int getMaxUnitlength() {
		return maxUnitlength;
	}

	public void setMaxUnitlength(int maxUnitlength) {
		this.maxUnitlength = maxUnitlength;
	}

	public void initByNode(Node n) {
		dbName = Dom4jUtil.attrVal(n, "", "name", "");
		dbType = Dom4jUtil.getText(n,"properties/property[@name='type']","mysql");
		driverclass = Dom4jUtil.getText(n,"properties/property[@name='driverclass']","");
		url = Dom4jUtil.getText(n,"properties/property[@name='url']","");
		username = Dom4jUtil.getText(n,"properties/property[@name='user']","");
		password = Dom4jUtil.getText(n,"properties/property[@name='password']","");
		method = Dom4jUtil.getText(n,"properties/property[@name='method']","");
		maxActive = Integer.parseInt(Dom4jUtil.getText(n,"properties/property[@name='maxActive']",""));
		
		minActive = Integer.parseInt(Dom4jUtil.getText(n,"properties/property[@name='minActive']",""));
		encode = Dom4jUtil.getText(n,"properties/property[@name='encode']","UTF-8");
		
		dialect = DialectFactory.getDialect(dbType);
		try {
			this.maxUnitlength = "大".getBytes(this.encode).length;
		} catch (UnsupportedEncodingException e) {
			this.maxUnitlength = 2;
		}
	}
}
