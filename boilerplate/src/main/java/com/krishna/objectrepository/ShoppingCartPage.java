package com.krishna.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.krishna.factories.ReporterFactory;
import com.relevantcodes.extentreports.ExtentTest;

public class ShoppingCartPage {
	
	WebDriver driver;
	ExtentTest testReporter;
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
	}
}
