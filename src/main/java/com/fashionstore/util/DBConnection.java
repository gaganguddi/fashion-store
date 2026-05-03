package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://tramway.proxy.rlwy.net:38862/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "waBNmOFhFnYTBXVXGVyuZaEqfYiCEBgp"; // your password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("✅ DB Connected SUCCESSFULLY (CLOUD)");
            }

            return conn;

        } catch (Exception e) {
            System.out.println("❌ DB Connection Failed (CLOUD)");
            e.printStackTrace();
        }
        return null;
    }
}