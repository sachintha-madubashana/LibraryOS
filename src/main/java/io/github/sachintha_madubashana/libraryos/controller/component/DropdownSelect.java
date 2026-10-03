package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.Launcher;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DropdownSelect<T> extends VBox {

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

    private final StringProperty labelText = new SimpleStringProperty(this, "labelText", "");
    private final BooleanProperty required = new SimpleBooleanProperty(this, "required", false);
    private final StringProperty error = new SimpleStringProperty(this, "error", "");

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

        labelText.addListener((obs, oldValue, newValue) -> {
            updateLabelVisibility();
            updateRequiredIndicator();
        });
        required.addListener((obs, oldValue, newValue) -> updateRequiredIndicator());
        error.addListener((obs, oldValue, newValue) -> updateErrorState());

        updateLabelVisibility();
        updateRequiredIndicator();
        updateErrorState();
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

    private void updateLabelVisibility() {
        boolean hasLabel = labelText.get() != null && !labelText.get().isBlank();
        setManagedAndVisible(labelContainer, hasLabel);
    }

    private void updateRequiredIndicator() {
        boolean showRequired = required.get()
                && labelText.get() != null
                && !labelText.get().isBlank();
        setManagedAndVisible(requiredIndicator, showRequired);
    }

    private void updateErrorState() {
        boolean hasError = error.get() != null && !error.get().isBlank();
        setManagedAndVisible(errorLabel, hasError);

        if (hasError) {
            if (!comboBox.getStyleClass().contains("input-error")) {
                comboBox.getStyleClass().add("input-error");
            }
        } else {
            comboBox.getStyleClass().remove("input-error");
        }
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

    private void setManagedAndVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }
}