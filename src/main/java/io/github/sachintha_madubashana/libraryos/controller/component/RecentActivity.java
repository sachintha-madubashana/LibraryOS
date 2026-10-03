package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class RecentActivity extends VBox {

    private final VBox activityList = new VBox();

    public RecentActivity() {
        getStyleClass().add("recent-activity");

        HBox header = createHeader();

        ScrollPane scrollPane = new ScrollPane(activityList);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        activityList.getStyleClass().add("activity-list");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(header, scrollPane);
    }

    private HBox createHeader() {
        Label title = new Label("Recent Activity");
        title.getStyleClass().add("activity-header-title");

        Button viewAll = new Button("VIEW ALL");
        viewAll.getStyleClass().add("view-all-button");

        viewAll.setOnAction(e -> {
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(title, spacer, viewAll);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("activity-header");

        return header;
    }

    public void setActivities(List<Activity> activities) {
        activityList.getChildren().clear();

        for (Activity activity : activities) {
            activityList.getChildren().add(
                    createActivityRow(activity)
            );
        }
    }

    public void addActivity(Activity activity) {
        activityList.getChildren().add(
                createActivityRow(activity)
        );
    }

    private HBox createActivityRow(Activity activity) {
        Label bookTitle = new Label(activity.getTitle());
        bookTitle.getStyleClass().add("book-title");

        Label details = new Label(
                activity.getBorrower() + "  ·  " + activity.getDate()
        );
        details.getStyleClass().add("activity-details");

        VBox information = new VBox(8, bookTitle, details);
        information.setAlignment(Pos.CENTER_LEFT);

        Label status = new Label(activity.getStatus().toString().toUpperCase());
        status.getStyleClass().add("status-badge");

        if (activity.getStatus() == ActivityStatus.OVERDUE) {
            status.getStyleClass().add("overdue");
        } else {
            status.getStyleClass().add("borrowed");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(information, spacer, status);

        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("activity-row");

        return row;
    }
}
