package io.github.sachintha_madubashana.libraryos.event;

import javafx.event.Event;
import javafx.event.EventType;

public class NavigationEvent extends Event {
    public static final EventType<NavigationEvent> NAVIGATE = new EventType<>(Event.ANY, "NAVIGATE");

    private final String targetScene;

    public NavigationEvent(String targetScene) {
        super(NAVIGATE);
        this.targetScene = targetScene;
    }

    public String getTargetScene() {
        return targetScene;
    }
}
