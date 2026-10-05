package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AlertMessage extends VBox {

    private final Label titleLabel;
    private final Label messageLabel;
    private Timeline dismissTimeline;
    private Duration dismissDuration = Duration.seconds(3);

    public AlertMessage(String title, String message, Type type) {
        getStyleClass().add("alert-message");

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("alert-title");

        messageLabel = new Label(message);
        messageLabel.getStyleClass().add("alert-message-text");
        messageLabel.setWrapText(true);

        setSpacing(4);
        setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(titleLabel, messageLabel);

        setType(type);
        setOnMouseEntered(event -> {
            System.out.println("Mouse entered");
            pause();
        });
        setOnMouseExited(event -> {
            System.out.println("Mouse exited");
            resume();
        });
    }

    public void setType(Type type) {
        getStyleClass().removeAll("alert-success", "alert-error", "alert-warning", "alert-info");

        getStyleClass().add(
                switch (type) {
                    case SUCCESS -> "alert-success";
                    case ERROR -> "alert-error";
                    case WARNING -> "alert-warning";
                    case INFO -> "alert-info";
                }
        );
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public String getMessage() {
        return messageLabel.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void showIn(Pane parent, Duration duration) {
        dismissDuration = duration;
        if (getParent() != null) {
            return;
        }

        parent.getChildren().add(0, this);
        startDismissTimer();
    }

    public void dismiss() {
        if (dismissTimeline != null) {
            dismissTimeline.stop();
            dismissTimeline = null;
        }

        Parent parent = getParent();

        if (parent instanceof Pane pane) {
            pane.getChildren().remove(this);
        }
    }

    private void startDismissTimer() {
        if (dismissTimeline != null) {
            dismissTimeline.stop();
        }

        dismissTimeline = new Timeline(new KeyFrame(dismissDuration, event -> dismiss()));
        dismissTimeline.play();
    }

    public void restart() {
        if (dismissTimeline != null) {
            dismissTimeline.stop();
            dismissTimeline.playFromStart();
        }
    }

    public void pause() {
        if (dismissTimeline != null) {
            dismissTimeline.pause();
        }
    }

    public void resume() {
        if (dismissTimeline != null) {
            dismissTimeline.play();
        }
    }

    public enum Type {
        SUCCESS,
        ERROR,
        WARNING,
        INFO
    }
}
