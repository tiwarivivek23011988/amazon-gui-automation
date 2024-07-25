package com.assignment.amazon.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {
	
	private static final Logger logger = LogManager.getLogger(Setup.class);
	
	@Before
	public void setUp() {
		logger.info("Executing Setup Function");
		
		try {
		
		switch(DriverManager.browserName.get()) {
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
		default -> ExceptionHandler.throwsException(new IllegalArgumentException("Browser type not supported: " + DriverManager.browserName.get()));
		}
		
		DriverManager driverManager = new DriverManager();
		
		CustomWebDriverManager.setDriver(driverManager.getDriver());
		
		/**
		 * Explicit browser handling since some firefox browser version has compatibility issues
		 */
		if(DriverManager.browserName.get().equalsIgnoreCase("firefox")) {
			DriverManager.setBrowserSize();
		} else {
			DriverManager.maximizeBrowserWindow();
		}
		
		System.out.println("Scenario Counter Value In Before Hook Is => " +driverManager.getScenarioCounter());

		} catch(Exception e) {
			
			ExceptionHandler.throwsException(e);
		}
		
	}
	
	@After
	public void tearDown() {
		 CustomWebDriverManager.getDriver().manage().deleteAllCookies();
		 CustomWebDriverManager.removeDriver();
	}
}
