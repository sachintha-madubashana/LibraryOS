package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Activity;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;

import java.util.List;

public class DashboardService {
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;

    public DashboardService(BookRepository bookRepository, BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
    }

    public Result<List<BorrowingRecord>> getBorrowingRecords() {
        List<BorrowingRecord> borrowingRecords = borrowingRepository.findAll();

        if (borrowingRecords == null) {
            return Result.error(
                    "Unable to retrieve Borrowing Records",
                    "The Borrowing Record list could not be loaded."
            );
        }

        return Result.success(
                "Borrowing Records retrieved successfully",
                "The Borrowing Record list has been loaded.",
                borrowingRecords
        );
    }

    public Result<List<Activity>> findAllActivities() {
        List<Activity> activities = borrowingRepository.findAllActivities();

        if (activities == null) {
            return Result.error(
                    "Unable to retrieve Activities",
                    "The Activity list could not be loaded."
            );
        }

        return Result.success(
                "Activities retrieved successfully",
                "The Activity list has been loaded.",
                activities
        );
    }
}
