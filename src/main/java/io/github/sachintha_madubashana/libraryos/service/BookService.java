package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryBookRepository;
import javafx.collections.ObservableList;

public class BookService {
    private final InMemoryBookRepository bookRepository;

    public BookService(InMemoryBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ObservableList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book findBookById(String id) {
        return bookRepository.findBookById(id);
    }

    public void addBook(Book book) {
        if (bookRepository.findBookById(book.getId()) != null) {
            throw new IllegalArgumentException(
                    "A book with this ID already exists."
            );
        }

        bookRepository.addBook(book);
    }

    public void updateBook(Book book) {
        Book existingBook = bookRepository.findBookById(book.getId());

        if (existingBook == null) {
            throw new IllegalArgumentException(
                    "Book not found: " + book.getId()
            );
        }

        bookRepository.updateBook(book);
    }

    public boolean removeBook(Book book) {
        return bookRepository.isBookExist(book) && bookRepository.removeBook(book);
    }

    public boolean removeBookById(String id) {
        Book book = bookRepository.findBookById(id);
        return book != null && bookRepository.removeBook(book);
    }

}
