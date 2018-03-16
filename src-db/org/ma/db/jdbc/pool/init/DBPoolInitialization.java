package org.ma.db.jdbc.pool.init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.dao.BaseOptDao;
import org.ma.db.jdbc.pool.DBPool;
import org.ma.db.jdbc.util.ExecuteUtil;
import org.ma.util.Dom4jUtil;


/**
 * db 初始化
 *
 */
public class DBPoolInitialization {
	
	private DBConfigBean configBean;
	
	private static DBPoolInitialization dbControl= new  DBPoolInitialization();
	
	public static void init(){
		
		Document doc = Dom4jUtil.getCpDoc("db.xml");
		dbControl.configBean = new DBConfigBean(doc);
		List<SinglePoolConfig> list = dbControl.configBean.getPoolConfigList();
		//pool
		for(SinglePoolConfig config : list){
			DBPool pool =new DBPool();
			pool.setConfig(config);
			pool.build();
			SessionFactory.INSTANCE.registDBPool(pool);
		}
		
		Set<Table> tables = dbControl.configBean.getTables();
		
		SessionFactory.initTables(tables);
	}
	
	
		
	
	public static DBConfigBean getConfigBean() {
		
		return dbControl.configBean;
	}


}
