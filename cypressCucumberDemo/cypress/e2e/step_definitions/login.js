import {
  Given,
  When,
  Then,
} from "@badeball/cypress-cucumber-preprocessor";
import {loginPage} from '@pages/LoginPage'

Given("Patient is on the patient login page", () => {
  cy.visit("/");
});

Given("Patient have entered valid username {string} and password {string}", (username, password) => {
  loginPage.typeUsername(username);
  loginPage.typePassword(password);  
});

When("Patient clicked on the Submit login button", () => {
  loginPage.clickLogin();
});
Then("Patient should be logged in successfully with message {string}", (message) => {
  // loginPage.elements.toastMessage().should("have.text", message);
  cy.wait(5000); // waiting for pop-up message to disappear
  cy.url().should("contains", "/documents");
});
