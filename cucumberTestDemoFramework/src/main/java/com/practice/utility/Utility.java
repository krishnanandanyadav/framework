package com.practice.utility;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public class Utility {
	
	public String getRandomName() {
		return "Patient "+RandomStringUtils.randomAlphabetic(5).toLowerCase();
	}
	
	public String getRandomEmails() {
		return RandomStringUtils.randomAlphanumeric(8).toLowerCase()+"@yopmail.com";
	}
	
	public String getRandomPassword() {
		return RandomStringUtils.randomAlphanumeric(8).toLowerCase();
	}
	
	public String getRandomPhoneNumber() {
		String phone;
		Random rand = new Random();
		int num = rand.nextInt(90000000) + 10000000;
		phone = "99"+num;
		return phone;
	}

}
