package org.ma.db.jdbc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 值对象
 * 
 * @author ryan
 * 
 */
public class DataObject extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	/**
	 * 得到对象值
	 * 
	 * @param id
	 * @return
	 */
	public Object getObject(String id) {
		return this.get(id);
	}

	/**
	 * 得到日期值
	 * 
	 * @param id
	 * @return
	 */
	public Timestamp getTimestamp(String id) {
		Date date = getDate(id);
		return date != null?new Timestamp(date.getTime()):null;
	}

	/**
	 * 赋值
	 * 
	 * @param id
	 * @param value
	 */
	public DataObject setValue(String id, Object value) {
		this.put(id, value);
		return this;
	}

	/**
	 * 得到字符串值
	 * 
	 * @param id
	 * @return
	 */
	public String getString(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof String) {
				return (String) objValue;
			}
			if (objValue instanceof Object[]) {
				Object obj = ((Object[]) objValue)[0];
				return obj != null?obj.toString():null;
			} else {
				return objValue.toString();
			}
		}
		return null;
	}

	/**
	 * 得到日期值
	 * 
	 * @param id
	 * @return
	 */
	public Date getDate(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Date) {
				return (Date) objValue;
			}
			if (objValue instanceof Date[]) {
				return ((Date[]) objValue)[0];
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:parseDate(strValue);
			}
		}
		return null;
	}


	/**
	 * 按指定的格式得到日期字符串
	 * 
	 * @param id
	 * @param format
	 * @return
	 */
	public String getDateString(String id, String format) {
		Date date = getDate(id);
		return date != null?new SimpleDateFormat(format).format(date):null;
	}

	/**
	 * 得到整数值
	 * 
	 * @param id
	 * @return
	 */
	public int getInt(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return number.intValue();
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return number.intValue();
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?0:Integer.valueOf(strValue);
			}
		}
		return 0;
	}
	/**
	 * 得到双精度值
	 * 
	 * @param id
	 * @return
	 */
	public Double getDouble(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Double) {
				return (Double) objValue;
			} else if (objValue instanceof Double[]) {
				return ((Double[]) objValue)[0];
			} else if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new Double(number.doubleValue());
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return new Double(number.doubleValue());
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:Double.valueOf(strValue);
			}
		}
		return null;
	}

	/**
	 * 得到数字对象
	 * 
	 * @param id
	 * @return
	 */
	public BigDecimal getBigDecimal(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof BigDecimal) {
				return (BigDecimal) objValue;
			} else if (objValue instanceof BigDecimal[]) {
				return ((BigDecimal[]) objValue)[0];
			} else if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new BigDecimal(number.doubleValue());
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return new BigDecimal(number.doubleValue());
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:new BigDecimal(strValue);
			}
		}
		return null;
	}

	/**
	 * 得到整数值
	 * 
	 * @param id
	 * @return
	 */
	public Integer getInteger(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Integer) {
				return (Integer) objValue;
			} else if (objValue instanceof Integer[]) {
				return ((Integer[]) objValue)[0];
			} else if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new Integer(number.intValue());
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return new Integer(number.intValue());
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:Integer.valueOf(strValue);
			}
		}
		return null;
	}

	/**
	 * 得到长整数值
	 * 
	 * @param id
	 * @return
	 */
	public Long getLong(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Long) {
				return (Long) objValue;
			} else if (objValue instanceof Long[]) {
				return ((Long[]) objValue)[0];
			} else if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new Long(number.longValue());
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return new Long(number.longValue());
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:Long.valueOf(strValue);
			}
		}
		return null;
	}

	/**
	 * 得到浮点值
	 * 
	 * @param id
	 * @return
	 */
	public Float getFloat(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Float) {
				return (Float) objValue;
			} else if (objValue instanceof Float[]) {
				return ((Float[]) objValue)[0];
			} else if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new Float(number.floatValue());
			} else if (objValue instanceof Number[]) {
				Number number = (((Number[]) objValue)[0]);
				return new Float(number.floatValue());
			} else {
				String strValue = getString(id);
				return (strValue == null || strValue.trim().equals(""))?null:Float.valueOf(strValue);
			}
		}
		return null;
	}



	/**
	 * 按指定的格式得到数字字符串
	 * 
	 * @param id
	 * @param format
	 * @return
	 */
	public String getNumberString(String id, String format) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof Number) {
				Number number = (Number) objValue;
				return new DecimalFormat(format).format(number);
			} else if (objValue instanceof Number[]) {
				Number number = ((Number[]) objValue)[0];
				return new DecimalFormat(format).format(number);
			} else {
				BigDecimal bigDecimal = getBigDecimal(id);
				return (bigDecimal == null)?null:new DecimalFormat(format).format(bigDecimal);
			}
		}
		return null;
	}

	/**
	 * 得到一个二进制数组
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getBytes(String id) {
		Object objValue = getObject(id);
		if (objValue != null) {
			if (objValue instanceof byte[]) {
				return (byte[]) objValue;
			}
		}
		return null;
	}



	public DataObject() {

	}

	public DataObject(Map<String, Object> values) {
		this.putAll(values);
	}

	public DataObject(String id, String value) {
		this.put(id, value);
	}
	
	
	private static Date parseDate(String str) {
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

}
