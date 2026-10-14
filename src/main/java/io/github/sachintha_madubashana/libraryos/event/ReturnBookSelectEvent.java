package io.github.sachintha_madubashana.libraryos.event;

import io.github.sachintha_madubashana.libraryos.model.Activity;
import javafx.event.Event;
import javafx.event.EventType;

public class ReturnBookSelectEvent extends Event {
    public static final EventType<ReturnBookSelectEvent> RETURN_BOOK_SELECT = new EventType<>(Event.ANY, "RETURN_BOOK_SELECT");

    private final Activity activity;

    public ReturnBookSelectEvent(Activity activity) {
        super(RETURN_BOOK_SELECT);
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
