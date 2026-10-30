package io.github.sachintha_madubashana.libraryos.model;


public class Notification {
    private String title;
    private String message;
    private Type type;

    public Notification(String title, String message, Type type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        ALERT_ERROR,
        ALERT_INFO,
        ALERT_SUCCESS,
        ALERT_WARNING,
        DIALOG
    }
}
