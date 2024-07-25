package com.assignment.amazon.utilities;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.assignment.amazon.drivermanager.EdgeDriverManager;
import com.assignment.amazon.drivermanager.FirefoxDriverManager;
import com.assignment.amazon.drivermanager.RemoteDriverManager;
import com.assignment.amazon.drivermanager.SafariDriverManager;
import com.assignment.amazon.drivermanager.CustomWebDriverManager;


public final class DriverManager {
	public static JsonParser jsonParser = new JsonParser();
	public static String filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
	public static HashMap<String, ?> hashMap;
	public static volatile List<?> browserNames;
	public static ThreadLocal<String> browserName=new ThreadLocal<>();
	public static ThreadLocal<String> browserVersion=new ThreadLocal<>();;
	static {
		jsonParser = new JsonParser();
		filePath = FileSearchUtility.searchFile("src/test/resources", "data.json");
		hashMap = jsonParser.parseJson(filePath);
		browserNames=(List<?>) hashMap.get("browserName");
	}
	
	public static synchronized void browserCounter() {
		System.out.println("Inside browserCounter method => " +Thread.currentThread().getName());
		int counter = ParallelCounter.getCounter();
		if(counter < DriverManager.browserNames.size()) {
			browserName.set((String) DriverManager.browserNames.get(counter));
			System.out.println("Counter Value Is => " +counter);
			System.out.println("Browser Name Is => " +browserName.get());
			ParallelCounter.incrementCounter();
		} else {
			ParallelCounter.resetCounter();
		}
	}
	
	public static synchronized int decrementCounter() {
		return ParallelCounter.decrementCounter();
	}
	
	public static synchronized int getCounter() {	
		return ParallelCounter.getCounter();
	}
	
	public static synchronized int getScenarioCounter() {
		return ParallelCounter.scenarioCounter.get();
	}
	
	public static synchronized void resetCounter() {
		ParallelCounter.resetCounter();
	}
	
	public synchronized WebDriver getDriver() {
			System.out.println("Inside getDriver method => " +Thread.currentThread().getName());
			WebDriver driver = switch(browserName.get()) {
			case "chrome" -> new ChromeDriverManager().getDriver();
			case "firefox" -> new FirefoxDriverManager().getDriver();
			case "edge" -> new EdgeDriverManager().getDriver();
			case "safari" -> new SafariDriverManager().getDriver();
			case "remote" -> new RemoteDriverManager().getDriver();
			default -> throw new IllegalArgumentException("Browser type not supported: " + browserName.get());
		};
		return driver;
	}
	
	public static void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitUntilVisibilityOfAllElementsLocated(List<WebElement> element) {
		WebDriverWait wait = new WebDriverWait(CustomWebDriverManager.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
	public static void clickOnWebElement(WebElement element) {
		waitForElementToBeClickable(element);
		element.click();
	}
	
	public static Set<String> getWindowHandles() {
		if(CustomWebDriverManager.getDriver() != null)
			return CustomWebDriverManager.getDriver().getWindowHandles();
		return null;
	}
	
	public static WebDriver switchToWindowHandle(String handleId) {
		if(CustomWebDriverManager.getDriver() != null)
			return CustomWebDriverManager.getDriver().switchTo().window(handleId);
		return null;
	}
	
	public static void waitForElementVisibilityUsingFluentWait(WebElement element) {
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
          

        wait.until(ExpectedConditions.visibilityOf(element));

	}
	
	public static void waitForElementClickabilityUsingFluentWait(WebElement element) {
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
          

        wait.until(ExpectedConditions.elementToBeClickable(element));

	}
	
	public static void waitForAllAjaxCallsToCompleteUsingFluentWait(WebElement element) {
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

	}
	
	public static void waitForElementsVisibilityUsingFluentWait(List<WebElement> element) {
		Wait<WebDriver> wait = new FluentWait<>(CustomWebDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        wait.until(ExpectedConditions.visibilityOfAllElements(element));

	}
	
	public static void scrollToView(WebElement element) {
        ((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public static void scrollToViewAndClick(WebElement element) {
		//Smooth Scrolling
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' }); arguments[0].click();", element);
	}
	
	public static void maximizeBrowserWindow() {
		CustomWebDriverManager.getDriver().manage().window().maximize();
	}
	
	public static void moveToElementAndPerformElementClickUsingActions(WebElement element) {
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.moveToElement(element).click().perform();
	}
	
	public static void scrollToElementAndPerformElementClickUsingActions(WebElement element) {
		Actions actions = new Actions(CustomWebDriverManager.getDriver());
		actions.scrollToElement(element).click().perform();
	}
	
	public static void performJavaScriptClick(WebElement element) {
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript("arguments[0].click();", element);
	}
	
	public static void scrollToElementHeight(WebElement element) {
		((JavascriptExecutor) CustomWebDriverManager.getDriver()).executeScript(
	            "arguments[0].scrollTop = arguments[0].scrollHeight;",
	            element);
	}
	
	public static void setBrowserSize() {
		CustomWebDriverManager.getDriver().manage().window().setSize(new Dimension(768, 1024));
	}
}