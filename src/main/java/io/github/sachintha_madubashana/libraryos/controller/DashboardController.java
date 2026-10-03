package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.RecentActivity;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    private HBox listContainer;

    @FXML
    private void initialize() {

        RecentActivity recentActivity = new RecentActivity();
        recentActivity.setActivities(List.of(
                new Activity("To Kill a Mockingbird", "Sofia Andersen", "2026-08-01", ActivityStatus.OVERDUE),
                new Activity("The Great Gatsby", "Eleanor Whitfield", "2026-09-20", ActivityStatus.BORROWED),
                new Activity("Brave New World", "James Okafor", "2026-09-10", ActivityStatus.BORROWED),
                new Activity("Moby Dick", "Priya Nair", "2026-08-10", ActivityStatus.OVERDUE),
                new Activity("1984", "Marcus Chen", "2026-09-05", ActivityStatus.BORROWED)
        ));

        RecentActivity recentActivity2 = new RecentActivity();
        recentActivity2.setActivities(List.of(
                new Activity("To Kill a Mockingbird", "Sofia Andersen", "2026-08-01", ActivityStatus.OVERDUE),
                new Activity("The Great Gatsby", "Eleanor Whitfield", "2026-09-20", ActivityStatus.BORROWED),
                new Activity("Brave New World", "James Okafor", "2026-09-10", ActivityStatus.BORROWED),
                new Activity("Moby Dick", "Priya Nair", "2026-08-10", ActivityStatus.OVERDUE),
                new Activity("1984", "Marcus Chen", "2026-09-05", ActivityStatus.BORROWED)
        ));
        HBox.setHgrow(recentActivity, Priority.ALWAYS);
        HBox.setHgrow(recentActivity2, Priority.ALWAYS);

        listContainer.getChildren().add(recentActivity);
        listContainer.getChildren().add(recentActivity2);
    }
}
