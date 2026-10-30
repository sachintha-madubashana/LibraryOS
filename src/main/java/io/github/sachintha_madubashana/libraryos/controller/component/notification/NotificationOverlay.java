package io.github.sachintha_madubashana.libraryos.controller.component.notification;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class NotificationOverlay implements ForegroundOverlay {

    private final VBox container;

    public NotificationOverlay() {
        container = new VBox(10);

        container.setPickOnBounds(false);
        container.setMaxWidth(300);
        container.setMaxHeight(200);
        container.setAlignment(Pos.BOTTOM_CENTER);

        container.getStyleClass().add("notification-container");
    }

    @Override
    public VBox getContainer() {
        return container;
    }

    @Override
    public void show(Node node) {
        if (node instanceof AlertMessage alert) {
            alert.showInLast(container);
        }
    }
}
