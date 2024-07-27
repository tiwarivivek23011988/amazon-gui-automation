/**
 * @author Vivek Tiwari
 */
package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

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
		logger.info("<= In getDriver function of EdgeDriverManager class =>");
		return new InternetExplorerDriver(getCapabilities());
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	@Override
	public InternetExplorerOptions getCapabilities() {
		logger.info("<= In getCapabilities function of EdgeDriverManager class =>");
		return DriverOptionsFactory.getInternetExplorerOptions();
	}

}
