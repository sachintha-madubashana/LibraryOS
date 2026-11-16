package io.github.sachintha_madubashana.libraryos.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }

        return connection;
    }

    private static void createConnection() throws SQLException {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream(".env")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }

        String database = prop.getProperty("db.database", "library_os");
        String username = prop.getProperty("db.username", "root");
        String password = prop.getProperty("db.password");
        String port = prop.getProperty("db.port", "3306");

        String url = "jdbc:mysql://localhost:" + port + "/" + database;

        connection = DriverManager.getConnection(url, username, password);
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection", e);
            }
        }
    }
}
