/**
 * @author - Vivek Tiwari
 * 
 */
package com.assignment.amazon.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * {@summary}
 * 
 * The Setup Class
 * 
 * This class is used to perform setup and tear down operations related
 * to our test executions.
 * 
 * @see Setup
 * 
 */
public class Setup {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Setup.class);
	
	/**
	 * This function takes care of setting up the browser binaries and 
	 * driver initializations.
	 */
	@Before
	public synchronized void setUp() {
		logger.debug("*******In setUp Function*******");
		
		try {
		
		if(WebDriverUtilities.hashMap.get("runType").toString().equalsIgnoreCase("local")) {
						
			switch(WebDriverUtilities.browserName.get()) {
				case "chrome" -> {
					WebDriverManager.chromedriver().clearDriverCache().setup();
				}
				case "firefox" -> {
					WebDriverManager.firefoxdriver().clearDriverCache().setup();
				}
				case "edge" -> {
					WebDriverManager.edgedriver().clearDriverCache().setup();
				}
				case "safari" -> {
					WebDriverManager.safaridriver().clearDriverCache().setup();
				}
				case "explorer" -> {
					WebDriverManager.iedriver().clearDriverCache().setup();
				}
				default -> ExceptionHandler.throwsException(new IllegalArgumentException("Browser type not supported: " + WebDriverUtilities.browserName.get()));
			}
		}
		WebDriverUtilities driverManager = new WebDriverUtilities();
		
		CustomWebDriverManager.setDriver(driverManager.getDriver());
		
		WebDriverUtilities.maximizeBrowserWindow();
		
		logger.debug("*******Scenario Counter Value In Before Hook Is*******" +"\n"+WebDriverUtilities.getScenarioCounter());

		} catch(Exception e) {
			
			ExceptionHandler.throwsException(e);
		}
		
	}
	
	/**
	 * This function takes care of browser driver tear down operations,
	 * like deleting all cookies, removing browser driver and quitting
	 * browser driver instances.
	 */
	@After
	public synchronized void tearDown() {
		try {
		 logger.debug("*******In tearDown Function*******");
		 CustomWebDriverManager.getDriver().manage().deleteAllCookies();
		 CustomWebDriverManager.getDriver().quit();
		 CustomWebDriverManager.removeDriver();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
}
