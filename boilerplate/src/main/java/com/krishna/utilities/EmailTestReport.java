package com.krishna.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class EmailTestReport {
	
	private int passedTestsCounter;
	private int failedTestsCounter;
	private int totalSuiteRun;
	private static int failedTestRerunCount = 0;
	
	public void sendEmailReport(ITestContext context) throws Exception {
		String xmlFileName = context.getSuite().getXmlSuite().getFileName();		
		xmlFileName = xmlFileName.substring(xmlFileName.lastIndexOf("/")+1);
		String failedCasesXmlFileName = xmlFileName.replace(".xml", "_")+"failed_cases_rerun.xml";
		
		String result = getTestSuiteResult(context.getSuite());
		StringBuilder str = new StringBuilder("Hi Team,\n");
		str.append("\n");
		str.append("Please find Automation suite run report!\n");
		str.append("\n");
		str.append("Note:- This is Computer generated Email message");
		str.append("\n");
		str.append("*******************************************");
		str.append("\nTotal number of test suite run - "+this.totalSuiteRun);
		str.append("\nTotal Passed test cases - "+(this.passedTestsCounter));
		str.append("\nTotal Failed test cases - "+(this.failedTestsCounter));
		str.append("\n");
		str.append("*******************************************");
		
		if(failedTestRerunCount == 0 && failedTestsCounter >= 1 
				&& ConfigReader.allowToRerunFailedTest().equalsIgnoreCase("true")) {
			str.append("\n");
			str.append("Note - Some test cases have failed, re-running failed test cases one more time, "
					+ "you will receive final test report in some time. Re-run failed test report name will be like "+failedCasesXmlFileName);
		}
		str.append("\n"+result);
		String textMessage = str.toString();
		System.out.println(textMessage);
		DateFormat df = new SimpleDateFormat("dd-MMM-YYYY");
	    Date date = new Date();
		String subject = "["+ConfigReader.getENVConfigValue().toUpperCase()+"] Automation Suite Report | Date - "+df.format(date)+" | XmlFile - "+xmlFileName;
		String filePath = System.getProperty("user.dir")+"/testReports";
		String fileName = UtilityMethods.getTheNewestFile(filePath, "html").getName();
		filePath = filePath +"/"+fileName;
		if(ConfigReader.allowToSendEmail().equalsIgnoreCase("true")) {
			EmailSendGrid.sendEmailMessage(subject, textMessage, filePath, fileName);
		}
		
		System.out.println(" ##### Test Suite Ended ##### ");
		
		if(failedTestRerunCount == 0 && failedTestsCounter >= 1 
				&& ConfigReader.allowToRerunFailedTest().equalsIgnoreCase("true") ) {
			
			System.out.println("Re running only failed test cases ..... ");
					
			TestNGMaker testNg = new TestNGMaker(getAllFailedTestClassAndMethodsName(context.getSuite()));
			testNg.makeTestNGFile(failedCasesXmlFileName.replace(".xml", ""));
			
			Thread.sleep(10000); // waiting for 10 seconds before starting 2nd Suite
			
			failedTestRerunCount++;
			passedTestsCounter=0;
			failedTestsCounter=0;
			totalSuiteRun=0;
			
			TestNGRunner runner = new TestNGRunner();
			runner.runTestSuite(failedCasesXmlFileName);
		}
		failedTestRerunCount=0;
	}
	
	private String getTestSuiteResult(ISuite suite) {
        Collection<ISuiteResult> suiteResults = suite.getResults().values();
        this.totalSuiteRun = suiteResults.size();
        StringBuilder str = new StringBuilder();
        String failedTestPrepend = "\nFailed Test Cases -\n"+"*******************************************"+"\n";
        for (ISuiteResult suiteResult : suiteResults) {
        	for (ITestResult result : suiteResult.getTestContext().getFailedTests().getAllResults()) {
        		failedTestsCounter++;
        		if(failedTestsCounter==1) {
    				str.append(failedTestPrepend);
    			}
        		if(failedTestsCounter<51) {
    				str.append(getFormatedResult(result, failedTestsCounter));
    			}
        	}
        }
        if(failedTestsCounter>0) {
        	str.append("*******************************************");
        }
        if(failedTestsCounter>50) {
        	str.append("\n");
        	str.append("Note- For further Failed Test Cases and complete Automation Report, please download the attached file and open in your browser.");
        }
        str.append("\n");
        
        String passedTestPrepend = "\nPassed Test Cases -\n"+"*******************************************"+"\n";
        for (ISuiteResult suiteResult : suiteResults) {
        	for (ITestResult result : suiteResult.getTestContext().getPassedTests().getAllResults()) {
        		passedTestsCounter++;
        		if(passedTestsCounter==1) {
    				str.append(passedTestPrepend);
    			}
        		if(passedTestsCounter<51) {
    				str.append(getFormatedResult(result, passedTestsCounter));
    			}
        	}
        }
        if(passedTestsCounter>0) {
        	str.append("*******************************************");
        }
        if(passedTestsCounter>50) {
        	str.append("\n");
        	str.append("Note- For further Passed Test Cases and complete Automation Report, please download the attached file and open in your browser.");
        }
        return str.toString();
    }

    private String getFormatedResult(ITestResult result, int count) {
        return count+": " + result.getMethod().getMethodName() + ".\n";
    }
    
    private HashMap<String, ArrayList<String>> getAllFailedTestClassAndMethodsName(ISuite suite) {
    	Collection<ISuiteResult> suiteResults = suite.getResults().values();
    	HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    	
        for (ISuiteResult suiteResult : suiteResults) {
        	for (ITestResult result : suiteResult.getTestContext().getFailedTests().getAllResults()) {
        		String className = result.getTestClass().getName();
        		String methodName = result.getMethod().getMethodName();
        		
        		if(map.containsKey(className)) {
        			map.get(className).add(methodName);
        		}
        		else {
        			map.put(className, new ArrayList<String>());
        			map.get(className).add(methodName);
        		}
        	}
        }
        return map;
    }

}
