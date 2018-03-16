package org.ma.db.jdbc.util;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.pool.init.DBPoolInitialization;
import org.ma.db.jdbc.pool.init.Table;


public class SqlUtil {

	

	
	/**
	 * 为SQL参数赋值
	 * 
	 * @param pstmt
	 * @param index
	 * @param dataType
	 * @param name
	 * @param value
	 * @param nullable 是否允许赋空值，如果不允许则当值为空时不赋值（忽略该参数）
	 * @param session 
	 * @throws SQLException
	 */
	public static boolean setValue(PreparedStatement pstmt, int index, Column column, DataObject value, boolean nullable, ISession session) throws SQLException {
		boolean isShowSql= DBPoolInitialization.getConfigBean().isShowsql();
		int dataType = column.getDataType();
		String name = column.getPropertyName();
		if (!nullable && (value.getObject(name) == null || value.getString(name).trim().length() == 0)) {
			return false;
		}
		if (dataType == Types.TIMESTAMP || dataType == Types.DATE) {
			pstmt.setTimestamp(index, value.getTimestamp(name));
		} else if (dataType == Types.BIGINT) {
			pstmt.setLong(index, value.getLong(name));
		} else if (dataType == Types.INTEGER || dataType == Types.SMALLINT || dataType == Types.TINYINT || dataType == Types.BIT) {
			pstmt.setInt(index, value.getInt(name));
		} else if (dataType == Types.DECIMAL || dataType == Types.NUMERIC || dataType == Types.DOUBLE || dataType == Types.FLOAT) {
			pstmt.setBigDecimal(index, value.getBigDecimal(name));
		} else if (dataType == Types.BINARY || dataType == Types.BLOB || dataType == Types.VARBINARY || dataType == Types.JAVA_OBJECT || dataType == Types.LONGVARBINARY) {
			pstmt.setBytes(index, value.getBytes(name));
		} else if (dataType == Types.LONGVARCHAR || dataType == Types.CLOB) {
			String tmpValue = value.getString(name);
			if (tmpValue != null && tmpValue.equals(""))
				tmpValue = null;
			pstmt.setString(index, tmpValue);
		} else {
			String tmpValue = value.getString(name);
			if (tmpValue != null && tmpValue.equals(""))
				tmpValue = null;
			// 如果允许数据截断，则当用户填入值超过限定长度时截断数据
			try {
				if (tmpValue != null 
						&& tmpValue.getBytes(session.getPool().getConfig().getEncode()).length > column.getLength()) {
					tmpValue = getLimitLength(tmpValue, column.getLength(), session.getPool().getConfig().getMaxUnitlength());
				}
			} catch (UnsupportedEncodingException e) {
			}
			pstmt.setString(index, tmpValue);
		}
		return true;
	}

	
	/**
	 * 得到限长字符串
	 * 
	 * @param data
	 *            原字符串
	 * @param length
	 *            字段长度
	 * @param ulength
	 *            单位字符长度
	 * @return 满足最大长度的字符串
	 */
	public static String getLimitLength(String data, int length, int ulength) {
		if (data == null || data.length() == 0)
			return data;
		else {
			int currentLength = 0;
			int clength = 0;
			StringBuilder newdata = new StringBuilder();
			for (int i = 0; i < data.length(); i++) {
				char c = data.charAt(i);
				if (c >= 0x0 && c <= 0x7F) {
					clength = 1;
				} else {
					clength = ulength;
				}

				if (currentLength + clength < length) {
					currentLength = currentLength + clength;
					newdata.append(c);
				} else if (currentLength + clength == length) {
					currentLength = currentLength + clength;
					newdata.append(c);
					break;
				} else {
					break;
				}
			}
			return newdata.toString();
		}
	}
}
