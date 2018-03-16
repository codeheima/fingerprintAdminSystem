package org.ma.app.service;

import java.util.List;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;

public interface CommonService {

	Pagination queryPage(String scriptId,DataObject params,int currentPage,int pageSize);
	
	void execute(String dbName,String sql);
	
	List<DataObject> executeQuery(String dbName,String sql);
	
	void insert(String tableId,DataObject data);

	DataObject update(String tableId, DataObject params);
	
	DataObject updateWithNotNull(String tableId, DataObject value);
	
	void delete(String tableId, DataObject value);
}
