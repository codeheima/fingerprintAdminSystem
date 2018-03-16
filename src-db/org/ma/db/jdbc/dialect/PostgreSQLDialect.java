package org.ma.db.jdbc.dialect;

import java.sql.Types;


public class PostgreSQLDialect implements Dialect{
	
	private static final long serialVersionUID = 1L;

	public String getPrefixEsc() {
		//return "\"";
		return "";
	}

	public String getSuffixEsc() {
		//return "\"";
		return "";
	}

	public String getLimitQueryScript(String sql, int begin, int end) {

		//return "SELECT * FROM ("+sql+") T_T LIMIT " + (end-begin+1) + " OFFSET " + (begin-1);
		return sql + " LIMIT " + (end-begin+1) + " OFFSET " + (begin-1);
	}
	
	public String getTopQueryScript(String sql,int max){
		return "SELECT * FROM ("+sql+") T_T LIMIT " + max + " OFFSET 0";
	}
	
	public String getCountByMaxLimit(String sql,int max){
		return "SELECT COUNT(1) TOTAL FROM ("+sql+") T_T LIMIT " + max + " OFFSET 0";
	}

	public String dateValue(String date) {
		return "'"+date+"'";
	}

	public String getName() {
		return "postgresql";
	}

	public String getCurrentTimeFunctionName() {
		return "now()";
	}

	public String getCurrentTimeScript() {
		return "select now()";
	}
	
	/**
	 * 检查表中是否存在数据的脚本
	 * @param tableName		表名
	 * @return
	 */
	public String existDataCheckScript(String tableName){
		return "SELECT * FROM " + tableName + " LIMIT 1 OFFSET 0";
	}
	/**
	 * 得到数据库schema
	 * @param userId		登录用户ID
	 * @return
	 */
	public String getSchema(String userId) {
		return null;
	}
	
	/**
	 * 得到更改表名的脚本
	 * @param oldTableName	原表名
	 * @param newTableName	新表名
	 * @return
	 */
	public String getRenameTableScript(String oldTableName,String newTableName){
		return "RENAME TABLE " + oldTableName+" TO " + newTableName;
	}
	
	/**
	 * 得到删除主键的脚本
	 * @param tableName			表名
	 * @param constraintName	主键名
	 * @return
	 */
	public String getDropPrimaryKeyScript(String tableName,String constraintName){
		return "ALTER TABLE "+tableName+" DROP PRIMARY KEY";
	}
	/**
	 * 得到添加主键的脚本
	 * @param tableName			表名
	 * @param constraintName	主键名
	 * @param columns			主键列
	 * @return
	 */
	public String getAddPrimaryKeyScript(String tableName,String constraintName,String columns){
		return "ALTER TABLE "+tableName+" ADD CONSTRAINT "+constraintName+" PRIMARY KEY("+columns+")";
	}
	/**
	 * 得到添加新列的脚本
	 * @param tableName		表名
	 * @param columnName	列名
	 * @param dataType		数据类型
	 * @param length		长度
	 * @param scale			小数位长度
	 * @param nullAble		是否允许为空	1：允许空 2：不允许空
	 * @param defaultValue	默认值
	 * @return
	 */
	public String getAddColumnScript(String tableName,String columnName,
			int dataType,int length,int scale,int nullAble,String defaultValue){
		return new StringBuffer()
			.append("ALTER TABLE ").append(tableName).append(" ADD ").append(getPrefixEsc()).append(columnName).append(getSuffixEsc())
			.append(" ").append(getDataType(dataType, length, scale))
			.append(" ").append(defaultValue==null?"":"DEFAULT "+defaultValue)
			.append(" ").append(nullAble==1?"NULL":"NOT NULL").toString();	
	}
	/**
	 * 得到修改列的脚本
	 * @param tableName		表名
	 * @param columnName	列名
	 * @param dataType		数据类型
	 * @param length		长度
	 * @param scale			小数位长度
	 * @param nullAble		是否允许为空	0：未改变 1：允许空 2：不允许空
	 * @param defaultValue	默认值
	 * @return
	 */
	public String getModifyColumnScript(String tableName,String columnName,
			int dataType,int length,int scale,int nullAble,String defaultValue){
		return new StringBuffer()
			.append("ALTER TABLE ").append(tableName).append(" MODIFY ").append(getPrefixEsc()).append(columnName).append(getSuffixEsc())
			.append(" ").append(getDataType(dataType, length, scale))
			.append(" ").append(defaultValue==null?"":"DEFAULT "+defaultValue)
			.append(" ").append(nullAble==0?"":(nullAble==1?"NULL":"NOT NULL")).toString();
	}
	/**
	 * 得到删除列的脚本
	 * @param tableName		表名
	 * @param columnName	列表
	 * @return
	 */
	public String getDropColumnScript(String tableName,String columnName){
		return new StringBuffer().append("ALTER TABLE ").append(tableName).append(" DROP COLUMN ").append(getPrefixEsc()).append(columnName).append(getSuffixEsc()).toString();		
	}
	
	/**
	 * 得到列改名的脚本
	 * @param tableName		表名
	 * @param columnName	列名
	 * @param dataType		数据类型
	 * @param length		长度
	 * @param scale			小数位长度
	 * @param nullAble		是否允许为空	1：允许空 2：不允许空
	 * @param defaultValue	默认值
	 * @return
	 */
	public String getRenameColumnScript(String tableName,String oldColumnName,String newColumnName,
			int dataType,int length,int scale,int nullAble,String defaultValue){
		return new StringBuffer().append("ALTER TABLE ").append(tableName)
			.append(" CHANGE ")
			.append(getPrefixEsc()).append(oldColumnName).append(getSuffixEsc())
			.append(" ")
			.append(getPrefixEsc()).append(newColumnName).append(getSuffixEsc())
			.append(" ").append(getDataType(dataType, length, scale))
			.append(" ").append(defaultValue==null?"":"DEFAULT "+defaultValue)
			.append(" ").append(nullAble==1?"NULL":"NOT NULL").toString();	
	}
	
	public String getDataType(int dataType,int length,int scale){	
		switch(dataType){
			case Types.BIT:
				return "BIT";										
			case Types.BIGINT:
				return "BIGINT";
			case Types.SMALLINT:
				return "SMALLINT";
			case Types.TINYINT:
				return "TINYINT";
			case Types.INTEGER:
				return "INTEGER";
			case Types.CHAR:
				return "CHAR("+length+")";
			case Types.VARCHAR:
				if(length>16777215)
					return "LONGTEXT";
				else if(length>65535)
					return "MEDIUMTEXT";
				else
					return "VARCHAR("+length+")";
			case Types.FLOAT:
				return "FLOAT";
			case Types.REAL:
				return "FLOAT";
			case Types.DOUBLE:
				return "DOUBLE";
			case Types.DATE:
				return "DATE";
			case Types.TIME:
				return "TIME";
			case Types.TIMESTAMP:
				return "DATETIME";
			case Types.VARBINARY:
				return "VARBINARY";
			case Types.BINARY:
				return "BINARY";
			case Types.NUMERIC:
				return "NUMERIC("+length+","+scale+")";
			case Types.DECIMAL:
				return "NUMERIC("+length+","+scale+")";				
			case Types.BLOB:
				return "BLOB";
			case Types.LONGVARBINARY:
				return "BLOB";
			case Types.LONGVARCHAR:
				return "LONGTEXT";
			case Types.CLOB:
				return "TEXT";
			default:
				return "VARCHAR("+length+")";	
		}
	}	
	
	public String getDefaultTableName(String name) {
		
		return name.toLowerCase();
	}	

}
