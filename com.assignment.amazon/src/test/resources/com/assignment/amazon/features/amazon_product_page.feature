#Author: your.email@your.domain.com
## (Comments)
#Sample Feature Definition Template
Feature: This feature is to test electronic mobile search functionality
	
	Background:
		Given User navigates to product specification page
		|dropdown_value|search_text|
		|Electronics|iPhone 13 128GB|
		
  @ProductScenario
  Scenario Outline: Verify product specification page opens in new browser tab on click operation
    When User clicks on visit apple store link
    And User clicks on "<productType>" link
    And User selects "<productName>" from "<productType>"
    Then User validates "<productName>" on apple store page
    Then User validates quick look presence for "<productName>" and clicks on it
    Then User performs mouse hover on the "<productName>" and validates presence of products magnified image
    Examples:
      | productType | productName |
      | Apple Watch | Apple Watch SE (GPS + Cellular) |