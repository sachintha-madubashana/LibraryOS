package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryRecentActivityRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecentActivityService {
    private final InMemoryRecentActivityRepository recentActivitiesRepository;

    public RecentActivityService(InMemoryRecentActivityRepository recentActivitiesRepository) {
        this.recentActivitiesRepository = recentActivitiesRepository;
    }

    public ObservableList<Activity> getRecentActivities() {
        return recentActivitiesRepository.getRecentActivities();
    }

    public void addRecentActivity(Activity activity) {
        recentActivitiesRepository.addRecentActivity(activity);
    }
}
