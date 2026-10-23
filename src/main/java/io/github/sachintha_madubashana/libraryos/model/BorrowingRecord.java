package io.github.sachintha_madubashana.libraryos.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BorrowingRecord implements Serializable {
    private final String recordId;
    private final Member member;
    private final Book book;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private final LocalDate returnDate;
    private final BorrowStatus status;

    public BorrowingRecord(String recordId, Member member, Book book, LocalDate issueDate, LocalDate dueDate, LocalDate returnDate, BorrowStatus status) {
        this.recordId = recordId;
        this.member = member;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getRecordId() {
        return recordId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
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

    public BorrowStatus getStatus() {
        return status;
    }
}
