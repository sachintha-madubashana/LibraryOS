package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private void initialize() {

        generateTable();
    }


    private void generateTable() {
        DataTable<BorrowingRecord> table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("RECORD ID", BorrowingRecord::getRecordId).width(100),
                DataColumn.of("MEMBER", BorrowingRecord::getMember).width(170),
                DataColumn.of("BOOK TITLE", BorrowingRecord::getBookTitle).width(240),
                DataColumn.of("ISSUE DATE", BorrowingRecord::getIssueDate).width(120),
                DataColumn.of("DUE DATE", BorrowingRecord::getDueDate).width(120),
                DataColumn.of("RETURN DATE", BorrowingRecord::getReturnDate).width(120),
                DataColumn.of("STATUS", BorrowingRecord::getStatus).width(110)
        );

        List<BorrowingRecord> borrowingRecords = new ArrayList<>();
        borrowingRecords.add(new BorrowingRecord("BR001", "John Smith", "To Kill a Mockingbird", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 13), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR002", "Emily Johnson", "1984", LocalDate.of(2026, 8, 5), LocalDate.of(2026, 8, 19), LocalDate.of(2026, 8, 18), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR003", "Michael Brown", "The Great Gatsby", LocalDate.of(2026, 8, 12), LocalDate.of(2026, 8, 26), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR004", "Sarah Wilson", "Pride and Prejudice", LocalDate.of(2026, 8, 18), LocalDate.of(2026, 9, 1), LocalDate.of(2026, 8, 30), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR005", "David Miller", "The Hobbit", LocalDate.of(2026, 8, 22), LocalDate.of(2026, 9, 5), null, "Overdue"));
        borrowingRecords.add(new BorrowingRecord("BR006", "Jessica Davis", "The Lord of the Rings", LocalDate.of(2026, 8, 25), LocalDate.of(2026, 9, 8), LocalDate.of(2026, 9, 6), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR007", "Daniel Anderson", "Harry Potter and the Philosopher's Stone", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 15), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR008", "Sophia Taylor", "The Shining", LocalDate.of(2026, 9, 4), LocalDate.of(2026, 9, 18), LocalDate.of(2026, 9, 17), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR009", "James Thomas", "The Da Vinci Code", LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 24), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR010", "Olivia Moore", "The Chronicles of Narnia", LocalDate.of(2026, 9, 12), LocalDate.of(2026, 9, 26), null, "Overdue"));
        borrowingRecords.add(new BorrowingRecord("BR011", "William Martin", "The Lord of the Flies", LocalDate.of(2026, 9, 18), LocalDate.of(2026, 10, 2), LocalDate.of(2026, 9, 29), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR012", "Emma Jackson", "The Catcher in the Rye", LocalDate.of(2026, 9, 20), LocalDate.of(2026, 10, 4), null, "Borrowed"));

        table.setItems(borrowingRecords);

        Pagination pagination = new Pagination();
        pagination.setPageCount(8);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();
                    System.out.println("Current page: " + page);
                }
        );

        Label paginationLabel = new Label("6 records shown");
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
