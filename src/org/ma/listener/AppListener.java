package org.ma.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ma.org.proxy.ano.BeanFactory;

import org.ma.app.App;
import org.ma.db.jdbc.SessionFactory;
import org.ma.db.jdbc.pool.init.DBPoolInitialization;
import org.ma.device.FingerDevice;

public class AppListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		
		//关闭数据库连接
		SessionFactory.INSTANCE.closeConn();
		
		
	}

	
	public void contextInitialized(ServletContextEvent arg0) {
		//app.xml
		App.INSTANCE.init();
		
		//init bean
		BeanFactory.init("org.ma.app");
		
		//init db
		DBPoolInitialization.init();
		
		//init device
		FingerDevice.getInstance().init();
		
	}

}
