package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


//All re-usable methods for multiple test cases will be stored here
public class BaseClass
{
	public static WebDriver driver;
	public Logger logger; //log4j
	public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
		{
		
		//loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
				
		
		logger = LogManager.getLogger(this.getClass()); 
		
		//This code to run test case under Grid environment i.e Remote environment,Decide OS and browser
		//Environment Local/Remote will be taken from Config.properties execution_env variable
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities cap=new DesiredCapabilities();
			
			//Operating System
			if(os.equalsIgnoreCase("windows"))
			{
				cap.setPlatform(Platform.WIN10);
				
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				cap.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No Matching OS");
				return;
			}
				
			
			//Browser
			
			switch(br.toLowerCase())
			{
			
				case "chrome":cap.setBrowserName("chrome");break;
				case "edge":cap.setBrowserName("MicrosoftEdge");break;
				case "firefox":cap.setBrowserName("firefox");break;
				
				default:System.out.println("No matching Browser");return;
				
			}
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
			
		}
		
		
		//This is the code to execute from our system
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome": driver=new ChromeDriver();break;
			case "firefox": driver=new FirefoxDriver();break;
			default:System.out.println("Invalid browser name"); return;
			//If we dont put return keyword here, test will continue for execution even though it is invalid browser
			//Because of return keyword,it will comeout from execution when  browser name is invalid
			
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); //Reading URL from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}

	
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(9);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		String generatedNumber = RandomStringUtils.randomNumeric(5);
		return (generatedString + "@" + generatedNumber);
	}
	
	

	public String captureScreen(String tname) throws IOException
	{
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
	   
}
