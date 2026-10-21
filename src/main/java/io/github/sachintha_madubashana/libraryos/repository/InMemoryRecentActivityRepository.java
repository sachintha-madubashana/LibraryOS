package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InMemoryRecentActivityRepository {
    private final ObservableList<Activity> recentActivities;

    public InMemoryRecentActivityRepository() {
        this.recentActivities = FXCollections.observableArrayList(SampleData.createRecentActivities());
    }

    public ObservableList<Activity> getRecentActivities() {
        return recentActivities;
    }

    public void addRecentActivity(Activity activity) {
        recentActivities.add(activity);
    }
}
