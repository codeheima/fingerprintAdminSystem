package org.ma.db.jdbc.test;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.bean.Pagination;
import org.ma.db.jdbc.dao.BaseOptDao;

public class TestPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDBOpt.opt(new TestDBOpt.IOpt() {
			public void opt() {
				testOne();
			}
		});
	}
	
	private static void testOne() {
		
		DataObject param = new DataObject();
		
		param.setValue("appType", "1030036");
		
		Pagination p = BaseOptDao.executeQuery("platformdb.chat.one",param,1,3 );
		
		for(DataObject d : p.getDatas()){
		
			System.out.println(d);
		
		}
		
	}

}
