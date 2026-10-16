package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.ScrollableList;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.BookAvailabilityRow;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.RecentActivityRow;
import io.github.sachintha_madubashana.libraryos.event.NavigationEvent;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<Activity> items = FXCollections.observableArrayList();
        items.setAll(
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
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.RETURNED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.RETURNED));
        recentActivity.setItems(items);
        return recentActivity;
    }
}
