package com.krishna.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.krishna.factories.BrowserFactory;
import com.krishna.factories.ReporterFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UnitTest extends BaseTest {
	
	WebDriver driver;
	
	@Test(groups = { "sso_unit_test_one" })
	public void unit_test2() throws Exception {
		driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test started on browser");
		testReporter.assignCategory("SSO Auth And User Service");
	}
}