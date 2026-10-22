package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBorrowingRepository implements BorrowingRepository {
    private final List<BorrowingRecord> borrowings;

    public InMemoryBorrowingRepository() {
        this.borrowings = new ArrayList<>(SampleData.createBorrowings());
    }

    @Override
    public Optional<BorrowingRecord> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }

        return borrowings.stream()
                .filter(borrowing -> borrowing.getRecordId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<BorrowingRecord> findAll() {
        return List.copyOf(borrowings);
    }

    @Override
    public boolean save(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || existsById(borrowingRecord.getRecordId())) {
            return false;
        }

        borrowings.add(borrowingRecord);
        return true;
    }

    @Override
    public boolean update(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || borrowingRecord.getRecordId() == null) {
            return false;
        }

        for (int i = 0; i < borrowings.size(); i++) {
            if (borrowings.get(i).getRecordId().equals(borrowingRecord.getRecordId())) {
                borrowings.set(i, borrowingRecord);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }

        return borrowings.removeIf(borrowing -> borrowing.getRecordId().equals(id));
    }
}
