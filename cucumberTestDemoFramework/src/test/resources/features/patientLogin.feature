Feature: Patient login functionality
	as a Patient I want to login to use this application

Background:
	Given Patient is on the patient login page having URL "https://nextjs-poc-theta.vercel.app/"

@Test2	
Scenario: Patient can login Successfully with valid username and password
  Given Patient have entered valid username "krishnayadav@yopmail.com" and password "Pass@123"
  When Patient clicked on the Submit login button
  Then Patient should be logged in successfully with message "You have logged in successfully!"
