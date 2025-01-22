package testCases;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass 
{
  
    

    @Test(groups={"Regression","Master"})
    public void verify_Account_registration()
    {
        logger.info("***** Starting TC001_AccountRegistrationTest *****");
        try 
        {
            HomePage hp = new HomePage(driver);
  
            hp.clickMyAccount();
            logger.info("***** Clicked on MyAccount link *****");
            hp.clickRegister();
            logger.info("***** Clicked on Register Link *****");

            AccountRegistrationPage ap = new AccountRegistrationPage(driver);
            logger.info("***** Providing Customer details *****");
            ap.setFirstName(randomString().toUpperCase());
            ap.setLastName(randomString().toUpperCase());
            String password = randomAlphaNumeric(); // Corrected spelling
            ap.setPassword(password);
            ap.setConfirmPassword(password);
            ap.setEmail(randomString() + "@gmail.com");
            ap.setNumber(randomNumber());
            ap.setprivacypolicy();
            ap.ClickContinue();

            logger.info("***** Validating expected message *****");
            String cf_msg = ap.getConfirmationMsg();
            Assert.assertEquals(cf_msg, "Your Account Has Been Created!");
        } 
        catch (Exception e)
        {
            logger.error("Test failed due to an exception: " + e.getMessage());
            logger.debug("Debug logs");
            Assert.fail(e.getMessage());
        }
        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }
} // Closing brace for the class
