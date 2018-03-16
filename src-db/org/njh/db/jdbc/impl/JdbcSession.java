package org.ma.db.jdbc.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.pool.DBPool;
import org.ma.db.jdbc.pool.PoolConn;
import org.ma.util.MyUUID;

public class JdbcSession implements ISession{
	
	private static final Log log = LogFactory.getLog(JdbcSession.class);
	
	private PoolConn poolConn = null;
	private Connection c = null;
	private String name;
	private List<Object> clears;
	
	private String id;
	
	public JdbcSession(String name,PoolConn conn){
		poolConn = conn;
		this.name = name;
		c = conn.getConn().getConn();
		clears = new ArrayList<Object>();
		id = MyUUID.getUUID();
	}

	public Connection getConn() {
		return c;
	}

	public DBPool getPool() {
		return poolConn.getSrcPool();
	}

	public String getName() {
		return name;
	}

	public void close() {
		//do nothing
		try{
			clear();
		}catch(Exception err){
			poolConn.getConn().close();
		}finally{
			poolConn.getSrcPool().addPoolConn(poolConn);
		}
		log.info("close session:"+ this.name);
	}
	
	public void openTrans() {
		try {
			c.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void commit() {
		try {
			c.commit();
			c.setAutoCommit(true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	public void rollback() {
		try {
			c.rollback();
			c.setAutoCommit(true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 添加一个待清除对象
	 * @param object
	 */
	public void addClears(Object object){
		if(clears==null)
			clears = new ArrayList<Object>();
		clears.add(object);
	}
	
	/**
	 * 清除资源statement/resultset
	 */
	private void clear(){
		if(clears!=null && clears.size()>0){
			for(int i=0;i<clears.size();i++){
				Object obj = clears.get(i);
				if(obj!=null){
					try {
						if(obj instanceof ResultSet){
							((ResultSet)obj).close();
						}else if(obj instanceof Statement){
							((Statement)obj).close();
						}
					} catch (SQLException e) {}
				}
			}
			clears.clear();
		}
	}

	public String getId() {
		return id;
	}
	
}
