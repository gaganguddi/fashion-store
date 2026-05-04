package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        try {
            // Load the driver explicitly for Tomcat/Render environments
            Class.forName("com.mysql.cj.jdbc.Driver");

            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            System.out.println("DEBUG: Connecting to DB at " + host + ":" + port + "/" + database);

            if (host == null || port == null || database == null) {
                System.out.println("❌ ERROR: Missing database environment variables!");
                return null;
            }

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("✅ DB CONNECTED SUCCESSFULLY");
                return conn;
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ DB CONNECTION FAILED: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}