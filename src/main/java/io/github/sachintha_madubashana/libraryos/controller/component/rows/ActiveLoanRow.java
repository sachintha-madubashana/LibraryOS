package io.github.sachintha_madubashana.libraryos.controller.component.rows;

import io.github.sachintha_madubashana.libraryos.event.ReturnBookSelectEvent;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ActiveLoanRow extends HBox {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private final Label titleLabel;
    private final Label borrowerLabel;
    private final Label dueDateLabel;
    private final Label statusLabel;
    private final VBox detailsContainer;

    public ActiveLoanRow(Activity activity) {
        getStyleClass().add("active-loan-row");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(16);
        setOnMouseClicked(event -> {
            getStyleClass().add("loan-selected");
            fireEvent(new ReturnBookSelectEvent(activity));
            event.consume();
        });

        titleLabel = new Label();
        titleLabel.getStyleClass().add("active-loan-title");

        borrowerLabel = new Label();
        borrowerLabel.getStyleClass().add("active-loan-borrower");

        dueDateLabel = new Label();
        dueDateLabel.getStyleClass().add("active-loan-due-date");

        detailsContainer = new VBox(8, titleLabel, borrowerLabel, dueDateLabel);
        detailsContainer.getStyleClass().add("active-loan-details");

        HBox.setHgrow(detailsContainer, Priority.ALWAYS);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("active-loan-status");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.NEVER);

        getChildren().addAll(detailsContainer, statusLabel);

        updateActivity(activity);
    }

    public void updateActivity(Activity activity) {

        titleLabel.setText(activity.getTitle());
        borrowerLabel.setText(activity.getBorrower());

        updateDueDate(activity.getDate());
        updateStatus(activity.getStatus());
        updateRowStyle(activity);
    }

    private void updateDueDate(String date) {

        LocalDate dueDate;

        try {
            dueDate = LocalDate.parse(date, DATE_FORMATTER);
        } catch (Exception exception) {
            dueDateLabel.setText("Due: " + date);
            dueDateLabel.getStyleClass().remove("overdue");
            return;
        }

        LocalDate today = LocalDate.now();
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);

        String text = "Due: " + dueDate.format(DATE_FORMATTER);

        dueDateLabel.getStyleClass().remove("overdue");

        if (daysOverdue > 0) {
            text += "  ·  " + daysOverdue + "d overdue";

            if (!dueDateLabel.getStyleClass().contains("overdue")) {
                dueDateLabel.getStyleClass().add("overdue");
            }
        }

        dueDateLabel.setText(text);
    }

    private void updateStatus(ActivityStatus status) {

        statusLabel.getStyleClass().removeAll(
                "status-borrowed",
                "status-overdue",
                "status-returned"
        );

        if (status == null) {
            statusLabel.setText("UNKNOWN");
            return;
        }

        statusLabel.setText(status.toString());

        switch (status) {
            case BORROWED -> statusLabel.getStyleClass().add("status-borrowed");
            case OVERDUE -> statusLabel.getStyleClass().add("status-overdue");
            case RETURNED -> statusLabel.getStyleClass().add("status-returned");
        }
    }

    private void updateRowStyle(Activity activity) {
        getStyleClass().removeAll("loan-overdue", "loan-selected");

        if (activity.getStatus() == ActivityStatus.OVERDUE) {
            getStyleClass().add("loan-overdue");
        }
    }
}