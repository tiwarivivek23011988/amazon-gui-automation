/**
 *@author Vivek Tiwari 
 * 
 */

package com.assignment.amazon.stepdefinitions;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.pages.ProductPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * {@summary}
 * 
 * The ProductSearchStepDefinition Class
 * 
 * This class is used to implement product page step definitions.
 * 
 * @see ProductSearchStepDefinition
 * 
 */
public class ProductSearchStepDefinition {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ProductSearchStepDefinition.class);
	
	/**  Private web-driver object. */
	private WebDriver driver = CustomWebDriverManager.getDriver();
	
	/** The LandingPage class object. */
	private ProductPage productPage = new ProductPage(driver);
	
	/** The amazon search step definition reference */
	private AmazonSearchStepDefinition amazonSearchStepDef = new AmazonSearchStepDefinition();
	
	/**
	 * User navigates to amazon page.
	 *
	 * @param dataTable the data table
	 */
	
	@Given("User navigates to product specification page") 
	public void userNavigatesToProductSpecificationPage(DataTable dataTable){
		logger.debug("*******In userNavigatesToProductSpecificationPage function*******");
		try {
			List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		    String categoryType = null;
		    String searchText = null;
		    for (Map<String, String> row : data) {
		    	categoryType = row.get("dropdown_value");
		        searchText = row.get("search_text");
		        logger.info("categoryType: " + categoryType + ", searchText: " + searchText);
		    }
			amazonSearchStepDef.userNavigatesToAmazonPage();
			amazonSearchStepDef.userSelectsCategoryValueAndTypesProductName(categoryType, searchText);
			amazonSearchStepDef.userClicksOnAutoCompleteSuggestionMatchingProductName(searchText);
			amazonSearchStepDef.userClicksOnTheProductFromResultingProductCatalogList(searchText);
			amazonSearchStepDef.userValidatesThatProductSpecificationPageOpensInNewTab(searchText);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User clicks on visit apple store link.
	 */
	@When("User clicks on visit apple store link")
	public void userClicksOnVisitAppleStoreLink() {
		logger.debug("*******In userClicksOnVisitAppleStoreLink function*******");
		try {
			Assert.assertTrue(productPage.clickOnVisitAppleStoreLink());
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User clicks on product type link.
	 *
	 * @param productType the product type
	 */
	@And("User clicks on {string} link")
	public void userClicksOnProductTypeLink(String productType) {
		logger.debug("*******In userClicksOnProductTypeLink function*******");
		try {
			Assert.assertTrue(productPage.clickOnAppleWatchLink(productType));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User selects product name.
	 *
	 * @param productName the product name
	 * @param productType the product type
	 */
	@And("User selects {string} from {string}")
	public void userSelectsProductName(String productName, String productType) {
		logger.debug("*******In userSelectsProductName function*******");
		try {
			Assert.assertTrue(productPage.clickOnProductSublistElement(productType, productName));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User validates product name on apple store page.
	 *
	 * @param productName the product name
	 */
	@Then("User validates {string} on apple store page")
	public void userValidatesProductNameOnAppleStorePage(String productName) {
		logger.debug("*******In userValidatesProductNameOnAppleStorePage function*******");
		try {
			Assert.assertTrue(productPage.verifyProductSubTypeText(productName));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User validates quick look presence and clicks on it.
	 *
	 * @param productName the product name
	 */
	@Then("User validates quick look presence for {string} and clicks on it")
	public void userValidatesQuickLookPresenceAndClicksOnIt(String productName) {
		logger.debug("*******In userValidatesQuickLookPresenceAndClicksOnIt function*******");
		try {
			Assert.assertTrue(productPage.verifyQuickLookAndClickOnIt(productName));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
	
	/**
	 * User performs mouse hover on the product and vlidates magnified product image presence.
	 *
	 * @param productName the product name
	 */
	@Then("User performs mouse hover on the {string} and validates presence of products magnified image")
	public void userPerformsMouseHoverOnTheProductAndVlidatesMagnifiedProductImagePresence(String productName) {
		logger.debug("*******In userPerformsMouseHoverOnTheProductAndVlidatesMagnifiedProductImagePresence function*******");
		try {
			Assert.assertTrue(productPage.mouseHoverOnImageAndVerifyMagnifiedImage(productName));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	}
}
