package com.bank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// JDBC connection
public class DBConnection {
    public static Connection connection = null;
    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Exception at getConnection() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }

}
