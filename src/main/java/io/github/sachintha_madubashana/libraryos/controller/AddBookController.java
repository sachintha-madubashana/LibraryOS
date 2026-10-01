package io.github.sachintha_madubashana.libraryos.controller;

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
    private void initialize() {
        generateCard();
        generateTable();
    }

    private void generateCard() {
        card.setPadding(new Insets(28));



    }

    private void generateTable() {

    }
}
