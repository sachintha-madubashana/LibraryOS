package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Book;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AddBookController {

    @FXML
    private HBox root;

    @FXML
    private VBox card;

    @FXML
    private VBox tableContainer;

    @FXML
    private InputField bookId;

    @FXML
    private InputField bookTitle;

    @FXML
    private InputField bookAuthor;

    @FXML
    private DropdownSelect<String> categoryField;

    @FXML
    private InputField bookQty;

    @FXML
    private InputField bookYear;


    @FXML
    private void initialize() {
        generateCard();
        generateTable();
    }

    private void generateCard() {
        categoryField.getItems().addAll("Fiction", "Non-Fiction", "Science", "History", "Biography");
        categoryField.setValue("Select");

//        card.setPadding(new Insets(28));
//        Pagination pagination = new Pagination();
//
//        pagination.setPageCount(50);
//        pagination.setCurrentPage(1);
//
//        pagination.currentPageProperty().addListener(
//                (observable, oldPage, newPage) -> {
//                    int page = newPage.intValue();
//
//                    System.out.println("Current page: " +page);
//                }
//        );
//
//        card.getChildren().add(pagination);
//
//
    }

    private void generateTable() {
        DataTable<Book> table = new DataTable<>();
        table.setMaxHeight(360);

        table.addColumns(
                DataColumn.of("ID", Book::getId).width(70),
                DataColumn.of("TITLE", Book::getTitle).width(240),
                DataColumn.of("AUTHOR", Book::getAuthor).width(220),
                DataColumn.of("CATEGORY", Book::getCategory).width(180),
                DataColumn.of("QTY", Book::getQuantity).width(80)
        );

        List<Book> books = new ArrayList<>();
        books.add(new Book("BK001", "To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("BK002", "1984", "George Orwell", "Fiction", 5));
        books.add(new Book("BK003", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 8));
        books.add(new Book("BK004", "Pride and Prejudice", "Jane Austen", "Fiction", 7));
        books.add(new Book("BK005", "The Catcher in the Rye", "J.D. Salinger", "Fiction", 6));
        books.add(new Book("BK006", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 12));
        books.add(new Book("BK007", "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 15));
        books.add(new Book("BK008", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 20));
        books.add(new Book("BK009", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 18));
        books.add(new Book("BK010", "The Shining", "Stephen King", "Horror", 10));
        books.add(new Book("BK011", "The Da Vinci Code", "Dan Brown", "Thriller", 14));
        books.add(new Book("BK012", "The Chronicles of Narnia", "C.S. Lewis", "Fantasy", 16));
        books.add(new Book("BK013", "The Lord of the Flies", "William Golding", "Fiction", 9));

        table.setItems(books);
        table.setSelectionMode(SelectionMode.MULTIPLE);

        Pagination pagination = new Pagination();
        pagination.setPageCount(8);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();

                    System.out.println("Current page: " +page);
                }
        );

        HBox paginationContainer = new HBox();
        paginationContainer.setAlignment(Pos.CENTER_RIGHT);
        paginationContainer.getChildren().add(pagination);

        tableContainer.getChildren().add(table);
        tableContainer.getChildren().add(paginationContainer);
    }
}
