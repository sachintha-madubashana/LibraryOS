package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.repository.InMemoryBookRepository;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryBorrowingRepository;

public class AppServices {

    private static AppServices INSTANCE;
    private final BookService bookService;
    private final BorrowingHistoryService borrowingHistoryService;

    private AppServices() {
        bookService = new BookService(new InMemoryBookRepository());
        borrowingHistoryService = new BorrowingHistoryService(new InMemoryBorrowingRepository());
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

    public BorrowingHistoryService getBorrowingHistoryService() {
        return borrowingHistoryService;
    }
}
