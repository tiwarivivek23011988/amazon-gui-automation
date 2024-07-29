/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * {@summary}
 * 
 * The ChromeDriverManager Class
 * 
 * This class provides chrome driver handling functions.
 * 
 * @see ChromeDriverManager
 * 
 */
public class ChromeDriverManager implements AbstractDriverFunction<WebDriver,ChromeOptions>{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ChromeDriverManager.class);
	
	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	@Override
	public synchronized WebDriver getDriver() {
		logger.debug("*******In getDriver function of ChromeDriverManager Class*******");
		return new ChromeDriver(getCapabilities());
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	@Override
	public ChromeOptions getCapabilities() {
		logger.debug("*******In getCapabilities function of ChromeDriverManager Class*******");
		return DriverOptionsFactory.getChromeOptions();
	}

}
