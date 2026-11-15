package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.BookEntity;
import io.github.sachintha_madubashana.libraryos.database.entity.CategoryEntity;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.Category;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HibernateBookRepository implements BookRepository {

    @Override
    public Optional<Book> findById(String code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BookEntity entity = session.createQuery("FROM BookEntity WHERE code = :code", BookEntity.class)
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
    public List<Book> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<BookEntity> entities = session.createQuery("FROM BookEntity", BookEntity.class).list();
            List<Book> books = new ArrayList<>();
            for (BookEntity entity : entities) {
                books.add(toDto(entity));
            }
            return books;
        }
    }

    @Override
    public boolean save(Book book) {
        if (book == null || existsById(book.getCode())) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CategoryEntity categoryEntity = session.find(CategoryEntity.class, book.getCategory().getId());
            if (categoryEntity == null) return false;

            BookEntity entity = new BookEntity();
            entity.setCode(book.getCode());
            entity.setTitle(book.getTitle());
            entity.setAuthor(book.getAuthor());
            entity.setPublishedYear(book.getPublishedYear());
            entity.setTotalCopies(book.getTotalCopies());
            entity.setAvailableCopies(book.getAvailableCopies());
            entity.setCategory(categoryEntity);

            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to save book.", e);
        }
    }

    @Override
    public boolean update(Book book) {
        if (book == null || book.getId() == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BookEntity entity = session.find(BookEntity.class, book.getId());
            if (entity == null) return false;

            CategoryEntity categoryEntity = session.find(CategoryEntity.class, book.getCategory().getId());
            if (categoryEntity == null) return false;

            entity.setTitle(book.getTitle());
            entity.setAuthor(book.getAuthor());
            entity.setPublishedYear(book.getPublishedYear());
            entity.setTotalCopies(book.getTotalCopies());
            entity.setAvailableCopies(book.getAvailableCopies());
            entity.setCategory(categoryEntity);

            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to update book.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if (code == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            BookEntity entity = session.createQuery("FROM BookEntity WHERE code = :code", BookEntity.class)
                    .setParameter("code", code)
                    .uniqueResult();

            if (entity == null) return false;

            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete book.", e);
        }
    }

    private Book toDto(BookEntity entity) {
        Category category = new Category(
                entity.getCategory().getId(),
                entity.getCategory().getName()
        );

        return new Book(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublishedYear(),
                category,
                entity.getTotalCopies(),
                entity.getAvailableCopies()
        );
    }
}
