package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager implements AbstractDriverFunction<WebDriver,FirefoxOptions> {

	private static final Logger logger = LogManager.getLogger(FirefoxDriverManager.class);

	@Override
	public synchronized WebDriver getDriver() {
		logger.info("<= In getDriver function of FirefoxDriverManager class =>");
		return new FirefoxDriver(getCapabilities());
	}

	@Override
	public FirefoxOptions getCapabilities() {
		logger.info("<= In getCapabilities function of FirefoxDriverManager class =>");
		return DriverOptionsFactory.getFirefoxOptions();
	}

}
