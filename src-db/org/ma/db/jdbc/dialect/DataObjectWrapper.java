package org.ma.db.jdbc.dialect;



import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.ma.db.jdbc.util.MadbUtil;


/**
 * 当数据传递给freemark用于解析前，先对数据做个包装，以方便某些属性的获取
 * 
 * @author ryan
 * 
 */
public class DataObjectWrapper {

	private Object object = null;

	private Dialect dialect = null;

	public DataObjectWrapper(Object object, Dialect dialect) {
		this.object = object;
		this.dialect = dialect;
	}

	/**
	 * 判断值对象是否为数组
	 * 
	 * @return
	 */
	public boolean isArray() {
		if (object == null)
			return false;
		else
			return object.getClass().isArray();
	}

	/**
	 * 判断值对象是否不为数组
	 * 
	 * @return
	 */
	public boolean isNotArray() {
		if (object == null)
			return true;
		else
			return !object.getClass().isArray();
	}

	/**
	 * 得到长度
	 * 
	 * @return
	 */
	public int length() {
		return stringValue().length();
	}

	/**
	 * 得到数组大小
	 * 
	 * @return
	 */
	public int size() {
		if (isArray()) {
			return Array.getLength(object);
		} else {
			return 1;
		}
	}

	/**
	 * 得到最小值
	 * 
	 * @return
	 */
	public String toLowerCase() {
		return stringValue().toLowerCase();
	}

	/**
	 * 得到最大值
	 * 
	 * @return
	 */
	public String toUpperCase() {
		return stringValue().toUpperCase();
	}

	/**
	 * 去左右空格
	 * 
	 * @return
	 */
	public String trim() {
		return stringValue().trim();
	}

	/**
	 * 截取字符串
	 * 
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public String substring(int beginIndex, int endIndex) {
		return stringValue().substring(beginIndex, endIndex);
	}

	/**
	 * 得到开始日期
	 * 
	 * @return
	 */
	public String beginDate() {
		return dialect.dateValue(toDate("yyyy-MM-dd HH:mm:ss", "1900-01-01 00:00:00"));
	}

	/**
	 * 得到开始日期
	 * 
	 * @param format 格式
	 * @return
	 */
	public String beginDate(String format) {
		return dialect.dateValue(toDate(format, "1900-01-01 00:00:00"));
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public String endDate() {
		return dialect.dateValue(toDate("yyyy-MM-dd HH:mm:ss", "9999-12-31 23:59:59"));
	}

	/**
	 * 得到当前日期
	 * 
	 * @param format 格式
	 * @return
	 */
	public String endDate(String format) {
		return dialect.dateValue(toDate(format, "9999-12-31 23:59:59"));
	}

	/**
	 * 得到日期
	 * 
	 * @return
	 */
	public String date() {
		return dialect.dateValue(toDate("yyyy-MM-dd HH:mm:ss", ""));
	}

	/**
	 * 得到日期
	 * 
	 * @param format 格式
	 * @return
	 */
	public String date(String format) {
		return dialect.dateValue(toDate(format, ""));
	}

	/**
	 * 得到日期值
	 * 
	 * @param format 格式化
	 * @param def 默认值
	 * @return
	 */
	public String date(String format, String def) {
		return dialect.dateValue(toDate(format, def));
	}

	/**
	 * 得到日期值
	 * 
	 * @param format 格式化
	 * @param def 默认值
	 * @return
	 */
	private String toDate(String format, String def) {
		String value = null;
		if (object instanceof Date) {
			value = new SimpleDateFormat(format).format((Date) object);
		} else {
			value = stringValue();
			Date dataValue = MadbUtil.parseDate(value);
			if (dataValue != null) {
				value = new SimpleDateFormat(format).format(dataValue);
			}
		}
		if (value == null || value.equals("")) {
			value = def;
		}
		return value;
	}

	/**
	 * 判断是否为空对象
	 * 
	 * @return
	 */
	public boolean isNull() {
		if (object == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 如果为空对象则返回默认值
	 * 
	 * @param def
	 * @return
	 */
	public String isNull(String def) {
		if (isNull()) {
			return def;
		} else {
			return stringValue();
		}
	}

	/**
	 * 判断是否为空对象
	 * 
	 * @return
	 */
	public boolean isNotNull() {
		if (object == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (stringValue().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 如果为空字符串则返回默认值
	 * 
	 * @param def
	 * @return
	 */
	public String isEmpty(String def) {
		if (isEmpty()) {
			return def;
		} else {
			return stringValue();
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @return
	 */
	public boolean isNotEmpty() {
		if (stringValue().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否相等
	 * 
	 * @param str
	 * @return
	 */
	public boolean equals(String str) {
		if (stringValue().equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否相等
	 * 
	 * @param str
	 * @return
	 */
	public boolean notEquals(String str) {
		if (stringValue().equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否以指定字符串开头
	 * 
	 * @param str
	 * @return
	 */
	public boolean startsWith(String str) {
		if (stringValue().startsWith(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否以指定字符串结尾
	 * 
	 * @param str
	 * @return
	 */
	public boolean endsWith(String str) {
		if (stringValue().endsWith(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否包含指定字符串
	 * 
	 * @param str
	 * @return
	 */
	public int indexOf(String str) {
		return stringValue().indexOf(str);
	}

	/**
	 * 判断是否是一个合法IP
	 * 
	 * @return
	 */
	public boolean isIp() {
		String ip = stringValue();
		return Pattern.matches("\\d{1,3}(\\.\\d{1,3}){3}", ip);
	}

	/**
	 * 为IP补零
	 * 
	 * @return
	 */
	public String ip() {
		return ip("");
	}

	/**
	 * 为IP补零，如果IP为空则返回默认值
	 * 
	 * @param def
	 * @return
	 */
	public String ip(String def) {
		String ip = stringValue();
		if (ip != null && !ip.equals("")) {
			if (isIp()) {
				String newip = "";
				String value[] = ip.split("\\.");
				for (int i = 0; i < value.length; i++) {
					newip = newip + fillStr(value[i], 3, "0") + ".";
				}
				return newip.substring(0, newip.length() - 1);
			} else {
				// 如果不是合法IP则直接返回原值
				return ip;
			}
		} else
			return def;
	}

	/**
	 * 得到16进制IP格式
	 * 
	 * @return
	 */
	public String ipHex() {
		String ip = stringValue();
		if (isIp()) {
			String hex = "";
			String value[] = ip.split("\\.");
			for (int i = 0; i < value.length; i++) {
				int d = Integer.parseInt(value[i]);
				String hexstr = Integer.toHexString(d);
				if (hexstr.length() == 1)
					hexstr = "0" + hexstr;
				hex = hex + hexstr;
			}
			return hex.toUpperCase();
		} else
			return ip;
	}

	/**
	 * IP转换为长整数形式
	 * 
	 * @return
	 */
	public String ipLong() {
		if (isIp()) {
			String hex = ipHex();
			return String.valueOf(Long.parseLong(hex, 16));
		} else
			return "";
	}

	/**
	 * 得到SQL语句中的in值方式，如“('1','2','3')”
	 * 
	 * @return
	 */
	public String inValue() {
		if (object == null) {
			return "";
		} else if (object.getClass().isArray()) {
			StringBuffer data = new StringBuffer();
			int length = Array.getLength(object);
			for (int i = 0; i < length; i++) {
				Object value = Array.get(object, i);
				if (value == null || value instanceof Number) {
					if (data.length() == 0) {
						data.append("(").append(value == null ? "NULL" : value.toString());
					} else {
						data.append(",").append(value == null ? "NULL" : value.toString());
					}
				} else {
					if (data.length() == 0) {
						data.append("('").append(value.toString().replaceAll("'", "''")).append("'");
					} else {
						data.append(",'").append(value.toString().replaceAll("'", "''")).append("'");
					}
				}
			}
			if (data.length() > 0) {
				return data.append(")").toString();
			} else {
				return "";
			}
		} else {
			if (object instanceof Number) {
				return new StringBuffer().append("(").append(object.toString()).append(")").toString();
			} else {
				return new StringBuffer().append("('").append(toString()).append("')").toString();
			}
		}
	}

	/**
	 * 得到SQL语句中的or值方式，如“(A=1 OR A=2 OR A=3)”
	 * 
	 * @param columnName 列名
	 * @return
	 */
	public String orValue(String columnName) {
		if (object == null) {
			return new StringBuffer().append(columnName).append(" IS NULL ").toString();
		} else if (object.getClass().isArray()) {
			StringBuffer data = new StringBuffer();
			int length = Array.getLength(object);
			for (int i = 0; i < length; i++) {
				Object value = Array.get(object, i);
				if (data.length() == 0) {
					data.append(" (").append(columnName);
				} else {
					data.append(" OR ").append(columnName);
				}

				if (value == null || value instanceof Number) {
					if (value == null) {
						data.append(" IS NULL ");
					} else {
						data.append("=").append(value.toString());
					}
				} else {
					data.append("=").append("'").append(value.toString()).append("'");
				}
			}
			return data.length() > 0 ? data.append(")").toString() : "";
		} else {
			return (object instanceof Number) ? new StringBuffer().append(columnName).append("=").append(object.toString()).toString() : new StringBuffer().append(columnName).append("=").append("'")
					.append(toString()).append("'").toString();
		}
	}

	/**
	 * 补零
	 * 
	 * @param str
	 * @param length
	 * @param filestr
	 * @return
	 */
	private String fillStr(String str, int length, String filestr) {
		String newstr = str;
		if (str.length() < length) {
			for (int i = 0; i < length - str.length(); i++) {
				newstr = filestr + newstr;
			}
		}
		return newstr;
	}

	/**
	 * 取字符串值
	 */
	public String stringValue() {
		if (object == null) {
			return "";
		} else if (object.getClass().isArray()) {
			// 如果是数组，则取数组第一个值
			if (Array.getLength(object) > 0) {
				Object obj = Array.get(object, 0);
				return obj != null ? obj.toString() : "";
			} else {
				return "";
			}
		} else {
			return object.toString();
		}
	}

	public String toString() {
		String str = stringValue();
		return str == null ? "" : str.replaceAll("'", "''");
	}

	public Object getObject() {
		return object;
	}

}
