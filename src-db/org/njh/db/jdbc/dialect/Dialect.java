package org.ma.db.jdbc.dialect;

import java.io.Serializable;


public interface Dialect extends Serializable{
	
	/**
	 * 得到数据库类型名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 得到字段转义前缀
	 * @return
	 */
	public String getPrefixEsc();
	
	/**
	 * 得到字段转义后缀
	 * @return
	 */
	public String getSuffixEsc();	
	
	/**
	 * 得到限定查询脚本
	 * @param sql	原查询脚本
	 * @param begin	起始行
	 * @param end	结束行
	 * @return		
	 */
	public String getLimitQueryScript(String sql,int begin,int end);
	
	/**
	 * 得到指定前几行记录
	 * @param sql
	 * @param max
	 * @return
	 */
	public String getTopQueryScript(String sql,int max);
	
	/**
	 * 查询最大记录数范围以内的记录条数
	 * @param sql
	 * @param max
	 * @return
	 */
	public String getCountByMaxLimit(String sql,int max);
	
	/**
	 * 得到数据库中使用的日期值
	 * @param date	日期格式字符串
	 * @return
	 */
	public String dateValue(String date);
	

	/**
	 * 得到获得当前时间的函数
	 * @return
	 */
	public String getCurrentTimeFunctionName();
	
	/**
	 * 得到当前时间脚本
	 * @return
	 */
	public String getCurrentTimeScript();
	
	/**
	 * 检查表中是否存在数据的脚本
	 * @param tableName		表名
	 * @return
	 */
	public String existDataCheckScript(String tableName);	
	
	/**
	 * 得到数据库schema
	 * @param userId		登录用户ID
	 * @return
	 */
	public String getSchema(String userId);
	
	/**
	 * 得到更改表名的脚本
	 * @param oldTableName	原表名
	 * @param newTableName	新表名
	 * @return
	 */
	public String getRenameTableScript(String oldTableName,String newTableName);
	
	/**
	 * 得到删除主键的脚本
	 * @param tableName			表名
	 * @param constraintName	主键名
	 * @return
	 */
	public String getDropPrimaryKeyScript(String tableName,String constraintName);
	
	/**
	 * 得到添加主键的脚本
	 * @param tableName			表名
	 * @param constraintName	主键名
	 * @param columns			主键列
	 * @return
	 */
	public String getAddPrimaryKeyScript(String tableName,String constraintName,String columns);
	
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
			int dataType,int length,int scale,int nullAble,String defaultValue);
	
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
			int dataType,int length,int scale,int nullAble,String defaultValue);
	
	/**
	 * 得到删除列的脚本
	 * @param tableName		表名
	 * @param columnName	列表
	 * @return
	 */
	public String getDropColumnScript(String tableName,String columnName);
	
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
			int dataType,int length,int scale,int nullAble,String defaultValue);
	
	/**
	 * 得到数据库中数据类型
	 * @param dataType	JAVA类型定义
	 * @param length	长度
	 * @param scale		小数位长度
	 * @return
	 */
	public String getDataType(int dataType, int length, int scale);
	
	/**
	 * 得到数据库默认使用的表名
	 * @param name
	 * @return
	 */
	public String getDefaultTableName(String name);
}
