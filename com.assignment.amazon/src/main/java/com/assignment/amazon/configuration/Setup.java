package com.assignment.amazon.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {
	
	private static final Logger logger = LogManager.getLogger(Setup.class);
	
	@Before
	public void setUp() {
		logger.info("<= In setUp Function =>");
		
		try {
		
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
		
		WebDriverUtilities driverManager = new WebDriverUtilities();
		
		CustomWebDriverManager.setDriver(driverManager.getDriver());
		
		/**
		 * Explicit browser handling since some firefox browser version has compatibility issues
		 */
		if(WebDriverUtilities.browserName.get().equalsIgnoreCase("firefox")) {
			WebDriverUtilities.setBrowserSize();
		} else {
			WebDriverUtilities.maximizeBrowserWindow();
		}
		
		logger.info("Scenario Counter Value In Before Hook Is => " +WebDriverUtilities.getScenarioCounter());

		} catch(Exception e) {
			
			ExceptionHandler.throwsException(e);
		}
		
	}
	
	@After
	public void tearDown() {
		try {
		 logger.info("<= In tearDown Function =>");
		 CustomWebDriverManager.getDriver().manage().deleteAllCookies();
		 CustomWebDriverManager.removeDriver();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
}
