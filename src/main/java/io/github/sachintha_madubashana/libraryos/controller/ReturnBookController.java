package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.*;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.ActiveLoanRow;
import io.github.sachintha_madubashana.libraryos.event.ReturnBookSelectEvent;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.ActivityStatus;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ReturnBookController {

    @FXML
    private HBox root;

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
            AlertMessage alert = new AlertMessage("Return Date Required", "Please select a return date.", AlertMessage.Type.WARNING);
            alert.showIn(column2);
            return;
        }

        AlertMessage alert = new AlertMessage("Confirm Return", "Return date: " + returnDate, AlertMessage.Type.INFO);
        alert.showIn(column2);
    }

    @FXML
    private void initialize() {
        returnDatePicker.setDateValue(LocalDate.now());

        FontIcon fontIcon = new FontIcon(Feather.SEARCH);
        fontIcon.setIconSize(20);
        fontIcon.getStyleClass().add("button-icon");
        searchBtn.setGraphic(fontIcon);

        ScrollableList<Activity> recentActivityList = new ScrollableList<>(ActiveLoanRow::new);
        ObservableList<Activity> items = FXCollections.observableArrayList();
        items.setAll(new Activity("To Kill a Mockingbird", "Sofia Andersen", "2026-08-01", ActivityStatus.OVERDUE),
                new Activity("The Great Gatsby", "Eleanor Whitfield", "2026-09-20", ActivityStatus.BORROWED),
                new Activity("Brave New World", "James Okafor", "2026-09-10", ActivityStatus.BORROWED),
                new Activity("Moby Dick", "Priya Nair", "2026-08-10", ActivityStatus.OVERDUE),
                new Activity("1984", "Marcus Chen", "2026-09-05", ActivityStatus.BORROWED),
                new Activity("The Hitchhiker's Guide to the Galaxy", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("Harry Potter and the Philosopher's Stone", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Lord of the Rings", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED),
                new Activity("The Hobbit", "Sophia Patel", "2026-09-15", ActivityStatus.BORROWED));
        recentActivityList.setItems(items);
        recentActivityList.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(recentActivityList, Priority.ALWAYS);

        column1.getChildren().add(recentActivityList);

        AlertMessage alertMessage = new AlertMessage("9 days overdue", null, AlertMessage.Type.WARNING);
        alertMessage.showIn(alertContainer, false);

        root.addEventHandler(ReturnBookSelectEvent.RETURN_BOOK_SELECT, event -> {
            System.out.println(event.getActivity().toString());
            MemberInfoCard compactCard = new MemberInfoCard(new Member("MEM001", "Eleanor Whitfield",
                    "e.whitfield@email.com", "555-0142", null, null),
                    MemberInfoCard.Layout.COMPACT);
            Image memberImage = new Image(
                    Objects.requireNonNull(Launcher.class.getResourceAsStream(
                            "/io/github/sachintha_madubashana/libraryos/image/user.png"
                    ))
            );
            BookInfoCard largeCard = new BookInfoCard(new Book("BOOK001", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 10,5), BookInfoCard.Layout.LARGE);

            largeCard.setBookImage(memberImage);
            compactCard.setMemberImage(memberImage);
            VBox.setVgrow(largeCard, Priority.ALWAYS);
            column3.getChildren().setAll(compactCard, largeCard);
            event.consume();
        });

        EmptyInfoCard memberInfoCard = new EmptyInfoCard("No Member Details", "Please select a book to see member details", Feather.BOOK, InfoCard.LayoutType.COMPACT);

        EmptyInfoCard bookInfoCard = new EmptyInfoCard("No Book Details", "Please select a book to see details", Feather.BOOK, InfoCard.LayoutType.LARGE);
        VBox.setVgrow(bookInfoCard, Priority.ALWAYS);
        column3.getChildren().addAll(memberInfoCard, bookInfoCard);


    }

}
