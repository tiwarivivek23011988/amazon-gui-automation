package com.assignment.amazon.drivermanager;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;

public class RemoteDriverManager implements AbstractDriverFunction<WebDriver,MutableCapabilities>{
	
	private static final Logger logger = LogManager.getLogger(RemoteDriverManager.class);

	@Override
	public synchronized WebDriver getDriver() {
		try {
			logger.info("<= In getDriver function of RemoteDriverManager class =>");
			String url = (String)WebDriverUtilities.hashMap.get("gridBaseUrl")+(String)WebDriverUtilities.hashMap.get("gridPathParam");
			return new RemoteWebDriver(new URL(url),getCapabilities());
		} catch (Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}

	@Override
	public MutableCapabilities getCapabilities() {
		logger.info("<= In getCapabilities function of RemoteDriverManager class =>");
		return DriverOptionsFactory.getOptions(WebDriverUtilities.browserName.get());
	}

}
