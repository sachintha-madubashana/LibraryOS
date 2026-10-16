package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryBookRepository;
import javafx.collections.FXCollections;
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
        Book existingBook = bookRepository.findBookById(book.getId());
        System.out.println("Existing book: " + existingBook);
        if (existingBook != null) {
            bookRepository.updateBook(bookRepository.getBookIndex(existingBook), book);
        } else {
            bookRepository.addBook(book);
        }
    }

    public boolean removeBook(Book book) {
        Integer bookIndex = bookRepository.getBookIndex(book);
        if (bookIndex != -1) {
            return bookRepository.removeBook(book);
        }
        return false;
    }

    public boolean removeBookById(String id) {
        Book existingBook = findBookById(id);
        if (existingBook != null) {
            return bookRepository.removeBook(existingBook);
        }
        return false;
    }

}
