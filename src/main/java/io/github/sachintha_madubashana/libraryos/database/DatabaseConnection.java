package io.github.sachintha_madubashana.libraryos.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;

    static {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(".env")){
            prop.load(input);

            String database = prop.getProperty("db.database", "library_os");
            String username = prop.getProperty("db.username", "root");
            String password = prop.getProperty("db.password");
            String port = prop.getProperty("db.port", "3306");


            connection = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + database, username, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
