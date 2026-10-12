package io.github.sachintha_madubashana.libraryos.controller.component.rows;

import io.github.sachintha_madubashana.libraryos.model.Book;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class BookAvailabilityRow extends HBox {
    public BookAvailabilityRow(Book book) {
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add("book-availability-row");

        Label bookTitle = new Label(book.getTitle());
        bookTitle.getStyleClass().add("book-title");

        Label details = new Label(book.getAuthor());
        details.getStyleClass().add("book-author");

        VBox information = new VBox(8, bookTitle, details);
        information.setAlignment(Pos.CENTER_LEFT);

        Label qty = new Label(book.getTotalQuantity() + " / " + book.getAvailableQuantity());
        Label status = new Label();
        if (book.getAvailableQuantity() == 0) {
            qty.getStyleClass().add("unavailable");
            status.setText("Unavailable");
            status.getStyleClass().add("unavailable");
        } else {
            qty.getStyleClass().add("available");
            status.setText("Available");
            status.getStyleClass().add("available");
        }

        VBox availabilityInformation = new VBox(8, qty, status);
        availabilityInformation.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().setAll(information, spacer, availabilityInformation);
    }
}
