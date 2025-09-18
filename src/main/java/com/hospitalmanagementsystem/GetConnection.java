package com.hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
	private static Connection c = null;
	private static final String URL = "jdbc:mysql://localhost:3306/hospital?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Narendra@1234";
	
	private GetConnection() {
		// Private constructor
	}
	
	public static Connection getConnection() {
		if(c == null) {
			try {
				// Load MySQL driver explicitly
				Class.forName("com.mysql.cj.jdbc.Driver");
				c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				System.out.println("Database connected successfully!");
			} catch (ClassNotFoundException e) {
				System.err.println("MySQL Driver not found: " + e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				System.err.println("Database connection failed: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return c;
	}
}
