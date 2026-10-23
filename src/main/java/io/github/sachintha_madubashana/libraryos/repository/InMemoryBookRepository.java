package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.SampleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> books;

    public InMemoryBookRepository() {
        this.books = new ArrayList<>(SampleData.getBooks());
    }

    @Override
    public Optional<Book> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }

        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Book> findAll() {
        return List.copyOf(books);
    }

    @Override
    public boolean save(Book book) {
        if (book == null || existsById(book.getId())) {
            return false;
        }

        books.add(book);
        return true;
    }

    @Override
    public boolean update(Book book) {
        if (book == null || book.getId() == null) {
            return false;
        }

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
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

        return books.removeIf(book -> book.getId().equals(id));
    }
}
