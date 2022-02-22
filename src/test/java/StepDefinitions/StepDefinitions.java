package StepDefinitions;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import PageClasses.AddressPage;
import PageClasses.DressPage;
import PageClasses.LandingPage;
import PageClasses.PaymentPage;
import PageClasses.ShippingPage;
import PageClasses.SignInPage;
import PageClasses.SummaryPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StepDefinitions {

	//Global WebDriver and Properties Objects , can be encapsulated with access specifiers
	WebDriver driver;
	Properties prop;
	
	//Declaring all the Objects of the Page classes which will be initialized post Driver is Initialized
	LandingPage lp;
	DressPage dp;
	SummaryPage sp;
	SignInPage sip;
	AddressPage ap;
	ShippingPage sh;
	PaymentPage pp;
	
	//Variables to capture and parse the product price across the different End to End Purchase flow
	String productCost;
	float productCostFloat;
	String totalPrice;
	float totalPriceFloat;
	String totalPriceVerify;
	float totalPriceVerifyFloat;
	float priceDiff;

	@Given("{string} browser is launched with url {string}")
	public void browser_is_launched_with_url(String browserName, String launchURL) {
		if(browserName.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edgechromium"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else
		{
			Assert.assertTrue(false, "Please enter valid browser name in Launch Step");
		}

		driver.get(launchURL);
		System.out.println("SUCCESS: Driver is launched successfully.");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Given("page load is completed and header logo is displayed")
	public void page_load_is_completed_and_header_logo_is_displayed() {
		lp=new LandingPage(driver);
		Assert.assertTrue(lp.headerLogoDisplayed(), "ERROR: Header Logo is not displayed");
	}

	@When("user navigates to {string} tab")
	public void user_navigates_to_tab(String tabName) {
		lp.clickOnTab(tabName);
	}

	@When("user hovers on the last dress")
	public void user_hovers_on_the_last_dress() {
		dp = new DressPage(driver);
		dp.hoverOnLastDress();
	}

	@When("user clicks on Add to Cart Button")
	public void user_clicks_on_add_to_cart_button() {
		dp.addToCartLastDress();
	}

	@Then("I validate product is successfully added to shopping cart")
	public void i_validate_product_is_successfully_added_to_shopping_cart() {
		Assert.assertTrue(dp.productAddedToCart(), "ERROR: Product is not added to cart successfully");
	}

	@Then("capture Total Product Amount before taxes and fees")
	public void capture_total_product_amount_before_taxes_and_fees() {
		productCost = dp.returnProductTotalCost();
		System.out.println("Product Total Cost Captured from WebPage is : "+productCost);
		
		productCost = productCost.replaceAll("[^\\d.]", "");
		productCostFloat = Float.parseFloat(productCost);
	}
	
	@Then("I click proceed to Checkout Button")
	public void i_click_proceed_to_checkout_button() {
	    dp.clickProceedToCheckOut();
	}
	
	@Given("user is in {string} Page")
	public void user_is_in_page(String pageTitle) {
	    String currPage = driver.getTitle();
	    Assert.assertEquals(pageTitle, currPage,"ERROR : Page Title Mismatch");
	}

	@Then("Cart Total Amount is captured")
	public void cart_total_amount_is_captured() {
		sp=new SummaryPage(driver);
		totalPrice = sp.getTotalPrice();
		System.out.println("Total Price Captured from Application : "+ totalPrice);
		
		totalPrice = totalPrice.replaceAll("[^\\d.]", "");
		totalPriceFloat = Float.parseFloat(totalPrice);
		
		priceDiff = totalPriceFloat-productCostFloat;
		System.out.println("Price Difference between Product Price and Total Price is : "+ priceDiff);
	}

	@Then("user clicks on Proceed to Checkout Button")
	public void user_clicks_on_proceed_to_checkout_button() {
		sp.clickProceedToCheckOut();
	}

	@Then("user enters email ID")
	public void user_enters_email_id() {
		sip=new SignInPage(driver);
		
		try
		{
		prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Config/Input.properties"));
		}
		catch(Exception e)
		{
			System.out.println("ERROR : Loading Prop File");
			e.printStackTrace();
		}
		
		String email = prop.getProperty("email");
	    sip.enterEmail(email);
	    
	}

	@Then("user enters password")
	public void user_enters_password() {
		String pass = prop.getProperty("password");
		sip.enterPassword(pass);
	}

	@Then("user clicks on Sign In Button")
	public void user_clicks_on_sign_in_button() {
	    sip.clickSubmit();
	}

	@Then("user clicks Proceed to Checkout in Address Page")
	public void user_clicks_proceed_to_checkout_in_address_page() {
	    ap=new AddressPage(driver);
	    ap.clickProceedToCheckout();
	}

	@Then("user selects terms and conditions checkbox")
	public void user_selects_terms_and_conditions_checkbox() {
	    sh=new ShippingPage(driver);
	    sh.selectCheckbox();
	}

	@Then("user clicks on Proceed to Checkout")
	public void user_clicks_on_proceed_to_checkout() {
	    sh.clickProceedToCheckout();
	}
	
	@Then("Cart Total Amount is captured again and compared")
	public void cart_total_amount_is_captured_again_and_compared() {
		pp=new PaymentPage(driver);
		
		totalPriceVerify = pp.getTotalPrice().trim();
		System.out.println("Final Price Captured again on web Page payment state is : "+totalPriceVerify);
		
		totalPriceVerify = totalPriceVerify.replaceAll("[^\\d.]", "");
		Assert.assertEquals(totalPrice, totalPriceVerify,"ERROR : Mismatch in Total Price between Summary Page and Shipping Page.");
		
		totalPriceVerifyFloat = Float.parseFloat(totalPrice);
		Assert.assertEquals(productCostFloat+priceDiff, totalPriceVerifyFloat, "ERROR : Product Price + Extra Charges not equal to Total Price in Payment page");
		
	}

	@Then("user clicks on Pay by Bank Wire")
	public void user_clicks_on_pay_by_bank_wire() {
	    pp.clickPaybyBankWire();
	}

	@Then("user clicks on Confirm Order Button")
	public void user_clicks_on_confirm_order_button() {
	    pp.confirmOrder();
	}

	@Then("it is validated that Order is completed successfully")
	public void it_is_validated_that_order_is_completed_successfully() {
	    Assert.assertTrue(pp.orderComplete(),"ERROR : Order placed successfully message is not displayed");
	}
	
	@Then("I close the browser")
	public void close_browser() {
	    if(driver!=null)
	    	driver.quit();
	}
}
