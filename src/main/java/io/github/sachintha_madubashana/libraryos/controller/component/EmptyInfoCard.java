package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class EmptyInfoCard extends StackPane implements InfoCard {
    private final StackPane iconContainer;
    private final String title;
    private final String description;
    private final Feather icon;
    private LayoutType layoutType;

    public EmptyInfoCard(String title, String description, Feather icon, LayoutType layoutType) {
        this.iconContainer = new StackPane();
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.layoutType = layoutType;

        getStyleClass().add("empty-info-card");
        configureLayout();
    }

    @Override
    public void configureLayout() {
        getChildren().clear();
        iconContainer.getChildren().clear();

        iconContainer.getStyleClass().add("empty-info-icon-container");

        FontIcon fontIcon = new FontIcon(icon.getDescription());
        fontIcon.getStyleClass().add("empty-info-icon");

        iconContainer.getChildren().add(fontIcon);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("empty-info-title");

        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("empty-info-description");
        descriptionLabel.setWrapText(true);

        if (layoutType == LayoutType.LARGE) {
            configureLargeLayout(titleLabel, descriptionLabel);
        } else {
            configureSmallLayout(titleLabel, descriptionLabel);
        }
    }

    private void configureLargeLayout(Label titleLabel, Label descriptionLabel) {
        getStyleClass().remove("empty-info-card-compact");

        if (!getStyleClass().contains("empty-info-card-large")) {
            getStyleClass().add("empty-info-card-large");
        }

        VBox content = new VBox(
                iconContainer,
                titleLabel,
                descriptionLabel
        );

        content.getStyleClass().add("empty-info-content");
        content.setAlignment(Pos.CENTER);

        getChildren().setAll(content);
    }

    private void configureSmallLayout(Label titleLabel, Label descriptionLabel) {
        getStyleClass().remove("empty-info-card-large");

        if (!getStyleClass().contains("empty-info-card-compact")) {
            getStyleClass().add("empty-info-card-compact");
        }

        VBox content = new VBox(titleLabel, descriptionLabel);
        content.getStyleClass().add("empty-info-content");
        content.setAlignment(Pos.CENTER_LEFT);

        HBox row = new HBox(iconContainer, content);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(28);

        getChildren().setAll(row);
    }

    @Override
    public LayoutType getLayout() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
        configureLayout();
    }
}
