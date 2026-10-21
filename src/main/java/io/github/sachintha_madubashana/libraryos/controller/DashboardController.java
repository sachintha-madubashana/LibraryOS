package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.ScrollableList;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.BookAvailabilityRow;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.RecentActivityRow;
import io.github.sachintha_madubashana.libraryos.event.NavigationEvent;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    private HBox listContainer;

    @FXML
    private void initialize() {
        ScrollableList<Book> bookAvailability = createBookAvailability();
        ScrollableList<Activity> recentActivity = createRecentActivity();

        HBox.setHgrow(recentActivity, Priority.ALWAYS);
        HBox.setHgrow(bookAvailability, Priority.ALWAYS);

        listContainer.getChildren().add(recentActivity);
        listContainer.getChildren().add(bookAvailability);
    }

    private ScrollableList<Book> createBookAvailability() {
        ScrollableList<Book> bookAvailability = new ScrollableList<>(BookAvailabilityRow::new);
        Button bookHeaderBtn = new Button("Add Book   →");
        bookHeaderBtn.setOnAction(e -> {
            root.fireEvent(new NavigationEvent(MainController.ADD_BOOK));
            e.consume();
        });

        bookAvailability.setHeaderText("Book Availability", bookHeaderBtn);
        bookAvailability.setItems(AppServices.getInstance().getBookService().getAllBooks());
        return bookAvailability;
    }

    private ScrollableList<Activity> createRecentActivity() {
        ScrollableList<Activity> recentActivity = new ScrollableList<>(RecentActivityRow::new);
        Button headerBtn = new Button("VIEW ALL   →");
        headerBtn.setOnAction(e -> {
            root.fireEvent(new NavigationEvent(MainController.HISTORY));
            e.consume();
        });

        recentActivity.setHeaderText("Recent Activity", headerBtn);
        recentActivity.setItems(AppServices.getInstance().getRecentActivityService().getRecentActivities());
        return recentActivity;
    }
}
