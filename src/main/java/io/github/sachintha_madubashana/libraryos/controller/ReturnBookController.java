package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertMessage;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.RecentActivity;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.util.List;

public class ReturnBookController {

    @FXML
    private Button searchBtn;

    @FXML
    private VBox column1;

    @FXML
    private VBox column2;

    @FXML
    private VBox column3;

    @FXML
    private VBox alertContainer;

    @FXML
    private Label recordIdLabel;

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label borrowedDateLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label overdueLabel;

    @FXML
    private InputField returnDatePicker;

    @FXML
    private Button confirmReturnButton;

    @FXML
    private void handleConfirmReturn() {
        LocalDate returnDate = returnDatePicker.getDateValue();

        if (returnDate == null) {
            AlertMessage alert = new AlertMessage("Return Date Required","Please select a return date.",AlertMessage.Type.WARNING);
            alert.showIn(column2);
            return;
        }

        AlertMessage alert = new AlertMessage("Confirm Return","Return date: " + returnDate,AlertMessage.Type.INFO);
        alert.showIn(column2);
    }

    @FXML
    private void initialize(){
        returnDatePicker.setDateValue(LocalDate.now());

        FontIcon fontIcon = new FontIcon(Feather.SEARCH);
        fontIcon.setIconSize(20);
        fontIcon.getStyleClass().add("button-icon");
        searchBtn.setGraphic(fontIcon);

        RecentActivity recentActivity = new RecentActivity();
        recentActivity.setActivities(List.of(
                new Activity("To Kill a Mockingbird", "Sofia Andersen", "2026-08-01", ActivityStatus.OVERDUE),
                new Activity("The Great Gatsby", "Eleanor Whitfield", "2026-09-20", ActivityStatus.BORROWED),
                new Activity("Brave New World", "James Okafor", "2026-09-10", ActivityStatus.BORROWED),
                new Activity("Moby Dick", "Priya Nair", "2026-08-10", ActivityStatus.OVERDUE),
                new Activity("1984", "Marcus Chen", "2026-09-05", ActivityStatus.BORROWED),
                new Activity("The Hitchhiker's Guide to the Galaxy", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("Harry Potter and the Philosopher's Stone", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED)
        ));

        column1.getChildren().add(recentActivity);

        AlertMessage alertMessage = new AlertMessage("9 days overdue",null, AlertMessage.Type.WARNING);
        alertMessage.showIn(alertContainer, false);
    }

}
