Feature: Patient registration functionality for new Patient
	as a Patient I want to register myself to use this application

Background:
	Given I am on the patient registration page having URL "https://nextjs-poc-theta.vercel.app/registration"

@Test1	
Scenario: Successfully login a new user after registration
  Given I have entered my valid details in registration form
  When I clicked on the Submit button
  Then I should be register myself with success pop-up
  Given I have entered my email and password on login page
  When I clicked on the Submit login button
  Then I should be logged in successfully
