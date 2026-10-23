package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class BorrowingHistoryController {

    @FXML
    VBox root;

    @FXML
    Button allBtn;

    @FXML
    Button borrowed;

    @FXML
    Button returnedBtn;

    @FXML
    Button overDueBtn;

    ObservableList<BorrowingRecord> borrowings;

    @FXML
    private void initialize() {
        generateTable();
    }


    private void generateTable() {
        DataTable<BorrowingRecord> table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("RECORD ID", BorrowingRecord::getRecordId).width(100),
                DataColumn.of("MEMBER", (((Function<BorrowingRecord, Member>) BorrowingRecord::getMember).andThen(Member::getFullName))).width(170),
                DataColumn.of("BOOK TITLE",(((Function<BorrowingRecord, Book>) BorrowingRecord::getBook).andThen(Book::getTitle))).width(240),
                DataColumn.of("ISSUE DATE", BorrowingRecord::getIssueDate).width(120),
                DataColumn.of("DUE DATE", BorrowingRecord::getDueDate).width(120),
                DataColumn.of("RETURN DATE", BorrowingRecord::getReturnDate).width(120),
                DataColumn.of("STATUS", BorrowingRecord::getStatus).width(110)
        );
        borrowings = FXCollections.observableArrayList(AppServices.getInstance().getBorrowingHistoryService().getBorrowings().getData());

        table.setItems(borrowings);

        Pagination pagination = new Pagination();
        pagination.setPageCount(8);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();
                    System.out.println("Current page: " + page);
                }
        );

        Label paginationLabel = new Label(borrowings.size() + " records shown");
        paginationLabel.getStyleClass().add("text-caption");
        paginationLabel.setAlignment(Pos.CENTER_LEFT);
        paginationLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(paginationLabel, Priority.ALWAYS);

        HBox paginationContainer = new HBox();
        paginationContainer.setAlignment(Pos.CENTER);
        paginationContainer.getChildren().addAll(paginationLabel, pagination);

        table.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.getChildren().addAll(table, paginationContainer);
    }
}
