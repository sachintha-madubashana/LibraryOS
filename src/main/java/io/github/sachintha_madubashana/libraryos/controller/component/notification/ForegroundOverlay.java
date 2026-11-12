package io.github.sachintha_madubashana.libraryos.controller.component.notification;

import javafx.scene.Node;

public interface ForegroundOverlay {
    Node getContainer();
    void show(Node node);
}
