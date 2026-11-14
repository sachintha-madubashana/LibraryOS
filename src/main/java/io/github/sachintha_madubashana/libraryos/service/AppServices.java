package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.repository.BookRepository;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;
import io.github.sachintha_madubashana.libraryos.repository.hibernate.HibernateBookRepository;
import io.github.sachintha_madubashana.libraryos.repository.hibernate.HibernateBorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.hibernate.HibernateMemberRepository;
import io.github.sachintha_madubashana.libraryos.repository.mysql.MySQLBookRepository;
import io.github.sachintha_madubashana.libraryos.repository.mysql.MySQLBorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.mysql.MySQLMemberRepository;

public class AppServices {

    private static AppServices INSTANCE;
    private final BookService bookService;
    private final BorrowingService borrowingService;
    private final MemberService memberService;
    private final DashboardService dashboardService;

    private AppServices() {
        BookRepository bookRepository = new HibernateBookRepository();
        BorrowingRepository borrowingRepository = new HibernateBorrowingRepository();
        MemberRepository memberRepository = new HibernateMemberRepository();

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
