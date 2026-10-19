package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
    private Button addBookBtn;

    @FXML
    private Button clearBtn;

    private DataTable<Book> table;

    @FXML
    private void initialize() {
        card.setMaxHeight(Region.USE_PREF_SIZE);
        generateCard();
        generateTable();

        addBookBtn.setOnAction(e -> addBook());
        clearBtn.setOnAction( e-> clear());
    }

    private void generateCard() {
        categoryField.getItems().addAll("Fiction", "Non-Fiction", "Science", "History", "Biography");
        categoryField.setValue("Select");
    }

    private void generateTable() {
        table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("ID", Book::getId).width(70),
                DataColumn.of("TITLE", Book::getTitle).width(240),
                DataColumn.of("AUTHOR", Book::getAuthor).width(220),
                DataColumn.of("CATEGORY", Book::getCategory).width(180),
                DataColumn.of("QTY", Book::getTotalQuantity).width(80)
        );

        table.setItems(AppServices.getInstance().getBookService().getAllBooks());
        table.setSelectionMode(SelectionMode.SINGLE);

        Pagination pagination = new Pagination();
        pagination.setPageCount(8);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();

                    System.out.println("Current page: " + page);
                }
        );

        HBox paginationContainer = new HBox();
        paginationContainer.setAlignment(Pos.CENTER_RIGHT);
        paginationContainer.getChildren().add(pagination);

        table.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);

        tableContainer.getChildren().addAll(table, paginationContainer);
    }

    private void addBook() {
        AppServices.getInstance().getBookService().addBook(
                new Book("BK014", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 18, 12)
        );
    }

    private void clear(){
        AppServices.getInstance().getBookService().removeBookById("BK014");
    }
}
