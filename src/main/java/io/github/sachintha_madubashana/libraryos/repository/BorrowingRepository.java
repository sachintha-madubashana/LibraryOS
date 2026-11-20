package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.BorrowStatus;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BorrowingRepository {
    Optional<BorrowingRecord> findById(String id);
    boolean existsById(String id);
    List<BorrowingRecord> findAll();
    boolean save(BorrowingRecord borrowingRecord);
    boolean update(BorrowingRecord borrowingRecord);
    boolean deleteById(String id);
    BorrowingRecord findLast();
    List<BorrowingRecord> findByStatuses(List<BorrowStatus> statuses);
    Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses);
}
