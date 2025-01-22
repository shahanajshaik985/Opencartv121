package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven") //dataProviderClass variable should be mentioned becasue dataproviders class file is in another class
	public void verify_LoginDDT(String email,String pwd,String exp) throws Exception
	{
		logger.info("Starting TC003_LoginDDTTest");
		try
		{
		HomePage hp=new HomePage(driver); //Passing driver from BaseClass
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email); //Reading Email and Password my config.properties
		lp.setPassword(pwd);
		lp.ClickLogin();
		
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountExists();
		
		/*  Valid data -- Login success --Test pass --Logout
		 				--	Login fail-- Test fail
		 	Invalid data --Login success--Test fail--Logout
		 				--Login fail--Test pass */
		

		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetpage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
				
				
			}
			else
			{
				Assert.assertTrue(false);
				
			}
		
		}
		
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetpage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
				
				
			}
			else
			{
				Assert.assertTrue(true);
				
				
			}
		
		}
		}
		catch(Exception e)
		{
			Assert.fail();		
		}
		
		logger.info("Finished TC003_LoginDDTTest");
		
	}

}
