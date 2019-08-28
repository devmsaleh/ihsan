package com.ihs;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class CustomContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("######### IHS contextInitialized #########");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("######### IHS contextDestroyed #########");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.info(String.format("deregistering jdbc driver: %s", driver));
			} catch (SQLException e) {
				logger.info(String.format("Error deregistering driver %s", driver), e);
			}

		}
	}

}
