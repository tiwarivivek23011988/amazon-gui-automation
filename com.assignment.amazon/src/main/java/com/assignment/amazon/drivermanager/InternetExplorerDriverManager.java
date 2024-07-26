package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class InternetExplorerDriverManager implements AbstractDriverFunction<WebDriver,InternetExplorerOptions>{

	private static final Logger logger = LogManager.getLogger(InternetExplorerDriverManager.class);

	@Override
	public synchronized WebDriver getDriver() {
		logger.info("<= In getDriver function of EdgeDriverManager class =>");
		return new InternetExplorerDriver(getCapabilities());
	}

	@Override
	public InternetExplorerOptions getCapabilities() {
		logger.info("<= In getCapabilities function of EdgeDriverManager class =>");
		return DriverOptionsFactory.getInternetExplorerOptions();
	}

}
