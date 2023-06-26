package com.practice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PatientRegistrationPage {
	
	private WebDriver driver;
	
	@FindBy(id = "patient-name")
	private WebElement patientName;
	
	@FindBy(id = "Phone-No")
	private WebElement patientPhoneNo;
	
	@FindBy(id = "patient-age")
	private WebElement patientAge;
	
	@FindBy(id = "exampleInputEmail1")
	private WebElement patientEmail;
	
	@FindBy(id = "exampleInputPassword1")
	private WebElement patientPassword;
	
	@FindBy(id = "country-id")
	private WebElement patientCountry;
	
	@FindBy(xpath = "//button[@type = 'submit' and text() = 'Submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//div/div[@role='alert']/div[text()]")
	private WebElement toastMessage;
	
	
	public PatientRegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void enterPatientName(String name) {
		this.patientName.click();
		this.patientName.clear();
		this.patientName.sendKeys(name);
	}
	
	public void enterPatientPhoneNo(String phoneNo) {
		this.patientPhoneNo.click();
		this.patientPhoneNo.clear();
		this.patientPhoneNo.sendKeys(phoneNo);
	}
	
	public void enterPatientAge(int age) {
		this.patientAge.click();
		this.patientAge.clear();
		this.patientAge.sendKeys(String.valueOf(age));
	}
	
	public void enterPatientEmail(String email) {
		this.patientEmail.click();
		this.patientEmail.clear();
		this.patientEmail.sendKeys(email);
	}
	
	public void enterPatientPassword(String password) {
		this.patientPassword.click();
		this.patientPassword.clear();
		this.patientPassword.sendKeys(password);
	}
	
	public void selectPatientCountryByName(String countryName) {
		this.patientCountry.click();
		Select sc = new Select(this.patientCountry);
		sc.selectByVisibleText(countryName);
	}
	
	public void clickOnSubmitButton() {
		this.submitButton.click();
	}
	
	public boolean ifRegistrationSuccess() {
		return true;
	}
	
	public String getToastMessage() {
		return this.toastMessage.getAttribute("innerHTML");
	}

}
