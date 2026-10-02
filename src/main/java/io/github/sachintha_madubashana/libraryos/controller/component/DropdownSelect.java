package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.Launcher;
import javafx.beans.DefaultProperty;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DropdownSelect<T> extends VBox {

    @FXML
    private Label label;

    @FXML
    private Label requiredIndicator;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<T> comboBox;

    private final StringProperty labelText =
            new SimpleStringProperty(this, "labelText", "");

    private final BooleanProperty required =
            new SimpleBooleanProperty(this, "required", false);

    private final StringProperty error =
            new SimpleStringProperty(this, "error", "");

    public DropdownSelect() {
        FXMLLoader loader = new FXMLLoader(
                Launcher.class.getResource(
                        "view/component/dropdown-select.fxml")
        );

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DropdownSelect", e);
        }

        initialize();
    }

    private void initialize() {
        // Label
        label.textProperty().bind(labelText);

        // Required indicator
        requiredIndicator.visibleProperty().bind(required);
        requiredIndicator.managedProperty().bind(required);

        // Error
        errorLabel.textProperty().bind(error);

        errorLabel.visibleProperty().bind(
                error.isNotEmpty()
        );

        errorLabel.managedProperty().bind(
                error.isNotEmpty()
        );

        // Error styling
        error.addListener((obs, oldValue, newValue) -> {
            boolean hasError = newValue != null && !newValue.isBlank();

            if (hasError) {
                if (!comboBox.getStyleClass().contains("input-error")) {
                    comboBox.getStyleClass().add("input-error");
                }
            } else {
                comboBox.getStyleClass().remove("input-error");
            }
        });
    }

    // -------------------------------------------------------------------------
    // Label
    // -------------------------------------------------------------------------

    public String getLabelText() {
        return labelText.get();
    }

    public void setLabelText(String value) {
        labelText.set(value);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    // -------------------------------------------------------------------------
    // Required
    // -------------------------------------------------------------------------

    public boolean isRequired() {
        return required.get();
    }

    public void setRequired(boolean value) {
        required.set(value);
    }

    public BooleanProperty requiredProperty() {
        return required;
    }

    // -------------------------------------------------------------------------
    // Error
    // -------------------------------------------------------------------------

    public String getError() {
        return error.get();
    }

    public void setError(String value) {
        error.set(value == null ? "" : value);
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void clearError() {
        setError("");
    }

//    // -------------------------------------------------------------------------
//    // Items
//    // -------------------------------------------------------------------------
//
//    public ObservableList<T> getItems() {
//        return comboBox.getItems();
//    }
//
//    public void setItems(ObservableList<T> items) {
//        comboBox.setItems(items);
//    }
//
//    public ObjectProperty<ObservableList<T>> itemsProperty() {
//        return comboBox.itemsProperty();
//    }

    // -------------------------------------------------------------------------
    // ComboBox
    // -------------------------------------------------------------------------

    public T getValue() {
        return comboBox.getValue();
    }

    public void setValue(T value) {
        comboBox.setValue(value);
    }

    public ObjectProperty<T> valueProperty() {
        return comboBox.valueProperty();
    }

    public ObservableList<T> getItems() {
        return comboBox.getItems();
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public void show() {
        comboBox.show();
    }

    public void hide() {
        comboBox.hide();
    }

    public boolean isShowing() {
        return comboBox.isShowing();
    }

    @Override
    public void requestFocus() {
        comboBox.requestFocus();
    }
}