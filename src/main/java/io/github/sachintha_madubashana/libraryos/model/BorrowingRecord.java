package io.github.sachintha_madubashana.libraryos.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BorrowingRecord implements Serializable {
    private final String recordId;
    private final String member;
    private final String bookTitle;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private final LocalDate returnDate;
    private final String status;

    public BorrowingRecord(String recordId, String member, String bookTitle, LocalDate issueDate, LocalDate dueDate, LocalDate returnDate, String status) {
        this.recordId = recordId;
        this.member = member;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getMember() {
        return member;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }
}
