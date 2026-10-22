package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String id);
    boolean existsById(String id);
    List<Book> findAll();
    boolean save(Book book);
    boolean update(Book book);
    boolean deleteById(String id);
}
