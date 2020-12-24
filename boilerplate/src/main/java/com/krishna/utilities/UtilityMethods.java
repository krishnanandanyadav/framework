package com.krishna.utilities;

import static io.restassured.RestAssured.expect;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.krishna.factories.BrowserFactory;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UtilityMethods {

	public static synchronized String getScreenShot(String ScreenShotName) {
		WebDriver driver = BrowserFactory.getBrowser();
		String fileName = (new Date()).toString().replace(" ", "_").replace(":", "-").trim() + ".png";
		String destinationFilePath = "./ScreenShots/" + ScreenShotName + "_" + fileName;
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File(destinationFilePath));
		} catch (Exception e) {
			System.out.println("Exception while taking screen shot " + e.getMessage());
		}
		System.out.println("Screen shot taken");
		return destinationFilePath;
	}
	
	public static synchronized Double getPercentage(int count, int total) {
		Double toBeTruncated = ((count*100.0)/(total*1.0));
		System.out.println("un truncated "+toBeTruncated);
	    Double truncatedDouble=new BigDecimal(toBeTruncated ).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return truncatedDouble;
	}
	
	public static synchronized String getDate(String format, int delaydate) {
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DAY_OF_MONTH, delaydate);
		dNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(dNow);
		return strDate;
	}
	
	public static synchronized String getISTDate(String format, int delaydate) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DAY_OF_MONTH, delaydate);
		dNow = cal.getTime();
		String strDate = sdf.format(dNow);
		return strDate;
	}
	
	public static synchronized String getTimeWithDelay(int delayTimeInMinutes) {
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.MINUTE, delayTimeInMinutes);
		dNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String strDate = sdf.format(dNow);
		return strDate;
	}
	
	public static synchronized String getISTTimeWithDelay(int delayTimeInMinutes) {
		SimpleDateFormat sd = new SimpleDateFormat(
                "hh:mma");
        Date date = new Date();  
        Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, delayTimeInMinutes);
		date = cal.getTime();
        sd.setTimeZone(TimeZone.getTimeZone("IST"));
        System.out.println("Time is - "+sd.format(date));
		return sd.format(date);
	}
	
	public static synchronized String getCurrentTime(String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
        Date date = new Date();  
        Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return sd.format(date);
	}
	
	public static synchronized long getTimeDifferenceInSeconds(String startTime, String endTime) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("hh:mm:ss");
		Date d1 = sd.parse(startTime);
		Date d2 = sd.parse(endTime);
		return TimeUnit.MILLISECONDS.toSeconds(d2.getTime() - d1.getTime());
	}
	
	public static synchronized String getBasicAuth(String username, String password) {
		return "Basic "+ Base64.getEncoder().encodeToString((username +":"+ password).getBytes());
	}
	
	public static synchronized File getTheNewestFile(String filePath, String ext) {
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter("*." + ext);
	    File[] files = dir.listFiles(fileFilter);

	    if (files.length > 0) {
	        /** The newest file comes first **/
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }
	    return theNewestFile;
	}
	
	public static synchronized List<String> getAllCSVColumnsValueByHeaderName(File file, String headerName) throws Exception {
		List<String> values = new ArrayList<String>();
		try (
	            Reader reader = new FileReader(file);
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                    .withFirstRecordAsHeader()
	                    .withIgnoreHeaderCase()
	                    .withTrim());
	        ) {
	            for (CSVRecord csvRecord : csvParser) {
	                // Accessing values by Header names
	                String value = csvRecord.get(headerName).trim().toString();
	                if (!value.isEmpty()) {
	                	values.add(value);
	                }
	            }
	        }
		return values;
	}
	
	public static synchronized ArrayList<String> getEmailMessageContentAndLink(WebDriver driver, String email, String subject) throws InterruptedException {		
		email = email.substring(0, email.indexOf('@'));
		System.out.println("Email is -- "+email);
		driver.navigate().to("https://www.mailinator.com/v3/index.jsp?zone=public&query="+email+"#/#inboxpane");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='inboxpane']//table[@class='table table-striped jambo_table']//td/a[contains(text(), '"+subject+"')]")).click();	
		driver.switchTo().frame("msg_body");
		Thread.sleep(3000);
		String emailText = driver.findElement(By.xpath("/html/body/p")).getText().trim();
		ArrayList<String> result = new ArrayList<String>();
		result.add(emailText);
		String text = driver.findElement(By.xpath("/html/body/p/a[1]")).getAttribute("href").trim();
		result.add(text);
		return result;
	}
	
	public static synchronized boolean isEmailMessagePresentWithSubjectNameInInbox(WebDriver driver, String email, String subject) throws InterruptedException {		
		email = email.substring(0, email.indexOf('@'));
		System.out.println("Email is -- "+email);
		driver.navigate().to("https://www.mailinator.com/v3/index.jsp?zone=public&query="+email+"#/#inboxpane");
		Thread.sleep(3000);
		WebElement emailSubject = driver.findElement(By.xpath("//div[@id='inboxpane']//table[@class='table table-striped jambo_table']//td/a[contains(text(), '"+subject+"')]"));	
		if(emailSubject.isDisplayed() && emailSubject.isEnabled())
		{
			return true;
		}
		return false;
	}
	
	public static synchronized String getoAuth() {		
		String url = ConfigReader.getConfig("oauth_url");
		Response r = expect().given()
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "password")
				.formParam("client_id", ConfigReader.getConfig("client_id"))
				.formParam("client_secret", ConfigReader.getConfig("client_secret"))
				.formParam("username", ConfigReader.getConfig("oauth_username"))
				.formParam("password", ConfigReader.getConfig("oauth_password"))
				.log().all().when().post(url);
		String body = r.getBody().asString();
		System.out.println("Response body "+body);
		Assert.assertEquals(r.statusCode(), 200);
		JsonPath jsonPathEvaluator = r.jsonPath();		
		return jsonPathEvaluator.get("access_token").toString();
	}
	
	public static synchronized String authTokenIpWhiteList() {
		
		String url = ConfigReader.getConfig("ssoAuthTokenUrl");
		Response r = expect().given()
				.contentType("application/x-www-form-urlencoded")
				.formParam("email", ConfigReader.getConfig("ssoAuthTokenUsername"))
				.formParam("password", ConfigReader.getConfig("ssoAuthTokenPassword"))
				.formParam("platform_account_id", ConfigReader.getConfig("account_id"))
				.log().all().when().post(url);
		String body = r.getBody().asString();
		System.out.println("Response body "+body);
		Assert.assertEquals(r.statusCode(), 200);
		JsonPath jsonPathEvaluator = r.jsonPath();
		
		return jsonPathEvaluator.get("token").toString();
	}	
}
