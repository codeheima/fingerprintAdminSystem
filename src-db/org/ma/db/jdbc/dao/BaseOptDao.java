package org.ma.db.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.db.jdbc.exception.CommondbException;
import org.ma.db.jdbc.pool.init.DBPoolInitialization;
import org.ma.db.jdbc.pool.init.Table;
import org.ma.db.jdbc.util.ExecuteUtil;
import org.ma.db.jdbc.util.SqlUtil;
import org.ma.util.ComplexUtil;
import org.ma.util.EmptyUtil;
import org.ma.util.MyUUID;


/**
 * 数据库基础操作
 *
 */
public class BaseOptDao {
	
	private static final Log log = LogFactory.getLog(BaseOptDao.class);
	
	
	public static List<DataObject> executeQuery(String script,DataObject params){
		return ExecuteUtil.executeQuery(script,params);
	}
	
	public static List<DataObject> executeQuerySql(ISession session,String sql) {
		return ExecuteUtil.executeQueryBySql(session, sql);
	}
	
	public static void executeSql(ISession session,String sql){
		ExecuteUtil.executeSql(session, sql);
	}
	
	public static void execute(String script,DataObject params){
		ExecuteUtil.execute(script, params);
	}
	
	public static Pagination executeQuery(String scriptId, DataObject value, int currentPage, int pageSize){
		return ExecuteUtil.executeQuery(scriptId, value,currentPage,pageSize);
	}
	
	
	public static Object insert(String tableId, DataObject value) throws CommondbException{
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		
		if (value == null)
			throw new CommondbException("没有指定要保存的记录");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Table table =  SessionFactory.getTable(tableId);
		ISession session= SessionFactory.getSession(table.getDb());
		try {
			conn = session.getConn();
			String sql = table.getInsertSql();
			long l = System.currentTimeMillis();
			
			if (isShowSql) {
				log.info(sql);
				
			}
			for (Column column :table.getPks()){
				if(EmptyUtil.isEmptyStr(value.getString(column.getPropertyName()))){
					value.setValue(column.getPropertyName(), MyUUID.getUUID());
				}
			}

			pstmt = conn.prepareStatement(sql);
			int index = 0;
			for (Column column : table.getCols()) {
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			
			pstmt.executeUpdate();

			if (isShowSql) {
				log.info("time:" + (System.currentTimeMillis() - l) + "ms");
			}
			return value;
		} catch (SQLException e) {
			throw new CommondbException(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					
				}
		}
		
	}
	
	
	public static DataObject update(String tableId, DataObject value) throws CommondbException{
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		
		if (value == null)
			throw new CommondbException("没有指定要保存的记录");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Table table =  SessionFactory.getTable(tableId);
		ISession session= SessionFactory.getSession(table.getDb());
		try {
			conn = session.getConn();
			String sql = table.getUpdateSql();
			long l = System.currentTimeMillis();
			
			if (isShowSql) {
				log.info(sql);
				
			}

			pstmt = conn.prepareStatement(sql);
			int index = 0;
		
			for (Column column : table.getCols()) {
				if(column.isPrimaryKey()){
					continue;
				}
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			
			for (Column column : table.getPks()) {
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			
			pstmt.executeUpdate();

			if (isShowSql) {
				log.info("time:" + (System.currentTimeMillis() - l) + "ms");
			}
			return value;
		} catch (SQLException e) {
			throw new CommondbException(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
	public static DataObject updateWithNotNull(String tableId, DataObject value) throws CommondbException{
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		
		if (value == null)
			throw new CommondbException("没有指定要保存的记录");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Table table =  SessionFactory.getTable(tableId);
		ISession session= SessionFactory.getSession(table.getDb());
		try {
			conn = session.getConn();
			String sql = table.getUpdateSqlWithNotNull(value);
			long l = System.currentTimeMillis();
			
			if (isShowSql) {
				log.info(sql);
				
			}

			pstmt = conn.prepareStatement(sql);
			int index = 0;
		
			for (Column column : table.getCols()) {
				Object val = value.getObject(column.getPropertyName());
				if(val == null ||"".equals(val)){
					continue;
				}
				if(column.isPrimaryKey()){
					continue;
				}
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			
			for (Column column : table.getPks()) {
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			
			pstmt.executeUpdate();

			if (isShowSql) {
				log.info("time:" + (System.currentTimeMillis() - l) + "ms");
			}
			return value;
		} catch (SQLException e) {
			throw new CommondbException(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
	
	public static Object delete(String tableId, DataObject value) throws CommondbException{
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		
		if (value == null)
			throw new CommondbException("没有指定要保存的记录");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Table table =  SessionFactory.getTable(tableId);
		ISession session= SessionFactory.getSession(table.getDb());
		try {
			conn = session.getConn();
			String sql = table.getDeleteByKeySql();
			long l = System.currentTimeMillis();
			if (isShowSql) {
				log.info(sql);
				
			}
			pstmt = conn.prepareStatement(sql);
			int index = 0;
			for (Column column : table.getPks()) {
				index++;
				SqlUtil.setValue(pstmt, index, column, value, true,session);
			}
			pstmt.executeUpdate();

			if (isShowSql) {
				log.info("time:" + (System.currentTimeMillis() - l) + "ms");
			}
			return value;
		} catch (SQLException e) {
			throw new CommondbException(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
	}
	
	
}
