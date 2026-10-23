package io.github.sachintha_madubashana.libraryos.model;

public class Activity {
    private String bookId;
    private String bookTitle;
    private String borrowerId;
    private String borrower;
    private String date;
    private BorrowStatus status;

    public Activity(String bookId, String bookTitle, String borrowerId, String borrower, String date, BorrowStatus status) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowerId = borrowerId;
        this.borrower = borrower;
        this.date = date;
        this.status = status;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book Title: " + bookTitle + ", Borrower: " + borrower + ", Date: " + date + ", Status: " + status.toString();
    }
}
