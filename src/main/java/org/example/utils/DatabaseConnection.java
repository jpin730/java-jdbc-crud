package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database");
                System.out.println();
            } catch (SQLException e) {
                System.out.println("An error occurred in database connection");
                throw e;
            }
        }

        return connection;
    }
}
