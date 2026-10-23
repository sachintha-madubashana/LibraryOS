package io.github.sachintha_madubashana.libraryos.model;

public class Activity {
    private String bookTitle;
    private String borrower;
    private String date;
    private BorrowStatus status;

    public Activity(String bookTitle, String borrower, String date, BorrowStatus status) {
        this.bookTitle = bookTitle;
        this.borrower = borrower;
        this.date = date;
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
