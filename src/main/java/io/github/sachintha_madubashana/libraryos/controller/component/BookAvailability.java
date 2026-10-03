package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.model.Book;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class BookAvailability extends VBox {

    private final VBox bookAvailabilityList = new VBox();

    public BookAvailability() {
        getStyleClass().add("book-availability");

        HBox header = createHeader();

        ScrollPane scrollPane = new ScrollPane(bookAvailabilityList);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        bookAvailabilityList.getStyleClass().add("book-list");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(header, scrollPane);
    }

    private HBox createHeader() {
        Label title = new Label("Book Availability");
        title.getStyleClass().add("book-header-title");

        Button addBtn = new Button("ADD BOOK");
        addBtn.getStyleClass().add("view-all-button");

        addBtn.setOnAction(e -> {
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(title, spacer, addBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("book-header");

        return header;
    }

    public void setBooks(List<Book> books) {
        bookAvailabilityList.getChildren().clear();

        for (Book book : books) {
            bookAvailabilityList.getChildren().add(
                    createBookRow(book)
            );
        }
    }

    public void addBook(Book book) {
        bookAvailabilityList.getChildren().add(
                createBookRow(book)
        );
    }

    private HBox createBookRow(Book book) {
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

        HBox row = new HBox(information, spacer, availabilityInformation);

        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("book-availability-row");

        return row;
    }
}
