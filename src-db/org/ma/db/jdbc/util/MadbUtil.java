package org.ma.db.jdbc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.dialect.DataObjectWrapper;
import org.ma.db.jdbc.dialect.Dialect;

import freemarker.template.TemplateException;

public class MadbUtil {

	/**
	 * 将结果集封装为List对象
	 * 
	 * @param rs
	 * @return
	 */
	public static List<DataObject> parseResultSet(ResultSet rs) {
		try {
			List<DataObject> dataList = null;
			List<Column> columns = MadbUtil.getResultColumns(rs);
			while (rs.next()) {
				if (dataList == null)
					dataList = new ArrayList<DataObject>();
				dataList.add(parseResult(rs, columns));
			}

			return dataList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析当前记录为对象
	 * 
	 * @param rs
	 * @return
	 */
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

	/**
	 * 将某字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	private static String upperFirstCase(String str) {
		return (str == null || str.equals("")) ? "" : str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 处理数据库列名为标准命名格式
	 * 
	 * @param name
	 * @return
	 */
	public static String convertToPropertyName(String name) {
		name = name.toLowerCase();
		String[] str = name.split("\\_");
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				name = str[i];
			} else {
				name = name + upperFirstCase(str[i]);
			}
		}
		return name;
	}

	public static List<Column> getResultColumns(ResultSet rs) {
		try {
			List<Column> list = new ArrayList<Column>();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				Column column = new Column();
				column.setName(rsmd.getColumnLabel(i));
				column.setDataType(rsmd.getColumnType(i));
				list.add(column);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析日期字符串为日期对象
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate(String str) {
		String[] parsePatterns = new String[] { "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss.SSS", "MM/dd/yyyy HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss",
				"yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm", "MM/dd/yyyy HH:mm", "yyyy-MM-dd HH", "yyyy/MM/dd HH", "MM/dd/yyyy HH", "yyyy-MM-dd", "yyyy/MM/dd", "MM/dd/yyyy" };

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		return null;
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
