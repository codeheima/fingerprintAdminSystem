package org.ma.db.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.dialect.DialectFactory;
import org.ma.db.jdbc.pool.DBPool;
import org.ma.db.jdbc.pool.init.SinglePoolConfig;
import org.ma.db.jdbc.pool.init.Table;
import org.ma.db.jdbc.util.ExecuteUtil;
import org.ma.util.ComplexUtil;





public enum SessionFactory {
	
	INSTANCE;
	
	private Map<String,DBPool> poolNameMap = new HashMap<String,DBPool>();
	
	/** key = tableId */
	private Map<String,Table> tableMap = new HashMap<String,Table>();
	
	private List<DBPool> poolList = new ArrayList<DBPool>();
	
	private static final ThreadLocal<Map<String,ISession>> sessionLocal = new ThreadLocal<Map<String,ISession>>();
	
	private static final Log log = LogFactory.getLog(SessionFactory.class);
	
	
	public static ISession getSession(String dbName){
		return INSTANCE.currentSession(dbName);
	}
	
	public static void closeSession(){
		INSTANCE.closeAllSession();
	}
	
	public ISession currentSession(String dbName){
	
		if(dbName==null){
			dbName = "db";
		}
		log.info("open session \"" + dbName+"\"");
		ISession session = null;
		Map<String,ISession> sessions = sessionLocal.get();
		
		if(sessions == null){
			sessions = new HashMap<String,ISession>();
			sessionLocal.set(sessions);
		}
		
		if(sessions.containsKey(dbName)){
			//如果当前session存在，取得session
			session = sessions.get(dbName);
		}else{

			long l1 = System.currentTimeMillis();
			DBPool pool = poolNameMap.get(dbName);
			//根据curserId 打开session的方法

			session = pool.openSession(dbName);
			sessions.put(dbName, session);
			
			long l2 = System.currentTimeMillis();
			if((l2 - l1) > 1000){
				log.error("创建session耗时严重：" + (l2 - l1));
			}
		}
		return session;	
		
	}
	
	
	
	public  void closeAllSession() {
		
		Map<String,ISession> sessions = sessionLocal.get();
		if(sessions!=null){
			Iterator<ISession> it = sessions.values().iterator();
			for(;it.hasNext();){
				ISession session = it.next();
				try {
					
					session.close();
				} catch (Exception e) {
					log.error("关闭数据库\""+session.getName() +"\"连接失败",e);
				}				
			}
			sessionLocal.set(null);
		}
		
		ExecuteUtil.clearLocal();
		
	}
	
	/**
	 * 没事别乱用
	 */
	public void closeConn(){
		for(DBPool pool :poolList){
			pool.close();
		}
	}
	
	public void registDBPool(DBPool pool){
		Set<String> set = pool.getDbNameSet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			poolNameMap.put(it.next(),pool);
		}
		
		poolList.add(pool);
	}
	
	public List<DBPool> getPoolList(){
		return poolList; 
	}
	
	

	public static void initTables(Set<Table> tables) {
	
		SessionFactory.INSTANCE.initAllTables(tables);
	}
	
	public static Table getTable(String tableId){
		return SessionFactory.INSTANCE.tableMap.get(tableId);
	}

	private void initAllTables(Set<Table> tables) {
		Map<String,List<Table>> jdbcMap =new HashMap<String,List<Table>>();
		for(Table t : tables){
			String dbName = t.getDb();
			DBPool pool =poolNameMap.get(dbName);
			if("jdbc".equals(pool.getConfig().getMethod())){
				List<Table> list = null;
				if(!jdbcMap.containsKey(dbName)){
					list = new ArrayList<Table>();
					jdbcMap.put(dbName, list);
				}else{
					list = jdbcMap.get(dbName);
				}
				list.add(t);
			}
		}
		
		initJdbcTables(jdbcMap);
	}

	private void initJdbcTables(Map<String, List<Table>> jdbcMap) {
		ComplexUtil.eachMap(jdbcMap, new ComplexUtil.Each<String, List<Table>>() {
			public void each(String dbName, List<Table> tables) {
				try{
					ISession session = currentSession(dbName);
					SinglePoolConfig config = session.getPool().getConfig();
					String dbType = config.getDbType();
					Connection con = session.getConn();
					DatabaseMetaData dbmd = con.getMetaData(); 
					for(Table t : tables){
						ResultSet rs =dbmd.getPrimaryKeys(con.getCatalog(), "", t.getName());
						Set<String> pkSet = ComplexUtil.set();
						while(rs.next()){
							pkSet.add(rs.getString("COLUMN_NAME"));
						}
						
						rs = dbmd.getColumns(con.getCatalog(), "", t.getName(), null); 
					//	ResultSetMetaData meta = rs.getMetaData();
						
						List<Column> pkList = ComplexUtil.list();
						List<Column> colList = ComplexUtil.list();
						Map<String,Column> colMap = ComplexUtil.map();
						while(rs.next()){
							//{BUFFER_LENGTH=7, CHAR_OCTET_LENGTH=15, COLUMN_DEF=12, COLUMN_NAME=3, COLUMN_SIZE=6, DATA_TYPE=4, DECIMAL_DIGITS=8, IS_NULLABLE=17, NULLABLE=10, NUM_PREC_RADIX=9, ORDINAL_POSITION=16, REMARKS=11, SQL_DATA_TYPE=13, SQL_DATETIME_SUB=14, TABLE_CAT=0, TABLE_NAME=2, TABLE_SCHEM=1, TYPE_NAME=5}
							rs.getString("COLUMN_NAME");
							rs.getString("DATA_TYPE");  // java.sql.Types 的 SQL 类型 
							rs.getString("COLUMN_SIZE");  // 列的大小。对于 char 或 date 类型，列的大小是最大字符数，对于 numeric 和 decimal 类型，列的大小就是精度。 
							rs.getString("DECIMAL_DIGITS"); //  小数部分的位数
							
							int nullable = rs.getInt("NULLABLE");
							rs.getString("COLUMN_DEF");
							Column col = new Column();
							col.setName(rs.getString("COLUMN_NAME"));
							col.setDataType(rs.getInt("DATA_TYPE"));
							col.setLength(rs.getInt("COLUMN_SIZE"));
							col.setScale(rs.getInt("DECIMAL_DIGITS"));
							col.setNullAble(nullable == 1 );
							
							if(pkSet.contains(col.getName())){
								col.setPrimaryKey(true);
								pkList.add(col);
							}
							
							colList.add(col);
							colMap.put(col.getPropertyName(), col);
							
						}
						t.setPks(pkList);
						t.setColMap(colMap);
						t.setCols(colList);
						t.setDialect(DialectFactory.getDialect(dbType));
						tableMap.put(t.getId(), t);
						
					}
				}catch(Exception err){
					throw new RuntimeException(err);
				}
			}
		});
		
	}

}
