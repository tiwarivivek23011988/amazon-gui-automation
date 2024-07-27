/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;

/**
 * {@summary}
 * 
 * The ExceptionHandler Class
 * 
 * This class handles all kind of exceptions being thrown
 * across our automation framework.
 * 
 * @see ExceptionHandler
 * 
 */
public class ExceptionHandler extends Exception {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Throws exception.
	 *
	 * @param e - the exception object
	 */
	public static void throwsException(Throwable e) {
		if (e instanceof WebDriverException) {
            logger.error("WebDriverException occurred: {}", e.getMessage(), e);
        } else {
            logger.error("Java Exception occurred: {}", e.getMessage(), e);
        }
	}

}
