package org.ma.db.jdbc.pool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.ma.db.jdbc.IConnection;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.dialect.Dialect;
import org.ma.db.jdbc.dialect.DialectFactory;
import org.ma.db.jdbc.impl.DBConn;
import org.ma.db.jdbc.impl.JdbcSession;
import org.ma.db.jdbc.pool.init.SinglePoolConfig;

public class DBPool {
	
	private Set<String> dbNameSet;
	
	private SinglePoolConfig config;
	
	private List<PoolConn> dbList;
	
	
	private BlockingQueue<PoolConn> queue;
	
	
	private static ThreadLocal<Set<PoolConn>>  usedConn = new ThreadLocal<Set<PoolConn>>();
	
	
	private Dialect dialect;
	
	
	public void setConfig(SinglePoolConfig config) {
		this.config = config;
	}


	public SinglePoolConfig getConfig(){
		return config;
	}
	
	
	public Dialect getDialect() {
		return dialect;
	}


	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}


	public void build(){
		String dbName = config.getDbName();
		dbNameSet = new HashSet<String>();
		if(dbName.contains("|")){
			String [] ss = dbName.split("\\|");
			for(int i = 0; i< ss.length; i++){
				dbNameSet.add(ss[i]);
			}
		}else{
			dbNameSet.add(dbName);
		}
		
		
		int min = config.getMinActive() > 0 &&  config.getMinActive() <100 ?config.getMinActive() : 1;
		int max = config.getMaxActive() > 0 && config.getMinActive() > min ?  config.getMaxActive() : min + 5;
		config.setMinActive(min);
		config.setMaxActive(max);
		
		dbList =  new ArrayList<PoolConn>();
		
		queue = new LinkedBlockingQueue<PoolConn>(max);
		
		for(int i = 0 ; i< max; i++){
			DBConn dbConn = createDBConn();
			if( i < min){
				//init min
				dbConn.getConn();
			}
			//处理conn
			PoolConn poolConn = new PoolConn();
			poolConn.setConn(dbConn);
			poolConn.setSrcPool(this);
			
			dbList.add(poolConn);
			addPoolConn(poolConn);
			
		}
		
	}
	
	public void releasUsed(){
		usedConn.get();
		
	}
	
	public void  addPoolConn(PoolConn conn){
		try {
			queue.put(conn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public PoolConn getPoolConn(){
		
		try {
			return queue.poll(5,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private DBConn createDBConn() {
		
		DBConn dbConn = new DBConn();
		dbConn.setDriverclass(config.getDriverclass());
		dbConn.setUrl(config.getUrl());
		dbConn.setPassword(config.getPassword());
		dbConn.setUsername(config.getUsername());
		dbConn.setDbType(config.getDbType());
		
		this.dialect = DialectFactory.getDialect(config.getDbType());
		
		dbConn.build();
		return dbConn;
	}


	public Set<String> getDbNameSet() {
		return dbNameSet;
	}


	public ISession openSession(String dbName) {
		ISession session = new JdbcSession(dbName,this.getPoolConn());
		
		return session;
		
	}


	public void close() {
		for(PoolConn pc : dbList){
			try{
				IConnection ic = pc.getConn();
				if(ic ==null){
					continue;
				}
				Connection c = ic.getConn();
				if(c == null)
					continue;
				c.close();
			}catch(Exception err){
				err.printStackTrace();
			}
		}
	}



	
	
}
