package com.example.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection;
    private static final String username = "user";
    private static final String password = "12345";
    private static final String dbname = "db";

    private DbConnection() {

    }

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(
                        String.format("jdbc:h2:file:./db/%s;INIT=RUNSCRIPT FROM 'classpath:schema.sql'" +
                                "\\;RUNSCRIPT FROM 'classpath:data.sql'", dbname),
                        username,
                        password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
