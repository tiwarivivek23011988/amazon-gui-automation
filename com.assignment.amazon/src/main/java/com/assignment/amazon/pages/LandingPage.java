package com.assignment.amazon.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assignment.amazon.utilities.DriverManager;

public class LandingPage {
	
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
		DriverManager.waitForElementClickabilityUsingFluentWait(categoryDropDownById);
		DriverManager.clickOnWebElement(categoryDropDownById);
		DriverManager.waitForElementsVisibilityUsingFluentWait(searchWebElement);
		for(WebElement element: searchWebElement) {
			if(element.getText().equalsIgnoreCase(dropDownValue)) {
				DriverManager.waitForElementClickabilityUsingFluentWait(element);
				DriverManager.clickOnWebElement(element);
				
				System.out.println("categoryDropDown element text is => " + categoryDropDownByXPath.getText());
				break;
			}
		}
	}
	
	public void inputTextInSearchBox(String inputText) {
		searchBox.clear();
		DriverManager.clickOnWebElement(searchBox);
		searchBox.sendKeys(inputText);
	}
	
	public List<String> storeAutoCompleteSuggestions() {
		DriverManager.waitForElementsVisibilityUsingFluentWait(autoCompleteSuggestions);
		for(WebElement element: autoCompleteSuggestions) {
			DriverManager.waitForElementToBeClickable(element);
		}
		return autoCompleteSuggestions.stream().map(x -> x.getText()).collect(Collectors.toList());
	}
	
	public WebElement returnElementMatchingAutoSuggestedText(String inputText) {
		DriverManager.waitUntilVisibilityOfAllElementsLocated(autoCompleteSuggestions);
		for(WebElement element: autoCompleteSuggestions) {
			 if(element.getText().equalsIgnoreCase(inputText.toLowerCase()) ||
			 element.getText().toLowerCase().contains(inputText.toLowerCase())) { 
				 return element;
			}
		}
		return null;
	}

	public boolean checkForPresenceOfAutoCompleteSuggestion(String searchText) {
		DriverManager.waitForAllAjaxCallsToCompleteUsingFluentWait(searchBoxAjax);
		for(WebElement element: autoCompleteSuggestions) {
			DriverManager.waitForElementToBeClickable(element);
				if(element.getText().equalsIgnoreCase(searchText) || element.getText().toLowerCase().contains(searchText.toLowerCase())) {
					return true;
			}
		}
		return false;
	}
	
	public void clickOnFirstResultFromResultsCatalog(String searchText) {
		for(WebElement element:listOfSearchedProducts) {
			String formattedString=element.getText().replaceAll("[^A-Za-z0-9 ]+", "");
			if(formattedString.equalsIgnoreCase(searchText) || formattedString.toLowerCase().contains(searchText.toLowerCase())) {
				element.click();
				break;
			}
		}
	}
	
	public List<String> storeResultsCatalogElementsText(String searchText) {
		DriverManager.waitUntilVisibilityOfAllElementsLocated(listOfSearchedProducts);
		return listOfSearchedProducts.stream().filter(x -> (x.getText().replaceAll("[^A-Za-z0-9 ]+", "").equalsIgnoreCase(searchText) || x.getText().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase().contains(searchText.toLowerCase()))).map(x -> x.getText().replaceAll("[^A-Za-z0-9 ]+", "")).collect(Collectors.toList());
	}
	
	public String getProductNameFromTitle() {
		DriverManager.waitForElementToBeVisible(productTitle);
		return productTitle.getText().replaceAll("[^A-Za-z0-9 ]+", "");
	}

}