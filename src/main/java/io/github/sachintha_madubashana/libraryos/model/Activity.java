package io.github.sachintha_madubashana.libraryos.model;

public class Activity {
    private String title;
    private String borrower;
    private String date;
    private ActivityStatus status;

    public Activity(String title, String borrower, String date, ActivityStatus status) {
        this.title = title;
        this.borrower = borrower;
        this.date = date;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
}
