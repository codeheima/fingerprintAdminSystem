package org.ma.db.jdbc.pool;

import org.ma.db.jdbc.IConnection;

public class PoolConn {

	private IConnection conn;
	
	private int currentIndex;
	
	
	private DBPool srcPool;
	
	

	public DBPool getSrcPool() {
		return srcPool;
	}

	public void setSrcPool(DBPool srcPool) {
		this.srcPool = srcPool;
	}

	public IConnection getConn() {
		return conn;
	}

	public void setConn(IConnection conn) {
		this.conn = conn;
	}

	
	public int getCurrentIndex() {
		return currentIndex;
	}
	

	void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
	
}
