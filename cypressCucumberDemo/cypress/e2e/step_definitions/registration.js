import {
    Given,
    When,
    Then,
  } from "@badeball/cypress-cucumber-preprocessor";
  import {loginPage} from '@pages/LoginPage'
  import {registrationPage} from '@pages/RegistrationPage'
  import { faker } from '@faker-js/faker';

  // var patientName = faker.person.fullName();
  // var patientEmail = faker.internet.email({ firstName: faker.string.alpha(), 
  //   lastName: faker.string.alpha(5), provider: 'yopmail.com' });

  // var patientPhone = faker.phone.number('98########');

  Given("I am on the patient registration page", () => {
    cy.visit("/registration");
  });

  Given("I have entered my valid details in registration form", () => {
    registrationPage.enterPatientName("Krishna");
    registrationPage.enterPatientPhoneNo("8956353656");
    registrationPage.enterPatientAge(45);
    registrationPage.enterPatientEmail("krishnatest@yopmail.com");
    registrationPage.enterPatientPassword("Pass@123");
    registrationPage.selectPatientCountry("India");
  });

  When("I clicked on the Submit button", () => {
    registrationPage.clickSubmitButton();
  });

  Then("I should be register myself with success pop-up", () => {
    cy.wait(2000);
    registrationPage.elements.toastMessage().should('have.text', 'Patient Registered successfully!');
  });

  Then("Waiting for success pop-up to disappear", () => {
    cy.wait(3000);
  });

  Given("I have entered my email and password on login page", () => {
    loginPage.typeUsername("krishnatest@yopmail.com");
    loginPage.typePassword("Pass@123");
  });

  When("I clicked on the Submit login button", () => {
    loginPage.clickLogin();
  });

  Then("I should be logged in successfully", () => {
    loginPage.elements.toastMessage().should('have.text', 'You have logged in successfully!');
  });