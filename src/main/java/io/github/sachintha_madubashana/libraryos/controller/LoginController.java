package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.model.AuthenticationService;
import io.github.sachintha_madubashana.libraryos.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class LoginController {
    @FXML
    private Button closeBtn;

    @FXML
    private Button loinBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private InputField usernameField;

    @FXML
    private InputField passwordField;

    private AuthenticationService authenticationService;

    @FXML
    private void initialize() {
        authenticationService = new AuthenticationService();
        usernameField.setOnKeyPressed(this::focusPasswordField);
        passwordField.setOnKeyPressed(this::handleRootKeyPressed);

        FontIcon icon = new FontIcon(Feather.X);
        icon.setIconSize(16);
        icon.getStyleClass().add("close-icon");
        closeBtn.setGraphic(icon);

        closeBtn.setOnAction(event -> onCloseButtonClick());
        loinBtn.setOnAction(event -> onLoginButtonClick());
        clearBtn.setOnAction(event -> onClearButtonClick());
    }

    private void onCloseButtonClick() {
        System.exit(0);
    }

    private void onLoginButtonClick() {
        User credential = new User(usernameField.getInputValue(), passwordField.getInputValue());
        if (authenticationService.authenticate(credential)) {
            try {
                Stage stage = (Stage) loinBtn.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Library OS");
                FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(Launcher.class.getResource("view/main-view.fxml")));
                primaryStage.setScene(new Scene(fxml.load(), 1069, 600));
                primaryStage.show();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login failed");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Credentials are wrong");
            alert.showAndWait();
        }
    }

    private void focusPasswordField(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
        passwordField.requestFocus();
            event.consume();
        }
    }

    private void handleRootKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLoginButtonClick();
            event.consume();
        }
    }

    private void onClearButtonClick() {
        usernameField.clear();
        passwordField.clear();
    }
}
