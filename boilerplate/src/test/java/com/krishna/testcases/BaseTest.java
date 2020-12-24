package com.krishna.testcases;

import com.krishna.factories.BrowserFactory;
import com.krishna.factories.DatabaseFactory;
import com.krishna.factories.ReporterFactory;
import com.krishna.utilities.ConfigReader;
import com.krishna.utilities.EmailTestReport;
import com.krishna.utilities.UtilityMethods;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class BaseTest {
	
	@BeforeSuite(alwaysRun = true)
	public synchronized void startSuite(ITestContext context) throws Exception {
		System.out.println("**************************************************************************************");
		System.out.println(" ##### Test Suite Started On "+ConfigReader.getENVConfigValue().toUpperCase()+" ##### ");
		
		String xmlFileName = context.getSuite().getXmlSuite().getFileName();
		xmlFileName = xmlFileName.substring(xmlFileName.lastIndexOf("/")+1);
		ReporterFactory.setTestNgXmlFileName(xmlFileName);
		ReporterFactory.nameToTestMap.clear();
		ReporterFactory.threadToExtentTestMap.clear();
		System.out.println("***************************  Running testNg xml file name - "+xmlFileName+" - ***************************");
	}

	@AfterSuite(alwaysRun = true)
	public synchronized void endSuite(ITestContext context) throws Exception {
		ReporterFactory.closeReport();
		EmailTestReport emailReport = new EmailTestReport();
		emailReport.sendEmailReport(context);
	}

	@BeforeTest(alwaysRun = true)
	public synchronized void startTest(final ITestContext testContext) {

		System.out.println("----- Test '" + testContext.getName() + "' Started -----");
	}

	@AfterTest(alwaysRun = true)
	public synchronized void endTest(final ITestContext testContext) {

		System.out.println("----- Test '" + testContext.getName() + "' Ended -----");
		BrowserFactory.closeWebDriver();
		DatabaseFactory.closeConnection();
	}

	@BeforeMethod(alwaysRun = true)
	public synchronized void startMethod(Method method) {
		ReporterFactory.getTest(method.getName(), "Test Method '" + method.getName() + "' Started");
		System.out.println("**** Test Method '" + method.getName() + "' Started ****");
	}

	@AfterMethod(alwaysRun = true)
	public synchronized void endMethod(ITestResult result) {

		ExtentTest testReporter = ReporterFactory.getTest();
		if (ITestResult.FAILURE == result.getStatus()) {
			System.out.println(">>>>>>>> Error :- Test Method '" + result.getName() + "' Failed <<<<<<<<");
			testReporter.log(LogStatus.FAIL, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Failed ****");
			StringWriter sw = new StringWriter(); 
			result.getThrowable().printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString(); 
			// Write the stack trace to extent reports 
			testReporter.log(LogStatus.FAIL, "<span class='label failure'>" + result.getName() + "</span>", "<pre>Stacktrace:\n" + stacktrace + "</pre>");
			String screenshot_path = UtilityMethods.getScreenShot("Method_" + result.getName() + "_onFailure");
			String image = testReporter.addScreenCapture(screenshot_path);
			testReporter.log(LogStatus.FAIL, result.getMethod().getMethodName(), image);
			System.out.println("Screen Shot taken");
		} if(ITestResult.SUCCESS == result.getStatus()){
			testReporter.log(LogStatus.PASS, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Passed ****");
		}if(ITestResult.SKIP == result.getStatus()) {
			testReporter.log(LogStatus.INFO, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Skipped ****");
		}
		ReporterFactory.closeTest(result.getMethod().getMethodName());
		System.out.println("**** Test Method '" + result.getName() + "' Ended ****");
	}     
}
