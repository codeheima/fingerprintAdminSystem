package org.ma.app.service.impl;

import ma.org.proxy.ano.declare.Service;

import org.ma.app.service.CustomerService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.db.jdbc.dao.BaseOptDao;
@Service(name = "customerService" )
public class CustomerServiceImpl implements CustomerService {

	public void insert(DataObject data) {
		BaseOptDao.insert("zfdb.customer", data);
	}
	
	public Pagination query(DataObject params,int currentPage,int pageSize){
		return BaseOptDao.executeQuery("platformdb.customer.query", params, currentPage, pageSize);
	}

}
