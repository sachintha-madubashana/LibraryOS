module io.github.sachintha_madubashana.libraryos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires spring.security.crypto;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.core;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens io.github.sachintha_madubashana.libraryos.controller to javafx.fxml;
    opens io.github.sachintha_madubashana.libraryos.controller.component to javafx.fxml;
    exports io.github.sachintha_madubashana.libraryos;
    exports io.github.sachintha_madubashana.libraryos.model;
    exports io.github.sachintha_madubashana.libraryos.controller;
    exports io.github.sachintha_madubashana.libraryos.controller.component;
    exports io.github.sachintha_madubashana.libraryos.controller.component.table;
    opens io.github.sachintha_madubashana.libraryos.controller.component.table to javafx.fxml;
    exports io.github.sachintha_madubashana.libraryos.service;
    exports io.github.sachintha_madubashana.libraryos.common.result;
    exports io.github.sachintha_madubashana.libraryos.manager;
    exports io.github.sachintha_madubashana.libraryos.database;
    exports io.github.sachintha_madubashana.libraryos.database.entity;
    exports io.github.sachintha_madubashana.libraryos.repository;
    exports io.github.sachintha_madubashana.libraryos.repository.hibernate;
    opens io.github.sachintha_madubashana.libraryos.database.entity to org.hibernate.orm.core;
}