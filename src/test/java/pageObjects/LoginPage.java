package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txt_email;
	@FindBy(xpath="//input[@id='input-password']") WebElement password;
	@FindBy(xpath="//input[@value='Login']") WebElement button_login;
	
	public void setEmail(String email)
	{
		txt_email.sendKeys(email);
	}
	
	
	public void setPassword(String passwrd)
	{
		password.sendKeys(passwrd);
	}
	
	
	public void ClickLogin()
	{
		button_login.submit();;
	}
	
}
