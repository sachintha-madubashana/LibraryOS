package io.github.sachintha_madubashana.libraryos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Button closeBtn;

    @FXML
    private Button loinBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private void initialize() {
        closeBtn.setOnAction(event -> onCloseButtonClick());
        loinBtn.setOnAction(event -> onLoginButtonClick());
        clearBtn.setOnAction(event -> onClearButtonClick());
    }

    private void onCloseButtonClick() {
        System.exit(0);
    }

    private void onLoginButtonClick() {
        System.out.println("Login button clicked");
    }

    private void onClearButtonClick() {
        System.out.println("Clear button clicked");
    }
}
