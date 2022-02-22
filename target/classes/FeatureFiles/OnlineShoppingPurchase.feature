#Author: satadipmukherjee@gmail.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps

@endtoend
Feature: Login and purchase dress on online shopping application

  @endtoend
  Scenario: Launch Application and Add Dress to Cart
    Given "chrome" browser is launched with url "http://automationpractice.com/index.php"
    And page load is completed and header logo is displayed
    When user navigates to "Dresses" tab
    And user hovers on the last dress
    And user clicks on Add to Cart Button
    Then I validate product is successfully added to shopping cart
    And capture Total Product Amount before taxes and fees 
    And I click proceed to Checkout Button
    Given user is in "Order - My Store" Page
    Then Cart Total Amount is captured
    And user clicks on Proceed to Checkout Button
    Then user enters email ID
    And user enters password
    And user clicks on Sign In Button
    Then user clicks Proceed to Checkout in Address Page
    Then user selects terms and conditions checkbox
    And user clicks on Proceed to Checkout
    Then Cart Total Amount is captured again and compared
    And user clicks on Pay by Bank Wire
    Then user clicks on Confirm Order Button
    And it is validated that Order is completed successfully
    Then I close the browser
    
    