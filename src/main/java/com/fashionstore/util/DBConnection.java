package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Gagan@2003";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("✅ DB Connected");
            }

            return conn;

        } catch (Exception e) {
            try {
                java.io.FileWriter fw = new java.io.FileWriter("c:/Users/user/eclipse-workspace/db_error.txt");
                java.io.PrintWriter pw = new java.io.PrintWriter(fw);
                e.printStackTrace(pw);
                pw.close();
            } catch(Exception ex) {}
            System.out.println("❌ DB Connection Failed");
            e.printStackTrace();
        }
        return null;
    }
}