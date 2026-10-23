package io.github.sachintha_madubashana.libraryos.controller.component.rows;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RecentActivityRow extends HBox {

    public RecentActivityRow(BorrowingRecord borrowingRecord) {
        getStyleClass().add("recent-activity-row");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);

        Label titleLabel = new Label(borrowingRecord.getBook().getTitle());
        titleLabel.getStyleClass().add("recent-activity-title");

        Label detailsLabel = new Label(borrowingRecord.getMember().getFullName() + " · " + borrowingRecord.getDueDate());
        detailsLabel.getStyleClass().add("recent-activity-details");

        VBox bookDetails = new VBox(6, titleLabel, detailsLabel);
        bookDetails.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(bookDetails, Priority.ALWAYS);

        Label statusLabel = new Label(borrowingRecord.getStatus().name());
        statusLabel.getStyleClass().addAll("recent-activity-status", getStatusStyle(borrowingRecord.getStatus().name()));

        getChildren().addAll(bookDetails, statusLabel);
    }

    private String getStatusStyle(String status) {
        return switch (status.toUpperCase()) {
            case "OVERDUE" -> "status-overdue";
            case "BORROWED" -> "status-borrowed";
            case "RETURNED" -> "status-returned";
            default -> "status-default";
        };
    }
}
