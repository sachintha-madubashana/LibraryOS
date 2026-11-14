package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;

import java.util.List;
import java.util.Optional;

public class HibernateBorrowingRepository implements BorrowingRepository {
    @Override
    public Optional<BorrowingRecord> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public List<BorrowingRecord> findAll() {
        return List.of();
    }

    @Override
    public boolean save(BorrowingRecord borrowingRecord) {
        return false;
    }

    @Override
    public boolean update(BorrowingRecord borrowingRecord) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public List<BorrowingRecord> findActiveLoans() {
        return List.of();
    }

    @Override
    public List<BorrowingRecord> findAllOverdue() {
        return List.of();
    }

    @Override
    public Integer countAll() {
        return 0;
    }

    @Override
    public BorrowingRecord findLast() {
        return null;
    }
}
