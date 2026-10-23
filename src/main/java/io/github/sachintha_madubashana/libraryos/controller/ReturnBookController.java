package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.*;
import io.github.sachintha_madubashana.libraryos.controller.component.rows.ActiveLoanRow;
import io.github.sachintha_madubashana.libraryos.event.ReturnBookSelectEvent;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
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

        ScrollableList<BorrowingRecord> recentActivityList = new ScrollableList<>(ActiveLoanRow::new);
        ObservableList<BorrowingRecord> items = FXCollections.observableArrayList(AppServices.getInstance().getBorrowingHistoryService().findActiveLoans().getData());
        recentActivityList.setItems(items);
        recentActivityList.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(recentActivityList, Priority.ALWAYS);
        recentActivityList.getListView().setOnMouseClicked(event -> {
            System.out.println("Mouse Clicked on Recent Activity");
            loadEmptyInfoCards();
        });

        column1.getChildren().add(recentActivityList);

        AlertMessage alertMessage = new AlertMessage("9 days overdue", null, AlertMessage.Type.WARNING);
        alertMessage.showIn(alertContainer, false);

        root.addEventHandler(ReturnBookSelectEvent.RETURN_BOOK_SELECT, this::selectEventHandler);
        loadEmptyInfoCards();
        column3.setSpacing(8);
    }

    private void selectEventHandler(ReturnBookSelectEvent event) {
        Image memberImage = new Image(
                Objects.requireNonNull(Launcher.class.getResourceAsStream(
                        "/io/github/sachintha_madubashana/libraryos/image/user.png"
                ))
        );

        Image bookImage = new Image(
                Objects.requireNonNull(Launcher.class.getResourceAsStream(
                        "/io/github/sachintha_madubashana/libraryos/image/book.png"
                ))
        );

        MemberInfoCard memberCard = new MemberInfoCard(event.getBorrowingRecord().getMember(), MemberInfoCard.Layout.COMPACT);
        BookInfoCard bookCard = new BookInfoCard(event.getBorrowingRecord().getBook(), BookInfoCard.Layout.LARGE);

        bookCard.setBookImage(bookImage);
        memberCard.setMemberImage(memberImage);

        bookCard.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(bookCard, Priority.ALWAYS);
        column3.getChildren().setAll(memberCard, bookCard);
        event.consume();
    }

    private void loadEmptyInfoCards() {
        EmptyInfoCard memberInfoCard = new EmptyInfoCard("No Member Details", "Please select a book to see member details", Feather.BOOK, InfoCard.LayoutType.COMPACT);
        EmptyInfoCard bookInfoCard = new EmptyInfoCard("No Book Details", "Please select a book to see details", Feather.BOOK, InfoCard.LayoutType.LARGE);
        VBox.setVgrow(bookInfoCard, Priority.ALWAYS);
        column3.getChildren().setAll(memberInfoCard, bookInfoCard);
    }
}
