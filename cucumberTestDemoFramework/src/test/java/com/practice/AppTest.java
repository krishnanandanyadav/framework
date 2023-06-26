package com.practice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.practice.pages.LoginPage;
import com.practice.pages.PatientRegistrationPage;
import com.practice.utility.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
	
	@Test
	public void testLogin() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://nextjs-poc-theta.vercel.app/registration");		
		PatientRegistrationPage page = new PatientRegistrationPage(driver);
		
		Utility ut = new Utility();
		
		String email = ut.getRandomEmails();
		String password = ut.getRandomPassword();
		
		page.enterPatientName(ut.getRandomName());
		page.enterPatientPhoneNo(ut.getRandomPhoneNumber());
		page.enterPatientAge(34);
		page.enterPatientEmail(email);
		page.enterPatientPassword(password);
		page.selectPatientCountryByName("India");
		page.clickOnSubmitButton();
		
		Assert.assertEquals(page.getToastMessage(), "Patient Registered successfully!");
		
		Thread.sleep(10000);
		
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.enterLoginEmail(email);
		
		loginPage.enterLoginPassword(password);
		
		loginPage.clickOnSubmitButton();
		
		Thread.sleep(1000);
		
		WebElement element = driver.findElement(By.xpath("//div/div[@role='alert']/div[text()]"));
		
		System.out.println("Login Message - "+element.getAttribute("innerHTML"));
		
		Thread.sleep(10000);
	}
}
