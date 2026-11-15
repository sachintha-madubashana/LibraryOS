package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.MemberEntity;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Member> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<MemberEntity> entities = session.createQuery("FROM MemberEntity", MemberEntity.class).list();
            List<Member> members = new ArrayList<>();
            for (MemberEntity entity : entities) {
                members.add(toDto(entity));
            }
            return members;
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
