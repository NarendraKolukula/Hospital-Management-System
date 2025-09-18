package com.hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = GetConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
            try {
                System.out.println("Database: " + conn.getCatalog());
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection failed!");
        }
    }
}