package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.MemberEntity;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class HibernateMemberRepository implements MemberRepository {

    @Override
    public Optional<Member> findById(String code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            MemberEntity entity = session
                    .createQuery("FROM MemberEntity WHERE code = :code", MemberEntity.class)
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
    public List<Member> find(List<String> terms, SortingValue sortingValue) {
        if(terms == null || sortingValue == null) return new ArrayList<>();

        StringBuilder query = new StringBuilder("FROM MemberEntity WHERE 1=1");
        Map<String, Object> parameters = new HashMap<>();

        for(int idx = 0; idx < terms.size(); idx++) {
            String term = terms.get(idx);
            if (term != null && !term.isBlank()) {
                query.append(" AND (LOWER(code) LIKE :term").append(idx)
                     .append(" OR LOWER(fullName) LIKE :term").append(idx)
                     .append(" OR LOWER(email) LIKE :term").append(idx).append(")");
                parameters.put("term" + idx, "%" + term.toLowerCase() + "%");
            }
        }

        switch (sortingValue) {
            case ID_ASCENDING -> query.append(" ORDER BY code ASC");
            case ID_DESCENDING -> query.append(" ORDER BY code DESC");
            case NAME_ASCENDING -> query.append(" ORDER BY fullName ASC");
            case NAME_DESCENDING -> query.append(" ORDER BY fullName DESC");
        }

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<MemberEntity> entities = session.createQuery(query.toString(), MemberEntity.class)
                    .setProperties(parameters)
                    .getResultList();
            List<Member> members = new ArrayList<>();
            for (MemberEntity entity : entities) {
                members.add(toDto(entity));
            }
            return members;
        }
    }

    @Override
    public Member findLast() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            MemberEntity entity = session.createQuery("FROM MemberEntity ORDER BY id DESC", MemberEntity.class)
                    .setMaxResults(1)
                    .uniqueResult();
            if (entity != null) return toDto(entity);
            return null;
        }
    }

    @Override
    public boolean save(Member member) {
        if (member == null || existsById(member.getCode())) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            MemberEntity entity = new MemberEntity();
            entity.setCode(member.getCode());
            entity.setFullName(member.getFullName());
            entity.setEmail(member.getEmail());
            entity.setPhone(member.getPhone());
            entity.setAddress(member.getAddress());
            entity.setJoinedDate(member.getJoinedDate());

            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to save member.", e);
        }
    }

    @Override
    public boolean update(Member member) {
        if (member == null || member.getId() == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            MemberEntity entity = session.find(MemberEntity.class, member.getId());
            if (entity == null) return false;

            entity.setFullName(member.getFullName());
            entity.setEmail(member.getEmail());
            entity.setPhone(member.getPhone());
            entity.setAddress(member.getAddress());
            entity.setJoinedDate(member.getJoinedDate());

            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to update member.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if (code == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            MemberEntity entity = session.createQuery("FROM MemberEntity WHERE code = :code", MemberEntity.class)
                    .setParameter("code", code)
                    .uniqueResult();
            if (entity == null)  return false;

            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to delete member.", e);
        }
    }

    @Override
    public Long count() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(*) FROM MemberEntity", Long.class)
                    .uniqueResult();
            return count != null ? count : 0;
        }
    }

    private Member toDto(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getCode(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getJoinedDate()
        );
    }
}
