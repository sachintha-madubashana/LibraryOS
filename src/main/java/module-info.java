module io.github.sachintha_madubashana.libraryos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires spring.security.crypto;

    opens io.github.sachintha_madubashana.libraryos.controller to javafx.fxml;
    exports io.github.sachintha_madubashana.libraryos;
    exports io.github.sachintha_madubashana.libraryos.model;
    exports io.github.sachintha_madubashana.libraryos.controller;
    exports io.github.sachintha_madubashana.libraryos.controller.component;
}