package org.ma.db.jdbc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.bean.ModelBean;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.db.jdbc.dialect.DataObjectWrapper;
import org.ma.db.jdbc.dialect.Dialect;
import org.ma.db.jdbc.exception.CommondbException;
import org.ma.db.jdbc.pool.init.DBPoolInitialization;
import org.ma.util.ComplexUtil;

import freemarker.template.TemplateException;


public class ExecuteUtil {
	
	private static final Log log = LogFactory.getLog(ExecuteUtil.class);
	
	private static final ThreadLocal<Map<String,Statement>> statLocal = new ThreadLocal<Map<String,Statement>>();
	
	public static void clearLocal(){
	
		statLocal.set(null);
	
	}
	
	public static void executeSql(ISession session,String sql){
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		if(isShowSql){
			log.info(sql);
		}
		
		Statement stat = getStatment(session);
		try {
			stat.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void execute(String script,DataObject params){
		
		
		try {
			ModelBean bean = new ModelBean(script);
			
			ISession session = SessionFactory.getSession(bean.getDbName());
			Statement stat = getStatment(session);	
			
			String sql = compileScript(script,params,session.getPool().getDialect(),bean);
			
			boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
			if(isShowSql){
				log.info(sql);
			}
			stat.execute(sql);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<DataObject> executeQuery(String script,DataObject params){
		
		List<DataObject> list = new ArrayList<DataObject>();
		try {
			ModelBean bean = new ModelBean(script);
			
			
			ISession session = SessionFactory.getSession(bean.getDbName());
			
			
			String sql = compileScript(script,params,session.getPool().getDialect(),bean);
			
			Statement stat = getStatment(session);	
			boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
			if(isShowSql){
				log.info(sql);
			}
			ResultSet rs = stat.executeQuery(sql);
		
			list = createList(rs);
		
		} catch (SQLException e) {
		
			throw new RuntimeException(e);
		
		}
		
		return list;
	}
	
	public static List<DataObject> executeQueryBySql(ISession session,String sql){
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		if(isShowSql){
			log.info(sql);
		}
		Statement stat = getStatment(session);
		List<DataObject> list = new ArrayList<DataObject>();
		try {
			ResultSet rs = stat.executeQuery(sql);
			list = createList(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
		
	}
	

	private static List<DataObject> createList(ResultSet rs) throws SQLException {
		List<DataObject> list = new ArrayList<DataObject>();
		
		List<Column> colList = new ArrayList<Column>();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			
			Column column = new Column();
			column.setName(rsmd.getColumnLabel(i));
			column.setDataType(rsmd.getColumnType(i));
			colList.add(column);
			
		}
		
		while (rs.next()) {
			list.add(parseResult(rs, colList));
		}
		
		rs.close();
		
		return list;
	}

	private static Statement getStatment(ISession session) {
		Map<String,Statement> map = statLocal.get();
		if(map == null){
			map = ComplexUtil.map();
			statLocal.set(map);
		}
		Statement stat = map.get(session.getId());
		if(stat == null){
			try {
				stat = session.getConn().createStatement();
				map.put(session.getId(), stat);
				
				session.addClears(stat);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return stat;
	}
	/**
	 * 利用freemark编译sql
	 * 
	 * @param script
	 * @param root
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String compileScript(String script, Map<String, Object> values, Dialect dialect,ModelBean bean) {
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			if (values != null) {
				Iterator<String> it = values.keySet().iterator();
				for (;it.hasNext();) {
					String key = it.next();
					Object obj = values.get(key);
					root.put(key, new DataObjectWrapper(obj, dialect));
				}
			}
			return bean.getModel().simpleCompile(root);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public static DataObject parseResult(ResultSet rs, List<Column> columns) {
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			for (int i = 1; i <= columns.size(); i++) {
				int type = columns.get(i - 1).getDataType();
				String name = columns.get(i - 1).getPropertyName();
				if (type == Types.TIMESTAMP || type == Types.DATE || type == Types.TIME) {
					values.put(name, rs.getTimestamp(i));
				} else if (type == Types.BLOB || type == Types.LONGVARBINARY || type == Types.BINARY) {
					Object objectValue = rs.getObject(i);
					if (objectValue instanceof Blob) {
						Blob data = (Blob) objectValue;
						InputStream is = null;
						ByteArrayOutputStream bao = null;
						try {
							bao = new ByteArrayOutputStream();
							is = data.getBinaryStream();
							byte[] b = new byte[1024];
							int index;
							while ((index = is.read(b)) != -1) {
								bao.write(b, 0, index);
							}
							values.put(name, bao.toByteArray());
						} finally {
							if (is != null)
								try {
									is.close();
								} catch (IOException e) {
								}
							if (bao != null)
								try {
									bao.close();
								} catch (IOException e) {
								}
						}

					} else if (objectValue instanceof byte[]) {
						values.put(name, objectValue);
					} else {
						values.put(name, objectValue);
					}
				} else if (type == Types.CLOB) {
					Object objectValue = rs.getObject(i);
					if (objectValue instanceof Clob) {
						Clob data = (Clob) objectValue;
						Reader reader = null;
						if (data != null) {
							try {
								String str = "";
								char ac[] = new char[1024];
								reader = data.getCharacterStream();
								int index;
								while ((index = reader.read(ac)) != -1) {
									str = str + new String(ac, 0, index);
								}
								values.put(name, str);
							} finally {
								if (reader != null)
									try {
										reader.close();
									} catch (IOException e) {
									}
							}
						}
					} else {
						if (objectValue != null) {
							values.put(name, objectValue.toString());
						}
					}
				} else if (type == Types.INTEGER || type == Types.TINYINT) {
					values.put(name, new Integer(rs.getInt(i)));
				} else if (type == Types.NUMERIC || type == Types.DECIMAL) {
					values.put(name, rs.getBigDecimal(i));
				} else if (type == Types.FLOAT) {
					values.put(name, rs.getFloat(i));
				} else if (type == Types.DOUBLE) {
					values.put(name, rs.getDouble(i));
				} else if (type == Types.BIGINT) {
					values.put(name, rs.getLong(i));
				} else {
					String value = rs.getString(i);
					if (value == null) {
						values.put(name, value);
					} else {
						values.put(name, value.trim());
					}
				}
			}
			return new DataObject(values);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public static Pagination executeQuery(String scriptId, DataObject value, int currentPage, int pageSize) {
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		Statement stat = null;
		ResultSet rs = null;
		try {
			ModelBean bean = new ModelBean(scriptId);
			
			ISession session = SessionFactory.getSession(bean.getDbName());
			stat =  getStatment(session);	
			
			String sql = compileScript(scriptId,value,session.getPool().getDialect(),bean);
			

			String countSql = "SELECT COUNT(1) FROM (" + sql + ") T";

			// 先计算总记录数
			if (isShowSql) {
				log.info(countSql);
			}
			long l = System.currentTimeMillis();
			
			rs = stat.executeQuery(countSql);
			
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			rs.close();

			if (pageSize <= 0) {
				// 认为用户不需要分页，返回所有记录
				List<DataObject> result = executeQuery(scriptId, value);
				return new Pagination(result, totalCount, currentPage, pageSize);
			} else {
				// 查询出指定记录
				List<DataObject>  resultList = ComplexUtil.list();
				if (totalCount > 0) {
					if (currentPage < 1) {
						currentPage = 1;
					}

					int min = (currentPage - 1) * pageSize + 1;
					int max = currentPage * pageSize;

					if (totalCount < min) {
						// 如果总记录数小于当前用户试图查看的最小记录行，则认为数据发生改变，直接给用户看第一页
						currentPage = 1;
						min = 1;
						max = pageSize;
					}
					// 得到用于分页查询的SQL语句
					String limitSql = session.getPool().getDialect().getLimitQueryScript(sql, min, max);
					
					if (isShowSql) {
						log.info(limitSql);
					}
					rs = stat.executeQuery(limitSql);
					resultList = createList(rs);
					
				}
				if (isShowSql) {
					log.info("time:" + (System.currentTimeMillis() - l) + "ms");
				}
				// 返回
				return new Pagination(resultList, totalCount, currentPage, pageSize);
			}
		} catch (SQLException e) {
		
			throw new CommondbException(e);
		
		} finally {
			// 将rs和stmt添加到后续清除列表中
		
		}
	}
}
