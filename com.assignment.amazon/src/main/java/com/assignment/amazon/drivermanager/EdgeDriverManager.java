/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The EdgeDriverManager Class
 * 
 * This class provides edge driver handling functions.
 * 
 * @see EdgeDriverManager
 * 
 */

public class EdgeDriverManager implements AbstractDriverFunction<WebDriver,EdgeOptions>{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(EdgeDriverManager.class);

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	@Override
	public synchronized WebDriver getDriver() {
		logger.debug("*******In getDriver function of EdgeDriverManager class*******");
		try {
			return new EdgeDriver(getCapabilities());
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
	public EdgeOptions getCapabilities() {
		logger.debug("*******In getCapabilities function of EdgeDriverManager class*******");
		try {
			return DriverOptionsFactory.getEdgeOptions();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}

}
