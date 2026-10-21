package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.repository.InMemoryBookRepository;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryBorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryMemberRepository;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryRecentActivityRepository;

public class AppServices {

    private static AppServices INSTANCE;
    private final BookService bookService;
    private final BorrowingHistoryService borrowingHistoryService;
    private final MemberService memberService;
    private final RecentActivityService recentActivityService;

    private AppServices() {
        bookService = new BookService(new InMemoryBookRepository());
        borrowingHistoryService = new BorrowingHistoryService(new InMemoryBorrowingRepository());
        memberService = new MemberService(new InMemoryMemberRepository());
        recentActivityService = new RecentActivityService(new InMemoryRecentActivityRepository());
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

    public MemberService getMemberService() {
        return memberService;
    }

    public RecentActivityService getRecentActivityService() {
        return recentActivityService;
    }
}
