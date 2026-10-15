package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.model.Book;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BookInfoCard extends VBox {

    private final Layout layout;
    private final Book book;
    private final StackPane imageContainer;
    private final ImageView imageView;

    public BookInfoCard(Book book, Layout layout) {
        imageContainer = new StackPane();
        imageView = new ImageView();
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }

        if (layout == null) {
            throw new IllegalArgumentException("Layout cannot be null.");
        }

        this.book = book;
        this.layout = layout;

        getStyleClass().add("book-info-card");

        Label sectionLabel = new Label("BOOK INFO");
        sectionLabel.getStyleClass().add("book-info-section-label");
        getChildren().add(sectionLabel);

        imageContainer.getStyleClass().add("book-info-image-container");
        imageView.getStyleClass().add("book-info-image");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageContainer.getChildren().add(imageView);
        imageContainer.setAlignment(Pos.CENTER);

        Label titleLabel = createLabel(book.getTitle(), "book-info-name");
        Label authorLabel = createLabel(book.getAuthor(), "book-info-id");
        Label idLabel = createLabel(book.getId(), "book-info-detail");

        Label availabilityLabel = createLabel("AVAILABLE", "book-info-quantity-label");
        Label availabilityCountLabel = createLabel(String.valueOf(book.getAvailableQuantity()), "book-info-available-count");

        Label totalLabel = createLabel("TOTAL", "book-info-quantity-label");
        Label totalCountLabel = createLabel(String.valueOf(book.getTotalQuantity()), "book-info-total-count");

        VBox availabilityContainer = new VBox(availabilityLabel, availabilityCountLabel);
        availabilityContainer.getStyleClass().add("book-info-quantity-container");

        VBox totalContainer = new VBox(totalLabel, totalCountLabel);
        totalContainer.getStyleClass().add("book-info-quantity-container");

        HBox availabilityTotalContainer = new HBox(availabilityContainer, totalContainer);
        availabilityTotalContainer.getStyleClass().add("book-info-quantity-section");
        availabilityTotalContainer.setAlignment(Pos.CENTER_LEFT);

        VBox detailsContainer = new VBox(titleLabel, authorLabel, idLabel, availabilityTotalContainer);
        detailsContainer.getStyleClass().add("book-info-details");

        if (layout == Layout.LARGE) {
            configureLargeLayout(detailsContainer);
        } else {
            configureCompactLayout(detailsContainer);
        }

    }

    private void configureLargeLayout(VBox detailsContainer) {
        getStyleClass().add("large");
        imageContainer.getStyleClass().add("large");

        imageView.fitWidthProperty().bind(imageContainer.widthProperty());
        imageView.fitHeightProperty().bind(imageContainer.heightProperty());
        VBox.setVgrow(imageContainer, Priority.ALWAYS);

        getChildren().addAll(imageContainer, detailsContainer);
    }

    private void configureCompactLayout(VBox detailsContainer) {
        getStyleClass().add("compact");
        imageContainer.getStyleClass().add("compact");

        HBox content = new HBox(10, imageContainer, detailsContainer);
        content.getStyleClass().add("book-info-compact-content");
        content.setAlignment(Pos.TOP_LEFT);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        imageContainer.setMinWidth(100);
        imageContainer.setPrefWidth(100);
        imageContainer.setMaxWidth(100);

        imageContainer.setMinHeight(100);
        imageContainer.setPrefHeight(100);
        imageContainer.setMaxHeight(100);

        HBox.setHgrow(detailsContainer, Priority.ALWAYS);
        getChildren().add(content);
    }

    private Label createLabel(String text, String styleClass) {
        Label label = new Label(text == null ? "" : text);

        label.getStyleClass().add(styleClass);
        label.setWrapText(true);

        return label;
    }

    public void setBookImage(Image image) {
        imageView.setImage(image);
    }

    public void setBookImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            imageView.setImage(null);
            return;
        }

        imageView.setImage(new Image(imageUrl, true));
    }

    public Layout getLayout() {
        return layout;
    }

    public Book getBook() {
        return book;
    }

    public enum Layout {
        LARGE,
        COMPACT
    }
}