package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.controller.component.*;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowStatus;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class IssueBookController {
    @FXML
    private DropdownSelect<Member> memberDropdown;

    @FXML
    private DropdownSelect<Book> bookDropdown;

    @FXML
    private InputField issueDateInput;

    @FXML
    private InputField dueDateInput;

    @FXML
    private VBox column1;

    @FXML
    private VBox column2;

    @FXML
    private VBox column3;

    @FXML
    private Button issueBtn;

    @FXML
    private Button clearBtn;

    private Member selectedMember;
    private Book selectedBook;

    @FXML
    private void initialize() {
        issueDateInput.setDateValue(LocalDate.now(ZoneId.systemDefault()));
        dueDateInput.setDateValue(LocalDate.now(ZoneId.systemDefault()).plusDays(7));

        Result<List<Member>> memberResult = AppServices.getInstance().getMemberService().getMembers();

        if (memberResult.isError()) {
            System.out.println(memberResult.getMessage());
            return;
        }

        memberDropdown.getItems().addAll(memberResult.getData());
        memberDropdown.setOnSelectionChanged((event -> {
            if (memberDropdown.getValue() == null) {
                return;
            }

            Result<Member> result = AppServices.getInstance().getMemberService().getMemberById(memberDropdown.getValue().getCode());

            if (result.isError()) {
                System.out.println(result.getMessage());
                return;
            }

            selectedMember = result.getData();
            loadMemberInfoCard(selectedMember);
        }));

        Result<List<Book>> bookResult = AppServices.getInstance().getBookService().getAllBooks();

        if (bookResult.isError()) {
            System.out.println(bookResult.getMessage());
            return;
        }

        bookDropdown.getItems().addAll(bookResult.getData());
        bookDropdown.setOnSelectionChanged(event -> {
            if (bookDropdown.getValue() == null) {
                return;
            }

            Result<Book> result = AppServices.getInstance().getBookService().getBookById(bookDropdown.getValue().getCode());

            if (result.isError()) {
                System.out.println(result.getMessage());
                return;
            }

            selectedBook = result.getData();
            loadBookInfoCard(selectedBook);
        });

        loadEmptyInfoCards();
        clearBtn.setOnAction(event -> clear());
        issueBtn.setOnAction(event -> issueBook());
    }

    private void loadEmptyInfoCards() {
        EmptyInfoCard memberInfoCard = new EmptyInfoCard("No Member Details", "Please select a member to see member details", Feather.BOOK, InfoCard.LayoutType.LARGE);
        VBox.setVgrow(memberInfoCard, Priority.ALWAYS);
        column2.getChildren().setAll(memberInfoCard);
        EmptyInfoCard bookInfoCard = new EmptyInfoCard("No Book Details", "Please select a book to see details", Feather.BOOK, InfoCard.LayoutType.LARGE);
        VBox.setVgrow(bookInfoCard, Priority.ALWAYS);
        column3.getChildren().setAll(bookInfoCard);
    }

    private void loadMemberInfoCard(Member member) {
        Image memberImage = new Image(
                Objects.requireNonNull(Launcher.class.getResourceAsStream(
                        "/io/github/sachintha_madubashana/libraryos/image/user.png"
                ))
        );

        MemberInfoCard memberInfoCard = new MemberInfoCard(member, MemberInfoCard.Layout.LARGE);
        memberInfoCard.setMemberImage(memberImage);
        VBox.setVgrow(memberInfoCard, Priority.ALWAYS);
        column2.getChildren().setAll(memberInfoCard);
    }

    private void loadBookInfoCard(Book book) {
        Image bookImage = new Image(
                Objects.requireNonNull(Launcher.class.getResourceAsStream(
                        "/io/github/sachintha_madubashana/libraryos/image/book.png"
                ))
        );

        BookInfoCard bookInfoCard = new BookInfoCard(book, BookInfoCard.Layout.LARGE);
        bookInfoCard.setBookImage(bookImage);
        VBox.setVgrow(bookInfoCard, Priority.ALWAYS);
        column3.getChildren().setAll(bookInfoCard);
    }

    private void clear() {
        memberDropdown.clearSelection();
        bookDropdown.clearSelection();
        issueDateInput.clear();
        dueDateInput.clear();
        loadEmptyInfoCards();
    }

    private void issueBook() {
        Result<String> borrowingIdResult = AppServices.getInstance().getBorrowingHistoryService().generateBorrowingId();
        if (borrowingIdResult.isError()) {
            System.out.println("Error generating borrowing ID");
            return;
        }

        String borrowingId = borrowingIdResult.getData();

        BorrowingRecord borrowing = new BorrowingRecord(borrowingId, selectedMember, selectedBook, issueDateInput.getDateValue(), dueDateInput.getDateValue(), null, BorrowStatus.BORROWED);

        Result<BorrowingRecord> result = AppServices.getInstance().getBorrowingHistoryService().addBorrowing(borrowing);
        if (result.isError()) {
            System.out.println(result.getMessage());
            return;
        }

        AlertMessage alert = new AlertMessage(result);
        clear();
        alert.showIn(column1);
    }
}
