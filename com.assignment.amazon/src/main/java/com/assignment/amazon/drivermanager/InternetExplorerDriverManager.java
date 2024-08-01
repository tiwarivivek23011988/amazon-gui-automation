/**
 * @author Vivek Tiwari
 */
package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The InternetExplorerDriverManager Class
 * 
 * This class provides Internet explorer driver handling functions.
 * 
 * @see InternetExplorerDriverManager
 * 
 */

public class InternetExplorerDriverManager implements AbstractDriverFunction<WebDriver,InternetExplorerOptions>{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(InternetExplorerDriverManager.class);

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	@Override
	public synchronized WebDriver getDriver() {
		logger.debug("*******In getDriver function of EdgeDriverManager class*******");
		try {
			return new InternetExplorerDriver(getCapabilities());
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	@Override
	public InternetExplorerOptions getCapabilities() {
		logger.debug("*******In getCapabilities function of EdgeDriverManager class*******");
		try {
			return DriverOptionsFactory.getInternetExplorerOptions();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

}
