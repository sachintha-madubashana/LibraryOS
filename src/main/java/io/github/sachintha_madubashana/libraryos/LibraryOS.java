package io.github.sachintha_madubashana.libraryos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LibraryOS extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource("css/main.css")).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1069, 600);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}
