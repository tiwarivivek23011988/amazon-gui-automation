/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.stepdefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.pages.LandingPage;
import com.assignment.amazon.utilities.WebDriverUtilities;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * {@summary}
 * 
 * The AmazonSearchStepDefinition Class
 * 
 * This is a step definition class of amazon login page
 * feature file implementation @amazon_landing_page.feature
 * 
 * @see AmazonSearchStepDefinition
 * 
 */
public class AmazonSearchStepDefinition {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(AmazonSearchStepDefinition.class);
	
	/** Private web-driver object */
	private WebDriver driver = CustomWebDriverManager.getDriver();
	
	/** The LandingPage class object. */
	private LandingPage landingPage = new LandingPage(driver);
	
	/**
	 * User navigates to amazon page.
	 */
	@Given("User navigates to amazon page")
	public synchronized void userNavigatesToAmazonPage() {
		logger.debug("*******In userNavigatesToAmazonPage function*******" +"\n"+Thread.currentThread().getName());
		try {
			String url = (String) WebDriverUtilities.hashMap.get("url");
			driver.get(url);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	/**
	 * User selects category value and types product name.
	 *
	 * @param dropdownValue - the category drop-down value
	 * 
	 * @param productName - the product name to be provided in search box
	 */
	@When("User selects {string} from categories dropdown and types {string}")
	public synchronized void userSelectsCategoryValueAndTypesProductName(String dropdownValue, String productName) {
		logger.debug("*******In userSelectsCategoryValueAndTypesProductName function*******");
		boolean isElementSelected = landingPage.selectCategoryFromDropdown(dropdownValue);
		Assert.assertTrue(isElementSelected);
		Assert.assertTrue(landingPage.inputTextInSearchBox(productName));
		
	}
	
	/**
	 * User validates auto complete suggestions with product name.
	 *
	 * @param productName - the product name
	 */
	@Then("User validates auto-complete suggestions align with the provided {string}")
	public synchronized void userValidatesAutoCompleteSuggestionsWithProductName(String productName) {
		logger.debug("*******In userValidatesAutoCompleteSuggestionsWithProductName function*******");
		boolean flag = landingPage.checkAutoCompleteSuggestions(productName);
		Assert.assertTrue(flag);
	}
	
	/**
	 * User clicks on auto complete suggestion matching productName.
	 *
	 * @param productName - the product name
	 */
	@Then("User clicks on {string} from auto-complete option thus suggested")
	public synchronized void userClicksOnAutoCompleteSuggestionMatchingProductName(String productName) {
		logger.debug("*******In userClicksOnAutoCompleteSuggestionMatchingProductName function*******");
		Assert.assertTrue(landingPage.clickElementMatchingAutoSuggestedText(productName));
	}
	
	/**
	 * User validates presence of product results for the searched product.
	 *
	 * @param productName - the product name
	 */
	@And("User validates that {string} search returns products catalog list")
	public synchronized void userValidatesPresenceOfProductResultsForTheSearchedProduct(String productName) {
		logger.debug("*******In userValidatesPresenceOfProductResultsForTheSearchedProduct function*******");
		Assert.assertTrue(landingPage.storeResultsCatalogElementsText(productName).size()>0);
	}
	
	/**
	 * User clicks on the product from resulting product catalog list.
	 *
	 * @param productName - the product name
	 */
	@Then("User clicks on the {string} product from resulting product-catalog list")
	public synchronized void userClicksOnTheProductFromResultingProductCatalogList(String productName) {
		logger.debug("*******In userClicksOnTheProductFromResultingProductCatalogList function*******");
		Assert.assertTrue(landingPage.clickOnFirstResultFromResultsCatalog(productName));
		
	}
	
	/**
	 * User validates that product specification page opens in new tab.
	 *
	 * @param productName - the product name
	 */
	@And("User validates that {string} specification page opens in new tab")
	public synchronized void userValidatesThatProductSpecificationPageOpensInNewTab(String productName) {
	
		logger.debug("*******In userValidatesThatProductSpecificationPageOpensInNewTab function*******");
		Assert.assertTrue(landingPage.getProductNameFromTitle().toLowerCase().contains(productName.toLowerCase()));

	}
}
