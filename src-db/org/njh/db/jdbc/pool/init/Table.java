package org.ma.db.jdbc.pool.init;

import java.util.List;
import java.util.Map;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Column;
import org.ma.db.jdbc.dialect.Dialect;
import org.ma.db.jdbc.util.MadbUtil;


public class Table {
	
	
	private String db;
	
	private String id;
	
	private String name;
	
	private String propertyName;
	
	private List<Column> cols;
	
	private List<Column> pks;
	
	/** key = col.propertyName */
	private Map<String,Column> colMap;   

	private String insertSql = null;

	private String updateSql = null;

	private String deleteByKeySql = null;

	private String findByKeySql = null;

	private String findAllSql = null;

	private String createSql = null;

	private Dialect dialect = null;
	
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}

	public List<Column> getPks() {
		return pks;
	}

	public void setPks(List<Column> pks) {
		this.pks = pks;
	}

	public Map<String, Column> getColMap() {
		return colMap;
	}

	public void setColMap(Map<String, Column> colMap) {
		this.colMap = colMap;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString() {
		return "Table [db=" + db + ", id=" + id + ", name=" + name
				+ ", propertyName=" + propertyName + ", cols=" + cols + "]";
	}
	
	
	
	/**
	 * 得到入库语句
	 * 
	 * @return
	 */
	public String getInsertSql() {
		if (insertSql == null)
			insertSql = this.getInsertSQL();
		return insertSql;
	}

	/**
	 * 得到更新语句
	 * 
	 * @return
	 */
	public String getUpdateSql() {
		if (updateSql == null)
			updateSql = this.getUpdateSQL();
		return updateSql;
	}
	
	
	public String getUpdateSqlWithNotNull(DataObject data) {
		StringBuffer set = new StringBuffer();
		StringBuffer where = new StringBuffer();
		for (Column column : this.pks) {
			
			if (where.length() == 0) {
				where.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			} else {
				where.append(" AND ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			}
		}

		for (Column column : this.cols) {
			Object val = data.getObject(column.getPropertyName());
			if(val == null ||"".equals(val)){
				continue;
			}
			if (!column.isPrimaryKey()) {
				if (set.length() == 0) {
					set.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
				} else {
					set.append(" , ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
				}
			}
		}
		return new StringBuffer().append("UPDATE ").append(getName()).append(" SET ").append(set).append(" WHERE ").append(where).toString();

	}

	/**
	 * 得到删除语句
	 * 
	 * @return
	 */
	public String getDeleteByKeySql() {
		if (deleteByKeySql == null)
			deleteByKeySql = this.getDeleteByKeySQL();
		return deleteByKeySql;
	}

	/**
	 * 得到查找语句
	 * 
	 * @return
	 */
	public String getFindByKeySql() {
		if (findByKeySql == null)
			findByKeySql = this.getFindByKeySQL();
		return findByKeySql;
	}

	/**
	 * 得到查找语句
	 * 
	 * @return
	 */
	public String getFindAllSql() {
		if (findAllSql == null)
			findAllSql = this.getFindAllSQL();
		return findAllSql;
	}

	/**
	 * 得到建表语句
	 * 
	 * @return
	 */
	public String getCreateSql() {
		if (createSql == null)
			createSql = this.getCreateSQL();
		return createSql;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	
	public Dialect getDialect() {
		return dialect;
	}

	/**
	 * 得到入库SQL
	 * 
	 * @param table
	 * @return
	 */
	private String getInsertSQL() {
		StringBuffer sql = new StringBuffer();
		StringBuffer cs = new StringBuffer();
		StringBuffer vs = new StringBuffer();
		for (Column column : cols) {
	//		if (!column.isPrimaryKey() || (column.isPrimaryKey() && getKeyGenerate() != Table.IDENTITY)) {
				cs.append(",").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc());
				vs.append(",?");
//			}
		}
		return sql.append("INSERT INTO ").append(getName()).append("(").append(cs.toString().substring(1)).append(") ").append("VALUES(").append(vs.toString().substring(1)).append(")").toString();
	}

	/**
	 * 得到更新SQL
	 * 
	 * @param table
	 * @return
	 */
	private String getUpdateSQL() {
		StringBuffer set = new StringBuffer();
		StringBuffer where = new StringBuffer();
		for (Column column : this.pks) {
			if (where.length() == 0) {
				where.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			} else {
				where.append(" AND ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			}
		}

		for (Column column : this.cols) {
			if (!column.isPrimaryKey()) {
				if (set.length() == 0) {
					set.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
				} else {
					set.append(" , ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
				}
			}
		}
		return new StringBuffer().append("UPDATE ").append(getName()).append(" SET ").append(set).append(" WHERE ").append(where).toString();
	}

	/**
	 * 得到删除SQL
	 * 
	 * @param table
	 * @return
	 */
	private String getDeleteByKeySQL() {
		StringBuffer where = new StringBuffer();
		for (Column column : this.pks) {
			if (where.length() == 0) {
				where.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			} else {
				where.append(" AND ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			}
		}
		return new StringBuffer().append("DELETE FROM ").append(getName()).append(" WHERE ").append(where).toString();
	}

	/**
	 * 得到查询SQL
	 * 
	 * @param table
	 * @return
	 */
	private String getFindByKeySQL() {
		StringBuffer where = new StringBuffer();
		for (Column column : this.pks) {
			if (where.length() == 0) {
				where.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			} else {
				where.append(" AND ").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" = ? ");
			}
		}
		return new StringBuffer().append("SELECT * FROM ").append(getName()).append(" WHERE ").append(where).toString();
	}

	/**
	 * 得到查询SQL
	 * 
	 * @param table
	 * @return
	 */
	private String getFindAllSQL() {
		return new StringBuffer().append("SELECT * FROM ").append(getName()).toString();
	}

	/**
	 * 根据表结构得到建表语句
	 * 
	 * @return
	 */
	private String getCreateSQL() {
		StringBuffer sql = new StringBuffer();
		StringBuffer ks = new StringBuffer();
		StringBuffer cs = new StringBuffer();

		for (Column column : this.pks) {
			if (ks.length() == 0) {
				ks.append(",\nCONSTRAINT PK_").append(getName()).append(" PRIMARY KEY(").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc());
			} else {
				ks.append(",").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc());
			}
		}
		if (ks.length() > 0)
			ks.append(")");

		for (Column column : cols) {
			int length = column.getLength();
			String typeName = getDialect().getDataType(column.getDataType(), length, column.getScale());
			if (cs.length() == 0) {
				cs.append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" ").append(typeName);
			} else {
				cs.append(",\n").append(getDialect().getPrefixEsc()).append(column.getName()).append(getDialect().getSuffixEsc()).append(" ").append(typeName);
			}

			if (column.isNullAble()) {
				cs.append(" NULL");
			} else {
				cs.append(" NOT NULL");
			}
		}
		return sql.append("CREATE TABLE ").append(getName()).append("(").append(cs.toString()).append(ks.toString()).append(") ").toString();
	}

	
}
