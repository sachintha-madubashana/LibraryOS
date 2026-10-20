package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryBorrowingRepository;
import javafx.collections.ObservableList;

public class BorrowingHistoryService {
    private final InMemoryBorrowingRepository borrowingRepository;

    public BorrowingHistoryService(InMemoryBorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public ObservableList<BorrowingRecord> getBorrowings() {
        return borrowingRepository.getBorrowings();
    }

    public void addBorrowing(BorrowingRecord borrowing) {
        borrowingRepository.addBorrowing(borrowing);
    }

    public void updateBorrowing(BorrowingRecord borrowing) {
        borrowingRepository.updateBorrowing(borrowing);
    }

    public void removeBorrowing(BorrowingRecord borrowing) {
        borrowingRepository.removeBorrowing(borrowing);
    }
}
