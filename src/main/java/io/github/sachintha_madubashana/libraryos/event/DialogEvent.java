package io.github.sachintha_madubashana.libraryos.event;

import io.github.sachintha_madubashana.libraryos.model.DialogData;
import io.github.sachintha_madubashana.libraryos.model.Notification;
import javafx.event.Event;
import javafx.event.EventType;

public class DialogEvent extends Event {
    public static final EventType<DialogEvent> DIALOG_EVENT = new EventType<>(Event.ANY, "DIALOG_EVENT");

    private final transient DialogData targetDialogData;

    public DialogEvent(DialogData data){
        super(DIALOG_EVENT);
        this.targetDialogData = data;
    }

    public DialogData getTargetDialogData(){
        return targetDialogData;
    }
}
