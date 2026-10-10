package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class InfoCard extends HBox {

    private final StackPane iconContainer;
    private final FontIcon icon;

    private final Label numberLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;

    public InfoCard() {
        this("", "", "", "");
    }

    public InfoCard(Feather icon, String number, String title, String description) {
        this(icon.getDescription(), number, title, description);
    }

    public InfoCard(String iconLiteral, String number, String title, String description) {
        getStyleClass().add("info-card");

        icon = new FontIcon();
        if (iconLiteral != null && !iconLiteral.isEmpty()) {
            icon.setIconLiteral(iconLiteral);
        }
        icon.getStyleClass().add("info-card-icon");

        iconContainer = new StackPane(icon);
        iconContainer.getStyleClass().add("info-card-icon-container");

        numberLabel = new Label(number);
        numberLabel.getStyleClass().add("info-card-number");

        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("info-card-title");

        descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("info-card-description");
        descriptionLabel.setWrapText(true);

        VBox content = new VBox(numberLabel, titleLabel, descriptionLabel);

        content.getStyleClass().add("info-card-content");
        content.setAlignment(Pos.CENTER_LEFT);

        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(iconContainer, content);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public String getNumber() {
        return numberLabel.getText();
    }

    public void setNumber(String number) {
        numberLabel.setText(number);
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public String getIcon() {
        return icon.getIconLiteral();
    }

    public void setIcon(String iconLiteral) {
        icon.setIconLiteral(iconLiteral);
    }
}
