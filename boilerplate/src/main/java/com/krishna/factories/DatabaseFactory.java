package com.krishna.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.krishna.utilities.ConfigReader;

public class DatabaseFactory {
	private static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

	private DatabaseFactory() {
	}

	public static synchronized Connection getConnection() throws SQLException {

		if (threadConnection.get() == null) {
			Connection connection = createConnection();
			threadConnection.set(connection);
			return threadConnection.get();
		} else
			return threadConnection.get();
	}

	private static synchronized Connection createConnection() {
		try { Class.forName("org.postgresql.Driver").newInstance();} catch (Exception e) {
			System.out.println("postgresql Driver is not found , Error : - " + e.getMessage());
			e.printStackTrace();
		} try {
			String jdbcURL = ConfigReader.getConfig("jdbcURL");
			String jdbcUser = ConfigReader.getConfig("jdbcUser");
			String jdbcPassword = ConfigReader.getConfig("jdbcPassword");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
			System.out.println("Sucessfully Connected to DB");
			System.out.println("Starting DB Connection for threadId - " + Thread.currentThread().getId());
			return con;
		} catch (SQLException e) { System.out.println("not able to connect to postgresql , Error : - " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized void closeConnection() {
		try {
			if (threadConnection.get() != null) {
				threadConnection.get().close();
				threadConnection.remove();
				System.out.println("Closing DB Connection for threadId - " + Thread.currentThread().getId());
			}
		} catch (SQLException e) {
			throw new RuntimeException("Unable to close the connection", e);
		}
	}
}
