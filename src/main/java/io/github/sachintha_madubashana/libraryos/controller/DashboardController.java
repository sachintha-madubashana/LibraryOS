package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.BookAvailability;
import io.github.sachintha_madubashana.libraryos.controller.component.RecentActivity;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import io.github.sachintha_madubashana.libraryos.model.Book;
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
                new Activity("1984", "Marcus Chen", "2026-09-05", ActivityStatus.BORROWED),
                new Activity("The Hitchhiker's Guide to the Galaxy", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("Harry Potter and the Philosopher's Stone", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED)
        ));

        BookAvailability bookAvailability = new BookAvailability();
        bookAvailability.setBooks(List.of(
                new Book("BK001", "To Kill a Mockingbird", "Harper Lee", "Fiction", 10, 5),
                new Book("BK002", "1984", "George Orwell", "Fiction", 5, 0),
                new Book("BK003", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 8, 2),
                new Book("BK004", "Pride and Prejudice", "Jane Austen", "Fiction", 7, 0),
                new Book("BK005", "The Catcher in the Rye", "J.D. Salinger", "Fiction", 6, 1),
                new Book("BK006", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 12, 7),
                new Book("BK007", "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 15, 0),
                new Book("BK008", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 20, 10),
                new Book("BK009", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 18, 12),
                new Book("BK010", "The Shining", "Stephen King", "Horror", 10, 5),
                new Book("BK011", "The Da Vinci Code", "Dan Brown", "Thriller", 14, 7),
                new Book("BK012", "The Chronicles of Narnia", "C.S. Lewis", "Fantasy", 16, 8),
                new Book("BK013", "The Lord of the Flies", "William Golding", "Fiction", 9, 3)
        ));

        HBox.setHgrow(recentActivity, Priority.ALWAYS);
        HBox.setHgrow(bookAvailability, Priority.ALWAYS);

        listContainer.getChildren().add(recentActivity);
        listContainer.getChildren().add(bookAvailability);
    }
}
