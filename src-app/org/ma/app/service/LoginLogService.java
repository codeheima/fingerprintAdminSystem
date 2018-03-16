package org.ma.app.service;

import java.util.List;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;

public interface LoginLogService {
	List<DataObject> refreshMemory();
	String login(DataObject param);
	
	String logout(DataObject param);
	
}
