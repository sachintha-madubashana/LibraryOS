package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class HibernateBookRepository implements BookRepository {

    @Override
    public Optional<Book> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public boolean save(Book book) {
        return false;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
