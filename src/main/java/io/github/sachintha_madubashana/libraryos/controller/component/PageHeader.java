package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PageHeader extends VBox {

    private String placeholder;
    private String title;
    private String subtitle;

    public PageHeader() {
        this("This is placeholder", "This is title", "This is subtitle");
    }

    public PageHeader(String placeholder, String title, String subtitle) {
        this.placeholder = placeholder;
        this.title = title;
        this.subtitle = subtitle;
        initialize();
    }

    private void initialize() {
        this.getChildren().add(new Label(placeholder));
        this.getChildren().add(new Label(title));
        this.getChildren().add(new Label(subtitle));
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
