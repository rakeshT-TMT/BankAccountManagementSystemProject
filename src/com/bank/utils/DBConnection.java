package com.bank.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// JDBC connection
public class DBConnection {
    static String URL="jdbc:mysql://localhost:3306/bankdb";
    static String USER="root";
    static String PASSWORD="root";

    public static Connection connection=null;
    public static Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
