package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class AlertMessage extends HBox {

    private static final Duration DEFAULT_DISMISS_DURATION = Duration.seconds(3);

    private final Label titleLabel;
    private final Label messageLabel;
    private Timeline dismissTimeline;
    private Duration dismissDuration;

    public AlertMessage(String title, String message, Type type) {
        getStyleClass().add("alert-message");

        FontIcon fontIcon = new FontIcon();
        fontIcon.getStyleClass().add("alert-icon");
        fontIcon.setIconCode(type.getIconName());


        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("alert-title");

        messageLabel = new Label(message);
        messageLabel.getStyleClass().add("alert-message-text");
        messageLabel.setWrapText(true);

        VBox messageBox = new VBox(4);
        HBox.setHgrow(messageBox, Priority.ALWAYS);
        messageBox.getChildren().addAll(titleLabel,messageLabel);


        Button closeButton = new Button();
        closeButton.setGraphic(new FontIcon(Feather.X));
        closeButton.getStyleClass().add("alert-close-button");
        closeButton.setOnAction(event -> dismiss());

        getChildren().addAll(fontIcon, messageBox,closeButton);

        setType(type);
        setOnMouseEntered(event -> pause());
        setOnMouseExited(event -> resume());
    }

    public void setType(Type type) {
        getStyleClass().removeAll("alert-success", "alert-error", "alert-warning", "alert-info");
        getStyleClass().add(type.getCssClass());
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

    public void showIn(Pane parent) {
        showIn(parent, DEFAULT_DISMISS_DURATION);
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
        SUCCESS("alert-success", Feather.CHECK_CIRCLE),
        ERROR("alert-error", Feather.X_CIRCLE),
        WARNING("alert-warning", Feather.ALERT_TRIANGLE),
        INFO("alert-info", Feather.ALERT_CIRCLE);

        private final String cssClass;
        private final Feather iconName;

        Type(String cssClass, Feather iconName) {
            this.cssClass = cssClass;
            this.iconName = iconName;
        }

        public String getCssClass() { return cssClass; }
        public Feather getIconName() { return iconName; }
    }
}
