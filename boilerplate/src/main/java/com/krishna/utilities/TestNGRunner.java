package com.krishna.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestNGRunner {
	
	public void runTestSuite(String runXMLs) throws FileNotFoundException, IOException {
		System.out.println("xml files are - " + runXMLs);
		
		List<String> listOfRunXmls = new ArrayList<String>();
		for (String runXML : runXMLs.split(",")) {
			listOfRunXmls.add(System.getProperty("user.dir") + "/src/test/resources/testNgXml/" + runXML.trim());
		}
		
		TestNG testng = new TestNG();
		List<String> suites = new ArrayList<String>();
		for (String suite : listOfRunXmls) {
			suites.add(suite);
		}
		testng.setTestSuites(suites);
		testng.setUseDefaultListeners(false);
		testng.run();
	}
}
