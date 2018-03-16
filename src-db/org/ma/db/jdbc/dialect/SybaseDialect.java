package org.ma.db.jdbc.dialect;

import java.sql.Types;


public class SybaseDialect implements Dialect{
	
	private static final long serialVersionUID = 1L;
	
	public String getName() {
		return "sybase";
	}	

	public String getPrefixEsc() {
		return "[";
	}

	public String getSuffixEsc() {
		return "]";
	}

	public String getLimitQueryScript(String sql, int begin, int end) {

		return "EXEC P_DIV_QUERY '"+sql+"',"+begin+","+end;
	}
	
	public String getTopQueryScript(String sql,int max){
		return "SELECT TOP "+max+" * FROM ("+sql+") T_T";
	}
	
	public String getCountByMaxLimit(String sql,int max){
		return "SELECT TOP "+max+" COUNT(1) TOTAL FROM ("+sql+") T_T";
	}	

	public String dateValue(String date) {
		return "'"+date+"'";
	}
	
	public String getCurrentTimeFunctionName() {
		return "getdate()";
	}	
	
	public String getCurrentTimeScript() {
		return "select getdate()";
	}	
	
	/**
	 * 检查表中是否存在数据的脚本
	 * @param tableName		表名
	 * @return
	 */
	public String existDataCheckScript(String tableName){
		return "select top 1 * from " + tableName;
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
		return "sp_rename " + oldTableName+"," + newTableName;
	}
	
	/**
	 * 得到删除主键的脚本
	 * @param tableName			表名
	 * @param constraintName	主键名
	 * @return
	 */
	public String getDropPrimaryKeyScript(String tableName,String constraintName){
		return "ALTER TABLE " + tableName + " DROP CONSTRAINT " + constraintName;
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
			.append(" ").append(defaultValue==null?"":"DEFAULT "+defaultValue).append(" NULL").toString();
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
			.append(" ").append(nullAble==0?"":(nullAble==1?"NULL":"NOT NULL")).toString();
	}
	/**
	 * 得到删除列的脚本
	 * @param tableName		表名
	 * @param columnName	列表
	 * @return
	 */
	public String getDropColumnScript(String tableName,String columnName){
		return new StringBuffer().append("ALTER TABLE ").append(tableName).append(" DROP ").append(getPrefixEsc()).append(columnName).append(getSuffixEsc()).toString();		
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
		return "sp_rename '" + tableName+"."+oldColumnName+"'," + newColumnName;
	}
	
	public String getDataType(int dataType,int length,int scale){
		switch(dataType){
			case Types.BIT:
				return "TINYINT";										
			case Types.BIGINT:
				return "BIGINT";
			case Types.SMALLINT:
				return "SMALLINT";
			case Types.TINYINT:
				return "TINYINT";
			case Types.INTEGER:
				return "INT";
			case Types.CHAR:
				return "CHAR("+length+")";
			case Types.VARCHAR:
				return "VARCHAR("+length+")";
			case Types.FLOAT:
				return "FLOAT";
			case Types.DOUBLE:
				return "DOUBLE";
			case Types.DATE:
				return "DATE";
			case Types.TIME:
				return "DATETIME";
			case Types.TIMESTAMP:
				return "DATETIME";
			case Types.VARBINARY:
				if(length>8000)
					return "IMAGE";
				else
					return "VARBINARY("+length+")";
			case Types.NUMERIC:
				return "NUMERIC("+length+","+scale+")";
			case Types.DECIMAL:
				return "DECIMAL("+length+","+scale+")";				
			case Types.BLOB:
				return "IMAGE";
			case Types.BINARY:
				return "IMAGE";	
			case Types.LONGVARBINARY:
				return "IMAGE";
			case Types.LONGVARCHAR:
				return "TEXT";				
			case Types.CLOB:
				return "TEXT";
			default:
				return "VARCHAR("+length+")";
		}
	}
	
	public String getDefaultTableName(String name) {
		
		return name;
	}	

}
//需要存储过程的支持，存储过程如下：
//CREATE PROC [P_DIV_QUERY]
//             @SQL	      VARCHAR(10240),	--查询SQL
//             @MIN	      INT,	          --查询的表
//             @MAX	      INT             --查询条件
// AS
// BEGIN
//     DECLARE @EXECSQL VARCHAR(10240)
//     SET TRANSACTION  ISOLATION LEVEL 0	---允许脏读
//     SET @EXECSQL = STUFF(@SQL,CHARINDEX('SELECT',@SQL),6,'SELECT TOP '+CONVERT(VARCHAR,@MAX)+' CURRENT_ROW=IDENTITY(12),')
//     SET @EXECSQL = STUFF(@EXECSQL, CHARINDEX('FROM',@EXECSQL),4,'INTO #T FROM')
//     SET @EXECSQL = @EXECSQL + ' SELECT * FROM #T WHERE CURRENT_ROW>='+CONVERT(VARCHAR,@MIN)+' AND CURRENT_ROW<='+CONVERT(VARCHAR,@MAX)
//     EXECUTE(@EXECSQL)
// END
