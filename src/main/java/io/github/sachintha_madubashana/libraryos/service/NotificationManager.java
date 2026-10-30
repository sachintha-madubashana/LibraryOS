package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.model.Notification;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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

    private void changeAlertPosition(Node alertNode, Notification.Position position) {
        AnchorPane.clearConstraints(alertNode);
        switch (position) {
            case TOP_LEFT -> {
                AnchorPane.setTopAnchor(alertNode, 20.0);
                AnchorPane.setLeftAnchor(alertNode, 20.0);
            }
            case TOP_RIGHT -> {
                AnchorPane.setTopAnchor(alertNode, 20.0);
                AnchorPane.setRightAnchor(alertNode, 20.0);
            }
            case BOTTOM_LEFT -> {
                AnchorPane.setBottomAnchor(alertNode, 20.0);
                AnchorPane.setLeftAnchor(alertNode, 20.0);
            }
            case BOTTOM_RIGHT -> {
                AnchorPane.setBottomAnchor(alertNode, 20.0);
                AnchorPane.setRightAnchor(alertNode, 20.0);
            }
        }
    }
}
