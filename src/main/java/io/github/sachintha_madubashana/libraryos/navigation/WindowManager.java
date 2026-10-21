package io.github.sachintha_madubashana.libraryos.navigation;

import io.github.sachintha_madubashana.libraryos.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class WindowManager {
    private static final String LOGIN_VIEW = "view/login-view.fxml";
    private static final String MAIN_VIEW = "view/main-view.fxml";
    private static final String APP_ICON = "icon/icon.png";

    private void openStage(String view, String title, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Launcher.class.getResource(view)));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.getIcons().add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream(APP_ICON))));

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("Failed to open view: " + view, e);
        }
    }

    private void loginStage(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(LOGIN_VIEW));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1069, 600);
            scene.setFill(Color.TRANSPARENT);
            stage.getIcons().add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream(APP_ICON))));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setIconified(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to open view: " + LOGIN_VIEW, e);
        }
    }

    public void openLoginStage(Stage stage) {
        loginStage(stage);
    }

    public void openLoginStage() {
        loginStage(new Stage());
    }

    public void openMainStage() {
        openStage(MAIN_VIEW, "LibraryOS", 1069, 600);
    }
}
