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
    public Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses) {
        if (statuses == null) return new EnumMap<>(BorrowStatus.class);

        return borrowings.stream()
                .filter(borrowingRecord -> statuses.contains(borrowingRecord.getStatus()))
                .collect(Collectors.groupingBy(BorrowingRecord::getStatus, Collectors.counting()));
    }

    @Override
    public List<BorrowingRecord> searchBorrowingsWithStatuses(List<String> terms, List<BorrowStatus> statuses) {
        List<BorrowingRecord> result = new ArrayList<>();
        if (terms == null || statuses == null) return result;

        List<String> cleanTerms = terms.stream()
                .filter(term -> term != null && !term.isBlank())
                .map(String::toLowerCase)
                .toList();

        boolean filterByStatus = !statuses.isEmpty() && !statuses.contains(BorrowStatus.ALL);

        result.addAll(borrowings.stream()
                .filter(br -> {
                    if (filterByStatus && !statuses.contains(br.getStatus())) {
                        return false;
                    }
                    return cleanTerms.stream().allMatch(term ->
                            (br.getRecordId() != null && br.getRecordId().toLowerCase().contains(term)) ||
                            (br.getMember() != null && br.getMember().getFullName() != null && br.getMember().getFullName().toLowerCase().contains(term)) ||
                            (br.getBook() != null && br.getBook().getTitle() != null && br.getBook().getTitle().toLowerCase().contains(term))
                    );
                }).toList());

        return result;
    }
}
