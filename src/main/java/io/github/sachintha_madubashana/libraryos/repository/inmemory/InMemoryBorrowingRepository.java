package io.github.sachintha_madubashana.libraryos.repository.inmemory;

import io.github.sachintha_madubashana.libraryos.model.BorrowStatus;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
import io.github.sachintha_madubashana.libraryos.service.SampleData;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBorrowingRepository implements BorrowingRepository {
    private final List<BorrowingRecord> borrowings;

    public InMemoryBorrowingRepository() {
        this.borrowings = new ArrayList<>(SampleData.getBorrowingRecords());
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
    public BorrowingRecord findLast() {
        return borrowings.isEmpty() ? null : borrowings.getLast();
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

    @Override
    public List<BorrowingRecord> findByStatuses(List<BorrowStatus> statuses) {
        if (statuses == null) return new ArrayList<>();

        return borrowings.stream()
                .filter(borrowingRecord -> statuses.contains(borrowingRecord.getStatus()))
                .toList();
    }

    @Override
    public Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses) {
        if (statuses == null) return new EnumMap<>(BorrowStatus.class);

        return borrowings.stream()
                .filter(borrowingRecord -> statuses.contains(borrowingRecord.getStatus()))
                .collect(Collectors.groupingBy(BorrowingRecord::getStatus, Collectors.counting()));
    }
}
