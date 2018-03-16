package org.ma.db.jdbc.test;

import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.pool.init.DBPoolInitialization;

public class TestDBOpt {

	public static void opt(IOpt opt){
		DBPoolInitialization.init();
		
		opt.opt();
		
		SessionFactory.closeSession();
		SessionFactory.INSTANCE.closeConn();
	}
	
	
	public interface IOpt{
		void opt();
	}
}
