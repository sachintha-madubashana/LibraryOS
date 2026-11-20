package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.BookEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.BorrowingRecordEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.BorrowingStatusEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.MemberEntity;
import io.github.sachintha_madubashana.libraryos.model.*;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

public class HibernateBorrowingRepository implements BorrowingRepository {

    private static final String FIND_BY_RECORD_ID_QUERY =
            "FROM BorrowingRecordEntity WHERE recordId = :code";

    @Override
    public Optional<BorrowingRecord> findById(String code) {
        if (code == null || code.isEmpty()) return Optional.empty();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BorrowingRecordEntity entity = session.createQuery(FIND_BY_RECORD_ID_QUERY, BorrowingRecordEntity.class)
                    .setParameter("code", code)
                    .uniqueResult();
            if (entity != null) return Optional.of(toDto(entity));
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(String code) {
        return findById(code).isPresent();
    }

    @Override
    public List<BorrowingRecord> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<BorrowingRecordEntity> entities = session
                    .createQuery("FROM BorrowingRecordEntity ORDER BY recordId DESC", BorrowingRecordEntity.class)
                    .list();
            List<BorrowingRecord> records = new ArrayList<>();
            for (BorrowingRecordEntity entity : entities) {
                records.add(toDto(entity));
            }
            return records;
        }
    }

    //Need to Delete this method
    // --- IGNORE ---
    @Override
    public Integer countAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(*) FROM BorrowingRecordEntity", Long.class).uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    @Override
    public BorrowingRecord findLast() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BorrowingRecordEntity entity = session
                    .createQuery("FROM BorrowingRecordEntity ORDER BY recordId DESC", BorrowingRecordEntity.class)
                    .setMaxResults(1)
                    .uniqueResult();
            if (entity != null) return toDto(entity);
            return null;
        }
    }

    @Override
    public boolean save(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || existsById(borrowingRecord.getRecordId())) {
            return false;
        }

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            MemberEntity memberEntity = session.find(MemberEntity.class, borrowingRecord.getMember().getId());
            if (memberEntity == null) return false;

            BookEntity bookEntity = session.find(BookEntity.class, borrowingRecord.getBook().getId());
            if (bookEntity == null) return false;

            BorrowingStatusEntity statusEntity = BorrowingStatusEntity.fromBorrowStatus(borrowingRecord.getStatus());
            statusEntity = session.merge(statusEntity);

            BorrowingRecordEntity entity = new BorrowingRecordEntity();
            entity.setRecordId(borrowingRecord.getRecordId());
            entity.setMember(memberEntity);
            entity.setBook(bookEntity);
            entity.setIssueDate(borrowingRecord.getIssueDate());
            entity.setDueDate(borrowingRecord.getDueDate());
            entity.setReturnDate(borrowingRecord.getReturnDate());
            entity.setStatus(statusEntity);

            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to save borrowing record.", e);
        }
    }

    @Override
    public boolean update(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || borrowingRecord.getRecordId() == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BorrowingRecordEntity entity = session
                    .createQuery(FIND_BY_RECORD_ID_QUERY, BorrowingRecordEntity.class)
                    .setParameter("code", borrowingRecord.getRecordId())
                    .uniqueResult();
            if (entity == null) return false;

            MemberEntity memberEntity = session.find(MemberEntity.class, borrowingRecord.getMember().getId());
            BookEntity bookEntity = session.find(BookEntity.class, borrowingRecord.getBook().getId());
            BorrowingStatusEntity statusEntity = BorrowingStatusEntity.fromBorrowStatus(borrowingRecord.getStatus());
            statusEntity = session.merge(statusEntity);

            entity.setMember(memberEntity);
            entity.setBook(bookEntity);
            entity.setIssueDate(borrowingRecord.getIssueDate());
            entity.setDueDate(borrowingRecord.getDueDate());
            entity.setReturnDate(borrowingRecord.getReturnDate());
            entity.setStatus(statusEntity);

            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to update borrowing record.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if (code == null || !existsById(code)) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            BorrowingRecordEntity entity = session
                    .createQuery(FIND_BY_RECORD_ID_QUERY, BorrowingRecordEntity.class)
                    .setParameter("code", code)
                    .uniqueResult();

            if (entity == null) return false;

            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete borrowing record.", e);
        }
    }

    //Need to Delete this method
    @Override
    public List<BorrowingRecord> findActiveLoans() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<BorrowingRecordEntity> entities = session
                    .createQuery("FROM BorrowingRecordEntity WHERE status.id != :returnedId", BorrowingRecordEntity.class)
                    .setParameter("returnedId", BorrowStatus.RETURNED.getValue())
                    .list();
            List<BorrowingRecord> records = new ArrayList<>();
            for (BorrowingRecordEntity entity : entities) {
                records.add(toDto(entity));
            }
            return records;
        }
    }

    //Need to Delete this method
    @Override
    public List<BorrowingRecord> findAllOverdue() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<BorrowingRecordEntity> entities = session
                    .createQuery("FROM BorrowingRecordEntity WHERE status.id = :overdueId", BorrowingRecordEntity.class)
                    .setParameter("overdueId", BorrowStatus.OVERDUE.getValue())
                    .list();
            List<BorrowingRecord> records = new ArrayList<>();
            for (BorrowingRecordEntity entity : entities) {
                records.add(toDto(entity));
            }
            return records;
        }
    }

    @Override
    public List<BorrowingRecord> findByStatus(BorrowStatus status) {
        if (status == null) return new ArrayList<>();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<BorrowingRecordEntity> entities = session
                    .createQuery("FROM BorrowingRecordEntity WHERE status.id = :statusId", BorrowingRecordEntity.class)
                    .setParameter("statusId", status.getValue())
                    .list();
            List<BorrowingRecord> records = new ArrayList<>();
            for (BorrowingRecordEntity entity : entities) {
                records.add(toDto(entity));
            }
            return records;
        }
    }

    public Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses){
        Map<BorrowStatus, Long> counts = new EnumMap<>(BorrowStatus.class);
        if (statuses == null || statuses.isEmpty()) {
            return counts;
        }

        StringBuilder query = new StringBuilder("SELECT status.id, COUNT(*) FROM BorrowingRecordEntity WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        for(BorrowStatus status : statuses){
            if (status == null) continue;

            query.append(" OR status.id = :").append(status.name());
            params.put(status.name(), status.getValue());
        }
        query.append(" GROUP BY status.id");

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query<Object[]> sessionQuery = session.createQuery(query.toString(), Object[].class);
            params.forEach(sessionQuery::setParameter);

            List<Object[]> result = sessionQuery.getResultList();

            for (var row : result) {
                Integer statusId = (Integer) row[0];
                Long count = (Long) row[1];
                counts.put(BorrowStatus.fromValue(statusId), count);
            }
            return counts;
        }
    }

    private BorrowingRecord toDto(BorrowingRecordEntity entity) {
        Category category = new Category(
                entity.getBook().getCategory().getId(),
                entity.getBook().getCategory().getName()
        );

        Book book = new Book(
                entity.getBook().getId(),
                entity.getBook().getCode(),
                entity.getBook().getTitle(),
                entity.getBook().getAuthor(),
                entity.getBook().getPublishedYear(),
                category,
                entity.getBook().getTotalCopies(),
                entity.getBook().getAvailableCopies()
        );

        Member member = new Member(
                entity.getMember().getId(),
                entity.getMember().getCode(),
                entity.getMember().getFullName(),
                entity.getMember().getEmail(),
                entity.getMember().getPhone(),
                entity.getMember().getAddress(),
                entity.getMember().getJoinedDate()
        );

        BorrowStatus status = entity.getStatus().toBorrowStatus();

        return new BorrowingRecord(
                entity.getRecordId(),
                member,
                book,
                entity.getIssueDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                status
        );
    }
}
