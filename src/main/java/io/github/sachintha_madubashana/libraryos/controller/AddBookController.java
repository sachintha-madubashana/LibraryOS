package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddBookController {

    @FXML
    private HBox root;

    @FXML
    private VBox card;

    @FXML
    private VBox tableContainer;
    @FXML
    private DropdownSelect<String> categoryField;
    @FXML
    private void initialize() {
        generateCard();
        generateTable();
    }

    private void generateCard() {
        card.setPadding(new Insets(28));
        Pagination pagination = new Pagination();

        pagination.setPageCount(50);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();

                    System.out.println("Current page: " +page);
                }
        );

        card.getChildren().add(pagination);
    }

    private void generateTable() {

    }
}
