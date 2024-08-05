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
	
	@FindBy(id="nav-search-bar-form")
	WebElement formId;
	
	/** The search web element. */
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//child::select[@id='searchDropdownBox']/option")
	List<WebElement> searchWebElement;
	
	/** The search web element. */
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//child::select[@id='searchDropdownBox']")
	WebElement selectWebElement;
	
	/**Category drop down selected text web-element */
	@FindBy(xpath="//div[@data-value='search-alias=aps']/span")
	WebElement categoryTextToVerify;
	
	/** The overlay element */
	@FindBy(xpath="//*[@id='gw-desktop-herotator']/div/div")
	WebElement overLayElement;
	
	/** The category drop-down by id. */
	@FindBy(xpath = "//form[@id='nav-search-bar-form']//div[contains(@class,'nav-search-scope')]")
	WebElement categoryDropDownById;
	
	/** The search text box. */
	@FindBy(css="#twotabsearchtextbox")
	WebElement searchBox;
	
	@FindBy(xpath="//div[@class='two-pane-results-container']")
	WebElement autoCompleteBox;
	
	@FindBy(css="#nav-flyout-searchAjax .autocomplete-results-container div.s-suggestion-container > div[role='button']")
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
		WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(formId);
		int i=0;
		helperFunctionToHandleCategoryDropDown();
		List<String> elementTexts = searchWebElement.stream().map(x -> x.getText()).collect(Collectors.toList());
		logger.info("Element Texts Are => "+elementTexts);
		logger.info("Element Texts Size Is => "+elementTexts.size());
		logger.info("Elements Size Is => "+searchWebElement.size());
		while(i<searchWebElement.size()) {
		WebDriverUtilities.keyPressEventUsingActionsChord(Keys.ARROW_DOWN);
		WebDriverUtilities.waitForElementVisibilityUsingFluentWait(searchWebElement.get(i));
		WebDriverUtilities.scrollToView(searchWebElement.get(i));
		WebDriverUtilities.waitForElementClickabilityUsingFluentWait(searchWebElement.get(i));
		if(elementTexts.get(i).equalsIgnoreCase(dropDownValue.trim()) && searchWebElement.get(i).getText().equalsIgnoreCase(dropDownValue.trim())) {
			logger.info("Index inside if is => "+i);
			if(!categoryTextToVerify.getText().equals(dropDownValue.trim())) {
				WebDriverUtilities.keyPressEventUsingActionsChord(Keys.ARROW_UP);
			}
			WebDriverUtilities.performJavaScriptClick(searchWebElement.get(i));
			WebDriverUtilities.waitForElementVisibilityUsingFluentWait(categoryTextToVerify);
			logger.info("Text to verify is => "+searchWebElement.get(i).getText());
			logger.info("Extracted Text is => "+elementTexts.get(i));
			logger.info("Text verified is => "+categoryTextToVerify.getText());
			if(!(categoryTextToVerify.getText().equalsIgnoreCase(dropDownValue.trim())
					&& searchWebElement.get(i).getText().equalsIgnoreCase(dropDownValue.trim()))) {
				logger.info("Text does not match => "+categoryTextToVerify.getText().equalsIgnoreCase(searchWebElement.get(i).getText()));
			} else {
				return true;
			}
		} else {
			logger.info("Text to verify inside else is => "+searchWebElement.get(i).getText());
			logger.info("Index inside else is => "+i);
		}
		i++;
		}
	  } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
		  throw e;
	  }
		return false;
	}
	
	private synchronized void helperFunctionToHandleCategoryDropDown() {
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
			searchBox.click();
			searchBox.clear();
			WebDriverUtilities.keyPressEventUsingActionsRelease(searchBox, inputText);
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
	public synchronized boolean checkAutoCompleteSuggestions(String productName) {
		try {
			logger.debug("*******In storeAutoCompleteSuggestions function*******");
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element:autoCompleteSuggestions) {
				try {
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
				} catch(Exception e) {
					continue;
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
	public synchronized boolean clickElementMatchingAutoSuggestedText(String inputText) {
	
			logger.debug("*******In returnElementMatchingAutoSuggestedText function*******");
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				try {
					WebDriverUtilities.waitUntilAttributeNotEmpty(element, "aria-label");
					 WebDriverUtilities.moveToElementUsingActions(element);
					 WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
					 if(element.getAttribute("aria-label").toString().equalsIgnoreCase(inputText.toLowerCase()) ||
						 element.getAttribute("aria-label").toString().toLowerCase().contains(inputText.toLowerCase())) {
						 WebDriverUtilities.keyPressEventUsingActionsRelease(element, Keys.chord(Keys.DOWN, Keys.ENTER));
						 return true;
					 }
				} catch(Exception e) {
					continue;
				}
			}
	
		return false;
	}

	/**
	 * Check for presence of auto complete suggestion.
	 *
	 * @param searchText - the search text
	 * @return true, if successful
	 */
	public synchronized boolean checkForPresenceOfAutoCompleteSuggestion(String searchText) {
			try {
			logger.debug("*******In checkForPresenceOfAutoCompleteSuggestion function*******");
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				try {
				WebDriverUtilities.scrollToView(element);
				WebDriverUtilities.moveToElementUsingActions(element);
				if(element.getAttribute("aria-label").toString().equalsIgnoreCase(searchText.trim()) || element.getAttribute("aria-label").toString().toLowerCase().contains(searchText.trim().toLowerCase())) {
						return true;
				}
				} catch(Exception e) {
					continue;
				}
			 }
			}catch(Exception e) {
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
	public synchronized boolean clickOnFirstResultFromResultsCatalog(String searchText) {
		try {
			logger.debug("*******In clickOnFirstResultFromResultsCatalog function*******");
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(listOfSearchedProducts);
			for(WebElement element:listOfSearchedProducts) {
				try {
					WebDriverUtilities.waitForElementVisibilityUsingFluentWait(element);
					WebDriverUtilities.moveToElementUsingActions(element);
					String formattedString=element.getText().replaceAll("[^A-Za-z0-9 ]+", "");
					if(formattedString.equalsIgnoreCase(searchText) || formattedString.toLowerCase().contains(searchText.toLowerCase())) {
						WebDriverUtilities.scrollToView(element);
						WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
						WebDriverUtilities.performJavaScriptClick(element);
						return true;
					}
				} catch(Exception e) {
					continue;
				}
	
			}
	 	
		return false;
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * Store results catalog elements text.
	 *
	 * @param searchText - the search text
	 * @return the list
	 */
	public synchronized List<String> storeResultsCatalogElementsText(String searchText) {
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
	public synchronized String getProductNameFromTitle() {
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