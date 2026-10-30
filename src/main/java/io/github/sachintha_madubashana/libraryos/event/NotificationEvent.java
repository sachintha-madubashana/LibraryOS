package io.github.sachintha_madubashana.libraryos.event;

import io.github.sachintha_madubashana.libraryos.model.Notification;
import javafx.event.Event;
import javafx.event.EventType;

public class NotificationEvent extends Event {
    public static final EventType<NotificationEvent> NOTIFICATION = new EventType<>(Event.ANY, "NOTIFICATION");

    private final transient Notification targetNotification;

    public NotificationEvent(Notification notification){
        super(NOTIFICATION);
        this.targetNotification = notification;
    }

    public Notification getTargetNotification(){
        return targetNotification;
    }
}
