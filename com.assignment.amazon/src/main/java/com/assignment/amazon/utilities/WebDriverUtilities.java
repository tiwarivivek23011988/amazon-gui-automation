/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.utilities;


import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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
	public static JsonParserUtility jsonParser = new JsonParserUtility();
	
	/** The file path object */
	public static String filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
	
	// Map to store the PIDs of the browser processes for each thread
    public static final Map<Thread, List<Long>> pidMap = new HashMap<>();

 // Map to store the PIDs of the browser processes for each thread
    public static final List<Long> allPidList = new ArrayList<>();
    
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
		jsonParser = new JsonParserUtility();
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
		logger.debug("*******In browserCounter method*******" +"\n"+Thread.currentThread().getName());
		int counter = ParallelCounterUtility.getCounter();
		if(counter < WebDriverUtilities.browserNames.size()) {
			browserName.set((String) WebDriverUtilities.browserNames.get(counter));
			logger.info("Counter Value Is => " +counter);
			logger.info("Browser Name Is => " +browserName.get());
			ParallelCounterUtility.incrementCounter();
		} else {
			ParallelCounterUtility.resetCounter();
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
		logger.debug("*******In decrementCounter method*******");
		return ParallelCounterUtility.decrementCounter();
	}
	
	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public static synchronized int getCounter() {	
		logger.debug("*******In getCounter method*******");
		return ParallelCounterUtility.getCounter();
	}
	
	/**
	 * Gets the scenario counter.
	 *
	 * @return the scenario counter
	 */
	public static synchronized int getScenarioCounter() {
		logger.debug("*******In getScenarioCounter method*******");
		return ParallelCounterUtility.scenarioCounter.get();
	}
	
	/**
	 * Reset counter.
	 */
	public static synchronized void resetCounter() {
		logger.debug("*******In resetCounter method*******");
		ParallelCounterUtility.resetCounter();
	}
	
	/**
	 * Gets the web-driver object.
	 *
	 * @return the driver
	 */
	public synchronized WebDriver getDriver() {
		try {
			logger.debug("*******In getDriver method*******" +"\n"+Thread.currentThread().getName());
			String runType=hashMap.get("runType").toString().toLowerCase();
			logger.info("runType is => " + runType);
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
		    
			pidMap.put(Thread.currentThread(), ProcessManagementUtility.getBrowserProcessPID(driver, browserName.get()));
		    
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
		logger.debug("*******In waitForElementToBeVisible method*******");
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
		logger.debug("*******In waitForElementToBeClickable method*******");
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
		logger.debug("*******In waitUntilVisibilityOfAllElementsLocated method*******");
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
		logger.debug("*******In clickOnWebElement method*******");
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
		logger.debug("*******In getWindowHandles method*******");
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
		logger.debug("*******In switchToWindowHandle method*******");
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
		logger.debug("*******In waitForElementVisibilityUsingFluentWait method*******");
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
		logger.debug("*******In waitForElementClickabilityUsingFluentWait method*******");
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
		logger.debug("*******In waitForAllAjaxCallsToCompleteUsingFluentWait method*******");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(60))
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
		logger.debug("*******In waitForElementsVisibilityUsingFluentWait method*******");
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(60))
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
		logger.debug("*******In scrollToView method*******");
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
		logger.debug("*******In scrollToViewAndClick method*******");
		//Smooth Scrolling
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center'}); arguments[0].click();", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Maximize browser window.
	 */
	public static void maximizeBrowserWindow() {
		try {
		logger.debug("*******In maximizeBrowserWindow method*******");
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
		logger.debug("*******In moveToElementAndPerformElementClickUsingActions method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.moveToElement(element).click().perform();
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
		logger.debug("*******In scrollToElementAndPerformElementClickUsingActions method*******");
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
		logger.debug("*******In performElementClickAndHoldUsingActions method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.clickAndHold(element).release().build().perform();
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
		logger.info("*******In performJavaScriptClick method*******");
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
		logger.debug("*******In scrollToElementHeight method*******");
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript(
	            "arguments[0].scrollDown = arguments[0].scrollHeight;",
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
		logger.debug("*******In setBrowserSize method*******");
		CustomWebDriverManager.getDriver().manage().window().setSize(new Dimension(1200, 1200));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * Zoom out browser window.
	 */
	public static void zoomOutBrowserWindow() {
		logger.debug("*******In zoomOutBrowserWindow method*******");
		JavascriptExecutor js = (JavascriptExecutor) CustomWebDriverManager.getDriver();
		js.executeScript("document.body.style.transform = 'scale(0.5)';");
	}
	
	/**
	 * Wait for css transitions to complete
	 * 
	 * @param - WebElement as element
	 * 
	 * @return - boolean value once css animation completes
	 * 
	 */
	
	public static boolean waitForCssTransitionsToComplete(WebElement element) {
		logger.debug("*******In waitForCssTransitionsToComplete method*******");
		JavascriptExecutor js = (JavascriptExecutor) CustomWebDriverManager.getDriver();
		Boolean animationComplete = (Boolean) js.executeScript("return window.getComputedStyle(arguments[0]).animationName !== 'none';", element);
		return animationComplete;
	}
	
	
	/**
	 * Wait for text to be present in element located
	 * 
	 * @param WebElement as element, String element text as text
	 *
	 */
	
	public static void waitUntilTextToBePresentInElementLocated(WebElement element, String text) {
		try {
			logger.debug("*******In waitUntilTextToBePresentInElementLocated method*******");
			Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
	                .withTimeout(Duration.ofSeconds(30))
	                .pollingEvery(Duration.ofSeconds(1))
	                .ignoring(Exception.class);

	        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
			} catch(Exception e) {
				ExceptionHandler.throwsException(e);
			}
		
	}
	
	/**
	 * Actions class with keyDown click and hold
	 * 
	 * @param Keyboard Keys as key
	 * 
	 */
	
	public static void keyPressEventUsingActionsClickAndHold(Keys key) {
		logger.debug("*******In keyPressEventUsingActionsClickAndHold method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.keyDown(key).clickAndHold().pause(Duration.ofMillis(5)).build().perform();
	}
	
	/**
	 * Move to element using actions and keyboard key press events
	 * 
	 * @param WebElement as element, Keyboard Keys as key
	 * 
	 */
	
	public static void keyPressEventUsingActionsRelease(WebElement element, Keys key) {
		logger.debug("*******In keyPressEventUsingActionsRelease method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.moveToElement(element).sendKeys(key).release().perform();
	}
	
	/**
	 * Actions using sendKeys and keyboard event
	 * 
	 * @param Keyboard Keys as key
	 * 
	 */
	
	public static void keyPressEventUsingActionsSendKeys(Keys key) {
		logger.debug("*******In keyPressEventUsingActionsSendKeys method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.sendKeys(key).pause(Duration.ofMillis(20)).build().perform();
	}
	
	/**
	 * Plain Actions moveToElement
	 * 
	 * @param WebElement as element
	 * 
	 */
	
	public static void moveToElementUsingActions(WebElement element) {
		logger.debug("*******In moveToElementUsingActions method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.moveToElement(element).build().perform();
	}
	
	/**
	 * Plain Actions cickAndHold
	 * 
	 * @param - WebElement as element
	 * 
	 */
	
	public static void clickAndHoldUsingActions(WebElement element) {
		logger.debug("*******In clickAndHoldUsingActions method*******");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.clickAndHold(element).build().perform();
	}
	
	/**
	 * Select by visible text using Javascript
	 * 
	 * @param - WebElement as element, integer as index of Select drop-down
	 * 
	 */
	public static synchronized void selectByIndexUsingJavascript(WebElement element, int index) {
		logger.debug("*******In selectByIndexUsingJavascript method*******");
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript(String.format("arguments[0].selectedIndex = %d;",index), element);
	}
	
	/**
	 * Select by visible text using Javascript and wait
	 * till element is clicked.
	 * 
	 * @param - WebElement as element, integer as index of Select drop-down
	 * 
	 */
	public static void selectByIndexUsingJsAndWaitTillElementClicked(WebElement element, int index) {
		logger.debug("*******In selectByIndexUsingJsAndWaitTillElementClicked method*******");
		JavascriptExecutor jsExecutor = (JavascriptExecutor) CustomWebDriverManager.getDriver();

		/** Execute JavaScript to set selectedIndex and click with retry mechanism */
		jsExecutor.executeScript(
		    "var element = arguments[0]; " +
		    "var index = arguments[1]; " +
		    "var attempts = 0; " +
		    "var maxAttempts = 50; " + /** Adjust the number of attempts as needed */
		    "function tryClick() { " +
		    "   element.selectedIndex = index; " +
		    "   element.click(); " +
		    "   attempts++; " +
		    "   if (!element.onclick && attempts < maxAttempts) { " +
		    "       setTimeout(tryClick, 1000); " + /** Adjust the timeout (1000 ms = 1 second) as needed */
		    "   } " +
		    "} " +
		    "tryClick();",
		    element, index);
	}
	
	/**
	 * Switch to new window from current window
	 * 
	 */
	public static void switchToWindow() {
		logger.debug("*******In switchToWindow method*******");
		Set<String> set = getWindowHandles();
		String currentWindowHandle=CustomWebDriverManager.getDriver().getWindowHandle();
		String handleToSwitch=null;
		Assert.assertTrue(set.size()>1);
		Iterator<String> itr = set.iterator();
		while(itr.hasNext()) {
			String handle = itr.next();
			if(!handle.equals(currentWindowHandle)) {
				handleToSwitch=handle;
			}
		}
		CustomWebDriverManager.setDriver(WebDriverUtilities.switchToWindowHandle(handleToSwitch));
	}
}