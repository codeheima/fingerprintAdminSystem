package org.ma.db.jdbc;

import java.sql.Connection;

import org.ma.db.jdbc.pool.DBPool;



public interface ISession {
	
	Connection getConn();
	
	DBPool getPool();
	
	String getName();
	
	void close();
	
	void openTrans();
	
	void commit();
	
	void rollback();
	
	public void addClears(Object object);
	
	
	String getId();
}
	