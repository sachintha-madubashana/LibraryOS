package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import javafx.fxml.FXML;

public class IssueBookController {
    @FXML
    private DropdownSelect<String> memberDropdown;

    @FXML
    private DropdownSelect<String> bookDropdown;

    @FXML
    private InputField issueDateInput;

    @FXML
    private InputField dueDateInput;

    @FXML
    private void initialize() {
        memberDropdown.getItems().addAll("MB001 - John Smith", "MB002 - Emily Johnson", "MB003 - Michael Brown", "MB004 - Sarah Wilson", "MB005 - David Miller", "MB006 - Jessica Davis", "MB007 - Daniel Anderson", "MB008 - Sophia Taylor", "MB009 - James Thomas", "MB010 - Olivia Moore", "MB011 - William Martin", "MB012 - Emma Jackson");
        memberDropdown.setValue("Select");
        bookDropdown.getItems().addAll("BK001 - To Kill a Mockingbird", "BK002 - 1984", "BK003 - The Great Gatsby", "BK004 - Pride and Prejudice", "BK005 - The Catcher in the Rye", "BK006 - The Hobbit", "BK007 - The Lord of the Rings", "BK008 - Harry Potter and the Philosopher's Stone", "BK009 - The Hitchhiker's Guide to the Galaxy", "BK010 - The Shining", "BK011 - The Da Vinci Code", "BK012 - The Chronicles of Narnia", "BK013 - The Lord of the Flies");
        bookDropdown.setValue("Select");
    }
}
