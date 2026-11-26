package io.github.sachintha_madubashana.libraryos.database;

import io.github.sachintha_madubashana.libraryos.database.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {
    }

    public static synchronized  SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            createSessionFactory();
        }

        return sessionFactory;
    }

    private static void createSessionFactory() {
            Properties prop = new Properties();
            try (InputStream input = new FileInputStream(".env")) {
                prop.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load .env file", e);
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
            configuration.setProperty("hibernate.hbm2ddl.auto", prop.getProperty("hibernate.ddl-auto", "validate"));

            configuration.addAnnotatedClass(UserEntity.class);
            configuration.addAnnotatedClass(CategoryEntity.class);
            configuration.addAnnotatedClass(BookEntity.class);
            configuration.addAnnotatedClass(MemberEntity.class);
            configuration.addAnnotatedClass(BorrowingRecordEntity.class);
            configuration.addAnnotatedClass(BorrowingStatusEntity.class);

            sessionFactory = configuration.buildSessionFactory();
    }

    public static synchronized void closeSessionFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
                sessionFactory = null;
            } catch (Exception e) {
                throw new RuntimeException("Failed to close Hibernate session", e);
            }
        }
    }
}
