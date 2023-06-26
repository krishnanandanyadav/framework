package com.practice.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions( features = "src/test/resources/features", glue = {"com.practice.stepdefs"},
					plugin = {"pretty", "html:target/cucumber-reports.html"}, monochrome = true
		)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@Override
	@DataProvider (parallel = true)
	public Object[][] scenarios(){
		return super.scenarios();
		
	}

}
