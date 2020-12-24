package com.krishna.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TestNGMaker {
	
	private Map<String, ArrayList<String>> classAndMethodsName;
	
	private String start = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
			"<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >\n" + 
			"<suite name=\"Sample Test Suite\" parallel=\"tests\">\n" + 
			"\n" + 
			"	<listeners>\n" + 
			"		<listener\n" + 
			"			class-name=\"com.mPulse.listeners.AnnotationTransformer\">\n" + 
			"		</listener>\n" + 
			"	</listeners>\n" + 
			"\n" + 
			"	<test name=\"Test01\">\n" + 
			"		<classes>\n" + 
			"			";
	private String end = "\n" + 
			"		</classes>\n" + 
			"	</test>\n" + 
			"</suite>";
	
	public TestNGMaker(Map<String, ArrayList<String>> classAndMethodsName) {
		this.classAndMethodsName=classAndMethodsName;
	}
	
	public String makeTestNGFile(String fileName) {
		String xmlFile = start;
		for(Map.Entry<String, ArrayList<String>> entry : this.classAndMethodsName.entrySet()) {
			String className = entry.getKey();
			
			String classTag = "<class name=\""+className+"\">\n" + 
					"				<methods>\n" + 
					"					";
			int methodsCount = entry.getValue().size();
			
			for(int i=0; i<methodsCount; i++) {
				String method = entry.getValue().get(i);
				classTag = classTag + "<include name=\""+method+"\" />";
			}
			
			classTag = classTag + "\n" + 
					"				</methods>\n" + 
					"			</class>";
			xmlFile = xmlFile + classTag;
		}
		xmlFile = xmlFile + end;
		String filePath = System.getProperty("user.dir") + "/src/test/resources/testNgXml/" + fileName + ".xml";
		
		try {
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xmlFile);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xmlFile;
	}

}
