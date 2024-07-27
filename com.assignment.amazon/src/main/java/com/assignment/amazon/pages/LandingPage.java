/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//select/option")
	List<WebElement> searchWebElement;
	
	/** The category drop-down by id. */
	@FindBy(id = "searchDropdownBox")
	WebElement categoryDropDownById;
	
	/** The category drop-down by X path. */
	@FindBy(xpath = "//div[@id='nav-search']/form[@id='nav-search-bar-form']/div[@class='nav-left']/div")
	WebElement categoryDropDownByXPath;
	
	/** The search text box. */
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	/** The auto-complete suggestions. */
	@FindBy(xpath="//div[contains(@id,'nav-flyout-searchAjax')]/descendant::div[@role='button']")
	List<WebElement> autoCompleteSuggestions;
	
	/** The search text box ajax location. */
	@FindBy(id="//div[contains(@id,'nav-flyout-searchAjax')]")
	WebElement searchBoxAjax;
	
	/** The searched product list. */
	@FindBy(xpath="//span[contains(text(),'//span[contains(@class,'a-size-base-plus')]')]")
	List<WebElement> searchedProductList;
	
	/** The visit apple store. */
	@FindBy(xpath="//div[contains(@class,'a-section')]/a[@id='bylineInfo']")
	WebElement visitAppleStore;
	
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
	 * Select category from dropdown.
	 *
	 * @param dropDownValue - the drop down value
	 * @return true, if successful
	 */
	public boolean selectCategoryFromDropdown(String dropDownValue) {
		try {
		logger.info("<= In selectCategoryFromDropdown function =>");
		WebDriverUtilities.waitForElementClickabilityUsingFluentWait(categoryDropDownByXPath);
		WebDriverUtilities.scrollToView(categoryDropDownByXPath);
		WebDriverUtilities.moveToElementAndPerformElementClickUsingActions(categoryDropDownByXPath);
		WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(categoryDropDownById);
		WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(searchWebElement);
		 for(WebElement element: searchWebElement) {
		 WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
		 if(element.getText().equalsIgnoreCase(dropDownValue)) {
			 WebDriverUtilities.performJavaScriptClick(element);
			 return true; 
		 	} 
		 }
	  } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  return false;
	  }
		return false;
	}
	
	/**
	 * Input text in search box.
	 *
	 * @param inputText - the input text
	 * @return true, if successful
	 */
	public boolean inputTextInSearchBox(String inputText) {
	try {
		logger.info("<= In inputTextInSearchBox function =>");
		WebDriverUtilities.waitForElementClickabilityUsingFluentWait(searchBox);
		searchBox.clear();
		WebDriverUtilities.clickOnWebElement(searchBox);
		searchBox.sendKeys(inputText);
		return true;
	 } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  return false;
	  }
	}
	
	/**
	 * Store auto complete suggestions.
	 *
	 * @return - the list
	 */
	public List<String> storeAutoCompleteSuggestions() {
		try {
			logger.info("<= In storeAutoCompleteSuggestions function =>");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				 WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
			}
			return autoCompleteSuggestions.stream().map(x -> x.getText()).collect(Collectors.toList());
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		 }
		return null;
	}
	
	/**
	 * Return element matching auto suggested text.
	 *
	 * @param inputText - the input text
	 * @return the web element
	 */
	public WebElement returnElementMatchingAutoSuggestedText(String inputText) {
		try {
			logger.info("<= In returnElementMatchingAutoSuggestedText function =>");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				 if(element.getText().equalsIgnoreCase(inputText.toLowerCase()) ||
				 element.getText().toLowerCase().contains(inputText.toLowerCase())) { 
					 return element;
				}
			}
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		}
		return null;
	}

	/**
	 * Check for presence of auto complete suggestion.
	 *
	 * @param searchText - the search text
	 * @return true, if successful
	 */
	public boolean checkForPresenceOfAutoCompleteSuggestion(String searchText) {
		try {
			logger.info("<= In checkForPresenceOfAutoCompleteSuggestion function =>");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
					if(element.getText().equalsIgnoreCase(searchText) || element.getText().toLowerCase().contains(searchText.toLowerCase())) {
						return true;
				}
			}
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
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
			logger.info("<= In clickOnFirstResultFromResultsCatalog function =>");
			for(WebElement element:listOfSearchedProducts) {
				String formattedString=element.getText().replaceAll("[^A-Za-z0-9 ]+", "");
				if(formattedString.equalsIgnoreCase(searchText) || formattedString.toLowerCase().contains(searchText.toLowerCase())) {
					element.click();
					return true;
				}
			}
	 	} catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  return false;
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
			logger.info("<= In storeResultsCatalogElementsText function =>");
			WebDriverUtilities.waitUntilVisibilityOfAllElementsLocated(listOfSearchedProducts);
			return listOfSearchedProducts.stream().filter(x -> (x.getText().replaceAll("[^A-Za-z0-9 ]+", "").equalsIgnoreCase(searchText) || x.getText().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase().contains(searchText.toLowerCase()))).map(x -> x.getText().replaceAll("[^A-Za-z0-9 ]+", "")).collect(Collectors.toList());
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		}
		return null;
	}
	
	/**
	 * Gets the product name from title.
	 *
	 * @return the product name from title
	 */
	public String getProductNameFromTitle() {
		try {
			logger.info("<= In getProductNameFromTitle function =>");
			WebDriverUtilities.waitForElementToBeVisible(productTitle);
			return productTitle.getText().replaceAll("[^A-Za-z0-9 ]+", "");
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		}
		return null;
	}

}