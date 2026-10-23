package io.github.sachintha_madubashana.libraryos.event;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import javafx.event.Event;
import javafx.event.EventType;

public class ReturnBookSelectEvent extends Event {
    public static final EventType<ReturnBookSelectEvent> RETURN_BOOK_SELECT = new EventType<>(Event.ANY, "RETURN_BOOK_SELECT");

    private final BorrowingRecord borrowingRecord;

    public ReturnBookSelectEvent(BorrowingRecord borrowingRecord) {
        super(RETURN_BOOK_SELECT);
        this.borrowingRecord = borrowingRecord;
    }

    public BorrowingRecord getBorrowingRecord() {
        return borrowingRecord;
    }
}
