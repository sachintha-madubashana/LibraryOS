package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.model.AuthenticationService;
import io.github.sachintha_madubashana.libraryos.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
    @FXML
    private Button closeBtn;

    @FXML
    private Button loinBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private AuthenticationService authenticationService;

    @FXML
    private void initialize() {
        authenticationService = new AuthenticationService();
        usernameField.setOnKeyPressed(this::focusPasswordField);
        passwordField.setOnKeyPressed(this::handleRootKeyPressed);

        closeBtn.setOnAction(event -> onCloseButtonClick());
        loinBtn.setOnAction(event -> onLoginButtonClick());
        clearBtn.setOnAction(event -> onClearButtonClick());
    }

    private void onCloseButtonClick() {
        System.exit(0);
    }

    private void onLoginButtonClick() {
        User credential = new User(usernameField.getText(), passwordField.getText());
        if (authenticationService.authenticate(credential)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Login successful");
            alert.setContentText("This is the main message content");
            alert.showAndWait();
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
        System.out.println("Clear button clicked");
    }
}
