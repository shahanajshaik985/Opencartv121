package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
	

	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement firstname;
	
	@FindBy(xpath="//input[@id='input-lastname']") WebElement lastname;
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txt_email;
	
	@FindBy(xpath="//input[@id='input-telephone']") WebElement telephone;
	
	@FindBy(xpath="//input[@id='input-password']") WebElement password;
	
	@FindBy(xpath="//input[@id='input-confirm']") WebElement cnf_password;
	
	@FindBy(xpath="//input[@name='agree']") WebElement Checkbox;
	
	
	@FindBy(xpath="//input[@value='Continue']") WebElement button_continue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msg_confirmation;




public void setFirstName(String fname)
{
	firstname.sendKeys(fname);
}

public void setLastName(String lname)
{
	lastname.sendKeys(lname);
}

public void setEmail(String email)
{
	txt_email.sendKeys(email);
}


public void setNumber(String number)
{
	telephone.sendKeys(number);
}

public void setPassword(String passwrd)
{
	password.sendKeys(passwrd);
}

public void setConfirmPassword(String passwrd)
{
	cnf_password.sendKeys(passwrd);
}

public void setprivacypolicy()
{
	Checkbox.click();
}


public void ClickContinue()
{
	button_continue.submit();;
}


//Here, we are not validating as it is PageObjectClass, we are justing getting String of conformation message, we wll validate it in test case
public String getConfirmationMsg()
{
	try
	{
		return msg_confirmation.getText();
	}
	catch(Exception e)
	{
		return (e.getMessage());
	}
}

}
