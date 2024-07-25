package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.assignment.amazon.exceptions.ExceptionHandler;

public class CustomWebDriverManager {
	
	private static final Logger logger = LogManager.getLogger(CustomWebDriverManager.class);
	
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
    	logger.info("<= In getDriver function of CustomWebDriverManager class =>");
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
    	logger.info("<= In setDriver function of CustomWebDriverManager class =>");
        driver.set(driverInstance);
    }

    public static void removeDriver() {
    	try {
    	logger.info("<= In removeDriver function of CustomWebDriverManager class =>");
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
    }
}
