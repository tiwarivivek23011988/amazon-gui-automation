package com.assignment.amazon.stepdefinitions;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.pages.LandingPage;
import com.assignment.amazon.utilities.DriverManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonSearchStepDefinition {
	
	private static final Logger logger = LogManager.getLogger(AmazonSearchStepDefinition.class);
	
	private WebDriver driver = CustomWebDriverManager.getDriver();
	private final LandingPage landingPage = new LandingPage(driver);
	
	@Given("User navigates to amazon page")
	public void userNavigatesToAmazonPage() {
		logger.info("<= In userNavigatesToAmazonPage function => " +Thread.currentThread().getName());
		driver.get((String) DriverManager.hashMap.get("url"));
		
	}
	
	@When("User selects {string} from categories dropdown and types {string}")
	public void userSelectsCategoryValueAndTypesSearchString(String dropdownValue, String searchText) {
		logger.info("<= In userSelectsCategoryValueAndTypesSearchString function => ");
		landingPage.selectCategoryFromDropdown(dropdownValue);
		landingPage.inputTextInSearchBox(searchText);
	}
	
	@Then("User validates auto-complete suggestions align with the provided {string}")
	public void userValidatesAutoCompleteSuggestionsWithSearchText(String searchText) {
		try {
			logger.info("<= In userValidatesAutoCompleteSuggestionsWithSearchText function => ");
			List<String> list = landingPage.storeAutoCompleteSuggestions();
			for(String str: list) {
				if(!str.toLowerCase().contains(searchText.toLowerCase())) {
					Assert.assertTrue(false);
				}
			}
			Assert.assertTrue(true);
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
	
	@Then("User clicks on {string} from auto-complete option thus suggested")
	public void userClicksOnAutoCompleteSuggestionMatchingSearchText(String searchText) {
		logger.info("<= In userClicksOnAutoCompleteSuggestionMatchingSearchText function => ");
		Assert.assertTrue(landingPage.checkForPresenceOfAutoCompleteSuggestion(searchText));
		DriverManager.clickOnWebElement(landingPage.returnElementMatchingAutoSuggestedText(searchText));
		
	}
	
	@And("User validates that {string} search returns products catalog list")
	public void userValidatesPresenceOfProductResultsForTheSearchedProduct(String searchText) {
		logger.info("<= In userValidatesPresenceOfProductResultsForTheSearchedProduct function => ");
		Assert.assertTrue(landingPage.storeResultsCatalogElementsText(searchText).size()>0);
	}
	
	@Then("User clicks on the {string} product from resulting product-catalog list")
	public void userClicksOnTheProductFromResultingProductCatalogList(String searchText) {
		logger.info("<= In userClicksOnTheProductFromResultingProductCatalogList function => ");
		landingPage.clickOnFirstResultFromResultsCatalog(searchText);
	}
	
	@And("User validates that {string} specification page opens in new tab")
	public void userValidatesThatProductSpecificationPageOpensInNewTab(String searchText) {
		try {
			logger.info("<= In userValidatesThatProductSpecificationPageOpensInNewTab function => ");
			Set<String> set = DriverManager.getWindowHandles();
			Assert.assertTrue(set.size()>1);
			Iterator<String> itr = set.iterator();
			String parentWindowHandle = itr.next();
			String childWindowHandle = itr.next();
			CustomWebDriverManager.setDriver(DriverManager.switchToWindowHandle(childWindowHandle));
			Assert.assertTrue(landingPage.getProductNameFromTitle().toLowerCase().contains(searchText.toLowerCase()));
			driver.close();
			CustomWebDriverManager.setDriver(DriverManager.switchToWindowHandle(parentWindowHandle));
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
	}
}
