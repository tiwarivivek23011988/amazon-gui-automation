/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * 
 * {@summary}
 * 
 * The CustomWebDriverManager Class
 * 
 * This class handles ThreadLocal implementation of WebDriver instance.
 * It provide functions which helps in setting,getting and removing
 * driver instance.
 * 
 * @see CustomWebDriverManager
 * 
 */
public class CustomWebDriverManager {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CustomWebDriverManager.class);
	
    /** The Constant driver. */
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public static WebDriver getDriver() {
    	logger.debug("*******In getDriver function of CustomWebDriverManager class*******");
        return driver.get();
    }

    /**
     * Sets the driver.
     *
     * @param driverInstance - the new driver
     */
    public static void setDriver(WebDriver driverInstance) {
    	logger.debug("*******In setDriver function of CustomWebDriverManager class*******");
        driver.set(driverInstance);
    }

    /**
     * Removes the driver.
     */
    public static void removeDriver() {
    	try {
    	logger.debug("*******In removeDriver function of CustomWebDriverManager class*******");
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
    }
}
