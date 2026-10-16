package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InMemoryBookRepository {
    private final ObservableList<Book> books;

    public InMemoryBookRepository() {
        books = FXCollections.observableArrayList(SampleData.createBooks());
    }

    public Integer getBookIndex(Book book){
        return books.indexOf(book);
    }

    public ObservableList<Book> getAllBooks() {
        return books;
    }

    public Book findBookById(String id) {
        System.out.println("looking book id: " + id);
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {
        System.out.println("book added: " + book);
        books.add(book);
    }

    public void updateBook(int index, Book book) {
        System.out.println("book updated: " + book);
        books.set(index, book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }
}
