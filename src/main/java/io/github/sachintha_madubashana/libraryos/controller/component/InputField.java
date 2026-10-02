package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.model.InputType;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;

public class InputField extends VBox {
    private final StringProperty labelText =
            new SimpleStringProperty(this, "labelText", "");
    private final StringProperty iconLiteral =
            new SimpleStringProperty(this, "iconLiteral", null);
    private final StringProperty promptText =
            new SimpleStringProperty(this, "promptText", "");
    private final ObjectProperty<InputType> inputType =
            new SimpleObjectProperty<>(this, "inputType", InputType.DEFAULT);
    private final BooleanProperty iconEnabled =
            new SimpleBooleanProperty(this, "iconEnabled", true);
    private final BooleanProperty required =
            new SimpleBooleanProperty(this, "required", false);
    private final ObjectProperty<LocalDate> dateValue =
            new SimpleObjectProperty<>(this, "dateValue", null);
    private final ObjectProperty<Integer> yearValue =
            new SimpleObjectProperty<>(this, "yearValue", null);
    private final StringProperty errorMessage =
            new SimpleStringProperty(this, "errorMessage", null);
    @FXML
    private HBox labelContainer;
    @FXML
    private Label label;
    @FXML
    private Label requiredIndicator;

    // -------------------------------------------------------------------------
    // Internal State
    // -------------------------------------------------------------------------
    @FXML
    private HBox container;
    @FXML
    private FontIcon icon;

    // -------------------------------------------------------------------------
    // JavaFX Properties
    // -------------------------------------------------------------------------
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField yearField;
    @FXML
    private FontIcon passwordShowHideButtonIcon;
    @FXML
    private Button passwordShowHideButton;
    @FXML
    private Label errorLabel;
    private TextField currentTextField;
    private boolean passwordVisible = false;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public InputField() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Launcher.class.getResource(
                        "view/component/input-field-view.fxml"
                )
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load InputField FXML.",
                    e
            );
        }
    }


    // -------------------------------------------------------------------------
    // Initialization
    // -------------------------------------------------------------------------

    @FXML
    private void initialize() {

        // Property bindings
        label.textProperty().bind(labelText);

        textField.promptTextProperty().bind(promptText);
        passwordField.promptTextProperty().bind(promptText);
        datePicker.promptTextProperty().bind(promptText);
        datePicker.valueProperty().bindBidirectional(dateValue);
        yearField.promptTextProperty().bind(promptText);

        icon.visibleProperty().bind(iconEnabled);
        icon.managedProperty().bind(iconEnabled);

        // Property listeners
        labelText.addListener((obs, oldValue, newValue) ->
                updateLabelVisibility()
        );

        required.addListener((obs, oldValue, newValue) ->
                updateRequiredIndicator()
        );

        inputType.addListener((obs, oldValue, newValue) ->
                switchInputField(newValue)
        );

        iconLiteral.addListener((obs, oldValue, newValue) ->
                updateIcon()
        );

        yearField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                yearValue.set(null);
                return;
            }

            if (!newValue.matches("\\d{0,4}")) {
                yearField.setText(oldValue);
                return;
            }

            if (Integer.parseInt(newValue) < 1 || Integer.parseInt(newValue) > 9999) {
                yearField.setText(oldValue);
                return;
            }

            yearValue.set(Integer.parseInt(newValue));
        });
        errorMessage.addListener(
                (obs, oldValue, newValue) -> updateErrorState()
        );

        // Focus handling
        textField.focusedProperty().addListener(
                (obs, oldValue, newValue) ->
                        updateFocus(newValue)
        );

        passwordField.focusedProperty().addListener(
                (obs, oldValue, newValue) ->
                        updateFocus(newValue)
        );

        yearField.focusedProperty().addListener(
                (obs, oldValue, newValue) ->
                        updateFocus(newValue)
        );


        // Number validation.
        textField.textProperty().addListener(
                (obs, oldValue, newValue) -> {

                    if (inputType.get() != InputType.NUMBER) {
                        return;
                    }

                    if (!newValue.matches("\\d*")) {
                        textField.setText(oldValue);
                    }
                }
        );


        // Initial component state
        passwordShowHideButton.setVisible(false);
        passwordShowHideButton.setManaged(false);

        updateLabelVisibility();
        updateRequiredIndicator();
        updateIcon();

        switchInputField(inputType.get());
        updateErrorState();
    }


    // -------------------------------------------------------------------------
    // Label
    // -------------------------------------------------------------------------

    private void updateLabelVisibility() {

        boolean hasLabel =
                labelText.get() != null &&
                        !labelText.get().isBlank();

        setManagedAndVisible(labelContainer, hasLabel);
    }

    private void updateRequiredIndicator() {

        boolean showRequired =
                required.get() &&
                        labelText.get() != null &&
                        !labelText.get().isBlank();

        setManagedAndVisible(requiredIndicator, showRequired);
    }


    // -------------------------------------------------------------------------
    // Input Type
    // -------------------------------------------------------------------------

    private void switchInputField(InputType type) {

        if (type == null) {
            type = InputType.DEFAULT;
        }

        setManagedAndVisible(textField, false);
        setManagedAndVisible(passwordField, false);
        setManagedAndVisible(datePicker, false);
        setManagedAndVisible(yearField, false);
        setManagedAndVisible(passwordShowHideButton, false);

        if (type != InputType.PASSWORD) {
            passwordVisible = false;
        }

        switch (type) {
            case DEFAULT, TEXT -> {
                currentTextField = textField;
                setManagedAndVisible(textField, true);
                setDefaultIcon("fth-file-text");
            }
            case EMAIL -> {
                currentTextField = textField;
                setManagedAndVisible(textField, true);
                setDefaultIcon("fth-mail");
            }
            case NUMBER -> {
                currentTextField = textField;
                setManagedAndVisible(textField, true);
                setDefaultIcon("fth-hash");
            }
            case PASSWORD -> {
                currentTextField = passwordField;
                setManagedAndVisible(passwordField, true);
                setManagedAndVisible(passwordShowHideButton, true);
                passwordShowHideButtonIcon.setIconLiteral("fth-eye");
                setDefaultIcon("fth-lock");
            }
            case DATE -> {
                currentTextField = null;
                setManagedAndVisible(datePicker, true);
                setDefaultIcon("fth-calendar");
            }
            case YEAR -> {
                currentTextField = yearField;
                setManagedAndVisible(yearField, true);
                setDefaultIcon("fth-calendar");
            }
        }
    }


    // -------------------------------------------------------------------------
    // Icon
    // -------------------------------------------------------------------------

    private void setDefaultIcon(String defaultIcon) {
        if (iconLiteral.get() == null || iconLiteral.get().isBlank()) {
            iconLiteral.set(defaultIcon);
        }
    }

    private void updateIcon() {
        String literal = iconLiteral.get();
        if (literal != null && !literal.isBlank()) {
            icon.setIconLiteral(literal);
        }
    }

    // -------------------------------------------------------------------------
    // Focus
    // -------------------------------------------------------------------------

    private void updateFocus(boolean focused) {
        if (focused) {
            if (!container.getStyleClass().contains("input-box-focused")) {
                container.getStyleClass().add("input-box-focused");
            }

        } else {
            container.getStyleClass().remove("input-box-focused");
        }
    }

    @Override
    public void requestFocus() {
        if (currentTextField != null) {
            currentTextField.requestFocus();
        } else if (datePicker != null) {
            datePicker.requestFocus();
        }
    }

    // -------------------------------------------------------------------------
    // Password Visibility
    // -------------------------------------------------------------------------

    @FXML
    private void changeEye() {
        if (passwordVisible) {
            hidePassword();
        } else {
            showPassword();
        }
    }

    private void showPassword() {
        textField.setText(passwordField.getText());
        setManagedAndVisible(passwordField, false);
        setManagedAndVisible(textField, true);
        currentTextField = textField;
        passwordShowHideButtonIcon.setIconLiteral("fth-eye-off");
        passwordVisible = true;
    }

    private void hidePassword() {
        passwordField.setText(textField.getText());
        setManagedAndVisible(textField, false);
        setManagedAndVisible(passwordField, true);
        currentTextField = passwordField;
        passwordShowHideButtonIcon.setIconLiteral("fth-eye");
        passwordVisible = false;
    }


    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private void setManagedAndVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    private void updateErrorState() {
        String message = errorMessage.get();
        boolean hasError = message != null && !message.isBlank();
        errorLabel.setText(hasError ? message : "");
        setManagedAndVisible(errorLabel, hasError);
        updateInputErrorStyle(hasError);
    }

    private void updateInputErrorStyle(boolean hasError) {
        if (hasError) {
            if (!container.getStyleClass().contains("input-box-error")) {
                container.getStyleClass().add("input-box-error");
            }
        } else {
            container.getStyleClass().remove("input-box-error");
        }
    }


    // -------------------------------------------------------------------------
    // Label Text Property
    // -------------------------------------------------------------------------

    public String getLabelText() {
        return labelText.get();
    }

    public void setLabelText(String text) {
        labelText.set(text);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }


    // -------------------------------------------------------------------------
    // Icon Property
    // -------------------------------------------------------------------------

    public String getIconLiteral() {
        return iconLiteral.get();
    }

    public void setIconLiteral(String literal) {
        iconLiteral.set(literal);
    }

    public StringProperty iconLiteralProperty() {
        return iconLiteral;
    }

    // -------------------------------------------------------------------------
    // Prompt Text Property
    // -------------------------------------------------------------------------

    public String getPromptText() {
        return promptText.get();
    }

    public void setPromptText(String value) {
        promptText.set(value);
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    // -------------------------------------------------------------------------
    // Input Value
    // -------------------------------------------------------------------------

    public String getInputValue() {
        return currentTextField != null
                ? currentTextField.getText()
                : "";
    }

    public void setInputValue(String value) {
        if (currentTextField != null) {
            currentTextField.setText(value);
        }
    }


    // -------------------------------------------------------------------------
    // Input Type Property
    // -------------------------------------------------------------------------

    public InputType getInputType() {
        return inputType.get();
    }

    public void setInputType(InputType value) {
        inputType.set(value);
    }

    public ObjectProperty<InputType> inputTypeProperty() {
        return inputType;
    }


    // -------------------------------------------------------------------------
    // Icon Enabled Property
    // -------------------------------------------------------------------------

    public boolean isIconEnabled() {
        return iconEnabled.get();
    }

    public void setIconEnabled(boolean value) {
        iconEnabled.set(value);
    }

    public BooleanProperty iconEnabledProperty() {
        return iconEnabled;
    }


    // -------------------------------------------------------------------------
    // Required Property
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
    // Date Property
    // -------------------------------------------------------------------------

    public LocalDate getDateValue() {
        return dateValue.get();
    }

    public void setDateValue(LocalDate value) {
        dateValue.set(value);
    }

    public ObjectProperty<LocalDate> dateValueProperty() {
        return dateValue;
    }

    // -------------------------------------------------------------------------
    // Year Property
    // -------------------------------------------------------------------------

    public Integer getYearValue() {
        return yearValue.get();
    }

    public void setYearValue(Integer value) {
        yearValue.set(value);

        if (value == null) {
            yearField.clear();
        } else {
            yearField.setText(String.valueOf(value));
        }
    }

    public ObjectProperty<Integer> yearValueProperty() {
        return yearValue;
    }


    // -------------------------------------------------------------------------
    // Error Property
    // -------------------------------------------------------------------------

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public void setErrorMessage(String message) {
        errorMessage.set(message);
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public void clearError() {
        errorMessage.set(null);
    }

    public boolean hasError() {
        String message = errorMessage.get();

        return message != null && !message.isBlank();
    }

    public void clear() {
        if (currentTextField != null) {
            currentTextField.clear();
        }
        yearField.clear();
        errorMessage.set(null);
    }
}