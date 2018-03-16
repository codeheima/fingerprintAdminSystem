package org.ma.app.service;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;

public interface CustomerService {

	void insert(DataObject data);
	
	Pagination query(DataObject params,int currentPage,int pageSize);
	

	
}
