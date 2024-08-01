/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * 
 * {@summary}
 * 
 * The SafariDriverManager Class
 * 
 * This class provides safari driver handling functions.
 * 
 * @see SafariDriverManager
 * 
 */
public class SafariDriverManager implements AbstractDriverFunction<WebDriver,SafariOptions>{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SafariDriverManager.class);
	
	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	@Override
	public synchronized WebDriver getDriver() {
		logger.debug("*******In getDriver function of SafariDriverManager class*******");
		try {
			return new SafariDriver(getCapabilities());
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
	public SafariOptions getCapabilities() {
		logger.debug("*******In getCapabilities function of SafariDriverManager class*******");
		try {
			return DriverOptionsFactory.getSafariOptions();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

}
