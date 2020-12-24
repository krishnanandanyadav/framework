package com.krishna.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.krishna.factories.ReporterFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage {
	
	WebDriver driver;
	ExtentTest testReporter;
	
	@FindBy(id = "user-name")
	@CacheLookup
	private WebElement userNameInputBox;

	@FindBy(id = "password")
	@CacheLookup
	private WebElement passwordInputBox;
	
	@FindBy(id = "login-button")
	@CacheLookup
	private WebElement loginButton;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
	}
	
	public boolean isUserNameInputBoxPresent() {
		if(this.userNameInputBox.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean isPasswordInputBoxPresent() {
		if(this.passwordInputBox.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean isLoginButtonPresent() {
		if(this.loginButton.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public LoginPage enterUserName(String userName) {
		if (this.isUserNameInputBoxPresent()) {
			testReporter.log(LogStatus.INFO, "UserName textbox found and going to fill user name");
			this.userNameInputBox.clear();
			this.userNameInputBox.sendKeys(userName);
			testReporter.log(LogStatus.INFO, "user name '"+userName+"' entered in username textbox");
			return this;
		} else {
			System.out.println("Error: Element 'user name' not Displayed");
			testReporter.log(LogStatus.ERROR, "Error: Element 'user name' not Displayed");
		}
		return this;
	}

	public LoginPage enterPassword(String password) {
		if (this.isPasswordInputBoxPresent()) {
			testReporter.log(LogStatus.INFO, "password textbox found and going to fill password");
			this.passwordInputBox.clear();
			this.passwordInputBox.sendKeys(password);
			testReporter.log(LogStatus.INFO, "password '"+password+"' entered in password textbox");
			return this;
		} else {
			System.out.println("Error: Element 'password' not Displayed");
			testReporter.log(LogStatus.ERROR, "Error: Element 'password' not Displayed");
		}
		return this;
	}

	public ProductsPage clickOnLoginButton() {
		if (this.isLoginButtonPresent()) {
			testReporter.log(LogStatus.INFO, "Login button found and going to click login button");
			loginButton.click();
			testReporter.log(LogStatus.INFO, "Login button clicked");
			return new ProductsPage(this.driver);
		} else {
			System.out.println("Error: Element 'login button' not Displayed");
			testReporter.log(LogStatus.ERROR, "Error: Element 'login button' not Displayed");
		}
		return new ProductsPage(this.driver);
	}

	public ProductsPage login(String userName, String password) {
		return this.enterUserName(userName).enterPassword(password).clickOnLoginButton();
	}

}
