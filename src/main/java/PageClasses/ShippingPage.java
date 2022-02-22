package PageClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShippingPage {

	WebDriver driver;
	public ShippingPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@type='checkbox']")
	private WebElement agreeCheckbox;
	
	@FindBy(xpath="(//span[contains(text(),'Proceed to checkout')])[2]")
	private WebElement proceedtoCheckout;
	
	public void clickProceedToCheckout()
	{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		js.executeScript("arguments[0].click();", proceedtoCheckout);
	}
	
	public void selectCheckbox()
	{
		try
		{
		agreeCheckbox.click();
		System.out.println("PASS : Agree Checkbox is selected successfully");
		}
		catch(Exception e)
		{
			System.out.println("ERROR : Error while selecting checkbox");
			e.printStackTrace();
		}
	}
}
