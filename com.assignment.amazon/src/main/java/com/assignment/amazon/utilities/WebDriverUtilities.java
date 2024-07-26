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


public final class WebDriverUtilities {
	private static final Logger logger = LogManager.getLogger(WebDriverUtilities.class);
	public static JsonParser jsonParser = new JsonParser();
	public static String filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
	public static HashMap<String, ?> hashMap;
	public static volatile List<?> browserNames;
	public static final ThreadLocal<String> browserName;
	public static final ThreadLocal<String> browserVersion;
	static {
		jsonParser = new JsonParser();
		filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
		hashMap = jsonParser.parseJson(filePath);
		browserNames=(List<?>) hashMap.get("browserName");
		browserName=new ThreadLocal<>();
		browserVersion=new ThreadLocal<>();
	}
	
	public static synchronized void browserCounter() {
		try {
		logger.info("<= In browserCounter method => " +Thread.currentThread().getName());
		int counter = ParallelCounter.getCounter();
		if(counter < WebDriverUtilities.browserNames.size()) {
			browserName.set((String) WebDriverUtilities.browserNames.get(counter));
			logger.info("Counter Value Is => " +counter);
			logger.info("Browser Name Is => " +browserName.get());
			ParallelCounter.incrementCounter();
		} else {
			ParallelCounter.resetCounter();
			browserName.set((String) WebDriverUtilities.browserNames.get(ParallelCounter.getCounter()));
		}
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static synchronized int decrementCounter() {
		logger.info("<== In decrementCounter method ==>");
		return ParallelCounter.decrementCounter();
	}
	
	public static synchronized int getCounter() {	
		logger.info("<== In getCounter method ==>");
		return ParallelCounter.getCounter();
	}
	
	public static synchronized int getScenarioCounter() {
		logger.info("<== In getScenarioCounter method ==>");
		return ParallelCounter.scenarioCounter.get();
	}
	
	public static synchronized void resetCounter() {
		logger.info("<== In resetCounter method ==>");
		ParallelCounter.resetCounter();
	}
	
	public synchronized WebDriver getDriver() {
		try {
			logger.info("<= In getDriver method => " +Thread.currentThread().getName());
			String runType=hashMap.get("runType").toString().toLowerCase();
			logger.info("<= runType value is => " + runType);
			WebDriver driver=switch(runType) {
			case "local" -> {
				yield switch(browserName.get()) {
					case "chrome" -> new ChromeDriverManager().getDriver();
					case "firefox" -> new FirefoxDriverManager().getDriver();
					case "edge" -> new EdgeDriverManager().getDriver();
					case "safari" -> new SafariDriverManager().getDriver();
					case "explorer" -> new InternetExplorerDriverManager().getDriver();
					default -> throw new IllegalArgumentException("Browser type not supported: " + browserName.get());
					};
				}
			case "remote" -> {yield new RemoteDriverManager().getDriver();}
			default -> throw new IllegalArgumentException("Unexpected value: " + hashMap.get("runType").toString().toLowerCase());
			};
			return driver;
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return null;
	}
	
	public static void waitForElementToBeVisible(WebElement element) {
		try {
		logger.info("<== In waitForElementToBeVisible method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void waitForElementToBeClickable(WebElement element) {
		try {
		logger.info("<== In waitForElementToBeClickable method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void waitUntilVisibilityOfAllElementsLocated(List<WebElement> element) {
		try {
		logger.info("<== In waitUntilVisibilityOfAllElementsLocated method ==>");
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void clickOnWebElement(WebElement element) {
		try {
		logger.info("<== In clickOnWebElement method ==>");
		waitForElementToBeClickable(element);
		element.click();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
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
	
	public static void scrollToView(WebElement element) {
		try {
		logger.info("<== In scrollToView method ==>");
        ((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void scrollToViewAndClick(WebElement element) {
		try {
		logger.info("<== In scrollToViewAndClick method ==>");
		//Smooth Scrolling
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' }); arguments[0].click();", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void maximizeBrowserWindow() {
		try {
		logger.info("<== In maximizeBrowserWindow method ==>");
		CustomWebDriverManager.getDriver().manage().window().maximize();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void moveToElementAndPerformElementClickUsingActions(WebElement element) {
		try {
		logger.info("<== In moveToElementAndPerformElementClickUsingActions method ==>");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.moveToElement(element).click().perform();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void scrollToElementAndPerformElementClickUsingActions(WebElement element) {
		try {
		logger.info("<== In scrollToElementAndPerformElementClickUsingActions method ==>");
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.scrollToElement(element).click().perform();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	public static void performJavaScriptClick(WebElement element) {
		try {
		logger.info("<== In performJavaScriptClick method ==>");
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].click();", element);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
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
	
	public static void setBrowserSize() {
		try {
		logger.info("<== In setBrowserSize method ==>");
		CustomWebDriverManager.getDriver().manage().window().setSize(new Dimension(768, 1024));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
}