package com.assignment.amazon.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;

public class ExceptionHandler extends Exception {

	private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void throwsException(Throwable e) {
		if (e instanceof WebDriverException) {
            logger.error("WebDriverException occurred: {}", e.getMessage(), e);
        } else {
            logger.error("Java Exception occurred: {}", e.getMessage(), e);
        }
	}

}
