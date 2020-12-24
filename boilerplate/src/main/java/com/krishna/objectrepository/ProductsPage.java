package com.krishna.objectrepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.krishna.factories.ReporterFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProductsPage {
	
	WebDriver driver;
	ExtentTest testReporter;
	
	@FindBy(xpath = "//a[@href and contains(@class, 'shopping_cart_link')]")
	private WebElement shoppingCartLink;
	
	@FindBy(xpath = "//div[@id='menu_button_container']//button[text()='Open Menu']")
	private WebElement openMenuButton;
	
	@FindBy(xpath = "//div[@id='menu_button_container']//button[text()='Close Menu']")
	private WebElement closeMenuButton;
	
	@FindBy(id = "about_sidebar_link")
	private WebElement aboutLink;
	
	@FindBys({@FindBy(xpath="//div[@id='inventory_container' and @class]//button[text()='ADD TO CART']")})
	private List<WebElement> allAddToCartButton;
	
	@FindBys({@FindBy(xpath="//div[@id='inventory_container' and @class]//div[@class='inventory_item_name']")})
	private List<WebElement> allItemNameText;
	
	public ProductsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
	}
	
	public boolean isShoppingCartLinkPresent() {
		if(this.shoppingCartLink.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean isOpenMenuButtonPresent() {
		if(this.openMenuButton.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean isCloseMenuButtonPresent() {
		if(this.closeMenuButton.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public boolean isAboutLinkPresent() {
		if(this.aboutLink.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public ShoppingCartPage clickOnShoppingCartLink() {
		if (this.isShoppingCartLinkPresent()) {
			testReporter.log(LogStatus.INFO, "Shopping Cart Link found and going");
			this.shoppingCartLink.click();
			testReporter.log(LogStatus.INFO, "Shopping Cart Link clicked");
			return new ShoppingCartPage(this.driver);
		} else {
			System.out.println("Error: Element 'shoppingCartLink' not Displayed");
			testReporter.log(LogStatus.ERROR, "Error: Element 'shoppingCartLink' not Displayed");
		}
		return new ShoppingCartPage(this.driver);
	}

}
