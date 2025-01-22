package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("Starting TC002_LoginTest");
		HomePage hp=new HomePage(driver); //Passing driver from BaseClass
		hp.clickMyAccount();
		hp.clickLogin();
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email")); //Reading Email and Password my config.properties
		lp.setPassword(p.getProperty("password"));
		lp.ClickLogin();
		
		MyAccountPage macc=new MyAccountPage(driver);
		try
		{
		boolean targetpage=macc.isMyAccountExists();
		Assert.assertEquals(targetpage, true,"LoginFailed");
		}
		
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("Finished TC002_LoginTest");
		
		
	}

}
//We cannot run this file directly because BaseClass also depends on XML file for Browser parameter