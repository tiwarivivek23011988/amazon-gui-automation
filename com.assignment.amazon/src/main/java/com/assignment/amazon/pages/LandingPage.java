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

public class LandingPage {
	
	private static final Logger logger = LogManager.getLogger(LandingPage.class);

	public LandingPage(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//select/option")
	List<WebElement> searchWebElement;
	
	@FindBy(id = "nav-search-dropdown-card")
	WebElement categoryDropDownById;
	
	@FindBy(xpath = "//div[@id='nav-search-dropdown-card']//select")
	WebElement categoryDropDownByXPath;
	
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	@FindBy(xpath="//div[contains(@id,'nav-flyout-searchAjax')]/descendant::div[@role='button']")
	List<WebElement> autoCompleteSuggestions;
	
	@FindBy(id="//div[contains(@id,'nav-flyout-searchAjax')]")
	WebElement searchBoxAjax;
	
	@FindBy(xpath="//span[contains(text(),'//span[contains(@class,'a-size-base-plus')]')]")
	List<WebElement> searchedProductList;
	
	@FindBy(xpath="//div[contains(@class,'a-section')]/a[@id='bylineInfo']")
	WebElement visitAppleStore;
	
	@FindBy(xpath="//ul[contains(@class,'Navigation__navList')]/li")
	List<WebElement> appleStoreSearchResults;
	
	@FindBy(xpath="//h2[contains(@class,'a-size-mini')]//span")
	List<WebElement> listOfSearchedProducts;
	
	@FindBy(xpath="//h1[@id='title']/span")
	WebElement productTitle;
	
	
	public void selectCategoryFromDropdown(String dropDownValue) {
		try {
		logger.info("<= In selectCategoryFromDropdown function =>");
		WebDriverUtilities.waitForElementClickabilityUsingFluentWait(categoryDropDownById);
		WebDriverUtilities.clickOnWebElement(categoryDropDownById);
		WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(searchWebElement);
		for(WebElement element: searchWebElement) {
			if(element.getText().equalsIgnoreCase(dropDownValue)) {
				WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
				WebDriverUtilities.clickOnWebElement(element);
				
				System.out.println("categoryDropDown element text is => " + categoryDropDownByXPath.getText());
				break;
			}
		}
	  } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
	  }
	}
	
	public void inputTextInSearchBox(String inputText) {
	try {
		logger.info("<= In inputTextInSearchBox function =>");
		searchBox.clear();
		WebDriverUtilities.clickOnWebElement(searchBox);
		searchBox.sendKeys(inputText);
	 } catch(Exception e) {
		  ExceptionHandler.throwsException(e);
	  }
	}
	
	public List<String> storeAutoCompleteSuggestions() {
		try {
			logger.info("<= In storeAutoCompleteSuggestions function =>");
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				WebDriverUtilities.waitForElementToBeClickable(element);
			}
			return autoCompleteSuggestions.stream().map(x -> x.getText()).collect(Collectors.toList());
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		 }
		return null;
	}
	
	public WebElement returnElementMatchingAutoSuggestedText(String inputText) {
		try {
			logger.info("<= In returnElementMatchingAutoSuggestedText function =>");
			WebDriverUtilities.waitUntilVisibilityOfAllElementsLocated(autoCompleteSuggestions);
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

	public boolean checkForPresenceOfAutoCompleteSuggestion(String searchText) {
		try {
			logger.info("<= In checkForPresenceOfAutoCompleteSuggestion function =>");
			WebDriverUtilities.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
			WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
			for(WebElement element: autoCompleteSuggestions) {
				WebDriverUtilities.waitForElementToBeClickable(element);
					if(element.getText().equalsIgnoreCase(searchText) || element.getText().toLowerCase().contains(searchText.toLowerCase())) {
						return true;
				}
			}
		 } catch(Exception e) {
			  ExceptionHandler.throwsException(e);
		}
		return false;
	}
	
	public void clickOnFirstResultFromResultsCatalog(String searchText) {
		try {
			logger.info("<= In clickOnFirstResultFromResultsCatalog function =>");
			for(WebElement element:listOfSearchedProducts) {
				String formattedString=element.getText().replaceAll("[^A-Za-z0-9 ]+", "");
				if(formattedString.equalsIgnoreCase(searchText) || formattedString.toLowerCase().contains(searchText.toLowerCase())) {
					element.click();
					break;
				}
			}
	 	} catch(Exception e) {
		  ExceptionHandler.throwsException(e);
	  }
	}
	
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