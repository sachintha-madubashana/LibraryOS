package io.github.sachintha_madubashana.libraryos.manager;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.notification.ForegroundOverlay;
import io.github.sachintha_madubashana.libraryos.model.Notification;

public class NotificationManager {
    private final ForegroundOverlay overlay;

    public NotificationManager(ForegroundOverlay overlay) {
        this.overlay = overlay;
    }

    public void showNotification(Notification notification) {
        AlertMessage alert = new AlertMessage(
                notification.getTitle(),
                notification.getMessage(),
                mapNotificationTypeToAlertType(notification.getType())
        );

        overlay.show(alert);
    }

    private AlertMessage.Type mapNotificationTypeToAlertType(Notification.Type type) {
        return switch (type) {
            case ALERT_ERROR -> AlertMessage.Type.ERROR;
            case ALERT_SUCCESS -> AlertMessage.Type.SUCCESS;
            case ALERT_WARNING -> AlertMessage.Type.WARNING;
            default -> AlertMessage.Type.INFO;
        };
    }
}
