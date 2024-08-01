/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;

/**
 * {@summary}
 * 
 * The LandingPage Class
 * 
 * This class handles Amazon landing page objects and web-elements.
 * It also handle functions performed on amazon web page.
 *
 * @see LandingPage
 * 
 */
public class LandingPage {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LandingPage.class);

	/**
	 * Instantiates a new landing page.
	 *
	 * @param driver - the web-driver object
	 */
	public LandingPage(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
	}
	
	/** The search web element. */
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//child::select[@id='searchDropdownBox']/option")
	List<WebElement> searchWebElement;
	
	/** The search web element. */
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//child::select[@id='searchDropdownBox']")
	WebElement selectWebElement;
	
	/**Category drop down selected text web-element */
	@FindBy(id="nav-search-label-id")
	WebElement categoryTextToVerify;
	
	/** The overlay element */
	@FindBy(xpath="//*[@id='gw-desktop-herotator']/div/div")
	WebElement overLayElement;
	
	/** The category drop-down by id. */
	@FindBy(id = "nav-search-dropdown-card")
	WebElement categoryDropDownById;
	
	/** The search text box. */
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	@FindBy(xpath="//div[@id='nav-flyout-searchAjax']//div[@role='button']")
	List<WebElement> autoCompleteSuggestions;
	
	@FindBy(id="nav-flyout-searchAjax")
	WebElement searchBoxAjax;
	
	/** The searched product list. */
	@FindBy(xpath="//span[contains(text(),'//span[contains(@class,'a-size-base-plus')]')]")
	List<WebElement> searchedProductList;
	
	/** The apple store search results. */
	@FindBy(xpath="//ul[contains(@class,'Navigation__navList')]/li")
	List<WebElement> appleStoreSearchResults;
	
	/** The list of searched products. */
	@FindBy(xpath="//h2[contains(@class,'a-size-mini')]//span")
	List<WebElement> listOfSearchedProducts;
	
	/** The product title. */
	@FindBy(xpath="//h1[@id='title']/span")
	WebElement productTitle;
	
	
	/**
	 * Select category from drop-down.
	 *
	 * @param dropDownValue - the drop down value
	 * @return true, if successful
	 * 
	 */
	public synchronized boolean selectCategoryFromDropdown(String dropDownValue) {
		try {
		logger.debug("*******In selectCategoryFromDropdown function*******");
		helperFunctionToHandleCategoryDropDown();
		int index=0;
		for(WebElement element: searchWebElement) {
		WebDriverUtilities.scrollToView(element);
		WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
		boolean flag=false;
		if(element.getText().equals(dropDownValue)) {
			flag=helperFunctionToVerifyCategoryDropDownValue(element, categoryTextToVerify, index, dropDownValue);
			if(flag) {
				return true;
			}
		}
		if(!flag) {
			if(helperFunctionToVerifyCategoryDropDownValue(element, categoryTextToVerify, index, dropDownValue)) {
				return true;
			}
		}
		WebDriverUtilities.keyPressEventUsingActionsClickAndHold(Keys.DOWN);
		index++;
		}
	  } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  throw e;
	  }
		return false;
	}
	
	/**
	 * Select category from drop-down.
	 *
	 * @param currentElement - current Element.
	 * @param elementToVerify - Element to verify
	 * @param optionIndex - category drop down element index
	 * @param expectedValue - expected category type
	 * 
	 * @return true, if successful
	 * 
	 */
	private boolean helperFunctionToVerifyCategoryDropDownValue(WebElement currentElement, WebElement elementToVerify, int optionIndex, String expectedValue) {
		try {
		helperFunctionToHandleCategoryDropDown();
		for(WebElement element: searchWebElement) {
			if(!element.getText().equals(currentElement.getText())) {
				WebDriverUtilities.selectByIndexUsingJavascript(currentElement, optionIndex);
				WebDriverUtilities.waitForElementVisibilityUsingFluentWait(elementToVerify);
				WebDriverUtilities.scrollToView(elementToVerify);
			}
			if(elementToVerify.getText().equals(expectedValue)) {
				WebDriverUtilities.performJavaScriptClick(currentElement);
				return true;
			}
			WebDriverUtilities.keyPressEventUsingActionsClickAndHold(Keys.DOWN);
		   }
		
			return false;
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	
	private void helperFunctionToHandleCategoryDropDown() {
		try {
		WebDriverUtilities.scrollToView(categoryDropDownById);
		WebDriverUtilities.moveToElementAndPerformElementClickUsingActions(categoryDropDownById);
		WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(categoryDropDownById);
		WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(searchWebElement);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * Input text in search box.
	 *
	 * @param inputText - the input text
	 * @return true, if successful
	 */
	public boolean inputTextInSearchBox(String inputText) {
	try {
		logger.debug("*******In inputTextInSearchBox function*******");
		WebDriverUtilities.waitForElementVisibilityUsingFluentWait(searchBox);
		WebDriverUtilities.scrollToView(searchBox);
		searchBox.clear();
		searchBox.sendKeys(inputText);
		return true;
	 } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  throw e;
	  }
	}
	
	/**
	 * check auto complete suggestions.
	 *
	 * @return - the list
	 */
	public boolean checkAutoCompleteSuggestions(String productName) {
		try {
			logger.debug("*******In storeAutoCompleteSuggestions function*******");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element:autoCompleteSuggestions) {
				WebDriverUtilities.moveToElementUsingActions(element);
				WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
				String attributeTextFromAutoCompleteSearchBox=element.getAttribute("aria-label").toString();
				if(attributeTextFromAutoCompleteSearchBox.length()==productName.length()) {
					if(!attributeTextFromAutoCompleteSearchBox.equalsIgnoreCase(productName)) {
						return false;
					}
				}else if(!attributeTextFromAutoCompleteSearchBox.toLowerCase().contains(productName.toLowerCase())) {
					return false;
				}
			}
			return true;
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
			  throw e;
		 }
	}
	
	/**
	 * Return element matching auto suggested text.
	 *
	 * @param inputText - the input text
	 * @return the web element
	 */
	public boolean clickElementMatchingAutoSuggestedText(String inputText) {
		try {
			logger.debug("*******In returnElementMatchingAutoSuggestedText function*******");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBox);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				WebDriverUtilities.waitForElementVisibilityUsingFluentWait(element);
				WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
				 if(element.getText().equalsIgnoreCase(inputText.toLowerCase()) ||
				 element.getText().toLowerCase().contains(inputText.toLowerCase())) {
					 WebDriverUtilities.performJavaScriptClick(element);
					 return true;
				}
			}
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
			  throw e;
		}
		return false;
	}

	/**
	 * Check for presence of auto complete suggestion.
	 *
	 * @param searchText - the search text
	 * @return true, if successful
	 */
	public boolean checkForPresenceOfAutoCompleteSuggestion(String searchText) {
		try {
			logger.debug("*******In checkForPresenceOfAutoCompleteSuggestion function*******");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
				if(element.getText().equalsIgnoreCase(searchText) || element.getText().toLowerCase().contains(searchText.toLowerCase())) {
						return true;
				}
			}
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
			  throw e;
		}
		return false;
	}
	
	/**
	 * Click on first result from results catalog.
	 *
	 * @param searchText - the search text
	 * @return true, if successful
	 */
	public boolean clickOnFirstResultFromResultsCatalog(String searchText) {
		try {
			logger.debug("*******In clickOnFirstResultFromResultsCatalog function*******");
			for(WebElement element:listOfSearchedProducts) {
				String formattedString=element.getText().replaceAll("[^A-Za-z0-9 ]+", "");
				if(formattedString.equalsIgnoreCase(searchText) || formattedString.toLowerCase().contains(searchText.toLowerCase())) {
					element.click();
					return true;
				}
			}
	 	} catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  throw e;
	  }
		return false;
	}
	
	/**
	 * Store results catalog elements text.
	 *
	 * @param searchText - the search text
	 * @return the list
	 */
	public List<String> storeResultsCatalogElementsText(String searchText) {
		try {
			logger.debug("*******In storeResultsCatalogElementsText function*******");
			WebDriverUtilities.waitUntilVisibilityOfAllElementsLocated(listOfSearchedProducts);
			return listOfSearchedProducts.stream().filter(x -> (x.getText().replaceAll("[^A-Za-z0-9 ]+", "").equalsIgnoreCase(searchText) || x.getText().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase().contains(searchText.toLowerCase()))).map(x -> x.getText().replaceAll("[^A-Za-z0-9 ]+", "")).collect(Collectors.toList());
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
			  throw e;
		}
	}
	
	/**
	 * Gets the product name from title.
	 *
	 * @return the product name from title
	 */
	public String getProductNameFromTitle() {
		try {
			logger.debug("*******In getProductNameFromTitle function*******");
			WebDriverUtilities.switchToWindow();
			WebDriverUtilities.waitForElementToBeVisible(productTitle);
			WebDriverUtilities.scrollToView(productTitle);
			return productTitle.getText().replaceAll("[^A-Za-z0-9 ]+", "");
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
			  throw e;
		}
	}

}