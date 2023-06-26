package com.practice.stepdefs;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.practice.pages.LoginPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PatientLoginStepDef {
	
	private WebDriver driver;
	private LoginPage loginPage;
	
	@Before("@Test2")
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		System.out.println("Before method of PatientLoginStepDef");
	}
	
	@After("@Test2")
	public void tearDown() {
		if (this.driver != null) {
			this.driver.quit();
		}
		System.out.println("After method of PatientLoginStepDef");
	}
	
	@Given("Patient is on the patient login page having URL {string}")
	public void patient_is_on_the_patient_login_page(String url) {
		this.driver.get(url);
		this.loginPage = new LoginPage(this.driver);
	}
	
	@Given("Patient have entered valid username {string} and password {string}")
	public void patient_have_entered_valid_username_and_password(String username, String password){
		this.loginPage.enterLoginEmail(username);
		this.loginPage.enterLoginPassword(password);
	}
	
	@When("Patient clicked on the Submit login button")
	public void patient_clicked_on_the_Submit_login_button() {
		this.loginPage.clickOnSubmitButton();
	}
	
	@Then("Patient should be logged in successfully with message {string}")
	public void patient_should_be_logged_in_successfully_with_message(String loginMessage) {
		Assert.assertEquals(this.loginPage.getToastMessage(), loginMessage);
	}

}
