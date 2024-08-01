/**
 * @author - Vivek Tiwari
 */

package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;


/**
 * 
 * {@summary}
 * 
 * The DriverOptionsFactory Class
 * 
 * A factory for creating DriverOptions objects.
 * 
 * This is a helper class which returns different driver specific options
 * as per provider browser names.
 * 
 * @see DriverOptionsFactory
 * 
 */

public class DriverOptionsFactory {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(DriverOptionsFactory.class);
	
	/**
	 * Gets the options.
	 *
	 * @param browser - the browser
	 * @return the options
	 */
	public static MutableCapabilities getOptions(String browser) {
		try {
			logger.debug("*******In getOptions function*******");
	        return switch (browser.toLowerCase()) {
	            case "chrome" -> getChromeOptions();
	            case "firefox" -> getFirefoxOptions();
	            case "safari" -> getSafariOptions();
	            case "explorer" -> getInternetExplorerOptions();
	            case "edge" -> getEdgeOptions();
	            default -> throw new IllegalArgumentException("Browser type not supported: " + browser);
	        };
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }

    /**
     * Gets the chrome options.
     *
     * @return the chrome options
     */
    static ChromeOptions getChromeOptions() {
    	try {
	    	logger.debug("*******In getChromeOptions function*******");
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--start-maximized");
	        options.addArguments("--incognito");
	        options.addArguments("--disable-gpu");
	        options.addArguments("--no-sandbox");
	        options.addArguments("--disable-dev-shm-usage");
	        options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
	        return options;
	    } catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }

    /**
     * Gets the firefox options.
     *
     * @return the firefox options
     */
    static FirefoxOptions getFirefoxOptions() {
    	try {
	    	logger.debug("*******In getFirefoxOptions function*******");
	    	FirefoxProfile profile = new FirefoxProfile();
	        FirefoxOptions options = new FirefoxOptions();
	        options.addPreference("browser.privatebrowsing.autostart", true);
	        options.setProfile(profile);
	        options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
	        return options;
	    } catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }

    /**
     * Gets the safari options.
     *
     * @return the safari options
     */
    static SafariOptions getSafariOptions() {
    	try {
	    	logger.debug("*******In getSafariOptions function*******");
	        SafariOptions options = new SafariOptions();
	        options.setCapability(CapabilityType.BROWSER_NAME, "Safari");
	        return options;
	    } catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }

    /**
     * Gets the internet explorer options.
     *
     * @return the internet explorer options
     */
    static InternetExplorerOptions getInternetExplorerOptions() {
    	try {
	    	logger.debug("*******In getInternetExplorerOptions function*******");
	        InternetExplorerOptions options = new InternetExplorerOptions();
	        options.setCapability("enablePersistentHover", true);
	        options.setCapability("nativeEvents", true);
	        options.setCapability("ignoreProtectedModeSettings", true);
	        options.setCapability("ignoreZoomSetting", true);
	        options.setCapability("requireWindowFocus", true);
	        options.setCapability("ie.ensureCleanSession", true);
	        return options;
    	} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }
    
    /**
     * Gets the edge options.
     *
     * @return the edge options
     */
    static EdgeOptions getEdgeOptions() {
    	try {
	    	logger.debug("*******In getInternetExplorerOptions function*******");
	        EdgeOptions options = new EdgeOptions();
	        options.addArguments("--disable-notifications");
	        options.addArguments("--disable-popup-blocking");
	        options.addArguments("-inprivate");
	        return options;
    	} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }
	
}
