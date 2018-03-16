package org.ma.db.jdbc.bean;

import java.io.Serializable;
import org.ma.db.jdbc.util.MadbUtil;


public class Column implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = null;

	private String propertyName = null;

	private boolean nullAble = true;

	private int length = 0;

	private int scale = 0;

	private int dataType = 0;

	private boolean primaryKey = false;

	private String defaultValue = null;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.propertyName = MadbUtil.convertToPropertyName(name);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", propertyName=" + propertyName
				+ ", nullAble=" + nullAble + ", length=" + length + ", scale="
				+ scale + ", dataType=" + dataType + ", primaryKey="
				+ primaryKey + ", defaultValue=" + defaultValue + "]";
	}
	

}
