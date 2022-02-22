package PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage {
	WebDriver driver;
	public LandingPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@id='header_logo']/a/img")
	private WebElement headerLogo;
	
	public boolean headerLogoDisplayed()
	{
		return headerLogo.isDisplayed();
	}
	
	public void clickOnTab(String tabName)
	{
		try
		{
		WebElement tab = driver.findElement(By.xpath("//ul[contains(@class,'sf-menu')]/li/a[contains(text(),'"+tabName+"')]"));
		
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.elementToBeClickable(tab));
		
		tab.click();
		}
		catch(Exception e)
		{
			System.out.println("FAILURE : Failed to click on tab name : "+ tabName);
			e.printStackTrace();
		}
		
	}
}
