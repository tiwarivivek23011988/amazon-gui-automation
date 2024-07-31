/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.utilities.TextProcessingUtility;
import com.assignment.amazon.utilities.WebDriverUtilities;


/**
 * {@summary}
 * 
 * The ProductPage Class
 * 
 * This class caters locator values and locator specific actions
 * on product specification page.
 * 
 * @see - ProductPage
 * 
 */
public class ProductPage {
	
	/** The default logger. */
	private static final Logger logger = LogManager.getLogger(ProductPage.class);
	
	/**
	 * Instantiates a new product page.
	 *
	 * @param driver - the web-driver object
	 */
	public ProductPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	/** The more or category drop-down locator */
	@FindBy(xpath="//button[@data-click-type='OTHER']/span/span")
	WebElement moreOrCategory;
	
	/** The products type list from main page. */
	@FindBy(css="ul[class*='Navigation__navList']> li > a[tabindex='0'] > span")
	List<WebElement> productsTypeListFromMainPage;
	
	/** The product type list from side menu. */
	@FindBy(xpath="//div[@data-testid='navigation']//nav[contains(@class,'Navigation__navMenu')]//ul/descendant::a[@data-click-type='OTHER']//span")
	List<WebElement> productListFromSideMenu;

	/** The product type headers. */
	@FindBy(xpath="//div[@data-testid='navigation']/div/nav[contains(@class,'Navigation__navBar')]/descendant::a[@data-click-type='OTHER']")
	List<WebElement> productHeaders;
	
	/** All product types Sublist. */
	@FindBy(xpath="//li[contains(@class,'Navigation__hasChildren')]//div[@aria-label='Navigation List']//li//span")
	List<WebElement> allProductTypesSublist;
	
	/** All quick search button web elements which get displayed on mouse hover */
	@FindBy(xpath="//h3[contains(@class,'EditorialTileProduct')]/parent::div/following-sibling::button")
	List<WebElement> allQuickSearchButtonWebElements;
	
	/** The searched product header names for verification. */
	@FindBy(xpath="//h3[contains(@class,'EditorialTileProduct')]")
	List<WebElement> allElementsHeaderNameForVerification;
	
	/** The product to hover on to see magnified image */
	@FindBy(xpath="//div[contains(@class,'ProductShowcase__image-area') and contains(@class,'ProductShowcase__desktop')]//div[@data-index='0']//div[@aria-label='1 / 7']/img")
	WebElement productToHoverOn;
	
	/** The visit apple store. */
	@FindBy(xpath="//div[@id='titleSection']/parent::div/following-sibling::div//a")
	WebElement visitAppleStore;
	
	/** The product sub list from side menu. */
	@FindBy(css="div[class*='Navigation__isOpen'] > ul:nth-child(1) > li > a span")
	List<WebElement> productSubListFromSideMenu;
	
	/** The image magnifier view of product. */
	@FindBy(xpath="//div[contains(@class,'ProductShowcase__image-magnifier')]/img")
	WebElement imageMagnifierViewOfProduct;
	
	/**
	 * Click on visit apple store link.
	 *
	 * @return true, if successful
	 */
	public boolean clickOnVisitAppleStoreLink() {
		logger.debug("*****In clickOnVisitAppleStoreLink function*****");
		WebDriverUtilities.waitForElementVisibilityUsingFluentWait(visitAppleStore);
		WebDriverUtilities.scrollToViewAndClick(visitAppleStore);
		return true;
	}
	
	/**
	 * Click on apple watch link.
	 *
	 * @param productType - the product type
	 * @return true, if successful
	 * 
	 */
	public boolean clickOnAppleWatchLink(String productType) {
		logger.debug("*****In clickOnAppleWatchLink function*****");
		boolean flag=false;
		for(WebElement element: productsTypeListFromMainPage) {
			try {
				WebDriverUtilities.waitForElementVisibilityUsingFluentWait(element);
				if(element.getText().equalsIgnoreCase(productType)) {
					WebDriverUtilities.scrollToView(element);
					WebDriverUtilities.waitForElementVisibilityUsingFluentWait(element);
					WebDriverUtilities.performJavaScriptClick(element);
					flag=true;
					break;
				}
			} catch(Exception e) {
				CustomWebDriverManager.getDriver().navigate().refresh();
				continue;
			}
		}
		if(!flag) {
			WebDriverUtilities.waitForElementVisibilityUsingFluentWait(moreOrCategory);
			if(moreOrCategory.isEnabled() && moreOrCategory.isDisplayed()) {
				if(moreOrCategory.getText().equalsIgnoreCase("more")
						|| moreOrCategory.getText().equalsIgnoreCase("categories")) {
					WebDriverUtilities.scrollToView(moreOrCategory);
					WebDriverUtilities.performJavaScriptClick(moreOrCategory);
					flag=true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * Click on product sublist element.
	 *
	 * @param productName - the product type name
	 * @param subProductName - the sub product name
	 * @return true, if successful
	 * 
	 */
	public boolean clickOnProductSublistElement(String productName, String subProductName) {
		logger.debug("*****In clickOnProductSublistElement function*****");
		boolean flag=false;
		for(WebElement element: allProductTypesSublist)
		try {
			if(element.getText().equalsIgnoreCase(subProductName)) {
				WebDriverUtilities.scrollToView(element);
				WebDriverUtilities.performJavaScriptClick(element);
				flag=true;
				break;
			}
		} catch(Exception e) {
			continue;
		}
		if(!flag) {
			boolean flagForSideMenu=false;
			for(WebElement element: productListFromSideMenu) {
				try {
				if(element.getText().equalsIgnoreCase(productName)) {
					WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
					WebDriverUtilities.scrollToView(element);
					WebDriverUtilities.performJavaScriptClick(element);
					flagForSideMenu=true;
					break;
				}
				} catch(Exception e) {
					continue;
				}
			}
		
			if(flagForSideMenu) {
				flag=false;
				WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(productSubListFromSideMenu);
				for(WebElement element:productSubListFromSideMenu) {
					try {
					if(element.getText().equalsIgnoreCase(subProductName)) {
						WebDriverUtilities.waitForElementClickabilityUsingFluentWait(element);
						WebDriverUtilities.scrollToView(element);
						WebDriverUtilities.performJavaScriptClick(element);
						flag=true;
						break;
					}
					} catch(Exception e) {
						continue;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * Verify product sub type text.
	 *
	 * @param productName - the product name
	 * @return true, if successful
	 * 
	 */
	public boolean verifyProductSubTypeText(String productName) {
		logger.debug("*****In verifyProductSubTypeText function*****");
		
		WebDriverUtilities.waitForElementsVisibilityUsingFluentWait(allElementsHeaderNameForVerification);
		
		for(WebElement element: allElementsHeaderNameForVerification) {
			try {
			WebDriverUtilities.scrollToView(element);
			String extractedText=TextProcessingUtility.returnExtractedSubProductText(element.getText());
			if(extractedText.equalsIgnoreCase(productName)) {
				return true;
			}
			} catch(Exception e) {
				continue;
			}
		}
		return false;
	}
	
	/**
	 * Verify quick look and click on it.
	 *
	 * @param productName - the product name
	 * @return true, if successful
	 * 
	 */
	public boolean verifyQuickLookAndClickOnIt(String productName) {
			logger.debug("*****In verifyQuickLookAndClickOnIt function*****");
			int i=0;
			for(WebElement element: allQuickSearchButtonWebElements) {
				try {
					WebElement header = allElementsHeaderNameForVerification.get(i);
					WebDriverUtilities.waitForElementVisibilityUsingFluentWait(header);
					WebDriverUtilities.scrollToView(header);
					String extractedText=TextProcessingUtility.returnExtractedSubProductText(header.getText());
					if(productName.equalsIgnoreCase(extractedText)) {
						WebDriverUtilities.scrollToView(element);
						WebDriverUtilities.performJavaScriptClick(element);;
						return true;
					}
				} catch(Exception e) {
					continue;
				}
			}
			return false;
		}
	
	/**
	 * Mouse hover on image and verify magnified image.
	 *
	 * @param productName - the product name
	 * @return true, if successful
	 * 
	 */
	public boolean mouseHoverOnImageAndVerifyMagnifiedImage(String productName) {
		logger.debug("*****In mouseHoverOnImageAndVerifyMagnifiedImage function*****");
		
		WebDriverUtilities.waitForElementVisibilityUsingFluentWait(productToHoverOn);
		
		WebDriverUtilities.scrollToView(productToHoverOn);
		
		WebDriverUtilities.moveToElementUsingActions(productToHoverOn);
		
		WebDriverUtilities.waitForElementVisibilityUsingFluentWait(imageMagnifierViewOfProduct);

		return imageMagnifierViewOfProduct.isDisplayed();
	}
}
