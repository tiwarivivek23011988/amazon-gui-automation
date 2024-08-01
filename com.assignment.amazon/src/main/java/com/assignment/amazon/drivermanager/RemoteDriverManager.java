/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;

/**
 * {@summary}
 * 
 * The RemoteDriverManager Class
 * 
 * This class provides remote driver handling functions.
 * 
 * @see RemoteDriverManager
 * 
 */
public class RemoteDriverManager implements AbstractDriverFunction<WebDriver,MutableCapabilities>{
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(RemoteDriverManager.class);

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	@Override
	public synchronized WebDriver getDriver() {
		try {
			logger.debug("*******In getDriver function of RemoteDriverManager class*******");
			String url = (String)WebDriverUtilities.hashMap.get("gridBaseUrl")+(String)WebDriverUtilities.hashMap.get("gridPathParam");
			return new RemoteWebDriver(new URL(url),getCapabilities());
		} catch (Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	@Override
	public synchronized MutableCapabilities getCapabilities() {
		logger.debug("*******In getCapabilities function of RemoteDriverManager class*******");
		try {
			return DriverOptionsFactory.getOptions(WebDriverUtilities.browserName.get());
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

}
