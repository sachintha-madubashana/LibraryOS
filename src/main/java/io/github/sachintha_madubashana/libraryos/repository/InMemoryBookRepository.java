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

    public boolean isBookExist(Book book){
        return books.contains(book);
    }

    public ObservableList<Book> getAllBooks() {
        return books;
    }

    public Book findBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(Book book) {
        Book existingBook = books.stream().filter(book1 -> book1.getId().equals(book.getId())).findFirst().orElse(null);
        int index = books.indexOf(existingBook);

        if (index != -1) {
            books.set(index, book);
        }
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }
}
