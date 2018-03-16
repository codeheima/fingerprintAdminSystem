package org.ma.db.jdbc.dialect;



public class DialectFactory {
	
	/**
	 * 根据数据库类型得到方言
	 * @param type
	 * @return
	 */
	public static Dialect getDialect(String type){
		if(type.equals("oracle"))
			return new OracleDialect();
		else if(type.equals("sybase"))
			return new SybaseDialect();
		else if(type.equals("mysql"))
			return new MysqlDialect();	
		else if(type.equals("postgresql"))
			return new PostgreSQLDialect();			
		else
			throw new RuntimeException("不支持的数据库类型\""+type+"\"");
	}

}
