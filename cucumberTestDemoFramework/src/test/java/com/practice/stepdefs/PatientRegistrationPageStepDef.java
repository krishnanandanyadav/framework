package com.practice.stepdefs;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.practice.pages.LoginPage;
import com.practice.pages.PatientRegistrationPage;
import com.practice.utility.Utility;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PatientRegistrationPageStepDef {
	
	private WebDriver driver;
	private PatientRegistrationPage page;
	private LoginPage loginPage;
	
	private String email;
	private String password;
	
	@Before
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@After
	public void tearDown() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}
	
	@Given("I am on the patient registration page")
	public void i_am_on_the_patient_registration_page() {
		this.driver.get("https://nextjs-poc-theta.vercel.app/registration");
		this.page = new PatientRegistrationPage(this.driver);
	}
	
	@Given("I have entered my valid details in registration form")
	public void i_have_entered_my_valid_details_in_registration_form() {
		Utility ut = new Utility();
		this.email = ut.getRandomEmails();
		this.password = ut.getRandomPassword();
		this.page.enterPatientName(ut.getRandomName());
		this.page.enterPatientPhoneNo(ut.getRandomPhoneNumber());
		this.page.enterPatientAge(34);
		this.page.enterPatientEmail(this.email);
		this.page.enterPatientPassword(this.password);
		this.page.selectPatientCountryByName("India");
	}
	
	@When("I clicked on the Submit button")
	public void i_clicked_on_the_Submit_button() {
		this.page.clickOnSubmitButton();
	}
	
	@Then("I should be register myself with success pop-up")
	public void i_should_be_register_myself_with_success_pop_up() throws InterruptedException{
		Assert.assertEquals(this.page.getToastMessage(), "Patient Registered successfully!");
	}
	
	@Given("I have entered my email and password on login page")
	public void I_have_entered_my_email_and_password_on_login_page() throws InterruptedException {
		Thread.sleep(6000); // waiting for patient Registered success toast message to disappear
		this.loginPage = new LoginPage(this.driver);
		this.loginPage.enterLoginEmail(this.email);
		this.loginPage.enterLoginPassword(this.password);
	}
	
	@When("I clicked on the Submit login button")
	public void I_clicked_on_the_Submit_login_button() {
		this.loginPage.clickOnSubmitButton();
	}
	
	@Then("I should be logged in successfully")
	public void I_should_be_logged_in_successfully() {
		Assert.assertEquals(this.loginPage.getToastMessage(), "You have logged in successfully!");
	}

}
