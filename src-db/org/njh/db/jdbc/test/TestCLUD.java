package org.ma.db.jdbc.test;

import java.sql.SQLException;
import java.util.List;

import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.ISession;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.dao.BaseOptDao;
import org.ma.db.jdbc.util.ExecuteUtil;

public class TestCLUD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestDBOpt.opt(new TestDBOpt.IOpt() {
			public void opt() {
				
		//		testInsert();
				testInsertBlob();
		//		testUpdate();
				
		//		testDel();
				
				try {
			//		testResearch();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}
	
	private static void testResearch() throws SQLException {
		ISession session = SessionFactory.getSession("systemdb");
		
		List<DataObject> list = BaseOptDao.executeQuerySql(session, "select * from data_im_chat");
		for(DataObject d : list){
			System.out.println(d);
		}
	
		System.out.println("---------------------");
		
		DataObject param = new DataObject();
		param.setValue("appType", "1030036");
		list = BaseOptDao.executeQuery("platformdb.chat.one",param );
		
		for(DataObject d : list){
			System.out.println(d);
		}
		
	}

	private static void testDel() {
		DataObject val = new DataObject();
		
		val.setValue("cusId", "33");
		val.setValue("cusName", "name");

		BaseOptDao.delete("zfdb.customer", val);
	}

	private static void testUpdate() {
		DataObject val = new DataObject();
		
		val.setValue("cusId", "12");
		val.setValue("cusName", "n1");

		val.setValue("cusPd", "555");
		
		BaseOptDao.update("zfdb.customer", val);
		
		
		val = new DataObject();
		
		val.setValue("cusId", "000");
		val.setValue("cusName", "n1");

		val.setValue("cusPd", "555");
		
		BaseOptDao.updateWithNotNull("zfdb.customer", val);
		
	}

	private static void testInsert() {
		DataObject val = new DataObject();
		
		val.setValue("cusId", "000");
		val.setValue("cusName", "name");
		val.setValue("cusCode", "090921");
		val.setValue("cusSex", "01");
		val.setValue("cusPd", "pd");
		
		BaseOptDao.insert("zfdb.customer", val);
		
		val = new DataObject();
		
		val.setValue("cusId", "12");
		val.setValue("cusName", "name");
		val.setValue("cusCode", "090921");
		val.setValue("cusSex", "01");
		val.setValue("cusPd", "pd");
		
		BaseOptDao.insert("zfdb.customer", val);
		
		
		val = new DataObject();
		
		val.setValue("cusId", "33");
		val.setValue("cusName", "name");
		val.setValue("cusCode", "090921");
		val.setValue("cusSex", "01");
		val.setValue("cusPd", "pd");
		
		BaseOptDao.insert("zfdb.customer", val);
		
		
		for(int i = 0; i< 80; i++){
			val = new DataObject();
			int mark =55 + i;
			val.setValue("cusId",mark );
			val.setValue("cusName", "na" + mark);
			val.setValue("cusCode", "88" + mark);
			val.setValue("cusCompany", "Taiji");
			val.setValue("cusSex", "02");
			val.setValue("cusPd", "pppp" + mark);
			
			BaseOptDao.insert("zfdb.customer", val);
		}
	}

	
	public static void testInsertBlob() {
		DataObject val = new DataObject();
		
		val.setValue("cusId", "001");
		byte[] bs = new byte[] {2,12,33,127,-12,-33,88};
		val.setValue("zfArr", bs);
		
		System.out.println(val.getBytes("zfArr"));
		
		BaseOptDao.insert("zfdb.customerZf", val);
		
		ISession session = SessionFactory.getSession("zfdb");
		
		List<DataObject> list = BaseOptDao.executeQuerySql( session, "select * from customer_zf");
		
		System.out.println(list.size());
		for(DataObject d : list) {
			System.out.println(d.getString("cusId"));
			System.out.println(d.getBytes("zfArr"));
		}
	}
}
