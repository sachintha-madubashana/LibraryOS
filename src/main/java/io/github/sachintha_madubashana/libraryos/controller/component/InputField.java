package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.model.InputType;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class InputField extends VBox {
    @FXML
    private Label label;
    @FXML
    private HBox container;
    @FXML
    private FontIcon icon;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private FontIcon passwordShowHideButtonIcon;
    @FXML
    private Button passwordShowHideButton;

    private TextField currentTextField;

    private boolean isPasswordVisible = false;

    private final StringProperty labelText = new SimpleStringProperty(this, "labelText", "");
    private final StringProperty iconLiteral = new SimpleStringProperty(this, "iconLiteral", null);
    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "");
    private final ObjectProperty<InputType> inputType = new SimpleObjectProperty<>(this, "inputType", InputType.DEFAULT);
    private final BooleanProperty isIconEnable = new SimpleBooleanProperty(this, "isIconEnable", true);

    public InputField() {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("view/component/input-field-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        // Default visible input type
        switchInputField(inputType.get());

        // Listen for changes from FXML attributes
        inputType.addListener((obs, oldVal, newVal) -> switchInputField(newVal));

        // Bindings
        label.textProperty().bind(labelText);
        iconLiteral.addListener((obs, oldVal, newVal) -> icon.setIconLiteral(newVal));
        textField.promptTextProperty().bind(promptText);
        passwordField.promptTextProperty().bind(promptText);

        // Focus effect
        textField.focusedProperty().addListener((o, ov, nv) -> updateFocus(nv));
        passwordField.focusedProperty().addListener((o, ov, nv) -> updateFocus(nv));

        // Hidden eye button for email/text/number fields
        passwordShowHideButton.setVisible(false);
        passwordShowHideButton.setManaged(false);

        label.setVisible(false);
        label.setManaged(false);
    }

    private void switchInputField(InputType type) {

        textField.setVisible(false);
        textField.setManaged(false);
        passwordField.setVisible(false);
        passwordField.setManaged(false);
        passwordShowHideButton.setVisible(false);
        passwordShowHideButton.setManaged(false);

        icon.visibleProperty().bind(isIconEnable);
        icon.managedProperty().bind(isIconEnable);

        switch (type) {

            case EMAIL -> {
                currentTextField = textField;
                textField.setVisible(true);
                textField.setManaged(true);
                iconLiteral.set("fth-mail");
            }

            case TEXT -> {
                currentTextField = textField;
                textField.setVisible(true);
                textField.setManaged(true);
                iconLiteral.set("fth-file-text");
            }

            case NUMBER -> {
                currentTextField = textField;
                textField.setVisible(true);
                textField.setManaged(true);
                textField.textProperty().addListener((obs, oldV, newV) -> {
                    if (!newV.matches("\\d*")) textField.setText(oldV);
                });
                iconLiteral.set("fth-hash");
            }

            case PASSWORD -> {
                currentTextField = passwordField;
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                passwordShowHideButton.setVisible(true);
                passwordShowHideButton.setManaged(true);
                iconLiteral.set("fth-lock");
            }
        }
    }

    private void updateFocus(boolean newValue) {
        if (newValue) {
            if (!container.getStyleClass().contains("input-box-focused")) {
                container.getStyleClass().add("input-box-focused");
            }
        } else {
            container.getStyleClass().remove("input-box-focused");
        }

    }

    @FXML
    private void changeEye() {
        if (!isPasswordVisible) {
            // Show
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            textField.setVisible(true);
            textField.setManaged(true);
            passwordShowHideButtonIcon.setIconLiteral("fth-eye-off");
            isPasswordVisible = true;
        } else {
            // Hide
            passwordField.setText(textField.getText());
            textField.setVisible(false);
            textField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordShowHideButtonIcon.setIconLiteral("fth-eye");
            isPasswordVisible = false;
        }
    }

    public String getLabelText() {
        return label.getText();
    }

    public void setLabelText(String text) {
        label.setVisible(true);
        label.setManaged(true);
        labelText.set(text);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    public String getIconLiteral() {
        return icon.getIconLiteral();
    }

    public void setIconLiteral(String literal) {
        iconLiteral.set(literal);
    }

    public StringProperty iconLiteralProperty() {
        return iconLiteral;
    }

    public String getPromptText() {
        return currentTextField.getPromptText();
    }

    public void setPromptText(String value) {
        promptText.set(value);
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    public String getInputValue() {
        return currentTextField.getText();
    }

    public void setInputValue(String value) {
        currentTextField.setText(value);
    }

    public InputType getInputType() {
        return inputType.get();
    }

    public void setInputType(InputType value) {
        inputType.set(value);
    }

    public ObjectProperty<InputType> inputTypeProperty() {
        return inputType;
    }

    public boolean getIsIconEnable() {
        return isIconEnable.get();
    }

    public void setIsIconEnable(boolean value) {
        isIconEnable.set(value);
    }

}
