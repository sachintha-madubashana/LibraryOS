package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.BookEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.BorrowingRecordEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.BorrowingStatusEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.MemberEntity;
import io.github.sachintha_madubashana.libraryos.model.*;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
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

    @Override
    public List<BorrowingRecord> findByStatuses(List<BorrowStatus> statuses) {
        if (statuses == null) return  new ArrayList<>();

        StringBuilder query = new StringBuilder("FROM BorrowingRecordEntity WHERE 1=0");
        Map<String, Object> params = appendStatuesToQuery(statuses, query);

        return getBorrowingRecords(query, params);

//        //Criteria Query
//        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<BorrowingRecordEntity> cq = cb.createQuery(BorrowingRecordEntity.class);
//            var root = cq.from(BorrowingRecordEntity.class);
//
//            List<Predicate> predicates = new ArrayList<>();
//            for (BorrowStatus status : statuses) {
//                if (status != null) {
//                    predicates.add(cb.equal(root.get("status").get("id"), status.getValue()));
//                }
//            }
//
//            if (!predicates.isEmpty()) {
//                cq.where(cb.or(predicates.toArray(new Predicate[0])));
//            }
//
//            List<BorrowingRecordEntity> entities = session.createQuery(cq).getResultList();
//            for (BorrowingRecordEntity entity : entities) {
//                records.add(toDto(entity));
//            }
//            return records;
//        }

    }

    private Map<String, Object> appendStatuesToQuery(List<BorrowStatus> statuses, StringBuilder query) {
        Map<String, Object> params = new HashMap<>();

        for (BorrowStatus status : statuses) {
            if (status == null) continue;

            query.append(" OR status.id = :").append(status.name());
            params.put(status.name(), status.getValue());
        }
        return params;
    }

    @Override
    public Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses) {
        Map<BorrowStatus, Long> counts = new EnumMap<>(BorrowStatus.class);
        if (statuses == null || statuses.isEmpty()) {
            return counts;
        }

        StringBuilder query = new StringBuilder("SELECT status.id, COUNT(*) FROM BorrowingRecordEntity WHERE 1=0");
        Map<String, Object> params = appendStatuesToQuery(statuses, query);
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

    @Override
    public List<BorrowingRecord> searchBorrowings(List<String> terms) {
        if (terms == null || terms.isEmpty()) return new ArrayList<>();

        StringBuilder query = new StringBuilder("FROM BorrowingRecordEntity WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        for (int idx = 0; idx < terms.size(); idx++) {
            if (terms.get(idx) == null || terms.get(idx).isBlank()) continue;
            String paramKey = "t" + idx;
            query.append(" AND (recordId LIKE :").append(paramKey);
            query.append(" OR LOWER(member.fullName) LIKE :").append(paramKey).append("_name");
            query.append(" OR LOWER(book.title) LIKE :").append(paramKey).append("_title)");
            String likeValue = "%" + terms.get(idx).toLowerCase() + "%";
            params.put(paramKey, likeValue);
            params.put(paramKey + "_name", likeValue);
            params.put(paramKey + "_title", likeValue);
        }
        query.append(" ORDER BY recordId DESC");

        return getBorrowingRecords(query, params);
    }

    private List<BorrowingRecord> getBorrowingRecords(StringBuilder query, Map<String, Object> params) {
        List<BorrowingRecord> records = new ArrayList<>();
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query<BorrowingRecordEntity> sessionQuery = session.createQuery(query.toString(), BorrowingRecordEntity.class);
            params.forEach(sessionQuery::setParameter);
            List<BorrowingRecordEntity> entities = sessionQuery.list();
            for (BorrowingRecordEntity entity : entities) {
                records.add(toDto(entity));
            }
            return records;
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
