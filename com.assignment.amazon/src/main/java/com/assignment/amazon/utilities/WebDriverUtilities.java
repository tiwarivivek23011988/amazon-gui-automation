/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.utilities;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assignment.amazon.drivermanager.ChromeDriverManager;
import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.drivermanager.EdgeDriverManager;
import com.assignment.amazon.drivermanager.FirefoxDriverManager;
import com.assignment.amazon.drivermanager.InternetExplorerDriverManager;
import com.assignment.amazon.drivermanager.RemoteDriverManager;
import com.assignment.amazon.drivermanager.SafariDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;


/**
 * 
 * {@summary}
 * 
 * The WebDriverUtilities Class
 * 
 * This is a utility class for handling all selenium web-driver
 * related operations and actions.
 * 
 * @see WebDriverUtilities
 * 
 */
public final class WebDriverUtilities {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(WebDriverUtilities.class);
	
	/** The json parser object */
	public static JsonParser jsonParser = new JsonParser();
	
	/** The file path object */
	public static String filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
	
	/** The Constant hashMap reference */
	public static final HashMap<String, ?> hashMap;
	
	/** The list of browser names reference. */
	public static List<?> browserNames;
	
	/** The Constant thread-local browserName. */
	public static final ThreadLocal<String> browserName;
	
	/** The Constant thread-local browserVersion. */
	public static final ThreadLocal<String> browserVersion;
	
	/** Static block initialization of object references.*/
	static {
		jsonParser = new JsonParser();
		filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
		hashMap = jsonParser.parseJson(filePath);
		browserNames=(List<?>) hashMap.get("browserName");
		browserName=new ThreadLocal<>();
		browserVersion=new ThreadLocal<>();
	}
	
	/**
	 * This function fetches browser name based on thread local
	 * implemented counter.
	 */
	public static synchronized void browserCounter() {
		try {
		logger.info("<= In browserCounter method => " +Thread.currentThread().getName());
		int counter = ParallelCounter.getCounter();
		if(counter < WebDriverUtilities.browserNames.size()) {
			browserName.set((String) WebDriverUtilities.browserNames.get(counter));
			System.out.println("Counter Value Is => " +counter);
			System.out.println("Browser Name Is => " +browserName.get());
			ParallelCounter.incrementCounter();
		} else {
			ParallelCounter.resetCounter();
			browserCounter();
		}
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Decrement counter.
	 *
	 * @return the integer value
	 */
	public static synchronized int decrementCounter() {
		logger.info("<== In decrementCounter method ==>");
		return ParallelCounter.decrementCounter();
	}
	
	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public static synchronized int getCounter() {	
		logger.info("<== In getCounter method ==>");
		return ParallelCounter.getCounter();
	}
	
	/**
	 * Gets the scenario counter.
	 *
	 * @return the scenario counter
	 */
	public static synchronized int getScenarioCounter() {
		logger.info("<== In getScenarioCounter method ==>");
		return ParallelCounter.scenarioCounter.get();
	}
	
	/**
	 * Reset counter.
	 */
	public static synchronized void resetCounter() {
		logger.info("<== In resetCounter method ==>");
		ParallelCounter.resetCounter();
	}
	
	/**
	 * Gets the web-driver object.
	 *
	 * @return the driver
	 */
	public synchronized WebDriver getDriver() {
		try {
			logger.info("<= In getDriver method => " +Thread.currentThread().getName());
			String runType=hashMap.get("runType").toString().toLowerCase();
			logger.info("<= runType value is => " + runType);
			WebDriver driver=switch(runType) {
			case "local" -> 
				switch(browserName.get()) {
					case "chrome" -> new ChromeDriverManager().getDriver();
					case "firefox" -> new FirefoxDriverManager().getDriver();
					case "edge" -> new EdgeDriverManager().getDriver();
					case "safari" -> new SafariDriverManager().getDriver();
					case "explorer" -> new InternetExplorerDriverManager().getDriver();
					default -> throw new IllegalArgumentException("Browser type not supported: " + browserName.get());
					};
				
			case "remote" -> new RemoteDriverManager().getDriver();
			default -> throw new IllegalArgumentException("Unexpected value: " + hashMap.get("runType").toString().toLowerCase());
			};
			/**
			 *  This is required for firefox due to existing compatibility issues with latest version of firefox
			 */
			return driver;
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}
	
	/**
	 * Wait for element to be visible.
	 *
	 * @param element - the web-element
	 */
	public static void waitForElementToBeVisible(WebElement element) {
		try {
		logger.info("<== In waitForElementToBeVisible method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Wait for element to be clickable.
	 *
	 * @param element - the web-element
	 */
	public static void waitForElementToBeClickable(WebElement element) {
		try {
		logger.info("<== In waitForElementToBeClickable method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Wait until visibility of all elements located.
	 *
	 * @param element - the web-element
	 */
	public static void waitUntilVisibilityOfAllElementsLocated(List<WebElement> element) {
		try {
		logger.info("<== In waitUntilVisibilityOfAllElementsLocated method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Click on web element.
	 *
	 * @param element - the web-element
	 */
	public static void clickOnWebElement(WebElement element) {
		try {
		logger.info("<== In clickOnWebElement method ==>");
		waitForElementToBeClickable(element);
		element.click();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Gets the window handles.
	 *
	 * @return the window handles
	 */
	public static Set<String> getWindowHandles() {
		try {
		logger.info("<== In getWindowHandles method ==>");
		if(CustomWebDriverManager.getDriver() != null)
			return CustomWebDriverManager.getDriver().getWindowHandles();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}
	
	/**
	 * Switch to window handle.
	 *
	 * @param handleId - the browser handle id
	 * @return the web driver
	 */
	public static WebDriver switchToWindowHandle(String handleId) {
		try {
		logger.info("<== In switchToWindowHandle method ==>");
		if(CustomWebDriverManager.getDriver() != null)
			return CustomWebDriverManager.getDriver().switchTo().window(handleId);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}
	
	/**
	 * Wait for element visibility using fluent wait.
	 *
	 * @param element - the web-element
	 */
	public static void waitForElementVisibilityUsingFluentWait(WebElement element) {
		try {
		logger.info("<== In waitForElementVisibilityUsingFluentWait method ==>");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
          

        wait.until(ExpectedConditions.visibilityOf(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}

	}
	
	/**
	 * Wait for element clickability using fluent wait.
	 *
	 * @param element - the web-element
	 */
	public static void waitForElementClickabilityUsingFluentWait(WebElement element) {
		try {
		logger.info("<== In waitForElementClickabilityUsingFluentWait method ==>");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
          

        wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Wait for all ajax calls to complete using fluent wait.
	 *
	 * @param element - the web-element
	 */
	public static void waitForAllAjaxCallsToCompleteUsingFluentWait(WebElement element) {
		try {
		logger.info("<== In waitForAllAjaxCallsToCompleteUsingFluentWait method ==>");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Wait for elements visibility using fluent wait.
	 *
	 * @param element - the web-element
	 */
	public static void waitForElementsVisibilityUsingFluentWait(List<WebElement> element) {
		try {
		logger.info("<== In waitForElementsVisibilityUsingFluentWait method ==>");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}

	}
	
	/**
	 * Scroll to view.
	 *
	 * @param element - the web-element
	 */
	public static void scrollToView(WebElement element) {
		try {
		logger.info("<== In scrollToView method ==>");
        ((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Scroll to view and click.
	 *
	 * @param element - the web-element
	 */
	public static void scrollToViewAndClick(WebElement element) {
		try {
		logger.info("<== In scrollToViewAndClick method ==>");
		//Smooth Scrolling
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' }); arguments[0].click();", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Maximize browser window.
	 */
	public static void maximizeBrowserWindow() {
		try {
		logger.info("<== In maximizeBrowserWindow method ==>");
		CustomWebDriverManager.getDriver().manage().window().maximize();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Move to element and perform element click using actions.
	 *
	 * @param element - the web-element
	 */
	public static void moveToElementAndPerformElementClickUsingActions(WebElement element) {
		try {
		logger.info("<== In moveToElementAndPerformElementClickUsingActions method ==>");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.click(element).perform();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Scroll to element and perform element click using actions class.
	 *
	 * @param element - the web-element
	 */
	public static void scrollToElementAndPerformElementClickUsingActions(WebElement element) {
		try {
		logger.info("<== In scrollToElementAndPerformElementClickUsingActions method ==>");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.scrollToElement(element).click().perform();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Perform element click and hold using actions.
	 *
	 * @param element - the web-element
	 */
	public static void performElementClickAndHoldUsingActions(WebElement element) {
		try {
		logger.info("<== In scrollToElementAndPerformElementClickUsingActions method ==>");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.clickAndHold(element).perform();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Perform java script click.
	 *
	 * @param element - the web-element
	 */
	public static void performJavaScriptClick(WebElement element) {
		try {
		logger.info("<== In performJavaScriptClick method ==>");
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].click();", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Scroll to element height.
	 *
	 * @param element - the web-element
	 */
	public static void scrollToElementHeight(WebElement element) {
		try {
		logger.info("<== In scrollToElementHeight method ==>");
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript(
	            "arguments[0].scrollTop = arguments[0].scrollHeight;",
	            element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Sets the browser size.
	 */
	public static void setBrowserSize() {
		try {
		logger.info("<== In setBrowserSize method ==>");
		CustomWebDriverManager.getDriver().manage().window().setSize(new Dimension(768, 1024));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Zoom out browser window.
	 */
	public static void zoomOutBrowserWindow() {
		JavascriptExecutor js = (JavascriptExecutor) CustomWebDriverManager.getDriver();
		js.executeScript("document.body.style.transform = 'scale(0.5)'");
	}
}