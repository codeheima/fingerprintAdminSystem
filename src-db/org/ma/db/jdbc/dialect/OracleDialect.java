package org.ma.db.jdbc.dialect;

import java.sql.Types;


public class OracleDialect implements Dialect {

	private static final long serialVersionUID = 1L;

	public String getPrefixEsc() {
		return "";
	}

	public String getSuffixEsc() {
		return "";
	}

	public String getLimitQueryScript(String sql, int begin, int end) {

		return "SELECT * FROM (SELECT ROWNUM ROW_ID,T_T.* FROM (" + sql + ") T_T WHERE ROWNUM<=" + end + ") WHERE ROW_ID>=" + begin;
	}
	
	public String getTopQueryScript(String sql,int max){
		return "SELECT ROWNUM ROW_ID,T_T.* FROM (" + sql + ") T_T WHERE ROWNUM<=" + max;
	}
	
	public String getCountByMaxLimit(String sql,int max){
		return "SELECT MAX(ROWNUM) TOTAL FROM (" + sql + ") T_T WHERE ROWNUM<=" + max;
	}	

	public String dateValue(String date) {
		return "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
	}

	public String getName() {
		return "oracle";
	}

	public String getCurrentTimeFunctionName() {
		return "sysdate";
	}

	public String getCurrentTimeScript() {
		return "select sysdate from dual";
	}
	/**
	 * 检查表中是否存在数据的脚本
	 * @param tableName		表名
	 * @return
	 */
	public String existDataCheckScript(String tableName){
		return "select rownum from " + tableName + " where rownum<=1";
	}
	/**
	 * 得到数据库schema
	 * @param userId		登录用户ID
	 * @return
	 */
	public String getSchema(String userId) {
		if (userId == null)
			return null;
		else
			return userId.toUpperCase();
	}
	
	/**
	 * 得到更改表名的脚本
	 * @param oldTableName	原表名
	 * @param newTableName	新表名
	 * @return
	 */
	public String getRenameTableScript(String oldTableName,String newTableName){
		return "RENAME " + oldTableName+" TO " + newTableName;
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
			.append(" RENAME COLUMN ")
			.append(getPrefixEsc()).append(oldColumnName).append(getSuffixEsc())
			.append(" TO ")
			.append(getPrefixEsc()).append(newColumnName).append(getSuffixEsc()).toString();
	}
	

	/**
	 * 得到指定数据类型在数据库中的类型名称
	 */
	public String getDataType(int dataType, int length, int scale) {
		switch (dataType) {
		case Types.BIT:
			return "SMALLINT";
		case Types.BIGINT:
			return "NUMBER(19,0)";
		case Types.SMALLINT:
			return "NUMBER(5,0)";
		case Types.TINYINT:
			return "NUMBER(3,0)";
		case Types.INTEGER:
			return "INTEGER";
		case Types.CHAR:
			return "CHAR(" + length + ")";
		case Types.VARCHAR:
			if (length > 4000)
				return "CLOB";
			else
				return "VARCHAR2(" + length + ")";
		case Types.FLOAT:
			return "FLOAT";
		case Types.DOUBLE:
			return "DOUBLE";
		case Types.DATE:
			return "DATE";
		case Types.TIME:
			return "DATE";
		case Types.TIMESTAMP:
			return "DATE";
		case Types.VARBINARY:
			if (length > 2000)
				return "LONG RAW";
			else
				return "RAW(" + length + ")";
		case Types.NUMERIC:
			return "NUMBER(" + length + "," + scale + ")";
		case Types.DECIMAL:
			return "NUMBER(" + length + "," + scale + ")";
		case Types.BLOB:
			return "BLOB";
		case Types.BINARY:
			return "BLOB";
		case Types.LONGVARBINARY:
			return "BLOB";
		case Types.LONGVARCHAR:
			return "CLOB";
		case Types.CLOB:
			return "CLOB";
		default:
			return "VARCHAR2(" + length + ")";
		}
	}
	
	public String getDefaultTableName(String name) {
		
		return name.toUpperCase();
	}	
}
