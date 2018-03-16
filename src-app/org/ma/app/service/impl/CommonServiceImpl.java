package org.ma.app.service.impl;

import ma.org.proxy.ano.declare.Service;

import java.util.List;

import org.ma.app.service.CommonService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.db.jdbc.dao.BaseOptDao;

@Service(name = "commonService" )
public class CommonServiceImpl implements CommonService {



	public Pagination queryPage(String scriptId, DataObject params,
			int currentPage, int pageSize) {
		return BaseOptDao.executeQuery(scriptId, params, currentPage, pageSize);
	}


	public void insert(String tableId, DataObject data) {
		BaseOptDao.insert(tableId, data);
	}
	
	public DataObject update(String tableId, DataObject params) {
		return BaseOptDao.update(tableId, params);
	}

	public DataObject updateWithNotNull(String tableId, DataObject value) {
		return BaseOptDao.updateWithNotNull(tableId, value);
	}
	public void delete(String tableId, DataObject value) {
		 BaseOptDao.delete(tableId, value);
	}


	public void execute(String dbName, String sql) {
		BaseOptDao.executeSql(SessionFactory.getSession(dbName), sql);
	}


	public List<DataObject> executeQuery(String dbName, String sql) {
		return BaseOptDao.executeQuerySql(SessionFactory.getSession(dbName), sql);
		
	}
	

}
