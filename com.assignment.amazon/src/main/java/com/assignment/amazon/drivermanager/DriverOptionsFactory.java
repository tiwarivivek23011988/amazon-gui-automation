package com.assignment.amazon.drivermanager;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

public class DriverOptionsFactory {
	
	public static MutableCapabilities getOptions(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> getChromeOptions();
            case "firefox" -> getFirefoxOptions();
            case "safari" -> getSafariOptions();
            case "explorer" -> getInternetExplorerOptions();
            default -> throw new IllegalArgumentException("Browser type not supported: " + browser);
        };
    }

    static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        return options;
    }

    static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        return options;
    }

    static SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        return options;
    }

    static EdgeOptions getInternetExplorerOptions() {
        EdgeOptions options = new EdgeOptions();
        return options;
    }
	
}
