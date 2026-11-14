package io.github.sachintha_madubashana.libraryos.database;

import io.github.sachintha_madubashana.libraryos.database.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateSessionFactory {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties prop = new Properties();
            try (InputStream input = new FileInputStream(".env")) {
                prop.load(input);
            }

            String database = prop.getProperty("db.database", "library_os");
            String username = prop.getProperty("db.username", "root");
            String password = prop.getProperty("db.password", "");
            String port = prop.getProperty("db.port", "3306");

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:" + port + "/" + database);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addAnnotatedClass(CategoryEntity.class);
            configuration.addAnnotatedClass(BookEntity.class);
            configuration.addAnnotatedClass(MemberEntity.class);
            configuration.addAnnotatedClass(BorrowingRecordEntity.class);
            configuration.addAnnotatedClass(BorrowingStatusEntity.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
