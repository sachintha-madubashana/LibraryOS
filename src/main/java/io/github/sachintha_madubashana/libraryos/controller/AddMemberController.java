package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.MemberValidator;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
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
    private InputField memberId;

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

    @FXML
    private void initialize() {
        generateMemberId();
        registerBtn.setOnAction(event -> registerMember());
        clearBtn.setOnAction(event -> clearForm());
    }

    private void registerMember() {
        clearErrors();

        String name = nameField.getInputValue().trim();
        String email = emailField.getInputValue().trim();
        String phone = numberField.getInputValue().trim();
        String address = addressField.getInputValue().trim();

        // Name
        String nameError = MemberValidator.validateName(name);
        if (nameError != null) {
            nameField.setErrorMessage(nameError);
            return;
        }

        // Email
        String emailError = MemberValidator.validateEmail(email);
        if (emailError != null) {
            emailField.setErrorMessage(emailError);
            return;
        }

        // Phone
        String phoneError = MemberValidator.validatePhone(phone);
        if (phoneError != null) {
            numberField.setErrorMessage(phoneError);
            return;
        }

        // Address
        String addressError = MemberValidator.validateAddress(address);
        if (addressError != null) {
            addressField.setErrorMessage(addressError);
            return;
        }

        Member member = new Member(memberId.getInputValue(), name, email, phone, address, LocalDate.now());
        register(member);
    }

    private void register(Member member) {
        Result<Member> result = AppServices.getInstance().getMemberService().register(member);
        AlertMessage alert;
        if (result.isSuccess()) {
            alert = new AlertMessage("Member added", "Member added successfully", AlertMessage.Type.SUCCESS);
        } else {
            alert = new AlertMessage("Member add failed", result.getMessage(), AlertMessage.Type.ERROR);
        }
        alert.showIn(parent);
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        numberField.clear();
        addressField.clear();

        clearErrors();
        generateMemberId();
    }

    private void clearErrors() {
        nameField.clearError();
        emailField.clearError();
        numberField.clearError();
        addressField.clearError();
    }

    private void generateMemberId() {
        Result<String> data = AppServices.getInstance().getMemberService().generateMemberId();
        if (data.isSuccess()) {
            String generatedBookId = data.getData();
            memberId.setInputValue(generatedBookId);
        } else {
            memberId.setErrorMessage(data.getMessage());
        }
    }
}