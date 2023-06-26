package com.practice.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	
	@FindBy(id = "exampleInputEmail1")
	private WebElement loginEmail;
	
	@FindBy(id = "exampleInputPassword1")
	private WebElement loginPassword;
	
	@FindBy(xpath = "//button[@type = 'submit' and text() = 'Submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//div/div[@role='alert']/div[text()]")
	private WebElement toastMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void enterLoginEmail(String email) {
		this.loginEmail.click();
		this.loginEmail.clear();
		this.loginEmail.sendKeys(email);
	}
	
	public void enterLoginPassword(String password) {
		this.loginPassword.click();
		this.loginPassword.clear();
		this.loginPassword.sendKeys(password);
	}
	
	public void clickOnSubmitButton() {
		this.submitButton.click();
	}
	
	public String getToastMessage() {
		return this.toastMessage.getAttribute("innerHTML");
	}

}
