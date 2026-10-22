package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;

import java.util.List;

public class BorrowingHistoryService {
    private final BorrowingRepository borrowingRepository;

    public BorrowingHistoryService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public Result<BorrowingRecord> addBorrowing(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null) {
            return Result.error(
                    "Invalid borrowing record",
                    "Borrowing information is required."
            );
        }

        if (borrowingRepository.existsById(borrowingRecord.getRecordId())) {
            return Result.error(
                    "Duplicate borrowing record ID",
                    "A borrowing record with this ID already exists."
            );
        }

        boolean isSaved = borrowingRepository.save(borrowingRecord);

        if (!isSaved) {
            return Result.error(
                    "Unable to add borrowing record",
                    "The borrowing record could not be saved."
            );
        }

        return Result.success(
                "Borrowing record added successfully",
                "The new borrowing record has been added.",
                borrowingRecord
        );
    }

    public Result<List<BorrowingRecord>> getBorrowings() {
        List<BorrowingRecord> borrowingsList = borrowingRepository.findAll();

        if (borrowingsList == null) {
            return Result.error(
                    "Unable to retrieve Borrowings",
                    "The Borrowing list could not be loaded."
            );
        }

        return Result.success(
                "Borrowings retrieved successfully",
                "The borrowing list has been loaded.",
                borrowingsList
        );
    }

    public Result<BorrowingRecord> updateBorrowing(BorrowingRecord borrowing) {
        if (borrowing == null) {
            return Result.error(
                    "Invalid borrowing",
                    "Borrowing information is required."
            );
        }

        if (!borrowingRepository.existsById(borrowing.getRecordId())) {
            return Result.error(
                    "Borrowing not found",
                    "The borrowing with the provided ID does not exist."
            );
        }

        boolean isUpdated = borrowingRepository.update(borrowing);

        if (!isUpdated) {
            return Result.error(
                    "Unable to update borrowing",
                    "The borrowing could not be isUpdated."
            );
        }

        return Result.success(
                "Borrowing updated successfully",
                "The borrowing has been updated.",
                borrowing
        );
    }

    public Result<String> generateBookId() {
        List<BorrowingRecord> borrowingRecords   = borrowingRepository.findAll();
        String newRecordId = getNewBorrowingId(0);

        if (!borrowingRecords.isEmpty()) {
            BorrowingRecord lastBorrowingRecord = borrowingRecords.get(borrowingRecords.size() - 1);
            int recordCount = Integer.parseInt(lastBorrowingRecord.getRecordId().substring(2));
            newRecordId = getNewBorrowingId(recordCount);
        }

        return Result.success(
                "Borrowing id generated successfully",
                "The borrowing id has been generated.",
                newRecordId
        );
    }

    private String getNewBorrowingId(int number) {
        return "BR" + String.format("%05d", (number + 1));
    }

}
