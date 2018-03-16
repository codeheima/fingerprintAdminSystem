package org.ma.db.jdbc;

import java.sql.Connection;


public interface IConnection {
	Connection getConn();
	
	
	void close();
}
