package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.repository.InMemoryBookRepository;

public class AppServices {

    private static AppServices INSTANCE;
    private final BookService bookService;

    private AppServices() {
        bookService = new BookService(new InMemoryBookRepository());
    }

    public static AppServices getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppServices();
        }
        return INSTANCE;
    }

    public BookService getBookService() {
        return bookService;
    }

}
