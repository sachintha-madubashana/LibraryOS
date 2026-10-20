package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InMemoryBorrowingRepository {
    private final ObservableList<BorrowingRecord> borrowings;

    public InMemoryBorrowingRepository() {
        this.borrowings = FXCollections.observableArrayList(SampleData.createBorrowings());
    }

    public ObservableList<BorrowingRecord> getBorrowings() {
        return borrowings;
    }

    public void addBorrowing(BorrowingRecord borrowing) {
        borrowings.add(borrowing);
    }

    public void updateBorrowing(BorrowingRecord borrowing) {
        int index = borrowings.indexOf(borrowing);
        if (index != -1) {
            borrowings.set(index, borrowing);
        }
    }

    public void removeBorrowing(BorrowingRecord borrowing) {
        borrowings.remove(borrowing);
    }
}
