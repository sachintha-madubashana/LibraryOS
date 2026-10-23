package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.repository.*;

public class AppServices {

    private static AppServices INSTANCE;
    private final BookService bookService;
    private final BorrowingService borrowingService;
    private final MemberService memberService;
    private final DashboardService dashboardService;

    private AppServices() {
        BookRepository bookRepository = new InMemoryBookRepository();
        BorrowingRepository borrowingRepository = new InMemoryBorrowingRepository();
        MemberRepository memberRepository = new InMemoryMemberRepository();

        bookService = new BookService(bookRepository);
        borrowingService = new BorrowingService(borrowingRepository);
        memberService = new MemberService(memberRepository);
        dashboardService = new DashboardService(bookRepository, borrowingRepository, memberRepository);
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

    public BorrowingService getBorrowingHistoryService() {
        return borrowingService;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public DashboardService getDashboardService() {
        return dashboardService;
    }
}
