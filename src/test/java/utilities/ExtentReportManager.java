package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.net.URL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

	public class ExtentReportManager implements ITestListener
	
	{
		public ExtentSparkReporter sparkReporter;
		public ExtentReports extent;
		public ExtentTest test;
		String repName;
		
		 public void onStart(ITestContext testContext) 
		 {
			   
			//Executes only once before all test methods got executed
			/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Date dt=new Date();
			String currentdatatimestamp=df.format(dt);*/
			
			
			String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //TimeStamp Creation
			repName="Test-Report-"+ timeStamp +".html"; //ReportName Generation			
			sparkReporter=new ExtentSparkReporter(".\\reports\\"+ repName);
			
			sparkReporter.config().setDocumentTitle("opencart Automation Report");
			sparkReporter.config().setReportName("opencart Functional Testing");
			sparkReporter.config().setTheme(Theme.DARK);
			
			
			extent=new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Application", "opencart");
			extent.setSystemInfo("Module", "Admin");
			extent.setSystemInfo("Sub Module", "Customers");
			extent.setSystemInfo("User Name", System.getProperty("user.name")); //Displays current user of the system
			extent.setSystemInfo("Environment", "QA");
			
			
			String os=testContext.getCurrentXmlTest().getParameter("os");// Displays the information(OS,Browser,GroupNames) from XML file of TestMethod
			extent.setSystemInfo("Operating System", os);
			
			String browser=testContext.getCurrentXmlTest().getParameter("browser");
			extent.setSystemInfo("Browser", browser);
			
			List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
			if(!includedGroups.isEmpty())
			{
				extent.setSystemInfo("Groups", includedGroups.toString());
			}
		 }
		 
		 
		 public void onTestSuccess(ITestResult result)
		 {
			 test=extent.createTest(result.getTestClass().getName());
			 test.assignCategory(result.getMethod().getGroups()); //Display Groups in Report
			 
			 test.log(Status.PASS,result.getName()+"got Sucessfully executed");
		 }
		 
		 public void onTestFailure(ITestResult result)
		 {
			 test=extent.createTest(result.getTestClass().getName());
			 test.assignCategory(result.getMethod().getGroups());
			 
			 test.log(Status.FAIL,result.getName()+" got failed");
			 test.log(Status.INFO, result.getThrowable().getMessage());
			 
			 
			 try
			 {
				 String imgPath=new BaseClass().captureScreen(result.getName()); //Passing ClassName
				 //System.out.println("Screenshot saved at: " + imgPath);
				 test.addScreenCaptureFromPath(imgPath);
			 }
			 catch(IOException e1)
			 {
				 e1.printStackTrace();
			 }
			
		 }
		 
		 
		 public void onTestSkipped(ITestResult result)
		 {
			 test=extent.createTest(result.getTestClass().getName());
			 test.assignCategory(result.getMethod().getGroups());
			 test.log(Status.SKIP,result.getName()+"got skipped");
			 test.log(Status.INFO, result.getThrowable().getMessage());
		 }
		 
		 
		 public void onFinish(ITestContext testContext)
		 {
			 extent.flush();//Consolidate all the information from report and generate it
			 String pathOfExtentReport =System.getProperty("user.dir")+"\\reports\\"+repName;
			 File extentReport=new File(pathOfExtentReport);
			 
			 try
			 {
				 Desktop.getDesktop().browse(extentReport.toURI());  // Opens extentreport.html file on browser Automatically
			 }
			 catch(IOException e)
			 {
				e.printStackTrace(); 
			 }
		 }
			 
			 //To send test results report to the team Automatically
			 
			 /*try
			 {
				 URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
				 ImageHtmlEmail email=new ImageHtmlEmail();
				 email.setDataSourceResolver(new DataSourceUrlResolver(url));
				 email.setHostName("smtp.googlemail.com");//Works for GMail ID
				 email.setSmtpPort(465);
				 email.setAuthenticator(new DefaultAuthenticator("Username","password"));//provide username and password
				 email.setSSLOnConnect(true);
				 email.setFrom("SenderEmailID");
				 email.setSubject("TestResults");
				 email.setMsg("Please find attached report..");
				 email.addTo("Reciever EMail ID");
				 email.attach(url,"extent report","please check report..");
				 email.send();
			 }
			 catch(Exception e1)
			 {
				 e1.printStackTrace();
			 }*/
	}
			
			
			
			
			
