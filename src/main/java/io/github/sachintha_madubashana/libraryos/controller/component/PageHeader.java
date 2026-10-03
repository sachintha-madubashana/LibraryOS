package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PageHeader extends VBox {

    private String placeholder;
    private String title;
    private String subtitle;

    private Label placeholderLabel;
    private Label titleLabel;
    private Label subtitleLabel;

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
        getStyleClass().add("page-header");

        placeholderLabel = new Label(placeholder);
        placeholderLabel.getStyleClass().add("page-header-placeholder");

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("page-header-title");

        subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("page-header-subtitle");

        setSpacing(4);
        subtitleLabel.setPadding(new Insets(0, 0, 8, 0));

        getChildren().addAll(placeholderLabel, titleLabel, subtitleLabel);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        placeholderLabel.setText(placeholder);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleLabel.setText(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        subtitleLabel.setText(subtitle);
    }
}
