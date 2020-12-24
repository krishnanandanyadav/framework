package com.krishna.utilities;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomeUtility {
	
	public static String getRandomEmails(int length) {
		return RandomStringUtils.randomAlphanumeric(length).toLowerCase()+"@mailinator.com";
	}
	
	public static String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6).toLowerCase();
	}
	
	public static synchronized String getRandomPhoneNumber() {
			Random rand = new Random();
			int num = rand.nextInt(90000000) + 10000000;
			String finalnumber = "199"+num;
			return finalnumber;
	}
	
	public static String getRandomNumber() {
		Random rand = new Random();
		int num = rand.nextInt(900) + 100;
		String finalnumber = "1"+num;
		return finalnumber;
	}
}
