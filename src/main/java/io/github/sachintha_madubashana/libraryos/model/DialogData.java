package io.github.sachintha_madubashana.libraryos.model;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertDialog;
import org.kordamp.ikonli.javafx.FontIcon;

public class DialogData {
    private AlertDialog.Type type;
    private String title;
    private String description;
    private FontIcon icon;
    private String confirmButtonText;
    private String cancelButtonText;
    private Runnable onConfirm;
    private Runnable onCancel;

    public DialogData() {
    }

    public DialogData(AlertDialog.Type type, String title, String description, FontIcon icon, String confirmButtonText, String cancelButtonText, Runnable onConfirm, Runnable onCancel) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
    }

    public AlertDialog.Type getType() {
        return type;
    }

    public void setType(AlertDialog.Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FontIcon getIcon() {
        return icon;
    }

    public void setIcon(FontIcon icon) {
        this.icon = icon;
    }

    public String getConfirmButtonText() {
        return confirmButtonText;
    }

    public void setConfirmButtonText(String confirmButtonText) {
        this.confirmButtonText = confirmButtonText;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    public Runnable getOnConfirm() {
        return onConfirm;
    }

    public void setOnConfirm(Runnable onConfirm) {
        this.onConfirm = onConfirm;
    }

    public Runnable getOnCancel() {
        return onCancel;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}
