package io.github.sachintha_madubashana.libraryos.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;

import java.util.Objects;

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
        categoryField.getItems().addAll(
                "Fiction",
                "Science",
                "Technology",
                "History"
        );
        categoryField.setValue("Select");

        DropdownSelect<String> databaseType2 = new DropdownSelect<>();

        databaseType2.setLabelText("Database Type");
        databaseType2.setRequired(true);

        databaseType2.getItems().addAll(
                "MySQL", "PostgreSQL",
                "SQLite"
        );

        databaseType2.setValue("MySQL");
        card.getChildren().add(databaseType2);

        String category = categoryField.getValue();
        if (Objects.equals(categoryField.getValue(), "Select")) {
            categoryField.setError("Please select a category.");
        } else {
            categoryField.clearError();
        }
    }

    private void generateTable() {

    }
}
