package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.AutocompletePopup;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.event.NotificationEvent;
import io.github.sachintha_madubashana.libraryos.model.*;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
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

    @FXML
    Button searchBtn;

    @FXML
    InputField searchInput;

    ObservableList<BorrowingRecord> borrowings;

    AutocompletePopup<BorrowingRecord> popup;

    List<BorrowingRecord> filtered;

    BorrowStatus currentFilterStatus = BorrowStatus.ALL;

    @FXML
    private void initialize() {
        borrowings = FXCollections.observableArrayList();
        initiateButtonActions();

        filterBorrowings(BorrowStatus.ALL);
        generateTable();

        popup = new AutocompletePopup<>();
        initListener();
    }

    private void initListener() {
        searchInput.setOnInputChange((obs, oldValue, newValue) -> {
            search(newValue);
        });
        searchInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showSearchedResult(null);
            }
        });
        popup.setOnSuggestionSelected(selectedStudent -> {
            searchInput.setInputValue(selectedStudent.toString());
            showSearchedResult(selectedStudent);
        });
        searchInput.focusedProperty().addListener((observable, oldValue, isFocused) -> {
            if (Boolean.TRUE.equals(isFocused) && filtered != null && !filtered.isEmpty() && !popup.isShowing() && !searchInput.getInputValue().isBlank()) {
                popup.showBelow(searchInput);
            }
        });
    }

    private void initiateButtonActions() {
        allBtn.setOnAction(_ -> {
            filterBorrowings(BorrowStatus.ALL);
        });

        borrowed.setOnAction(_ -> {
            filterBorrowings(BorrowStatus.BORROWED);
        });

        returnedBtn.setOnAction(_ -> {
            filterBorrowings(BorrowStatus.RETURNED);
        });

        overDueBtn.setOnAction(_ -> {
            filterBorrowings(BorrowStatus.OVERDUE);
        });

        searchBtn.setOnAction(_ -> {
            showSearchedResult(null);
        });
    }

    private void generateTable() {
        DataTable<BorrowingRecord> table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("RECORD ID", ((Function<BorrowingRecord, String>)BorrowingRecord::getRecordId).andThen(String::toUpperCase)).width(100),
                DataColumn.of("MEMBER", (((Function<BorrowingRecord, Member>) BorrowingRecord::getMember).andThen(Member::getFullName))).width(170),
                DataColumn.of("BOOK TITLE", (((Function<BorrowingRecord, Book>) BorrowingRecord::getBook).andThen(Book::getTitle))).width(240),
                DataColumn.of("ISSUE DATE", BorrowingRecord::getIssueDate).width(120),
                DataColumn.of("DUE DATE", BorrowingRecord::getDueDate).width(120),
                DataColumn.of("RETURN DATE", BorrowingRecord::getReturnDate).width(120),
                DataColumn.of("STATUS", BorrowingRecord::getStatus).width(110)
        );

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

        Label paginationLabel = new Label();
        paginationLabel.textProperty().bind(Bindings.size(borrowings).asString().concat(" records shown"));
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

    private void search(String value) {
        if (value == null || value.isBlank()) {
            filterBorrowings(currentFilterStatus);
            popup.hide();
            return;
        }

        filtered = AppServices.getInstance().getBorrowingHistoryService().searchBorrowings(value, List.of(currentFilterStatus)).getData();
        popup.setSuggestions(filtered);

        if (!filtered.isEmpty()) {
            popup.showBelow(searchInput);
        } else {
            popup.hide();
        }
    }

    private void showSearchedResult(BorrowingRecord borrowRecord) {
        String value = searchInput.getInputValue();
        if (value.isBlank()) {
            root.fireEvent(new NotificationEvent(new Notification("Empty search query", "The search query cannot be empty.", Notification.Type.ALERT_WARNING)));
            return;
        }

        if (borrowRecord != null) {
            borrowings.setAll(borrowRecord);
        }

        if (filtered == null || filtered.isEmpty()) {
            root.fireEvent(new NotificationEvent(new Notification("No results found", "No borrowing records found for the search term: " + value, Notification.Type.ALERT_INFO)));
            return;
        }

        borrowings.setAll(filtered);
    }

    private void filterBorrowings(BorrowStatus status) {
        buttonSelectorByStatus(status);
        currentFilterStatus = status;
        switch (status) {
            case BORROWED, OVERDUE, RETURNED ->
                    borrowings.setAll(AppServices.getInstance().getBorrowingHistoryService().filterLoans(status).getData());
            default ->
                    borrowings.setAll(AppServices.getInstance().getBorrowingHistoryService().getBorrowings().getData());
        }
    }

    private void buttonSelectorByStatus(BorrowStatus status) {
        if (filtered != null) {
            filtered.clear();
        }
        resetButtonStyles(allBtn, borrowed, returnedBtn, overDueBtn);
        switch (status) {
            case BORROWED -> selectButton(borrowed);
            case RETURNED -> selectButton(returnedBtn);
            case OVERDUE -> selectButton(overDueBtn);
            default -> selectButton(allBtn);
        }
    }

    private void resetButtonStyles(Button... buttons) {
        for (Button button : buttons) {
            deselectButton(button);
        }
    }

    private void selectButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button", "button-primary");
    }

    private void deselectButton(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button", "button-secondary");
    }
}
