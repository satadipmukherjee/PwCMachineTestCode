package PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DressPage {

	WebDriver driver;
	public DressPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="(//ul[@class='product_list grid row']//a[@class='product-name'])[last()]")
	private WebElement lastDress;

	@FindBy(xpath="(//span[text()='Add to cart'])[last()]")
	private WebElement addToCartLastDress;

	@FindBy(xpath="//span[contains(text(),'There is 1 item in your cart')]")
	private WebElement productAddedToCart;

	@FindBy(xpath="//strong[contains(text(),'Total products')]//following::span[@class='ajax_block_products_total']")
	private WebElement productTotalCost;

	@FindBy(xpath="//a[@title='Proceed to checkout']")
	private WebElement proceedToCheckOut;

	public void hoverOnLastDress()
	{
		try
		{
			Actions action = new Actions(driver);
			action.moveToElement(lastDress).build().perform();
			Thread.sleep(3000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("FAILURE : Failed to hover over Last Dress.");
			err.printStackTrace();
		}
	}

	public void addToCartLastDress()
	{
		try
		{
			JavascriptExecutor js=(JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", addToCartLastDress);

			WebDriverWait wait = new WebDriverWait(driver,15);
			wait.until(ExpectedConditions.visibilityOf(productAddedToCart));
		}
		catch(Exception err)
		{
			System.out.println("FAILURE : Failed to Click on Add Cart on Last Dress.");
			err.printStackTrace();
		}
	}

	public boolean productAddedToCart()
	{
		//WebElement message = driver.findElement(By.xpath("//span[contains(text(),'There is 1 item in your cart')]"));
		return productAddedToCart.isDisplayed();
	}

	public String returnProductTotalCost()
	{
		try
		{
		return (productTotalCost.getText()).trim();
		}
		catch(Exception err)
		{
			System.out.println("FAILURE : Failed to capture total Product cost.");
			err.printStackTrace();
			return null;
		}
	}

	public void clickProceedToCheckOut()
	{
		try {
			JavascriptExecutor js=(JavascriptExecutor) driver;
			Thread.sleep(2000);
			js.executeScript("arguments[0].click();", proceedToCheckOut);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("FAILURE : Failed to Click on Proceed to Checkout.");
			err.printStackTrace();
		}

		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Shopping-cart summary')]")));
	}
}
