/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The FirefoxDriverManager Class
 * 
 * This class provides firefox driver handling functions.
 * 
 * @see FirefoxDriverManager
 * 
 */

public class FirefoxDriverManager implements AbstractDriverFunction<WebDriver,FirefoxOptions> {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FirefoxDriverManager.class);

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 * 
	 */
	
	@Override
	public synchronized WebDriver getDriver() {
		logger.debug("*******In getDriver function of FirefoxDriverManager class*******");
		try {
			return new FirefoxDriver(getCapabilities());
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 * 
	 */
	
	@Override
	public FirefoxOptions getCapabilities() {
		logger.debug("*******In getCapabilities function of FirefoxDriverManager class*******");
		try {
			return DriverOptionsFactory.getFirefoxOptions();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

}
