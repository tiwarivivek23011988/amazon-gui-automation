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
		logger.info("<= In getDriver function of SafariDriverManager class =>");
		return new SafariDriver(getCapabilities());
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	@Override
	public SafariOptions getCapabilities() {
		logger.info("<= In getCapabilities function of SafariDriverManager class =>");
		return DriverOptionsFactory.getSafariOptions();
	}

}
