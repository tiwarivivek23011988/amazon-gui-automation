#Author: your.email@your.domain.com
## (Comments)
#Sample Feature Definition Template
Feature: This feature is to test electronic mobile search functionality
	
	Background:
		Given User navigates to amazon page
		
  @Smoke
  Scenario Outline: Verify amazon landing page search functionality for IPhone 13
    When User selects "<dropdown_value>" from categories dropdown and types "<search_text>"
    Then User validates auto-complete suggestions align with the provided "<search_text>"
    Examples: 
      | dropdown_value | search_text |
      | Electronics|IPhone 13|

  @Sanity
  Scenario Outline: Verify amazon landing page search functionality for iPhone 13 128GB
    When User selects "<dropdown_value>" from categories dropdown and types "<search_text>"
    Then User clicks on "<search_text>" from auto-complete option thus suggested
    And User validates that "<search_text>" search returns products catalog list
    Examples: 
      | dropdown_value | search_text |
      | Electronics|iPhone 13 128GB|
      
  @Regression
  Scenario Outline: Verify product specification page opens in new browser tab on click operation
    When User selects "<dropdown_value>" from categories dropdown and types "<search_text>"
    Then User clicks on "<search_text>" from auto-complete option thus suggested
    Then User clicks on the "<search_text>" product from resulting product-catalog list
    And User validates that "<search_text>" specification page opens in new tab
    Examples: 
      | dropdown_value | search_text |
      | Electronics|iPhone 13 128GB|