package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriverManager implements AbstractDriverFunction<WebDriver,SafariOptions>{

	private static final Logger logger = LogManager.getLogger(SafariDriverManager.class);
	
	@Override
	public synchronized WebDriver getDriver() {
		logger.info("<= In getDriver function of SafariDriverManager class =>");
		return new SafariDriver(getCapabilities());
	}

	@Override
	public SafariOptions getCapabilities() {
		logger.info("<= In getCapabilities function of SafariDriverManager class =>");
		return DriverOptionsFactory.getSafariOptions();
	}

}
