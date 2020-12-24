package com.krishna.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static String readConfig(String fileName, String config) {
		Properties property = new Properties();
		try {
			property.load(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property.getProperty(config);
	}
	
	public static synchronized String getConfig(String config) {
		
		if (readConfig("common.properties", "ENV").equalsIgnoreCase("qa")) {
			return readConfig("qa.properties", config);
		}
		if (readConfig("common.properties", "ENV").equalsIgnoreCase("uat")) {
			return readConfig("uat.properties", config);
		}
		if (readConfig("common.properties", "ENV").equalsIgnoreCase("stage")) {
			return readConfig("stage.properties", config);
		}
		if (readConfig("common.properties", "ENV").equalsIgnoreCase("prod")) {
			return readConfig("prod.properties", config);
		}
		else return null;		
	}
	
	public static synchronized String getENVConfigValue() {		
		return readConfig("common.properties", "ENV");		
	}
	
	public static synchronized String allowToSendEmail() {		
		return readConfig("common.properties", "allowToSendEmail");		
	}
	
	public static synchronized String getBrowserName() {		
		return readConfig("common.properties", "browserType");		
	}
	
	public static synchronized String allowToRerunFailedTest() {		
		return readConfig("common.properties", "allowToRerunFailedTest");		
	}
}
