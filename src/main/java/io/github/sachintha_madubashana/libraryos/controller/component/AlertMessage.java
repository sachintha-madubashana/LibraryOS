package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlertMessage extends VBox {
    private final Label titleLabel;
    private final Label messageLabel;

    public AlertMessage(String title, String message) {
        getStyleClass().add("alert-message");

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("alert-title");

        messageLabel = new Label(message);
        messageLabel.getStyleClass().add("alert-message-text");
        messageLabel.setWrapText(true);

        setSpacing(4);
        setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(titleLabel, messageLabel);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getMessage() {
        return messageLabel.getText();
    }
}
