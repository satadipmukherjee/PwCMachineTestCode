package PageClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage {

	WebDriver driver;
	public PaymentPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@id='total_price']")
	private WebElement totalPrice;
	
	@FindBy(xpath="//a[@class='bankwire']")
	private WebElement bankWire;
	
	@FindBy(xpath="//span[contains(text(),'I confirm my order')]")
	private WebElement confirmOrder;
	
	@FindBy(xpath="//strong[contains(text(),'Your order on My Store is complete.')]")
	private WebElement orderComplete;
	
	public String getTotalPrice()
	{
		return (totalPrice.getText()).trim();
	}
	
	public void clickPaybyBankWire()
	{
		try
		{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", bankWire);
		}
		catch(Exception e)
		{
			System.out.println("ERROR : Exception while clicking Pay via Bank Wire Button");
			e.printStackTrace();
		}
	}
	
	public void confirmOrder()
	{
		try
		{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", confirmOrder);
		}
		catch(Exception e)
		{
			System.out.println("ERROR : Exception while clicking Confirm Order Button");
			e.printStackTrace();
		}
	}
	
	public boolean orderComplete()
	{
		return orderComplete.isDisplayed();
	}
	
}
