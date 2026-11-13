package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.Launcher;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DropdownSelect<T> extends VBox {

    private final StringProperty labelText = new SimpleStringProperty(this, "labelText", "");
    private final BooleanProperty required = new SimpleBooleanProperty(this, "required", false);
    private final StringProperty error = new SimpleStringProperty(this, "error", "");
    @FXML
    private HBox labelContainer;
    @FXML
    private Label label;
    @FXML
    private Label requiredIndicator;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<T> comboBox;

    public DropdownSelect() {
        FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("view/component/dropdown-select.fxml"));

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
        label.textProperty().bind(labelText);
        errorLabel.textProperty().bind(error);
        labelContainer.visibleProperty().bind(labelText.isNotEmpty());
        labelContainer.managedProperty().bind(labelContainer.visibleProperty());
        requiredIndicator.visibleProperty().bind(required.and(labelText.isNotEmpty()));
        requiredIndicator.managedProperty().bind(requiredIndicator.visibleProperty());
        errorLabel.visibleProperty().bind(error.isNotEmpty());
        errorLabel.managedProperty().bind(errorLabel.visibleProperty());
        error.addListener((obs, oldValue, newValue) -> updateErrorState());
        updateErrorState();
        comboBox.setPromptText("Select");
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

    private void updateErrorState() {
        boolean hasError = error.get() != null && !error.get().isBlank();
        comboBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), hasError);
    }

    // -------------------------------------------------------------------------
    // Items
    // -------------------------------------------------------------------------

    public ObservableList<T> getItems() {
        return comboBox.getItems();
    }

    public void setItems(ObservableList<T> items) {
        comboBox.setItems(items);
    }

    // -------------------------------------------------------------------------
    // Value
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

    // -------------------------------------------------------------------------
    // Selection
    // -------------------------------------------------------------------------
    public void clearSelection() {
        comboBox.setPromptText("Select");
        comboBox.setValue(null);
        comboBox.getSelectionModel().clearSelection();
    }

    public void selectFirst() {
        comboBox.getSelectionModel().selectFirst();
    }

    public void select(int index) {
        comboBox.getSelectionModel().select(index);
    }

    public void setPromptText(String value) {
        comboBox.setPromptText(value);
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

    // -------------------------------------------------------------------------
    // Events
    // -------------------------------------------------------------------------

    public void setOnSelectionChanged(EventHandler<ActionEvent> value) {
        comboBox.setOnAction(value);
    }
}