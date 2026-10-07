package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.MemberValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class AddMemberController {

    @FXML
    private VBox parent;

    @FXML
    private VBox root;

    @FXML
    private InputField nameField;

    @FXML
    private InputField emailField;

    @FXML
    private InputField numberField;

    @FXML
    private InputField addressField;

    @FXML
    private Button registerBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Label generatedIdLabel;

    private int count = 7;
    @FXML
    private void initialize() {
        generatedIdLabel.setText("Member ID (auto-assigned): MEM" + count);
        registerBtn.setOnAction(event -> registerMember());
        clearBtn.setOnAction(event -> clearForm());
    }

    private void registerMember() {

        clearErrors();

        boolean valid = true;

        String name = nameField.getInputValue().trim();
        String email = emailField.getInputValue().trim();
        String phone = numberField.getInputValue().trim();
        String address = addressField.getInputValue().trim();

        // Name
        String nameError = MemberValidator.validateName(name);
        if (nameError != null) {
            nameField.setErrorMessage(nameError);
            valid = false;
        }

        // Email
        String emailError = MemberValidator.validateEmail(email);
        if (emailError != null) {
            emailField.setErrorMessage(emailError);
            valid = false;
        }

        // Phone
        String phoneError = MemberValidator.validatePhone(phone);
        if (phoneError != null) {
            numberField.setErrorMessage(phoneError);
            valid = false;
        }

        // Address
        String addressError = MemberValidator.validateAddress(address);
        if (addressError != null) {
            addressField.setErrorMessage(addressError);
            valid = false;
        }

        if (!valid) {
            return;
        }

        Member member = new Member(generateMemberId(), name, email, phone, address, LocalDate.now());
        registerMember(member);
    }

    private void registerMember(Member member) {
        AlertMessage alert = new AlertMessage(
                "Member registered",
                "Member registered successfully.",
                AlertMessage.Type.SUCCESS
        );
        alert.showIn(parent);
        clearForm();
    }

    private String generateMemberId() {
        String memberId = "MEM00" + count++;
        generatedIdLabel.setText("Member ID (auto-assigned): " + count);
        return memberId;
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        numberField.clear();
        addressField.clear();

        clearErrors();
    }

    private void clearErrors() {
        nameField.clearError();
        emailField.clearError();
        numberField.clearError();
        addressField.clearError();
    }
}