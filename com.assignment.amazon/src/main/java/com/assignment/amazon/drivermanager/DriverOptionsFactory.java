package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

import com.assignment.amazon.exceptions.ExceptionHandler;

public class DriverOptionsFactory {
	
	private static final Logger logger = LogManager.getLogger(DriverOptionsFactory.class);
	
	public static MutableCapabilities getOptions(String browser) {
		try {
		logger.info("<= In getOptions function =>");
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
		}
		return null;
    }

    static ChromeOptions getChromeOptions() {
    	try {
    	logger.info("<= In getChromeOptions function =>");
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
		}
    	return null;
    }

    static FirefoxOptions getFirefoxOptions() {
    	try {
    	logger.info("<= In getFirefoxOptions function =>");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        return options;
	    } catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
    	return null;
    }

    static SafariOptions getSafariOptions() {
    	try {
    	logger.info("<= In getSafariOptions function =>");
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        return options;
	    } catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
    	return null;
    }

    static InternetExplorerOptions getInternetExplorerOptions() {
    	try {
    	logger.info("<= In getInternetExplorerOptions function =>");
        InternetExplorerOptions options = new InternetExplorerOptions();
        return options;
    	} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
    	return null;
    }
    
    static EdgeOptions getEdgeOptions() {
    	try {
    	logger.info("<= In getInternetExplorerOptions function =>");
        EdgeOptions options = new EdgeOptions();
        return options;
    	} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
    	return null;
    }
	
}
