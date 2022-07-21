package com.pages;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;

 

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Main
{
	static ExtentHtmlReporter htmlReporter;
	static ExtentTest test;
	static ExtentReports report;
	@BeforeTest
	public static void startTest()
	{
		    //htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport.html");
			htmlReporter = new ExtentHtmlReporter("/test-output/STMExtentReport.html");
			
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			
			htmlReporter.config().setDocumentTitle("Title of the Report Comes here");
			htmlReporter.config().setReportName("Name of the Report Comes here");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
	}
	
	@Test
	public void passTest(){
		test = report.createTest("passTest");
		Assert.assertTrue(true);
		test.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed is passTest", ExtentColor.GREEN));
	}
	
	@Test
	public void failTest(){
		test = report.createTest("failTest");
		Assert.assertTrue(false);
		test.log(Status.PASS, "Test Case (failTest) Status is passed");
		test.log(Status.PASS, MarkupHelper.createLabel("Test Case (failTest) Status is passed", ExtentColor.GREEN));
	}
	
	@Test
	public void skipTest(){
		test = report.createTest("skipTest");
		throw new SkipException("Skipping - This is not ready for testing ");
	}
	
	
	@AfterMethod
	public void getResult(ITestResult result)
	{
		if(result.getStatus() == ITestResult.FAILURE){
			//logger.log(Status.FAIL, "Test Case Failed is "+result.getName());
			//MarkupHelper is used to display the output in different colors
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}
		else if(result.getStatus() == ITestResult.SKIP){
			//logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));	
		}
	}
	@AfterTest
	public void endReport(){
		report.flush();
    }
}