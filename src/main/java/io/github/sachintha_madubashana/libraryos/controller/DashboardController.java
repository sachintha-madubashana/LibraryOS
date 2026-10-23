package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.ScrollableList;
import io.github.sachintha_madubashana.libraryos.controller.component.StatsCard;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.BookAvailabilityRow;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.RecentActivityRow;
import io.github.sachintha_madubashana.libraryos.event.NavigationEvent;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.navigation.ScenesManager;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    private HBox statsCardContainer;

    @FXML
    private HBox listContainer;

    private StatsCard totalBook;
    private StatsCard totalMember;
    private StatsCard totalBorrowing;
    private StatsCard overDueBorrowing;

    @FXML
    private void initialize() {
        loadStatsCards();
        ScrollableList<Book> bookAvailability = createBookAvailability();
        ScrollableList<BorrowingRecord> recentActivity = createRecentActivity();

        HBox.setHgrow(recentActivity, Priority.ALWAYS);
        HBox.setHgrow(bookAvailability, Priority.ALWAYS);

        listContainer.getChildren().add(recentActivity);
        listContainer.getChildren().add(bookAvailability);
        loadStatsCardData();
    }

    private ScrollableList<Book> createBookAvailability() {
        ScrollableList<Book> bookAvailability = new ScrollableList<>(BookAvailabilityRow::new);
        Button bookHeaderBtn = new Button("Add Book   →");
        bookHeaderBtn.setOnAction(e -> {
            root.fireEvent(new NavigationEvent(ScenesManager.ADD_BOOK));
            e.consume();
        });

        bookAvailability.setHeaderText("Book Availability", bookHeaderBtn);
        bookAvailability.setItems(FXCollections.observableArrayList(AppServices.getInstance().getBookService().getAllBooks().getData()));
        return bookAvailability;
    }

    private ScrollableList<BorrowingRecord> createRecentActivity() {
        ScrollableList<BorrowingRecord> recentActivity = new ScrollableList<>(RecentActivityRow::new);
        Button headerBtn = new Button("VIEW ALL   →");
        headerBtn.setOnAction(e -> {
            root.fireEvent(new NavigationEvent(ScenesManager.HISTORY));
            e.consume();
        });

        recentActivity.setHeaderText("Recent Activity", headerBtn);

        ObservableList<BorrowingRecord> activities = FXCollections.observableArrayList(AppServices.getInstance().getDashboardService().findAllActivities().getData());

        recentActivity.setItems(activities);
        return recentActivity;
    }

    private void loadStatsCards() {
        totalBook = new StatsCard(Feather.BOOK, String.valueOf(0), "Total Books", "6 titles");
        HBox.setHgrow(totalBook, Priority.ALWAYS);
        totalMember = new StatsCard(Feather.USER, String.valueOf(0), "Total Members", "registered");
        HBox.setHgrow(totalMember, Priority.ALWAYS);
        totalBorrowing = new StatsCard(Feather.BOOK_OPEN, String.valueOf(0), "Currently Borrowed", "active loans");
        HBox.setHgrow(totalBorrowing, Priority.ALWAYS);
        overDueBorrowing = new StatsCard(Feather.PAUSE, String.valueOf(0), "Overdue Borrowings", "require attention");
        HBox.setHgrow(overDueBorrowing, Priority.ALWAYS);

        statsCardContainer.getChildren().clear();
        statsCardContainer.getChildren().add(totalBook);
        statsCardContainer.getChildren().add(totalMember);
        statsCardContainer.getChildren().add(totalBorrowing);
        statsCardContainer.getChildren().add(overDueBorrowing);
    }

    private void loadStatsCardData() {
        totalBook.setNumber(String.valueOf(AppServices.getInstance().getDashboardService().countBooks().getData()));
        totalMember.setNumber(String.valueOf(AppServices.getInstance().getDashboardService().countMembers().getData()));
        totalBorrowing.setNumber(String.valueOf(AppServices.getInstance().getDashboardService().countBorrowings().getData()));
        overDueBorrowing.setNumber(String.valueOf(AppServices.getInstance().getDashboardService().countOverdueBorrowings().getData()));
    }
}
