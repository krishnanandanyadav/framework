package com.krishna.utilities;

import com.krishna.factories.DatabaseFactory;
import com.krishna.utilities.ConfigReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

	public static synchronized String getStringData(String query) throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		do {
			r = stmt.executeQuery(query);
			Thread.sleep(3000);
			x=x+1;
		} while (r.next() == false && x<=20);
		String value = r.getString(1);
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}

		return value;
	}
	
	public static synchronized int getIntData(String query) throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		do {
			r = stmt.executeQuery(query);
			Thread.sleep(3000);
			x=x+1;
		} while (r.next() == false && x<=20);
		int value = r.getInt(1);
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}

		return value;
	}

	public static synchronized int updateData (String query) throws Exception {
		if (!ConfigReader.getENVConfigValue().equalsIgnoreCase("prod")) {
		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
			int r = stmt.executeUpdate(query);
			Thread.sleep(3000);
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) { }
		return r;
		}
		else {
			return 0;
		}
	}
	
	public static synchronized String selectNow() throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		do {
			r = stmt.executeQuery("select now()");
			Thread.sleep(3000);
			x=x+1;
		} while (r.next() == false && x<=20);
		String value = r.getString(1);
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}

		return value;
	}

	public static ResultSet getResultSet(String query) throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet r = stmt.executeQuery(query);
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		return r;
	}
	
	public static Map<String, String> getResultMap(String query) throws Exception {
		
		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		do {
			r = stmt.executeQuery(query);
			Thread.sleep(3000);
			x=x+1;
		} while (r.next() == false && x<=20);
		Map<String, String> result = new HashMap<String, String>();
		ResultSetMetaData rsmd = r.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		for (int i = 1; i <= columnCount; i++ ) {
		  String name = rsmd.getColumnName(i);
		  result.put(name, r.getString(i));
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		return result;
	}
	
	public static synchronized Boolean isRecoredPresent(String query)
			throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		do {
			r = stmt.executeQuery(query);
			Thread.sleep(3000);
			x=x+1;
		} while (r.next() == false && x<=20);
		int value = r.getRow();
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		if (value >= 1)
			return true;
		return false;
	}
	
	public static synchronized ArrayList<String> getAllRowData(String query)
			throws Exception {

		Connection conn = DatabaseFactory.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		int x=0;
		ResultSet r;
		ArrayList<String> resultList = new ArrayList<String>();
		boolean istrue = false;
		
		do {
			r = stmt.executeQuery(query);
			Thread.sleep(3000);
			x=x+1;
			istrue = r.next();
			if (istrue) {
				resultList.add(r.getString(1));
			}
		} while (istrue == false && x<=20);
		
		String result;
		while (r.next()) {
		        result = r.getString(1); 
		        resultList.add(result);
		    }
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		return resultList;
	}
}
