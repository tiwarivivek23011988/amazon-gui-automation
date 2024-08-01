/**
 * @author - Vivek Tiwari
 * 
 */
package com.assignment.amazon.configuration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.ProcessManagementUtility;
import com.assignment.amazon.utilities.WebDriverUtilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

// TODO: Auto-generated Javadoc
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
	public void setUp() {
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
			throw e;
		}
		
	}
	
	/**
	 * This function takes care of browser driver tear down operations,
	 * like deleting all cookies, removing browser driver and quitting
	 * browser driver instances.
	 */
	@After
	public void tearDown() {
		WebDriver driver = CustomWebDriverManager.getDriver();
		if(driver != null) {
			try {
			 
			 logger.debug("*******In tearDown Function*******");
			 logger.info("Attempting to delete all cookies and quit the driver.");
			 driver.manage().deleteAllCookies();
			 driver.close();
			 driver.quit();
			 logger.info("Driver quit successfully.!!");
			 
			} catch(Exception e) {
				
				logger.error("Exception occurred while quitting the driver: ");
				ExceptionHandler.throwsException(e);
				throw e;
				
			}finally {
	            try {
	                
	            	CustomWebDriverManager.removeDriver();
	            	
	            	List<Long> processIdList = WebDriverUtilities.pidMap.get(Thread.currentThread());
	            	
	            	WebDriverUtilities.allPidList.addAll(processIdList);
	            	
	                logger.info("Driver removed from ThreadLocal.");
	                
	                
	            } catch (Exception e) {
	                logger.error("Exception occurred while removing the driver: ");
	                ExceptionHandler.throwsException(e);
	                throw e;
	            }
	        }
		} else {
	        logger.warn("Driver instance is null, no action needed.!!");
	    }
	}
	
	/**
	 * Kill dangling browser process references.
	 * 
	 */
	
	public static void killDanglingBrowserProcessReferences() {
		
		logger.debug("*******In killDanglingBrowserProcessReferences function*******");
    	
		try {
			List<Long> childPids = ProcessManagementUtility.getChildProcessIds(WebDriverUtilities.allPidList);
			
			logger.info("Child Process ids are => "+childPids);
			
			for(Long pid: childPids) {
				try {
		    		ProcessManagementUtility.killProcess(pid);
		    		logger.info("Killed child process with pid => "+pid+" successfully.!");
		    		} catch(Exception e) {
		    			logger.warn("Child Process with id => "+pid+" already killed");
		        		continue;
		    		}
		    	}
			
	    	for(Long pid: WebDriverUtilities.allPidList) {
		    	try {
		    		ProcessManagementUtility.killProcess(pid);
		    		logger.info("Killed parent process with pid => "+pid+" successfully.!");
		    		} catch(Exception e) {
		    			logger.warn("Parent Process with id => "+pid+" already killed");
		        		continue;
		        }
	    	}
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
	}
}
