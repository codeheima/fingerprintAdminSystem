package org.ma.db.jdbc.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.db.jdbc.IConnection;
import org.ma.util.ExceptionUtil;



public class DBConn implements IConnection{
	private static final Log log = LogFactory.getLog(DBConn.class);
	
	public static final String DBTYPE_MYSQL = "mysql";
	public static final String DBTYPE_ORACLE = "oracle";
	
	/** 0 is not  null; 1 is not close  ;  2 is valid  */
	public static  int checkLevel = 1;
	
	
	private String driverclass;
	private String url;
	private String username;
	private String password;
	private String dbType = "mysql";
	
	
	private Connection conn = null;
	

	public DBConn build(){
	    try {
			Class.forName(driverclass);
		} catch (ClassNotFoundException e) {
			System.out.println("driverclass 有问题:" + this);
			throw new RuntimeException(e);
		} //classLoader,加载对应驱动
		
	    
		return this;
	}
	
	public Connection getConn(){
		if(isNormalUse()){
			
			return conn;
			
		}else{
		
			conn = getNewConn(this.driverclass,this.url,this.username,this.password);
			
		}
		
		return conn;
		
	}

	
	
	private boolean isNormalUse(){
		if(conn == null){
			return false;
		}
		if(checkLevel <= 0){
			return true;
		}
		
		try {
			if(conn.isClosed()){
				return false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
		}
		
		if(checkLevel <= 1){
			return true;
		}
		
		try {
			return conn.isValid(0);
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
		}
		
	}
	
	private static Connection getNewConn(String driverclass,String url,String username,String password) {
		Connection conn = null;
		    try {
		        conn = (Connection) DriverManager.getConnection(url, username, password);
		    }catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }
	    return conn;
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

	public void close() {
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			log.warn( ExceptionUtil.getCommonStr(e));
		}
	}


	
	
	
}


