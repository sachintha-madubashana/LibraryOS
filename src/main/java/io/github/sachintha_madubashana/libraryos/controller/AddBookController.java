package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Objects;

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
    private Button generateBookIdBtn;

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

        ImageView image = new ImageView(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("image/ai.png"))));
        image.setPreserveRatio(true);
        image.setFitWidth(20);
        image.setFitHeight(20);
        generateBookIdBtn.setGraphic(image);

        generateBookIdBtn.setOnAction(e -> generateBookId());
        addBookBtn.setOnAction(e -> addBook());
        clearBtn.setOnAction(e -> clear());
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

        ObservableList<Book> books = FXCollections.observableArrayList(AppServices.getInstance().getBookService().getAllBooks().getData());

        table.setItems(books);
        table.setSelectionMode(SelectionMode.SINGLE);

        Pagination pagination = new Pagination();
        pagination.setPageCount(3);
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
        clearErrors();

        String id = bookId.getInputValue();
        String title = bookTitle.getInputValue();
        String author = bookAuthor.getInputValue();
        String category = categoryField.getValue();
        String qty = bookQty.getInputValue();
        String year = bookYear.getInputValue();

        if(id.isEmpty()){
            bookId.setErrorMessage("Book ID is required");
            return;
        }

        if(title.isEmpty()){
            bookTitle.setErrorMessage("Book title is required");
            return;
        }

        if(author.isEmpty()){
            bookAuthor.setErrorMessage("Book author is required");
            return;
        }

        if(Objects.equals(category, "Select")){
            categoryField.setError("Category is required");
            return;
        }

        if(qty.isEmpty()){
            bookQty.setErrorMessage("Quantity is required");
            return;
        }

        if(year.isEmpty() || Integer.parseInt(year) <= 0){
            bookYear.setErrorMessage("Year must be greater than 0");
            return;
        }

        if(Integer.parseInt(year) >= 9999){
            bookYear.setErrorMessage("Year must be less than 9999");
            return;
        }

        if(!qty.matches("\\d+")){
            bookQty.setErrorMessage("Quantity must be a number");
            return;
        }

        if(Integer.parseInt(qty) <= 0){
            bookQty.setErrorMessage("Quantity must be greater than 0");
            return;
        }

        if(!year.matches("\\d+")){
            bookYear.setErrorMessage("Year must be a number");
            return;
        }

        Book book = new Book(id, title, author, category, Integer.parseInt(qty), Integer.parseInt(year));
        Result<Book> result = AppServices.getInstance().getBookService().addBook(book);

        if (result.isSuccess()) {
            table.getItems().add(result.getData());
            AlertMessage alert = new AlertMessage("Book added","Book added successfully", AlertMessage.Type.SUCCESS);
            alert.showIn(card);
            clear();
        }else{
            AlertMessage alert = new AlertMessage("Book add failed",result.getMessage(), AlertMessage.Type.ERROR);
            alert.showIn(card);
        }
    }

    private void clear() {
        bookId.clear();
        bookTitle.clear();
        bookAuthor.clear();
        categoryField.setValue("Select");
        categoryField.clearError();
        bookQty.clear();
        bookYear.clear();
        generateBookIdBtn.setDisable(false);
    }

    private void clearErrors() {
        bookId.clearError();
        bookTitle.clearError();
        bookAuthor.clearError();
        categoryField.clearError();
        bookQty.clearError();
        bookYear.clearError();
    }

    private void generateBookId() {
        Result<String> data = AppServices.getInstance().getBookService().generateBookId();
        if (data.isSuccess()) {
            String generatedBookId = data.getData();
            bookId.setInputValue(generatedBookId);
        } else {
            bookId.setErrorMessage(data.getMessage());
        }
        generateBookIdBtn.setDisable(true);
    }
}
