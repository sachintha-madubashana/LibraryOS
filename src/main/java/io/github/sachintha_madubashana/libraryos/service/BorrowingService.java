package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;

public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    public BorrowingService(BorrowingRepository borrowingRepository) {
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

    public boolean deleteBorrowing(String recordId) {
        return borrowingRepository.deleteById(recordId);
    }
}
