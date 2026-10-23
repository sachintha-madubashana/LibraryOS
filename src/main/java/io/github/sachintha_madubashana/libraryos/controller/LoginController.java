package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.navigation.WindowManager;
import io.github.sachintha_madubashana.libraryos.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

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

    @FXML
    private VBox errorContainer;

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

//        Platform.runLater(() -> loinBtn.fire());
    }

    private void onCloseButtonClick() {
        System.exit(0);
    }

    private void onLoginButtonClick() {
        User credential = new User(usernameField.getInputValue(), passwordField.getInputValue());
        Result<User> result = authenticationService.authenticate(credential);
        if (result.isSuccess()) {
            try {
                Stage stage = (Stage) loinBtn.getScene().getWindow();
                stage.close();
                new WindowManager().openMainStage();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login failed");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            showError(result);
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

    private void showError(Result<User> result) {
        errorContainer.setVisible(true);
        errorContainer.getChildren().clear();
        AlertMessage alertMessage = new AlertMessage(result);
        alertMessage.showIn(errorContainer, Duration.seconds(10));
    }
}
