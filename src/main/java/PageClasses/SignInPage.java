package PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {

	WebDriver driver;
	public SignInPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h1[contains(text(),'Authentication')]")
	private WebElement signInPageHeader;
	
	@FindBy(xpath="//input[@id='email']")
	private WebElement emailField;
	
	@FindBy(xpath="//input[@id='passwd']")
	private WebElement passField;
	
	@FindBy(xpath="//button[@id='SubmitLogin']")
	private WebElement submitLoginButton;
	
	public void enterEmail(String email)
	{
		emailField.sendKeys(email);
	}
	
	public void enterPassword(String pass)
	{
		passField.sendKeys(pass);
	}
	
	public void clickSubmit()
	{
		submitLoginButton.click();
	}
}
