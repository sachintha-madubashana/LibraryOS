package io.github.sachintha_madubashana.libraryos.model;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;

public class Notification {
    private String title;
    private String message;
    private Position position;
    private Type type;

    public Notification(String title, String message, Position position, Type type) {
        this.title = title;
        this.message = message;
        this.position = position;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Position{
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTRE
    }

    public enum Type{
        ALERT,
        DIALOG
    }
}
