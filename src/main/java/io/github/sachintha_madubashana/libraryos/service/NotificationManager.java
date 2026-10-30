package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.model.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NotificationManager {
    private int x = 0;

    public void showNotification(VBox root, Notification notification) {
        AlertMessage.Type alertType = notification.getType() == Notification.Type.ALERT
                ? AlertMessage.Type.ERROR
                : AlertMessage.Type.INFO;

        AlertMessage alert = new AlertMessage(notification.getTitle(), notification.getMessage() + x++, alertType);

        changeAlertPosition(root, notification.getPosition());
        alert.showInLast(root);
    }

    private void changeAlertPosition(VBox node, Notification.Position position) {
        switch (position) {
            case TOP_LEFT     -> {
                StackPane.setAlignment(node, Pos.TOP_LEFT);
                node.setAlignment(Pos.TOP_CENTER);
            }
            case TOP_RIGHT    -> {
                StackPane.setAlignment(node, Pos.TOP_RIGHT);
                node.setAlignment(Pos.TOP_CENTER);
            }
            case BOTTOM_LEFT  -> {
                StackPane.setAlignment(node, Pos.BOTTOM_LEFT);
                node.setAlignment(Pos.BOTTOM_CENTER);
            }
            case BOTTOM_RIGHT -> {
                StackPane.setAlignment(node, Pos.BOTTOM_RIGHT);
                node.setAlignment(Pos.BOTTOM_CENTER);
            }
            case CENTRE       -> {
                StackPane.setAlignment(node, Pos.CENTER);
                node.setAlignment(Pos.CENTER);
            }
        }

        StackPane.setMargin(node, new Insets(20));
    }
}
