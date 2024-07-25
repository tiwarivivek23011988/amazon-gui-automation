package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.assignment.amazon.configuration.Setup;

public class ChromeDriverManager implements AbstractDriverFunction<WebDriver,ChromeOptions>{

	private static final Logger logger = LogManager.getLogger(ChromeDriverManager.class);
	
	@Override
	public synchronized WebDriver getDriver() {
		logger.info("<= In getDriver function of ChromeDriverManager Class =>");
		return new ChromeDriver(getCapabilities());
	}

	@Override
	public ChromeOptions getCapabilities() {
		logger.info("<= In getCapabilities function of ChromeDriverManager Class =>");
		return DriverOptionsFactory.getChromeOptions();
	}

}
