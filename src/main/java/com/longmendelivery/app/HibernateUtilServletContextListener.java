package com.longmendelivery.app;
import com.longmendelivery.persistence.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateUtilServletContextListener implements ServletContextListener {
 
	public void contextDestroyed(ServletContextEvent arg0) {

		System.out.println("HibernateUtilServletContextListener closed Hibernate");
	}
 
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener starting Hibernate...");
		HibernateUtil.getSessionFactory();
		System.out.println("ServletContextListener started Hibernate");
	}
}